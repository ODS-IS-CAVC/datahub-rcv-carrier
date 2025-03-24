package datahub.job;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.util.StringUtil;
import datahub.constants.Constants;
import datahub.entity.Arguments;
import datahub.entity.FileStatusResponse;
import datahub.jsonData.JsonDataToAPI;
import datahub.jsonData.Vehicle;
import datahub.step.AddHeader;
import datahub.step.Check;
import datahub.step.CreateEventIdStep;
import datahub.step.CreateJsonStep;
import datahub.step.CsvReadStep;
import datahub.step.DeleteFileStep;
import datahub.step.ExternalApiConnectionsStep;
import datahub.step.ExternalApiLoginStep;
import datahub.step.RequestFileCreateStep;
import datahub.step.RequestFileDownloadStep;
import datahub.step.RequestFileStatusStep;
import datahub.step.UnzipFileStep;

/**
 *CSVファイルからJSONデータに変換ジョブ
 * 
 */
@Component
public class rcvJob implements ApplicationRunner {

	private int splitFileCount;
	private int exitCode;
	@Autowired
	private Check check;
	@Autowired
	CreateEventIdStep createEventIdStep;
	@Autowired
	RequestFileCreateStep requestFileCreateStep;
	@Autowired
	UnzipFileStep unzipFileStep;
	@Autowired
	DeleteFileStep deleteFileStep;
	private String processId;
	private Arguments arguments;
	private String prevExtractValue;
	private String curExtractValue;
	@Autowired
	RequestFileStatusStep requestFileStatusStep;
	@Autowired
	ExternalApiLoginStep externalApiLoginStep;
	@Autowired
	ExternalApiConnectionsStep externalApiConnectionsStep;
	@Autowired
	private RequestFileDownloadStep requestFileDownloadStep;
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(new Object() {
	}.getClass().getEnclosingClass());
	@Autowired
	CreateJsonStep createJsonStep;
	@Autowired
	CsvReadStep csvReadStep;
	@Autowired
	AddHeader addHeader;

	/**
	 * 実行処理
	 * 
	 */
	//登録・更新・削除ボタンによって起動される
	public void run(ApplicationArguments args) {
		try {
			//パラメータチェックとBeanに設定
			this.arguments = check.paramCheckAndSet(args);
			// イベントID生成
			String eventId = createEventIdStep.execute();
			//削除機能の場合は外部IFに接続
			String condition = arguments.getCondition();
			if ("2".equals(condition)) {
				//外部IF接続：認証
				String accessToken = externalApiLoginStep.externalApiLogin();			
				//外部IF接続：データチャネル //arguments.conditionをもとに送り先のURIを分ける。
				//外部IFデータ送信(JSON)
				externalApiConnectionsStep.externalApiConnectionsDelete(arguments, accessToken);

			} else {
				// 利用データ抽出API(作成依頼)呼出
				this.processId = requestFileCreateStep.execute(arguments, eventId, prevExtractValue, curExtractValue);

				// 利用データ抽出API(作成確認)呼出
				FileStatusResponse resposeBody = requestFileStatusStep.execute(arguments, eventId, processId);
				// 分割ファイル数が0の場合は処理を終了
				this.splitFileCount = resposeBody.getSplitCount();
				if (splitFileCount == 0) {
					logger.info("ファイル分割数：0");
					this.exitCode = 0;
					return;
				}
				// 利用データ抽出API(ダウンロード)呼出
				// 作成確認のレスポンス「分割ファイル数」分ループを行ってダウンロードを行う
				long recordCount = 0;
				int fileNo = 1;
				for (int seqNo = 0; seqNo < splitFileCount; seqNo++) {

					Path zipFilePath = requestFileDownloadStep.execute(arguments, arguments.getDataTypeToUse(),
							seqNo + 1,
							eventId,
							processId);

					// ファイル解凍
					Path csvFilePath = unzipFileStep.execute(zipFilePath);
					recordCount += Files.lines(Path.of(csvFilePath.toString()), Charset.forName(Constants.ENCODE))
							.count();

					//ヘッダー付与
					addHeader.addHeader(arguments.getDataTypeToUse(), csvFilePath);
					//csv読み込み 
					List<String> recordsList = csvReadStep.csvReadStep(csvFilePath);

					//車両マスタ登録用
					if ("4902".equals(this.arguments.getDataTypeToUse())) {
						logger.info("車両マスタ登録用");
						//CSV→JSON変換 
						List<Vehicle> vehicleList = createJsonStep.createJsonToRegister(arguments, recordsList);
						String accessToken = "";
						for (int i = 0; i < vehicleList.size(); i++) {
							JsonDataToAPI jsonDataToAPI = new JsonDataToAPI();
							// JSON に変換
							jsonDataToAPI.setAttribute(vehicleList.get(i));
							//外部IF接続：認証
							if (StringUtil.isNullOrEmpty(accessToken)) {
								accessToken = externalApiLoginStep.externalApiLogin();
							}
							//外部IF接続：データチャネル //arguments.conditionをもとに送り先のURIを分ける。
							//外部IFデータ送信(JSON)
							externalApiConnectionsStep.externalApiConnections(arguments, jsonDataToAPI, accessToken);
						}
						// 取得件数表示
						logger.info("レコード数" + String.valueOf(vehicleList.size()));
					} else {

						JsonDataToAPI jsonDataToAPI = new JsonDataToAPI();
						//CSV→JSON変換 
						createJsonStep.createJson(arguments, recordsList, jsonDataToAPI);

						//外部IF接続：認証
						String accessToken = externalApiLoginStep.externalApiLogin();

						//外部IF接続：データチャネル //arguments.conditionをもとに送り先のURIを分ける。
						//外部IFデータ送信(JSON)
						externalApiConnectionsStep.externalApiConnections(arguments, jsonDataToAPI, accessToken);
						// 取得件数表示
						logger.info("レコード数" + String.valueOf(recordCount));
					}
					//ZIP,CSV削除
					deleteFileStep.execute(arguments.getDataTypeToUse(), fileNo, processId);
					fileNo++;

				}
			}
			logger.info("他社システムへデータ登録処理終了 終了コード:" + exitCode);

		} catch (Exception e) {
			logger.error(e.getMessage());
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String stackTrace = sw.toString();//StackTraceを文字列型で取得
			logger.error(stackTrace);
		}
	}
}
