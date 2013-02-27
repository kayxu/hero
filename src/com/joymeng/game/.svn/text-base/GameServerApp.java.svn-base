package com.joymeng.game;

import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.test.data.DataFixtures;

import com.joymeng.core.log.GameLog;
import com.joymeng.core.scheduler.SchedulerServer;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.console.ConsoleService;
import com.joymeng.services.core.context.RemoteService;
import com.joymeng.services.core.plugin.Plugin;

public class GameServerApp {
	static final Logger logger = LoggerFactory.getLogger(GameServerApp.class);
	public static boolean FREEZE = false;// 服务器是否关闭
	public static byte type = -1;// 默认服务器类型
	public static int instanceID;// 服务器实例号
	public static String instanceName="hero";//服务器名称
	public static boolean specialLogin;//特殊登录方式
	public static AtomicLong send=new AtomicLong();//服务器收发数据统计
	public static AtomicLong receive=new AtomicLong();
	public static void main(String[] args) throws Exception {

		if (args.length != 0) {
			System.out.println("args[0]=" + args[0]);
			if (args[0].equals("db-init")) {
				DBManager dbManager = DBManager.getInstance();
				dbManager.init();
				long time = TimeUtils.nowLong();
				DataSource ds = dbManager.getWorldDAO().getDataSource();
				DataFixtures.executeScript(ds,
						"classpath:resource2/data/game_server1-init.sql",
						"classpath:resource2/data/game_server1-clear.sql");
				logger.info("初始数据库脚本耗时=" + (TimeUtils.nowLong() - time)
						/ 1000);
				System.exit(0);
			}else if(args[0].equals("special")){//登录后不向服务器列表推送状态
				GameServerApp.specialLogin=true;
			}
			
		}
		final JoyServiceApp app = JoyServiceApp.getInstance();
		app.registPlugin(new Plugin() {
			@Override
			public void onAppLogin(RemoteService service) throws Exception {
				logger.info("demo login succ...");
			}

			@Override
			public void onAppStart() throws Exception {
				logger.info("demo app start...");
				instanceID = (Integer) app
						.getContext()
						.getContextAttribute(
								com.joymeng.services.core.Constants.CONTEXT_KEY_INSTANCE_ID);
				startGame();
			}

			@Override
			public void onAppStop() throws Exception {
				logger.info("demo app stop...");
			}
		});
		app.start();
	}

	public static void startGame() throws Exception {
		World gameWorld = World.getInstance();

		try {
			Thread.sleep(100L);
			logger.info(">>>>>>>>      DATABASE DAO START");
			DBManager.getInstance().init();
			logger.info("---------      DATABASE DAO START SUCCESSFUL");
			logger.info(">>>>>>>>      GAME WORLD START");
			GameConfig.load();
			// CouchBaseUtil.connect(GameConfig.couchbase);
			gameWorld.init();
			MongoServer.getInstance();// 初始化MongoServer
			logger.info("---------     GAME WORLD START SUCCESSFUL");
			logger.info(">>>>>>>>      GAME SCHEDULER START");
			SchedulerServer scheduler = new SchedulerServer();
			gameWorld.initScheduler(scheduler);
			scheduler.run();
			logger.info("---------     GAME SCHEDULER START SUCCESSFUL");
			gameWorld.start();
			// 添加控制台处理程序
			ConsoleService consoleService = MhxxConsoleHandler.getInstance();
			JoyServiceApp.getInstance().registConsole(20002, consoleService);
		} catch (Exception e) {
			GameLog.error("game start error!", e);
			e.printStackTrace();
			System.exit(0);
		}
		Thread.sleep(5000);
		logger.info("后台程序启动中...");
		JettyServerApp.start();
		logger.info("后台程序启动完毕...");
	}
}
