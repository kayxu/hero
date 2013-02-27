package com.joymeng.game.cache.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

@Document
public class PlayerCache extends ClientModuleBase {
	@Id
	private int userid;// 玩家id

	/**
	 * 玩家姓名
	 */
	private String name;

	/**
	 * 玩家等级
	 */
	private short level;

	/**
	 * 玩家角色（图标）
	 */
	private byte faction;

	/**
	 * 爵位0:平民/1:骑士/2:准男爵/3:男爵/4:子爵/5: 伯爵/6:侯爵/7:公爵
	 */
	private int cityLevel;

	/**
	 * 占领用户ID 没有默认为0
	 */
	private long occupyUserId;

	/**
	 * 占领将领ID
	 */
	private int officerId;

	/**
	 * 占领将领信息
	 */
	private String officerInfo;

	/**
	 * 索引，方便查找
	 */
	//private int index;
	
	/**
	 * 随机玩家时的随机字段
	 */
	private double ra;
	
	//主线任务id
	private int taskId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public byte getFaction() {
		return faction;
	}

	public void setFaction(byte faction) {
		this.faction = faction;
	}

	public int getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(int cityLevel) {
		this.cityLevel = cityLevel;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public long getOccupyUserId() {
		return occupyUserId;
	}

	public void setOccupyUserId(long occupyUserId) {
		this.occupyUserId = occupyUserId;
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

	/*public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}*/

	public double getRa() {
		return ra;
	}

	public void setRa(double ra) {
		this.ra = ra;
	}

	@Override
	public String toString() {
		return "PlayerCache [name=" + name + ", level=" + level + ", faction="
				+ faction + ", cityLevel=" + cityLevel + ", userid=" + userid
				+ ", occupyUserId=" + occupyUserId + ", officerId=" + officerId
				+ ", officerInfo=" + officerInfo + ", ra=" + ra + "]";
	}

	@Override
	public byte getModuleType() {
		return NTC_SIMPLE_ROLEDATA;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(getUserid());
		out.putShort(getLevel());
		out.putPrefixedString(getName(),(byte)2);
		out.put(getFaction());
		
		String aa = getOfficerInfo();
		if (!"".equals(aa) && aa.split("#").length > 0) {
			String[] heroCache = aa.split("#");
			out.putInt(heroCache[0] == null || "".equals(heroCache[0]) ? 0
					: Integer.parseInt(heroCache[0]));
		} else {
			out.putInt(0);
		}
		out.put((byte) getCityLevel());
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}
