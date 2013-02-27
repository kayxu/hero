package com.joymeng.game.domain.item.props;

import com.joymeng.game.common.GameConst;

public class PropsPrototype {
	
	/**
	 * ID
	 */
	int id;
	/**
	 * name
	 */
	String name;
	/**
	 * 类型
	 */
	byte propsType; //资源，增益 ...
	/**
	 * 图标
	 */
	String propsIcon;
	/**
	 * 使用类型
	 */
	short useType;
	private byte qulitityColor;//品质颜色
	/**
	 * 能否贩卖
	 */
	boolean canSell;
	
	/**
	 * 己方单体
	 * 己方全体
	 * 对方单体
	 * 对方全体
	 * 自己
	 * 己方0HP单体
	 * 己方0HP全体
	 */
	private	short needLevelMin = 0;	//使用物品最低等级
	private	short needLevelMax = GameConst.MAX_LEVEL;
	private	short maxStackCount;	//最大累加数目	
	
	
	
	
	/**
	 * 购买价格
	 */
	private int buyPrice;
	/**
	 * 贩卖价格
	 */
	int sellPrice;
	/**
	 * 属性1
	 */
	String property1; //材料     加制模具时  等级限制（最低等级）
	/**
	 * 属性2
	 */
	String property2; //材料     加制模具时  使用加制石数量
	/**
	 * 属性3
	 */
	String property3;
	/**
	 * 属性4
	 */
	String property4;
	/**
	 * 属性5
	 */
	String property5;
	
	
	/**
	 * 说明
	 */
	String description;
	
	
	
	
	/**
	 * 获取 propsType
	 * @return the propsType
	 */
	public byte getPropsType() {
		return propsType;
	}
	/**
	 * 设置 propsType
	 * @param propsType the propsType to set
	 */
	public void setPropsType(byte propsType) {
		this.propsType = propsType;
	}
	/**
	 * 获取 propsIcon
	 * @return the propsIcon
	 */
	public String getPropsIcon() {
		return propsIcon;
	}
	/**
	 * 设置 propsIcon
	 * @param propsIcon the propsIcon to set
	 */
	public void setPropsIcon(String propsIcon) {
		this.propsIcon = propsIcon;
	}
	/**
	 * 获取 useType
	 * @return the useType
	 */
	public short getUseType() {
		return useType;
	}
	/**
	 * 设置 useType
	 * @param useType the useType to set
	 */
	public void setUseType(short useType) {
		this.useType = useType;
	}
	
	/**
	 * 获取 needLevelMin
	 * @return the needLevelMin
	 */
	public short getNeedLevelMin() {
		return needLevelMin;
	}
	/**
	 * 设置 needLevelMin
	 * @param needLevelMin the needLevelMin to set
	 */
	public void setNeedLevelMin(short needLevelMin) {
		this.needLevelMin = needLevelMin;
	}
	/**
	 * 获取 needLevelMax
	 * @return the needLevelMax
	 */
	public short getNeedLevelMax() {
		return needLevelMax;
	}
	/**
	 * 设置 needLevelMax
	 * @param needLevelMax the needLevelMax to set
	 */
	public void setNeedLevelMax(short needLevelMax) {
		this.needLevelMax = needLevelMax;
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
	
	/**
	 * 获取 qulitityColor
	 * @return the qulitityColor
	 */
	public byte getQulitityColor() {
		return qulitityColor;
	}
	/**
	 * 设置 qulitityColor
	 * @param qulitityColor the qulitityColor to set
	 */
	public void setQulitityColor(byte qulitityColor) {
		this.qulitityColor = qulitityColor;
	}
	/**
	 * 获取 canSell
	 * @return the canSell
	 */
	public boolean isCanSell() {
		return canSell;
	}
	/**
	 * 设置 canSell
	 * @param canSell the canSell to set
	 */
	public void setCanSell(boolean canSell) {
		this.canSell = canSell;
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
	 * 获取 property1
	 * @return the property1
	 */
	public String getProperty1() {
		return property1;
	}
	/**
	 * 设置 property1
	 * @param property1 the property1 to set
	 */
	public void setProperty1(String property1) {
		this.property1 = property1;
	}
	/**
	 * 获取 property2
	 * @return the property2
	 */
	public String getProperty2() {
		return property2;
	}
	/**
	 * 设置 property2
	 * @param property2 the property2 to set
	 */
	public void setProperty2(String property2) {
		this.property2 = property2;
	}
	/**
	 * 获取 property3
	 * @return the property3
	 */
	public String getProperty3() {
		return property3;
	}
	/**
	 * 设置 property3
	 * @param property3 the property3 to set
	 */
	public void setProperty3(String property3) {
		this.property3 = property3;
	}
	/**
	 * 获取 property4
	 * @return the property4
	 */
	public String getProperty4() {
		return property4;
	}
	/**
	 * 设置 property4
	 * @param property4 the property4 to set
	 */
	public void setProperty4(String property4) {
		this.property4 = property4;
	}
	/**
	 * 获取 property5
	 * @return the property5
	 */
	public String getProperty5() {
		return property5;
	}
	/**
	 * 设置 property5
	 * @param property5 the property5 to set
	 */
	public void setProperty5(String property5) {
		this.property5 = property5;
	}
	
	
	/**
	 * 获取 description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 description
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 设置 name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
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
	/**
	 * 获取 name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
