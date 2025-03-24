package datahub.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * 運送能力情報用JSON変換Entity
 * 
 */
@Getter
@Setter
public class ShipperTransCapacity {

	@JsonProperty("shipper_trans_capacity")
	private String ShipperTransCapacity;
}
