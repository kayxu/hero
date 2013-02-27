package com.joymeng.game.net.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.MessageUtil;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.item.ItemConst;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.game.net.request.ItemRequest;
import com.joymeng.game.net.response.ItemResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 道具
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class ItemService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(ItemService.class);

	private ItemService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleItem(ItemRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
//			gameWorld.sendFail((byte) ErrorMessage.NO_PLAYER);
			return null;
		}

		byte type = request.getType();
		logger.info(">>>>>>>>>item type=" + type);
		ItemResp resp = new ItemResp();
		resp.setType(type);
		resp.setUserInfo(request.getUserInfo());
		UserInfo info=request.getUserInfo();
		switch (type) {
		case ProcotolType.ITEM_GET:// 获取用户背包数据     装备和道具数据
//			player.getPlayerStorageAgent().addE();
			List<Cell> goods = player.getPlayerStorageAgent().getGoods();
			RespModuleSet rms = new RespModuleSet(ProcotolType.ITEMS_RESP);
			if(goods != null ){
				for(Cell cell : goods){
					if(cell.getItem().isProp()){
						rms.addModule(cell);
					}else{
						Equipment e = (Equipment)cell.getItem();
						rms.addModule(e);
					}
				}
			}
			AndroidMessageSender.sendMessage(rms, player);
			return null;
		case ProcotolType.ITEM_GET_EQUIMENT:// 用户背包装备数据
			//player.getPlayerStorageAgent().test();
			List<Cell> items = player.getPlayerStorageAgent().getGoods(
					Item.ITEM_EQUIPMENT);
			RespModuleSet rms1 = new RespModuleSet(ProcotolType.ITEMS_RESP);
			if(items != null ){
				for (Cell cell : items) {
						Equipment e = (Equipment)cell.getItem();
						rms1.addModule(e);
					}
			}
			AndroidMessageSender.sendMessage(rms1, player);
			return null;
		case ProcotolType.ITEM_GET_PROPS:// 用户道具数据
			List<Cell> items2 = player.getPlayerStorageAgent().getGoods(
					Item.ITEM_PROPS);
			RespModuleSet rms2 = new RespModuleSet(ProcotolType.ITEMS_RESP);
			if(items2 != null ){
				for (Cell cell : items2) {
					rms2.addModule(cell);
				}
			}
			AndroidMessageSender.sendMessage(rms2, player);
			return null;
		case ProcotolType.ITEM_ADD_EQUIMENT:// 添加装备到背包
			int eId = request.getItemId();
			Equipment e = player.getPlayerBuilgingManager().buyEquipment(eId);
//			resp.setEqu(e);
//			resp.setResult(GameConst.GAME_RESP_SUCCESS);
//			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			RespModuleSet rms9=new RespModuleSet(ProcotolType.ITEMS_RESP);
			if(e!= null)
			{
				if(e.getTip().getResult() == 1){
					rms9.addModule(e);
					//写入日志  玩家 在日期xx购买装备id
					GameLog.logPlayerEvent(player, LogEvent.WEAPON_BUY, new LogBuffer().add(eId));
				}
				GameUtils.sendTip(e.getTip(), info,GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("装备不存在！",ProcotolType.ITEMS_RESP,GameConst.GAME_RESP_FAIL,ProcotolType.ITEM_ADD_EQUIMENT), info,GameUtils.FLUTTER);
			}
			AndroidMessageSender.sendMessage(rms9,player);
			return null;
		case ProcotolType.ITEM_ADD_PROPS:// 添加道具到背包
			int pId = request.getItemId();//33
			int num2 = request.getNum();
			Props flag2 = player.getPlayerStorageAgent().addProps(
					pId, num2);
			if (flag2 != null)
				resp.setTrueOrFalse((byte) 1);
			else
				resp.setTrueOrFalse((byte) 0);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ITEM_USER_PROPS:// 使用道具
			int pId2 = request.getItemId();
			return resp;
		case ProcotolType.ITEM_USER_EQUIMENT:// 使用装备
			int eId2 = request.getItemId();
			int heroId = request.getHeroId();
//			eq1 = takeOffEquipment(id1, heroId);
//			eq2 = userEquipment(id2, heroId);
//			hero = owner.getPlayerHeroManager().getHero(heroId);
			Equipment ee = player.getPlayerStorageAgent().userEquipment(eId2,
					heroId,null);
			PlayerHero hero = player.getPlayerHeroManager().getHero(heroId);
//			World.getInstance().savePlayer(player);
			RespModuleSet rms3 = new RespModuleSet(ProcotolType.ITEMS_RESP);
			if(hero != null ){
				rms3.addModule(hero);
			}
			AndroidMessageSender.sendMessage(rms3, player);
			return null;
		case ProcotolType.ITEM_OFF_EQUIMENT://脱装备
//			int eId6 = request.getItemId();
//			int heroId6 = request.getHeroId();
//			Equipment ee2 =  player.getPlayerStorageAgent().takeOffEquipment(eId6,heroId6);
//			PlayerHero hero2 = player.getPlayerHeroManager().getHero(heroId6);
////			World.getInstance().savePlayer(player);
//			RespModuleSet rms4 = new RespModuleSet(ProcotolType.ITEMS_RESP);
//			if(hero2 != null ){
//				rms4.addModule(hero2);
//			}
//			AndroidMessageSender.sendMessage(rms4, player);
			return null;
		case ProcotolType.CUTOVER_EQUIMENT://切换
			int eqid1 = request.getItemId();
			int eqid2 = request.getNum();
			int hId = request.getHeroId();
			MessageUtil mess = player.getPlayerStorageAgent().cutoverEquipment(eqid1, eqid2, hId);
//			World.getInstance().savePlayer(player);
//			RespModuleSet rms5 = new RespModuleSet(ProcotolType.ITEMS_RESP);
//			if(mess.getModuleLst() != null && mess.getModuleLst().size() >0 ){
//				logger.info("返回集合大小："+mess.getModuleLst().size());
//				for(ClientModule c : mess.getModuleLst()){
//					if(c != null){
//						rms5.addModule(c);
//					}
//				}
//			}
//			AndroidMessageSender.sendMessage(rms5, player);
//			if(mess.getModuleLst() != null && mess.getModuleLst().size() >0 ){
//				for(ClientModule c : mess.getModuleLst()){
//					if(c != null){
//						if(c instanceof Equipment){
//							Equipment ex = (Equipment)c;
//							ex.setIsAdd((byte) 0);
//						}
//					}
//				}
//			}
			GameUtils.sendTip(mess.getTip(), info,GameUtils.FLUTTER);
			return null;
		case ProcotolType.ITEM_USER_UPGRADE://升级
			int eId3 = request.getItemId();
			byte upgradeType = request.getUpgradeType();
			int stoneId = request.getJzId();//加制石id,没有添0
			int hId2 = request.getHeroId();
			Equipment result = player.getPlayerStorageAgent().equiUpgrade(eId3, upgradeType, stoneId,hId2);
			resp.setUpgType(upgradeType);
			if(result != null && result.getTip().getResult() == 1){
				resp.setTrueOrFalse((byte) 1);
				resp.setEqu(result);
			}else{
				resp.setTrueOrFalse((byte) 0);//升级失败
				resp.setEqu(null);
			}
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ITEM_USER_DISMANT://拆解
			int eId4 = request.getItemId();
			System.out.println(eId4);
			List<Integer> dismantLst = player.getPlayerStorageAgent().dismantProps(eId4);
			System.out.println("拆解："+dismantLst.size());
			resp.setPropsIds(dismantLst);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ITEM_SPY_ON:
//			int ids = request.getHeroId();//id
//			int propsId = request.getItemId();//道具id
//			int herosId = request.getJzId();//id2
//			TipUtil tip = player.getPlayerStorageAgent().spyOn(userId, heroIds);
//			if(!tip.isResult()){
//				GameUtils.sendTip(tip.getTip(), request.getUserInfo());
//				resp.setResult(GameConst.GAME_RESP_FAIL);
//				resp.setErrorCode(GameConst.GAME_RESP_FAIL);
//				return resp;
//			}
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return null;
		case ProcotolType.ITEM_USE_PROPS:
			int ids = request.getHeroId();//id
			int propsId = request.getItemId();//道具id
			int herosId = request.getJzId();//id2
			TipUtil tipss = player.getPlayerStorageAgent().getPis().userPropsEffic(ids, propsId,herosId, false);
			if(tipss.isResult()){
				if(tipss.getProcotolType() == ItemConst.SPY_ON_TYPE){
					resp.setType(ProcotolType.ITEM_SPY_ON);
					resp.setClientLst(tipss.getLst());
					resp.setResult(GameConst.GAME_RESP_SUCCESS);
					resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
					GameUtils.sendTip(tipss.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
					return resp;
				}else {
					GameUtils.sendTip(tipss.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
				}
			}else{
				GameUtils.sendTip(tipss.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
			}
			return resp;
		case ProcotolType.ITEM_DEL_DELAY:
			byte tt = request.getUpgradeType();
			byte ts = player.getPlayerStorageAgent().getPis().deleteDelay(tt);
			resp.setTrueOrFalse(ts);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.EXCHANGE_STAR://兑换名将卡
			TipMessage tip = player.getPlayerStorageAgent().exchangeStar();
			GameUtils.sendTip(tip, request.getUserInfo(),GameUtils.FLUTTER);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		}
		return null;
	}
}
