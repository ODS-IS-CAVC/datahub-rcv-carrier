package datahub.jsonData;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 車輛マスタ用Model
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) //存在しないJSONデータがあれば無視する
public class Vehicle {

	@JsonProperty("vehicle_info")
	private VehicleInfo vehicle_info;
	@JsonProperty("motas_info")
	private MotasInfo motas_info;
	@JsonProperty("vehicle_details")
	private VehicleDetails vehicle_details;
	@JsonProperty("max_carrying_capacity")
	private List<MaxCarryingCapacity> max_carrying_capacity = new ArrayList<MaxCarryingCapacity>();
	@JsonProperty("hazardous_material_info")
	private List<HazardousMaterialInfo> hazardous_material_info = new ArrayList<HazardousMaterialInfo>();

	/**
	 * VehicleInfo（車輌情報）Model
	 */
	@Data
	public static class VehicleInfo {

		/**
		 * コンストラクタ
		 */
		public VehicleInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public VehicleInfo(String registration_number, String giai, String registration_transport_office_name,
				String registration_vehicle_type, String registration_vehicle_use, String registration_vehicle_id,
				String chassis_number, String vehicle_id, String operator_corporate_number,
				String operator_business_code, String owner_corporate_number, String owner_business_code,
				String vehicle_type, String hazardous_material_vehicle_type, String tractor, String trailer) {
			this.registration_number = registration_number;
			this.giai = giai;
			this.registration_transport_office_name = registration_transport_office_name;
			this.registration_vehicle_type = registration_vehicle_type;
			this.registration_vehicle_use = registration_vehicle_use;
			this.registration_vehicle_id = registration_vehicle_id;
			this.chassis_number = chassis_number;
			this.vehicle_id = vehicle_id;
			this.operator_corporate_number = operator_corporate_number;
			this.operator_business_code = operator_business_code;
			this.owner_corporate_number = owner_corporate_number;
			this.owner_business_code = owner_business_code;
			this.vehicle_type = vehicle_type;
			this.hazardous_material_vehicle_type = hazardous_material_vehicle_type;
			this.tractor = tractor;
			this.trailer = trailer;
		}

		@JsonProperty("registration_number")
		private String registration_number;
		@JsonProperty("giai")
		private String giai;
		@JsonProperty("registration_transport_office_name")
		private String registration_transport_office_name;
		@JsonProperty("registration_vehicle_type")
		private String registration_vehicle_type;
		@JsonProperty("registration_vehicle_use")
		private String registration_vehicle_use;
		@JsonProperty("registration_vehicle_id")
		private String registration_vehicle_id;
		@JsonProperty("chassis_number")
		private String chassis_number;
		@JsonProperty("vehicle_id")
		private String vehicle_id;
		@JsonProperty("operator_corporate_number")
		private String operator_corporate_number;
		@JsonProperty("operator_business_code")
		private String operator_business_code;
		@JsonProperty("owner_corporate_number")
		private String owner_corporate_number;
		@JsonProperty("owner_business_code")
		private String owner_business_code;
		@JsonProperty("vehicle_type")
		private String vehicle_type;
		@JsonProperty("hazardous_material_vehicle_type")
		private String hazardous_material_vehicle_type;
		@JsonProperty("tractor")
		private String tractor;
		@JsonProperty("trailer")
		private String trailer;
	}

	/**
	 * MotasInfo（MOTAS情報）Model
	 */
	@Data
	public static class MotasInfo {

		/**
		 * コンストラクタ
		 */
		public MotasInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public MotasInfo(String max_payload_1, String max_payload_2, String vehicle_weight,
				String vehicle_length, String vehicle_width, String vehicle_height,
				String hazardous_material_volume, String hazardous_material_specific_gravity,
				String expiry_date, String deregistration_status) {

			this.max_payload_1 = max_payload_1;
			this.max_payload_2 = max_payload_2;
			this.vehicle_weight = vehicle_weight;
			this.vehicle_length = vehicle_length;
			this.vehicle_width = vehicle_width;
			this.vehicle_height = vehicle_height;
			this.hazardous_material_volume = hazardous_material_volume;
			this.hazardous_material_specific_gravity = hazardous_material_specific_gravity;
			this.expiry_date = expiry_date;
			this.deregistration_status = deregistration_status;

		}

