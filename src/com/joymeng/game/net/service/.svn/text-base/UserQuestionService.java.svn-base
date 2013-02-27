package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.question.UserQuestionManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.UserQuestionRequest;
import com.joymeng.game.net.response.UserQuestionResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class UserQuestionService extends AbstractJoyService{

static Logger logger = LoggerFactory.getLogger(UserQuestionService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(UserQuestionRequest request, ServicesContext context) {
	logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		
		byte type = request.getType();
		UserQuestionResp resp = new UserQuestionResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		switch(type){
		case ProcotolType.POST_QUESTION://提交联系客服问题
			String content = request.getContent();
			boolean success = UserQuestionManager.getInstance().postUserQuestion(player, content);
			if(success){
				player.getData().setLastPostQuestionTime(TimeUtils.nowLong());
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				return resp;
			}
		}
		return null;
	}
	
}
