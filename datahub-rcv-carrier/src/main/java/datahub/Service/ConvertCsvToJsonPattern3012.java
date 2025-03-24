package datahub.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import datahub.jsonData.JsonDataToAPI;
import datahub.jsonData.TransportPlan;
import datahub.jsonData.TransportPlan.CneePrty;
import datahub.jsonData.TransportPlan.Cns;
import datahub.jsonData.TransportPlan.CnsLineItem;
import datahub.jsonData.TransportPlan.CnsgPrty;
import datahub.jsonData.TransportPlan.CutOffInfo;
import datahub.jsonData.TransportPlan.DelInfo;
import datahub.jsonData.TransportPlan.FreeTimeInfo;
import datahub.jsonData.TransportPlan.FretClimToPrty;
import datahub.jsonData.TransportPlan.LogsSrvcPrv;
import datahub.jsonData.TransportPlan.Msginfo;
import datahub.jsonData.TransportPlan.RoadCarr;
import datahub.jsonData.TransportPlan.ShipFromPrty;
import datahub.jsonData.TransportPlan.ShipFromPrtyRqrm;
import datahub.jsonData.TransportPlan.ShipToPrty;
import datahub.jsonData.TransportPlan.ShipToPrtyRqrm;
import datahub.jsonData.TransportPlan.TrspIsr;
import datahub.jsonData.TransportPlan.TrspPlan;
import datahub.jsonData.TransportPlan.TrspPlanLineItem;
import datahub.jsonData.TransportPlan.TrspRqrPrty;
import datahub.jsonData.TransportPlan.TrspSrvc;
import datahub.jsonData.TransportPlan.TrspVehicleTrms;
import datahub.step.BaseStep;

/**
 * キャリア向け運行依頼案件用CSVファイルからJSONデータ変換
 * 
 */
@Service
public class ConvertCsvToJsonPattern3012 extends BaseStep {

	/**
	 * コンストラクタ
	 */
	public ConvertCsvToJsonPattern3012(MessageSource ms) {
		super(ms);

	}

