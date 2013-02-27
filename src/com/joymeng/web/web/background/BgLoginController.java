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
import com.joymeng.game.cache.LogUserInterface;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.cache.domain.ActiveUser;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.LoyalUser;
import com.joymeng.game.cache.domain.NewUser;
import com.joymeng.game.common.GameUtils;

/**
 *            
 *            登录用户，活跃用户，忠诚用户，新增用户
 * 
 * */
@Controller
@RequestMapping(value = "/bg_login")
public class BgLoginController {

//
//	/**
//	 * 保存当天数据
//	 */
//	@RequestMapping(value = "login/save")
//	public String saveLoginData() {
//		String time = TimeUtils.now().format(TimeUtils.FORMAT);
//		MongoUtil.saveLogin(time);
//		return "background/main";
//	}
//
//	/**
//	 * 保存当天数据
//	 */
//	@RequestMapping(value = "active/save")
//	public String saveActiveData() {
//		String time = TimeUtils.now().format(TimeUtils.FORMAT);
//		MongoUtil.saveActiveUser(time);
//		return "background/main";
//	}
//
//	/**
//	 * 保存当天数据
//	 */
//	@RequestMapping(value = "loyal/save")
//	public String saveLoyalData() {
//		String time = TimeUtils.now().format(TimeUtils.FORMAT);
//		MongoUtil.saveLoyalUser(time);
//		return "background/main";
//	}
//	/**
//	 * 保存新增玩家
//	 * @return
//	 */
//	@RequestMapping(value = "new/save")
//	public String saveNewUser(){
//		String time = TimeUtils.now().format(TimeUtils.FORMAT);
//		MongoUtil.saveNewUser(time);
//		return "background/main";
//	}
	/**
	 * 查询
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "search/login")
	@ResponseBody
	public List<Map<String, ?>> search2(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getUserList(request,Login.class);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "export/login")
	public void export1(HttpServletResponse response, HttpServletRequest request) {
		List<Map<String, ?>> lst = getUserList(request,Login.class);
		FileUtil.download("text_1" + ".xls",lst, response);
	}

	/**
	 * 查询活跃用户
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "search/active")
	@ResponseBody
	public List<Map<String, ?>> active2(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getUserList(request,ActiveUser.class);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出活跃用户
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "export/active")
	public void active2Export(HttpServletResponse response,
			HttpServletRequest request) {
		List<Map<String, ?>> lst = getUserList(request,ActiveUser.class);
		FileUtil.download("text_1" + ".xls",lst, response);
	}


	/**
	 * 查询忠诚用户
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "search/loyal")
	@ResponseBody
	public List<Map<String, ?>> loyal(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getUserList(request,LoyalUser.class);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 导出忠诚用户
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "export/loyal")
	public void loyalExport(HttpServletResponse response,
			HttpServletRequest request) {
		List<Map<String, ?>> lst = getUserList(request,LoyalUser.class);
		FileUtil.download("text_1" + ".xls",lst, response);
	}

	/**
	 * 查找新增玩家
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "search/new")
	@ResponseBody
	public List<Map<String, ?>>  newUserSearch(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap){
		List<Map<String, ?>> lst = getUserList(request,NewUser.class);
		modelMap.put("data", lst);
		return lst;
	}
	/**
	 * 导出新增玩家
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(value = "export/new")
	public void newUserExport(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap){
		List<Map<String, ?>> lst = getUserList(request,NewUser.class);
		FileUtil.download("text_1" + ".xls",lst, response);
	}
	
	/**
	 * 取得指定类型的玩家的数量
	 * @param request
	 * @param c
	 * @return
	 */
	public List<Map<String, ?>> getUserList(HttpServletRequest request,Class c) {
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		List<Map<String, ?>> lst = new ArrayList<>();
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, c);
		for (int i = 0; i < list.size(); i++) {
			LogUserInterface log=(LogUserInterface)list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("时间", log.getTime());
			map.put("用户数量", String.valueOf(log.getTotal()));
			lst.add(map);
		}
		return lst;
	}
}
