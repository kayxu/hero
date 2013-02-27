package com.joymeng.game.domain.fight;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.battle.FightBattleBase;
import com.joymeng.game.domain.fight.battle.FightBattleFactory;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.BaseRequest;
import com.joymeng.game.net.request.BuildingRequest;
import com.joymeng.game.net.request.FightRequest;

/**
 * 战斗管理
 * @author admin
 * @date 2012-5-3
 * TODO
 */
public class FightManager  extends Thread { 
	static Logger logger = LoggerFactory.getLogger(FightManager.class);
	private static Queue<BaseRequest> arenaQueue = new ConcurrentLinkedQueue< BaseRequest>();
	private static final int QUEUE_NUM=100;
	/**
	 * 加入战斗处理
	 * @param request
	 */
	public static void addFight(BaseRequest request){
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(request.getUserInfo()
				.getUid());
		if (player == null) {
			logger.info("玩家不存在，id=" + request.getUserInfo().getUid());
			return;
		}
		//竞技场关闭
		if(!ArenaManager.getInstance().isOpen()){
			GameUtils.sendTip(new TipMessage("竞技场维护中，请稍后再试！", ProcotolType.FIGHT_RESP,
					GameConst.GAME_RESP_FAIL, ((FightRequest)request).getType()), player.getUserInfo(), GameUtils.FLUTTER);
			return;
		}
		if(arenaQueue.size()>QUEUE_NUM){
			logger.info("服务器忙，请稍后再试！");
			if(request instanceof FightRequest){
				GameUtils.sendTip(new TipMessage("服务器忙，请稍后再试！", ProcotolType.FIGHT_RESP,
						GameConst.GAME_RESP_FAIL, ((FightRequest)request).getType()), player.getUserInfo(), GameUtils.FLUTTER);
			}else if(request instanceof BuildingRequest){
				GameUtils.sendTip(new TipMessage("服务器忙，请稍后再试！", ProcotolType.BUILDING_RESP,
						GameConst.GAME_RESP_FAIL, ((BuildingRequest)request).getType()), player.getUserInfo(), GameUtils.FLUTTER);
			}else{
				logger.info("状态错误");
			}
			return;
		}
		arenaQueue.offer(request);
		logger.info("加入竞技场战斗");
	}

	@Override
	public void run() {
		while (true) {
			try {
				if(arenaQueue.size()==0){
					Thread.sleep(10);
					continue;
				}
				BaseRequest request=arenaQueue.poll();
				if(request!=null){
				
					if(request instanceof FightRequest){
						logger.info("arenaQueue size="+arenaQueue.size()+" 竞技场战斗start  request"+request.toString());
						doFight((FightRequest)request);
						logger.info("arenaQueue size="+arenaQueue.size()+" 竞技场战斗end   request"+request.toString());
					}else if(request instanceof BuildingRequest){
						logger.info("arenaQueue size="+arenaQueue.size()+" 竞技场撤防start request"+request.toString());
						disarm((BuildingRequest)request);
						logger.info("arenaQueue size="+arenaQueue.size()+" 竞技场撤防end  request"+request.toString());
					}else{
						logger.info("状态错误");
					}
					
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 竞技场撤防
	 * @param request
	 */
	public static void disarm(BuildingRequest request){
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(request.getUserInfo()
				.getUid());
		if (player == null) {
			logger.info("玩家不存在，id=" + request.getUserInfo().getUid());
			return;
		}
		int pid11 =  request.getBuildingId();//将领id
		byte types = request.getStatus();
		PlayerHero ph = player.getPlayerHeroManager().getHero(pid11);
		TipUtil tips = player.getPlayerBuilgingManager().disarm(ph, "");
		GameUtils.sendTip(tips.getTip(), player.getUserInfo(),GameUtils.FLUTTER);
	}
	/**
	 * 战斗逻辑
	 * @param request
	 */
	public static void doFight(FightRequest request){ 
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(request.getUserInfo()
				.getUid());
		if (player == null) {
			logger.info("玩家不存在，id=" + request.getUserInfo().getUid());
			return;
		}
		long time1=TimeUtils.nowLong();
		FightBattleBase fb = FightBattleFactory.create(request, player);// 创建战场
		TipMessage tip = fb.getTip();
		fb.start();
		logger.info("总耗时="+(TimeUtils.nowLong()-time1));
		GameUtils.sendTip(tip, player.getUserInfo(), GameUtils.FLUTTER);
	
	}

	public static Queue<BaseRequest> getArenaQueue() {
		return arenaQueue;
	}

	public static void setArenaQueue(Queue<BaseRequest> arenaQueue) {
		FightManager.arenaQueue = arenaQueue;
	}
	
}
