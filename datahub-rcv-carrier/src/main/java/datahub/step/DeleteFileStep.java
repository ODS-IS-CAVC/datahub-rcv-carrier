package datahub.step;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * ファイル削除ステップ
 *
 */
@Component
public class DeleteFileStep extends BaseStep {

	/**
	 * コンストラクタ
	 */
	public DeleteFileStep(MessageSource ms) {
		super(ms);

	}

	@Value("${api_url_files_download}")
	private String apiUrlFilesDownload;
	@Value("${zip_files_path}")
	private String zipFilesPath;
	@Value("${work_path}")
	private String workPath;
	@Value("${recv_path}")
	private String recvPath;

	/**
	 * ZIPファイルとCSVファイル削除を行う
	 */
	public void execute(String dataType, int fileNo, String processId) {
		// 成功有無に関わらずZIPファイル、CSVファイルを削除する
		// ただし、INIファイル「出力方式」にて、ファイル出力が指定されている場合は、CSVファイルの削除は行わない
		// 情報区分単位で削除制御を行うので、他の保存ファイルが巻き込み削除されないようにする。
		// 削除中に例外が発生した場合でも必ず本処理で取得したファイルが削除されるようにする。
		logger.info("DeleteFileStep.execute 開始");

		String fileNameWithoutExtension = dataType + "_" + processId + "_" + String.format("%03d", fileNo);
		// ダウンロードファイル(ZIP)削除
		Path deleteZipFilePath = Paths.get(zipFilesPath, fileNameWithoutExtension + ".zip");
		deleteFile(deleteZipFilePath);

		// CSVファイルも削除

		Path deleteCsvFilePath = Paths.get(zipFilesPath, fileNameWithoutExtension + ".csv");
		deleteFile(deleteCsvFilePath);

		logger.info("DeleteFileStep.execute 終了");
	}

	/**
	 * ファイル削除する
	 */
	private void deleteFile(Path deleteTargetFilePath) {
		try {
			Files.delete(deleteTargetFilePath);
			logger.info("ファイル削除に成功しました。ファイルパス" + deleteTargetFilePath);
		} catch (IOException e) {
			logger.error("ファイル削除に失敗しました。ファイルパス" + deleteTargetFilePath);
		}
	}
}
