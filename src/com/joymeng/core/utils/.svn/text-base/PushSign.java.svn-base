package com.joymeng.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;

public abstract class PushSign {
	static final Logger logger = LoggerFactory.getLogger(PushSign.class);
	//得到对应的类型map
	public abstract Map<Integer,Map<Integer,Object>> getPushTypeMap();
	
	//得到对应的用户map
	public  Map<Integer,Object> getPushPlayerMap(Integer pushType){
		Map<Integer,Map<Integer,Object>> allPush = getPushTypeMap();
		//logger.info("pushType="+pushType+"|allPush="+allPush);
		if(allPush != null){
			Map<Integer,Object> push = allPush.get(pushType);
			//logger.info("pushType="+pushType+"|push="+push);
			return push;
		}else{
			return null;
		}
	}
	/**
	 * 添加推送用户列表
	 * @param pushType
	 * @param playerid
	 */
	public void addPushMap(Integer pushType,Integer playerid){
		Map<Integer,Map<Integer,Object>> AllPush = getPushTypeMap();
		if(AllPush != null){
			Map<Integer,Object> push = AllPush.get(pushType);
			if(push == null){
				push = new HashMap<Integer,Object>();
				push.put(playerid, null);
				AllPush.put(pushType, push);//
				logger.info("pushType:"+pushType+"添加："+push.size());
			}else{
				push.put(playerid, null);
				logger.info("pushType:"+pushType+"添加："+push.size());
			}
		}
		
	}
	/**
	 * 删除对应类型 列表
	 * @param pushType
	 */
	public void clearByPushType(Integer pushType){
		Map<Integer,Map<Integer,Object>> pushTypeData = getPushTypeMap();
		if(pushTypeData == null){
			
		}else{
			pushTypeData.remove(pushType);
		}
	}
	/**
	 * 清除所有的推送列表
	 * @param pushType
	 */
	public void clearAllPushType(Integer pushType){
		Map<Integer,Object> push = getPushPlayerMap(pushType);
		if(push != null){
			push.clear();
		}
	}
	
	/**
	 * 清空全部
	 */
	public void clearAll(){
		Map<Integer,Map<Integer,Object>> push =  getPushTypeMap();
		if(push != null){
			push.clear();
		}
	}

	/**
	 * 清空某类型的 某个玩家
	 * @param pushType
	 * @param playerid
	 */
	public void clearByTypeAndPlayer(Integer pushType,Integer playerid){
		Map<Integer,Object> push = getPushPlayerMap(pushType);
		if(push != null){
			push.remove(playerid);
			logger.info("pushType:"+pushType+"|删除："+push.size()+"|剩余："+push.size());
		}
	}
	
	/**
	 * 清空莫格类型一组数据
	 * @param pushType
	 * @param playerLst
	 */
	public void clearByTypeAndPlayerList(Integer pushType,List<Integer> playerLst){
		Map<Integer,Object> push = getPushPlayerMap(pushType);
		for(Integer id : playerLst){
			push.remove(id);
			logger.info("pushType:"+pushType+"|删除："+push.size()+"|剩余："+push.size());
		}
	}
	
	/**
	 * 发送一个数据
	 * @param clientModuleBase
	 * @param pushMap
	 */
	public void sendRmsOne(ClientModuleBase clientModuleBase,Integer pushType){
		RespModuleSet rms = new RespModuleSet(ProcotolType.USER_INFO_RESP);
		Map<Integer,Object> pushMap =  getPushPlayerMap(pushType);
		List<Integer> playerLst = new ArrayList<Integer>();
		rms.addModule(clientModuleBase);
		if(pushMap != null){
			logger.info("pushType:"+pushType+"|推送数量："+pushMap.size());
			for(Integer playerid : pushMap.keySet()){
				PlayerCharacter playerOnline = World.getInstance().getOnlineRole(playerid);
				if(playerOnline != null){
					//logger.info("时间："+TimeUtils.nowLong()+"|userid="+playerid);
					AndroidMessageSender.sendMessage(rms, playerOnline);
				} else{
					playerLst.add(playerid);//(pushType, playerid);
				}
			}
			logger.info("pushType:"+pushType+"|推送完成");
			//清除数据
			//clearByTypeAndPlayerList(pushType, playerLst);
		}
	}
	
	/**
	 * 发送一个数据
	 * @param clientModuleBase
	 * @param pushMap
	 */
	public void sendRmsList(List<ClientModuleBase> clientModuleBaseLst,Integer pushType){
		RespModuleSet rms = new RespModuleSet(ProcotolType.USER_INFO_RESP);
		Map<Integer,Object> pushMap =  getPushPlayerMap(pushType);
		List<Integer> playerLst = new ArrayList<Integer>();
		rms.addModuleBase(clientModuleBaseLst);
		if(pushMap != null){
			logger.info("pushType:"+pushType+"|推送数量："+pushMap.size());
			for(Integer playerid : pushMap.keySet()){
				PlayerCharacter playerOnline = World.getInstance().getOnlineRole(playerid);
				if(playerOnline != null){
					//logger.info("时间："+TimeUtils.nowLong());
					AndroidMessageSender.sendMessage(rms, playerOnline);
				} else{
					playerLst.add(playerid);
				}
			}
			logger.info("pushType:"+pushType+"|推送完成");
			//清除数据
			//clearByTypeAndPlayerList(pushType, playerLst);
		}
	}
	
	/**
	 * 发送消息
	 * 
	 * @param base
	 * @param ps
	 */
	public static void sendOne(ClientModuleBase base, PlayerCharacter[] ps,int type) {
		RespModuleSet rms = new RespModuleSet(type);// 模块消息
		rms.addModule(base);
		for (int i = 0; i < ps.length; i++) {
			if (null != ps[i]) {
				AndroidMessageSender.sendMessage(rms, ps[i]);
			} else {
				//logger.info("sendOne user is null");
				return;
			}

		}
		//logger.info("sendOne:" + base);
	}

	/**
	 * 发送消息
	 * 
	 * @param base
	 * @param ps
	 */
	public static void sendAll(List<ClientModuleBase> lst, PlayerCharacter[] ps,int type) {
		RespModuleSet rms = new RespModuleSet(type);// 模块消息
		rms.addModuleBase(lst);
		for (int i = 0; i < ps.length; i++) {
			if (null != ps[i]) {
				//logger.info("时间："+TimeUtils.nowLong());
				AndroidMessageSender.sendMessage(rms, ps[i]);
			} else {
				//logger.info("sendAll user is null");
				return;
			}
		}
		//logger.info("sendAll:" + lst.size());
		return;
	}

}
