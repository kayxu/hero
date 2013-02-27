package com.joymeng.game.db;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springside.modules.test.data.DataFixtures;

import com.joymeng.core.utils.TimeUtils;


public class DBManager {
	private static DBManager dbManager = null;
	private ApplicationContext context = null;

	private GameDAO worldDAO = null;
	private BgDAO bgDAO=null;
	public static DBManager getInstance() {
		if (dbManager == null) {
			dbManager = new DBManager();
		}
		return dbManager;
	}


	public void init() {
		context = new ClassPathXmlApplicationContext("conf/applicationContext-game.xml");
		worldDAO = (GameDAO) context.getBean("gameWorldDao");
		bgDAO=(BgDAO)context.getBean("bgDao");
	}

	public GameDAO getWorldDAO() {
		return worldDAO;
	}

	public void setWorldDAO(GameDAO worldDAO) {
		this.worldDAO = worldDAO;
	}


	public BgDAO getBgDAO() {
		return bgDAO;
	}


	public void setBgDAO(BgDAO bgDAO) {
		this.bgDAO = bgDAO;
	}
	

//	long time=TimeUtils.nowLong();
	//执行数据库脚本
//	 DataSource ds=worldDAO.getDataSource();
//	 DataFixtures.executeScript( ds,"classpath:resource2/data/game_server1-clear.sql");
//	 System.out.println("初始数据库脚本耗时="+(TimeUtils.nowLong()-time));

}
