package com.joymeng.game.domain.fight.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;

/**
 * 战斗奖励-----废弃
 * @author admin
 * @date 2012-5-3
 * TODO
 */
public class FightRewardManager {
	
	private static FightRewardManager instance;
	//FightReward[0]失败
	//FightReward[1]成功
	//key=FightReward.type
	private HashMap<Byte,List<FightReward>> fightRewardMap=new HashMap<Byte, List<FightReward>>();
	public static FightRewardManager getInstance() {
		if (instance == null) {
			instance = new FightRewardManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, FightReward.class);
		for (Object obj : list) {
			FightReward data = (FightReward) obj;
//			System.out.println("data.id="+data.getPoint());
			List<FightReward> flist=fightRewardMap.get(data.getType());
			if(flist==null){
				flist=new ArrayList<FightReward>();
				flist.add(data);
				fightRewardMap.put(data.getType(), flist);
			}else{
				flist.add(data);
			}
		}
//		System.out.println("size="+fightRewardMap.size());
	}
	/**
	 * 取得战斗奖励
	 * @param type
	 * @param i
	 * @return
	 */
	public FightReward getFightReward(byte type,int point){
		List<FightReward> list=fightRewardMap.get(type);
		if(list==null){
			return null;
		}
		for(FightReward fr:list){
			if(fr.getPoint()==point){
				return fr;
			}
		}
		return null;
	}
}
