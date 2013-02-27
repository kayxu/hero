package com.joymeng.game.domain.quest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.mod.FightInfo;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.soldier.Soldier;
import com.joymeng.game.domain.soldier.SoldierManager;

public class QuestUtils {
	public static final byte TYPE1=1;
	public static final byte TYPE2=2;
	public static final byte TYPE3=3;
	public static final byte TYPE4=4;//战斗打了
	public static final byte TYPE5=5;//战斗打赢
	public static final byte TYPE6=6;
	public static final byte TYPE7=7;
	public static final byte TYPE8=8;
	public static final byte TYPE9=9;
	public static final byte TYPE10=10;
	public static final byte TYPE11=11;
	public static final byte TYPE12=12;
	public static final byte TYPE13=13;
	public static final byte TYPE14=14;
	public static final byte TYPE15=15;
	public static final byte TYPE16=16;
	public static final byte TYPE17=17;
	public static final byte TYPE18=18;
	public static final byte TYPE19=19;
	public static final byte TYPE20=20;
	public static final byte TYPE21=21;
	public static final byte TYPE22=22;
	public static final byte TYPE23=23;
	public static final byte TYPE24=24;
	public static final byte TYPE25=25;
	public static final byte TYPE26=26;
	public static final byte TYPE27=27;
	public static final byte TYPE28=28;
	public static final byte TYPE29=29;
	public static final byte TYPE30=30;
	public static final byte TYPE31=31;
	public static final byte TYPE32=32;
	public static final byte TYPE33=33;
	public static final byte TYPE34=34;
	public static final byte TYPE35=35;
	public static final byte TYPE36=36;
	public static final byte TYPE37=37;
	public static final byte TYPE38=38;
	public static final byte TYPE39=39;
	public static final byte TYPE40=40;
	public static final byte TYPE41=41;
	public static final byte TYPE42=42;
	public static final byte TYPE43=43;
	public static final byte TYPE44=44;
	public static final byte TYPE45=45;
	public static final byte TYPE46=46;
	public static final byte TYPE47=47;
	public static final byte TYPE48=48;
	public static final byte TYPE49=49;
	public static final byte TYPE50=50;
	public static final byte TYPE51=51;
	public static final Logger logger = LoggerFactory
			.getLogger(QuestUtils.class);

