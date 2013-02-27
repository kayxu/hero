package com.joymeng.game.domain.fight.battle;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.fight.FightLog;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.net.request.FightRequest;
import com.joymeng.game.net.service.FightService;

public class FightBattleFactory {
	static Logger logger = LoggerFactory.getLogger(FightBattleFactory.class);
	public static FightBattleBase create(FightRequest request,
			PlayerCharacter player) {
		FightBattleBase battle = null;
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> handleFight start,player="+ player.getData().getName()+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
		switch (request.getType()) {
		case FightConst.FIGHTBATTLE_TEST:
			battle = new FBTest(request, player);
			logger.info(request.getUserInfo().getUid()+"创建测试战场================================\n"+request.toString());
			break;
		case FightConst.FIGHTBATTLE_LADDER:// 天梯
			battle = new FBLadder(request, player);
			logger.info(request.getUserInfo().getUid()+"创建通天塔战场================================\n"+request.toString());
			break;
		case FightConst.FIGHTBATTLE_CAMP:// 战役
			battle = new FBCamp(request, player);
			logger.info(request.getUserInfo().getUid()+"创建战役战场================================\n"+request.toString());
			break;
		case FightConst.FIGHTBATTLE_GUARD:// 驻防
			battle = new FBGuard(request, player);
			logger.info(request.getUserInfo().getUid()+"创建驻防战场================================\n"+request.toString());
			break;
		case FightConst.FIGHTBATTLE_ARENA:
			battle = new FBArena(request, player);
			logger.info(request.getUserInfo().getUid()+"创建竞技场战场================================"+request.toString());
			break;
		case FightConst.FIGHTBATTLE_RESOURCE:
		case FightConst.FIGHTBATTLE_RESOURCE2:
			logger.info(request.getUserInfo().getUid()+"创建FBGold战场================================"+request.toString());
			battle = new FBGold(request, player);
			break;
		case FightConst.FIGHTBATTLE_TEC:
		case FightConst.FIGHTBATTLE_TEC2:
			logger.info(request.getUserInfo().getUid()+"创建FBVeins战场================================"+request.toString());
			battle = new FBVeins(request, player);
			break;
		case FightConst.FIGHTBATTLE_REGION:
			logger.info(request.getUserInfo().getUid()+"创建县长争夺战战场=========================="+request.toString());
			battle = new FBRegion(request,player);
			break;
		case FightConst.FIGHTBATTLE_CITY:
		case FightConst.FIGHTBATTLE_STATE_CAMP:
		case FightConst.FIGHTBATTLE_COUNTRY_CAMP:
		case FightConst.FIGHTBATTLE_CITY_CAMP:
			FightLog.info(request.getUserInfo().getUid()+"创建  军营争夺战战场=========================="+request.toString());
			logger.info(request.getUserInfo().getUid()+"创建  军营争夺战战场=========================="+request.toString());
			battle = new FBNationCamp(request, player);
			break;
		case FightConst.FIGHTBATTLE_STATE_STRONG:
		case FightConst.FIGHTBATTLE_COUNTRY_STRONG:
		case FightConst.FIGHTBATTLE_CITY_STRONG:
			FightLog.info(request.getUserInfo().getUid()+"创建  据点争夺战战场==========================\n"+request.toString());
			logger.info(request.getUserInfo().getUid()+"创建  据点争夺战战场==========================\n"+request.toString());
			battle = new FBStrongHold(request, player);
			break;
		case FightConst.FIGHTBATTLE_FLAG:
			logger.info(request.getUserInfo().getUid()+"创建  1 VS 1 争夺战战场==========================\n"+request.toString());
			battle = new FBFlag(request, player);
			break;
		default:
			System.out.println("没有改战斗类型,type="+request.getType());
			break;
			
				
		}
		UUID uuid  =  UUID.randomUUID(); 
		battle.uuid=uuid;
		return battle;
	}
}
