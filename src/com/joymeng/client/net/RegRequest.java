package com.joymeng.client.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class RegRequest extends JoyRequest {
	static Logger logger = LoggerFactory.getLogger(RegRequest.class);
	String name;
	String pwd;
	public RegRequest() {
		super(ProcotolType.REG_REQ);
	}


	@Override
	protected void _deserialize(JoyBuffer in) {
		logger.info("receive regist...");
		name=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		pwd=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.putPrefixedString(name,(byte)2);
		out.putPrefixedString(pwd,(byte)2);
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


}
