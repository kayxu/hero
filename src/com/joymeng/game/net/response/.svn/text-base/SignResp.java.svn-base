package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class SignResp extends JoyResponse{

	byte type;
	byte result;
	int errorCode;
	
	public SignResp() {
		super(ProcotolType.SIGN_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		switch(type){
		case ProcotolType.OPEN_UI:
		case ProcotolType.SIGN_IN:
			break;
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	

}
