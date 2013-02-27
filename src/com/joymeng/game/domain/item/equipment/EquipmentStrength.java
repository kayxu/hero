/**
 * Copyright com.joymeng.game.domain.Equipment-EquipmentStrength.java
 * @author xufangliang
 * @time 2012-5-3
 */
package com.joymeng.game.domain.item.equipment;

/**
 * @author xufangliang
 *  1.1
 *  装备强化
 */
public class EquipmentStrength {
//	攻击力 = (装备等级+等级偏移)*基础攻击*（品质系数 + 等级系数*白装品质系数）																				
//			血量 = (装备等级+等级偏移)*基础血量*（品质系数 + 锻造等级系数*白装品质系数）																				
//			带兵数 =【(武将等级+等级偏移）*100 + 向下取整(武将等级/4)*(向下取整(武将等级/4)+1)*50】*（品质系数+锻造等级系数*白装品质系数）/5																				
//			护甲值 = （装备等级+等级偏移）*（护甲系数*0.7/0.3 -3.5）*（品质系数/橙色品质系数）*（锻造等级系数/10级锻造系数）																				

	/**
	 * id
	 */
	int id;
	
	/**
	 * 1:强化  2:精炼 3:升阶
	 */
	byte useType;
	/**
	 * 目标等级、精炼。升阶
	 */
	byte upLevel;
	
	byte equipLevel; //装备等级

	/**
	 * 材料ID,0为金钱
	 */
	int PropsId;
	
	/**
	 * 消耗数量
	 */
	int costCount;
	
	
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
	 * 获取 useType
	 * @return the useType
	 */
	public byte getUseType() {
		return useType;
	}

	/**
	 * 设置 useType
	 * @param useType the useType to set
	 */
	public void setUseType(byte useType) {
		this.useType = useType;
	}

	/**
	 * 获取 upLevel
	 * @return the upLevel
	 */
	public byte getUpLevel() {
		return upLevel;
	}

	/**
	 * 设置 upLevel
	 * @param upLevel the upLevel to set
	 */
	public void setUpLevel(byte upLevel) {
		this.upLevel = upLevel;
	}

	/**
	 * 获取 propsId
	 * @return the propsId
	 */
	public int getPropsId() {
		return PropsId;
	}

	/**
	 * 设置 propsId
	 * @param propsId the propsId to set
	 */
	public void setPropsId(int propsId) {
		PropsId = propsId;
	}

	/**
	 * 获取 costCount
	 * @return the costCount
	 */
	public int getCostCount() {
		return costCount;
	}

	/**
	 * 设置 costCount
	 * @param costCount the costCount to set
	 */
	public void setCostCount(int costCount) {
		this.costCount = costCount;
	}


	/**
	 * 获取 equipLevel
	 * @return the equipLevel
	 */
	public byte getEquipLevel() {
		return equipLevel;
	}

	/**
	 * 设置 equipLevel
	 * @param equipLevel the equipLevel to set
	 */
	public void setEquipLevel(byte equipLevel) {
		this.equipLevel = equipLevel;
	}
	
}
