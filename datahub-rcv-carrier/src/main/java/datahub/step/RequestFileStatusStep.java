package datahub.step;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import datahub.entity.Arguments;
import datahub.entity.ErrorResponse;
import datahub.entity.FileStatusRequest;
import datahub.entity.FileStatusResponse;
import datahub.exception.DataHubException;

/**
 * 利用データ抽出API(作成確認)呼出ステップ
 *
 */
@Component
public class RequestFileStatusStep extends BaseStep {

	/**
	 * コンストラクタ
	 * 
	 */
	public RequestFileStatusStep(MessageSource ms) {
		super(ms);

	}

	@Value("${api_url_files_status}")
	private String urlFilesStatus;

	/**
	 * 利用データ抽出API(作成確認)呼出を行い、ダウンロード対象のファイルの作成状況を確認する
	 * INIファイル「定周期回数」分ループを行い、
	 * INIファイル「定周期時間」待機する。定周期回数分を超えた場合は、異常終了
	 * 
	 */
	public FileStatusResponse execute(Arguments arguments, String eventId, String processId)
			throws DataHubException, IOException, InterruptedException {
		logger.info("FileStatusResponseID.execute 開始");
		int loopCount = 20;
		long loopTimeSecond = 1 * 1000;
		FileStatusResponse responseBody = new FileStatusResponse();
		// HTTPリクエストボディを作成
		FileStatusRequest requestBody = this.createFileStatusRequestBody(arguments, eventId, processId);
		for (int i = 0; i <= loopCount; i++) {
			if (i == loopCount) {
				throw new DataHubException("ループ回数上限エラー");
			}
			logger.info("プロセスID" + processId);
			// HTTPクライアント作成
			HttpRequest httpRequest = this.createHttpRequet(requestBody);

			// HTTPリクエスト送信&レスポンス受信
			responseBody = this.requestHttp(httpRequest);

			// 作成未完 : 待機して再実行
			if (responseBody.getIsCreated() == 1) {
				Thread.sleep(loopTimeSecond);
			} else {
				break;
			}
		}
		logger.info("FileStatusResponseID.execute 終了");
		return responseBody;
	}

	/**
	 * HTTPリクエストボディに設定するオブジェクトを生成する
	 * 
	 */
	private FileStatusRequest createFileStatusRequestBody(Arguments arguments, String eventId, String processId) {
		logger.info("FileStatusResponseID.createFileStatusRequestBody 開始");
		FileStatusRequest requestBody = new FileStatusRequest();
		requestBody.setToId(arguments.getCompanyId());//画面からもらう
		requestBody.setEventId(eventId);
		requestBody.setProcessId(processId);
		requestBody.setDhUserId(arguments.getUserId());//画面からもらう
		requestBody.setDhPassword(arguments.getPassword());// 画面からもらう
		logger.info("FileStatusResponseID.createFileStatusRequestBody 終了");
		return requestBody;
	}

	/**
	 * HttpRequestを作成する
	 * データ形式はapplication/json
	 * 
	 */
	private HttpRequest createHttpRequet(FileStatusRequest requestBody) throws JsonProcessingException {
		// Jacksonを使用して、オブジェクト⇒Json文字列に変換
		logger.info("FileStatusResponseID.createHttpRequet 開始");
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(urlFilesStatus))
				.headers("Content-Type", "application/json;")
				.headers("Accept", "application/json")
				.headers("Authorization", "")
				.POST(HttpRequest.BodyPublishers.ofString(
						new ObjectMapper().writeValueAsString(requestBody)))
				.build();
		logger.info(" URI:" + urlFilesStatus);
		logger.info("FileStatusResponseID.createHttpRequet 終了");
		return httpRequest;
	}

	/**
	 * HTTPリクエスト送信及びHTTPレスポンス受信
	 * 
	 */
	private FileStatusResponse requestHttp(HttpRequest httpRequest)
			throws DataHubException, IOException, InterruptedException {
		logger.info("FileStatusResponseID.requestHttp 開始");
		FileStatusResponse responseBody = new FileStatusResponse();
		int httpStatusCode = 0;
		int splitCount = 0;
		int isCreated = 0;
		String messageId = "";
		String message = "";

		HttpResponse<String> httpResponse = getResponse(httpRequest);
		httpStatusCode = httpResponse.statusCode();
		// 予期しないフィールドを無視する設定を有効化
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		if (httpStatusCode == 201) {
			responseBody = objectMapper.readValue(httpResponse.body(), FileStatusResponse.class);
			isCreated = responseBody.getIsCreated();
			splitCount = responseBody.getSplitCount();
			if (isCreated == 0 && splitCount == 0) {
				logger.info("ファイルサイズが0");
			} else if (isCreated == 0 && splitCount > 0) {
				String count = String.valueOf(splitCount);
				logger.info("ファイル作成済み。" + "分割数:" + count);
			} else {
				logger.info("ファイル作成実行中");
			}
		} else {
			ErrorResponse errorResponse = objectMapper.readValue(httpResponse.body(), ErrorResponse.class);
			messageId = errorResponse.getMessageId();
			message = errorResponse.getMessage();

			String[] messagesArray = { String.valueOf(httpStatusCode), messageId, message };
			logger.error("ファイル作成NG:" + messagesArray);
			// ループ処理に戻るため、明示的に未作成である状態を設定する
			responseBody.setIsCreated(1);
			throw new DataHubException("ファイルステータス確認のレスポンスのステータスコードがエラーです。");
		}
		logger.info("FileStatusResponseID.requestHttp 終了");
		return responseBody;
	}

	/**
	 * HTTPレスポンスを受信する
	 * 
	 */
	private HttpResponse<String> getResponse(HttpRequest httpRequest) throws IOException, InterruptedException {
		return HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
	}
}
