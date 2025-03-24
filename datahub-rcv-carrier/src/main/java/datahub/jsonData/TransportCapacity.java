package datahub.jsonData;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 荷主向け運行案件用Model
 * 
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) //存在しないJSONデータがあれば無視する
public class TransportCapacity {

	/**
	 * コンストラクタ
	 */
	public TransportCapacity() {
	}

	@JsonProperty("msg_info")
	private Msginfo msg_info;

	@JsonProperty("trsp_ability_line_item")
	private List<TrspAbilityLineItem> trsp_ability_line_item;

	/**
	 *  MsgInfo（メッセージ情報）Model
	 */
	@Data
	public static class Msginfo {

		/**
		 * コンストラクタ
		 */
		public Msginfo(String msg_id, String msg_info_cls_typ_cd, String msg_date_iss_dttm, String msg_time_iss_dttm,
				String msg_fn_stas_cd, String note_dcpt_txt) {
			this.msg_id = msg_id;
			this.msg_info_cls_typ_cd = msg_info_cls_typ_cd;
			this.msg_date_iss_dttm = msg_date_iss_dttm;
			this.msg_time_iss_dttm = msg_time_iss_dttm;
			this.msg_fn_stas_cd = msg_fn_stas_cd;
			this.note_dcpt_txt = note_dcpt_txt;
		}

		/**
		 * コンストラクタ
		 */
		public Msginfo() {
		}

		@JsonProperty("msg_id")
		private String msg_id;

		@JsonProperty("msg_info_cls_typ_cd")
		private String msg_info_cls_typ_cd;

		@JsonProperty("msg_date_iss_dttm")
		private String msg_date_iss_dttm;

		@JsonProperty("msg_time_iss_dttm")
		private String msg_time_iss_dttm;

		@JsonProperty("msg_fn_stas_cd")
		private String msg_fn_stas_cd;

		@JsonProperty("note_dcpt_txt")
		private String note_dcpt_txt;

	}

	/**
	 * TrspAbilityLineItem（運送能力明細）Model
	 */
	@Data
	public static class TrspAbilityLineItem {

		@JsonProperty("road_carr")
		private RoadCarr road_carr;
		@JsonProperty("logs_srvc_prv")
		private LogsSrvcPrv logs_srvc_prv;
		@JsonProperty("car_info")
		private List<CarInfo> car_info = new ArrayList<CarInfo>();
		@JsonProperty("drv_info")
		private List<DrvInfo> drv_info = new ArrayList<DrvInfo>();

	}

	/**
	 * RoadCarr（運送事業者）Model
	 */
	@Data
	public static class RoadCarr {

		/**
		 * コンストラクタ
		 */
		public RoadCarr() {

		}

		/**
		 * コンストラクタ
		 */
		public RoadCarr(
				String trsp_cli_prty_head_off_id,
				String trsp_cli_prty_brnc_off_id,
				String trsp_cli_prty_name_txt,
				String road_carr_depa_sped_org_id,
				String road_carr_depa_sped_org_name_txt,
				String trsp_cli_tel_cmm_cmp_num_txt,
				String road_carr_arr_sped_org_id,
				String road_carr_arr_sped_org_name_txt) {
			this.trsp_cli_prty_head_off_id = trsp_cli_prty_head_off_id;
			this.trsp_cli_prty_brnc_off_id = trsp_cli_prty_brnc_off_id;
			this.trsp_cli_prty_name_txt = trsp_cli_prty_name_txt;
			this.road_carr_depa_sped_org_id = road_carr_depa_sped_org_id;
			this.road_carr_depa_sped_org_name_txt = road_carr_depa_sped_org_name_txt;
			this.trsp_cli_tel_cmm_cmp_num_txt = trsp_cli_tel_cmm_cmp_num_txt;
			this.road_carr_arr_sped_org_id = road_carr_arr_sped_org_id;
			this.road_carr_arr_sped_org_name_txt = road_carr_arr_sped_org_name_txt;
		}

