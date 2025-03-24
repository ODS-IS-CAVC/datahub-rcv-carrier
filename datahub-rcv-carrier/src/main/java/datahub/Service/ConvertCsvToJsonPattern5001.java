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
import datahub.jsonData.TransportCapacity;
import datahub.jsonData.TransportCapacity.CarInfo;
import datahub.jsonData.TransportCapacity.CutOffInfo;
import datahub.jsonData.TransportCapacity.DrvAvbTime;
import datahub.jsonData.TransportCapacity.DrvInfo;
import datahub.jsonData.TransportCapacity.FreeTimeInfo;
import datahub.jsonData.TransportCapacity.LogsSrvcPrv;
import datahub.jsonData.TransportCapacity.Msginfo;
import datahub.jsonData.TransportCapacity.RoadCarr;
import datahub.jsonData.TransportCapacity.TrspAbilityLineItem;
import datahub.jsonData.TransportCapacity.VehicleAvbResource;
import datahub.step.BaseStep;

/**
 * 荷主向け運行案件用CSVファイルからJSONデータ変換
 * 
 */
@Service
public class ConvertCsvToJsonPattern5001 extends BaseStep {

	/**
	 * コンストラクタ
	 */
	public ConvertCsvToJsonPattern5001(MessageSource ms) {
		super(ms);
	}

	// msg_info 関連のフィールド
	public final List<String> MSG_INFO = Arrays.asList("msg_id", "msg_info_cls_typ_cd", "msg_date_iss_dttm",
			"msg_time_iss_dttm", "msg_fn_stas_cd", "note_dcpt_txt");
	// road_carr 関連のフィールド
	public final List<String> ROAD_CARR = Arrays.asList("trsp_cli_prty_head_off_id", "trsp_cli_prty_brnc_off_id",
			"trsp_cli_prty_name_txt", "road_carr_depa_sped_org_id", "road_carr_depa_sped_org_name_txt",
			"trsp_cli_tel_cmm_cmp_num_txt", "road_carr_arr_sped_org_id", "road_carr_arr_sped_org_name_txt");
	// logs_srvc_prv 関連のフィールド
	public final List<String> LOGS_SRVC_PRV = Arrays.asList("logs_srvc_prv_prty_head_off_id",
			"logs_srvc_prv_prty_brnc_off_id", "logs_srvc_prv_prty_name_txt", "logs_srvc_prv_sct_sped_org_id",
			"logs_srvc_prv_sct_sped_org_name_txt", "logs_srvc_prv_sct_prim_cnt_pers_name_txt",
			"logs_srvc_prv_sct_tel_cmm_cmp_num_txt");
	// car_info 関連のフィールド
	public final List<String> CAR_INFO = Arrays.asList("service_no", "service_name", "service_strt_date",
			"service_strt_time", "service_end_date", "service_end_time", "freight_rate", "car_ctrl_num_id",
			"car_license_plt_num_id", "giai", "car_body_num_cd", "car_cls_of_size_cd", "tractor_idcr",
			"trailer_license_plt_num_id", "car_weig_meas", "car_lngh_meas", "car_wid_meas", "car_hght_meas",
			"car_max_load_capacity1_meas", "car_max_load_capacity2_meas", "car_vol_of_hzd_item_meas",
			"car_spc_grv_of_hzd_item_meas", "car_trk_bed_hght_meas", "car_trk_bed_wid_meas", "car_trk_bed_lngh_meas",
			"car_trk_bed_grnd_hght_meas", "car_max_load_vol_meas", "pcke_frm_cd", "pcke_frm_name_cd",
			"car_max_untl_cp_quan", "car_cls_of_shp_cd", "car_cls_of_tlg_lftr_exst_cd", "car_cls_of_wing_body_exst_cd",
			"car_cls_of_rfg_exst_cd", "trms_of_lwr_tmp_meas", "trms_of_upp_tmp_meas", "car_cls_of_crn_exst_cd",
			"car_rmk_about_eqpm_txt", "car_cmpn_name_of_gtp_crtf_exst_txt");
	// vehicle_avb_resource 関連のフィールド
	public final List<String> VEHICLE_AVG_RESOURCE = Arrays.asList("trsp_op_strt_area_line_one_txt",
			"trsp_op_strt_area_cty_jis_cd", "trsp_op_date_trm_strt_date", "trsp_op_plan_date_trm_strt_time",
			"trsp_op_end_area_line_one_txt", "trsp_op_end_area_cty_jis_cd", "trsp_op_date_trm_end_date",
			"trsp_op_plan_date_trm_end_time", "clb_area_txt", "trms_of_clb_area_cd", "avb_date_cll_date",
			"avb_from_time_of_cll_time", "avb_to_time_of_cll_time", "delb_area_txt", "trms_of_delb_area_cd",
			"esti_del_date_prfm_dttm", "avb_from_time_of_del_time", "avb_to_time_of_del_time",
			"avb_load_cp_of_car_meas", "avb_load_vol_of_car_meas", "pcke_frm_cd", "avb_num_of_retb_cntn_of_car_quan",
			"trk_bed_stas_txt");
	// drv_info 関連のフィールド
	public final List<String> DRV_INFO = Arrays.asList("drv_ctrl_num_id", "drv_cls_of_drvg_license_cd",
			"drv_cls_of_fkl_license_exst_cd", "drv_rmk_about_drv_txt", "drv_cmpn_name_of_gtp_crtf_exst_txt");
	// drv_avb_time 関連のフィールド
	public final List<String> drv_avb_time = Arrays.asList("drv_avb_from_date", "drv_avb_from_time_of_wrkg_time",
			"drv_avb_to_date", "drv_avb_to_time_of_wrkg_time", "drv_wrkg_trms_txt", "drv_frmr_optg_date",
			"drv_frmr_op_end_time");
	// cut_off_Info 関連のフィールド
	public final List<String> CUT_OFF_INFO = Arrays.asList("cut_off_time", "cut_off_fee");
	// free_time_Info 関連のフィールド
	public final List<String> FREE_TIME_INFO = Arrays.asList("free_time", "free_time_fee");

