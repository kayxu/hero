package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class CardRequest extends JoyRequest{

	byte type;
	
	/**
	 * 所翻牌的下标
	 */
	byte index;
	
	public CardRequest() {
		super(ProcotolType.CARD_REQ);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch(type){
		case ProcotolType.ENTER://进入
			break;
		case ProcotolType.FLIP_CARDS://翻牌
		case ProcotolType.ROTATE_CARD_FACE://旋转牌
			index = in.get();
			break;
		case ProcotolType.GET_AWARD://领奖
			break;
		case ProcotolType.OUT_SHOW://翻牌界面外面显示
		case ProcotolType.OUT_GET_CHANCE:
			break;
		}
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch(type){
		case ProcotolType.ENTER://进入
			break;
		case ProcotolType.FLIP_CARDS://翻牌
		case ProcotolType.ROTATE_CARD_FACE://旋转牌
			out.put(index);
			break;
		case ProcotolType.GET_CHANCE://倒计时到，获取翻牌机会增加
			break;
		case ProcotolType.GET_AWARD://领取奖励
			break;
		case ProcotolType.OUT_SHOW://翻牌界面外面显示
		case ProcotolType.OUT_GET_CHANCE:
			break;
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getIndex() {
		return index;
	}

	public void setIndex(byte index) {
		this.index = index;
	}
	
	
	
	

}
