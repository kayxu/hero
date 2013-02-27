package com.joymeng.game.net.service;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.ChatChannel;
import com.joymeng.core.chat.Notice;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.ChatRequest;
import com.joymeng.game.net.response.ChatResp;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class ChatService extends AbstractJoyService {

	static Logger logger = LoggerFactory.getLogger(ChatService.class);

	public ChatService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleChat(ChatRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
//			gameWorld.sendFail((byte) ErrorMessage.NO_PLAYER,
//					ProcotolType.HERO_REQ, request.getUserInfo());
			return null;
		}
		
		// 获得在线列表
		ConcurrentHashMap<Integer, PlayerCharacter> playerList = gameWorld
				.getWorldRoleMap();
		byte type = request.getType();
		
		if(player.getData().getCanSpeak() == 1){//如果玩家被禁言
			String msg = I18nGreeting.getInstance().getMessage("chat.speak.not.allowed", null);
			GameUtils.sendTip(
					new TipMessage(msg, ProcotolType.CHAT_RESP,
							GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
			return null;
		}
		
		String message = request.getMessage();
		int receiveId = request.getReceiveId();

		Notice notice = new Notice(type, message);
		notice.setName(player.getData().getName());
		notice.setPlayerId(player.getId());
		notice.setIcon(player.getData().getFaction());
		notice.setLevel(player.getData().getLevel());
		notice.setCityLevel((byte)player.getData().getCityLevel());
		notice.setReceiveId(receiveId);
		Nation ids[] = NationManager.getInstance().getDetailed(
				player.getData().getNativeId());
		ArrayList<PlayerCharacter> list = null;

		ChatResp resp = new ChatResp();

		switch (type) {
		case ChatChannel.CHANNEL_COUNTRY:
		case ChatChannel.CHANNEL_STATE:
		case ChatChannel.CHANNEL_CITY:
		case ChatChannel.CHANNEL_WORLD:
			QuestUtils.checkFinish(player, QuestUtils.TYPE19, true, type);
			if(type==ChatChannel.CHANNEL_WORLD){
//				// 从背包中移除该书籍
//				Cell cell = player.getPlayerStorageAgent().dellCell(14, 1);
//				if(cell==null){
//					GameUtils.sendTip(new TipMessage("喊话道具数量不足",
//							ProcotolType.CHAT_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo());
//					break;
//				}
//				RespModuleSet rms = new RespModuleSet(ProcotolType.CHAT_RESP);// 模块消息
//				rms.addModule(cell);
//				AndroidMessageSender.sendMessage(rms, player);
				list = GameUtils.getList(type, 0, playerList);
			}else{
				list = GameUtils.getList(type, ids[type-1].getId(), playerList);
			}
		
		
			for (PlayerCharacter pc : list) {
				if (pc.getUserInfo() == null) {
					continue;
				}
				resp.setUserInfo(pc.getUserInfo());
				resp.setNotice(notice);
				JoyServiceApp.getInstance().sendMessage(resp);
			}
			break;
		case ChatChannel.CHANNEL_PRIVATE:
			//先给自己发一条
			resp.setUserInfo(player.getUserInfo());
			resp.setNotice(notice);
			JoyServiceApp.getInstance().sendMessage(resp);
			//再给对方发一条
			PlayerCharacter pc = gameWorld.getPlayer(receiveId);
			if (pc == null || pc.getUserInfo() == null) {
				break;
			}
			resp.setUserInfo(pc.getUserInfo());
			resp.setNotice(notice);
			JoyServiceApp.getInstance().sendMessage(resp);
			break;
		case ChatChannel.CHANNEL_SYS:
//			list = GameUtils.getList(type, ids[0].getId(), playerList);
//			for (PlayerCharacter pc1 : list) {
//				if (pc1.getUserInfo() == null) {
//					continue;
//				}
//				resp.setUserInfo(pc1.getUserInfo());
//				resp.setNotice(notice);
//				JoyServiceApp.getInstance().sendMessage(resp);
//			}
//			break;
		case ChatChannel.CHANNEL_SHOUT:
			if(gameWorld.getShoutSize()>10){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					//发送失败
					GameUtils.sendTip(new TipMessage("Send failed. The server is busy.",
							ProcotolType.CHAT_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					//发送失败
					GameUtils.sendTip(new TipMessage("发送失败，队列已经满",
							ProcotolType.CHAT_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}
				
			}
//			if(TimeUtils.nowLong()-player.getShoutTime()<30*GameConst.TIME_SECOND){//大于30秒不能喊话
//				GameUtils.sendTip(new TipMessage("距离上次喊话时间<30秒",
//						ProcotolType.CHAT_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo());
//				break;
//			}
			// 从背包中移除该书籍
			Cell cell = player.getPlayerStorageAgent().dellCell(14, 1);
			//写入日志  玩家 喊话
			GameLog.logPlayerEvent(player, LogEvent.SHOUT, new LogBuffer());
			if(cell==null){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					//发送失败
					GameUtils.sendTip(new TipMessage("No enough Horns!",
							ProcotolType.CHAT_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}else{
					//发送失败
					GameUtils.sendTip(new TipMessage("喊话道具数量不足",
							ProcotolType.CHAT_RESP, GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				}
				
				break;
			}
			TipMessage tip= new TipMessage( player.getData().getName()+":"+message,0,GameConst.GAME_RESP_SUCCESS);
//			player.setShoutTime(TimeUtils.nowLong());
			gameWorld.addShout(tip);
			RespModuleSet rms = new RespModuleSet(ProcotolType.CHAT_RESP);// 模块消息
			rms.addModule(cell);
			AndroidMessageSender.sendMessage(rms, player);
//			GameUtils.sendWolrdMessage(new TipMessage( player.getData().getName()+"："+message,0,GameConst.GAME_RESP_SUCCESS), (byte)1);
			break;
		}
		return null;
	}

}