	public int road_carrMapCount = 0;
	public int logs_srvc_prvMapCount = 0;
	public int car_infoMapCount = 0;
	public int drv_infoMapCount = 0;

	/**
	 * CSVファイルからJSONデータに変換
	 */
	public String convertCsvToJson(List<String> csvData, JsonDataToAPI jsonDataToAPITest) throws Exception {
		logger.info("ConvertCsvToJsonPattern5001.convertCsvToJson 開始");
		if (csvData == null || csvData.isEmpty()) {
			return "{}";
		}

		// ヘッダー行を取得
		String[] headers = csvData.get(0).split(",");
		TransportCapacity transportCapacity = new TransportCapacity();

		// msg_info の作成
		Msginfo msg_info = new Msginfo();
		Map<String, String> firstRecord = mapHeadersToValues(headers, csvData.get(1).split(",", -1));
		msg_info.setMsg_id(firstRecord.get("msg_id"));
		msg_info.setMsg_info_cls_typ_cd(firstRecord.get("msg_info_cls_typ_cd"));
		msg_info.setMsg_date_iss_dttm(firstRecord.get("msg_date_iss_dttm"));
		msg_info.setMsg_time_iss_dttm(firstRecord.get("msg_time_iss_dttm"));
		msg_info.setMsg_fn_stas_cd(firstRecord.get("msg_fn_stas_cd"));
		msg_info.setNote_dcpt_txt(firstRecord.get("note_dcpt_txt"));
		transportCapacity.setMsg_info(msg_info);

		// road_carrを作成
		Map<String, RoadCarr> road_carrMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addRoadCarr(road_carrMap, record));

		// Map から List<RoadCarr> に変換
		List<RoadCarr> road_carrList = new ArrayList<>(road_carrMap.values());

