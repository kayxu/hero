package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.SkillRequest;
import com.joymeng.game.net.response.SkillResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 技能合成
 * @author admin
 * @date 2012-6-7
 * TODO
 */
@JoyMessageService
public class SkillService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(SkillService.class);

	private SkillService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleSkill(SkillRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());
		int itemId1=request.getItemId1();
		int itemId2=request.getItemId2();
		World gameWorld = World.getInstance();
		SkillResp resp=new SkillResp();
		resp.setUserInfo(request.getUserInfo());
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
//			gameWorld.sendFail((byte) ErrorMessage.NO_PLAYER,ProcotolType.SKILL_REQ,request.getUserInfo());
			return null;
		}
		//获得技能
		logger.info("");
		int sk1=player.getSkillIdFromBook(itemId1);
		int sk2=player.getSkillIdFromBook(itemId2);
		if(sk1<=0||sk2<=0){
//			gameWorld.sendFail((byte) ErrorMessage.NO_BOOK,ProcotolType.SKILL_REQ,request.getUserInfo());
			return null;
		}
		//合成技能
		int newId=GameDataManager.skillManager.compose(sk1, sk2);
		if(newId<=0){
			logger.info("newId=="+newId);
//			gameWorld.sendFail((byte) ErrorMessage.ERROR_SKILLCOMPOSE,ProcotolType.SKILL_REQ,request.getUserInfo());
			resp.setResult(GameConst.GAME_RESP_FAIL);
			resp.setErrorCode(GameConst.GAME_RESP_FAIL);
			resp.setSkillId(0);
			return  resp;
		}
		RespModuleSet rms=new RespModuleSet(ProcotolType.SKILL_RESP);//模块消息
		//加入新技能书
		Props prop3 =player.getPlayerStorageAgent().getBookFromAllSkill(newId);
		if(prop3==null){
			logger.info("无法获得技能道具，skillid="+newId);
			resp.setResult(GameConst.GAME_RESP_FAIL);
			resp.setErrorCode(GameConst.GAME_RESP_FAIL);
			resp.setSkillId(0);
			return resp;
		}
		//删除旧技能书
		Cell cell1=player.getPlayerStorageAgent().dellCell(itemId1, 1);
		if(cell1==null){
			logger.info("cell is null ,id="+itemId1);
			return null;
		}
		else{
			Props props = (Props) cell1.getItem();
			logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
					+ " propsName=" + props.getPrototype().getName() + " useNumber=1" + " uid=" + player.getId() 
					+ " uname=" + player.getData().getName());
			GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
					,String.valueOf(player.getId()));
		}
		Cell cell2=player.getPlayerStorageAgent().dellCell(itemId2, 1);
		if(cell2==null){
			logger.info("cell is null ,id="+itemId2);
			return null;
		}
		else{
			Props props = (Props) cell2.getItem();
			logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
					+ " propsName=" + props.getPrototype().getName() + " useNumber=1" + " uid=" + player.getId() 
					+ " uname=" + player.getData().getName());
			GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
					,String.valueOf(player.getId()));
		}
		Cell cell3= player.getPlayerStorageAgent().addPropsCell(prop3.getId(), 1);
		if(cell3==null){
			logger.info("cell is null ,id="+prop3.getId());
			return null;
		}
		QuestUtils.checkFinish(player, QuestUtils.TYPE14, true);
		logger.info("=====技能合成,合成物品id="+prop3.getId());
		//推送给客户端
		rms.addModule(cell1);
		rms.addModule(cell2); 
		rms.addModule(cell3);
		AndroidMessageSender.sendMessage(rms, player);
		//返回新技能
		resp.setResult(GameConst.GAME_RESP_SUCCESS);
		resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
		resp.setSkillId(prop3.getId());
		return resp;
	}
	
}
