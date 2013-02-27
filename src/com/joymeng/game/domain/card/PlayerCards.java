package com.joymeng.game.domain.card;

/**
 * 玩家牌局，供数据持久化
 * @author madi
 *
 */
public class PlayerCards {
	
	private int id;
	
	/**
	 * 玩家id
	 */
	private int userid;
	
	/**
	 * 牌面面值
	 */
	private String faces;
	
	/**
	 * 牌面在牌局中的下标
	 */
	private String indexes;
	
	/**
	 * 牌面抽中的奖品
	 */
	private String what;
	
	/**
	 * 牌面奖品数量
	 */
	private String values;
	
	/**
	 * 玩家翻牌机会
	 */
	private int flipChance;
	
	/**
	 * 玩家可旋转次数
	 */
	private int rotateChance;
	
	/**
	 * 第几次获得翻牌机会增加
	 */
	private int turnsForChance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getFaces() {
		return faces;
	}

	public void setFaces(String faces) {
		this.faces = faces;
	}

	public String getIndexes() {
		return indexes;
	}

	public void setIndexes(String indexes) {
		this.indexes = indexes;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public int getFlipChance() {
		return flipChance;
	}

	public void setFlipChance(int flipChance) {
		this.flipChance = flipChance;
	}

	public int getRotateChance() {
		return rotateChance;
	}

	public void setRotateChance(int rotateChance) {
		this.rotateChance = rotateChance;
	}

	public int getTurnsForChance() {
		return turnsForChance;
	}

	public void setTurnsForChance(int turnsForChance) {
		this.turnsForChance = turnsForChance;
	}
	
	
	
	

}
