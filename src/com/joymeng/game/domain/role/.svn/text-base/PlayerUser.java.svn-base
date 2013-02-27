package com.joymeng.game.domain.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.NumberUtil;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.Instances;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;

public class PlayerUser implements Instances {
	private static final Logger logger = LoggerFactory
	.getLogger(PlayerUser.class);
	PlayerCharacter player;
	
	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}
//	/**
//	 * 随机用户
//	 */
//	public List<RoleData> randomUser(int num){
//		return gameDao.getRoles(num);
//	}
	public List<PlayerCache> myRandomUser(){
		ArrayList<PlayerCache> simpleLst = new ArrayList<PlayerCache>();
		ConcurrentHashMap<Integer, PlayerCharacter> playerList = gameWorld
				.getWorldRoleMap();
		for(PlayerCharacter pc : playerList.values()){
			PlayerBuilding main = pc.getPlayerBuilgingManager().getMainCity();
			if(pc.getData() != null && pc.getData().getUserid() != player.getData().getUserid() && main != null && main.getOccupyUserId() != player.getData().getUserid())
			{
				simpleLst.add(GameUtils.getFromCache(pc.getId()));
			}
		}
		return simpleLst;
	}
	
	/**
	 * 集合去重
	 * @param list
	 * @return
	 */
	public List listNoRepeat(List list){
		Set set = new HashSet();
		set.addAll(list);
		List lst = new ArrayList();
		lst.addAll(set);
		return lst;
	}
	
	/**
	 * 数组添加
	 * @param levels
	 * @return
	 */
	public List listAdd(List one,List two){
		one.addAll(two);
		return listNoRepeat(one);
	}
	
	
	public List<PlayerCache> myRandomUser2(int t){
		return MongoServer.getInstance().getLogServer().getPlayerCacheDAO().randomCertainNumPlayerCache(t, player.getData().getLevel(),player.getData().getUserid());
	}
	
	
