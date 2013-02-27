package com.joymeng.game.net.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.domain.world.OnlineNum;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.GameListRequest;
import com.joymeng.game.net.response.GameListResp;
import com.joymeng.game.net.response.ServerInnerResp;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;


@JoyMessageService
public class GameListService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(GameListService.class);

	private GameListService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleGameList(GameListResp resp,
			ServicesContext context) {
		List<OnlineNum>  list=resp.getGameOnlineList();
		for(OnlineNum on:list){
			System.out.println("id="+on.getGameId()+" name="+on.getGameName()+" status="+on.getGameState());
		}
		World.getInstance().gameOnlineList=list;
		return null;
	}

}
