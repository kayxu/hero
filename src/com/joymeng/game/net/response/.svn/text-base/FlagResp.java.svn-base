package com.joymeng.game.net.response;

import java.util.Map;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.flag.HeroPoint;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyResponse;

public class FlagResp extends JoyResponse {
	byte type;
	byte result;
	int errorCode;
	byte isSignType;
	Map<Byte, ClientModuleBase> all;
	Map<HeroPoint, Integer> heroBuffA;
	Map<HeroPoint, Integer> heroBuffB;
	
	public FlagResp() {
		super(ProcotolType.FLAG_RESP);
	}

	@Override
	protected void _serialize(JoyBuffer out) {
		// todo
		out.put(result);
		out.putInt(errorCode);
		out.put(type);
		switch (type) {
		case ProcotolType.SIGN_UP://报名
			out.put(isSignType);
			break;
		case ProcotolType.SIGN_QUIT://退出
			break;
		case ProcotolType.START_GAME_FIGHT:
			break;
		case ProcotolType.CAPTURE_FLAG://夺旗
			break;
		case ProcotolType.REFRESH_MOBILITY://刷新积分
			break;
		case ProcotolType.QUIT_ROOM://结束
			break;
		case ProcotolType.MOVE_POINT://可移动点
			if(all != null){
				out.putInt(all.size());
				for(Byte b : all.keySet()){
					out.put(b);
				}
			}else{
				out.putInt(-1);
			}
			break;
		case ProcotolType.HERO_BUFF:
			if(heroBuffA != null){
				out.putInt(heroBuffA.size());
				for(HeroPoint b : heroBuffA.keySet()){
					if(b != null){
						out.putInt(b.getHero().getId());
						out.putPrefixedString(b.getHero().getIcon(),(byte)2);
						out.putInt(heroBuffA.get(b));
						out.put(b.getFl().getPoint());
						out.putInt(FightUtil.getSoldierNum(b.getFl().getSoinfo()));
					}else{
						out.putInt(-1);
					}
				}
			}else{
				out.putInt(0);
			}
			if(heroBuffB != null){
				out.putInt(heroBuffB.size());
				for(HeroPoint b : heroBuffB.keySet()){
					if(b != null){
						out.putInt(b.getHero().getId());
						out.putPrefixedString(b.getHero().getIcon(),(byte)2);
						out.putInt(heroBuffB.get(b));
						out.put(b.getFl().getPoint());
						out.putInt(FightUtil.getSoldierNum(b.getFl().getSoinfo()));
					}else{
						out.putInt(-1);
					}
				}
			}else{
				out.putInt(0);
			}
			break;
		}
	}

	@Override
	protected void _deserialize(JoyBuffer in) {
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

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public byte getIsSignType() {
		return isSignType;
	}

	public void setIsSignType(byte isSignType) {
		this.isSignType = isSignType;
	}

	public Map<Byte, ClientModuleBase> getAll() {
		return all;
	}

	public void setAll(Map<Byte, ClientModuleBase> all) {
		this.all = all;
	}

	public Map<HeroPoint, Integer> getHeroBuffA() {
		return heroBuffA;
	}

	public void setHeroBuffA(Map<HeroPoint, Integer> heroBuffA) {
		this.heroBuffA = heroBuffA;
	}

	public Map<HeroPoint, Integer> getHeroBuffB() {
		return heroBuffB;
	}

	public void setHeroBuffB(Map<HeroPoint, Integer> heroBuffB) {
		this.heroBuffB = heroBuffB;
	}
	
}
