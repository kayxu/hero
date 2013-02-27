package com.joymeng.game.domain.friend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.Instances;
import com.joymeng.game.common.MessageConst;
import com.joymeng.game.db.dao.RelationDAO;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;

public class RelationManager implements Instances {
	public static Logger logger = LoggerFactory
			.getLogger(RelationManager.class);
	// public static MailManager mailMgr = MailManager.getInstance();
	public PlayerCharacter player;

	/**
	 * @return GET the player
	 */
	public PlayerCharacter getPlayer() {
		return player;
	}

	/**
	 * @param SET
	 *            player the player to set
	 */
	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	public HashMap<Integer, Friend> friMgr = new HashMap<Integer, Friend>();// 好友list
	public HashMap<Integer, Enemy> emyMgr = new HashMap<Integer, Enemy>();// 仇敌

	public HashMap<Integer, RoleData> roleMgr = new HashMap<Integer, RoleData>();// 最近玩家

	public void init() {
		initFriend();
		// initEnemy();
	}

	public void initFriend() {// 我的好友
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate()
				.queryForList(RelationDAO.SQL_SELECT_FRIEND,
						player.getData().getUserid());
		for (Map<String, Object> map : lst) {
			Friend friend = gameDao.getRelationDAO().loadFriend(map);
			if (friend != null) {
				if (friend.getType() == 2) {
					friMgr.put(friend.getFriendId(), friend);// 成为好友
//					logger.info("用户：" + player.getId() + "|初始好友："
//							+ friMgr.size());
				}
			}
		}
//		System.out.println("用户：" + player.getId() + "|" + friMgr.size());
	}

	public void initEnemy() {
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate()
				.queryForList(RelationDAO.SQL_SELECT_ENEMY, player.getId());
		for (Map<String, Object> map : lst) {
			Enemy enemy = gameDao.getRelationDAO().loadEnemy(map);
			if (enemy != null) {
				emyMgr.put(enemy.getEnemyId(), enemy);
			}
		}
	}

	/**
	 * 查询所有好友
	 * 
	 * @param friendId
	 * @return
	 */
	public List<Friend> myFriend() {
		List<Friend> list = new ArrayList<Friend>(friMgr.values());
		List<Friend> newLst = new ArrayList<Friend>();
		if (list.size() > 0) {
			QuestUtils.checkFinish(player, QuestUtils.TYPE20, true);
		}
		for(Friend fr : list){
			if(World.getInstance().getOnlineRole(fr.getFriendId()) != null){
				fr.setType((byte)1);
			}else{
				fr.setType((byte)0);
			}
			newLst.add(fr);
		}
//		logger.info("用户：" + player.getId() + "|好友数量：" + list.size());
		return newLst;
	}

	/**
	 * 查找某个好友
	 */
	public Friend selFriend(int friendId) {
		return friMgr.get(friendId);
	}

	/**
	 * 被用户拒绝
	 * 
	 * @param friendId
	 */
	public TipUtil delFriend(int friendId) {
		TipUtil tip = new TipUtil(ProcotolType.FRIEND_RESP);
		tip.setFailTip("del fail");
//		logger.info("删除好友 ： " + friendId);
		Friend fr = friMgr.get(friendId);
		if (fr != null) {
			friMgr.remove(friendId);
			fr.setIsdel((byte)1);
			List<Friend> roleLst = player.getRelationManager().myFriend();
//			ArrayList<PlayerCache> roleLst1 = player.getRelationManager()
//					.randomOnlineUser((byte) 0);
			// **********rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.FRIEND_RESP);
//			for (Friend frs : roleLst) {
//				rms.addModule(frs);
//			}
//			for (PlayerCache cache : roleLst1) {
//				rms.addModule(cache);
//			}
			rms.addModule(fr);
			AndroidMessageSender.sendMessage(rms, player);
			gameDao.getRelationDAO().delFriend(fr);
			// **********rms
			tip.setSuccTip(MessageConst.FRIENT_DEL_S);
		} else {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setFailTip("Delete failed.");
			}else{
				tip.setFailTip("删除失败");
			}
			
		}
		return tip;
	}

	/**
	 * 添加 好友申请
	 */
	public TipUtil addFriend(int friendId) {
		TipUtil tip = new TipUtil(ProcotolType.FRIEND_RESP);
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			tip.setFailTip("Add failed.");
		}else{
			tip.setFailTip("添加失败");
		}
		
