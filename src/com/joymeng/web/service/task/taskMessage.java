package com.joymeng.web.service.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.joymeng.core.chat.NoticeManager;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.web.service.biz.BizPlayer;
import com.joymeng.web.web.background.BgMessageController.Message;

public class taskMessage {
	List<Map<String, ?>> sendList = new ArrayList<Map<String, ?>>();
	public static taskMessage instance;
	public static taskMessage getInstance() {
		if (instance == null) {
			instance = new taskMessage();
		}
		return instance;
	}
	/**
	 * 查询
	 * @param c
	 * @param limit
	 * @return
	 */
	public List<?> serch(Class c,int limit){
			Query query=new Query(Criteria.where("isAuto").is("T"));
			query.sort().on("timestamp", Order.DESCENDING);
			if(limit>0){
				query.limit(limit);
			}
			List<?> list = MongoServer.getInstance().getLogServer().getMongoTemplate().find(query, c);
			return list;
	}

	

	/**
	 * 加载
	 * @return
	 */
	public void load() {
		List<?> list=serch(Message.class, 10);
		List<Map<String, ?>> lst = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Message message = (Message) list.get(i);
			System.out.println(message.getMessage());
			Map<String, String> map = new HashMap<String, String>();
			map.put("message",message.getMessage());
			map.put("date",message.getTimestamp().toString());
			map.put("isAuto",message.isAuto);
			lst.add(map);
		}
		sendList= lst;
	}
	
	public void autoSend(){
		boolean isSend = false;
		if(sendList == null || 0 == sendList.size()){
			load();
		}
		if(sendList != null && 0 < sendList.size() ){
			String msg = (String) sendList.get(0).get("message");
			GameUtils.sendWolrdMessage(new TipMessage(msg,
					ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_SUCCESS),
					GameUtils.SCROLL);
//			NoticeManager.getInstance().sendSystemWorldMessage(msg);
			sendList.remove(0);
		}
	}

}
