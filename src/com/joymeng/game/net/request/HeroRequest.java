package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class HeroRequest extends JoyRequest {
	byte type;
	int id;
	int skillId;
	int itemId;//道具id
	byte trainType;//训练类型
	byte trainIndex;//训练位置
	byte moneyType;//
	public HeroRequest() {
		super(ProcotolType.HERO_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
//		System.out.println("send "+HeroRequest.class.getName());
		out.put(type);
		switch (type) {
		case ProcotolType.HERO_REFRESH:// 刷新将领
			out.put((byte)id);//酒馆刷新类别byte（0自动刷新3个；1手动刷新3个；2自动刷新1个；3手动刷新1个）
			break;
		case ProcotolType.HERO_GET:// 获得将领
		case ProcotolType.HERO_TRAINEND://结束训练
		case ProcotolType.HERO_DEL:// 删除将领
			out.putInt(id);
			break;
		case ProcotolType.HERO_LIST://将领列表
			break;
		case ProcotolType.HERO_EQUIP:// 装备
			break;
		case ProcotolType.HERO_UNEQUIP:// 卸下装备
			break;
		case ProcotolType.HERO_ADDSKILL:// 学习技能
			out.putInt(id);
			out.putInt(itemId);
			break;
		case ProcotolType.HERO_DELSKILL:// 删除技能
			out.putInt(id);
			out.putInt(skillId);
			out.put(moneyType);
			break;
		case ProcotolType.HERO_SOLDIER:// 带兵数
			break;
		case ProcotolType.HERO_LEVELUP://升级
			break;
		case ProcotolType.HERO_TRAINSTART://开始训练
			out.putInt(id);
			out.put(trainType);
			out.put(trainIndex);
			break;
		case ProcotolType.HERO_EXPANDSKILL:
			out.putInt(id);
			out.put(moneyType);
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		System.out.println("receive "+HeroRequest.class.getName());
		// todo
		type = in.get();
		switch (type) {
		case ProcotolType.HERO_REFRESH:// 刷新将领
			id=in.get();
			break;
		case ProcotolType.HERO_GET:// 获得将领
		
		case ProcotolType.HERO_TRAINEND://结束训练
		case ProcotolType.HERO_DEL:// 删除将领
			id=in.getInt();
			break;
		case ProcotolType.HERO_LIST://将领列表
			break;
		case ProcotolType.HERO_EQUIP:// 装备
			break;
		case ProcotolType.HERO_UNEQUIP:// 卸下装备
			break;
		case ProcotolType.HERO_ADDSKILL:// 学习技能
			id=in.getInt();
			itemId=in.getInt();
			break;
		case ProcotolType.HERO_DELSKILL:// 删除技能
			id=in.getInt();
			skillId=in.getInt();
			moneyType=in.get();
			break;
		case ProcotolType.HERO_SOLDIER:// 带兵数
			break;
		case ProcotolType.HERO_LEVELUP://升级
			break;
		case ProcotolType.HERO_TRAINSTART://开始训练
			id=in.getInt();
			trainType=in.get();
			trainIndex=in.get();
			break;
		case ProcotolType.HERO_SPEEDUP://加速训练
			id=in.getInt();
			break;
		case ProcotolType.HERO_EXPANDSKILL:
			id=in.getInt();
			moneyType=in.get();
			break;
		}
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public byte getTrainType() {
		return trainType;
	}

	public void setTrainType(byte trainType) {
		this.trainType = trainType;
	}

	public byte getTrainIndex() {
		return trainIndex;
	}

	public void setTrainIndex(byte trainIndex) {
		this.trainIndex = trainIndex;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public byte getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(byte moneyType) {
		this.moneyType = moneyType;
	}
	
}
