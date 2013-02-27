package com.joymeng.game.domain.time;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameConfig;
import com.joymeng.services.core.buffer.JoyBuffer;

public class SysTime extends ClientModuleBase {

	@Override
	public byte getModuleType() {
		return NTC_SYS_TIME;
	}

	@Override
	public void _serialize(JoyBuffer out) {
//		int index1=out.position();
		out.putInt((int)(TimeUtils.nowLong()/1000));
//		int index2=out.position();
//		return index2-index1;		
		System.out.println("time="+TimeUtils.getTime(GameConfig.arenaUpdate));
		out.putInt((int)(GameConfig.arenaUpdate/1000));
	}

	@Override
	public void deserialize(JoyBuffer in) {

	}

}
