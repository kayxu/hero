package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.HeartbeatRequest;
import com.joymeng.game.net.response.HeartbeatResponse;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class HeartbeatService extends AbstractJoyService {

	static Logger logger = LoggerFactory.getLogger(HeartbeatService.class);

	private HeartbeatService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleHeartbeat(HeartbeatRequest request,
			ServicesContext context) {
//		logger.info("echo from " + request.getJoyID()+" service "+this.getClass().getName());
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
//			gameWorld.sendFail((byte) ErrorMessage.NO_PLAYER);
			return null;
		}
		//设置玩家心跳时间
		player.setHeartTime(TimeUtils.nowLong());
		player.getReceive().incrementAndGet();
		logger.info("set heartTime,uid="+player.getId()+" time="+TimeUtils.now());
		//发送心跳响应
		HeartbeatResponse resp = new HeartbeatResponse();
		resp.setUserInfo(request.getUserInfo());
		JoyServiceApp.getInstance().sendMessage(resp);
		return null;
	}
}
