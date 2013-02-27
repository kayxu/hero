package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.web.service.task.taskMessage;
import com.joymeng.web.web.background.BgMessageController;

public class RemovalGold implements Job {
	private static Logger _log = LoggerFactory.getLogger(RemovalGold.class);
	public RemovalGold(){
		
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		NationManager.getInstance().autoRemoval();
		_log.info("*** RemovalGold");
		//添加没十分中发个消息
		taskMessage.getInstance().autoSend();
	}
}
