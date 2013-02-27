package com.joymeng.game.common;

import java.util.Locale;

/**
 * 游戏中的常量
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class GameConst {
	public final static String PACK_RES_PATH = "";//资源文件列表
	public final static String ZIP_PATH = "";//生成zip文件地址
	public final static String RES_PICTURE_GENRE1 = ".png";//过滤文件
	public final static String RES_PICTURE_GENRE2 = ".jpg";//过滤文件
	public final static String RES_MUSIC_GENRE3 = "";//过滤文件
	public final static String RES_MAP_GENRE3 = "";//过滤文件
	public final static String RES_XML_GENRE3 = ".xml";//过滤文件
	public final static String ZIP_GENRE = ".zip";//生成文件格式
	
	public final static int BEGIN_FIGHT = 5;//开始县长战斗
	
	
	public final static byte GAME_RESP_SUCCESS = 1;// 反馈状态成功
	public final static byte GAME_RESP_FAIL = 0;// 反馈状态失败

	/**
	 * 资源文件路径，默认是程序相同目录下的res文件夹，可通过程序参数改变
	 */
	public final static String RES_PATH_CH = "./resource2/ch";
	
	public final static String RES_PATH_EN = "./resource2/en";
	
	/**
	 * 最大等级
	 */
	public final static short MAX_LEVEL = 90;
	/**
	 * 初始士兵数量
	 */
	public final static int INITIAL_SOLIDER = 2000;

	/**
	 * 实体类包路径
	 */
//	public final static String DOMAIN_PACKAGE = "com.joymeng.game.domain.";
	
	/**
	 * 用户，建筑 初始等级
	 */
	public final static Short DEFAULT_LEVEL = 1;
	
	public final static int MAINCITY_ID = 1000;//主城ID
	public final static int FUSHU_CITY_ID = 1023;//附属成
	public final static int JIUGUAN_ID = 1007;//酒馆
	public final static int BARRACK_ID=1009;//兵营
	public final static int TRAINING_ID=1014;//训练场
	public final static int BLACKSMITHY_ID = 1008;//铁匠铺
	public final static int BLACKSMITHY_PROPS_ID = 0;//刷新铁匠铺道具id
	
	/**
	 * 资源建筑类型
	 */
	public final static int RESOURCES_TYPE = 2;
	
	/**
	 * 默认数值
	 */
	public final static int INT_DEFAULT_VALUE = 0;
	/**
	 * 默认数值
	 */
	public final static String STRING_DEFAULT_VALUE = "";
	
	/**
	 * 科技最高驻防玩家
	 */
	public final static int MAX_VEINS_COUNT  = 500000;
	
	   //玩家状态，标识在线或离线
	public static final byte STATE_OFFLINE = 0;
    public static final byte STATE_ONLINE = 1;
    
    
    public static float getAdditon(short level){
    	if(level>= 1 && level <=5){
    		return (float) 0.45;
    	}else if(level>= 6 && level <=10){
    		return (float) 0.45;
    	}else if(level>= 11 && level <=15){
    		return (float) 0.45;
    	}else if(level>= 16 && level <=20){
    		return (float) 0.45;
    	}else if(level>= 21 && level <=25){
    		return (float) 0.45;
    	}else if(level>= 26 && level <=30){
    		return (float) 0.45;
    	}else{
    		return (float) 0.45;
    	}
    	
    }
    //训练时间
    public static final int TRAINTIME_EXP=4*3600;
    public static final int TRAINTIME_SKILL=2*3600;
    //将领状态
    public static final byte HEROSTATUS_IDEL=0;//空闲
    public static final byte HEROSTATUS_TRAIN=1;//训练
    public static final byte HEROSTATUS_ZHUFANG=2;//驻防(附属成)
    public static final byte HEROSTATUS_ARENA=3;//竞技场
    public static final byte HEROSTATUS_ZHUFANG_VEINS=4;//矿脉
    public static final byte HEROSTATUS_ZHUFANG_GOLDMINE=5;//金矿
    public static final byte HEROSTATUS_ZHUFANG_CAMPE=6;//军营

    public static final byte HEROSTATUS_ZHUFANG_COUNTY = 7;//县长驻防
    public static final byte HEROSTATUS_ZHUFANG_NATION_CITY=8;//市
    public static final byte HEROSTATUS_ZHUFANG_NATION_STATE=9;//州
    public static final byte HEROSTATUS_ZHUFANG_NATION_COUNTRY=10;//国家
    public static final byte HEROSTATUS_ZHUFANG_MY_MAINCITY=11;//自己的主城

 // 将领品级
    public static final int HERO_TYPE = 6;
//    public static final int HERO_LEVEL=31;
    //技能上限
    public static final int SKILL_MAXLEVEL=10;
    //通天塔  免费付费次数
    public static final int LADDER_FREENUM=2;
    public static final int LADDER_CHARGENUM=1;
    
  //官员级别
  	public static final byte TITLE_CIVILIAN = 0;//平民
  	public static final byte TITLE_OFFICIALS_TOWN = 1;//县级官员
  	public static final byte TITLE_MAYOR_TOWN = 2;//县长
  	public static final byte TITLE_OFFICIALS_CITY = 3;//市级官员
  	public static final byte TITLE_MAYOR_CITY = 4;//市长
  	public static final byte TITLE_OFFICIALS_STATE = 5;//州级官员
  	public static final byte TITLE_GOVERNOR = 6;//州长
  	public static final byte TITLE_OFFICIALS_COUNTRY = 7;//国级官员
  	public static final byte TITLE_KING = 8;//国王
  	
	public static final int MAX_STORAGE = 1000;//装备最大格子数
	public static final int MAX_WAR_TIMES = 100;//初始战斗次数
	public static final long TIME_SECOND = 1000;
	public static final long TIME_MINUTE = 60 * 1000;
	public static final long TIME_HOUR = 60 * 60 * 1000;
	public static final long TIME_DAY = 24 * 60 * 60 * 1000;
	
	public static final byte COUNTRY_ID1 = 1;//国家1
	public static final String COUNTRY_NAME1 = "国家1";
	public static final byte COUNTRY_ID2 = 2;//国家2
	public static final String COUNTRY_NAME2 = "国家2";
	public static final byte COUNTRY_ID3 = 3;//国家3
	public static final String COUNTRY_NAME3 = "国家3";
	
	public static final String CITY_START = "";
	public static final String CITY_END = "";
	public static final String STATE_START = "";
	public static final String STATE_END = "";
	public static final String COUNTRY_START = "";
	public static final String COUNTRY_END = "";
	
	public static final Locale CHINA = Locale.CHINA;//国际化中文
	public static final Locale US = Locale.US;//国际化英文
	public static final String I18N_FILEPATH = "/conf/fmt_resource.xml";
	
	public static final int  STRONGHOLD_BEGIN = 5;
	public static final int  STRONGHOLD_END = 30;
	
	public static final int  CAMP_BEGIN = 10;
	public static final int  CAMP_END = 30;
	
	//钻石使用途径 
	public static final int DIAMOND_BUYPROP=0;//购买商品
	public static final int DIAMOND_CHANGENAME=1;//君主改名
	public static final int DIAMOND_ARENA=2;//竞技场清冷却时间
	public static final int DIAMOND_CHIP=3;//购买筹码
	public static final int DIAMOND_SMITHY=4;//铁匠铺刷新
	public static final int DIAMOND_TRAIN=5;//训练台开训练位
	public static final int DIAMOND_LADDERFIGHT=6;//通天塔自动战斗
	public static final int DIAMOND_LADDERRESET=7;//通天塔购买重置天梯
}
