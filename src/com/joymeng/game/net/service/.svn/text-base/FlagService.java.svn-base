package com.joymeng.game.net.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.flag.FlagManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.FlagRequest;
import com.joymeng.game.net.response.FlagResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 夺旗
 * 
 * @author xu_fangliang
 * @date 2012-10-29 
 */
@JoyMessageService
public class FlagService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(FlagService.class);

	private FlagService() {
		
	}
	FlagManager faMgr = FlagManager.getInstance();

	@JoyMessageHandler
	public JoyProtocol handleActive(FlagRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			logger.info("玩家不存在，id="+request.getUserInfo().getUid());
			return null;
		}
		FlagResp resp = new FlagResp();//
		UserInfo info = request.getUserInfo();
		byte type = request.getType();
		resp.setUserInfo(request.getUserInfo());
		resp.setType(type);
		logger.info("handleHero from " + request.getUserInfo().getUid()
				+ " type=" + type);
		RespModuleSet rms = new RespModuleSet(ProcotolType.FLAG_RESP);// 模块消息
		switch (type) {
		case ProcotolType.SIGN_UP://准备
			int signid= request.getSignId();
			int heroA = request.getHeroa();
			int heroB  = request.getHerob();
			int heroC =  request.getHeroc();
			PlayerCharacter pp = World.getInstance().getOnlineRole(signid);
			if(pp != null){
				if(heroA == 0 && heroB == 0 && heroC == 0){
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						GameUtils.sendTip(new TipMessage("Please select a hero.", ProcotolType.FLAG_RESP, GameConst.GAME_RESP_SUCCESS), info,GameUtils.FLUTTER);
					}else{
						GameUtils.sendTip(new TipMessage("请选择武将", ProcotolType.FLAG_RESP, GameConst.GAME_RESP_SUCCESS), info,GameUtils.FLUTTER);
					}
					
					resp.setIsSignType((byte)-1);
				}
				byte tt = faMgr.signUp(player,heroA,heroB,heroC);
				if(tt == 0){
					//resp.setType(ProcotolType.START_GAME_FIGHT);
				}else if(tt == -1){
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						GameUtils.sendTip(new TipMessage("Join failed.Your Lord should be at least Lv.5.", ProcotolType.FLAG_RESP, GameConst.GAME_RESP_SUCCESS), info,GameUtils.FLUTTER);
					}else{
						GameUtils.sendTip(new TipMessage("小于五级不能参加", ProcotolType.FLAG_RESP, GameConst.GAME_RESP_SUCCESS), info,GameUtils.FLUTTER);
					}
					
					resp.setIsSignType(tt);
				}else{
					resp.setIsSignType(tt);
				}
			}
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.SIGN_QUIT://退出
			int signid1= request.getSignId();
			faMgr.quit(signid1);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.START_GAME_FIGHT://开始
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.QUIT_ROOM://结束
			String roomid = request.getRoomids();
			int userid = request.getSignId();
			faMgr.quitFlagFight(roomid, userid);
//			GameUtils.sendTip(new TipMessage("quit ok", ProcotolType.CAPTURE_FLAG, GameConst.GAME_RESP_SUCCESS), player.getUserInfo());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.CAPTURE_FLAG://夺旗
			String room = request.getRoomids();
			Byte flagLatticeId = (byte)request.getSignId();
			Byte gamePoint =  (byte)request.getHeroa();
			TipUtil tip = faMgr.captureFlag(room, flagLatticeId, gamePoint);
			GameUtils.sendTip(new TipMessage(tip.getResultMsg(), ProcotolType.CAPTURE_FLAG, GameConst.GAME_RESP_SUCCESS), player.getUserInfo(),GameUtils.FLUTTER);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.REFRESH_MOBILITY://刷新积分
			String roomids = request.getRoomids();
			faMgr.refreshMobility(roomids);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.MOVE_POINT://可移动点
			String roomidss = request.getRoomids();
			Byte flagLatticeIdss = (byte)request.getSignId();
			int userids =  request.getHeroa();
			Map<Byte, ClientModuleBase> all = faMgr.canMoveLatt(roomidss, flagLatticeIdss, userids);
			//对应的buff
			resp.setAll(all);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.HERO_BUFF://buff
			String roo = request.getRoomids();
			int user = request.getSignId();
			faMgr.heroBuff(roo, user,true);
			faMgr.heroBuff(roo, user,false);
			
//			resp.setHeroBuffA(heroBuffA);
//			resp.setHeroBuffB(heroBuffB);
//			resp.setResult(GameConst.GAME_RESP_SUCCESS);
//			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return null;
		}
		return null;
	}
}
