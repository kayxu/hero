package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.ExitGameRequest;
import com.joymeng.game.net.response.ExitGameResp;
import com.joymeng.game.net.response.HeroResp;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 *  退出游戏
 * @author admin
 *
 */
@JoyMessageService
public class ExitGameService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(ExitGameService.class);


	@JoyMessageHandler
	public JoyProtocol handleExitGame(ExitGameRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			logger.info("玩家不存在，id="+request.getUserInfo().getUid());
			return null;
		}
		ExitGameResp resp = new ExitGameResp();
		resp.setUserInfo(player.getUserInfo());
		JoyServiceApp.getInstance().sendMessage(resp);
		gameWorld.logout(player);
		return null;
	}
}
