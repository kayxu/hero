package com.joymeng.core.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor {

	/**
	 * 读取文件字节，并进行GZIP压缩，并返回压缩后的字节
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static byte[] compressFile(File file) throws Exception {

		int bitlen = (int) file.length();
		FileInputStream is = new FileInputStream(file);

		byte[] bs = new byte[bitlen];
		is.read(bs);
		is.close();

		return compressBytes(bs);
	}

	public static byte[] compressBytes(byte[] bytes) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream zos = new GZIPOutputStream(bos);
		zos.write(bytes, 0, bytes.length);
		zos.close();

		byte[] c = bos.toByteArray();
		bos.close();
		bytes = c;

		return bytes;
	}

	public static byte[] compressFile(String filePath) throws Exception {
		File file = new File(filePath);
		return compressFile(file);
	}
}
