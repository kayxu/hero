package com.joymeng.game.net.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.base.net.service.EchoService;
import com.joymeng.core.fight.FightLog;
import com.joymeng.game.ErrorMessage;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.war.UserWarData;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.Power;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.NationRequest;
import com.joymeng.game.net.response.NationResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 世界
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class NationService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(EchoService.class);

	private NationService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleNation(NationRequest request, ServicesContext context) {
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
		logger.info(">>>>>>>>>nation type=" + type);
		NationResp resp = new NationResp();
		resp.setType(type);
		resp.setUserInfo(request.getUserInfo());
		logger.info(">>>>>>>>>request UserInfo="+type+"|userinfo："+request.toString());
		switch (type) {
		case ProcotolType.NATION_ALL_STATE:
			List<ClientModuleBase> arrs = new ArrayList<ClientModuleBase>();
			HashMap<Integer,Nation> statemap = player.getPlayerNationManager().getAllState();
			HashMap<Integer,Nation> countryMap = player.getPlayerNationManager().getAllCountry();
			HashMap<Integer,Nation> map = new HashMap<Integer, Nation>();
			if(statemap != null && statemap.size() >0 && countryMap != null && countryMap.size() >0){
				map.putAll(statemap);
				map.putAll(countryMap);
				for(Nation n : countryMap.values()){//放国家的英雄
					if(!"".equals(n.getHeroInfo())){
						SimplePlayerHero playerHero = new SimplePlayerHero(null,n.getHeroInfo());
						if(playerHero != null){
							RespModuleSet cherorms = new RespModuleSet(ProcotolType.HERO_RESP);// 模块消息
							cherorms.addModule(playerHero);
							AndroidMessageSender.sendMessage(cherorms, player);
						}
					}
				}
			}

			System.out.println("***************："+map.keySet());
			if(map != null && map.size() >0){
				for(Nation n : map.values()){
					arrs.add(n);
//					if(!"".equals(n.getHeroInfo())){
//						SimplePlayerHero playerHero = new SimplePlayerHero(null,n.getHeroInfo());
//						if(playerHero != null){
//							arrs.add(playerHero);
//						}
//					}
					
				}
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				resp.setFight(arrs);
			}else{
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}
			return resp;
		case ProcotolType.NATION_ALL_CITY:
			int stateId = request.getStateId();
			Nation state = NationManager.getInstance().getNation(stateId);
			List<ClientModuleBase> nations = player.getPlayerNationManager().getAllCity(stateId);
			if(nations == null || nations .size() == 0){
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}
			else{
				if(state!=null){
					nations.add(state);
					if(!"".equals(state.getHeroInfo())){
						SimplePlayerHero playerHero = new SimplePlayerHero(null,state.getHeroInfo());
						if(playerHero != null){
							nations.add(playerHero);
						}
					}
				}
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				resp.setFight(nations);
			}
			return resp;
		case ProcotolType.NATION_MOTIFY_NAME:
			int id = request.getStateId(); 
			String name = request.getName();
			TipUtil tipps = player.getPlayerNationManager().setNationName(id, name);
			System.out.println("返回类型："+tipps);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			if(!tipps.isResult()){
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}else{
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				// ************************* rms
				RespModuleSet morms = new RespModuleSet(ProcotolType.USER_INFO_RESP);// 模块消息
				morms.addModule(player.getData());
				AndroidMessageSender.sendMessage(morms, player);
				// ************************* rms
			}
			//发送
			GameUtils.sendTip(tipps.getTip(),player.getUserInfo(),GameUtils.FLUTTER);
			return resp;
		case ProcotolType.NATION_MIND_RES:// 获取资源
			byte resType = request.getResType();
			byte page = request.getPage();
			int stateid = request.getStateId();
			player.getPlayerNationManager().getAllResource(page, resType,stateid);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ADD_SOLDIER:
			int veinsId = request.getStateId();
			int playerId = request.getHeroId();
			String add = request.getName();
			TipUtil tip = player.getPlayerNationManager().addSoliderToVeins(veinsId, playerId, add);
			GameUtils.sendTip(tip.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.NATION_FIGHT://争夺战页面
			int nationId = request.getStateId();
			List<ClientModuleBase> arr = player.getPlayerNationManager().nationFight(nationId);
			List<UserWarData> arr1 = player.getPlayerNationManager().nationFightUserWarData(nationId);
			if(arr != null && arr.size() >= 0){
				resp.setFight(arr);
				resp.setTopLst(arr1);
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				logger.info("区域id"+nationId+"用户："+player.getId()+"|获取到数据："+arr.size());
			}else{
				logger.info("区域id"+nationId+"用户："+player.getId()+"|获取到数据：错误");
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			}
//			GameUtils.sendTip(new TipMessage("TTTTTTTTT", ProcotolType.ARENA_RESP,
//					GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			return resp;
		case ProcotolType.NATION_WAR_DATA://积分
			int nationIdas = request.getStateId();
			List<UserWarData> arrWarData = player.getPlayerNationManager().nationFightUserWarData(nationIdas);
			resp.setTopLst(arrWarData);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.TOP_WAR ://排行榜
			int nationIds = request.getStateId();
			ArrayList<UserWarData> lst= WarManager.getInstance().topScore(nationIds,player);
			resp.setTopLst(lst);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.NATION_ADD_SOLIDER://军营增兵
			int campId = request.getStateId();
			String addSolider = request.getName();
			TipUtil tips = player.getPlayerNationManager().moreTroops(campId, addSolider);
			GameUtils.sendTip(tips.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.NATION_POWER://战斗力
			int countyId = request.getStateId();
			List<Power> arrpower = NationManager.getInstance().getPower(countyId);
			// ************************* rms
			RespModuleSet prms = new RespModuleSet(ProcotolType.USER_INFO_RESP);// 模块消息
			if(arrpower != null && arrpower.size() > 0){
				for(Power p : arrpower){
					prms.addModule(p);
				}
			}
			AndroidMessageSender.sendMessage(prms, player);
			// ************************* rms
			FightLog.info("用户："+player.getId()+"|区域："+countyId+"|"+ProcotolType.NATION_POWER+"|成功");
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.WAR_SING://争夺战报名
			int nid =  request.getStateId();
			int userid =  request.getHeroId();
			WarManager.getInstance().addPushMap(nid, userid);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			FightLog.info("用户："+player.getId()+"|区域："+nid+"|"+ProcotolType.WAR_SING+"|成功");
			return resp;
		case ProcotolType.WAR_QUIT://争夺战退出
			int nids =  request.getStateId();
			int userids =  request.getHeroId();
			WarManager.getInstance().clearByTypeAndPlayer(nids, userids);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			FightLog.info("用户："+player.getId()+"|区域："+nids+"|"+ProcotolType.WAR_QUIT+"|成功");
			return resp;
		case ProcotolType.RESOURCES_SING://争夺战报名
			int nidr =  request.getStateId();
			int useridr =  request.getHeroId();
			NationManager.getInstance().addPushMap(nidr, useridr);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			FightLog.info("用户："+player.getId()+"|区域："+nidr+"|"+ProcotolType.RESOURCES_SING+"|成功");
			return resp;
		case ProcotolType.RESOURCES_QUIT://争夺战报名
			int nidrs =  request.getStateId();
			int useridrs =  request.getHeroId();
			NationManager.getInstance().clearByTypeAndPlayer(nidrs, useridrs);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			FightLog.info("用户："+player.getId()+"|区域："+nidrs+"|"+ProcotolType.RESOURCES_QUIT+"|成功");
			return resp;
		case ProcotolType.KEEP_UNDER://换将
			int changeHeroid =  request.getHeroId();
			String changeSolider = request.getName();
			TipUtil ntips = player.keepUnder(changeHeroid, changeSolider);
			if(ntips.isResult()){
				resp.setFight(ntips.getLst());
			}
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		}
		return null;
	}
}
