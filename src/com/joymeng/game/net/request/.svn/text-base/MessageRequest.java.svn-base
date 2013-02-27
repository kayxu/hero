package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class MessageRequest extends JoyRequest {
	byte type;
	public MessageRequest() {
		super(ProcotolType.MESSAGE_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// todo
		type=in.get();
	}

}
