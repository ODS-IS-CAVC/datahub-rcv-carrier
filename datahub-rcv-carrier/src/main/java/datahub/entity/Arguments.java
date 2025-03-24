package datahub.entity;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * 実行パラメータクラス
 *
 */
@Getter
@Setter
@Component
public final class Arguments {

	/** 情報区分・画面からの引数を保持しておく用・4桁or5桁*/
	private String dataTypeFromDsply;

	/** 企業ID*/
	private String companyId;

	/** データオーナーID*/
	private String dataOwnerId;

	/** ユーザーID*/
	private String userId;

	/** パスワード*/
	private String password;

	/** where句*/
	private String whereClause;

	/** データ送信条件*/
	private String condition;

	/** 情報区分・処理に使う用・4桁のみ*/
	private String dataTypeToUse;
	
	/**  GIAI*/
	private String giai;
	
	/** 運送事業者コード（本社）*/
	private String trsp_cli_prty_head_off_id;

	/** 運送事業者コード（事業所）*/
	private String trsp_cli_prty_brnc_off_id;

	/** 便・ダイヤ番号*/
	private String service_no;

	/** 便の運行日*/
	private String service_strt_date;

	/** 運送依頼番号*/
	private String trsp_instruction_id;

	/** 荷送人コード（本社）*/
	private String cnsg_prty_head_off_id;

	/** 荷送人コード（事業所）*/
	private String cnsg_prty_brnc_off_id;

	/** 運送依頼者コード（本社）*/
	private String trsp_rqr_prty_head_off_id;

	/** 運送依頼者コード（事業所）*/
	private String trsp_rqr_prty_brnc_off_id;
	

}