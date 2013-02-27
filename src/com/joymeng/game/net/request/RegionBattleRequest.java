package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class RegionBattleRequest extends JoyRequest{

	byte type;
	int countryId;
	int stateId;
	int cityId;
	int countyId;
	
	public RegionBattleRequest() {
		super(ProcotolType.REGION_BATTLE_REQ);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch(type){
		case ProcotolType.ENTER_COUNTY_BATTLE_ACTION://按下进入县长争夺战按钮
			break;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_LAST://向上翻页获取当前玩家所属市平行市
			break;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_NEXT:////向下翻页获取当前玩家所属市平行市
			break;
		case ProcotolType.SELECT_COUNTRY://选择国家，展现国家下的所有州
			countryId = in.getInt();
			break;
		case ProcotolType.ENTER_MAP://获取所有国家
			break;
		case ProcotolType.ENTER_CERTAIN_STATE://进入特定州
			stateId = in.getInt();
			break;
		case ProcotolType.BACK_TO_BATTLE_REGION://返回战斗区域
			cityId = in.getInt();
			break;
		case ProcotolType.DISARM://撤防
			countyId = in.getInt();
			break;
		case ProcotolType.VIEW_WAR://查看
			break;
		}
		
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch(type){
		case ProcotolType.ENTER_COUNTY_BATTLE_ACTION://按下进入县长争夺战按钮
			break;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_LAST://向上翻页获取当前玩家所属市平行市
			break;
		case ProcotolType.PAGE_FOR_COUNTY_BATTLE_NEXT:////向下翻页获取当前玩家所属市平行市
			break;
		case ProcotolType.SELECT_COUNTRY:
			out.putInt(countryId);
			break;
		case ProcotolType.ENTER_MAP:
			break;
		case ProcotolType.ENTER_CERTAIN_STATE:
			out.putInt(stateId);
			break;
		case ProcotolType.BACK_TO_BATTLE_REGION:
			out.putInt(cityId);
			break;
		case ProcotolType.DISARM://撤防
			out.putInt(countyId);
			break;
		case ProcotolType.VIEW_WAR://查看
			break;
		}
		
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getCountyId() {
		return countyId;
	}

	public void setCountyId(int countyId) {
		this.countyId = countyId;
	}

}
