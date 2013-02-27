package com.joymeng.game.domain.rank;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;

public class RankArena extends ClientModuleBase {

	private int rank;
	
	private String name;
	
	private long userId;
	
	private int goldNum;
	
	private int medalNum;
	
	private byte blank;
	
	@Override
	public byte getModuleType() {
		return ClientModule.NTC_ARENA_RANK;
	}

	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		super.deserialize(in);
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(rank);
		out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
		out.putLong(userId);
		out.putInt(goldNum);
		out.putInt(medalNum);
		out.put(blank);
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getGoldNum() {
		return goldNum;
	}

	public void setGoldNum(int goldNum) {
		this.goldNum = goldNum;
	}

	public int getMedalNum() {
		return medalNum;
	}

	public void setMedalNum(int medalNum) {
		this.medalNum = medalNum;
	}

	public byte getBlank() {
		return blank;
	}

	public void setBlank(byte blank) {
		this.blank = blank;
	}

	
	
	
}
