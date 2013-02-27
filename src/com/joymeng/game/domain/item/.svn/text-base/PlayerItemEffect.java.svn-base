package com.joymeng.game.domain.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.Instances;
import com.joymeng.game.db.dao.PropsDAO;
import com.joymeng.game.domain.box.BoxManager;
import com.joymeng.game.domain.building.PlayerTavernManager;
import com.joymeng.game.domain.hero.HeroManager;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.shop.Goods;
import com.joymeng.game.domain.shop.GoodsManager;
import com.joymeng.game.domain.world.TipUtil;

//import com.joymeng.game.domain.item.props.PropsEffect;

/**
 * 用户道具背包
 * 
 * @author xufangliang 1.1
 */
public class PlayerItemEffect implements Instances{
	// 日志
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(PlayerItemEffect.class);
	static PropsManager propMgr = PropsManager.getInstance();
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BoxManager boxMgr = BoxManager.getInstance();
	private PlayerCharacter player;// 用户
	private PlayerStorageAgent playerStorageAgent;
	public static GoodsManager grMgr = GoodsManager.getInstance();
	
	public Map<Byte,PropsDelay> delayDatas = new HashMap<Byte,PropsDelay>();
	//加载用户延时道具
	public void loadDelay(){
		RespModuleSet rms = new RespModuleSet(ProcotolType.ITEMS_RESP);
		delayDatas.clear();//map清空
//		logger.info("***************加载用户延时道具");
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate().queryForList(PropsDAO.SQL_SELECT_DELAY,(int)player.getData().getUserid());
		for (Map<String, Object> map : lst) {
			PropsDelay p = gameDao.getPropsDAO().loadObject(map);
			if(p != null){
				if(TimeUtils.nowLong()/1000 >= p.getEndTime()){
//					gameDao.getPropsDAO().deletePropsDelay(p.getId());
					if(p.getPropsType() == ItemConst.BUILD_QUEUE_TYPE){
						player.getPlayerBuilgingManager().setDelay(false);
					}
					deleteDelays(p);
				}else{
					delayDatas.put(p.getPropsType(), p);
					if(p.getPropsType() == ItemConst.BUILD_QUEUE_TYPE){
						player.getPlayerBuilgingManager().setDelay(true);
					}
					rms.addModule(p);
				}
			}
		}
		AndroidMessageSender.sendMessage(rms, player);
//		logger.info("***************加载用户延时道具:"+delayDatas.size());
	}
	//保存用户延时道具
	public void saveDelay(PropsDelay p,int id){
		if(p.getEndTime() > TimeUtils.nowLong()/1000){
			gameDao.getPropsDAO().savePropsDelay(p,id);
		}
	}
	//删除延时道具
	public void deleteDelays(PropsDelay delay){
//		logger.info("清除效果:"+delay.getPropsType());
//			PropsDelay delay  = delayDatas.get(type);
		if(delay != null){
			if(TimeUtils.nowLong()/1000 >= delay.getEndTime()){
				gameDao.getPropsDAO().deletePropsDelay(delay.getId());
				if(delay.getPropsType() == ItemConst.BUILD_QUEUE_TYPE){
//					logger.info("清除建筑队列效果:"+delay.getPropsType()+"|成功");
					player.getPlayerBuilgingManager().setDelay(false);
				}
//				logger.info("清除效果:"+delay.getPropsType()+"|成功");
			}
			logger.info("清除效果:"+delay.getPropsType()+"|失败");
		}else{
			logger.info("清除效果:"+delay.getPropsType()+"|失败");
		}
	}
	//删除延时道具
	public byte deleteDelay(byte type){
//		logger.info("清除效果:"+type);
		PropsDelay delay  = delayDatas.get(type);
		if(delay != null){
			System.out.println(TimeUtils.nowLong()/1000);
			if(TimeUtils.nowLong()/1000 >= delay.getEndTime()){
				gameDao.getPropsDAO().deletePropsDelay(delay.getId());
				delayDatas.remove(type);
				if(delay.getPropsType() == ItemConst.BUILD_QUEUE_TYPE){
//					logger.info("清除建筑队列效果:"+type+"|成功");
					player.getPlayerBuilgingManager().setDelay(false);
				}
//				logger.info("清除效果:"+type+"|成功");
				return type;
			}
			logger.info("清除效果:"+type+"|失败");
			return 0;
		}else{
			logger.info("清除效果:"+type+"|失败");
			return 0;
		}
	}
	//增加个对象
	public PropsDelay addDelay(PropsDelay p){
		PropsDelay delay  = delayDatas.get(p.getPropsType());
		if(delay != null){//原来存在
			saveDelay(p,delay.getId());
			delayDatas.put(p.getPropsType(), p);
			if(p.getPropsType() == ItemConst.BUILD_QUEUE_TYPE){
				player.getPlayerBuilgingManager().setDelay(true);
			}
			return p;
		}else{
			p = gameDao.getPropsDAO().addDelay(p);
			delayDatas.put(p.getPropsType(), p);
			return p;
		}
	}
	//得到 生效的delay道具
	public PropsDelay getDelay(byte key){
		PropsDelay delay =  delayDatas.get(key);
		int now = (int)(TimeUtils.nowLong()/1000);
		if(delay !=null){
			if(delay.getEndTime() >= now){
//				logger.info("delay == endtime = "+delay.getEndTime()+"|now == "+(now) + "效果存在");
				return delay;
			}else{
//				logger.info("delay == endtime = "+delay.getEndTime()+"|now == "+(now) + "删除效果");
				deleteDelays(delay);//超时删除效果
				return null;
			}
		}else{
			return null;
		}
	}
	public void activation(PlayerCharacter user, PlayerStorageAgent p) {// 激活本类
//		logger.info("激活用户道具背包数据<<<<<<<<<<<<<<<<<<<");
		player = user;
		playerStorageAgent = p;
		loadDelay();
//		logger.info("用户道具背包激活成功<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 是否是书
	 * 
	 * @param itemId
	 * @return
	 */
	public boolean isBook(int itemId) {
		if (player == null)
			return false;
		else {
			Props p = player.getProps(itemId);
			if (p != null && p.getPrototype().getPropsType() == 22) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 显示技能id
	 * 
	 * @param itemId
	 * @return
	 */
	public int getBookSkill(int itemId) {
		if (player == null)
			return 0;
		else {
			Props p = player.getProps(itemId);
			if (p != null && p.getPrototype().getPropsType() == 22) {
				return Integer.parseInt(p.getPrototype().getProperty4());
			}
			return 0;
		}
	}

	/**
	 * 显示技能id
	 * 
	 * @param skillId
	 * @return
	 */
	public Props getBookFromSkill(int skillId) {
		if (player == null || playerStorageAgent == null)
			return null;
		else {
			for (Cell cell : playerStorageAgent.cellDatas) {
				if (cell.getItem().isProp()) {
					Props p = (Props) cell.getItem();
					if (p.getPrototype().getPropsType() == 22
							&& skillId == Integer.parseInt(p.getPrototype()
									.getProperty4())) {
						return p;
					}
				}
			}
			return null;
		}
	}

	/**
	 * 显示技能id
	 * 
	 * @param skillId
	 * @return
	 */
	public Props getBookFromAllSkill(int skillId) {
		if (player == null || propMgr.propsDatas == null
				|| playerStorageAgent == null)
			return null;
		else {
			for (PropsPrototype pp : propMgr.propsDatas.values()) {
				if (pp.getPropsType() == 22
						&& skillId == Integer.parseInt(pp.getProperty4())) {
					return new Props(pp);
				}
			}
			return null;
		}
	}

	public Props getUseProps(int id, boolean isDiamond) {
		TipUtil tip = userProps(id, isDiamond);
		if (tip.isResult()) {
			List<ClientModuleBase> lst = tip.getLst();
			if (lst != null && lst.size() > 0) {
				if (lst.get(0) instanceof Cell) {
					Cell cell = (Cell) lst.get(0);
					if (cell != null) {
						return (Props) cell.getItem();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 使用道具
	 * 
	 * @return
	 */
	public TipUtil userProps(int id, boolean isDiamond) {
		TipUtil tip = new TipUtil(ProcotolType.ITEMS_RESP);
		tip.setFailTip("使用失败!");
//		logger.info("使用道具:" + id + "|是否需要钻石:" + isDiamond);
		if (playerStorageAgent.isDelete(id, 1, Item.ITEM_PROPS).getStatus()) {// 是否有足够的道具
			Props props = playerStorageAgent.getUserProps(id);
			Cell cell = playerStorageAgent.dellCell(id, 1);
			if(null != cell){
				props = (Props) cell.getItem();
//				logger.info("useTime=" + TimeUtils.now().format(TimeUtils.FORMAT1) + " propsId=" + props.getPrototype().getId() 
//						+ " propsName=" + props.getPrototype().getName() + " useNumber=1"+ " uid=" + player.getId() 
//						+ " uname=" + player.getData().getName());
				GameLog.logSystemEvent(LogEvent.USE_PROPS, String.valueOf(props.getPrototype().getId()),props.getPrototype().getName(),String.valueOf(1)
						,String.valueOf(player.getId()));
				tip.setFailTip("没有道具!");
			}
			
			/** ----------rms */
			// RespModuleSet rms = new RespModuleSet(ProcotolType.ITEMS_RESP);
			// rms.addModule(cell);
			// AndroidMessageSender.sendMessage(rms, player);
			List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
			lst.add(cell);
			tip.setLst(lst);
			/** ----------rms */
			return tip.setSuccTip("");
		} else if (isDiamond) {// 扣除钻石
			Goods good = grMgr.getGoodProps(id);
			int price = 0;
			if (good != null) {
				price = good.getBuyPrice();
			}
			if (player.getData().getJoyMoney() >= price) {
//				logger.info("扣除钻石:" + price + "|实际拥有:"
//						+ player.getData().getJoyMoney());
				player.saveResources(GameConfig.JOY_MONEY, price * -1);
				/** ----------rms */
				List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
				lst.add(player.getData());
				tip.setLst(lst);
				// RespModuleSet rms = new
				// RespModuleSet(ProcotolType.ITEMS_RESP);
				// rms.addModule(player.getData());
				// AndroidMessageSender.sendMessage(rms, player);
				/** ----------rms */
				return tip.setSuccTip("");
			} else {
//				logger.info("扣除钻石:" + price + "|实际拥有:"
//						+ player.getData().getJoyMoney());
				String msg = I18nGreeting.getInstance().getMessage("dimond.not.enough", null);
				return tip.setFailTip(msg);
			}
		}
		return tip;
	}

	/**
	 * 使用背包道具
	 * 
	 * @param id
	 * @return
	 */
	public TipUtil userStorageProps(int id) {
		TipUtil tip = new TipUtil(ProcotolType.ITEMS_RESP);
		tip.setFailTip("使用失败!");
//		logger.info("使用背包道具:" + id);
		if (playerStorageAgent.isDelete(id, 1, Item.ITEM_PROPS).getStatus()) {
			// 宝箱
		} else {
//			logger.info("使用背包道具:" + id + "|道具数量不足");
			tip.setFailTip("没有道具!");
		}
		return tip;
	}
	/**
	 * 使用道具
	 * @param id
	 * @param propsId
	 * @param isDiamond
	 * @return
	 */
	public TipUtil userPropsEffic(int id, int propsId,int id2, boolean isDiamond) {
		TipUtil tip = new TipUtil(ProcotolType.ITEMS_RESP);
		tip.setFailTip("使用失败!");
		if (playerStorageAgent.isDelete(propsId, 1, Item.ITEM_PROPS).getStatus()) {
			Props props = playerStorageAgent.getUserProps(propsId);
			//建筑使用加速道具,或增产道具
			if(props != null){
				//建筑道具
				if (props.getPrototype().getPropsType() == ItemConst.LUBAN_TYPE
								|| props.getPrototype().getPropsType() == ItemConst.ADD_GOLD_TYPE || props
								.getPrototype().getPropsType() == ItemConst.ADD_HORSE_TYPE ||  props
								.getPrototype().getPropsType() == ItemConst.ADD_WOOD_TYPE ||  props
								.getPrototype().getPropsType() == ItemConst.ADD_IRON_TYPE) {
					tip = player.getPlayerBuilgingManager().userluBan(id,props);
					tip.setProcotolType(props.getPrototype().getPropsType());
					QuestUtils.checkFinish(player, QuestUtils.TYPE50, true);
					return tip;
				}else if(props.getPrototype().getPropsType() == ItemConst.HERO_EXP_TYPE || props.getPrototype().getPropsType() ==ItemConst.TRAINING_HERO_TYPE){
					//英雄训练加速道具
					tip = player.getPlayerHeroManager().userProps(id, props);
					tip.setProcotolType(props.getPrototype().getPropsType());
					return tip;
				}else if(props.getPrototype().getPropsType() == ItemConst.TRAINING_SOLIDER_TYPE){
					//士兵加速道具
					tip = player.getPlayerBuilgingManager().changeTrainingTime(id, props);
					tip.setProcotolType(props.getPrototype().getPropsType());
					QuestUtils.checkFinish(player, QuestUtils.TYPE33, true);
					QuestUtils.checkFinish(player, QuestUtils.TYPE51, true);
					return tip;
				}else if(props.getPrototype().getPropsType() == ItemConst.SPY_ON_TYPE){
					//侦查道具
					tip = player.getPlayerStorageAgent().spyOn(id, id2, props);
					tip.setProcotolType(props.getPrototype().getPropsType());
					return tip;
				}else if(props.getPrototype().getPropsType() == ItemConst.REFRESH_HERO_TYPE || props.getPrototype().getPropsType() == ItemConst.REFRESH_FHERO_TYPE){
					//刷将道具
//					logger.info(">>>使用刷将符,开始");
					boolean flag = HeroManager.getInstance().refresh(id, player);
//					logger.info(">>>使用刷将符,结束");
					if(props.getPrototype().getPropsType() == ItemConst.REFRESH_HERO_TYPE){
						QuestUtils.checkFinish(player, QuestUtils.TYPE47, true);
					}else{
						QuestUtils.checkFinish(player, QuestUtils.TYPE48, true);
					}
					if(flag){
						tip = userProps(props.getId(), false);
						RespModuleSet rms = new RespModuleSet(ProcotolType.ITEMS_RESP);// 模块消息
						rms.addModuleBase(tip.getLst());
						AndroidMessageSender.sendMessage(rms, player);
					}
					return tip;
				}else if(props.getPrototype().getPropsType() == ItemConst.HERO_EXP_CARD_TYPE){//武将经验卡
					PropsDelay delay  = delayDatas.get(ItemConst.HERO_EXP_CARD_TYPE);
					tip = userProps(props.getId(), false);
					long end = 0;
					if(delay == null){
						PropsDelay delays = new PropsDelay();
						delays.setEndTime((int) (TimeUtils.nowLong() / 1000)
								+ Integer.parseInt(props.getPrototype()
										.getProperty3()));
						delays.setPropsId(props.getId());
						delays.setPropsType(props.getPrototype().getPropsType());
						delays.setUserId((int)player.getData().getUserid());
						delays.setAdditionCount(Integer.parseInt(props.getPrototype().getProperty2()));
						PropsDelay pd = addDelay(delays);
						RespModuleSet rms = new RespModuleSet(ProcotolType.ITEMS_RESP);// 模块消息
						rms.addModuleBase(tip.getLst());
						rms.addModule(pd);
						AndroidMessageSender.sendMessage(rms, player);
						end = (long)(delays.getEndTime())*1000;
						
					}else{
						delay.setEndTime(delay.getEndTime()
								+ Integer.parseInt(props.getPrototype()
										.getProperty3()));
						saveDelay(delay, player.getId());
						RespModuleSet rms = new RespModuleSet(ProcotolType.ITEMS_RESP);// 模块消息
						rms.addModuleBase(tip.getLst());
						rms.addModule(delay);
						AndroidMessageSender.sendMessage(rms, player);
						end = (long)(delay.getEndTime())*1000;
					}
					String str = "使用成功，有效时间至" + TimeUtils.chDate(end);
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						str = "OK. Item effect is valid to"+TimeUtils.enDate(end);
					}
					tip.setSuccTip(str);
					return tip;
				}else if(props.getPrototype().getPropsType() == ItemConst.BUILD_QUEUE_TYPE){//建筑队列
//					PropsDelay delay  = delayDatas.get(ItemConst.BUILD_QUEUE_TYPE);
//					if(delay == null || (delay != null && delay.getEndTime() <= TimeUtils.nowLong()/1000)){
						tip = player.getPlayerBuilgingManager().userluBan(id,props);
						tip.setProcotolType(props.getPrototype().getPropsType());
						return tip;
//					}else{
//						tip.setFailTip("fail");
//						return tip;
//					}
				}else if(props.getPrototype().getPropsType() == ItemConst.HERO_EXPAND){
					PlayerTavernManager taven = player.getPlayerBuilgingManager().getPlayerTavern();
					if(taven == null || taven.getItemTime() > 13){
						String title = I18nGreeting.getInstance().getMessage("hero.expand",new Object[] {});
						tip.setFailTip(title);
						return tip;
					}else{
						tip = userProps(props.getId(), false);
						if(!tip.isResult()){
							return tip;
						}
						//下发道具
						RespModuleSet rms = new RespModuleSet(ProcotolType.ITEMS_RESP);
						 rms.addModuleBase(tip.getLst());
						 AndroidMessageSender.sendMessage(rms, player);
						//扩将
						if(taven.addItemTime()){
							tip.setSuccTip("");
							return tip;
						}else{
							tip.setFailTip("fail");
							return tip;
						}
					}
				}else{
					tip.setFailTip("fail");
					return tip;
				}
			}else{
//				logger.info("用户没有道具:" + id);
				tip.setProcotolType((byte)0);
				return tip.setFailTip("用户没有道具");
			}
		} else {
//			logger.info("用户没有道具:" + id);
			tip.setProcotolType((byte)0);
			return tip.setFailTip("用户没有道具");
		}
		//return tip;
	}
}
