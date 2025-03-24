package datahub.jsonData;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) //存在しないJSONデータがあれば無視する

/**
 *キャリア向け運行依頼案件用Model 
 */
public class TransportPlan {

	@JsonProperty("msg_info")
	private Msginfo msg_info;

	@JsonProperty("trsp_plan")
	private TrspPlan trsp_plan;

	@JsonProperty("trsp_plan_line_item")
	private List<TrspPlanLineItem> trsp_plan_line_item = new ArrayList<TrspPlanLineItem>();

	/**
	 * Msginfo（メッセージ情報）Model
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
	 * TrspPlan（運送計画）Model
	 */
	@Data
	public static class TrspPlan {

		/**
		 * コンストラクタ
		 */
		public TrspPlan() {
		}

		/**
		 * コンストラクタ
		 */
		public TrspPlan(String trsp_plan_stas_cd) {
			this.trsp_plan_stas_cd = trsp_plan_stas_cd;
		}

		@JsonProperty("trsp_plan_stas_cd")
		private String trsp_plan_stas_cd;
	}

	/**
	 * TrspPlanLineItem（運送計画明細）Model
	 */
	@Data
	public static class TrspPlanLineItem {

		@JsonProperty("trsp_isr")
		private TrspIsr trsp_isr;
		@JsonProperty("trsp_srvc")
		private TrspSrvc trsp_srvc;
		@JsonProperty("trsp_vehicle_trms")
		private TrspVehicleTrms trsp_vehicle_trms;
		@JsonProperty("del_info")
		private DelInfo del_info;
		@JsonProperty("cns")
		private Cns cns;
		@JsonProperty("cns_line_item")
		private List<CnsLineItem> cns_line_item = new ArrayList<CnsLineItem>();
		@JsonProperty("cnsg_prty")
		private CnsgPrty cnsg_prty;
		@JsonProperty("trsp_rqr_prty")
		private TrspRqrPrty trsp_rqr_prty;
		@JsonProperty("cnee_prty")
		private CneePrty cnee_prty;
		@JsonProperty("logs_srvc_prv")
		private LogsSrvcPrv logs_srvc_prv;
		@JsonProperty("road_carr")
		private RoadCarr road_carr;
		@JsonProperty("fret_clim_to_prty")
		private FretClimToPrty fret_clim_to_prty;
		@JsonProperty("ship_from_prty")
		private List<ShipFromPrty> ship_from_prty = new ArrayList<ShipFromPrty>();
		@JsonProperty("ship_to_prty")
		private List<ShipToPrty> ship_to_prty = new ArrayList<ShipToPrty>();

	}

	/**
	 * TrspIsr（運送依頼）Model
	 */
	@Data
	public static class TrspIsr {

		/**
		 * コンストラクタ
		 */
		public TrspIsr() {
		}

		/**
		 * コンストラクタ
		 */
		public TrspIsr(
				String trsp_instruction_id,
				String trsp_instruction_date_subm_dttm,
				String inv_num_id,
				String cmn_inv_num_id,
				String mix_load_num_id) {
			this.trsp_instruction_id = trsp_instruction_id;
			this.trsp_instruction_date_subm_dttm = trsp_instruction_date_subm_dttm;
			this.inv_num_id = inv_num_id;
			this.cmn_inv_num_id = cmn_inv_num_id;
			this.mix_load_num_id = mix_load_num_id;
		}

		@JsonProperty("trsp_instruction_id")
		private String trsp_instruction_id;
		@JsonProperty("trsp_instruction_date_subm_dttm")
		private String trsp_instruction_date_subm_dttm;
		@JsonProperty("inv_num_id")
		private String inv_num_id;
		@JsonProperty("cmn_inv_num_id")
		private String cmn_inv_num_id;
		@JsonProperty("mix_load_num_id")
		private String mix_load_num_id;
	}

	/**
	 * TrspSrvc（運送サービス）Model
	 */
	@Data
	public static class TrspSrvc {

		/**
		 * コンストラクタ
		 */
		public TrspSrvc() {
		}

