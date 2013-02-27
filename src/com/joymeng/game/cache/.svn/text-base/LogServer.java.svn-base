package com.joymeng.game.cache;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.joymeng.game.cache.respository.LogInterface;
import com.joymeng.game.cache.respository.PlayerCacheDAO;
import com.joymeng.web.web.background.BgMessageController.Message;

public class LogServer {
	private MongoTemplate mongoTemplate;
	private PlayerCacheDAO playerCacheDAO;
	private LogInterface logChche;
	public static LogServer instance;
	public static LogServer getInstance(){
		if (null == instance) {
			instance = new LogServer();
		}
		return instance;
	}
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	public List<?> getFightEvent(int userId, Class c) {
		List<?> list = mongoTemplate.find(
				new Query(new Criteria("userId").is(userId)), c);
		return list;
	}
	public void delete(String id){
		try{
			mongoTemplate.remove(new Query(Criteria.where("id").is(id)),
					Message.class);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public List<?> getByTime(Class c, int limit) {
		Query query = new Query();
		query.sort().on("timestamp", Order.DESCENDING);
		if (limit > 0) {
			query.limit(limit);
		}
		List<?> list = mongoTemplate.find(query, c);
		return list;
	}
	public PlayerCacheDAO getPlayerCacheDAO() {
		return playerCacheDAO;
	}
	public void setPlayerCacheDAO(PlayerCacheDAO playerCacheDAO) {
		this.playerCacheDAO = playerCacheDAO;
	}
	public LogInterface getLogChche() {
		return logChche;
	}
	public void setLogChche(LogInterface logChche) {
		this.logChche = logChche;
	}
}
