package com.joymeng.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class DyClassLoader extends ClassLoader {

	ArrayList<String> classpath = new ArrayList<String>();

	/**
     * 
     */
	public DyClassLoader() {
		super(DyClassLoader.class.getClassLoader());
	}

	/**
	 * 增加类路径
	 * 
	 * @param s
	 */
	public void addClassPath(String s) {
		classpath.add(s);
	}

	/**
	 * 清除所有类路径
	 */
	public void clearClassPath() {
		classpath.clear();
	}

	/**
	 * 从自有路径下加载类
	 * 
	 * @param className
	 * @return
	 */
	public Class<?> loadFromCustomRepository(String className) {
		byte[] classBytes = null;

		for (String dir : classpath) {

			// replace '.' in the class name with File.separatorChar & append
			// .class to the name
			String classFileName = className.replace('.', File.separatorChar);
			classFileName += ".class";
			try {
				File file = new File(dir + File.separatorChar + classFileName);
				if (file.exists()) {
					InputStream is = new FileInputStream(file);
					/** 把文件读到字节文件 */
					classBytes = new byte[is.available()];
					is.read(classBytes);
					break;
				}
			} catch (IOException ex) {
				System.out
						.println("IOException raised while reading class file data");
				ex.printStackTrace();
				return null;
			}
		}

		return this.defineClass(className, classBytes, 0, classBytes.length);// 加载类

	}

	/**
	 * 
	 * @param className
	 * @return
	 */
	public Class<?> loadFromSysAndCustomRepository(String className) {
		/** 取环境变量 */
		String classPath = System.getProperty("java.class.path");
		List<String> classRepository = new ArrayList<String>();
		/** 取得该路径下的所有文件夹 */
		if ((classPath != null) && !(classPath.equals(""))) {
			StringTokenizer tokenizer = new StringTokenizer(classPath,
					File.pathSeparator);
			while (tokenizer.hasMoreTokens()) {
				classRepository.add(tokenizer.nextToken());
			}
		}
		Iterator<String> dirs = classRepository.iterator();
		byte[] classBytes = null;
		/** 在类路径上查找该名称的类是否存在，如果不存在继续查找 */
		while (dirs.hasNext()) {
			String dir = (String) dirs.next();
			// replace '.' in the class name with File.separatorChar & append
			// .class to the name
			String classFileName = className.replace('.', File.separatorChar);
			classFileName += ".class";
			try {
				File file = new File(dir + File.separatorChar + classFileName);
				if (file.exists()) {
					InputStream is = new FileInputStream(file);
					/** 把文件读到字节文件 */
					classBytes = new byte[is.available()];
					is.read(classBytes);
					break;
				}
			} catch (IOException ex) {
				System.out
						.println("IOException raised while reading class file data");
				ex.printStackTrace();
				return null;
			}
		}
		return this.defineClass(className, classBytes, 0, classBytes.length);// 加载类

	}
}