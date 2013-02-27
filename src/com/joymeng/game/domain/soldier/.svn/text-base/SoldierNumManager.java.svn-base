package com.joymeng.game.domain.soldier;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;

public class SoldierNumManager {
	private static SoldierNumManager instance;
	private HashMap<Integer,SoldierNum> soldierNumMap=new HashMap<Integer, SoldierNum>();
	public static SoldierNumManager getInstance() {
		if (instance == null) {
			instance = new SoldierNumManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, SoldierNum.class);
		for (Object obj : list) {
			SoldierNum data = (SoldierNum) obj;
			soldierNumMap.put(data.getLevel(), data);
		}
	}
	public HashMap<Integer, SoldierNum> getSoldierNumMap() {
		return soldierNumMap;
	}
	public void setSoldierNumMap(HashMap<Integer, SoldierNum> soldierNumMap) {
		this.soldierNumMap = soldierNumMap;
	}
	
}
