package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class ChatRequest extends JoyRequest {
	byte type;
	String message;
	int receiveId;

	public ChatRequest() {
		super(ProcotolType.CHAT_REQ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		type = in.get();
		message = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		receiveId = in.getInt();
	}

	@Override
	protected void _serialize(JoyBuffer arg0) {
		// TODO Auto-generated method stub

	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}

}
