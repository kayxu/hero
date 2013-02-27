package com.joymeng.game.domain.quest;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyProtocol;

public class AcceptedQuest extends  ClientModuleBase{
	private Quest q;
	private byte status;
	private String info;
	public AcceptedQuest(Quest _q,byte _status){
		this.q=_q;
		this.status=_status;
		this.info="";
	}
	@Override
	public byte getModuleType() {
		return NTC_QUEST;
	}
	@Override
	public final void _serialize(JoyBuffer out) {
		out.putInt(q.getId());
		out.put(status);
		out.putPrefixedString(info,JoyBuffer.STRING_TYPE_SHORT);
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Quest getQ() {
		return q;
	}
	public void setQ(Quest q) {
		this.q = q;
	}
	
}
