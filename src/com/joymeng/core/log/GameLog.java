package com.joymeng.core.log;

import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.role.PlayerCharacter;

/**
 * 游戏日志
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class GameLog {
	public static final char SPLIT_CHAR = '|';
	/**
	 * 玩家类日志
	 * @param player
	 * @param event
	 * @param logBuffer
	 */
	public static void logPlayerEvent(PlayerCharacter player, LogEvent event,
			LogBuffer logBuffer) {
		StringBuffer strBuff = new StringBuffer(128);
		strBuff.append(player.getId()).append(SPLIT_CHAR)
				.append(event.getLogType()).append(SPLIT_CHAR)
				.append(logBuffer.getStringBuffer());
//		event.getLogType().getLogger().info(strBuff.toString());
		GameLog.info(strBuff.toString());
	}
	/**
	 * 系统类日志
	 * @param event
	 * @param message
	 */
	public static void logSystemEvent(LogEvent event,String ...message){
		StringBuffer strBuff = new StringBuffer(128);
		strBuff.append(event.getLogType()).append(SPLIT_CHAR);
		for(String str:message){
			strBuff.append(str).append(SPLIT_CHAR);
		}
		GameLog.info(strBuff.toString());
	}

	private final static Logger logger = LoggerFactory.getLogger(GameLog.class);

//	public static void error(String error) {
//		logger.error(error);
//	}
//
	public static void error(String error, Throwable throwable) {
		logger.error(error, throwable);
	}
//
	public static void error(String str) {
		logger.error(str);
	}
//
	private static void info(String info) {
		logger.info(info);
	}

//	public static void debug(String deb) {
//		logger.debug(deb);
//	}
//
//	public static void warn(String warn) {
//		logger.warn(warn);
//	}
//
//	public static void logForUpdate() {
//		StringBuffer strBuff = new StringBuffer(128);
//
//		strBuff.append(-1).append(SPLIT_CHAR).append("").append(SPLIT_CHAR)
//				.append("FOR_UPDATE").append(SPLIT_CHAR).append(-1)
//				.append(SPLIT_CHAR);
//		String str = strBuff.toString();
//		for (LogType logType : LogType.getValues()) {
//			logType.getLogger().info(str);
//		}
//	}
}
