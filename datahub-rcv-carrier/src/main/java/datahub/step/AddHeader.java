package datahub.step;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * ヘッダーの追加
 */
@Component
public class AddHeader extends BaseStep {

	/**
	 *コンストラクタ
	 */
	public AddHeader(MessageSource ms) {
		super(ms);
	}

	/**
	 * ヘッダーの追加
	 */
	public void addHeader(String DataTypeToUse, Path csvFilePath)
			throws FileNotFoundException, IOException {
		String inputFilePath = String.valueOf(csvFilePath);
		String originalFilePath = String.valueOf(csvFilePath);
		// ファイルパスを "_bk.csv" で終わるように変更 
		String outputFilePath = originalFilePath.replace(".csv", "_bk.csv");

		/**
		 * 情報区分によりヘッダーを分岐
		 */
		String[] header = { "Column1", "Column2", "Column3" };
		if ("5001".equals(DataTypeToUse)) {
			header = new String[] {
					"msg_id", "msg_info_cls_typ_cd", "msg_date_iss_dttm", "msg_time_iss_dttm", "msg_fn_stas_cd",
					"note_dcpt_txt", "trsp_cli_prty_head_off_id", "trsp_cli_prty_brnc_off_id", "trsp_cli_prty_name_txt",
					"road_carr_depa_sped_org_id", "road_carr_depa_sped_org_name_txt", "trsp_cli_tel_cmm_cmp_num_txt",
					"road_carr_arr_sped_org_id", "road_carr_arr_sped_org_name_txt", "logs_srvc_prv_prty_head_off_id",
					"logs_srvc_prv_prty_brnc_off_id", "logs_srvc_prv_prty_name_txt", "logs_srvc_prv_sct_sped_org_id",
					"logs_srvc_prv_sct_sped_org_name_txt", "logs_srvc_prv_sct_prim_cnt_pers_name_txt",
					"logs_srvc_prv_sct_tel_cmm_cmp_num_txt", "service_no", "service_name", "service_strt_date",
					"service_strt_time", "service_end_date", "service_end_time", "freight_rate", "car_ctrl_num_id",
					"car_license_plt_num_id", "giai", "car_body_num_cd", "car_cls_of_size_cd", "tractor_idcr",
					"trailer_license_plt_num_id", "car_weig_meas", "car_lngh_meas", "car_wid_meas", "car_hght_meas",
					"car_max_load_capacity1_meas", "car_max_load_capacity2_meas", "car_vol_of_hzd_item_meas",
					"car_spc_grv_of_hzd_item_meas", "car_trk_bed_hght_meas", "car_trk_bed_wid_meas",
					"car_trk_bed_lngh_meas", "car_trk_bed_grnd_hght_meas", "car_max_load_vol_meas", "car_pcke_frm_cd",
					"pcke_frm_name_cd", "car_max_untl_cp_quan", "car_cls_of_shp_cd", "car_cls_of_tlg_lftr_exst_cd",
					"car_cls_of_wing_body_exst_cd", "car_cls_of_rfg_exst_cd", "trms_of_lwr_tmp_meas",
					"trms_of_upp_tmp_meas", "car_cls_of_crn_exst_cd", "car_rmk_about_eqpm_txt",
					"car_cmpn_name_of_gtp_crtf_exst_txt", "trsp_op_strt_area_line_one_txt",
					"trsp_op_strt_area_cty_jis_cd", "trsp_op_date_trm_strt_date", "trsp_op_plan_date_trm_strt_time",
					"trsp_op_end_area_line_one_txt", "trsp_op_end_area_cty_jis_cd", "trsp_op_date_trm_end_date",
					"trsp_op_plan_date_trm_end_time", "clb_area_txt", "trms_of_clb_area_cd", "avb_date_cll_date",
					"avb_from_time_of_cll_time", "avb_to_time_of_cll_time", "delb_area_txt", "trms_of_delb_area_cd",
					"esti_del_date_prfm_dttm", "avb_from_time_of_del_time", "avb_to_time_of_del_time",
					"avb_load_cp_of_car_meas", "avb_load_vol_of_car_meas", "vehicle_pcke_frm_cd",
					"avb_num_of_retb_cntn_of_car_quan", "trk_bed_stas_txt", "cut_off_time", "cut_off_fee", "free_time",
					"free_time_fee", "drv_ctrl_num_id", "drv_cls_of_drvg_license_cd", "drv_cls_of_fkl_license_exst_cd",
					"drv_rmk_about_drv_txt", "drv_cmpn_name_of_gtp_crtf_exst_txt", "drv_avb_from_date",
					"drv_avb_from_time_of_wrkg_time", "drv_avb_to_date", "drv_avb_to_time_of_wrkg_time",
					"drv_wrkg_trms_txt", "drv_frmr_optg_date", "drv_frmr_op_end_time"
			};
		} else if ("3012".equals(DataTypeToUse)) {
			header = new String[] {
					"msg_id", "msg_info_cls_typ_cd", "msg_date_iss_dttm", "msg_time_iss_dttm", "msg_fn_stas_cd",
					"note_dcpt_txt",
					"trsp_plan_stas_cd",
					"trsp_instruction_id", "trsp_instruction_date_subm_dttm", "inv_num_id", "cmn_inv_num_id",
					"mix_load_num_id",
					"service_no", "service_name", "service_strt_date", "service_strt_time", "service_end_date",
					"service_end_time",
					"freight_rate", "trsp_means_typ_cd", "trsp_srvc_typ_cd", "road_carr_srvc_typ_cd",
					"trsp_root_prio_cd",
					"car_cls_prio_cd", "cls_of_carg_in_srvc_rqrm_cd", "cls_of_pkg_up_srvc_rqrm_cd",
					"pyr_cls_srvc_rqrm_cd",
					"trms_of_mix_load_cnd_cd", "dsed_cll_from_date", "dsed_cll_to_date", "dsed_cll_from_time",
					"dsed_cll_to_time",
					"dsed_cll_time_trms_srvc_rqrm_cd", "aped_arr_from_date", "aped_arr_to_date",
					"aped_arr_from_time_prfm_dttm",
					"aped_arr_to_time_prfm_dttm", "aped_arr_time_trms_srvc_rqrm_cd", "trms_of_mix_load_txt",
					"trsp_srvc_note_one_txt",
					"trsp_srvc_note_two_txt",
					"car_cls_of_size_cd", "car_cls_of_shp_cd", "car_cls_of_tlg_lftr_exst_cd",
					"car_cls_of_wing_body_exst_cd",
					"car_cls_of_rfg_exst_cd", "trms_of_upp_tmp_meas", "trms_of_lwr_tmp_meas", "car_cls_of_crn_exst_cd",
					"car_rmk_about_eqpm_txt",
					"del_note_id", "shpm_num_id", "rced_ord_num_id", "istd_totl_pcks_quan", "num_unt_cd",
					"istd_totl_weig_meas", "weig_unt_cd", "istd_totl_vol_meas", "vol_unt_cd", "istd_totl_untl_quan",
					"line_item_num_id", "sev_ord_num_id", "cnsg_crg_item_num_id", "buy_assi_item_cd",
					"sell_assi_item_cd",
					"wrhs_assi_item_cd", "item_name_txt", "gods_idcs_in_ots_pcke_name_txt", "num_of_istd_untl_quan",
					"num_of_istd_quan", "sev_num_unt_cd", "istd_pcke_weig_meas", "sev_weig_unt_cd",
					"istd_pcke_vol_meas",
					"sev_vol_unt_cd", "istd_quan_meas", "cnte_num_unt_cd", "dcpv_trpn_pckg_txt", "pcke_frm_cd",
					"pcke_frm_name_cd",
					"crg_hnd_trms_spcl_isrs_txt", "glb_retb_asse_id", "totl_rti_quan_quan",
					"chrg_of_pcke_ctrl_num_unt_amnt",
					"cnsg_prty_head_off_id", "cnsg_prty_brnc_off_id", "cnsg_prty_name_txt", "cnsg_sct_sped_org_id",
					"cnsg_sct_sped_org_name_txt", "cnsg_tel_cmm_cmp_num_txt", "cnsg_pstl_adrs_line_one_txt",
					"cnsg_pstc_cd",
					"trsp_rqr_prty_head_off_id", "trsp_rqr_prty_brnc_off_id", "trsp_rqr_prty_name_txt",
					"trsp_rqr_sct_sped_org_id",
					"trsp_rqr_sct_sped_org_name_txt", "trsp_rqr_sct_tel_cmm_cmp_num_txt",
					"trsp_rqr_pstl_adrs_line_one_txt",
					"trsp_rqr_pstc_cd",
					"cnee_prty_head_off_id", "cnee_prty_brnc_off_id", "cnee_prty_name_txt", "cnee_sct_id",
					"cnee_sct_name_txt",
					"cnee_prim_cnt_pers_name_txt", "cnee_tel_cmm_cmp_num_txt", "cnee_pstl_adrs_line_one_txt",
					"cnee_pstc_cd",
					"logs_srvc_prv_prty_head_off_id", "logs_srvc_prv_prty_brnc_off_id", "logs_srvc_prv_prty_name_txt",
					"logs_srvc_prv_sct_sped_org_id", "logs_srvc_prv_sct_sped_org_name_txt",
					"logs_srvc_prv_sct_prim_cnt_pers_name_txt",
					"logs_srvc_prv_sct_tel_cmm_cmp_num_txt",
					"trsp_cli_prty_head_off_id", "trsp_cli_prty_brnc_off_id", "trsp_cli_prty_name_txt",
					"road_carr_depa_sped_org_id",
					"road_carr_depa_sped_org_name_txt", "trsp_cli_tel_cmm_cmp_num_txt", "road_carr_arr_sped_org_id",
					"road_carr_arr_sped_org_name_txt",
					"fret_clim_to_prty_head_off_id", "fret_clim_to_prty_brnc_off_id", "fret_clim_to_prty_name_txt",
					"fret_clim_to_sct_sped_org_id", "fret_clim_to_sct_sped_org_name_txt",
					"ship_from_prty_head_off_id", "ship_from_prty_brnc_off_id", "ship_from_prty_name_txt",
					"ship_from_sct_id",
					"ship_from_sct_name_txt", "ship_from_tel_cmm_cmp_num_txt", "ship_from_pstl_adrs_cty_id",
					"ship_from_pstl_adrs_id",
					"ship_from_pstl_adrs_line_one_txt", "ship_from_pstc_cd", "from_plc_cd_prty_id", "from_gln_prty_id",
					"from_jpn_uplc_cd", "from_jpn_van_srvc_cd", "from_jpn_van_vans_cd",
					"from_trms_of_car_size_cd", "from_trms_of_car_hght_meas", "trms_of_gtp_cert_txt", "trms_of_cll_txt",
					"from_trms_of_gods_hnd_txt", "anc_wrk_of_cll_txt", "from_spcl_wrk_txt",
					"cut_off_time", "cut_off_fee",
					"ship_to_prty_head_off_id", "ship_to_prty_brnc_off_id", "ship_to_prty_name_txt", "ship_to_sct_id",
					"ship_to_sct_name_txt", "ship_to_prim_cnt_id", "ship_to_prim_cnt_pers_name_txt",
					"ship_to_tel_cmm_cmp_num_txt",
					"ship_to_pstl_adrs_cty_id", "ship_to_pstl_adrs_id", "ship_to_pstl_adrs_line_one_txt",
					"ship_to_pstc_cd",
					"to_plc_cd_prty_id", "to_gln_prty_id", "to_jpn_uplc_cd", "to_jpn_van_srvc_cd", "to_jpn_van_vans_cd",
					"free_time", "free_time_fee",
					"to_trms_of_car_size_cd", "to_trms_of_car_hght_meas", "to_trms_of_gtp_cert_txt", "trms_of_del_txt",
					"to_trms_of_gods_hnd_txt", "anc_wrk_of_del_txt", "to_spcl_wrk_txt"
			};
		} else if ("4902".equals(DataTypeToUse)) {
			header = new String[] {
					"registration_number", "giai", "registration_transport_office_name", "registration_vehicle_type",
					"registration_vehicle_use", "registration_vehicle_id", "chassis_number", "vehicle_id",
					"operator_corporate_number", "operator_business_code", "owner_corporate_number",
					"owner_business_code", "vehicle_type", "hazardous_material_vehicle_type", "tractor", "trailer",
					"max_payload_1", "max_payload_2", "vehicle_weight",
					"vehicle_length", "vehicle_width", "vehicle_height", "hazardous_material_volume",
					"hazardous_material_specific_gravity", "expiry_date", "deregistration_status",
					"bed_height", "cargo_height", "cargo_width",
					"cargo_length", "max_cargo_capacity", "body_type", "power_gate", "wing_doors", "refrigeration_unit",
					"temperature_range_min", "temperature_range_max", "crane_equipped", "vehicle_equipment_notes",
					"master_data_start_date", "master_data_end_date",
					"package_code", "package_name_kanji",
					"width", "height", "depth", "dimension_unit_code", "max_load_quantity",
					"hazardous_material_item_code", "hazardous_material_text_info"
			};
		}
		// 一時ファイルにヘッダーとデータを書き込む 
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
				BufferedWriter writer = new BufferedWriter(
						new FileWriter(outputFilePath, StandardCharsets.UTF_8))) {
			// ヘッダーを書き込む 
			writer.write(String.join(",", header));
			writer.newLine();
			// 既存のCSVの内容を読み込み、一時ファイルに書き込む 
			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}
		}
		// 一時ファイルを元のファイルに置き換える 
		Files.move(Paths.get(outputFilePath), Paths.get(inputFilePath),
				java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		logger.info("CSVファイルにヘッダーが追加されました。");
	}

}
