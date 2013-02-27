package com.joymeng.web.web.background;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.utils.FileUtil;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.cache.domain.GameOnlineNum;
import com.joymeng.game.common.GameUtils;

@Controller
@RequestMapping("/bg_onlineNum")
public class BgOnlineNumController {

//	@RequestMapping
//	public String handle(HttpServletRequest request, ModelMap modelMap) {
//		// 查询当日登录人数,本周登录人数
//		// List<ShopCache> list = MongoServer.getInstance().getShopCache()
//		// .getTopSell(8);
//		String start = TimeUtils.now().format("YYYY-MM-DD");
//		// Map<Long, Integer> map = MongoServer.getInstance().getLogChche()
//		// .getMaxOnlineNum(start + " 0:0:0", start + " 24:0:0");
//
//		return "background/bg_onlineNum";
//	}
//
//	/**
//	 * 
//	 * @param request
//	 * @param response
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping(params = "method=search", method = RequestMethod.POST)
//	@ResponseBody
//	public ModelMap updateChart(HttpServletRequest request,
//			final HttpServletResponse response, ModelMap modelMap) {
//		String start = request.getParameter("start");
//		Map<Long, Integer> map = MongoServer.getInstance().getLogChche()
//				.getOnlineNum(start + " 0:0:0", start + " 24:0:0");
//		modelMap.put("time", start);
//		modelMap.put("data", map);
//
//		return modelMap;
//	}
//	@RequestMapping(value = "search")
//	@ResponseBody
//	public ModelMap updateChart2(HttpServletRequest request,
//			final HttpServletResponse response, ModelMap modelMap) {
//		String start = TimeUtils.now().format(TimeUtils.FORMAT);
//		String end=start;
//		String ids[] = TimeUtils.getDays(start, end);
//		Map<Long, Integer> map=new HashMap<Long,Integer>();
//		List<?> list = MongoServer.getInstance().get(ids, GameOnlineNum.class);
//		for (int i = 0; i < list.size(); i++) {
//			GameOnlineNum gon = (GameOnlineNum) list.get(i);
//			List<OnlineNum> olist=gon.getList();
//			for(OnlineNum on:olist){
//				long time=on.getTimestamp().getTime();
//				map.put(time, on.getNum());
//			}
//		}
//		modelMap.put("data", map);
//
//		return modelMap;
//	}
	

	/**
	 * 保存当天数据
	 */
	@RequestMapping(value = "save")
	public String saveLoginData() {
		String time = TimeUtils.now().format(TimeUtils.FORMAT);
		MongoUtil.saveOnlineNum(time);
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
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, GameOnlineNum.class);
		for (int i = 0; i < list.size(); i++) {
			GameOnlineNum gon = (GameOnlineNum) list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("时间", gon.getTime());
			map.put("最大在线人数", String.valueOf(gon.getMax()));
			map.put("平均在线人数", String.valueOf(gon.getAverage()));
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
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, GameOnlineNum.class);
		for (int i = 0; i < list.size(); i++) {
			GameOnlineNum gon = (GameOnlineNum) list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("时间", gon.getTime());
			map.put("最大在线时间", String.valueOf(gon.getMax()));
			map.put("平均在线时间", String.valueOf(gon.getAverage()));
			lst.add(map);
		}
		String ret = FileUtil.download("text_1" + ".xls",
				FileUtil.createExcelExportBuf(null, null, null, lst), response);
	}

}
