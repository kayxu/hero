package com.joymeng.web.web.background;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.web.service.biz.BizHero;

@Controller
@RequestMapping(value = "/herodata")
public class HeroDataController {
	@Autowired
	private BizHero hero;
	
	@RequestMapping(method = RequestMethod.GET)
	public String handle(ModelMap modelMap) {
		return "background/herodata";
	}
	/**
	 * 数据查看
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "search/{userid}")
	public String search(@PathVariable("userid") int userid,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		List<PlayerHero> list=hero.getHeros(userid);
		modelMap.put("list", list);
		return "background/herodata";
	}

}
