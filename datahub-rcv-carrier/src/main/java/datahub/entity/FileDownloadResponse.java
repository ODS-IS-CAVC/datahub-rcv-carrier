package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 利用データ抽出(ダウンロード)呼出時に受信するレスポンスボディ
 *
 */
@Getter
@Setter
public class FileDownloadResponse {

	/**
	 * HTTPステータス
	 */
	@JsonProperty("status")
	private int status;

	/**
	 * ZIPファイル名
	 */
	@JsonProperty("zipfile_name")
	String zipfileName;

	/**
	 * ZIPファイルデータ
	 */
	@JsonProperty("zipfile_content")
	String zipfileContent;

}
