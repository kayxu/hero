package com.joymeng.game.domain.monster;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;

/**
 * 怪物管理器
 * @author admin
 * @date 2012-6-6
 * TODO
 */
public class MonsterManager {
	private static MonsterManager instance;
	private HashMap<Integer, Monster> monsterMap = new HashMap<Integer, Monster>();
	public static MonsterManager getInstance() {
		if (instance == null) {
			instance = new MonsterManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, Monster.class);
		for (Object obj : list) {
			Monster data = (Monster) obj;
			monsterMap.put(data.getId(), data);
//			System.out.println("monster id="+data.getId());
		}
	}
	public Monster getMonster(int id){
		return monsterMap.get(id);
	}
}
