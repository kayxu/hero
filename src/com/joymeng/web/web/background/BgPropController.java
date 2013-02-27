package com.joymeng.web.web.background;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joymeng.core.utils.FileUtil;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.MongoUtil;
import com.joymeng.game.cache.domain.DiamondPlayer;
import com.joymeng.game.cache.domain.DiamondUse;
import com.joymeng.game.cache.domain.Login;
import com.joymeng.game.cache.domain.LoyalUser;
import com.joymeng.game.cache.domain.PropSale;
import com.joymeng.game.cache.domain.PropUse;
import com.joymeng.game.common.GameUtils;
import com.joymeng.web.entity.Sale;
import com.joymeng.web.entity.User;
import com.mongodb.BasicDBObject;

@Controller
@RequestMapping("/bg_prop")
public class BgPropController {
//	@RequestMapping(method = RequestMethod.GET)
//	public String handle(HttpServletRequest request, ModelMap modelMap) {
//		// String name[] = { "prop1", "prop2", "prop3" };
//		// int id[] = { 10, 100, 1000 };
//		// int num[] = { 10, 100, 1000 };
//		// List<Sale> sales = new ArrayList<Sale>();
//		// for (int i = 0; i < id.length; i++) {
//		// Sale s = new Sale();
//		// s.setId(id[i]);
//		// s.setName(name[i]);
//		// s.setNum(num[i]);
//		// sales.add(s);
//		// }
//		return "background/bg_prop";
//	}
//
//	/**
//	 * 查询道具使用，进行累计计算
//	 * 
//	 * @param request
//	 * @param response
//	 * @param modelMap
//	 * @return
//	 */
//	@RequestMapping(params = "method=search", method = RequestMethod.POST)
//	@ResponseBody
//	public ModelMap update(HttpServletRequest request,
//			final HttpServletResponse response, ModelMap modelMap) {
//		String start = request.getParameter("start");
//		String end = request.getParameter("end");
//		if (start == null || end == null || start.equals("") || end.equals("")) {
//			start = TimeUtils.now().format("YYYY-MM-DD");
//			end = start;
//		}
//		List<Sale> list = new ArrayList<Sale>();
//		String type = request.getParameter("type");
//		if (type.equals("0")) {
//			list = MongoServer.getInstance().getLogChche()
//					.getEachPropsBuyNum(start + " 0:0:0", end + " 24:0:0");
////			PropSale ps = new PropSale();
////			ps.setTime("2012-11.19");
////			ps.setList(list);
////			MongoServer.getInstance().save("propSale", ps);
//		} else if (type.equals("1")) {
//			list = MongoServer.getInstance().getLogChche()
//					.getEachPropsUseNum(start + " 0:0:0", end + " 24:0:0");
////			PropUse pu = new PropUse();
////			pu.setTime("2012-11.19");
////			pu.setList(list);
////			MongoServer.getInstance().save("propUse", pu);
//		} else {
//			list = MongoServer.getInstance().getLogChche()
//					.getUseDiamond(start + " 0:0:0", end + " 24:0:0");
////			DiamondUse ds = new DiamondUse();
////			ds.setTime("2012-11.19");
////			ds.setList(list);
////			MongoServer.getInstance().save("diamondUse", ds);
//		}
//		modelMap.put("data", list);
//		return modelMap;
//	}

	/**
	 * 数据查看
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "search/{type}")
	@ResponseBody
	public List<Map<String, ?>> search(@PathVariable("type") byte type,
			HttpServletResponse response, HttpServletRequest request,
			ModelMap modelMap) {
		Class c = null;
		switch (type) {
		case 0://消费钻石的人数
			c = DiamondPlayer.class;
			break;
		case 1:
			c = PropSale.class;
			break;
		case 2:
			c = PropUse.class;
			break;
		case 3:
			c = DiamondUse.class;
			break;
		default:
			return null;
		}
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		List<Map<String, ?>> lst = getList(start, end, c,type);
		modelMap.put("data", lst);
		return lst;
	}

	/**
	 * 数据导出
	 * 
	 * @param type
	 * @param response
	 */
	@RequestMapping(value = "export/{type}")
	public void export(@PathVariable("type") byte type,HttpServletRequest request,
			HttpServletResponse response) {
		Class c = null;
		switch (type) {
		case 0://消费钻石的人数
			c = DiamondUse.class;
			break;
		case 1:
			c = PropSale.class;
			break;
		case 2:
			c = PropUse.class;
			break;
		case 3:
			c = DiamondUse.class;
			break;
		default:
			return ;
		}
		String start = GameUtils.getHttpQuest("from", request);
		String end = GameUtils.getHttpQuest("to", request);
		List<Map<String, ?>> lst = getList(start, end, c,type);
		FileUtil.download("text_1" + ".xls",lst, response);
	}

	public List<Map<String, ?>> getList(String start, String end, Class c,byte type) {
		List<Map<String, ?>> lst = new ArrayList<>();
		if (start==null|end==null||start.equals("") || end.equals("")) {
			start = TimeUtils.now().format(TimeUtils.FORMAT);
			end = start;
		}
		String ids[] = TimeUtils.getDays(start, end);
		List<?> list = MongoServer.getInstance().getBgServer().getByTime(ids, c);
		for (int i = 0; i < list.size(); i++) {
			List<Sale> slist=null;
			PropSale ps=null;
			PropUse pu=null;
			DiamondUse du=null;
			DiamondPlayer dp=null;
			switch(type){
			case 0:
				dp = (DiamondPlayer) list.get(i);
				break;
			case 1:
				ps = (PropSale) list.get(i);
				slist=ps.getList();
				break;
			case 2:
				pu=(PropUse) list.get(i);
				slist=pu.getList();
				break;
			case 3:
				du=(DiamondUse)list.get(i);
				slist=du.getList();
				break;
		
			}
			if(type!=0){
				for (Sale s : slist) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("时间", s.getTime());
					map.put("道具id", String.valueOf(s.getId()));
					map.put("道具名称", s.getName());
					map.put("道具数量", String.valueOf(s.getNum()));
					lst.add(map);
				}
			}else{
				Map<String, String> map = new HashMap<String, String>();
				map.put("时间", dp.getTime());
				map.put("消费钻石人数", String.valueOf(dp.getIds().size()));
				lst.add(map);
			}

		}
		return lst;
	}

	/**
	 * 保存当天数据
	 */
	@RequestMapping(value = "propsale/save")
	public String savePropSaleData() {
		String time = TimeUtils.now().format(TimeUtils.FORMAT);
		MongoUtil.savePropSale(time, (byte) 1);
		return "background/main";
	}

	/**
	 * 保存当天数据
	 */
	@RequestMapping(value = "propuse/save")
	public String savePropUseData() {
		String time = TimeUtils.now().format(TimeUtils.FORMAT);
		MongoUtil.savePropSale(time, (byte) 2);
		return "background/main";
	}

	/**
	 * 保存当天数据
	 */
	@RequestMapping(value = "diamonduse/save")
	public String saveDiamondUseData() {
		String time = TimeUtils.now().format(TimeUtils.FORMAT);
		MongoUtil.savePropSale(time, (byte) 3);
		return "background/main";
	}
}
