package com.joymeng.game.domain.flag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.time.SysTime;
import com.joymeng.game.domain.world.TipUtil;

public class FlagManager {
	static int INITMOB = 9;// 初始积分
	static int INIT_MOVE = 1;// 移动积分
	static int ADD_MOB = 1;// 每分钟自动恢复积分
	static int INIT_OUT = 3;// 出营积分
	static int INIT_BUFF = 40;// 加成
	static int WIN_MULTIPLE = 3;// 胜利加成
	static int FIL_MULTIPLE = 1;// 失败加成
	static int REFRESH_TIME = 30;//刷新时间间隔
	static int END_TIME = 10 * 60;//游戏结束时间
	static final Logger logger = LoggerFactory.getLogger(FlagManager.class);
	// 报名列表
	public Map<Integer, GameHome> signMap = new HashMap<Integer, GameHome>();

	// 房间列表
	public Map<String, Room> roomMap = new HashMap<String, Room>();
	public static FlagManager instance;

	public static FlagManager getInstance() {
		if (instance == null)
			instance = new FlagManager();
		return instance;
	}

	/**
	 * 报名顺序 5- 5 6 -10 11- 15 16 -20 21 - 30
	 */
	public synchronized byte signUp(PlayerCharacter player, int a, int b, int c) {
		if(a == 0 && b ==0 && c == 0){
			return (byte) -1;
		}
		GameHome homeB = null;
		int begin = 0;
		int end = 0;
		if (player.getData().getLevel() < 5) {
			return (byte) -1;
		} else if (player.getData().getLevel() <= 5) {
			begin = 0;
			end = 5;
		} else if (player.getData().getLevel() <= 10
				&& player.getData().getLevel() >= 6) {
			begin = 6;
			end = 10;
		} else if (player.getData().getLevel() <= 15
				&& player.getData().getLevel() >= 11) {
			begin = 11;
			end = 15;
		} else if (player.getData().getLevel() <= 20
				&& player.getData().getLevel() >= 16) {
			begin = 16;
			end = 20;
		} else if (player.getData().getLevel() <= 30
				&& player.getData().getLevel() >= 21) {
			begin = 21;
			end = 30;
		}
		for (GameHome pm : signMap.values()) {
			if (pm.getPlayer().getData().getLevel() >= begin
					&& pm.getPlayer().getData().getLevel() <= end
					&& pm.getPlayer().getId() != player.getId()) {
				if (pm.getCreateTime() + 120 >= (int) (System
						.currentTimeMillis() / 1000)) {
					homeB = pm;
					break;
				} else {
					// 超时退出
					quit(pm.getPlayer().getId());
				}
			}
		}
		if (homeB != null) {// 可以配对
			logger.info("房间开启...");
			signMap.remove(homeB.getPlayer().getId());// 等待用户扣除
			// homeB = new GameHome();
			// homeB.create(World.getInstance().getPlayer(1603), 0, 0, 0,
			// INITMOB);

			GameHome homeA = new GameHome();
			homeA.create(player, a, b, c, INITMOB);
			// 开房间
			Room room = new Room();
			room.homeA = homeA;
			room.homeB = homeB;
			room.refTime = (int) (TimeUtils.nowLong() / 1000) + 120;
			room.startTime = (int) (TimeUtils.nowLong() / 1000);
			roomMap.put(room.id, room);// 房间加入 开始
			if (room.startGame()) {
				sendOne(new GameStart((byte) 0,"",(byte) 0,(byte) 0,0),
						new PlayerCharacter[] { homeA.getPlayer(),
								homeB.getPlayer() });
				return (byte) 0;
			} else {
				roomQuit(room.getId());// 房间退出
				return (byte) 1;
			}
		} else {// 不可以等待
			logger.info("房间等待...");
			GameHome homeA = new GameHome();
			homeA.create(player, a, b, c, INITMOB);
			signMap.put(player.getId(), homeA);
			logger.info("...房间等待...数量："+roomMap.size());
			String message = I18nGreeting.getInstance().getMessage("PVP.mate",
 					new Object[]{player.getData().getName(),player.getData().getLevel()});
			NoticeManager.getInstance().sendSystemWorldMessage(message);
			return (byte) 1;
		}
	}

	/**
	 * 自行退出
	 * 
	 * @param player
	 */
	public void quit(int id) {
		signMap.remove(id);
		System.out.println(signMap.keySet());
	}

	/**
	 * 房间关闭
	 * 
	 * @param roomid
	 */
	public void roomQuit(String roomid) {
		Room room = roomMap.get(roomid);
		if (room != null && room.homeA != null && room.homeB != null) {
			signMap.put(room.homeA.getPlayer().getId(), room.homeA);
			signMap.put(room.homeB.getPlayer().getId(), room.homeB);
			roomMap.remove(roomid);
		}
	}

