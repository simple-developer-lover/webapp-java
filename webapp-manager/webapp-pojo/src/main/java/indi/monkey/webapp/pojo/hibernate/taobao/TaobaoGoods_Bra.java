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
	@Column(name = "rateContent", length = 300)
	private String rateContent;

	@ManyToOne(optional = false)
	@JoinColumn(name = "shop_id", referencedColumnName = "shop_id", foreignKey = @ForeignKey(name = "shop_id"))
	@SerializedName(value = "shopId")
	private TaobaoShop shop;

	@SerializedName(value = "size")
	@Column(length = 15)
	private String size;

	@SerializedName(value = "cup")
	@Column(length = 2)
	private String cup;

	@SerializedName(value = "color")
	@Column(length = 15)
	private String color;

}
