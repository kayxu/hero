package com.joymeng.game.net.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.friend.Friend;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FriendRequest;
import com.joymeng.game.net.response.FriendResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 好友
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class FriendService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(FriendService.class);

	private FriendService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleFriend(FriendRequest request,
			ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			// gameWorld.sendFail((byte) ErrorMessage.NO_PLAYER);
			return null;
		}

		byte type = request.getType();
		logger.info(">>>>>>>>>Friend type=" + type);
		FriendResp resp = new FriendResp();
		resp.setType(type);
		resp.setUserInfo(request.getUserInfo());
		// ***************************
		RespModuleSet rms = new RespModuleSet(ProcotolType.FRIEND_RESP);
		switch (type) {
		case ProcotolType.FRIEND_ALL:// 全部好友
			List<Friend> roleLst = player.getRelationManager().myFriend();
			//ArrayList<PlayerCache> roleLst1 = player.getRelationManager().randomOnlineUser((byte)0);
//			List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
//			lst.addAll(roleLst);
//			lst.addAll(roleLst1);
			
			for(Friend role : roleLst){
				rms.addModule(role);
			}
//			for(PlayerCache role : roleLst1){
//				rms.addModule(role);
//			}
			AndroidMessageSender.sendMessage(rms, player);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ONLINE_ALL:// 随机玩家
			byte types = request.getBackType();
			ArrayList<PlayerCache> roleLst3 = player.getRelationManager().randomOnlineUser(types);
			for(PlayerCache role : roleLst3){
				rms.addModule(role);
			}
			AndroidMessageSender.sendMessage(rms, player);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;

		case ProcotolType.RECENTLY_ALL:// 全部好友
//			int myId = request.getFriendId();
			List<Friend> roleLst2 = player.getRelationManager().myFriend();
			for(Friend role : roleLst2){
				rms.addModule(role);
			}
			AndroidMessageSender.sendMessage(rms, player);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ENEMY_ALL:// 全部敌人
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.CITY_PLAYER://本市在线好友
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ADD_FRIEND:// 添加好友
			int friendId = request.getFriendId();
			TipUtil tip = player.getRelationManager().addFriend(friendId);
			GameUtils.sendTip(tip.getTip(),request.getUserInfo(),GameUtils.FLUTTER);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.DEL_FRIEND:// 删除好友
			int friendIds = request.getFriendId();
			TipUtil tips = player.getRelationManager().delFriend(friendIds);
			GameUtils.sendTip(tips.getTip(),request.getUserInfo(),GameUtils.FLUTTER);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		}
		// ***************************
		return null;
	}
}
