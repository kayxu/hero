package com.joymeng.game.domain.force;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.joymeng.game.domain.fight.obj.FightGroup;
import com.joymeng.game.domain.fight.obj.FightMonster;
import com.joymeng.game.domain.monster.Monster;
import com.joymeng.game.domain.nation.war.StrongHold;
import com.joymeng.game.domain.world.GameDataManager;

/**
 * 部队管理器
 * @author admin
 * @date 2012-6-19
 * TODO
 */
public class MobManager {
	public static Logger logger = Logger.getLogger(MobManager.class);
	private static MobManager instance;
	private HashMap<Integer, Mob> forceMap=new HashMap<Integer,  Mob>();
	public static MobManager getInstance() {
		if (instance == null) {
			instance = new MobManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, Mob.class);
		for (Object obj : list) {
			Mob data = ( Mob) obj;
			 forceMap.put(data.getId(), data);
		}
	}
	public Mob getForce(int id){
		return forceMap.get(id);
	}
	
}
