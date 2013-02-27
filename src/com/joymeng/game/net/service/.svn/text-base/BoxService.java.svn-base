package com.joymeng.game.net.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.box.BoxConst;
import com.joymeng.game.domain.box.ExtremeBoxManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.BoxRequest;
import com.joymeng.game.net.response.BoxResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class BoxService extends AbstractJoyService{

	static Logger logger = LoggerFactory.getLogger(BoxService.class);
	
	
	/**
	 * @param request
	 * @param context
	 * @return
	 */
	@JoyMessageHandler
	public JoyProtocol handleService(BoxRequest request, ServicesContext context) {
		UUID uuid  =  UUID.randomUUID(); 
		logger.info("echo from start " + request.getJoyID()+" service"+this.getClass().getName()+" uuid="+uuid);

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		
		byte type = request.getCmd();
		BoxResp resp = new BoxResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		switch(type){
		case BoxConst.OPEN_BOX:
			ExtremeBoxManager.getInstance().openBox(player);
			resp.setRefresCostChip(player.getData().getTempRefreshCostChip());
			resp.setTurnCostChip(player.getData().getTempTurnCostChip());
			resp.setSelectedAwardIdString(player.getData().getSelectedAwardIdString());
			resp.setBeAwardedIdString(player.getData().getBeAwardedIdString());
			resp.setBoxIntegral(player.getData().getScore());
			resp.setChip(player.getData().getChip());
			resp.setTotalChance((byte)player.getData().getTotalChance());
			resp.setLeftChance((byte)player.getData().getLeftChance());

			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			break;
		case BoxConst.REFRESH_BOX:
			ExtremeBoxManager.getInstance().refresh(player);
			resp.setRefresCostChip(player.getData().getTempRefreshCostChip());
			resp.setTurnCostChip(player.getData().getTempTurnCostChip());
			resp.setSelectedAwardIdString(player.getData().getSelectedAwardIdString());
			resp.setBeAwardedIdString(player.getData().getBeAwardedIdString());
			resp.setChip(player.getData().getChip());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			break;
			
		case BoxConst.START:
			ExtremeBoxManager.getInstance().getPrize(player);
			resp.setRefresCostChip(player.getData().getTempRefreshCostChip());
			resp.setTurnCostChip(player.getData().getTempTurnCostChip());
			resp.setBoxIntegral(player.getData().getScore());
			resp.setChip(player.getData().getChip());
			resp.setTotalChance((byte)player.getData().getTotalChance());
			resp.setLeftChance((byte)player.getData().getLeftChance());
			resp.setBeAwardedIdString(player.getData().getBeAwardedIdString());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			break;
		case BoxConst.RECEIVE_AWARD:
			ExtremeBoxManager.getInstance().receiveAward(player);
			resp.setBoxIntegral(player.getData().getScore());
			resp.setTotalChance((byte)player.getData().getTotalChance());
			resp.setLeftChance((byte)player.getData().getLeftChance());
			resp.setTurnCostChip((byte)player.getData().getTempTurnCostChip());
			resp.setRefresCostChip((byte)player.getData().getTempRefreshCostChip());
			resp.setBeAwardedIdString(player.getData().getBeAwardedIdString());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			break;
			
		case BoxConst.RESTART:
			ExtremeBoxManager.getInstance().restart(player);
			resp.setBoxIntegral(player.getData().getScore());
			resp.setTotalChance((byte)player.getData().getTotalChance());
			resp.setLeftChance((byte)player.getData().getLeftChance());
			resp.setTurnCostChip((byte)player.getData().getTempTurnCostChip());
			resp.setRefresCostChip((byte)player.getData().getTempRefreshCostChip());
			resp.setSelectedAwardIdString(player.getData().getSelectedAwardIdString());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			break;
			
		case BoxConst.BUY://购买筹码
			int number = request.getNumber();
			boolean success = ExtremeBoxManager.getInstance().buyChip(number,player);
			if(!success){
				String failMsg = I18nGreeting.getInstance().getMessage("dimond.not.enough", null);
				GameUtils.sendTip(
						new TipMessage(failMsg, ProcotolType.BOX_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(GameConst.GAME_RESP_FAIL);
				return resp;
			}
			String msg = I18nGreeting.getInstance().getMessage("box.buychip", null);
			if(number != 0){
				GameUtils.sendTip(
						new TipMessage(msg, ProcotolType.BOX_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
			}	
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			break;
			
		//case BoxConst.RECEIVE_AWARD:
			
		}
		logger.info("echo from end " + request.getJoyID()+" service"+this.getClass().getName()+" uuid="+uuid);

		return resp;
		
	}
}