	/**
	 * 任务检测
	 * 
	 * @param player
	 * @param id
	 * @param arg
	 *            参数
	 * @param isSend
	 *            是否立即发送
	 */
	public static void checkFinish(PlayerCharacter player, int id, boolean isSend,int ...arg) {
		StringBuilder sb=new StringBuilder();
//		sb.append("check quest finish,uid=").append(player.getId()).append(" questId=").append(id);
		if (player == null) {
			sb.append(" player is null");
			logger.info(sb.toString());
			return ;
		}
		if (player.getPlayerQuestAgent() == null) {
			sb.append(" player quest is null");
			logger.info(sb.toString());
			return ;
		}
		List<AcceptedQuest> list = player.getPlayerQuestAgent().getList(
				(byte) id);
		if (list.size() <= 0) {
//			sb.append(" player quest size=0");
			logger.info(sb.toString());
			return ;
		}
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		switch (id) {
		case TYPE1:
			break;
		case TYPE2:
			finalList = QuestUtils.checkProp(list, arg[0]);
			break;
		case TYPE3:
			finalList =  QuestUtils.checkBuildLevel(list,arg[0],arg[1]);
			break;
		case TYPE4:
			finalList = QuestUtils.checkFightQuest(list, (byte) arg[0], arg[1]);
			break;
		case TYPE5:
			finalList = QuestUtils.checkFightWin(list, (byte) arg[0], arg[1],
					(byte) arg[2]);
			break;
		case TYPE6:
		case TYPE7://战斗
			break;
		case TYPE8:
			finalList = QuestUtils.checkHeroColor(list, arg[0]);
			break;
		case TYPE9:
		case TYPE44:
			FightInfo fi=new FightInfo(player.getData().getFightInfo());
			finalList = QuestUtils.checkFightNum(list, fi);
			//必须要重新保存
			player.getData().setFightInfo(fi.toStr());
			break;
		case TYPE10:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE11:
			finalList = QuestUtils.checkEquipLevelUp(list,arg[0]);
			break;
		case TYPE12:
			finalList = QuestUtils.checkResource(list, arg[0]);
			break;
		case TYPE13:
			finalList = QuestUtils.checkTrainSoldier(list, arg[0]);
			break;
		case TYPE14:
		case TYPE15:
		case TYPE16:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE17:
		case TYPE18://战斗
			break;
		case TYPE19:
			finalList = QuestUtils.checkChat(list, arg[0]);
			break;
		case TYPE20:
		case TYPE21:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE22:
		case TYPE23:
		case TYPE24:
		case TYPE25:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE26:
			finalList = QuestUtils.checkBuyEquip(list, (byte) arg[0]);
			break;
		case TYPE27:
			finalList = QuestUtils.checkPlayerLevel(list,player.getData().getLevel());
			break;
		case TYPE28:
			finalList = QuestUtils.checkCityLevel(list,player.getData().getCityLevel());
			break;
		case TYPE29:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE30:
			finalList = QuestUtils.checkCityNum(list, player.getPlayerBuilgingManager().getFucityNum());
			break;
		case TYPE31:
			finalList =QuestUtils.checkHeroLevel(list,player.getPlayerHeroManager().getHeroArray());
			break;
		case TYPE32:
			finalList =QuestUtils.checkHeroNum(list,player.getPlayerHeroManager().getHeroArray().length);
			break;
		case TYPE33:
			finalList = QuestUtils.checkTotalSoldierNum(list,player.getPlayerBuilgingManager().allSoliderCount());
			break;
		case TYPE34:
			//资源类型
			finalList = QuestUtils.checkStealList(list, (byte) arg[0]);
			break;
		case TYPE35:
			finalList = QuestUtils.checkTotalSoldierEquip(list,player.getPlayerBuilgingManager().allSoliderEqu());
			break;
		case TYPE37:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE38:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE36:
		case TYPE39:
//			finalList =QuestUtils.checkGold(list,player.getPlayerHeroManager().checkHeroStatus(GameConst.HEROSTATUS_ZHUFANG_GOLDMINE));
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE40:
		case TYPE41:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE42:
			finalList =QuestUtils.checkArenaId(list,player.getData().getArenaId());
			break;
		case TYPE43:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		case TYPE45:
			finalList =QuestUtils.checkBoxPoint(list,player.getData().getScore());
			break;
		case TYPE46:
			break;
		case TYPE47:
		case TYPE48:
		case TYPE49:
		case TYPE50:
		case TYPE51:
			finalList=QuestUtils.checkNoCondition(list);
			break;
		}
		if ((list.size() <= 0)) {
			sb.append(" 没有任务需要更新");
			logger.info(sb.toString());
			return ;
		}
		for (AcceptedQuest q : finalList) {
			q.setStatus(PlayerQuestAgent.COMPLETE);
		}
		if (!isSend) {
			sb.append(" 不立即发送");
			return ;
		}
		RespModuleSet rms = new RespModuleSet(ProcotolType.MISSION_RESP);// 模块消息
		for (AcceptedQuest q : list) {
			rms.addModule(q);
			sb.append("\n send to client id=").append("status=").append(q.getStatus()).append("\n");
		}
		AndroidMessageSender.sendMessage(rms, player);
//		player.getPlayerQuestAgent().save();
//		logger.info(sb.toString());
		return ;
	}

	// ********************************************判断原语 ********************************************
	/**
	 * 检测道具使用
	 * 
	 * @param id
	 */
	private static List<AcceptedQuest> checkProp(List<AcceptedQuest> list, int id) {
		return QuestUtils.check1(list, id);
	}

	/**
	 * 检测战斗参与
	 * 
	 * @param type
	 * @param id
	 */
	private static List<AcceptedQuest> checkFightQuest(List<AcceptedQuest> list,
			int... args) {
		 return QuestUtils.check1(list, args);
	}

