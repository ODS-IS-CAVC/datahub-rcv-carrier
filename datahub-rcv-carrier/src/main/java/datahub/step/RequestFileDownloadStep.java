package datahub.step;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import datahub.constants.Constants;
import datahub.entity.Arguments;
import datahub.entity.ErrorResponse;
import datahub.entity.FileDownloadRequest;
import datahub.entity.FileDownloadResponse;
import datahub.exception.DataHubException;

/**
 * 利用データ抽出API(ダウンロード)呼出ステップ
 *
 */
@Component
public class RequestFileDownloadStep extends BaseStep {

	/**
	 * コンストラクタ
	 */
	public RequestFileDownloadStep(MessageSource ms) {
		super(ms);

	}

	@Value("${api_url_files_download}")
	private String apiUrlFilesDownload;
	@Value("${zip_files_path}")
	private String zipFilesPath;

	/**
	 * 利用データ抽出API(ダウンロード)呼出を行い、ファイルをダウンロードする
	 * 
	 */
	public Path execute(Arguments arguments, String dataType, int seqNo, String eventId, String processId)
			throws DataHubException, IOException, InterruptedException {
		logger.info("RequestFileDownloadStep.execute 開始");
		// HTTPリクエストボディを作成
		FileDownloadRequest requestBody = this.createFileDownloadRequestBody(arguments, seqNo, eventId, processId);
		String strSeqNo = String.format("%03d", seqNo);
		String donwloadFileName = dataType + "_" + processId + "_" + strSeqNo + ".zip";
		logger.info("ファイルダウンロードを開始しました。ファイル名:" + donwloadFileName);

		// HTTPクライアント作成
		HttpRequest httpRequest = this.createHttpRequet(requestBody);

		// HTTPリクエスト送信&レスポンス受信
		FileDownloadResponse responseBody = this.requestHttp(httpRequest);

		// ダウンロードファイルを所定フォルダに格納
		Path downloadZipFilePath = this.savaDonwloadDataToFile(responseBody);
		logger.info("RequestFileDownloadStep.execute 終了");
		return downloadZipFilePath;
	}

	/**
	 * HTTPリクエストボディに設定するオブジェクトを生成する
	 * 
	 */
	private FileDownloadRequest createFileDownloadRequestBody(Arguments arguments, int seqNo, String eventId,
			String processId) {
		logger.info("RequestFileDownloadStep.createFileDownloadRequestBody 開始");
		FileDownloadRequest requestBody = new FileDownloadRequest();
		requestBody.setToId(arguments.getCompanyId());//画面からもらう
		requestBody.setEventId(eventId);
		requestBody.setProcessId(processId);
		String strSeqNo = String.valueOf(seqNo);
		requestBody.setSplitSeqNo(strSeqNo);
		requestBody.setDhUserId(arguments.getUserId());//画面からもらう
		requestBody.setDhPassword(arguments.getPassword());//画面からもらう
		logger.info("RequestFileDownloadStep.createFileDownloadRequestBody 終了");
		return requestBody;
	}

