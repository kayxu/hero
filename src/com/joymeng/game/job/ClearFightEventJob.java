package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.db.DBManager;

public class ClearFightEventJob implements Job{

	private static final Logger logger = LoggerFactory.getLogger(ClearFightEventJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		//清除3天未登录的玩家的所有战报消息
//		DBManager.getInstance().getWorldDAO().getFightEventDAO().clearSomeDaysNotLoginFightEvent(3);
//		logger.info("清空连续3天未登录的玩家的战报");
		
	}

}
