package com.joymeng.web.web.background;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.db.cache.mongo.MongoTest;
import com.joymeng.core.utils.FileUtil;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.cache.domain.GameOnlineTime;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.PropSale;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.shop.GoodsManager;
import com.joymeng.web.entity.OnlineTime;
import com.joymeng.web.entity.Sale;

@Controller
@RequestMapping("/bg_onlineTime")
public class BgOnlineTimeController {

	// @RequestMapping
	// public String handle(HttpServletRequest request, ModelMap modelMap) {
	// // 查询当日登录人数,本周登录人数
	// // List<ShopCache> list = MongoServer.getInstance().getShopCache()
	// // .getTopSell(8);
	// String start = TimeUtils.now().format("YYYY-MM-DD");
	// // Map<Long, Integer> map = MongoServer.getInstance().getLogChche()
	// // .getMaxOnlineNum(start + " 0:0:0", start + " 24:0:0");
	//
	// return "background/bg_onlineTime";
	// }
	//
	// /**
	// * 查询登录
	// *
	// * @param request
	// * @param response
	// * @param modelMap
	// * @return
	// */
	// @RequestMapping(params = "method=search", method = RequestMethod.POST)
	// @ResponseBody
	// public ModelMap updateChart(HttpServletRequest request,
	// final HttpServletResponse response, ModelMap modelMap) {
	// String start = request.getParameter("start");
	// String end = request.getParameter("end");
	// List<OnlineTime> list = MongoServer.getInstance().getLogChche()
	// .getOnlineTime(start + " 0:0:0", end + " 24:0:0");
	// // 计算每一天的平均在线时间和最大在线时间
	// long max = 0;
	// long all = 0;
	// List<GameOnlineTime> timeList = new ArrayList<GameOnlineTime>();
	// for (OnlineTime ot : list) {
	// System.out.println(" id=" + ot.getUid() + " time=" + ot.getOnline()
	// + " time=" + ot.getTimestamp());
	// long time = ot.getOnline() / 1000;
	// if (time > max) {
	// max = time;
	// }
	// all += time;
	// Date date = ot.getTimestamp();
	// boolean b = false;
	// for (GameOnlineTime d : timeList) {
	// if (TimeUtils.isSameDay(date.getTime(), d.getDate().getTime())) {//
	// 如果有当天数据
	// d.setTotal(d.getTotal() + time);
	// if (d.getMax() < time) {
	// d.setMax(time);// 最大在线时间
	// }
	// // d.getSet().add(ot.getUid());
	// b = true;
	// break;
	// }
	// }
	// // 当天统计的玩家数量
	// if (!b) {
	// GameOnlineTime d = new GameOnlineTime();
	// d.setDate(date);
	// d.setMax(time);
	// d.setTotal(time);
	// // d.getSet().add(ot.getUid());
	// d.setTime(TimeUtils.getTime(date.getTime()).format("MM-DD"));
	// timeList.add(d);
	// }
	// }
	// modelMap.put("time", start);
	// modelMap.put("data", timeList);
	// return modelMap;
	// }

	/**
	 * 保存当天数据
	 */
	@RequestMapping(value = "save")
	public String saveLoginData() {
		String time = TimeUtils.now().format(TimeUtils.FORMAT);
		MongoUtil.saveOnlineTime(time);
		return "background/main";
	}

