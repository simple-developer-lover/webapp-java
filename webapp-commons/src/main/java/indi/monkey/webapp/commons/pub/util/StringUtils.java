package indi.monkey.webapp.commons.pub.util;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

public class StringUtils extends org.springframework.util.StringUtils {
	public static String toJsonStr(Object obj) {
		Assert.isNull(obj, "obj can not be null");
		return JSON.toJSONString(obj);
	}

	public static boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		} else if (obj instanceof String) {
			return ((String) obj).isEmpty();
		}
		return false;
	}

	public static String replace(String s, String oldValue, String newValue) {
		return s.replace(oldValue, newValue);
	}

	public static String filterEmoji(String content) {
		byte[] b_text = content.getBytes();
		for (int i = 0; i < b_text.length; i++) {
			if ((b_text[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					b_text[i + j] = 0x30;
				}
				i += 3;
			}
		}
		return new String(b_text);
	}
}