		@JsonProperty("trsp_cli_prty_head_off_id")
		private String trsp_cli_prty_head_off_id;
		@JsonProperty("trsp_cli_prty_brnc_off_id")
		private String trsp_cli_prty_brnc_off_id;
		@JsonProperty("trsp_cli_prty_name_txt")
		private String trsp_cli_prty_name_txt;
		@JsonProperty("road_carr_depa_sped_org_id")
		private String road_carr_depa_sped_org_id;
		@JsonProperty("road_carr_depa_sped_org_name_txt")
		private String road_carr_depa_sped_org_name_txt;
		@JsonProperty("trsp_cli_tel_cmm_cmp_num_txt")
		private String trsp_cli_tel_cmm_cmp_num_txt;
		@JsonProperty("road_carr_arr_sped_org_id")
		private String road_carr_arr_sped_org_id;
		@JsonProperty("road_carr_arr_sped_org_name_txt")
		private String road_carr_arr_sped_org_name_txt;

	}

	/**
	 * LogsSrvcPrv（物流サービス提供者）Model
	 */
	@Data
	public static class LogsSrvcPrv {

		/**
		 * コンストラクタ
		 */
		public LogsSrvcPrv() {

		}

		/**
		 * コンストラクタ
		 */
		public LogsSrvcPrv(String logs_srvc_prv_prty_head_off_id, String logs_srvc_prv_prty_brnc_off_id,
				String logs_srvc_prv_prty_name_txt, String logs_srvc_prv_sct_sped_org_id,
				String logs_srvc_prv_sct_sped_org_name_txt, String logs_srvc_prv_sct_prim_cnt_pers_name_txt,
				String logs_srvc_prv_sct_tel_cmm_cmp_num_txt) {
			this.logs_srvc_prv_prty_head_off_id = logs_srvc_prv_prty_head_off_id;
			this.logs_srvc_prv_prty_brnc_off_id = logs_srvc_prv_prty_brnc_off_id;
			this.logs_srvc_prv_prty_name_txt = logs_srvc_prv_prty_name_txt;
			this.logs_srvc_prv_sct_sped_org_id = logs_srvc_prv_sct_sped_org_id;
			this.logs_srvc_prv_sct_sped_org_name_txt = logs_srvc_prv_sct_sped_org_name_txt;
			this.logs_srvc_prv_sct_prim_cnt_pers_name_txt = logs_srvc_prv_sct_prim_cnt_pers_name_txt;
			this.logs_srvc_prv_sct_tel_cmm_cmp_num_txt = logs_srvc_prv_sct_tel_cmm_cmp_num_txt;

		}

		@JsonProperty("logs_srvc_prv_prty_head_off_id")
		private String logs_srvc_prv_prty_head_off_id;
		@JsonProperty("logs_srvc_prv_prty_brnc_off_id")
		private String logs_srvc_prv_prty_brnc_off_id;
		@JsonProperty("logs_srvc_prv_prty_name_txt")
		private String logs_srvc_prv_prty_name_txt;
		@JsonProperty("logs_srvc_prv_sct_sped_org_id")
		private String logs_srvc_prv_sct_sped_org_id;
		@JsonProperty("logs_srvc_prv_sct_sped_org_name_txt")
		private String logs_srvc_prv_sct_sped_org_name_txt;
		@JsonProperty("logs_srvc_prv_sct_prim_cnt_pers_name_txt")
		private String logs_srvc_prv_sct_prim_cnt_pers_name_txt;
		@JsonProperty("logs_srvc_prv_sct_tel_cmm_cmp_num_txt")
		private String logs_srvc_prv_sct_tel_cmm_cmp_num_txt;

	}

	/**
	 * CarInfo（車輌情報）Model
	 */
	@Data
	public static class CarInfo {

