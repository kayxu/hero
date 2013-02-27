package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class MissionResp extends JoyResponse {
	byte type;
	public MissionResp() {
		super(ProcotolType.MISSION_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
	}

}
