package com.joymeng.game.domain.fight.mod;

import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;

public class CampInfo {
	int id;//关卡id
	long time;//时间
	int freeNum;//可免费次数
	int chargeNum;//可付费次数
	public CampInfo(String str){
		if(str==null||str.equals("")){
			this.id=-1;
			this.time=TimeUtils.nowLong();
			this.freeNum=2;
			this.chargeNum=1;
		}else{
			String[] info=str.split(";");
			this.id=Integer.parseInt(info[0]);
			this.time=Long.parseLong(info[1]);
			this.freeNum=Integer.parseInt(info[2]);
			this.chargeNum=Integer.parseInt(info[3]);
		
		}
	}
	public String toStr(){
		String str[]={String.valueOf(this.id),String.valueOf(this.time),String.valueOf(this.freeNum),String.valueOf(this.chargeNum)};
		return StringUtils.recoverNewStr(str, ";");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
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
	
}
