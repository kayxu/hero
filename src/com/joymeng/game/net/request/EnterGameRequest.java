package com.joymeng.game.net.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class EnterGameRequest extends JoyRequest {
	int uid;
	byte type;
	static Logger logger = LoggerFactory.getLogger(EnterGameRequest.class);

	public EnterGameRequest() {
		super(ProcotolType.ENTER_GAME_REQ);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		uid = in.getInt();
		type = in.get();
		// logger.info("receive uid=="+uid+" login in");
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.putInt(uid);
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	

}
