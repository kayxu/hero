package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.nation.war.WarManager;

public class ActivityCityWar implements Job{
	WarManager warMgr = WarManager.getInstance();
	private static Logger _log = LoggerFactory.getLogger(ActivityCityWar.class);
	public ActivityCityWar(){
		
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		warMgr.FIGHT_START = TimeUtils.getWarTime(" 11:00:00");
		warMgr.FIGHT_END = TimeUtils.getWarTime(" 19:00:00");
		if(TimeUtils.now().getHour()==11 && TimeUtils.now().getMinute() < 20){//每天第一次开启
			warMgr.ROUND = 0;
		}
		warMgr.FIGHT_TYPE = (byte)0;
		warMgr.FIGHT_NUM = 4;//数及城市
		warMgr.CAMP_NUM = 8;//军营数据
		warMgr.IS_REFRESH = true;
		warMgr.startWar(true,null);
		_log.info("市长争夺战开始:");

	}
	public static void main(String[] args) {
		System.out.println(TimeUtils.getWarTime(" 11:00:00"));
		System.out.println(TimeUtils.now().getMinute());
		System.out.println(TimeUtils.getWarTime(" 19:00:00")-TimeUtils.getWarTime(" 11:00:00"));
	}

}
