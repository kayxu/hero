package com.joymeng.game.domain.hero.data;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.world.GameDataManager;

public class HeroBarManager {
	private static HeroBarManager instance;
	private HashMap<Integer,int[]> heroBarMap=new HashMap<Integer, int[]>();
	public static HeroBarManager getInstance() {
		if (instance == null) {
			instance = new HeroBarManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, HeroBar.class);
		for (Object obj : list) {
			HeroBar data = (HeroBar) obj;
			int[] rate=new int[GameConst.HERO_TYPE];
			rate[0]=data.getRate1();
			rate[1]=data.getRate2();
			rate[2]=data.getRate3();
			rate[3]=data.getRate4();
			rate[4]=data.getRate5();
			rate[5]=data.getRate6();
			heroBarMap.put(data.getLevel(), rate);
		}
	}
	public HashMap<Integer, int[]> getHeroBarMap() {
		return heroBarMap;
	}
	public void setHeroBarMap(HashMap<Integer, int[]> heroBarMap) {
		this.heroBarMap = heroBarMap;
	}
	
}
