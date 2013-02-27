package com.joymeng.game.domain.sound;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class SoundVoice extends ClientModuleBase{
	byte type;// 1:升级  
	int level;//等级
	
	public static SoundVoice instance;
	
	public static SoundVoice getInstance(){
		if(instance == null){
			instance = new SoundVoice();
		}
		return instance;
	}
	
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public byte getModuleType() {
		return NTC_SOUND;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.put(type);
		out.putInt(level);
	}

	
}
