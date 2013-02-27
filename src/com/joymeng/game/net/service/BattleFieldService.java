package com.joymeng.game.net.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.card.SimpleAward;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightEventManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.BattleFieldRequest;
import com.joymeng.game.net.response.BattleFieldResp;
import com.joymeng.game.net.response.FightResp;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class BattleFieldService extends AbstractJoyService{

	static Logger logger = LoggerFactory.getLogger(BattleFieldService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(BattleFieldRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色		
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
//		long userid = player.getData().getUserid();
		//long id = player.getData().getId();
		if (player == null) {
			return null;
		}
		
		byte type = request.getType();
		BattleFieldResp resp = new BattleFieldResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
//		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		switch(type){
		case ProcotolType.PERSONAL_FIGHT_EVENT:
			int requestPage = request.getRequestPage();
			int pageSize = request.getPageSize();
			player.getFightEventManager().personalFightEvent(requestPage, pageSize);
			resp.setCurrentPage(player.getFightEventManager().getCurrentPage());
			resp.setTotalPages(player.getFightEventManager().getTotalPages());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.SEE_FIGHT_EVENT:
			int id = request.getId();
			FightEvent fightEvent=player.getFightEventManager().seeFightEvent(id);
			//发送战斗数据
			String str=fightEvent.getData();
			FightResp fresp = new FightResp();
			byte[] data=null;
			try {
				data = str.getBytes("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			FightUtil.deserializeCmd(data);
			fresp.setData(data);
			fresp.setUserInfo(request.getUserInfo());
			JoyServiceApp.getInstance().sendMessage(fresp);
			
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.STAGE_FIGHT_EVENT://阶段战报
			FightEvent fightEvent2 = FightEventManager.stageFightEvent;
			//发送战斗数据
			String str2=fightEvent2.getData();
			FightResp fresp2 = new FightResp();
			byte[] data2=null;
			try {
				data2 = str2.getBytes("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			FightUtil.deserializeCmd(data);
			fresp2.setData(data2);
			fresp2.setUserInfo(request.getUserInfo());
			JoyServiceApp.getInstance().sendMessage(fresp2);
			
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.SYS_BATTLE_INFO://系统战斗开放信息
			requestPage = request.getRequestPage();
			pageSize = request.getPageSize();
			player.getFightEventManager().getSystemBattleFieldInfo(requestPage,pageSize);
			resp.setCurrentPage(player.getFightEventManager().getCurrentPage());
			resp.setTotalPages(player.getFightEventManager().getTotalPages());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.SYS_AWARD://系统奖励
			requestPage = request.getRequestPage();
			pageSize = request.getPageSize();
			player.getFightEventManager().getSystemAwardInfo(requestPage, pageSize);
			resp.setCurrentPage(player.getFightEventManager().getCurrentPage());
			resp.setTotalPages(player.getFightEventManager().getTotalPages());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.GET_SYS_AWARD://领取系统奖励
			id = request.getId();
			List<SimpleAward> saList = player.getFightEventManager().getAward(id);
			resp.setSaList(saList);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.IS_NEED_GET_SYS_AWARD://是否需要领取系统奖励
			boolean need = DBManager.getInstance().getWorldDAO().getArenaAwardDAO().isPlayerHasArenaAward((int)player.getData().getUserid());
			if(need){
				//发系统消息给获得奖励的玩家
				GameUtils.sendEventOpenFlag((byte)3, player.getUserInfo());
			}
			//return null;
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		}
		return null;
	}
	
}
