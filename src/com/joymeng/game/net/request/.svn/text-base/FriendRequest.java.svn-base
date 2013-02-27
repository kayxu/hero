package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class FriendRequest extends JoyRequest {
	
	private static final long serialVersionUID = 1L;
	byte type;//请求类型
	int friendId;
	byte backType;//回复类型

	public FriendRequest() {
		super(ProcotolType.FRIEND_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		System.out.println("send "+FriendRequest.class.getName());
		out.put(type);
		System.out.println("************send Friend type ：" + type);
		switch (type) {
			case ProcotolType.FRIEND_ALL:
				break;
			case ProcotolType.ENEMY_ALL:
				break;
			case ProcotolType.CITY_PLAYER:
				break;
			case ProcotolType.ADD_FRIEND:
				out.putInt(friendId);
				break;
			case ProcotolType.DEL_FRIEND:
				out.putInt(friendId);
				break;
			case ProcotolType.ONLINE_ALL:
				out.put(backType);
				break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		System.out.println("************ Friend type：" + type);
		switch (type) {
			case ProcotolType.FRIEND_ALL:
				break;
			case ProcotolType.ENEMY_ALL:
				break;
			case ProcotolType.CITY_PLAYER:
				break;
			case ProcotolType.ADD_FRIEND:
				friendId = in.getInt();
				break;
			case ProcotolType.DEL_FRIEND:
				friendId = in.getInt();
				break;
			case ProcotolType.ONLINE_ALL:
				backType = in.get();
				break;
		}
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

	/**
	 * @return GET the friendId
	 */
	public int getFriendId() {
		return friendId;
	}
	
	/**
	 * @return GET the backType
	 */
	public byte getBackType() {
		return backType;
	}

	/**
	 * @param SET backType the backType to set
	 */
	public void setBackType(byte backType) {
		this.backType = backType;
	}

	/**
	 * @param SET friendId the friendId to set
	 */
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	
}
