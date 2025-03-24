package datahub.step;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import datahub.entity.LoginInfo;

/**
 * 外部認証システムに接続するクラス
 */
@Component
public class ExternalApiLoginStep extends BaseStep {

	@Autowired
	LoginInfo loginInfo;

	@Value("${login}")
	private String login;

	/**
	 * コンストラクタ
	 * @param ms
	 */
	public ExternalApiLoginStep(MessageSource ms) {
		super(ms);
	}

	/**
	 * 外部認証システムに接続 
	 */
	public String externalApiLogin() throws IOException, InterruptedException {
		logger.info("外部認証システム　認証開始");

		//HttpRequestを作成する
		HttpRequest httpRequest = createHttpRequest();

		//HttpRequest送信、Response受信
		HttpResponse<String> httpResponse = httpResponse(httpRequest);
		String responseData = httpResponse.body();

		// アクセストークンの抽出
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode tokenData = objectMapper.readTree(responseData).get("accessToken");
		String accessToken = tokenData.asText();

		logger.info("accessToken:" + accessToken);
		logger.info("外部認証システム　認証終了");

		return accessToken;

	}

	/**
	 * HTTPRequest作成
	 */
	public HttpRequest createHttpRequest() throws JsonProcessingException {
		Map<String, String> loginInfoMap = new HashMap<>();
		loginInfoMap.put("clientId", loginInfo.getId());
		loginInfoMap.put("clientSecret", loginInfo.getSecret());
		logger.info("clientId: " + this.loginInfo.getId());
		logger.info("clientSecret: " + this.loginInfo.getSecret());

		String requestbody = new ObjectMapper().writeValueAsString(loginInfoMap);
		
		//Requestbody作成		
		logger.info("URL: " + this.login);
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(this.login))
				.headers("Content-Type", "application/json;")
				.headers("apiKey", loginInfo.getApikey())
				.POST(HttpRequest.BodyPublishers.ofString(requestbody))
				.build();

		return httpRequest;

	}

	/**
	 * HTTPリクエスト送信及びHTTPレスポンス受信
	 */
	public HttpResponse<String> httpResponse(HttpRequest httpRequest) throws IOException, InterruptedException {

		return HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());

	}
}
