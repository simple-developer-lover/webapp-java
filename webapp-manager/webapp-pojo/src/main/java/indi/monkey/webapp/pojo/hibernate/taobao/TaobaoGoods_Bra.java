package indi.monkey.webapp.pojo.hibernate.taobao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity
@Table(name = "taobaogoods_bra")
public class TaobaoGoods_Bra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4375272496164893922L;

	@Id
	@GeneratedValue
	private Integer id;

	@SerializedName(value = "rateContent", alternate = { "content" })
	@Column(name = "rateContent", length = 2000)
	private String rateContent;

	@SerializedName(value = "size")
	@Column(length = 15)
	private String size;

	@SerializedName(value = "cup")
	@Column(length = 30)
	private String cup;

	@SerializedName(value = "color")
	@Column(length = 20)
	private String color;

	@SerializedName(value = "auctionSku")
	@Column(name = "auctionSku", length = 40)
	private String auctionSku;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "shop_id", referencedColumnName = "shop_id", foreignKey = @ForeignKey(name = "shop_id"))
	@SerializedName(value = "shopId")
	private TaobaoShop shop;
}
