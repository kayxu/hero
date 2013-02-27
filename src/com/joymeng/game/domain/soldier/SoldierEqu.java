package com.joymeng.game.domain.soldier;

import com.joymeng.core.base.net.response.ClientModuleBase;

public class SoldierEqu extends ClientModuleBase {
	int id;
	String name;
	int attack;
	int defense;
	int soldier;
	String icon;
	int wood;
	int iron;
	int fur;
	String desc;
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
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getSoldier() {
		return soldier;
	}
	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public int getWood() {
		return wood;
	}
	public void setWood(int wood) {
		this.wood = wood;
	}
	public int getIron() {
		return iron;
	}
	public void setIron(int iron) {
		this.iron = iron;
	}
	public int getFur() {
		return fur;
	}
	public void setFur(int fur) {
		this.fur = fur;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
