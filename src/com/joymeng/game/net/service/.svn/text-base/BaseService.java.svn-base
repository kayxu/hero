package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.message.JoyRequest;
import com.joymeng.services.core.service.AbstractJoyService;
import com.joymeng.services.core.service.JoyService;

public class BaseService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(FightService.class);
	public PlayerCharacter  init(JoyRequest request,JoyService service){
		logger.info("receive from " + request.getJoyID() + " service"
				+ service.getClass().getName());
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(request.getUserInfo()
				.getUid());
		if (player == null) {
			logger.info("玩家不存在，id=" + request.getUserInfo().getUid());
			return null;
		}
//		player.getReceive().incrementAndGet();
//		logger.info("player receive Message num="+player.getReceive().intValue());
		return player;
	}
}
