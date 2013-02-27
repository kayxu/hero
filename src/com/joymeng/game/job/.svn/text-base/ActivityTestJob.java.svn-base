package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.joymeng.game.domain.activity.ActivityBase;
import com.joymeng.game.domain.activity.ActivityConst;
import com.joymeng.game.domain.activity.ActivityTest;
import com.joymeng.game.domain.activity.GameActivityManager;

public class ActivityTestJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		System.out.println("active test");
		ActivityBase ab=GameActivityManager.getInstance().get(ActivityConst.TYPE_TEST);
		ActivityTest at=(ActivityTest)ab;
		at.tick();
	}

}
