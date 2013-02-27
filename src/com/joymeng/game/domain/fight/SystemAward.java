package com.joymeng.game.domain.fight;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 系统奖励实体类
 * @author madi
 *
 */
public class SystemAward extends ClientModuleBase{

	private int id;
	
	private String name;
	
	private String desc;
	
	private int award;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}
	
	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		return NTC_SYS_AWARD;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(desc,JoyBuffer.STRING_TYPE_SHORT);
		out.putInt(award);
	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.id = in.getInt();
		this.name = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.desc = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.award = in.getInt();

	}
	
	
}