//		logger.info("[addFriend]添加好友 ： " + friendId);
		if (friMgr.get(friendId) == null) {
			// add friend type = 0
			Friend friend = new Friend();
			friend.setFriendId(friendId);
			friend.setMyId(player.getId());
			friend.setType((byte) 2);
			friend.setAddTime(TimeUtils.nowLong());
			gameDao.getRelationDAO().addFriend(friend);
			friend.setIsdel((byte)0);
			friMgr.put(friendId, friend);
//			logger.info("[addFriend]" + MessageConst.FRIENT_ADD_S);
			tip.setSuccTip(MessageConst.FRIENT_ADD_S);
			List<Friend> roleLst = player.getRelationManager().myFriend();
//			ArrayList<PlayerCache> roleLst1 = player.getRelationManager()
//					.randomOnlineUser((byte) 0);
			// **********rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.FRIEND_RESP);
			// rms.addModule(friend);
//			for (Friend role : roleLst) {
//				rms.addModule(role);
//			}
//			for (PlayerCache role : roleLst1) {
//				rms.addModule(role);
//			}
			rms.addModule(friend);
			AndroidMessageSender.sendMessage(rms, player);
			// **********rms
			return tip;
		} else {
			Friend friend = friMgr.get(friendId);
			if (friend == null) {
//				logger.info("好友为空,添加失败");
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("Add failed.Friend doesn't exist. ");
				}else{
					tip.setFailTip("好友为空,添加失败");
				}
				return tip;
			} else {
//				logger.info("[addFriend]" + MessageConst.FRIENTR_F1);
				friend.setTips(MessageConst.FRIENTR_F1,
						GameConst.GAME_RESP_FAIL);
				tip.setFailTip(MessageConst.FRIENTR_F1);
				return tip;
			}
		}
	}

	/**
	 * 加仇敌
	 */
	public void addEnemy(int enemyId) {
		Enemy enemy = new Enemy();
		enemy.setEnemyId(enemyId);
		enemy.setMyId(player.getId());
		enemy.setAttackTime(TimeUtils.nowLong());
		enemy.setReason("用户：" + enemyId + " 于  "
				+ TimeUtils.addSecond(enemy.getAttackTime(), 0) + " 攻击我！");
		gameDao.getRelationDAO().addEnemy(enemy);
		emyMgr.put(enemyId, enemy);// 添加缓存
	}

	/**
	 * 获得 所有在线列表
	 * 
	 * @param nationId
	 * @return
	 */
	public static ArrayList<RoleData> getAllOnline() {
		ArrayList<RoleData> simpleLst = new ArrayList<RoleData>();//
		// 获得在线列表
		ConcurrentHashMap<Integer, PlayerCharacter> playerList = gameWorld
				.getWorldRoleMap();
		for (PlayerCharacter pc : playerList.values()) {
			simpleLst.add(pc.getData());
		}
		return simpleLst;
	}

	/**
	 * 用户 0:世界 1:国家 2:州 3:市 4:县
	 */
	public ArrayList<PlayerCache> getUserOnline(byte type) {
		int nationid = player.getData().getNativeId();
		ArrayList<RoleData> allSimpleLst = getAllOnline();//
		ArrayList<PlayerCache> simpleLst = new ArrayList<PlayerCache>();
		switch (type) {
		case 0://全部
			for (RoleData sr : allSimpleLst) {
				simpleLst.add(MongoServer.getInstance().getLogServer().getPlayerCacheDAO()
						.findPlayerCacheByUserid((int) sr.getUserid()));
			}
			break;
		case 1://本国
			for (RoleData sr : allSimpleLst) {
				if (sr != null
						&& sr.getNativeId() / 1000 * 1000 == nationid / 1000 * 1000) {
					simpleLst.add(MongoServer.getInstance()
							.getLogServer().getPlayerCacheDAO()
							.findPlayerCacheByUserid((int) sr.getUserid()));
				}
			}
			break;
		case 2://本州
			for (RoleData sr : allSimpleLst) {
				if (sr != null
						&& sr.getNativeId() / 100 * 100 == nationid / 100 * 100) {
					simpleLst.add(MongoServer.getInstance()
							.getLogServer().getPlayerCacheDAO()
							.findPlayerCacheByUserid((int) sr.getUserid()));
				}
			}
			break;
		case 3://本市
			for (RoleData sr : allSimpleLst) {
				if (sr != null
						&& sr.getNativeId() / 10 * 10 == nationid / 10 * 10) {
					simpleLst.add(MongoServer.getInstance()
							.getLogServer().getPlayerCacheDAO()
							.findPlayerCacheByUserid((int) sr.getUserid()));
				}
			}
			break;
		case 4://本县
			for (RoleData sr : allSimpleLst) {
				if (sr != null && sr.getNativeId() == nationid) {
					simpleLst.add(MongoServer.getInstance()
							.getLogServer().getPlayerCacheDAO()
							.findPlayerCacheByUserid((int) sr.getUserid()));
				}
			}
			break;
		case 5:
			for (RoleData rd : roleMgr.values()) {
				simpleLst.add(MongoServer.getInstance().getLogServer().getPlayerCacheDAO()
						.findPlayerCacheByUserid((int) rd.getUserid()));
			}
		}
		return simpleLst;
	}

	/**
	 * 添加最近用户
	 * 
	 * @param userid
	 */
	public void addRecentlyPlayer(int userid) {
		PlayerCharacter pp = World.getInstance().getPlayer(userid);
		if (pp != null) {
			roleMgr.put(userid, pp.getData());
			// rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.FRIEND_RESP);
			rms.addModule(MongoServer.getInstance().getLogServer().getPlayerCacheDAO()
					.findPlayerCacheByUserid(pp.getId()));
			AndroidMessageSender.sendMessage(rms, player);
			// rms
		}
	}

	/**
	 * 随机20个
	 * 
	 * @param type
	 */
	public ArrayList<PlayerCache> randomOnlineUser(byte type) {
		ArrayList<PlayerCache> simpleLst = getUserOnline(type);
		
//		logger.info("用户：" + player.getId() + "|随机玩家数量：" + simpleLst.size());
		if (simpleLst.size() <= 20) {
			return simpleLst;
		} else {
			Collections.shuffle(simpleLst); // 重新洗牌
			return new ArrayList<PlayerCache>(simpleLst.subList(0, 20));
		}
	}
}
