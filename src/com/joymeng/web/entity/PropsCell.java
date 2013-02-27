package com.joymeng.web.entity;

import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.item.props.PropsPrototype;

/**
 * 物品
 * @author Administrator
 *
 */
public class PropsCell {
	PropsPrototype prototype;
	int num;
	public PropsPrototype getPrototype() {
		return prototype;
	}
	public void setPrototype(PropsPrototype prototype) {
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
		sb.append(Item.ITEM_PROPS).append(",").append(getPrototype().getId()).append(",").append(num).append(";");
		return sb.toString();
	}
}