		/**
		 * コンストラクタ
		 */
		public CarInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public CarInfo(String service_no,
				String service_name,
				String service_strt_date,
				String service_strt_time,
				String service_end_date,
				String service_end_time,
				String freight_rate,
				String car_ctrl_num_id,
				String car_license_plt_num_id,
				String giai,
				String car_body_num_cd,
				String car_cls_of_size_cd,
				String tractor_idcr,
				String trailer_license_plt_num_id,
				String car_weig_meas,
				String car_lngh_meas,
				String car_wid_meas,
				String car_hght_meas,
				String car_max_load_capacity1_meas,
				String car_max_load_capacity2_meas,
				String car_vol_of_hzd_item_meas,
				String car_spc_grv_of_hzd_item_meas,
				String car_trk_bed_hght_meas,
				String car_trk_bed_wid_meas,
				String car_trk_bed_lngh_meas,
				String car_trk_bed_grnd_hght_meas,
				String car_max_load_vol_meas,
				String pcke_frm_cd,
				String pcke_frm_name_cd,
				String car_max_untl_cp_quan,
				String car_cls_of_shp_cd,
				String car_cls_of_tlg_lftr_exst_cd,
				String car_cls_of_wing_body_exst_cd,
				String car_cls_of_rfg_exst_cd,
				String trms_of_lwr_tmp_meas,
				String trms_of_upp_tmp_meas,
				String car_cls_of_crn_exst_cd,
				String car_rmk_about_eqpm_txt,
				String car_cmpn_name_of_gtp_crtf_exst_txt) {
			this.service_no = service_no;
			this.service_name = service_name;
			this.service_strt_date = service_strt_date;
			this.service_strt_time = service_strt_time;
			this.service_end_date = service_end_date;
			this.service_end_time = service_end_time;
			this.freight_rate = freight_rate;
			this.car_ctrl_num_id = car_ctrl_num_id;
			this.car_license_plt_num_id = car_license_plt_num_id;
			this.giai = giai;
			this.car_body_num_cd = car_body_num_cd;
			this.car_cls_of_size_cd = car_cls_of_size_cd;
			this.tractor_idcr = tractor_idcr;
			this.trailer_license_plt_num_id = trailer_license_plt_num_id;
			this.car_weig_meas = car_weig_meas;
			this.car_lngh_meas = car_lngh_meas;
			this.car_wid_meas = car_wid_meas;
			this.car_hght_meas = car_hght_meas;
			this.car_max_load_capacity1_meas = car_max_load_capacity1_meas;
			this.car_max_load_capacity2_meas = car_max_load_capacity2_meas;
			this.car_vol_of_hzd_item_meas = car_vol_of_hzd_item_meas;
			this.car_spc_grv_of_hzd_item_meas = car_spc_grv_of_hzd_item_meas;
			this.car_trk_bed_hght_meas = car_trk_bed_hght_meas;
			this.car_trk_bed_wid_meas = car_trk_bed_wid_meas;
			this.car_trk_bed_lngh_meas = car_trk_bed_lngh_meas;
			this.car_trk_bed_grnd_hght_meas = car_trk_bed_grnd_hght_meas;
			this.car_max_load_vol_meas = car_max_load_vol_meas;
			this.pcke_frm_cd = pcke_frm_cd;
			this.pcke_frm_name_cd = pcke_frm_name_cd;
			this.car_max_untl_cp_quan = car_max_untl_cp_quan;
			this.car_cls_of_shp_cd = car_cls_of_shp_cd;
			this.car_cls_of_tlg_lftr_exst_cd = car_cls_of_tlg_lftr_exst_cd;
			this.car_cls_of_wing_body_exst_cd = car_cls_of_wing_body_exst_cd;
			this.car_cls_of_rfg_exst_cd = car_cls_of_rfg_exst_cd;
			this.trms_of_lwr_tmp_meas = trms_of_lwr_tmp_meas;
			this.trms_of_upp_tmp_meas = trms_of_upp_tmp_meas;
			this.car_cls_of_crn_exst_cd = car_cls_of_crn_exst_cd;
			this.car_rmk_about_eqpm_txt = car_rmk_about_eqpm_txt;
			this.car_cmpn_name_of_gtp_crtf_exst_txt = car_cmpn_name_of_gtp_crtf_exst_txt;

		}

