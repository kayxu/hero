package com.joymeng.core.chat;


public class Notice {
	//id
	private int id;
	private byte type;
  // 公告内容
  private String content;
  //发送方的id和名称
  private int playerId;
  private String name;
  private byte icon;
  private short level;
  private byte cityLevel;
  //接受方的id
  private int receiveId;
  /**
   * 循环公告
   * @param type
   */
  public Notice(byte _type,String content) {
  	this.type =_type;
  	this.content = content;
//  	this.sendTimes = _sendTimes;
  }
  
  
	public String getContent() {
		return content;
	}
	
	
	public byte getType() {
		return type;
	}
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	public void setType(byte type) {
		this.type = type;
	}


	public int getPlayerId() {
		return playerId;
	}


	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public byte getIcon() {
		return icon;
	}


	public void setIcon(byte icon) {
		this.icon = icon;
	}


	public short getLevel() {
		return level;
	}


	public void setLevel(short level) {
		this.level = level;
	}



	public void setContent(String content) {
		this.content = content;
	}


	public byte getCityLevel() {
		return cityLevel;
	}


	public void setCityLevel(byte cityLevel) {
		this.cityLevel = cityLevel;
	}


	public int getReceiveId() {
		return receiveId;
	}


	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}


	
}
