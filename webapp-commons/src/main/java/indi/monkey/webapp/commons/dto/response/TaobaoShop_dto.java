package indi.monkey.webapp.commons.dto.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TaobaoShop_dto {
	@SerializedName(value = "nick")
	private String nick;

	@SerializedName(value = "shopId", alternate = { "nid" })
	private String shopId;

	@SerializedName(value = "seller_id", alternate = { "sellerId", "user_id" })
	private String sellerId;

	private List<TaobaoGoods_Bra_dto> bras;
}
