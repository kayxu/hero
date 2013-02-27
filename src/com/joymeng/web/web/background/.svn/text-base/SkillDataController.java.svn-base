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

import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.building.Building;
import com.joymeng.game.domain.building.BuildingManager;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.skill.Skill;
import com.joymeng.game.domain.skill.SkillManager;
import com.joymeng.web.service.biz.BizBuild;
import com.joymeng.web.service.biz.BizHero;
@Controller
@RequestMapping(value = "/skilldata")
public class SkillDataController {
	@Autowired
	BizBuild build;
	@Autowired
	BizHero hero;
	@RequestMapping(method = RequestMethod.GET)
	public String handle(ModelMap modelMap) {
		return "background/skilldata";
	}
	/**
	 * 数据查看
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "search/{heroid}")
	public String search(@PathVariable("heroid") int heroid,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		PlayerHero ph=hero.getHero(heroid);
		String skArray[] = ph.getSkill().split(",");
		List<Map<String,String>> lst=new ArrayList<Map<String,String>>();
		String attri[]={"水","风","火","土","雷","暗"};
		for(int i=0;i<skArray.length;i++){
			Skill sk=SkillManager.getInstance().getSKill(Integer.parseInt(skArray[i]));
			if(sk==null){
				continue;
			}
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", String.valueOf(sk.getId()));
			map.put("name",sk.getName());
			map.put("level", String.valueOf(sk.getLevel()));
//			水 0；风1 ；火2 ；土3 ；雷4;暗 5
			map.put("attri", attri[sk.getAttri()]);
			lst.add(map);
		}
		modelMap.put("userId", ph.getUserId());
		modelMap.put("list", lst);
		return "background/skilldata";
	}
	
}
