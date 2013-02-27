package com.joymeng.game.cache;

import hirondelle.date4j.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.domain.ActiveUser;
import com.joymeng.game.cache.domain.ActiveUserLevel;
import com.joymeng.game.cache.domain.DiamondPlayer;
import com.joymeng.game.cache.domain.GameOnlineNum;
import com.joymeng.game.cache.domain.GameOnlineTime;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.LoyalUser;
import com.joymeng.game.cache.domain.LoyalUserLevel;
import com.joymeng.game.cache.domain.NewUser;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.cache.domain.PropSale;
import com.joymeng.game.cache.domain.TimeTemp;
import com.joymeng.web.entity.OnlineNum;

public class MongoUtil {
	static Logger logger = LoggerFactory.getLogger(MongoUtil.class);
	/**
	 * 生成指定日期的统计数据
	 * @param start
	 * @param end
	 */
	public static void doJob(String start,String end) {
		logger.info("save data start");
		//对日志按照日期进行分类
		String ids[] = TimeUtils.getDays(start, end);
		//对分类后的日志进行统计
		for(int i=0;i<ids.length;i++){
			MongoServer.getInstance().getLogServer().getLogChche().getAllLogs(ids[i]);
			saveNewUser(ids[i]);
			saveLogin(ids[i]);
			saveOnlineNum(ids[i]);
			saveOnlineTime(ids[i]);
			saveActiveUser(ids[i]);
			saveLoyalUser(ids[i]);
			saveActiveUserLevel(ids[i]);
			saveLoyalUserLevel(ids[i]);
			savePropSale(ids[i], (byte) 1);
			savePropSale(ids[i], (byte) 2);
			savePropSale(ids[i], (byte) 3);
			saveDiamondPlayer(ids[i]);
		}
		logger.info("save data end");
		
	}
	/**
	 * 保存新增玩家
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<NewUser> saveNewUser(String day){
		logger.info("save data saveNewUser");
		List<NewUser> list=MongoServer.getInstance().getLogServer().getLogChche().getNewUser(day);
		for(NewUser nu:list){
			MongoServer.getInstance().getBgServer().save("newUser", nu);
		}
		return list;
 	}
	/**
	 * 保存登录玩家
	 * @param start
	 * @param end
	 */
	public static void saveLogin(String day) {
		logger.info("save data saveLogin");
		List<Login> list = MongoServer.getInstance().getLogServer().getLogChche()
				.getLoginNum(day);
		for (Login login : list) {
			MongoServer.getInstance().getBgServer().save("login", login);
		}
	}
	/**
	 * 保存在线玩家数量
	 * @param start
	 * @param end
	 */
	public static void saveOnlineNum(String day) {
		logger.info("save data saveOnlineNum");
		List<OnlineNum> list = MongoServer.getInstance().getLogServer().getLogChche()
				.getOnlineNum2(day);
		List<GameOnlineNum> goList = new ArrayList<GameOnlineNum>();
		// 把每一条记录按照日期分类
		for (OnlineNum on : list) {
			boolean b = false;
			for (GameOnlineNum go : goList) {
				if (go.add2List(on)) {
					b = true;
					break;
				}
			}
			if (!b) {
				GameOnlineNum go = new GameOnlineNum();
				go.setTime(TimeUtils.getTime(on.getTimestamp().getTime())
						.format(TimeUtils.FORMAT));
				go.add2List(on);
				goList.add(go);
			}
		}
		// 计算每一天的总时间和平均时间...，并且保存
		for (GameOnlineNum go : goList) {
			int total = 0, max = 0, average = 0;
			List<OnlineNum> onList = go.getList();
			for (OnlineNum on : onList) {
				if (max < on.getNum()) {
					max = on.getNum();
				}
				total += on.getNum();
			}
			if (onList.size() != 0) {
				average = total / onList.size();
			}
			go.setMax(max);
			go.setAverage(average);
			MongoServer.getInstance().getBgServer().save("gameOnlineNum", go);
		}
	}
	/**
	 * 保存在线时间
	 * @param start
	 * @param end
	 */
	public static void saveOnlineTime(String day) {
		logger.info("save data saveOnlineTime");
		 MongoServer.getInstance().getBgServer().drop(TimeTemp.class);
		// 取得指定日期内的在线时间数据
		 MongoServer.getInstance().getLogServer().getLogChche()
				.getOnlineTime(day);
		// 计算每一天的总时间和平均时间...，并且保存
		List<?> list=MongoServer.getInstance().getBgServer().getAll(TimeTemp.class);
		long total = 0, max = 0, average = 0;
		for(int i=0;i<list.size();i++){
			TimeTemp tt=(TimeTemp)list.get(i);
			total+=tt.getTime();
			if(max<tt.getTime()){
				max=tt.getTime();
			}
		}
		if(list.size()!=0){
			average=max/list.size();
		}
		GameOnlineTime got=new GameOnlineTime();
		got.setAverage(average);
		got.setMax(max);
		got.setTotal(total);
		MongoServer.getInstance().getBgServer().save("gameOnlineTime", got);
	}
	/**
	 * 获得<now-7,now>时间范围内的登陆过的玩家
	 * 
	 * @param start
	 * @param end
	 */
	public static void saveActiveUser(String day) {
		logger.info("save data saveActiveUser");
		DateTime endTime = TimeUtils.getTime(day);
		DateTime startTime = endTime.plusDays(-7);// 7天前
		String start = startTime.format(TimeUtils.FORMAT);
		List<Map<String, ?>> lst = new ArrayList<>();
//		List<Login> list = MongoServer.getInstance().getLogChche()
//				.getLoginNum(start + " 0:0:0", day + " 24:0:0");
		//查找7天内登录过的玩家数据
		String ids[] = TimeUtils.getDays(start, day);
		List<?> list=MongoServer.getInstance().getBgServer().getByTime(ids, Login.class);
		Set<Integer> newSet = new HashSet<Integer>();
		for (int i=0;i<list.size();i++) {
			Login login =(Login)list.get(i);
			Set<Integer> set = login.getSet();// 当天的有效用户
			for (Integer j: set) {
				newSet.add(j);
			}
		}
		ActiveUser au = new ActiveUser();
		au.setTime(day);
		au.setSet(newSet);
		MongoServer.getInstance().getBgServer().save("activeUser", au);
	}
	/**
	 * 忠诚用户=活跃用户在7天内 有三天登录过的
	 * @param end
	 */
	public static void saveLoyalUser(String end) {
		logger.info("save data saveLoyalUser");
		String ids[] = new String[] { end };
		// 获得当天的活跃用户
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, ActiveUser.class);
		DateTime endTime = TimeUtils.getTime(end);
		DateTime startTime = endTime.plusDays(-7);// 7天前
		String start = startTime.format(TimeUtils.FORMAT);
		ids = TimeUtils.getDays(start, end);
		//获得7天内的登录数据
		List<?> loginList = MongoServer.getInstance().getBgServer().getByTime(ids,Login.class);
		//查找7天内这些活跃用户登录天数
		//key =id,value=登录次数
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i=0;i<loginList.size();i++){
			Login login=(Login)loginList.get(i);
			Set<Integer> set=login.getSet();
			for(Integer id:set){
				if(isExist(id,list)){
					if(map.containsKey(id)){
						map.put(id, (int)map.get(id)+1);
					}else{
						map.put(id, 1);
					}
				}
			}
		}
		//登录天数>3
		LoyalUser lu = new LoyalUser();
		lu.setTime(end);
		for(Integer id:map.keySet()){
			int num=map.get(id);
			if(num>=3){
				lu.getSet().add(id);
			}
		}
		MongoServer.getInstance().getBgServer().save("loyalUser", lu);
	}
	
	
	public static boolean isExist(int id,List<?> list){
		for(int index=0;index<list.size();index++){
			ActiveUser au=(ActiveUser)list.get(index);
			for(Integer _id:au.getSet()){
				if(_id==id){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 获得活跃用户级别分布
	 * 
	 * @param start
	 * @param end
	 */
	public static void saveActiveUserLevel(String day) {
		logger.info("save data saveActiveLevel");
		// 获得指定时间范围内的活跃用户
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(new String[]{day}, ActiveUser.class);
		for (int i = 0; i < list.size(); i++) {
			ActiveUser au = (ActiveUser) list.get(i);
			Set<Integer> set = au.getSet();
			Map<Short, Integer> map = getLevels(set);
			ActiveUserLevel ul = new ActiveUserLevel();
			ul.setTime(au.getTime());
			ul.setMap(map);
			MongoServer.getInstance().getBgServer().save("activeUserLevel", ul);
		}
	}
	/**
	 * 获得忠诚用户级别分布
	 * 
	 * @param start
	 * @param end
	 */
	public static void saveLoyalUserLevel(String day) {
		logger.info("save data saveLoyalLevel");
		// 获得指定时间范围内的忠诚用户
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(new String[]{day}, LoyalUser.class);
		for (int i = 0; i < list.size(); i++) {
			LoyalUser lu = (LoyalUser) list.get(i);
			Set<Integer> set = lu.getSet();
			Map<Short, Integer> map = getLevels(set);
			LoyalUserLevel ul = new LoyalUserLevel();
			ul.setTime(lu.getTime());
			ul.setMap(map);
			MongoServer.getInstance().getBgServer().save("loyalUserLevel", ul);
		}
	}

	/**
	 * 获得玩家的级别分布
	 * 
	 * @param ids
	 * @return
	 */
	public static Map<Short, Integer> getLevels(Set<Integer> ids) {
		List<?> list = MongoServer.getInstance().getBgServer().getById(ids, PlayerCache.class);
		// 统计每个级别的玩家数量
		Map<Short, Integer> map = new HashMap<Short, Integer>();
		for (int i = 0; i < list.size(); i++) {
			PlayerCache pc = (PlayerCache) list.get(i);
			// 包含该级别则+1
			if (map.containsKey(pc.getLevel())) {
				int num = map.get(pc.getLevel());
				map.put(pc.getLevel(), num + 1);
			} else {
				// 不包含则重置
				map.put(pc.getLevel(), 1);
			}
		}
		return map;
	}
	/**
	 * 道具销售,只保存开始时间的数据
	 * 
	 * @param start
	 * @param end
	 */
	public static void savePropSale(String day,byte type) {
		logger.info("save data savePropSale "+type);
		List<PropSale> list = MongoServer.getInstance().getLogServer().getLogChche()
				.getPropsBuyType(day, type);
		String[] collectionName={"","propSale","propUse","diamondUse"};
		for (PropSale ps : list) {
			MongoServer.getInstance().getBgServer().save(collectionName[type], ps);
		}
	}
	/**
	 * 使用钻石的人数
	 * @param start
	 * @param end
	 */
	public static void saveDiamondPlayer(String day) {
		List<DiamondPlayer> list =MongoServer.getInstance().getLogServer().getLogChche().getDiamondPlayerNum(day);
		for (DiamondPlayer dp : list) {
			MongoServer.getInstance().getBgServer().save("diamondPlayer", dp);
		}
	}

}
