/**
 * 
 */
package com.joymeng.game.domain.building;


/**
 * @author Administrator
 *
 */
public class BaseTrainingBits {
	int trainingNO;//训练位
	int condition;//1:占领主城 2：消耗钻石 3：行政
	int numLevel;//数量或级别
	int addType;//0:无 1：经验  2：时间减少 
	int addCount;//加成数量   实际使用需要 /100使用
	
	
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
	
}
