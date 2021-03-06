package com.joymeng.game.domain.building;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * @author xufangliang
 * 
 */
public class Building extends ClientModuleBase{
	
	/**
	 * 建筑iD
	 */
	private int id;
	/**
	 * 建筑名称
	 */
	private String name;
	/**
	 * 建筑说明
	 */
	private String description;
	
	/**
	 * 是否可以被占领/驻防
	 */
	private int isOccupy;
	
	/**
	 * 是否初始建筑
	 */
	private int isInitial;
	
	/**
	 * 建筑类型
	 */
	private int category;//1是资源建筑
	
	/**
	 * 对应动画
	 */
	private String ani;
	
	/**
	 * 建筑长度
	 */
	private int length;
	
	/**
	 * 建筑宽度
	 */
	private int width;
	
	/**
	 * 建筑高度
	 */
	private int height;
	
	/**
	 * 开放等级
	 */
	private int levelRequired;
	
	/**
	 * 价格
	 */
	private int price;
	
	/**
	 * 功勋价格
	 */
	private int honerPrice;
	
	/**
	 * 建设时间
	 */
	private int constructionTime;
	
	
	/**
	 * 是否唯一
	 */
	private int isUnique;
	
	/**
	 * 能否拆除
	 */
	private int canBeDestroyed;
	
	/**
	 * 能否升级
	 */
	private int isLevelUp;
	
	/**
	 * 是否显示姓名
	 */
	private byte showName;
	
	/**
	 * 最高等级
	 */
	private byte maxLevel;
	
	
	
	/**
	 * 获取 maxLevel
	 * @return the maxLevel
	 */
	public byte getMaxLevel() {
		return maxLevel;
	}

	/**
	 * 设置 maxLevel
	 * @param maxLevel the maxLevel to set
	 */
	public void setMaxLevel(byte maxLevel) {
		this.maxLevel = maxLevel;
	}

	/**
	 * 获取 showName
	 * @return the showName
	 */
	public byte getShowName() {
		return showName;
	}

	/**
	 * 设置 showName
	 * @param showName the showName to set
	 */
	public void setShowName(byte showName) {
		this.showName = showName;
	}


	public int getIsInitial() {
		return isInitial;
	}


	public void setIsInitial(int isInitial) {
		this.isInitial = isInitial;
	}


	public int getIsOccupy() {
		return isOccupy;
	}


	public void setIsOccupy(int isOccupy) {
		this.isOccupy = isOccupy;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getCategory() {
		return category;
	}


	public void setCategory(int category) {
		this.category = category;
	}


	public String getAni() {
		return ani;
	}


	public void setAni(String ani) {
		this.ani = ani;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getLevelRequired() {
		return levelRequired;
	}


	public void setLevelRequired(int levelRequired) {
		this.levelRequired = levelRequired;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getHonerPrice() {
		return honerPrice;
	}


	public void setHonerPrice(int honerPrice) {
		this.honerPrice = honerPrice;
	}


	public int getConstructionTime() {
		return constructionTime;
	}


	public void setConstructionTime(int constructionTime) {
		this.constructionTime = constructionTime;
	}


	public int getIsUnique() {
		return isUnique;
	}


	public void setIsUnique(int isUnique) {
		this.isUnique = isUnique;
	}


	public int getCanBeDestroyed() {
		return canBeDestroyed;
	}


	public void setCanBeDestroyed(int canBeDestroyed) {
		this.canBeDestroyed = canBeDestroyed;
	}


	public int getIsLevelUp() {
		return isLevelUp;
	}


	public void setIsLevelUp(int isLevelUp) {
		this.isLevelUp = isLevelUp;
	}

	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		 return NTC_DTCD_BUILDING;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putPrefixedString(name,(byte)2);
		out.putPrefixedString(description,(byte)2);
		out.putInt(isOccupy);
		out.putInt(isInitial);
		out.putInt(category);
		out.putPrefixedString(ani,(byte)2);
		out.putInt(length);
		out.putInt(width);
		out.putInt(height);
		out.putInt(levelRequired);
		out.putInt(price);
		out.putInt(honerPrice);
		out.putInt(constructionTime);
		out.putInt(isUnique);
		out.putInt(canBeDestroyed);
		out.putInt(isLevelUp);
	}

	@Override
	public void deserialize(JoyBuffer in) {
		byte modelType=in.get();
		this.id=in.getInt();
		this.name=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.description=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.isOccupy=in.getInt();
		this.isInitial=in.getInt();
		this.category=in.getInt();
		this.ani=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.length=in.getInt();
		this.width=in.getInt();
		this.height=in.getInt();
		this.levelRequired=in.getInt();
		this.price=in.getInt();
		this.honerPrice=in.getInt();
		this.constructionTime=in.getInt();
		this.isUnique=in.getInt();
		this.canBeDestroyed=in.getInt();
		this.isLevelUp=in.getInt();
		
	}

//	public void print(){
//		System.out.println("id=="+getId());
//		System.out.println("name=="+getName());
//		System.out.println("description=="+getDescription());
//		System.out.println("isOccupy=="+getIsOccupy());
//		System.out.println("isInitial=="+getIsInitial());
//		System.out.println("category=="+getCategory());
//		System.out.println("ani=="+getAni());
//		System.out.println("length=="+getLength());
//		System.out.println("width=="+getWidth());
//		System.out.println("height=="+getHeight());
//		System.out.println("levelRequired=="+getLevelRequired());
//		System.out.println("price=="+getPrice());
//		System.out.println("honerPrice=="+getHonerPrice());
//		System.out.println("constructionTime=="+getConstructionTime());
//		System.out.println("isUnique=="+getIsUnique());
//		System.out.println("canBeDestroyed=="+getCanBeDestroyed());
//		System.out.println("isLevelUp=="+getIsLevelUp());
//	}

	
}
