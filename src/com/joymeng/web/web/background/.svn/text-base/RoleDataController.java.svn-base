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

import com.joymeng.core.chat.ChatChannel;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.utils.NumberUtil;
import com.joymeng.core.utils.StringUtil;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.building.Building;
import com.joymeng.game.domain.building.BuildingManager;
import com.joymeng.game.domain.building.PlayerBarrackManager;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.building.PlayerSoldier;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.item.equipment.EquipPrototype;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.quest.AcceptedQuest;
import com.joymeng.game.domain.quest.PlayerQuestAgent;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.skill.Skill;
import com.joymeng.game.domain.soldier.Soldier;
import com.joymeng.game.domain.soldier.SoldierManager;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.web.service.biz.BizPlayer;

@Controller
@RequestMapping("/roledata")
public class RoleDataController extends BaseController {
	int count = 0;// 总页码

	@RequestMapping
	public String getRoleDataAll(HttpServletRequest request, ModelMap modelMap) {
		String types = request.getParameter("types");// 类型
		String numerical = request.getParameter("numerical");// 数值
		String symbol = request.getParameter("symbol");// 符号
		String pages = request.getParameter("pages");// 页码
		String from = request.getParameter("from");// 页码
		String to = request.getParameter("to");// 页码
		boolean isRe = false;
		if(numerical != null && !"".equals(numerical)){
			isRe = true;
		}
		System.out.println(from);
		System.out.println(to);
		if (pages != null && !"".equals(pages)) {
			modelMap.put("pages", pages);
		} else {
			modelMap.put("pages", 1);
		}
		if (types != null && !"".equals(types)) {
			modelMap.put("types", types);
			if (from != null && !"".equals(from) && to != null && !"".equals(to) && ("11".equalsIgnoreCase(types) || "12".equalsIgnoreCase(types))) {
				modelMap.put("from", from);
				modelMap.put("to", to);
				isRe= true;
			}
		}
		
		if (symbol != null && !"".equals(symbol)) {
			modelMap.put("symbol", symbol);
		}
		if (numerical != null && !"".equals(numerical)) {
			modelMap.put("numerical", numerical);
		} else if(!isRe){
			super.loadDataAll(request, modelMap, "roledata");
			return "background/roledata";
		}
		count = BizPlayer.getInstance()._getPlayerListByCondition(request,
				modelMap);
		super.loadDataAll(request, modelMap,
				"submits('/quickstart/roledata?pages=");
		return "background/roledata";
	}

	@RequestMapping(params = "method=detail")
	public String getRoleDataDetail(HttpServletRequest request,
			ModelMap modelMap) {
		BizPlayer.getInstance()._getPlayerById(request, modelMap);
		BizPlayer.getInstance()._getPlayerByIdItem(request, modelMap);
		return "background/roledata_detail";
	}

	@RequestMapping(params = "method=save")
	public String saveRoleDataDetail(HttpServletRequest request,
			ModelMap modelMap, RoleData data) {
		System.out.println(data);
		BizPlayer.getInstance()._savePlayer(data, modelMap);
		BizPlayer.getInstance()._getPlayerByIdItem(request, modelMap);
		return "background/roledata_detail";
	}

	@RequestMapping(params = "method=item")
	public String getRoleDataItemDetail(HttpServletRequest request,
			ModelMap modelMap) {
		// //player._getPlayerByIdItem(request, modelMap);
		// // modelMap.put("closeFlag", "open");
		// String userid = request.getParameter("id");
		// return "background/roledata_item_detail";
		String userid = request.getParameter("id");
		PlayerCharacter pc = null;
		if (NumberUtil.isNumeric(userid)) {
			pc = World.getInstance().getPlayer(Integer.parseInt(userid));
		}
		if (pc != null) {
			BizPlayer.getInstance()._savePlayerCell(request, modelMap, pc);
		}

		BizPlayer.getInstance()._getPlayerById(request, modelMap);
		BizPlayer.getInstance()._getPlayerByIdItem(request, modelMap);
		return "background/roledata_detail";
	}

	@RequestMapping(params = "value=itemsave")
	public String saveRoleDataItemDetail(HttpServletRequest request,
			ModelMap modelMap) {
		String userid = request.getParameter("id");
		PlayerCharacter pc = null;
		if (NumberUtil.isNumeric(userid)) {
			pc = World.getInstance().getPlayer(Integer.parseInt(userid));
		}
		if (pc != null) {
			BizPlayer.getInstance()._savePlayerCell(request, modelMap, pc);
		}

		return "background/roledata_detail";
	}

