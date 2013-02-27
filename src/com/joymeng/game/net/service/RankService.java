package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.rank.RankManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.RankRequest;
import com.joymeng.game.net.response.RankResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 排行榜
 * @author admin
 * @date 2012-5-31
 * TODO
 */
@JoyMessageService
public class RankService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(RankService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(RankRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色		
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		//long userid = player.getData().getUserid();
		//long id = player.getData().getId();
		if (player == null) {

			return null;
		}
		
		byte type = request.getType();
		RankResp resp = new RankResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		switch(type){
		case ProcotolType.GAME_MONEY_RANK:
			int requestPage = request.getRequestPage();
			int pageSize = request.getPageSize();
			RankManager.getInstance().rankGameMoney(requestPage, pageSize, player);
			resp.setTotalPages(RankManager.getInstance().gameMoneyTotalPages);
			resp.setCurrentPage(player.getData().getTempCurrentPage());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.GAME_JOY_MONEY_RANK:
			int requestPage2 = request.getRequestPage();
			int pageSize2 = request.getPageSize();
			RankManager.getInstance().rankJoyMoney(requestPage2, pageSize2, player);
			resp.setTotalPages(RankManager.getInstance().joyMoneyTotalPages);
			resp.setCurrentPage(player.getData().getTempCurrentPage());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.LADDER_RANK:
			int requestPage3 = request.getRequestPage();
			int pageSize3 = request.getPageSize();
			RankManager.getInstance().rankLadderMax(requestPage3, pageSize3, player);
			resp.setTotalPages(RankManager.getInstance().ladderMaxTotalPages);
			resp.setCurrentPage(player.getData().getTempCurrentPage());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.GENERAL_ATTACK_RANK:
			int requestPage4 = request.getRequestPage();
			int pageSize4 = request.getPageSize();
			RankManager.getInstance().rankHeroAttack(requestPage4, pageSize4, player);
			resp.setTotalPages(RankManager.getInstance().heroAttackTotalPages);
			resp.setCurrentPage(player.getData().getTempCurrentPage());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.GENERAL_DEFENSE_RANK:
			int requestPage5 = request.getRequestPage();
			int pageSize5 = request.getPageSize();
			RankManager.getInstance().rankHeroDefence(requestPage5, pageSize5, player);
			resp.setTotalPages(RankManager.getInstance().heroDefenceTotalPages);
			resp.setCurrentPage(player.getData().getTempCurrentPage());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.GENERAL_HP_RANK:
			int requestPage6 = request.getRequestPage();
			int pageSize6 = request.getPageSize();
			RankManager.getInstance().rankHeroHP(requestPage6, pageSize6, player);
			resp.setTotalPages(RankManager.getInstance().heroHPTotalPages);
			resp.setCurrentPage(player.getData().getTempCurrentPage());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ARENA_RANK:
			int requestPage7 = request.getRequestPage();
			int pagesize7 = request.getPageSize();
			ArenaManager.getInstance().getArenaRank(requestPage7, pagesize7, player);
			resp.setTotalPages(ArenaManager.getInstance().getTotalPages());
			resp.setCurrentPage(ArenaManager.getInstance().getCurrentPage());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
			
		}
		return null;
	}
}
