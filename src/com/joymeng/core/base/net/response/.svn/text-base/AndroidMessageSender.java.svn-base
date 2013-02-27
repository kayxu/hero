package com.joymeng.core.base.net.response;

import com.joymeng.core.log.GameLog;
import com.joymeng.game.GameServerApp;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;
import com.joymeng.services.core.message.JoyResponse;
/**
 * 发送moduleSet方法
 * @author admin
 * @date 2012-5-17
 * TODO
 */
public class AndroidMessageSender 
{
	public static boolean direct_connect=false;

	/**
	 * 该方法
	 * 包括封装了斯凯层的头，以及发送包
	 * @param moduleSet
	 */
	public static void sendMessage(RespModuleSet moduleSet, PlayerCharacter player)
	{
//		if (GameServerApp.FREEZE)
//		{
//			return ;
//		}
		UserInfo connInfo = player.getUserInfo();
		if (connInfo == null) 
		{
			return ;
		}
		if (player.getState() == GameConst.STATE_OFFLINE)
		{
			System.out.println("send module error,id="+player.getId()+" status="+player.getState());

			return ;
		}
		//真正的斯凯封装下行包	
		moduleSet.setUserInfo(connInfo);//设置连接对象，如果不设，上层框架默认会设置成req.getUserInfo()，这里需要设置
		try
		{
			//System.out.println("userInfo="+connInfo.getCid()+","+connInfo.getEid()+","+connInfo.getUid()+" size="+moduleSet.moduleList.size());
			//System.out.println("commandId:"+moduleSet.commandId);
			GameServerApp.send.incrementAndGet();
			JoyServiceApp.getInstance().sendMessage(moduleSet);
		}
		catch (Exception e)
		{
			GameLog.error("send msg error ...",e);
		}
	}
	
}
