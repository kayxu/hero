package com.joymeng.game.domain.box;

import java.util.HashMap;
import java.util.List;

import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.game.domain.world.GameDataManager;

public class BoxManager {

	private static BoxManager instance;
	private HashMap<Integer, Box> boxMap=new HashMap<Integer,  Box>();
	public static BoxManager getInstance() {
		if (instance == null) {
			instance = new BoxManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, Box.class);
		for (Object obj : list) {
			 Box data = ( Box) obj;
			 boxMap.put(data.getId(), data);
		}
	}
	
	public Box getBox(int id){
		if(boxMap != null)
			return boxMap.get(id);
		return null;
	}
	/**
	 * 获得奖励
	 * @param id
	 */
	public int[] getAward(int id){
		int[] dataResult = new int[4];//四种类型
		Box box=boxMap.get(id);
		byte type=box.getType();
		String smoney=box.getMoney();
		String smoneyRate=box.getMoney();
		String sprop=box.getProp();
		String spropRate=box.getPropRate();
		String sequip=box.getEquip();
		String sequipRate=box.getEquipRate();
		String shero=box.getHero();
		String sheroRate=box.getHeroRate();
		int money[]=StringUtils.changeToInt(smoney,";");
		int moneyRate[]=StringUtils.changeToInt(smoneyRate,";");
		int prop[]=StringUtils.changeToInt(sprop,";");
		int propRate[]=StringUtils.changeToInt(spropRate,";");
		int equip[]=StringUtils.changeToInt(sequip,";");
		int equipRate[]=StringUtils.changeToInt(sequipRate,";");
		int hero[]=StringUtils.changeToInt(shero,";");
		int heroRate[]=StringUtils.changeToInt(sheroRate,";");
		//最终数组
		if(money != null && moneyRate != null){
			dataResult[0] = MathUtils.getRandomId1(money,moneyRate,100);
		}else{
			dataResult[0] = 0;
		}
		if(prop != null && propRate != null){
			dataResult[1] =MathUtils.getRandomId1(prop,propRate,100);
		}else{
			dataResult[1] = 0;
		}
		if(equip != null && equipRate != null){
			dataResult[2] = MathUtils.getRandomId1(equip,equipRate,100);
		}else{
			dataResult[2] = 0;
		}
		if(hero != null && heroRate != null){
			dataResult[3] = MathUtils.getRandomId1(hero,heroRate,100);
		}else{
			dataResult[3] = 0;
		}
		return dataResult;
	}
}
