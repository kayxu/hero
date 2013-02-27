package com.joymeng.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LogType {
	LOGIN(LoggerFactory
			.getLogger("login")), // 创建、进入、离开、升级
			
	ONLINE_NUM(LoggerFactory
			.getLogger("online_num")),//在线玩家数量
	ONLINE_TIME(LoggerFactory.getLogger("online_time")),//玩家在线时长
	BOX_USE_NUM(LoggerFactory.getLogger("box_use_num")),//每日大转盘使用数量
	COST_CHIP(LoggerFactory.getLogger("cost_chip")),//大转盘每日使用筹码数量
	AWARD_LIST(LoggerFactory.getLogger("award_list")),//大转盘每日奖品产出列表
	USE_DIAMOND(LoggerFactory.getLogger("use_diamond")),//使用钻石
	BUY_PROPS(LoggerFactory.getLogger("buy_props")),//购买道具
	USE_PROPS(LoggerFactory.getLogger("use_props")),//使用道具
	SERVER_INFO(LoggerFactory.getLogger("server_info")),//服务器信息
	QUEST_INFO(LoggerFactory.getLogger("quest")),//任务信息
	NEW_USER(LoggerFactory.getLogger("new_user")),//新增用户
	REFRESH_BOX(LoggerFactory.getLogger("refresh_box")),//刷新宝箱，大转盘
	ROLL_BOX(LoggerFactory.getLogger("roll_box")),//转动大转盘
	FLIP_CARD(LoggerFactory.getLogger("flip_card")),//翻牌
	ROTATE_CARD(LoggerFactory.getLogger("rotate_card")),//旋转牌
	HERO_FRESH(LoggerFactory.getLogger("hero_fresh")),//将领刷新
	HERO_TRAIN(LoggerFactory.getLogger("hero_train")),//将领训练
	SHOUT(LoggerFactory.getLogger("shout")),//喊话
	WEAPON_FRESH(LoggerFactory.getLogger("weapon_fresh")),//铁匠铺刷新
	WEAPON_BUY(LoggerFactory.getLogger("weapon_buy")),//铁匠铺购买
	SOLDIER(LoggerFactory.getLogger("soldier")),//士兵购买
	ARENA(LoggerFactory.getLogger("arena")),//战斗
	TITLE(LoggerFactory.getLogger("title")),//设置tile
	WARSTART(LoggerFactory.getLogger("warStart")),//争夺战
	;

	Logger logger;

	public Logger getLogger() {
		return logger;
	}

	private LogType(Logger logger) {
		this.logger = logger;
	}

	public static LogType[] values = values();

	public static LogType[] getValues() {
		return values;
	}
}