package com.joymeng.game.domain.hero;


public enum HeroOptType {

	HERO_REFRESH(0),

	HERO_GET(1),

	HERO_DEL(2),

	HERO_EQUIP(3),

	HERO_UNEQUIP(4),

	HERO_ADDSKILL(5),

	HERO_DELSKILL(6),

	HERO_SOLDIER(7);
	
	int value;

	HeroOptType(int value) {
		this.value = value;
	}
	public int getValue(){
		return value;
	}

}