	/**
	 * 检测战斗获胜
	 * 
	 * @param type
	 * @param id
	 */
	private static List<AcceptedQuest> checkFightWin(List<AcceptedQuest> list,
			int... args) {

		if (args[2] == FightConst.FIGHT_LOSE) {
			return new ArrayList<AcceptedQuest>();
		}
		 return QuestUtils.check1(list, args[0],args[1]);
	}

	/**
	 * 检测将领品质
	 * 
	 * @param color
	 */
	private static List<AcceptedQuest> checkHeroColor(List<AcceptedQuest> list,
			int color) {
		return QuestUtils.check1(list, color);
	}
	/**
	 * 购买装备
	 * 
	 * @param color
	 */
	private static List<AcceptedQuest> checkBuyEquip(List<AcceptedQuest> list,int color) {
		return QuestUtils.check1(list, color);
	}
	/**
	 * 偷取
	 * 
	 * @param type
	 */
	private static List<AcceptedQuest> checkStealList(List<AcceptedQuest> list,int type) {
		return QuestUtils.check1(list, type);
	}
	/**
	 * 检测收取资源
	 * 
	 * @param type
	 */
	private  static List<AcceptedQuest> checkResource(List<AcceptedQuest> list,int type) {
		return QuestUtils.check1(list, type);
	}
	/**
	 * 聊天
	 * 
	 * @param type
	 */
	private static List<AcceptedQuest> checkChat(List<AcceptedQuest> list,int type) {
		return QuestUtils.check1(list, type);
	}
	
	/**
	 * 检测参数是否相等
	 * 
	 * @param args
	 */
	private static boolean checkEqual(AcceptedQuest aq, int... args) {
		Quest q = aq.getQ();
		if (q == null) {
			return false;
		}
		for (int i = 0; i < args.length; i++) {
			switch (i) {
			case 0:
				if(q.getTargetArgs1()==-1){
					return true;
				}
				if (q.getTargetArgs1() != args[0]) {
					return false;
				}
				break;
			case 1:
				if (q.getTargetArgs2() != args[1]) {
					return false;
				}
				break;
			case 2:
				if (q.getTargetArgs3() != args[2]) {
					return false;
				}
				break;
			}
		}
		return true;
	}
	/**
	 * 检测方法1
	 * @param list
	 * @param arg
	 * @return
	 */
	private static List<AcceptedQuest> check1(List<AcceptedQuest> list,int... arg){
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		for (AcceptedQuest aq : list) {
			if (QuestUtils.checkEqual(aq, arg)) {
				finalList.add(aq);
			}
		}
		return finalList;
	}
	/**
	 * 通用的检测方法，判断小于等于
	 * 
	 * @param arg
	 * @param list
	 */
	private static List<AcceptedQuest> check2(List<AcceptedQuest> list,int arg){
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		for (AcceptedQuest aq : list) {
			Quest q =aq.getQ();
			if (q.getTargetArgs1() <= arg) {
				finalList.add(aq);
			}
		}
		return finalList;
	}
	/**
	 * 通用的检测方法，判断大于等于
	 * @param list
	 * @param arg
	 * @return
	 */
	private static  List<AcceptedQuest> check3(List<AcceptedQuest> list,int arg){
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		for (AcceptedQuest aq : list) {
			Quest q = aq.getQ();
			if (q.getTargetArgs1() >= arg) {
				finalList.add(aq);
			}
		}
		return finalList;
	}
	/**
	 * 无条件检测
	 * @param list
	 * @return
	 */
	private static  List<AcceptedQuest> checkNoCondition(List<AcceptedQuest> list){
		return list;
	}
	/**
	 * 检测竞技场排名
	 * 
	 * @param id
	 */
	private static List<AcceptedQuest>  checkArenaId(List<AcceptedQuest> list,int args) {
		return QuestUtils.check3(list,args);
	}

	/**
	 * 检测转盘积分
	 */
	private static List<AcceptedQuest>  checkBoxPoint(List<AcceptedQuest> list,int args) {
		return QuestUtils.check2(list,args);
	}
	/**
	 * 检测士兵数量
	 * 
	 * @param num
	 */
	private static List<AcceptedQuest> checkTotalSoldierNum(List<AcceptedQuest> list,int args) {
		return QuestUtils.check2(list,args);
	}

