package indi.monkey.webapp.commons.pub.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathUtil {

	private static final String[] units = { "", "十", "百", "千" };

	private static final String each_unit = "亿";

	private static final String addUnit(String s) {
		String[] result = new String[s.length()];
		char[] arr = s.toCharArray();
		for (int a = arr.length - 1; a >= 0; a--) {
			if (arr[a] == '零') {
				result[a] = String.valueOf(arr[a]);
			} else {
				result[a] = arr[a] + units[arr.length - 1 - a];
			}
		}
		return Arrays.toString(result).replaceAll(",", "").replace("[", "").replace("]", "");
	}

	public static String coverNumber2Str(long number) {
		String str_num = String.valueOf(number);
		for (int i = 0; i < str_num.length(); i++) {

		}
		return null;
	}

	public static void main(String[] args) {
		String addUnit = addUnit("七零九");
		System.out.println(addUnit);
	}
}
