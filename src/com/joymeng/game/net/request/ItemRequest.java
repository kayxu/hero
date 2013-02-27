package com.joymeng.game.net.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class ItemRequest extends JoyRequest {
	
	private static final long serialVersionUID = 1L;

	static Logger logger = LoggerFactory.getLogger(ItemRequest.class);
	
	int itemId;//物品id
	byte type;//请求类型
	byte goodType;//物品类型
	int num;//道具数量
	int efficId;//效果ID
	int heroId;//英雄id
	byte upgradeType;
	int jzId;//加制石材料id/幸运石
	
	
	public byte getUpgradeType() {
		return upgradeType;
	}

	public void setUpgradeType(byte upgradeType) {
		this.upgradeType = upgradeType;
	}

	public int getJzId() {
		return jzId;
	}

	public void setJzId(int jzId) {
		this.jzId = jzId;
	}

	/**
	 * @return the heroId
	 */
	public int getHeroId() {
		return heroId;
	}

	/**
	 * @param heroId the heroId to set
	 */
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the efficId
	 */
	public int getEfficId() {
		return efficId;
	}

	/**
	 * @param efficId the efficId to set
	 */
	public void setEfficId(int efficId) {
		this.efficId = efficId;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the goodType
	 */
	public byte getGoodType() {
		return goodType;
	}

	/**
	 * @param goodType the goodType to set
	 */
	public void setGoodType(byte goodType) {
		this.goodType = goodType;
	}

	/**
	 * 获取 type
	 * @return the type
	 */
	public byte getType() {
		return type;
	}

	/**
	 * 设置 type
	 * @param type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}

	public ItemRequest() {
		super(ProcotolType.ITEMS_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		System.out.println("send "+ItemRequest.class.getName());
		out.put(type);
		System.out.println("************send item type ：" + type);
		switch (type) {
		case ProcotolType.ITEM_GET:// 获取用户背包数据
			break;
		case ProcotolType.ITEM_GET_EQUIMENT:// 用户背包装备数据
			break;
		case ProcotolType.ITEM_GET_PROPS:// 用户道具数据
			break;
		case ProcotolType.ITEM_ADD_EQUIMENT:// 添加装备到背包
			out.putInt(itemId);
			break;
		case ProcotolType.ITEM_ADD_PROPS://添加道具到背包
			out.putInt(itemId);
			out.putInt(num);
		case ProcotolType.ITEM_USER_PROPS:// 使用道具
			out.putInt(itemId);
			break;
		case ProcotolType.ITEM_USER_EQUIMENT://使用装备 
			out.putInt(itemId);
			out.putInt(heroId);
			break;
		case ProcotolType.ITEM_OFF_EQUIMENT://脱装备
			out.putInt(itemId);
			out.putInt(heroId);
			break;
		case ProcotolType.ITEM_USER_UPGRADE:
			out.put(upgradeType);
			out.putInt(itemId);
			out.putInt(jzId);
			out.putInt(heroId);
			break;
		case ProcotolType.ITEM_USER_DISMANT:
			out.putInt(itemId);
			break;
		case ProcotolType.CUTOVER_EQUIMENT:
			out.putInt(itemId);
			out.putInt(num);
			out.putInt(heroId);
			break;
		case ProcotolType.ITEM_SPY_ON:
			out.putInt(itemId);
			out.putInt(heroId);
			break;
		case ProcotolType.ITEM_USE_PROPS:
			out.putInt(heroId);
			out.putInt(itemId);
			out.putInt(jzId);
			break;
		case ProcotolType.ITEM_DEL_DELAY:
			out.put(upgradeType);
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// todo
		type = in.get();
		System.out.println("************ item type：" + type);
		switch (type) {
		case ProcotolType.ITEM_GET:// 获取用户背包数据
			break;
		case ProcotolType.ITEM_GET_EQUIMENT:// 用户背包装备数据
			break;
		case ProcotolType.ITEM_GET_PROPS:// 用户道具数据
			break;
		case ProcotolType.ITEM_ADD_EQUIMENT:// 购买装备
			itemId = in.getInt();
			break;
		case ProcotolType.ITEM_ADD_PROPS://添加道具到背包
			itemId = in.getInt();
			num = in.getInt();
			break;
		case ProcotolType.ITEM_USER_PROPS:// 使用道具
			itemId = in.getInt();
			break;
		case ProcotolType.ITEM_USER_EQUIMENT://使用装备 
			itemId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.ITEM_OFF_EQUIMENT://脱装备
			itemId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.ITEM_USER_UPGRADE:
			upgradeType = in.get();
			itemId = in.getInt();
			jzId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.ITEM_USER_DISMANT:
			itemId = in.getInt();
			break;
		case ProcotolType.CUTOVER_EQUIMENT:
			itemId = in.getInt();
			num = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.ITEM_SPY_ON:
			itemId = in.getInt();
			heroId = in.getInt();
			break;
		case ProcotolType.ITEM_USE_PROPS:
			heroId = in.getInt();
			itemId = in.getInt();
			jzId = in.getInt();
			break;
		case ProcotolType.ITEM_DEL_DELAY:
			upgradeType = in.get();
			break;
		}
	}

}
