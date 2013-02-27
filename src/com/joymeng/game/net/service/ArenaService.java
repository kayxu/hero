package com.joymeng.game.net.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.utils.PropertyManager;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.mod.Arena;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.time.SysTime;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.game.net.request.ArenaRequest;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 竞技场模块
 * 
 * @author admin
 * @date 2012-7-9 TODO
 */
@JoyMessageService
public class ArenaService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(ArenaService.class);

	private ArenaService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleArena(ArenaRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			// gameWorld.sendFail((byte) ErrorMessage.NO_PLAYER);
			logger.info("玩家不存在，id=" + request.getUserInfo().getUid());
			return null;
		}
		byte type = request.getType();
		int rankId = player.getData().getArenaId();
		switch (type) {
		case 0://刷新擂台
			break;
		case 1://刷新时间
			int m=GameConfig.arenaCD;
			if(player.saveResources( GameConfig.JOY_MONEY, -m)>0){
				
//				logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " uid=" + player.getId() + " uname=" + player.getData().getName() 
//						+ " diamondNum=" + m + " description=竞技场清冷却时间");
				
				GameLog.logSystemEvent(LogEvent.USE_DIAMOND, String.valueOf(GameConst.DIAMOND_ARENA),"", String.valueOf(m),String.valueOf(player.getId()));

				
				player.getData().setArenaTime(TimeUtils.nowLong());
			}else{
				//金钱不足
				GameUtils.sendTip(new TipMessage("金钱不足,当前钻石="+player.getData().getJoyMoney(), ProcotolType.ARENA_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				return null;
			}
			
			break;
		}
		List<Arena> list = GameDataManager.arenaManager.search(rankId);
		RespModuleSet rms = new RespModuleSet(ProcotolType.ARENA_RESP);// 模块消息
		for (Arena a : list) {
//			System.out.println("刷新擂台 id=" + a.getId());
			rms.addModule(a);
		}
		rms.addModule(player.getData());
		rms.addModule(new SysTime());
		AndroidMessageSender.sendMessage(rms, player);
		return null;
	}
}