		@JsonProperty("service_no")
		private String service_no;
		@JsonProperty("service_name")
		private String service_name;
		@JsonProperty("service_strt_date")
		private String service_strt_date;
		@JsonProperty("service_strt_time")
		private String service_strt_time;
		@JsonProperty("service_end_date")
		private String service_end_date;
		@JsonProperty("service_end_time")
		private String service_end_time;
		@JsonProperty("freight_rate")
		private String freight_rate;
		@JsonProperty("car_ctrl_num_id")
		private String car_ctrl_num_id;
		@JsonProperty("car_license_plt_num_id")
		private String car_license_plt_num_id;
		@JsonProperty("giai")
		private String giai;
		@JsonProperty("car_body_num_cd")
		private String car_body_num_cd;
		@JsonProperty("car_cls_of_size_cd")
		private String car_cls_of_size_cd;
		@JsonProperty("tractor_idcr")
		private String tractor_idcr;
		@JsonProperty("trailer_license_plt_num_id")
		private String trailer_license_plt_num_id;
		@JsonProperty("car_weig_meas")
		private String car_weig_meas;
		@JsonProperty("car_lngh_meas")
		private String car_lngh_meas;
		@JsonProperty("car_wid_meas")
		private String car_wid_meas;
		@JsonProperty("car_hght_meas")
		private String car_hght_meas;
		@JsonProperty("car_max_load_capacity1_meas")
		private String car_max_load_capacity1_meas;
		@JsonProperty("car_max_load_capacity2_meas")
		private String car_max_load_capacity2_meas;
		@JsonProperty("car_vol_of_hzd_item_meas")
		private String car_vol_of_hzd_item_meas;
		@JsonProperty("car_spc_grv_of_hzd_item_meas")
		private String car_spc_grv_of_hzd_item_meas;
		@JsonProperty("car_trk_bed_hght_meas")
		private String car_trk_bed_hght_meas;
		@JsonProperty("car_trk_bed_wid_meas")
		private String car_trk_bed_wid_meas;
		@JsonProperty("car_trk_bed_lngh_meas")
		private String car_trk_bed_lngh_meas;
		@JsonProperty("car_trk_bed_grnd_hght_meas")
		private String car_trk_bed_grnd_hght_meas;
		@JsonProperty("car_max_load_vol_meas")
		private String car_max_load_vol_meas;
		@JsonProperty("pcke_frm_cd")
		private String pcke_frm_cd;
		@JsonProperty("pcke_frm_name_cd")
		private String pcke_frm_name_cd;
		@JsonProperty("car_max_untl_cp_quan")
		private String car_max_untl_cp_quan;
		@JsonProperty("car_cls_of_shp_cd")
		private String car_cls_of_shp_cd;
		@JsonProperty("car_cls_of_tlg_lftr_exst_cd")
		private String car_cls_of_tlg_lftr_exst_cd;
		@JsonProperty("car_cls_of_wing_body_exst_cd")
		private String car_cls_of_wing_body_exst_cd;
		@JsonProperty("car_cls_of_rfg_exst_cd")
		private String car_cls_of_rfg_exst_cd;
		@JsonProperty("trms_of_lwr_tmp_meas")
		private String trms_of_lwr_tmp_meas;
		@JsonProperty("trms_of_upp_tmp_meas")
		private String trms_of_upp_tmp_meas;
		@JsonProperty("car_cls_of_crn_exst_cd")
		private String car_cls_of_crn_exst_cd;
		@JsonProperty("car_rmk_about_eqpm_txt")
		private String car_rmk_about_eqpm_txt;
		@JsonProperty("car_cmpn_name_of_gtp_crtf_exst_txt")
		private String car_cmpn_name_of_gtp_crtf_exst_txt;
		@JsonProperty("vehicle_avb_resource")
		private List<VehicleAvbResource> vehicle_avb_resource = new ArrayList<VehicleAvbResource>();
	}

	/**
	 * VehicleAvbResource（車輌稼働可能リソース）Model
	 */
	@Data
	public static class VehicleAvbResource {

		/**
		 * コンストラクタ
		 */
		public VehicleAvbResource() {
		}

		/**
		 * コンストラクタ
		 */
		public VehicleAvbResource(String trsp_op_strt_area_line_one_txt,
				String trsp_op_strt_area_cty_jis_cd,
				String trsp_op_date_trm_strt_date,
				String trsp_op_plan_date_trm_strt_time,
				String trsp_op_end_area_line_one_txt,
				String trsp_op_end_area_cty_jis_cd,
				String trsp_op_date_trm_end_date,
				String trsp_op_plan_date_trm_end_time,
				String clb_area_txt,
				String trms_of_clb_area_cd,
				String avb_date_cll_date,
				String avb_from_time_of_cll_time,
				String avb_to_time_of_cll_time,
				String delb_area_txt,
				String trms_of_delb_area_cd,
				String esti_del_date_prfm_dttm,
				String avb_from_time_of_del_time,
				String avb_to_time_of_del_time,
				String avb_load_cp_of_car_meas,
				String avb_load_vol_of_car_meas,
				String pcke_frm_cd,
				String avb_num_of_retb_cntn_of_car_quan,
				String trk_bed_stas_txt) {
			this.trsp_op_strt_area_line_one_txt = trsp_op_strt_area_line_one_txt;
			this.trsp_op_strt_area_cty_jis_cd = trsp_op_strt_area_cty_jis_cd;
			this.trsp_op_date_trm_strt_date = trsp_op_date_trm_strt_date;
			this.trsp_op_plan_date_trm_strt_time = trsp_op_plan_date_trm_strt_time;
			this.trsp_op_end_area_line_one_txt = trsp_op_end_area_line_one_txt;
			this.trsp_op_end_area_cty_jis_cd = trsp_op_end_area_cty_jis_cd;
			this.trsp_op_date_trm_end_date = trsp_op_date_trm_end_date;
			this.trsp_op_plan_date_trm_end_time = trsp_op_plan_date_trm_end_time;
			this.clb_area_txt = clb_area_txt;
			this.trms_of_clb_area_cd = trms_of_clb_area_cd;
			this.avb_date_cll_date = avb_date_cll_date;
			this.avb_from_time_of_cll_time = avb_from_time_of_cll_time;
			this.avb_to_time_of_cll_time = avb_to_time_of_cll_time;
			this.delb_area_txt = delb_area_txt;
			this.trms_of_delb_area_cd = trms_of_delb_area_cd;
			this.esti_del_date_prfm_dttm = esti_del_date_prfm_dttm;
			this.avb_from_time_of_del_time = avb_from_time_of_del_time;
			this.avb_to_time_of_del_time = avb_to_time_of_del_time;
			this.avb_load_cp_of_car_meas = avb_load_cp_of_car_meas;
			this.avb_load_vol_of_car_meas = avb_load_vol_of_car_meas;
			this.pcke_frm_cd = pcke_frm_cd;
			this.avb_num_of_retb_cntn_of_car_quan = avb_num_of_retb_cntn_of_car_quan;
			this.trk_bed_stas_txt = trk_bed_stas_txt;
		}

