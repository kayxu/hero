package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.GameServerApp;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.ServerInnerRequest;
import com.joymeng.services.core.JoyServiceApp;

/**
 * 服务器间通讯
 * 通知登录服务器，当前服务器状态
 * @author admin
 *
 */
public class ServerInnerJob implements Job{
	private static final Logger logger = LoggerFactory.getLogger(ServerInnerJob.class);
	
	private static Logger _log = LoggerFactory.getLogger(ServerInnerJob.class);
	public ServerInnerJob(){
		
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		int num = World.getInstance().getOnlinePlayerNum();
		if(GameServerApp.specialLogin){
			return;
		}
		//处理服务器间通讯
		try{
			ServerInnerRequest req = new ServerInnerRequest(num,GameServerApp.type);
			//设置具体要通讯的服务器的instanceId,发送给登录服务器
			req.setDestInstanceID(GameConfig.serverList);
			JoyServiceApp.getInstance().sendMessage(req);
		}catch(Exception ex){
			logger.info("error ,str="+ex.toString());
		}
	}

}
