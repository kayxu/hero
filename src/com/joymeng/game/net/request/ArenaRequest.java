package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class ArenaRequest extends JoyRequest {
	byte type;

	public ArenaRequest() {
		super(ProcotolType.ARENA_REQ);
	}
	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// todo
		type=in.get();
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	
}
