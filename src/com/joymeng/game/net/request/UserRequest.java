package com.joymeng.game.net.request;

import com.joymeng.game.ProcotolType;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class UserRequest extends JoyRequest {
	int userId;
	byte type;
	String cityName;
	byte countryId;
	byte stateId;
	byte cityId;
	int heroId;
	int pId;
	byte tt;
	byte level;
	byte charge;
	
	
	//用户类型
	byte userType;
	
	//玩家名称
	String username;

	public UserRequest() {
		super(ProcotolType.USER_INFO_REQ);
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		type=in.get();
//		userId = in.getInt();
		switch (type) {
		case ProcotolType.USER_MOTIFY_NAME:
			cityName = in.getPrefixedString((byte)2);
			break;
		case ProcotolType.USER_RANDOM:
			heroId = in.getInt();
			break;
		case ProcotolType.USER_ENTER:
			userId = in.getInt();
			break;
		case ProcotolType.OFFER_ZHUCHENG:
			pId = in.getInt();
			heroId = in.getInt();
			userId = in.getInt();
			break;
		case ProcotolType.OFFER_CHEFANG:
			tt = in.get();
			pId = in.getInt();
			break;
		case ProcotolType.LADDER_RESET:
			level=in.get();
			charge=in.get();
			break;
		case ProcotolType.USER_TYPE:
			userType = in.get();
			break;
		case ProcotolType.USER_COMMIT:
			userType = in.get();
			username = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
			pId = in.getInt();
			level = in.get();//是否推荐玩家
		case ProcotolType.ARENA_MESSAGE:
			break;
		case ProcotolType.UP_PROMOTED:
			break;
		case ProcotolType.COUNTRY_NATION:
			pId = in.getInt();
			break;
		case ProcotolType.PLAYER_HALT:
			stateId=in.get();
			break;
		}

	}

	@Override
	protected void _serialize(JoyBuffer out) {
		out.put(type);
//		out.putInt(userId);
		switch (type) {
		case ProcotolType.USER_MOTIFY_NAME:
			out.putPrefixedString(cityName, (byte)2);
			break;
		case ProcotolType.USER_RANDOM:
			out.putInt(heroId);
			break;
		case ProcotolType.USER_ENTER:
			out.putInt(userId);
			break;
		case ProcotolType.OFFER_ZHUCHENG:
			out.putInt(pId);
			out.putInt(heroId);
			out.putInt(userId);
			break;
		case ProcotolType.OFFER_CHEFANG:
			out.put(tt);
			out.putInt(pId);
			break;
		case ProcotolType.LADDER_RESET:
			out.put(level);
			out.put(charge);
			break;
		case ProcotolType.USER_TYPE:
			out.put(userType);
			break;
		case ProcotolType.USER_COMMIT:
			out.put(userType);
			out.putPrefixedString(username, JoyBuffer.STRING_TYPE_SHORT);
			out.putInt(pId);
			out.put(level);
		case ProcotolType.ARENA_MESSAGE:
			break;
		case ProcotolType.UP_PROMOTED:
			break;
		case ProcotolType.COUNTRY_NATION:
			out.putInt(pId);
			break;
		case ProcotolType.PLAYER_HALT:
			out.put(stateId);
			break;
		}
//		if(type!=0){
//			out.putPrefixedString(cityName,(byte)2);
//			out.put(countryId);
//			out.put(cityId);
//			out.put(stateId);
//		}
	
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public byte getCountryId() {
		return countryId;
	}

	public void setCountryId(byte countryId) {
		this.countryId = countryId;
	}

	public byte getStateId() {
		return stateId;
	}

	public void setStateId(byte stateId) {
		this.stateId = stateId;
	}

	public byte getCityId() {
		return cityId;
	}

	public void setCityId(byte cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return GET the heroId
	 */
	public int getHeroId() {
		return heroId;
	}

	/**
	 * @param SET heroId the heroId to set
	 */
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	/**
	 * @return GET the pId
	 */
	public int getpId() {
		return pId;
	}

	/**
	 * @return GET the tt
	 */
	public byte getTt() {
		return tt;
	}

	/**
	 * @param SET tt the tt to set
	 */
	public void setTt(byte tt) {
		this.tt = tt;
	}

	/**
	 * @param SET pId the pId to set
	 */
	public void setpId(int pId) {
		this.pId = pId;
	}

	public byte getLevel() {
		return level;
	}

	public void setLevel(byte level) {
		this.level = level;
	}

	public byte getCharge() {
		return charge;
	}

	public void setCharge(byte charge) {
		this.charge = charge;
	}
	
	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
