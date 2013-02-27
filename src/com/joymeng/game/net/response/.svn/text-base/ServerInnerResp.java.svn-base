package com.joymeng.game.net.response;


import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyModuleMessage;
import com.joymeng.services.core.message.JoyResponse;

public class ServerInnerResp extends JoyModuleMessage {

	String content = "x";

	public ServerInnerResp() {
		super(ProcotolType.SERVER_INNER_RESP);
	}

	public ServerInnerResp(String content) {
		super(ProcotolType.SERVER_INNER_RESP);
		// this.content = content;
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		
		out.putPrefixedString(content);
		System.out.println("content="+content);
		// out.putPrefixedString(this.content, JoyBuffer.STRING_TYPE_SHORT);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// this.content = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		content=in.getPrefixedString();
		System.out.println("content="+content);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