		// logs_srvc_prvを作成
		Map<String, LogsSrvcPrv> logs_srvc_prvMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addLogsSrvcPrv(logs_srvc_prvMap, record));

		// Map から List<LogsSrvcPrv> に変換
		List<LogsSrvcPrv> logs_srvc_prvList = new ArrayList<>(logs_srvc_prvMap.values());

		//car_infoを作成
		Map<String, CarInfo> car_infoMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addCarInfo(car_infoMap, record));

		// Map から List<CarInfo> に変換
		List<CarInfo> car_infoList = new ArrayList<>(car_infoMap.values());

		// drv_infoを作成
		Map<String, DrvInfo> drv_infoMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addDrvInfo(drv_infoMap, record));

		// Map から List<DrvInfo> に変換
		List<DrvInfo> drv_infoList = new ArrayList<>(drv_infoMap.values());

		//trsp_ability_line_itemの生成
		List<TrspAbilityLineItem> trsp_ability_line_itemList = new ArrayList<>();
		for (int i = 0; i < road_carrList.size(); i++) {
			TrspAbilityLineItem trsp_ability_line_item = new TrspAbilityLineItem();
			trsp_ability_line_item.setRoad_carr(road_carrList.get(i));
			trsp_ability_line_item.setLogs_srvc_prv(logs_srvc_prvList.get(i));
			trsp_ability_line_item.getCar_info().add(car_infoList.get(i));
			trsp_ability_line_item.getDrv_info().add(drv_infoList.get(i));
			trsp_ability_line_itemList.add(trsp_ability_line_item);
		}
		transportCapacity.setMsg_info(msg_info);
		transportCapacity.setTrsp_ability_line_item(trsp_ability_line_itemList);
		// JSON に変換
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		logger.info("ConvertCsvToJsonPattern5001.convertCsvToJson 終了");
		jsonDataToAPITest.setAttribute(transportCapacity);
		return mapper.writeValueAsString(transportCapacity);
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
		 * road_carr を作成しMapに保存
		 */
	private void addRoadCarr(Map<String, RoadCarr> road_carrMap, Map<String, String> record) {
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

		// RoadCarr に保存
		RoadCarr road_carr =
				new RoadCarr(trsp_cli_prty_head_off_id, trsp_cli_prty_brnc_off_id, trsp_cli_prty_name_txt,
						road_carr_depa_sped_org_id,
						road_carr_depa_sped_org_name_txt, trsp_cli_tel_cmm_cmp_num_txt,
						road_carr_arr_sped_org_id, road_carr_arr_sped_org_name_txt);
		// Map に保存
		road_carrMap.put(String.valueOf(road_carrMapCount), road_carr);
		road_carrMapCount = road_carrMapCount + 1;
	}

	/**
	 * logs_srvc を作成しMapに保存
	 */
	private void addLogsSrvcPrv(Map<String, LogsSrvcPrv> logs_srvcMap, Map<String, String> record) {
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
		LogsSrvcPrv logs_srvc =
				new LogsSrvcPrv(logs_srvc_prv_prty_head_off_id, logs_srvc_prv_prty_brnc_off_id,
						logs_srvc_prv_prty_name_txt,
						logs_srvc_prv_sct_sped_org_id,
						logs_srvc_prv_sct_sped_org_name_txt, logs_srvc_prv_sct_prim_cnt_pers_name_txt,
						logs_srvc_prv_sct_tel_cmm_cmp_num_txt);
		// Map に保存
		logs_srvcMap.put(String.valueOf(logs_srvc_prvMapCount), logs_srvc);
		logs_srvc_prvMapCount = logs_srvc_prvMapCount + 1;
	}

	/**
	 * car_info を作成しMapに保存
	 */
	private void addCarInfo(Map<String, CarInfo> car_infoMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, CAR_INFO)) {
			return;
		}
		String service_no = record.get("service_no");
		String service_name = record.get("service_name");
		String service_strt_date = record.get("service_strt_date");
		String service_strt_time = record.get("service_strt_time");
		String service_end_date = record.get("service_end_date");
		String service_end_time = record.get("service_end_time");
		String freight_rate = record.get("freight_rate");
		String car_ctrl_num_id = record.get("car_ctrl_num_id");
		String car_license_plt_num_id = record.get("car_license_plt_num_id");
		String giai = record.get("giai");
		String car_body_num_cd = record.get("car_body_num_cd");
		String car_cls_of_size_cd = record.get("car_cls_of_size_cd");
		String tractor_idcr = record.get("tractor_idcr");
		String trailer_license_plt_num_id = record.get("trailer_license_plt_num_id");
		String car_weig_meas = record.get("car_weig_meas");
		String car_lngh_meas = record.get("car_lngh_meas");
		String car_wid_meas = record.get("car_wid_meas");
		String car_hght_meas = record.get("car_hght_meas");
		String car_max_load_capacity1_meas = record.get("car_max_load_capacity1_meas");
		String car_max_load_capacity2_meas = record.get("car_max_load_capacity2_meas");
		String car_vol_of_hzd_item_meas = record.get("car_vol_of_hzd_item_meas");
		String car_spc_grv_of_hzd_item_meas = record.get("car_spc_grv_of_hzd_item_meas");
		String car_trk_bed_hght_meas = record.get("car_trk_bed_hght_meas");
		String car_trk_bed_wid_meas = record.get("car_trk_bed_wid_meas");
		String car_trk_bed_lngh_meas = record.get("car_trk_bed_lngh_meas");
		String car_trk_bed_grnd_hght_meas = record.get("car_trk_bed_grnd_hght_meas");
		String car_max_load_vol_meas = record.get("car_max_load_vol_meas");
		String pcke_frm_cd = record.get("car_pcke_frm_cd");
		String pcke_frm_name_cd = record.get("pcke_frm_name_cd");
		String car_max_untl_cp_quan = record.get("car_max_untl_cp_quan");
		String car_cls_of_shp_cd = record.get("car_cls_of_shp_cd");
		String car_cls_of_tlg_lftr_exst_cd = record.get("car_cls_of_tlg_lftr_exst_cd");
		String car_cls_of_wing_body_exst_cd = record.get("car_cls_of_wing_body_exst_cd");
		String car_cls_of_rfg_exst_cd = record.get("car_cls_of_rfg_exst_cd");
		String trms_of_lwr_tmp_meas = record.get("trms_of_lwr_tmp_meas");
		String trms_of_upp_tmp_meas = record.get("trms_of_upp_tmp_meas");
		String car_cls_of_crn_exst_cd = record.get("car_cls_of_crn_exst_cd");
		String car_rmk_about_eqpm_txt = record.get("car_rmk_about_eqpm_txt");
		String car_cmpn_name_of_gtp_crtf_exst_txt = record.get("car_cmpn_name_of_gtp_crtf_exst_txt");

		//CarInfo に保存
		CarInfo car_info = new CarInfo(
				service_no,
				service_name,
				service_strt_date,
				service_strt_time,
				service_end_date,
				service_end_time,
				freight_rate,
				car_ctrl_num_id,
				car_license_plt_num_id,
				giai,
				car_body_num_cd,
				car_cls_of_size_cd,
				tractor_idcr,
				trailer_license_plt_num_id,
				car_weig_meas,
				car_lngh_meas,
				car_wid_meas,
				car_hght_meas,
				car_max_load_capacity1_meas,
				car_max_load_capacity2_meas,
				car_vol_of_hzd_item_meas,
				car_spc_grv_of_hzd_item_meas,
				car_trk_bed_hght_meas,
				car_trk_bed_wid_meas,
				car_trk_bed_lngh_meas,
				car_trk_bed_grnd_hght_meas,
				car_max_load_vol_meas,
				pcke_frm_cd,
				pcke_frm_name_cd,
				car_max_untl_cp_quan,
				car_cls_of_shp_cd,
				car_cls_of_tlg_lftr_exst_cd,
				car_cls_of_wing_body_exst_cd,
				car_cls_of_rfg_exst_cd,
				trms_of_lwr_tmp_meas,
				trms_of_upp_tmp_meas,
				car_cls_of_crn_exst_cd,
				car_rmk_about_eqpm_txt,
				car_cmpn_name_of_gtp_crtf_exst_txt);

		// vehicle_avb_resource の追加
		if (!isAllFieldsEmpty(record, VEHICLE_AVG_RESOURCE)) {
			VehicleAvbResource vehicle_avb_resource = new VehicleAvbResource(
					record.get("trsp_op_strt_area_line_one_txt"),
					record.get("trsp_op_strt_area_cty_jis_cd"),
					record.get("trsp_op_date_trm_strt_date"),
					record.get("trsp_op_plan_date_trm_strt_time"),
					record.get("trsp_op_end_area_line_one_txt"),
					record.get("trsp_op_end_area_cty_jis_cd"),
					record.get("trsp_op_date_trm_end_date"),
					record.get("trsp_op_plan_date_trm_end_time"),
					record.get("clb_area_txt"),
					record.get("trms_of_clb_area_cd"),
					record.get("avb_date_cll_date"),
					record.get("avb_from_time_of_cll_time"),
					record.get("avb_to_time_of_cll_time"),
					record.get("delb_area_txt"),
					record.get("trms_of_delb_area_cd"),
					record.get("esti_del_date_prfm_dttm"),
					record.get("avb_from_time_of_del_time"),
					record.get("avb_to_time_of_del_time"),
					record.get("avb_load_cp_of_car_meas"),
					record.get("avb_load_vol_of_car_meas"),
					record.get("vehicle_pcke_frm_cd"),
					record.get("avb_num_of_retb_cntn_of_car_quan"),
					record.get("trk_bed_stas_txt"));

			car_info.getVehicle_avb_resource().add(vehicle_avb_resource);
		}

		int maxIndex = car_info.getVehicle_avb_resource().size() - 1;
		// cut_off_Info の追加
		if (!isAllFieldsEmpty(record, CUT_OFF_INFO)) {
			CutOffInfo cut_off_info = new CutOffInfo(
					record.get("cut_off_time"),
					record.get("cut_off_fee"));
			car_info.getVehicle_avb_resource().get(maxIndex).getCut_off_info().add(cut_off_info);
		}
		// free_time_info の追加
		if (!isAllFieldsEmpty(record, FREE_TIME_INFO)) {
			FreeTimeInfo free_time_info = new FreeTimeInfo(
					record.get("free_time"),
					record.get("free_time_fee"));
			car_info.getVehicle_avb_resource().get(maxIndex).getFree_time_info().add(free_time_info);
		}

		// Map に保存
		car_infoMap.put(String.valueOf(car_infoMapCount), car_info);
		car_infoMapCount = car_infoMapCount + 1;
	}

	/**
	 * drv_info を作成しMapに保存
	 */
	private void addDrvInfo(Map<String, DrvInfo> drv_infoMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, DRV_INFO)) {
			return;
		}
		String drv_ctrl_num_id = record.get("drv_ctrl_num_id");
		String drv_cls_of_drvg_license_cd = record.get("drv_cls_of_drvg_license_cd");
		String drv_cls_of_fkl_license_exst_cd = record.get("drv_cls_of_fkl_license_exst_cd");
		String drv_rmk_about_drv_txt = record.get("drv_rmk_about_drv_txt");
		String drv_cmpn_name_of_gtp_crtf_exst_txt = record.get("drv_cmpn_name_of_gtp_crtf_exst_txt");

		// DrvInfo に保存
		DrvInfo drv_info = //drv_infoMap.getOrDefault(drv_ctrl_num_id,
				new DrvInfo(drv_ctrl_num_id, drv_cls_of_drvg_license_cd,
						drv_cls_of_fkl_license_exst_cd,
						drv_rmk_about_drv_txt, drv_cmpn_name_of_gtp_crtf_exst_txt);
		// drv_avb_time の追加
		if (!isAllFieldsEmpty(record, drv_avb_time)) {
			DrvAvbTime drv_avb_time = new DrvAvbTime(
					record.get("drv_avb_from_date"),
					record.get("drv_avb_from_time_of_wrkg_time"),
					record.get("drv_avb_to_date"),
					record.get("drv_avb_to_time_of_wrkg_time"),
					record.get("drv_wrkg_trms_txt"),
					record.get("drv_frmr_optg_date"),
					record.get("drv_frmr_op_end_time"));
			drv_info.getDrv_avb_time().add(drv_avb_time);
		}

		// Map に保存
		drv_infoMap.put(String.valueOf(drv_infoMapCount), drv_info);
		drv_infoMapCount = drv_infoMapCount + 1;
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
