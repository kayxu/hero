/**
 * 
 */
package com.joymeng.game.domain.building;

import java.util.Map;


/**
 * @author Administrator
 *
 */
public class TrainingBits {
	int trainingNO;//训练位
	int condition;//1:占领主城 2：消耗钻石 3：行政
	int numLevel;//数量或级别
	int addType;//0:无 1：经验  2：时间减少 
	int addCount;//加成数量   实际使用需要 /100使用
	Map<Integer,int[]> trainExp;//经验map
	Map<Integer,int[]> TrainSkill;//技能map
	int expTime;//经验训练时间
	int skillTime;//技能训练时间
	
	
	/**
	 * @return GET the trainExp
	 */
	public Map<Integer, int[]> getTrainExp() {
		return trainExp;
	}
	/**
	 * @param SET trainExp the trainExp to set
	 */
	public void setTrainExp(Map<Integer, int[]> trainExp) {
		this.trainExp = trainExp;
	}
	/**
	 * @return GET the trainSkill
	 */
	public Map<Integer, int[]> getTrainSkill() {
		return TrainSkill;
	}
	/**
	 * @param SET trainSkill the trainSkill to set
	 */
	public void setTrainSkill(Map<Integer, int[]> trainSkill) {
		TrainSkill = trainSkill;
	}
	/**
	 * @return GET the expTime
	 */
	public int getExpTime() {
		return expTime;
	}
	/**
	 * @param SET expTime the expTime to set
	 */
	public void setExpTime(int expTime) {
		this.expTime = expTime;
	}
	/**
	 * @return GET the skillTime
	 */
	public int getSkillTime() {
		return skillTime;
	}
	/**
	 * @param SET skillTime the skillTime to set
	 */
	public void setSkillTime(int skillTime) {
		this.skillTime = skillTime;
	}
	/**
	 * @return GET the condition
	 */
	public int getCondition() {
		return condition;
	}
	/**
	 * @param SET condition the condition to set
	 */
	public void setCondition(int condition) {
		this.condition = condition;
	}
	/**
	 * @return GET the numLevel
	 */
	public int getNumLevel() {
		return numLevel;
	}
	/**
	 * @param SET numLevel the numLevel to set
	 */
	public void setNumLevel(int numLevel) {
		this.numLevel = numLevel;
	}
	/**
	 * @return GET the trainingNO
	 */
	public int getTrainingNO() {
		return trainingNO;
	}
	/**
	 * @param SET trainingNO the trainingNO to set
	 */
	public void setTrainingNO(int trainingNO) {
		this.trainingNO = trainingNO;
	}
	/**
	 * @return GET the addType
	 */
	public int getAddType() {
		return addType;
	}
	/**
	 * @param SET addType the addType to set
	 */
	public void setAddType(int addType) {
		this.addType = addType;
	}
	/**
	 * @return GET the addCount
	 */
	public int getAddCount() {
		return addCount;
	}
	/**
	 * @param SET addCount the addCount to set
	 */
	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}
//	/* (non-Javadoc)
//	 * @see java.lang.Object#toString()
//	 */
//	@Override
//	public String toString() {
//		return "";
//	}
	
}
