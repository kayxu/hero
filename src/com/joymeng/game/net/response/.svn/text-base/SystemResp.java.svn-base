package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class SystemResp extends JoyResponse {
	byte drawType;//0,提示 1,滚动 2,喊话
	int type;//类型
	byte result;//结果
//	int errorCode;//错误代码
	String errorStr;
	int p1;//参数1
	int p2;//参数2
	public SystemResp() {
		super(ProcotolType.SYSTEM_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.put(drawType);
		out.putInt(type);
		out.put(result);
//		out.putInt(errorCode);
		out.putPrefixedString(errorStr,JoyBuffer.STRING_TYPE_SHORT);
		out.putInt(p1);
		out.putInt(p2);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		drawType=in.get();
		type=in.getInt();
		result=in.get();
//		errorCode=in.getInt();
		errorStr=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		p1=in.getInt();
		p2=in.getInt();
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

//	public int getErrorCode() {
//		return errorCode;
//	}
//
//	public void setErrorCode(int errorCode) {
//		this.errorCode = errorCode;
//	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getErrorStr() {
		return errorStr;
	}

	public void setErrorStr(String errorStr) {
		this.errorStr = errorStr;
	}


	public byte getDrawType() {
		return drawType;
	}

	public void setDrawType(byte drawType) {
		this.drawType = drawType;
	}

	public int getP1() {
		return p1;
	}

	public void setP1(int p1) {
		this.p1 = p1;
	}

	public int getP2() {
		return p2;
	}

	public void setP2(int p2) {
		this.p2 = p2;
	}
    
	
	
}
