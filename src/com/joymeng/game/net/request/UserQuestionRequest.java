package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class UserQuestionRequest extends JoyRequest{

	byte type;
	
	String content;
	
	public UserQuestionRequest() {
		super(ProcotolType.USER_QUESTION_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch (type) {
		case ProcotolType.POST_QUESTION://提交问题
			out.putPrefixedString(content, JoyBuffer.STRING_TYPE_SHORT);
			break;
		}
		
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch(type){
		case ProcotolType.POST_QUESTION://提交问题
			content = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
			break;
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
