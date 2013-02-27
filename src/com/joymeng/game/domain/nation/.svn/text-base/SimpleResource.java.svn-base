package com.joymeng.game.domain.nation;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.services.core.buffer.JoyBuffer;

public class SimpleResource extends ClientModuleBase {
	int myVeins;//我占领的科技个数
	int stateVeins;//本州科技个数
	PlayerCharacter data;
	GoldMine gold;//我的金矿
	int basic;//基本加成
	int mili;//军功
	int miliAdd;//军功加成
	int stateMili;//本州全部军功
	byte page;//区
	int interval = 0;
	byte type;//类型
	public SimpleResource(PlayerCharacter data,GoldMine gold,int my,int state,int basic,byte page,byte type){
		this.data = data;
		this.gold = gold;
		this.myVeins = my;
		this.stateVeins = state;
		this.basic = basic;
		this.page = page;
		this.mili = data.getResourcesData(GameConfig.MEDALS);
		this.miliAdd = (int)((NationManager.getInstance().armyAdd.get(data.getId()) == null ? 0 : NationManager.getInstance().armyAdd.get(data.getId()))*1000);
		this.stateMili =NationManager.getInstance().getSatateMilitary(data.getStateId());
		setInterval(data, gold);
		this.type = type;
	}
	public void setInterval(PlayerCharacter data,GoldMine gold){
		if(gold != null && gold.getHeroId() != 0){
			PlayerHero hero = data.getPlayerHeroManager().getHero(gold.getHeroId());
			if(hero != null){
				GoldHero gh = NationManager.getInstance().allGoldHero.get((int)(hero.getLevel()*10+gold.getType()));
				this.interval = gh.getInterval();
			}
		}
//		GoldHero gh = allGoldHero.get((int)(level*10+type));
	}
	public int getMyVeins() {
		return myVeins;
	}
	
	public void setMyVeins(int myVeins) {
		this.myVeins = myVeins;
	}
	@Override
	public byte getModuleType() {
		return NTC_SIMPLERESOURCE;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		if(gold == null){
			out.putInt(0);
		}else{
			out.putInt((int)(gold.getIntervalTime()/1000)+ 6*60*60);
		}
		out.putInt(myVeins);
		out.putInt(basic);
		out.putInt((stateVeins));
		out.putInt(page);
		out.putInt(myVeins);
		out.putInt(mili);
		out.putInt(miliAdd);
		out.putInt(stateMili);
		out.putInt((int)((1+(stateVeins)+myVeins)*1000) + miliAdd);
		out.putInt(interval);
		out.put(type);
	}

}
