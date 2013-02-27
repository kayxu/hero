package com.joymeng.game.domain.shop;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;

import com.joymeng.core.db.cache.couchbase.CouchBaseUtil;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.web.entity.Sale;

public class GoodsManager {
	// 日志
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(GoodsManager.class);
	private static GoodsManager instance;

	private GoodsManager() {
	}

	public static GoodsManager getInstance() {
		if (instance == null)
			instance = new GoodsManager();
		return instance;
	}

	private Map<Integer, Goods> goodsMap = new TreeMap<Integer, Goods>();
	
	private int[] hotGoodsId = new int[8];// 热门的道具
	private int[] hotGoodsNum = new int[8];

	/**
	 * @return GET the goodsMap
	 */
	public Map<Integer, Goods> getGoodsMap() {
		return goodsMap;
	}

	/**
	 * @param SET
	 *            goodsMap the goodsMap to set
	 */
	public void setGoodsMap(Map<Integer, Goods> goodsMap) {
		this.goodsMap = goodsMap;
	}

	public void load(String path) throws Exception {

		List<Object> list = GameDataManager.loadData(path, Goods.class);
		for (Object obj : list) {
			Goods data = (Goods) obj;
			goodsMap.put(data.getId(), data);
			// //初始商店道具到cache中
			// String value=CouchBaseUtil.get("goods_"+data.getId());
			// if(value==null){
			// CouchBaseUtil.put("goods_"+data.getId(), "0", 0);
			// }
		}
		logger.info("商品数据加载完成！" + goodsMap.size());
	}

	public Goods getGood(int id) {
		if (goodsMap == null)
			return null;
		return goodsMap.get(id);
	}

	/**
	 * 根据道具id取物品
	 * 
	 * @param propsId
	 * @return
	 */
	public Goods getGoodProps(int propsId) {
		for (Goods good : goodsMap.values()) {
			if (good.getGoodId() == propsId) {
				return good;
			}
		}
		return null;
	}

	public int getSaleNum(int id,List<Sale> list){
		for(Sale s:list){
			if(s.getId()==id){
				return s.getNum(); 
			}
		}
		return -1;
	}
	/**
	 * 计算热门商品
	 * 遍历物品列表，查询每个物品的购买数量
	 * 进行排序
	 */
	public void mathGoodProps(String start,String end) {
		PriorityQueue<Goods> pq = new PriorityQueue<Goods>(8,
				new MyComparator());
	
		List<Sale> list=MongoServer.getInstance().getLogServer().getLogChche()
				.getEachPropsBuyNum(start + " 0:0:0", end + " 24:0:0");
//		for(Sale s:list){
//			System.out.println("id="+s.getId()+" num="+s.getNum());
//		}
		int i = 0;
		for (Goods good : goodsMap.values()) {
			int id = good.getGoodId();
			int num = getSaleNum(id,list);
			good.set_num(num);
			if (i < 8) {
				pq.offer(good);
			} else {
				if (num > ((Goods) pq.peek()).get_num()) {
					pq.poll();
					pq.offer(good);
				}
			}
			i++;
			if (num > 0) {
				logger.info("goods id=" + id + " buyNum=" + num);
			}

		}
		//清除上一次记录
		for(int j=0;j<hotGoodsNum.length;j++){
			hotGoodsNum[j]=0;	
			hotGoodsId[j]=0;
		}
		i = 0;
		while (pq.size() > 0) {
			Goods good = pq.poll();
			logger.info("sort num=" + good.get_num() + " id==" + good.getGoodId());
			if(good.get_num()>0){
				hotGoodsNum[i] = good.get_num();
				hotGoodsId[i] = good.getGoodId();
			}
			i++;
		}
	}

	public int[] getHotGoodsId() {
		return hotGoodsId;
	}

	public void setHotGoodsId(int[] hotGoodsId) {
		this.hotGoodsId = hotGoodsId;
	}

	public int[] getHotGoodsNum() {
		return hotGoodsNum;
	}

	public void setHotGoodsNum(int[] hotGoodsNum) {
		this.hotGoodsNum = hotGoodsNum;
	}

	class MyComparator implements Comparator<Goods> {

		@Override
		public int compare(Goods o1, Goods o2) {
			return o1.get_num() - o2.get_num();
		}

	}
}
