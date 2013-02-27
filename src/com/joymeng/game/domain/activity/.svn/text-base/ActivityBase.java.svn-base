package com.joymeng.game.domain.activity;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;

import com.joymeng.core.scheduler.SchedulerServer;
import com.joymeng.core.scheduler.SimpleJob;
import com.joymeng.game.domain.role.PlayerCharacter;

/**
 * 游戏活动的基类
 * @author admin
 *
 */
public class ActivityBase {
	byte type;
	//定时器
	String cron;
	//job
	
	//参与活动的玩家
	public ActivityBase(byte type){
		this.type=type;
	}
	
	/**
	 *发送消息,向所有参与活动的玩家发送 
	 * @param message
	 */
	public void sendMessage(String message){
		
	}
	/**
	 * 开始计划任务
	 * @param scheduler
	 */
	public void start(SchedulerServer scheduler){
//		JobDetail job = null;
//		CronTrigger trigger = null;
//		job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
//		trigger = newTrigger().withIdentity("trigger1", "group1")
//				.withSchedule(cronSchedule("0 0/1 * * * ?")).build();
//		scheduler.addJob(job, trigger);
	}
	/**
	 * 活动结束
	 */
	public void end(){
		
	}
	/**
	 * 进入活动检测
	 * @param player
	 * @return
	 */
	public boolean enter(PlayerCharacter player){
		return false;
	}
	/**
	 * 退出活动检测
	 * @param player
	 * @return
	 */
	public boolean exit(PlayerCharacter player){
		return false;
	}
	/**
	 * 参与活动检测
	 * @param player
	 * @return
	 */
	public boolean check(PlayerCharacter player){
		return false;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	
}
