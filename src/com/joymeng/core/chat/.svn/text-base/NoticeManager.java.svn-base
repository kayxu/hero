package com.joymeng.core.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.response.ChatResp;
import com.joymeng.services.core.JoyServiceApp;

public class NoticeManager {
	static final Logger logger = LoggerFactory.getLogger(NoticeManager.class);

	private static NoticeManager instance = null;

	public static NoticeManager getInstance() {
		if (instance == null) {
			instance = new NoticeManager();
		}
		return instance;
	}

	private ArrayList<Notice> noticeList = new ArrayList<Notice>();

	/**
	 * 发送
	 */
	public void logic() {
		// Map<Integer, Player>
		// playerList=GLServer.getPlayerManager().getAllList();
		// ChatResponse resp = new ChatResponse();
		// //向所有在线玩家发送
		// for(Notice notice:noticeList){
		// logger.info("send notice ,notice="+notice.getContent());
		// Iterator<Integer> it=playerList.keySet().iterator();
		// while(it.hasNext()){
		// logger.info("send message ");
		// Player player=playerList.get(it.next());
		// if(player==null){
		// continue;
		// }
		//
		// resp.setUserInfo(player.getUserInfo());
		// resp.setNotice(notice);
		// JoyServiceApp.getInstance().sendMessage(resp);
		// }
		// }
		noticeList.clear();

	}

	/**
	 * 加入
	 * 
	 * @param n
	 */
	public void addNotice(Notice n) {
		noticeList.add(n);
	}

	/**
	 * 发送系统消息
	 * 
	 * @param message
	 */
	public void sendSystemWorldMessage(String message) {

		if (null == message || "".equals(message.trim())) {
			return;
		}
		// TEST 消息停顿一秒
		// Thread.sleep(1000);
		Notice notice = new Notice((byte) 5, message.trim());
		logger.info("系统消息：" + (message.length() == message.trim().length()));
		// 系统用户
		notice.setName("系统消息");
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			notice.setName("sys");
		}
		notice.setPlayerId(-1);
		notice.setIcon((byte) -1);
		notice.setLevel((short) 0);
		notice.setCityLevel((byte) 0);

		ConcurrentHashMap<Integer, PlayerCharacter> playerMap = World
				.getInstance().getWorldRoleMap();
		Iterator it = playerMap.keySet().iterator();
		ChatResp resp = new ChatResp();
		while (it.hasNext()) {
			PlayerCharacter player = playerMap.get(it.next());
			if (player.getUserInfo() == null) {
				continue;
			}
			resp.setUserInfo(player.getUserInfo());
			resp.setNotice(notice);
			JoyServiceApp.getInstance().sendMessage(resp);
		}

	}

	/**
	 * 发送系统消息给摸个用户
	 * 
	 * @param message
	 */
	public String sendSystemWorldMessageToPlayer(String message, int receiveId) {
		Notice notice = new Notice((byte) 5, message);
		logger.info("系统管理员：" + message);
		// 系统用户
		notice.setName("系统管理员");
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			notice.setName("admin");
		}
		
		notice.setPlayerId(-1);
		notice.setIcon((byte) -1);
		notice.setLevel((short) 0);
		notice.setCityLevel((byte) 0);

		PlayerCharacter pc = World.getInstance().getPlayer(receiveId);
		if (pc == null || pc.getUserInfo() == null) {
			logger.info("用户：" + pc.getId() + "|不在线无法发送");
			return "用户不在线无法发送";
		}
		ChatResp resp = new ChatResp();
		resp.setUserInfo(pc.getUserInfo());
		resp.setNotice(notice);
		JoyServiceApp.getInstance().sendMessage(resp);
		logger.info("用户：" + pc.getId() + "|发送成功");
		return "发送成功";
	}
	// /**
	// * 删除
	// * @param id
	// */
	// public void delNoticeById(int id){
	// for(Notice n:noticeList){
	// if(n.getId()==id){
	// noticeList.remove(n);
	// }
	// }
	// }
}
