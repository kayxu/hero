package com.joymeng.game.domain.activity;

/**
 * 活动的工厂类
 * @author admin
 *
 */
public class ActivityFactory {
	public static ActivityBase create(byte type){
		ActivityBase ab = null;
		switch(type){
		case ActivityConst.TYPE_TEST:
			ab =new ActivityTest(type);
			break;
		}
		return ab;
	}
}	
