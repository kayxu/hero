package com.joymeng.game.domain.fight;

/**
 * 战斗常量表
 * 
 * @author admin
 * @date 2012-6-1 TODO
 */
public class FightConst {
	// 战斗类型，战斗阶段表示
	public static final byte STATUS_HERO = 0;// 将战
	public static final byte STATUS_SOLDIER = 1;// 兵战斗
	public static final int MAXROUND = 50;// 最大回合数
	public static int MAXTYPE;// 总的兵种类型

	// 仍然需要一个战场坐标系统，无论客户端是否需要这种结构
	public static final int MAXDISTANCE = 5;// 最大距离
	// 移动距离
	public static final byte MOVEDISTANCE_HERO = 2;// 将领移动距离
	public static final byte MOVEDISTANCE_SOLDIER = 1;// 士兵移动距离

	// 战斗对象优先级别（0-4）不包括-1，-1只是代表战斗方类型为怪物
	public static final byte CREATURE_MONSTER = -1;// 怪物
	public static final byte CREATURE_HERO = 0;// 将领
	public static final byte CREATURE_SOLDIER_ARCHER = 1;// 弓兵
	public static final byte CREATURE_SOLDIER_RIDER = 2;// 骑兵
	public static final byte CREATURE_SOLDIER_FOOTMAN = 3;// 步兵
	public static final byte CREATURE_SOLDIER_ANGEL = 4;// 天使
	// public static final byte CREATURE_SOLDIER_GRIFFIN = 5;// 狮鹫
	// public static final byte CREATURE_SOLDIER_KNIGHT = 6;// 骑士

	// 兵种优先级[优先级][兵种类型]
	// public static final byte[][] PRIORITY = new byte[][] {
	// { 0 }, { 1, 2, 3, 5, 6, 4 },
	// { 2, 3, 1, 6, 4, 5 }, { 3, 1, 2, 4, 5, 6 }, { 4, 5, 6, 3, 2, 1 },
	// { 5, 6, 4, 6, 1, 3 }, { 6, 4, 5, 1, 3, 2 } };
	// 兵种克制，按照优先级排序
	public static final byte[] RELATION = new byte[] { -1, 2, 3, 1, -1 };

	// 作用对象类型
	// 0自己将领 1对方将领 2自己小兵 3对方小兵
	public static final byte FIGHT_TARGET_MYHERO = 0;
	public static final byte FIGHT_TARGET_OTHERHERO = 1;
	public static final byte FIGHT_TARGET_MYSOLDIER = 2;
	public static final byte FIGHT_TARGET_OTHERSOLDIER = 3;

	// 战斗指令常量
	public static final byte FIGHT_INIT = 0;// 初始化
	public static final byte FIGHT_MOVE = 1;// 移动
	public static final byte FIGHT_ATTACK = 2;// 攻击
	public static final byte FIGHT_END = 3;// 结束
	public static final byte FIGHT_SKILL = 4;// 技能
	public static final byte FIGHT_ROUND = 5;// 回合数
	public static final byte FIGHT_RESULT=6;//战果描述
	public static final byte FIGHT_HEROEND=7;//将战结束的加成
	// 攻守双方的类别
	public static final byte FTYPE_ATTACK = 0;// 攻击方
	public static final byte FTYPE_DEFENCE = 1;// 防守方

	// 战斗类型
	public static final byte FIGHTBATTLE_INFO=-3;//战斗统计
	public static final byte FIGHTBATTLE_TEST = -2;
	public static final byte FIGHTBATTLE_EVENT = -1;
	public static final byte FIGHTBATTLE_ARENA = 0;// 竞技场
	public static final byte FIGHTBATTLE_GUARD = 1;// 驻防
	public static final byte FIGHTBATTLE_CAMP = 2;// 战役
	public static final byte FIGHTBATTLE_LADDER = 3;// 通天塔
	public static final byte FIGHTBATTLE_RESOURCE = 4;// 金矿
	public static final byte FIGHTBATTLE_RESOURCE2 = 6;// 资源点
	public static final byte FIGHTBATTLE_TEC = 5;// 矿脉
	public static final byte FIGHTBATTLE_TEC2 = 7;// 资源脉
	public static final byte FIGHTBATTLE_REGION = 8;//县长争夺战
	
	public static final byte FIGHTBATTLE_CITY = 9;//市长争夺战
	public static final byte FIGHTBATTLE_CITY_CAMP = 10;//市长 军营
	public static final byte FIGHTBATTLE_CITY_STRONG = 11;//市长 据点
	public static final byte FIGHTBATTLE_STATE = 12;//州长争夺战
	public static final byte FIGHTBATTLE_STATE_CAMP = 13;//州长 军营
	public static final byte FIGHTBATTLE_STATE_STRONG = 14;//州长 据点
	public static final byte FIGHTBATTLE_COUNTRY = 15;//国王争夺战
	public static final byte FIGHTBATTLE_COUNTRY_CAMP = 16;//国王 军营
	public static final byte FIGHTBATTLE_COUNTRY_STRONG = 17;//国王 据点
	public static final byte FIGHTBATTLE_FLAG = 18;//1 vs 1
	// 战斗触发点类型
	public static final byte FIGHT_TRIGGER0 = 0;

	// 战斗结果
	public static final byte FIGHT_WIN = 1;
	public static final byte FIGHT_LOSE = 0;
	// 技能属性 水 0；风1 ；火2 ；土3 ；雷4
	public static final byte FATTRI_WATER = 0;
	public static final byte FATTRI_WIND = 1;
	public static final byte FATTRI_FIRE = 2;
	public static final byte FATTRI_GROUND = 3;
	public static final byte FATTRI_FLASH = 4;
	public static final byte FATTRI_DRAK = 5;
	//effect,技能附带的特殊效果
	public static final byte EFFECT_1=1;
	public static final byte EFFECT_2=2;
	public static final byte EFFECT_3=3;
	public static final byte EFFECT_4=4;
	public static final byte EFFECT_5=5;
	public static final byte EFFECT_6=6;
	public static final byte EFFECT_7=7;
	public static final byte EFFECT_8=8;
	public static final byte EFFECT_9=9;
	public static final byte EFFECT_10=10;
	public static final byte EFFECT_11=11;
	//真正的技能类型
	public static final byte SKILL_TYPE0=0;
	public static final byte SKILL_TYPE1=1;
	public static final byte SKILL_TYPE2=2;
	public static final byte SKILL_TYPE3=3;
	public static final byte SKILL_TYPE4=4;
	public static final byte SKILL_TYPE5=5;
	public static final byte SKILL_TYPE6=6;
	public static final byte SKILL_TYPE7=7;
	//技能id
	public static final byte SID_8=8;
	public static final byte SID_10=10;
	public static final byte SID_11=11;
	public static final byte SID_12=12;
	public static final byte SID_14=14;
//	public static final byte SID_13=13;
	public static final byte SID_62=62;
	public static final byte SID_66=66;
	public static final byte SID_67=67;
	public static final byte SID_5=5;
//	public static final byte SID=1;
	public static final byte SID_40=40;
	public static final byte SID_43=43;
//	public static final byte SID=1;
	public static final byte SID_63=63;
	public static final byte SID_31=31;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
//	public static final byte SID=1;
	//兵战类型
	public static final byte SOLDIER_TYPE1=1;
	public static final byte SOLDIER_TYPE2=2;
	public static final byte SOLDIER_TYPE3=3;
	public static final byte SOLDIER_TYPE4=4;
	public static final byte SOLDIER_TYPE5=5;
	public static final byte SOLDIER_TYPE6=6;

}