	//  msg_info  関連のフィールド 
	public final List<String> MSG_INFO = Arrays.asList("msg_id", "msg_info_cls_typ_cd", "msg_date_iss_dttm",
			"msg_time_iss_dttm",
			"msg_fn_stas_cd", "note_dcpt_txt");
	//  trsp_plan  関連のフィールド 
	public final List<String> TRSP_PLAN = Arrays.asList("trsp_plan_stas_cd");
	//  trsp_isr  関連のフィールド 
	public final List<String> TRSP_ISR = Arrays.asList("trsp_instruction_id", "trsp_instruction_date_subm_dttm",
			"inv_num_id",
			"cmn_inv_num_id", "mix_load_num_id");
	//  trsp_srvc  関連のフィールド 
	public final List<String> TRSP_SRVC = Arrays.asList("service_no", "service_name", "service_strt_date",
			"service_strt_time",
			"service_end_date", "service_end_time", "freight_rate", "trsp_means_typ_cd", "trsp_srvc_typ_cd",
			"road_carr_srvc_typ_cd",
			"trsp_root_prio_cd", "car_cls_prio_cd", "cls_of_carg_in_srvc_rqrm_cd", "cls_of_pkg_up_srvc_rqrm_cd",
			"pyr_cls_srvc_rqrm_cd", "trms_of_mix_load_cnd_cd", "dsed_cll_from_date", "dsed_cll_to_date",
			"dsed_cll_from_time", "dsed_cll_to_time", "dsed_cll_time_trms_srvc_rqrm_cd", "aped_arr_from_date",
			"aped_arr_to_date", "aped_arr_from_time_prfm_dttm", "aped_arr_to_time_prfm_dttm",
			"aped_arr_time_trms_srvc_rqrm_cd", "trms_of_mix_load_txt", "trsp_srvc_note_one_txt",
			"trsp_srvc_note_two_txt");
	//  trsp_vehicle_trms  関連のフィールド 
	public final List<String> TRSP_VEHICLE_TRMS = Arrays.asList("car_cls_of_size_cd", "car_cls_of_shp_cd",
			"car_cls_of_tlg_lftr_exst_cd", "car_cls_of_wing_body_exst_cd", "car_cls_of_rfg_exst_cd",
			"trms_of_upp_tmp_meas",
			"trms_of_lwr_tmp_meas", "car_cls_of_crn_exst_cd", "car_rmk_about_eqpm_txt");
	//  del_info  関連のフィールド 
	public final List<String> DEL_INFO = Arrays.asList("del_note_id", "shpm_num_id", "rced_ord_num_id",
			"istd_totl_pcks_quan",
			"num_unt_cd");
	//  cns  関連のフィールド 
	public final List<String> CNS = Arrays.asList("istd_totl_weig_meas", "weig_unt_cd", "istd_totl_vol_meas",
			"vol_unt_cd",
			"istd_totl_untl_quan");
	//  cns_line_item  関連のフィールド 
	public final List<String> CNS_LINE_ITEM = Arrays.asList("line_item_num_id", "sev_ord_num_id",
			"cnsg_crg_item_num_id",
			"buy_assi_item_cd", "sell_assi_item_cd", "wrhs_assi_item_cd", "item_name_txt",
			"gods_idcs_in_ots_pcke_name_txt",
			"num_of_istd_untl_quan", "num_of_istd_quan", "sev_num_unt_cd", "istd_pcke_weig_meas", "sev_weig_unt_cd",
			"istd_pcke_vol_meas", "sev_vol_unt_cd", "istd_quan_meas", "cnte_num_unt_cd", "dcpv_trpn_pckg_txt",
			"pcke_frm_cd", "pcke_frm_name_cd", "crg_hnd_trms_spcl_isrs_txt", "glb_retb_asse_id", "totl_rti_quan_quan",
			"chrg_of_pcke_ctrl_num_unt_amnt");
	//  cnsg_prty  関連のフィールド 
	public final List<String> CNSG_PRTY = Arrays.asList("cnsg_prty_head_off_id", "cnsg_prty_brnc_off_id",
			"cnsg_prty_name_txt",
			"cnsg_sct_sped_org_id", "cnsg_sct_sped_org_name_txt", "cnsg_tel_cmm_cmp_num_txt",
			"cnsg_pstl_adrs_line_one_txt",
			"cnsg_pstc_cd");
	//  trsp_rqr_prty  関連のフィールド 
	public final List<String> TRSP_RQR_PRTY = Arrays.asList("trsp_rqr_prty_head_off_id", "trsp_rqr_prty_brnc_off_id",
			"trsp_rqr_prty_name_txt", "trsp_rqr_sct_sped_org_id", "trsp_rqr_sct_sped_org_name_txt",
			"trsp_rqr_sct_tel_cmm_cmp_num_txt",
			"trsp_rqr_pstl_adrs_line_one_txt", "trsp_rqr_pstc_cd");
	//  cnee_prty  関連のフィールド 
	public final List<String> CNEE_PRTY = Arrays.asList("cnee_prty_head_off_id", "cnee_prty_brnc_off_id",
			"cnee_prty_name_txt",
			"cnee_sct_id", "cnee_sct_name_txt", "cnee_prim_cnt_pers_name_txt", "cnee_tel_cmm_cmp_num_txt",
			"cnee_pstl_adrs_line_one_txt",
			"cnee_pstc_cd");
	//  logs_srvc_prv  関連のフィールド 
	public final List<String> LOGS_SRVC_PRV = Arrays.asList("logs_srvc_prv_prty_head_off_id",
			"logs_srvc_prv_prty_brnc_off_id",
			"logs_srvc_prv_prty_name_txt", "logs_srvc_prv_sct_sped_org_id", "logs_srvc_prv_sct_sped_org_name_txt",
			"logs_srvc_prv_sct_prim_cnt_pers_name_txt", "logs_srvc_prv_sct_tel_cmm_cmp_num_txt");
	//  road_carr  関連のフィールド 
	public final List<String> ROAD_CARR = Arrays.asList("trsp_cli_prty_head_off_id", "trsp_cli_prty_brnc_off_id",
			"trsp_cli_prty_name_txt", "road_carr_depa_sped_org_id", "road_carr_depa_sped_org_name_txt",
			"trsp_cli_tel_cmm_cmp_num_txt",
			"road_carr_arr_sped_org_id", "road_carr_arr_sped_org_name_txt");
	//  fret_clim_to_prty  関連のフィールド 
	public final List<String> FRET_CLIM_TO_PRTY = Arrays.asList("fret_clim_to_prty_head_off_id",
			"fret_clim_to_prty_brnc_off_id",
			"fret_clim_to_prty_name_txt", "fret_clim_to_sct_sped_org_id", "fret_clim_to_sct_sped_org_name_txt");
	//  ship_from_prty  関連のフィールド 
	public final List<String> SHIP_FROM_PRTY = Arrays.asList("ship_from_prty_head_off_id", "ship_from_prty_brnc_off_id",
			"ship_from_prty_name_txt", "ship_from_sct_id", "ship_from_sct_name_txt", "ship_from_tel_cmm_cmp_num_txt",
			"ship_from_pstl_adrs_cty_id", "ship_from_pstl_adrs_id", "ship_from_pstl_adrs_line_one_txt",
			"ship_from_pstc_cd",
			"from_plc_cd_prty_id", "from_gln_prty_id", "from_jpn_uplc_cd", "from_jpn_van_srvc_cd",
			"from_jpn_van_vans_cd");
	//  ship_from_prty_rqrm  関連のフィールド 
	public final List<String> SHIP_FROM_PRTY_RQRM = Arrays.asList("from_trms_of_car_size_cd",
			"from_trms_of_car_hght_meas",
			"trms_of_gtp_cert_txt", "trms_of_cll_txt", "from_trms_of_gods_hnd_txt", "anc_wrk_of_cll_txt",
			"from_spcl_wrk_txt");
	//  cut_off_Info  関連のフィールド 
	public final List<String> CUT_OFF_INFO = Arrays.asList("cut_off_time", "cut_off_fee");
	//  ship_to_prty  関連のフィールド 
	public final List<String> SHIP_TO_PRTY = Arrays.asList("ship_to_prty_head_off_id", "ship_to_prty_brnc_off_id",
			"ship_to_prty_name_txt", "ship_to_sct_id", "ship_to_sct_name_txt", "ship_to_prim_cnt_id",
			"ship_to_prim_cnt_pers_name_txt",
			"ship_to_tel_cmm_cmp_num_txt", "ship_to_pstl_adrs_cty_id", "ship_to_pstl_adrs_id",
			"ship_to_pstl_adrs_line_one_txt",
			"ship_to_pstc_cd", "to_plc_cd_prty_id", "to_gln_prty_id", "to_jpn_uplc_cd", "to_jpn_van_srvc_cd",
			"to_jpn_van_vans_cd");
	//  free_time_Info  関連のフィールド 
	public final List<String> FREE_TIME_INFO = Arrays.asList("free_time", "free_time_fee");
	//  ship_to_prty_rqrm  関連のフィールド 
	public final List<String> SHIP_TO_PRTY_RQRM = Arrays.asList("to_trms_of_car_size_cd", "to_trms_of_car_hght_meas",
			"to_trms_of_gtp_cert_txt", "trms_of_del_txt", "to_trms_of_gods_hnd_txt", "anc_wrk_of_del_txt",
			"to_spcl_wrk_txt");

	public int trsp_isrMapCount = 0;
	public int trsp_srvcMapCount = 0;
	public int trsp_vehicle_trmsMapCount = 0;
	public int del_infoMapCount = 0;
	public int cnsMapCount = 0;
	public int cns_line_itemMapCount = 0;
	public int cnsg_prtyMapCount = 0;
	public int trsp_rqr_prtyMapCount = 0;
	public int cnee_prtyMapCount = 0;
	public int logs_srvc_prvMapCount = 0;
	public int road_carrMapCount = 0;
	public int fret_clim_to_prtyMapCount = 0;
	public int ship_from_prtyMapCount = 0;
	public int ship_from_prty_rqrmMapCount = 0;
	public int cut_off_infoMapCount = 0;
	public int ship_to_prtyMapCount = 0;
	public int free_time_infoMapCount = 0;
	public int ship_to_prty_rqrmMapCount = 0;

