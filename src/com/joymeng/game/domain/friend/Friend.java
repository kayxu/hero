package com.joymeng.game.domain.friend;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipModule;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Friend extends  ClientModuleBase implements TipModule{//朋友
	int id;
	int myId;//我的id
	int friendId;//朋友的id
	byte type;// 1：在线    2：不在线
	long addTime;//添加时间
	byte isdel = 0;//0 添加 1，删除
	
	private TipMessage tip;
	/**
	 * @return GET the addTime
	 */
	public long getAddTime() {
		return addTime;
	}
	/**
	 * @param SET addTime the addTime to set
	 */
	public void setAddTime(long addTime) {
		this.addTime = addTime;
	}
	/**
	 * @return GET the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param SET id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return GET the myId
	 */
	public int getMyId() {
		return myId;
	}
	/**
	 * @param SET myId the myId to set
	 */
	public void setMyId(int myId) {
		this.myId = myId;
	}
	/**
	 * @return GET the friendId
	 */
	public int getFriendId() {
		return friendId;
	}
	/**
	 * @param SET friendId the friendId to set
	 */
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	/**
	 * @return GET the type
	 */
	public byte getType() {
		return type;
	}
	/**
	 * @param SET type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}
	public byte getIsdel() {
		return isdel;
	}
	public void setIsdel(byte isdel) {
		this.isdel = isdel;
	}
	@Override
	public byte getModuleType() {
		return NTC_FRIEND;
	}
	@Override
	public void _serialize(JoyBuffer out) {
//		String ss = GameUtils.getFromCache(getFriendId(),"role_");
		PlayerCache ss  = GameUtils.getFromCache(getFriendId());
		if(ss != null ){
			out.putInt(friendId);
			out.putShort(ss.getLevel());
			out.putPrefixedString(ss.getName(),(byte)2);
			out.put(ss.getFaction());
			out.put((byte) ss.getCityLevel());
			out.put(type);
			out.put(isdel);
		}else{
//			RoleData data = World.getInstance().getRole(getFriendId()).getData();
			out.putInt(0);
			out.putShort((short) 0);
			out.putPrefixedString("",(byte)2);
			out.put((byte) 0);
			out.put((byte) 0);
			out.put((byte) 0);
			out.put((byte) 0);
		}
//		out.put(getType());
		
	}
	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public TipMessage getTip() {
		return this.tip;
	}
	@Override
	public void setTip(TipMessage tip) {
		this.tip = tip;
	}
	
	public void setTips(String msg,byte isSucc){
		setTip(new TipMessage(msg, ProcotolType.FRIEND_RESP, isSucc));
	}
	
}
