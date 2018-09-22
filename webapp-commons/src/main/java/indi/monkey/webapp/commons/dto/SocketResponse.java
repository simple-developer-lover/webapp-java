package indi.monkey.webapp.commons.dto;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocketResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7428529444837524567L;

	transient private static final Gson gson = new Gson();

	@SerializedName(value = "status")
	private String status;
	@SerializedName(value = "msg", alternate = { "message" })
	private String msg;
	@SerializedName(value = "data")
	private T data;

	public static <T> SocketResponse<T> of(String s, Type type) {
		return gson.fromJson(s, type);
	}

}
