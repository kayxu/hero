package com.joymeng.web.entity;

import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.item.equipment.Equipment;

public class EquimentCell {
	Equipment prototype;
	int num;
	public Equipment getPrototype() {
		return prototype;
	}
	public void setPrototype(Equipment prototype) {
		this.prototype = prototype;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public String toCell(){
		StringBuffer sb = new StringBuffer();
		sb.append(Item.ITEM_EQUIPMENT).append(",").append(getPrototype().getId()).append(",").append(getNum());
		sb.append(",").append(getPrototype().getHeroId()).append(",").append(getPrototype().getEffectId()).append(",").append(getPrototype().getEffectTime());
		sb.append(";");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Equipment e = new Equipment();
		System.out.println(e.getEffectTime());
	}
}
