package com.joymeng.game.domain.train;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;
/**
 * 训练技能
 * @author admin
 * @date 2012-5-22
 * TODO
 */
public class TrainSkillManager {
	private static TrainSkillManager instance;
	private HashMap<Integer,TrainSkill> trainSkillMap=new HashMap<Integer, TrainSkill>();
	private HashMap<Integer,int[]> trainSkill = new HashMap<Integer, int[]>();
	public static TrainSkillManager getInstance() {
		if (instance == null) {
			instance = new TrainSkillManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, TrainSkill.class);
		for (Object obj : list) {
			TrainSkill data = (TrainSkill) obj;
			getTrainSkill(data);
			trainSkillMap.put(data.getLevel(), data);
		}
	}
	
	public void getTrainSkill(TrainSkill data) {
		int[] skill = new int[10];
		skill[0] = data.getSkill1();
		skill[1] = data.getSkill2();
		skill[2] = data.getSkill3();
		skill[3] = data.getSkill4();
		skill[4] = data.getSkill5();
		skill[5] = data.getSkill6();
		skill[6] = data.getSkill7();
		skill[7] = data.getSkill8();
		skill[8] = data.getSkill9();
		skill[9] = data.getSkill10();
		trainSkill.put(data.getLevel(), skill);
	}
	
	
	/**
	 * @return GET the trainSkill
	 */
	public HashMap<Integer, int[]> getTrainSkill() {
		return trainSkill;
	}
	/**
	 * @param SET trainSkill the trainSkill to set
	 */
	public void setTrainSkill(HashMap<Integer, int[]> trainSkill) {
		this.trainSkill = trainSkill;
	}
	public HashMap<Integer, TrainSkill> getTrainSkillMap() {
		return trainSkillMap;
	}
	public void setTrainSkillMap(HashMap<Integer, TrainSkill> trainSkillMap) {
		this.trainSkillMap = trainSkillMap;
	}
	
}
