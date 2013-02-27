package com.joymeng.game.domain.skill;


/**
 * 技能固化数据
 * @author admin
 * @date 2012-5-2
 * TODO
 */
public class Skill {
	int id;
	String name;
	String icon;
	String ani;
	String attackedAni;

	int level;
	byte attri;//属性 水 0；风1 ；火2 ；土3 ；雷4
//	<Item id="0" value="水" />
//	<Item id="1" value="风" />
//	<Item id="2" value="火" />
//	<Item id="3" value="土" />
//	<Item id="4" value="雷" />
//	<Item id="5" value="暗" />

	byte type;//（作用对象，自己武将0，对方武将1，自己小兵2，对方小兵3）
	byte trigger;//触发点
	byte rate;//触发几率
//	int skillAdd;//技能成长
	byte status;//附加状态（4 麻痹）
	byte round;//持续回合
	String memo;//说明
//	private byte dark;//暗属性
	int skillType;//技能类别
	
	byte special;//特殊效果()
	private int attack;
	private int defence;
	private int hp;
	private int attackRate;
	private int defenceRate;
	private int hpRate;
	private int reduceHurt;
	private int realHurt;
	private int realHurtRate;
	
	private int soldierAttack;//士兵攻击
	private int soldierDefence;//士兵防御
	private int soldierDeadRate;//士兵死亡
	private int endExp;
	private int endMoney;
	
	private int soldierNum;//带兵数
	private int trainTime;//训练时间缩短
	
	private int backup1; //触发条件
//	0 将领死亡不触发
//	1 获胜触发
//	2 全部触发
//	3 攻击方触发
//	4 防守方触发

	private int backup2;//技能类型-1 被动1兵战
	private int backup3;//	士兵类型 
//	0 全部
//	1 弓兵
//	2 骑兵 
//	3步兵
//	4 特种兵

	private int backup4;//类型细分;
	private int backup5;
	private int backup6;
	private int backup7;
	private int backup8;
	//------------需要特殊处理---------------------
	// 重伤几率

	private int hurtRebound;//伤害反弹

	private int hpResume;//恢复生命

	private int hpResumeRate;//恢复生命比例
	
	private int suckBlood; // 吸血

	private int enchant;//蛊惑

	private int seriousInjury;//重伤
//	
//	private int injured ;//被重伤--废弃，统一用seriousInjury

	
	private int skillHurt;//技能伤害

