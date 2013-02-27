package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.rank.RankManager;

public class LoadRankDataJob implements Job {

	private static Logger _log = LoggerFactory.getLogger(LoadRankDataJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		_log.info(">>>load rank data");
		RankManager.getInstance().getNeedGameMoneyRank();
		RankManager.getInstance().getNeedJoyMoneyRank();
		RankManager.getInstance().getNeedLadderMaxRank();
		RankManager.getInstance().getNeedAttackRank();
		RankManager.getInstance().getNeedDefenceRank();
		RankManager.getInstance().getNeedHPRank();
	}

}
