package com.joymeng.game.domain.question;

/**
 * 玩家联系客服的问题
 * @author 马缔
 *
 */
public class UserQuestion {

	private int id;
	
	/**
	 * 玩家id
	 */
	private long userid;
	
	/**
	 * 玩家留言内容
	 */
	private String content;
	
	/**
	 * 玩家留言时间
	 */
	private String createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
	
}
