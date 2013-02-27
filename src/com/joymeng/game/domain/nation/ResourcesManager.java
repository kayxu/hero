package com.joymeng.game.domain.nation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourcesManager {
	public static Logger logger = LoggerFactory.getLogger(ResourcesManager.class);
	
	public static  ResourcesManager instance;
	public ResourcesManager(){}
	
	public static ResourcesManager getInstance(){
		if(instance == null)
			instance = new ResourcesManager();
		return instance;
	}
	
	public static NationManager nationMgr = NationManager.getInstance();
	//
	public void makeGold(){
//		List<Nation> lst = nationMgr.getNationMap();
	}
}
