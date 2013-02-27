package com.joymeng.game.job;

import hirondelle.date4j.DateTime;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.box.ExtremeBoxManager;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.shop.GoodsManager;
import com.joymeng.game.domain.world.World;

/**
 * 每天0点做相应的处理
 * @author madi
 *
 */
public class DoWhenEndOfDayJob implements Job{

	private static final Logger logger = LoggerFactory.getLogger(DoWhenEndOfDayJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		//最高同时在线：一天中同时在线的玩家数量的最大值，同时记录该时间
//		logger.info("time=" + CountOnlinePlayerMaxNumJob.when + " maximumNum=" + CountOnlinePlayerMaxNumJob.MAX_NUM);
//		//清空数值，以便重新记录新的一天的数据
//		CountOnlinePlayerMaxNumJob.MAX_NUM = 0;
//		
//		//平均同时在线人数：每天每15分钟统计一次在线人数，平均在线为他们的平均值
//		int total = 0;
//		List<Integer> nums = CountOnlinePlayerMaxNumPer15MinJob.onlinePlayerNums;
//		for(int num : nums){
//			total += num;
//		}
//		if(nums.size() != 0){
//			int average = total/nums.size();
//			logger.info("recordTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + "averageNum=" + average);
//		}
//		
//		//清空数值，以便重新记录新的一天的数据
//		CountOnlinePlayerMaxNumPer15MinJob.onlinePlayerNums.clear();
		
		
		//流失帐号数量：连续7天都未登录的账号数
//		List<RoleData> roles = DBManager.getInstance().getWorldDAO().getAllRoles();
//		int lossOfAccountNum = 0;
//		for(RoleData role : roles){
//			long lastLoginTime = role.getLastLoginTime();
//			long nowTime = TimeUtils.nowLong();
//			long intervalTime = nowTime - lastLoginTime;
//			int day = (int)intervalTime/(1000 * 60 * 60 * 24); 
//			if(day >= 7){
//				lossOfAccountNum ++;
//			}
//		}
//		logger.info("recordTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + "lossNum=" + lossOfAccountNum);
		
		//获得当前时间
		DateTime now=TimeUtils.now();
		//获得前一天时间
		String time=now.plusDays(-1).format(TimeUtils.FORMAT);
		MongoUtil.doJob(time,time);
		//大转盘每日消耗筹码数量
		long costChipPerDay = ExtremeBoxManager.getInstance().costChipPerDay;
		logger.info("recordTime=" + time + "costChipPerDay=" + costChipPerDay+" time="+time);
		ExtremeBoxManager.costChipPerDay = 0;
	}

}