		/**
		 * コンストラクタ
		 */
		public TrspSrvc(
				String service_no,
				String service_name,
				String service_strt_date,
				String service_strt_time,
				String service_end_date,
				String service_end_time,
				String freight_rate,
				String trsp_means_typ_cd,
				String trsp_srvc_typ_cd,
				String road_carr_srvc_typ_cd,
				String trsp_root_prio_cd,
				String car_cls_prio_cd,
				String cls_of_carg_in_srvc_rqrm_cd,
				String cls_of_pkg_up_srvc_rqrm_cd,
				String pyr_cls_srvc_rqrm_cd,
				String trms_of_mix_load_cnd_cd,
				String dsed_cll_from_date,
				String dsed_cll_to_date,
				String dsed_cll_from_time,
				String dsed_cll_to_time,
				String dsed_cll_time_trms_srvc_rqrm_cd,
				String aped_arr_from_date,
				String aped_arr_to_date,
				String aped_arr_from_time_prfm_dttm,
				String aped_arr_to_time_prfm_dttm,
				String aped_arr_time_trms_srvc_rqrm_cd,
				String trms_of_mix_load_txt,
				String trsp_srvc_note_one_txt,
				String trsp_srvc_note_two_txt) {
			this.service_no = service_no;
			this.service_name = service_name;
			this.service_strt_date = service_strt_date;
			this.service_strt_time = service_strt_time;
			this.service_end_date = service_end_date;
			this.service_end_time = service_end_time;
			this.freight_rate = freight_rate;
			this.trsp_means_typ_cd = trsp_means_typ_cd;
			this.trsp_srvc_typ_cd = trsp_srvc_typ_cd;
			this.road_carr_srvc_typ_cd = road_carr_srvc_typ_cd;
			this.trsp_root_prio_cd = trsp_root_prio_cd;
			this.car_cls_prio_cd = car_cls_prio_cd;
			this.cls_of_carg_in_srvc_rqrm_cd = cls_of_carg_in_srvc_rqrm_cd;
			this.cls_of_pkg_up_srvc_rqrm_cd = cls_of_pkg_up_srvc_rqrm_cd;
			this.pyr_cls_srvc_rqrm_cd = pyr_cls_srvc_rqrm_cd;
			this.trms_of_mix_load_cnd_cd = trms_of_mix_load_cnd_cd;
			this.dsed_cll_from_date = dsed_cll_from_date;
			this.dsed_cll_to_date = dsed_cll_to_date;
			this.dsed_cll_from_time = dsed_cll_from_time;
			this.dsed_cll_to_time = dsed_cll_to_time;
			this.dsed_cll_time_trms_srvc_rqrm_cd = dsed_cll_time_trms_srvc_rqrm_cd;
			this.aped_arr_from_date = aped_arr_from_date;
			this.aped_arr_to_date = aped_arr_to_date;
			this.aped_arr_from_time_prfm_dttm = aped_arr_from_time_prfm_dttm;
			this.aped_arr_to_time_prfm_dttm = aped_arr_to_time_prfm_dttm;
			this.aped_arr_time_trms_srvc_rqrm_cd = aped_arr_time_trms_srvc_rqrm_cd;
			this.trms_of_mix_load_txt = trms_of_mix_load_txt;
			this.trsp_srvc_note_one_txt = trsp_srvc_note_one_txt;
			this.trsp_srvc_note_two_txt = trsp_srvc_note_two_txt;
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
		@JsonProperty("trsp_means_typ_cd")
		private String trsp_means_typ_cd;
		@JsonProperty("trsp_srvc_typ_cd")
		private String trsp_srvc_typ_cd;
		@JsonProperty("road_carr_srvc_typ_cd")
		private String road_carr_srvc_typ_cd;
		@JsonProperty("trsp_root_prio_cd")
		private String trsp_root_prio_cd;
		@JsonProperty("car_cls_prio_cd")
		private String car_cls_prio_cd;
		@JsonProperty("cls_of_carg_in_srvc_rqrm_cd")
		private String cls_of_carg_in_srvc_rqrm_cd;
		@JsonProperty("cls_of_pkg_up_srvc_rqrm_cd")
		private String cls_of_pkg_up_srvc_rqrm_cd;
		@JsonProperty("pyr_cls_srvc_rqrm_cd")
		private String pyr_cls_srvc_rqrm_cd;
		@JsonProperty("trms_of_mix_load_cnd_cd")
		private String trms_of_mix_load_cnd_cd;
		@JsonProperty("dsed_cll_from_date")
		private String dsed_cll_from_date;
		@JsonProperty("dsed_cll_to_date")
		private String dsed_cll_to_date;
		@JsonProperty("dsed_cll_from_time")
		private String dsed_cll_from_time;
		@JsonProperty("dsed_cll_to_time")
		private String dsed_cll_to_time;
		@JsonProperty("dsed_cll_time_trms_srvc_rqrm_cd")
		private String dsed_cll_time_trms_srvc_rqrm_cd;
		@JsonProperty("aped_arr_from_date")
		private String aped_arr_from_date;
		@JsonProperty("aped_arr_to_date")
		private String aped_arr_to_date;
		@JsonProperty("aped_arr_from_time_prfm_dttm")
		private String aped_arr_from_time_prfm_dttm;
		@JsonProperty("aped_arr_to_time_prfm_dttm")
		private String aped_arr_to_time_prfm_dttm;
		@JsonProperty("aped_arr_time_trms_srvc_rqrm_cd")
		private String aped_arr_time_trms_srvc_rqrm_cd;
		@JsonProperty("trms_of_mix_load_txt")
		private String trms_of_mix_load_txt;
		@JsonProperty("trsp_srvc_note_one_txt")
		private String trsp_srvc_note_one_txt;
		@JsonProperty("trsp_srvc_note_two_txt")
		private String trsp_srvc_note_two_txt;
	}

	/**
	 * TrspVehicleTrms（運送車輌条件）Model
	 */
	@Data
	public static class TrspVehicleTrms {

		/**
		 * コンストラクタ
		 */
		public TrspVehicleTrms() {
		}

