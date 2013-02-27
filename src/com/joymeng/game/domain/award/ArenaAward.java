package com.joymeng.game.domain.award;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class ArenaAward extends ClientModuleBase{

	/**
	 * key
	 */
	private int id;
	
	/**
	 * 玩家id
	 */
	private int userId;
	
	/**
	 * 玩家竞技场排名
	 */
	private int rankId;
	
	/**
	 * 奖励中金币值
	 */
	private int gold;
	
	/**
	 * 奖励中功勋值
	 */
	private int medal;
	
	/**
	 * 物品或装备
	 */
	private String goodsOrEqu;
	
	/**
	 * 中奖消息标题
	 */
	private String title;
	
	/**
	 * 中奖消息
	 */
	private String tipsForAward;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getRankId() {
		return rankId;
	}

	public void setRankId(int rankId) {
		this.rankId = rankId;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getMedal() {
		return medal;
	}

	public void setMedal(int medal) {
		this.medal = medal;
	}
	
	public String getTipsForAward() {
		return tipsForAward;
	}

	public void setTipsForAward(String tipsForAward) {
		this.tipsForAward = tipsForAward;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getGoodsOrEqu() {
		return goodsOrEqu;
	}

	public void setGoodsOrEqu(String goodsOrEqu) {
		this.goodsOrEqu = goodsOrEqu;
	}

	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		return NTC_SYS_AWARD;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);//标志 id，客户端领奖请求时发送此id
		out.putPrefixedString(title, JoyBuffer.STRING_TYPE_SHORT);//奖品信息标题
		out.putPrefixedString(tipsForAward,JoyBuffer.STRING_TYPE_SHORT);//奖品信息提示内容
	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.id = in.getInt();
		this.title = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.tipsForAward = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);

	}
	
	
	
}
