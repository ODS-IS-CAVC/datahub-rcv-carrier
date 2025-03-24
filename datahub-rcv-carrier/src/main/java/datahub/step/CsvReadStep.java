package datahub.step;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import datahub.constants.Constants;
import datahub.exception.DataHubException;

/**
 * CSVファイルをレコード化する
 * 
 */
@Component
public class CsvReadStep extends BaseStep {
	public CsvReadStep(MessageSource ms) {
		super(ms);
	}

	/**
	 * CSVファイルをレコード化するクラス
	 * 
	 */
	public List<String> csvReadStep(Path csvFilePath)
			throws DataHubException, IOException, SQLException, Exception {
		logger.info("CsvReadStep.csvReadStep 開始");
		List<String> recordsList = new ArrayList<>();

		// 1.CSVファイルの存在チェック
		if (!Files.exists(csvFilePath)) {
			throw new DataHubException("ファイルが存在しません" +
					csvFilePath.getFileName().toString());
		}

		// CSVファイルからレコードを取得して、リスト化
		recordsList = this.readCsv(csvFilePath);
		//クォーテーションを削除
		List<String> replaceRecordsList = recordsList.stream()
				.map(item -> item.replace("\"", ""))
				.collect(Collectors.toList());

		logger.info("CsvReadStep.csvReadStep 終了");
		return replaceRecordsList;
	}

	/**
	 * CSVファイルを読み込んでリスト化する
	 * 
	 */
	private List<String> readCsv(Path csvFilePath) throws FileNotFoundException, IOException {
		try (Stream<String> stream = Files.lines(csvFilePath, Charset.forName(Constants.ENCODE))) {
			return Collections.unmodifiableList(stream.collect(Collectors.toList()));
		}
	}
}
