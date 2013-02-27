package com.joymeng.web.web.background;

import hirondelle.date4j.DateTime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.utils.FileUtil;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.GameServerApp;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.cache.domain.QuestTemp;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.world.OnlineNum;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.request.GameListRequest;
import com.joymeng.game.net.request.ServerInnerRequest;
import com.joymeng.services.core.JoyServiceApp;
import com.joymeng.web.entity.Sale;

@Controller
@RequestMapping("/bg_server")
public class BgServerController {
	@RequestMapping
	public String handle(HttpServletRequest request, ModelMap modelMap) {
		// String name[] = { "prop1", "prop2", "prop3" };
		// int id[] = { 10, 100, 1000 };
		// int num[] = { 10, 100, 1000 };
		// List<Sale> sales = new ArrayList<Sale>();
		// for (int i = 0; i < id.length; i++) {
		// Sale s = new Sale();
		// s.setId(id[i]);
		// s.setName(name[i]);
		// s.setNum(num[i]);
		// sales.add(s);
		// }
		return "background/bg_server";
	}

	/**
	 * 请求服务器列表
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JSONException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(params = "method=search", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap update(HttpServletRequest request,
			final HttpServletResponse response, ModelMap modelMap) throws ClientProtocolException, IOException, IllegalStateException, JSONException {
		//返回服务器列表
		//处理服务器间通讯,请求最新服务器列表
		try{
			GameListRequest req = new GameListRequest();
			//设置具体要通讯的服务器的instanceId,发送给登录服务器
			req.setDestInstanceID(GameConfig.serverList);
			JoyServiceApp.getInstance().sendMessage(req);
			Thread.sleep(1000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		List<OnlineNum> list=World.getInstance().gameOnlineList;
		for(OnlineNum on:list){
			System.out.println("id="+on.getGameId()+" name="+on.getGameName()+" status="+on.getGameState());
		}
//		List<OnlineNum> list=new ArrayList<OnlineNum>();
//		
//	        // The underlying HTTP connection is still held by the response object 
//	        // to allow the response content to be streamed directly from the network socket. 
//	        // In order to ensure correct deallocation of system resources 
//	        // the user MUST either fully consume the response content  or abort request 
//	        // execution by calling HttpGet#releaseConnection().
//	        JSONObject json = null;  
//	        DefaultHttpClient httpclient =null;
//	        try {
//	        	 httpclient = new DefaultHttpClient();
//	 	        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/server");
//
//	 	        HttpResponse res = httpclient.execute(httpGet);
//
//	            System.out.println(res.getStatusLine());
//	            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
//	                HttpEntity entity = res.getEntity();  
//	                json = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(), HTTP.UTF_8)));  
//	                JSONArray servers=json.getJSONArray("D");
//	                int num=servers.length();
//	                for (int i=0;i<servers.length();i++){
//	                	JSONObject server=servers.getJSONObject(i);
//	                	System.out.println("id="+server.getInt("Id"));
//	                	System.out.println("name="+server.getString("Name"));
//	                	System.out.println("players="+server.getInt("Players"));
//	                	System.out.println("type="+server.getInt("Type"));
//	                	System.out.println("status="+server.getInt("Status"));
//	                	OnlineNum on=new OnlineNum();
//	                	on.setGameId(server.getInt("Id"));
//	                	on.setGameName(server.getString("Name"));
//	                	on.setGameType(server.getInt("Type"));
//	                	on.setGameState((byte)server.getInt("Status"));
//	                	on.setOnlinePlayerNum(server.getInt("Players"));
//	                	list.add(on);
//	                }
//	                
//	            }  
////	            HttpEntity entity1 = response1.getEntity();
//	            // do something useful with the response body
//	            // and ensure it is fully consumed
////	            EntityUtils.consume(entity1);
//	        } finally {
//	        	httpclient.getConnectionManager().shutdown();
////	            ((Object) httpGet).releaseConnection();
//	        }
		modelMap.put("data", list);
		modelMap.put("time", TimeUtils.nowLong());
		return modelMap;
	}
	/**
	 * 更改服务器状态
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = "method=change", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap changeStatus(HttpServletRequest request,
			final HttpServletResponse response, ModelMap modelMap) {
		String type=request.getParameter("type");
		GameServerApp.type=Byte.parseByte(type);
		//通知登录服务器状态改变
		int num = World.getInstance().getOnlinePlayerNum();
		//处理服务器间通讯
		try{
			ServerInnerRequest req = new ServerInnerRequest(num,GameServerApp.type);
			//设置具体要通讯的服务器的instanceId,发送给登录服务器
			req.setDestInstanceID(0xFFFE);
			JoyServiceApp.getInstance().sendMessage(req);
			Thread.sleep(1000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			GameListRequest req = new GameListRequest();
			//设置具体要通讯的服务器的instanceId,发送给登录服务器
			req.setDestInstanceID(0xFFFE);
			JoyServiceApp.getInstance().sendMessage(req);
			Thread.sleep(1000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		List<OnlineNum> list=World.getInstance().gameOnlineList;
		for(OnlineNum on:list){
			System.out.println("id="+on.getGameId()+" name="+on.getGameName()+" status="+on.getGameState());
		}
		modelMap.put("data", list);
		return modelMap;
	}
	/**
	 * 保存当天数据
	 */
	@RequestMapping(value = "data/create")
	public String saveActiveData( HttpServletRequest request) {
	            	String start = GameUtils.getHttpQuest("from", request);
	        		String end = GameUtils.getHttpQuest("to", request);
	        		MongoUtil.doJob(start,end);
		return "background/bg_output";
	}
//	/**
//	 * 任务id=1的玩家数
//	 */
//	@RequestMapping(value = "data/test")
//	@ResponseBody
//	public List<Map<String, ?>> test(ModelMap modelMap){
//		List<QuestTemp> list=MongoServer.getInstance().getMongoTemplate().find(new Query(new Criteria("maxId").is(1)), QuestTemp.class);
//		System.out.println("list size="+list.size());
//		int num=0;
//		for(QuestTemp qt:list){
//			RoleData rd=	DBManager.getInstance().getWorldDAO().getName(qt.getUid());
//			if(rd!=null){
//				System.out.println("name="+rd.getName());
//				num++;
//			}
//		}
//		List<Map<String, ?>> lst=new ArrayList<Map<String, ?>>();
////		Map<String, String> _map = new HashMap<String, String>();
////		_map.put("num",String.valueOf( num));
////		lst.add(_map);
////		modelMap.put("data", lst);
//		return lst;
//	}
	/**
	 * 查询下载信息
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "download/search")
	@ResponseBody
	public List<Map<String, ?>> downloadSearch(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
//		String start = GameUtils.getHttpQuest("from", request);
		List<Map<String, ?>> lst=getDownloadList(request);
		modelMap.put("data", lst);
		return lst;
	}
	/**
	 * 导出下载信息
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 */
	@RequestMapping(value = "download/export")
	public void downloadExport(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		List<Map<String, ?>> lst = getDownloadList(request);
		FileUtil.download("text_1" + ".xls",lst, response);
	}
	public List<Map<String, ?>> getDownloadList(HttpServletRequest request) {
		String start = GameUtils.getHttpQuest("from", request);
		DateTime endTime=TimeUtils.getTime(start).plusDays(1);
		String end=endTime.format(TimeUtils.FORMAT);
		List<Map<String, ?>> lst = new ArrayList<>();
		
		Map<String, String> _map = new HashMap<String, String>();
//		_map.put("級別", String.valueOf(i));
//		_map.put("數量", String.valueOf(num));
		int num=0;
		try {
			System.out.println(""+DBManager.getInstance().getBgDAO().getDataSource().getConnection().isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//apk 下载完成
		num=DBManager.getInstance().getBgDAO().countAPK(start+" 00:00:00", end+" 00:00:00");
		 _map = new HashMap<String, String>();
		_map.put("apk下载数", String.valueOf(num));
		lst.add(_map);
		//资源下载完成
		num=DBManager.getInstance().getBgDAO().countSource(start+" 00:00:00", end+" 00:00:00");
		 _map = new HashMap<String, String>();
		_map.put("资源下载数", String.valueOf(num));
		lst.add(_map);
		//解压完成
		num=DBManager.getInstance().getBgDAO().countUnzip(start+" 00:00:00", end+" 00:00:00");
		 _map = new HashMap<String, String>();
		_map.put("解压完成", String.valueOf(num));
		lst.add(_map);
		//注册完成
		num=DBManager.getInstance().getWorldDAO().countReg(start+" 00:00:00", end+" 00:00:00");
		 _map = new HashMap<String, String>();
		_map.put("注册数", String.valueOf(num));
		lst.add(_map);
		return lst;
	}
//	final String script = "db.hero.findOne({\"className\":\"com.joymeng.core.log.GameLog\"});";
	/**
	 * 查询下载信息
	 * 
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "script")
	@ResponseBody
	public List<Map<String, ?>> script(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
//		String script=request.getParameter("script");
//		String result=MongoServer.getInstance().script(script.trim());
		List<Map<String, ?>> lst = new ArrayList<>();
//		Map<String, String> _map = new HashMap<String, String>();
//		_map.put("执行脚本", script);
//		lst.add(_map);
//		_map = new HashMap<String, String>();
//		_map.put("执行结果", result);
//		lst.add(_map);
//		modelMap.put("data", lst);
		return lst;
	}
	
	
	
	
}
