package indi.monkey.webapp.commons.pub.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class APPUtil {

	public static final String defaultConfigName = "application.properties";

	public static boolean isWin() {
		return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
	}

	public static String getProjectPath(Class<?> clazz) {
		String path = clazz.getResource("").getPath();
		String name = clazz.getName();
		String simpleName = clazz.getSimpleName();
		name = name.replaceAll("\\.", "/").replace("\\/" + simpleName, "");
		return path.replace(name, "");
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericType(Class<?> targetClass) {
		Type[] types = ((ParameterizedType) targetClass.getGenericSuperclass()).getActualTypeArguments();
		if (types.length == 1) {
			return (Class<T>) types[0];
		}
		throw new RuntimeException("genericType error!!!");
	}

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<String>();
		List<String> lists = list1.stream().collect(Collectors.toList());
		lists.add("1");
		System.out.println(list1);
		// AI 精度
		// 算法 --> 企业 -->
		// 数据量大 --> 用户
	}
}
