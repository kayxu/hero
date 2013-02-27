package com.joymeng.game.domain.item;

import com.joymeng.core.utils.StringUtils;


/**
 * 用户背包管理类
 * @author xufangliang
 *  1.1
 */
public class PlayerItem {
	
	//类型   道具、武器
	byte itemType;
	//明细类型
	byte detailsType;
	//itemID
	String itemId;
	//数量
	int itemCount;
	//效果ID
	int efficId;
	
	/**
	 * 获取 detailsType
	 * @return the detailsType
	 */
	public byte getDetailsType() {
		return detailsType;
	}
	/**
	 * 设置 detailsType
	 * @param detailsType the detailsType to set
	 */
	public void setDetailsType(byte detailsType) {
		this.detailsType = detailsType;
	}
	/**
	 * 获取 efficId
	 * @return the efficId
	 */
	public int getEfficId() {
		return efficId;
	}
	/**
	 * 设置 efficId
	 * @param efficId the efficId to set
	 */
	public void setEfficId(int efficId) {
		this.efficId = efficId;
	}
	
	/**
	 * 获取 itemType
	 * @return the itemType
	 */
	public byte getItemType() {
		return itemType;
	}
	/**
	 * 设置 itemType
	 * @param itemType the itemType to set
	 */
	public void setItemType(byte itemType) {
		this.itemType = itemType;
	}
	/**
	 * 获取 itemId
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置 itemId
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 获取 itemCount
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * 设置 itemCount
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	//类型;id;数量;效果
	public String ItemToString() {
		String play = StringUtils.join(";",String.valueOf(getItemType()),getItemId(),String.valueOf(getItemCount()));
		if(getEfficId() != 0){
			play = StringUtils.join(";",play,String.valueOf(getEfficId()));
		}
		return play;
	}
}
