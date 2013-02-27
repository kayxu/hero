package com.joymeng.web.web.background;

import hirondelle.date4j.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.utils.ServerInfo;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.GameServerApp;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.ActiveUser;
import com.joymeng.game.cache.domain.LostActiveUser;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.nation.war.WarThread;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.world.World;

@Controller
@RequestMapping("/bg_monitor")
public class BgMonitor {

	@RequestMapping
	public String handle(HttpServletRequest request, ModelMap modelMap) {
		// 查询当日登录人数,本周登录人数
		// List<ShopCache> list = MongoServer.getInstance().getShopCache()
		// .getTopSell(8);
		String start = TimeUtils.now().format("YYYY-MM-DD");
		//Map<Long, Integer> map = MongoServer.getInstance().getLogChche()
		//		.getMaxOnlineNum(start + " 0:0:0", start + " 24:0:0");
		return "background/bg_monitor";
	}
	
	/**
	 * 更新图表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=update", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap updateChart(HttpServletRequest request,
			final HttpServletResponse response, ModelMap modelMap) {
		ServerInfo info=new ServerInfo();
		System.out.println(info.toString());
		modelMap.put("freeMemory", info.getFreeMemory());
		modelMap.put("totalMemory", info.getTotalMemory());
		modelMap.put("cpuLoad", info.getCpuLoad());
		modelMap.put("sysLoad", info.getSysLoad());
		modelMap.put("onlineNum",World.getInstance().getOnlinePlayerNum());
		modelMap.put("isOpen", GameServerApp.FREEZE?0:1);
		return modelMap;
	}
	/**
	 * 关闭/开启登录功能， 
	 * 0 开启
	 * 1关闭
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "login")
	@ResponseBody
	public ModelMap login(
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		GameServerApp.FREEZE=!GameServerApp.FREEZE;
		modelMap.put("isOpen", GameServerApp.FREEZE?0:1);
		return modelMap;
	}
	/**
	 * 踢出所有玩家
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "kickall")
	@ResponseBody
	public ModelMap kickAll(
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		GameServerApp.FREEZE=true;
		 ConcurrentHashMap<Integer, PlayerCharacter> all=World.getInstance().getWorldRoleMap();
		 for(Integer id:all.keySet()){
			 PlayerCharacter pc=all.get(id);
			 World.getInstance().kickRole(pc);
		 }
		 modelMap.put("success", "全部踢出");
		return modelMap;
	}

	/**
	 * 关闭服务器
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "shutdown")
	@ResponseBody
	public ModelMap shutdown(
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		//5秒后关闭服务器
		 Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            public void run() {
	            	 System.out.println("-------设定要指定任务--------");
	            	World.getInstance().stop();
	            }
	        }, 5000);// 设定指定的时间time,此处为2000毫秒
		modelMap.put("success", "服务器5秒后关闭");
		return modelMap;
	}

	/**
	 * 重载玩家缓存
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "reload")
	@ResponseBody
	public ModelMap reload(
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		//先清空MongoDB中PlayerCache数据
		MongoServer.getInstance().getLogServer().getPlayerCacheDAO().dropCollection();
		
		//重新加载PlayerCache数据
		int PAGE_SIZE = 100;//一次查询100条
		int totalSize = DBManager.getInstance().getWorldDAO().countRoles();//总记录条数
		int totalPages;//总页数
		if(totalSize % PAGE_SIZE == 0){
			totalPages = totalSize / PAGE_SIZE;
		}
		else{
			totalPages = (totalSize / PAGE_SIZE) + 1;
		}
		
		long startTime = TimeUtils.nowLong();
		//分页查询出所有玩家数据,并将玩家数据缓存到MongoDB
		for(int i=0;i<totalPages;i++){
			List<RoleData> roleDataList = DBManager.getInstance().getWorldDAO().queryRoleByPage(i * PAGE_SIZE,i * PAGE_SIZE + PAGE_SIZE);
			for(RoleData rd : roleDataList){
				PlayerCharacter playerCharacter = World.getInstance().getPlayer((int)rd.getUserid());
				GameUtils.putToCache(playerCharacter);
			}
		}
		long endTime = TimeUtils.nowLong();
		modelMap.put("success", "重置成功！耗时"+(endTime-startTime)/1000+"秒");
		return modelMap;
	}
//	@RequestMapping(value = "status")
//	@ResponseBody
//	public List<Map<String, ?>> showStatus(HttpServletResponse response,
//			HttpServletRequest request, ModelMap modelMap) {
//		List<Map<String, ?>> lst = MongoServer.getInstance().showStatus();
//		return lst;
//	}
}
