package com.joymeng.web.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 在线时间统计
 * 
 * @author admin
 * 
 */
public class OnlineTime {
    private Date timestamp;
	private int uid;//玩家id
	private long online;//在线时间
	private int heartNum;//接受到心跳的次数
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public long getOnline() {
		return online;
	}
	public void setOnline(long online) {
		this.online = online;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getHeartNum() {
		return heartNum;
	}
	public void setHeartNum(int heartNum) {
		this.heartNum = heartNum;
	}
	
}
