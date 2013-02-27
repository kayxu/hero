package com.joymeng.game.domain.role;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;
/**
 * 玩家升级经验表
 * @author admin
 * @date 2012-5-25
 * TODO
 */
public class PlayerExpManager {
	private static PlayerExpManager instance;
	private HashMap<Integer,PlayerExp> playerExpMap=new HashMap<Integer, PlayerExp>();
	public static PlayerExpManager getInstance() {
		if (instance == null) {
			instance = new PlayerExpManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, PlayerExp.class);
		for (Object obj : list) {
			PlayerExp data = (PlayerExp) obj;
			playerExpMap.put(data.getId(), data);
		}
	}
}
