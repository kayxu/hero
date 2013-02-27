package com.joymeng.game.domain.activity;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.scheduler.SchedulerServer;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.job.ActivityTestJob;

public class ActivityWar extends ActivityBase {
	private static final Logger logger = LoggerFactory.getLogger(ActivityWar.class);

	public ActivityWar(byte type) {
		super(type);
	}
	//long格式
	private long endDate = TimeUtils.nowLong();
	//类型
	private byte type ;
	//是否开启
	private boolean open = false;

	@Override
	public void start(SchedulerServer scheduler) {
		JobDetail job = null;
		CronTrigger trigger = null;
		job = newJob(ActivityTestJob.class).withIdentity(
				"job" + this.getClass().getName(),
				"group" + this.getClass().getName()).build();
		//每三十分钟进行
		trigger = newTrigger()
				.withIdentity("trigger" + this.getClass().getName(),
						"group" + this.getClass().getName())
				.withSchedule(cronSchedule("0 2/0 * * * ?")).build();
		scheduler.addJob(job, trigger);
		open = true;
		endDate = TimeUtils.nowLong() + 20 * 60 *1000;
//		GameUtils.sendWolrdMessage("活动开始了",ChatChannel.CHANNEL_SCROLL);
	}
	
	/**
	 * 停止,进行数据整理清空
	 */
	public void tick() {
		//当前时间小于结束时间则执行逻辑
		while (TimeUtils.nowLong() > endDate) {
			try {
				logger.info("ActivityCity 活动停止整理中...");
				Thread.sleep(1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void end() {
		while (TimeUtils.nowLong() > endDate) {
			// 结束
//			GameUtils.sendWolrdMessage("活动结束了",ChatChannel.CHANNEL_SCROLL);
			open = false;
		}
		
	}
	
	@Override
	public boolean enter(PlayerCharacter player) {
		return false;
	}

	@Override
	public boolean exit(PlayerCharacter player) {
		return false;
	}

	@Override
	public boolean check(PlayerCharacter player) {
		return false;
	}
}
