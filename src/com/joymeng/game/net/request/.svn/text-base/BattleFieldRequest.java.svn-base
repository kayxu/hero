package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

/**
 * 战报消息
 * @author madi
 *
 */
public class BattleFieldRequest extends JoyRequest{

	byte type;
	int requestPage;
	byte pageSize;
	int id;
	
	public BattleFieldRequest() {
		super(ProcotolType.BATTLE_FIELD_REQ);
	}
	
	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch(type){
		case ProcotolType.PERSONAL_FIGHT_EVENT:
		case ProcotolType.SYS_BATTLE_INFO://系统战斗是否可进入
		case ProcotolType.SYS_AWARD://系统奖励
			requestPage = in.getInt();
			pageSize = in.get();
			break;
		case ProcotolType.SEE_FIGHT_EVENT:
			id = in.getInt();
			break;
		case ProcotolType.STAGE_FIGHT_EVENT://阶段战报
			break;
		case ProcotolType.GET_SYS_AWARD://领取系统奖励
			id = in.getInt();
			break;
		case ProcotolType.IS_NEED_GET_SYS_AWARD://是否需要领取系统奖励
			break;
			
		}
		
		
	}
	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch(type){
		case ProcotolType.PERSONAL_FIGHT_EVENT:
		case ProcotolType.SYS_BATTLE_INFO:
		case ProcotolType.SYS_AWARD:
			out.putInt(requestPage);
			out.put(pageSize);
			break;
		case ProcotolType.SEE_FIGHT_EVENT:
			out.putInt(id);
			break;
		case ProcotolType.STAGE_FIGHT_EVENT://阶段战报
			break;
		case ProcotolType.GET_SYS_AWARD://领取系统奖励
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
