package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class MissionRequest extends JoyRequest {

	byte type;
	int id;
	public MissionRequest() {
		super(ProcotolType.MISSION_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type=in.get();
		switch(type){
		case 0:
			break;
		case 1:
		case 2:
		case 3:
			id=in.getInt();
			break;
		}
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
