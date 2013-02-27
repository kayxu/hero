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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.joymeng.game.domain.building.Building;
import com.joymeng.game.domain.building.BuildingManager;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.web.service.biz.BizBuild;
import com.joymeng.web.service.biz.BizHero;
@Controller
@RequestMapping(value = "/builddata")
public class BuildDataController {
	@Autowired
	BizBuild build;
	@RequestMapping(method = RequestMethod.GET)
	public String handle(ModelMap modelMap) {
		return "background/builddata";
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
		List<PlayerBuilding> list=build.getBuild(userid);
		List<Map<String,String>> lst=new ArrayList<Map<String,String>>();
		for(PlayerBuilding pb:list){
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", String.valueOf(pb.getId()));
			Building b=BuildingManager.getInstance().getBuilding(pb.getBuildingID());
			map.put("name",b.getName() );
			map.put("level", String.valueOf(pb.getBuildLevel()));
			map.put("status", "");
			lst.add(map);
		}
		modelMap.put("list", lst);
		return "background/builddata";
	}
	
}
