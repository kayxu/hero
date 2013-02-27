package com.joymeng.web.service.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.joymeng.core.utils.NumberUtil;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.Item;
import com.joymeng.game.domain.item.equipment.BasePrototype;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.world.World;
import com.joymeng.web.entity.EquimentCell;
import com.joymeng.web.entity.PropsCell;
import com.joymeng.web.service.AbstractBusiness;
public class BizPlayer extends AbstractBusiness {
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static PropsManager propMgr = PropsManager.getInstance();
	public static BizPlayer instance;
	public static BizPlayer getInstance() {
		if (instance == null) {
			instance = new BizPlayer();
		}
		return instance;
	}
	/**
	 * 用户查询条件
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BizPlayer.class);

	/**
	 * 根据条件查询 用户列表
	 * 
	 * @param request
	 * @return
	 */
	public int _getPlayerListByCondition(final HttpServletRequest request,
			final ModelMap modelMap) {
		String condition = doSelectCondition(request);
		int pages = _getCurrentPage(request);// 页码
		int t = gameDao.getPlayersCountByGMCondition(condition);// 总条数
		List<RoleData> list = gameDao.getAllRole(condition, pages);
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				byte statue = isOnLine((int) list.get(i).getUserid());
				list.get(i).setAttriPolitics(statue);
			}
			modelMap.put("userList", list);
			return _getCountPage(t);
		}
		return 0;
	}

	/**
	 * 根据ID查询 用户列表
	 * 
	 * @param request
	 * @return
	 */
	public ModelMap _getPlayerById(final HttpServletRequest request,
			final ModelMap modelMap) {
		String id = request.getParameter("id");
		String pages = request.getParameter("pages");
		if (id != null && !"".equals(id) && NumberUtil.isNumeric(id)) {
			int ids = Integer.parseInt(id);
			PlayerCharacter pc = World.getInstance().getPlayer(ids);
			if (pc != null) {
				byte statue = isOnLine(pc.getId());
				pc.getData().setAttriPolitics(statue);
				// _getDetailItem(pc.getData().getPlayerCells(), modelMap);
				modelMap.put("user", pc.getData());
			}
		}
		return modelMap;
	}

	/**
	 * 根据ID查询 用户列表
	 * 
	 * @param request
	 * @return
	 */
	public ModelMap _getPlayerByIdItem(final HttpServletRequest request,
			final ModelMap modelMap) {
		String id = request.getParameter("id");
		getSelect(modelMap);
		// String pages = request.getParameter("pages");
		if (id != null && !"".equals(id) && NumberUtil.isNumeric(id)) {
			int ids = Integer.parseInt(id);
			PlayerCharacter pc = World.getInstance().getPlayer(ids);
			if (pc != null) {
				byte statue = isOnLine(pc.getId());
				pc.getData().setAttriPolitics(statue);
				_getDetailItem(pc.getData().getPlayerCells(), modelMap);
				modelMap.put("user", pc.getData());
			}
		}
		return modelMap;
	}

	public ModelMap _savePlayer(final RoleData data, final ModelMap modelMap) {
		if (data == null) {
			return modelMap;
		}
		PlayerCharacter pc = World.getInstance()
				.getPlayer((int) data.getUserid());
		if (_savePlayerName(pc, data.getName())) {// 可以保存
			// 保存进数据库
			// pc.getData().setGameMoney(data.getGameMoney());
			pc.setData(data);
			pc.saveData();
			modelMap.put("user", data);
			return modelMap;
		} else {
			modelMap.put("user", pc.getData());
			return modelMap;
		}

	}

	/**
	 * 修改 name
	 * 
	 * @param pc
	 * @param name
	 * @return
	 */

	public boolean _savePlayerName(final PlayerCharacter pc, final String name) {
		if (pc != null) {
			RoleData data = pc.motifyName(name);
			if (name.equals(data.getName())) {
				return true;
			}
		}
		return false;
	}

	public List<Cell> _getDetailItem(String items, final ModelMap modelMap) {
		List<Cell> cellDatas = new ArrayList<Cell>();
		List<PropsCell> propsDatas = new ArrayList<PropsCell>();
		List<EquimentCell> equiDatas = new ArrayList<EquimentCell>();
		String logString = items;
		if (logString == null || "".equals(logString)) {
			cellDatas = new ArrayList<Cell>();
			logger.info("用户背包使用格子数：" + cellDatas.size());
			logger.info("载入用户背包数据完成<<<<<<<<<<<<<<<<<<<");
		}else{
			cellDatas = createFromStrings(logString);
			if (cellDatas != null && cellDatas.size() > 0) {
				for (Cell cell : cellDatas) {
					if (cell.getItem().getId() != 0 && cell.getItemCount() != 0) {
						if (cell.getItem().isProp()) {
							PropsPrototype props = propMgr.getProps(cell.getItem()
									.getId());
							if (props != null) {
								PropsCell pcell = new PropsCell();
								pcell.setPrototype(props);
								pcell.setNum(cell.getItemCount());
								propsDatas.add(pcell);
							}
						} else {
							if(cell.getItem() instanceof Equipment){
								if(((Equipment)cell.getItem()).getHeroId() ==0) {
									EquimentCell ecell = new EquimentCell();
									ecell.setPrototype((Equipment)cell.getItem());
									ecell.setNum(cell.getItemCount());
									equiDatas.add(ecell);
								}
							}
						}
					}
				}
			}
		}
		
		modelMap.put("ecells", equiDatas);
		modelMap.put("pcells", propsDatas);
		return cellDatas;
	}

	public List<Cell> createFromStrings(String logString) {
		List<Cell> cells = new ArrayList<Cell>();
		if (logString.trim().length() == 0) {
			return null;
		}
		String[] split = logString.split(";");
		for (String PlayerItemString : split) {
			Cell cell = createFromString(PlayerItemString);
			if (cell != null && cell.getItemCount() != 0) {
				cells.add(cell);
			}
		}
		return cells;
	}

	/**
	 * 返回对象
	 * 
	 * @param logString
	 * @return
	 */
	public Cell createFromString(String logString) {
		int idx = 0;
		if (logString.trim().length() == 0) {
			return null;
		}
		String[] split = logString.split(",");
		if (split.length < 2) { // 物品字符串至少包括 classname 和 PlayerItemId
			return null;
		}
		String itemClassType = split[idx++];
		short itemType = Short.parseShort(itemClassType);
		if (itemType == Item.ITEM_EQUIPMENT) {
			String id = split[idx++];
			String count = split[idx++];
			String heroId = split[idx++];
			String effId = split[idx++];
			String effTime = split[idx++];
			Equipment e = new Equipment(equipMgr.getEquipment(Integer
					.parseInt(id)));
			e.setHeroId(Integer.parseInt(heroId));
			e.setEffectId(Integer.parseInt(effId));
			e.setEffectTime(Long.parseLong(effTime));
			Cell cell = new Cell();
			cell.setItem(e);
			cell.setItemCount(Integer.parseInt(count));
			return cell;
		} else if (itemType == Item.ITEM_PROPS) {
			String id = split[idx++];
			String count = split[idx++];
			Props p = new Props(propMgr.getProps(Integer.parseInt(id)));
			Cell cell = new Cell();
			cell.setItem(p);
			cell.setItemCount(Integer.parseInt(count));
			return cell;
		}
		return null;
	}

	/**
	 * 返回 获取总页数
	 * 
	 * @param count
	 * @return
	 */
	@Override
	public int _getCountPage(final int count) {
		int countPage = count / gameDao.SELECT_PALYER_NUM;
		if (count % gameDao.SELECT_PALYER_NUM != 0) {
			countPage++;
		}
		if (countPage <= 0) {
			countPage = 1;
		}
		return countPage;
	}

	/**
	 * 获取当前页码
	 * 
	 * @param request
	 * @return
	 */
	public int _getCurrentPage(HttpServletRequest request) {
		if (request.getParameter("pages") != null
				|| "null".equalsIgnoreCase(request.getParameter("pages"))) {
			return Integer.parseInt(request.getParameter("pages"));// 分页页码变量
		}
		return 0;
	}

	public byte isOnLine(int id) {
		ConcurrentHashMap<Integer, PlayerCharacter> playerList = gameWorld
				.getWorldRoleMap();
		if (playerList.containsKey(id)) {
			return GameConst.STATE_ONLINE;
		}
		return GameConst.STATE_OFFLINE;
	}

	@Override
	public String doSelectCondition(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		sb.append(" and ");
		String types = request.getParameter("types");// 类型
		String numerical = request.getParameter("numerical");// 数值
		String from =  request.getParameter("from");// 数值
		String to =request.getParameter("to");// 数值
		if (numerical == null || "".equals(numerical)) {
			numerical = "1";
		}
		//类型
		sb.append(SelectCondition.getSelectValue(types));
		if("3".equals(types) || "4".equals(types)){
			sb.append(" like '%");
			sb.append(numerical);
			sb.append("%'");
		}else if("11".equals(types) || "12".equals(types)){
			
			sb.append(" >= '").append(from).append("'");
//			sb.append(from).append("'");
			sb.append(" and ");
			sb.append(SelectCondition.getSelectValue(types));
			sb.append(" < '").append(to).append("'");
			//sb.append(to).append("'");
			
		}else{
			String symbol = request.getParameter("symbol");// 符号
			sb.append(SelectCondition.getSelectSymbol(symbol));
			sb.append(numerical);
		}
		sb.append(" order by userid ");
		// sb.append(" limit "+begin +" , "+GameWorldDAO.SELECT_PALYER_NUM);
		logger.debug("查询条件：" + sb.toString());
		return sb.toString();
	}
	
	public void getSelect(final ModelMap modelMap){
		if(propMgr.propsDatas != null){
			modelMap.put("propselect", propMgr.propsDatas.values());
		}
		if(equipMgr.baseDatas != null){
			modelMap.put("equselect", equipMgr.baseDatas.values());
		}
	}
	
	public void _savePlayerCell(HttpServletRequest request,final ModelMap modelMap,PlayerCharacter pc){
		String[] pids = request.getParameterValues("pid");//道具id
		String[] pCounts = request.getParameterValues("pcount");//道具数量
		String[] eids = request.getParameterValues("eid");//装备id
		String[] esl = request.getParameterValues("esl");//装备强化等级
		String[] eqc = request.getParameterValues("eqc");//装备品质
		String[] epl = request.getParameterValues("epl");//装备等级
		pc.getPlayerStorageAgent().clearAll();
		if(pids != null && pids.length > 0){
			for(int i = 0 ; i < pids.length;i++){
				PropsPrototype pp =propMgr.propsDatas.get(Integer.parseInt(pids[i]));
				if(pp != null ){//加入背包数据
					if(pc.getPlayerStorageAgent().isFull()){
						logger.info("用户："+pc.getId()+"|道具id:"+pids[i]+"添加失败，背包已满！");
						continue;
					}
					pc.getPlayerStorageAgent().addPropsCell(Integer.parseInt(pids[i]), Integer.parseInt(pCounts[i]));
				}
			}
		}
		if(eids != null && eids.length > 0){
			for(int i = 0 ; i < eids.length;i++){
				BasePrototype bp = EquipmentManager.getInstance().baseDatas.get(Integer.parseInt(eids[i]));
				if(bp != null ){//加入背包数据
					if(pc.getPlayerStorageAgent().isFull()){
						logger.info("用户："+pc.getId()+"|武器id:"+eids[i]+"添加失败，背包已满！");
						continue;
					}
					int str = 0;//强化等级
					int qur = 0;//品质
					if(Integer.parseInt(esl[i]) > 10){
						str = 10;
					}else if(Integer.parseInt(esl[i]) < 0){
						str = 0;
					}else{
						str = Integer.parseInt(esl[i]);
					}
					
					if(Integer.parseInt(eqc[i]) > 5){
						str = 5;
					}else if(Integer.parseInt(eqc[i]) < 1){
						str = 1;
					}else{
						str = Integer.parseInt(eqc[i]);
					}
					int id = EquipmentManager.getEquiId(Integer.parseInt(eids[i]), Integer.parseInt(eqc[i]), Integer.parseInt(esl[i]));//装备id
					pc.getPlayerStorageAgent().addEquipment(id, 1, 0);
				}
			}
		}
		
	}

}