		@JsonProperty("trsp_op_strt_area_line_one_txt")
		private String trsp_op_strt_area_line_one_txt;
		@JsonProperty("trsp_op_strt_area_cty_jis_cd")
		private String trsp_op_strt_area_cty_jis_cd;
		@JsonProperty("trsp_op_date_trm_strt_date")
		private String trsp_op_date_trm_strt_date;
		@JsonProperty("trsp_op_plan_date_trm_strt_time")
		private String trsp_op_plan_date_trm_strt_time;
		@JsonProperty("trsp_op_end_area_line_one_txt")
		private String trsp_op_end_area_line_one_txt;
		@JsonProperty("trsp_op_end_area_cty_jis_cd")
		private String trsp_op_end_area_cty_jis_cd;
		@JsonProperty("trsp_op_date_trm_end_date")
		private String trsp_op_date_trm_end_date;
		@JsonProperty("trsp_op_plan_date_trm_end_time")
		private String trsp_op_plan_date_trm_end_time;
		@JsonProperty("clb_area_txt")
		private String clb_area_txt;
		@JsonProperty("trms_of_clb_area_cd")
		private String trms_of_clb_area_cd;
		@JsonProperty("avb_date_cll_date")
		private String avb_date_cll_date;
		@JsonProperty("avb_from_time_of_cll_time")
		private String avb_from_time_of_cll_time;
		@JsonProperty("avb_to_time_of_cll_time")
		private String avb_to_time_of_cll_time;
		@JsonProperty("delb_area_txt")
		private String delb_area_txt;
		@JsonProperty("trms_of_delb_area_cd")
		private String trms_of_delb_area_cd;
		@JsonProperty("esti_del_date_prfm_dttm")
		private String esti_del_date_prfm_dttm;
		@JsonProperty("avb_from_time_of_del_time")
		private String avb_from_time_of_del_time;
		@JsonProperty("avb_to_time_of_del_time")
		private String avb_to_time_of_del_time;
		@JsonProperty("avb_load_cp_of_car_meas")
		private String avb_load_cp_of_car_meas;
		@JsonProperty("avb_load_vol_of_car_meas")
		private String avb_load_vol_of_car_meas;
		@JsonProperty("pcke_frm_cd")
		private String pcke_frm_cd;
		@JsonProperty("avb_num_of_retb_cntn_of_car_quan")
		private String avb_num_of_retb_cntn_of_car_quan;
		@JsonProperty("trk_bed_stas_txt")
		private String trk_bed_stas_txt;
		@JsonProperty("cut_off_info")
		private List<CutOffInfo> cut_off_info = new ArrayList<>();
		@JsonProperty("free_time_info")
		private List<FreeTimeInfo> free_time_info = new ArrayList<>();

	}

	/**
	 * CutOffInfo（カットオフ情報）Model
	 */
	@Data
	public static class CutOffInfo {

