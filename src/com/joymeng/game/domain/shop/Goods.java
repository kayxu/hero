package com.joymeng.game.domain.shop;


import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Goods  extends ClientModuleBase{
	int id;
	int goodId;
	byte propsType;//类型
	int buyPrice;//价格
	long startRebate;//开始时间
	long endRebate;//结束时间
	int rebate;//折扣率
	int limitCount;//限购数量
	byte isHotSell = 0;//是否热卖  0:否  1:是
	int _num;//购买次数
	/**
	 * @return GET the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param SET id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return GET the goodId
	 */
	public int getGoodId() {
		return goodId;
	}
	/**
	 * @param SET goodId the goodId to set
	 */
	public void setGoodId(int goodId) {
		this.goodId = goodId;
	}
	/**
	 * @return GET the propsType
	 */
	public byte getPropsType() {
		return propsType;
	}
	/**
	 * @param SET propsType the propsType to set
	 */
	public void setPropsType(byte propsType) {
		this.propsType = propsType;
	}
	/**
	 * @return GET the buyPrice
	 */
	public int getBuyPrice() {
		return buyPrice;
	}
	/**
	 * @param SET buyPrice the buyPrice to set
	 */
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	/**
	 * @return GET the startRebate
	 */
	public long getStartRebate() {
		return startRebate;
	}
	/**
	 * @param SET startRebate the startRebate to set
	 */
	public void setStartRebate(long startRebate) {
		this.startRebate = startRebate;
	}
	/**
	 * @return GET the endRebate
	 */
	public long getEndRebate() {
		return endRebate;
	}
	/**
	 * @param SET endRebate the endRebate to set
	 */
	public void setEndRebate(long endRebate) {
		this.endRebate = endRebate;
	}
	/**
	 * @return GET the rebate
	 */
	public int getRebate() {
		return rebate;
	}
	/**
	 * @param SET rebate the rebate to set
	 */
	public void setRebate(int rebate) {
		this.rebate = rebate;
	}
	/**
	 * @return GET the limitCount
	 */
	public int getLimitCount() {
		return limitCount;
	}
	
	
	public byte getIsHotSell() {
		return isHotSell;
	}
	public void setIsHotSell(byte isHotSell) {
		this.isHotSell = isHotSell;
	}
	
	public int get_num() {
		return _num;
	}
	public void set_num(int _num) {
		this._num = _num;
	}
	/**
	 * @param SET limitCount the limitCount to set
	 */
	public void setLimitCount(int limitCount) {
		this.limitCount = limitCount;
	}
	@Override
	public byte getModuleType() {
		return NTC_SHOP;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putInt(goodId);
		out.put(propsType);
		out.putInt(buyPrice);
		out.put(isHotSell);
	}
	
	
//	public void print(){
//		System.out.println("***************************************");
//		System.out.println("modelType=="+getModuleType());
//		System.out.println("goodId=="+getGoodId());
//		System.out.println("category=="+getPropsType());
//		System.out.println("goldPrice=="+getBuyPrice());
//	}
	public static void main(String[] args) {
		
		System.out.println(TimeUtils.nowLong());
	}
}
