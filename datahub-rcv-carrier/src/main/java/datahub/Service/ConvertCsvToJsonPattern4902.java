package datahub.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import datahub.jsonData.Vehicle;
import datahub.jsonData.Vehicle.HazardousMaterialInfo;
import datahub.jsonData.Vehicle.MaxCarryingCapacity;
import datahub.jsonData.Vehicle.MotasInfo;
import datahub.jsonData.Vehicle.VehicleDetails;
import datahub.jsonData.Vehicle.VehicleInfo;
import datahub.step.BaseStep;

/**
 * 車輛マスタの用CSVファイルからJSONデータ変換
 */
@Service
public class ConvertCsvToJsonPattern4902 extends BaseStep {

	/**
	 * コンストラクタ
	 */
	public ConvertCsvToJsonPattern4902(MessageSource ms) {
		super(ms);
	}

	// vehicle_info 関連のフィールド
	public final List<String> vehicle_info = Arrays.asList("registration_number", "giai",
			"registration_transport_office_name", "registration_vehicle_type", "registration_vehicle_use",
			"registration_vehicle_id", "chassis_number", "vehicle_id", "operator_corporate_number",
			"operator_business_code", "owner_corporate_number", "owner_business_code", "vehicle_type",
			"hazardous_material_vehicle_type", "tractor", "trailer");
	// motas_info 関連のフィールド
	public final List<String> motas_info = Arrays.asList("max_payload_1", "max_payload_2", "vehicle_weight",
			"vehicle_length", "vehicle_width", "vehicle_height", "hazardous_material_volume",
			"hazardous_material_specific_gravity", "expiry_date", "deregistration_status");

	// vehicle_details 関連のフィールド
	public final List<String> vehicle_details = Arrays.asList("bed_height", "cargo_height", "cargo_width",
			"cargo_length", "max_cargo_capacity", "body_type", "power_gate", "wing_doors", "refrigeration_unit",
			"temperature_range_min", "temperature_range_max", "crane_equipped", "vehicle_equipment_notes",
			"master_data_start_date", "master_data_end_date");
	// max_carrying_capacity 関連のフィールド
	public final List<String> max_carrying_capacity = Arrays.asList("package_code", "package_name_kanji", "width",
			"height", "depth", "dimension_unit_code", "max_load_quantity");
	// hazardous_material_info 関連のフィールド
	public final List<String> hazardous_material_info = Arrays.asList("hazardous_material_item_code",
			"hazardous_material_text_info");

	/**
	 * CSVファイルからJSONデータに変換
	 */
	public List<Vehicle> convertCsvToJson(List<String> csvData) throws Exception {
		logger.info("ConvertCsvToJsonPattern4902.convertCsvToJson 開始");
		if (csvData == null || csvData.isEmpty()) {
			return null;
		}

		// ヘッダー行を取得
		String[] headers = csvData.get(0).split(",");
		List<Vehicle> vehicleList = new ArrayList<>();

		// VehicleInfoを作成
		Map<String, VehicleInfo> vehicleinfoMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addVehicleinfo(vehicleinfoMap, record));

