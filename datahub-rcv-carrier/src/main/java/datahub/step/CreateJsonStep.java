package datahub.step;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import datahub.Service.ConvertCsvToJsonPattern3012;
import datahub.Service.ConvertCsvToJsonPattern4902;
import datahub.Service.ConvertCsvToJsonPattern5001;
import datahub.entity.Arguments;
import datahub.jsonData.JsonDataToAPI;
import datahub.jsonData.Vehicle;

/**
 * 変換情報区分によりCSVファイルからJsonデータに変換
 * 
 */
@Component
public class CreateJsonStep extends BaseStep {

	/**
	 * コンストラクタ
	 */
	public CreateJsonStep(MessageSource ms) {
		super(ms);
	}

	@Autowired
	ConvertCsvToJsonPattern5001 convertCsvToJsonPattern5001;

	@Autowired
	ConvertCsvToJsonPattern4902 convertCsvToJsonPattern4902;

	@Autowired
	ConvertCsvToJsonPattern3012 convertCsvToJsonPattern3012;


	/**
	 * 変換情報区分によりCSVファイルからJsonデータに変換
	 * 
	 */
	public void createJson(Arguments arguments, List<String> csvData, JsonDataToAPI jsonDataToAPITest)
			throws Exception {
		logger.info("CreateJsonStep.createJson 開始");
		String dataType = arguments.getDataTypeFromDsply();
		if ("4902".equals(dataType)) {
			// 個別実装
			convertCsvToJsonPattern4902.convertCsvToJson(csvData);

		} else if ("5001".equals(dataType)) {
			//個別実装
			convertCsvToJsonPattern5001.convertCsvToJson(csvData, jsonDataToAPITest);

		} else if ("30121".equals(dataType)) {
			// 個別実装
			convertCsvToJsonPattern3012.convertCsvToJson(csvData, jsonDataToAPITest);

		} else if ("30122".equals(dataType)) {
			//個別実装
			convertCsvToJsonPattern3012.convertCsvToJson(csvData, jsonDataToAPITest);
		}
		logger.info("CreateJsonStep.createJson 終了");

	}

	/**
	 * 車両マスタ登録用
	 * 
	 */
	public List<Vehicle> createJsonToRegister(Arguments arguments, List<String> csvData) throws Exception {
		//個別実装

		return convertCsvToJsonPattern4902.convertCsvToJson(csvData);

	}

}
