package com.joymeng.game.domain.item;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Cell  extends ClientModuleBase{
	private Item item;
	private int itemCount;
	public Cell() {
		// TODO Auto-generated constructor stub
	}
	
	public Cell(Item items,int count) {
		this.item = items;
		this.itemCount = count;
	}
	
	/**
	 * 获取 item
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}
	/**
	 * 设置 item
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}
	/**
	 * 获取 itemCount
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * 设置 itemCount
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	
	@Override
	public String toString() {
		if(getItem().isProp()){//类型,id,count;
			PropsPrototype p = PropsManager.getInstance().getProps(getItem().getId());
			String play = StringUtils.join(",",String.valueOf(Item.ITEM_PROPS),String.valueOf(p.getId()),String.valueOf(getItemCount()));
			return play.toString();
		}else{//类型,id,count;heroid,effectId,effectTime
			Equipment ee = (Equipment) getItem();
			String play = StringUtils.join(",",String.valueOf(Item.ITEM_EQUIPMENT),String.valueOf(ee.getId()),String.valueOf(getItemCount()),String.valueOf(ee.getHeroId()));
			if(ee.getEffectId() != 0){
				play = StringUtils.join(",",play,String.valueOf(ee.getEffectId()),String.valueOf(ee.getEffectTime()));
			}else{
				play = StringUtils.join(",",play,"0","0");
			}
			return play;
		}
	}
	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public byte getModuleType() {
		if(item.isProp()){
			Props p = (Props)getItem();
			return p.getModuleType();
		}else{
			Equipment e = (Equipment)getItem();
			return e.getModuleType();
		}
	}
	@Override
	public void _serialize(JoyBuffer out) {
		if(item.isProp()){//道具
			Props p = (Props)getItem();
			if(p!=null){
				p.serialize(out);
				out.putInt(getItemCount());
			}
		}else{//装备
			Equipment e = (Equipment)getItem();
			if(e!=null){
				e._serialize(out);
//				out.putPrefixedString(toEffectStr(e));
			}
		}
	}
	
	
}
