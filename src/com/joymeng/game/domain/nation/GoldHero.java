package com.joymeng.game.domain.nation;

public class GoldHero {
	int id; //= heroLevel * 10 + type
	int heroLevel;//武将级别
	byte type;//产出类型
	int interval;//时间间隔
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the heroLevel
	 */
	public int getHeroLevel() {
		return heroLevel;
	}
	/**
	 * @param heroLevel the heroLevel to set
	 */
	public void setHeroLevel(int heroLevel) {
		this.heroLevel = heroLevel;
	}
	/**
	 * @return the type
	 */
	public byte getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}
	/**
	 * @return the interval
	 */
	public int getInterval() {
		return interval;
	}
	/**
	 * @param interval the interval to set
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
}