	@Override
	public int _getCountPage() {
		return count;
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
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
		if (pc.getPlayerQuestAgent() == null) {
			pc.setPlayerQuestAgent(new PlayerQuestAgent(pc));
		}
		modelMap.put("user", pc.getData());
		// 建筑
		List<PlayerBuilding> blist = pc.getPlayerBuilgingManager()
				.getAllUpgrade();
		for (PlayerBuilding pb : blist) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(pb.getId()));
			Building b = BuildingManager.getInstance().getBuilding(
					pb.getBuildingID());
			map.put("name", b.getName());
			map.put("level", String.valueOf(pb.getBuildLevel()));
			map.put("status", String.valueOf(pb.getBuildType()));
			lst.add(map);
		}
		modelMap.put("builddata", lst);
		// 将领
		PlayerHero[] heroList = pc.getPlayerHeroManager().getHeroArray();
		modelMap.put("herodata", heroList);
		// 技能
		List<Map<String, ?>> lst6 = new ArrayList<Map<String, ?>>();
		for (int i = 0; i < heroList.length; i++) {
			PlayerHero ph = heroList[i];
			List<Skill> list = ph.getSkills();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("skill", list);
			map.put("id", ph.getId());
			lst6.add(map);
		}
		modelMap.put("heroskill", lst6);
		// 将领装备
		List<Map<String, ?>> lst7 = new ArrayList<Map<String, ?>>();
		for (int i = 0; i < heroList.length; i++) {
			PlayerHero ph = heroList[i];
			List<Equipment> list = ph.getEquips();
			if (list.size() == 0) {
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("equip", list);
			map.put("id", ph.getId());
			lst7.add(map);
		}
		modelMap.put("heroequip", lst7);
		// 任务
		List<Map<String, String>> lst2 = new ArrayList<Map<String, String>>();
		List<AcceptedQuest> qlist = pc.getPlayerQuestAgent()
				.getAcceptedQuests();
		// String status[]={"","已接","未完成","完成"};
		for (AcceptedQuest aq : qlist) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(aq.getQ().getId()));
			map.put("name", aq.getQ().getName());
			map.put("status", String.valueOf(aq.getStatus()));
			lst2.add(map);
		}
		modelMap.put("questdata", lst2);
		// 士兵
		PlayerBarrackManager pb = pc.getPlayerBuilgingManager()
				.getPlayerBarrack();
		List<Map<String, String>> lst3 = new ArrayList<Map<String, String>>();
		if (pb != null) {
			Map<Integer, PlayerSoldier> soldierMap = pb.getSoldier();
			for (Integer key : soldierMap.keySet()) {
				PlayerSoldier ps = soldierMap.get(key);
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(ps.getSoldierId()));
				Soldier s = SoldierManager.getInstance().getSoldier(
						ps.getSoldierId());
				map.put("name", s.getName());
				map.put("num", String.valueOf(ps.getSoldierCount()));
				lst3.add(map);
			}
		}

		modelMap.put("soldierdata", lst3);
		// 背包
		List<Map<String, String>> lst4 = new ArrayList<Map<String, String>>();
		List<Cell> props = pc.getPlayerStorageAgent().getGoods(Item.ITEM_PROPS);
		if (props != null) {
			for (Cell cell : props) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(cell.getItem().getId()));
				map.put("name", cell.getItem().getName());
				map.put("num", String.valueOf(cell.getItemCount()));
				lst4.add(map);
			}
		}

		modelMap.put("propdata", lst4);
		List<Map<String, String>> lst5 = new ArrayList<Map<String, String>>();
		List<Cell> equips = pc.getPlayerStorageAgent().getGoods(
				Item.ITEM_EQUIPMENT);
		if (equips != null) {
			for (Cell cell : equips) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(cell.getItem().getId()));
				map.put("name", cell.getItem().getName());
				map.put("num", String.valueOf(cell.getItemCount()));
				lst5.add(map);
			}
		}
		modelMap.put("equipdata", lst5);
		// 战报
		pc.getFightEventManager().init();
