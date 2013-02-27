package com.joymeng.game.net.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.message.JoyResponse;

public class EnterGameResp extends JoyResponse {
	static Logger logger = LoggerFactory.getLogger(EnterGameResp.class);
	byte result;
	int errorCode;
	int sysTime;//系统时间
	int recommendCounty;//推荐国家
	public EnterGameResp() {
		super(ProcotolType.ENTER_GAME_RESP);
		super.setDestInstanceID(JoyProtocol.MODULE_SYS);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		result=in.get();
		errorCode=in.getInt();
		sysTime = in.getInt();
		recommendCounty = in.getInt();
		
//		logger.info("result="+result+" errorCode="+errorCode+" sysTime="+sysTime);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// TODO Auto-generated method stub
		out.put(result);
		out.putInt(errorCode);
		out.putInt(sysTime);
		out.putInt(recommendCounty);
//		logger.info("result="+result+" errorCode="+errorCode+" sysTime="+sysTime);
	}

	
	/**
	 * @return GET the sysTime
	 */
	public int getSysTime() {
		return sysTime;
	}

	/**
	 * @param SET sysTime the sysTime to set
	 */
	public void setSysTime(int sysTime) {
		this.sysTime = sysTime;
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

	public int getRecommendCounty() {
		return recommendCounty;
	}

	public void setRecommendCounty(int recommendCounty) {
		this.recommendCounty = recommendCounty;
	}
	
}
