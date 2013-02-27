package com.joymeng.core.db.cache.mongo.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.joymeng.core.db.cache.mongo.domain.GoodsCache;
import com.joymeng.core.db.cache.mongo.domain.ShopCache;

public class ShopCacheImpl implements ShopInterface{
	MongoTemplate mongoTemplate;
	String collectionName;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		collectionName = mongoTemplate.getCollectionName(GoodsCache.class);
	}

	public void insert(ShopCache shopCache) {

		if (!mongoTemplate.collectionExists(GoodsCache.class)) {
			mongoTemplate.createCollection(GoodsCache.class);
		}
		mongoTemplate.insert(shopCache);
	}

	@Override
	public List<ShopCache> getTopSell(int num) {
		if(!mongoTemplate.collectionExists(ShopCache.class)){
			return new ArrayList<ShopCache>();
		}
		Query query=new Query();
		query.sort().on("value",  Order.DESCENDING);
		query.limit(num);
		List<ShopCache> cacheList = mongoTemplate.find(query, ShopCache.class,"jmr1_out");
		for(ShopCache sc:cacheList){
			System.out.println(sc);
		}
		return cacheList;
	}
}
