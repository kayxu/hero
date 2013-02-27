package com.joymeng.game.domain.train;

import java.util.HashMap;
import java.util.List;

import com.joymeng.game.domain.world.GameDataManager;
/**
 * 训练经验数值
 * @author admin
 * @date 2012-5-22
 * TODO
 */
public class TrainExpManager {
	private static TrainExpManager instance;
	private HashMap<Integer,TrainExp> trainExpMap=new HashMap<Integer, TrainExp>();
	private HashMap<Integer,int[]> trainExp = new HashMap<Integer,int[]>();
	public static TrainExpManager getInstance() {
		if (instance == null) {
			instance = new TrainExpManager();
		}
		return instance;
	}
	public void load(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, TrainExp.class);
		for (Object obj : list) {
			TrainExp data = (TrainExp) obj;
			for(int i = 1 ; i <=30;i++){
				setTrainExp(data,i);
			}
			
			trainExpMap.put(data.getLevel(), data);
		}
	}
	
	public void setTrainExp(TrainExp data,int buildLevel){
		int[] lst  = trainExp.get(buildLevel);
		if(lst == null || lst.length == 0){//等级
			lst = new int[60];
			lst[data.getLevel()-1] = data.fromMethodName(data,"getBuild"+buildLevel);
		}else{
			lst[data.getLevel()-1] = data.fromMethodName(data,"getBuild"+buildLevel);
		}
//		System.out.println(buildLevel+"==="+lst.length);
		trainExp.put(buildLevel, lst);
	}
	
	/**
	 * @return GET the trainExp
	 */
	public HashMap<Integer, int[]> getTrainExp() {
		return trainExp;
	}
	/**
	 * @param SET trainExp the trainExp to set
	 */
	public void setTrainExp(HashMap<Integer, int[]> trainExp) {
		this.trainExp = trainExp;
	}
	public HashMap<Integer, TrainExp> getTrainExpMap() {
		return trainExpMap;
	}
	public void setTrainExpMap(HashMap<Integer, TrainExp> trainExpMap) {
		this.trainExpMap = trainExpMap;
	}
	
}
