package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 利用データ抽出(ダウンロード)呼出時に送信するリクエストボディ
 *
 */
@Getter
@Setter
public class FileDownloadRequest {
	
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
	 * 分割枝番
	 */
	@JsonProperty("split_seq_no")
	private String splitSeqNo;
	
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
