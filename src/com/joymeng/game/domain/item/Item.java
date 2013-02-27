package com.joymeng.game.domain.item;

import com.joymeng.game.domain.role.PlayerCharacter;


/**
 * 物品 分为道具和装备
 * @author xufangliang
 *  1.1
 */
public abstract class Item {
	public static final byte ITEM_EQUIPMENT = 0;
	public static final byte ITEM_PROPS = 1;
	
	
	/**
	 * 需要下发给客户端的物品描述数据
	 * TODO 需要和客户端 协议
	 * @return
	 */
	public abstract String getDownDesc(PlayerCharacter role);
	
	protected String downDesc;
	public void setDownDesc(String downDesc) {
		this.downDesc = downDesc;
	}
	
	public abstract String getIconIdx();
	public abstract String getName();
	//类型
	public abstract byte getType();
	//是否道具
	public abstract boolean isProp();
	//单格     最大     叠加数量
	public abstract int getMaxStackCount();
	
	public abstract int getId();
	
}
