package com.joymeng.core.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


public class CalendarUtil {
//	private static SimpleDateFormat formatter = new SimpleDateFormat(
//			"yyyy-MM-dd HH:mm:ss");
//	private static SimpleDateFormat formatter2 = new SimpleDateFormat(
//			"yyyy-MM-dd");
//	private static SimpleDateFormat formatter4 = new SimpleDateFormat("MM-dd");
//	/** yyyy/MM/dd HH:mm */
//	private static SimpleDateFormat formatter3 = new SimpleDateFormat(
//			"yyyy/MM/dd HH:mm");
//	private static Calendar calendar = Calendar.getInstance();
//	private static TimeZone timeZone = calendar.getTimeZone();
//	
//	public static synchronized String enDate(long time) {
//		calendar.setTimeInMillis(time);
//		return calendar.getTime().toString();
//	}
//	public static synchronized void setDateFormat(String paramString) {
//		formatter = new SimpleDateFormat(paramString);
//	}
//
//	/** yyyy/MM/dd HH:mm */
//	public static synchronized String format3(long time) {
//		calendar.setTimeInMillis(time);
//		return formatter3.format(calendar.getTime());
//	}
//
//	/** yyyy/MM/dd HH:mm */
//	public static synchronized String format5(long time) {
//		calendar.setTimeInMillis(time);
//		return formatter5.format(calendar.getTime());
//	}
//
//	public static synchronized String format(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatSimpleDate(long dateLong) {
//		calendar.setTimeInMillis(dateLong);
//		return formatter2.format(calendar.getTime());
//	}
//
//	public static synchronized String formatSimpleMonthDate(long dateLong) {
//		calendar.setTimeInMillis(dateLong);
//		return formatter4.format(calendar.getTime());
//	}
//
//	public static synchronized String formatAddYear(long paramLong, int paramInt) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.add(1, paramInt);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatAddMonth(long paramLong,
//			int paramInt) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.add(2, paramInt);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatAddDay(long paramLong, int paramInt) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.add(5, paramInt);
//		return formatter.format(calendar.getTime());
//	}
//	
//	
//	public static synchronized String formatAddHour(long paramLong, int paramInt) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.add(11, paramInt);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatAddMinute(long paramLong,
//			int paramInt) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.add(12, paramInt);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatAddSecond(long paramLong,
//			int paramInt) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.add(13, paramInt);
//		return formatter.format(calendar.getTime());
//	}
//	
//	public static synchronized Timestamp addSecond(long paramLong, int paramInt) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.add(13, paramInt);
//		return new Timestamp(calendar.getTimeInMillis());
//	}
//	
//
//
//	public static synchronized String formatDayStart(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.set(11, 0);
//		calendar.set(12, 0);
//		calendar.set(13, 0);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatDayEnd(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.set(11, 23);
//		calendar.set(12, 59);
//		calendar.set(13, 59);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatMonthStart(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.set(5, 1);
//		calendar.set(11, 0);
//		calendar.set(12, 0);
//		calendar.set(13, 0);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatMonthEnd(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.set(5, calendar.getActualMaximum(5));
//		calendar.set(11, 23);
//		calendar.set(12, 59);
//		calendar.set(13, 59);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatYearStart(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.set(2, 0);
//		calendar.set(5, 1);
//		calendar.set(11, 0);
//		calendar.set(12, 0);
//		calendar.set(13, 0);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized String formatYearEnd(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		calendar.set(2, 11);
//		calendar.set(5, 31);
//		calendar.set(11, 23);
//		calendar.set(12, 59);
//		calendar.set(13, 59);
//		return formatter.format(calendar.getTime());
//	}
//
//	public static synchronized long leftSecondsToNextHour(long paramLong) {
//		calendar.setTimeInMillis(paramLong);
//		Calendar localCalendar = Calendar.getInstance();
//		localCalendar.setTimeInMillis(paramLong);
//		localCalendar.set(12, 0);
//		localCalendar.set(13, 0);
//		localCalendar.add(11, 1);
//		return TimeUnit.MILLISECONDS.toSeconds(localCalendar.getTimeInMillis()
//				- calendar.getTimeInMillis());
//	}
//
//	public static synchronized long convert(String paramString) {
//		try {
//			calendar.setTime(formatter.parse(paramString));
//			return calendar.getTimeInMillis();
//		} catch (ParseException localParseException) {
//			localParseException.printStackTrace();
//		}
//		return 0L;
//	}
//
//	/**
//	 * �� yyyy/MM/dd hh:mm װ���ɺ���
//	 * 
//	 * @param paramString
//	 * @return
//	 */
//	public static synchronized long convert3(String paramString) {
//		try {
//			calendar.setTime(formatter3.parse(paramString));
//			return calendar.getTimeInMillis();
//		} catch (ParseException localParseException) {
//			localParseException.printStackTrace();
//		}
//		return 0L;
//	}
//
//	private static SimpleDateFormat formatter5 = new SimpleDateFormat(
//			"MM-dd HH:mm");
//
//	/**
//	 * �� yyyy-MM-dd hh:mm װ���ɺ���
//	 * 
//	 * @param paramString
//	 * @return
//	 */
//	public static synchronized long convert5(String paramString) {
//		try {
//			calendar.setTime(formatter5.parse(paramString));
//			return calendar.getTimeInMillis();
//		} catch (ParseException localParseException) {
//			localParseException.printStackTrace();
//		}
//		return 0L;
//	}
//
//	public static long getLocalTimeZoneRawOffset() {
//		return timeZone.getRawOffset();
//	}
//
//	public static long getTimeInMillisWithoutDay(long paramLong) {
//		return ((paramLong + getLocalTimeZoneRawOffset()) % 86400000L);
//	}
//
//	/**
//	 * �������ڼ� Ӣ��ϰ���ǣ���,1��һ,2��...��,7
//	 * 
//	 * @return ����һ��1��...�����գ�7
//	 */
//	public static int getDayOfWeek() {
//		Calendar calendar = Calendar.getInstance();
//		Date date = new Date();
//		calendar.setTime(date);
//		return calendar.get(Calendar.DAY_OF_WEEK);
//	}
//	
//	
//	
//	public static void main(String[] args){
//		
//		System.out.println(Calendar.getInstance().getTime().toString());
//	}
////	public static int getSysTime(){
////		return (int)(Calendar.getInstance().getTimeInMillis());
////	}
//	
////	public static long getSysLongTime(){
////		return Calendar.getInstance().getTimeInMillis();
////	}
//	
//	public static int getWarTime(String hour){
//		try {
//			String day = formatter2.format(calendar.getTime());
//			String all = day + hour;
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(formatter.parse(all));
//			return (int)(cal.getTimeInMillis()/1000);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return 0;
//		}
//	}
//	
//	public static String fomrts(int time){
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(time*1000);
//		return formatter.format(cal.getTime());
//	}
}