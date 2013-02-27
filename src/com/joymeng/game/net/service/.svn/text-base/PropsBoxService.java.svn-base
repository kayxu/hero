package com.joymeng.game.net.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.box.PropsBoxManager;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.PlayerLimit;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.PropsBoxRequest;
import com.joymeng.game.net.response.PropsBoxResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;
import com.sun.imageio.plugins.common.I18N;

@JoyMessageService
public class PropsBoxService extends AbstractJoyService{

	static Logger logger = LoggerFactory.getLogger(PropsBoxService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(PropsBoxRequest request, ServicesContext context) {
		//logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		
		byte type = request.getType();
		PropsBoxResp resp = new PropsBoxResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		boolean success;
		switch(type){
		case ProcotolType.GET_PROPS://打开宝箱获取something
		case ProcotolType.WOOD_ORE_HORSE://获取木材，矿石，马匹
		case ProcotolType.OPEN_NEW_BAG://打开新手大礼包
			int which = request.getWhich();
			int propsId = request.getPropsId();
			success = PropsBoxManager.getInstance().getAProp(which,propsId, player);
			if(!success){
				/*GameUtils.sendTip(
						new TipMessage("不合法的请求", ProcotolType.PROPS_BOX_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo());*/
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(GameConst.GAME_RESP_FAIL);
				return resp;
			}
			resp.setEquipIds(player.getData().getTempEquipIds());
			resp.setItemIds(player.getData().getTempItemIds());
			
			//resp.setGoodOrEquipId(player.getData().getTempGoodOrEquipIdOrNeedValue());
			resp.setWhat((byte)player.getData().getTempWhatForPropsBox());
			resp.setGoodOrEquipIdOrNeedValue(player.getData().getTempGoodOrEquipIdOrNeedValue());
			resp.setInPackageValue(player.getData().getTempGoodsOrEquipNumForPropsBox());			
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			QuestUtils.checkFinish(player, QuestUtils.TYPE2, true,propsId);
			return resp;
		case ProcotolType.OPEN_PACKAGE://打开包裹获取装备
			int whichPackage = request.getWhichPackage();
			int packageId = request.getPackageId();
			success = PropsBoxManager.getInstance().openPackageOnceAItem(whichPackage, packageId, player);
			if(!success){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(
							new TipMessage("Illegal request", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(
							new TipMessage("不合法的请求", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}
				
				return null;
			}
			resp.setTotalEquipForSelect(player.getData().getTempTotalEquipIds());
			resp.setTotalItemForSelect(player.getData().getTempTotalItemIds());
			//resp.setTotalForSelect(player.getData().get);
			resp.setEquipIds(player.getData().getTempEquipIds());
			resp.setItemIds(player.getData().getTempItemIds());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			QuestUtils.checkFinish(player, QuestUtils.TYPE2, true,packageId);
			return resp;
		case ProcotolType.GET_HERO:
			if(!player.checkHeroNumlimit()){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(
							new TipMessage("Recruit hero failed. The numbers of heroes have reach the maximum", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(
							new TipMessage("召唤将领失败，当前将领数已经到达上限", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}
				
				return null;
			}
			int id = request.getHeroCardId();
			success = PropsBoxManager.getInstance().getHero(id, player);
			if(!success){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(
							new TipMessage("Illegal request", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(
							new TipMessage("不合法的请求", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}
				return null;
			}
			resp.setHeroId(player.getData().getTempGetPlayerHeroId());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			QuestUtils.checkFinish(player, QuestUtils.TYPE2, true,id);
			return resp;
		case ProcotolType.CONFIRM_GET_HERO:
			PropsBoxManager.getInstance().confirmGetHero(player);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.CANCLE_GET_HERO:
			PropsBoxManager.getInstance().cancelGetHero(player);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.DIAMOND_CHIP://获取钻石筹码
			propsId = request.getPropsId();
			success = PropsBoxManager.getInstance().getDiamondAndChip(propsId, player);
			if(!success){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(
							new TipMessage("Illegal request", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(
							new TipMessage("不合法的请求", ProcotolType.PROPS_BOX_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}
				return null;
			}
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			QuestUtils.checkFinish(player, QuestUtils.TYPE2, true,propsId);
			return resp;
		/*case ProcotolType.OPEN_NEW_BAG://打开新手大礼包
			success = PropsBoxManager.getInstance().openNewBag(player);
			if(!success){
				resp.setCanOpenNewBag((byte)0);
			}
			else{
				resp.setCanOpenNewBag((byte)1);
			}
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;*/
		case ProcotolType.IS_NEED_NEW_BAG://是否需要出现新手大礼包
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			resp.setIsNeedShow((byte)Math.abs(player.getData().getNewBag() - 1));
			return resp;
		}
		return null;
	
	}
}
