package com.joymeng.game.domain.nation.battle;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.common.Instances;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.role.PlayerCharacter;

/**
 * 行政城战争
 * @author madi
 *
 */
public class RegionBattleManager implements Instances{

	private static final Logger logger = LoggerFactory.getLogger(RegionBattleManager.class);
	
	PlayerCharacter player;//用户

	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	private static RegionBattleManager regionBattleManager;

	public RegionBattleManager getInstance(){
		if(null == regionBattleManager){
			regionBattleManager = new RegionBattleManager();
		}
		return regionBattleManager;
	}
	
	private static NationManager naMgr = NationManager.getInstance();
	
	/**
	 * 获取所有国家
	 * @return
	 */
	public List<Nation> getAllCountries(){
		List<Nation> countries = new ArrayList<Nation>();
		for(Nation nation : naMgr.nationMap.values()){
			if(nation.getType() == 0){//如果是国家
				countries.add(nation);
			}
		}
		for(Nation nation : countries){
			nation.getId();
		}
		return countries;
		
	}
	
	/**
	 * 按市拿到所有县
	 * @param cityId
	 * @return
	 */
	public List<Nation> getCountiesByCity(int cityId){
		logger.info("市id:" + cityId);
		List<Nation> nationList = naMgr.countyMap.get(cityId);
		return nationList;
	}
	
	/**
	 * 按国家拿到所有州
	 * @param countryId
	 * @return
	 */
	public List<Nation> getStatesByCountry(int countryId){
		logger.info("国家id:" + countryId);
		return naMgr.stateInCertainCountryMap.get(countryId);
	}
	
	public List<Nation> getCitiesByState(int stateId){
		logger.info("州id:" + stateId);
		List<Nation> nationList = naMgr.cityMap.get(stateId);
		return nationList;
	}
	
	/**
	 * 根据id获得对应的区域
	 * @return
	 */
	public Nation getNationById(int id){
		return naMgr.getNation(id);
	}
	
	/**
	 * 查找玩家所在县对应的市
	 * @param player
	 * @return
	 */
	public int findPlayerCityByCurrentCounty(){
		
		//获取玩家所在县的id
		int countyId = player.getData().getNativeId();
		int cityId = (countyId/10) * 10;
		return cityId;
	}
	
	/**
	 * 查找玩家所在县对应的州
	 * @return
	 */
	public int findPlayerStateByCurrentCounty(){
		int countyId = player.getData().getNativeId();
		return (countyId/100)*100;
		
	}
	
	/**
	 * 查找玩家所在县对应的国家
	 * @return
	 */
	public int findPlayerCountryByCurrentCounty(){
		int countyId = player.getData().getNativeId();
		return (countyId/1000) * 1000;
		
	}
	
	/**
	 * 查找玩家所在地理位置对应的市
	 * @return
	 */
	public int findPlayerCityByNativeId(){
		int nativeId = player.getData().getNativeId();
		return (nativeId/10) * 10;
		
	}
	
	/**
	 * 根据县城获取所属市
	 * @param countyId int
	 * @return
	 */
	public int getCityByCounty(int countyId){
		int cityId = (countyId/10) * 10;
		return cityId;
	}
	
	
	
	
	
	
	
	
}
