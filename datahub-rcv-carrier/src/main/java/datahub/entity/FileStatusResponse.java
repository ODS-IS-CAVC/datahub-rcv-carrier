package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 利用データ抽出(作成確認)呼出時に受信するレスポンスボディ
 *
 */
@Getter
@Setter
public class FileStatusResponse {

	/**
	 * HTTPステータス
	 */
	@JsonProperty("status")
	private int status;

	/**
	 * 作成完了フラグ
	 */
	@JsonProperty("is_created")
	private int isCreated;

	/**
	 * ファイル分割数
	 */
	@JsonProperty("split_count")
	private int splitCount;

}
