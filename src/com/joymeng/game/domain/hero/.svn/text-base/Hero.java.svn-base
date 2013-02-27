package com.joymeng.game.domain.hero;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 固化将领数据
 * @author admin
 * @date 2012-5-2
 * TODO
 */
public class Hero extends ClientModuleBase{
	static final Logger logger = LoggerFactory.getLogger(Hero.class);
	private int levelId;//品级id对应herolevel表中的level
	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	// 同playerHero的区别主要在于，没有userId，增加了minLevel、maxLevel
	private int minLevel;// 刷新需求酒馆级别
	private int maxLevel;// 刷新需求酒馆级别
	// id
	private int id;
	//
	private int baseId;
	// 名称
	private String name;
	// 头像
	private String icon;
	// 性别
	private byte sex = 0;// 角色性别（0:男 1:女）
	// 攻击
	private int attack;
	// 防御
	private int defence;
	// 生命
	private int maxHp;

	// 攻击成长
	private int attackAdd;
	// 防御成长
	private int defenceAdd;
	// 生命成长
	private int hpAdd;
	// 颜色
	private byte color;
	// 带兵数
	private int soldierNum;
	//天生技能
	private int bornSkill;
	//技能格子
	private byte skillNum;
	//说明文字
	private String memo;
	//贩卖价格
	private int money;
	//是否是名将，服务器生成的属性
	private boolean isSpecial;
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


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
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
		return hpAdd;
	}

	public void setHpAdd(int hpAdd) {
		this.hpAdd = hpAdd;
	}

	public byte getColor() {
		return color;
	}

	public void setColor(byte color) {
		this.color = color;
	}

	public int getSoldierNum() {
		return soldierNum;
	}

	public void setSoldierNum(int soldierNum) {
		this.soldierNum = soldierNum;
	}




	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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


	public byte getSkillNum() {
		return skillNum;
	}

	public void setSkillNum(byte skillNum) {
		this.skillNum = skillNum;
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

	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		return NTC_DTCD_HERO;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(minLevel);
		out.putInt(maxLevel);
		out.putInt(id);
		out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(icon,JoyBuffer.STRING_TYPE_SHORT);
		out.put(sex);
		out.putInt(attack);
		out.putInt(defence);
		out.putInt(maxHp);
		out.putInt(attackAdd);
		out.putInt(defenceAdd);
		out.putInt(hpAdd);
		out.put(color);
		out.putInt(soldierNum);
		out.putInt(bornSkill);
		out.put(skillNum);
		out.putPrefixedString(memo,(byte)2);
		out.putInt(money);
	}
//	public void print(){
//		logger.info("minLevel=="+getMinLevel());
//		logger.info("maxLevel=="+getMaxLevel());
//		logger.info("id=="+getId());
//		logger.info("name=="+getName());
//		logger.info("icon=="+getIcon());
//		logger.info("sex=="+getSex());
//		logger.info("attack=="+getAttack());
//		logger.info("defence=="+getDefence());
//		logger.info("maxHp=="+getMaxHp());
//		logger.info("attackAdd=="+getAttackAdd());
//		logger.info("defenceAdd=="+getDefenceAdd());
//		logger.info("hpAdd=="+getHpAdd());
//		logger.info("color=="+getColor());
//		logger.info("soldierNum=="+getSoldierNum());
//		logger.info("bornSkill=="+getBornSkill());
//		logger.info("skillNum=="+getSkillNum());
//		logger.info("memo=="+getMemo());
//		logger.info("money=="+getMoney());
//	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.minLevel=in.getInt();
		this.maxLevel=in.getInt();
		this.id=in.getInt();
		this.name=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.icon=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.sex=in.get();
		this.attack=in.getInt();
		this.defence=in.getInt();
		this.maxHp=in.getInt();
		this.attackAdd=in.getInt();
		this.defenceAdd=in.getInt();
		this.hpAdd=in.getInt();
		this.color=in.get();
		this.soldierNum=in.getInt();
		this.bornSkill=in.getInt();
		this.skillNum=in.get();
		this.memo=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.money=in.getInt();
	}

	public int getBaseId() {
		return baseId;
	}

	public void setBaseId(int baseId) {
		this.baseId = baseId;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
	
}
