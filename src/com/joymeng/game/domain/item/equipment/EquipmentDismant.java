/**
 * Copyright com.joymeng.game.domain.Equipment-EquipmentDismant.java
 * @author xufangliang
 * @time 2012-5-3
 */
package com.joymeng.game.domain.item.equipment;

/**
 * @author xufangliang
 *  1.1
 *  装备拆解
 */
public class EquipmentDismant {
	
	int id;
	short equiLevel;//装备等级
	short StrengthLevel;//强化等级
	int num;//抽取次数
//	5、幸运石LV1
	float luckyLv1StoneRate;
//	6、幸运石LV2
	float luckyLv2StoneRate;
//	7、幸运石LV3
	float luckyLv3StoneRate;
//	8、幸运石LV4
	float luckyLv4StoneRate;
//	9、幸运石LV5
	float luckyLv5StoneRate;
//	10、加制模具LV1
	float jzLv1StoneRate;
//	11、加制模具LV2
	float jzLv2StoneRate;
//	12、加制模具LV3
	float jzLv3StoneRate;
//	13、加制模具LV4
	float jzLv4StoneRate;
//	14、加制模具LV5
	float jzLv5StoneRate;
	/**
	 * 获取 id
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置 id
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取 num
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * 设置 num
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * 获取 equiLevel
	 * @return the equiLevel
	 */
	public short getEquiLevel() {
		return equiLevel;
	}
	/**
	 * 设置 equiLevel
	 * @param equiLevel the equiLevel to set
	 */
	public void setEquiLevel(short equiLevel) {
		this.equiLevel = equiLevel;
	}
	/**
	 * 获取 strengthLevel
	 * @return the strengthLevel
	 */
	public short getStrengthLevel() {
		return StrengthLevel;
	}
	/**
	 * 设置 strengthLevel
	 * @param strengthLevel the strengthLevel to set
	 */
	public void setStrengthLevel(short strengthLevel) {
		StrengthLevel = strengthLevel;
	}
	/**
	 * 获取 luckyLv1StoneRate
	 * @return the luckyLv1StoneRate
	 */
	public float getLuckyLv1StoneRate() {
		return luckyLv1StoneRate;
	}
	/**
	 * 设置 luckyLv1StoneRate
	 * @param luckyLv1StoneRate the luckyLv1StoneRate to set
	 */
	public void setLuckyLv1StoneRate(float luckyLv1StoneRate) {
		this.luckyLv1StoneRate = luckyLv1StoneRate;
	}
	/**
	 * 获取 luckyLv2StoneRate
	 * @return the luckyLv2StoneRate
	 */
	public float getLuckyLv2StoneRate() {
		return luckyLv2StoneRate;
	}
	/**
	 * 设置 luckyLv2StoneRate
	 * @param luckyLv2StoneRate the luckyLv2StoneRate to set
	 */
	public void setLuckyLv2StoneRate(float luckyLv2StoneRate) {
		this.luckyLv2StoneRate = luckyLv2StoneRate;
	}
	/**
	 * 获取 luckyLv3StoneRate
	 * @return the luckyLv3StoneRate
	 */
	public float getLuckyLv3StoneRate() {
		return luckyLv3StoneRate;
	}
	/**
	 * 设置 luckyLv3StoneRate
	 * @param luckyLv3StoneRate the luckyLv3StoneRate to set
	 */
	public void setLuckyLv3StoneRate(float luckyLv3StoneRate) {
		this.luckyLv3StoneRate = luckyLv3StoneRate;
	}
	/**
	 * 获取 luckyLv4StoneRate
	 * @return the luckyLv4StoneRate
	 */
	public float getLuckyLv4StoneRate() {
		return luckyLv4StoneRate;
	}
	/**
	 * 设置 luckyLv4StoneRate
	 * @param luckyLv4StoneRate the luckyLv4StoneRate to set
	 */
	public void setLuckyLv4StoneRate(float luckyLv4StoneRate) {
		this.luckyLv4StoneRate = luckyLv4StoneRate;
	}
	/**
	 * 获取 luckyLv5StoneRate
	 * @return the luckyLv5StoneRate
	 */
	public float getLuckyLv5StoneRate() {
		return luckyLv5StoneRate;
	}
	/**
	 * 设置 luckyLv5StoneRate
	 * @param luckyLv5StoneRate the luckyLv5StoneRate to set
	 */
	public void setLuckyLv5StoneRate(float luckyLv5StoneRate) {
		this.luckyLv5StoneRate = luckyLv5StoneRate;
	}
	/**
	 * 获取 jzLv1StoneRate
	 * @return the jzLv1StoneRate
	 */
	public float getJzLv1StoneRate() {
		return jzLv1StoneRate;
	}
	/**
	 * 设置 jzLv1StoneRate
	 * @param jzLv1StoneRate the jzLv1StoneRate to set
	 */
	public void setJzLv1StoneRate(float jzLv1StoneRate) {
		this.jzLv1StoneRate = jzLv1StoneRate;
	}
	/**
	 * 获取 jzLv2StoneRate
	 * @return the jzLv2StoneRate
	 */
	public float getJzLv2StoneRate() {
		return jzLv2StoneRate;
	}
	/**
	 * 设置 jzLv2StoneRate
	 * @param jzLv2StoneRate the jzLv2StoneRate to set
	 */
	public void setJzLv2StoneRate(float jzLv2StoneRate) {
		this.jzLv2StoneRate = jzLv2StoneRate;
	}
	/**
	 * 获取 jzLv3StoneRate
	 * @return the jzLv3StoneRate
	 */
	public float getJzLv3StoneRate() {
		return jzLv3StoneRate;
	}
	/**
	 * 设置 jzLv3StoneRate
	 * @param jzLv3StoneRate the jzLv3StoneRate to set
	 */
	public void setJzLv3StoneRate(float jzLv3StoneRate) {
		this.jzLv3StoneRate = jzLv3StoneRate;
	}
	/**
	 * 获取 jzLv4StoneRate
	 * @return the jzLv4StoneRate
	 */
	public float getJzLv4StoneRate() {
		return jzLv4StoneRate;
	}
	/**
	 * 设置 jzLv4StoneRate
	 * @param jzLv4StoneRate the jzLv4StoneRate to set
	 */
	public void setJzLv4StoneRate(float jzLv4StoneRate) {
		this.jzLv4StoneRate = jzLv4StoneRate;
	}
	/**
	 * 获取 jzLv5StoneRate
	 * @return the jzLv5StoneRate
	 */
	public float getJzLv5StoneRate() {
		return jzLv5StoneRate;
	}
	/**
	 * 设置 jzLv5StoneRate
	 * @param jzLv5StoneRate the jzLv5StoneRate to set
	 */
	public void setJzLv5StoneRate(float jzLv5StoneRate) {
		this.jzLv5StoneRate = jzLv5StoneRate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return "EquipmentDismant [id=" + id + ", equiLevel=" + equiLevel
//				+ ", StrengthLevel=" + StrengthLevel + ", num=" + num
//				+ ", luckyLv1StoneRate=" + luckyLv1StoneRate
//				+ ", luckyLv2StoneRate=" + luckyLv2StoneRate
//				+ ", luckyLv3StoneRate=" + luckyLv3StoneRate
//				+ ", luckyLv4StoneRate=" + luckyLv4StoneRate
//				+ ", luckyLv5StoneRate=" + luckyLv5StoneRate
//				+ ", jzLv1StoneRate=" + jzLv1StoneRate + ", jzLv2StoneRate="
//				+ jzLv2StoneRate + ", jzLv3StoneRate=" + jzLv3StoneRate
//				+ ", jzLv4StoneRate=" + jzLv4StoneRate + ", jzLv5StoneRate="
//				+ jzLv5StoneRate + "]";
//	}
	
}