	/**
	 * 查询
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public List<Map<String, ?>> search2(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		List<Map<String, ?>> lst = new ArrayList<>();
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, GameOnlineTime.class);
		for (int i = 0; i < list.size(); i++) {
			GameOnlineTime got = (GameOnlineTime) list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("时间", got.getTime());
			map.put("最大在线时间", String.valueOf(got.getMax() / 1000));
			map.put("平均在线时间", String.valueOf(got.getAverage() / 1000));
			lst.add(map);
		}
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "export")
	public void export1(HttpServletResponse response, HttpServletRequest request) {
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		List<Map<String, ?>> lst = new ArrayList<>();
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, GameOnlineTime.class);
		for (int i = 0; i < list.size(); i++) {
			GameOnlineTime got = (GameOnlineTime) list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("时间", got.getTime());
			map.put("最大在线时间", String.valueOf(got.getMax() / 1000));
			map.put("平均在线时间", String.valueOf(got.getAverage() / 1000));
			lst.add(map);
		}
		String ret = FileUtil.download("text_1" + ".xls",
				FileUtil.createExcelExportBuf(null, null, null, lst), response);
	}

	/**
	 * 查询心跳次数与在线时间的分布情况
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "search/new")
	@ResponseBody
	public List<Map<String, ?>> search(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = new ArrayList<>();
		try{
			String start = GameUtils.getHttpQuest("from", request);
			String end = GameUtils.getHttpQuest("to", request);
			String ids[] = TimeUtils.getDays(start, end);
			List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, GameOnlineTime.class);
			
			String onlineTime = request.getParameter("online_time");
			if(onlineTime!=null&&!onlineTime.equals("")){
				String array1[]=onlineTime.split(",");
				long timeArray[]=new long[array1.length+1];
				for (int i = 0; i < list.size(); i++) {
					GameOnlineTime got = (GameOnlineTime) list.get(i);
					List<OnlineTime> otlist=got.getList();
				
					for(OnlineTime ot:otlist){
						long time=ot.getOnline();
						int num=ot.getHeartNum();
						math(array1,timeArray,time);
					}
				}
				for(int i=0;i<timeArray.length;i++){
					Map<String, String> map = new HashMap<String, String>();
					 map.put("在线时间区间（秒）"+i, String.valueOf(timeArray[i]));
					 lst.add(map);
				}
			}
			String heartNum = request.getParameter("heart_num");
			if(heartNum!=null&&!heartNum.equals("")){
				String array2[]=heartNum.split(",");
				long  numArray[]=new long[array2.length+1];
				for (int i = 0; i < list.size(); i++) {
					GameOnlineTime got = (GameOnlineTime) list.get(i);
					List<OnlineTime> otlist=got.getList();
				
					for(OnlineTime ot:otlist){
						long time=ot.getOnline()/1000;
						int num=ot.getHeartNum();
						math(array2,numArray,num);
					}
				}
				for(int i=0;i<numArray.length;i++){
					Map<String, String> map = new HashMap<String, String>();
					 map.put("心跳次数区间:"+i, String.valueOf(numArray[i]));
					 lst.add(map);
				}
			}
		}catch(Exception ex){
			Map<String, String> map = new HashMap<String, String>();
			map.put("数据错误",ex.toString());
			lst.add(map);
		}
	
		modelMap.put("data", lst);
		return lst;
	}
	/**
	 * 计算 n在区间 strArray中的分布，并加入到array数组统计中
	 * @param strArray
	 * @param array
	 * @param n
	 */
	public void math(String[] strArray,long[] array,long n){
			boolean b=false;
			for(int i=0;i<strArray.length;i++){
				long m=Long.parseLong(strArray[i]);
				if(n<m){
					array[i]+=1;
					b=true;
					break;
				}
			}
			if(!b){
				array[array.length-1]+=1;
			}
	}
	// @RequestMapping(value = "export")
	// public void export(HttpServletResponse response) {
	// List<Map<String, ?>> lst = new ArrayList<>();
	// String _time = TimeUtils.now().format(TimeUtils.FORMAT);
	// List<OnlineTime> list = MongoServer.getInstance().getLogChche()
	// .getOnlineTime(_time + " 0:0:0", _time + " 24:0:0");
	// long max = 0;
	// long all = 0;
	// List<GameOnlineTime> timeList = new ArrayList<GameOnlineTime>();
	// for (OnlineTime ot : list) {
	// System.out.println(" id=" + ot.getUid() + " time=" + ot.getOnline()
	// + " time=" + ot.getTimestamp());
	// long time = ot.getOnline() / 1000;
	// if (time > max) {
	// max = time;
	// }
	// all += time;
	// Date date = ot.getTimestamp();
	// boolean b = false;
	// for (GameOnlineTime d : timeList) {
	// if (TimeUtils.isSameDay(date.getTime(), d.getDate().getTime())) {//
	// 如果有当天数据
	// d.setTotal(d.getTotal() + time);
	// if (d.getMax() < time) {
	// d.setMax(time);// 最大在线时间
	// }
	// d.getList().add(ot);
	// // d.getSet().add(ot.getUid());
	// b = true;
	// break;
	// }
	// }
	// // 当天统计的玩家数量
	// if (!b) {
	// GameOnlineTime d = new GameOnlineTime();
	// d.setDate(date);
	// d.setMax(time);
	// d.setTotal(time);
	// d.getList().add(ot);
	// d.setTime(TimeUtils.getTime(date.getTime()).format("MM-DD"));
	// timeList.add(d);
	// }
	// }
	// String collectionName = "gameOnlineTime";
	// for (GameOnlineTime got : timeList) {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("时间", got.getTime());
	// map.put("平均在线时间(秒)", String.valueOf(got.getTotal()/1000));
	// map.put("最高在线时间(秒)", String.valueOf(got.getMax()/1000));
	// lst.add(map);
	// MongoServer.getInstance().save(collectionName, got);
	// }
	// String ret = FileUtil.download("text_1" + ".xls",
	// FileUtil.createExcelExportBuf(null, null, null, lst), response);
	// }
}
