package com.joymeng.game.domain.world;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import hirondelle.date4j.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.ChatChannel;
import com.joymeng.core.db.cache.couchbase.CouchBaseUtil;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.scheduler.SchedulerServer;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.EncryptUtil;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.GameServerApp;
import com.joymeng.game.JettyServerApp;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.Instances;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.db.GameDAO;
import com.joymeng.game.domain.award.DiamondAwardManager;
import com.joymeng.game.domain.card.CardActivityModule;
import com.joymeng.game.domain.fight.FightManager;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.quest.PlayerQuestAgent;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.role.UsernameManager;
import com.joymeng.game.domain.shop.PlayerShopManager;
import com.joymeng.game.job.ActivityCityWar;
import com.joymeng.game.job.ActivityCountyWar;
import com.joymeng.game.job.ActivityNationWar;
import com.joymeng.game.job.ActivityStateWar;
import com.joymeng.game.job.ArenaRewardJob;
import com.joymeng.game.job.ClearCardInfoJob;
import com.joymeng.game.job.ClearFightEventJob;
import com.joymeng.game.job.CountOnlinePlayerNumJob;
import com.joymeng.game.job.DoPerWeekJob;
import com.joymeng.game.job.DoWhenEndOfDayJob;
import com.joymeng.game.job.GoodsUpdateJob;
import com.joymeng.game.job.LoadRankDataJob;
import com.joymeng.game.job.MilitaryGold;
import com.joymeng.game.job.RemovalGold;
import com.joymeng.game.job.ServerInnerJob;
import com.joymeng.game.job.ShoutJob;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;

/**
 * 游戏世界，包含游戏主线程
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class World implements ChatChannel, Instances {
	private static final Logger logger = LoggerFactory.getLogger(World.class);

	private static final World instance = new World();
	private static SchedulerServer schedulerServer;
	public static List<OnlineNum>  gameOnlineList=new ArrayList<OnlineNum>();
	public static final World getInstance() {
		return instance;
	}

	/**
	 * 游戏世界初始化
	 */
	public void init() {
		try{
			I18nGreeting.getInstance().load();//加载开启中语言类型 
			String path = GameConst.RES_PATH_CH;
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				path = GameConst.RES_PATH_EN;
			}
			logger.info("语言环境===>"+path);
			GameDataManager.load(path);// 游戏数据载入
		}catch(Exception ex){
			ex.printStackTrace();
		}
		// 各个fixManager的载入
		// loadServerList();//服务器列表
		// Const.load(Const.RES_PATH);//字符串的载入
