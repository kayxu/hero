package com.joymeng.web.web.background;

import hirondelle.date4j.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.utils.FileUtil;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.cache.domain.ActiveUser;
import com.joymeng.game.cache.domain.ActiveUserLevel;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.LostActiveUser;
import com.joymeng.game.cache.domain.LoyalUser;
import com.joymeng.game.cache.domain.LoyalUserLevel;
import com.joymeng.game.cache.domain.NewUser;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.cache.domain.PlayerTemp;
import com.joymeng.game.cache.domain.QuestTemp;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;

@Controller
@RequestMapping(value = "/bg_user")
public class BgUserController {

	/**
	 * 活跃用户 保存当天数据
	 */
	@RequestMapping(value = "level/active/save")
	public String saveActiveData() {
		String time = TimeUtils.now().format(TimeUtils.FORMAT);
		MongoUtil.saveActiveUserLevel(time);
		return "background/main";
	}

	/**
	 * 忠诚用户 保存当天数据
	 */
	@RequestMapping(value = "level/loyal/save")
	public String saveLoyalData() {
		String time = TimeUtils.now().format(TimeUtils.FORMAT);
		MongoUtil.saveLoyalUserLevel(time);
		return "background/main";
	}

	/**
	 * 查询活跃用户级别分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "level/active/search")
	@ResponseBody
	public List<Map<String, ?>> activeLevel(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getActiveList(request);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出活跃用户级别分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(value = "level/active/export")
	public void activeLevelExport(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getActiveList(request);
		FileUtil.download("text_1" + ".xls", lst, response);
	}

	public List<Map<String, ?>> getActiveList(HttpServletRequest request) {
		List<Map<String, ?>> lst = new ArrayList<>();
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance()
				.getBgServer().getByTime(ids, ActiveUserLevel.class);
		for (int i = 0; i < list.size(); i++) {
			ActiveUserLevel au = (ActiveUserLevel) list.get(i);
			Map<Short, Integer> map = au.getMap();
			for (Short level : map.keySet()) {
				Map<String, String> _map = new HashMap<String, String>();
				int num = map.get(level);
				System.out.println("level=" + level + " num=" + num);
				_map.put("时间", au.getTime());
				_map.put("級別", String.valueOf(level));
				_map.put("數量", String.valueOf(num));
				lst.add(_map);
			}
		}
		return lst;
	}

	/**
	 * 查询注册用户级别分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "level/reg/search")
	@ResponseBody
	public List<Map<String, ?>> regLevel(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		// String start = GameUtils.getHttpQuest("from", request);
		List<Map<String, ?>> lst = getRegList(request);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出注册用户级别分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(value = "level/reg/export")
	public void regExport(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getRegList(request);
		FileUtil.download("text_1" + ".xls", lst, response);
	}

	public List<Map<String, ?>> getRegList(HttpServletRequest request) {
		List<Map<String, ?>> lst = new ArrayList<>();
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		for (int i = 1; i <= GameConfig.MAX_LEVEL; i++) {
			int num = DBManager.getInstance().getWorldDAO().countRoles(i,start+" 00:00:00", end+" 00:00:00");
			Map<String, String> _map = new HashMap<String, String>();
			// _map.put("时间", start);
			_map.put("級別", String.valueOf(i));
			_map.put("數量", String.valueOf(num));
			lst.add(_map);
		}
		return lst;
	}

	/**
	 * 查询忠诚用户分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "level/loyal/search")
	@ResponseBody
	public List<Map<String, ?>> loyalLevel(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getLoyalList(request);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出忠诚用户分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(value = "level/loyal/export")
	public void loyalLevelExport(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getLoyalList(request);
		FileUtil.download("text_1" + ".xls", lst, response);
	}

	public List<Map<String, ?>> getLoyalList(HttpServletRequest request) {
		List<Map<String, ?>> lst = new ArrayList<>();
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, LoyalUserLevel.class);
		for (int i = 0; i < list.size(); i++) {
			LoyalUserLevel au = (LoyalUserLevel) list.get(i);
			Map<Short, Integer> map = au.getMap();
			for (Short level : map.keySet()) {
				Map<String, String> _map = new HashMap<String, String>();
				int num = map.get(level);
				System.out.println("level=" + level + " num=" + num);
				_map.put("时间", au.getTime());
				_map.put("級別", String.valueOf(level));
				_map.put("數量", String.valueOf(num));
				lst.add(_map);
			}
		}
		return lst;
	}

	@RequestMapping(value = "gamemoney/search")
	@ResponseBody
	public List<Map<String, ?>> gameMoney(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = this.getGameMoney(request);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出忠诚用户分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(value = "gamemoney/export")
	public void gameMoneyExport(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getGameMoney(request);
		String ret = FileUtil.download("text_1" + ".xls",
				FileUtil.createExcelExportBuf(null, null, null, lst), response);
	}

	public List<Map<String, ?>> getGameMoney(HttpServletRequest request) {
		List<Map<String, ?>> lst = new ArrayList<>();
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, ActiveUser.class);
		for (int i = 0; i < list.size(); i++) {
			ActiveUser au = (ActiveUser) list.get(i);
			long money =DBManager.getInstance().getWorldDAO().sumGameMoney(
					au.getSet());
			Map<String, String> _map = new HashMap<String, String>();
			_map.put("时间:", au.getTime());
			_map.put("总金钱:", String.valueOf(money));
			lst.add(_map);
		}
		return lst;
	}

	@RequestMapping(value = "joymoney/search")
	@ResponseBody
	public List<Map<String, ?>> joyMoney(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = this.getJoyMoney(request);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出忠诚用户分布
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(value = "joymoney/export")
	public void joyMoneyExport(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getJoyMoney(request);
		String ret = FileUtil.download("text_1" + ".xls",
				FileUtil.createExcelExportBuf(null, null, null, lst), response);
	}

	public List<Map<String, ?>> getJoyMoney(HttpServletRequest request) {
		List<Map<String, ?>> lst = new ArrayList<>();
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, ActiveUser.class);
		for (int i = 0; i < list.size(); i++) {
			ActiveUser au = (ActiveUser) list.get(i);
			long money = DBManager.getInstance().getWorldDAO().sunJoyMoney(au.getSet());
			Map<String, String> _map = new HashMap<String, String>();
			_map.put("时间:", au.getTime());
			_map.put("总金钱:", String.valueOf(money));
			lst.add(_map);
		}
		return lst;
	}

	/**
	 * 查询流失用户
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "lost/search")
	@ResponseBody
	public List<Map<String, ?>> lost(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = new ArrayList<>();
		// 取得指定日期内的玩家---查询日期14天内的
		String s = GameUtils.getHttpQuest("from", request);
		DateTime startTime = TimeUtils.getTime(s).plusDays(1);
		DateTime endTime = TimeUtils.getTime(s).plusDays(14);
		// 查询第一天的新增用户
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(new String[] { s },
				NewUser.class);
		NewUser newUser = (NewUser) list.get(0);
		Set<Integer> set = newUser.getSet();
		Map<String, String> _map = new HashMap<String, String>();
		_map.put("时间", newUser.getTime());
		_map.put("數量", String.valueOf(set.size()));
		lst.add(_map);
		// 查询第2-14天内的玩家在后面中的登录人数
		String start = startTime.format(TimeUtils.FORMAT);
		String end = endTime.format(TimeUtils.FORMAT);
		String ids[] = TimeUtils.getDays(start, end);
		List<?> loginList = MongoServer.getInstance().getBgServer().getByTime(ids,Login.class);
		for (int i = 0; i < loginList.size(); i++) {
			Login _login = (Login) loginList.get(i);
			int num = compare(set, _login.getSet());
			_map = new HashMap<String, String>();
			_map.put("时间", _login.getTime());
			_map.put("數量", String.valueOf(num));
			lst.add(_map);
		}
		modelMap.put("data", lst);
		return lst;
	}

	// 查询s1中的数据是否在s2中也有
	public int compare(Set<Integer> s1, Set<Integer> s2) {
		int num = 0;
		for (Integer i1 : s1) {
			for (Integer i2 : s2) {
				if ((int) i1 == (int) i2) {
					num++;
					break;
				}
			}
		}
		return num;
	}

	/**
	 * 指定日期的活跃用户，在未来一周内一次都未登陆的玩家
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "activelost/search")
	@ResponseBody
	public List<Map<String, ?>> activeLost(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = new ArrayList<>();
		// 取得指定日期内的玩家---查询日期7天内的
		String s = GameUtils.getHttpQuest("from", request);
		// 查询当天的活跃用户
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(new String[] { s },
				ActiveUser.class);
		// 查询第7天内的玩家在后面中的登录人数
		DateTime startTime = TimeUtils.getTime(s).plusDays(1);
		DateTime endTime = TimeUtils.getTime(s).plusDays(8);
		String start = startTime.format(TimeUtils.FORMAT);
		String end = endTime.format(TimeUtils.FORMAT);
		String ids[] = TimeUtils.getDays(start, end);
		List<?> loginList = MongoServer.getInstance().getBgServer().getByTime(ids,Login.class);
		// 查询登录情况
		Set<Integer> lost = new HashSet<Integer>();
		ActiveUser au = (ActiveUser) list.get(0);
		if(au==null){
			Map<String, String> _map = new HashMap<String, String>();
			_map.put("错误：","没有该天数据,日期="+s);
			lst.add(_map);
			modelMap.put("data", lst);
			return lst;
		}
		for (Integer id : au.getSet()) {
			if (!isLogin(id, loginList)) {
				lost.add(id);
			}
		}
		LostActiveUser lau = new LostActiveUser();
		lau.setTime(s);
		lau.setSet(lost);
		MongoServer.getInstance().getBgServer().save("lostActiveUser", lau);
		// 发送到前端
		Map<String, String> _map = new HashMap<String, String>();
		_map.put("时间", s);
		_map.put("流失數量", String.valueOf(lost.size()));
		lst.add(_map);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 判断某id是否有登录记录
	 * 
	 * @param id
	 * @param loginList
	 * @return
	 */
	public boolean isLogin(int id, List<?> loginList) {
		for (int i = 0; i < loginList.size(); i++) {
			Login _login = (Login) loginList.get(i);
			for (Integer _id : _login.getSet()) {
				if ((int) id == (int) _id) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 活跃用户任务流失
	 * 该方法暂时失效
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "activelost/task/search")
	@ResponseBody
	public List<Map<String, ?>> activeTaskLost(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = new ArrayList<>();
		String s = GameUtils.getHttpQuest("from", request);
		//查询当天的流失用户
		long time1=TimeUtils.nowLong();
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(new String[] { s },
				LostActiveUser.class);
		LostActiveUser lau = (LostActiveUser)list.get(0);
		if(lau==null){
			Map<String, String> _map = new HashMap<String, String>();
			 _map.put("错误", "没有该天数据，日期="+s);
			modelMap.put("data", lst);
			return lst;
		}
		long time2=System.currentTimeMillis();
		System.out.println("查询流失玩家耗时="+(time2-time1)/1000);
//		1-4级 1-64
//		5-9级 65-129
//		10-19 130-220
//		20-30 221-313
		//537 
		int[] idNum=new int[314];
		for(Integer uid:lau.getSet()){
//			//查找每一个玩家的任务情况
//			QuestTemp qt=MongoServer.getInstance().getMongoTemplate().findOne(new Query(new Criteria("uid").is(uid)), QuestTemp.class);
			int maxTaskId=0;
			PlayerCache pc=GameUtils.getFromCache(uid);
			if(pc!=null){
				maxTaskId=pc.getTaskId();
			}
//			if(qt!=null){
//				maxTaskId=qt.getMaxId();
				if(maxTaskId<313){
					idNum[maxTaskId-1]+=1;
				}else{
					idNum[313]+=1;
				}
//			}
		
		}
		// 发送到前端
		for(int i=0;i<idNum.length;i++){
			Map<String, String> _map = new HashMap<String, String>();
			 _map.put("任务id"+i, "完成人数"+String.valueOf(idNum[i]));
			 lst.add(_map);
		}
		modelMap.put("data", lst);
		return lst;
	}
	public int getMax(List<?> taskIds){
		int max=1;
		for(int i=0;i<taskIds.size();i++){
			PlayerTemp pt=(PlayerTemp)taskIds.get(i);
//			System.out.println(pt.getContent());
			int id=pt.getQuestId();
			int type=pt.getQuestType();
			if( type==1){
				if(id>max){
					max=id;
				}
			}
		}
		return max;
	}

}
