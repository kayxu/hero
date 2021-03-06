package com.joymeng.game.net.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ErrorMessage;
import com.joymeng.game.GameServerApp;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.building.PlayerBuildingManager;
import com.joymeng.game.domain.flag.FlagManager;
import com.joymeng.game.domain.friend.Friend;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.time.SysTime;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.EnterGameRequest;
import com.joymeng.game.net.response.EnterGameResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class EnterGameService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(EnterGameService.class);
	int uid;

	public EnterGameService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleLogin(EnterGameRequest request,
			ServicesContext context) {
		EnterGameResp resp = new EnterGameResp();
		try {
			uid = request.getUid();
			byte type = request.getType();//类型 1 登录，0从玩家返回
			logger.info("echo from " + request.getJoyID() + " service"
					+ this.getClass().getName()+" uid="+uid+" userInfo="+request.getUserInfo().getUid());
			if(uid!=request.getUserInfo().getUid()){
				logger.error("uid="+uid+" userInfo="+request.getUserInfo().getUid());
			}
			if (GameServerApp.FREEZE) {// 如果服务器没有关闭
				logger.info("服务器已经关闭了");
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(new TipMessage("The server has been closed.",
							ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_FAIL),
							request.getUserInfo(),GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("服务器已经关闭了",
							ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_FAIL),
							request.getUserInfo(),GameUtils.FLUTTER);
				}
				
				return null;
			} else {
				// 发送服务器关闭消息
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.ERROR_SHUTWODN);
			}
			World gameWorld = World.getInstance();
			PlayerCharacter player = null;
			if(type == 1){
				// 获得登录玩家
				player = gameWorld
						.login(uid, request.getUserInfo());
			}else{
				player = gameWorld.getPlayer(uid);
			}
			
			// System.out.println("cache == "+GameUtils.getFromCache(player.getId()));
			// 缓存 用户基本数据
			
			if (player == null) {
				resp.setResult(GameConst.GAME_RESP_FAIL);
				resp.setErrorCode(ErrorMessage.NO_PLAYER);
				return resp;
			}
			
			GameUtils.putToCache(player);
			FlagManager.getInstance().closeRoom(player.getId(),true);
			// GameLog.info("logintime="+TimeUtils.now().format(TimeUtils.FORMAT1)+" uid="+player.getId()+
			// " uname="+player.getData().getName());
			if(type == 1){
				GameLog.logPlayerEvent(
						player,
						LogEvent.ROLE_LOGIN,
						new LogBuffer().add(TimeUtils.now().format(
								TimeUtils.FORMAT1)));
			}
			
			RespModuleSet rms = new RespModuleSet(ProcotolType.ENTER_GAME_RESP);// 模块消息
			
			player.getPlayerStorageAgent().loadPlayerCells(
					player.getData().getPlayerCells());
			// RelationManager.
			List<PlayerBuilding> lst = player.getPlayerBuilgingManager()
					.getPlayerAll();
			List<Cell> goods = player.getPlayerStorageAgent().getGoods();
			// player.getPlayerStorageAgent().loadPis();
			List<PlayerCache> roleLst = player.getPlayerUser().myRandomUser2(4);
			PlayerHero arr[] = player.getPlayerHeroManager().getHeroArray();

			/** =========修改建筑序列 */
			int x = 0;
			for (PlayerBuilding pp : lst) {
				if (pp.getBuildType() == PlayerBuildingManager.LEVELUP_BUILD) {
					x += 1;
				}
			}
			if (x > 0) {
				player.getPlayerBuilgingManager().setBuilding(false);
			} else {
				player.getPlayerBuilgingManager().setBuilding(true);
			}
			/** =========修改建筑序列 */

			if (player.getPlayerBuilgingManager().getPma() != null) {
				player.getPlayerBuilgingManager().getPma()
						.getTraining((byte) 0);
			}
			// 我主城的将领下发
			PlayerCache cache = MongoServer.getInstance()
					.getLogServer().getPlayerCacheDAO()
					.findPlayerCacheByUserid(player.getId());
			// String hh =
			// player.getPlayerUser().getUserHero((int)player.getData().getUserid());
			if (!"".equals(cache.getOfficerInfo())) {
				SimplePlayerHero occHero = new SimplePlayerHero(null,
						cache.getOfficerInfo());
				rms.addModule(occHero);// 同步时间
			}
			rms.addModule(new SysTime());// 同步时间
			rms.addModule(player.getData());
			
			List<Friend> friends = player.getRelationManager().myFriend();//下发好友
			for(Friend fri : friends){
				rms.addModule(fri);
			}
			// for(Mail m : mailLst){
			// rms.addModule(m);
			// }
			for (int i = 0; i < arr.length; i++) {
				rms.addModule(arr[i]);
			}

			if (lst != null) {
				for (PlayerBuilding pb : lst) {
					rms.addModule(pb);
				}
			}
			if (goods != null) {
				for (Cell cell : goods) {
					rms.addModule(cell);
				}
			}
			if (roleLst != null) {
				for (PlayerCache r : roleLst) {
					rms.addModule(r);
				}
			}
			AndroidMessageSender.sendMessage(rms, player);
			// 当前已经接受的任务列表
			player.getPlayerQuestAgent().sendTaskToClient();
			for (PlayerBuilding pp : lst) {
				if (pp.getBuildType() == PlayerBuildingManager.LEVELUP_BUILD) {
					player.getPlayerBuilgingManager().changeBuildingType(
							pp.getId(), PlayerBuildingManager.COMPLETE_LEVELUP);
				}
			}
			
			player.checkTraining();// 检查训练台
			// 如果存在玩家的缓存，则删除掉.当玩家退出游戏的时候，根据规则判断是否缓存该玩家
			// GameLog.logEvent(player, LogEvent.ROLE_LOGIN,
			// player.getUserId());
			resp.setUserInfo(request.getUserInfo());
			resp.setResult(GameConst.GAME_RESP_SUCCESS);
			resp.setErrorCode(GameConst.GAME_RESP_SUCCESS);
			resp.setSysTime((int) (TimeUtils.nowLong() / 1000));
			//下发推荐国家
			int countryId = NationManager.getInstance().recommendCounty();
			resp.setRecommendCounty(countryId);
			//军功加成
			if(NationManager.getInstance().armyAdd.get(player.getId()) == null)
			{
				NationManager.getInstance().armyAdd.put(player.getId(), player.goldMili());
			}
			
			//升级士兵
			player.getPlayerBuilgingManager().getPlayerBarrack().unLock((byte)1);
//			for(int i = 0 ; i < 20 ; i++){
//				player.getPlayerBuilgingManager().getPlayerBarrack().soliderUpgrade((byte)1);
//			}
//			player.getPlayerBuilgingManager().getPlayerBarrack().soliderUpgrade((byte)1);
//			player.getPlayerBuilgingManager().addBuild(23, 5);
		} catch (Exception ex) {
			GameLog.error("登录失败，uid=" + uid + "\n" + ex.toString(), ex);
			ex.printStackTrace();
		}

		// logger.info("isBuilding =="+player.getPlayerBuilgingManager().isBuilding+" "+player.getId());
		return resp;
	}

	private void Sleep(int i) throws InterruptedException {
		Thread.sleep(i);
		
	}
}
