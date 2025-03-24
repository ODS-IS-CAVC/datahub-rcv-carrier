package datahub.jsonData;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 外部API接続用JSONデータ
 * 
 */
@Data
public class JsonDataToAPI {
	@JsonProperty("dataModelType")
	private String dataModelType = "test1";

	@JsonProperty("attribute")
	private Object attribute;
}
