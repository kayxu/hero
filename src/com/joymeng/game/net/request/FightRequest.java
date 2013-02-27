package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class FightRequest extends BaseRequest {
	byte type = 0;
//	int attackUser;
	int attackHero;
	int defenceUser;
	int defenceHero;
	int id;// 战役id
	String soldier1;
	String soldier2;
	byte charge;//0免费，1付费
	String roomid;//房间id
	int fromId;

	public FightRequest() {
		super(ProcotolType.FIGHT_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
//		out.putInt(attackUser);
		out.putInt(attackHero);
		out.putPrefixedString(soldier1,JoyBuffer.STRING_TYPE_SHORT);
		switch (type) {
		case FightConst.FIGHTBATTLE_TEST:
			out.putInt(defenceUser);
			out.putInt(defenceHero);
			out.putPrefixedString(soldier2, JoyBuffer.STRING_TYPE_SHORT);
			break;
		case FightConst.FIGHTBATTLE_CAMP:
			out.putInt(id);
			out.put(charge);
			break;
		case FightConst.FIGHTBATTLE_LADDER:
			out.putInt(id);
			out.put(charge);
			break;
		case FightConst.FIGHTBATTLE_GUARD://驻防
			out.putInt(defenceUser);
			break;
		case FightConst.FIGHTBATTLE_ARENA:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_RESOURCE:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_RESOURCE2:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_TEC:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_TEC2:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_REGION:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_STATE_CAMP:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_COUNTRY_CAMP:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_CITY_CAMP:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_STATE_STRONG:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_COUNTRY_STRONG:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_CITY_STRONG:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_CITY:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_STATE:
			out.putInt(id);
			break;
		case FightConst.FIGHTBATTLE_COUNTRY:
			out.putInt(id);
		case FightConst.FIGHTBATTLE_FLAG:
			out.putPrefixedString(roomid,JoyBuffer.STRING_TYPE_SHORT);
			out.putInt(id);//移动到的区域
			out.putInt(fromId);//我的区域
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		super.init();
		// todo
		type=in.get();
//		attackUser = in.getInt();
		attackHero = in.getInt();
		soldier1 = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		switch (type) {
		case FightConst.FIGHTBATTLE_TEST:
			defenceUser = in.getInt();
			defenceHero = in.getInt();
			soldier2 = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
			break;
		case FightConst.FIGHTBATTLE_CAMP:
			id=in.getInt();
			charge=in.get();
			break;
		case FightConst.FIGHTBATTLE_LADDER:
			id=in.getInt();
			charge=in.get();
			break;
		case FightConst.FIGHTBATTLE_GUARD://驻防
			defenceUser=in.getInt();
			break;
		case  FightConst.FIGHTBATTLE_ARENA:
			id=in.getInt();
			break;
		case FightConst.FIGHTBATTLE_RESOURCE:
			id=in.getInt();
			break;
		case FightConst.FIGHTBATTLE_TEC:
			id=in.getInt();
			break;
		case FightConst.FIGHTBATTLE_RESOURCE2:
			id=in.getInt();
			break;
		case FightConst.FIGHTBATTLE_TEC2:
			id=in.getInt();
			break;
		case FightConst.FIGHTBATTLE_REGION:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_STATE_CAMP:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_COUNTRY_CAMP:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_CITY_CAMP:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_STATE_STRONG:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_COUNTRY_STRONG:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_CITY_STRONG:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_CITY:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_STATE:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_COUNTRY:
			id = in.getInt();
			break;
		case FightConst.FIGHTBATTLE_FLAG:
			roomid = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);//room id
			id = in.getInt();;//移动到的区域
			fromId = in.getInt();//我的区域
			break;
		}
	}

//	public int getAttackUser() {
//		return attackUser;
//	}
//
//	public void setAttackUser(int attackUser) {
//		this.attackUser = attackUser;
//	}

	public int getAttackHero() {
		return attackHero;
	}

	public void setAttackHero(int attackHero) {
		this.attackHero = attackHero;
	}

	public int getDefenceUser() {
		return defenceUser;
	}

	public void setDefenceUser(int defenceUser) {
		this.defenceUser = defenceUser;
	}

	public int getDefenceHero() {
		return defenceHero;
	}

	public void setDefenceHero(int defenceHero) {
		this.defenceHero = defenceHero;
	}

	public String getSoldier1() {
		return soldier1;
	}

	public void setSoldier1(String soldier1) {
		this.soldier1 = soldier1;
	}

	public String getSoldier2() {
		return soldier2;
	}

	public void setSoldier2(String soldier2) {
		this.soldier2 = soldier2;
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

	public byte getCharge() {
		return charge;
	}

	public void setCharge(byte charge) {
		this.charge = charge;
	}
	
	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		com.joymeng.core.utils.StringUtil.append(sb, String.valueOf(type),String.valueOf(id),String.valueOf(attackHero),String.valueOf(defenceUser),String.valueOf(defenceHero),String.valueOf(this.soldier1),String.valueOf(this.soldier2),String.valueOf(this.charge));
		return sb.toString();
	}
	
}
