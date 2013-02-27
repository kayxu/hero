package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;

public class ShoutJob implements Job{
	private static Logger _log = LoggerFactory.getLogger(ShoutJob.class);
	public ShoutJob(){
		
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		TipMessage tip=World.getInstance().getShout();
		if(tip==null){
			return;
		}
		GameUtils.sendWolrdMessage(tip, GameUtils.SCROLL);
		_log.info("shout==="+tip.getMessage());
	}

}
