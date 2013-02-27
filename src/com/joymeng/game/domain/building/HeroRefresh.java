package com.joymeng.game.domain.building;


public class HeroRefresh {
	//<HeroRefresh num1="7" num2="31" num3="8" refreshTimes="3"/>
	int refreshTimes;//次数
	byte type;// 0 , 1
	int num1;//一号位
	int num2;
	int num3;
	public int getRefreshTimes() {
		return refreshTimes;
	}
	public void setRefreshTimes(int refreshTimes) {
		this.refreshTimes = refreshTimes;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int getNum3() {
		return num3;
	}
	public void setNum3(int num3) {
		this.num3 = num3;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	
}
