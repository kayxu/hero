package com.joymeng.game.common;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.chat.ChatChannel;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.NewUser;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.cache.domain.PropSale;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.quest.Quest;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.response.SystemResp;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;
import com.joymeng.web.entity.CardOperationInfo;
import com.joymeng.web.entity.ExtremeBox;
import com.joymeng.web.entity.Sale;

public class GameUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(GameUtils.class);

	/**
	 * 飘动
	 */
	public static final byte FLUTTER = 0;

	/**
	 * 滚动
	 */
	public static final byte SCROLL = 1;

	/**
	 * 活动消息
	 */
	public static final byte ACTIVE = 2;

	/**
	 * 系统奖励消息
	 */
	public static final byte SYSTEM_AWARD = 3;

	/**
	 * 根据类型查找相同（id）的玩家
	 * 
	 * @param channel
	 *            国家1/州2 / 市3
	 * @param id
	 *            玩家的国家/州 /市数值 na[0] = contry; na[1] = state; na[2] = city;
	 * @param map
	 *            //全部玩家列表
	 * @return
	 */
	public static ArrayList<PlayerCharacter> getList(byte channel, int id,
			ConcurrentHashMap<Integer, PlayerCharacter> map) {
		ArrayList<PlayerCharacter> list = new ArrayList<PlayerCharacter>();
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			PlayerCharacter pc = map.get(it.next());
			Nation nations[] = NationManager.getInstance().getDetailed(
					pc.getData().getNativeId());
			if (channel == ChatChannel.CHANNEL_WORLD) {
				list.add(pc);
			} else {
				if (nations[channel - 1].getId() == id) {
					list.add(pc);
				}
			}
		}
		return list;
	}

	/**
	 * 发送统一的提示
	 * 
	 * @param tip
	 * @param info
	 * @param drawType
	 *            0漂浮 1滚动
	 */
	public static void sendTip(TipMessage tip, UserInfo info, int drawType) {
		if (tip == null) {
			return;
		}
		if (info == null) {
			return;
		}
		String str = tip.getMessage();
		int type = tip.getType();
		byte result = tip.getResult();
		int p1 = tip.getP1();
		int p2 = tip.getP2();
		SystemResp resp = new SystemResp();// 系统消息
		resp.setUserInfo(info);
		resp.setErrorStr(str);
		resp.setType(type);
		resp.setResult(result);
		resp.setP1(p1);
		resp.setP2(p2);
		resp.setDrawType((byte) drawType);
		JoyServiceApp.getInstance().sendMessage(resp);
//		logger.info(info.getUid() + "..." + str + " result=" + result
//				+ " type=" + type + " p1=" + p1 + " p2=" + p2);
	}

	/*
	 * public static void sendScrollTip(TipMessage tip,UserInfo info) {
	 * if(tip==null){ return; } if(info==null){ return; } String
	 * str=tip.getMessage(); int type=tip.getType(); byte
	 * result=tip.getResult(); int p1=tip.getP1(); int p2=tip.getP2();
	 * SystemResp resp = new SystemResp();//系统消息 resp.setUserInfo(info);
	 * resp.setErrorStr(str); resp.setType(type); resp.setResult(result);
	 * resp.setP1(p1); resp.setP2(p2); resp.setDrawType((byte)1);
	 * JoyServiceApp.getInstance().sendMessage(resp);
	 * logger.info(info.getUid()+"send tip str="
	 * +str+" result="+result+" type="+type+" p1="+p1+" p2="+p2); }
	 */
	// public static void sendShout(TipMessage tip,UserInfo info) {
	// if(tip==null){
	// return;
	// }
	// if(info==null){
	// return;
	// }
	// String str=tip.getMessage();
	// int type=tip.getType();
	// byte result=tip.getResult();
	// int p1=tip.getP1();
	// int p2=tip.getP2();
	// SystemResp resp = new SystemResp();//系统消息
	// resp.setUserInfo(info);
	// resp.setErrorStr(str);
	// resp.setType(type);
	// resp.setResult(result);
	// resp.setP1(p1);
	// resp.setP2(p2);
	// resp.setDrawType((byte)2);
	// JoyServiceApp.getInstance().sendMessage(resp);
	// logger.info(info.getUid()+"..."+str+" result="+result+" type="+type+" p1="+p1+" p2="+p2);
	//
	// }
	// /**
	// * 将将领id转换为字符串,只用于刷新将领
	// *
	// * @param array
	// * 第0个代表隐藏,用；分割
	// * @param type
	// * 1表示隐藏
	// * @return
	// */
	// public static String changeIds(Hero[] array, byte type, String idStr) {
	// String temp = "";
	// if (idStr == null || idStr.equals("")) {
	// temp = "0;0;0;0";
	// } else {
	// temp = idStr;
	// }
	// String ids[] = temp.split(";");
	// if (type == 1) {
	// ids[0] = String.valueOf(array[0].getId());
	// } else {
	// for (int i = 0; i < array.length; i++) {
	// ids[i + 1] = String.valueOf(array[i].getId());
	// }
	// }
	// String str = "";
	// for (int i = 0; i < ids.length; i++) {
	// if (i == 0) {
	// str = "" + ids[i];
	// } else {
	// str += ";" + ids[i];
	// }
	// }
	// System.out.println("changeIds=" + str);
	// return str;
	// }
	/**
	 * 发送全服消息
	 * 
	 * @param player
	 * @param message
	 */
	public static void sendWolrdMessage(TipMessage tip, byte drwaType) {
		World gameWorld = World.getInstance();
		ConcurrentHashMap<Integer, PlayerCharacter> map = gameWorld
				.getWorldRoleMap();
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			PlayerCharacter player = map.get(it.next());
			if (player.getUserInfo() == null) {
				continue;
			}
			if (drwaType == GameUtils.FLUTTER || drwaType == GameUtils.SCROLL) {
				GameUtils.sendTip(tip, player.getUserInfo(), drwaType);
				logger.info("str:"+tip.getMessage());
				/*
				 * }else if(drwaType==1){ GameUtils.sendScrollTip(tip,
				 * player.getUserInfo());
				 */
			} else if (drwaType == GameUtils.ACTIVE
					|| drwaType == GameUtils.SYSTEM_AWARD) {// 2--活动开启 ，3--系统奖励
				logger.info("str:"+tip.getMessage());
				sendEventOpenFlag(drwaType, player.getUserInfo());
				// GameUtils.sendShout(tip, player.getUserInfo());
			}

		}
	}

	/**
	 * 检测玩家是否合法
	 * 
	 * @param player
	 * @return
	 */
	public static boolean checkPlayer(PlayerCharacter player) {
		if (player == null || player.getId() == 0) {
			return false;
		}
		return true;
	}

	public static String getIntArrayLog(int[] array, String key) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			sb.append(key);
			sb.append("[");
			sb.append(i);
			sb.append("]=");
			sb.append(array[i]);
			sb.append("\n");
		}
		return sb.toString();
	}

	public static String getStringArrayLog(String[] array, String key) {
		StringBuffer sb = new StringBuffer();
		sb.append(key);
		for (int i = 0; i < array.length; i++) {
			sb.append("[");
			sb.append(i);
			sb.append("]=");
			sb.append(array[i]);
			sb.append(" ");
		}
		return sb.toString();
	}

	public static String getListLog(ArrayList<Integer> c) {
		StringBuffer sb = new StringBuffer();

		for (Integer t : c) {
			sb.append(t.toString());
		}
		return sb.toString();
	}

	/**
	 * 查找已经完成的任务的次数
	 * 
	 * @param id
	 * @return
	 */
	public static int getCompletedNum(int id, List<Quest> completedQuests) {
		int num = 0;
		for (Quest q : completedQuests) {
			if (id == q.getId()) {
				num++;
			}
		}
		return num;
	}

	/**
	 * 保存玩家缓存
	 * 
	 * @param player
	 */
	public static void putToCache(PlayerCharacter player) {
		if (player != null) {
			PlayerCache playerCache = player.playerToCache();
			long startTime = TimeUtils.nowLong();
			MongoServer.getInstance().getLogServer().getPlayerCacheDAO().insert(playerCache);
			long endTime = TimeUtils.nowLong();
			logger.info("putToCache 消耗时长：" + (endTime - startTime));
		}
	}
	/**
	 * 获得玩家缓存
	 * @param uid
	 * @return
	 */
	public static PlayerCache getFromCache(long uid){
		return MongoServer.getInstance().getLogServer().getPlayerCacheDAO().findPlayerCacheByUserid((int)uid);
	}

	// public static void putToCacheLevel(String ids,String keys){
	// if(ids != null && !"".equals(ids)){
	// CouchBaseUtil.put(keys,ids,0);
	// }
	// }
	// public static String getFromCache(int uid,String keys){
	// String key = keys+uid;
	// String strLevel = CouchBaseUtil.get(key);
	// logger.info("级别："+uid+"|数据："+strLevel);
	// return strLevel;
	// }
	// public static String[] getFromCache(String[] uids){
	// List<String> keys=new ArrayList<String>();
	// for(int i=0;i<uids.length;i++){
	// String key = "role_"+uids[i];
	// keys.add(key);
	// }
	// Map<String, Object> map=CouchBaseUtil.get(keys);
	// if(map==null){
	// return new String[0];
	// }
	// String[] values=new String[map.size()];
	// int i=0;
	// Iterator<String> it=map.keySet().iterator();
	// while(it.hasNext()){
	// String key=it.next();
	// values[i]=map.get(key).toString();
	// i++;
	// }
	// return values;
	// }

	/**
	 * 发送事件开启标志
	 * 
	 * @param eventType
	 *            0和1 被系统奖励使用此方法中不需要传 2--活动开启 ，3--系统奖励
	 * @param userInfo
	 */
	public static void sendEventOpenFlag(byte eventType, UserInfo userInfo) {
		SystemResp sysResp = new SystemResp();
		sysResp.setUserInfo(userInfo);
		sysResp.setDrawType(eventType);// 设置消息类型
		sysResp.setResult(GameConst.GAME_RESP_SUCCESS);
		sysResp.setErrorStr("");
		sysResp.setP1(eventType);
		JoyServiceApp.getInstance().sendMessage(sysResp);
	}

	/**
	 * 全服发送事件开启标志
	 * 
	 * @param eventType
	 *            0和1 被系统奖励使用此方法中不需要传 2--活动开启 ，3--系统奖励
	 */
	/*
	 * public static void sendEventOpenFlagToAll(byte eventType){ World
	 * gameWorld=World.getInstance(); ConcurrentHashMap<Integer,
	 * PlayerCharacter> map = gameWorld .getWorldRoleMap(); Iterator<Integer>
	 * it=map.keySet().iterator(); while(it.hasNext()){ PlayerCharacter player
	 * =map.get(it.next()); if(player.getUserInfo()!=null){
	 * sendEventOpenFlag(eventType,player.getUserInfo()); } } }
	 */

	/**
	 * 获取英雄字符串
	 * 
	 * @param hero
	 * @return
	 */
	public static String heroInfo(PlayerHero hero) {
		if (hero == null) {
			return "";
		} else {
			return hero.getId() + "#" + hero.getUserId() + "#" + hero.getSex()
					+ "#" + hero.getLevel() + "#" + hero.getWeapon() + "#"
					+ hero.getArmour() + "#" + hero.getHelmet() + "#"
					+ hero.getHorse();
		}
	}

	public static void httpGet(String url) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		HttpResponse res = httpclient.execute(httpGet);

		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST either fully consume the response content or abort
		// request
		// execution by calling HttpGet#releaseConnection().
		JSONObject json = null;
		HttpEntity entity = res.getEntity();
		try {
			System.out.println(res.getStatusLine());
			// 如果接受成功
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				json = new JSONObject(new JSONTokener(new InputStreamReader(
						entity.getContent(), HTTP.UTF_8)));
				//获得服务器列表
				JSONArray jsons = json.getJSONArray("D");
				for (int i = 0; i < jsons.length(); i++) {
					JSONObject j = jsons.getJSONObject(i);
					System.out.println("id=" + j.getInt("Id"));
					System.out.println("name=" + j.getString("Name"));
					System.out.println("players=" + j.getInt("Players"));
					System.out.println("type=" + j.getInt("Type"));
					System.out.println("status=" + j.getInt("Status"));
				}
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

	}
	public static void httpPos(String url) throws Exception{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		  HttpPost httpPost = new HttpPost(url);
	        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
	        nvps.add(new BasicNameValuePair("username", "vip"));
	        nvps.add(new BasicNameValuePair("password", "seret"));
	        nvps.add(new BasicNameValuePair("type","1"));
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
	        HttpResponse res = httpclient.execute(httpPost);
	        JSONObject json = null;
	        HttpEntity entity= res.getEntity();
	        try {
	            System.out.println(res.getStatusLine());
	            // do something useful with the response body
	            // and ensure it is fully consumed
//	            EntityUtils.consume(entity);
	        	// 如果接受成功
				if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					json = new JSONObject(new JSONTokener(new InputStreamReader(
							entity.getContent(), HTTP.UTF_8)));
//					JSONObject j=json.getJSONObject("D");
					System.out.println(json.toString());
//					JSONArray jsons = json.getJSONArray("D");
//					for (int i = 0; i < jsons.length(); i++) {
//						JSONObject server = jsons.getJSONObject(i);
//						System.out.println("id=" + j.getString("Name"));
//						System.out.println("name=" + server.getString("Name"));
//						System.out.println("players=" + server.getInt("Age"));
//					}
				}
	        } finally {
	        	httpclient.getConnectionManager().shutdown();  
	        }
	}
	public static String getHttpQuest(String key,HttpServletRequest request){
		String time = request.getParameter(key);
		if ("".equals(time)) {
			time = TimeUtils.now().format(TimeUtils.FORMAT);
		}
		return time;
	}
	public static void addMessage(int uid,String time,List<NewUser> list){
		boolean b=false;
		//如果日期相同，则累加id
		for(NewUser nu:list){
			if(TimeUtils.isSameDay(TimeUtils.getTimes(nu.getTime()), TimeUtils.getTimes(time))){
				nu.getSet().add(uid);
				b=true;
				break;
			}
		}
		//否则加入新的对象
		if(!b){
			NewUser user=new NewUser();
			user.setTime(time);
			user.getSet().add(uid);
			list.add(user);
		}
	}
	public static void addMessage(List<Login> list,int uid,String time){
		boolean b = false;
		for (Login login : list) {// 如果已经记录过该日期了，则计数+1
			if (login.getTime().equals(time)) {
				login.add(uid);
				b = true;
				break;
			}
		}
		if (!b) {// 从来没有记录过
			Login login = new Login(time);
			list.add(login);
		}
	}

	/**
	 * 向list中加入sale(每一条记录)，PropSale为每一天的统计数据
	 * 
	 * @param list
	 * @param s
	 */
	public static void addToList(List<PropSale> list, Sale s) {
		long time = TimeUtils.getTimes(s.getTime());
		PropSale ps = isSameDay(list, s);
		if (ps == null) {
			ps = new PropSale();
			ps.setTime(TimeUtils.getTime(s.getTime()).format(TimeUtils.FORMAT));
			list.add(ps);
		}
		ps.add(s);
	}
	public static PropSale isSameDay(List<PropSale> list, Sale s) {
		long time = TimeUtils.getTimes(s.getTime());
		for (PropSale ps : list) {
			long t = TimeUtils.getTimes(ps.getTime());
			if (TimeUtils.isSameDay(time, t)) {
				return ps;
			}
		}
		return null;
	}
}
