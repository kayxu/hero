package com.joymeng.core.utils;

import com.joymeng.services.core.buffer.JoyBuffer;
/**
 * joybuffer传入数组的辅助方法
 * @author admin
 * @date 2012-4-28
 * TODO
 */
public class JoyBufferPlus {
	/**
	 * int数组
	 * @param out
	 * @param array
	 */
	public static void putIntArray(JoyBuffer out,int[] array){
		out.putInt(array.length);
		for(int i=0;i<array.length;i++){
			out.putInt(array[i]);
		}
	}
	/**
	 * byte 数组
	 * @param out
	 * @param array
	 */
	public static void putByteArray(JoyBuffer out,byte[] array){
		out.putInt(array.length);
		for(int i=0;i<array.length;i++){
			out.put(array[i]);
		}
	}
	/**
	 * string 数组
	 * @param out
	 * @param array
	 */
	public static void putStringArray(JoyBuffer out,String[] array){
		out.putInt(array.length);
		for(int i=0;i<array.length;i++){
			out.putPrefixedString(array[i],(byte)2);
		}
	}
	/**
	 * short数组
	 * @param out
	 * @param array
	 */
	public static void putShortArray(JoyBuffer out,short[] array){
		out.putInt(array.length);
		for(int i=0;i<array.length;i++){
			out.putShort(array[i]);
		}
	}
}