		/**
		 * コンストラクタ
		 */
		public TrspVehicleTrms(
				String car_cls_of_size_cd,
				String car_cls_of_shp_cd,
				String car_cls_of_tlg_lftr_exst_cd,
				String car_cls_of_wing_body_exst_cd,
				String car_cls_of_rfg_exst_cd,
				String trms_of_lwr_tmp_meas,
				String trms_of_upp_tmp_meas,
				String car_cls_of_crn_exst_cd,
				String car_rmk_about_eqpm_txt) {
			this.car_cls_of_size_cd = car_cls_of_size_cd;
			this.car_cls_of_shp_cd = car_cls_of_shp_cd;
			this.car_cls_of_tlg_lftr_exst_cd = car_cls_of_tlg_lftr_exst_cd;
			this.car_cls_of_wing_body_exst_cd = car_cls_of_wing_body_exst_cd;
			this.car_cls_of_rfg_exst_cd = car_cls_of_rfg_exst_cd;
			this.trms_of_lwr_tmp_meas = trms_of_lwr_tmp_meas;
			this.trms_of_upp_tmp_meas = trms_of_upp_tmp_meas;
			this.car_cls_of_crn_exst_cd = car_cls_of_crn_exst_cd;
			this.car_rmk_about_eqpm_txt = car_rmk_about_eqpm_txt;
		}

		@JsonProperty("car_cls_of_size_cd")
		private String car_cls_of_size_cd;
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
	}

	/**
	 * DelInfo（納品情報）Model
	 */
	@Data
	public static class DelInfo {

		/**
		 * コンストラクタ
		 */
		public DelInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public DelInfo(
				String del_note_id,
				String shpm_num_id,
				String rced_ord_num_id) {
			this.del_note_id = del_note_id;
			this.shpm_num_id = shpm_num_id;
			this.rced_ord_num_id = rced_ord_num_id;
		}

		@JsonProperty("del_note_id")
		private String del_note_id;
		@JsonProperty("shpm_num_id")
		private String shpm_num_id;
		@JsonProperty("rced_ord_num_id")
		private String rced_ord_num_id;
	}

	/**
	 * Cns（荷送人）Model
	 */
	@Data
	public static class Cns {
		
		/**
		 * コンストラクタ
		 */
		public Cns() {
		}

		/**
		 * コンストラクタ
		 */
		public Cns(
				String istd_totl_pcks_quan,
				String num_unt_cd,
				String istd_totl_weig_meas,
				String weig_unt_cd,
				String istd_totl_vol_meas,
				String vol_unt_cd,
				String istd_totl_untl_quan) {
			this.istd_totl_pcks_quan = istd_totl_pcks_quan;
			this.num_unt_cd = num_unt_cd;
			this.istd_totl_weig_meas = istd_totl_weig_meas;
			this.weig_unt_cd = weig_unt_cd;
			this.istd_totl_vol_meas = istd_totl_vol_meas;
			this.vol_unt_cd = vol_unt_cd;
			this.istd_totl_untl_quan = istd_totl_untl_quan;
		}

		@JsonProperty("istd_totl_pcks_quan")
		private String istd_totl_pcks_quan;
		@JsonProperty("num_unt_cd")
		private String num_unt_cd;
		@JsonProperty("istd_totl_weig_meas")
		private String istd_totl_weig_meas;
		@JsonProperty("weig_unt_cd")
		private String weig_unt_cd;
		@JsonProperty("istd_totl_vol_meas")
		private String istd_totl_vol_meas;
		@JsonProperty("vol_unt_cd")
		private String vol_unt_cd;
		@JsonProperty("istd_totl_untl_quan")
		private String istd_totl_untl_quan;
	}

	/**
	 * CnsLineItem（貨物明細）Model
	 */
	@Data
	public static class CnsLineItem {

		/**
		 * コンストラクタ
		 */
		public CnsLineItem() {
		}

		/**
		 * コンストラクタ
		 */
		public CnsLineItem(
				String line_item_num_id,
				String sev_ord_num_id,
				String cnsg_crg_item_num_id,
				String buy_assi_item_cd,
				String sell_assi_item_cd,
				String wrhs_assi_item_cd,
				String item_name_txt,
				String gods_idcs_in_ots_pcke_name_txt,
				String num_of_istd_untl_quan,
				String num_of_istd_quan,
				String sev_num_unt_cd,
				String istd_pcke_weig_meas,
				String sev_weig_unt_cd,
				String istd_pcke_vol_meas,
				String sev_vol_unt_cd,
				String istd_quan_meas,
				String cnte_num_unt_cd,
				String dcpv_trpn_pckg_txt,
				String pcke_frm_cd,
				String pcke_frm_name_cd,
				String crg_hnd_trms_spcl_isrs_txt,
				String glb_retb_asse_id,
				String totl_rti_quan_quan,
				String chrg_of_pcke_ctrl_num_unt_amnt) {
			this.line_item_num_id = line_item_num_id;
			this.sev_ord_num_id = sev_ord_num_id;
			this.cnsg_crg_item_num_id = cnsg_crg_item_num_id;
			this.buy_assi_item_cd = buy_assi_item_cd;
			this.sell_assi_item_cd = sell_assi_item_cd;
			this.wrhs_assi_item_cd = wrhs_assi_item_cd;
			this.item_name_txt = item_name_txt;
			this.gods_idcs_in_ots_pcke_name_txt = gods_idcs_in_ots_pcke_name_txt;
			this.num_of_istd_untl_quan = num_of_istd_untl_quan;
			this.num_of_istd_quan = num_of_istd_quan;
			this.sev_num_unt_cd = sev_num_unt_cd;
			this.istd_pcke_weig_meas = istd_pcke_weig_meas;
			this.sev_weig_unt_cd = sev_weig_unt_cd;
			this.istd_pcke_vol_meas = istd_pcke_vol_meas;
			this.sev_vol_unt_cd = sev_vol_unt_cd;
			this.istd_quan_meas = istd_quan_meas;
			this.cnte_num_unt_cd = cnte_num_unt_cd;
			this.dcpv_trpn_pckg_txt = dcpv_trpn_pckg_txt;
			this.pcke_frm_cd = pcke_frm_cd;
			this.pcke_frm_name_cd = pcke_frm_name_cd;
			this.crg_hnd_trms_spcl_isrs_txt = crg_hnd_trms_spcl_isrs_txt;
			this.glb_retb_asse_id = glb_retb_asse_id;
			this.totl_rti_quan_quan = totl_rti_quan_quan;
			this.chrg_of_pcke_ctrl_num_unt_amnt = chrg_of_pcke_ctrl_num_unt_amnt;
		}

