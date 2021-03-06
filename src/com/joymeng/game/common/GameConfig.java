package com.joymeng.game.common;

import hirondelle.date4j.DateTime;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.joymeng.core.utils.PropertyManager;
import com.joymeng.core.utils.TimeUtils;

/**
 * 游戏配置信息
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class GameConfig {
	public static final int MAX_LEVEL = 30;// 最高等级
	public static final byte GAME_MONEY = 0;// 金币
	public static final byte GAME_WARS = 4;// 战斗次数
	public static final byte GAME_EXP = 5;// 经验
	public static final byte JOY_MONEY = 7;// 游戏币
	public static final byte AWARD = 8;// 功勋
	public static final byte MEDALS = 9;// 军功
	public static final byte ARCHEREQU = 10;// 军功
	public static final byte INFANTRYEQU = 11;// 军功
	public static final byte CAVALRYEQU = 12;// 军功
	public static final byte SPECIALARMS = 13;// 军功
	public static final byte ACHIVE = 14;// 政绩
	public static final byte CHIP = 15;// 筹码
	public static final int SCORE = 16;//积分
	public static final int LEVEL = 17;//等级
	public static final int TITLE = 18;//官职
	public static final int KNIGHTHOOD = 19;//爵位
	public static final int GAME_TIMBER_ID = 551;// 木材
	public static final int GAME_IRONORE_ID = 552;// 铁矿
	public static final int GAME_HORSES_ID = 553;// 马匹

	public static long arenaUpdate;// 竞技场发送奖励的时间
	public static int ladderMoney;// 通天塔的消耗
	public static int heroSkill5;
	public static int heroSkill6;
	public static int ladderFresh;
	public static int arenaCD;
	public static int openVip1 = 0;
	public static int openVip2 = 0;
	public static int openVip3 = 0;
	public static int refreshBlackSmithy = 0;
	public static int motifyName = 0;
	public static int goldMine = 0;
	// public static String couchbase="";
	// 出生时候的游戏币，金币，荣誉
	public static int bornGameMoney = 0;
	public static int bornJoyMoney = 0;
	public static int bornAward = 0;
	public static int serverList=0;
	public static int haltTime=0;
	public static int heartTime=0;
	static PropertyManager pm = null;
	public static final String FILENAME = "conf/game.properties";

	public static void load() throws FileNotFoundException, IOException {
		pm = new PropertyManager(FILENAME);
		String str = pm.getString("arenaUpdate");
		if (str == null || str.equals("")) {
			arenaUpdate = TimeUtils.nowLong();
			DateTime sDate = TimeUtils.getTime("2009-12-31 00:00:00");
			pm.set("arenaUpdate", sDate.toString());
			pm.save(FILENAME);
		} else {
			DateTime aDate = TimeUtils.getTime(str);
			arenaUpdate = aDate.getMilliseconds(TimeUtils.tz);
		}
		ladderMoney = Integer.parseInt(pm.getString("LadderMoney"));
		heroSkill5 = Integer.parseInt(pm.getString("HeroSkill5"));
		heroSkill6 = Integer.parseInt(pm.getString("HeroSkill6"));
		ladderFresh = Integer.parseInt(pm.getString("LadderFresh"));
		arenaCD = Integer.parseInt(pm.getString("ArenaCD"));

		openVip1 = Integer.parseInt(pm.getString("openVip1"));
		openVip2 = Integer.parseInt(pm.getString("openVip2"));
		openVip3 = Integer.parseInt(pm.getString("openVip3"));
		refreshBlackSmithy = Integer.parseInt(pm
				.getString("refreshBlackSmithy"));
		motifyName = Integer.parseInt(pm.getString("motifyName"));
		goldMine = Integer.parseInt(pm.getString("goldMine"));
		bornGameMoney = Integer.parseInt(pm.getString("bornGameMoney"));
		bornJoyMoney = Integer.parseInt(pm.getString("bornJoyMoney"));
		bornAward = Integer.parseInt(pm.getString("bornAward"));
		serverList= Integer.parseInt(pm.getString("ServerList"));
		haltTime=Integer.parseInt(pm.getString("HALTTIME"));
		heartTime=Integer.parseInt(pm.getString("HEARTTIME"));
		// couchbase=pm.getString("couchbase");
	}

	public static PropertyManager getPm() {
		return pm;
	}

	public static void setPm(PropertyManager pm) {
		GameConfig.pm = pm;
	}

}
