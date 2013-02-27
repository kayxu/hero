package com.joymeng.game.domain.fight;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class FightMessage extends ClientModuleBase{
	String content[]=null;
	public FightMessage(FightEvent fe){
		String str=fe.getMemo();
		
		content=str.split(";");
	}
	@Override
	public byte getModuleType() {
		return NTC_FIGHTMESSAGE;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putPrefixedString(this.content[0], JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(this.content[1], JoyBuffer.STRING_TYPE_SHORT);
	}
}
