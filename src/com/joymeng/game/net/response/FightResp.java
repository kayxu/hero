package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class FightResp extends JoyResponse {
//	int num;//指令数量
//	byte type;//类型
	byte data[];//数据
//	byte round;//战斗回合数
	public FightResp() {
		super(ProcotolType.FIGHT_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
//		out.put(type);
//		out.putInt(num);
//		out.put(round);
		out.put(data);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
	
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	
//	public int getNum() {
//		return num;
//	}
//
//	public void setNum(int num) {
//		this.num = num;
//	}

//	public byte getType() {
//		return type;
//	}
//
//	public void setType(byte type) {
//		this.type = type;
//	}

//	public byte getRound() {
//		return round;
//	}
//
//	public void setRound(byte round) {
//		this.round = round;
//	}

}
