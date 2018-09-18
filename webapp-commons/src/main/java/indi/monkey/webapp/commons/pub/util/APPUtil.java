package indi.monkey.webapp.commons.pub.util;

public class APPUtil {

	public static final String defaultConfigName = "application.properties";

	public static boolean isWin() {
		return System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1;
	}

	public static String getProjectPath(Class<?> clazz) {
		String path = clazz.getResource("").getPath();
		String name = clazz.getName();
		String simpleName = clazz.getSimpleName();
		name = name.replaceAll("\\.", "/").replace("/" + simpleName, "");
		return path.replace(name, "");
	}

}
