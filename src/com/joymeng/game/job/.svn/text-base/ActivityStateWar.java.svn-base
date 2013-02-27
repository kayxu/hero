package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.nation.war.WarManager;

public class ActivityStateWar implements Job{
	WarManager warMgr = WarManager.getInstance();
	private static Logger _log = LoggerFactory.getLogger(ActivityStateWar.class);
	public ActivityStateWar(){
		
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		warMgr.FIGHT_START = TimeUtils.getWarTime(" 20:00:00");
		warMgr.FIGHT_END = TimeUtils.getWarTime(" 20:30:00");
		warMgr.ROUND = 0;
		warMgr.FIGHT_TYPE = (byte)1;
		warMgr.FIGHT_NUM = 4;
		warMgr.CAMP_NUM = 4;//军营数据
		warMgr.IS_REFRESH = false;
		byte type =warMgr.startWar(true,null);
		_log.info("州长争夺战开始:"+type);
	}

}