		@JsonProperty("line_item_num_id")
		private String line_item_num_id;
		@JsonProperty("sev_ord_num_id")
		private String sev_ord_num_id;
		@JsonProperty("cnsg_crg_item_num_id")
		private String cnsg_crg_item_num_id;
		@JsonProperty("buy_assi_item_cd")
		private String buy_assi_item_cd;
		@JsonProperty("sell_assi_item_cd")
		private String sell_assi_item_cd;
		@JsonProperty("wrhs_assi_item_cd")
		private String wrhs_assi_item_cd;
		@JsonProperty("item_name_txt")
		private String item_name_txt;
		@JsonProperty("gods_idcs_in_ots_pcke_name_txt")
		private String gods_idcs_in_ots_pcke_name_txt;
		@JsonProperty("num_of_istd_untl_quan")
		private String num_of_istd_untl_quan;
		@JsonProperty("num_of_istd_quan")
		private String num_of_istd_quan;
		@JsonProperty("sev_num_unt_cd")
		private String sev_num_unt_cd;
		@JsonProperty("istd_pcke_weig_meas")
		private String istd_pcke_weig_meas;
		@JsonProperty("sev_weig_unt_cd")
		private String sev_weig_unt_cd;
		@JsonProperty("istd_pcke_vol_meas")
		private String istd_pcke_vol_meas;
		@JsonProperty("sev_vol_unt_cd")
		private String sev_vol_unt_cd;
		@JsonProperty("istd_quan_meas")
		private String istd_quan_meas;
		@JsonProperty("cnte_num_unt_cd")
		private String cnte_num_unt_cd;
		@JsonProperty("dcpv_trpn_pckg_txt")
		private String dcpv_trpn_pckg_txt;
		@JsonProperty("pcke_frm_cd")
		private String pcke_frm_cd;
		@JsonProperty("pcke_frm_name_cd")
		private String pcke_frm_name_cd;
		@JsonProperty("crg_hnd_trms_spcl_isrs_txt")
		private String crg_hnd_trms_spcl_isrs_txt;
		@JsonProperty("glb_retb_asse_id")
		private String glb_retb_asse_id;
		@JsonProperty("totl_rti_quan_quan")
		private String totl_rti_quan_quan;
		@JsonProperty("chrg_of_pcke_ctrl_num_unt_amnt")
		private String chrg_of_pcke_ctrl_num_unt_amnt;
	}

	/**
	 * CnsgPrty（荷送人）Model
	 */
	@Data
	public static class CnsgPrty {

		/**
		 * コンストラクタ
		 */
		public CnsgPrty() {
		}

		/**
		 * コンストラクタ
		 */
		public CnsgPrty(
				String cnsg_prty_head_off_id,
				String cnsg_prty_brnc_off_id,
				String cnsg_prty_name_txt,
				String cnsg_sct_sped_org_id,
				String cnsg_sct_sped_org_name_txt,
				String cnsg_tel_cmm_cmp_num_txt,
				String cnsg_pstl_adrs_line_one_txt,
				String cnsg_pstc_cd) {
			this.cnsg_prty_head_off_id = cnsg_prty_head_off_id;
			this.cnsg_prty_brnc_off_id = cnsg_prty_brnc_off_id;
			this.cnsg_prty_name_txt = cnsg_prty_name_txt;
			this.cnsg_sct_sped_org_id = cnsg_sct_sped_org_id;
			this.cnsg_sct_sped_org_name_txt = cnsg_sct_sped_org_name_txt;
			this.cnsg_tel_cmm_cmp_num_txt = cnsg_tel_cmm_cmp_num_txt;
			this.cnsg_pstl_adrs_line_one_txt = cnsg_pstl_adrs_line_one_txt;
			this.cnsg_pstc_cd = cnsg_pstc_cd;
		}

