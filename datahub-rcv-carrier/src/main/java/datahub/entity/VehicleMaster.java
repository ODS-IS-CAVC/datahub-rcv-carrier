package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 車輛マスタ用JSON変換Entity
 * 
 */
@Getter
@Setter
public class VehicleMaster {
	
	@JsonProperty("vehicle_master")
	private String vehicleMaster;
}
