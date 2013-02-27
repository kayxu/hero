package com.joymeng.game.domain.role;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class PlayerAche extends ClientModuleBase{
	int myOfficial;//我的数据
	int myFeat;
	int myAdd;
	
	String kingName ="";//国王数据
	int kingFeat;
	int kingAdd;
	
	String stateName = "";//州长数据
	int stateFeat;
	int stateAdd;
	
	String cityName = "";//市长数据
	int cityFeat;
	int cityAdd;
	
	String townName ="";//县长数据
	int townFeat;
	int townAdd;
	
	int prosperity = 0;
	
	
	public int getProsperity() {
		return prosperity;
	}

	public void setProsperity(int prosperity) {
		this.prosperity = prosperity;
	}

	public int getMyOfficial() {
		return myOfficial;
	}

	public void setMyOfficial(int myOfficial) {
		this.myOfficial = myOfficial;
	}

	public int getMyFeat() {
		return myFeat;
	}

	public void setMyFeat(int myFeat) {
		this.myFeat = myFeat;
	}

	public int getMyAdd() {
		return myAdd;
	}

	public void setMyAdd(int myAdd) {
		this.myAdd = myAdd;
	}

	public String getKingName() {
		return kingName;
	}

	public void setKingName(String kingName) {
		this.kingName = kingName;
	}

	public int getKingFeat() {
		return kingFeat;
	}

	public void setKingFeat(int kingFeat) {
		this.kingFeat = kingFeat;
	}

	public int getKingAdd() {
		return kingAdd;
	}

	public void setKingAdd(int kingAdd) {
		this.kingAdd = kingAdd;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getStateFeat() {
		return stateFeat;
	}

	public void setStateFeat(int stateFeat) {
		this.stateFeat = stateFeat;
	}

	public int getStateAdd() {
		return stateAdd;
	}

	public void setStateAdd(int stateAdd) {
		this.stateAdd = stateAdd;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityFeat() {
		return cityFeat;
	}

	public void setCityFeat(int cityFeat) {
		this.cityFeat = cityFeat;
	}

	public int getCityAdd() {
		return cityAdd;
	}

	public void setCityAdd(int cityAdd) {
		this.cityAdd = cityAdd;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public int getTownFeat() {
		return townFeat;
	}

	public void setTownFeat(int townFeat) {
		this.townFeat = townFeat;
	}

	public int getTownAdd() {
		return townAdd;
	}

	public void setTownAdd(int townAdd) {
		this.townAdd = townAdd;
	}

	@Override
	public byte getModuleType() {
		return NTC_PLAYERACHE;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putPrefixedString(String.valueOf(myOfficial),(byte)2);
		out.putPrefixedString(String.valueOf(myFeat),(byte)2);
		out.putPrefixedString(String.valueOf(myAdd)+"%",(byte)2);
		
		out.putPrefixedString(kingName,(byte)2);
		out.putPrefixedString(String.valueOf(kingFeat),(byte)2);
		out.putPrefixedString(String.valueOf(kingAdd)+"%",(byte)2);
		
		out.putPrefixedString(stateName,(byte)2);
		out.putPrefixedString(String.valueOf(stateFeat),(byte)2);
		out.putPrefixedString(String.valueOf(stateAdd)+"%",(byte)2);
		
		out.putPrefixedString(cityName,(byte)2);
		out.putPrefixedString(String.valueOf(cityFeat),(byte)2);
		out.putPrefixedString(String.valueOf(cityAdd)+"%",(byte)2);
		
		out.putPrefixedString(townName,(byte)2);
		out.putPrefixedString(String.valueOf(townFeat),(byte)2);
		out.putPrefixedString(String.valueOf(townAdd)+"%",(byte)2);
	}

//	public void print(){
//		System.out.println("myOfficial=="+getMyOfficial());
//		System.out.println("myFeat=="+getMyFeat());
//		System.out.println("myAdd=="+getMyAdd());
//		System.out.println("kingName=="+getKingName());
//		System.out.println("kingFeat=="+getKingFeat());
//		System.out.println("kingAdd=="+getKingAdd());
//		System.out.println("stateName=="+getStateName());
//		System.out.println("stateFeat=="+getStateFeat());
//		System.out.println("stateAdd=="+getStateAdd());
//		System.out.println("cityName=="+getCityName());
//		System.out.println("cityFeat=="+getCityFeat());
//		System.out.println("cityAdd=="+getCityAdd());
//		System.out.println("townName=="+getTownName());
//		System.out.println("townFeat=="+getTownFeat());
//		System.out.println("townAdd=="+getTownAdd());
//	}

}
