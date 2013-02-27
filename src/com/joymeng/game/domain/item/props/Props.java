package com.joymeng.game.domain.item.props;

import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Props extends Item implements ClientModule{
	
	public byte isAdd = 0;//是否添加
	
	/**
	 * @return GET the isAdd
	 */
	public byte getIsAdd() {
		return isAdd;
	}
	/**
	 * @param SET isAdd the isAdd to set
	 */
	public void setIsAdd(byte isAdd) {
		this.isAdd = isAdd;
	}
	public Props(PropsPrototype pr){
		prototype = pr;
	}
	public Props(){}
	//效果类型
	byte effectType;
	
	PropsPrototype prototype;
	/**
	 * 获取 prototype
	 * @return the prototype
	 */
	public PropsPrototype getPrototype() {
		return prototype;
	}
	
	/**
	 * 设置 prototype
	 * @param prototype the prototype to set
	 */
	public void setPrototype(PropsPrototype prototype) {
		this.prototype = prototype;
	}
	
	//道具下发说明
	@Override
	public String getDownDesc(PlayerCharacter role) {
		return "";
	}

	@Override
	public byte getType() {
		return prototype.getPropsType();
	}

	@Override
	public boolean isProp() {
		return true;
	}

	@Override
	public int getMaxStackCount() {
		return getPrototype().getMaxStackCount();
	}

	@Override
	public String getIconIdx() {
		return getPrototype().getPropsIcon();
	}

	@Override
	public String getName() {
		return getPrototype().getName();
		
	}

	@Override
	public int getId() {
		return getPrototype().getId();
		
	}
	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public byte getModuleType() {
		return NTC_PROPS;// TODO Auto-generated method stub
		
	}
	@Override
	public void serialize(JoyBuffer out) {
		out.putInt(getId());
		out.put(getIsAdd());//是否添加
	}
	

	
}
