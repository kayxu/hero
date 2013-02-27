package com.joymeng.web.web.background;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.question.UserQuestion;

/**
 * @see RequestMapping 参数
 * 
 * @param value
 *            需要跳转的地址
 * 
 * @param mehtod
 *            基于RestFul的跳转参数,有RequestMethod.get post,put 等
 * 
 * @param params
 *            符合某个参数的时候才调用该方法
 * 
 * @param headers
 *            符合头信息的时候才调用
 * 
 * */
@Controller
@RequestMapping(value = "/bg_question")
public class BgQuestionController {
	public static int pageSize = 20;

	@RequestMapping
	public String getMainPage() {
		return "background/bg_question";
	}

	@RequestMapping(value = "/{page}")
	public String page(@PathVariable("page") int page,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		// String exp = request.getParameter("i");
		// String gameMoney = request.getParameter("gameMoney");
		// String joyMoney = request.getParameter("joyMoney");
		// String chip = request.getParameter("chip");
		// String militaryMedals = request.getParameter("militaryMedals");
		// String achieve = request.getParameter("achieve");
		// String cityLevel = request.getParameter("cityLevel");
		int fromIndex = page * pageSize;
		List<UserQuestion> list = DBManager.getInstance().getWorldDAO()
				.getUserQuestionDAO()
				.queryUserQuestionByPage(fromIndex, pageSize);
		for (UserQuestion uq : list) {
			System.out.println("question:" + uq.getContent());
		}
		modelMap.put("questions", list);
		return "background/bg_question";
	}

	@RequestMapping(value = "time")
	@ResponseBody
	public List<UserQuestion> time(HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		String start = request.getParameter("from");
		String end = request.getParameter("to");
		if(start==null||end==null){
			start = TimeUtils.now().format(TimeUtils.FORMAT)+" 00:00:00";
			end = TimeUtils.now().plusDays(1).format(TimeUtils.FORMAT)+" 00:00:00";
		}
		List<UserQuestion> list = DBManager.getInstance().getWorldDAO()
				.getUserQuestionDAO().queryUserQuestionByTime(start, end);
		return list;
	}
}
