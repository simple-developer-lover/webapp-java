package indi.monkey.webapp.pojo.hibernate.tieba;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
@Entity
@Table(name = "tieba_user")
public class TiebaUser {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(length = 30)
	@SerializedName(value = "userName", alternate = { "name" })
	private String userName;

	@SerializedName(value = "url")
	@Column(length = 80)
	private String url;

	@SerializedName(value = "userHead", alternate = { "head" })
	@Column(length = 100)
	private String userHead;
	@SerializedName(value = "userAge", alternate = { "tbAge" })

	@Column(length = 10)
	private String userAge;
	@Column(length = 8)
	@SerializedName(value = "userSex", alternate = { "sex" })
	private String userSex;

	@Column(length = 250)
	@SerializedName(value = "tbTitleCount")
	private String tbTitleCount;
}
