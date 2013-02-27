package com.joymeng.core.base.net.request;

import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class EchoRequest extends JoyRequest {
	public static final int ECHO_REQ = 0x0A;
	String content;

	public EchoRequest() {
		super(ECHO_REQ);
	}

	public EchoRequest(String content) {
		super(ECHO_REQ);
		this.content = content;
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.putPrefixedString(this.content,(byte)2);
		System.out.println("send echo");
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		this.content = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		System.out.println("receive echo");
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
