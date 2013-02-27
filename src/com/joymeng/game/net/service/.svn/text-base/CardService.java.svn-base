package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.CardRequest;
import com.joymeng.game.net.response.CardResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class CardService extends AbstractJoyService{

	static Logger logger = LoggerFactory.getLogger(CardService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(CardRequest request, ServicesContext context) {
	//logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		
		byte type = request.getType();
		CardResp resp = new CardResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		switch(type){
		case ProcotolType.ENTER://进入
			player.getCardManager().enter();
			resp.setCards(player.getCardManager().getCards());
			resp.setChance((byte)player.getCardManager().getFlipChance());
			resp.setRotateChance((byte)player.getCardManager().getRotateChance());
			resp.setNextTime(player.getCardManager().getNextTime());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			//logger.info("=================进入翻牌 ==================" + player.getData().getName());
			//logger.info("翻牌机会：" + player.getCardManager().getFlipChance());
			//logger.info("旋转机会：" + player.getCardManager().getRotateChance());
			//logger.info("倒计时：" + player.getCardManager().getNextTime());
			return resp;
		case ProcotolType.FLIP_CARDS://翻牌
			boolean success = player.getCardManager().showClickResult(request.getIndex());
			if(!success){
				return null;
			}
			resp.setShowCardsList(player.getCardManager().getShowCardsList());
			resp.setCards(player.getCardManager().getCards());
			resp.setChance((byte)player.getCardManager().getFlipChance());
			resp.setRotateChance((byte)player.getCardManager().getRotateChance());
			resp.setNextTime(player.getCardManager().getNextTime());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			//logger.info("==================进行翻牌=================" + player.getData().getName());
			//logger.info("翻牌机会：" + player.getCardManager().getFlipChance());
			//logger.info("旋转机会：" + player.getCardManager().getRotateChance());
			//logger.info("倒计时：" + player.getCardManager().getNextTime());
			return resp;
		case ProcotolType.GET_CHANCE://倒计时到，获取翻牌机会增加
			//logger.info("GET_CHANCE");
			success = player.getCardManager().getChance();
			if(!success){
				GameUtils.sendTip(
						new TipMessage("illegal request!", ProcotolType.CARD_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				//logger.info("=================翻牌内部倒计时请求不合法，不在允许时间范围内==================" + player.getData().getName());
				return null;
			}
			resp.setCards(player.getCardManager().getCards());
			resp.setChance((byte)player.getCardManager().getFlipChance());
			resp.setRotateChance((byte)player.getCardManager().getRotateChance());
			resp.setNextTime(player.getCardManager().getNextTime());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			//logger.info("==================翻牌内部倒计时到=================" + player.getData().getName());
			//logger.info("翻牌机会：" + player.getCardManager().getFlipChance());
			//logger.info("旋转机会：" + player.getCardManager().getRotateChance());
			//logger.info("倒计时：" + player.getCardManager().getNextTime());
			return resp;
		case ProcotolType.ROTATE_CARD_FACE://旋转牌
			success = player.getCardManager().rotateCertainCard(request.getIndex());
			if(!success){
				GameUtils.sendTip(
						new TipMessage("You can not rotate this card", ProcotolType.CARD_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				//logger.info("=================can not rotate this card==================");
				return null;
			}
			resp.setCards(player.getCardManager().getCards());
			resp.setChance((byte)player.getCardManager().getFlipChance());
			resp.setRotateChance((byte)player.getCardManager().getRotateChance());
			resp.setNextTime(player.getCardManager().getNextTime());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			//logger.info("==================旋转牌====================" + player.getData().getName());
			//logger.info("翻牌机会：" + player.getCardManager().getFlipChance());
			//logger.info("旋转机会：" + player.getCardManager().getRotateChance());
			//logger.info("倒计时：" + player.getCardManager().getNextTime());
			return resp;
		case ProcotolType.GET_AWARD://领奖
			success = player.getCardManager().getAward();
			if(!success){
				GameUtils.sendTip(
						new TipMessage("You can not reward!", ProcotolType.CARD_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				//logger.info("=================can not reward!==================");
				return null;
			}
			resp.setCards(player.getCardManager().getCards());
			resp.setChance((byte)player.getCardManager().getFlipChance());
			resp.setRotateChance((byte)player.getCardManager().getRotateChance());
			resp.setNextTime(player.getCardManager().getNextTime());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			//logger.info("==================领奖=================" + player.getData().getName());
			//logger.info("翻牌机会：" + player.getCardManager().getFlipChance());
			//logger.info("旋转机会：" + player.getCardManager().getRotateChance());
			//logger.info("倒计时：" + player.getCardManager().getNextTime());
			return resp;
		case ProcotolType.OUT_SHOW:
			player.getCardManager().outShowNextTime();
			resp.setChance((byte)player.getCardManager().getFlipChance());
			resp.setNextTime(player.getCardManager().getNextTime());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			//logger.info("==================登录时请求获取翻牌机会倒计时=================" + player.getData().getName());
			//logger.info("翻牌机会：" + player.getCardManager().getFlipChance());
			//logger.info("旋转机会：" + player.getCardManager().getRotateChance());
			//logger.info("倒计时：" + player.getCardManager().getNextTime());
			return resp;
		case ProcotolType.OUT_GET_CHANCE://牌局外倒计时到，获取翻牌机会增加
			success = player.getCardManager().getChance();
			if(!success){
				GameUtils.sendTip(
						new TipMessage("illegal request!", ProcotolType.CARD_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				//logger.info("=================翻牌外倒计时请求不合法，不在允许时间范围内==================" + player.getData().getName());
				return null;
			}
			resp.setChance((byte)player.getCardManager().getFlipChance());
			resp.setNextTime(player.getCardManager().getNextTime());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			//logger.info("==================翻牌外部倒计时到=================" + player.getData().getName());
			//logger.info("翻牌机会：" + player.getCardManager().getFlipChance());
			//logger.info("旋转机会：" + player.getCardManager().getRotateChance());
			//logger.info("倒计时：" + player.getCardManager().getNextTime());
			return resp;
		}
		return null;
	}
	
}
