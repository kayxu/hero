package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.shop.Goods;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class ShopRequest extends JoyRequest {

	byte type;//类型
	int goodId;
	int num;
	public ShopRequest() {
		super(ProcotolType.SHOP_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		System.out.println("send "+Goods.class.getName());
		out.put(type);
		switch (type) {
		case ProcotolType.SHOP_ALL_GOODS:
			break;
		case ProcotolType.SHOP_GOOD_BUY:
			out.putInt(goodId);
			out.putInt(num);
			break;
		case ProcotolType.SHOP_LADDER:
			out.putInt(num);
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch (type) {
		case ProcotolType.SHOP_ALL_GOODS:
			break;
		case ProcotolType.SHOP_GOOD_BUY:
			goodId = in.getInt();
			num = in.getInt();
			break;
		case ProcotolType.SHOP_LADDER:
			num=in.getInt();
			break;
		}
	}

	/**
	 * @return GET the type
	 */
	public byte getType() {
		return type;
	}

	/**
	 * @param SET type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * @return GET the goodId
	 */
	public int getGoodId() {
		return goodId;
	}

	/**
	 * @return GET the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param SET num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @param SET goodId the goodId to set
	 */
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	
}
