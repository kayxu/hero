package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class FriendResp extends JoyResponse {
	byte result;
	int errorCode;
	byte type;
	public FriendResp() {
		super(ProcotolType.FRIEND_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		System.out.println("Friend resp type >>>>>>"+ type);
		switch (type) {
		case ProcotolType.FRIEND_ALL:// 获取用户背包数据
			break;
		case ProcotolType.ENEMY_ALL:// 用户背包装备数据
			break;
		case ProcotolType.CITY_PLAYER:// 用户背包装备数据
			break;
		case ProcotolType.ADD_FRIEND:// 用户背包装备数据
			break;
		case ProcotolType.DEL_FRIEND:
			break;
	}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
	}

	/**
	 * @return GET the result
	 */
	public byte getResult() {
		return result;
	}

	/**
	 * @param SET result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
	}

	/**
	 * @return GET the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param SET errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
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
	
}
