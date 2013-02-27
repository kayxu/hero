package com.joymeng.game.cache.respository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;

public class PlayerCacheDAOImpl implements PlayerCacheDAO {
	
	@Resource(name = "mongoTemplate")
	MongoTemplate mongoTemplate;
	private Executor executor = Executors.newFixedThreadPool(10);
	private long mongWaitSeconds = 10;
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerCacheDAOImpl.class);

	//String collectionName="playerCache";


	/**
	 * 防止查询的数据过大，给数据的限定，此数也不宜太小，小了会影响随机效果
	 */
	private static final int LIMIT = 500;


//	public void setMongoTemplate(MongoTemplate mongoTemplate) {
//		this.mongoTemplate = mongoTemplate;
//		collectionName = mongoTemplate.getCollectionName(PlayerCache.class);
//		if (!mongoTemplate.collectionExists(PlayerCache.class)) {
//			mongoTemplate.createCollection(PlayerCache.class);
//		}
//	}

	/**
	 * 保存数据
	 */
	 public void insert(PlayerCache playerCache) {  
//	       if(null != findPlayerCacheByUserid(playerCache.getUserid())){//如果已存在此用户数据则更新数据
	    	    playerCache.setRa(Math.random());
	        	mongoTemplate.save(playerCache);
//	        }
//	        else{
//	        	playerCache.setRa(Math.random());
//	        	mongoTemplate.insert(playerCache);
//	        }
	    }
	 
	 /**
	  * 按玩家id找寻
	  * @param userid
	  * @return
	  */
	 public PlayerCache findPlayerCacheByUserid(int userid){
		 PlayerCache playerCache = mongoTemplate.findOne(new Query(new Criteria("userid").is(userid)), PlayerCache.class);
		 if(playerCache == null){//如果用户cache不存在实例化cache出来
			 PlayerCharacter pc = World.getInstance().getPlayer(userid);
			 if(pc != null){
				 playerCache = pc.playerToCache();
				 insert(playerCache);//存入缓存
			 }
		 }
		 //playerCache = mongoTemplate.findOne(new Query(new Criteria("userid").is(userid)), PlayerCache.class);
		 return playerCache;
		 
	 }
	 //test
	 public List<PlayerCache> findPlayers(int ids[]){
		 Integer ss[]=new Integer[]{2010,2331};
		 List<Integer> list=new ArrayList<Integer>();
		 list.add(2010);
		 list.add(2331);
		 List<PlayerCache> _list=mongoTemplate.find(new Query(new Criteria("userid").in(ss)), PlayerCache.class);
		 return _list;
	 }
	 public List<PlayerCache> findAll() {  
	        List<PlayerCache> list = mongoTemplate.findAll(PlayerCache.class);  
			return list;
	    }  
	 
	 public void dropCollection() {
				mongoTemplate.dropCollection(PlayerCache.class);
	 }

	public void deletePlayerCache(int userid) {
		mongoTemplate.remove(new Query(Criteria.where("userid").is(userid)),
				PlayerCache.class);
	}

	/**
	 * 找与玩家同等级的空城的登录过的玩家，且不包括玩家自己
	 * 
	 * @param level
	 *            1-3 玩家等级
	 * @param userid
	 *            玩家自己id
	 * @param limit
	 *            随机数量限制
	 * @return PlayerCache
	 */
	public List<PlayerCache> findSameLevelAndNotOccupied(short level, int userid) {
		// 根据玩家等级查询
		List<PlayerCache> playerCacheList = mongoTemplate.find(
				new Query(new Criteria("level").is(level).and("officerId")
						.is(0).and("userid").ne(userid).and("occupyUserId")
						.ne(userid).and("faction").gt(0)).limit(LIMIT), PlayerCache.class);
		return playerCacheList;
	}

	/**
	 * 查找与玩家同等级的玩家信息，且不包括玩家自己，且附属城未被当前玩家占领
	 * 
	 * @param level
	 *            大于等于4 玩家等级
	 * @param userid
	 *            玩家自己id
	 * @return
	 */
	public List<PlayerCache> findSameLevel(short level, int userid) {
		List<PlayerCache> playerCacheList = mongoTemplate.find(
				new Query(new Criteria("level").is(level).and("userid")
						.ne(userid).and("occupyUserId").ne(userid).and("faction").gt(0))
						.limit(LIMIT), PlayerCache.class);
		return playerCacheList;
	}

	/**
	 * 自己等级为n，n-4≤n≤n+4的空城，不包括自己的等级n
	 * 
	 * @param level
	 *            玩家等级1-3级
	 * @return
	 */
	public List<PlayerCache> findRangeLevelAndNotOccupied(short level,
			int userid) {
		int minLevel = 0;
		// 因玩家等级没有负值，当计算为负值是置零
		if (level - 4 > 0) {
			minLevel = level - 4;
		}
		int maxLevel = level + 4;
		List<PlayerCache> playerCacheList = mongoTemplate.find(
				new Query(new Criteria("level").gte(minLevel).lte(maxLevel)
						.ne(level).and("officerId").is(0).and("occupyUserId")
						.ne(userid).and("faction").gt(0)).limit(LIMIT), PlayerCache.class);
		return playerCacheList;
	}

	/**
	 * 自己等级为n，n-4≤n≤n+4的附属城，不包括自己的等级n，不被当前玩家占领的附属城
	 * 
	 * @param level
	 *            玩家等级4级以上
	 * @param limit
	 *            随机数量限制
	 * @return
	 */
	public List<PlayerCache> findRangeLevel(short level, int userid) {
		int minLevel = 0;
		// 因玩家等级没有负值，当计算为负值是置零
		if (level - 4 > 0) {
			minLevel = level - 4;
		}
		int maxLevel = level + 4;
		List<PlayerCache> playerCacheList = mongoTemplate.find(new Query(
				new Criteria("level").gte(minLevel).lte(maxLevel).ne(level)
						.and("occupyUserId").ne(userid).and("faction").gt(0)).limit(LIMIT),
				PlayerCache.class);
		return playerCacheList;
	}

	/**
	 * 完全随机一个玩家信息
	 * 
	 * @return
	 */
	public PlayerCache randomAPlayerCache() {
		double rand = Math.random();
		logger.info("============================随机数为" + rand);
		/*PlayerCache playerCache = mongoTemplate.findOne(new Query(new Criteria(
				"ra").gte(rand).and("faction").gt(0)), PlayerCache.class);
		if (null == playerCache) {
			playerCache = mongoTemplate.findOne(
					new Query(new Criteria("ra").lte(rand).and("faction").gt(0)), PlayerCache.class);
		}*/
		List<PlayerCache> somePlayerCache = mongoTemplate.find(new Query(new Criteria("ra").gte(rand)
				.and("faction").gt(0)).limit(50), PlayerCache.class);
		Random rd = new Random();
		PlayerCache playerCache = null;
		if(null != somePlayerCache && somePlayerCache.size() != 0){
			playerCache = somePlayerCache.get(rd.nextInt(somePlayerCache.size()));
		}
		else{
			somePlayerCache = mongoTemplate.find(new Query(new Criteria("ra").lte(rand)
					.and("faction").gt(0)).limit(50), PlayerCache.class);
			if(null != somePlayerCache && somePlayerCache.size() != 0){
				playerCache = somePlayerCache.get(rd.nextInt(somePlayerCache.size()));
			}
		}
		return playerCache;
	}

	/**
	 * 随机一定数量的玩家信息
	 * 
	 * @param num
	 *            随机出的玩家数量
	 * @param level
	 *            玩家自己当前等级
	 * @param userid
	 *            玩家id
	 * @return
	 */
	public List<PlayerCache> randomCertainNumPlayerCache(int num, short level,
			long userid) {
		List<PlayerCache> returnList = new ArrayList<PlayerCache>();
		List<PlayerCache> cacheList1 = new ArrayList<PlayerCache>();
		List<PlayerCache> cacheList2 = new ArrayList<PlayerCache>();

		// 如果玩家等级为1-3
		if (level >= 1 && level <= 3) {
			cacheList1 = findSameLevelAndNotOccupied(level, (int) userid);
			if (cacheList1.size() >= num) {// 如果与玩家同等级数量已达到
				returnList = randomFromList(cacheList1, num);
			} else {
				returnList = cacheList1;
				cacheList2 = findRangeLevelAndNotOccupied(level, (int) userid);
				
				/*for(int i=0;i<cacheList2.size();i++){//去掉重复，上面已经判断
					for(PlayerCache pc : returnList){
						if(pc.getUserid() == cacheList2.get(i).getUserid()){
							cacheList2.remove(i);
							i --;
							break;
						}
					}
				}*/
				
				if (cacheList2.size() >= num - cacheList1.size()) {// 如果自己等级为n，n-4≤n≤n+4的空城，数量已达到
					returnList.addAll(randomFromList(cacheList2, num
							- cacheList1.size()));
				} else {// 以上2种情况都不未找到需要的数量，则完全随机
					returnList.addAll(cacheList2);
					int nowSize = returnList.size();
					if(mongoTemplate.getCollection(mongoTemplate.getCollectionName(PlayerCache.class)).count() <= num){//如果总数小于等于需求数
						returnList = mongoTemplate.findAll(PlayerCache.class);//只有在数据库中数据量小于num时执行
						int index = 0;
						boolean needRemove = false;
						//去掉自己
						for(int i=0;i<returnList.size();i++){
							if(returnList.get(i).getUserid() == (int)userid){
								needRemove = true;
								index = i;
								break;
							}
						}
						if(needRemove){
							returnList.remove(index);
						}
					}
					else{
						for(int i=0;i<num - nowSize;i++){
							PlayerCache pc = randomAPlayerCache();
							int circleTimes = 0;
							while (isAlreadyIn(returnList, pc, (int) userid)) {
								circleTimes++;
								if (circleTimes > 200) {
									break;
								}
								pc = randomAPlayerCache();
								logger.info("randomCertainNumPlayerCache1");
							}
							if(!isAlreadyIn(returnList, pc, (int) userid)){
								returnList.add(pc);
							}
						}
					}

				}
			}
		}
		// 如果玩家等级大于等于4
		if (level >= 4) {
			cacheList1 = findSameLevel(level, (int) userid);
			if (cacheList1.size() >= num) {// 如果与玩家同等级数量已达到
				returnList = randomFromList(cacheList1, num);
			} else {
				returnList = cacheList1;
				cacheList2 = findRangeLevel(level, (int) userid);
				
				/*for(int i=0;i<cacheList2.size();i++){//去掉重复,上面已经判断
					for(PlayerCache pc : returnList){
						if(pc.getUserid() == cacheList2.get(i).getUserid()){
							cacheList2.remove(i);
							i --;
							break;
						}
					}
				}*/
				
				if (cacheList2.size() >= num - cacheList1.size()) {// 如果自己等级为n，n-4≤n≤n+4的附属城，数量已达到
					returnList.addAll(randomFromList(cacheList2, num
							- cacheList1.size()));
				} else {// 以上2种情况都不未找到需要的数量，则完全随机
					returnList.addAll(cacheList2);
					int nowSize = returnList.size();
					if (mongoTemplate.getCollection(mongoTemplate.getCollectionName(PlayerCache.class)).count() <= num) {// 如果总数小于等于需求数
						returnList = mongoTemplate.findAll(PlayerCache.class);// 只有在数据库中数据量小于num时执行
						int index = 0;
						boolean needRemove = false;
						// 去掉自己
						for (int i = 0; i < returnList.size(); i++) {
							if (returnList.get(i).getUserid() == (int) userid) {
								needRemove = true;
								index = i;
								break;
							}
						}
						if (needRemove) {
							returnList.remove(index);
						}

					} else {
						for (int i = 0; i < num - nowSize; i++) {
							PlayerCache pc = randomAPlayerCache();
							int circleTimes = 0;
							while (isAlreadyIn(returnList, pc, (int) userid)) {
								circleTimes++;
								if (circleTimes > 200) {
									break;
								}
								pc = randomAPlayerCache();
								logger.info("randomCertainNumPlayerCache2");
							}
							if(!isAlreadyIn(returnList, pc, (int) userid)){
								returnList.add(pc);
							}
							
						}
					}
				}
			}
		}

		/**
		 * 按要求存放，空城在前，被占领的城在后
		 */
		List<PlayerCache> ruleList = new ArrayList<PlayerCache>();
		for (PlayerCache pc : returnList) {
			if (pc.getOfficerId() == 0) {
				ruleList.add(pc);
			}
		}
		for (PlayerCache pc : returnList) {
			if (pc.getOfficerId() != 0) {
				ruleList.add(pc);
			}
		}
		for (PlayerCache pc : returnList) {
			logger.info("附属城随机玩家============" + pc.getOfficerId());
		}

		return ruleList;
	}

	public List<PlayerCache> randomFromList(List<PlayerCache> list, int num) {
		List<PlayerCache> randomList = new ArrayList<PlayerCache>();
		if (list.size() == num) {
			randomList = list;
		}
		if (list.size() > num) {
			Collections.shuffle(list);
			randomList = list.subList(0, num);
		}
		return randomList;

	}
	

	private boolean isAlreadyIn(List<PlayerCache> list,
			PlayerCache playerCache, int userid) {
		if (userid == playerCache.getUserid()) {// 如果是自己
			return true;
		}
		if (userid == playerCache.getOccupyUserId()) {// 如果是被当前玩家占领过的附属城
			return true;
		}
		for (PlayerCache p : list) {
			if (p.getUserid() == playerCache.getUserid()) {
				return true;
			}
		}
		return false;

	}

}