//		loadUnallowNameWords();
//		loadVersionList();
//		// 加载冻结、禁言信息
//		loadForbidList();
//		// 自动生成的名字
//		loadAutoName();
	}

	/**
	 * 加载版本信息
	 */
	public void loadVersionList() {
		// List<GameVersion> mainList = dao.getVersionMainList();
		// List<GameVersion> deleteFileList = dao.getVersionDeleteFileList();
		//
		// versionManager.loadVersionMain(mainList);
		// versionManager.loadVersionDeleteFile(deleteFileList);
	}

	// 加载冻结、禁言信息
	public void loadForbidList() {
		// List<Forbid> dataList = dao.getForbidList();
		// ForbidManager.getInstance().load(dataList);
	}

	public void loadAutoName() {
		// List<AotuName> nameList = dao.getAllNames();
		// if(nameList.size() > 0){
		// for(AotuName name:nameList){
		// if(name.getType() == 0){
		// maleNameMap.put(name.getName(), name);
		// }else{
		// manNameMap.put(name.getName(), name);
		// }
		//
		// }
		// }
	}

	/**
	 * 载入不允许玩家角色名包含字符
	 */
	public void loadUnallowNameWords() {
		try {
			// Set<String> tempWordsSet = new HashSet<String>();
			// logger.info("开始载入非法角色名包含字符<<<<<<<<<<<<<<<");
			// FileInputStream fin = new FileInputStream(Const.RES_PATH +
			// "unallowednames.txt");
			// BufferedReader br = new BufferedReader(new InputStreamReader(fin,
			// "UTF-8"));
			// String line = null;
			// while ((line = br.readLine()) != null)
			// {
			// tempWordsSet.add(line);
			// }
			// br.close();
			// unallowNameWords = tempWordsSet;
		} catch (Exception e) {
			GameLog.error("LOAD ERROR : ", e);
		}
	}

	/**
	 * 初始化定时任务
	 * http://www.cronmaker.com/
	 * @param scheduler
	 */
	public void initScheduler(SchedulerServer scheduler) {
		this.schedulerServer = scheduler;
		JobDetail job = null;
		CronTrigger trigger = null;
		// 在当前时间15秒后运行
		// Date startTime = DateBuilder.nextGivenSecondDate(null,15);
		// 当前时间的加上5分钟
		// Date endTime = DateBuilder.nextGivenMinuteDate(null, 5);
		// job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
		// trigger = newTrigger().withIdentity("trigger1",
		// "group1").startAt(startTime).endAt(endTime)
		// .withSchedule(cronSchedule("0 0/1 * * * ?")).build();
		// trigger = newTrigger().withIdentity("trigger1", "group1")
		// .withSchedule(cronSchedule("0 0/1 * * * ?")).build();
		// scheduler.addJob(job, trigger);
		
		// GameActivityManager.getInstance().start(scheduler);
		
		job = newJob(RemovalGold.class).withIdentity("job2", "group2").build();
		trigger = newTrigger().withIdentity("trigger2", "group2")
				.withSchedule(cronSchedule("0 0/10 * * * ?")).build();
		scheduler.addJob(job, trigger);
//		System.out.println(trigger.getNextFireTime());
		job = newJob(LoadRankDataJob.class).withIdentity("job3", "group3")
				.build();
		trigger = newTrigger().withIdentity("trigger3", "group3")
				.withSchedule(cronSchedule("0 0/30 * * * ?")).build();
		scheduler.addJob(job, trigger);
		
		//竞技场奖励的发放0 0 0 1/2 * ?     2天一次
		job = newJob(ArenaRewardJob.class).withIdentity("job4", "group4")
				.build();
		trigger = newTrigger().withIdentity("trigger4", "group4")
				.withSchedule(cronSchedule("0 0 0 1/2 * ?")).build();
		scheduler.addJob(job, trigger);
//		System.out.println(trigger.getStartTime()+" "+trigger.getNextFireTime());
		Date d=trigger.getNextFireTime();
		DateTime dt=TimeUtils.getTime(d.getTime());
		GameConfig.arenaUpdate=TimeUtils.getTimes(dt.format(TimeUtils.FORMAT));
		logger.info("下一次奖励发送时间="+dt.format(TimeUtils.FORMAT));
		GameConfig.getPm().set("arenaUpdate",dt.toString());
		GameConfig.getPm().save(GameConfig.FILENAME);
		//
		job = newJob(MilitaryGold.class).withIdentity("job5", "group5").build();
		trigger = newTrigger().withIdentity("trigger5", "group5")
				.withSchedule(cronSchedule("0 10 0 * * ?")).build();
		scheduler.addJob(job, trigger);
		//
		// 每隔15分钟记录当前在线人数
		job = newJob(CountOnlinePlayerNumJob.class).withIdentity("job6",
				"group6").build();
		trigger = newTrigger().withIdentity("trigger6", "group6")
				.withSchedule(cronSchedule("0 0/15 * * * ?")).build();//
		scheduler.addJob(job, trigger);
		//
		// // 每天1点做相应的处理
		 job = newJob(DoWhenEndOfDayJob.class).withIdentity("job7", "group7")
		 .build();
		 trigger = newTrigger().withIdentity("trigger7", "group7")
		 .withSchedule(cronSchedule("0 0 1 * * ?")).build();
		 scheduler.addJob(job, trigger);

		//
		// 每隔2小时统计当前服务器道具销售
		 job = newJob(GoodsUpdateJob.class).withIdentity("job8",
		 "group8").build();
		 trigger = newTrigger().withIdentity("trigger8", "group8")
		 .withSchedule(cronSchedule("0 0 0/2 * * ?")).build();
		 scheduler.addJob(job, trigger);

		// 每周定时任务
		job = newJob(DoPerWeekJob.class).withIdentity("job9", "group9").build();
		trigger = newTrigger().withIdentity("trigger9", "group9")
				.withSchedule(cronSchedule("0 0 0 ? * 7")).build();
		
		job = newJob(ActivityCityWar.class).withIdentity("job10", "group10").build();
		trigger = newTrigger().withIdentity("trigger10", "group10")
				.withSchedule(cronSchedule("0 0/30 11-19 ? * MON-SUN")).build();
		scheduler.addJob(job, trigger);
		
		job = newJob(ActivityCountyWar.class).withIdentity("job11", "group11").build();
		trigger = newTrigger().withIdentity("trigger11", "group11")
				.withSchedule(cronSchedule("0 0/20 * ? * *")).build();
		scheduler.addJob(job, trigger);
		
		job = newJob(ActivityStateWar.class).withIdentity("job12", "group12").build();
		trigger = newTrigger().withIdentity("trigger12", "group12")
				.withSchedule(cronSchedule("0 0 20 ? * MON-SAT")).build();
		scheduler.addJob(job, trigger);
		
		job = newJob(ActivityNationWar.class).withIdentity("job13", "group13").build();
		trigger = newTrigger().withIdentity("trigger13", "group13")
				.withSchedule(cronSchedule("0 0 20 ? * SUN")).build();
		scheduler.addJob(job, trigger);
		
		
		//每天零点清除在线玩家牌局信息
				job = newJob(ClearCardInfoJob.class).withIdentity("job14","group14").build();
				trigger = newTrigger().withIdentity("trigger14","group14")
						.withSchedule(cronSchedule("0 0 0 * * ?")).build();
				scheduler.addJob(job, trigger);
				
		//每1分钟通知登录服务器当前状态
		job = newJob(ServerInnerJob.class).withIdentity("job15", "group15").build();
		trigger = newTrigger().withIdentity("trigger15", "group15")
				.withSchedule(cronSchedule("0 0/1 * * * ?")).build();
		scheduler.addJob(job, trigger);
		
		// // 每周定时任务
		// job = newJob(DoPerWeekJob.class).withIdentity("job9",
		// "group9").build();
		// trigger = newTrigger().withIdentity("trigger9", "group9")
		// .withSchedule(cronSchedule("0 0 0 ? * 7")).build();
		// scheduler.addJob(job, trigger);
		

		//每30秒发送一次喊话
		job = newJob(ShoutJob.class).withIdentity("job16", "group16").build();
		trigger = newTrigger().withIdentity("trigger16", "group16")
				.withSchedule(cronSchedule("0/30 * * * * ? ")).build();
		scheduler.addJob(job, trigger);
		
		// // 每天2点做相应的处理
		 job = newJob(ClearFightEventJob.class).withIdentity("job17", "group17")
		 .build();
		 trigger = newTrigger().withIdentity("trigger17", "group17")
		 .withSchedule(cronSchedule("0 0 2 * * ?")).build();
		 scheduler.addJob(job, trigger);
		
		
	}

	/**
	 * 游戏启动
	 */
	public void start() {
		new MainLoop().start();
		new FightManager().start();
	}

	/**
	 * 游戏关闭，RPG中的gameWorldShutDown
	 */
	public void stop() {

		try {
			// 通知登录服务器，我关闭了
			GameServerApp.FREEZE = true;
			GameUtils.sendWolrdMessage( new TipMessage("服务器关闭了", ProcotolType.MESSAGE_RESP,
					GameConst.GAME_RESP_SUCCESS),(byte) 1);
			// 停止计划任务
			schedulerServer.stop();
			//保存竞技场数据
			while (FightManager.getArenaQueue().size()!=0){
				logger.info("竞技场战斗队列="+FightManager.getArenaQueue().size());
				Thread.sleep(100);
			}
			ArenaManager.getInstance().saveAll();
			// 保存所有的玩家
			worldRoleByUserId.clear();
			logger.info("server stop start");
			saveAllPlayer();
			logger.info("server stop end");
			worldRoleMap.clear();
			//
			JettyServerApp.server.stop();
			// 停止网络连接
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	WorldMonitor monitor = new WorldMonitor();

	/**
	 * 游戏主线程
	 * 
	 * @author ShaoLong Wang
	 * 
	 */
	public class MainLoop extends Thread {
		long tickcount = 0;

		@Override
		public void run() {
			logger.info("MAIN LOOP THREAD START!");
			while (true) {
				try {
					// 开启服务计时
					monitor.start();
					long lastTime = monitor.startTime;
					// 游戏逻辑
					gameTick();
					long saveTimeElapse = TimeUtils.nowLong() - lastTime;
					monitor.saveTimeElapse = saveTimeElapse;
					// 停止服务计时
					monitor.stop();
					long sleepTime = monitor.getTimeRemain();

					if (sleepTime > 0) {
						Thread.sleep(sleepTime);
					} else {
						System.out.println("时间过长:");
					}
					if (tickcount % monitor.PRINT_WORLD_MONITOR_TICK_COUNT == 0) {
						logger.info(monitor.toString());
					}
					tickcount++;
				} catch (Exception e) {
					GameLog.error("error in MainLoop tick : " + tickcount, e);
					tickcount++;
				}
			}
		}
		
		long shoutTime=TimeUtils.nowLong();
		boolean isFirstShout=true;//第一次喊话
		/**
		 * 游戏逻辑
		 */
		public void gameTick() {
			//喊话
			if(isFirstShout||(TimeUtils.nowLong()-shoutTime>30*GameConst.TIME_SECOND)){
				TipMessage tip=getShout();
				if(tip!=null){
					isFirstShout=false;
					GameUtils.sendWolrdMessage(tip, (byte)1);
					logger.info("shout==="+tip.getMessage());
					shoutTime=TimeUtils.nowLong();
				}
				
			}

			Iterator<Integer> it = worldRoleMap.keySet().iterator();
			while (it.hasNext()) {
				int key = it.next();
				PlayerCharacter p = worldRoleMap.get(key);
				if (p != null) {
					p.tick();
				}
			}
			saveRole();
			
		}

	}

	/**
	 * 获得玩家
	 * 
	 * @param uid
	 * @return
	 */
	public PlayerCharacter getPlayer(int uid) {
		long time=TimeUtils.nowLong();
		PlayerCharacter player = worldRoleMap.get(uid);
		if (player == null) {// 内存中不存在，从数据库中查找
			player = new PlayerCharacter();
			RoleData roleData = getRoleData(uid);
			if (roleData == null) {// 数据库中也不存在
				return null;
			}
			player.setData(roleData);
			player.init();
			// 只放入到worldRoleMap中
			worldRoleMap.put(uid, player);
		}
		//如果是离线的玩家，每被访问一次，则延长被保存的时间
		player.setHeartTime(TimeUtils.nowLong());
		logger.info("get Player uid=" + uid+" 耗时="+(TimeUtils.nowLong()-time) );
		return player;
	}
	
//	/**
//	 * 根据id获取用户自身信息
//	 * @param uid
//	 * @return
//	 */
//	public PlayerCharacter getRole(int uid) {
//		logger.info("get Player uid=" + uid);
//		PlayerCharacter player = worldRoleMap.get(uid);
//		if (player == null) {// 内存中不存在，从数据库中查找
//			player = new PlayerCharacter();
//			RoleData roleData = getRoleData(uid);
//			if (roleData == null) {// 数据库中也不存在
//				return null;
//			}
//			player.setData(roleData);
//		}
//		return player;
//	}

	/**
	 * 玩家登入
	 */
	public synchronized PlayerCharacter login(int uid, UserInfo userInfo) {
		// 查找uid下是否有角色，没有的话加入一个角色
		if (uid <= 0) {
			return null;
		}

		PlayerCharacter player = getPlayer(uid);
		
		// 账号是否被冻结判断
		if(null != player){
			if(player.getData().getCanLogin() == 1){//如果玩家被禁止登录
				String msg = I18nGreeting.getInstance().getMessage("login.not.allowed", null);
				GameUtils.sendTip(
						new TipMessage(msg, ProcotolType.ENTER_GAME_RESP,
								GameConst.GAME_RESP_FAIL),userInfo,GameUtils.FLUTTER);
				return null;
			}
		}
		
//		String value = CouchBaseUtil.get(String.valueOf(uid));
//		logger.info("login couchdb value=" + value);
//		if (value != null && EncryptUtil.match(String.valueOf(uid), value)) {
//			logger.info("login match");
//		} else {
//			logger.info("login no match");
//		}
		if (player == null) {
			player = new PlayerCharacter();
			RoleData data = createRoleData(uid);
			player.setData(data);
			player.init();
			logger.info("创建一个新角色 ，uid=" + uid);
			GameLog.logSystemEvent(LogEvent.NEW_USER, String.valueOf(uid));
		}
		if (player.getState() == GameConst.STATE_ONLINE) {// 已经登录过了
			// 玩家数据已经存在，踢出之前的旧玩家，使其下线
//			gameWorld.kickRole(player);
		}
		// 更新任务
		if (player.getPlayerQuestAgent() == null) {
			player.setPlayerQuestAgent(new PlayerQuestAgent(player));
		}
		long lastLoginTime=player.getData().getLastLoginTime();
	
		// 设置userInfo,设置转发给玩家的session
		player.setUserInfo(userInfo);
		// 设置心跳时间
		player.setHeartTime(TimeUtils.nowLong());
		// 定时保存时间
		player.setSaveTime(TimeUtils.nowLong());
		// 设置为在线状态，定时保存依赖于在线状态，需要放在定时保存后面
		player.setState(GameConst.STATE_ONLINE);
		player.setHalt((byte)1);
		//收发消息统计
		player.getSend().set(0);
		player.getReceive().set(0);
		// 更新战役进度
		GameDataManager.campaignManager.update(player);
		// 更新通天塔進度
		GameDataManager.ladderManager.update(player);

		//内存中保留玩家上次登录时间
		player.getData().setRecordLastLoginTime(player.getData().getLastLoginTime());
		// 更新上一次登录时间
		player.getData().setLastLoginTime(TimeUtils.nowLong());
		// 加载商店
		player.setPlayerShopManager(new PlayerShopManager(player));
		//初始化玩家签到数据
		//player.getSignManager().handleIsSigned();
		addRole(player);
		//发送内测奖品
		DiamondAwardManager.getInstance().sendAward(player);
		return player;
	}

	/**
	 * 玩家主动退出
	 * @param player
	 */
	public void logout(PlayerCharacter player) {
//		LogBuffer lg = new LogBuffer();
//		lg.add(TimeUtils.nowLong()
//				- player.getData().getLastLoginTime());
//		lg.add(player.getData().getLastLoginTime());
//		lg.add(TimeUtils.nowLong());
//		GameLog.logPlayerEvent(player, LogEvent.ONLINE_TIME, lg);
//		player.setState(GameConst.STATE_OFFLINE);
		 logger.info("logout id=" +player.getId() );
		player.getData().setLeaveTime(TimeUtils.nowLong());
//		this.savePlayer(player);
		player.getCardManager().saveCards();//保存牌局数据
		player.getRechargeManager().saveRechargeInfo();//保存玩家充值信息
		UsernameManager.getInstance().releaseName(player);//玩家在选择一个随机名后突然退出游戏
		this.kickRole(player);
		// logger.info("logout,uid=" + player.getId());
		// if (!saveQueue.contains(player)) {
		// saveQueue.offer(player);
		// }
		// long intervalTime = TimeUtils.nowLong()
		// - player.getData().getLastLoginTime();
		// // 玩家在线时间
		//
	}

	/**
	 * 创建一个新玩家
	 * 
	 * @param uid
	 * @return
	 */
	private RoleData createRoleData(int uid) {
		RoleData roleData = RoleData.create();
		roleData.setUserid(uid);
		DBManager.getInstance().getWorldDAO().addRoleData(roleData);
		return roleData;
	}

	/**
	 * 获得角色 如果不在线则从数据库中载入
	 * 
	 * @param roleId
	 * @return
	 */
	private RoleData getRoleData(int uid) {
		GameDAO dao = DBManager.getInstance().getWorldDAO();
		RoleData roleData = dao.getRoleByUid(uid);
		return roleData;
	}
	/**
	 * 踢出玩家，设置玩家为离线状态，调用系统 saveRole()方法进行保存
	 * @param playerCharacter
	 */
	public boolean  kickRole(PlayerCharacter player) {
//		logger.info("kick role");
		if(player==null){
			return false;
		}
		if(saveQueue.size()>100){
			return false;
		}
		logger.info("kick role ,id="+player);
		long receiveNum=player.getReceive().intValue();
		if(player.getState() == GameConst.STATE_ONLINE){//如果玩家在线则踢出
			player.setState(GameConst.STATE_OFFLINE);
			LogBuffer lg = new LogBuffer();
			//记录日志
			lg.add(TimeUtils.nowLong()
					- player.getData().getLastLoginTime());
			lg.add(player.getData().getLastLoginTime());
			lg.add(TimeUtils.nowLong());
			lg.add(receiveNum);
			GameLog.logPlayerEvent(player, LogEvent.ONLINE_TIME, lg);
		}
		savePlayer(player);//踢出玩家

		return true;
	}

	/**
	 * 判断玩家是否在线
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkOnline(int uid) {
		PlayerCharacter player = worldRoleMap.get(uid);
		if (player == null) {
			return false;
		}
		if (player.getUserInfo() == null) {
			return false;
		}
		return true;
	}
	

	/**
	 * 加入角色
	 * 
	 * @param role
	 */
	private void addRole(PlayerCharacter pc) {

		logger.info("add to map,roleId=" + pc.getId() + " uid="
				+ pc.getUserInfo().getUid());
		if (pc.getId() == 0) {
			logger.info("error ,id=" + 0);
		}
		if (!worldRoleMap.containsKey(pc.getId())) {
			worldRoleMap.put(pc.getId(), pc);
		}
		if (!worldRoleByUserId.containsKey(pc.getUserInfo().getUid())) {
			worldRoleByUserId.put(pc.getUserInfo().getUid(), pc);
		}

	}

	public void removeRole(PlayerCharacter pc) {
//		logger.info("remove role ,uid=" + pc.getId());
		if (worldRoleMap.containsKey(pc.getId())) {
			worldRoleMap.remove(pc.getId());// 从游戏世界中删除
		}
		// if (pc.getState() == GameConst.STATE_ONLINE) {
		if (pc.getUserInfo() != null) {
			if (worldRoleByUserId.containsKey(pc.getUserInfo().getUid())) {
				worldRoleByUserId.remove(pc.getUserInfo().getUid());
			}
		}

		// }

	}

	/**
	 * 保存角色的map
	 */
	ConcurrentHashMap<Integer, PlayerCharacter> worldRoleMap = new ConcurrentHashMap<Integer, PlayerCharacter>();// 在线玩家列表 id
	ConcurrentHashMap<Long, PlayerCharacter> worldRoleByUserId = new ConcurrentHashMap<Long, PlayerCharacter>();// 在线玩家列表 uid

	public ConcurrentHashMap<Integer, PlayerCharacter> getWorldRoleMap() {
		return worldRoleMap;
	}

	//
	// public void setWorldRoleMap(
	// ConcurrentHashMap<Integer, PlayerCharacter> worldRoleMap) {
	// this.worldRoleMap = worldRoleMap;
	// }
	public PlayerCharacter getPlayerByUid(long uid) {
		return worldRoleByUserId.get(uid);
	}

	// public ConcurrentHashMap<Long, PlayerCharacter> getWorldRoleByUserId() {
	// return worldRoleByUserId;
	// }

	// public void setWorldRoleByUserId(
	// ConcurrentHashMap<Long, PlayerCharacter> worldRoleByUserId) {
	// this.worldRoleByUserId = worldRoleByUserId;
	// }

	/**
	 * 根据roleId取得在线玩家
	 * 
	 * @param roleId
	 * @return
	 */
	public PlayerCharacter getOnlineRole(int uid) {
		PlayerCharacter role = worldRoleMap.get(uid);
		if (role == null) {
			return null;
		}
		// if (role.getState() == Constants.STATE_OFFLINE)
		// {
		// return null;
		// }
		return role;
	}

	/**
	 * 根据name查询roleID
	 */
	public int getRoleIdByName(String name) {
		// for (PlayerCharacter role : gameWorld.getAllRoles()) {
		// if (role.getName().equals(name)) {
		// return role.getId();
		// }
		// }
		return 0;
	}

	/**
	 * 取得在线玩家的数量 登陆过的玩家
	 * 
	 * @return
	 */
	public int getOnlinePlayerNum() {
		int num = 0;
		synchronized (worldRoleMap) {
			Iterator<Integer> it = worldRoleMap.keySet().iterator();
			
			while (it.hasNext()) {
				PlayerCharacter player = worldRoleMap.get(it.next());
				if (player != null) {
					if (player.getUserInfo() != null) {
						if(TimeUtils.nowLong()-player.getHeartTime()<60*1000){
							num++;
						}
					}
					// logger.info("online player,uid="
					// + worldRoleMap.get(it.next()).getId());
				}

			}
		}
		return num;
	}

	/**
	 * 内存中的总的玩家
	 * 
	 * @return
	 */
	public int getAllPlayerNum() {
		return worldRoleMap.size();
	}
	public int getAllPlayerNum2(){
		return worldRoleByUserId.size();
	}

	private Queue<PlayerCharacter> saveQueue = new ConcurrentLinkedQueue<PlayerCharacter>();
	//喊话队列
	private Queue<TipMessage> shoutQueue = new ConcurrentLinkedQueue<TipMessage>();
	/**
	 * 加入队列
	 * @param tip
	 */
	public void addShout(TipMessage tip){
		shoutQueue.add(tip);
		System.out.println(getShoutSize());
	}
	public int getShoutSize(){
		return shoutQueue.size();
	}
	/**
	 * 从队列移除
	 * @return
	 */
	public TipMessage getShout(){
		return shoutQueue.poll();
	}
	public boolean saveRole() {
		if(saveQueue.size()!=0){
			logger.info("saveQueue size="+saveQueue.size());
		}
		PlayerCharacter pc = saveQueue.poll();
		if (pc == null) {
			return true;
		}
		try {
		
			long time=TimeUtils.nowLong();
			pc.saveSelf();
			if (pc.getState() == GameConst.STATE_OFFLINE) {
				removeRole(pc);
			}
			logger.info("world save role,id="+pc.getId()+" consume time(毫秒)="+(TimeUtils.nowLong()-time));
		} catch (Exception e) {
			GameLog.error("SAVE ROLE ERROR : ", e);
		}
		return false;

	}

	/**
	 * 立即保存所有玩家，不判断玩家状态
	 */
	public void saveAllPlayer() {
		try{
			synchronized (worldRoleMap) {
				Iterator<Integer> it = worldRoleMap.keySet().iterator();
				while (it.hasNext()) {
					PlayerCharacter player = worldRoleMap.get(it.next());
					if (player != null) {
						player.saveSelf();
					}
					// logger.info("online player,uid="
					// + worldRoleMap.get(it.next()).getId());
				}
			}
		}catch(Exception ex){
			GameLog.error("saveAllPlayer error", ex);
			ex.printStackTrace();
		}
	
	}

	/** 保存玩家数据，heartBeat和玩家退出时调用 */
	public void savePlayer(PlayerCharacter pc) {
		
		if (pc != null) {
			try {
				if (!saveQueue.contains(pc)) {
					saveQueue.offer(pc);
					logger.info("超时保存，uid=" + pc.getId()+" saveQueue size="+saveQueue.size());
				}
			} catch (Exception e) {
				// logger.error("save player[" + pc.getId() + "][" +
				// pc.getName() +"] error...");
			}
		}
	}

	/**
	 * 游戏世界信息
	 * 
	 * @return
	 */
	public String info() {
		StringBuffer sb = new StringBuffer();
		// 计划任务的数量
		int scheNum = schedulerServer.getGroupNames().size();
		// 在线玩家的数量
		int onlineNum = getOnlinePlayerNum();
		// 内存中的总的玩家数量
		int allNum = worldRoleMap.size();
		sb.append("scheNum=").append(scheNum).append("\n");
		sb.append("onlineNum=").append(onlineNum).append("\n");
		sb.append("allNum=").append(allNum).append("\n");
		return sb.toString();
	}
	
	/**
	 * 清空玩家牌局信息
	 */
	public void clearPlayerCardsInfo(){
		synchronized (worldRoleMap) {
			Iterator<Integer> it = worldRoleMap.keySet().iterator();
			while (it.hasNext()) {
				PlayerCharacter player = worldRoleMap.get(it.next());
				if (player != null) {
					player.getCardManager().setFlipChance(0);
					player.getCardManager().setRotateChance(0);
					player.getCardManager().setTurnsForChance(1);
					player.getCardManager().setNextTime(60);
					player.getCardManager().setUseRotateChance(0);
					player.getCardManager().setLastAddChanceTime(TimeUtils.nowLong());
					
					//客户端实时更新
					CardActivityModule cam = new CardActivityModule();
					cam.setFlipChance(0);
					cam.setRotateChance(0);
					cam.setNextTime(60);
					RespModuleSet rms=new RespModuleSet(ProcotolType.CARD_RESP);
					rms.addModule(cam);
					AndroidMessageSender.sendMessage(rms, player);
					
				}
			}
		}
	}
	/** 自动保存 */
	// public void autoSavePlayer(PlayerCharacter player) {
	// if (player != null) {
	// // if (!saveQueue.contains(player))
	// // saveQueue.offer(player);
	// // logger.debug("auto save player:" + player.getId() + "," +
	// // player.getName());
	// }
	// }
	// //服务器相关信息
	// ServerInfo si = new ServerInfo();
	// /**
	// * @param si
	// */
	// public void setServerInfo(ServerInfo si)
	// {
	// this.si = si;
	// System.out.println(si);
	// }
	//
	// /**
	// * 取得定时收集的服务器状态
	// * @return
	// */
	// public ServerInfo getServerInfo()
	// {
	// return this.si;
	// }
	// /**
	// * 立即取得当前最新服务器状态
	// * @return
	// */
	// public ServerInfo getServerInfoRightNow()
	// {
	//
	// return this.si;
	// }

}
