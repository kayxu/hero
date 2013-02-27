package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class ExitGameRequest extends JoyRequest {


	public ExitGameRequest() {
		super(ProcotolType.EXITGAME_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// todo
	}

}
