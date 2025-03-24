package datahub.step;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import datahub.entity.Arguments;
import datahub.entity.LoginInfo;
import datahub.exception.DataHubException;
import datahub.jsonData.JsonDataToAPI;

/**
 * 外部API接続用のクラス
 * 
 */
@Component
public class ExternalApiConnectionsStep extends BaseStep {

	/**	車輌マスタ*/
	@Value("${4902_DELETE}")
	private String vehicleMasterDelete;
	/**	車輌マスタ*/
	@Value("${4902_REGISTER}")
	private String vehicleMasterRegister;
	/** 能力運送情報*/
	@Value("${5001_DELETE}")
	private String carryingCapacityDelete;
	/** 能力運送情報*/
	@Value("${5001_REGISTER}")
	private String carryingCapacityRegister;
	/** キャリア向け運行依頼案件*/
	@Value("${30121_DELETE}")
	private String carrierServiceRequestProjectDelete;
	/** キャリア向け運行依頼案件*/
	@Value("${30121_REGISTER}")
	private String carrierServiceRequestProjectRegister;
	/** キャリア向け運行案件*/
	@Value("${30122_DELETE}")
	private String carrierOperationProjectsDelete;
	/** キャリア向け運行案件*/
	@Value("${30122_REGISTER}")
	private String carrierOperationProjectsRegister;

	private String url;

	@Autowired
	LoginInfo loginInfo;

	/**
	 * コンストラクタ
	 * 
	 */
	public ExternalApiConnectionsStep(MessageSource ms) {
		super(ms);
	}

	/**
	 * 外部API接続
	 * 
	 */
	public void externalApiConnections(Arguments arguments, JsonDataToAPI jsonData, String accessToken)
			throws DataHubException, IOException, InterruptedException {
		logger.info("ExternalApiConnectionsStep.externalApiConnections 開始");
		this.createUrl(arguments);
		logger.info("URL:" + this.url);
		HttpRequest httpRequest = this.createHttpRequet(arguments, jsonData, accessToken);
		this.requestHttp(httpRequest);
		logger.info("ExternalApiConnectionsStep.externalApiConnections 終了");

	}

	/**
	 * 削除時リクエスト送信
	 * 
	 */
	public void externalApiConnectionsDelete(Arguments arguments, String accessToken)
			throws DataHubException, IOException, InterruptedException {
		logger.info("ExternalApiConnectionsStep.externalApiConnectionsDelete 開始");
		this.createUrlToDelete(arguments.getDataTypeFromDsply());
		logger.info("URL:" + this.url);
		String xTracking = UUID.randomUUID().toString();
		HttpRequest httpRequest = this.methodTypeProvisionalDelete(arguments, accessToken, xTracking);
		this.requestHttp(httpRequest);
		logger.info("ExternalApiConnectionsStep.externalApiConnectionsDelete 終了");

	}

	/**
	 * 種別の判定/URLの作成
	 * 
	 */
	public void createUrl(Arguments arguments) {
		String condition = arguments.getCondition();//種別
		String dataType = arguments.getDataTypeFromDsply();//情報区分
		//登録時
		if ("0".equals(condition)) {
			this.createUrlToRegister(dataType);
		}
		//削除時
		else if ("2".equals(condition)) {
			this.createUrlToDelete(dataType);
		}

	}

	/**
	 * 外部IFのエンドポイントの設定(登録)
	 * 
	 */
	public void createUrlToRegister(String dataType) {
		if ("4902".equals(dataType)) {
			this.url = this.vehicleMasterRegister;
		} else if ("5001".equals(dataType)) {
			this.url = this.carryingCapacityRegister;
		} else if ("30121".equals(dataType)) {
			this.url = this.carrierServiceRequestProjectRegister;
		} else if ("30122".equals(dataType)) {
			this.url = this.carrierOperationProjectsRegister;
		}
	}

	/**
	 * 外部IFのエンドポイントの設定(削除)
	 * 
	 */
	public void createUrlToDelete(String dataType) {
		if ("4902".equals(dataType)) {
			this.url = this.vehicleMasterDelete;
		} else if ("5001".equals(dataType)) {
			this.url = this.carryingCapacityDelete;
		} else if ("30121".equals(dataType)) {
			this.url = this.carrierServiceRequestProjectDelete;
		} else if ("30122".equals(dataType)) {
			this.url = this.carrierOperationProjectsDelete;
		}
	}

	//データ送信メソッド

