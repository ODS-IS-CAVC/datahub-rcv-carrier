package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * エラー時のレスポンスボディ
 *
 */
@Getter
@Setter
public class ErrorResponse {

	/**
	 * HTTPステータス
	 */
	@JsonProperty("status")
	private int status;

	/**
	 * メッセージID
	 */
	@JsonProperty("error_id")
	private String messageId;

	/**
	 * メッセージ
	 */
	@JsonProperty("error")
	private String message;
}
