package com.joymeng.game.domain.fight.mod;

import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;

public class LadderInfo {
	byte status ;//状态
	int freeNum;//免费次数
	int chargeNum;//付费次数
	long time ;//重置时间
	long buyNum;//购买次数
	public LadderInfo(String str){
		if(str==null||str.equals("")){//初始状态
			this.status=Ladder.STATUS_FREE;
			this.time=TimeUtils.nowLong();
			this.freeNum=2;
			this.chargeNum=0;
			this.buyNum=0;
		}else{
			String[] info=str.split(";");
			this.status=Byte.parseByte(info[0]);
			this.freeNum=Integer.parseInt(info[1]);
			this.chargeNum=Integer.parseInt(info[2]);
			this.time=Long.parseLong(info[3]);
			this.buyNum=Byte.parseByte(info[4]);
		}
	}
	public String toStr(){
		String str[]={String.valueOf(this.status),String.valueOf(this.freeNum),String.valueOf(this.chargeNum),String.valueOf(this.time),String.valueOf(buyNum)};
		return StringUtils.recoverNewStr(str, ";");
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public int getFreeNum() {
		return freeNum;
	}
	public void setFreeNum(int freeNum) {
		this.freeNum = freeNum;
	}
	public int getChargeNum() {
		return chargeNum;
	}
	public void setChargeNum(int chargeNum) {
		this.chargeNum = chargeNum;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(long buyNum) {
		this.buyNum = buyNum;
	}
	
}
