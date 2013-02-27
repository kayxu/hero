package com.joymeng.game.domain.building;

import java.sql.Timestamp;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipModule;
import com.joymeng.services.core.buffer.JoyBuffer;


/**
 * 用户建筑
 * 
 * @author xufangliang
 * 
 */
public class PlayerBuilding extends ClientModuleBase implements TipModule{

	/**
	 *ID
	 */
	private int id;
	/**
	 * 用户ID
	 */
	private int userID ;
	//>>>>>>>>>>>>>>>>>playerBuild 
	
	/**
	 * 建筑等级
	 */
	private short buildLevel;
	
	/**
	 * 更新时间
	 */
	private Timestamp updateTime; 
	
	/**
	 * 建设时间
	 */
	private Timestamp constructionTime;
	/**
	 * 建造状态
	 */
	private byte buildType;
	/**
	 * 加成数量
	 */
	private int additionCount;
	/**
	 * 占领时间
	 */
	private Timestamp officerTime; 
	/**
	 * 占领将领ID
	 */
	private int officerId;
	/**
	 * 占领将领信息
	 */
	private String officerInfo;
	/**
	 * 占领用户ID 没有默认为0
	 */
	private long occupyUserId;
	/**
	 * 用户士兵信息 a,b,c,d;
	 */
	private String soldierMsg;
	private short x;
	private short y;
	private Timestamp addTime;//道具加成结束时间
	private int additionCountTime;//时效加成数量
	private int stealCount;
	private Timestamp chargeOutTime;//收取时间
	private Timestamp stealTime;//偷盗时间
	
	private String BuildMsg;//建筑信息 
	private int exp;
	private int oldLevel = 1;//等级
	private TipMessage tip;
	
	//>>>>>>>>>>>>>>>>>playerBuild 
	
	/**
	 * 建筑ID
	 */
	private int  buildingID;
	/**
	 * 初始次数
	 */
	private int InitialTimes;
	/**
	 * 刷新次数
	 */
	private int refreshTimes;
	/**
	 * @return GET the exp
	 */
	public int getExp() {
		return exp;
	}

	/**
	 * @param SET exp the exp to set
	 */
	public void setExp(int exp) {
		this.exp = exp;
	}

	/**
	 * 获取 chargeOutTime
	 * @return the chargeOutTime
	 */
	public Timestamp getChargeOutTime() {
		return chargeOutTime;
	}

	/**
	 * 设置 chargeOutTime
	 * @param chargeOutTime the chargeOutTime to set
	 */
	public void setChargeOutTime(Timestamp chargeOutTime) {
		this.chargeOutTime = chargeOutTime;
	}

	/**
	 * 获取 stealTime
	 * @return the stealTime
	 */
	public Timestamp getStealTime() {
		return stealTime;
	}

	/**
	 * 设置 stealTime
	 * @param stealTime the stealTime to set
	 */
	public void setStealTime(Timestamp stealTime) {
		this.stealTime = stealTime;
	}


	/**
	 * 是否可以被占领/驻防
	 */
	private int isOccupy;
	/**
	 * 建筑类型
	 */
	private int category;

	/**
	 * 价格
	 */
	private int price;
	/**
	 * 功勋价格
	 */
	private int honerPrice;
	
	/**
	 * 是否唯一
	 */
	private int isUnique;
	/**
	 * 能否拆除
	 */
	private int canBeDestroyed;
	
	/**
	 * 获取 stealCount
	 * @return the stealCount
	 */
	public int getStealCount() {
		return stealCount;
	}

	/**
	 * 设置 stealCount
	 * @param stealCount the stealCount to set
	 */
	public void setStealCount(int stealCount) {
		this.stealCount = stealCount;
	}


	public int getInitialTimes() {
		return InitialTimes;
	}

	public void setInitialTimes(int initialTimes) {
		InitialTimes = initialTimes;
	}

	public int getRefreshTimes() {
		return refreshTimes;
	}

	public void setRefreshTimes(int refreshTimes) {
		this.refreshTimes = refreshTimes;
	}


	/**
	 * 能否升级
	 */
	private int isLevelUp;
	
	/**
	 * 收益类型
	 */
	private byte inCometype;
	
	/**
	 * 收益数量
	 */
	private int inComeCount;
	
	/**
	 * 道具ID
	 */
	private int propsId;
	
