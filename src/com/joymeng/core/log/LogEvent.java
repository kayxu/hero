/**
 * 
 */
package com.joymeng.core.log;

/**
 * 日志事件
 * 
 * @author gejing
 */
public enum LogEvent {
	ROLE_LOGIN(LogType.LOGIN, "角色进入游戏"), // 
	ONLINE_NUM(LogType.ONLINE_NUM,"在线玩家数量"),//
	ONLINE_TIME(LogType.ONLINE_TIME,"玩家在线时长"),
	BOX_USE_NUM(LogType.BOX_USE_NUM,"大转盘每日使用数量"),
	COST_CHIP(LogType.COST_CHIP,"大转盘每日消耗筹码数量"),
	AWARD_LIST(LogType.AWARD_LIST,"大转盘奖品产出列表"), 
	USE_DIAMOND(LogType.USE_DIAMOND,"使用钻石"),
	BUY_PROPS(LogType.BUY_PROPS,"购买道具"),
	USE_PROPS(LogType.USE_PROPS,"使用道具"),
	SERVER_INFO(LogType.SERVER_INFO,"服务器信息"),
	QUESTINFO(LogType.QUEST_INFO,"完成任务"),

	NEW_USER(LogType.NEW_USER,"新增用户"),
	REFRESH_BOX(LogType.REFRESH_BOX,"刷新宝箱"),
	ROLL_BOX(LogType.ROLL_BOX,"转动宝箱"),
	FLIP_CARD(LogType.FLIP_CARD,"翻牌"),
	ROTATE_CARD(LogType.ROTATE_CARD,"旋转牌"),
	HERO_FRESH(LogType.HERO_FRESH,"刷新将领"),
	HERO_TRAIN(LogType.HERO_TRAIN,"训练将领"),
	SHOUT(LogType.SHOUT,"喊话"),
	WEAPON_FRESH(LogType.WEAPON_FRESH,"铁匠铺刷新"),
	WEAPON_BUY(LogType.WEAPON_BUY,"铁匠铺购买"),
	SOLDIER(LogType.SOLDIER,"士兵购买"),
	ARENA(LogType.ARENA,"竞技场"),
	TITLE(LogType.TITLE,"设置国家州等头衔"),
	WARSTART(LogType.WARSTART,"争夺战开启");

//	GOLD_CHARGE(LogType.GOLD, "直充充值"), // 是否成功（1/0）(-1表示异常)，增加元宝，增加后元宝
//	GOLD_CHARGE_DUPLICATE(LogType.GOLD, "直充充值重复"), // skyId
//	GOLD_MALL_BUY(LogType.GOLD, "商城道具购买"), // 元宝花费，剩余元宝，道具ID，数量，
//
//	GOLD_SYSTEM_GOLD(LogType.GOLD, "系统赠送元宝"), // 系统赠送元宝
//
//	GOLD_JIMAI_BUY(LogType.GOLD, "购买寄卖元宝"), // 花的金币，购买的元宝，卖主ID
//	GOLD_JIMAI_CANCEL(LogType.GOLD, "取消寄卖元宝"), // 返回元宝
//	GOLD_TAOBAO_BUY(LogType.GOLD, "淘宝购买"), // needgold,playerGold,itemId
//	GOLD_TAOBAO_REFRSH(LogType.GOLD, "淘宝刷新"), // needGold,playerGold
//
//	GOLD_ONHOOK(LogType.GOLD, "挂机领取"), K_CHARGE(LogType.GOLD, "K币充值"), // 充值K币，置换的GOLD，充值后GOLD
//	K_CHARGE_ERROR(LogType.GOLD, "K币充值失败"), // 失败原因
//	GOLD_SMS_CHARGE(LogType.GOLD, "短信充值"), // 是否成功（1/0）(-1表示异常)byte)，增加元宝int，增加后玩家元宝int
//	// GOLD_INTERGRAL(LogType.GOL);
//	LOTTERY_WINNING(LogType.GOLD, "彩票中奖"), // 彩票期号，一等奖数目，二等奖数目，元宝数目
//	BUY_LOTTERY(LogType.GOLD, "购买彩票"), // 彩票期号，号码，数量
//	LUCK_GET_GOLD(LogType.GOLD, "游戏内幸运得到元宝"),
//
//	TRANS(LogType.PLAYER, "地图传送"), //
//
//	// >>>>>>>>>>>>>物品使用及出售
//	NORMAL_USE(LogType.PLAYER, "物品使用"), // 道具ID
//	PLAYER_BOX_DROP(LogType.PLAYER, "宝箱使用"), // 道具ID，[金币(修为等)*数量]，[数量*物品数据...]，是/否放妖
//	PLAYER_PACKAGE_DROP(LogType.PLAYER, "包裹使用"), // 道具ID，[数量*物品数据...]
//	SELL_ITEM(LogType.PLAYER, "物品出售"), // 数量*物品数据，单价,得到金币
//
//	// >>>>>>>>>>>>>杀怪、被怪杀
//	PLAYER_KILL_MOB(LogType.KILL_MOB, "怪物掉落"), // 怪物ID(,ID,ID)，经验，修为，银两，[是/否得到：数量*物品数据]
//	PLAYER_BEKILL(LogType.PLAYER, "被怪杀死"), // 怪物ID(,ID,ID)，是/否使用替身符，[损失经验]
//
//	PLAYER_SYSTEM_PROPS(LogType.PLAYER, "系统赠送道具"), // 系统赠送道具
//
//	LEARN_ALLY_SKILL(LogType.PLAYER, "学习宗派技能"), // 技能ID，学习前等级，需要修为，银两，资材，宗派贡献
//	LEARN_SKILL(LogType.PLAYER, "学习门派技能"), // 技能ID，学习前等级，需要修为，银两
//
//	// >>>>>>>>>>>>>关系操作
//	CHAT_WITH_GIFT(LogType.PLAYER, "聊天赠礼物"), // 对方ID，物品ID
//	MARRY(LogType.PLAYER, "结婚"), DISMARRY(LogType.PLAYER, "离婚"),
//
//	BE_EDU(LogType.PLAYER, "收徒"), // 师徒柬去除
//	BE_EDU_PRENTICE_FINISH(LogType.PLAYER, "徒弟出师"), // 徒弟0/师傅1，经验奖励，修为奖励，师傅与徒弟友好度，徒弟与师傅友好度，[道具ID，数量]
//
//	REWARD_GET(LogType.PLAYER, "领取在线奖励"), // 领取在线奖励
//
//	// 角色创建，升级==
//	ROLE_CREATE(LogType.LOGIN, "创建角色"), // ID, SKYID, 男女，种族，门派，人名，
//
//	ROLE_LEAVE(LogType.LOGIN, "角色离开游戏"), // SKYID, 掉线/主动
//	ROLE_LEVEL_UP(LogType.LOGIN, "角色升级"),
//
//	// >>>>>>>>>>>>>背包、仓库操作
//	PUT_TO_STORE(LogType.PLAYER, "放入仓库"), // 数量*物品数据，哪号仓库
//	TAKE_FROM_STORE(LogType.PLAYER, "从仓库取出"), // 数量*物品数据，哪号仓库
//	ERROR_ADD_ITEM(LogType.PLAYER, "错误增加物品"), // 在物品无法装下的情况下依然增加物品。 记录
//	//
//
//	FAMILY_DAILY_AWARD(LogType.PLAYER, "家族每日福利"), DAILY_ANSWER(LogType.PLAYER,
//			"每日答题"), // 答对数目，经验，银两
//	EXIT_LOTTERY(LogType.PLAYER, "下线抽奖"), // item
//	BATTLE_LOTTERY(LogType.PLAYER, "战斗抽奖"), // item
//	RECEIVE_WAGE(LogType.PLAYER, "领取工资"), // 类型（1,道具;2,装备），物品id，数量
//	WAGE_LOTTERY(LogType.PLAYER, "工资抽奖"), // 工资抽奖结果
//
//	EQUIP_EMBED(LogType.EQUIP, "装备镶嵌"), // 各种幸运符使用数量，使用的吸附石属性，装备数据，成功与否
//	EQUIP_ENCHANT(LogType.EQUIP, "装备强化"), EQUIP_ENCHANT_STONE(LogType.EQUIP,
//			"宝石合成"), PICTURE_SYNTHESIS(LogType.EQUIP, "神工图合成"), EQUIP_RESOLVE(
//			LogType.EQUIP, "装备分解"),
//
//	GOLD_QIANZHUANG(LogType.GOLD, "寄卖元宝"), GOLD_QIANZHUANG_BUY(LogType.GOLD,
//			"钱庄购买"), GOLD_QIANZHUANG_DOWN(LogType.GOLD, "钱庄下架"), GOLD_QIANZHUANG_TAX(
//			LogType.GOLD, "寄卖元宝税"),
//
//	BOOTH_UP(LogType.BOOTH, "摆摊上架"), // 道具/装备，数量，物品数据，金币
//	BOOTH_DOWN(LogType.BOOTH, "摆摊下架"), // 道具/装备，数量，物品数据
//	BOOTH_BUY(LogType.BOOTH, "摆摊购买"), // 摊主ID，物品数据，花费金币
//
//	PK_FIGHT_USE(LogType.PK, "战斗使用"), // 道具ID
//
//	TRADE_SUCESS(LogType.TRADE, "交易成功"), // 甲方ID，乙方ID，甲方金币，乙方金币，[甲方物品数据]，[乙方物品数据]
//	TRADE_LOCK(LogType.TRADE, "交易锁定"),
//
//	ALLY_CREATE(LogType.ALLY, "创建帮派"), // allyID，花费金币，..
//	ALLY_PROP_DONATION(LogType.ALLY, "帮派资金捐献"), ALLY_CONSTRUCTION_BUILDING(
//			LogType.ALLY, "帮派建筑升级"), ALLY_ADD_CONSTRUCTION_SPEED(LogType.ALLY,
//			"帮派建筑加速"), // allyId,建筑类型，建筑等级，加速的玩家Id
//	ALLY_BUFF_UPDATE(LogType.ALLY, "帮派科技升级"), // allyId,buffer类型
//	ALLY_ADD_BUFF_SPEED(LogType.ALLY, "帮派科技加速"), // 科技加速
//													// allyId,buffer类型，等级，加速玩家Id
//	ALLY_SEND_WAGE(LogType.ALLY, "发放工资"), // allyId,发放工资的成员，消耗的帮派资金
//	ALLY_QUEST_ADD(LogType.ALLY, "帮派任务增加"),
//
//	MOXING_DROP(LogType.ACTIVITY, "魔星掉落"), // [否/是邮寄：数量*物品数据...]
//	DOUFA_REWARD(LogType.ACTIVITY, "斗法奖励"), // 修为，银两，[金仙排名，否/是邮寄，道具ID，道具数量]
//	XIANMO_REWARD(LogType.ACTIVITY, "仙魔大战"), // 积分，经验，银两，[仙魔加成]
//	XIANMO_EXTRA_REWARD(LogType.ACTIVITY, "仙魔大战额外物品奖励"), // 物品ID，物品数量
//
//	ONLINE_RANDOM_GIFT(LogType.ACTIVITY, "在线随机奖励"), // 赠送经验，赠送银两，（若是不为-1则发送到邮箱）赠送物品ID，赠送数量
//
//	CHARGE_AMOUNT(LogType.ACTIVITY, "充值累计活动"), // 奖励元宝，奖励银两，奖励积分，奖励道具ID（-1则没有)，奖励道具数量
//	CHARGE_SINGLE(LogType.ACTIVITY, "充值单次活动"), // 奖励元宝，奖励银两，奖励积分，奖励道具ID（-1则没有)，奖励道具数量
//
//	OB_NEW_CHARACTER(LogType.ACTIVITY, "新玩家注册"), // 奖励道具ID，奖励道具数量
//
//	SERIAL_DAY_ONLINE(LogType.ACTIVITY, "多日在线时间累积活动"), // 奖励银两，奖励道具ID，奖励道具数量
//	SINGLE_DAY_ONLINE(LogType.ACTIVITY, "多日在线时间累积活动"), // 奖励银两，奖励道具ID，奖励道具数量
//	LOGIN_SERIAL_DAYS(LogType.ACTIVITY, "连续登陆天数"), // 奖励银两，奖励道具ID，奖励道具数量
//	GOLD_MALL_EXTRA_NUM(LogType.ACTIVITY, "元宝买赠活动"), // 奖励道具ID， 奖励道具数量
//
//	OB_LEVEL_UP(LogType.ACTIVITY, "冲级活动"), // 第几名冲级到活动奖励，通用活动奖励
//	OB_EXTRA_SCORE(LogType.ACTIVITY, "元宝商城积分活动"), // 额外积分
//	OB_XIANMO(LogType.ACTIVITY, "仙魔大战活动"), // 第几组，是否胜利，通用活动奖励
//	OB_DOUFA(LogType.ACTIVITY, "斗法大会活动"), // 第几组，仙级别，排名，通用活动奖励
//
//	MOUNT_RACE(LogType.ACTIVITY, "坐骑竞速"), // 参加玩家Id
//	SEND_DAILY_ITEM(LogType.ACTIVITY, "赠送每日物品"), // id号，数目
//	ARENA_INTEGRAL(LogType.ACTIVITY, "竞技场"), // 增加的分数
//	RECEIVE_SMTU(LogType.ACTIVITY, "领取神秘图志"), // 物品id号
//	GET_CBTCY(LogType.ACTIVITY, "获得藏宝图残页"), // 物品id号
//	HAND_CBTCY(LogType.ACTIVITY, "提交藏宝图残页"), // 第几次，经验
//	GET_TREASUREMAP(LogType.ACTIVITY, "获得藏宝图"), // 物品id号
//	OPEN_TREASUREMAP(LogType.ACTIVITY, "开启藏宝图"), // 开启藏宝图的结果
//	ENTER_SLCJG(LogType.ACTIVITY, "进入少林藏经阁"), // 进入玩家
//	ENTER_LHYD(LogType.ACTIVITY, "进入琅环玉洞"), // 进入次数
//	ENTER_ZHLMJ(LogType.ACTIVITY, "进入珍珑迷局"), // 进入次数
//
//	QUEST_CANCEL(LogType.QUEST, "放弃任务"), // 任务ID
//	QUEST_ACCEPT(LogType.QUEST, "接收任务"), // 任务ID
//	QUEST_COMMIT(LogType.QUEST, "完成任务"), // 任务ID
//	QUEST_CMIT_EQUIP(LogType.QUEST, "提交装备"), // 任务ID
//	QUEST_SELECT_AWARD(LogType.QUEST, "选择奖励"), // 任务ID
//
//	PROP_TOUCH_GATHER(LogType.PROP, "采集"), PROP_PUTINTO_WAREHOUSE(LogType.PROP,
//			"放入仓库"), PROP_PUTINTO_BAG(LogType.PROP, "放入背包"),
//
//	EQUIP_REPAIR(LogType.EQUIP, "修理装备"),
//
//	GROUPBUY_JOIN(LogType.SHOP, "参加团购"), GROUPBUY_FAILED(LogType.SHOP, "团购失败"), GROUPBUY_SUCCESS(
//			LogType.SHOP, "团购成功"),
//
//	JIMAI_CONSIGN_ITEM(LogType.JIMAI, "寄售物品"), JIMAI_BUY_ITEM(LogType.JIMAI,
//			"寄卖购买"), JIMAI_PUT_OFF(LogType.JIMAI, "寄卖下架"),
//
//	EMAIL_SEND(LogType.MAIL, "发送邮件"), EMAIL_RECEIVE(LogType.MAIL, "接收附件"), EMAIL_DELETE(
//			LogType.MAIL, "删除邮件"),
//
//	SHOP_COMMON(LogType.SHOP, "普通商店购买"), SHOP_ALLY(LogType.SHOP, "宗派商店购买"), SHOP_BUY_INTEGRAL_MALL(
//			LogType.SHOP, "积分商城购买"),
//
//	CASINO_RESULE(LogType.CASINO, "赌场结果"), CASINO_START(LogType.CASINO, "开始游戏"), CASINO_EVERY_DAY(
//			LogType.CASINO, "每天统计"),
//
//	PLAYER_DELETED(LogType.DELETE_PLAYER, "删除人物"), // 删除人物
//
//	GAME_RECORD_COMMIT(LogType.PLAYER, "玩家成就完成"), // 成就ID,成就值，是否完成
//	GAME_RECORD_REWARD(LogType.PLAYER, "玩家成就奖励"), // 经验，金钱，元宝，物品（最多3个）
//
//	DAY_REMIND_ADD(LogType.PLAYER, "每日进度"), // 成就ID,成就值，是否完成
//
//	// 家族日志
//	FAMILY_COPY_REWARD(LogType.FAMILY, "家族副本奖励"), FAMILY_COPY_OPEN(
//			LogType.FAMILY, "家族副本开启"), FAMILY_TRIAL_OPEN(LogType.FAMILY,
//			"家族试炼开启"), // 家族id，家族职位
//	FAMILY_TRIAL_REWARD(LogType.FAMILY, "家族试炼奖励"), // 家族id，经验
//	// 团队副本
//	TEAMCOPY_REWARD(LogType.TEAMCOPY, "团队副本奖励"),
//	// 押镖
//	ESCORT_ACCEPT(LogType.PLAYER, "领取押镖"), // 目标场景，目标NPC,押镖类型 0 普通镖:1 完美镖
//	ESCORT_COMMIT(LogType.PLAYER, "交取押镖"), // 目标NPC,押镖类型 (0 普通镖:1 完美镖),奖励经验，奖励金钱
//	ESCORT_FAIL(LogType.PLAYER, "押镖失败"), // 目标场景，目标NPC,押镖类型 0 普通镖:1 完美镖
//	ESCORT_ROB(LogType.PLAYER, "抢劫押镖"), // 押镖者ID,目标NPC,押镖类型,获得经验,获得金钱
//
//	// <<<<<<<GM
//	GM_REPLACE_PLAYER_POINT(LogType.GM, "GM洗点"), GM_UPDATE_PLAYER_ATTRIBUTE(
//			LogType.GM, "GM修改玩家基本数据"), GM_UPDATE_PLAYER_GAME_MONEY(LogType.GM,
//			"修改玩家游戏币"), GM_REDUCE_PLAYER_CELL_ITEM(LogType.GM, "减少背包仓库物品"), GM_OPERATE_PLAYER_QUEST(
//			LogType.GM, "任务操作 "), GM_UPDATE_PLAYER_SKILL(LogType.GM, "更新玩家技能 "), GM_GIVE_PLAYER_ITEM(
//			LogType.GM, "GM赠送"), GM_ADD_PLAYER_FORBID(LogType.GM, "GM冻结禁言"), GM_DELETE_PLAYER_FORBID(
//			LogType.GM, "GM解冻解言"), GM_CHANGE_PLAYER_SCENE(LogType.GM,
//			"GM切换玩家场景"), GM_UPDATE_ALLY_BASIC_DATA(LogType.GM, "GM修改宗派基本数据"),
//
//	BATTLE(LogType.BATTLE, "战斗"),
//	// create table battle (time datetime,skyid bigint,clientType tinyint,
//	// roleid int, scene varchar(32), bkey int, type int, l1 int, l2 int, l3
//	// int, l4 int, a1 int, a2 int, a3 int, a4 int, l1l int, l2l int, l3l int,
//	// l4l int, a1l int, a2l int, a3l int, a4l int);
//	DAYANTA_ENTER(LogType.ACTIVITY, "进入大雁塔"),
//	// create table dayanta_enter (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32));
//	DAYANTA_LEAVE(LogType.ACTIVITY, "离开大雁塔"),
//	// create table dayanta_leave (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), floor int, addexp int);
//	BAIHUTANG_ENTER(LogType.ACTIVITY, "进入白虎堂"),
//	// create table baihutang_enter (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32));
//	BAIHUTANG_LEAVE(LogType.ACTIVITY, "离开白虎堂"),
//	// create table baihutang_leave (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), floor int, addexp int);
//	BIWU_ENTER(LogType.ACTIVITY, "进入比武大会"),
//	// create table biwu_enter (time datetime,skyid bigint,clientType tinyint,
//	// roleid int, scene varchar(32), bkey int);
//	BIWU_QUALIFICATION(LogType.ACTIVITY, "进入比武资格"),
//	// create table biwu_qualification (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), rank int, money int);
//	GRASS_GATHER(LogType.ACTIVITY, "采集仙草"), // 得到物品ID， 物品数目
//	// create table grass_gather (time datetime,skyid bigint,clientType tinyint,
//	// roleid int, scene varchar(32), npcid varchar(32), propid int, propnum
//	// int);
//	TOWN_COMPETE_QUALI_ENTER(LogType.ACTIVITY, "采集仙草"),
//	// create table town_compete_quali_enter (time datetime,skyid
//	// bigint,clientType tinyint, roleid int, scene varchar(32), allyid int, p1
//	// int, p2 int, p3 int, p4 int);
//	TOWN_COMPETE_QUALI_LOST(LogType.ACTIVITY, "帮派失去资格"),
//	// create table town_compete_quali_lost (time datetime,skyid
//	// bigint,clientType tinyint, roleid int, scene varchar(32), allyid int,
//	// competescene varchar(32));
//	TOWN_COMPETE_QUALI_WIN_ATLAST(LogType.ACTIVITY, "帮派获得最终资格"),
//	// create table town_compete_quali_win_atlast (time datetime,skyid
//	// bigint,clientType tinyint, roleid int, scene varchar(32), allyid int,
//	// competescene varchar(32));
//	TOWN_COMPETE_OCCURY(LogType.ACTIVITY, "帮派获得主城"),
//	// create table town_compete_occury (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), allyid int, competescene
//	// varchar(32));
//	TOWN_COMPETE_LOST(LogType.ACTIVITY, "主城争夺失败"),
//	// create table town_compete_lost (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), ownerallyid int, attackallyid
//	// int, competescene varchar(32));
//	TOWN_COMPETE_WIN(LogType.ACTIVITY, "主城争夺成功"),
//	// create table town_compete_win (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), ownerallyid int, attackallyid
//	// int, competescene varchar(32));
//	TOWN_COMPETE_ENTER(LogType.ACTIVITY, "主城争夺战报名"),
//	// create table town_compete_enter (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32));
//	LEVEL_UP_SEND_GOLD(LogType.ACTIVITY, "升级到20级的元宝"),
//	// create table level_up_send_gold (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), addgold int);
//	DROP_ITEM(LogType.PLAYER, "丢弃物品"), // 物品常用数据
//	// create table level_up_send_gold (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), item1_num int, item1_type int,
//	// item1_id int, item1_level int, item1_color int, item1 varchar(128));
//	KILLED_DROP_EXP_MONEY(LogType.PLAYER, "被杀掉落"),
//	// create table killed_drop_exp_money (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), battlekey int, pktype int,
//	// useitem int, dropexp int, dropmoney int);
//	SONGLIAO_REWARD(LogType.ACTIVITY, "宋辽大战奖励"),
//	// create table songliao_reward (time datetime,skyid bigint,clientType
//	// tinyint, roleid int, scene varchar(32), iswin int, rank int, exp int,
	// money int);
	/** 记录标记(0,进入，1赢，2输，3，离开） 银两，元宝 */
//	MATCH_RECORD(LogType.ACTIVITY, "竞技场记录"),
	// create table match_record (time datetime,skyid bigint,clientType tinyint,
	// roleid int, scene varchar(32), level int, flag tinyint, money int, gold
	// int);

	//;

	private String msg;
	private LogType type;

	/**
	 * 事件
	 * 
	 * @param eventName
	 *            事件名
	 * @param dataDescs
	 *            事件数据的描述，对应
	 */
	private LogEvent(LogType type, String eventName) {
		this.msg = eventName;
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public LogType getLogType() {
		return type;
	}
}
