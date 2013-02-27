package com.joymeng.client.net;

import com.joymeng.client.Client;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class LoginRequest extends JoyRequest {
	String name;
	String pwd;
	byte type;//type==0 (试玩)-type==1(正常登录),
	public LoginRequest() {
		super(Client.LOGIN_REQ);
	}


	@Override
	protected void _deserialize(JoyBuffer in) {
		name = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		pwd = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		type=in.get();
	}
	@Override
	protected void _serialize(JoyBuffer out) {
		out.putPrefixedString(name,(byte)2);
		out.putPrefixedString(pwd,(byte)2);
		out.put(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public byte getType() {
		return type;
	}


	public void setType(byte type) {
		this.type = type;
	}

}
