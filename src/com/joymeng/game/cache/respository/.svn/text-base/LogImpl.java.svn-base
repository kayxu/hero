package com.joymeng.game.cache.respository;

import hirondelle.date4j.DateTime;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.log.LogType;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.GameServerApp;
import com.joymeng.game.cache.MongoLogManager;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.DiamondPlayer;
import com.joymeng.game.cache.domain.Log;
import com.joymeng.game.cache.domain.LogQuery;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.NewUser;
import com.joymeng.game.cache.domain.PlayerTemp;
import com.joymeng.game.cache.domain.PropSale;
import com.joymeng.game.cache.domain.QuestTemp;
import com.joymeng.game.cache.domain.TimeTemp;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.web.entity.CardOperationInfo;
import com.joymeng.web.entity.ExtremeBox;
import com.joymeng.web.entity.OnlineNum;
import com.joymeng.web.entity.OnlineTime;
import com.joymeng.web.entity.Sale;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class LogImpl implements LogInterface {
	@Resource(name = "mongoTemplate")
	MongoTemplate mongoTemplate;
	@Resource(name = "bgTemplate")
	MongoTemplate bgTemplate;
	private Executor executor = Executors.newFixedThreadPool(10);
	private long mongWaitSeconds = 3600;
	private static final Logger logger = LoggerFactory.getLogger(LogImpl.class);

	// String collectionName;

	// public void setMongoTemplate(MongoTemplate mongoTemplate) {
	// this.mongoTemplate = mongoTemplate;
	// collectionName = mongoTemplate.getCollectionName(Log.class);
	// if (!mongoTemplate.collectionExists(collectionName)) {
	// mongoTemplate.createCollection(collectionName);
	// }
	// }
	/**d
	 * 获得指定日期范围内的日志，并
	 */
	@Override
	public List<Log> getAllLogs(final String start) {
		LogQuery logQuery = new LogQuery();
		logQuery.setStart(start+" 0:0:0");
		logQuery.setEnd(start+" 24:0:0");
		final List<Log> list = new ArrayList<Log>();
		try {
			final DBCursor cursor = this.findLogs(logQuery, 0);
			final MongoConverter converter = mongoTemplate.getConverter();
			FutureTask<String> task = new FutureTask(new Callable<String>() {
				@Override
				public String call() throws Exception {
					long startTime = TimeUtils.nowLong();
					while (cursor.hasNext()) {
						Log log = converter.read(Log.class, cursor.next());
						logger.info(log.toString());
						list.add(log);
						long current = TimeUtils.nowLong();
						mongoTemplate.save(log, "log"+start);
					}
					return "";
				}
			});
			// 执行任务
			executor.execute(task);
			// 5秒后关闭
			try {
				task.get(mongWaitSeconds , TimeUnit.SECONDS);
				cursor.close();
			} catch (Exception e) {
				task.cancel(true);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return list;
	}

	public DBCursor findLogs(LogQuery logQuery, int limit)
			throws ParseException {

		Query query = new BasicQuery(logQuery.toQuery());
		if (limit > 0) {
			query.limit(limit);
		}
		 query.sort().on("timestamp", Order.DESCENDING);
		DBCursor cursor = mongoTemplate.getCollection(GameServerApp.instanceName)
				.find(query.getQueryObject()).sort(query.getSortObject())
				.limit(limit);
		return cursor;
	}

	@Override
	// mongo key LOGIN 4
	public String getLogByKey(String key, int limit) {
		final StringBuffer buf = new StringBuffer();
		LogQuery logQuery = new LogQuery();
		logQuery.setKeyWord(key);
		try {
			final DBCursor cursor = this.findLogs(logQuery, limit);
			final MongoConverter converter = mongoTemplate.getConverter();

			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				buf.append(log.toString());
//				logger.info(log.toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return buf.toString();
	}

	@Override
	// mongo time 2012-9-3 1:1:1 2012-9-5 1:1:1
	//查找
	public String getLogByTime(String start, String end) {
		final StringBuffer buf = new StringBuffer();
		String ids[] = TimeUtils.getDays(start, end);
	
		try {
			for(int i=0;i<ids.length;i++){
				LogQuery logQuery = new LogQuery();
				logQuery.setStart(ids[i]+" 0:0:0");
				logQuery.setEnd(ids[i]+" 24:0:0");
				final DBCursor cursor = this.findLogs(logQuery, 0);
				final MongoConverter converter = mongoTemplate.getConverter();
				while (cursor.hasNext()) {
					Log log = converter.read(Log.class, cursor.next());
//					logger.info(log.toString());
					MongoServer.getInstance().getBgServer().save("log"+ids[i], log);
//					buf.append(log.toString());
					
				}
			}
		
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return buf.toString();
	}

	/**
	 * 
	 * 大转盘某一段时间的使用人数
	 */
	@Override
	public int getBoxUseNum(String start, String end) {
		int useNum = 0;
		final StringBuffer buf = new StringBuffer();
		try {
			final DBCursor cursor = this.getLog(start, end,
					LogType.BOX_USE_NUM.toString());
			final MongoConverter converter = mongoTemplate.getConverter();

			while (cursor.hasNext()) {
				useNum++;
				Log log = converter.read(Log.class, cursor.next());
				buf.append(log.toString());
//				logger.info(log.toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return useNum;
	}

	/**
	 * 大转盘某一段时间消耗的筹码数量
	 */
	@Override
	public int getBoxCostChipNum(String start, String end) {
		int costNum = 0;
		final StringBuffer buf = new StringBuffer();
		try {
			final DBCursor cursor = this.getLog(start, end,
					LogType.COST_CHIP.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
				str = message.split("\\|");
				costNum += Integer.parseInt(str[1]);
				buf.append(log.toString());
//				logger.info(log.toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return costNum;
	}

	/**
	 * 大转盘某一段时间产出的奖品数量
	 */
	@Override
	public int getBoxAwardNum(String start, String end) {
		int awardNum = 0;
		final StringBuffer buf = new StringBuffer();
		try {
			final DBCursor cursor = this.getLog(start, end,
					LogType.AWARD_LIST.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				awardNum++;
				buf.append(log.toString());
//				logger.info(log.toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return awardNum;
	}

	/**
	 * 商城每一道具在某一短时间的购买数量 ，相同道具id进行累计 BUY_PROPS|567|建设锤|1|1985|2012-10-30
	 * 10:21:54| BUY_PROPS，物品id，物品名称，使用数量，使用者，使用时间
	 */
	@Override
	public List<Sale> getEachPropsBuyNum(String start, String end) {
		List<Sale> list = new ArrayList<Sale>();
		try {
			final DBCursor cursor = this.getLog(start, end,
					LogType.BUY_PROPS.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
				str = message.split("\\|");
				int propsId = Integer.parseInt(str[1]);
				String name = str[2];
				int num = Integer.parseInt(str[3]);
				boolean b = false;
				for (Sale ss : list) {
					if (ss.getId() == propsId) {
						ss.setNum(ss.getNum() + num);
						b = true;
						break;
					}
				}
				if (!b) {
					Sale s = new Sale();
					s.setId(propsId);
					s.setNum(num);
					s.setName(name);
					list.add(s);
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 相同道具id不进行累计
	// GameLog.logSystemEvent(LogEvent.BUY_PROPS,
	// String.valueOf(props.getPrototype().getId()),props.getPrototype().getName()
	// ,String.valueOf(num),String.valueOf(player.getId()));
	// 购买道具，道具id，道具名称，购买数量，玩家id

	// GameLog.logSystemEvent(LogEvent.USE_DIAMOND,
	// String.valueOf(GameConst.DIAMOND_ARENA),"竞技场清冷却时间",
	// String.valueOf(m),String.valueOf(player.getId()));
	// 使用钻石，使用途径，使用说明，使用数量，玩家id

	// GameLog.logSystemEvent(LogEvent.USE_PROPS,
	// String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
	// ,String.valueOf(player.getId()));
	// 使用道具，道具id，道具名称，使用数量，玩家id
	@Override
	public List<PropSale> getPropsBuyType(String day, byte type) {
		DBCursor cursor = null;
		List<PropSale> list = new ArrayList<PropSale>();
		try {
			switch (type) {
			case 1:
				cursor = this.getLog(day, LogType.BUY_PROPS.toString());
				break;
			case 2:
				cursor = this.getLog(day, LogType.USE_PROPS.toString());
				break;
			case 3:
				cursor = this
						.getLog(day, LogType.USE_DIAMOND.toString());
				break;
			default:
				return null;
			}
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
//				System.out.println("message==" + message);
				try {
				str = message.split("\\|");
//				if(str.length<5){
//					logger.info("message="+log.toString());
//					continue;
//				}
				int propsId = Integer.parseInt(str[1]);
				String name = str[2];
				int num = Integer.parseInt(str[3]);
				int userId = Integer.parseInt(str[4]);
				String time = TimeUtils.getTime(log.getTimestamp().getTime())
						.format(TimeUtils.FORMAT1);
				Sale s = new Sale();
				s.setId(propsId);
				s.setNum(num);
				s.setName(name);
				s.setTime(time);
				s.setUserId(userId);
				GameUtils.addToList(list, s);
				}catch(Exception ex){
					GameLog.error("error log="+log.toString());
					this.removeLog(log.getId());
				}
			
			}
		} catch (ParseException e) {
//			e.printStackTrace();
		}
		return list;
	}
	public void getArenaLogByUid(String start,String end,int uid){
		try {
		DBCursor cursor = this.getLog(start, end, LogType.ARENA.toString());
		final MongoConverter converter = mongoTemplate.getConverter();
		while (cursor.hasNext()) {
			Log log = converter.read(Log.class, cursor.next());
			MongoLogManager.getInstance().addLog(log, (byte)0,uid);
		}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	public void getArenaLogByAid(String start,String end,int aid){
		try {
		DBCursor cursor = this.getLog(start, end, LogType.ARENA.toString());
		final MongoConverter converter = mongoTemplate.getConverter();
		while (cursor.hasNext()) {
			Log log = converter.read(Log.class, cursor.next());
			MongoLogManager.getInstance().addLog(log, (byte)1,aid);
		}
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}
	@Override
	public List<DiamondPlayer> getDiamondPlayerNum(String day){
		List<DiamondPlayer> list = new ArrayList<DiamondPlayer>();
		try {
			DBCursor cursor = this.getLog(day, LogType.USE_DIAMOND.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
//				System.out.println("message==" + message);
				str = message.split("\\|");
//				if(str.length!=5){
//					logger.info("message="+log.toString());
//					continue;
//				}
				try{
				int propsId = Integer.parseInt(str[1]);
				String name = str[2];
				int num = Integer.parseInt(str[3]);
				int userId = Integer.parseInt(str[4]);
				String time = TimeUtils.getTime(log.getTimestamp().getTime())
						.format(TimeUtils.FORMAT1);
				Sale s = new Sale();
				s.setId(propsId);
				s.setNum(num);
				s.setName(name);
				s.setTime(time);
				s.setUserId(userId);
				boolean b=false;
				for(DiamondPlayer dp:list){
					long time1=TimeUtils.getTimes(time);
					long time2=TimeUtils.getTimes(dp.getTime());
					if(TimeUtils.isSameDay(time1,time2)){
						dp.getIds().add(userId);
						b=true;
						break;
					}
				}
				if(!b){
					DiamondPlayer dp=new DiamondPlayer();
					dp.setTime(TimeUtils.getTime(log.getTimestamp().getTime())
							.format(TimeUtils.FORMAT));
					dp.getIds().add(userId);
					list.add(dp);
				}
				}catch(Exception e){
					GameLog.error("error log="+log.toString());
					this.removeLog(log.getId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 某一段时间登录过的玩家数 日志格式 玩家id，登录，日期 551|LOGIN|2012-10-08 09:13:43|
	 */
	@Override
	public List<Login> getLoginNum(String day) {
		List<Login> list = new ArrayList<Login>();
		try {
			final DBCursor cursor = this.getLog(day,
					LogType.LOGIN.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				// logger.info("LOGIN ="+log.toString());
				String message = log.getMessage();
//				System.out.println("login message="+message);
				str = message.split("\\|");
				try{
				int uid = Integer.parseInt(str[0]);
				String date = str[2].split(" ")[0];// 只获取日期
				GameUtils.addMessage(list, uid, date);
				}catch (Exception ex){
					GameLog.error("error log="+log.toString());
					this.removeLog(log.getId());
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 某一段时间同时在线玩家数量 日志格式 ONLINE|1|2012-10-25 09:20:00|
	 */
	@Override
	public List<OnlineNum> getOnlineNum2(String day) {
		List<OnlineNum> list = new ArrayList<OnlineNum>();
		try {
			final DBCursor cursor = this.getLog(day,
					LogType.ONLINE_NUM.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
//				logger.info(log.toString());
				String message = log.getMessage();
				str = message.split("\\|");
				int num = Integer.parseInt(str[1]);// 在线玩家数量
				OnlineNum on = new OnlineNum();
				on.setNum(num);
				on.setTimestamp(log.getTimestamp());
				list.add(on);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 一段时间内的在线时间,
	 * 保存每一个玩家的在线时间
	 * 
	 * @param uid
	 * @param start
	 * @param end
	 *            uid,, 在线时间（毫秒），登录时间，离开时间，心跳时间
	 *            498|ONLINE_TIME|45469|1351482972834|1351483018303|10
	 */
	public List<OnlineTime> getOnlineTime(String day) {
		List<OnlineTime> list = new ArrayList<OnlineTime>();
		mongoTemplate.dropCollection("timeTemp");
		try {
			final DBCursor cursor = this.getLog(day,
					LogType.ONLINE_TIME.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
//				logger.info(log.toString());
				String message = log.getMessage();
//				System.out.println("onlineTime message="+message);
				str = message.split("\\|");
				try{
				int uid = Integer.parseInt(str[0]);
				long time = Long.parseLong(str[2]);
				int num=0;
				if(str.length==6){
					 num=Integer.parseInt(str[5]);
				}
				log.getTimestamp();
				TimeTemp tt=mongoTemplate.findOne(new Query(new Criteria("uid").is(uid)), TimeTemp.class);
				if(tt==null){
					tt=new TimeTemp();
					tt.setUid(uid);
					tt.setTime(time);
					MongoServer.getInstance().getBgServer().save("timeTemp", tt);
				}else{
					tt.setTime(time+tt.getTime());
					MongoServer.getInstance().getBgServer().save("timeTemp", tt);
				}
				}catch(Exception ex){
					GameLog.error("error log="+log.toString());
					this.removeLog(log.getId());
				}

//				OnlineTime ot = new OnlineTime();
//				ot.setUid(uid);
//				ot.setOnline(time);
//				ot.setTimestamp(log.getTimestamp());
//				ot.setHeartNum(num);
//				list.add(ot);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 保存所有的任务信息到playerTemp表
	 * @return
	 */
	public void getQuestAll() {
		// 339284|QUEST_INFO|14|1|3|
		LogQuery logQuery = new LogQuery();
		logQuery.setKeyWord(LogType.QUEST_INFO.toString());
		if (mongoTemplate.collectionExists(PlayerTemp.class)) {
			mongoTemplate.dropCollection(PlayerTemp.class);
		}
		try {
			final DBCursor cursor = this.findLogs(logQuery, 0);
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				// logger.info("LOGIN ="+log.toString());
				String message = log.getMessage();
				System.out.println("message=" + message + " time="
						+ log.getId());
				try {
					str = message.split("\\|");
					long uid = Integer.parseInt(str[0]);
					int questId = Integer.parseInt(str[2]);
					byte type=Byte.parseByte(str[3]);
					//保存每一个任务到playerTemp中
					PlayerTemp pt = new PlayerTemp();
					pt.setTime(log.getTimestamp());
					pt.setUid(uid);
					pt.setQuestId(questId);
					pt.setQuestType(type);
					if(type!=1){//只处理该类型任务
						continue;
					}
					MongoServer.getInstance().getBgServer().save("playerTemp", pt);
					QuestTemp qt=mongoTemplate.findOne(new Query(new Criteria("uid").is(uid)), QuestTemp.class);
					if(qt==null){
						qt=new QuestTemp();
						qt.setMaxId(questId);
						qt.setUid(uid);
						MongoServer.getInstance().getBgServer().save("questTemp", qt);
					}else{
						if(questId>qt.getMaxId()){
							qt.setMaxId(questId);
							MongoServer.getInstance().getBgServer().save("questTemp", qt);
						}
					}
					
				} catch (Exception ex) {

				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得新注册用户数据
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public List<NewUser> getNewUser(String day) {
		List<NewUser> list = new ArrayList<NewUser>();
		try {
			final DBCursor cursor = this.getLog(day,
					LogType.NEW_USER.toString());
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
				str = message.split("\\|");
				int uid = Integer.parseInt(str[1]);
				String time = TimeUtils.getTime(log.getTimestamp().getTime())
						.format(TimeUtils.FORMAT);
				GameUtils.addMessage(uid, time, list);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	

	@Override
	public void save(Log log) {

	}

	/**
	 * 统计某一段时间内宝箱，大转盘的相关数据
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            截止时间
	 * @return 信息实体
	 */
	public ExtremeBox getExtremeBox(String start, String end) {
		Set<Integer> rollSet = new HashSet<Integer>();
		Set<Integer> refreshSet = new HashSet<Integer>();
		int rollTimes = 0;// 转动次数
		int score = 0;// 转动宝箱获得宝箱积分
		List<Integer> awardIdList = new ArrayList<Integer>();// 获取奖品的id
		int refreshTimes = 0;
		ExtremeBox extremeBox = new ExtremeBox();

		// 转动宝箱查询
		LogQuery logQuery = new LogQuery();
		logQuery.setKeyWord(LogType.ROLL_BOX.toString());// 格式:62478|ROLL_BOX|17|技能书盒lv2|5
		logQuery.setStart(start);
		logQuery.setEnd(end);
		try {
			final DBCursor cursor = this.findLogs(logQuery, 0);
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
				str = message.split("\\|");
				int userId = Integer.parseInt(str[0]);
				rollSet.add(userId);
				rollTimes++;// 转动次数增加
				int awardId = Integer.parseInt(str[2]);
				awardIdList.add(awardId);// 记录获取的奖品id
				if (str.length >= 5) {
					score += Integer.parseInt(str[4]);
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 刷新宝箱查询
		LogQuery logQuery2 = new LogQuery();
		logQuery2.setKeyWord(LogType.REFRESH_BOX.toString());
		logQuery2.setStart(start);
		logQuery2.setEnd(end);
		try {
			final DBCursor cursor = this.findLogs(logQuery2, 0);
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
				str = message.split("\\|");
				int userId = Integer.parseInt(str[0]);
				refreshSet.add(userId);
				refreshTimes++;

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		extremeBox.setRollNum(rollSet.size());
		extremeBox.setRollTimes(rollTimes);
		extremeBox.setAwardIDList(awardIdList);
		extremeBox.setRefreshNum(refreshSet.size());
		extremeBox.setRefreshTimes(refreshTimes);
		extremeBox.setTotalScore(score);

		return extremeBox;
	}

	/**
	 * 查询翻牌相关操作记录信息
	 * 
	 * @param start
	 *            起始时间
	 * @param end
	 *            截止时间
	 * @return
	 */
	public CardOperationInfo getCardOperationInfo(String start, String end) {
		Set<Integer> playerFlipSet = new HashSet<Integer>();
		Set<Integer> playerRotateSet = new HashSet<Integer>();
		List<Integer> awardIdList = new ArrayList<Integer>();
		List<Integer> awardNumList = new ArrayList<Integer>();
		int flipTimes = 0;// 翻牌次数
		int rotateTimes = 0;// 旋转次数
		int totalFlipNums = 0;// 翻开的总牌数
		CardOperationInfo cardOperationInfo = new CardOperationInfo();

		// 翻牌记录查询
		LogQuery logQuery = new LogQuery();
		logQuery.setKeyWord(LogType.FLIP_CARD.toString());// 格式：62478|FLIP_CARD|2|5|1|-3|1|
		logQuery.setStart(start);
		logQuery.setEnd(end);
		try {
			final DBCursor cursor = this.findLogs(logQuery, 0);
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
				str = message.split("\\|");
				int userId = Integer.parseInt(str[0]);
				playerFlipSet.add(userId);
				flipTimes++;
				totalFlipNums += Integer.parseInt(str[2]);
				for (int i = 3; i < str.length; i = i + 2) {
					awardIdList.add(Integer.parseInt(str[i]));
					awardNumList.add(Integer.parseInt(str[i + 1]));
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 旋转记录查询
		LogQuery logQuery2 = new LogQuery();
		logQuery2.setKeyWord(LogType.ROTATE_CARD.toString());// 格式：62478|ROTATE_CARD|
		logQuery2.setStart(start);
		logQuery2.setEnd(end);
		try {
			final DBCursor cursor = this.findLogs(logQuery2, 0);
			final MongoConverter converter = mongoTemplate.getConverter();
			String str[] = null;
			while (cursor.hasNext()) {
				Log log = converter.read(Log.class, cursor.next());
				String message = log.getMessage();
				str = message.split("\\|");
				int userId = Integer.parseInt(str[0]);
				playerRotateSet.add(userId);
				rotateTimes++;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cardOperationInfo.setFlipPlayerNum(playerFlipSet.size());
		cardOperationInfo.setFlipTimes(flipTimes);
		cardOperationInfo.setTotalFlipNums(totalFlipNums);
		cardOperationInfo.setRotatePlayerNum(playerRotateSet.size());
		cardOperationInfo.setRotateTimes(rotateTimes);
		cardOperationInfo.setAwardIdList(awardIdList);
		cardOperationInfo.setAwardNumList(awardNumList);

		return cardOperationInfo;

	}

	public DBCursor getLog(String start, String end, String key)
			throws ParseException {
		LogQuery logQuery = new LogQuery();
		logQuery.setKeyWord(key);
		logQuery.setStart(start);
		logQuery.setEnd(end);
		final DBCursor cursor = this.findLogs(logQuery, 0);
		return cursor;
	}
	/**
	 * 按照日期去指定数据库中查找
	 * @param day
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public DBCursor getLog(String day, String key)
			throws ParseException {
		LogQuery logQuery = new LogQuery();
		logQuery.setKeyWord(key);
		Query query = new BasicQuery(logQuery.toQuery());
		 query.sort().on("timestamp", Order.DESCENDING);
		 final DBCursor cursor = mongoTemplate.getCollection("log"+day)
				.find(query.getQueryObject()).sort(query.getSortObject());
		return cursor;
	}
	/**
	 * 根据id删除日志
	 * @param id
	 */
	public void removeLog(String id){
		DBCollection collection = mongoTemplate.getCollection(GameServerApp.instanceName);

		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id)); 
		
		collection.remove(query);

	}
}
