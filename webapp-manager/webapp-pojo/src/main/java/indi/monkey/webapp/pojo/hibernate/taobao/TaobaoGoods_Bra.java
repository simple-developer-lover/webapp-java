package indi.monkey.webapp.pojo.hibernate.taobao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity
@Table(name = "taobaogoods_bra")
public class TaobaoGoods_Bra {

	@Id
	@GeneratedValue
	private Integer id;

	@SerializedName(value = "rateContent", alternate = { "content" })
	@Column(name = "rateContent", length = 300)
	private String rateContent;
	
	@Column(name = "shop_id",length = 20)
	private String shopId;
	
	@SerializedName(value = "size")
	@Column(length = 15)
	private String size;

	@SerializedName(value = "color")
	@Column(length = 15)
	private String color;

}
