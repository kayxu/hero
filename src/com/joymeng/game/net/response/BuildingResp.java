package com.joymeng.game.net.response;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.building.Building;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.message.JoyResponse;

public class BuildingResp extends JoyResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = LoggerFactory.getLogger(BuildingResp.class);
	byte type;
	List<PlayerBuilding>  PlayerBuildLst;
	
	List<Building> buildLst;
	List<Equipment> eLst;
	int playerBuildId;
	int[] chargeMsg ;
	/**
	 * 0 ： false 1:true
	 */
	byte tureOrFalse ; 
	int chargeOut;
	
	/**
	 * 获取 chargeOut
	 * @return the chargeOut
	 */
	public int getChargeOut() {
		return chargeOut;
	}

	/**
	 * 设置 chargeOut
	 * @param chargeOut the chargeOut to set
	 */
	public void setChargeOut(int chargeOut) {
		this.chargeOut = chargeOut;
	}

	byte result;
	int errorCode;
	

	
	public BuildingResp() {
		super(ProcotolType.BUILDING_RESP);
		super.setDestInstanceID(JoyProtocol.MODULE_SYS);

	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		
		out.put(type);
		System.out.println("out put type >>>>>>>>>>>>"+type);
		switch (type) {
		case ProcotolType.BUILDING_GET_DEFAULT:// 获取用户初始建筑
			if(PlayerBuildLst == null){
				out.putInt(0);
				break;
			}
			out.putInt(PlayerBuildLst.size());
			for(PlayerBuilding p : PlayerBuildLst){
				p.serialize(out);
				p.print();
			}
			break;
		case ProcotolType.BUILDING_ITEM:// 获得建筑列表
			for(Building b : buildLst){
				if(b !=null){
					b.serialize(out);
				}
			}
			break;
		case ProcotolType.BUILDING_DEL:
			break;// 拆除建筑
		case ProcotolType.BUILDING_ADD:// 添加建筑
			for(PlayerBuilding p : PlayerBuildLst){
				if(p !=null){
					p.serialize(out);
				}
			}
			break;
		case ProcotolType.BUILDING_CHANGE_STATUS://修改建筑建设状态
			break;
		case ProcotolType.BUILDING_DISARM:// 撤防
			break;
		case ProcotolType.BUILDING_GUARD:// 驻防
			break;
		case ProcotolType.BUILDING_LEVELUP:// //建筑升级 
			out.put(tureOrFalse);
			out.putInt(playerBuildId);
			out.putInt(chargeOut);
			System.out.println(tureOrFalse+"===="+playerBuildId+"==="+chargeOut);
			break;
		case ProcotolType.BUILDING_RECOVER:// 收复
			break;
		case ProcotolType.BUILDING_CHARGEOUT:// 收取
			out.put(tureOrFalse);
			out.putInt(chargeOut);
			break;
		case ProcotolType.BUILDING_STEAL:// 偷取
			out.put(tureOrFalse);
			out.putInt(chargeOut);
			break;
		case ProcotolType.BUILDING_SOLDIER:// 
			out.put(tureOrFalse);
			break;
		case ProcotolType.BUILDING_REFUSE_SMITHY://刷新铁匠铺数据
			out.putInt(chargeOut);
			if(eLst != null && eLst.size() > 0){
				out.putInt(eLst.size());
				for(Equipment e : eLst){
					e.serialize(out);
				}
			}else{
				out.putInt(0);
			}
			break;
		case ProcotolType.CHARGE_DETAIL://收取明细
			if(getChargeMsg() !=null){
				for(int i = 0;i < getChargeMsg().length;i++){
					out.putInt(getChargeMsg()[i]);
				}
			}
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		result=in.get();
		errorCode=in.getInt();
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		type=in.get();
		switch (type) {
		case ProcotolType.BUILDING_GET_DEFAULT:// 获取用户初始建筑
			System.out.println(ProcotolType.BUILDING_GET_DEFAULT);
			break;
		case ProcotolType.BUILDING_ITEM:// 获得建筑列表
			System.out.println(ProcotolType.BUILDING_ITEM);

			break;
		case ProcotolType.BUILDING_DEL:// 拆除建筑
			System.out.println(ProcotolType.BUILDING_DEL);
			break;
		case ProcotolType.BUILDING_ADD:// 添加建筑
			break;
		case ProcotolType.BUILDING_CHANGE_STATUS://修改建筑建设状态
			break;
		case ProcotolType.BUILDING_DISARM:// 撤防
			break;
		case ProcotolType.BUILDING_GUARD:// 驻防
			break;
		case ProcotolType.BUILDING_LEVELUP:// //建筑升级 
			break;
		case ProcotolType.BUILDING_RECOVER:// 收复
			break;
		case ProcotolType.BUILDING_STEAL:// 偷盗
			tureOrFalse = in.get();
			chargeOut = in.getInt();
			break;
		case ProcotolType.BUILDING_SOLDIER:// 偷盗
			tureOrFalse = in.get();
			break;
		}
	}
	
	
	public List<PlayerBuilding> getPlayerBuildLst() {
		return PlayerBuildLst;
	}

	public void setPlayerBuildLst(List<PlayerBuilding> playerBuildLst) {
		PlayerBuildLst = playerBuildLst;
	}

	public List<Building> getBuildLst() {
		return buildLst;
	}

	public void setBuildLst(List<Building> buildLst) {
		this.buildLst = buildLst;
	}

	public byte getTureOrFalse() {
		return tureOrFalse;
	}

	public void setTureOrFalse(byte tureOrFalse) {
		this.tureOrFalse = tureOrFalse;
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

	/**
	 * @return GET the playerBuildId
	 */
	public int getPlayerBuildId() {
		return playerBuildId;
	}

	/**
	 * @param SET playerBuildId the playerBuildId to set
	 */
	public void setPlayerBuildId(int playerBuildId) {
		this.playerBuildId = playerBuildId;
	}

	

	public int[] getChargeMsg() {
		return chargeMsg;
	}

	public void setChargeMsg(int[] chargeMsg) {
		this.chargeMsg = chargeMsg;
	}

	/**
	 * @return GET the eLst
	 */
	public List<Equipment> geteLst() {
		return eLst;
	}

	/**
	 * @param SET eLst the eLst to set
	 */
	public void seteLst(List<Equipment> eLst) {
		this.eLst = eLst;
	}
	
}
