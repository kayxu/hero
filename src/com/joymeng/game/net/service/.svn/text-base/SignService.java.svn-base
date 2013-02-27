package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.SignRequest;
import com.joymeng.game.net.response.SignResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class SignService extends AbstractJoyService{

	static Logger logger = LoggerFactory.getLogger(SignService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(SignRequest request, ServicesContext context) {
	logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		
		byte type = request.getType();
		SignResp resp = new SignResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		switch(type){
		case ProcotolType.OPEN_UI://打开界面
			logger.info("=============打开签到界面");
			player.getSignManager().openUI();
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.SIGN_IN://提交
			logger.info("=============执行签到");
			TipUtil tip = player.getSignManager().signIn();
			if(tip.isResult()){
				/*GameUtils.sendTip(
						new TipMessage(tip.getResultMsg(), ProcotolType.SIGN_RESP,
								GameConst.GAME_RESP_SUCCESS, type), player.getUserInfo(),GameUtils.FLUTTER);*/
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				return resp;
			}
			else{
				GameUtils.sendTip(
						new TipMessage(tip.getResultMsg(), ProcotolType.SIGN_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(GameConst.GAME_RESP_FAIL);
				return resp;
			}
			
		}
		return null;
	}
}
