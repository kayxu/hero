package com.joymeng.game.domain.role;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Power extends ClientModuleBase implements Comparable{
	Nation n;//区域
	int powerPoint = 0;//对应点数
	int achive = 0;//政绩
	public Nation getN() {
		return n;
	}
	public void setN(Nation n) {
		this.n = n;
	}
	public int getPowerPoint() {
		return powerPoint;
	}
	public void setPowerPoint(int powerPoint) {
		this.powerPoint = powerPoint;
	}
	
	public int getAchive() {
		return achive;
	}
	public void setAchive(int achive) {
		this.achive = achive;
	}
	@Override
	public byte getModuleType() {
		return NTC_POWER;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putPrefixedString(n.getName(), (byte)2);
		PlayerCache pc = GameUtils.getFromCache(n.getOccupyUser());
		if(pc != null){
			out.putPrefixedString(pc.getName(), (byte)2);
			out.putInt(getPowerPoint());
			out.putInt(getAchive());
		}else{
			out.putPrefixedString("", (byte)2);
			out.putInt(0);
			out.putInt(0);
		}
	}
	@Override
	public int compareTo(Object o) {
		if(o instanceof Power){
			Power p = (Power)o;
			int cmp =  p.getPowerPoint() - getPowerPoint();
			if(cmp == 0){
				return getN().getId() - p.getN().getId() ;
			}
			return cmp;
		}
		return 0;
	}
}
