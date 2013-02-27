package com.joymeng.game.net.response;

import java.util.List;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class RegionBattleResp extends JoyResponse{
	
	byte type;
	byte result;
	int errorCode;
	List<Nation> nations;
	byte whichPanel;//供客户端识别跳到哪一个标签
	int stateId;//当前玩家所在州id
	byte warType;//争夺类型
	byte warEnd;//是否结束

	public RegionBattleResp() {
		super(ProcotolType.REGION_BATTLE_RESP);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		switch (type){
		case ProcotolType.ENTER_COUNTY_BATTLE_ACTION://按下进入县长争夺战按钮
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					//n.serialize(out);
					out.putInt(n.getId());
					out.putPrefixedString(n.getName(),(byte)2);
					out.putPrefixedString(n.getOccupyUsername(),(byte)2);
					out.putInt(n.getHeroId());
					out.putInt(n.getOccupyUser());
				}
			}
			break;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_LAST:
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					//n.serialize(out);
					out.putInt(n.getId());
					out.putPrefixedString(n.getName(),(byte)2);
					out.putPrefixedString(n.getOccupyUsername(),(byte)2);
					out.putInt(n.getHeroId());
					out.putInt(n.getOccupyUser());
					
				}
			}
			break;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_NEXT:
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					//n.serialize(out);
					out.putInt(n.getId());
					out.putPrefixedString(n.getName(),(byte)2);
					out.putPrefixedString(n.getOccupyUsername(),(byte)2);
					out.putInt(n.getHeroId());
					out.putInt(n.getOccupyUser());
				}
			}
			break;
		case ProcotolType.SELECT_COUNTRY://选择国家，展现此国家下的所有州
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					if(n.getId() == stateId){
						//n.serialize(out);
						out.putInt(n.getId());
						out.putPrefixedString(n.getName(),(byte)2);
						out.putPrefixedString(n.getOccupyUsername(),(byte)2);
						out.putInt(n.getHeroId());
						out.putInt(n.getOccupyUser());
						out.put((byte)1);
					}
					else{
						out.putInt(n.getId());
						out.putPrefixedString(n.getName(),(byte)2);
						out.putPrefixedString(n.getOccupyUsername(),(byte)2);
						out.putInt(n.getHeroId());
						out.putInt(n.getOccupyUser());
						out.put((byte)0);
					}
				}
			}
			break;
		case ProcotolType.ENTER_MAP://进入世界地图
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					if(n.getId() == stateId){
						//n.serialize(out);
						out.putInt(n.getId());
						out.putPrefixedString(n.getName(),(byte)2);
						out.putPrefixedString(n.getOccupyUsername(),(byte)2);
						out.putInt(n.getHeroId());
						out.putInt(n.getOccupyUser());
						out.put((byte)1);
					}
					else{
						out.putInt(n.getId());
						out.putPrefixedString(n.getName(),(byte)2);
						out.putPrefixedString(n.getOccupyUsername(),(byte)2);
						out.putInt(n.getHeroId());
						out.putInt(n.getOccupyUser());
						out.put((byte)0);
					}
					
				}
				out.put(whichPanel);
			}
			break;
		case ProcotolType.ENTER_CERTAIN_STATE://进入特定州
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					//n.serialize(out);
					out.putInt(n.getId());
					out.putPrefixedString(n.getName(),(byte)2);
					out.putPrefixedString(n.getOccupyUsername(),(byte)2);
					out.putInt(n.getHeroId());
					out.putInt(n.getOccupyUser());
				}
			}
			break;
		case ProcotolType.BACK_TO_BATTLE_REGION://返回战争区域
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					//n.serialize(out);
					out.putInt(n.getId());
					out.putPrefixedString(n.getName(),(byte)2);
					out.putPrefixedString(n.getOccupyUsername(),(byte)2);
					out.putInt(n.getHeroId());
					out.putInt(n.getOccupyUser());
				}
			}
			break;
		case ProcotolType.DISARM://撤防
			if(nations != null){
				out.putInt(nations.size());
				for(Nation n : nations){
					//n.serialize(out);
					out.putInt(n.getId());
					out.putPrefixedString(n.getName(),(byte)2);
					out.putPrefixedString(n.getOccupyUsername(),(byte)2);
					out.putInt(n.getHeroId());
					out.putInt(n.getOccupyUser());
				}
			}
			break;
		case ProcotolType.VIEW_WAR://进入世界地图
			out.put(warEnd);//0：开启 1 ：结束
			out.put(warType);//0 无 1 县长 2 市长 3州长 4国王 
		}
		
	}
	
	

	public byte getWarType() {
		return warType;
	}

	public void setWarType(byte warType) {
		this.warType = warType;
	}

	public byte getWarEnd() {
		return warEnd;
	}

	public void setWarEnd(byte warEnd) {
		this.warEnd = warEnd;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<Nation> getNations() {
		return nations;
	}

	public void setNations(List<Nation> nations) {
		this.nations = nations;
	}

	public byte getWhichPanel() {
		return whichPanel;
	}

	public void setWhichPanel(byte whichPanel) {
		this.whichPanel = whichPanel;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	
	
	

}
