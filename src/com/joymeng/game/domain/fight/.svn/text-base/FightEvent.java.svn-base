package com.joymeng.game.domain.fight;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.services.core.buffer.JoyBuffer;
/**
 * 战斗日志
 * @author admin
 * @date 2012-6-1
 * TODO
 */
@Document
public class FightEvent extends ClientModuleBase implements Comparable<FightEvent> {
	@Id
	String id;
	int userId;
	int heroId;
	String memo;
	byte isAttack;//攻击方0;防守方1
	/**
	 * 战斗记录数据
	 */
	String data;
	
	/**
	 * 战斗开始时间
	 */
//	long fightTime;
	Date date;
	
	/**
	 * 战斗类型
	 */
	byte type;
	
	/**
	 * 战斗结果
	 */
	byte result;
	int index;//战报索引---发送给客户端的id修改为索引
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	boolean isNew;//是否是最新加入的


	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		return NTC_FIGHTEVENT;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(index);
		//out.putInt(userId);
		//out.putInt(heroId);
		out.putPrefixedString(TimeUtils.getTime(date.getTime()).format(TimeUtils.FORMAT1),JoyBuffer.STRING_TYPE_SHORT);
		out.putPrefixedString(memo,JoyBuffer.STRING_TYPE_SHORT);
		out.put(isAttack);
//		out.putPrefixedString(data,JoyBuffer.STRING_TYPE_SHORT);
		//out.putLong(fightTime);
		//out.put(type);
		//out.put(result);

	}

	@Override
	public void deserialize(JoyBuffer in) {
		//byte modelType = in.get();
//		this.index = in.getInt();
		//this.userId = in.getInt();
		//this.heroId = in.getInt();
//		this.memo = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
//		this.data = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
//		this.fightTime = in.getLong();
		//this.type = in.get();
		//this.result = in.get();

	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public byte getIsAttack() {
		return isAttack;
	}

	public void setIsAttack(byte isAttack) {
		this.isAttack = isAttack;
	}

	@Override
	public int compareTo(FightEvent o) {
		return (int)(o.getDate().getTime()-this.getDate().getTime());
	}

}
