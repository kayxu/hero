package com.joymeng.game.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.db.DBManager;

public class DoPerWeekJob implements Job{
	
	private static final Logger logger = LoggerFactory.getLogger(DoPerWeekJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//活跃用户数量：上周登录过游戏的用户数量
//		List<RoleData> roles = DBManager.getInstance().getWorldDAO().getAllRoles();
		int loginNumLastWeek = 0;
//		for(RoleData role : roles){
//			long lastLoginTime = role.getLastLoginTime();
//			long nowTime = TimeUtils.nowLong();
//			long intervalTime = nowTime - lastLoginTime;
//			int day = (int) intervalTime/(1000*60*60*24);
//			if(day < 7){
//				loginNumLastWeek ++;
//			}
//		}
		logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + "loginNumLastWeek=" + loginNumLastWeek);
		
	}

}