		/**
		 * コンストラクタ
		 */
		public CutOffInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public CutOffInfo(String cut_off_time, String cut_off_fee) {
			this.cut_off_time = cut_off_time;
			this.cut_off_fee = cut_off_fee;
		}

		@JsonProperty("cut_off_time")
		private String cut_off_time;
		@JsonProperty("cut_off_fee")
		private String cut_off_fee;

	}

	/**
	 * FreeTimeInfo（フリータイム情報）Model
	 */
	@Data
	public static class FreeTimeInfo {

		/**
		 * コンストラクタ
		 */
		public FreeTimeInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public FreeTimeInfo(String free_time, String free_time_fee) {
			this.free_time = free_time;
			this.free_time_fee = free_time_fee;
		}

		@JsonProperty("free_time")
		private String free_time;
		@JsonProperty("free_time_fee")
		private String free_time_fee;

	}

	/**
	 * DrvInfo（運転手情報）Model
	 */
	@Data
	public static class DrvInfo {

		/**
		 * コンストラクタ
		 */
		public DrvInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public DrvInfo(String drv_ctrl_num_id,
				String drv_cls_of_drvg_license_cd,
				String drv_cls_of_fkl_license_exst_cd,
				String drv_rmk_about_drv_txt,
				String drv_cmpn_name_of_gtp_crtf_exst_txt) {
			this.drv_ctrl_num_id = drv_ctrl_num_id;
			this.drv_cls_of_drvg_license_cd = drv_cls_of_drvg_license_cd;
			this.drv_cls_of_fkl_license_exst_cd = drv_cls_of_fkl_license_exst_cd;
			this.drv_rmk_about_drv_txt = drv_rmk_about_drv_txt;
			this.drv_cmpn_name_of_gtp_crtf_exst_txt = drv_cmpn_name_of_gtp_crtf_exst_txt;

		}

		@JsonProperty("drv_ctrl_num_id")
		private String drv_ctrl_num_id;
		@JsonProperty("drv_cls_of_drvg_license_cd")
		private String drv_cls_of_drvg_license_cd;
		@JsonProperty("drv_cls_of_fkl_license_exst_cd")
		private String drv_cls_of_fkl_license_exst_cd;
		@JsonProperty("drv_rmk_about_drv_txt")
		private String drv_rmk_about_drv_txt;
		@JsonProperty("drv_cmpn_name_of_gtp_crtf_exst_txt")
		private String drv_cmpn_name_of_gtp_crtf_exst_txt;
		@JsonProperty("drv_avb_time")
		private List<DrvAvbTime> drv_avb_time = new ArrayList<DrvAvbTime>();
	}
	
	/**
	 *DrvAvbTime（運転手稼働可能時間）Model 
	 */
	@Data
	public static class DrvAvbTime {

		/**
		 * コンストラクタ
		 */
		public DrvAvbTime() {
		}

		/**
		 * コンストラクタ
		 */
		public DrvAvbTime(String drv_avb_from_date, String drv_avb_from_time_of_wrkg_time, String drv_avb_to_date,
				String drv_avb_to_time_of_wrkg_time, String drv_wrkg_trms_txt, String drv_frmr_optg_date,
				String drv_frmr_op_end_time) {
			this.drv_avb_from_date = drv_avb_from_date;
			this.drv_avb_from_time_of_wrkg_time = drv_avb_from_time_of_wrkg_time;
			this.drv_avb_to_date = drv_avb_to_date;
			this.drv_avb_to_time_of_wrkg_time = drv_avb_to_time_of_wrkg_time;
			this.drv_wrkg_trms_txt = drv_wrkg_trms_txt;
			this.drv_frmr_optg_date = drv_frmr_optg_date;
			this.drv_frmr_op_end_time = drv_frmr_op_end_time;

		}

		@JsonProperty("drv_avb_from_date")
		private String drv_avb_from_date;
		@JsonProperty("drv_avb_from_time_of_wrkg_time")
		private String drv_avb_from_time_of_wrkg_time;
		@JsonProperty("drv_avb_to_date")
		private String drv_avb_to_date;
		@JsonProperty("drv_avb_to_time_of_wrkg_time")
		private String drv_avb_to_time_of_wrkg_time;
		@JsonProperty("drv_wrkg_trms_txt")
		private String drv_wrkg_trms_txt;
		@JsonProperty("drv_frmr_optg_date")
		private String drv_frmr_optg_date;
		@JsonProperty("drv_frmr_op_end_time")
		private String drv_frmr_op_end_time;
	}

}
