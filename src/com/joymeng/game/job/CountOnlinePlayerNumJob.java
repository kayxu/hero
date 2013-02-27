package com.joymeng.game.job;

import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.utils.ServerInfo;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.ServerInnerRequest;
import com.joymeng.services.core.JoyServiceApp;
/**
 * 计算玩家在线数量最大值
 * @author madi
 *
 */
public class CountOnlinePlayerNumJob implements Job{

	private static final Logger logger = LoggerFactory.getLogger(CountOnlinePlayerNumJob.class);
	
	/**
	 * 何时
	 */
	public static String when = "";
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//统计在线人数
		int num = World.getInstance().getOnlinePlayerNum();
		GameLog.logSystemEvent(LogEvent.ONLINE_NUM, String.valueOf(num),TimeUtils.now().format(TimeUtils.FORMAT1));
//		logger.info("CountOnlinePlayerMaxNumJob =======================num=" + num);
		ServerInfo info=new ServerInfo();
		GameLog.logSystemEvent(LogEvent.SERVER_INFO, info.toString());
//		JobKey jobKey = arg0.getJobDetail().getKey();
//		logger.info("jobKey=="+jobKey.getGroup()+" "+jobKey.getName());
//		if (arg0.getMergedJobDataMap().size() > 0) {
//			Set<String> keys = arg0.getMergedJobDataMap().keySet();
//			for (String key : keys) {
//				String val = arg0.getMergedJobDataMap().getString(key);
//				logger.info(" - jobDataMap entry: " + key + " = " + val);
//			}
//		}
	}

}
