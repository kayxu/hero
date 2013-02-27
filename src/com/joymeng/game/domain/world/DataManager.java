package com.joymeng.game.domain.world;

/**
 * test
 * @author admin
 * @date 2012-5-22
 * TODO
 */
public class DataManager {
	private static DataManager instance;
	public   static  DataManager  getInstance(){
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
}
