package com.joymeng.game.net.response;

import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.hero.Hero;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class HeroResp extends JoyResponse {
	byte type;
	Hero[] array;
	PlayerHero[] playerHero;
	byte result;
	byte isEject;//是否弹出
	int errorCode;
	public HeroResp() {
		super(ProcotolType.HERO_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.put(result);
		out.putInt(errorCode);
//		if(result==GameConst.GAME_RESP_FAIL){
//			return;
//		}
		out.put(type);
		
		switch(type){
		case ProcotolType.HERO_REFRESH://刷新将领
//			out.putInt(array.length);
//			for(int i=0;i<array.length;i++){
//				Hero hero=array[i];
//				hero.serialize(out);
//			}
			break;
		case ProcotolType.HERO_GET://获得将领
		case ProcotolType.HERO_LIST://将领列表
		case ProcotolType.HERO_DEL://删除将领
			out.put(isEject);
			break;
		case ProcotolType.HERO_LEVELUP://升级
		case ProcotolType.HERO_ADDSKILL://学习技能
		case ProcotolType.HERO_DELSKILL://删除技能
//			out.putInt(playerHero.length);
//			for(int i=0;i<playerHero.length;i++){
//				playerHero[i].serialize(out);
//			}
			break;
		case ProcotolType.HERO_EQUIP://装备
			break;
		case ProcotolType.HERO_UNEQUIP://卸下装备
			break;
		case ProcotolType.HERO_SOLDIER://带兵数
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
		result=in.get();
		errorCode=in.getInt();
		if(result==GameConst.GAME_RESP_FAIL){
			return;
		}
		type=in.get();
		int num=0;
		switch(type){
		case ProcotolType.HERO_REFRESH://刷新将领
//			num=in.getInt();
//			array=new Hero[num];
//			for(int i=0;i<num;i++){
//				Hero hero=new Hero();
//				hero.deserialize(in);
//				hero.print();
//				array[i]=hero;
//			}
			break;
		case ProcotolType.HERO_GET://获得将领
		case ProcotolType.HERO_LIST://将领列表
		case ProcotolType.HERO_DEL://删除将领
			
		case ProcotolType.HERO_ADDSKILL://学习技能
		case ProcotolType.HERO_DELSKILL://删除技能
//			num=in.getInt();
//			playerHero=new PlayerHero[num];
//			for(int i=0;i<num;i++){
//				PlayerHero pHero=new PlayerHero();
//				pHero.deserialize(in);
//				pHero.print();
//				playerHero[i]=pHero;
//			}
			break;
		case ProcotolType.HERO_EQUIP://装备
			break;
		case ProcotolType.HERO_UNEQUIP://卸下装备
			break;
		case ProcotolType.HERO_SOLDIER://带兵数
			break;
		case ProcotolType.HERO_LEVELUP://升级
			break;
		}
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public Hero[] getArray() {
		return array;
	}

	public void setArray(Hero[] array) {
		this.array = array;
	}

	public PlayerHero[] getPlayerHero() {
		return playerHero;
	}

	public void setPlayerHero(PlayerHero[] playerHero) {
		this.playerHero = playerHero;
	}

	public byte getResult() {
		return result;
	}

	public void setResult(byte result) {
		this.result = result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public byte getIsEject() {
		return isEject;
	}

	public void setIsEject(byte isEject) {
		this.isEject = isEject;
	}
	
	
	
}
