package com.joymeng.game.domain.item.equipment;

/**
 * 装备原型数据
 * @author xufangliang
 *  1.1
 */
public class BasePrototype{
	
	/**
	 * ID
	 */
	int id;
	
	/**
	 * 名称
	 */
	String name;
	
	/**
	 * 中文名
	 */
	String equipmentCName;
	/**
	 * 图标
	 */
	String equipmentIcon;
	/**
	 * 类型
	 */
	byte equipmentType; //相当于部位
	/**
	 * 等级
	 */
	byte equipmentLevel;
	
	/**
	 * 品质
	 */
	byte qualityColor;
	
	/**
	 * 强化等级
	 */
	byte strengthenLevel;
	
	/**
	 * 精炼后下一等级ID
	 */
	int nextEquipmentId;
	
	/**
	 * 穿着需求等级
	 */
	private short needLevel ;
	
	/**
	 * 购买价格
	 */
	int buyPrice;
	/**
	 * 贩卖价格
	 */
	int sellPrice;
	/**
	 * 是否能贩卖
	 */
	boolean isSell;
	
	/**
	 * 说明
	 */
	String explain;
	
	/**
	 * 最大累加数目	单格 默认为1 
	 */
	short maxStackCount = 1;

	/**
	 * 获取 id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置 id
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 equipmentIcon
	 * @return the equipmentIcon
	 */
	public String getEquipmentIcon() {
		return equipmentIcon;
	}

	/**
	 * 设置 equipmentIcon
	 * @param equipmentIcon the equipmentIcon to set
	 */
	public void setEquipmentIcon(String equipmentIcon) {
		this.equipmentIcon = equipmentIcon;
	}

	/**
	 * 获取 equipmentType
	 * @return the equipmentType
	 */
	public byte getEquipmentType() {
		return equipmentType;
	}

	/**
	 * 设置 equipmentType
	 * @param equipmentType the equipmentType to set
	 */
	public void setEquipmentType(byte equipmentType) {
		this.equipmentType = equipmentType;
	}

	/**
	 * 获取 equipmentLevel
	 * @return the equipmentLevel
	 */
	public byte getEquipmentLevel() {
		return equipmentLevel;
	}

	/**
	 * 设置 equipmentLevel
	 * @param equipmentLevel the equipmentLevel to set
	 */
	public void setEquipmentLevel(byte equipmentLevel) {
		this.equipmentLevel = equipmentLevel;
	}

	/**
	 * 获取 qualityColor
	 * @return the qualityColor
	 */
	public byte getQualityColor() {
		return qualityColor;
	}

	/**
	 * 设置 qualityColor
	 * @param qualityColor the qualityColor to set
	 */
	public void setQualityColor(byte qualityColor) {
		this.qualityColor = qualityColor;
	}

	/**
	 * 获取 strengthenLevel
	 * @return the strengthenLevel
	 */
	public byte getStrengthenLevel() {
		return strengthenLevel;
	}

	/**
	 * 设置 strengthenLevel
	 * @param strengthenLevel the strengthenLevel to set
	 */
	public void setStrengthenLevel(byte strengthenLevel) {
		this.strengthenLevel = strengthenLevel;
	}

	/**
	 * 获取 nextEquipmentId
	 * @return the nextEquipmentId
	 */
	public int getNextEquipmentId() {
		return nextEquipmentId;
	}

	/**
	 * 设置 nextEquipmentId
	 * @param nextEquipmentId the nextEquipmentId to set
	 */
	public void setNextEquipmentId(int nextEquipmentId) {
		this.nextEquipmentId = nextEquipmentId;
	}

	/**
	 * 获取 needLevel
	 * @return the needLevel
	 */
	public short getNeedLevel() {
		return needLevel;
	}

	/**
	 * 设置 needLevel
	 * @param needLevel the needLevel to set
	 */
	public void setNeedLevel(short needLevel) {
		this.needLevel = needLevel;
	}

	/**
	 * 获取 buyPrice
	 * @return the buyPrice
	 */
	public int getBuyPrice() {
		return buyPrice;
	}

	/**
	 * 设置 buyPrice
	 * @param buyPrice the buyPrice to set
	 */
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	/**
	 * 获取 sellPrice
	 * @return the sellPrice
	 */
	public int getSellPrice() {
		return sellPrice;
	}

	/**
	 * 设置 sellPrice
	 * @param sellPrice the sellPrice to set
	 */
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	/**
	 * 获取 isSell
	 * @return the isSell
	 */
	public boolean isSell() {
		return isSell;
	}

	/**
	 * 设置 isSell
	 * @param isSell the isSell to set
	 */
	public void setSell(boolean isSell) {
		this.isSell = isSell;
	}

	/**
	 * 获取 explain
	 * @return the explain
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * 设置 explain
	 * @param explain the explain to set
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}

	/**
	 * 获取 maxStackCount
	 * @return the maxStackCount
	 */
	public short getMaxStackCount() {
		return maxStackCount;
	}

	/**
	 * 设置 maxStackCount
	 * @param maxStackCount the maxStackCount to set
	 */
	public void setMaxStackCount(short maxStackCount) {
		this.maxStackCount = maxStackCount;
	}
	
	
}
