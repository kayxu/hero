package com.joymeng.game.domain.nation.war;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 用户区域战争数据
 * @author Administrator
 *
 */
public class UserWarData extends ClientModuleBase{
	public static Logger logger = Logger.getLogger(UserWarData.class
			.getName());
	public static final byte CD_TIME = 0;
	public static final byte WAR_INTEGRAL = 1;
	int id;
//	int userId;//用户id
	PlayerCache warCache;
	int nationId;//区域id
	int cdTime;//据点cd时间
	int warIntegral;//战争积分
	
	public UserWarData(PlayerCache player,int nId,int cd,int integral){
		this.warCache = player;
		this.nationId = nId;
		this.cdTime = cd;
		this.warIntegral = integral;
		if(warCache != null){
			PlayerCharacter play = World.getInstance().getPlayer(warCache.getUserid());
			PushSign.sendOne(this, new PlayerCharacter[]{play}, ProcotolType.USER_INFO_RESP);
		}
	}
	
	
	/**
	 * 保存积分  
	 * @param key
	 * @param add
	 * @param issend 是否发送
	 */
	public void saveWarData(byte key,int add,boolean issend){
		switch (key) {
		case CD_TIME:
			setCdTime((int)(TimeUtils.nowLong()/1000) + WarManager.CD_TIME);
			break;
		case WAR_INTEGRAL:
			setWarIntegral(getWarIntegral()+add);
			if(issend){
				ArrayList<UserWarData> lst= WarManager.getInstance().topScore(getNationId(),null);
				if(lst != null && lst.size() > 0){
					if(getWarCache() != null && WarManager.getInstance().top.get(getWarCache().getUserid()) != null ){
						int topUserId = WarManager.getInstance().top.get(getWarCache().getUserid());
//						logger.info("当前州："+getNationId()+"|最高积分者是："+topUserId);
						if(lst.get(0).getWarCache() !=null && topUserId != lst.get(0).getWarCache().getUserid()){
							topUserId = lst.get(0).getWarCache().getUserid();
							PlayerCharacter my = World.getInstance().getOnlineRole(topUserId);
							if(my != null){
								//发送滚动消息
								String msg = I18nGreeting.getInstance().getMessage("war.top", new Object[]{my.getData().getName()});
								Map<Integer,Object> push = WarManager.getInstance().warPush.get(getNationId());
								if(push != null){
									for(Integer i : push.keySet()){
										PlayerCharacter pp = World.getInstance().getOnlineRole(i);
										if(pp != null){
											GameUtils.sendTip(new TipMessage(msg, ProcotolType.USER_INFO_RESP, GameConst.GAME_RESP_SUCCESS), pp.getUserInfo(),GameUtils.SCROLL);
										}else{
											WarManager.getInstance().clearByTypeAndPlayer(getNationId(), i);//退出
										}
									}
								}
							}
						}
					}
				}
			}
			//发送给所有玩家
//			logger.info("=============saveWarData");
			WarManager.getInstance().sendRmsOne(this, getNationId());
//			WarManager.getInstance().sendRmsOne(new StopWar(), getNationId());
			break;
		}
//		PlayerCharacter mys = World.getInstance().getOnlineRole(userId);
//		if(mys != null){
//			//PushSign.sendOne(this, new PlayerCharacter[]{mys}, ProcotolType.USER_INFO_RESP);
//		}
		WarManager.getInstance().savewarData(this);
	}
	
	public UserWarData(){
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNationId() {
		return nationId;
	}
	public void setNationId(int nationId) {
		this.nationId = nationId;
	}
	public int getCdTime() {
		return cdTime;
	}
	public void setCdTime(int cdTime) {
		this.cdTime = cdTime;
	}
	public int getWarIntegral() {
		return warIntegral;
	}
	public void setWarIntegral(int warIntegral) {
		this.warIntegral = warIntegral;
	}

	public PlayerCache getWarCache() {
		return warCache;
	}
	public void setWarCache(PlayerCache warCache) {
		this.warCache = warCache;
	}

	@Override
	public byte getModuleType() {
		return NTC_USER_WAR;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		if(getWarCache() != null){
			out.putInt(getWarCache().getUserid());
		}else{
			out.putInt(0);
		}
		
		out.putInt(getWarIntegral());
		if (getWarCache() != null) {
			out.put(getWarCache().getFaction());
			out.putPrefixedString(getWarCache().getName(), JoyBuffer.STRING_TYPE_SHORT);
		} else {
			out.put((byte)1);
			out.putPrefixedString("", JoyBuffer.STRING_TYPE_SHORT);
		}
//		
		logger.info(">>>UserWarData 区域:"+nationId+"|用户:"+getWarCache()+"|发送成功");
		//out.putInt(getCdTime());
	}
}
