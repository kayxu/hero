package com.joymeng.game.net.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.game.ErrorMessage;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.game.net.request.RegionBattleRequest;
import com.joymeng.game.net.response.RegionBattleResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class RegionBattleService extends AbstractJoyService{

	static Logger logger = LoggerFactory.getLogger(RegionBattleService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(RegionBattleRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		
		byte type = request.getType();
		RegionBattleResp resp = new RegionBattleResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		int cityId;
		switch(type){
		case ProcotolType.ENTER_COUNTY_BATTLE_ACTION://按下进入县长争夺战按钮
			
			//玩家所在县所属城市
			cityId = player.getRegionBattleManager().findPlayerCityByCurrentCounty();
			
			//记录下当前玩家所在县所属市的id以便下次玩家请求翻页时判断
			player.getData().setTempCityId(cityId);	
			List<Nation> nationList = player.getRegionBattleManager().getCountiesByCity(cityId);
			for(Nation nation : nationList){
				int id=nation.getOccupyUser();
			
				PlayerCharacter tp =World.getInstance().getPlayer(id);// gameWorld.getPlayerByUid((long)nation.getOccupyUser());
				//tp.getData().getName();
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
			}
			if(null == nationList || nationList.size() == 0){
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}
			else{
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				Nation nation = player.getRegionBattleManager().getNationById(cityId);
				
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
				/*if(nation.getOccupyUsername() == null){
					nation.setOccupyUsername("");
				}*/
				
				//确保countyMap中的值不被改变
				List<Nation> nations = new ArrayList<Nation>();
				for(Nation n : nationList){
					nations.add(n);
				}
				nations.add(nation);
				resp.setNations(nations);
				sendMessage(nations,player);
			}
			return resp;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_LAST://向上翻页获取当前玩家所属市平行市
			cityId = player.getData().getTempCityId();
			if((cityId % 100) - 10 > 0){//如果可以向上翻页
				cityId -= 10;
			}
			//记录下当前玩家所在县所属市的id以便下次玩家请求翻页时判断
			player.getData().setTempCityId(cityId);
			List<Nation> nationList2 = player.getRegionBattleManager().getCountiesByCity(cityId);
			for(Nation nation : nationList2){
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
			}
			if(null == nationList2 || nationList2.size() == 0){
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}
			else{
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				Nation nation = player.getRegionBattleManager().getNationById(cityId);
				
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
				/*if(nation.getOccupyUsername() == null){
					nation.setOccupyUsername("");
				}*/
				//确保countyMap中的值不被改变
				List<Nation> nations = new ArrayList<Nation>();
				for(Nation n : nationList2){
					nations.add(n);
				}
				nations.add(nation);
				resp.setNations(nations);
				sendMessage(nations,player);
			}
			return resp;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_NEXT://向下翻页获取当前玩家所属市平行市
			cityId = player.getData().getTempCityId();
			if((cityId % 100) + 10 <=80){//如果可以向下翻页
				cityId += 10;
			}
			//记录下当前玩家所在县所属市的id以便下次玩家请求翻页时判断
			player.getData().setTempCityId(cityId);
			List<Nation> nationList3 = player.getRegionBattleManager().getCountiesByCity(cityId);
			for(Nation nation : nationList3){
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
				
			}
			if(null == nationList3 || nationList3.size() == 0){
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}
			else{
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				Nation nation = player.getRegionBattleManager().getNationById(cityId);
				
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
				/*if(nation.getOccupyUsername() == null){
					nation.setOccupyUsername("");
				}*/
				//确保countyMap中的值不被改变
				List<Nation> nations = new ArrayList<Nation>();
				for(Nation n : nationList3){
					nations.add(n);
				}
				nations.add(nation);
				resp.setNations(nations);
				sendMessage(nations,player);
			}
			return resp;
		case ProcotolType.SELECT_COUNTRY://选择国家，展现此国家下的所有州
			int countryId = request.getCountryId();
			/*if(countryId == 0){
				return null;
			}*/
			List<Nation> needStates = player.getRegionBattleManager().getStatesByCountry(countryId);
			int stateId3 = player.getRegionBattleManager().findPlayerStateByCurrentCounty();
			//控制占领玩家名称
			for(Nation nation : needStates){
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
			}
			resp.setStateId(stateId3);
			resp.setNations(needStates);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ENTER_MAP://进入世界地图
			List<Nation> countriesAndStates = player.getRegionBattleManager().getAllCountries();
			//int countryId2 = countriesAndStates.get(0).getId();
			int countryId2 = player.getRegionBattleManager().findPlayerCountryByCurrentCounty();
			int stateId2 = player.getRegionBattleManager().findPlayerStateByCurrentCounty();
			
			List<Nation> states = player.getRegionBattleManager().getStatesByCountry(countryId2);
			for(Nation nation : states){
				countriesAndStates.add(nation);
			}
			
			//控制占领玩家名称
			for(Nation nation : countriesAndStates){
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
			}
			resp.setStateId(stateId2);
			resp.setWhichPanel((byte)(countryId2/1000 - 1));
			resp.setNations(countriesAndStates);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ENTER_CERTAIN_STATE://进入特定州   或市
			int stateId = request.getStateId();
			if(stateId % 100 == 0){//如果是州
				List<Nation> cities = player.getRegionBattleManager().getCitiesByState(stateId);
				//int needCityId = cities.get(0).getId();//特定州的第一个市的id
				//int needCityId = player.getRegionBattleManager().findPlayerCityByNativeId();//定位到特定州玩家所在市
				int needCityId = 0;
				int playerAtState = player.getRegionBattleManager().findPlayerStateByCurrentCounty();
				if(playerAtState == stateId){//玩家进入自己所在的州
					needCityId = player.getRegionBattleManager().findPlayerCityByNativeId();//定位到特定州玩家所在市
				}
				else{
					needCityId = cities.get(0).getId();//特定州的第一个市的id
				}
				//记录下当前玩家所在县所属市的id以便下次玩家请求翻页时判断
				player.getData().setTempCityId(needCityId);	
				List<Nation> nationList4 = player.getRegionBattleManager().getCountiesByCity(needCityId);
				for(Nation nation : nationList4){
					
					PlayerCharacter tp =World.getInstance().getPlayer(nation.getOccupyUser());// gameWorld.getPlayerByUid((long)nation.getOccupyUser());
					if(null != tp){
						nation.setOccupyUsername(tp.getData().getName());
					}
					else{
						nation.setOccupyUsername("");
					}
					
				}
				if(null == nationList4 || nationList4.size() == 0){
					resp.setResult(GameConst.GAME_RESP_FAIL);
					resp.setErrorCode(ErrorMessage.NO_BUILD);
				}
				else{
					resp.setResult(GameConst.GAME_RESP_SUCCESS);
					resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
					Nation nation = player.getRegionBattleManager().getNationById(needCityId);
					PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
					if(null != tp){
						nation.setOccupyUsername(tp.getData().getName());
					}
					else{
						nation.setOccupyUsername("");
					}
					/*if(nation.getOccupyUsername() == null){
						nation.setOccupyUsername("");
					}*/
					
					//确保countyMap中的值不被改变
					List<Nation> nations = new ArrayList<Nation>();
					for(Nation n : nationList4){
						nations.add(n);
					}
					nations.add(nation);
					resp.setNations(nations);
					sendMessage(nations,player);
					
				}
				return resp;
			}
			else{
				List<Nation> counties = player.getRegionBattleManager().getCountiesByCity(stateId);//此处stateId为城市id
				for(Nation nation : counties){
					PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
					if(null != tp){
						nation.setOccupyUsername(tp.getData().getName());
					}
					else{
						nation.setOccupyUsername("");
					}
				}
				if(null == counties || counties.size() == 0){
					resp.setResult(GameConst.GAME_RESP_FAIL);
					resp.setErrorCode(ErrorMessage.NO_BUILD);
				}
				else{
					resp.setResult(GameConst.GAME_RESP_SUCCESS);
					resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
					Nation nation = player.getRegionBattleManager().getNationById(stateId);
					PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
					if(null != tp){
						nation.setOccupyUsername(tp.getData().getName());
					}
					else{
						nation.setOccupyUsername("");
					}
					/*if(nation.getOccupyUsername() == null){
						nation.setOccupyUsername("");
					}*/
					//确保countyMap中的值不被改变
					List<Nation> nations = new ArrayList<Nation>();
					for(Nation n : counties){
						nations.add(n);
					}
					nations.add(nation);
					resp.setNations(nations);
					sendMessage(nations,player);
				}
				return resp;
			}
			
		case ProcotolType.BACK_TO_BATTLE_REGION://返回战斗区域
			//玩家所在县所属城市
			cityId = request.getCityId();
			
			//记录下当前玩家所在县所属市的id以便下次玩家请求翻页时判断
			player.getData().setTempCityId(cityId);	
			List<Nation> nationList5 = player.getRegionBattleManager().getCountiesByCity(cityId);
			for(Nation nation : nationList5){
				
				PlayerCharacter tp =World.getInstance().getPlayer(nation.getOccupyUser());// gameWorld.getPlayerByUid((long)nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
				
			}
			if(null == nationList5 || nationList5.size() == 0){
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}
			else{
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				Nation nation = player.getRegionBattleManager().getNationById(cityId);
				if(nation.getOccupyUsername() == null){
					nation.setOccupyUsername("");
				}
				
				//确保countyMap中的值不被改变
				List<Nation> nations = new ArrayList<Nation>();
				for(Nation n : nationList5){
					nations.add(n);
				}
				nations.add(nation);
				resp.setNations(nations);
			}
			RespModuleSet rms = new RespModuleSet(ProcotolType.REGION_BATTLE_RESP);
			PlayerHero[] playerHeros = player.getPlayerHeroManager().getHeroArray();
			for(PlayerHero ph : playerHeros){
				rms.addModule(ph);
			}
			AndroidMessageSender.sendMessage(rms,player);
			return resp;
			
		case ProcotolType.DISARM://撤防
			int countyId = request.getCountyId();
			logger.info("撤防" + countyId);
			Nation n = player.getRegionBattleManager().getNationById(countyId);
			if(null != n){
				TipUtil tip = n.retreatNation(0, false);
				GameUtils.sendTip(tip.getTip(),request.getUserInfo(),GameUtils.FLUTTER);
			}
			
			int cityId2 = player.getRegionBattleManager().getCityByCounty(countyId);
			
			//记录下当前玩家所在县所属市的id以便下次玩家请求翻页时判断
			player.getData().setTempCityId(cityId2);	
			List<Nation> nationList6 = player.getRegionBattleManager().getCountiesByCity(cityId2);
			for(Nation nation : nationList6){
				
				PlayerCharacter tp =World.getInstance().getPlayer(nation.getOccupyUser());// gameWorld.getWorldRoleByUserId().get((long)nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
				
			}
			if(null == nationList6 || nationList6.size() == 0){
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_BUILD);
			}
			else{
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				Nation nation = player.getRegionBattleManager().getNationById(cityId2);
				PlayerCharacter tp = World.getInstance().getPlayer(nation.getOccupyUser());
				if(null != tp){
					nation.setOccupyUsername(tp.getData().getName());
				}
				else{
					nation.setOccupyUsername("");
				}
				/*if(nation.getOccupyUsername() == null){
					nation.setOccupyUsername("");
				}*/
				
				//确保countyMap中的值不被改变
				List<Nation> nations = new ArrayList<Nation>();
				for(Nation nn : nationList6){
					nations.add(nn);
				}
				nations.add(nation);
				resp.setNations(nations);
			}
			RespModuleSet rms2 = new RespModuleSet(ProcotolType.REGION_BATTLE_RESP);
			PlayerHero[] playerHeros2 = player.getPlayerHeroManager().getHeroArray();
			for(PlayerHero ph : playerHeros2){
				rms2.addModule(ph);
			}
			AndroidMessageSender.sendMessage(rms2,player);
			return resp;
		case ProcotolType.VIEW_WAR://查看
			byte warType = NationManager.getInstance().viewWar();
			if(warType > 0){
				resp.setWarEnd((byte)1);
			}else{
				resp.setWarEnd((byte)0);
			}
			resp.setWarType(warType);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		}
		return null;
	}
	
	/**
	 * 通用模块发将领信息
	 * @param nationList
	 * @param player
	 */
	public void sendMessage(List<Nation> nations,PlayerCharacter player){
		RespModuleSet rms=new RespModuleSet(ProcotolType.REGION_BATTLE_RESP);
		ArrayList<ClientModule> sphList = new ArrayList<ClientModule>();
		for(Nation n : nations){
			if(!"".equals(n.getHeroInfo())){
				SimplePlayerHero sph = new SimplePlayerHero(null,n.getHeroInfo());
			}
			PlayerCharacter p = World.getInstance().getPlayer(n.getOccupyUser());
			if(p != null){
				PlayerHero heros = p.getPlayerHeroManager().getHero(n.getHeroId());
				if(heros != null){
					SimplePlayerHero sph = new SimplePlayerHero(heros,"");
					sphList.add(sph);
				}
			}
			
		}
		rms.addModules(sphList);
		AndroidMessageSender.sendMessage(rms, player);
	}
}


