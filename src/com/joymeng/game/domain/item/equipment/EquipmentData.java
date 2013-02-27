package com.joymeng.game.domain.item.equipment;

/**
 * 装备基本数据
 * @author xufangliang
 *  1.1
 */
public class EquipmentData {
	
	int id;//D
	byte position;//部位
	byte eLevel;//装备等级
	int attackPiont;//攻击
	int defensePiont;//防御
	int liftPiont;//生命
	int soldierCount;//带兵数
	
	
	/**
	 * 获取 id
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置 id
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取 position
	 * @return the position
	 */
	public byte getPosition() {
		return position;
	}
	/**
	 * 设置 position
	 * @param position the position to set
	 */
	public void setPosition(byte position) {
		this.position = position;
	}
	/**
	 * 获取 eLevel
	 * @return the eLevel
	 */
	public byte geteLevel() {
		return eLevel;
	}
	/**
	 * 设置 eLevel
	 * @param eLevel the eLevel to set
	 */
	public void seteLevel(byte eLevel) {
		this.eLevel = eLevel;
	}
	
	/**
	 * 获取 attackPiont
	 * @return the attackPiont
	 */
	public int getAttackPiont() {
		return attackPiont;
	}
	/**
	 * 设置 attackPiont
	 * @param attackPiont the attackPiont to set
	 */
	public void setAttackPiont(int attackPiont) {
		this.attackPiont = attackPiont;
	}
	/**
	 * 获取 defensePiont
	 * @return the defensePiont
	 */
	public int getDefensePiont() {
		return defensePiont;
	}
	/**
	 * 设置 defensePiont
	 * @param defensePiont the defensePiont to set
	 */
	public void setDefensePiont(int defensePiont) {
		this.defensePiont = defensePiont;
	}
	/**
	 * 获取 liftPiont
	 * @return the liftPiont
	 */
	public int getLiftPiont() {
		return liftPiont;
	}
	/**
	 * 设置 liftPiont
	 * @param liftPiont the liftPiont to set
	 */
	public void setLiftPiont(int liftPiont) {
		this.liftPiont = liftPiont;
	}
	/**
	 * 获取 soldierCount
	 * @return the soldierCount
	 */
	public int getSoldierCount() {
		return soldierCount;
	}
	/**
	 * 设置 soldierCount
	 * @param soldierCount the soldierCount to set
	 */
	public void setSoldierCount(int soldierCount) {
		this.soldierCount = soldierCount;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
//	@Override
//	public String toString() {
//		return "EquipmentData [id=" + id + ", position=" + position
//				+ ", eLevel=" + eLevel + ", attackPiont=" + attackPiont
//				+ ", defensePiont=" + defensePiont + ", liftPiont=" + liftPiont
//				+ ", soldierCount=" + soldierCount + "]";
//	}
	
}
