package com.joymeng.game.net.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class BuildingRequest extends BaseRequest {
	

	static Logger logger = LoggerFactory.getLogger(BuildingRequest.class);
	
	byte type;
	/**
	 * playerBuilding ID
	 */
	int playerId;
	/**
	 * 建筑Id
	 */
	int buildingId;
	
	/**
	 * 坐标ID
	 */
	int coordinateId;
	
	/**
	 * 建设状态
	 */
	byte status;
	
	int userId;
	
	int soldierId;
	int num;
	byte soldierType;
	String soMsg;
//	int uid;
	
	@Override
	protected void _serialize(JoyBuffer out) {
		System.out.println("send "+BuildingRequest.class.getName());
		out.put(type);
		switch (type) {
		case ProcotolType.BUILDING_GET_DEFAULT:// 获取用户初始建筑
			break;
		case ProcotolType.BUILDING_DEL:// 拆除建筑
			out.putInt(playerId);
			break;
		case ProcotolType.BUILDING_ADD:// 添加建筑
			out.putInt(buildingId);
			out.putInt(coordinateId);
			break;
		case ProcotolType.BUILDING_CHARGEOUT:// 建筑列表
			out.putInt(playerId);
			break;
		case ProcotolType.BUILDING_CHANGE_STATUS://修改建筑建设状态
			break;
		case ProcotolType.BUILDING_DISARM:// 撤防
			out.put(status);
			out.putInt(buildingId);
			break;
		case ProcotolType.BUILDING_GUARD:// 驻防
//			out.putInt(buildingId);
			out.putInt(playerId);
			out.putPrefixedString(soMsg, (byte)2);
			break;
		case ProcotolType.BUILDING_LEVELUP:// //建筑升级 
			out.putInt(playerId);
			break;
		case ProcotolType.BUILDING_RECOVER:// 收复
			break;
		case ProcotolType.BUILDING_SOLDIER:// 训练
			out.put(soldierType);
			out.putInt(soldierId);
			out.putInt(num);
			break;
		case ProcotolType.BUILDING_REFUSE_SOLDIER:// 刷新
			out.put(soldierType);
			break;
		case ProcotolType.BUILDING_TRAINING_ADDTIME://加速
			out.putInt(soldierId);
			out.putInt(playerId);
			break;
		case ProcotolType.BUILDING_REFUSE_SMITHY:
			out.put(soldierType);
			break;
		case ProcotolType.BUILDING_TRAINING_VIP:
			out.putInt(soldierId);
			break;
		case ProcotolType.BUILDING_REFUSE_SOLDIEREQU://加速
			out.putInt(soldierId);
			out.putInt(num);
			break;
		case ProcotolType.CHARGE_DETAIL:
			out.putInt(playerId);
			break;
		case ProcotolType.SOLDIER_UPGRADE:
			out.put(status);
			break;
		case ProcotolType.SOLDIER_UN_LOCK:
			out.put(status);
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		// todo
		type = in.get();
		switch (type) {
		case ProcotolType.BUILDING_GET_DEFAULT:// 获取用户初始建筑
			break;
		case ProcotolType.BUILDING_DEL:// 拆除建筑
			playerId = in.getInt();
			break;
		case ProcotolType.BUILDING_ADD:// 添加建筑
			buildingId = in.getInt();
			coordinateId = in.getInt();
			break;
		case ProcotolType.BUILDING_CHANGE_STATUS://修改建筑建设状态
			playerId = in.getInt();
			status = in.get();
			break;
		case ProcotolType.BUILDING_DISARM:// 撤防
			status = in.get();
			buildingId = in.getInt();
			break;
		case ProcotolType.BUILDING_GUARD:// 驻防
//			buildingId = in.getInt();
			playerId = in.getInt();
			soMsg = in.getPrefixedString((byte)2);
			break;
		case ProcotolType.BUILDING_LEVELUP:// //建筑升级 
			playerId = in.getInt();
			break;
		case ProcotolType.BUILDING_RECOVER:// 收复
			break;
		case ProcotolType.BUILDING_ITEM:// 建筑列表
			break;
		case ProcotolType.BUILDING_CHARGEOUT:// 建筑列表
			playerId = in.getInt();
			break;
		case ProcotolType.BUILDING_STEAL:// 建筑列表
			playerId = in.getInt();
			userId = in.getInt();
			break;
		case ProcotolType.BUILDING_SOLDIER:// 
			soldierType = in.get();
			soldierId = in.getInt();
			num = in.getInt();
			break;
		case ProcotolType.BUILDING_REFUSE_SOLDIER:
			soldierType = in.get();
			break;
		case ProcotolType.BUILDING_TRAINING_ADDTIME:
			soldierId = in.getInt();
			playerId = in.getInt();
			break;
		case ProcotolType.BUILDING_REFUSE_SMITHY://铁匠铺
			soldierType = in.get();
			break;
		case ProcotolType.BUILDING_TRAINING_VIP:
			soldierId = in.getInt();
			break;
		case ProcotolType.BUILDING_REFUSE_SOLDIEREQU:
			soldierId = in.getInt();
			num = in.getInt();
			break;
		case ProcotolType.CHARGE_DETAIL:
			playerId = in.getInt();
			break;
		case ProcotolType.SOLDIER_UPGRADE:
			status = in.get();
			break;
		case ProcotolType.SOLDIER_UN_LOCK:
			status = in.get();
			break;
		}
	}

	
	/**
	 * @return GET the soldierId
	 */
	public int getSoldierId() {
		return soldierId;
	}

	/**
	 * @param SET soldierId the soldierId to set
	 */
	public void setSoldierId(int soldierId) {
		this.soldierId = soldierId;
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
	 * @return GET the soldierType
	 */
	public byte getSoldierType() {
		return soldierType;
	}

	/**
	 * @param SET soldierType the soldierType to set
	 */
	public void setSoldierType(byte soldierType) {
		this.soldierType = soldierType;
	}

	/**
	 * 获取 type
	 * @return the type
	 */
	public byte getType() {
		return type;
	}

	/**
	 * 获取 userId
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * 设置 userId
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * 设置 type
	 * @param type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}

	

	/**
	 * 获取 playerId
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * 设置 playerId
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * 获取 buildingId
	 * @return the buildingId
	 */
	public int getBuildingId() {
		return buildingId;
	}

	/**
	 * 设置 buildingId
	 * @param buildingId the buildingId to set
	 */
	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	/**
	 * 获取 coordinateId
	 * @return the coordinateId
	 */
	public int getCoordinateId() {
		return coordinateId;
	}

	/**
	 * 设置 coordinateId
	 * @param coordinateId the coordinateId to set
	 */
	public void setCoordinateId(int coordinateId) {
		this.coordinateId = coordinateId;
	}

	/**
	 * 获取 status
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * 设置 status
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	public BuildingRequest() {
		super(ProcotolType.BUILDING_REQ);
	}

	/**
	 * @return GET the soMsg
	 */
	public String getSoMsg() {
		return soMsg;
	}

	/**
	 * @param SET soMsg the soMsg to set
	 */
	public void setSoMsg(String soMsg) {
		this.soMsg = soMsg;
	}
	
}
