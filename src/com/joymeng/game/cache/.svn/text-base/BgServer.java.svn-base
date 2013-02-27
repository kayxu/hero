package com.joymeng.game.cache;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.joymeng.game.cache.respository.LogInterface;
import com.joymeng.game.cache.respository.PlayerCacheDAO;

public class BgServer {
	private MongoTemplate mongoTemplate;
	public static BgServer instance;
	public static BgServer getInstance(){
		if (null == instance) {
			instance = new BgServer();
		}
		return instance;
	}
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 保存对象到某一个collection
	 * 修改为保存到后台数据库
	 * @param collectionName
	 * @param obj
	 */
	public void save(String collectionName, Object obj) {
		if (!mongoTemplate.collectionExists(collectionName)) {
			mongoTemplate.createCollection(collectionName);
		}
		mongoTemplate.save(obj, collectionName);
	}
	public void drop(Class c){
		if (mongoTemplate.collectionExists(c)) {
			mongoTemplate.dropCollection(c);
		}
	}
	/**
	 * 获得所有数据，如果数据非常多，不建议直接使用该方法
	 * @param c
	 * @return
	 */
	public List<?> getAll(Class c){
		return mongoTemplate.findAll(c);
	}
	/**
	 * 按照指定日期进行查找
	 * @param id
	 * @param c
	 * @return
	 */
	public List<?> getByTime(String id[], Class c) {
		List<?> list = mongoTemplate.find(
				new Query(new Criteria("time").in(id)), c);
		return list;
	}
	
	/**
	 * 按照玩家id查找
	 * 
	 * @param ids
	 * @return
	 */
	public List<?> getById(Set<Integer> ids, Class c) {
		List<?> list = mongoTemplate.find(
				new Query(new Criteria("userid").in(ids)), c);
		return list;
	}

	
	
}
