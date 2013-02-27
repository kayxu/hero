package com.joymeng.game.net.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

/**
 * 道具宝箱
 * @author madi
 *
 */
public class PropsBoxResp extends JoyResponse{
	
	static Logger logger = LoggerFactory.getLogger(PropsBoxResp.class);
	byte result;
	int errorCode;
	byte type;
	String equipIds = "";
	String itemIds = "";
	String totalEquipForSelect = "";
	String totalItemForSelect = "";
	int heroId;
	byte isNeedShow;//1--需要显示新手大礼包，0--不需要显示
	byte canOpenNewBag;//1--可以打开，0--不可以打开
	
	/**
	 * 是何种道具
	 */
	byte what;
	/**
	 * 物品或装备id或者金币或功勋值
	 */
	int goodOrEquipIdOrNeedValue;
	
	/**
	 * 包裹中物品或装备的数量
	 */
	int inPackageValue;

	public PropsBoxResp() {
		super(ProcotolType.PROPS_BOX_RESP);
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
		switch(type){
		case ProcotolType.GET_PROPS:
		case ProcotolType.WOOD_ORE_HORSE://获取木材，矿石，马匹
		case ProcotolType.OPEN_NEW_BAG://打开新手大礼包
			if(!"".equals(equipIds)){//如果装备列表不为空
				String[] equipIdArray = equipIds.split(":");
				out.put((byte)equipIdArray.length);
				for(String equipId : equipIdArray){
					out.putInt(Integer.parseInt(equipId));
				}
			}
			else{
				out.put((byte)0);
			}
			if(!"".equals(itemIds)){//如果物品列表不为空
				String[] itemIdArray = itemIds.split(":");
				out.put((byte) itemIdArray.length);
				for(String itemId : itemIdArray){
					out.putInt(Integer.parseInt(itemId));
				}
			}
			else{
				out.put((byte)0);
			}
			out.put(what);//当前是什么，1-金币，2-功勋，3-物品，4-装备
			out.putInt(goodOrEquipIdOrNeedValue);//值，如果是物品或装备则为id,如果是金币或功勋则为数量
			out.putInt(inPackageValue);//数量，如果是金币或功勋则不处理
			//out.putInt(goodOrEquipId);
			break;
		case ProcotolType.OPEN_PACKAGE:
			if(!"".equals(totalEquipForSelect)){//如果供选择装备列表不为空
				String[] selectEquipIdArray = totalEquipForSelect.split(":");
				out.put((byte)selectEquipIdArray.length);
				for(String selectId : selectEquipIdArray){
					out.putInt(Integer.parseInt(selectId));
				}
			}
			else{
				out.put((byte)0);
			}
			
			if(!"".equals(totalItemForSelect)){//如果供选择物品列表不为空
				String[] selectItemIdArray = totalItemForSelect.split(":");
				out.put((byte)selectItemIdArray.length);
				for(String selectId : selectItemIdArray){
					out.putInt(Integer.parseInt(selectId));
				}
			}
			else{
				out.put((byte)0);
			}
			
			if(!"".equals(equipIds)){//如果装备列表不为空
				String[] equipIdArray = equipIds.split(":");
				out.put((byte)equipIdArray.length);
				for(String equipId : equipIdArray){
					out.putInt(Integer.parseInt(equipId));
				}
			}
			else{
				out.put((byte)0);
			}
			
			if(!"".equals(itemIds)){//如果物品列表不为空
				String[] itemIdArray = itemIds.split(":");
				out.put((byte) itemIdArray.length);
				for(String itemId : itemIdArray){
					out.putInt(Integer.parseInt(itemId));
				}
			}
			else{
				out.put((byte)0);
			}
			break;
		case ProcotolType.GET_HERO:
			out.putInt(heroId);
			break;
		case ProcotolType.CONFIRM_GET_HERO:
			break;
		case ProcotolType.CANCLE_GET_HERO:
			break;
		case ProcotolType.DIAMOND_CHIP:
			break;
	/*	case ProcotolType.OPEN_NEW_BAG://打开新手大礼包
			out.put(canOpenNewBag);
			break;*/
		case ProcotolType.IS_NEED_NEW_BAG://是否需要显示新手大礼包
			out.put(isNeedShow);
			break;
		}
		
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

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getEquipIds() {
		return equipIds;
	}

	public void setEquipIds(String equipIds) {
		this.equipIds = equipIds;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public byte getWhat() {
		return what;
	}

	public void setWhat(byte what) {
		this.what = what;
	}

	public int getGoodOrEquipIdOrNeedValue() {
		return goodOrEquipIdOrNeedValue;
	}

	public void setGoodOrEquipIdOrNeedValue(int goodOrEquipIdOrNeedValue) {
		this.goodOrEquipIdOrNeedValue = goodOrEquipIdOrNeedValue;
	}

	public int getInPackageValue() {
		return inPackageValue;
	}

	public void setInPackageValue(int inPackageValue) {
		this.inPackageValue = inPackageValue;
	}

	public String getTotalEquipForSelect() {
		return totalEquipForSelect;
	}

	public void setTotalEquipForSelect(String totalEquipForSelect) {
		this.totalEquipForSelect = totalEquipForSelect;
	}

	public String getTotalItemForSelect() {
		return totalItemForSelect;
	}

	public void setTotalItemForSelect(String totalItemForSelect) {
		this.totalItemForSelect = totalItemForSelect;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public byte getIsNeedShow() {
		return isNeedShow;
	}

	public void setIsNeedShow(byte isNeedShow) {
		this.isNeedShow = isNeedShow;
	}

	public byte getCanOpenNewBag() {
		return canOpenNewBag;
	}

	public void setCanOpenNewBag(byte canOpenNewBag) {
		this.canOpenNewBag = canOpenNewBag;
	}

	
	

	
	
	
	
	

}
