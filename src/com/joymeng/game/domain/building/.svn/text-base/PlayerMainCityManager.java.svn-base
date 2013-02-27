package com.joymeng.game.domain.building;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.fight.FightLog;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.PlayerNobility;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;

public class PlayerMainCityManager extends ClientModuleBase {// 主城操作类

	private static final int SEND_TYPE = ProcotolType.BATTLE_FIELD_RESP;// 发送类型
	private static final long serialVersionUID = 1L;
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BuildingManager bldMgr = BuildingManager.getInstance();
	PlayerCharacter owner;
	PlayerBuildingManager pm;
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerMainCityManager.class);

	public void activation(PlayerCharacter p, PlayerBuildingManager pm) {// 激活主城操作类
		if (p != null) {
			owner = p;
			this.pm = pm;
//			logger.info("用户：" + owner.getData().getUserid() + " 主城："
//					+ getMainCity().getId() + " 控制类加载成功");
		}
	}

	/**
	 * 获取主城
	 * 
	 * @return
	 */
	public PlayerBuilding getMainCity() {
		return pm.getPlayersBuild(GameConst.MAINCITY_ID, (int) owner.getData()
				.getUserid());
	}

	/**
	 * 获取主城等级
	 * 
	 * @return
	 */
	public int getLevel() {
		if (getMainCity() == null)
			return 0;
		return getMainCity().getBuildLevel();
	}

	/**
	 * 重新设置 士兵
	 * 
	 * @param soMsg
	 */
	public void backSolider(String soMsg) {
		getMainCity().setSoldierMsg(soMsg);
	}

	/**
	 * 主城升级
	 */
	public int[] levelUp() {
		StringBuffer sb = new StringBuffer();
		// 检验升级条件
		BuildingUpGrade bu = pm.checkCondition(getMainCity());
		if (getMainCity() == null || bu == null) {
			GameUtils.sendTip(new TipMessage("升级失败",
					ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
					owner.getUserInfo(),GameUtils.FLUTTER);
			return new int[] { 0, 0, 0 };
		}
//		logger.info("~~~player:" + owner.getId() + "~~~~getMainCity():"
//				+ getMainCity());
		TipUtil tip = checkUp();
		if (!tip.isResult()) {
			GameUtils.sendTip(new TipMessage(tip.getResultMsg(),
					ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
					owner.getUserInfo(),GameUtils.FLUTTER);
			return new int[] { 0, 0, 0 };
		}
		if (owner.saveResources(GameConfig.GAME_MONEY, bu.getGold() * -1) == -1) {// 金币不足
			return new int[] { 0, 0, 0 };
		}
		// 更新数据库和缓存对应信息
		Long now = TimeUtils.nowLong();
		// 升级时间
		getMainCity().setConstructionTime(
				TimeUtils.addSecond(now, bu.getTime()));
		getMainCity().setBuildType(PlayerBuildingManager.LEVELUP_BUILD);
		// 产出类型
		getMainCity().setInCometype(bu.getType());
		getMainCity().setBuildLevel((short) (getMainCity().getBuildLevel()));
		sb.append("用户：" + owner.getId()).append(
				">>>升级产生经验：" + bu.getExp());
		getMainCity().setExp(bu.getExp());// 设置经验
		FightLog.info("升级：+++++++用户："+owner.getId()+"|建筑："+getMainCity().getBuildingID()+"|等级升级:"+getMainCity().getBuildLevel()+"| 设置经验："+bu.getExp());
		// 进入数据库
//		savePlayerBuilding(getMainCity());
		sb.append(">>>建筑产生经验：" + getMainCity().getExp());
		logger.info(sb.toString());
		// owner.saveResources(GameConfig.GAME_MONEY, bu.getGold() * -1);
		// player.setData(role);
//		owner.saveData();
		pm.setBuilding(false);
		return new int[] { 1, getMainCity().getId(), bu.getGold() * -1 };
	}

	/**
	 * 保存
	 * 
	 * @param playerbuilding
	 * @return
	 */
	public boolean savePlayerBuilding(PlayerBuilding playerbuilding) {
		pm.gameDao.getBuildDAO().savePlayerBuilding(playerbuilding);
		// 存入放入map
		pm.playerBuildDatas.put(playerbuilding.getId(), playerbuilding);
		return true;
	}

	/**
	 * 检验主城升级条件
	 */
	public TipUtil checkUp() {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		PlayerNobility pn = owner.getPlayerNobility();
//		logger.info("用户爵位:" + owner.getPromotedMG());
		if (pn == null) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				return tip.setFailTip("Error!");
			}else{
				return tip.setFailTip("爵位数据错误");
			}
		} else {
			short max = pn.getTownMaxLevel();
//			logger.info("主城等级:" + getMainCity().getBuildLevel());
//			logger.info("主城限制等级:" + max);
			if (getMainCity().getBuildLevel() >= max) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					return tip.setFailTip("Upgrade failed.Upgrade your Lord's Title first.");
				}else{
					return tip.setFailTip("超过爵位主城等级上限,无法升级");
				}
				
			} else {
				tip.setSuccTip("");
				return tip;
			}
		}
	}

	/**
	 * 被掠夺数量
	 * 
	 * @return
	 */
	public double plunderGold(int base) {
		if (getMainCity() == null) {
//			logger.info("用户：" + owner.getId() + "|主城或升级数据不存在");
			return 0;
		}
//		logger.info("用户：" + owner.getId() + "主城产出|<>"
//				+ GameConst.getAdditon(getMainCity().getBuildLevel()));
		return base * GameConst.getAdditon(getMainCity().getBuildLevel());
	}

	/**
	 * 是否可以 占领
	 * 
	 * @param playerBuildingId
	 * @return
	 */
	public boolean isOccupied() {
		// 主城ID
		if (getMainCity() == null)
			return false;
		if (getMainCity().getIsOccupy() == 1) {
			return true;
		}
		return false;
	}

	// 派兵
	public boolean dispatch(int heroId, String soMsg, long time, byte type) {
//		logger.info("修改状态 ---主城驻防将领id：" + heroId + " 派兵 --- 主城驻防士兵：" + soMsg);
		if (getMainCity() == null || getMainCity().getOccupyUserId() != 0
				|| getMainCity().getOfficerId() != 0)// 无法驻扎
		{
//			logger.info("主城无法驻扎！");
			return false;
		}
		boolean flag1 = owner.getPlayerBuilgingManager().dispatchSoldier(soMsg);// 派兵
		String memo = "驻防主城";
		if(I18nGreeting.LANLANGUAGE_TIPS ==1 ){
			memo = "Garrsion in Capitol";
		}
		boolean flag = owner.getPlayerHeroManager().motifyStatus(heroId, type,
				memo, soMsg, time);// 修改武将驻防状态
		if (!flag1) {
//			logger.info("派兵失败！");
			return false;
		} else if (!flag) {
//			logger.info("将领驻防失败！");
			return false;
		} else {
//			logger.info("将领驻防和派兵成功！");
			return true;
		}
	}

	/**
	 * 设置占领 详细信息
	 */
	public void occMaincity(int userid, PlayerHero ph, String soMsg) {
		getMainCity().setOccupyUserId(userid);// 占领玩家信息
		getMainCity().setSoldierMsg(soMsg);// 士兵信息
		getMainCity().setOfficerId(ph.getId());// 将领id
		getMainCity().setOfficerInfo(GameUtils.heroInfo(ph));// 将领明细
		getMainCity().setOfficerTime(
				TimeUtils.addSecond(
						TimeUtils.nowLong(), 5 * 60));// 占领时间
//		pm.savePlayerBuilding(getMainCity());// 保存
	}

	/**
	 * 设置清空 所有占领信息
	 */
	public void clearMaincity() {
		getMainCity().setOccupyUserId(0);// 占领玩家信息
		getMainCity().setSoldierMsg("");// 士兵信息
		getMainCity().setOfficerId(0);// 将领id
		getMainCity().setOfficerInfo("");// 将领明细
//		pm.savePlayerBuilding(getMainCity());// 保存
	}

	/**
	 * 驻扎主城
	 */
	public TipUtil presence(int heroId, String soMsg, FightEvent fightEvent1) {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);//
		StringBuffer sb = new StringBuffer("\n----------presence主城---------\n");
		sb.append("主城驻防将领id：" + heroId + " 主城驻防士兵：" + soMsg+"\n");
		synchronized (this) {
			if (!dispatch(heroId, soMsg,
					TimeUtils.nowLong() + 5 * 30 * 1000,
					GameConst.HEROSTATUS_ZHUFANG_MY_MAINCITY)) {
				sb.append("【dispatch】 失败 \n");
				tip.setFailTip("派兵派将失败");
				return tip;
			}
			PlayerHero ph = owner.getPlayerHeroManager().getHero(heroId);// 获取将领
			occMaincity(0, ph, soMsg);
			// ********* rms 下发
			PushSign.sendOne(getMainCity(), new PlayerCharacter[] { owner },
					ProcotolType.BUILDING_RESP);

//			logger.info("驻防成功！");
			getMainCity().setTip(
					new TipMessage("驻扎主城成功！", ProcotolType.BUILDING_RESP,
							GameConst.GAME_RESP_SUCCESS));
			tip.setSuccTip("驻防成功");
			if (fightEvent1 != null) {
				if (heroId > 0) {
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						fightEvent1.setMemo("You garrisoned your Capitol successfully.");
					}
					else{
						fightEvent1.setMemo("你驻扎了你的主城成功");
					}
					
				} else {
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						fightEvent1.setMemo("You garrisoned your Capitol failed.");
					}else{
						fightEvent1.setMemo("你驻扎了你的主城失败");
					}
					
				}
			}
			sb.append("驻防成功 \n");
			sb.append("---------------- \n");
			// 保存到 cache
			GameUtils.putToCache(owner);
			tip.setStr(sb.toString());
			return tip;
		}
		
	}

	/**
	 * 主城撤防
	 */
	public TipUtil withdrawal(String ssMsg, boolean istime) {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		if (getMainCity() == null
				|| (getMainCity().getOccupyUserId() != 0 && getMainCity()
						.getOccupyUserId() != owner.getId())) {
			tip.setFailTip("主城无法驻防！");
			return tip;
		}
		if (istime) {// 是否需要时间限制
			if (getMainCity().getOfficerTime().getTime() > TimeUtils.nowLong()) {
				String msg = I18nGreeting.getInstance().getMessage("quit.fail",
						null);
				tip.setFailTip(msg);
				return tip;
			}
		}
		int heroId = getMainCity().getOfficerId();
		String soMsg = getMainCity().getSoldierMsg();
		if (!"".equals(ssMsg))
			soMsg = ssMsg;
		synchronized (this) {
			if (pm.backHeroAndSoldier(heroId, soMsg)) {
//				logger.info("撤出将领：" + heroId + " 撤出士兵：" + soMsg);
				clearMaincity();
				// *****rms
				PushSign.sendOne(getMainCity(), new PlayerCharacter[] { owner },
						ProcotolType.BUILDING_RESP);
				String msg = I18nGreeting.getInstance().getMessage("quit.success",
						null);
				getMainCity().setTip(
						new TipMessage(msg, ProcotolType.BUILDING_RESP,
								GameConst.GAME_RESP_SUCCESS));
				tip.setSuccTip(msg);
				// 保存到 cache
				GameUtils.putToCache(owner);
				return tip;
			} else {
				PlayerHero playerhero = owner.getPlayerHeroManager().getHero(heroId);
				if(playerhero != null && playerhero.getStatus() == GameConst.HEROSTATUS_ZHUFANG_MY_MAINCITY){
					owner.getPlayerHeroManager().motifyStatus(heroId,
							GameConst.HEROSTATUS_IDEL, "", "", 0);
					tip.setSuccTip("");
				}else{
					tip.setFailTip("撤出将领 失败");
					getMainCity().setTip(
							new TipMessage("将领撤防或者收兵失败！", ProcotolType.BUILDING_RESP,
									GameConst.GAME_RESP_FAIL));
					logger.info("撤出将领：" + heroId + " 撤出士兵：" + soMsg);
					logger.info("【PlayerBuildingManager >>> backHeroAndSoldier】 失败");
				}
				
				return tip;
			}
		}
		
		
	}

	/**
	 * 主城被占领
	 * 
	 * @param userId
	 * @param heroId
	 * @param userInfo
	 */
	public TipUtil occupiedMainCity(boolean issucc, int userId, int ph,
			String soMsg, String ssMsg, PlayerBuilding aff,FightEvent fightEvent1,
			FightEvent fightEvent2) {
		StringBuffer sb = new StringBuffer("\n-------occupiedMainCity-------------\n");
		sb.append("占领者：" + userId + " 将领：" + ph + " 士兵" + soMsg +"|剩余士兵："+ssMsg+"\n");
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);//
		// 攻击方
		PlayerCharacter att = World.getInstance().getPlayer(userId);
		// 攻击方将领
		PlayerHero attph = att.getPlayerHeroManager().getHero(ph);
		// 防守
		PlayerCharacter def = owner;//自己占领
		if ((int) getMainCity().getOccupyUserId() != 0) {
			def = World.getInstance().getPlayer(
					(int) getMainCity().getOccupyUserId());
		}
		// 防守将领
		PlayerHero defph = att.getPlayerHeroManager().getHero(
				(int) getMainCity().getOfficerId());
		if (att == null || attph == null || def == null) {
			sb.append("攻击方或攻击方将领为空\n");
			tip.setSuccTip("攻击方或攻击方将领为空");
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			return tip;
		}
		if (!issucc) {// 驻防失败
			def.getPlayerBuilgingManager().getPma()
					.motify((int) owner.getData().getUserid(), ssMsg);// 修改玩家附属成消息
			getMainCity().setSoldierMsg(ssMsg);// 修改兵力
//			pm.savePlayerBuilding(getMainCity());
			// ***** rms
			PushSign.sendOne(getMainCity(), new PlayerCharacter[] { owner },
					ProcotolType.BUILDING_RESP);
			sb.append("占领者：" + userId + " 将领：" + ph + " 士兵" + soMsg
					+ "  占领失败\n");
			getMainCity().setTip(
					new TipMessage("攻占主城失败！", ProcotolType.BUILDING_RESP,
							GameConst.GAME_RESP_FAIL));
			if (fightEvent1 != null) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					fightEvent1.setMemo("You dispatched " + attph.getName() + " to occupy the capitol of "
							+ owner.getData().getName() + "  but failed.");
				}else{
					fightEvent1.setMemo("你派遣" + attph.getName() + "占领"
							+ owner.getData().getName() + "的主城时失败了");
				}
				
			}
			if (fightEvent2 != null) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					fightEvent2.setMemo("Your hero " + defph.getName() + "defended against "
							+ att.getData().getName() + " when garrisoning.");
				}else{
					fightEvent2.setMemo("你的武将" + defph.getName() + "在驻防时抵御了"
							+ att.getData().getName() + "的进攻");
				}
				
			}
			tip.setSuccTip("攻占主城失败！\n");
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			return tip;
		} else {// 驻防成功
			if (isOccupied()) {
				if (def.getId() == att.getId()) {
					getMainCity().setTip(
							new TipMessage("占领者：" + userId + " 已经占领无法重复占领",
									ProcotolType.BUILDING_RESP,
									GameConst.GAME_RESP_FAIL));
					sb.append("占领者：" + userId + " 已经占领无法再占领\n");
					tip.setFailTip("占领者：" + userId + " 已经占领无法再占领");
					sb.append("--------------------\n");
					tip.setStr(sb.toString());
					return tip;
				}
				if(aff == null){
					sb.append("aff is null");
					sb.append("--------------------\n");
					return tip.setFailTip("失败");
				}
				if (def.getId() != owner.getId()) {// 第三方占领
					tip = def
							.getPlayerBuilgingManager()
							.getPma()
							.withdrawal((byte) 1, getMainCity().getOfficerId(),
									ssMsg);//原驻防者撤退
					sb.append(tip.getStr()+"\n");
					if (!tip.isResult()) {
						sb.append("--------------------\n");
						tip.setStr(sb.toString());
						return tip.setFailTip(tip.getResultMsg());
					}
					getMainCity().setTip(
							new TipMessage("占领者：" + userId + " 将领：" + ph
									+ " 士兵" + soMsg + "  占领【" + def.getId()
									+ "驻扎主城】成功！", ProcotolType.BUILDING_RESP,
									GameConst.GAME_RESP_SUCCESS));
					sb.append("占领者：" + userId + " 将领：" + ph + " 士兵" + soMsg
							+ "  占领【" + def.getId() + "驻扎主城】成功！\n");
//					if (fightEvent1 != null) {
//						fightEvent1.setMemo("你派遣" + attph.getName() + "占领了"
//								+ owner.getData().getName() + "的主城");
//					}
//					if (fightEvent2 != null) {
//						fightEvent2.setMemo("你的武将在驻防时被"
//								+ att.getData().getName() + "击败了");
//					}
					// 保存到 cache
					GameUtils.putToCache(owner);
				} else {// 自己占领
					if (getMainCity().getOfficerId() != 0) {// 有将领自动占领
						tip = withdrawal(ssMsg, false);
						if (!tip.isResult()) {
							sb.append("【pmc >>> withdrawal】 失败\n");
							logger.info(sb.toString());
							sb.append("--------------------\n");
							tip.setStr(sb.toString());
							return tip.setFailTip("占领者撤退失败");
						}
						getMainCity().setTip(
								new TipMessage("占领成功！",
										ProcotolType.BUILDING_RESP,
										GameConst.GAME_RESP_SUCCESS));
					}
				}
				//攻击者驻防附属城
				boolean flag = att.getPlayerBuilgingManager().getPma().occAffCity(aff, soMsg, owner, attph);
				if(flag){
					//攻击者派兵
					String m1 = "驻守";
					String m2 = "主城";
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						m1 = "Garrsion in ";
						m2 = " Capitol";
					}
					sb.append(att.getPlayerBuilgingManager().getPma().dispatch(ph, m1
							+ owner.getData().getName() + m2, soMsg, TimeUtils.nowLong() + 5 * 30 * 1000));
				}else{
					sb.append("att.getPlayerBuilgingManager().getPma().occAffCity失败");
					sb.append("--------------------\n");
					return tip.setFailTip("失败");
				}
				if (fightEvent1 != null) {
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						fightEvent1.setMemo("You dispatched " + attph.getName() + " to occupy the capitol of "
								+ owner.getData().getName() + "");
					}else{
						fightEvent1.setMemo("你派遣" + attph.getName() + "占领了"
								+ owner.getData().getName() + "的主城");
					}
					
				}
				if (fightEvent2 != null) {
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						fightEvent2.setMemo("Your hero was defeated by "+ att.getData().getName() + "when garrisoned Capitol");
					}else{
						fightEvent2.setMemo("你的武将在驻防时被"
								+ att.getData().getName() + "击败了");
					}
					
				}
				// 主城被修改
				occMaincity(userId, attph, soMsg);
				// ******************* rms
				List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
				lst.add(getMainCity());
				lst.add(new SimplePlayerHero(attph,""));
				RespModuleSet rms2=new RespModuleSet(ProcotolType.BUILDING_RESP);
				List<PlayerBuilding> bLst = pm.getAllResource();
				for(PlayerBuilding pp : bLst){
					pp.setStealTime(TimeUtils.addSecond(TimeUtils.nowLong(), 10*60));//偷取时间
					lst.add(pp);
					rms2.addModule(pp);
				}
				PushSign.sendAll(lst, new PlayerCharacter[] {
					owner, att}, ProcotolType.BUILDING_RESP);
				getMainCity().setTip(
						new TipMessage("占领成功！", ProcotolType.BUILDING_RESP,
								GameConst.GAME_RESP_SUCCESS));
				// 保存到 cache
				GameUtils.putToCache(owner);
				sb.append("成功\n");
				sb.append("--------------------\n");
				tip.setStr(sb.toString());
				return tip.setSuccTip("");
			}
		}
		return tip;
	}

	/**
	 * 占领玩家主动撤防
	 * 
	 * @return
	 */
	public PlayerBuilding accord(int hh) {
			logger.info("占领玩家id：" + getMainCity().getOccupyUserId() + " 占领将领："
					+ getMainCity().getOfficerId() + " 占领士兵："
					+ getMainCity().getSoldierMsg() + "  主动撤防");
			if(getMainCity().getOfficerId() == hh){
				clearMaincity();
				// ******************* rms
				PushSign.sendOne(getMainCity(), new PlayerCharacter[] { owner },
						ProcotolType.BUILDING_RESP);
				logger.info("玩家主城收复  *** 未驻扎，成功");
				getMainCity().setTip(
						new TipMessage("占领成功！", ProcotolType.BUILDING_RESP,
								GameConst.GAME_RESP_SUCCESS));
				// 保存到 cache
				GameUtils.putToCache(owner);
			}
			return getMainCity();
	}

	/**
	 * 收复
	 */
	public TipUtil recover(boolean issucc, int heroId, String msg,
			String backMsg, FightEvent fightEvent1, FightEvent fightEvent2) {
		StringBuffer sb = new StringBuffer("\n-------recover-------------\n");
		sb.append("将领id：" + heroId + " 驻扎士兵：" + msg+"\n");
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		if (getMainCity() == null || getMainCity().getIsOccupy() == 0) {
			// getMainCity().setTip(new
			// TipMessage("主城不能被占领/驻扎！",ProcotolType.BUILDING_RESP,GameConst.GAME_RESP_FAIL));
			sb.append("主城不能被占领/驻扎 \n");
			sb.append("----------------");
			tip.setStr(sb.toString());
			tip.setFailTip("主城不能被占领/驻扎");
			return tip;
		} else if( getMainCity().getOccupyUserId() != 0 &&  getMainCity().getOccupyUserId() != owner.getId()){
			// 玩家主城修改
			PlayerCharacter def = World.getInstance().getPlayer(
					(int) getMainCity().getOccupyUserId());
			PlayerHero defph = def.getPlayerHeroManager().getHero(
					getMainCity().getOfficerId());
			PlayerCharacter att = owner;
			PlayerHero attph = att.getPlayerHeroManager().getHero(heroId);
			if (def == null || defph == null || attph == null) {
				// getMainCity().setTip(new
				// TipMessage("用户将领不存在！",ProcotolType.BUILDING_RESP,GameConst.GAME_RESP_FAIL));
				tip.setFailTip("用户将领不存在！");
				sb.append("用户将领不存在！\n");
				sb.append("----------------");
				tip.setStr(sb.toString());
				return tip;
			}
			if (!issucc) {
				// 收复失败
				getMainCity().setSoldierMsg(backMsg);
				owner.getPlayerBuilgingManager().dispatchSoldier(msg);
				if (fightEvent1 != null && fightEvent2 != null) {
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						fightEvent1.setMemo("You dispatched " + attph.getName() + " to recapture your Capitol but failed.");
						fightEvent2.setMemo("Your hero " + defph.getName() + " defended against "
								+ att.getData().getName() + " when garrisoning "
								+ attph.getName() + "'s capitol.");
					}else{
						fightEvent1.setMemo("你派遣" + attph.getName() + "收复自己的主城失败了");
						fightEvent2.setMemo("你的武将" + defph.getName() + "在驻防"
								+ att.getData().getName() + "的主城时抵御了"
								+ attph.getName() + "的进攻");
					}
					
				}
				sb.append("----------------");
				tip.setStr(sb.toString());
				return tip;
			}
			TipUtil tips = def.getPlayerBuilgingManager().getPma()
					.withdrawal((byte) 1, defph.getId(), backMsg);// user.getPlayerBuilgingManager().disarm(def.getId(),backMsg);
			sb.append(tips.getStr()+"\n");
			
			if (tips.isResult()) {// 玩家附属城撤防
				clearMaincity();//辐射城撤防成功，清空主城
				tip = presence(heroId, msg, null);// 主城驻防
				sb.append(tips.getStr()+"\n");
				if(tips.isResult()){
//					logger.info("【玩家主城收复 *** 驻扎  成功】");
					if (fightEvent1 != null ) {
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched " + attph.getName() + " to recapture your Capitol ");
						}else{
							fightEvent1.setMemo("你派遣" + attph.getName() + "收复了你的主城");
						}
						
						
					}
					if ( fightEvent2 != null) {
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent2.setMemo("Your hero "+ defph.getName() + " was defeated by " + attph.getName() + " when garrisoned " + att.getData().getName() + "'s Captiol");
						}else{
							fightEvent2.setMemo("你的武将" + defph.getName() + "在驻防"
									+ att.getData().getName() + "的主城时被"
									+ attph.getName() + "击败了");
						}
						
					}
					// 保存到 cache
					GameUtils.putToCache(owner);
					getMainCity().setTip(
							new TipMessage("主城收复成功！", ProcotolType.BUILDING_RESP,
									GameConst.GAME_RESP_SUCCESS));
					
					sb.append("主城收复成功\n");
					sb.append("----------------");
					tip.setStr(sb.toString());
					tip.setSuccTip("");
					return tip;
				}else{
					sb.append("主城驻防失败\n");
					sb.append("----------------");
					tip.setStr(sb.toString());
					tip.setFailTip("主城驻防失败");
					return tip;
				}
				
			} else {
				getMainCity().setTip(
						new TipMessage("主城收复失败！", ProcotolType.BUILDING_RESP,
								GameConst.GAME_RESP_FAIL));
//				logger.info("【pm.disarm】 失败");
				tip.setFailTip("撤退 失败");
				sb.append("撤退 失败 \n");
				sb.append("----------------");
				tip.setStr(sb.toString());
				return tip;
			}
		}else{
			sb.append("无人占领,无法收复 \n");
			sb.append("----------------");
			tip.setFailTip("收复失败");
			tip.setStr(sb.toString());
			return tip;
		}
		
	}

}
