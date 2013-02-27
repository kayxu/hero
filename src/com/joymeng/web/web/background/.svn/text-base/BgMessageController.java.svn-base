package com.joymeng.web.web.background;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.groovy.tools.shell.util.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joymeng.core.chat.NoticeManager;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.web.service.task.taskMessage;

@Controller
@RequestMapping("/bg_message")
public class BgMessageController {
	@RequestMapping
	public String handle(HttpServletRequest request, ModelMap modelMap) {
		List<?> list=MongoServer.getInstance().getLogServer().getByTime(Message.class, 10);
		List<Map<String, ?>> lst = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			Message message = (Message) list.get(i);
			System.out.println(message.getMessage());
			Map<String, String> map = new HashMap<String, String>();
			map.put("id",message.getId());
			map.put("message",message.getMessage());
			map.put("date",message.getTimestamp().toString());
			map.put("isAuto",message.isAuto);
			lst.add(map);
		}
		modelMap.put("list", lst);
		return "background/bg_message";
	}

//	/**
//	 * 
//	 * @param response
//	 * @param request
//	 */
//	@RequestMapping(value = "world")
//	@ResponseBody
//	public ModelMap world(HttpServletResponse response,
//			HttpServletRequest request, ModelMap modelMap) {
//		String message = request.getParameter("message");
//		System.out.println("send world message=" + message);
//		GameUtils.sendWolrdMessage(new TipMessage(message,
//				ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_SUCCESS),
//				GameUtils.SCROLL);
//		modelMap.put("message", "提交成功");
//		MongoServer.getInstance().save("message", new Message(message));
//		return modelMap;
//	}
	/**
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "world")
	public String world(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		String isAuto = request.getParameter("add");
		String message = request.getParameter("message");
		if(message == null || "".equals(message)){
			return "";
		}
		if(null==isAuto||"".equals(isAuto)){
			System.out.println("send world message=" + message);
			GameUtils.sendWolrdMessage(new TipMessage(message,
					ProcotolType.FIGHT_RESP, GameConst.GAME_RESP_SUCCESS),
					GameUtils.SCROLL);
			MongoServer.getInstance().getBgServer().save("message", new Message(message));
		}else if("1".equals(isAuto)){
			//添加自动发送
			Message m = new Message(message);
			m.setIsAuto("T");
			MongoServer.getInstance().getBgServer().save("message", m);
			taskMessage.getInstance().load();
		}
		
		return "";
	}


/**
 * 
 * @param response
 * @param request
 */
@RequestMapping(value = "delete")
public String del(HttpServletResponse response,
		HttpServletRequest request, ModelMap modelMap) {
	String id = request.getParameter("id");
	if(null != id && !"".equals(id)){
		MongoServer.getInstance().getLogServer().delete(id);
	}
	return "";
}







@Document
public class Message {
	public Message(String message){
		this.message=message;
		this.timestamp=new Date();
		this.isAuto = "F";
	}
	@Id
	private String id;
	private String message;
	private Date timestamp;
	public String isAuto;

	public String getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
}
