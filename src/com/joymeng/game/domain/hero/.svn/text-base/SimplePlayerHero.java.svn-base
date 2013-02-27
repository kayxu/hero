package com.joymeng.game.domain.hero;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;

public class SimplePlayerHero extends  ClientModuleBase {
	PlayerHero hero;
	String heroInfo;
	public SimplePlayerHero(PlayerHero heros,String msg){
		this.hero = heros;
		this.heroInfo = msg;
	}
	
	@Override
	public byte getModuleType() {
		return ClientModule.NTC_SIMPLE_HERO;
	}
	
	public SimplePlayerHero(){
		
	}
	
	@Override
	public void _serialize(JoyBuffer out) {
		if(hero != null){
			out.putInt(hero.getId());
			out.putInt(hero.getUserId());
			out.put(hero.getSex());
			out.putInt(hero.getLevel());
			// 武器
			out.putInt(hero.getWeapon());
			// 铠甲
			out.putInt(hero.getArmour());
			// 头盔
			out.putInt(hero.getHelmet());
			// 马
			out.putInt(hero.getHorse());
		}else{
			if(!"".equals(heroInfo)){
				String[] heros = heroInfo.split("#");
				out.putInt(heros[0] ==null || "".equals(heros[0]) ? 0 : Integer.parseInt(heros[0]) );
				out.putInt(heros[1] ==null || "".equals(heros[1]) ? 0 : Integer.parseInt(heros[1]) );
				out.put(heros[2] ==null || "".equals(heros[2]) ? 0 : Byte.parseByte(heros[2]) );
				out.putInt(heros[3] ==null || "".equals(heros[3]) ? 0 : Integer.parseInt(heros[3]) );
				// 武器
				out.putInt(heros[4] ==null || "".equals(heros[4]) ? 0 : Integer.parseInt(heros[4]) );
				// 铠甲
				out.putInt(heros[5] ==null || "".equals(heros[5]) ? 0 : Integer.parseInt(heros[5]) );
				// 头盔
				out.putInt(heros[6] ==null || "".equals(heros[6]) ? 0 : Integer.parseInt(heros[6]) );
				// 马
				out.putInt(heros[7] ==null || "".equals(heros[7]) ? 0 : Integer.parseInt(heros[7]) );
			}
		}
	}

	@Override
	public void deserialize(JoyBuffer in) {
		// TODO Auto-generated method stub
		
	}
//	public static void main(String[] args) {
//		System.out.println(70406/10000);
//	}
	
}
