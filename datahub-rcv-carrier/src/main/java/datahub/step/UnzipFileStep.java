package datahub.step;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import datahub.exception.DataHubException;

/**
 * ZIPファイル解凍ステップ
 *
 */
@Component
public class UnzipFileStep extends BaseStep {

	/**
	 * コンストラクタ
	 */
	public UnzipFileStep(MessageSource ms) {
		super(ms);
	}

	@Value("${api_url_files_download}")
	private String apiUrlFilesDownload;
	@Value("${zip_files_path}")
	private String zipFilesPath;
	@Value("${recv_path}")
	private String recvPath;

	/**
	 * ZIPファイルをCSVファイルに解凍する
	 *  
	 */
	public Path execute(Path zipFilePath) throws DataHubException, IOException {
		logger.info("UnzipFileStep.execute 開始");
		// ZIPファイルパス存在チェック
		if (!Files.exists(zipFilePath)) {
			throw new DataHubException("ファイルが存在しません。ファイル名" + zipFilePath.getFileName().toString());
		}

		// ZIPファイル解凍
		Path csvFilePath = this.unzipFile(zipFilePath);
		logger.info("UnzipFileStep.execute 終了");
		return csvFilePath;
	}

	/**
	 * ZIPファイルを解凍する
	 * 
	 */
	private Path unzipFile(Path zipFilePath) throws IOException, DataHubException {
		logger.info("UnzipFileStep.unzipFile 開始");
		// フォルダ存在チェック
		Path csvRecvDir = Paths.get(zipFilesPath);
		csvRecvDir = this.mkdirs(csvRecvDir);

		// ZIP出力
		Path csvFilePath = null;
		try (FileInputStream fis = new FileInputStream(zipFilePath.toString());
				ZipInputStream zis = new ZipInputStream(fis);) {
			ZipEntry entry = zis.getNextEntry();
			while (entry != null) {
				csvFilePath = this.unzipEntry(entry, zis, csvRecvDir);
				entry = zis.getNextEntry();
			}
		} catch (IOException e) {
			throw new DataHubException("zip解凍エラー", e);
		}
		logger.info("zipファイルを解凍しました" +
				zipFilePath.getFileName().toString() + ":" + csvFilePath.getFileName().toString());
		logger.info("UnzipFileStep.unzipFile 終了");
		return csvFilePath;
	}

	/**
	 * ZipEntryの情報を出力先ファイルに書込み
	 * 
	 */
	private Path unzipEntry(ZipEntry entry, ZipInputStream zis, Path csvRecvDir)
			throws DataHubException, FileNotFoundException, IOException {
		logger.info("UnzipFileStep.unzipEntry 開始");
		String csvFileName = entry.getName();
		if (!csvFileName.endsWith(".csv")) {
			throw new DataHubException("csvファイルが見つかりませんでした。");
		}
		Path csvFilePath = Paths.get(csvRecvDir.toString(), csvFileName);
		try (FileOutputStream fos = new FileOutputStream(csvFilePath.toFile())) {
			// buffer for read and write data to file
			byte[] buffer = new byte[1024];
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		}
		zis.closeEntry();
		logger.info("UnzipFileStep.unzipEntry 終了");
		return csvFilePath;
	}

	/**
	 * ディレクトリ作成する
	 *  
	 */
	public Path mkdirs(Path targetDirPath) throws IOException {
		logger.info("UnzipFileStep.mkdirs 開始");
		if (Files.exists(targetDirPath)) {
			return targetDirPath;
		} else {
			Files.createDirectories(targetDirPath);
		}
		logger.info("UnzipFileStep.mkdirs 終了");
		return targetDirPath;
	}
}
