package com.joymeng.game;

/**
 * 游戏协议
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class ProcotolType {
	// 这里登录协议的消息码是必须的，千万不要改
	// //客户端大厅登录
	// public static final int INST_GAME_SVR = 0x0200;
	// public static final int INST_LOGIN_SVR = 0xFFFE;//0xFFFE;
	
	 public static final int LOGIN_REQ = 0x2;
	 public static final int LOGIN_RESP = 0x3;
	 public static final int REG_REQ = 0x34;
	 public static final int REG_RESP = 0x35;
	// //客户端大厅心跳
	public static final int HEART_BEAT_REQ = 0x0;
	public static final int HEART_BEAT_RESP = 0x1;
	//登陆，注册，获得游戏列表
		public static final int GAME_LIST_REQ=0xFF02;
		public static final int GAME_LIST_RESP=0xFF03;
	// //联通性测试
	// public static final int ECHO_REQ = 0x0A;
	// public static final int ECHO_RESP = 0x0B;
	// //服务器间通讯
	// public static final int SERVER_INNER_REQ=0xFF01;
	// public static final int SERVER_INNER_RESP=0xFF02;
	//通用的模块消息
	public static final int COMMON_RESP=1111;//
	//	------------------这里的消息码是具体每个游戏定义的，可以自行修改----------------------------------------------------
	// 进入游戏
	public static final int ENTER_GAME_REQ = 0x00010001;
	public static final int ENTER_GAME_RESP = 0x00010002;
	// 聊天
	public static final int CHAT_REQ = 0x00010003;
	public static final int CHAT_RESP = 0x00010004;
//	-------------------玩家信息---------------------------------------------------
	public static final int USER_INFO_REQ = 0x00020003;
	public static final int USER_INFO_RESP = 0x00020004;
	public static final byte USER_MOTIFY_NAME = 1;//改名
	public static final byte USER_RANDOM = 2;//随机用户
	public static final byte USER_ENTER = 3;//进入
	public static final byte USER_HERO = 4;//用户英雄
	public static final byte OFFER_ZHUCHENG = 5;//占领主城
	public static final byte OFFER_CHEFANG = 6;//撤出主城
	public static final byte LADDER_RESET=7;//重置天梯
	public static final byte USER_TYPE = 8;//玩家类型
	public static final byte USER_COMMIT = 9;//玩家信息提交
	public static final byte ARENA_MESSAGE=10;//竞技场消息
	public static final byte UP_PROMOTED=11;//爵位升级
	public static final byte COUNTRY_NATION=12;//选择国家
	public static final byte PLAYER_ACHER = 13;//政绩
	public static final byte PLAYER_USERINFO = 14;//用户数据
	public static final byte PLAYER_HALT=15;//用户挂起
//	-------------------建筑0x0003---------------------------------------------
	public static final int BUILDING_REQ = 0x00030001;
	public static final int BUILDING_RESP = 0x00030002;
	public static final byte BUILDING_GET_DEFAULT =1;//获得初始建筑
	public static final byte BUILDING_ITEM = 2; //建筑列表
	public static final byte BUILDING_DEL = 3; //拆除建筑
	public static final byte BUILDING_ADD = 4; //添加建筑
	public static final byte BUILDING_CHANGE_STATUS = 5;//修改建筑状态
	public static final byte BUILDING_LEVELUP = 6;//建筑升级
	public static final byte BUILDING_GUARD = 7;//驻防
	public static final byte BUILDING_DISARM = 8;//撤防
	public static final byte BUILDING_RECOVER = 9;//收复
	public static final byte BUILDING_CHARGEOUT = 10;//收取
	public static final byte BUILDING_STEAL = 11;//偷取被人建筑
	public static final byte BUILDING_SOLDIER = 12;//训练士兵
	public static final byte BUILDING_REFUSE_SMITHY = 13;//刷新铁匠铺
	public static final byte BUILDING_REFUSE_SOLDIER = 14;//刷新兵营
	public static final byte BUILDING_TRAININGOVER = 15;//训练结束
	public static final byte BUILDING_TRAINING_ADDTIME = 16;//加速
	public static final byte BUILDING_AFFILIATED = 17;//获取随机用户
	public static final byte BUILDING_TRAINING_VIP = 18;//获取随机用户
	public static final byte BUILDING_REFUSE_SOLDIEREQU = 19;//兵装数量
	public static final byte BUILDING_LUBAN = 20;//鲁班锤
	public static final byte CHARGE_DETAIL = 21;//查看详细数据
	public static final byte SOLDIER_UPGRADE = 22;//士兵升级
	public static final byte SOLDIER_UN_LOCK = 23;//士兵解锁
//	-------------------将领0x0004------------------------------------------------
	public static final int HERO_REQ = 0x00040001;
	public static final int HERO_RESP = 0x00040002;
	public static final byte HERO_REFRESH=0;//刷新将领,
	public static final byte HERO_GET=1;//获得将领,
	public static final byte HERO_DEL=2;//删除将领,
	public static final byte HERO_EQUIP=3;//装备
	public static final byte HERO_UNEQUIP=4;//卸下装备
	public static final byte HERO_ADDSKILL=5;//学习技能
	public static final byte HERO_DELSKILL=6;//删除技能
	public static final byte HERO_SOLDIER=7;//带兵数
	public static final byte HERO_LIST=8;//将领列表,
	public static final byte HERO_LEVELUP=9;//将领升级
	public static final byte HERO_TRAINSTART=10;//开始训练
	public static final byte HERO_TRAINEND=11;//结束训练
	public static final byte HERO_SPEEDUP=12;//加速训练
	public static final byte HERO_EXPANDSKILL=13;//扩展技能
//	-------------------战斗0x0006------------------------------------------------
	public static final int FIGHT_REQ = 0x00060001;
	public static final int FIGHT_RESP = 0x00060002;
//	-------------------道具0x0008------------------------------------------------
	public static final int ITEMS_REQ = 0x00080001;
	public static final int ITEMS_RESP = 0x00080002;
	public static final byte ITEM_GET =1;//获得所有背包物品
	public static final byte ITEM_GET_EQUIMENT =2;//获得所有装备物品
	public static final byte ITEM_GET_PROPS =3;//道具物品
	public static final byte ITEM_ADD_EQUIMENT =4;//添加装备物品
	public static final byte ITEM_ADD_PROPS =5;//添加道具物品
	public static final byte ITEM_USER_PROPS=6;//使用道具
	public static final byte ITEM_USER_EQUIMENT=7;//使用装备
	public static final byte ITEM_USER_TKEQUIMENT = 8;//
	public static final byte ITEM_USER_UPGRADE=10;//强化
	public static final byte ITEM_USER_DISMANT=11;//拆解
	public static final byte ITEM_OFF_EQUIMENT = 12;//脱装备
	public static final byte CUTOVER_EQUIMENT = 13;//切换装备
	public static final byte ITEM_SPY_ON=14;//侦查
	public static final byte ITEM_USE_PROPS=15;//使用道具
	public static final byte ITEM_DEL_DELAY=16;//删除延时道具
	public static final byte EXCHANGE_STAR = 17;//兑换名将
//	-------------------消息0x0009------------------------------------------------
	public static final int MESSAGE_REQ = 0x00090001;
	public static final int MESSAGE_RESP = 0x00090002;
// 	-------------------世界地图0x0010---------------------------------------------
	public static final int NATION_REQ = 0x00100001;
	public static final int NATION_RESP = 0x00100002;
	public static final int NATION_RESP_TEST = 0x00100003;
	public static final byte NATION_ALL_STATE = 1;//所有州
	public static final byte NATION_ALL_CITY = 2;//州下所有city
	public static final byte NATION_MOTIFY_NAME = 3;//修改名字
	public static final byte NATION_MIND_RES = 4;//所有资源
	public static final byte NATION_OCC_GOLD = 5;//占领金矿
	public static final byte NATION_RBT_GOLD = 6;//金矿撤退
	public static final byte ADD_SOLDIER = 7;//矿脉增加士兵
	public static final byte NATION_FIGHT = 8;//争夺战
	public static final byte NATION_WAR_DATA = 9;//结束
	public static final byte NATION_START = 10;//开始
	public static final byte TOP_WAR = 11;//积分排行榜
	public static final byte NATION_ADD_SOLIDER = 12;//增兵
	public static final byte NATION_POWER = 13;//战斗力
	public static final byte WAR_SING = 14;//争夺战报名
	public static final byte WAR_QUIT = 15;//争夺战退出
	
	public static final byte RESOURCES_SING = 16;//资源报名
	public static final byte RESOURCES_QUIT = 17;//资源退出
	
	public static final byte KEEP_UNDER = 18;//换将
// 	-------------------shop 0x0013------------------------------------------------
	public static final int SHOP_REQ = 0x00130001;
	public static final int SHOP_RESP = 0x00130002;
	public static final byte SHOP_ALL_GOODS = 1;//所有物品
	public static final byte SHOP_GOOD_BUY = 2;//购买物品
	public static final byte SHOP_LADDER=3;//购买天梯付费
//	-------------------技能 0x0012------------------------------------------------
	public static final int SKILL_REQ = 0x00120003;
	public static final int SKILL_RESP = 0x00120004;
//	-------------------好友 0x0014------------------------------------------------
	public static final int FRIEND_REQ = 0x00300001;
	public static final int FRIEND_RESP = 0x00300002;
	public static final byte FRIEND_ALL = 0;//全部好友
	public static final byte ENEMY_ALL = 1;//全部敌人
	public static final byte CITY_PLAYER = 2;//本市在线玩家
	public static final byte ADD_FRIEND = 3;//添加好友
	public static final byte DEL_FRIEND = 4;//删除好友
	public static final byte ONLINE_ALL = 5;//全部好友
	public static final byte RECENTLY_ALL = 6;//全部好友
// 	-------------------消息0x0015------------------------------------------------
	public static final int SYSTEM_REQ = 0x00150001;
	public static final int SYSTEM_RESP = 0x00150002;
	//----------------------竞技场0x0016------------------------------------------------------
	public static final int ARENA_REQ = 0x00160001;
	public static final int ARENA_RESP = 0x00160002;

	//---------------------测试0x0017------------------------------------------------------
	public static final int TEST_REQ = 0x00170001;
	public static final int TEST_RESP = 0x00170002;

	//---------------------测试0x0018------------------------------------------------------
	public static final int MISSION_REQ = 0x00180001;
	public static final int MISSION_RESP = 0x00180002;
	//---------------------------宝箱0x0021-------------------------------------------------
	public static final int BOX_REQ = 0x002100001;
	public static final int BOX_RESP = 0x002100002;
	
	//--------------------------排行榜0x0022
	public static final int RANK_REQ = 0x00220001;
	public static final int RANK_RESP = 0x00220002;
	public static final byte GAME_MONEY_RANK = 1;//金币排行榜
	public static final byte GAME_JOY_MONEY_RANK = 2;//钻石
	public static final byte LADDER_RANK = 3;//通天塔
	public static final byte GENERAL_ATTACK_RANK = 4;//武将攻击力
	public static final byte GENERAL_DEFENSE_RANK = 5;//武将防御力
	public static final byte GENERAL_HP_RANK = 6;//武将生命值
	public static final byte ARENA_RANK = 7;//竞技场排名
	
	//-----------------------战报0x0025
	public static final int BATTLE_FIELD_REQ = 0x00250001;
	public static final int BATTLE_FIELD_RESP = 0x00250002;
	public static final byte SYS_AWARD = 0;//系统奖励
	public static final byte PERSONAL_FIGHT_EVENT = 1;//个人战报信息
	public static final byte SEE_FIGHT_EVENT = 7;//观看战斗
	public static final byte STAGE_FIGHT_EVENT = 8;//阶段战报
	public static final byte SYS_BATTLE_INFO = 2;//系统战斗是否可进入
	public static final byte GET_SYS_AWARD = 9;//领取系统奖励
	public static final byte IS_NEED_GET_SYS_AWARD = 10;//是否需要领奖
	public static final byte AWARD_INFO = 11;//奖品信息
	
	//----------------------道具宝箱0x0026
	public static final int PROPS_BOX_REQ = 0x00260001;
	public static final int PROPS_BOX_RESP = 0x00260002;
	public static final int GET_PROPS = 1;//打开宝箱获取道具或物品
	public static final int OPEN_PACKAGE = 2;//打开包裹获取包裹中的装备
	public static final int GET_HERO = 3;//获取武将
	public static final int CONFIRM_GET_HERO = 4;//确定获取武将
	public static final int CANCLE_GET_HERO = 5;//取消获取武将
	public static final int WOOD_ORE_HORSE = 6;//获取木材，矿石，马匹
	public static final int DIAMOND_CHIP = 7;//钻石，筹码
	public static final int OPEN_NEW_BAG = 8;//打开新手大礼包
	public static final int IS_NEED_NEW_BAG = 9;//是否需要出现新手大礼包
	
	//----------------------行政城战争0x0028
	public static final int REGION_BATTLE_REQ = 0x00280001;
	public static final int REGION_BATTLE_RESP = 0x00280002;
	public static final int ENTER_COUNTY_BATTLE_ACTION = 1;//进入县长争夺战
	public static final int PAGE_FOR_COUNTY_BATTLE_LAST = 2;//向上翻页获取当前玩家所属市平行市
	public static final int PAGE_FOR_COUNTY_BATTLE_NEXT = 3;//向下翻页获取当前玩家所属市平行市
	public static final int SELECT_COUNTRY = 4;//选择国家展现国家下的所有州
	public static final int ENTER_MAP = 5;//进入世界地图
	public static final int ENTER_CERTAIN_STATE = 6;//进入特定州
	public static final int BACK_TO_BATTLE_REGION = 7;//返回战斗区域
	public static final int DISARM = 8;//撤防
	public static final int VIEW_WAR = 9;//查看争夺战
	//----------------------退出游戏0x0034
	public static final int EXITGAME_REQ = 0x00340001;
	public static final int EXITGAME_RESP = 0x00340002;
	//----------------------服务器间通讯FF01
	public static final int SERVER_INNER_REQ=0xFF01;
	public static final int SERVER_INNER_RESP=0xFF02;
	
	//------------------------翻牌
	public static final int CARD_REQ = 0x00320001;
	public static final int CARD_RESP = 0x00320002;
	public static final int ENTER = 0;//进入界面
	public static final int GET_CHANCE = 1;//倒计时到，获取翻牌机会增加
	public static final int ROTATE_CARD_FACE = 2;//旋转牌的背面
	public static final int FLIP_CARDS = 3;//翻牌
	public static final int GET_AWARD = 4;//领奖
	public static final int OUT_SHOW = 5;//翻牌界面外显示
	public static final int OUT_GET_CHANCE = 6;//外面倒计时获取机会
	
	//----------------------充值
	public static final int RECHARGE_REQ = 0x00360001;
	public static final int RECHARGE_RESP = 0x00360002;
	public static final byte DO_RECHARGE = 1;
	public static final byte GET_RECHARGE_AWARD = 2;
	
	//---------------夺旗
	public static final int FLAG_REQ = 0x00380001;
	public static final int FLAG_RESP = 0x00380002;
	public static final byte SIGN_UP = 1;//报名
	public static final byte SIGN_QUIT = 2;//退出
	public static final byte START_GAME_FIGHT = 3;//开始游戏
	public static final byte QUIT_ROOM = 4;//退出
	public static final byte CAPTURE_FLAG = 5;//夺旗
	public static final byte REFRESH_MOBILITY = 6;//刷新积分
	public static final byte MOVE_POINT = 7;//可移动点
	public static final byte HERO_BUFF = 8;//将领buff
	
	//--------------------联系客服
	public static final int USER_QUESTION_REQ = 0x00400001;
	public static final int USER_QUESTION_RESP = 0x00400002;
	public static final int POST_QUESTION = 1;//玩家提问
	
	//--------------------玩家签到
	public static final int SIGN_REQ = 0x00420001;
	public static final int SIGN_RESP = 0x00420002;
	public static final byte OPEN_UI = 1;//打开签到界面
	public static final byte SIGN_IN = 2;//签到
}
