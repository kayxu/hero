package com.joymeng.game.net.response;

import java.util.List;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class RechargeResp extends JoyResponse{

	byte type;
	byte result;
	int errorCode;
	
	byte actionType;// 当前状态（0表示奖励已经领取完； 1表示还有奖励待领取）
	int prevCharge; // 当前已充值
	int nextCharge; // 再充值数目
	int rewardValue;// 礼包价值
	String hornorName; // 荣誉称谓
	String rewardsIds;// 奖励物品的ID


	public RechargeResp() {
		super(ProcotolType.RECHARGE_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		switch(type){
		case ProcotolType.DO_RECHARGE://充值
		case ProcotolType.GET_RECHARGE_AWARD://领奖
			out.put(actionType);
			out.putInt(prevCharge);
			out.putInt(nextCharge);
			out.putInt(rewardValue);
			out.putPrefixedString(hornorName,(byte)2);
			out.putPrefixedString(rewardsIds,(byte)2);
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getActionType() {
		return actionType;
	}

	public void setActionType(byte actionType) {
		this.actionType = actionType;
	}

	public int getPrevCharge() {
		return prevCharge;
	}

	public void setPrevCharge(int prevCharge) {
		this.prevCharge = prevCharge;
	}

	public int getNextCharge() {
		return nextCharge;
	}

	public void setNextCharge(int nextCharge) {
		this.nextCharge = nextCharge;
	}

	public int getRewardValue() {
		return rewardValue;
	}

	public void setRewardValue(int rewardValue) {
		this.rewardValue = rewardValue;
	}

	public String getHornorName() {
		return hornorName;
	}

	public void setHornorName(String hornorName) {
		this.hornorName = hornorName;
	}

	public String getRewardsIds() {
		return rewardsIds;
	}

	public void setRewardsIds(String rewardsIds) {
		this.rewardsIds = rewardsIds;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	
	
	
	
	
	

}
