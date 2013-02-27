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
import org.springframework.web.bind.annotation.ResponseBody;

import com.joymeng.game.domain.building.Building;
import com.joymeng.game.domain.building.BuildingManager;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.quest.AcceptedQuest;
import com.joymeng.game.domain.quest.PlayerQuestAgent;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.World;
import com.joymeng.web.service.biz.BizHero;

@Controller
@RequestMapping(value = "/questdata")
public class QuestDataController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String handle(ModelMap modelMap) {
		return "background/questdata";
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
		PlayerCharacter pc = World.getInstance()
				.getPlayer(userid);
		if (pc.getPlayerQuestAgent() == null) {
			pc.setPlayerQuestAgent(new PlayerQuestAgent(pc));
		}
		
		if(pc==null){
			return "background/questdata";
		}
		String status[]={"","已接","未完成","完成"};
		List<AcceptedQuest> list=pc.getPlayerQuestAgent().getAcceptedQuests();
		List<Map<String,String>> lst=new ArrayList<Map<String,String>>();
		for(AcceptedQuest aq:list){
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", String.valueOf(aq.getQ().getId()));
			map.put("name",aq.getQ().getName() );
			map.put("status", status[aq.getStatus()]);
			lst.add(map);
		}
		modelMap.put("list", lst);
		return "background/questdata";
	}

}
