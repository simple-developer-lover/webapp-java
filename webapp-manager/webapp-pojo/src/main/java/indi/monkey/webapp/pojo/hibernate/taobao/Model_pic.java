package indi.monkey.webapp.pojo.hibernate.taobao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Entity
@Table(name = "model_pic")
@Data
public class Model_pic implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7392461536578049624L;

	@Id
	@GeneratedValue
	@SerializedName(value = "id")
	private Integer id;

	@Column(name = "shop_id", length = 20)
	@JoinColumn(name = "shop_id")
	@SerializedName(value = "shopId")
	private String shopId;

	@Column(length = 100)
	@SerializedName(value = "picUrl")
	private String picUrl;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Model_pic other = (Model_pic) obj;
		if (picUrl == null) {
			if (other.picUrl != null)
				return false;
		} else if (!picUrl.equals(other.picUrl))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((picUrl == null) ? 0 : picUrl.hashCode());
		return result;
	}
}
