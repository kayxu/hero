package com.joymeng.game.domain.flag;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Road extends ClientModuleBase{
	byte point;

	public byte getPoint() {
		return point;
	}

	public void setPoint(byte point) {
		this.point = point;
	}
	
	public Road(){
		
	}
	public Road(byte point){
		this.point = point;
	}
	
	@Override
	public byte getModuleType() {
		return NTC_ROAD;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.put(point);
	}

}
