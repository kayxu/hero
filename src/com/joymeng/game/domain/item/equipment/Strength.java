package com.joymeng.game.domain.item.equipment;

public class Strength {

	byte id;
	byte sLevel;//强化级别
	byte position;//部位
	float data;//系数
	//名字
	String strengthName;
	int strengthColor;//对应颜色
	
	
	/**
	 * 获取 strengthColor
	 * @return the strengthColor
	 */
	public int getStrengthColor() {
		return strengthColor;
	}
	/**
	 * 设置 strengthColor
	 * @param strengthColor the strengthColor to set
	 */
	public void setStrengthColor(int strengthColor) {
		this.strengthColor = strengthColor;
	}
	/**
	 * 获取 sLevel
	 * @return the sLevel
	 */
	public byte getsLevel() {
		return sLevel;
	}
	/**
	 * 设置 sLevel
	 * @param sLevel the sLevel to set
	 */
	public void setsLevel(byte sLevel) {
		this.sLevel = sLevel;
	}
	/**
	 * 获取 strengthName
	 * @return the strengthName
	 */
	public String getStrengthName() {
		return strengthName;
	}
	/**
	 * 设置 strengthName
	 * @param strengthName the strengthName to set
	 */
	public void setStrengthName(String strengthName) {
		this.strengthName = strengthName;
	}
	/**
	 * 获取 id
	 * @return the id
	 */
	public byte getId() {
		return id;
	}
	/**
	 * 设置 id
	 * @param id the id to set
	 */
	public void setId(byte id) {
		this.id = id;
	}
	/**
	 * 获取 position
	 * @return the position
	 */
	public byte getPosition() {
		return position;
	}
	/**
	 * 设置 position
	 * @param position the position to set
	 */
	public void setPosition(byte position) {
		this.position = position;
	}
	/**
	 * 获取 date
	 * @return the date
	 */
	public float getData() {
		return data;
	}
	/**
	 * 设置 date
	 * @param date the date to set
	 */
	public void setData(float data) {
		this.data = data;
	}

}
