package com.joymeng.game.domain.building;

public class SoldierUpGrade {
	
	public  static final  int GOLD = -1;//金币
	public  static final  int DIAMOND = -2;//钻石
	public  static final  int MERITORIOUS = -3;//功勋
	public  static final  int CHIP = -4;//筹码
	
	int id;//id
	int wood;//木材
	int ore;//矿石
	int horse;//马匹
	int special ;//特殊类型
	int num;//特殊消耗数量
	int restrictions;//限制条件
	int limitNumber;//限制数量
	
	int lockType;//锁定消耗类型
	int lockCost;//解锁消耗
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWood() {
		return wood;
	}
	public void setWood(int wood) {
		this.wood = wood;
	}
	public int getOre() {
		return ore;
	}
	public void setOre(int ore) {
		this.ore = ore;
	}
	public int getHorse() {
		return horse;
	}
	public void setHorse(int horse) {
		this.horse = horse;
	}
	public int getSpecial() {
		return special;
	}
	public void setSpecial(int special) {
		this.special = special;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(int restrictions) {
		this.restrictions = restrictions;
	}
	public int getLimitNumber() {
		return limitNumber;
	}
	public void setLimitNumber(int limitNumber) {
		this.limitNumber = limitNumber;
	}
	public int getLockType() {
		return lockType;
	}
	public void setLockType(int lockType) {
		this.lockType = lockType;
	}
	public int getLockCost() {
		return lockCost;
	}
	public void setLockCost(int lockCost) {
		this.lockCost = lockCost;
	}
	
	
	
}