	/**
	 * CSVファイルからJSONデータに変換
	 */
	public String convertCsvToJson(List<String> csvData, JsonDataToAPI jsonDataToAPITest) throws Exception {
		logger.info("ConvertCsvToJsonPattern3012.convertCsvToJson 開始");
		if (csvData == null || csvData.isEmpty()) {
			return "{}";
		}

		// ヘッダー行を取得
		String[] headers = csvData.get(0).split(",");
		TransportPlan transportPlan = new TransportPlan();

		Map<String, String> firstRecord = mapHeadersToValues(headers, csvData.get(1).split(",", -1));

		// msg_info の作成
		Msginfo msg_info = new Msginfo();
		msg_info.setMsg_id(firstRecord.get("msg_id"));
		msg_info.setMsg_info_cls_typ_cd(firstRecord.get("msg_info_cls_typ_cd"));
		msg_info.setMsg_date_iss_dttm(firstRecord.get("msg_date_iss_dttm"));
		msg_info.setMsg_time_iss_dttm(firstRecord.get("msg_time_iss_dttm"));
		msg_info.setMsg_fn_stas_cd(firstRecord.get("msg_fn_stas_cd"));
		msg_info.setNote_dcpt_txt(firstRecord.get("note_dcpt_txt"));

		// trsp_plan の作成
		TrspPlan trsp_plan = new TrspPlan();
		trsp_plan.setTrsp_plan_stas_cd(firstRecord.get("trsp_plan_stas_cd"));

		// trsp_isr を作成
		Map<String, TrspIsr> trsp_isrMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addTrspIsr(trsp_isrMap, record));
		// Map から List<TrspIsr> に変換
		List<TrspIsr> trsp_isrList = new ArrayList<>(trsp_isrMap.values());

