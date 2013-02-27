package com.joymeng.game.domain.nation;

public class ResourConst {//资源建筑
	int id;
	int stateId;//州id
	int userId;//占领者
	byte level;//金矿级别
	int chargeOut;//产出
	int chargeType;//类型
	int addition;//加成
	long intervalTime;//下次产出时间
	int heroId;//将领id
	String soMsg;//士兵驻扎信息
	long restTime ;//休整时间
	/**
	 * @return GET the chargeType
	 */
	public int getChargeType() {
		return chargeType;
	}
	/**
	 * @param SET chargeType the chargeType to set
	 */
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	/**
	 * @return GET the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param SET id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return GET the stateId
	 */
	public int getStateId() {
		return stateId;
	}
	/**
	 * @param SET stateId the stateId to set
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return GET the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param SET userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return GET the level
	 */
	public byte getLevel() {
		return level;
	}
	/**
	 * @param SET level the level to set
	 */
	public void setLevel(byte level) {
		this.level = level;
	}
	/**
	 * @return GET the chargeOut
	 */
	public int getChargeOut() {
		return chargeOut;
	}
	/**
	 * @param SET chargeOut the chargeOut to set
	 */
	public void setChargeOut(int chargeOut) {
		this.chargeOut = chargeOut;
	}
	/**
	 * @return GET the addition
	 */
	public int getAddition() {
		return addition;
	}
	/**
	 * @param SET addition the addition to set
	 */
	public void setAddition(int addition) {
		this.addition = addition;
	}
	/**
	 * @return GET the intervalTime
	 */
	public long getIntervalTime() {
		return intervalTime;
	}
	/**
	 * @param SET intervalTime the intervalTime to set
	 */
	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}
	/**
	 * @return GET the heroId
	 */
	public int getHeroId() {
		return heroId;
	}
	/**
	 * @param SET heroId the heroId to set
	 */
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	/**
	 * @return GET the soMsg
	 */
	public String getSoMsg() {
		return soMsg;
	}
	/**
	 * @param SET soMsg the soMsg to set
	 */
	public void setSoMsg(String soMsg) {
		this.soMsg = soMsg;
	}
	/**
	 * @return GET the restTime
	 */
	public long getRestTime() {
		return restTime;
	}
	/**
	 * @param SET restTime the restTime to set
	 */
	public void setRestTime(long restTime) {
		this.restTime = restTime;
	}
}
