package test;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.gson.Gson;

import indi.monkey.webapp.commons.dto.SocketResponse;

public class CommonsTest {
	@Test
	public void test01() {
		String json = "{\"status\":\"1\",\"message\":\"success\",\"data\":\"111\"}";
	
	}
	@Test
	public void test02() {
		Gson gson = new Gson();
		SocketResponse<String> socket = new SocketResponse<String>();
		socket.setStatus("1");
		socket.setMsg("success");
		socket.setData("");
		System.out.println(gson.toJson(socket));
		WebDriver driver = new ChromeDriver();
		driver.get("");
		
		
	}
	
	@Test
	public void test03() {
		Integer a = 129;
		Integer b = 129;
		System.out.println(a==b);
		Integer c = 3;
		Integer d = 3;
		System.out.println(c==d);
		Integer e = 10;
		System.out.println(e);
	}
	
	@Test
	public void test04() {
		String utterance = "我想听小虎队专辑";
		System.out.println(utterance.matches(".*专辑.*"));
	}
}
