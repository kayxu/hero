package com.joymeng.game.domain.activity;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import hirondelle.date4j.DateTime;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.chat.ChatChannel;
import com.joymeng.core.scheduler.SchedulerServer;
import com.joymeng.core.scheduler.SimpleJob;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightEventManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.job.ActivityTestJob;

public class ActivityTest extends ActivityBase {
	private static final Logger logger = LoggerFactory.getLogger(ActivityTest.class);

	public ActivityTest(byte type) {
		super(type);
	}
	//小时，分，秒
	private DateTime endDate = TimeUtils.today().plus(0, 0, 0, 15, 59, 4, DateTime.DayOverflow.Abort);
	private boolean open;

	@Override
	public void start(SchedulerServer scheduler) {
		JobDetail job = null;
		CronTrigger trigger = null;
		job = newJob(ActivityTestJob.class).withIdentity(
				"job" + this.getClass().getName(),
				"group" + this.getClass().getName()).build();
		trigger = newTrigger()
				.withIdentity("trigger" + this.getClass().getName(),
						"group" + this.getClass().getName())
				.withSchedule(cronSchedule("0 0/2 * * * ?")).build();
		scheduler.addJob(job, trigger);
		open = true;
//		GameUtils.sendWolrdMessage("活动开始了",ChatChannel.CHANNEL_SCROLL);
	}

	public void tick() {
		//当前时间小于结束时间则执行逻辑
		while (TimeUtils.now().lteq(endDate)) {
			try {
//				logger.info("active test1111111");
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void end() {
		// 结束
//		GameUtils.sendWolrdMessage("活动结束了",ChatChannel.CHANNEL_SCROLL);
		open = false;
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
