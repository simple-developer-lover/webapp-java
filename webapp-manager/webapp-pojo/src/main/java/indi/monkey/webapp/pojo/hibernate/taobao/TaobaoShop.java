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
	@SerializedName(value = "sellerId", alternate = { "user_id" })
	private String sellerId;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	@SerializedName(value = "model_pics", alternate = { "models" })
	private Set<Model_pic> model_pics;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "shop_id")
	@SerializedName(value = "taobaogoods_bras", alternate = { "bras" })
	private Set<TaobaoGoods_Bra> taobaogoods_bras;
}