	private int skillProbability;//技能释放几率




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
	
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public byte getAttri() {
		return attri;
	}
	public String getAttackedAni() {
		return attackedAni;
	}
	public void setAttackedAni(String attackedAni) {
		this.attackedAni = attackedAni;
	}
	public void setAttri(byte attri) {
		this.attri = attri;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte getTrigger() {
		return trigger;
	}
	public void setTrigger(byte trigger) {
		this.trigger = trigger;
	}
	public byte getRate() {
		return rate;
	}
	public void setRate(byte rate) {
		this.rate = rate;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public byte getRound() {
		return round;
	}
	public void setRound(byte round) {
		this.round = round;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public byte getSpecial() {
		return special;
	}
	public void setSpecial(byte special) {
		this.special = special;
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
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getAttackRate() {
		return attackRate;
	}
	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}
	public int getDefenceRate() {
		return defenceRate;
	}
	public void setDefenceRate(int defenceRate) {
		this.defenceRate = defenceRate;
	}
	public int getHpRate() {
		return hpRate;
	}
	public void setHpRate(int hpRate) {
		this.hpRate = hpRate;
	}
	public int getReduceHurt() {
		return reduceHurt;
	}
	public void setReduceHurt(int reduceHurt) {
		this.reduceHurt = reduceHurt;
	}
	public int getRealHurt() {
		return realHurt;
	}
	public void setRealHurt(int realHurt) {
		this.realHurt = realHurt;
	}
	public int getRealHurtRate() {
		return realHurtRate;
	}
	public void setRealHurtRate(int realHurtRate) {
		this.realHurtRate = realHurtRate;
	}
	public int getSoldierAttack() {
		return soldierAttack;
	}
	public void setSoldierAttack(int soldierAttack) {
		this.soldierAttack = soldierAttack;
	}
	public int getSoldierDefence() {
		return soldierDefence;
	}
	public void setSoldierDefence(int soldierDefence) {
		this.soldierDefence = soldierDefence;
	}
	public int getSoldierDeadRate() {
		return soldierDeadRate;
	}
	public void setSoldierDeadRate(int soldierDeadRate) {
		this.soldierDeadRate = soldierDeadRate;
	}
	public int getEndExp() {
		return endExp;
	}
	public void setEndExp(int endExp) {
		this.endExp = endExp;
	}
	public int getEndMoney() {
		return endMoney;
	}
	public void setEndMoney(int endMoney) {
		this.endMoney = endMoney;
	}
	public int getSoldierNum() {
		return soldierNum;
	}
	public void setSoldierNum(int soldierNum) {
		this.soldierNum = soldierNum;
	}
	public int getTrainTime() {
		return trainTime;
	}
	public void setTrainTime(int trainTime) {
		this.trainTime = trainTime;
	}
	public int getSeriousInjury() {
		return seriousInjury;
	}
	public void setSeriousInjury(int seriousInjury) {
		this.seriousInjury = seriousInjury;
	}
//	public byte getDark() {
//		return dark;
//	}
//	public void setDark(byte dark) {
//		this.dark = dark;
//	}
	public int getSkillType() {
		return skillType;
	}
	public void setSkillType(int skillType) {
		this.skillType = skillType;
	}
	public int getHurtRebound() {
		return hurtRebound;
	}
	public void setHurtRebound(int hurtRebound) {
		this.hurtRebound = hurtRebound;
	}
	public int getHpResume() {
		return hpResume;
	}
	public void setHpResume(int hpResume) {
		this.hpResume = hpResume;
	}
	public int getHpResumeRate() {
		return hpResumeRate;
	}
	public void setHpResumeRate(int hpResumeRate) {
		this.hpResumeRate = hpResumeRate;
	}
	public int getSuckBlood() {
		return suckBlood;
	}
	public void setSuckBlood(int suckBlood) {
		this.suckBlood = suckBlood;
	}
	public int getEnchant() {
		return enchant;
	}
	public void setEnchant(int enchant) {
		this.enchant = enchant;
	}
	public int getSkillHurt() {
		return skillHurt;
	}
	public void setSkillHurt(int skillHurt) {
		this.skillHurt = skillHurt;
	}
	public int getSkillProbability() {
		return skillProbability;
	}
	public void setSkillProbability(int skillProbability) {
		this.skillProbability = skillProbability;
	}
	public int getBackup1() {
		return backup1;
	}
	public void setBackup1(int backup1) {
		this.backup1 = backup1;
	}
	public int getBackup2() {
		return backup2;
	}
	public void setBackup2(int backup2) {
		this.backup2 = backup2;
	}
	public int getBackup3() {
		return backup3;
	}
	public void setBackup3(int backup3) {
		this.backup3 = backup3;
	}
	public int getBackup4() {
		return backup4;
	}
	public void setBackup4(int backup4) {
		this.backup4 = backup4;
	}
	public int getBackup5() {
		return backup5;
	}
	public void setBackup5(int backup5) {
		this.backup5 = backup5;
	}
	public int getBackup6() {
		return backup6;
	}
	public void setBackup6(int backup6) {
		this.backup6 = backup6;
	}
	public int getBackup7() {
		return backup7;
	}
	public void setBackup7(int backup7) {
		this.backup7 = backup7;
	}
	public int getBackup8() {
		return backup8;
	}
	public void setBackup8(int backup8) {
		this.backup8 = backup8;
	}
		
}
