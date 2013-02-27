package com.joymeng.core.base.net.response;

import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class EchoResp extends JoyResponse {
	public static final int ECHO_RESP = 0x0B;

	public EchoResp() {
		super(ECHO_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// doto
		System.out.println("send echo");
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		System.out.println("receive echo");
	}

}
