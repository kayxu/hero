package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.box.PropsBoxManager;
import com.joymeng.game.domain.recharge.Recharge;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.RechargeRequest;
import com.joymeng.game.net.response.RechargeResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;
import com.joymeng.game.domain.box.Package;

@JoyMessageService
public class RechargeService extends AbstractJoyService {

static Logger logger = LoggerFactory.getLogger(RechargeService.class);
	
	@JoyMessageHandler
	public JoyProtocol handleService(RechargeRequest request, ServicesContext context) {
	logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}
		
		byte type = request.getType();
		RechargeResp resp = new RechargeResp();//返回消息
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()+" type="+type);
		switch(type){
		case ProcotolType.DO_RECHARGE://充值
			player.getRechargeManager().doRecharge(request.getRechargeVal());
			if(player.getRechargeManager().getGetAwardChance() > 0){
				resp.setActionType((byte)1);
				resp.setPrevCharge(request.getRechargeVal());
				resp.setNextCharge(player.getRechargeManager().getToNextNeedRechargeVal());
				resp.setRewardValue(player.getRechargeManager().getNextRecharge().getAwardVal());
				resp.setHornorName(player.getRechargeManager().getNextRecharge().getKeyAward());
				resp.setRewardsIds(player.getRechargeManager().getAwardIds().get(0));
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			}
			else{
				resp.setActionType((byte)0);
				resp.setPrevCharge(request.getRechargeVal());
				resp.setNextCharge(player.getRechargeManager().getToNextNeedRechargeVal());
				resp.setRewardValue(player.getRechargeManager().getNextRecharge().getAwardVal());
				resp.setHornorName(player.getRechargeManager().getNextRecharge().getKeyAward());
				Recharge nextRecharge = player.getRechargeManager().getNextRecharge();
				Package p = PropsBoxManager.getInstance().packageMap.get(nextRecharge.getPackageId());
				resp.setRewardsIds(p.getItemId() + ":" + nextRecharge.getRechargeVal());
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
				
			}
			
			return resp;
		case ProcotolType.GET_RECHARGE_AWARD://领奖
			player.getRechargeManager().getRechargeAward();
			if(player.getRechargeManager().getGetAwardChance() > 0){
				resp.setActionType((byte)1);
				resp.setPrevCharge(request.getRechargeVal());
				resp.setNextCharge(player.getRechargeManager().getToNextNeedRechargeVal());
				resp.setRewardValue(player.getRechargeManager().getNextRecharge().getAwardVal());
				resp.setHornorName(player.getRechargeManager().getNextRecharge().getKeyAward());
				resp.setRewardsIds(player.getRechargeManager().getAwardIds().get(0));
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			}
			else{
				resp.setActionType((byte)0);
				resp.setPrevCharge(request.getRechargeVal());
				resp.setNextCharge(player.getRechargeManager().getToNextNeedRechargeVal());
				resp.setRewardValue(player.getRechargeManager().getNextRecharge().getAwardVal());
				resp.setHornorName(player.getRechargeManager().getNextRecharge().getKeyAward());
				Recharge nextRecharge = player.getRechargeManager().getNextRecharge();
				Package p = PropsBoxManager.getInstance().packageMap.get(nextRecharge.getPackageId());
				resp.setRewardsIds(p.getItemId());
				resp.setResult(GameConst.GAME_RESP_SUCCESS);
				resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			}
			
			return resp;

		}
		return null;
	}
}
