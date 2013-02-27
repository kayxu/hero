package com.joymeng.game.domain.hero.data;

/**
 * 将领基础数据表
 * @author admin
 * @date 2012-5-3
 * TODO
 */
public class HeroData {
	private int id;// id
	private int nameType;// 姓名类别(1男,2女,其他对应名将 姓名id)
	private String icon;// 头像
	private byte sex;// 性别
	private String memo;// 说明
	private byte levelId;// 品级id
	private int money;// 购买价格
	private int bornSkill;// 初始技能
	private int minLevel;// 刷新需求酒馆级别
	private int maxLevel;// 刷新需求酒馆级别
	private byte groupId;// 组别id

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getNameType() {
		return nameType;
	}

	public void setNameType(int nameType) {
		this.nameType = nameType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public byte getLevel() {
		return levelId;
	}

	public void setLevel(byte level) {
		this.levelId = level;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getBornSkill() {
		return bornSkill;
	}

	public void setBornSkill(int bornSkill) {
		this.bornSkill = bornSkill;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public byte getGroupId() {
		return groupId;
	}

	public void setGroupId(byte groupId) {
		this.groupId = groupId;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
