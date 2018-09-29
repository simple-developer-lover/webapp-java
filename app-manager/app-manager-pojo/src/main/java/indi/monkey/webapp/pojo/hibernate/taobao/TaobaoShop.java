package indi.monkey.webapp.pojo.hibernate.taobao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Target;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity
@Table(name = "taobao_shop")
public class TaobaoShop implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6382376130424653249L;

	@Id
	@GeneratedValue
	private Integer id;

	@SerializedName(value = "nick")
	@Column(length = 30)
	private String nick;

	@SerializedName(value = "shopId", alternate = { "nid" })
	@Column(name = "shop_id", length = 20)
	private String shopId;

	@Column(length = 30, name = "seller_id")
	@SerializedName(value = "seller_id", alternate = { "sellerId", "user_id" })
	private String sellerId;

	private transient List<TaobaoGoods_Bra> bras;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaobaoShop other = (TaobaoShop) obj;
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
		result = prime * result + ((shopId == null) ? 0 : shopId.hashCode());
		return result;
	}
}