		@JsonProperty("cnsg_prty_head_off_id")
		private String cnsg_prty_head_off_id;
		@JsonProperty("cnsg_prty_brnc_off_id")
		private String cnsg_prty_brnc_off_id;
		@JsonProperty("cnsg_prty_name_txt")
		private String cnsg_prty_name_txt;
		@JsonProperty("cnsg_sct_sped_org_id")
		private String cnsg_sct_sped_org_id;
		@JsonProperty("cnsg_sct_sped_org_name_txt")
		private String cnsg_sct_sped_org_name_txt;
		@JsonProperty("cnsg_tel_cmm_cmp_num_txt")
		private String cnsg_tel_cmm_cmp_num_txt;
		@JsonProperty("cnsg_pstl_adrs_line_one_txt")
		private String cnsg_pstl_adrs_line_one_txt;
		@JsonProperty("cnsg_pstc_cd")
		private String cnsg_pstc_cd;
	}

	/**
	 * TrspRqrPrty（運送依頼者）Model
	 */
	@Data
	public static class TrspRqrPrty {

		/**
		 * コンストラクタ
		 */
		public TrspRqrPrty() {
		}

		/**
		 * コンストラクタ
		 */
		public TrspRqrPrty(
				String trsp_rqr_prty_head_off_id,
				String trsp_rqr_prty_brnc_off_id,
				String trsp_rqr_prty_name_txt,
				String trsp_rqr_sct_sped_org_id,
				String trsp_rqr_sct_sped_org_name_txt,
				String trsp_rqr_sct_tel_cmm_cmp_num_txt,
				String trsp_rqr_pstl_adrs_line_one_txt,
				String trsp_rqr_pstc_cd) {
			this.trsp_rqr_prty_head_off_id = trsp_rqr_prty_head_off_id;
			this.trsp_rqr_prty_brnc_off_id = trsp_rqr_prty_brnc_off_id;
			this.trsp_rqr_prty_name_txt = trsp_rqr_prty_name_txt;
			this.trsp_rqr_sct_sped_org_id = trsp_rqr_sct_sped_org_id;
			this.trsp_rqr_sct_sped_org_name_txt = trsp_rqr_sct_sped_org_name_txt;
			this.trsp_rqr_sct_tel_cmm_cmp_num_txt = trsp_rqr_sct_tel_cmm_cmp_num_txt;
			this.trsp_rqr_pstl_adrs_line_one_txt = trsp_rqr_pstl_adrs_line_one_txt;
			this.trsp_rqr_pstc_cd = trsp_rqr_pstc_cd;
		}

		@JsonProperty("trsp_rqr_prty_head_off_id")
		private String trsp_rqr_prty_head_off_id;
		@JsonProperty("trsp_rqr_prty_brnc_off_id")
		private String trsp_rqr_prty_brnc_off_id;
		@JsonProperty("trsp_rqr_prty_name_txt")
		private String trsp_rqr_prty_name_txt;
		@JsonProperty("trsp_rqr_sct_sped_org_id")
		private String trsp_rqr_sct_sped_org_id;
		@JsonProperty("trsp_rqr_sct_sped_org_name_txt")
		private String trsp_rqr_sct_sped_org_name_txt;
		@JsonProperty("trsp_rqr_sct_tel_cmm_cmp_num_txt")
		private String trsp_rqr_sct_tel_cmm_cmp_num_txt;
		@JsonProperty("trsp_rqr_pstl_adrs_line_one_txt")
		private String trsp_rqr_pstl_adrs_line_one_txt;
		@JsonProperty("trsp_rqr_pstc_cd")
		private String trsp_rqr_pstc_cd;
	}

	/**
	 * CneePrty（荷受人）Model
	 */
	@Data
	public static class CneePrty {

		/**
		 * コンストラクタ
		 */
		public CneePrty() {
		}

		/**
		 * コンストラクタ
		 */
		public CneePrty(
				String cnee_prty_head_off_id,
				String cnee_prty_brnc_off_id,
				String cnee_prty_name_txt,
				String cnee_sct_id,
				String cnee_sct_name_txt,
				String cnee_prim_cnt_pers_name_txt,
				String cnee_tel_cmm_cmp_num_txt,
				String cnee_pstl_adrs_line_one_txt,
				String cnee_pstc_cd) {
			this.cnee_prty_head_off_id = cnee_prty_head_off_id;
			this.cnee_prty_brnc_off_id = cnee_prty_brnc_off_id;
			this.cnee_prty_name_txt = cnee_prty_name_txt;
			this.cnee_sct_id = cnee_sct_id;
			this.cnee_sct_name_txt = cnee_sct_name_txt;
			this.cnee_prim_cnt_pers_name_txt = cnee_prim_cnt_pers_name_txt;
			this.cnee_tel_cmm_cmp_num_txt = cnee_tel_cmm_cmp_num_txt;
			this.cnee_pstl_adrs_line_one_txt = cnee_pstl_adrs_line_one_txt;
			this.cnee_pstc_cd = cnee_pstc_cd;
		}

