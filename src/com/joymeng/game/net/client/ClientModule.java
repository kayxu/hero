package com.joymeng.game.net.client;

import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 
 * 对应于客户端各个拆分的模块
 * 
 * @author Shaolong Wang
 * 
 */
public interface ClientModule {
	
	public static final byte NTC_SYS_TIME = 127;//系统时间
	public static final byte NTC_DTCD_NONE = -1;
	public static final byte NTC_DTCD_HERO = 0; // 固化将领数据
	public static final byte NTC_DTCD_PLAYERHERO=1;//玩家将领
	public static final byte NTC_DTCD_BUILDING = 2;//固化建筑数据
	public static final byte NTC_PLAY_BUILDING = 3;//用户建筑数据
	public static final byte NTC_DTCD_EQUIPMENT = 4;
	public static final byte NTC_PLAY_EQUIPMENT = 5;
	public static final byte NTC_SOLDIER=6;//士兵数据
	public static final byte NTC_SKILL=7;//技能数据
	public static final byte NTC_ROLEDATA=8;
//	public static final byte NTC_EQUIPPROTOTYPE = 9;//装备原形数据
	public static final byte NTC_EQUIPMENT = 9;//用户装备数据
	public static final byte NTC_PROPS = 10;//用户装备数据
	public static final byte NTC_FIGHTEVENT=11;//战斗结果数据
	public static final byte NTC_TRAININGSOLDIER = 12;//训练士兵数据
	public static final byte NTC_NATION=13;//世界数据
	public static final byte NTC_SHOP=14;//商店
	public static final byte NTC_ARENA=15;//竞技场
	public static final byte NTC_FRIEND=16;//好友
	public static final byte NTC_MAIL=17;
	public static final byte NTC_SIMPLE_ROLEDATA=18;
	public static final byte NTC_SIMPLE_HERO = 19; // 简化将领数据
	public static final byte NTC_NATION_VEINS=20;//科技
	public static final byte NTC_NATION_GOLD=21;//资源点
	public static final byte NTC_SIMPLERESOURCE=22;//个人简略资源信息
	public static final byte NTC_FIGHTMESSAGE=23;//战斗消息
	public static final byte NTC_QUEST=24;//任务
	public static final byte NTC_PROPS_BOX = 35;//道具宝箱
	public static final byte RANK_GAME_MONEY = 30;//金币排行榜
	public static final byte RANK_HERO= 31;//武将排行榜
	public static final byte RANK_PROPS_DELAY= 32;//延时道具
	public static final byte NTC_COUNTY_BATTLE = 33;//县长争夺站
	public static final byte NTC_CAMP_BATTLE = 34;//军营
	public static final byte NTC_SHRONG_BATTLE = 35;//据点
	public static final byte NTC_PLAYERACHE = 36;//用户政绩数据
	public static final byte NTC_SOUND = 37;//声音协议
	public static final byte NTC_USER_WAR = 38;//用户积分
	public static final byte NTC_BATTLE_FIELD_INFO = 39;//战报开启信息
	public static final byte NTC_SYS_AWARD = 40;//系统奖励信息
	public static final byte NTC_SIMPLE_PAGE = 41;//翻页信息
	
	public static final byte NTC_POWER = 42;//战斗力
	
	public static final byte NTC_FLAG = 43;//格子
	public static final byte NTC_ROOM = 44;//房间
	public static final byte NTC_GAME_HOME = 45;//用户指令
	public static final byte NTC_ROAD = 46;//路
	
	
	public static final byte NTC_START_1VS1 = 47;//房间
	public static final byte NTC_HERO_POINT = 48;//buff
	
	public static final byte NTC_CARD_ACTIVITY = 49;//翻牌
	
	public static final byte NTC_ARENA_RANK = 50;//竞技场排行榜
	public static final byte NTC_WAR_STOP = 51;//争夺战结束发送消息
	
	public static final byte NTC_REFRESH_RESOURCES=52;//资源刷新
	
	public static final byte NTC_SIGN_IN = 53;//签到
	// 版本模块
	/**
	 * 模块类型
	 * 
	 * @return
	 */
	public byte getModuleType();

	/**
	 * 串行化
	 * 
	 * @param out
	 */
	public void serialize(JoyBuffer out);
	public void deserialize(JoyBuffer in);
}
