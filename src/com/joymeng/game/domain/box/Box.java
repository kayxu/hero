package com.joymeng.game.domain.box;
/**
 * 宝箱
 * @author admin
 * @date 2012-5-30
 * TODO
 */
public class Box {
	int id	;
	byte type;
	String money;
	String moneyRate;
	String prop;
	String propRate;
	String equip;
	String equipRate;
	String hero;
	String heroRate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getMoneyRate() {
		return moneyRate;
	}
	public void setMoneyRate(String moneyRate) {
		this.moneyRate = moneyRate;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getPropRate() {
		return propRate;
	}
	public void setPropRate(String propRate) {
		this.propRate = propRate;
	}
	public String getEquip() {
		return equip;
	}
	public void setEquip(String equip) {
		this.equip = equip;
	}
	public String getEquipRate() {
		return equipRate;
	}
	public void setEquipRate(String equipRate) {
		this.equipRate = equipRate;
	}
	public String getHero() {
		return hero;
	}
	public void setHero(String hero) {
		this.hero = hero;
	}
	public String getHeroRate() {
		return heroRate;
	}
	public void setHeroRate(String heroRate) {
		this.heroRate = heroRate;
	}
}
