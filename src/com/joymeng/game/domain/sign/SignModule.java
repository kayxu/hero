package com.joymeng.game.domain.sign;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class SignModule extends ClientModuleBase{
	
	private byte index;
	
	/**
	 * 状态 
	 * 1-已签到     2-签到    3-未签到
	 */
	private int status;

	/**
	 * 奖品图标
	 * propsId:0为筹码 -1为金币 -2为功勋，其他数值为道具ID
	 */
	private String icon;
	
	/**
	 * 奖品数量
	 */
	private int value;
	
	private String name;
	

	@Override
	public byte getModuleType() {
		return NTC_SIGN_IN;
	}
	
	@Override
	public void _serialize(JoyBuffer out) {
		out.put(index);
		out.putInt(status);
		out.putPrefixedString(icon, (byte)2);
		out.putInt(value);
		out.putPrefixedString(name,(byte)2);

	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.index = in.get();
		this.status = in.getInt();
		this.icon = in.getPrefixedString();
		this.value = in.getInt();
		this.name = in.getPrefixedString();

	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public byte getIndex() {
		return index;
	}

	public void setIndex(byte index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
