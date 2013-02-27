package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class FlagRequest extends JoyRequest {
	byte type;
	int signId;//参与玩家id
	String roomids;
	int heroa;
	int herob;
	int heroc;

	public FlagRequest() {
		super(ProcotolType.FLAG_REQ);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch (type) {
		case ProcotolType.SIGN_UP:
			out.putInt(signId);
			out.putInt(heroa);
			out.putInt(herob);
			out.putInt(heroc);
			break;
		case ProcotolType.SIGN_QUIT:
			out.putInt(signId);
			break;
		case ProcotolType.START_GAME_FIGHT:
			break;
		case ProcotolType.CAPTURE_FLAG://夺旗
			out.putPrefixedString(roomids, (byte)2);
			out.putInt(signId);
			out.putInt(heroa);
			break;
		case ProcotolType.REFRESH_MOBILITY://刷新积分
			out.putPrefixedString(roomids, (byte)2);
			break;
		case ProcotolType.MOVE_POINT://可移动点
			out.putPrefixedString(roomids, (byte)2);
			out.putInt(signId);
			out.putInt(heroa);
			break;
		case ProcotolType.QUIT_ROOM://结束
			out.putPrefixedString(roomids, (byte)2);
			out.putInt(signId);
			break;
		case ProcotolType.HERO_BUFF://将领buff
			out.putPrefixedString(roomids, (byte)2);
			out.putInt(signId);
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		System.out.println("receive "+FlagRequest.class.getName());
		// todo
		type = in.get();
		switch (type) {
		case ProcotolType.SIGN_UP:
			signId = in.getInt();
			heroa = in.getInt();
			herob = in.getInt();
			heroc = in.getInt();
			break;
		case ProcotolType.SIGN_QUIT:
			signId = in.getInt();
			break;
		case ProcotolType.START_GAME_FIGHT:
			break;
		case ProcotolType.CAPTURE_FLAG://夺旗
			roomids = in.getPrefixedString((byte)2);
			signId = in.getInt();
			heroa = in.getInt();
			break;
		case ProcotolType.REFRESH_MOBILITY://刷新积分
			roomids = in.getPrefixedString((byte)2);
			break;
		case ProcotolType.MOVE_POINT://可移动点
			roomids = in.getPrefixedString((byte)2);
			signId = in.getInt();
			heroa = in.getInt();
			break;
		case ProcotolType.QUIT_ROOM://结束
			roomids = in.getPrefixedString((byte)2);
			signId = in.getInt();
			break;
		case ProcotolType.HERO_BUFF://将领buff
			roomids = in.getPrefixedString((byte)2);
			signId = in.getInt();
			break;
		}
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getSignId() {
		return signId;
	}

	public void setSignId(int signId) {
		this.signId = signId;
	}

	public String getRoomids() {
		return roomids;
	}

	public void setRoomids(String roomids) {
		this.roomids = roomids;
	}

	public int getHeroa() {
		return heroa;
	}

	public void setHeroa(int heroa) {
		this.heroa = heroa;
	}

	public int getHerob() {
		return herob;
	}

	public void setHerob(int herob) {
		this.herob = herob;
	}

	public int getHeroc() {
		return heroc;
	}

	public void setHeroc(int heroc) {
		this.heroc = heroc;
	}
	
	

}
