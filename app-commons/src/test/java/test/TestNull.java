package test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.collect.Maps;

import test.bean.Bean;

public class TestNull {
	@Test
	public void test01() {
		Bean bean = new Bean();
		System.out.println(bean);
	}
	
	@Test
	public void test02() {
		
	}
	public static void main(String[] args) {
		HashMap<String, String> map = Maps.newHashMap();
		for(int i=0;i<100;i++) {
			String s = map.put("1","1");
			System.out.println(s);
		}
		System.out.println(map);
	}
}
