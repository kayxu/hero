package com.joymeng.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class LoggerUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(LoggerUtils.class);
	String name;//logger名,相当于类名
	StringBuffer heard = new StringBuffer("\n");
	StringBuffer foot = new StringBuffer("\n");
	StringBuffer body = new StringBuffer("");
	
	public LoggerUtils(String name){
		this.name = name;
		heard.append("---------------------"+name+"*** begin----------------/n");
		foot.append("---------------------"+name+"*** end----------------/n");
	}
	private static  Map<String,LoggerUtils> logMap = new HashMap<String,LoggerUtils>();
	
	/**
	 * 获得logger对象
	 * @param name
	 * @return
	 */
	public static LoggerUtils getInstance(String name){
		if(null != name && !"".equalsIgnoreCase(name)){
			if(logMap.containsKey(name.trim().toUpperCase())){
				return logMap.get(name.trim().toUpperCase());
			}else{
				LoggerUtils lu = new LoggerUtils(name.trim().toUpperCase());
				logMap.put(name.trim().toUpperCase(), lu);
				return lu;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 记录日志
	 * @param logg
	 */
	public void inLog(String logg){
		body.append(logg).append("\n");
	}
	
	/**
	 * 显示记录日志
	 * @param logg
	 */
	public String toCompleteRecord(){
		StringBuffer sb = new StringBuffer();
		sb.append(heard.toString()).append(body.toString()).append(foot.toString());
		body = new StringBuffer();
		return sb.toString();
	}
	public static void main(String[] args) {
		LoggerUtils lu = LoggerUtils.getInstance("aaa");
		lu.inLog("test1");
		lu.inLog("test1");
		lu.inLog("test1");
		lu.inLog("test1");
		//System.out.println(lu.toCompleteRecord());
		lu.inLog("test2");
		System.out.println(lu.toCompleteRecord());
	}
}
