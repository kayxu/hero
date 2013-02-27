package com.joymeng.game.domain.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.joymeng.core.scheduler.SchedulerServer;
import com.joymeng.game.domain.soldier.Soldier;


/**
 * 管理游戏当前的所有的活动
 * @author admin
 *
 */
public class GameActivityManager {
	public static  ActivityTest aTest=null;
	private static GameActivityManager instance;
	public static GameActivityManager getInstance() {
		if (instance == null) {
			instance = new GameActivityManager();
		}
		return instance;
	}
	private List<ActivityBase> list = new ArrayList<ActivityBase>();

	public void init(){
		ActivityBase aBase=ActivityFactory.create(ActivityConst.TYPE_TEST);
		list.add(aBase);
	}
	public ActivityBase get(byte type){
		for(ActivityBase ab:list){
			if(ab.getType()==type){
				return ab;
			}
		}
		return null;
	}
	public void start(SchedulerServer scheduler){
		this.init();
		for(ActivityBase ab:list){
			ab.start(scheduler);
		}
	}
}
