package com.joymeng.game;

/**
 * 错误信息提示
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class ErrorMessage {
	public static final int NO_PLAYER = -1;
	public static final int NO_HERO = -2;
	public static final int NO_BUILD = -3;
//	public static final int ERROR_SEAT = -4;
	public static final int NO_MONEY = -5;
	
	public static final int ERROR_ROUND = -6;
//	public static final int ERROR_RANDOM = -7;
	public static final int ERROR_SHUTWODN = -8;
	public static final int ERROR_TIME = -9;
	public static final int ERROR_MONEY = -10;
	public static final int ERROR_PWD = -11;
	public static final int ERROR_CMD = -12;
	
	public static final int MAX_HERONUM = -13;
	public static final int ERROR_NO_JOYMONEY = -14;
	public static final int NO_BOOK=-15;
	public static final int ERROR_SKILLCOMPOSE=-16;//
	public static final String[] ERROR_STR = { "",// 0
			"没有该玩家",// -1
			"没有该将领",// -2
			"没有该建筑",// -3
			"位置错误",// -4
			"金钱不足",// -5
			"回合数错误",// -6
			"验证错误",// -7
			"uuid错误",// -8
			"时间错误",// -9
			"操作错误,金额不对",// -10
			"密码错误",// -11
			"指令错误",// -12
			"已经达到将领上限",// -13
			"乐币不足",// -14
			"技能书不存在",//-15
			"技能合成错误",//-16
	};
}