//		List<FightEvent> fightEvents = pc.getFightEventManager()
//				.getFightEventList();
//		modelMap.put("fightdata", fightEvents);
		// cell
		List<PropsPrototype> allProps = PropsManager.getInstance()
				.getAllPrototype();
		List<EquipPrototype> allEquips = EquipmentManager.getInstance()
				.getAllEquipment();
		modelMap.put("cell_prop", allProps);
		return "background/new_detail";
	}

	/**
	 * 禁言
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "speak/{userid}")
	@ResponseBody
	public ModelMap speak(@PathVariable("userid") int userid,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		byte canSpeak = pc.getData().getCanSpeak();
		if (canSpeak == 0) {
			pc.getData().setCanSpeak((byte) 1);// 禁止玩家言发
			modelMap.put("success", "禁言中");
		} else {
			pc.getData().setCanSpeak((byte) 0);// 解除禁止玩家言发
			modelMap.put("success", "未禁言");
		}
		modelMap.put("type", "speak");
		modelMap.put("id", userid);
		return modelMap;
	}

	/**
	 * 冻结
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "login/{userid}")
	@ResponseBody
	public ModelMap login(@PathVariable("userid") int userid,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		byte canLogin = pc.getData().getCanLogin();
		if (canLogin == 0) {// 禁止登陆
			fobidden(pc);
			modelMap.put("success", "冻结中");

		} else {// 解除禁止
			pc.getData().setCanLogin((byte) 0);
			modelMap.put("success", "未冻结");

		}
		modelMap.put("type", "login");
		modelMap.put("id", userid);
		return modelMap;
	}

	public void fobidden(PlayerCharacter pc) {
		pc.getData().setCanLogin((byte) 1);
		World.getInstance().kickRole(pc);
	}

	/**
	 * 修改玩家基础信息
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "basic/{userid}")
	@ResponseBody
	public ModelMap basic(@PathVariable("userid") int userid,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		String exp = request.getParameter("exp");
		String gameMoney = request.getParameter("gameMoney");
		String joyMoney = request.getParameter("joyMoney");
		String chip = request.getParameter("chip");
		String militaryMedals = request.getParameter("militaryMedals");
		String achieve = request.getParameter("achieve");
		String cityLevel = request.getParameter("cityLevel");
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		RoleData data = pc.getData();
		if (data.getCanLogin() == 1) {
			data.setExp(Integer.parseInt(exp));
			data.setGameMoney(Integer.parseInt(gameMoney));
			data.setJoyMoney(Integer.parseInt(joyMoney));
			data.setChip(Integer.parseInt(chip));
			data.setMilitaryMedals(Integer.parseInt(militaryMedals));
			data.setAchieve(Integer.parseInt(achieve));
			data.setCityLevel(Integer.parseInt(cityLevel));
			pc.setData(data);
		}

		modelMap.put("id", userid);
		return modelMap;
	}

	/**
	 * 修改玩家基础信息
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "addProp/{userid}")
	@ResponseBody
	public ModelMap addProp(@PathVariable("userid") int userid,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		// String exp = request.getParameter("i");
		// String gameMoney = request.getParameter("gameMoney");
		// String joyMoney = request.getParameter("joyMoney");
		// String chip = request.getParameter("chip");
		// String militaryMedals = request.getParameter("militaryMedals");
		// String achieve = request.getParameter("achieve");
		// String cityLevel = request.getParameter("cityLevel");
		Map<String, String[]> map = request.getParameterMap();
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		RoleData data = pc.getData();
		for (String id : map.keySet()) {
			String[] num = map.get(id);
			int n=Integer.parseInt(num[0]);
			if(n>0){
				System.out.println(id+":"+num[0]);
				int i=Integer.parseInt(id);
				pc.getPlayerStorageAgent().addProps(i, n);
			}
			
		}
		modelMap.put("id", userid);
		return modelMap;
	}
	
	/**
	 * 修改玩家基础信息
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "addBuild/{userid}")
	@ResponseBody
	public ModelMap addBuild(@PathVariable("userid") int userid,HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		RoleData data = pc.getData();
		if (data.getCanLogin() == 1) {
//			System.out.println("==="+userid);
//			System.out.println(request.getParameter("obid"));
			String obid = request.getParameter("obid");
			if(null!= obid && StringUtils.isInteger(obid)){
				pc.getPlayerBuilgingManager().addBuild(Integer.parseInt(obid), 0);
			}
			
		}
		modelMap.put("id", userid);
		return modelMap;
	}

	/**
	 * 修改玩家任务信息
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "quest/{userid}/{questid}")
	@ResponseBody
	public ModelMap quest(@PathVariable("userid") int userid,
			@PathVariable("questid") int questid, HttpServletResponse response,
			HttpServletRequest request, ModelMap modelMap) {
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		RoleData data = pc.getData();
		if (data.getCanLogin() == 1) {
			if (pc.getPlayerQuestAgent() == null) {
				pc.setPlayerQuestAgent(new PlayerQuestAgent(pc));
			}
			AcceptedQuest aq = pc.getPlayerQuestAgent().getAcceptQuest(questid);
			aq.setStatus(PlayerQuestAgent.COMPLETE);
		}
		modelMap.put("id", userid);
		return modelMap;
	}

	/**
	 * 修改玩家士兵信息
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "soldier/{userid}")
	@ResponseBody
	public ModelMap soldier(@PathVariable("userid") int userid,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		Map<String, String[]> map = request.getParameterMap();
		PlayerCharacter pc = World.getInstance().getPlayer(userid);
		RoleData data = pc.getData();
		if (data.getCanLogin() == 1) {
			for (String id : map.keySet()) {
				String[] num = map.get(id);
				pc.getPlayerBuilgingManager()
						.getPlayerBarrack()
						.addSoliderNum(Integer.parseInt(id),
								Integer.parseInt(num[0]));
			}
		}
		modelMap.put("id", userid);
		return modelMap;
	}
	
	@RequestMapping(params = "method=send", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap sendMessage(
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		
		System.out.println(request.getParameter("userids"));
		System.out.println(request.getParameter("sendvalue"));
		NoticeManager.getInstance().sendSystemWorldMessageToPlayer(request.getParameter("sendvalue"), Integer.parseInt(request.getParameter("userids")));
		return modelMap;
	}

}
