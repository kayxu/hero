package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

/**
 * 道具宝箱请求
 * @author madi
 *
 */
public class PropsBoxRequest extends JoyRequest {

	byte type;
	
	/**
	 * 打开的是那种宝箱
	 */
	int which;
	
	/**
	 * 打开的宝箱的PropsPropType,id
	 */
	int propsId;
	
	/**
	 * 打开的是那种包裹
	 */
	int whichPackage;
	
	/**
	 * 包裹id
	 */
	int packageId;
	
	/**
	 * 武将卡id
	 */
	int heroCardId;
	
	
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getWhich() {
		return which;
	}

	public void setWhich(int which) {
		this.which = which;
	}

	public PropsBoxRequest() {
		super(ProcotolType.PROPS_BOX_REQ);
	}

	public int getPropsId() {
		return propsId;
	}

	public void setPropsId(int propsId) {
		this.propsId = propsId;
	}
	
	public int getWhichPackage() {
		return whichPackage;
	}

	public void setWhichPackage(int whichPackage) {
		this.whichPackage = whichPackage;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public int getHeroCardId() {
		return heroCardId;
	}

	public void setHeroCardId(int heroCardId) {
		this.heroCardId = heroCardId;
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type = in.get();
		switch(type){
		case ProcotolType.GET_PROPS://打开道具宝箱,新手大礼包
		case ProcotolType.WOOD_ORE_HORSE://获取木材，矿石，马匹
			which = in.getInt();//打开的是哪一种宝箱
			propsId = in.getInt();
			break;
		case ProcotolType.OPEN_PACKAGE://打开包裹获取装备
			whichPackage = in.getInt();//关联包裹id
			packageId = in.getInt();//id
			break;
		case ProcotolType.GET_HERO://获取武将
			heroCardId = in.getInt();
			break;
		case ProcotolType.CONFIRM_GET_HERO://确认获取武将
			break;
		case ProcotolType.CANCLE_GET_HERO://取消获取武将
			break;
		case ProcotolType.DIAMOND_CHIP://获取钻石筹码
			propsId = in.getInt();
			break;
		case ProcotolType.OPEN_NEW_BAG://打开新手大礼包
			which = in.getInt();//打开的是哪一种宝箱
			propsId = in.getInt();
			break;
		case ProcotolType.IS_NEED_NEW_BAG://是否需要显示新手大礼包
			break;
			
		}
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
		switch(type){
		case ProcotolType.GET_PROPS:
			out.putInt(which);
			out.putInt(propsId);
			break;
		case ProcotolType.OPEN_PACKAGE:
			out.putInt(whichPackage);
			out.putInt(packageId);
			break;
		case ProcotolType.GET_HERO:
			out.putInt(heroCardId);
			break;
		case ProcotolType.CONFIRM_GET_HERO:
			break;
		case ProcotolType.CANCLE_GET_HERO:
			break;
		case ProcotolType.DIAMOND_CHIP://获取钻石筹码
			out.putInt(propsId);
			break;
		case ProcotolType.OPEN_NEW_BAG:
			out.putInt(which);
			out.putInt(propsId);
			break;
		case ProcotolType.IS_NEED_NEW_BAG://是否需要显示新手大礼包
			break;
		}
		
	}

}
