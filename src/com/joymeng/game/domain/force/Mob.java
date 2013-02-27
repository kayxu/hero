package com.joymeng.game.domain.force;

import com.joymeng.core.utils.StringUtils;

/**
 * 部队表
 * @author admin
 * @param <E>
 * @date 2012-6-19
 * TODO
 */
public class Mob {
	private int id;
	private String  name;
	private int general;
	private int armyType;
	
	private int soldier1;
	private int soldier2;
	private int soldier3;
	private int soldier4;
	
	private int soldierNum1;
	private int soldierNum2;
	private int soldierNum3;
	private int soldierNum4;
	
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
	public int getGeneral() {
		return general;
	}
	public void setGeneral(int general) {
		this.general = general;
	}
	public int getArmyType() {
		return armyType;
	}
	public void setArmyType(int armyType) {
		this.armyType = armyType;
	}
	public int getSoldier1() {
		return soldier1;
	}
	public void setSoldier1(int soldier1) {
		this.soldier1 = soldier1;
	}
	public int getSoldier2() {
		return soldier2;
	}
	public void setSoldier2(int soldier2) {
		this.soldier2 = soldier2;
	}
	public int getSoldier3() {
		return soldier3;
	}
	public void setSoldier3(int soldier3) {
		this.soldier3 = soldier3;
	}
	public int getSoldier4() {
		return soldier4;
	}
	public void setSoldier4(int soldier4) {
		this.soldier4 = soldier4;
	}
	public int getSoldierNum1() {
		return soldierNum1;
	}
	public void setSoldierNum1(int soldierNum1) {
		this.soldierNum1 = soldierNum1;
	}
	public int getSoldierNum2() {
		return soldierNum2;
	}
	public void setSoldierNum2(int soldierNum2) {
		this.soldierNum2 = soldierNum2;
	}
	public int getSoldierNum3() {
		return soldierNum3;
	}
	public void setSoldierNum3(int soldierNum3) {
		this.soldierNum3 = soldierNum3;
	}
	public int getSoldierNum4() {
		return soldierNum4;
	}
	public void setSoldierNum4(int soldierNum4) {
		this.soldierNum4 = soldierNum4;
	}
	/**
	 * 转化为战斗士兵结构 1:2;2:3
	 * @return
	 */
	public String getSoldier(){
		String str[]=new String[4];
		str[0]=soldierNum1!=0?(soldier1+":"+soldierNum1+",0"):("");
		str[1]=soldierNum2!=0?(soldier2+":"+soldierNum2+",0"):("");
		str[2]=soldierNum3!=0?(soldier3+":"+soldierNum3+",0"):("");
		str[3]=soldierNum4!=0?(soldier4+":"+soldierNum4+",0"):("");
		return StringUtils.recoverNewStr(str, ";");
	}
}