	/**
	 * HttpRequestを作成する
	 * データ形式はapplication/json
	 * 
	 */
	private HttpRequest createHttpRequet(FileDownloadRequest requestBody) throws JsonProcessingException {
		// Jacksonを使用して、オブジェクト⇒Json文字列に変換
		logger.info("RequestFileDownloadStep.createHttpRequet 開始");
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(apiUrlFilesDownload))
				.headers("Content-Type", "application/json;")
				.headers("Accept", "application/json")
				.headers("Authorization", "")
				.POST(HttpRequest.BodyPublishers.ofString(
						new ObjectMapper().writeValueAsString(requestBody)))
				.build();
		logger.info(" URI:" + apiUrlFilesDownload);
		logger.info("RequestFileDownloadStep.createHttpRequet 終了");
		return httpRequest;
	}

	/**
	 * HTTPリクエスト送信及びHTTPレスポンス受信
	 * 
	 */
	private FileDownloadResponse requestHttp(HttpRequest httpRequest)
			throws DataHubException, IOException, InterruptedException {
		logger.info("RequestFileDownloadStep.requestHttp 開始");
		FileDownloadResponse responseBody = new FileDownloadResponse();
		int httpStatusCode = 0;
		String message = "";

		HttpResponse<String> httpResponse = getResponse(httpRequest);
		httpStatusCode = httpResponse.statusCode();

		// 予期しないフィールドを無視する設定を有効化
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		if (httpStatusCode == 201) {
			responseBody = objectMapper.readValue(httpResponse.body(), FileDownloadResponse.class);
			String fileName = responseBody.getZipfileName();
			String strStatusCode = String.valueOf(httpStatusCode);
			logger.info("ファイルダウンロードに成功しました。 ファイル名:" + fileName + "ステータス:" + strStatusCode);
		} else {
			ErrorResponse errorResponse = objectMapper.readValue(httpResponse.body(), ErrorResponse.class);
			message = errorResponse.getMessage();

			throw new DataHubException("ファイルダウンロードに失敗しました。レスポンスコードがエラーです。" + message);
		}
		logger.info("RequestFileDownloadStep.requestHttp 終了");

		return responseBody;
	}

	/**
	 * HTTPレスポンスを受信する
	 * 
	 */
	private HttpResponse<String> getResponse(HttpRequest httpRequest) throws IOException, InterruptedException {
		return HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
	}

	/**
	 * ダウンロードデータをファイルとして保存する
	 * 
	 */
	private Path savaDonwloadDataToFile(FileDownloadResponse responseBody) throws DataHubException, IOException {
		logger.info("RequestFileDownloadStep.savaDonwloadDataToFile 開始");
		// ファイルーデータの取得
		String zipfileContent = responseBody.getZipfileContent();
		String zipfileName = responseBody.getZipfileName();
		// Base64デコード
		byte[] bytes = this.decodeBase64(zipfileContent);
		// フォルダ存在チェック
		Path zipFileSaveDir = Paths.get(zipFilesPath);
		zipFileSaveDir = this.mkdirs(zipFileSaveDir);

		Path zipFilePath = Paths.get(zipFileSaveDir.toString(), zipfileName);
		// ZIPファイルの同名ファイル存在チェック
		if (Files.exists(zipFilePath)) {
			throw new DataHubException("既に同名ファイルが存在します。ファイルパス:" + zipFilePath);
		}
		// zipファイル格納
		// 格納先 : 作業領域
		this.outputDataToFile(zipFilePath, bytes);
		logger.info("RequestFileDownloadStep.savaDonwloadDataToFile 終了");
		return zipFilePath;
	}

	/**
	 * Base64をデコードする
	 * 
	 */
	private byte[] decodeBase64(String zipfileContent) throws UnsupportedEncodingException {
		byte[] bytes = Base64.getDecoder().decode(zipfileContent.getBytes(Constants.ENCODE));
		return bytes;
	}

	/**
	 * 指定されたファイルにデータを出力する
	 * 
	 */
	private void outputDataToFile(Path zipFilePath, byte[] zipContent) throws FileNotFoundException, IOException {
		try (FileOutputStream writer = new FileOutputStream(zipFilePath.toFile())) {
			writer.write(zipContent);
		}
		logger.info("zipファイルを保存しました。" + zipFilePath);
	}

	/**
	 * ディレクトリ作成する
	 *  
	 */
	public Path mkdirs(Path targetDirPath) throws IOException {
		logger.info("RequestFileDownloadStep.mkdirs 開始");
		if (Files.exists(targetDirPath)) {
			return targetDirPath;
		} else {
			Files.createDirectories(targetDirPath);
		}
		logger.info("RequestFileDownloadStep.mkdirs 終了");
		return targetDirPath;
	}
}
