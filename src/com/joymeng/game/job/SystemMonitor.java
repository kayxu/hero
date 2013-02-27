/* 
 * Copyright 2005 - 2009 Terracotta, Inc. 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package com.joymeng.game.job;

import java.io.File;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.SendMail;
import com.joymeng.core.utils.ServerInfo;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.JoyServiceApp;

/**
 * 系统监控
 * @author admin
 *
 */
public class SystemMonitor implements Job {

	private static Logger _log = LoggerFactory.getLogger(SystemMonitor.class);

	/**
	 * Empty constructor for job initilization
	 */
	public SystemMonitor() {
	}

	/**
	 * <p>
	 * Called by the <code>{@link org.quartz.Scheduler}</code> when a
	 * <code>{@link org.quartz.Trigger}</code> fires that is associated with the
	 * <code>Job</code>.
	 * </p>
	 * 
	 * @throws JobExecutionException
	 *             if there is an exception while executing the job.
	 */
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String serverinfo =new ServerInfo().toString();
		String worldinfo=World.getInstance().info();
		String[] splits = serverinfo.split("\n");
//		for (String str : splits)
//		{
//			
//		}
		SendMail sm=SendMail.getInstance();
		sm.sendFile(new File("./log/joy.log"));
	}

}