	/**
	 * HTTPリクエスト送信及びHTTPレスポンス受信
	 * 
	 */
	private void requestHttp(HttpRequest httpRequest) throws DataHubException, IOException, InterruptedException {
		int httpStatusCode = 0;

		HttpResponse<String> httpResponse = getResponse(httpRequest);

		// 予期しないフィールドを無視する設定を有効化
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		httpStatusCode = httpResponse.statusCode();
		// レスポンスボディを取得
		String responseBody = httpResponse.body();
		if (httpStatusCode == 200 || httpStatusCode == 201) {
			logger.info("データチャネルAPIが正常終了しました。レスポンスのステータスコード：" + httpStatusCode + "レスポンスボディ：" + responseBody);

		} else {

			throw new DataHubException(
					"データチャネルAPIが異常終了しました。レスポンスのステータスコード：" + httpStatusCode + "レスポンスボディ：" + responseBody);

		}

	}

	/**
	 * HTTPレスポンスを受信する
	 * 
	 */
	private HttpResponse<String> getResponse(HttpRequest httpRequest) throws IOException, InterruptedException {
		return HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
	}

	/**
	 * HTTPリクエストを作成する
	 * 
	 */
	private HttpRequest createHttpRequet(Arguments arguments, JsonDataToAPI jsonData,
			String accessToken) throws JsonProcessingException {
		String condition = arguments.getCondition();//種別
		String xTracking = UUID.randomUUID().toString();

		//登録時
		if ("0".equals(condition)) {
			HttpRequest httpRequest = this.methodTypePut(arguments, jsonData, accessToken, xTracking);
			return httpRequest;

			//更新時
		} else if ("1".equals(condition)) {
			HttpRequest httpRequest = this.methodTypePut(arguments, jsonData, accessToken, xTracking);
			return httpRequest;

			//削除時
		} else if ("2".equals(condition)) {

		}
		return null;
	}

	/**
	 * HTTPリクエストの作成(登録)
	 * 
	 */
	private HttpRequest methodTypePut(Arguments arguments, JsonDataToAPI jsonData, String accessToken,
			String xTracking)
			throws JsonProcessingException {
		logger.info("methodTypePut開始");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);

		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(this.url))
				.header("Content-Type", "application/json")
				//.header("Accept", "application/json")
				.header("Authorization", "Bearer " + accessToken)
				.header("apiKey", loginInfo.getApikey())
				.header("X-Tracking", xTracking)
				.PUT(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(jsonData)))
				.build();
		logger.info("Jsonデータ\n" + new ObjectMapper().writeValueAsString(jsonData));
		return httpRequest;
	}

	/**
	 * HTTPリクエストの作成(削除)恒久版
	 * 
	 */
	private HttpRequest methodTypeProvisionalDelete(Arguments arguments, String accessToken,
			String xTracking)
			throws JsonProcessingException {
		logger.info("methodTypeProvisionalDelete開始");

		String queryParams = this.selectQueryParams(arguments);
		HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(this.url + "?" + queryParams))
				.header("Content-Type", "application/json;")
				.header("Authorization", "Bearer " + accessToken)
				.header("apiKey", loginInfo.getApikey())
				.header("X-Tracking", xTracking)
				.DELETE()
				.build();
		logger.info("URL:" + httpRequest.uri());
		logger.info("methodTypeProvisionalDelete終了");
		return httpRequest;
	}

	/**
 * 情報区分によりクエリパラメータを分岐
 * 
 */
private String selectQueryParams(Arguments arguments) {
	String dataTypeToUse = arguments.getDataTypeToUse();
	List<String> selectedFields = null;
	if ("4902".equals(dataTypeToUse)) {
		selectedFields = List.of("giai");
	} else if ("5001".equals(dataTypeToUse)) {
		selectedFields = List.of("trsp_cli_prty_head_off_id", "trsp_cli_prty_brnc_off_id", "service_no",
				"service_strt_date");
	} else if ("3012".equals(dataTypeToUse)) {
		selectedFields = List.of("trsp_instruction_id", "service_no", "service_strt_date", "cnsg_prty_head_off_id",
				"cnsg_prty_brnc_off_id", "trsp_rqr_prty_head_off_id", "trsp_rqr_prty_brnc_off_id");
	}
	return this.buildQueryParams(arguments, selectedFields);
}

	/**
	 * クエリパラメータの生成
	 * 
	 */
	private String buildQueryParams(Arguments arguments, List<String> selectedFields) {
		return Stream.of(arguments.getClass().getDeclaredFields())
				.filter(field -> selectedFields.contains(field.getName()))
				.peek(field -> field.setAccessible(true))
				.map(field -> toQueryParam(field, arguments))
				.filter(param -> !param.isEmpty())
				.collect(Collectors.joining("&"));
	}

	/**
	 * フィールドと値をクエリパラメータ形式に変換
	 * 
	 */
	private static String toQueryParam(Field field, Arguments arguments) {
		try {
			Object value = field.get(arguments);
			if (value != null) {
				return field.getName() + "=" + value.toString();
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return "";
	}

}
