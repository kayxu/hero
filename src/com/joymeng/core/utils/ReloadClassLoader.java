/**
 * 
 */
package com.joymeng.core.utils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author gejing
 * 
 */
public class ReloadClassLoader extends ClassLoader {
	public Class<?> loadClass(File classFile) throws Exception {
		// 一般的class文件通常都小于100k，如果现实情况超出这个范围可以放大长度
		byte bytes[] = new byte[102400];
		FileInputStream fis = null;
		Class<?> clazz = null;
		try {
			fis = new FileInputStream(classFile);
			int j = 0;
			while (true) {
				int i = fis.read(bytes);
				if (i == -1)
					break;
				j += i;
			}
			clazz = super.defineClass(null, bytes, 0, j);
		} finally {
			if (fis != null)
				fis.close();
		}
		return clazz;
	}
}
