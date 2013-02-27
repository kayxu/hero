package com.joymeng.core.db.cache.mongo.Repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.joymeng.core.db.cache.mongo.domain.GoodsCache;
import com.joymeng.core.db.cache.mongo.domain.ShopCache;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.shop.PlayerShopManager;

public class GoodsCacheImpl implements GoodsInterface {
	private Logger logger = LoggerFactory.getLogger(GoodsCacheImpl.class);
	MongoTemplate mongoTemplate;
	String collectionName;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
		collectionName = mongoTemplate.getCollectionName(GoodsCache.class);
	}

	public void insert(GoodsCache goodsCache) {

		if (!mongoTemplate.collectionExists(GoodsCache.class)) {
			mongoTemplate.createCollection(GoodsCache.class);
		}
		mongoTemplate.insert(goodsCache);

	}
	//查询每一个玩家的购买情况
	@Override
	public List<GoodsCache> getBuyRecordByUid(int uid) {
		Query query=new Query(new Criteria("uid").is(uid));
		List<GoodsCache> cacheList = mongoTemplate.find(query, GoodsCache.class);
		StringBuilder sb=new StringBuilder();
		sb.append("getBuyRecordByUid=").append(uid).append("\n");
		for(GoodsCache gc:cacheList){
//			System.out.println(" gc1 id=="+gc.getPropId()+" num=="+gc.getPropNum());
			sb.append(gc.print()).append("\n");
		}
		logger.info(sb.toString());
		return cacheList;
	}
	//查询某个时间段内的某个物品的消耗情况
	@Override
	public List<GoodsCache> getBuyRecordByGid(int propId) {
//		Date start=new Date(2012-1900,10-1,9,0,0,1);
//		Date end=new Date(2012-1900,10-1,9,10,0,0);
//		System.out.println(start.toLocaleString());
//		Query query=new Query(new Criteria("timestamp").gt(start).lte(end).and("propId").is(3));
		Query query=new Query(new Criteria("propId").is(propId));
		StringBuilder sb=new StringBuilder();
		sb.append("getBuyRecordByGid=").append(propId).append("\n");
		List<GoodsCache> cacheList = mongoTemplate.find(query, GoodsCache.class);
		for(GoodsCache gc:cacheList){
			sb.append(gc.print()).append("\n");
		}
		logger.info(sb.toString());
		return cacheList;
	}
		/**
		 * 统计各个道具的销售情况，并输出到“jmr1_out”
		 */
	@Override
	public MapReduceResults<ShopCache> mathGoodsSell(){
		long time=TimeUtils.nowLong();
		if(!mongoTemplate.collectionExists(GoodsCache.class)){
			return null;
		}
		if(mongoTemplate.collectionExists("jmr1_out")){
//			mongoTemplate.createCollection("test");
			mongoTemplate.dropCollection("jmr1_out");
		}
		StringBuilder sb=new StringBuilder();
		sb.append("mathGoodsSell").append("\n");
		String mapFunction = "function () { emit(this.propId, this.propNum)}";
		String reduceFunction = "function (key, values) {    var sum = 0;    for (var i = 0; i < values.length; i++) sum += values[i];    return sum;}";
		MapReduceResults<ShopCache> mr=mongoTemplate.mapReduce("goodsCache", mapFunction, reduceFunction, new MapReduceOptions().outputCollection("jmr1_out"),ShopCache.class);
		for(ShopCache sc:mr){
			sb.append(sc).append("\n");
		}
		sb.append("time =").append(TimeUtils.nowLong()-time);
		logger.info(sb.toString());
		return mr;
//		values.forEach(function(v) {
//			       count+=v.pv;
//	    });
//		mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction, entityClass);
//		mongoTemplate.mapReduce(query, inputCollectionName, mapFunction, reduceFunction, entityClass)
//		mongoTemplate.mapReduce(inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, entityClass)
//		mongoTemplate.mapReduce(query, inputCollectionName, mapFunction, reduceFunction, mapReduceOptions, entityClass)
	}
}
