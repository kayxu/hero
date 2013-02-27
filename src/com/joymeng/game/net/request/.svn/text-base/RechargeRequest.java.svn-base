package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class RechargeRequest extends JoyRequest {

	byte type;
	int rechargeVal;
	
	public RechargeRequest() {
		super(ProcotolType.RECHARGE_REQ);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch(type){
		case ProcotolType.DO_RECHARGE://充值
			rechargeVal = in.getInt();
			break;
		case ProcotolType.GET_RECHARGE_AWARD://领取充值奖励
			break;
		}
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch(type){
		case ProcotolType.DO_RECHARGE:
			out.putInt(rechargeVal);
			break;
		case ProcotolType.GET_RECHARGE_AWARD:
			break;
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getRechargeVal() {
		return rechargeVal;
	}

	public void setRechargeVal(int rechargeVal) {
		this.rechargeVal = rechargeVal;
	}
	
	

}