//	/**
//	 * 随机一些在线玩家
//	 * @param num int
//	 * @return
//	 */
//	public List<PlayerCache> randomUsers(int num){
//		MongoServer MongoServer = new MongoServer();
//		//所有在线玩家基本数据
//		List<PlayerCache> playerCacheList = MongoServer.getLogServer().getPlayerCacheDAO().findAll();
//		
//		//按条件筛选。。。
//		
//		
//		
//		
//		
//		return null;
//	}
	
	public PlayerHero getUserHeroObj(int userId){
		PlayerCharacter user = World.getInstance().getPlayer(userId);
		if(user != null){
			int offerUser = (int) user.getPlayerBuilgingManager().getPmc().getMainCity().getOccupyUserId();
			int heroId = user.getPlayerBuilgingManager().getPmc().getMainCity().getOfficerId();
			if(offerUser == 0 && heroId != 0){
				PlayerHero hero = user.getPlayerHeroManager().getHero(heroId);
				return hero;
			}else if(offerUser != 0 && heroId != 0){
				PlayerCharacter other = World.getInstance().getPlayer(offerUser);
				if(other == null){
					return null;
				}
				PlayerHero hero = other.getPlayerHeroManager().getHero(heroId);
				return hero;
			}
		}
		return null;
	}
	
	/**
	 * 进入用户
	 */
	public List<ClientModule> enterPlayer(int userId){
		List<ClientModule> lst = new ArrayList<ClientModule>();
		PlayerCharacter user = World.getInstance().getPlayer(userId);
		if(user != null){
			lst.add(user.getData());
			List<PlayerBuilding> buldLst = user.getPlayerBuilgingManager().getPlayerAll();
			logger.info(buldLst.toString());
			lst.addAll(buldLst);
			List<PlayerHero> heros =  user.getPlayerBuilgingManager().getPma()== null ? new ArrayList<PlayerHero>() : user.getPlayerBuilgingManager().getPma().afflst3() ;
			if(user.getPlayerBuilgingManager().getPmc().getMainCity().getOccupyUserId() == 0){
				 heros.add(user.getPlayerHeroManager().getHero(user.getPlayerBuilgingManager().getPmc().getMainCity().getOfficerId()));
			}else{
				PlayerCharacter pp = World.getInstance().getPlayer((int) user.getPlayerBuilgingManager().getPmc().getMainCity().getOccupyUserId());
				heros.add(pp.getPlayerHeroManager().getHero(user.getPlayerBuilgingManager().getPmc().getMainCity().getOfficerId()));
			}
			
			if(heros != null){
				for(PlayerHero hero : heros){
					if(hero != null){
					SimplePlayerHero sp = new SimplePlayerHero(hero,"");
					lst.add(sp);
				}
				}
			}
			return lst;
		}
		return null;
	}
	
	public PlayerAche getPlayerAcher(){
		RoleData data = player.getData();
		PlayerAche ache = new PlayerAche();
		//我的数据
		ache.setMyOfficial(data.getTitle());
		ache.setMyFeat(data.getAchieve());
		ache.setMyAdd(player.goldAche());
		//国王
		Nation kingNation = NationManager.getInstance().getNation(data.getNativeId()/1000*1000);
		ache.setProsperity((int)(Double.parseDouble(kingNation.getRemark4())*100));
		if(kingNation != null && kingNation.getOccupyUser() != 0){
			PlayerCharacter king = World.getInstance().getPlayer(kingNation.getOccupyUser());
			if(king != null){
				RoleData rd = king.getData();
				ache.setKingName(rd.getName());
				ache.setKingFeat(rd.getAchieve());
				ache.setKingAdd(king.goldAche());
			}else{
				ache.setKingName("null");
				ache.setKingAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()/1000*1000))*100));
			}
		}else{
			ache.setKingName("null");
			ache.setKingAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()/1000*1000))*100));
			//System.out.println(NationManager.getInstance().achievMap);
		}
		//州
		Nation stateNation = NationManager.getInstance().getNation(data.getNativeId()/100*100);
		if(stateNation != null && stateNation.getOccupyUser() != 0){
			PlayerCharacter state = World.getInstance().getPlayer(stateNation.getOccupyUser());
			if(state != null){
				RoleData rd = state.getData();
				ache.setStateName(rd.getName());
				ache.setStateFeat(rd.getAchieve());
				ache.setStateAdd(state.goldAche());
			}else{
				ache.setStateName("null");
				ache.setStateAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()/100*100))*100));
			}
			
		}else{
			ache.setStateName("null");
			ache.setStateAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()/100*100))*100));
		}
		//市
		Nation cityNation = NationManager.getInstance().getNation(data.getNativeId()/10*10);
		//double add = NationManager.getInstance().achievMap.get(player.getData().getTitle()/10*10) ==null ? 0 : NationManager.getInstance().achievMap.get(player.getData().getTitle()/10*10);
		if(cityNation != null && cityNation.getOccupyUser() != 0){
			PlayerCharacter city = World.getInstance().getPlayer(cityNation.getOccupyUser());
			if(city != null){	
				RoleData rd = city.getData();
				ache.setCityName(rd.getName());
				ache.setCityFeat(rd.getAchieve());
				ache.setCityAdd(city.goldAche());
			}else{
				ache.setCityName("null");
				ache.setCityAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()/10*10))*100));
			}
			
		}else{
			ache.setCityName("null");
			ache.setCityAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()/10*10))*100));
		}
		//县
		Nation townNation = NationManager.getInstance().getNation(data.getNativeId());
		if(townNation != null && townNation.getOccupyUser() != 0){
			PlayerCharacter town = World.getInstance().getPlayer(townNation.getOccupyUser());
			if(town != null){
				RoleData rd = town.getData();
				ache.setTownName(rd.getName());
				ache.setTownFeat(rd.getAchieve());
				ache.setTownAdd(town.goldAche());
			}else{
				ache.setTownName("null");
				ache.setTownAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()))*100));
			}
		}else{
			ache.setTownName("null");
			ache.setTownAdd((int)((NationManager.getInstance().getAchievs(data.getNativeId()))*100));
		}
		return ache;
	}
}
