package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class SkillRequest extends JoyRequest {
	int itemId1;
	int itemId2;
	
	public SkillRequest() {
		super(ProcotolType.SKILL_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.putInt(itemId1);
		out.putInt(itemId2);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// todo
		itemId1=in.getInt();
		itemId2=in.getInt();
	}

	public int getItemId1() {
		return itemId1;
	}

	public void setItemId1(int itemId1) {
		this.itemId1 = itemId1;
	}

	public int getItemId2() {
		return itemId2;
	}

	public void setItemId2(int itemId2) {
		this.itemId2 = itemId2;
	}
	
}
