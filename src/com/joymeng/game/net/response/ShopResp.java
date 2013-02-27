package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class ShopResp extends JoyResponse {
	byte type;
	byte result;
	int errorCode;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ShopResp() {
		super(ProcotolType.SHOP_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		
		out.put(type);
		System.out.println("nation put type >>>>>>>>>>>>"+type);
		switch (type) {
		case ProcotolType.SHOP_ALL_GOODS:
			break;
		case ProcotolType.SHOP_GOOD_BUY:
			break;
		case ProcotolType.SHOP_LADDER:
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		result=in.get();
		errorCode=in.getInt();
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		type=in.get();
		switch (type) {
		case ProcotolType.SHOP_ALL_GOODS:
			break;
		case ProcotolType.SHOP_GOOD_BUY:
			break;
		case ProcotolType.SHOP_LADDER:
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
	
}
