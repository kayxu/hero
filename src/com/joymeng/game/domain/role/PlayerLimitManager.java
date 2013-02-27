package com.joymeng.game.domain.role;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;

public class PlayerLimitManager {
	private static PlayerLimitManager instance;
	private HashMap<Integer,PlayerLimit> playerLimitMap=new HashMap<Integer, PlayerLimit>();
	//升级爵位条件
	public HashMap<Integer,PlayerNobility> playerNobilityMap=new HashMap<Integer, PlayerNobility>();
	public static PlayerLimitManager getInstance() {
		if (instance == null) {
			instance = new PlayerLimitManager();
		}
		return instance;
	}
	
	/**
	 * @return GET the playerLimitMap
	 */
	public HashMap<Integer, PlayerLimit> getPlayerLimitMap() {
		return playerLimitMap;
	}
	
	public HashMap<Integer, PlayerNobility> getPlayerNobilityMap() {
		return playerNobilityMap;
	}

	/**
	 * @param SET playerLimitMap the playerLimitMap to set
	 */
	public void setPlayerLimitMap(HashMap<Integer, PlayerLimit> playerLimitMap) {
		this.playerLimitMap = playerLimitMap;
	}

	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, PlayerLimit.class);
		for (Object obj : list) {
			PlayerLimit data = (PlayerLimit) obj;
			playerLimitMap.put(data.getLevel(), data);
		}
		loadNobility(path);
	}
	public void loadNobility(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, PlayerNobility.class);
		for (Object obj : list) {
			PlayerNobility data = (PlayerNobility) obj;
			playerNobilityMap.put(data.getId(), data);
		}
		System.out.println(playerNobilityMap.size());
	}
	
	public PlayerLimit getPlayerLimit(int level){
		if(playerLimitMap == null || playerLimitMap.size() == 0)
			return null;
		return playerLimitMap.get(level);
	}
	public PlayerLimit get(int level){
		return playerLimitMap.get(level);
	}
}
