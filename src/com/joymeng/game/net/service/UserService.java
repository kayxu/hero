package com.joymeng.game.net.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.role.PlayerAche;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.role.UsernameManager;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.game.net.request.UserRequest;
import com.joymeng.game.net.response.UserResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 玩家信息
 * 
 * @author admin
 * @date 2012-4-24 TODO
 */
@JoyMessageService
public class UserService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(UserService.class);

	private UserService() {
	}

	@JoyMessageHandler
	public JoyProtocol handleUserInfo(UserRequest request,
			ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		//0 创建角色（name,国家id，州id，城市id，县id）
		//1 获得玩家信息
		//2更新玩家信息  
//		logger.info("echo player from " + request.getJoyID());
		World gameWorld = World.getInstance();
		// 获得玩家角色
		PlayerCharacter player = gameWorld.getPlayerByUid(
				request.getUserInfo().getUid());
		if (player == null) {
			return null;
		}

		byte type = request.getType();
		logger.info(">>>>>>>>>user type=" + type);
		UserResp resp = new UserResp();
		resp.setType(type);
		resp.setUserInfo(request.getUserInfo());
		resp.setResult(GameConst.GAME_RESP_SUCCESS);
		RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);
		switch (type) {
		case ProcotolType.PLAYER_USERINFO:
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
			return null;
		case ProcotolType.USER_MOTIFY_NAME:
			String name = request.getCityName();
			RoleData flag = player.motifyName(name);
			System.out.println("返回类型："+flag);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			if(flag == null)
				resp.setErrorCode((byte) 0);
			else{
				resp.setErrorCode((byte) 1);
				resp.setName(flag.getName());
			}
			return resp;
		case ProcotolType.USER_RANDOM://随机用户
			int num = request.getHeroId();
			List<PlayerCache> roleLst = player.getPlayerUser().myRandomUser2(num);
			List<SimplePlayerHero> heroLst = new ArrayList<SimplePlayerHero>();
			StringBuilder sb=new StringBuilder();
			if(roleLst!= null && roleLst.size() > 0){
				for(PlayerCache data : roleLst){
					if(!"".equals(data.getOfficerInfo())){
						heroLst.add(new SimplePlayerHero(null,data.getOfficerInfo()));
					}
					rms.addModule(data);
					sb.append("userId:"+data.getUserid()+" OccupyUserId:"+data.getOccupyUserId());
				}
				if(heroLst != null && heroLst.size() > 0){
					for(SimplePlayerHero sp : heroLst)
						rms.addModule(sp);
				}
			}
			logger.info("随机玩家id="+player.getId()+" log="+sb.toString());
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms,player);
			return null;
		case ProcotolType.USER_ENTER:
			int userId = request.getUserId();
			List<ClientModule> lst = player.getPlayerUser().enterPlayer(userId);
			resp.setAllLst(lst);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
//			AndroidMessageSender.sendMessage(rms,player);
			return resp;
		case ProcotolType.USER_HERO://用户英雄
			int userIds = request.getUserId();
			PlayerHero hero = player.getPlayerUser().getUserHeroObj(userIds);
			if(hero != null)
				rms.addModule(hero);
			AndroidMessageSender.sendMessage(rms,player);
			return null;
		case ProcotolType.LADDER_RESET:
			byte level=request.getLevel() ;
			byte charge=request.getCharge();
			boolean result=GameDataManager.ladderManager.reset(player, level,charge);
			if(result){//成功
				rms.addModule(player.getData());
				AndroidMessageSender.sendMessage(rms,player);
			}
			resp.setErrorCode((byte) -1);
			resp.setResult(result?GameConst.GAME_RESP_SUCCESS:GameConst.GAME_RESP_FAIL);
			return resp;
		case ProcotolType.USER_TYPE:
			byte userType = request.getUserType();
			//String fullName = "";
//			for(int i=0;i<1000;i++){
			String fullName = UsernameManager.getInstance().getRandomAndUniqueName(userType,player);
			
			resp.setFullName(fullName);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
//			}
			return resp;
			//返回不重复的玩家名称
		case ProcotolType.USER_COMMIT:
			byte userTypeCommit = request.getUserType();
			String username = request.getUsername();
			int contryIds = request.getpId();
			byte isRecommend = request.getLevel();//1：推荐 0 ：不是推荐
			TipUtil tip = UsernameManager.getInstance().commit(userTypeCommit, username, player);
			
			if(tip.isResult()){
				TipUtil tips = NationManager.getInstance().getRandomNation(player, contryIds);
				if(tips.isResult()){
					//生成对应的推荐礼包 礼包id 596
					if(isRecommend == 1){
						Cell cell = player.getPlayerStorageAgent().addPropsCell(596, 1);
						rms.addModule(cell);
					}
					rms.addModule(player.getData());
					AndroidMessageSender.sendMessage(rms,player);
				}else{
					GameUtils.sendTip(
							new TipMessage(tips.getResultMsg(), ProcotolType.USER_INFO_RESP,
									GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
				}
			}
			if(!tip.isResult()){
				GameUtils.sendTip(
						new TipMessage(tip.getResultMsg(), ProcotolType.USER_INFO_RESP,
								GameConst.GAME_RESP_FAIL, type), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			//翻牌特殊要求，注册时更新玩家登录时间为，创建角色之后
			player.getData().setLastLoginTime(TimeUtils.nowLong());//alter 2012-11-26
			
			//player.save();
			resp.setSuccess(tip.isResult());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.ARENA_MESSAGE:
//			List<FightEvent> list=player.getFightEventManager().search(FightConst.FIGHTBATTLE_ARENA);
//			for(FightEvent event:list){
//				FightMessage fm=new FightMessage(event);
//				rms.addModule(fm);
//			}
//			AndroidMessageSender.sendMessage(rms,player);
			break;
		case ProcotolType.UP_PROMOTED:
			TipUtil tip2 = player.promotedMG();
			GameUtils.sendTip(tip2.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
			break;
		case ProcotolType.COUNTRY_NATION:
			int contryId = request.getpId();
			TipUtil tips = NationManager.getInstance().getRandomNation(player, contryId);
			GameUtils.sendTip(tips.getTip(), request.getUserInfo(),GameUtils.FLUTTER);
			break;
		case ProcotolType.PLAYER_ACHER:
			PlayerAche pa= player.getPlayerUser().getPlayerAcher();
			resp.setPlayerAche(pa);
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			return resp;
		case ProcotolType.PLAYER_HALT:
			byte halt=request.getStateId();
			player.setHalt(halt);
			logger.info("uid="+player.getId()+" halt="+halt);
			return resp;
		}
		return null;
	}
//	/**
//	 * 创建一个新的角色
//	 */
//	public void createNewUser(String cityName,int userId){
//		RoleData rd=RoleData.create();
//		rd.setName(cityName);
//		rd.setPlayerCells("");
//		rd.setUserid(userId);
//		rd.setCreateTime(TimeUtils.nowLong());
//		DBManager.getInstance().getWorldDAO().addRoleData(rd);
//		
//	}

}
