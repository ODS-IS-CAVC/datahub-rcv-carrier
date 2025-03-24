package datahub.step;

import org.springframework.boot.ApplicationArguments;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import datahub.entity.Arguments;
import datahub.exception.DataHubException;

/**
 * パラメータのバリデーションチェック
 */
@Component
public class Check extends BaseStep {
	public Check(MessageSource ms) {
		super(ms);
	}

	private Arguments arguments;

	/**
	 * パラメータのバリデーションチェック
	 */
	public Arguments paramCheckAndSet(ApplicationArguments args) throws DataHubException {

		// チェック完了後はパラメータクラスに設定
		String[] argsList = args.getSourceArgs();
		logger.info("引数チェックを実施しました。");
		String log = "";
		//引数をログに出力
		int count = 1;
		for (int i = 0; i < argsList.length; i++) {
			log = log + "第" + count + "引数:" + argsList[i] + "/";
			count++;
		}
		logger.info(log);

		// 個数チェック
		if (argsList.length != 17) {
			String inputCount = String.valueOf(argsList.length);
			throw new DataHubException("引数の個数エラーです。引数の数:" + inputCount);
		}
		logger.info("引数の個数チェックOK");

		this.arguments = new Arguments();
		arguments.setDataTypeFromDsply(argsList[0]);
		arguments.setCompanyId(argsList[1]);
		arguments.setDataOwnerId(argsList[2]);
		arguments.setUserId(argsList[3]);
		arguments.setPassword(argsList[4]);
		arguments.setWhereClause(argsList[5]);
		arguments.setCondition(argsList[6]);
		arguments.setGiai(argsList[7]);
		arguments.setTrsp_cli_prty_head_off_id(argsList[8]);
		arguments.setTrsp_cli_prty_brnc_off_id(argsList[9]);
		arguments.setService_no(argsList[10]);
		arguments.setService_strt_date(argsList[11]);
		arguments.setTrsp_instruction_id(argsList[12]);
		arguments.setCnsg_prty_head_off_id(argsList[13]);
		arguments.setCnsg_prty_brnc_off_id(argsList[14]);
		arguments.setTrsp_rqr_prty_head_off_id(argsList[15]);
		arguments.setTrsp_rqr_prty_brnc_off_id(argsList[16]);

		String dataTypeToUse = argsList[0];
		String functionName = "";
		if ("30121".equals(dataTypeToUse)) {
			functionName = "キャリア向け運行依頼案件";
		} else if ("30122".equals(dataTypeToUse)) {
			functionName = "キャリア向け運行案件";
		} else if ("4902".equals(dataTypeToUse)) {
			functionName = "車両マスタ";
		} else if ("5001".equals(dataTypeToUse)) {
			functionName = "荷主向け運行案件";
		} else {
			functionName = "テスト用";
		}

		//情報区分3012+1桁の場合は、末尾1桁をカット
		if (dataTypeToUse.length() == 5) {
			dataTypeToUse = dataTypeToUse.substring(0, 4);
		}

		arguments.setDataTypeToUse(dataTypeToUse);

		String condition = arguments.getCondition();
		if ("0".equals(condition)) {
			condition = "登録";
		} else if ("1".equals(condition)) {
			condition = "更新";
		} else if ("2".equals(condition)) {
			condition = "削除";
		}
		logger.info("情報区分:" + dataTypeToUse + " 機能名:" + functionName + "[" + condition + "]");
		return arguments;
	}
}
