package com.joymeng.game.cache.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.web.entity.OnlineNum;

/**
 * 用于分析玩家行为的临时变量
 * 
 * @author admin
 * 
 */
@Document
public class PlayerTemp {
	@Id
	String id;
	Date time;
	//玩家id
	long uid;
	//记录类型
	int questId;
	//记录内容
	byte questType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getQuestId() {
		return questId;
	}
	public void setQuestId(int questId) {
		this.questId = questId;
	}
	public byte getQuestType() {
		return questType;
	}
	public void setQuestType(byte questType) {
		this.questType = questType;
	}
	
	
}