	/**
	 * 对于操作需要的数值
	 */
	private int operatcount;
	
	public String getSoldierMsg() {
		return soldierMsg;
	}

	public void setSoldierMsg(String soldierMsg) {
		this.soldierMsg = soldierMsg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short getBuildLevel() {
		return buildLevel;
	}

	public void setBuildLevel(short buildLevel) {
		this.buildLevel = buildLevel;
	}

	public int getBuildingID() {
		return buildingID;
	}

	public void setBuildingID(int buildingID) {
		this.buildingID = buildingID;
	}

	/**
	 * 获取 addTime
	 * @return the addTime
	 */
	public Timestamp getAddTime() {
		return addTime;
	}

	/**
	 * 设置 addTime
	 * @param addTime the addTime to set
	 */
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	/**
	 * 获取 additionCountTime
	 * @return the additionCountTime
	 */
	public int getAdditionCountTime() {
		return additionCountTime;
	}

	/**
	 * 设置 additionCountTime
	 * @param additionCountTime the additionCountTime to set
	 */
	public void setAdditionCountTime(int additionCountTime) {
		this.additionCountTime = additionCountTime;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getIsOccupy() {
		return isOccupy;
	}

	public void setIsOccupy(int isOccupy) {
		this.isOccupy = isOccupy;
	}

	

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
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

	public byte getBuildType() {
		return buildType;
	}

	public void setBuildType(byte buildType) {
		this.buildType = buildType;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
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

	public byte getInCometype() {
		return inCometype;
	}
	
	/**
	 * @return GET the buildMsg
	 */
	public String getBuildMsg() {
		return BuildMsg;
	}

	/**
	 * @param SET buildMsg the buildMsg to set
	 */
	public void setBuildMsg(String buildMsg) {
		BuildMsg = buildMsg;
	}

	public void setInCometype(byte inCometype) {
		this.inCometype = inCometype;
	}

	public int getOfficerId() {
		return officerId;
	}

	public void setOfficerId(int officerId) {
		this.officerId = officerId;
	}

	public String getOfficerInfo() {
		return officerInfo;
	}

	public void setOfficerInfo(String officerInfo) {
		this.officerInfo = officerInfo;
	}

	public long getOccupyUserId() {
		return occupyUserId;
	}

	public void setOccupyUserId(long occupyUserId) {
		this.occupyUserId = occupyUserId;
	}

	public int getPropsId() {
		return propsId;
	}

	public void setPropsId(int propsId) {
		this.propsId = propsId;
	}

	public int getOperatcount() {
		return operatcount;
	}

	public void setOperatcount(int operatcount) {
		this.operatcount = operatcount;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public Timestamp getConstructionTime() {
		return constructionTime;
	}

	public void setConstructionTime(Timestamp constructionTime) {
		this.constructionTime = constructionTime;
	}
	public int getInComeCount() {
		return inComeCount;
	}

	public void setInComeCount(int inComeCount) {
		this.inComeCount = inComeCount;
	}

	/**
	 * 获取 additionCount
	 * @return the additionCount
	 */
	public int getAdditionCount() {
		return additionCount;
	}

	/**
	 * 设置 additionCount
	 * @param additionCount the additionCount to set
	 */
	public void setAdditionCount(int additionCount) {
		this.additionCount = additionCount;
	}

	/**
	 * 获取 officerTime
	 * @return the officerTime
	 */
	public Timestamp getOfficerTime() {
		return officerTime;
	}

	/**
	 * 设置 officerTime
	 * @param officerTime the officerTime to set
	 */
	public void setOfficerTime(Timestamp officerTime) {
		this.officerTime = officerTime;
	}
	

	/**
	 * 获取 x
	 * @return the x
	 */
	public short getX() {
		return x;
	}

	/**
	 * 设置 x
	 * @param x the x to set
	 */
	public void setX(short x) {
		this.x = x;
	}

	/**
	 * 获取 y
	 * @return the y
	 */
	public short getY() {
		return y;
	}
	
	public int getOldLevel() {
		return oldLevel;
	}

	public void setOldLevel(int oldLevel) {
		this.oldLevel = oldLevel;
	}

	/**
	 * 设置 y
	 * @param y the y to set
	 */
	public void setY(short y) {
		this.y = y;
	}


	/**
	 * 保留字段1
	 */
	private String remark1;
	
	private String remark2;
	
	private String remark3;

	@Override
	public byte getModuleType() {
		return NTC_PLAY_BUILDING;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putInt(userID);
		out.putInt(buildLevel);
		out.putInt(buildingID);
		//时间格式 TImeStamp
		out.putLong(updateTime.getTime());
		out.putLong(constructionTime.getTime());
		out.putShort(x);
		out.putShort(y);
		out.putInt(officerId);
		out.putPrefixedString(officerInfo,(byte)2);
		out.putLong(occupyUserId);
		out.putInt(operatcount);
		out.putInt(inComeCount);
		out.putInt(additionCount+additionCountTime);
		out.putShort(Short.parseShort(Integer.toHexString(getOperatcount()),16));
		out.putInt((int)(stealTime.getTime()/1000));
		out.putInt(refreshTimes);
		if(occupyUserId == 0){
//			String my = GameUtils.getFromCache(userID,"role_");
			PlayerCache my  = GameUtils.getFromCache(userID);
			if(my != null ){
				out.putPrefixedString(my.getName(),(byte)2);
			}else{
				out.putPrefixedString("",(byte)2);
			}
		}else{
//			String other = GameUtils.getFromCache((int)occupyUserId,"role_");
			PlayerCache other  = GameUtils.getFromCache((int)occupyUserId);
			if(other != null){
				out.putPrefixedString(other.getName(),(byte)2);
			}else{
				out.putPrefixedString("",(byte)2);
			}
		}
		out.putPrefixedString(getRemark3(),(byte)2);
	}

	@Override
	public void deserialize(JoyBuffer in) {
		byte modelType=in.get();
		this.id=in.getInt();
		this.buildLevel=in.getShort();
		this.buildingID=in.getInt();
		this.userID=in.getInt();
		this.isOccupy=in.getInt();
		this.category=in.getInt();
		this.x=in.getShort();
		this.y=in.getShort();
		this.price=in.getInt();
		this.honerPrice=in.getInt();
		this.updateTime=TimeUtils.addSecond(in.getLong(), 0);
		this.constructionTime=TimeUtils.addSecond(in.getLong(), 0);
		this.buildType=in.get();
		this.isUnique=in.getInt();
		this.canBeDestroyed=in.getInt();
		this.isLevelUp=in.getInt();
		this.inCometype=in.get();
		this.inComeCount=in.getInt();
		this.officerId=in.getInt();
		this.officerInfo=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.occupyUserId=in.getLong();
		this.propsId=in.getInt();
		this.operatcount=in.getInt();
		this.remark1=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.remark2=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.remark3=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
	}
	
//	public void print(){
//		System.out.println("id=="+getId());
//		System.out.println("buildLevel=="+getBuildLevel());
//		System.out.println("buildingID=="+getBuildingID());
//		System.out.println("userID=="+getUserID());
//		System.out.println("isOccupy=="+getIsOccupy());
//		System.out.println("category=="+getCategory());
//		System.out.println("x=="+getX());
//		System.out.println("y=="+getY());
//		System.out.println("price=="+getPrice());
//		System.out.println("honerPrice=="+getHonerPrice());
//		System.out.println("updateTime=="+getUpdateTime());
//		System.out.println("constructionTime=="+getConstructionTime());
//		System.out.println("buildType=="+getBuildType());
//		System.out.println("isUnique=="+getIsUnique());
//		System.out.println("canBeDestroyed=="+getCanBeDestroyed());
//		System.out.println("isLevelUp=="+getIsLevelUp());
//		System.out.println("inCometype=="+getInCometype());
//		System.out.println("inComeCount=="+getInComeCount());
//		System.out.println("officerId=="+getOfficerId());
//		System.out.println("officerInfo=="+getOfficerInfo());
//		System.out.println("occupyUserId=="+getOccupyUserId());
//		System.out.println("propsId=="+getPropsId());
//		System.out.println("operatcount=="+getOperatcount());
//		System.out.println("remark1=="+getRemark1());
//		System.out.println("remark2=="+getRemark2());
//		System.out.println("remark3=="+getRemark3());
//	}

	@Override
	public TipMessage getTip() {
		return this.tip;
	}

	@Override
	public void setTip(TipMessage tip) {
		this.tip = tip;
		
	}

}
