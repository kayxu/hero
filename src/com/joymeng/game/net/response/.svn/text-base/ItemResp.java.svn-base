package com.joymeng.game.net.response;

import java.util.List;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class ItemResp extends JoyResponse {
	byte result;
	int errorCode;
	byte type;
	
	byte trueOrFalse;//是否成功
	int eqId;//升级后id;
	List<Integer> propsIds;//材料id
	Equipment equ;
	byte upgType;
	List<ClientModuleBase> clientLst;
	
	
	public List<ClientModuleBase> getClientLst() {
		return clientLst;
	}

	public void setClientLst(List<ClientModuleBase> clientLst) {
		this.clientLst = clientLst;
	}

	/**
	 * @return GET the upgType
	 */
	public byte getUpgType() {
		return upgType;
	}

	/**
	 * @param SET upgType the upgType to set
	 */
	public void setUpgType(byte upgType) {
		this.upgType = upgType;
	}

	/**
	 * @return GET the equ
	 */
	public Equipment getEqu() {
		return equ;
	}

	/**
	 * @param SET equ the equ to set
	 */
	public void setEqu(Equipment equ) {
		this.equ = equ;
	}

	/**
	 * @return GET the eqId
	 */
	public int getEqId() {
		return eqId;
	}

	/**
	 * @param SET eqId the eqId to set
	 */
	public void setEqId(int eqId) {
		this.eqId = eqId;
	}

	/**
	 * @return GET the propsIds
	 */
	public List<Integer> getPropsIds() {
		return propsIds;
	}

	/**
	 * @param SET propsIds the propsIds to set
	 */
	public void setPropsIds(List<Integer> propsIds) {
		this.propsIds = propsIds;
	}

	/**
	 * @return the trueOrFalse
	 */
	public byte getTrueOrFalse() {
		return trueOrFalse;
	}

	/**
	 * @param trueOrFalse the trueOrFalse to set
	 */
	public void setTrueOrFalse(byte trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}

	/**
	 * @return the result
	 */
	public byte getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(byte result) {
		this.result = result;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
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

	public ItemResp() {
		super(ProcotolType.ITEMS_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.put(result);
		out.putInt(errorCode);
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		out.put(type);
		System.out.println("item resp type >>>>>>"+ type);
		switch (type) {
		case ProcotolType.ITEM_GET:// 获取用户背包数据
			
			break;
		case ProcotolType.ITEM_GET_EQUIMENT:// 用户背包装备数据
			break;
		case ProcotolType.ITEM_GET_PROPS:// 用户道具数据
			break;
		case ProcotolType.ITEM_ADD_EQUIMENT:// 添加装备到背包
//			if()
			break;
		case ProcotolType.ITEM_ADD_PROPS://添加道具到背包
			out.put(trueOrFalse);
			break;
		case ProcotolType.ITEM_USER_PROPS:// 使用道具
			out.put(trueOrFalse);
			break;
		case ProcotolType.ITEM_USER_EQUIMENT://使用装备 
			out.put(trueOrFalse);
			break;
		case ProcotolType.ITEM_USER_UPGRADE://升级后返回新的装备
			out.put(upgType);
			out.put(trueOrFalse);
			if(equ != null){
				equ.serialize(out);
			}
			break;
		case ProcotolType.ITEM_USER_DISMANT://拆解
			System.out.println("拆解："+getPropsIds().size());
			if(getPropsIds() != null && getPropsIds().size() >0){
				out.putInt(getPropsIds().size());//size
				System.out.println("拆解："+getPropsIds().size());
				for (Integer i : getPropsIds())
					out.putInt(i);
			}else{
				out.putInt(0);//size
			}
			break;
		case ProcotolType.CUTOVER_EQUIMENT:
			out.put(trueOrFalse);
			break;
		case ProcotolType.ITEM_SPY_ON:
			if(clientLst != null && clientLst.size() > 0){
				if(clientLst.get(clientLst.size()-1) instanceof PlayerHero){
					PlayerHero hero = (PlayerHero)clientLst.get(clientLst.size()-1);
					out.putInt(hero.getId());
					out.putInt(clientLst.size());
					for(ClientModule client : clientLst){
						client.serialize(out);
					}
				}else{
					out.putInt(0);//heroid
					out.putInt(0);//size
				}
			}else{
				out.putInt(0);//heroid
				out.putInt(0);//size
			}
			break;
		case ProcotolType.ITEM_DEL_DELAY:
			out.put(trueOrFalse);
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
		case ProcotolType.ITEM_GET:// 获取用户背包数据
			break;
		case ProcotolType.ITEM_GET_EQUIMENT:// 用户背包装备数据
			break;
		case ProcotolType.ITEM_GET_PROPS:// 用户道具数据
			break;
		case ProcotolType.ITEM_ADD_EQUIMENT:// 添加装备到背包
			trueOrFalse = in.get();
			break;
		case ProcotolType.ITEM_ADD_PROPS://添加道具到背包
			trueOrFalse = in.get();
			break;
		case ProcotolType.ITEM_USER_PROPS:// 使用道具
			trueOrFalse = in.get();
			break;
		case ProcotolType.ITEM_USER_EQUIMENT://使用装备 
			trueOrFalse = in.get();
			break;
		case ProcotolType.ITEM_USER_UPGRADE://升级
			trueOrFalse = in.get();
			eqId = in.getInt();
			break;
		case ProcotolType.ITEM_USER_DISMANT://拆解
			break;
		case ProcotolType.CUTOVER_EQUIMENT:
			trueOrFalse = in.get();
			break;
		}
	}

}
