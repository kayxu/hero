package com.joymeng.game.common;

import com.joymeng.game.db.DBManager;
import com.joymeng.game.db.GameDAO;
import com.joymeng.game.domain.world.World;

/**
 * 唯一实例的类对象,便于管理
 * 
 * @author ShaoLong Wang
 * 
 */
public interface Instances {
	// 游戏世界
	public static final World gameWorld = World.getInstance();

	// 各个模块
	// DB
	public static final DBManager dbMgr = DBManager.getInstance();
	public static final GameDAO gameDao = dbMgr.getWorldDAO();
	// 节日活动
}
