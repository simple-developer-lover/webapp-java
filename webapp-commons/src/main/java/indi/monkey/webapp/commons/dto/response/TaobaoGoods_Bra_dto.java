package indi.monkey.webapp.commons.dto.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TaobaoGoods_Bra_dto {
	@SerializedName(value = "rateContent", alternate = { "content" })
	private String rateContent;

	@SerializedName(value = "shopId")
	private String shopId;

	@SerializedName(value = "size")
	private String size;

	@SerializedName(value = "cup")
	private String cup;

	@SerializedName(value = "color")
	private String color;
}