		// trsp_srvc を作成
		Map<String, TrspSrvc> trsp_srvcMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addTrspSrvc(trsp_srvcMap, record));
		// Map から List<TrspSrvc> に変換
		List<TrspSrvc> trsp_srvcList = new ArrayList<>(trsp_srvcMap.values());

		// trsp_vehicle_trms を作成
		Map<String, TrspVehicleTrms> trsp_vehicle_trmsMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addTrspVehicleTrms(trsp_vehicle_trmsMap, record));
		// Map から List<TrspVehicleTrms> に変換
		List<TrspVehicleTrms> trsp_vehicle_trmsList = new ArrayList<>(trsp_vehicle_trmsMap.values());

		// del_info を作成
		Map<String, DelInfo> del_infoMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addDelInfo(del_infoMap, record));
		// Map から List<DelInfo> に変換
		List<DelInfo> del_infoList = new ArrayList<>(del_infoMap.values());

		// cns を作成
		Map<String, Cns> cnsMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addCns(cnsMap, record));
		// Map から List<Cns> に変換
		List<Cns> cnsList = new ArrayList<>(cnsMap.values());

		// cns_line_item を作成
		Map<String, CnsLineItem> cns_line_itemMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addCnsLineItem(cns_line_itemMap, record));
		// Map から List<CnsLineItem> に変換
		List<CnsLineItem> cns_line_itemList = new ArrayList<>(cns_line_itemMap.values());

		// cnsg_prty を作成
		Map<String, CnsgPrty> cnsg_prtyMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addCnsgPrty(cnsg_prtyMap, record));
		// Map から List<CnsgPrty> に変換
		List<CnsgPrty> cnsg_prtyList = new ArrayList<>(cnsg_prtyMap.values());

		// trsp_rqr_prty を作成
		Map<String, TrspRqrPrty> trsp_rqr_prtyMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addTrspRqrPrty(trsp_rqr_prtyMap, record));
		// Map から List<TrspRqrPrty> に変換
		List<TrspRqrPrty> trsp_rqr_prtyList = new ArrayList<>(trsp_rqr_prtyMap.values());

		// cnee_prty を作成
		Map<String, CneePrty> cnee_prtyMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addCneePrty(cnee_prtyMap, record));
		// Map から List<CneePrty> に変換
		List<CneePrty> cnee_prtyList = new ArrayList<>(cnee_prtyMap.values());

		// logs_srvc_prv を作成
		Map<String, LogsSrvcPrv> logs_srvc_prvMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addLogsSrvcPrv(logs_srvc_prvMap, record));
		// Map から List<LogsSrvcPrv> に変換
		List<LogsSrvcPrv> logs_srvc_prvList = new ArrayList<>(logs_srvc_prvMap.values());

		// road_carr を作成
		Map<String, RoadCarr> road_carrMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addRoadCarr(road_carrMap, record));
		// Map から List<RoadCarr> に変換
		List<RoadCarr> road_carrList = new ArrayList<>(road_carrMap.values());

		// fret_clim_to_prty を作成
		Map<String, FretClimToPrty> fret_clim_to_prtyMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addFretClimToPrty(fret_clim_to_prtyMap, record));

		// Map から List<FretClimToPrty> に変換
		List<FretClimToPrty> fret_clim_to_prtyList = new ArrayList<>(fret_clim_to_prtyMap.values());

		// ship_from_prty を作成
		Map<String, ShipFromPrty> ship_from_prtyMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addShipFromPrty(ship_from_prtyMap, record));
		// Map から List<ShipFromPrty> に変換
		List<ShipFromPrty> ship_from_prtyList = new ArrayList<>(ship_from_prtyMap.values());

		// ship_to_prty を作成
		Map<String, ShipToPrty> ship_to_prtyMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addShipToPrty(ship_to_prtyMap, record));
		// Map から List<ShipToPrty> に変換
		List<ShipToPrty> ship_to_prtyList = new ArrayList<>(ship_to_prtyMap.values());

		// transportPlan の作成
		transportPlan.setMsg_info(msg_info);
		transportPlan.setTrsp_plan(trsp_plan);
		for (int i = 0; i < trsp_isrList.size(); i++) {
			TrspPlanLineItem trsp_plan_line_item = new TrspPlanLineItem();
			trsp_plan_line_item.setTrsp_isr(trsp_isrList.get(i));
			trsp_plan_line_item.setTrsp_srvc(trsp_srvcList.get(i));
			trsp_plan_line_item.setTrsp_vehicle_trms(trsp_vehicle_trmsList.get(i));
			trsp_plan_line_item.setDel_info(del_infoList.get(i));
			trsp_plan_line_item.setCns(cnsList.get(i));
			trsp_plan_line_item.getCns_line_item().add(cns_line_itemList.get(i));
			trsp_plan_line_item.setCnsg_prty(cnsg_prtyList.get(i));
			trsp_plan_line_item.setTrsp_rqr_prty(trsp_rqr_prtyList.get(i));
			trsp_plan_line_item.setCnee_prty(cnee_prtyList.get(i));
			trsp_plan_line_item.setLogs_srvc_prv(logs_srvc_prvList.get(i));
			trsp_plan_line_item.setRoad_carr(road_carrList.get(i));
			trsp_plan_line_item.setFret_clim_to_prty(fret_clim_to_prtyList.get(i));
			trsp_plan_line_item.getShip_from_prty().add(ship_from_prtyList.get(i));
			trsp_plan_line_item.getShip_to_prty().add(ship_to_prtyList.get(i));

			transportPlan.getTrsp_plan_line_item().add(trsp_plan_line_item);
		}

		// JSON に変換
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		jsonDataToAPITest.setAttribute(transportPlan);
		logger.info("ConvertCsvToJsonPattern3012.convertCsvToJson 終了");
		return mapper.writeValueAsString(transportPlan);
	}

	/**
	 * trsp_isr を作成しMapに保存
	 */
	private void addTrspIsr(Map<String, TrspIsr> TRSP_ISRMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, TRSP_ISR)) {
			return;
		}

		String trsp_instruction_id = record.get("trsp_instruction_id");
		String trsp_instruction_date_subm_dttm = record.get("trsp_instruction_date_subm_dttm");
		String inv_num_id = record.get("inv_num_id");
		String cmn_inv_num_id = record.get("cmn_inv_num_id");
		String mix_load_num_id = record.get("mix_load_num_id");

		// TrspIsr に保存
		TrspIsr trsp_isr = new TrspIsr(trsp_instruction_id, trsp_instruction_date_subm_dttm, inv_num_id, cmn_inv_num_id,
				mix_load_num_id);

		// Map に保存
		TRSP_ISRMap.put(String.valueOf(trsp_isrMapCount), trsp_isr);
		trsp_isrMapCount = trsp_isrMapCount + 1;
	}
	
	/**
	 * trsp_srvc を作成しMapに保存
	 */
	private void addTrspSrvc(Map<String, TrspSrvc> TRSP_SRVCMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, TRSP_SRVC)) {
			return;
		}

		String service_no = record.get("service_no");
		String service_name = record.get("service_name");
		String service_strt_date = record.get("service_strt_date");
		String service_strt_time = record.get("service_strt_time");
		String service_end_date = record.get("service_end_date");
		String service_end_time = record.get("service_end_time");
		String freight_rate = record.get("freight_rate");
		String trsp_means_typ_cd = record.get("trsp_means_typ_cd");
		String trsp_srvc_typ_cd = record.get("trsp_srvc_typ_cd");
		String road_carr_srvc_typ_cd = record.get("road_carr_srvc_typ_cd");
		String trsp_root_prio_cd = record.get("trsp_root_prio_cd");
		String car_cls_prio_cd = record.get("car_cls_prio_cd");
		String cls_of_carg_in_srvc_rqrm_cd = record.get("cls_of_carg_in_srvc_rqrm_cd");
		String cls_of_pkg_up_srvc_rqrm_cd = record.get("cls_of_pkg_up_srvc_rqrm_cd");
		String pyr_cls_srvc_rqrm_cd = record.get("pyr_cls_srvc_rqrm_cd");
		String trms_of_mix_load_cnd_cd = record.get("trms_of_mix_load_cnd_cd");
		String dsed_cll_from_date = record.get("dsed_cll_from_date");
		String dsed_cll_to_date = record.get("dsed_cll_to_date");
		String dsed_cll_from_time = record.get("dsed_cll_from_time");
		String dsed_cll_to_time = record.get("dsed_cll_to_time");
		String dsed_cll_time_trms_srvc_rqrm_cd = record.get("dsed_cll_time_trms_srvc_rqrm_cd");
		String aped_arr_from_date = record.get("aped_arr_from_date");
		String aped_arr_to_date = record.get("aped_arr_to_date");
		String aped_arr_from_time_prfm_dttm = record.get("aped_arr_from_time_prfm_dttm");
		String aped_arr_to_time_prfm_dttm = record.get("aped_arr_to_time_prfm_dttm");
		String aped_arr_time_trms_srvc_rqrm_cd = record.get("aped_arr_time_trms_srvc_rqrm_cd");
		String trms_of_mix_load_txt = record.get("trms_of_mix_load_txt");
		String trsp_srvc_note_one_txt = record.get("trsp_srvc_note_one_txt");
		String trsp_srvc_note_two_txt = record.get("trsp_srvc_note_two_txt");

		// TrspSrvc に保存
		TrspSrvc trsp_srvc = new TrspSrvc(service_no, service_name, service_strt_date, service_strt_time,
				service_end_date,
				service_end_time, freight_rate, trsp_means_typ_cd, trsp_srvc_typ_cd, road_carr_srvc_typ_cd,
				trsp_root_prio_cd,
				car_cls_prio_cd, cls_of_carg_in_srvc_rqrm_cd, cls_of_pkg_up_srvc_rqrm_cd, pyr_cls_srvc_rqrm_cd,
				trms_of_mix_load_cnd_cd, dsed_cll_from_date, dsed_cll_to_date, dsed_cll_from_time, dsed_cll_to_time,
				dsed_cll_time_trms_srvc_rqrm_cd, aped_arr_from_date, aped_arr_to_date, aped_arr_from_time_prfm_dttm,
				aped_arr_to_time_prfm_dttm, aped_arr_time_trms_srvc_rqrm_cd, trms_of_mix_load_txt,
				trsp_srvc_note_one_txt,
				trsp_srvc_note_two_txt);

		// Map に保存
		TRSP_SRVCMap.put(String.valueOf(trsp_srvcMapCount), trsp_srvc);
		trsp_srvcMapCount = trsp_srvcMapCount + 1;
	}

	/**
	 * trsp_vehicle_trms を作成しMapに保存
	 */
	private void addTrspVehicleTrms(Map<String, TrspVehicleTrms> TRSP_VEHICLE_TRMSMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, TRSP_VEHICLE_TRMS)) {
			return;
		}

		String car_cls_of_size_cd = record.get("car_cls_of_size_cd");
		String car_cls_of_shp_cd = record.get("car_cls_of_shp_cd");
		String car_cls_of_tlg_lftr_exst_cd = record.get("car_cls_of_tlg_lftr_exst_cd");
		String car_cls_of_wing_body_exst_cd = record.get("car_cls_of_wing_body_exst_cd");
		String car_cls_of_rfg_exst_cd = record.get("car_cls_of_rfg_exst_cd");
		String trms_of_lwr_tmp_meas = record.get("trms_of_lwr_tmp_meas");
		String trms_of_upp_tmp_meas = record.get("trms_of_upp_tmp_meas");
		String car_cls_of_crn_exst_cd = record.get("car_cls_of_crn_exst_cd");
		String car_rmk_about_eqpm_txt = record.get("car_rmk_about_eqpm_txt");

		// TrspVehicleTrms に保存
		TrspVehicleTrms trsp_vehicle_trms = new TrspVehicleTrms(car_cls_of_size_cd, car_cls_of_shp_cd,
				car_cls_of_tlg_lftr_exst_cd, car_cls_of_wing_body_exst_cd,
				car_cls_of_rfg_exst_cd, trms_of_upp_tmp_meas, trms_of_lwr_tmp_meas, car_cls_of_crn_exst_cd,
				car_rmk_about_eqpm_txt);

		// Map に保存
		TRSP_VEHICLE_TRMSMap.put(String.valueOf(trsp_vehicle_trmsMapCount), trsp_vehicle_trms);
		trsp_vehicle_trmsMapCount = trsp_vehicle_trmsMapCount + 1;
	}

	/**
	 * del_info を作成しMapに保存
	 */
	private void addDelInfo(Map<String, DelInfo> DEL_INFOMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, DEL_INFO)) {
			return;
		}

		String del_note_id = record.get("del_note_id");
		String shpm_num_id = record.get("shpm_num_id");
		String rced_ord_num_id = record.get("rced_ord_num_id");

		// DelInfo に保存
		DelInfo del_info = new DelInfo(del_note_id, shpm_num_id, rced_ord_num_id);

		// Map に保存
		DEL_INFOMap.put(String.valueOf(del_infoMapCount), del_info);
		del_infoMapCount = del_infoMapCount + 1;
	}

	/**
	 * cns を作成しMapに保存
	 */
	private void addCns(Map<String, Cns> CNSMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, CNS)) {
			return;
		}

		String istd_totl_pcks_quan = record.get("istd_totl_pcks_quan");
		String num_unt_cd = record.get("num_unt_cd");
		String istd_totl_weig_meas = record.get("istd_totl_weig_meas");
		String weig_unt_cd = record.get("weig_unt_cd");
		String istd_totl_vol_meas = record.get("istd_totl_vol_meas");
		String vol_unt_cd = record.get("vol_unt_cd");
		String istd_totl_untl_quan = record.get("istd_totl_untl_quan");

		//Cns に保存
		Cns cns = new Cns(istd_totl_pcks_quan, num_unt_cd, istd_totl_weig_meas, weig_unt_cd, istd_totl_vol_meas,
				vol_unt_cd, istd_totl_untl_quan);

		// Map に保存
		CNSMap.put(String.valueOf(cnsMapCount), cns);
		cnsMapCount = cnsMapCount + 1;
	}

	/**
	 * cns_line_item を作成しMapに保存
	 */
	private void addCnsLineItem(Map<String, CnsLineItem> CNS_LINE_ITEMMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, CNS_LINE_ITEM)) {
			return;
		}

		String line_item_num_id = record.get("line_item_num_id");
		String sev_ord_num_id = record.get("sev_ord_num_id");
		String cnsg_crg_item_num_id = record.get("cnsg_crg_item_num_id");
		String buy_assi_item_cd = record.get("buy_assi_item_cd");
		String sell_assi_item_cd = record.get("sell_assi_item_cd");
		String wrhs_assi_item_cd = record.get("wrhs_assi_item_cd");
		String item_name_txt = record.get("item_name_txt");
		String gods_idcs_in_ots_pcke_name_txt = record.get("gods_idcs_in_ots_pcke_name_txt");
		String num_of_istd_untl_quan = record.get("num_of_istd_untl_quan");
		String num_of_istd_quan = record.get("num_of_istd_quan");
		String sev_num_unt_cd = record.get("sev_num_unt_cd");
		String istd_pcke_weig_meas = record.get("istd_pcke_weig_meas");
		String sev_weig_unt_cd = record.get("sev_weig_unt_cd");
		String istd_pcke_vol_meas = record.get("istd_pcke_vol_meas");
		String sev_vol_unt_cd = record.get("sev_vol_unt_cd");
		String istd_quan_meas = record.get("istd_quan_meas");
		String cnte_num_unt_cd = record.get("cnte_num_unt_cd");
		String dcpv_trpn_pckg_txt = record.get("dcpv_trpn_pckg_txt");
		String pcke_frm_cd = record.get("pcke_frm_cd");
		String pcke_frm_name_cd = record.get("pcke_frm_name_cd");
		String crg_hnd_trms_spcl_isrs_txt = record.get("crg_hnd_trms_spcl_isrs_txt");
		String glb_retb_asse_id = record.get("glb_retb_asse_id");
		String totl_rti_quan_quan = record.get("totl_rti_quan_quan");
		String chrg_of_pcke_ctrl_num_unt_amnt = record.get("chrg_of_pcke_ctrl_num_unt_amnt");

		//CnsLineItem に保存
		CnsLineItem cns_line_item = new CnsLineItem(line_item_num_id, sev_ord_num_id, cnsg_crg_item_num_id,
				buy_assi_item_cd, sell_assi_item_cd,
				wrhs_assi_item_cd, item_name_txt, gods_idcs_in_ots_pcke_name_txt, num_of_istd_untl_quan,
				num_of_istd_quan, sev_num_unt_cd, istd_pcke_weig_meas, sev_weig_unt_cd, istd_pcke_vol_meas,
				sev_vol_unt_cd, istd_quan_meas, cnte_num_unt_cd, dcpv_trpn_pckg_txt, pcke_frm_cd, pcke_frm_name_cd,
				crg_hnd_trms_spcl_isrs_txt, glb_retb_asse_id, totl_rti_quan_quan, chrg_of_pcke_ctrl_num_unt_amnt);

		// Map に保存
		CNS_LINE_ITEMMap.put(String.valueOf(cns_line_itemMapCount), cns_line_item);
		cns_line_itemMapCount = cns_line_itemMapCount + 1;
	}

	/**
	 * cnsg_prty を作成しMapに保存
	 */
	private void addCnsgPrty(Map<String, CnsgPrty> CNSG_PRTYMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, CNSG_PRTY)) {
			return;
		}

		String cnsg_prty_head_off_id = record.get("cnsg_prty_head_off_id");
		String cnsg_prty_brnc_off_id = record.get("cnsg_prty_brnc_off_id");
		String cnsg_prty_name_txt = record.get("cnsg_prty_name_txt");
		String cnsg_sct_sped_org_id = record.get("cnsg_sct_sped_org_id");
		String cnsg_sct_sped_org_name_txt = record.get("cnsg_sct_sped_org_name_txt");
		String cnsg_tel_cmm_cmp_num_txt = record.get("cnsg_tel_cmm_cmp_num_txt");
		String cnsg_pstl_adrs_line_one_txt = record.get("cnsg_pstl_adrs_line_one_txt");
		String cnsg_pstc_cd = record.get("cnsg_pstc_cd");

		//CnsgPrty に保存
		CnsgPrty cnsg_prty = new CnsgPrty(cnsg_prty_head_off_id, cnsg_prty_brnc_off_id, cnsg_prty_name_txt,
				cnsg_sct_sped_org_id,
				cnsg_sct_sped_org_name_txt, cnsg_tel_cmm_cmp_num_txt, cnsg_pstl_adrs_line_one_txt, cnsg_pstc_cd);

		// Map に保存
		CNSG_PRTYMap.put(String.valueOf(cnsg_prtyMapCount), cnsg_prty);
		cnsg_prtyMapCount = cnsg_prtyMapCount + 1;
	}

	/**
	 * trsp_rqr_prty を作成しMapに保存
	 */
	private void addTrspRqrPrty(Map<String, TrspRqrPrty> TRSP_RQR_PRTYMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, TRSP_RQR_PRTY)) {
			return;
		}

		String trsp_rqr_prty_head_off_id = record.get("trsp_rqr_prty_head_off_id");
		String trsp_rqr_prty_brnc_off_id = record.get("trsp_rqr_prty_brnc_off_id");
		String trsp_rqr_prty_name_txt = record.get("trsp_rqr_prty_name_txt");
		String trsp_rqr_sct_sped_org_id = record.get("trsp_rqr_sct_sped_org_id");
		String trsp_rqr_sct_sped_org_name_txt = record.get("trsp_rqr_sct_sped_org_name_txt");
		String trsp_rqr_sct_tel_cmm_cmp_num_txt = record.get("trsp_rqr_sct_tel_cmm_cmp_num_txt");
		String trsp_rqr_pstl_adrs_line_one_txt = record.get("trsp_rqr_pstl_adrs_line_one_txt");
		String trsp_rqr_pstc_cd = record.get("trsp_rqr_pstc_cd");

		//TrspRqrPrty に保存
		TrspRqrPrty trsp_rqr_prty = new TrspRqrPrty(trsp_rqr_prty_head_off_id, trsp_rqr_prty_brnc_off_id,
				trsp_rqr_prty_name_txt,
				trsp_rqr_sct_sped_org_id, trsp_rqr_sct_sped_org_name_txt, trsp_rqr_sct_tel_cmm_cmp_num_txt,
				trsp_rqr_pstl_adrs_line_one_txt, trsp_rqr_pstc_cd);

		// Map に保存
		TRSP_RQR_PRTYMap.put(String.valueOf(trsp_rqr_prtyMapCount), trsp_rqr_prty);
		trsp_rqr_prtyMapCount = trsp_rqr_prtyMapCount + 1;
	}

	/**
	 * cnee_prty を作成しMapに保存
	 */
	private void addCneePrty(Map<String, CneePrty> CNEE_PRTYMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, CNEE_PRTY)) {
			return;
		}

		String cnee_prty_head_off_id = record.get("cnee_prty_head_off_id");
		String cnee_prty_brnc_off_id = record.get("cnee_prty_brnc_off_id");
		String cnee_prty_name_txt = record.get("cnee_prty_name_txt");
		String cnee_sct_id = record.get("cnee_sct_id");
		String cnee_sct_name_txt = record.get("cnee_sct_name_txt");
		String cnee_prim_cnt_pers_name_txt = record.get("cnee_prim_cnt_pers_name_txt");
		String cnee_tel_cmm_cmp_num_txt = record.get("cnee_tel_cmm_cmp_num_txt");
		String cnee_pstl_adrs_line_one_txt = record.get("cnee_pstl_adrs_line_one_txt");
		String cnee_pstc_cd = record.get("cnee_pstc_cd");

		//CneePrty に保存
		CneePrty cnee_prty = new CneePrty(cnee_prty_head_off_id, cnee_prty_brnc_off_id, cnee_prty_name_txt, cnee_sct_id,
				cnee_sct_name_txt, cnee_prim_cnt_pers_name_txt, cnee_tel_cmm_cmp_num_txt,
				cnee_pstl_adrs_line_one_txt, cnee_pstc_cd);

		// Map に保存
		CNEE_PRTYMap.put(String.valueOf(cnee_prtyMapCount), cnee_prty);
		cnee_prtyMapCount = cnee_prtyMapCount + 1;
	}

	/**
	 * logs_srvc_prv を作成しMapに保存
	 */
	private void addLogsSrvcPrv(Map<String, LogsSrvcPrv> LOGS_SRVC_PRVMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, LOGS_SRVC_PRV)) {
			return;
		}

		String logs_srvc_prv_prty_head_off_id = record.get("logs_srvc_prv_prty_head_off_id");
		String logs_srvc_prv_prty_brnc_off_id = record.get("logs_srvc_prv_prty_brnc_off_id");
		String logs_srvc_prv_prty_name_txt = record.get("logs_srvc_prv_prty_name_txt");
		String logs_srvc_prv_sct_sped_org_id = record.get("logs_srvc_prv_sct_sped_org_id");
		String logs_srvc_prv_sct_sped_org_name_txt = record.get("logs_srvc_prv_sct_sped_org_name_txt");
		String logs_srvc_prv_sct_prim_cnt_pers_name_txt = record.get("logs_srvc_prv_sct_prim_cnt_pers_name_txt");
		String logs_srvc_prv_sct_tel_cmm_cmp_num_txt = record.get("logs_srvc_prv_sct_tel_cmm_cmp_num_txt");

		//LogsSrvcPrv に保存
		LogsSrvcPrv logs_srvc_prv = new LogsSrvcPrv(logs_srvc_prv_prty_head_off_id, logs_srvc_prv_prty_brnc_off_id,
				logs_srvc_prv_prty_name_txt, logs_srvc_prv_sct_sped_org_id, logs_srvc_prv_sct_sped_org_name_txt,
				logs_srvc_prv_sct_prim_cnt_pers_name_txt, logs_srvc_prv_sct_tel_cmm_cmp_num_txt);

		// Map に保存
		LOGS_SRVC_PRVMap.put(String.valueOf(logs_srvc_prvMapCount), logs_srvc_prv);
		logs_srvc_prvMapCount = logs_srvc_prvMapCount + 1;
	}

	/**
	 * road_carr を作成しMapに保存
	 */
	private void addRoadCarr(Map<String, RoadCarr> ROAD_CARRMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, ROAD_CARR)) {
			return;
		}

		String trsp_cli_prty_head_off_id = record.get("trsp_cli_prty_head_off_id");
		String trsp_cli_prty_brnc_off_id = record.get("trsp_cli_prty_brnc_off_id");
		String trsp_cli_prty_name_txt = record.get("trsp_cli_prty_name_txt");
		String road_carr_depa_sped_org_id = record.get("road_carr_depa_sped_org_id");
		String road_carr_depa_sped_org_name_txt = record.get("road_carr_depa_sped_org_name_txt");
		String trsp_cli_tel_cmm_cmp_num_txt = record.get("trsp_cli_tel_cmm_cmp_num_txt");
		String road_carr_arr_sped_org_id = record.get("road_carr_arr_sped_org_id");
		String road_carr_arr_sped_org_name_txt = record.get("road_carr_arr_sped_org_name_txt");

		//RoadCarr に保存
		RoadCarr road_carr = new RoadCarr(trsp_cli_prty_head_off_id, trsp_cli_prty_brnc_off_id, trsp_cli_prty_name_txt,
				road_carr_depa_sped_org_id, road_carr_depa_sped_org_name_txt, trsp_cli_tel_cmm_cmp_num_txt,
				road_carr_arr_sped_org_id, road_carr_arr_sped_org_name_txt);

		// Map に保存
		ROAD_CARRMap.put(String.valueOf(road_carrMapCount), road_carr);
		road_carrMapCount = road_carrMapCount + 1;
	}

	/**
	 * fret_clim_to_prty を作成しMapに保存
	 */
	private void addFretClimToPrty(Map<String, FretClimToPrty> FRET_CLIM_TO_PRTYMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, FRET_CLIM_TO_PRTY)) {
			return;
		}

		String fret_clim_to_prty_head_off_id = record.get("fret_clim_to_prty_head_off_id");
		String fret_clim_to_prty_brnc_off_id = record.get("fret_clim_to_prty_brnc_off_id");
		String fret_clim_to_prty_name_txt = record.get("fret_clim_to_prty_name_txt");
		String fret_clim_to_sct_sped_org_id = record.get("fret_clim_to_sct_sped_org_id");
		String fret_clim_to_sct_sped_org_name_txt = record.get("fret_clim_to_sct_sped_org_name_txt");

		//FretClimToPrty に保存
		FretClimToPrty fret_clim_to_prty = new FretClimToPrty(fret_clim_to_prty_head_off_id,
				fret_clim_to_prty_brnc_off_id, fret_clim_to_prty_name_txt,
				fret_clim_to_sct_sped_org_id, fret_clim_to_sct_sped_org_name_txt);

		// Map に保存
		FRET_CLIM_TO_PRTYMap.put(String.valueOf(fret_clim_to_prtyMapCount), fret_clim_to_prty);
		fret_clim_to_prtyMapCount = fret_clim_to_prtyMapCount + 1;
	}

	/**
	 * ship_from_prty を作成しMapに保存
	 */
	private void addShipFromPrty(Map<String, ShipFromPrty> SHIP_FROM_PRTYMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, SHIP_FROM_PRTY)) {
			return;
		}

		String ship_from_prty_head_off_id = record.get("ship_from_prty_head_off_id");
		String ship_from_prty_brnc_off_id = record.get("ship_from_prty_brnc_off_id");
		String ship_from_prty_name_txt = record.get("ship_from_prty_name_txt");
		String ship_from_sct_id = record.get("ship_from_sct_id");
		String ship_from_sct_name_txt = record.get("ship_from_sct_name_txt");
		String ship_from_tel_cmm_cmp_num_txt = record.get("ship_from_tel_cmm_cmp_num_txt");
		String ship_from_pstl_adrs_cty_id = record.get("ship_from_pstl_adrs_cty_id");
		String ship_from_pstl_adrs_id = record.get("ship_from_pstl_adrs_id");
		String ship_from_pstl_adrs_line_one_txt = record.get("ship_from_pstl_adrs_line_one_txt");
		String ship_from_pstc_cd = record.get("ship_from_pstc_cd");
		String from_plc_cd_prty_id = record.get("from_plc_cd_prty_id");
		String from_gln_prty_id = record.get("from_gln_prty_id");
		String from_jpn_uplc_cd = record.get("from_jpn_uplc_cd");
		String from_jpn_van_srvc_cd = record.get("from_jpn_van_srvc_cd");
		String from_jpn_van_vans_cd = record.get("from_jpn_van_vans_cd");

		// ShipFromPrty に保存
		ShipFromPrty ship_from_prty = new ShipFromPrty(ship_from_prty_head_off_id, ship_from_prty_brnc_off_id,
				ship_from_prty_name_txt,
				ship_from_sct_id, ship_from_sct_name_txt, ship_from_tel_cmm_cmp_num_txt, ship_from_pstl_adrs_cty_id,
				ship_from_pstl_adrs_id, ship_from_pstl_adrs_line_one_txt, ship_from_pstc_cd,
				from_plc_cd_prty_id, from_gln_prty_id, from_jpn_uplc_cd, from_jpn_van_srvc_cd, from_jpn_van_vans_cd,
				null, new ArrayList<CutOffInfo>());

		// ship_from_prty_rqrm の追加
		ShipFromPrtyRqrm ship_from_prty_rqrm = new ShipFromPrtyRqrm(
				record.get("from_trms_of_car_size_cd"),
				record.get("from_trms_of_car_hght_meas"),
				record.get("trms_of_gtp_cert_txt"),
				record.get("rotrms_of_cll_txt"),
				record.get("from_trms_of_gods_hnd_txt"),
				record.get("anc_wrk_of_cll_txt"),
				record.get("from_spcl_wrk_txt"));

		ship_from_prty.setShip_from_prty_rqrm(ship_from_prty_rqrm);

		// cut_off_infoの追加
		CutOffInfo cut_off_info = new CutOffInfo(
				record.get("cut_off_time"),
				record.get("cut_off_fee"));

		ship_from_prty.getCut_off_info().add(cut_off_info);

		// Map に保存
		SHIP_FROM_PRTYMap.put(String.valueOf(ship_from_prtyMapCount), ship_from_prty);
		ship_from_prtyMapCount = ship_from_prtyMapCount + 1;
	}

	/**
	 * ship_to_prty を作成しMapに保存
	 */
	private void addShipToPrty(Map<String, ShipToPrty> SHIP_TO_PRTYMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, SHIP_TO_PRTY)) {
			return;
		}

		String ship_to_prty_head_off_id = record.get("ship_to_prty_head_off_id");
		String ship_to_prty_brnc_off_id = record.get("ship_to_prty_brnc_off_id");
		String ship_to_prty_name_txt = record.get("ship_to_prty_name_txt");
		String ship_to_sct_id = record.get("ship_to_sct_id");
		String ship_to_sct_name_txt = record.get("ship_to_sct_name_txt");
		String ship_to_prim_cnt_id = record.get("ship_to_prim_cnt_id");
		String ship_to_prim_cnt_pers_name_txt = record.get("ship_to_prim_cnt_pers_name_txt");
		String ship_to_tel_cmm_cmp_num_txt = record.get("ship_to_tel_cmm_cmp_num_txt");
		String ship_to_pstl_adrs_cty_id = record.get("ship_to_pstl_adrs_cty_id");
		String ship_to_pstl_adrs_id = record.get("ship_to_pstl_adrs_id");
		String ship_to_pstl_adrs_line_one_txt = record.get("ship_to_pstl_adrs_line_one_txt");
		String ship_to_pstc_cd = record.get("ship_to_pstc_cd");
		String to_plc_cd_prty_id = record.get("to_plc_cd_prty_id");
		String to_gln_prty_id = record.get("to_gln_prty_id");
		String to_jpn_uplc_cd = record.get("to_jpn_uplc_cd");
		String to_jpn_van_srvc_cd = record.get("to_jpn_van_srvc_cd");
		String to_jpn_van_vans_cd = record.get("to_jpn_van_vans_cd");

		// ShipToPrty に保存
		ShipToPrty ship_to_prty = new ShipToPrty(ship_to_prty_head_off_id, ship_to_prty_brnc_off_id,
				ship_to_prty_name_txt, ship_to_sct_id,
				ship_to_sct_name_txt, ship_to_prim_cnt_id, ship_to_prim_cnt_pers_name_txt, ship_to_tel_cmm_cmp_num_txt,
				ship_to_pstl_adrs_cty_id, ship_to_pstl_adrs_id, ship_to_pstl_adrs_line_one_txt, ship_to_pstc_cd,
				to_plc_cd_prty_id, to_gln_prty_id, to_jpn_uplc_cd, to_jpn_van_srvc_cd, to_jpn_van_vans_cd,
				new ArrayList<FreeTimeInfo>(), null);

		// free_time_infoの追加
		FreeTimeInfo free_time_info = new FreeTimeInfo(
				record.get("free_time"),
				record.get("free_time_fee"));

		ship_to_prty.getFree_time_info().add(free_time_info);

		// ship_to_prty_rqrmの追加
		ShipToPrtyRqrm ship_to_prty_rqrm = new ShipToPrtyRqrm(
				record.get("to_trms_of_car_size_cd"),
				record.get("to_trms_of_car_hght_meas"),
				record.get("to_trms_of_gtp_cert_txt"),
				record.get("trms_of_del_txt"),
				record.get("to_trms_of_gods_hnd_txt"),
				record.get("anc_wrk_of_del_txt"),
				record.get("to_spcl_wrk_txt"));

		ship_to_prty.setShip_to_prty_rqrm(ship_to_prty_rqrm);

		// Map に保存
		SHIP_TO_PRTYMap.put(String.valueOf(ship_to_prtyMapCount), ship_to_prty);
		ship_to_prtyMapCount = ship_to_prtyMapCount + 1;
	}

	/**
	 * ヘッダーと値をマッピングしてMapに保存
	 */
	private Map<String, String> mapHeadersToValues(String[] headers, String[] values) {
		return IntStream.range(0, headers.length)
				.boxed()
				.collect(Collectors.toMap(i -> headers[i], i -> values[i]));
	}

	/**
	 * 指定されたフィールドのリストに基づいて、すべてのフィールドが空かどうかをチェック
	 */
	private boolean isAllFieldsEmpty(Map<String, String> record, List<String> fields) {
		return fields.stream()
				.allMatch(
						field -> record.get(field) == null ||
								record.get(field).trim().isEmpty());
	}
}
