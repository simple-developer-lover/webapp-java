package indi.monkey.webapp.pojo.hibernate.taobao;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity
@Table(name = "taobao_shop")
public class TaobaoShop {

	@Id
	@GeneratedValue
	private Integer id;

	@SerializedName(value = "nick")
	@Column(length = 30)
	private String nick;

	@SerializedName(value = "shopId", alternate = { "nid" })
	@Column(name = "shop_id", length = 20)
	private String shopId;

	@Column(length = 30)
	@SerializedName(value = "seller_id", alternate = { "user_id" })
	private String sellerId;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	@SerializedName(value = "model_pics", alternate = { "models" })
	private Set<Model_pic> model_pics;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	@SerializedName(value = "taobaogoods_bras", alternate = { "bras" })
	private Set<TaobaoGoods_Bra> taobaogoods_bras;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaobaoShop other = (TaobaoShop) obj;
		if (sellerId == null) {
			if (other.sellerId != null)
				return false;
		} else if (!sellerId.equals(other.sellerId))
			return false;
		if (shopId == null) {
			if (other.shopId != null)
				return false;
		} else if (!shopId.equals(other.shopId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sellerId == null) ? 0 : sellerId.hashCode());
		result = prime * result + ((shopId == null) ? 0 : shopId.hashCode());
		return result;
	}
}
