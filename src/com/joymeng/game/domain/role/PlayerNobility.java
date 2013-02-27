package com.joymeng.game.domain.role;

public class PlayerNobility {
	int id;
	//名字
	String name;
	//用户等级条件
	short lordLevel;
	//功勋消耗
	int cost;
	//主城等级上限
	short townMaxLevel;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getLordLevel() {
		return lordLevel;
	}
	public void setLordLevel(short lordLevel) {
		this.lordLevel = lordLevel;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public short getTownMaxLevel() {
		return townMaxLevel;
	}
	public void setTownMaxLevel(short townMaxLevel) {
		this.townMaxLevel = townMaxLevel;
	}
	
}
