package com.joymeng.game.domain.flag;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 用户武将坐标类
 * 
 * @author Administrator
 * 
 */
public class HeroPoint extends ClientModuleBase {

	public PlayerHero hero;
	public FlagLattice fl;
	public int buff;

	public HeroPoint(PlayerHero hero, FlagLattice fl,int buff) {
		this.hero = hero;
		this.fl = fl;
		this.buff = buff;
	}

	public PlayerHero getHero() {
		return hero;
	}

	public void setHero(PlayerHero hero) {
		this.hero = hero;
	}

	public FlagLattice getFl() {
		return fl;
	}

	public void setFl(FlagLattice fl) {
		this.fl = fl;
	}
	
	public int getBuff() {
		return buff;
	}

	public void setBuff(int buff) {
		this.buff = buff;
	}


	@Override
	public byte getModuleType() {
		return NTC_HERO_POINT;
	}
	
	public String print() {
		StringBuffer s = new StringBuffer();
		if(fl != null){
			s.append("NTC_HERO_POINT====用户："+fl.getUserid()+"|格子点："+fl.getPoint()+"|将领："+getHero().getId()+"|城buff加成："+fl.getBuff()+"|buff加成："+getBuff()+"|士兵："+getFl().getSoinfo());
		}
		System.out.println(s.toString());
		return s.toString();
	}

	@Override
	public void _serialize(JoyBuffer out) {
		if(fl != null){
			out.putInt(fl.getUserid());
			out.putInt(getHero().getId());
			out.putInt(getBuff());
			out.put(getFl().getPoint());
			out.putInt(FightUtil.getSoldierNum(getFl().getSoinfo()));
			if(fl.isSee())
				out.put((byte)1);
			else
				out.put((byte)0);
		}else{
			out.putInt(0);
			out.putInt(0);
			out.putInt(0);
			out.put((byte)0);
			out.putInt(0);
			out.put((byte)0);
		}
		
		print();
		
	}
	
	public static void main(String[] args) {
		FightUtil.getSoldierNum("1:0,0;2:92,0");
	}

}
