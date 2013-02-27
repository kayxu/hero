package com.joymeng.web.service.biz;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.joymeng.core.utils.NumberUtil;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.web.service.AbstractBusiness;

public class BizWar extends AbstractBusiness {
	/**
	 * 用户查询条件
	 */
	private static final Logger logger = LoggerFactory.getLogger(BizWar.class);

	public static BizWar instance;

	public static BizWar getInstance() {
		if (instance == null) {
			instance = new BizWar();
		}
		return instance;
	}

	public ModelMap _startWar(final HttpServletRequest request,
			final ModelMap modelMap) {
		String fightType = request.getParameter("fightType");
		String fightid = request.getParameter("naid");
		boolean isAuto = true;
		int[] all = new int[]{};
		if(!"".equals(fightid)){
			int[] ids = StringUtils.changeToInt(fightid,",");
			if(ids != null && ids.length > 0){
				all  = ids;
			}
			if(all != null && all.length > 0){
				isAuto = false;
			}
		}
		if (fightType != null && !"".equals(fightType) 
				&& NumberUtil.isNumeric(fightType)) {
			if (!WarManager.getInstance().IS_FIGHT) {
				WarManager.getInstance().FIGHT_START = TimeUtils.getWarTime(" 06:11:00");
				WarManager.getInstance().FIGHT_END = TimeUtils.getWarTime(" 21:00:00");
				WarManager.getInstance().FIGHT_TYPE = Byte.parseByte(fightType);
				switch (Byte.parseByte(fightType)) {
				case 0:
					WarManager.getInstance().FIGHT_NUM = 4;
					WarManager.getInstance().CAMP_NUM = 8;//军营数据
					WarManager.getInstance().IS_REFRESH = true;
					break;

				case 1:
					WarManager.getInstance().FIGHT_NUM = 4;
					WarManager.getInstance().CAMP_NUM = 4;// 军营数据
					WarManager.getInstance().IS_REFRESH = false;
					break;
				case 2:
					WarManager.getInstance().FIGHT_NUM = 1;
					WarManager.getInstance().CAMP_NUM = 4;// 军营数据
					WarManager.getInstance().IS_REFRESH = false;
					break;
				case 3:
					WarManager.getInstance().FIGHT_NUM = 1;
					WarManager.getInstance().CAMP_NUM = 4;// 军营数据
					WarManager.getInstance().IS_REFRESH = false;
					break;
				}
				modelMap.put(
						"msg",
						"start ok "
								+ TimeUtils.chDate(System
										.currentTimeMillis()));
				if(Byte.parseByte(fightType) < 3){
					WarManager.getInstance().startWar(isAuto,all);
				}else if(Byte.parseByte(fightType) ==3){
					//县长
					Nation.startFight();//自动开启县长
				}
				
				//if (type == 0) {
					
			//	}
			} else {
				modelMap.put("msg", "争夺战类型："
						+ getType() + "开启，请先关闭在开启");
			}
		} else {
			modelMap.put("msg", "争夺战类型错误");
		}
		modelMap.put("fightType", fightType);
		return modelMap;
	}

	public ModelMap _stopWar(final HttpServletRequest request,
			final ModelMap modelMap) {
		String fightType = request.getParameter("fightType");
		if (fightType != null && !"".equals(fightType)
				&& NumberUtil.isNumeric(fightType)) {
			if (Byte.parseByte(fightType) == -1
					|| Byte.parseByte(fightType) == WarManager.getInstance().FIGHT_TYPE) {
				WarManager.getInstance().calcResults();
//				try {
////					Thread.sleep(20*1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				if(Byte.parseByte(fightType) < 3){
					WarManager.getInstance().stopWar(true);
				}else if(Byte.parseByte(fightType) ==3){
					//县长
					Nation.closeFight();//自动开启县长
				}
				
				modelMap.put("msg","stop ok "+ TimeUtils.chDate(TimeUtils.nowLong()));
			} else {
				modelMap.put("msg", "争夺战类型："
						+ getType() + "开启，请选择对应类型关闭");
			}
		} else {
			modelMap.put("msg", "争夺战类型错误");
		}
		modelMap.put("fightType", fightType);
		return modelMap;
	}

	public String getType() {
		switch (WarManager.getInstance().FIGHT_TYPE) {
		case 0:
			return "市争夺战";
		case 1:
			return "州争夺战";
		case 2:
			return "国争夺战";
		}
		return "类型错误";
	}

	@Override
	public String doSelectCondition(HttpServletRequest request) {
		return null;
	}

	@Override
	public int _getCountPage(int count) {
		return 0;
	}

}
