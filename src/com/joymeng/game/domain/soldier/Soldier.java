package com.joymeng.game.domain.soldier;

import java.io.InputStream;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 兵种的固化数据
 * @author admin
 * @date 2012-5-4
 */
public class Soldier  extends ClientModuleBase{
	int id;
	String name;
	String aniId;
	byte type;//兵种类型
	int attack;
	int defence;
	int hp;
//	byte actionPriority;//行动优先级别
	String targetPriority;//选择目标优先级别
	byte attackDistance;//攻击距离
	int equipNum=0;//兵装数量
	int num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getDefence() {
		return defence;
	}
	public void setDefence(int defence) {
		this.defence = defence;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
//	public byte getActionPriority() {
//		return actionPriority;
//	}
//	public void setActionPriority(byte actionPriority) {
//		this.actionPriority = actionPriority;
//	}
	
	public String getTargetPriority() {
		return targetPriority;
	}
	public void setTargetPriority(String targetPriority) {
		this.targetPriority = targetPriority;
	}
	public byte getAttackDistance() {
		return attackDistance;
	}
	public void setAttackDistance(byte attackDistance) {
		this.attackDistance = attackDistance;
	}
	@Override
	public byte getModuleType() {
		return NTC_SOLDIER;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(name,JoyBuffer.STRING_TYPE_SHORT);
		out.put(type);
		out.putInt(attack);
		out.putInt(defence);
		out.putInt(hp);
		out.put(this.type);//行动优先级
		out.putPrefixedString(targetPriority,JoyBuffer.STRING_TYPE_SHORT);
		out.put(attackDistance);
	}
	@Override
	public void deserialize(JoyBuffer in) {
		byte modelType=in.get();
		this.id=in.getInt();
		this.name=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.aniId=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.type=in.get();
		this.attack=in.getInt();
		this.defence=in.getInt();
		this.hp=in.getInt();
		in.get();//行动优先级
		this.targetPriority=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.attackDistance=in.get();
	}
	/**
	 * 获得该类兵种的目标优先级别
	 * @return
	 */
	public String[] targetPriority(){
		return targetPriority.split(",");
	}
	
	/**
	 * 是否是特种兵
	 * @return
	 */
	public boolean isSpecialSoldier(){
		return getType() == 4;
	}
	public String getAniId() {
		return aniId;
	}
	public void setAniId(String aniId) {
		this.aniId = aniId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getEquipNum() {
		return equipNum;
	}
	public void setEquipNum(int equipNum) {
		this.equipNum = equipNum;
	}
	
}
