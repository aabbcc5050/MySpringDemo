package com.myspring.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanUtils {

	private static final List<String> classPaths = new ArrayList<String>();

	public static void main(String[] args) throws ClassNotFoundException {
		searchClass("com.myspring").forEach(System.out::println);
	}

	public static List<String> searchClass(String path) throws ClassNotFoundException {

		String classpath = ScanUtils.class.getResource("/").getPath();
		// 然后把我们的包名basPach转换为路径名
		path = path.replace(".", File.separator);
		// 然后把classpath和basePack合并
		String searchPath = classpath + path;
		classPaths.clear();
		doPath(new File(searchPath));
		// 这个时候我们已经得到了指定包下所有的类的绝对路径了。我们现在利用这些绝对路径和java的反射机制得到他们的类对象
		return classPaths.stream().collect(() -> new ArrayList<String>(), (list, item) -> {
			list.add(item.replace(classpath.replace("/", "\\").replaceFirst("\\\\", ""), "").replace("\\", ".")
					.replace(".class", ""));
		}, (list1, list2) -> list1.addAll(list2));
	}

	/**
	 * 该方法会得到所有的类，将类的绝对路径写入到classPaths中
	 * 
	 * @param file
	 * @return
	 */
	private static List<String> doPath(File file) {

		if (file.isDirectory()) {// 文件夹
			// 文件夹我们就递归
			File[] files = file.listFiles();
			for (File f1 : files) {
				doPath(f1);
			}
		} else {// 标准文件
			// 标准文件我们就判断是否是class文件
			if (file.getName().endsWith(".class")) {
				// 如果是class文件我们就放入我们的集合中。
				classPaths.add(file.getPath());
			}
		}
		return classPaths;
	}
}
