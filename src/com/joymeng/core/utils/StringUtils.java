package com.joymeng.core.utils;

import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Shaolong Wang ������ 2010/11/26
 * 
 */
public class StringUtils {
	static Logger logger = LoggerFactory.getLogger(StringUtils.class);
	
	public static String WHITE = "{c:FFFFFF}";// ��
	public static String GREEN = "{c:00FF33}";// ��
	public static String BULE = "{c:0066FF}";// ��
	public static String VIOLET = "{c:9900FF}";// ��
	public static String ORANGE = "{c:FF6600}";// ��
	public static String GOLD = "{c:FFCC00}";// ��
	public static String RED = "{c:FF0000}";// ��
	public static String GRAY = "{c:666666}";// ��

	/**
	 * 
	 * @param color
	 * @return
	 */
	public static String getColor(int color) {
		switch (color) {
		case 0:
			return WHITE;// ��ɫ
		case 1:
			return GREEN;// ��ɫ
		case 2:
			return BULE;// ��ɫ
		case 3:
			return VIOLET;// ��ɫ
		case 4:
			return ORANGE;// ��ɫ
		case 5:
			return GOLD;// ��ɫ
		}
		return null;
	}

	/**
	 * Converts a line of text into an array of lower case words. Words are
	 * delimited by the following characters: , .\r\n:/\+
	 * <p>
	 * In the future, this method should be changed to use a
	 * BreakIterator.wordInstance(). That class offers much more fexibility.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken().toLowerCase();
		}
		return words;
	}

	/**
	 * Converts a line of text into an array of lower case words. Words are
	 * delimited by the following characters: , .\r\n:/\+
	 * <p>
	 * In the future, this method should be changed to use a
	 * BreakIterator.wordInstance(). That class offers much more fexibility.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @return text broken up into an array of words.
	 */
	public static final String[] toStringArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, ",\r\n/\\");
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		return words;
	}

	/**
	 * 
	 * @param text
	 * @param delimiter
	 * @return
	 */
	public static final String[] split(String text, String delimiter) {
		StringTokenizer tokens = new StringTokenizer(text, delimiter);
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		return words;
	}

	/**
	 * * Converts a line of text into an array of lower case words. Words are
	 * delimited by the following characters: , .\r\n:/\+
	 * <p>
	 * In the future, this method should be changed to use a
	 * BreakIterator.wordInstance(). That class offers much more fexibility.
	 * 
	 * @param text
	 *            a String of text to convert into an array of words
	 * @param token
	 *            String
	 * @return String[]broken up into an array of words.
	 */
	public static final String[] toStringArray(String text, String token) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, token);
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken();
		}
		return words;
	}

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String[] splitOnWhitespace(String source) {
		int pos = -1;
		LinkedList<String> list = new LinkedList<String>();
		int max = source.length();
		for (int i = 0; i < max; i++) {
			char c = source.charAt(i);
			if (Character.isWhitespace(c)) {
				if (i - pos > 1) {
					list.add(source.substring(pos + 1, i));
				}
				pos = i;
			}
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Replayer str
	 * 
	 * @param str
	 * @param key
	 * @param replacement
	 * @return
	 */
	public static final String replaceAll(String str, String key,
			String replacement) {
		if (str != null && key != null && replacement != null
				&& !str.equals("") && !key.equals("")) {
			StringBuilder strbuf = new StringBuilder();
			int begin = 0;
			int slen = str.length();
			int npos = 0;
			int klen = key.length();
			for (; begin < slen && (npos = str.indexOf(key, begin)) >= begin; begin = npos
					+ klen) {
				strbuf.append(str.substring(begin, npos)).append(replacement);
			}

			if (begin == 0) {
				return str;
			}
			if (begin < slen) {
				strbuf.append(str.substring(begin));
			}
			return strbuf.toString();
		} else {
			return str;
		}
	}

	/**
	 * �и��ַ�
	 * 
	 * @param str
	 * @param count
	 * @return
	 */
	public static final String subString(String str, int count) {
		if (str == null) {
			return "";
		}
		if (str.length() <= count) {
			return str;
		}
		return new StringBuilder(str.substring(0, count)).append("...")
				.toString();
	}

	/**
	 * �Ѽ����ַ���������
	 * 
	 * @param delimiter
	 * @param first
	 * @param others
	 * @return
	 */
	public static final String join(String delimiter, String first,
			String... strings) {
		StringBuilder sb = new StringBuilder(first);
		for (String s : strings) {
			sb.append(delimiter).append(s);
		}
		return sb.toString();
	}

	/**
	 * ����ָ��������Ƿ��Ƿ��ַ�
	 * 
	 * @param name
	 * @return
	 */
	public static boolean containsForbidChars(String name) {
		String forbidChars = "/#@\\+|";
		for (int i = 0; i < forbidChars.length(); i++) {
			if (name.indexOf(forbidChars.charAt(i)) >= 0) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String a = "-2314312.4142";
		System.out.println(isInteger(a));
	}
	
	/**
	 * 是否整形
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str){
		if(!isNull(str, "数字")){
			Pattern pattern = Pattern.compile("^(-)?\\d+(\\d+)?$"); 
		    return pattern.matcher(str).matches();    
		}
		return false;
	}
	
	/**
	 * 是否数字
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str){
		if(!isNull(str, "数字")){
			Pattern pattern = Pattern.compile("^(-)?\\d+(\\.\\d+)?$"); 
		    return pattern.matcher(str).matches();    
		}
		return false;
	}

	/**
	 * 
	 * @param str
	 * @param message
	 * @return
	 */
	public static boolean isNull(String str, String message) {
		if (str == null || str.trim().equals("")) {
			return true;
		}
		return false;
	}
	/**
	 * 需要解析的字符串，
	 * @param str
	 * @param split 分隔符号
	 * @return
	 */
	public static int[] changeToInt(String str,String split){
		if(str==null||str.equals("")){
			return null;
		}
		int data[]=null;
		try{
			String s[]=str.split(split);	
			data=new int[s.length];
			for(int i=0;i<s.length;i++){
				data[i]=Integer.parseInt(s[i]);
			}
		}catch(Exception ex){
			logger.info("change to int error!,str="+str+" split="+split);
			return null;
		}
		return data;
	}
	/**
	 * 从字符串中移除一个
	 * @param oldStr 需要处理的字符串
	 * @param id 查找的字符串
	 * @param split 分隔符号
	 * @param newStr 需要替换的字符
	 * @return
	 */
	public static String removeFromStr(String oldStr,int id,String split,String newStr){
		if(oldStr==null||oldStr.length()==0){
			return "";
		}
		String ids[]=oldStr.split(split);
		//找到相同的id则设置为“”
		for(int i=0;i<ids.length;i++){
			if(id==Integer.parseInt(ids[i])){
				ids[i]=newStr;
				break;
			}
		}
		//组合为新的字符串
		String str=recoverNewStr(ids,split);
//		String str="";
//		int m=0;
//		for(int i=0;i<ids.length;i++){
//			if(!ids[i].equals("")){
//				if(m==0){
//					str+=ids[i];
//				}else{
//					str+=split+ids[i];
//				}
//				m++;
//			}
//		}
		return str;
	}
	/**
	 * 还原数组 为字符串，去掉数组中的空字符串
	 * 如 {0,1,2} 为 0；1；2 
	 * @param oldStr
	 * @param reg
	 * @return
	 */
	public static String recoverNewStr(String oldStr[],String reg){
		String str="";
		int m=0;
		for(int i=0;i<oldStr.length;i++){
			if(!oldStr[i].equals("") && oldStr[i].indexOf(":0,") == -1){//不为空没有0的士兵
				if(m==0){
					str+=oldStr[i];
				}else{
					str+=reg+oldStr[i];
				}
				m++;
			}
		}
		return str;
	}
	/**
	 * 将int数组转化为字符串数组
	 * @param ids
	 * @return
	 */
	public static String[] changeToString(int ids[]){
		String str[]=new String[ids.length];
		for(int i=0;i<ids.length;i++){
			str[i]=String.valueOf(ids[i]);
		}
		return str;
	}
	/**
	 * 向数组中增加一个新元素
	 * @param array
	 * @param id
	 * @return
	 */
	public static int[] addNew(int array[],int id){
		int[] newArray=new int[array.length+1];
		System.arraycopy(array, 0, newArray, 0,array.length);
		newArray[newArray.length-1]=id;
		return newArray;
	}
}
