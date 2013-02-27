package com.joymeng.game.domain.rank;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;

public class RankHero extends ClientModuleBase {

	/**
	 * 名次
	 */
	private int ranking;
	
	/**
	 * 武将姓名
	 */
	private String heroName;
	
	/**
	 * 武将等级
	 */
	private int level;
	
	/**
	 * 数值
	 */
	private int value;
	
	/**
	 * 玩家名称
	 */
	private String playerName;
	
	/**
	 * 玩家id
	 */
	private Long playerId;
	
	/**
	 * 玩家姓名颜色
	 */
	private byte color;
	
	
	@Override
	public byte getModuleType() {
		return ClientModule.RANK_HERO;
	}

	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		super.deserialize(in);
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(ranking);
		out.putPrefixedString(heroName,JoyBuffer.STRING_TYPE_SHORT);
		out.put(color);
		out.putInt(level);
		out.putInt(value);
		out.putPrefixedString(playerName,JoyBuffer.STRING_TYPE_SHORT);
		out.putLong(playerId);
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public byte getColor() {
		return color;
	}

	public void setColor(byte color) {
		this.color = color;
	}
}
