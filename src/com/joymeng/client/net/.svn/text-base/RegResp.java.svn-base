package com.joymeng.client.net;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.message.JoyResponse;

public class RegResp extends JoyResponse {

	byte result;
	int errorCode;
	int userId;

	public RegResp() {
		super(ProcotolType.REG_RESP);
		super.setDestInstanceID(JoyProtocol.MODULE_SYS);
		super.setUnloginedFlag();
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==1){
			out.putInt(userId);
		}
		
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		result=in.get();
		errorCode=in.getInt();
		if(result==1){
			userId=in.getInt();
		}
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
