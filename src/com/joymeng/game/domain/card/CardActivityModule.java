package com.joymeng.game.domain.card;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class CardActivityModule extends ClientModuleBase {

	private int flipChance;
	
	private int rotateChance;
	
	private int nextTime;
	
	@Override
	public byte getModuleType() {
		return NTC_CARD_ACTIVITY;
	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.flipChance = in.getInt();
		this.rotateChance = in.getInt();
		this.nextTime = in.getInt();
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(flipChance);//翻牌次数
		out.putInt(rotateChance);//旋转次数
		out.putInt(nextTime);//倒计时时间
	}

	public int getFlipChance() {
		return flipChance;
	}

	public void setFlipChance(int flipChance) {
		this.flipChance = flipChance;
	}

	public int getRotateChance() {
		return rotateChance;
	}

	public void setRotateChance(int rotateChance) {
		this.rotateChance = rotateChance;
	}

	public int getNextTime() {
		return nextTime;
	}

	public void setNextTime(int nextTime) {
		this.nextTime = nextTime;
	}
	
	

	
}
