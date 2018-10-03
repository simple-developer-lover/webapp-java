package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceTest {

	private static String processCup(String data) {
		Pattern p = Pattern.compile("\\\\\\d{2}[A|B|C|D|E|F|G]");
		Matcher matcher = p.matcher(data);
		if (matcher.matches()) {
			Matcher m = Pattern.compile("\\d{2}").matcher(data);
			if(m.find()) {
				data = m.group();
			}
			return String.valueOf(data.charAt(data.length() - 1));
		}
		return "";
	}

	public static void main(String[] args) {
		String data = "\\70A";
		System.out.println(processCup(data) + "---" + data);
	}
}
