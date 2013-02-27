package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class SignRequest extends JoyRequest{

	byte type;
	
	public SignRequest() {
		super(ProcotolType.SIGN_REQ);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch(type){
		case ProcotolType.OPEN_UI:
		case ProcotolType.SIGN_IN://签到
			break;
		}
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch (type) {
		case ProcotolType.OPEN_UI:
		case ProcotolType.SIGN_IN://签到
			break;
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	
	

}
