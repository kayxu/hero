package com.joymeng.web.web.background;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.groovy.tools.shell.util.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.Log;
import com.joymeng.game.cache.domain.LogQuery;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.web.service.task.taskMessage;
import com.mongodb.DBCursor;

@Controller
@RequestMapping("/bg_log")
public class BgLogController {
	@RequestMapping
	public String handle(HttpServletRequest request, ModelMap modelMap) {
		Map<String, String> map = new HashMap<String, String>();
		// lst.add(map);
		// modelMap.put("list", lst);
		return "background/bg_log";
	}

	private Executor executor = Executors.newFixedThreadPool(10);
	/**
	 * 查找log日志
	 * 由于日志按照日期分类后，该方法无效，暂未修复
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public ModelMap saveActiveData(HttpServletResponse response,
			 HttpServletRequest request, ModelMap modelMap){
//		String start = request.getParameter("from");
//		String end = request.getParameter("to");
//		final String key = request.getParameter("key");
//		final String strAid = request.getParameter("aid");
//		
//		final String strUid = request.getParameter("uid");
//		System.out.println(""+strAid);
//		System.out.println(""+strUid);
//		final MongoConverter converter = MongoServer.getInstance()
//				.getMongoTemplate().getConverter();
//		final StringBuffer buf = new StringBuffer();
//		final long mongWaitSeconds = 30;//超时时间30秒
//		try{
//			LogQuery logQuery = new LogQuery();
//			logQuery.setKeyWord(key);
//			logQuery.setStart(start + " 0:0:0");
//			logQuery.setEnd(end + " 24:0:0");
//			Query query = new BasicQuery(logQuery.toQuery());
//			query.sort().on("timestamp", Order.DESCENDING);
//			final DBCursor cursor = MongoServer.getInstance().getMongoTemplate()
//					.getCollection("hero").find(query.getQueryObject())
//					.sort(query.getSortObject());
//			
//			
//		@SuppressWarnings("unchecked")
//		FutureTask<String> task = new FutureTask(new Callable<String>() {
//			@Override
//			public String call() throws Exception {
//				long startTime = System.currentTimeMillis();
//				// 遍历游标，最长不能超过20秒
//				while (cursor.hasNext()) {
//					Log log = converter.read(Log.class, cursor.next());
//					System.out.println(log.toString());
//					if (key.equals(LogEvent.ARENA.toString())) {// 如果是竞技场类型
//						int aid = 0;
//						if (!strAid.equals("")) {
//							aid = Integer.parseInt(strAid);
//						}
//						int uid = 0;
//						if (!strUid.equals("")) {
//							uid = Integer.parseInt(strUid);
//						}
//						String message = log.getMessage();
//						String strArray[] = message.split("\\|");
//						if (uid != 0) {
//							int id = Integer.parseInt(strArray[0]);
//							if (id == uid) {
//								buf.insert(0, log.toString() + "\n");
//							}
//						} else if (aid != 0) {
//							int arenaId = Integer.parseInt(strArray[2]
//									.substring(6));
//							if (arenaId == aid) {
//								buf.insert(0, log.toString() + "\n");
//							}
//						}else{
//							buf.insert(0, log.toString() + "\n");
//						}
//					} else {
//						buf.insert(0, log.toString() + "\n");
//					}
//
//					long current = System.currentTimeMillis();
//					if ((current - startTime) / 1000 >= mongWaitSeconds)
//						break;
//				}
//				return buf.toString();
//			}
//		});
//		executor.execute(task);
//		try {
//			task.get(mongWaitSeconds + 5, TimeUnit.SECONDS);
//			cursor.close();
//		} catch (Exception e) {
//			task.cancel(true);
//		}
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//
//		modelMap.put("data", buf.toString());
		return modelMap;
	}
	@RequestMapping(value = "check")
	@ResponseBody
	public ModelMap checkArena(HttpServletResponse response,
			 HttpServletRequest request, ModelMap modelMap){
		String message="";
		if(ArenaManager.getInstance().isOpen()){
			message="竞技场未关闭";
		}else{
			 message=ArenaManager.getInstance().check();
		}
		
		modelMap.put("data",message);
		return modelMap;
	}
	@RequestMapping(value = "open")
	@ResponseBody
	public ModelMap openArena(HttpServletResponse response,
			 HttpServletRequest request, ModelMap modelMap){
		boolean open=ArenaManager.getInstance().isOpen();
		ArenaManager.getInstance().setOpen(!open);
		modelMap.put("data",!open?"开启中":"关闭中");
		return modelMap;
	}
}
