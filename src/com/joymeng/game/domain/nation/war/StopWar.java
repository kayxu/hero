package com.joymeng.game.domain.nation.war;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class StopWar extends ClientModuleBase{

	String pusgMsg;//
	byte isStop;
	int eventId;
	
	public byte getIsStop() {
		return isStop;
	}
	public void setIsStop(byte isStop) {
		this.isStop = isStop;
	}
	public String getPusgMsg() {
		return pusgMsg;
	}
	public void setPusgMsg(String pusgMsg) {
		this.pusgMsg = pusgMsg;
	}
	@Override
	public byte getModuleType() {
		return NTC_WAR_STOP;//51
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.put(isStop);
		out.putPrefixedString(getPusgMsg(), (byte)2);
		out.putInt(eventId);
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
}
