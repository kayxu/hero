package com.joymeng.game.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import com.joymeng.game.cache.domain.Log;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.cache.domain.PlayerTemp;
import com.joymeng.game.cache.respository.LogImpl;
import com.joymeng.game.cache.respository.LogInterface;
import com.joymeng.game.cache.respository.PlayerCacheDAO;
import com.joymeng.game.cache.respository.PlayerCacheDAOImpl;
import com.joymeng.game.db.DBManager;
import com.joymeng.web.entity.CardOperationInfo;
import com.joymeng.web.entity.ExtremeBox;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DBCollection;

/**
 * 采用mongodb服务器来处理 玩家缓存数据和需要查询统计的log数据
 * 
 * @author admin
 * 
 */
public class MongoServer {
	static Logger logger = LoggerFactory.getLogger(MongoServer.class);
	private static MongoServer instance;
	private ConfigurableApplicationContext context;
	//日志服务器
	private LogServer logServer;
	//后台服务器
	private BgServer bgServer;
	public static MongoServer getInstance() {
		if (null == instance) {
			instance = new MongoServer();
		}
		return instance;
	}

	public MongoServer() {
		context = new ClassPathXmlApplicationContext("conf/mongo-config.xml");
		MongoTemplate mongoTemplate = (MongoTemplate) context.getBean("mongoTemplate");
		//日志服务器
		logServer=LogServer.getInstance();
		logServer.setMongoTemplate(mongoTemplate);
		PlayerCacheDAO playerCacheDAO = context.getBean(PlayerCacheDAOImpl.class);
		LogInterface logChche = context.getBean(LogImpl.class);
		logServer.setPlayerCacheDAO(playerCacheDAO);
		logServer.setLogChche(logChche);
		//后台服务器
		mongoTemplate = (MongoTemplate) context.getBean("bgTemplate");
		bgServer=BgServer.getInstance();
		bgServer.setMongoTemplate(mongoTemplate);
	
	}

//	/**
//	 * 显示数据库状态
//	 */
//	public List<Map<String, ?>> showStatus() {
//		List<Map<String, ?>> lst = new ArrayList<>();
//		lst.add(addToMap("hero"));
//		lst.add(addToMap("activeUser"));
//		lst.add(addToMap("activeUserLevel"));
//		lst.add(addToMap("diamondUse"));
//		lst.add(addToMap("fightEvent"));
//		lst.add(addToMap("gameOnlineNum"));
//		lst.add(addToMap("gameOnlineTime"));
//		lst.add(addToMap("login"));
//		lst.add(addToMap("loyalUser"));
//		lst.add(addToMap("loyalUserLevel"));
//		lst.add(addToMap("newUser"));
//		lst.add(addToMap("playerCache"));
//		lst.add(addToMap("propSale"));
//		lst.add(addToMap("propUse"));
//		lst.add(addToMap("playerTemp"));
//		return lst;
//	}
//
//	public Map<String, Long> addToMap(String name) {
//		Map<String, Long> map = new HashMap<String, Long>();
//		map.put(name, mongoTemplate.getCollection(name).getCount());
//		return map;
//	}

//	/**
//	 * 保存所有任务情况到单独的一个临时表playerTemp中
//	 */
//	public void saveAllQuest() {
//		mongoTemplate.dropCollection("playerTemp");
//		this.getLogChche().getQuestAll();
//		logger.info("save all quest finish");
//	}




//	/**
//	 * 取得玩家的任务完成情况
//	 * 
//	 * @param uid
//	 * @param key
//	 * @return
//	 */
//	public List<PlayerTemp> getPlayerTemp(long uid, String key) {
//		// System.out.println("get temp uid="+uid);
//		List<PlayerTemp> list = mongoTemplate.find(new Query(
//				new Criteria("uid").is(uid).and("questType").is(1)),
//				PlayerTemp.class);
//		// List<PlayerTemp> list=mongoTemplate.find(new Query(new
//		// Criteria("uid").is(uid).and("key").is(key)), PlayerTemp.class);
//		return list;
//	}

	@PostConstruct
	public void init() {
		System.out.println("datasave init");
	}

//	// 脚本执行
//	public String script(String script) {
//		FutureTask<CommandResult> futureTask = runScript(script);
//		CommandResult result = null;
//		try {
//			result = futureTask.get(20, TimeUnit.SECONDS);
//			logger.debug("run mongo script =[{}] ,result=[{}]", script, result);
//			return result.toString();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "error";
//
//	}

//	private final static ConcurrentTaskScheduler executor = new ConcurrentTaskScheduler();

//	private FutureTask<CommandResult> runScript(final String script) {
//		FutureTask<CommandResult> _fuFutureTask = new FutureTask(
//				new Callable() {
//					@Override
//					public CommandResult call() throws Exception {
//
//						logger.debug("run mongo script = {}", script);
//						CommandResult result = mongoTemplate.getDb().doEval(
//								script,
//								new BasicDBObject().append("nolock", true));
//						logger.debug("mongo task response {}", result);
//						return result;
//					}
//				});
//		executor.submit(_fuFutureTask);
//		return _fuFutureTask;
//	}

//	public void removeData(String collectName, String id) {
//		// mongoTemplate.remove(new Query(new Criteria("_id").is(new
//		// ObjectId(fe.getId()))), "fightEvent");
//		DBCollection collection = mongoTemplate.getCollection(collectName);
//
//		BasicDBObject query = new BasicDBObject("_id", new ObjectId(id));
//
//		collection.remove(query);
//		// DBCursor cursor = collection.find(query);
//		// while(cursor.hasNext()){
//		// System.out.println(cursor.next());
//		// }
//	}


	public LogServer getLogServer() {
		return logServer;
	}

	public void setLogServer(LogServer logServer) {
		this.logServer = logServer;
	}

	public BgServer getBgServer() {
		return bgServer;
	}

	public void setBgServer(BgServer bgServer) {
		this.bgServer = bgServer;
	}
}