	/**
	 * 夺旗/交旗
	 * 
	 * @param roomid
	 * @param flagLatticeId
	 * @param gamePoint
	 */
	public TipUtil captureFlag(String roomid, Byte flagLatticeId, Byte gamePoint) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		Room room = roomMap.get(roomid);
		if (room != null && !room.isClose) {
			tip = room.captureFlag(flagLatticeId, gamePoint);
		} else {
			tip.setFailTip("房间不存在");
		}
		return tip;
	}

	/**
	 * 刷新积分
	 * 
	 * @param roomid
	 */
	public void refreshMobility(String roomid) {
		Room room = roomMap.get(roomid);
		if (room != null && !room.isClose) {
			if (!room.outLine()) {// 没有掉线
				room.homeA.refreshMobility(ADD_MOB,room.allHomeArray);
				room.homeB.refreshMobility(ADD_MOB,room.allHomeArray);
			}
		}
	}

	/**
	 * 可移动道路
	 * 
	 * @param roomid
	 * @param latt
	 * @param userid
	 * @return
	 */
	public Map<Byte, ClientModuleBase> canMoveLatt(String roomid, byte latt,
			int userid) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		tip.setFailTip("fail");
		Room room = roomMap.get(roomid);
		if (room != null && !room.isClose) {
			Map<Byte, ClientModuleBase> all = room.allMove(userid, latt);
			if (all != null) {
				logger.info("可移动道路:" + all.keySet().toString());
				return all;
			}
		}
		return null;
	}
	/**
	 * 取得莫格玩家的buff 
	 * @param roomid
	 * @param userid
	 * @return
	 */
	public void  heroBuff(String roomid,int userid,boolean isMy){
		Room room = roomMap.get(roomid);
		if (room != null && !room.isClose) {
			if(isMy){
				room.buffOnHero(userid);
			}else{
				GameHome[] homess = room.getMyHome(userid);
				if(homess != null){
					 room.buffOnHero(homess[1].getPlayer().getId());
				}
			}
		}
		return;
	}

	/**
	 * 退出游戏
	 * 
	 * @param roomid
	 */
	public boolean quitFlagFight(String roomid, int userid) {
		Room room = roomMap.get(roomid);
		if (room != null && !room.isClose) {
			Room r = room.quitRoom(0, userid,false);
			roomMap.remove(r);
			return true;
		}
		return false;
	}

	/**
	 * 查询每个room看是否 超时或者 退出
	 * 
	 * @param roomid
	 */
	public void closeRoom(int playerid,boolean isEnter) {
		List<Room> delLst = new ArrayList<Room>();
		for (Room room : roomMap.values()) {
			if (room.isClose) {
				delLst.add(room);
			} else if ((room.getStartTime() -10) < (int) (System
					.currentTimeMillis()/1000)) {
				Room r = room.quitRoom(0, 0,false);
				if (r.isClose) {
					delLst.add(room);
				}
			} else {
				if (room.homeA.getPlayer().getId() == playerid
						|| room.homeB.getPlayer().getId() == playerid) {
					Room r = room.quitRoom(0, playerid,isEnter);
					if (r.isClose) {
						delLst.add(room);
					}
				}
			}
		}
		// 关闭房间
		for (Room room : delLst) {
			logger.info("关闭房间id:" + room.getId());
			roomMap.remove(room.getId());
		}
		logger.info("...房间等待...数量："+roomMap.size());
	}

	public void moveAll(String roomid, int lid) {
		Room room = roomMap.get(roomid);
		if (room != null && !room.isClose) {

		}
	}

	/**
	 * 发送消息
	 * 
	 * @param base
	 * @param ps
	 */
	public void sendOne(ClientModuleBase base, PlayerCharacter[] ps) {
		RespModuleSet rms = new RespModuleSet(ProcotolType.FLAG_RESP);// 模块消息
		SysTime sys = new SysTime();//矫正时间
		rms.addModule(sys);
		rms.addModule(base);
		for (int i = 0; i < ps.length; i++) {
			if (null != ps[i]) {
				AndroidMessageSender.sendMessage(rms, ps[i]);
			} else {
				logger.info("sendOne user is null");
				return;
			}

		}
		logger.info("sendOne:" + base);
	}

	/**
	 * 发送消息
	 * 
	 * @param base
	 * @param ps
	 */
	public void sendAll(List<ClientModuleBase> lst, PlayerCharacter[] ps) {
		RespModuleSet rms = new RespModuleSet(ProcotolType.FLAG_RESP);// 模块消息
		SysTime sys = new SysTime();//矫正时间
		rms.addModule(sys);
		rms.addModuleBase(lst);
		for (int i = 0; i < ps.length; i++) {
			if (null != ps[i]) {
				AndroidMessageSender.sendMessage(rms, ps[i]);
			} else {
				logger.info("sendAll user is null");
				return;
			}
		}
		logger.info("sendAll:" + lst.size());
		return;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map map = new HashMap<>();
		logger.info(map.keySet().toString());
	}

}
