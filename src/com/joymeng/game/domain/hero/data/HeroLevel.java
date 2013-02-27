package com.joymeng.game.domain.hero.data;

/**
 * 将领品级表
 * @author admin
 * @date 2012-5-3
 * TODO
 */
public class HeroLevel {
	private int id;
	private byte level;// 品级(对程序没有意义)
	private byte color;// 品质
	private int minAttack;// 最小攻击
	private int maxAttack;// 最大攻击
	private int defence;// 初始防御
	private int minHp;// 最小生命
	private int maxHp;// 最大生命
	private int attackAdd;// 攻击加成
	private int defenceAdd;// 防御加成
	private int HpAdd;// 生命加成
	private int skillRate1;// 1-4技能格几率
	private int skillRate2;// 5技能几率
	private int skillRate3;// 6 技能几率

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public byte getColor() {
		return color;
	}

	public void setColor(byte color) {
		this.color = color;
	}

	public int getMinAttack() {
		return minAttack;
	}

	public void setMinAttack(int minAttack) {
		this.minAttack = minAttack;
	}

	public int getMaxAttack() {
		return maxAttack;
	}

	public void setMaxAttack(int maxAttack) {
		this.maxAttack = maxAttack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getMinHp() {
		return minHp;
	}

	public void setMinHp(int minHp) {
		this.minHp = minHp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getAttackAdd() {
		return attackAdd;
	}

	public void setAttackAdd(int attackAdd) {
		this.attackAdd = attackAdd;
	}

	public int getDefenceAdd() {
		return defenceAdd;
	}

	public void setDefenceAdd(int defenceAdd) {
		this.defenceAdd = defenceAdd;
	}

	public int getHpAdd() {
		return HpAdd;
	}

	public void setHpAdd(int hpAdd) {
		HpAdd = hpAdd;
	}

	public int getSkillRate1() {
		return skillRate1;
	}

	public void setSkillRate1(int skillRate1) {
		this.skillRate1 = skillRate1;
	}

	public int getSkillRate2() {
		return skillRate2;
	}

	public void setSkillRate2(int skillRate2) {
		this.skillRate2 = skillRate2;
	}

	public int getSkillRate3() {
		return skillRate3;
	}

	public void setSkillRate3(int skillRate3) {
		this.skillRate3 = skillRate3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
