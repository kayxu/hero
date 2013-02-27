package com.joymeng.game.domain.fight.result;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;

public class ArenaRewardManager {
	private static ArenaRewardManager instance;
	private HashMap<Integer, ArenaReward> rewardMap=new HashMap<Integer,  ArenaReward>();
	public static ArenaRewardManager getInstance() {
		if (instance == null) {
			instance = new ArenaRewardManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, ArenaReward.class);
		for (Object obj : list) {
			ArenaReward data = ( ArenaReward) obj;
			rewardMap.put(data.getId(), data);
		}
	}
	public ArenaReward getReward(int id){
		return rewardMap.get(id);
	}
	
}
