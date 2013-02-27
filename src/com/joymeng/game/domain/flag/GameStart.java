package com.joymeng.game.domain.flag;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class GameStart extends ClientModuleBase{
	byte type; //0;开始；1；结束 2:登录界面进入
	String gameMsg;//下发结果
	byte isWin;//
	byte point;
	int dealNum;
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}
	public GameStart(byte st) {
		this.type = st;
	}

	@Override
	public byte getModuleType() {
		return NTC_START_1VS1;
	}
	
	public String getGameMsg() {
		return gameMsg;
	}

	public void setGameMsg(String gameMsg) {
		this.gameMsg = gameMsg;
	}
	
	public byte getIsWin() {
		return isWin;
	}

	public void setIsWin(byte isWin) {
		this.isWin = isWin;
	}
	
	public byte getPoint() {
		return point;
	}

	public void setPoint(byte point) {
		this.point = point;
	}
	

	public int getDealNum() {
		return dealNum;
	}

	public void setDealNum(int dealNum) {
		this.dealNum = dealNum;
	}

	public GameStart(byte st,String msg,byte isw,byte po,int dealNum) {
		this.type = st;
		this.gameMsg = msg;
		this.isWin = isw;
		this.point = po;
		this.dealNum = dealNum;
	}
	
	@Override
	public void _serialize(JoyBuffer out) {
		System.out.println(this.print());
		out.put(type);
		if(gameMsg == null){
			out.putPrefixedString("", (byte)2);
		}else{
			out.putPrefixedString(gameMsg, (byte)2);
		}
		out.put(isWin);
		out.put(point);
	}
}
