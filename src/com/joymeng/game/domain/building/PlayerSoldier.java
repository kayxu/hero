/**
 * Copyright com.joymeng.game.domain.Soldier-PlayerSoldier.java
 * @author xufangliang
 * @time 2012-5-7
 */
package com.joymeng.game.domain.building;


import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.domain.soldier.Soldier;
import com.joymeng.game.domain.soldier.SoldierManager;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * @author xufangliang
 *  1.1
 */
public class PlayerSoldier extends ClientModuleBase{
	/**
	 * 用户ID
	 */
	int playerId;
	
	/**
	 * 士兵类型ID
	 */
	int soldierId;
	/**
	 * 士兵数量
	 */
	int soldierCount;
	/**
	 * 训练数量
	 */
	int trainingSoldierCount;
	/**
	 * 训练结束时间
	 */
	long trainingTime;
	
	int level;
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * 获取 playerId
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}
	/**
	 * 设置 playerId
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	/**
	 * 获取 soldierId
	 * @return the soldierId
	 */
	public int getSoldierId() {
		return soldierId;
	}
	/**
	 * 设置 soldierId
	 * @param soldierId the soldierId to set
	 */
	public void setSoldierId(int soldierId) {
		this.soldierId = soldierId;
	}
	/**
	 * 获取 soldierCount
	 * @return the soldierCount
	 */
	public int getSoldierCount() {
		return soldierCount;
	}
	/**
	 * 设置 soldierCount
	 * @param soldierCount the soldierCount to set
	 */
	public void setSoldierCount(int soldierCount) {
		this.soldierCount = soldierCount;
	}
	/**
	 * 获取 trainingSoldierCount
	 * @return the trainingSoldierCount
	 */
	public int getTrainingSoldierCount() {
		return trainingSoldierCount;
	}
	/**
	 * 设置 trainingSoldierCount
	 * @param trainingSoldierCount the trainingSoldierCount to set
	 */
	public void setTrainingSoldierCount(int trainingSoldierCount) {
		this.trainingSoldierCount = trainingSoldierCount;
	}
	/**
	 * 获取 trainingTime
	 * @return the trainingTime
	 */
	public long getTrainingTime() {
		return trainingTime;
	}
	/**
	 * 设置 trainingTime
	 * @param trainingTime the trainingTime to set
	 */
	public void setTrainingTime(long trainingTime) {
		this.trainingTime = trainingTime;
	}
	
	public boolean isSpecialSoldier(){
		Soldier soldier = SoldierManager.getInstance().getSoldier(getSoldierId());
		if(soldier != null && soldier.getType() == 4){
			return true;
		}
		return false;
	}
	
	/**
	 *(non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getPlayerId()+","+getSoldierId()+","+getSoldierCount()+","+getTrainingSoldierCount()+","+getTrainingTime();//+","+getEquNum();
	}
	@Override
	public byte getModuleType() {
		return NTC_TRAININGSOLDIER;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(playerId);
		out.putInt(soldierId);
		out.putInt(soldierCount);
		out.putInt(trainingSoldierCount);
		out.putLong((int)(getTrainingTime()/1000));
	}
	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}

}
