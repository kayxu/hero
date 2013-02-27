package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.fight.FightLog;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightManager;
import com.joymeng.game.domain.fight.battle.FightBattleBase;
import com.joymeng.game.domain.fight.battle.FightBattleFactory;
import com.joymeng.game.domain.nation.GoldMine;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.Veins;
import com.joymeng.game.domain.nation.war.MilitaryCamp;
import com.joymeng.game.domain.nation.war.StrongHold;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FightRequest;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;

//0:竞技场
//1：附属城
//2：战役
//3：通天塔
//4：金矿争夺
//5：金矿脉战斗
//6：资源争夺
//7：资源脉争夺
//8：县长争夺
//9：市长争夺战-攻打据点
//10：市长争夺战-攻打市
//11：州长争夺战-攻打据点
//12：州长争夺战-攻打州
//13：国王争夺战-攻打据点
//14：国王争夺战-攻打国
//15：1V1战场
//16：3V3战场
/**
 * 战斗 //开始战斗
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class FightService extends BaseService {
	static Logger logger = LoggerFactory.getLogger(FightService.class);

	@JoyMessageHandler
	public JoyProtocol handleFight(FightRequest request, ServicesContext context) {
		PlayerCharacter player=super.init(request,this);
		StringBuffer sb = new StringBuffer();
		sb.append("用户：" + player.getId() + "创建战场：" + request.getType() + "\n");
		long startTime = System.nanoTime();// 纳秒
		long endTime = 0;
		long startTime1 = TimeUtils.nowLong();// 毫秒
		long endTime1 = 0;
//		long startTime = System.nanoTime();// 纳秒
//		long endTime = 0;
//		long startTime1 = System.currentTimeMillis();// 毫秒
//		long endTime1 = 0;
		if(request.getType()==FightConst.FIGHTBATTLE_ARENA){
			FightManager.addFight(request);
			return null;
		}
		FightBattleBase fb = FightBattleFactory.create(request, player);// 创建战场

		TipMessage tip = fb.getTip();
		try {
			// sb.append("用户："+player.getId()+"创建战场："+request.getType()+"\n");
			// while(TimeUtils.nowLong()-startTime<5*1000){//超时5秒
			sb.append("战斗开始:" + tip.getMessage() + "\n");
			// 开始战斗
//			if (fb.getType() == FightConst.FIGHTBATTLE_ARENA) {
//				for(int i=0;i<100;i++){
//					FightManager.addFight(request);
//				}
				
//				Arena arena = GameDataManager.arenaManager
//						.getArena((short) request.getId());
//				if (arena != null) {
//					synchronized (arena) {
//						fb.start();
//					}
//				} else {// 撤防的同步交给 FbArena处理
//					fb.start();
//				}
//				logger.info(player.getId()
//						+ "创建竞技场战场结束============================"
//						+ request.toString());
//			} else 
				if (fb.getType() == FightConst.FIGHTBATTLE_GUARD) {
				PlayerCharacter BUser = World.getInstance().getPlayer(
						request.getDefenceUser());//属于用户
				if (BUser != null) {
					//查找用户主城
					PlayerBuilding main = BUser.getPlayerBuilgingManager().getMainCity();
					synchronized (main) {
						fb.start();
					}
				}
				logger.info(player.getId()
						+ "创建驻防战场结束============================"
						+ request.toString());
			} else if (fb.getType() == FightConst.FIGHTBATTLE_RESOURCE2
					|| fb.getType() == FightConst.FIGHTBATTLE_RESOURCE) {
				GoldMine goldMine = NationManager.getInstance().allGoldMap
						.get(request.getId());
				if (goldMine != null) {
					synchronized (goldMine) {
						fb.start();
					}
				}
				logger.info(player.getId()
						+ "创建FBGold战场结束============================"
						+ request.toString());
			}  else if (fb.getType() == FightConst.FIGHTBATTLE_TEC
					|| fb.getType() == FightConst.FIGHTBATTLE_TEC2) {
				Veins veins = NationManager.getInstance().allVeinsMap
						.get(request.getId());
				if (veins != null) {
					synchronized (veins) {
						fb.start();
					}
				}
				logger.info(player.getId()
						+ "创建FBVeins战场结束============================"
						+ request.toString());
			} else if (fb.getType() == FightConst.FIGHTBATTLE_REGION) {
				Nation nation = NationManager.getInstance().nationMap
						.get(request.getId());
				if (nation != null) {
					synchronized (nation) {
						fb.start();
					}
				}
				logger.info(player.getId()
						+ "创建县长争夺战战场结束============================"
						+ request.toString());
			} else if (fb.getType() == FightConst.FIGHTBATTLE_CITY
					|| fb.getType() == FightConst.FIGHTBATTLE_STATE_CAMP
					|| fb.getType() == FightConst.FIGHTBATTLE_COUNTRY_CAMP
					|| fb.getType() == FightConst.FIGHTBATTLE_CITY_CAMP) {
				MilitaryCamp camp = WarManager.getInstance().getMyCamp(
						request.getId());
				if (camp != null) {
					synchronized (camp) {
						fb.start();
					}
				}
				logger.info(player.getId()
						+ "创建  军营争夺战战场结束============================"
						+ request.toString());
			} else if (fb.getType() == FightConst.FIGHTBATTLE_STATE_STRONG
					|| fb.getType() == FightConst.FIGHTBATTLE_COUNTRY_STRONG
					|| fb.getType() == FightConst.FIGHTBATTLE_CITY_STRONG) {
				StrongHold hold = WarManager.getInstance().getMyStrong(
						request.getId());
				if (hold != null) {
					synchronized (hold) {
						fb.start();
					}
				}
				logger.info(player.getId()
						+ "创建  据点争夺战战场结束============================"
						+ request.toString());
			} else {
				fb.start();
			}

			logger.info("fightInfo=" + player.getData().getFightInfo());
			endTime1 = TimeUtils.nowLong();

			// GameUtils.sendTip(tip, player.getUserInfo());
			// 写入日志 玩家 在日期xx参与战斗，战斗类型，战报id
//			FightEvent fe = fb.getAttacker().getFightEvent();
//			int fightEventId = 0;
//			if (fe != null) {
//				fightEventId = fe.getId();
//			}
//			GameLog.logPlayerEvent(player, LogEvent.FIGHT,
//					new LogBuffer().add(request.getType()).add(fightEventId));
			// tip.setMessage(tip.getMessage());
			// tip.setMessage(tip.getMessage()+" 耗时"+(endTime - startTime));
			// 发送提示消息
			GameUtils.sendTip(tip, player.getUserInfo(), GameUtils.FLUTTER);
//			sb.append("用户：" + player.getId() + "|战斗类型：" + request.getType()
//					+ " 回复:" + tip.getMessage() + "耗时"
//					+ (endTime1 - startTime1));
			// logger.info(sb.toString());
			FightLog.info(sb.toString());
			// return null;
			// }
			// tip.setMessage("战斗异常");
			// 发送提示消息
			// GameUtils.sendTip(tip, player.getUserInfo());
		} catch (Exception ex) {
			logger.info("fight error!" + ex.getMessage()+"\n"+request.toString());
		}
		return null;
	}

}
