package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 利用データ抽出(作成確認)呼出時に送信するリクエストボディ
 *
 */
@Getter
@Setter
public class FileStatusRequest {
	
	/**
	 * 送信先企業ID
	 */
	@JsonProperty("to_id")
	private String toId;
	
	/**
	 * イベントID
	 */
	@JsonProperty("event_id")
	private String eventId;
	
	/**
	 * プロセスID
	 */
	@JsonProperty("process_id")
	private String processId;
	
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