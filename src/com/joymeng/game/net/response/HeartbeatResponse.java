package com.joymeng.game.net.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class HeartbeatResponse extends JoyResponse {

	static final Logger logger = LoggerFactory
			.getLogger(HeartbeatResponse.class);

	public HeartbeatResponse() {
		super(ProcotolType.HEART_BEAT_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void _serialize(JoyBuffer arg0) {
		// TODO Auto-generated method stub
//		logger.info("heart response !!!");
//		System.out.println("heart response   !!!");
	}
}
