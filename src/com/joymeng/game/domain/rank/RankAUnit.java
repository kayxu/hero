package com.joymeng.game.domain.rank;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;

public class RankAUnit extends ClientModuleBase {

	/**
	 * 名次
	 */
	int ranking;
	
	/**
	 * 玩家名
	 */
	String name;
	
	/**
	 * 数量
	 */
	int value;
	
	/**
	 * 玩家id
	 */
	long playerId;

	@Override
	public byte getModuleType() {
		return ClientModule.RANK_GAME_MONEY;
	}

	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		super.deserialize(in);
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(ranking);
		out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
		out.putInt(value);
		out.putLong(playerId);
	}
	
	
	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

}
