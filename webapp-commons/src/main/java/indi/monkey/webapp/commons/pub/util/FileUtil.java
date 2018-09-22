package indi.monkey.webapp.commons.pub.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Cleanup;

public class FileUtil {
	/**
	 * 返回�?�?
	 * 
	 * @param root
	 * @param files
	 * @return
	 */
	public static List<File> parseDir(File root) {
		List<File> files = Lists.newArrayList();
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				files.addAll(parseDir(file));
			}
		} else {
			if (root.getName().endsWith("class")) {
				files.add(root);
			}
		}
		return files;
	}

	public static void write(String fileName, String str) throws IOException {
		@Cleanup
		FileOutputStream fos = new FileOutputStream(new File(fileName));
		fos.write(str.getBytes("utf-8"));
	}
}
