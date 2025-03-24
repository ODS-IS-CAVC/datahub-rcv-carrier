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
import datahub.entity.FileCreateRequest;
import datahub.entity.FileCreateResponse;
import datahub.exception.DataHubException;

/**
 * 外部APIリクエスト送信
 */
@Component
public class RequestFileCreateStep extends BaseStep {

	@Value("${api_url_files_create}")
	private String urlFilesCreate;

	public RequestFileCreateStep(MessageSource ms) {
		super(ms);
	}

	/**
	 * HTTPリクエストを作成して、送信・レスポンス受信を行う
	 */
	public String execute(Arguments arguments, String eventId, String prevExtractValue, String curExtractValue)
			throws DataHubException, IOException, InterruptedException {
		// HTTPリクエストボディを作成
		FileCreateRequest requestBody = this.createFileCreateRequestBody(arguments, eventId, prevExtractValue,
				curExtractValue);
		// HTTPクライアント作成
		HttpRequest httpRequest = this.createHttpRequet(requestBody);
		// HTTPリクエスト送信&レスポンス受信
		String processId = this.requestHttp(httpRequest);
		return processId;
	}

	/**
	 * HttpRequestを作成する
	 * データ形式はapplication/json
	 */
	private HttpRequest createHttpRequet(FileCreateRequest requestBody) throws JsonProcessingException {
		// Jacksonを使用して、オブジェクト⇒Json文字列に変換
		logger.info("RequestFileCreateStep.createHttpRequet 開始");
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(this.urlFilesCreate))
				.headers("Content-Type", "application/json;")
				.headers("Accept", "application/json")
				.headers("Authorization", "")
				.POST(HttpRequest.BodyPublishers.ofString(
						new ObjectMapper().writeValueAsString(requestBody)))
				.build();
		logger.info("URI:" + this.urlFilesCreate);
		logger.info("RequestFileCreateStep.createHttpRequet 終了");
		return httpRequest;
	}

	/**
	 * HTTPリクエストボディに設定するオブジェクトを生成する。前回抽出キーおよび今回抽出キーは送らない
	 */
	private FileCreateRequest createFileCreateRequestBody(Arguments arguments, String eventId, String prevExtractValue,
			String curExtractValue) {
		logger.info("RequestFileCreateStep.createFileCreateRequestBody 開始");
		FileCreateRequest requestBody = new FileCreateRequest();
		requestBody.setToId(arguments.getCompanyId());//実行画面から貰う
		requestBody.setFromId(arguments.getDataOwnerId());//実行画面から貰う
		requestBody.setDataType(arguments.getDataTypeToUse());//実行画面から貰って加工したもの(4桁)
		requestBody.setEventId(eventId);
		requestBody.setDhUserId(arguments.getUserId());//実行画面から貰う
		requestBody.setDhPassword(arguments.getPassword());//実行画面から貰う
		requestBody.setExtractKey(arguments.getWhereClause());//抽出条件・実行画面から貰う
		logger.info("RequestFileCreateStep.createFileCreateRequestBody 終了");
		return requestBody;
	}

	/**
	 * HTTPリクエスト送信及びHTTPレスポンス受信
	 */
	private String requestHttp(HttpRequest httpRequest) throws DataHubException, IOException, InterruptedException {
		logger.info("RequestFileCreateStep.requestHttp 開始");
		int httpStatusCode = 0;
		String processId = "";

		HttpResponse<String> httpResponse = this.getResponse(httpRequest);

		// 予期しないフィールドを無視する設定を有効化
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		httpStatusCode = httpResponse.statusCode();

		if (httpStatusCode == 201) { //成功した場合
			FileCreateResponse responseBody = objectMapper.readValue(httpResponse.body(), FileCreateResponse.class);
			processId = responseBody.getProcessId();
			String strStatusCode = String.valueOf(httpStatusCode);
			logger.info("RequestFileCreateStep.requestHttp ステータスコード：" + strStatusCode);

		} else { //失敗した場合
			logger.info("RequestFileCreateStep.requestHttp ステータスコード：" + String.valueOf(httpStatusCode));
			throw new DataHubException("ファイル作成リクエストのレスポンスのステータスコードがエラーです。");
		}
		logger.info("RequestFileCreateStep.requestHttp 終了 プロセスID:" + processId);
		return processId;
	}

	/**
	 * HTTPレスポンスを受信する
	 * 
	 */
	private HttpResponse<String> getResponse(HttpRequest httpRequest) throws IOException, InterruptedException {
		return HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
	}

}
