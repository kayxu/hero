package com.joymeng.game.domain.item;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class PropsDelay extends ClientModuleBase{//延时道具类型
	int id;//id
	int propsId;//道具id
	byte propsType;//道具类型
	int endTime;//结束时间
	int userId;//用户id
	int additionCount;//加成数量
	
	
	public int getAdditionCount() {
		return additionCount;
	}
	public void setAdditionCount(int additionCount) {
		this.additionCount = additionCount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPropsId() {
		return propsId;
	}
	public void setPropsId(int propsId) {
		this.propsId = propsId;
	}
	public byte getPropsType() {
		return propsType;
	}
	public void setPropsType(byte propsType) {
		this.propsType = propsType;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	@Override
	public byte getModuleType() {
		return RANK_PROPS_DELAY;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(getPropsId());
		out.put(getPropsType());
		out.putInt(getEndTime());
	}
}
