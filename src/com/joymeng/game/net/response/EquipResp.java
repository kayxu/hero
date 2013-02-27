package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class EquipResp extends JoyResponse {

	public EquipResp() {
		super(ProcotolType.ENTER_GAME_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
	}

}
