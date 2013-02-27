package com.joymeng.core.db.cache.mongo.Repository;

import java.util.List;

import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;

import com.joymeng.core.db.cache.mongo.domain.GoodsCache;
import com.joymeng.core.db.cache.mongo.domain.ShopCache;

public interface GoodsInterface {
	public void insert(GoodsCache goodsCache);
	//获得某个玩家的购买记录
	public List<GoodsCache> getBuyRecordByUid(int uid);
	//获得某个道具的购买记录
	public List<GoodsCache> getBuyRecordByGid(int propId);
	
	public MapReduceResults<ShopCache> mathGoodsSell();
}
