package test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import indi.monkey.webapp.commons.dto.SocketResponse;

public class CommonsTest {
	@Test
	public void test01() {
		String json = "{\"status\":\"1\",\"message\":\"success\",\"data\":\"111\"}";
		SocketResponse<String> socket = SocketResponse.of(json);
		System.out.println(socket.getData());
	}
	
	@Test
	public void test02() {
		Gson gson = new Gson();
		SocketResponse<String> socket = new SocketResponse<String>();
		socket.setStatus("1");
		socket.setMsg("success");
		socket.setData("");
		System.out.println(gson.toJson(socket));
	}
}
