package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 利用データ抽出(作成依頼)呼出時に受信するレスポンスボディ
 *
 */
@Getter
@Setter
public class FileCreateResponse {

	/**
	 * HTTPステータス
	 */
	@JsonProperty("status")
	private int status;

	/**
	 * プロセスID
	 */
	@JsonProperty("process_id")
	private String processId;

}