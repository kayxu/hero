package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyModuleMessage;

public class ServerInnerRequest extends JoyModuleMessage {

	private int onlineNum;
	private byte type;
	public ServerInnerRequest() {
		super(ProcotolType.SERVER_INNER_REQ);
	}

	public ServerInnerRequest(int  onlineNum,byte type) {
		super(ProcotolType.SERVER_INNER_REQ);
		this.onlineNum = onlineNum;
		this.type=type;
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.putInt(this.onlineNum);
		out.put(type);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		this.onlineNum = in.getInt();
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(int onlineNum) {
		this.onlineNum = onlineNum;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}


}
