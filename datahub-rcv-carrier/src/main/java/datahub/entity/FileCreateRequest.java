package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 利用データ抽出(作成依頼)呼出時に送信するリクエストボディ
 *
 */
@Getter
@Setter
public class FileCreateRequest {

	/**
	 * 送信先企業ID=企業ID
	 */
	@JsonProperty("to_id")
	private String toId;

	/**
	 * 送信元企業ID=データオーナーID
	 */
	@JsonProperty("from_id")
	private String fromId;

	/**
	 * 情報区分
	 */
	@JsonProperty("data_type")
	private String dataType;

	/**
	 * イベントID
	 */
	@JsonProperty("event_id")
	private String eventId;

	/**
	 * 抽出キー
	 */
	@JsonProperty("extract_key")
	private String extractKey;

	/**
	 * ユーザーID(DH認証用)
	 */
	@JsonProperty("dh_user_id")
	private String dhUserId;

	/**
	 * パスワード(DH認証用)
	 */
	@JsonProperty("dh_password")
	private String dhPassword;
}