		@JsonProperty("cnee_prty_head_off_id")
		private String cnee_prty_head_off_id;
		@JsonProperty("cnee_prty_brnc_off_id")
		private String cnee_prty_brnc_off_id;
		@JsonProperty("cnee_prty_name_txt")
		private String cnee_prty_name_txt;
		@JsonProperty("cnee_sct_id")
		private String cnee_sct_id;
		@JsonProperty("cnee_sct_name_txt")
		private String cnee_sct_name_txt;
		@JsonProperty("cnee_prim_cnt_pers_name_txt")
		private String cnee_prim_cnt_pers_name_txt;
		@JsonProperty("cnee_tel_cmm_cmp_num_txt")
		private String cnee_tel_cmm_cmp_num_txt;
		@JsonProperty("cnee_pstl_adrs_line_one_txt")
		private String cnee_pstl_adrs_line_one_txt;
		@JsonProperty("cnee_pstc_cd")
		private String cnee_pstc_cd;
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
		public LogsSrvcPrv(
				String logs_srvc_prv_prty_head_off_id,
				String logs_srvc_prv_prty_brnc_off_id,
				String logs_srvc_prv_prty_name_txt,
				String logs_srvc_prv_sct_sped_org_id,
				String logs_srvc_prv_sct_sped_org_name_txt,
				String logs_srvc_prv_sct_prim_cnt_pers_name_txt,
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
	 * RoadCarr(運送事業者）Model
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
	 * FretClimToPrty（運賃請求先）Model
	 */
	@Data
	public static class FretClimToPrty {

		/**
		 * コンストラクタ
		 */
		public FretClimToPrty() {
		}

		/**
		 * コンストラクタ
		 */
		public FretClimToPrty(
				String fret_clim_to_prty_head_off_id,
				String fret_clim_to_prty_brnc_off_id,
				String fret_clim_to_prty_name_txt,
				String fret_clim_to_sct_sped_org_id,
				String fret_clim_to_sct_sped_org_name_txt) {
			this.fret_clim_to_prty_head_off_id = fret_clim_to_prty_head_off_id;
			this.fret_clim_to_prty_brnc_off_id = fret_clim_to_prty_brnc_off_id;
			this.fret_clim_to_prty_name_txt = fret_clim_to_prty_name_txt;
			this.fret_clim_to_sct_sped_org_id = fret_clim_to_sct_sped_org_id;
			this.fret_clim_to_sct_sped_org_name_txt = fret_clim_to_sct_sped_org_name_txt;
		}

		@JsonProperty("fret_clim_to_prty_head_off_id")
		private String fret_clim_to_prty_head_off_id;
		@JsonProperty("fret_clim_to_prty_brnc_off_id")
		private String fret_clim_to_prty_brnc_off_id;
		@JsonProperty("fret_clim_to_prty_name_txt")
		private String fret_clim_to_prty_name_txt;
		@JsonProperty("fret_clim_to_sct_sped_org_id")
		private String fret_clim_to_sct_sped_org_id;
		@JsonProperty("fret_clim_to_sct_sped_org_name_txt")
		private String fret_clim_to_sct_sped_org_name_txt;
	}

	/**
	 * ShipFromPrty（出荷場所）Model
	 */
	@Data
	public static class ShipFromPrty {

		/**
		 * コンストラクタ
		 */
		public ShipFromPrty() {
		}

		/**
		 * コンストラクタ
		 */
		public ShipFromPrty(
				String ship_from_prty_head_off_id,
				String ship_from_prty_brnc_off_id,
				String ship_from_prty_name_txt,
				String ship_from_sct_id,
				String ship_from_sct_name_txt,
				String ship_from_tel_cmm_cmp_num_txt,
				String ship_from_pstl_adrs_cty_id,
				String ship_from_pstl_adrs_id,
				String ship_from_pstl_adrs_line_one_txt,
				String ship_from_pstc_cd,
				String plc_cd_prty_id,
				String gln_prty_id,
				String jpn_uplc_cd,
				String jpn_van_srvc_cd,
				String jpn_van_vans_cd,
				ShipFromPrtyRqrm ship_from_prty_rqrm,
				List<CutOffInfo> cut_off_info) {
			this.ship_from_prty_head_off_id = ship_from_prty_head_off_id;
			this.ship_from_prty_brnc_off_id = ship_from_prty_brnc_off_id;
			this.ship_from_prty_name_txt = ship_from_prty_name_txt;
			this.ship_from_sct_id = ship_from_sct_id;
			this.ship_from_sct_name_txt = ship_from_sct_name_txt;
			this.ship_from_tel_cmm_cmp_num_txt = ship_from_tel_cmm_cmp_num_txt;
			this.ship_from_pstl_adrs_cty_id = ship_from_pstl_adrs_cty_id;
			this.ship_from_pstl_adrs_id = ship_from_pstl_adrs_id;
			this.ship_from_pstl_adrs_line_one_txt = ship_from_pstl_adrs_line_one_txt;
			this.ship_from_pstc_cd = ship_from_pstc_cd;
			this.plc_cd_prty_id = plc_cd_prty_id;
			this.gln_prty_id = gln_prty_id;
			this.jpn_uplc_cd = jpn_uplc_cd;
			this.jpn_van_srvc_cd = jpn_van_srvc_cd;
			this.jpn_van_vans_cd = jpn_van_vans_cd;
			this.ship_from_prty_rqrm = ship_from_prty_rqrm;
			this.cut_off_info = cut_off_info;
		}

		@JsonProperty("ship_from_prty_head_off_id")
		private String ship_from_prty_head_off_id;
		@JsonProperty("ship_from_prty_brnc_off_id")
		private String ship_from_prty_brnc_off_id;
		@JsonProperty("ship_from_prty_name_txt")
		private String ship_from_prty_name_txt;
		@JsonProperty("ship_from_sct_id")
		private String ship_from_sct_id;
		@JsonProperty("ship_from_sct_name_txt")
		private String ship_from_sct_name_txt;
		@JsonProperty("ship_from_tel_cmm_cmp_num_txt")
		private String ship_from_tel_cmm_cmp_num_txt;
		@JsonProperty("ship_from_pstl_adrs_cty_id")
		private String ship_from_pstl_adrs_cty_id;
		@JsonProperty("ship_from_pstl_adrs_id")
		private String ship_from_pstl_adrs_id;
		@JsonProperty("ship_from_pstl_adrs_line_one_txt")
		private String ship_from_pstl_adrs_line_one_txt;
		@JsonProperty("ship_from_pstc_cd")
		private String ship_from_pstc_cd;
		@JsonProperty("plc_cd_prty_id")
		private String plc_cd_prty_id;
		@JsonProperty("gln_prty_id")
		private String gln_prty_id;
		@JsonProperty("jpn_uplc_cd")
		private String jpn_uplc_cd;
		@JsonProperty("jpn_van_srvc_cd")
		private String jpn_van_srvc_cd;
		@JsonProperty("jpn_van_vans_cd")
		private String jpn_van_vans_cd;
		@JsonProperty("ship_from_prty_rqrm")
		private ShipFromPrtyRqrm ship_from_prty_rqrm;
		@JsonProperty("cut_off_info")
		private List<CutOffInfo> cut_off_info = new ArrayList<CutOffInfo>();
	}

	/**
	 * ShipToPrty（荷届先）Model
	 */
	@Data
	public static class ShipToPrty {

		/**
		 * コンストラクタ
		 */
		public ShipToPrty() {
		}

		/**
		 * コンストラクタ
		 */
		public ShipToPrty(
				String ship_to_prty_head_off_id,
				String ship_to_prty_brnc_off_id,
				String ship_to_prty_name_txt,
				String ship_to_sct_id,
				String ship_to_sct_name_txt,
				String ship_to_prim_cnt_id,
				String ship_to_prim_cnt_pers_name_txt,
				String ship_to_tel_cmm_cmp_num_txt,
				String ship_to_pstl_adrs_cty_id,
				String ship_to_pstl_adrs_id,
				String ship_to_pstl_adrs_line_one_txt,
				String ship_to_pstc_cd,
				String plc_cd_prty_id,
				String gln_prty_id,
				String jpn_uplc_cd,
				String jpn_van_srvc_cd,
				String jpn_van_vans_cd,
				List<FreeTimeInfo> free_time_info,
				ShipToPrtyRqrm ship_to_prty_rqrm) {
			this.ship_to_prty_head_off_id = ship_to_prty_head_off_id;
			this.ship_to_prty_brnc_off_id = ship_to_prty_brnc_off_id;
			this.ship_to_prty_name_txt = ship_to_prty_name_txt;
			this.ship_to_sct_id = ship_to_sct_id;
			this.ship_to_sct_name_txt = ship_to_sct_name_txt;
			this.ship_to_prim_cnt_id = ship_to_prim_cnt_id;
			this.ship_to_prim_cnt_pers_name_txt = ship_to_prim_cnt_pers_name_txt;
			this.ship_to_tel_cmm_cmp_num_txt = ship_to_tel_cmm_cmp_num_txt;
			this.ship_to_pstl_adrs_cty_id = ship_to_pstl_adrs_cty_id;
			this.ship_to_pstl_adrs_id = ship_to_pstl_adrs_id;
			this.ship_to_pstl_adrs_line_one_txt = ship_to_pstl_adrs_line_one_txt;
			this.ship_to_pstc_cd = ship_to_pstc_cd;
			this.plc_cd_prty_id = plc_cd_prty_id;
			this.gln_prty_id = gln_prty_id;
			this.jpn_uplc_cd = jpn_uplc_cd;
			this.jpn_van_srvc_cd = jpn_van_srvc_cd;
			this.jpn_van_vans_cd = jpn_van_vans_cd;
			this.free_time_info = free_time_info;
			this.ship_to_prty_rqrm = ship_to_prty_rqrm;
		}

		@JsonProperty("ship_to_prty_head_off_id")
		private String ship_to_prty_head_off_id;
		@JsonProperty("ship_to_prty_brnc_off_id")
		private String ship_to_prty_brnc_off_id;
		@JsonProperty("ship_to_prty_name_txt")
		private String ship_to_prty_name_txt;
		@JsonProperty("ship_to_sct_id")
		private String ship_to_sct_id;
		@JsonProperty("ship_to_sct_name_txt")
		private String ship_to_sct_name_txt;
		@JsonProperty("ship_to_prim_cnt_id")
		private String ship_to_prim_cnt_id;
		@JsonProperty("ship_to_prim_cnt_pers_name_txt")
		private String ship_to_prim_cnt_pers_name_txt;
		@JsonProperty("ship_to_tel_cmm_cmp_num_txt")
		private String ship_to_tel_cmm_cmp_num_txt;
		@JsonProperty("ship_to_pstl_adrs_cty_id")
		private String ship_to_pstl_adrs_cty_id;
		@JsonProperty("ship_to_pstl_adrs_id")
		private String ship_to_pstl_adrs_id;
		@JsonProperty("ship_to_pstl_adrs_line_one_txt")
		private String ship_to_pstl_adrs_line_one_txt;
		@JsonProperty("ship_to_pstc_cd")
		private String ship_to_pstc_cd;
		@JsonProperty("plc_cd_prty_id")
		private String plc_cd_prty_id;
		@JsonProperty("gln_prty_id")
		private String gln_prty_id;
		@JsonProperty("jpn_uplc_cd")
		private String jpn_uplc_cd;
		@JsonProperty("jpn_van_srvc_cd")
		private String jpn_van_srvc_cd;
		@JsonProperty("jpn_van_vans_cd")
		private String jpn_van_vans_cd;
		@JsonProperty("free_time_info")
		private List<FreeTimeInfo> free_time_info = new ArrayList<FreeTimeInfo>();
		@JsonProperty("ship_to_prty_rqrm")
		private ShipToPrtyRqrm ship_to_prty_rqrm;
	}

	/**
	 * ShipFromPrtyRqrm（出荷場所要件）Model
	 */
	@Data
	public static class ShipFromPrtyRqrm {

		/**
		 * コンストラクタ
		 */
		public ShipFromPrtyRqrm() {
		}
		
		/**
		 * コンストラクタ
		 */
		public ShipFromPrtyRqrm(
				String trms_of_car_size_cd,
				String trms_of_car_hght_meas,
				String trms_of_gtp_cert_txt,
				String trms_of_cll_txt,
				String trms_of_gods_hnd_txt,
				String anc_wrk_of_cll_txt,
				String spcl_wrk_txt) {
			this.trms_of_car_size_cd = trms_of_car_size_cd;
			this.trms_of_car_hght_meas = trms_of_car_hght_meas;
			this.trms_of_gtp_cert_txt = trms_of_gtp_cert_txt;
			this.trms_of_cll_txt = trms_of_cll_txt;
			this.trms_of_gods_hnd_txt = trms_of_gods_hnd_txt;
			this.anc_wrk_of_cll_txt = anc_wrk_of_cll_txt;
			this.spcl_wrk_txt = spcl_wrk_txt;
		}

		@JsonProperty("trms_of_car_size_cd")
		private String trms_of_car_size_cd;
		@JsonProperty("trms_of_car_hght_meas")
		private String trms_of_car_hght_meas;
		@JsonProperty("trms_of_gtp_cert_txt")
		private String trms_of_gtp_cert_txt;
		@JsonProperty("trms_of_cll_txt")
		private String trms_of_cll_txt;
		@JsonProperty("trms_of_gods_hnd_txt")
		private String trms_of_gods_hnd_txt;
		@JsonProperty("anc_wrk_of_cll_txt")
		private String anc_wrk_of_cll_txt;
		@JsonProperty("spcl_wrk_txt")
		private String spcl_wrk_txt;
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
		public CutOffInfo(
				String cut_off_time,
				String cut_off_fee) {
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
		public FreeTimeInfo(
				String free_time,
				String free_time_fee) {
			this.free_time = free_time;
			this.free_time_fee = free_time_fee;
		}

		@JsonProperty("free_time")
		private String free_time;
		@JsonProperty("free_time_fee")
		private String free_time_fee;
	}

	/**
	 * ShipToPrtyRqrm（荷届先要件）Model
	 */
	@Data
	public static class ShipToPrtyRqrm {

		/**
		 * コンストラクタ
		 */
		public ShipToPrtyRqrm() {
		}

		/**
		 * コンストラクタ
		 */
		public ShipToPrtyRqrm(
				String trms_of_car_size_cd,
				String trms_of_car_hght_meas,
				String trms_of_gtp_cert_txt,
				String trms_of_del_txt,
				String trms_of_gods_hnd_txt,
				String anc_wrk_of_del_txt,
				String spcl_wrk_txt) {
			this.trms_of_car_size_cd = trms_of_car_size_cd;
			this.trms_of_car_hght_meas = trms_of_car_hght_meas;
			this.trms_of_gtp_cert_txt = trms_of_gtp_cert_txt;
			this.trms_of_del_txt = trms_of_del_txt;
			this.trms_of_gods_hnd_txt = trms_of_gods_hnd_txt;
			this.anc_wrk_of_del_txt = anc_wrk_of_del_txt;
			this.spcl_wrk_txt = spcl_wrk_txt;
		}

		@JsonProperty("trms_of_car_size_cd")
		private String trms_of_car_size_cd;
		@JsonProperty("trms_of_car_hght_meas")
		private String trms_of_car_hght_meas;
		@JsonProperty("trms_of_gtp_cert_txt")
		private String trms_of_gtp_cert_txt;
		@JsonProperty("trms_of_del_txt")
		private String trms_of_del_txt;
		@JsonProperty("trms_of_gods_hnd_txt")
		private String trms_of_gods_hnd_txt;
		@JsonProperty("anc_wrk_of_del_txt")
		private String anc_wrk_of_del_txt;
		@JsonProperty("spcl_wrk_txt")
		private String spcl_wrk_txt;
	}

}
