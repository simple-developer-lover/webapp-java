package indi.monkey.webapp.pojo.hibernate.taobao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Entity
@Table(name = "model_pic")
@Data
public class Model_pic {
	@Id
	@GeneratedValue
	@Column(length = 5)
	@SerializedName(value = "id")
	private Integer id;

	@Column(name = "shop_id",length = 20)
	@SerializedName(value = "shopId")
	private String shopId;
	
	@Column(length = 100)
	@SerializedName(value = "picUrl")
	private String picUrl;
	
	
}
