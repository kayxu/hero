package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.nation.NationManager;

public class MilitaryGold implements Job {
	private static Logger _log = LoggerFactory.getLogger(MilitaryGold.class);
	public MilitaryGold(){
		
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		NationManager.getInstance().satateMilitary();//州总军功
		NationManager.getInstance().deduAch();//扣除每天政绩
		NationManager.getInstance().setAchiev();//计算政绩加成
		NationManager.getInstance().calculationMilitary();//重新计算每天军功
		_log.info("*** satateMilitary");
	}
}
