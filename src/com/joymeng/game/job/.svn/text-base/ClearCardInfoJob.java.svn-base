package com.joymeng.game.job;

import java.util.Iterator;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;

public class ClearCardInfoJob implements Job{

	private static final Logger logger = LoggerFactory.getLogger(ClearCardInfoJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		World.getInstance().clearPlayerCardsInfo();
		logger.info("===========清空在线玩家牌局信息========");
	}

	
}