		// Map から List<VehicleInfo> に変換
		List<VehicleInfo> vehicleinfoList = new ArrayList<>(vehicleinfoMap.values());
		for (VehicleInfo vehicleinfo : vehicleinfoList) {
			Vehicle vehicle = new Vehicle();
			vehicle.setVehicle_info(vehicleinfo);
			vehicleList.add(vehicle);
		}
		// MotasInfoを作成
		Map<String, MotasInfo> motasInfoMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addMotasInfo(motasInfoMap, record));

		// Map から List<RoadCarr> に変換
		List<MotasInfo> motasInfoList = new ArrayList<>(motasInfoMap.values());
		for (int i = 0; i < vehicleList.size(); i++) {
			vehicleList.get(i).setMotas_info(motasInfoList.get(i));
		}
		// vehicle_detailsを作成
		Map<String, VehicleDetails> vehicleDetailsMap = new HashMap<>();
		csvData.stream()
				.skip(1)
				.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
				.forEach(record -> addVehicleDetails(vehicleDetailsMap, record));

		// Map から List<RoadCarr> に変換
		List<VehicleDetails> vehicleDetailsList = new ArrayList<>(vehicleDetailsMap.values());
		for (int i = 0; i < vehicleList.size(); i++) {
			vehicleList.get(i).setVehicle_details(vehicleDetailsList.get(i));
		}
		// maxCarryingCapacityを作成
		for (Vehicle vehicle : vehicleList) {
			csvData.stream()
					.skip(1)
					.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
					.forEach(record -> addMaxCarryingCapacity(vehicle, record));
		}
		// hazardousMaterialMapを作成
		for (Vehicle vehicle : vehicleList) {
			csvData.stream()
					.skip(1)
					.map(line -> mapHeadersToValues(headers, line.split(",", -1)))
					.forEach(record -> addHazardousMaterialInfo(vehicle, record));
		}
		for (Vehicle vehicle : vehicleList) {
			if (vehicle.getMax_carrying_capacity().size() == 0) {
				MaxCarryingCapacity maxCarryingCapacity = new MaxCarryingCapacity();
				vehicle.getMax_carrying_capacity().add(maxCarryingCapacity);
			}
			if (vehicle.getHazardous_material_info().size() == 0) {
				HazardousMaterialInfo hazardousmaterialinfo = new HazardousMaterialInfo();
				vehicle.getHazardous_material_info().add(hazardousmaterialinfo);
			}
		}

		return vehicleList;
	}

	/**
	 *  vehicle_info を作成しMapに保存
	 */
	private void addVehicleinfo(Map<String, VehicleInfo> vehicleinfoMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, vehicle_info)) {
			return;
		}
		// Vehicleinfo の作成
		String giai = record.get("giai");
		VehicleInfo vehicleinfo = new VehicleInfo();
		vehicleinfo.setRegistration_number(record.get("registration_number"));
		vehicleinfo.setGiai(record.get("giai"));
		vehicleinfo.setRegistration_transport_office_name(record.get("registration_transport_office_name"));
		vehicleinfo.setRegistration_vehicle_type(record.get("registration_vehicle_type"));
		vehicleinfo.setRegistration_vehicle_use(record.get("registration_vehicle_use"));
		vehicleinfo.setRegistration_vehicle_id(record.get("registration_vehicle_id"));
		vehicleinfo.setChassis_number(record.get("chassis_number"));
		vehicleinfo.setVehicle_id(record.get("vehicle_id"));
		vehicleinfo.setOperator_corporate_number(record.get("operator_corporate_number"));
		vehicleinfo.setOperator_business_code(record.get("operator_business_code"));
		vehicleinfo.setOwner_corporate_number(record.get("owner_corporate_number"));
		vehicleinfo.setOwner_business_code(record.get("owner_business_code"));
		vehicleinfo.setVehicle_type(record.get("vehicle_type"));
		vehicleinfo.setHazardous_material_vehicle_type(record.get("hazardous_material_vehicle_type"));
		vehicleinfo.setTractor(record.get("tractor"));
		vehicleinfo.setTrailer(record.get("trailer"));

		// Map に保存
		vehicleinfoMap.put(giai, vehicleinfo);

	}

	/**
	 *  motas_info を作成しMapに保存
	 */
	private void addMotasInfo(Map<String, MotasInfo> motasInfoMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, motas_info)) {
			return;
		}
		String giai = record.get("giai");
		//MotasInfo に保存
		MotasInfo motasinfo = new MotasInfo();
		motasinfo.setMax_payload_1(record.get("max_payload_1"));
		motasinfo.setMax_payload_2(record.get("max_payload_2"));
		motasinfo.setVehicle_weight(record.get("vehicle_weight"));
		motasinfo.setVehicle_length(record.get("vehicle_length"));
		motasinfo.setVehicle_width(record.get("vehicle_width"));
		motasinfo.setVehicle_height(record.get("vehicle_height"));
		motasinfo.setHazardous_material_volume(record.get("hazardous_material_volume"));
		motasinfo.setHazardous_material_specific_gravity(record.get("hazardous_material_specific_gravity"));
		motasinfo.setExpiry_date(record.get("expiry_date"));
		motasinfo.setDeregistration_status(record.get("deregistration_status"));
		// Map に保存
		motasInfoMap.put(giai, motasinfo);

	}

	/**
	 *  vehicle_details を作成しMapに保存
	 */
	private void addVehicleDetails(Map<String, VehicleDetails> vehicleDetailsMap, Map<String, String> record) {
		if (isAllFieldsEmpty(record, vehicle_details)) {
			return;
		}
		String giai = record.get("giai");
		//VehicleDetails に保存
		VehicleDetails vehicledetails = new VehicleDetails();
		vehicledetails.setBed_height(record.get("bed_height"));
		vehicledetails.setCargo_height(record.get("cargo_height"));
		vehicledetails.setCargo_width(record.get("cargo_width"));
		vehicledetails.setCargo_length(record.get("cargo_length"));
		vehicledetails.setMax_cargo_capacity(record.get("max_cargo_capacity"));
		vehicledetails.setBody_type(record.get("body_type"));
		vehicledetails.setPower_gate(record.get("power_gate"));
		vehicledetails.setWing_doors(record.get("wing_doors"));
		vehicledetails.setRefrigeration_unit(record.get("refrigeration_unit"));
		vehicledetails.setTemperature_range_min(record.get("temperature_range_min"));
		vehicledetails.setTemperature_range_max(record.get("temperature_range_max"));
		vehicledetails.setCrane_equipped(record.get("crane_equipped"));
		vehicledetails.setVehicle_equipment_notes(record.get("vehicle_equipment_notes"));
		vehicledetails.setMaster_data_start_date(record.get("master_data_start_date"));
		vehicledetails.setMaster_data_end_date(record.get("master_data_end_date"));
		// Map に保存
		vehicleDetailsMap.put(giai, vehicledetails);

	}

	/**
	 *  max_carrying_capacity を作成しMapに保存
	 */
	private void addMaxCarryingCapacity(Vehicle vehicle,
			Map<String, String> record) {
		if (isAllFieldsEmpty(record, max_carrying_capacity)) {
			return;
		}
		if (Objects.equals(vehicle.getVehicle_info().getGiai(), record.get("giai"))) {

			// Maxcarryingcapacity の作成
			MaxCarryingCapacity maxcarryingcapacity = new MaxCarryingCapacity();
			maxcarryingcapacity.setPackage_code(record.get("package_code"));
			maxcarryingcapacity.setPackage_name_kanji(record.get("package_name_kanji"));
			maxcarryingcapacity.setWidth(record.get("width"));
			maxcarryingcapacity.setHeight(record.get("height"));
			maxcarryingcapacity.setDepth(record.get("depth"));
			maxcarryingcapacity.setDimension_unit_code(record.get("dimension_unit_code"));
			maxcarryingcapacity.setMax_load_quantity(record.get("max_load_quantity"));
			vehicle.getMax_carrying_capacity().add(maxcarryingcapacity);
		}
	}

	/**
	 *  hazardous_material_info を作成しMapに保存
	 */
	private void addHazardousMaterialInfo(Vehicle vehicle,
			Map<String, String> record) {
		if (isAllFieldsEmpty(record, hazardous_material_info)) {
			return;
		}
		//HazardousMaterialInfo に保存
		if (Objects.equals(vehicle.getVehicle_info().getGiai(), record.get("giai"))) {
			HazardousMaterialInfo hazardousmaterialinfo = new HazardousMaterialInfo();
			hazardousmaterialinfo.setHazardous_material_item_code(record.get("hazardous_material_item_code"));
			hazardousmaterialinfo.setHazardous_material_text_info(record.get("hazardous_material_text_info"));
			vehicle.getHazardous_material_info().add(hazardousmaterialinfo);
		}

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

	/**
	 * ヘッダーと値をマッピングしてMapに保存
	 */
	private Map<String, String> mapHeadersToValues(String[] headers, String[] values) {
		return IntStream.range(0, headers.length)
				.boxed()
				.collect(Collectors.toMap(i -> headers[i], i -> values[i]));
	}

}