	/**
	 * 检测兵装数量
	 */
	private static List<AcceptedQuest> checkTotalSoldierEquip(List<AcceptedQuest> list,int args) {
		return QuestUtils.check2(list,args);
	}
	/**
	 * 检测玩家等级
	 * 
	 * @param level
	 */
	private static List<AcceptedQuest>  checkPlayerLevel(List<AcceptedQuest> list,int level) {
		return QuestUtils.check2(list,level);
	}

	/**
	 * 检测城市数量
	 * 
	 * @param level
	 */
	private static List<AcceptedQuest>  checkCityNum(List<AcceptedQuest> list,int num) {
		return QuestUtils.check2(list,num);
	}

	/**
	 * 检测勋章
	 * 
	 * @param level
	 */
	private static List<AcceptedQuest> checkCityLevel(List<AcceptedQuest> list,int level) {
		return QuestUtils.check2(list,level);
	}
	/**
	 * 检测装备升级
	 * 
	 * @param level
	 */
	private static List<AcceptedQuest>  checkEquipLevelUp(List<AcceptedQuest> list,int level) {
		return QuestUtils.check2(list,level);
	}
	/**
	 * 检测将领数量
	 */
	private static List<AcceptedQuest>  checkHeroNum(List<AcceptedQuest> list,int num) {
		return QuestUtils.check2(list,num);
	}
	/**
	 * 检测某战斗类型的战斗次数
	 * 
	 * @param num
	 */
	private static List<AcceptedQuest> checkFightNum(List<AcceptedQuest> list,FightInfo fi) {
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		for (AcceptedQuest aq : list) {
			Quest q =aq.getQ();
			if (q.getTargetArgs2() <= fi.getFightNum((byte)q.getTargetArgs1())) {
				finalList.add(aq);
			}
		}
		return finalList;
	}

	/**
	 * 检测将领级别
	 * 
	 * @param level
	 * @param send
	 */
	private static List<AcceptedQuest>   checkHeroLevel(List<AcceptedQuest> list,PlayerHero[] heros ) {
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		for (PlayerHero hero : heros) {
			for (AcceptedQuest aq : list) {
				Quest q = aq.getQ();
				if (q.getTargetArgs1() <=  hero.getLevel()) {
					if(!finalList.contains(aq)){
						finalList.add(aq);
					}
				}
			}
		}
		return finalList;
	}
	/**
	 * 检测金矿
	 */
	private static List<AcceptedQuest>   checkGold(List<AcceptedQuest> list,boolean b) {
		if(b){
			return list;
		}else{
			return new ArrayList<AcceptedQuest>();
		}
	}

	/**
	 * 检测资源点
	 */
	private static List<AcceptedQuest>   checkVeins(List<AcceptedQuest> list,boolean b) {
		if(b){
			return list;
		}else{
			return new ArrayList<AcceptedQuest>();
		}
	}
	/**
	 * 检测建筑级别
	 * 
	 * @param id
	 * @param level
	 */
	private static List<AcceptedQuest> checkBuildLevel(List<AcceptedQuest> list,int id, int level) {
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		for (AcceptedQuest aq : list) {
			Quest q = aq.getQ();
			if (q.getTargetArgs1() == id && q.getTargetArgs2() <= level) {
				finalList.add(aq);
			}
		}
		return finalList;
	}
	/**
	 * 训练士兵
	 * 
	 * @param id
	 */
	private static List<AcceptedQuest> checkTrainSoldier(List<AcceptedQuest> list,int id) {
		Soldier soldier = SoldierManager.getInstance().getSoldier(id);
		List<AcceptedQuest> finalList = new ArrayList<AcceptedQuest>();
		for (AcceptedQuest aq : list) {
			Quest q =aq.getQ();
			if(q.getTargetArgs1() ==-1){//任意士兵
				finalList.add(aq);
			}else{
				if (q.getTargetArgs1() == soldier.getType()) {
					finalList.add(aq);
				}
			}
		}
		return finalList;
	}

}