		@JsonProperty("max_payload_1")
		private String max_payload_1;
		@JsonProperty("max_payload_2")
		private String max_payload_2;
		@JsonProperty("vehicle_weight")
		private String vehicle_weight;
		@JsonProperty("vehicle_length")
		private String vehicle_length;
		@JsonProperty("vehicle_width")
		private String vehicle_width;
		@JsonProperty("vehicle_height")
		private String vehicle_height;
		@JsonProperty("hazardous_material_volume")
		private String hazardous_material_volume;
		@JsonProperty("hazardous_material_specific_gravity")
		private String hazardous_material_specific_gravity;
		@JsonProperty("expiry_date")
		private String expiry_date;
		@JsonProperty("deregistration_status")
		private String deregistration_status;
	}

	/**
	 * VehicleDetails（車輌情報詳細）Model
	 */
	@Data
	public static class VehicleDetails {

		/**
		 * コンストラクタ
		 */
		public VehicleDetails() {
		}

		/**
		 * コンストラクタ
		 */
		public VehicleDetails(String bed_height, String cargo_height, String cargo_width, String cargo_length,
				String max_cargo_capacity, String body_type, String power_gate, String wing_doors,
				String refrigeration_unit, String temperature_range_min, String temperature_range_max,
				String crane_equipped, String vehicle_equipment_notes, String master_data_start_date,
				String master_data_end_date) {

			this.bed_height = bed_height;
			this.cargo_height = cargo_height;
			this.cargo_width = cargo_width;
			this.cargo_length = cargo_length;
			this.max_cargo_capacity = max_cargo_capacity;
			this.body_type = body_type;
			this.power_gate = power_gate;
			this.wing_doors = wing_doors;
			this.refrigeration_unit = refrigeration_unit;
			this.temperature_range_min = temperature_range_min;
			this.temperature_range_max = temperature_range_max;
			this.crane_equipped = crane_equipped;
			this.vehicle_equipment_notes = vehicle_equipment_notes;
			this.master_data_start_date = master_data_start_date;
			this.master_data_end_date = master_data_end_date;
		}

		@JsonProperty("bed_height")
		private String bed_height;
		@JsonProperty("cargo_height")
		private String cargo_height;
		@JsonProperty("cargo_width")
		private String cargo_width;
		@JsonProperty("cargo_length")
		private String cargo_length;
		@JsonProperty("max_cargo_capacity")
		private String max_cargo_capacity;
		@JsonProperty("body_type")
		private String body_type;
		@JsonProperty("power_gate")
		private String power_gate;
		@JsonProperty("wing_doors")
		private String wing_doors;
		@JsonProperty("refrigeration_unit")
		private String refrigeration_unit;
		@JsonProperty("temperature_range_min")
		private String temperature_range_min;
		@JsonProperty("temperature_range_max")
		private String temperature_range_max;
		@JsonProperty("crane_equipped")
		private String crane_equipped;
		@JsonProperty("vehicle_equipment_notes")
		private String vehicle_equipment_notes;
		@JsonProperty("master_data_start_date")
		private String master_data_start_date;
		@JsonProperty("master_data_end_date")
		private String master_data_end_date;
	}

	/**
	 * MaxCarryingCapacity（最大積載能力）Model
	 */
	@Data
	public static class MaxCarryingCapacity {

		/**
		 * コンストラクタ
		 */
		public MaxCarryingCapacity() {
		}

		/**
		 * コンストラクタ
		 */
		public MaxCarryingCapacity(String package_code, String package_name_kanji, String width, String height,
				String depth, String dimension_unit_code, String max_load_quantity) {

			this.package_code = package_code;
			this.package_name_kanji = package_name_kanji;
			this.width = width;
			this.height = height;
			this.depth = depth;
			this.dimension_unit_code = dimension_unit_code;
			this.max_load_quantity = max_load_quantity;
		}

		@JsonProperty("package_code")
		private String package_code;
		@JsonProperty("package_name_kanji")
		private String package_name_kanji;
		@JsonProperty("width")
		private String width;
		@JsonProperty("height")
		private String height;
		@JsonProperty("depth")
		private String depth;
		@JsonProperty("dimension_unit_code")
		private String dimension_unit_code;
		@JsonProperty("max_load_quantity")
		private String max_load_quantity;
	}

	/**
	 * HazardousMaterialInfo（危険物情報）Model
	 */
	@Data
	public static class HazardousMaterialInfo {

		/**
		 * コンストラクタ
		 */
		public HazardousMaterialInfo() {
		}

		/**
		 * コンストラクタ
		 */
		public HazardousMaterialInfo(String hazardous_material_item_code, String hazardous_material_text_info) {
			this.hazardous_material_item_code = hazardous_material_item_code;
			this.hazardous_material_text_info = hazardous_material_text_info;
		}

		@JsonProperty("hazardous_material_item_code")
		private String hazardous_material_item_code;
		@JsonProperty("hazardous_material_text_info")
		private String hazardous_material_text_info;
	}
}
