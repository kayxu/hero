package com.joymeng.core.chat;
/**
 * 发送给玩家的 留言、私信、系统 消息
 * @author zhoujiqian 2012-4-26
 */
public class Message {
	private byte type;//消息类型 0：普通消息   1：邀请消息  2：系统消息
	private byte status;//状态     由用户中心定义
	private byte resultCode;//结果   由用户中心定义
	private int fromId;//发送者id
	private String fromName;//发送者昵称
	private String data;//消息内容
	private String datetime;//发送时间
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public byte getResultCode() {
		return resultCode;
	}
	public void setResultCode(byte resultCode) {
		this.resultCode = resultCode;
	}

	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

}
