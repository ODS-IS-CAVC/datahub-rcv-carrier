package datahub.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * ログイン用Bean
 * 
 */
@Getter
@Setter
@Component

public class LoginInfo {


	@Value("${APIKEY}")
	private String apikey;
	@Value("${ID}")
	private String id;
	@Value("${SECRET}")
	private String secret;

}
