package com.joymeng.core.db.cache.mongo.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.joymeng.core.base.net.response.ClientModuleBase;

/**
 * 购买物品的cache 在什么时间，哪个玩家，购买了哪种物品，多少个 可以查询每一个玩家的物品消耗情况， 可以查询每一个物品的购买情况
 * 
 * @author admin
 * 
 */
public class GoodsCache extends ClientModuleBase{
	@Id
	private String id;
	// 玩家id
	private int uid;
	//购买时间
	private Date timestamp;
	//物品id
	private int propId;
	//物品数量
	private int propNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
	}

	public int getPropNum() {
		return propNum;
	}

	public void setPropNum(int propNum) {
		this.propNum = propNum;
	}

}
