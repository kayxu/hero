package com.joymeng.core.utils;

import hirondelle.date4j.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.codehaus.groovy.tools.shell.util.NoExitSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUtils {
//	http://www.date4j.net/javadoc/index.html
	static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);
	public static final TimeZone tz = TimeZone.getTimeZone("GMT+8");//设置时区为北京时间
	/**
	 * 检测开始日期和结束日期，判断是否需要处理该条记录
	 * @param weekArray
	 * @param begin_date
	 * @param end_date
	 * @param now
	 * @return
	 */
	public static boolean checkTime(String[] weekArray, String begin_date,
			String end_date, DateTime now) {
		if (weekArray == null || weekArray.length == 0) {//不需要判断星期
			if (begin_date == null) {
				// 跳出
				return false;
			} else {
				// 按照日期处理
				DateTime sDate = new DateTime(begin_date);
				DateTime eDate = new DateTime(end_date);
				if (now.lteq(eDate) && sDate.lteq(now)) {
					// 在当前日期中判断是否在开始和结束时间内
					return true;
				}
			}
		} else {
			// 按照星期处理
			int day = now.getWeekDay();
			for (int i = 0; i < weekArray.length; i++) {
				logger.debug("week i==" + i + " ===" + weekArray[i]+" now day= "+day);
				if (day == Integer.parseInt(weekArray[i])) {
					// 在当前星期中
					return true;
				}
			}
			System.out.println("@@@@@@@day===" + now.toString());
			return false;
		}
		return false;
	}
	/**
	 * 检测是否是同一天
	 * @param time
	 * @return
	 */
	public static boolean isSameDay(long time1,long time2){
		DateTime startTime =TimeUtils.getTime(time1);
		DateTime endTime = TimeUtils.getTime(time2); 
		return startTime.isSameDayAs(endTime);
	}
	/**
	 * 检测是否是同一天
	 * @param time
	 * @return
	 */
	public static boolean isSameDay(long time){
		DateTime now =TimeUtils.now();//获得当前时间
		DateTime endTime = TimeUtils.getTime(time); 
		return now.isSameDayAs(endTime);
	}
	/**
	 * 获得时间
	 * @param time
	 * @return
	 */
	public static DateTime getTime(long time){
		DateTime dt = DateTime.forInstant(time, tz);
		return dt;
	}
	public static DateTime getTime(String str){
		return  new DateTime(str);
	}
	/**
	 * 获得当前时间
	 * @return
	 */
	public static DateTime now(){
		return DateTime.now(tz);//获得当前时间
	}
	
	/**
	 * 获得当前时间
	 * @return
	 */
	public static long nowLong(){
		return DateTime.now(tz).getMilliseconds(tz);//获得当前时间
	}
	/**
	 * 返回当前的日期，如2012-07-23
	 * @return
	 */
	public static DateTime today(){
		return DateTime.today(tz);
	}
	/**
	 * 返回两个日期之间的全部日期
	 * @param start
	 * @param end
	 * @return
	 */
	public static String[]getDays(String start,String end){
		
		DateTime startDate=TimeUtils.getTime(start);
		DateTime endDate=TimeUtils.getTime(end);
		//返回两个日期之间的时间差,如2012-11-1 2012-11-20  差值=19
		int num=startDate.numDaysFrom(endDate);
		String days[]=new String[num+1];
		days[0]=start;
		for(int i=1;i<days.length;i++){
			DateTime d=startDate.plusDays(i);
			days[i]=d.format(FORMAT);
		}
		return days;
	}
	
	/**
	 * 将字符串时间转化为long
	 * @param time
	 * @return
	 */
	public static long getTimes(String time){
		return getTime(time).getMilliseconds(TimeUtils.tz);
	}
	public static final String FORMAT1="YYYY-MM-DD hh:mm:ss";
	public static final String FORMAT="YYYY-MM-DD";
	public static final String FORMAT_EN = "hh:mm:ss DD.MM.YYYY";
	
	/**
	 * 英文时间显示
	 * @param time
	 * @return
	 */
	public static synchronized String enDate(long time) {
		return getTime(time).format(FORMAT_EN).toString();
	}
	/**
	 * 中文时间显示
	 * @param time
	 * @return
	 */
	public static synchronized String chDate(long time) {
		return getTime(time).format(FORMAT1).toString();
	}
	/**
	 * 争夺战开始结束时间
	 * @param time
	 * @return
	 */
	public static int getWarTime(String hour){
		String day = now().format(FORMAT);
		String all = day + hour;
		return (int)(getTime(all).getMilliseconds(tz)/1000);
	}
	
	/**
	 * 增加秒数
	 * @param time
	 * @return
	 */
	public static synchronized Timestamp addSecond(long paramLong,int paramInt) {
		return new Timestamp(paramLong + paramInt*1000);
	}	
	public static void main(String[] args) {
		System.out.println(now().getMinute());
		System.out.println(now().getHour());
		System.out.println(now().getSecond());
	}
}
