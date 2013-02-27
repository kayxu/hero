package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class SkillResp extends JoyResponse {
	byte result;//结果
	int errorCode;//错误代码
	int skillId;
	public SkillResp() {
		super(ProcotolType.SKILL_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.put(result);
		out.putInt(errorCode);
		out.putInt(skillId);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		
		result=in.get();
		errorCode=in.getInt();
		skillId=in.getInt();
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

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}


	
	
}
