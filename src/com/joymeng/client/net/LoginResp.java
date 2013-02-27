package com.joymeng.client.net;

import com.joymeng.client.Client;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class LoginResp extends JoyResponse {
	byte result;
	int errorCode;
	int userId;

	public LoginResp() {
		super(Client.LOGIN_RESP);
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		
		out.putLong(userId);
		out.put(result);
		out.putInt(errorCode);
//		if(result==Constants.GAME_RESP_SUCCESS){
//			
//		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		userId=(int)in.getLong();
		result=in.get();
		errorCode=in.getInt();
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
