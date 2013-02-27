package com.joymeng.game.domain.fight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 技能的特殊效果
 * @author admin
 * @date 2012-6-13
 * TODO
 */
public class FightSpecial {
	static Logger logger = LoggerFactory.getLogger(FightSpecial.class);
	byte type;//1麻痹，n回合内部能行动 2格挡 3闪避 4回血 5燃烧
	byte counter;
	int p1;//效果
	int p2;//skillid
	public FightSpecial(byte t,byte c){
		this.type=t;
		this.counter=c;
//		logger.info("初始化 特殊技能效果，type="+type+ " 持续回合="+this.counter);
	}
	public void effect(){
		this.counter--;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public byte getCounter() {
		return counter;
	}
	public void setCounter(byte counter) {
		this.counter = counter;
	}
	public int getP1() {
		return p1;
	}
	public void setP1(int p1) {
		this.p1 = p1;
	}
	
	public int getP2() {
		return p2;
	}
	public void setP2(int p2) {
		this.p2 = p2;
	}

		// 特殊效果
	//1麻痹，n回合内部能行动 2格挡 3闪避 4回血 5燃烧 6反弹
		public static final byte FIGHT_EFFECT_SLEEP = 1;
		public static final byte FIHGT_EFFECT_BLOCK = 2;
		public static final byte FIHGT_EFFECT_DODGE = 3;
		public static final byte FIGHT_EFFECT_RESUMEHP = 4;
		public static final byte FIGHT_EFFECT_BURN = 5;
		public static final byte FIGHT_EFFECT_BOUNCE = 6;
		public static final byte FIGHT_EFFECT_FIRST=7;//将战必然先手
		public static final byte FIGHT_EFFECT_NOHURT=8;//无视减伤
}
