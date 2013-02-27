package com.joymeng.game.net.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.quest.AcceptedQuest;
import com.joymeng.game.domain.quest.PlayerQuestAgent;
import com.joymeng.game.domain.quest.Quest;
import com.joymeng.game.domain.quest.QuestManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.MissionRequest;
import com.joymeng.game.net.response.MissionResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 任务
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class MissionService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(MissionService.class);

	@JoyMessageHandler
	public JoyProtocol handleMission(MissionRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service="+this.getClass().getName());
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			logger.info("玩家不存在，id="+request.getUserInfo().getUid());
			return null;
		}
//		MissionResp resp = new MissionResp();//返回消息
		RespModuleSet rms = new RespModuleSet(ProcotolType.MISSION_RESP);// 模块消息
		byte type=request.getType();
		int id=request.getId();
//		List< AcceptedQuest> quests=player.getPlayerQuestAgent().getAcceptedQuests();
		AcceptedQuest aq=player.getPlayerQuestAgent().getAcceptQuest(id);
//		Quest quest=QuestManager.getInstance().getQuest(id);
		logger.info("quest serivce ,type="+type);
		switch(type){
		case 0://获得任务列表
			player.getPlayerQuestAgent().sendTaskToClient();
			break;
		case 1://查看任务
			if(aq==null){
				GameUtils.sendTip(new TipMessage("该任务不存在，id="+id, ProcotolType.MISSION_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				break;
			}
			if(aq.getStatus()!=PlayerQuestAgent.ACCEPTED){
				GameUtils.sendTip(new TipMessage("查看任务失败，该任务状态="+aq.getStatus()+" id="+aq.getQ().getId(), ProcotolType.MISSION_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				break;
			}
			aq.setStatus(PlayerQuestAgent.UNCOMPLETE);
			rms.addModule(aq);
			AndroidMessageSender.sendMessage(rms, player);	
//			World.getInstance().savePlayer(player);
			player.getPlayerQuestAgent().save();
			break;
		case 2://完成任务
			//判断能否完成，更改当前任务,只处理ui任务
			if(aq==null){
				GameUtils.sendTip(new TipMessage("该任务不存在，id="+id, ProcotolType.MISSION_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				break;
			}
			//ui类的任务
			Quest quest=aq.getQ();
			if(quest.getTarget()!=1&&quest.getTarget()!=46){
				GameUtils.sendTip(new TipMessage("类型错误，id="+id+" target="+quest.getTarget(), ProcotolType.MISSION_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				break;
			}
			//不进行任务完成的检测直接完成
			aq.setStatus(PlayerQuestAgent.COMPLETE);
			rms.addModule(aq);
			AndroidMessageSender.sendMessage(rms, player);	
//			World.getInstance().savePlayer(player);
			player.getPlayerQuestAgent().save();
			break;
		case 3://领取奖励
//			领取奖励，完成后自动接取下一个任务，
			if(aq==null){
				GameUtils.sendTip(new TipMessage("该任务不存在，id="+id, ProcotolType.MISSION_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
				break;
			}
			if(!player.getPlayerQuestAgent().finishQuest(aq)){
				GameUtils.sendTip(new TipMessage("无法完成该任务，id="+id, ProcotolType.MISSION_RESP,
						GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			logger.info("finish quest,="+player.getPlayerQuestAgent().getTip().getMessage());
			//发送提示消息
			String msg  = I18nGreeting.getInstance().getMessage("reward.success", null);
			GameUtils.sendTip(new TipMessage(msg, ProcotolType.MISSION_RESP,
					GameConst.GAME_RESP_SUCCESS,id), player.getUserInfo(),GameUtils.FLUTTER);
			break;
		}
		return null;
	}
}
