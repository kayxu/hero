package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class RankRequest extends JoyRequest{

	byte type;
	int requestPage;
	byte pageSize;
	
	public RankRequest() {
		super(ProcotolType.RANK_REQ);
	
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		
		type = in.get();
		switch(type){
		case ProcotolType.GAME_MONEY_RANK:
			requestPage = in.getInt();
			pageSize = in.get();
			break;
		case ProcotolType.GAME_JOY_MONEY_RANK:
			requestPage = in.getInt();
			pageSize = in.get();
			break;
		case ProcotolType.LADDER_RANK:
			requestPage = in.getInt();
			pageSize = in.get();
			break;
		case ProcotolType.GENERAL_ATTACK_RANK:
			requestPage = in.getInt();
			pageSize = in.get();
			break;
		case ProcotolType.GENERAL_DEFENSE_RANK:
			requestPage = in.getInt();
			pageSize = in.get();
			break;
		case ProcotolType.GENERAL_HP_RANK:
			requestPage = in.getInt();
			pageSize = in.get();
			break;
		case ProcotolType.ARENA_RANK:
			requestPage = in.getInt();
			pageSize = in.get();
			break;
			
		}
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		
		out.put(type);
		switch(type){
		case ProcotolType.GAME_MONEY_RANK:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		case ProcotolType.GAME_JOY_MONEY_RANK:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		case ProcotolType.LADDER_RANK:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		case ProcotolType.GENERAL_ATTACK_RANK:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		case ProcotolType.GENERAL_DEFENSE_RANK:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		case ProcotolType.GENERAL_HP_RANK:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		case ProcotolType.ARENA_RANK:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(int requestPage) {
		this.requestPage = requestPage;
	}

	public byte getPageSize() {
		return pageSize;
	}

	public void setPageSize(byte pageSize) {
		this.pageSize = pageSize;
	}

	
}
