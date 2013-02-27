package com.joymeng.game.domain.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;

/**
 * 附属城类
 * 
 * @author xufangliang
 * 
 */
public class PlayerAffiliatedManager {
	public static final int RONDOM_NUM = 6;
	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BuildingManager bldMgr = BuildingManager.getInstance();
	Map<Integer, PlayerBuilding> affiliatedMap = new HashMap<Integer, PlayerBuilding>();// 附属城
	PlayerCharacter owner;
	PlayerBuildingManager pm;
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerAffiliatedManager.class);

	public void activation(PlayerCharacter p, PlayerBuildingManager pm) {// 激活附属城操作类
		if (p != null) {
			owner = p;
			this.pm = pm;
			getAffiliatedCity(null);
			logger.info("用户：" + p.getData().getUserid() + " 附属城："
					+ affiliatedMap.size() + " 控制类加载成功");
		}
	}

	public int getOfferSize() {
		int i = 0;
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return i;
		else {
			for (PlayerBuilding p : affiliatedMap.values()) {
				if (p.getOccupyUserId() != 0) {
					i += 1;
				}
			}
		}
		return i;
	}

	/**
	 * 获取附属城集合
	 */
	public void getAffiliatedCity(PlayerBuilding p) {
		if (pm != null) {
			if (p == null) {
				for (PlayerBuilding a : pm.getPlayersFromBuild(pm
						.getBuilding(GameConst.FUSHU_CITY_ID))) {
					affiliatedMap.put(a.getId(), a);
				}
			} else {
				affiliatedMap.put(p.getId(), p);
			}

		}
	}

	//
	public PlayerBuilding getFuShuCity(int pId) {
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return null;
		return affiliatedMap.get(pId);
	}

	public PlayerBuilding getFuShuCityByUser(int userId) {
		if (affiliatedMap == null || affiliatedMap.size() == 0) {
			return null;
		} else {
			for (PlayerBuilding p : affiliatedMap.values()) {
				if (p.getOccupyUserId() == userId)
					return p;
			}
			return null;
		}
	}

	// 通过用户获取对应附属城
	public PlayerBuilding getFuShuCityByHero(int heroid) {
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return null;
		else {
			for (PlayerBuilding p : affiliatedMap.values()) {
				if (p.getOfficerId() == heroid)
					return p;
			}
			return null;
		}
	}

	// 获取未占领附属成
	public List<PlayerBuilding> afflst() {
		List<PlayerBuilding> lst = new ArrayList<PlayerBuilding>();
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return null;
		else {
			for (PlayerBuilding p : affiliatedMap.values()) {
				if (p.getOccupyUserId() == 0)
					lst.add(p);
			}
			return lst;
		}
	}

	// 获取已占领附属成
	public List<PlayerBuilding> afflst2() {
		List<PlayerBuilding> lst = new ArrayList<PlayerBuilding>();
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return null;
		else {
			for (PlayerBuilding p : affiliatedMap.values()) {
				if (p.getOccupyUserId() != 0)
					lst.add(p);
			}
			return lst;
		}
	}

	// 获取占领附属成 将领
	public List<PlayerHero> afflst3() {
		List<PlayerHero> lst = new ArrayList<PlayerHero>();
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return null;
		else {
			for (PlayerBuilding p : affiliatedMap.values()) {
				if (p.getOccupyUserId() != 0) {
					PlayerHero hh = owner.getPlayerHeroManager().getHero(
							(int) p.getOccupyUserId());
					if (hh != null) {
						lst.add(hh);
					}
				}
			}
			return lst;
		}
	}

	/**
	 * 是否占满附属城
	 * 
	 * @return
	 */
	public boolean isFreeCity() {
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return false;
		else {
			for (PlayerBuilding p : affiliatedMap.values()) {
				if (p.getOccupyUserId() == 0)
					return false;
			}
			return true;
		}
	}

	// 判断对应是否开放
	public boolean isOpenAff(int pId) {
		PlayerBuilding aff = getFuShuCity(pId);
		if (aff == null)
			return false;
		if (TimeUtils.nowLong() < aff.getOfficerTime()
				.getTime()) {
			return false;
		}
		return true;
	}

	/**
	 * 修改附属成数据
	 * 
	 * @param userId
	 * @param ssMsg
	 * @return
	 */
	public boolean motify(int userId, String ssMsg) {
		List<PlayerBuilding> afflst = afflst2();
		for (PlayerBuilding p : afflst) {
			if (p.getOccupyUserId() == userId) {
				p.setSoldierMsg(ssMsg);
				p.setOfficerTime(TimeUtils.addSecond(TimeUtils.nowLong(), 5 * 60));
//				pm.savePlayerBuilding(p);
				// ******************* rms
				PushSign.sendOne(p, new PlayerCharacter[] { owner },
						ProcotolType.BUILDING_RESP);
				return true;
			}
		}
		return false;
	}

	// 获取空附属成
	public PlayerBuilding getFreeCity() {
		List<PlayerBuilding> lst = afflst();
		if (lst != null && lst.size() > 0) {
			return lst.get(0);
		}
		return null;
	}

	/**
	 * 派兵
	 * 
	 * @param heroId
	 * @param soMsg
	 * @return
	 */
	public String dispatch(int heroId, String memo, String soMsg, long time) {
		StringBuffer sb = new StringBuffer("\n------dispatch-----------\n");
		sb.append("附属成派兵状态 ---用户：" + owner.getId() + "|将领id：" + heroId
				+ "|派兵 ：" + soMsg + "\n");
		boolean flag = owner.getPlayerHeroManager().motifyStatus(heroId,
				GameConst.HEROSTATUS_ZHUFANG, memo, soMsg, time);// 驻防
		boolean flag1 = owner.getPlayerBuilgingManager().dispatchSoldier(soMsg);// 派兵;

		if (!flag || !flag1) {
			sb.append("将领驻防或者派兵失败！\n");
			sb.append("------------------");
			return sb.toString();
		}

		sb.append("将领驻防或者派兵成功！\n");
		sb.append("------------------");
		return sb.toString();
	}

	/**
	 * 附属城驻防 通用设置
	 * 
	 * @param aff
	 *            附属城
	 * @param heroId
	 *            英雄id
	 * @param soMsg
	 *            士兵
	 * @param user
	 *            占领user的主城
	 * @param hero
	 *            英雄
	 * @return
	 */
	public boolean occAffCity(PlayerBuilding aff, String soMsg,
			PlayerCharacter user, PlayerHero hero) {
		aff.setOccupyUserId(user.getData().getUserid());
		aff.setSoldierMsg(soMsg);
		if (hero != null) {
			aff.setOfficerId(hero.getId());
			aff.setOfficerInfo(GameUtils.heroInfo(hero));
		}
		aff.setOfficerTime(TimeUtils.addSecond(TimeUtils.nowLong(), 5 * 60));// 设置占领时间
//		pm.savePlayerBuilding(aff);
//		owner.saveData();
		getTraining((byte) 0);// 设置对应训练台
		// ******************* rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
		owner.getData().setCityNum((byte) getOfferSize());
		rms.addModule(owner.getPlayerHeroManager().getHero(hero.getId()));
		if (owner.getPlayerBuilgingManager().getPlayerTraining() != null) {
			rms.addModule(owner.getPlayerBuilgingManager().getPlayerTraining()
					.getTraining());
		}
		AndroidMessageSender.sendMessage(rms, owner);
		// ******************** rms
		aff.setTip(new TipMessage("附属城驻防成功！", ProcotolType.BUILDING_RESP,
				GameConst.GAME_RESP_SUCCESS));
		QuestUtils.checkFinish(owner, QuestUtils.TYPE30, true);
		// 发送消息
		if (isFreeCity()) {
			String msg = I18nGreeting.getInstance().getMessage(
					"affiliated.message",
					new Object[] { owner.getData().getName() });
			NoticeManager.getInstance().sendSystemWorldMessage(msg);
		}

		return true;
	}

	/**
	 * 附属城驻防
	 * 
	 * @param isSucc
	 *            是否胜利
	 * @param attHeroid
	 *            进攻英雄
	 * @param soMsg
	 *            进攻剩余士兵
	 * @param haveId
	 *            防守用户
	 * @param ssMsg
	 *            防守剩余士兵
	 * @param memo
	 *            武将状态
	 * @param defheroid
	 *            防守武将
	 * @return
	 */
	public TipUtil presence(boolean isSucc, int attHeroid, String soMsg,
			int haveId, String ssMsg, String memo, FightEvent fightEvent1,
			FightEvent fightEvent2, int defheroid) {
		StringBuffer sb = new StringBuffer("\n----------presence---------\n");
		sb.append("\n presence---附属城驻防 :" + isSucc + "|攻击用户：" + owner.getId()
				+ "|攻击将领:" + attHeroid + "|攻击士兵:" + soMsg + "|属于玩家：" + haveId
				+ "|防御将领:" + defheroid + "|防御士兵:" + ssMsg + "\n");
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);//
		tip.setFailTip("附属城驻防 >>> 失败！\n");
		PlayerBuilding aff = getFreeCity();
		PlayerCharacter having = World.getInstance().getPlayer(haveId);// 城拥有玩家
		PlayerCharacter def = null;// 防御玩家
		PlayerHero defHero = null;// 防御将领
		PlayerHero atthero = owner.getPlayerHeroManager().getHero(attHeroid);
		if (having == null || atthero == null) {
			sb.append("进攻英雄或防御玩家不存在\n");
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			return tip.setFailTip("攻击失败，请重试");
		}
		if (having.getPlayerBuilgingManager().getPmc().getMainCity()
				.getOccupyUserId() == 0) {
			def = having;
		} else {
			def = World.getInstance().getPlayer(
					(int) having.getPlayerBuilgingManager().getPmc()
							.getMainCity().getOccupyUserId());
		}
		if (def != null) {
			defHero = def.getPlayerHeroManager().getHero(defheroid);
		} else {
			sb.append("防御将领不存在 \n");
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			tip.setFailTip("防御方错误");
			return tip;
		}
		if (!isSucc) {// 占领失败
			sb.append("附属城驻防 >>> 失败\n");
			tip.setFailTip("附属城驻防失败！");
			tip.setFightresult(true);
			if (defHero != null) {
				having.getPlayerBuilgingManager().getPmc().backSolider(ssMsg);// 防守方回收士兵
			}
			if (fightEvent1 != null) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					fightEvent1.setMemo("You dispatched" + atthero.getName() + " to occupy "
							+ having.getData().getName() + "'s Capitol but failed.");
				}else{
					fightEvent1.setMemo("你派遣" + atthero.getName() + "占领"
							+ having.getData().getName() + "的主城时失败了");
				}
				
			}
			if (fightEvent2 != null) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					fightEvent2.setMemo("Your hero " + defHero.getName() + " defended against"
							+ owner.getData().getName() + "'s attack when garrisoning.");
				}else{
					fightEvent2.setMemo("你的武将" + defHero.getName() + "在驻防时抵御了"
							+ owner.getData().getName() + "的进攻");
				}
				
			}
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			tip.setFailTip("");
			return tip;
		}
		if (aff == null) {
			sb.append("无空余附属城\n");
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			return tip.setFailTip("无空余附属城");
		}
		// 占领主城
		tip = having.getPlayerBuilgingManager().occupiedMainCity(true,
				(int) owner.getData().getUserid(), attHeroid, soMsg, ssMsg,
				aff, fightEvent1, fightEvent2);
		sb.append(tip.getStr() + "\n");
		if (!tip.isResult()) {
			// aff.setTip(new TipMessage("占领 "+userId+
			// " 主城失败！",ProcotolType.BUILDING_RESP,GameConst.GAME_RESP_FAIL));
			sb.append("占领 " + haveId + " 主城失败！");
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			return tip.setFailTip(tip.getResultMsg());
		} else {
			PushSign.sendOne(aff, new PlayerCharacter[] { owner },
					ProcotolType.BUILDING_RESP);
			// 战报
			// fightEvent1.setMemo("你派遣将领占领了"+user.getData().getName()+"的主城");
			// aff.setTip(new
			// TipMessage("附属城  驻扎成功！",ProcotolType.BUILDING_RESP,GameConst.GAME_RESP_SUCCESS));
			sb.append("附属城  驻扎成功\n");
			sb.append("驻防附属城\n"
					+ having.getPlayerBuilgingManager().getPmc().print() + "\n");
			sb.append("--------------------\n");
			tip.setStr(sb.toString());
			return tip.setSuccTip("");
		}
	}

	/**
	 * 附属城撤防
	 * 
	 * @param type
	 * @param pId
	 * @param userId
	 * @param soMsg
	 * @return
	 */
	public TipUtil withdrawal(byte type, int heroid, String soMsg) {// 0 : 主动撤防
																	// 1 ：对方收复
		if (type == 0) {
			return quit(heroid);
		} else if (type == 1) {
			return recover(heroid, soMsg);
		}
		return new TipUtil(ProcotolType.BUILDING_RESP).setFailTip("fail");
	}

	// 撤出附属城
	public List<ClientModule> quitFuCity(PlayerBuilding aff) {
		aff.setSoldierMsg("");
		aff.setOfficerId(0);
		aff.setOfficerInfo("");
		aff.setOccupyUserId(0);
//		pm.savePlayerBuilding(aff);
		owner.getData().setCityNum((byte) getOfferSize());
		List<ClientModule> lst1 = getTraining((byte) 1);
		lst1.add(aff);
		return lst1;
	}

	/**
	 * 撤出驻扎附属城
	 */
	public TipUtil quit(int hh) {
		StringBuffer sb = new StringBuffer("\n---------quit---------");
		PlayerBuilding aff = getFuShuCityByHero(hh);
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		if (aff != null) {
			if (aff.getOfficerTime() == null
					|| TimeUtils.nowLong() < aff
							.getOfficerTime().getTime()) {// 五分钟内不能操作
				aff.setTip(new TipMessage("附属城 驻扎五分钟内不能操作！",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL));
				sb.append("主动撤防####附属城id:" + aff.getBuildingID()
						+ ">> 五分钟内不能操作 \n");
				sb.append("--------------------");
				tip.setStr(sb.toString());
				String msg = I18nGreeting.getInstance().getMessage("quit.fail",
						null);
				return tip.setFailTip(msg);
			}
			int heroId = aff.getOfficerId();
			int userId = (int) aff.getOccupyUserId();
			String soMsg = aff.getSoldierMsg();
			PlayerCharacter user = World.getInstance().getPlayer(userId);// 玩家主城修改
			if (user != null) {
				synchronized (user.getPlayerBuilgingManager().getMainCity()) {
					if (!pm.backHeroAndSoldier(heroId, soMsg)) {// 回收士兵将领
						sb.append("主动撤防####附属城id:" + aff.getBuildingID()
								+ ">>> 【pm.backHeroAndSoldier】 错误");
						sb.append("--------------------");
						tip.setStr(sb.toString());
						return tip.setFailTip("回收士兵将领 错误");
					}
					PlayerBuilding main = user.getPlayerBuilgingManager().getPmc()
							.accord(heroId);// 主城清空
					List<ClientModule> lst = quitFuCity(aff);// 附属城撤防
					// ******* *************rms
					RespModuleSet rms = new RespModuleSet(
							ProcotolType.BUILDING_RESP);
					rms.addModules(lst);
					rms.addModule(owner.getPlayerHeroManager().getHero(heroId));
					rms.addModule(main);
					owner.getData().setCityNum((byte) getOfferSize());
					rms.addModule(owner.getData());
					if (owner.getPlayerBuilgingManager().getPlayerTraining() != null) {
						rms.addModule(owner.getPlayerBuilgingManager()
								.getPlayerTraining().getTraining());
					}
					AndroidMessageSender.sendMessage(rms, owner);
					// ******************** rms
					String msg = I18nGreeting.getInstance().getMessage(
							"aff.quit.fail", null);
					aff.setTip(new TipMessage(msg, ProcotolType.BUILDING_RESP,
							GameConst.GAME_RESP_SUCCESS));
					sb.append("成功");
					sb.append("--------------------");
					tip.setStr(sb.toString());
					return tip.setSuccTip(msg);
					// ******* *************rms
				}
				
			} else {
				aff.setTip(new TipMessage("撤出驻扎附属城失败！",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL));
				sb.append("主动撤防####用户id:" + userId + ">> 不存在");
				sb.append("--------------------");
				tip.setStr(sb.toString());
				return tip.setFailTip("主动撤防####用户id:" + userId + ">> 不存在");
			}
		} else {
			sb.append("主动撤防####附属城id:" + hh + ">> 不存在");
			sb.append("--------------------");
			tip.setStr(sb.toString());
			return tip.setFailTip("主动撤防####附属城id:" + hh + ">> 不存在");
		}
	}

	/**
	 * 附属城被收复
	 * 
	 * @param heroid
	 * @param soMsg
	 * @return
	 */
	public TipUtil recover(int heroid, String soMsg) {// 附属城被收复
		StringBuffer sb = new StringBuffer("\n---------recover----------");

		sb.append("附属城被占领####英雄id:" + heroid + "|回收士兵：" + soMsg + "\n");
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		PlayerBuilding aff = getFuShuCityByHero(heroid);
		if (aff != null) {
			sb.append("玩家####撤防/收复附属城id:" + aff.getBuildingID() + "  撤出将领："
					+ aff.getOfficerId() + " 撤出士兵：" + soMsg + "\n");
			if (!pm.backHeroAndSoldier(aff.getOfficerId(), soMsg)) {
				aff.setTip(new TipMessage("附属城被收复失败！",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL));
				sb.append("附属城被收复####【pm.backHeroAndSoldier】 >>> 失败\n");
				logger.info(sb.toString());
				sb.append("--------------------");
				tip.setStr(sb.toString());
				return tip.setSuccTip("回收将领士兵 失败");
			} else {
				List<ClientModule> lst = quitFuCity(aff);// 附属城撤防
				// **************************** rms
				RespModuleSet rms = new RespModuleSet(
						ProcotolType.BUILDING_RESP);
				rms.addModules(lst);
				rms.addModule(owner.getData());
				rms.addModule(aff);
				if (owner.getPlayerBuilgingManager().getPlayerTraining() != null) {
					rms.addModule(owner.getPlayerBuilgingManager()
							.getPlayerTraining().getTraining());
				}
				rms.addModule(owner.getPlayerHeroManager().getHero(
						aff.getOfficerId()));
				AndroidMessageSender.sendMessage(rms, owner);
				// ******************** rms
				aff.setTip(new TipMessage("附属城被收复成功！",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_SUCCESS));
				sb.append("附属城被收复成功\n");
				sb.append("--------------------");
				tip.setStr(sb.toString());
				return tip.setSuccTip("");
				// ****************************** rms
			}
		} else {
			PlayerHero playerhero = owner.getPlayerHeroManager()
					.getHero(heroid);
			if (playerhero != null
					&& playerhero.getStatus() == GameConst.HEROSTATUS_ZHUFANG) {
				owner.getPlayerHeroManager().motifyStatus(heroid,
						GameConst.HEROSTATUS_IDEL, "", "", 0);
				tip.setSuccTip("");
			} else {
				sb.append("附属城被收复####没有对应id:" + heroid + "  附属城\n");
				logger.info(sb.toString());
				sb.append("--------------------");
				tip.setStr(sb.toString());
				tip.setSuccTip("");
			}
			return tip;
		}
	}

	// 查找自己以占领用户list
	public List<Integer> myAffiliatedPlayer() {
		if (affiliatedMap == null || affiliatedMap.size() == 0)
			return null;
		else {
			List<Integer> lst = new ArrayList<Integer>();
			for (PlayerBuilding pb : affiliatedMap.values()) {
				if (pb.getOccupyUserId() != 0) {
					lst.add((int) pb.getOccupyUserId());
				}
			}
			return lst;
		}
	}

	public static void main(String[] args) {
		List<Integer> lst = new ArrayList<Integer>();
		lst.add(1);
		lst.add(2);
		List<Integer> lst2 = new ArrayList<Integer>();
		lst2.add(1);
		lst2.add(2);
		lst2.addAll(lst);
		System.out.println(lst2);
	}

	public PlayerHero getTrainHero(int no) {
		PlayerHero[] heros = owner.getPlayerHeroManager().getHeroArray();
		for (int i = 0; i < heros.length; i++) {
			PlayerHero hero = heros[i];
			if (hero != null && hero.getStatus() == GameConst.HEROSTATUS_TRAIN
					&& hero.getTrainIndex() == (byte) no) {
				owner.getPlayerHeroManager().motifyStatus(hero.getId(),
						GameConst.HEROSTATUS_IDEL, "", "", 0);
				return hero;
			}
		}
		return null;
	}

	//
	public List<ClientModule> getTraining(byte type) {
		List<ClientModule> lst = new ArrayList<ClientModule>();
		// 判断附属成信息
		int times = getOfferSize();
		PlayerBuilding training = null;
		if (type == 0) {// 开启
			if (times >= 1 && times < 4) {
				training = pm.openTraining(3);
			} else if (times >= 4 && times < 7) {
				training = pm.openTraining(3);
				training = pm.openTraining(4);
			} else if (times >= 7) {
				training = pm.openTraining(3);
				training = pm.openTraining(4);
				training = pm.openTraining(5);
			}
		} else if (type == 1) {
			if (times < 1) {
				training = pm.closeTraining(5);
				lst.add(getTrainHero(5));
				training = pm.closeTraining(4);
				lst.add(getTrainHero(4));
				training = pm.closeTraining(3);
				lst.add(getTrainHero(3));
			} else if (times >= 1 && times < 4) {
				training = pm.closeTraining(5);
				lst.add(getTrainHero(5));
				training = pm.closeTraining(4);
				lst.add(getTrainHero(4));
			} else if (times >= 4 && times < 7) {
				training = pm.closeTraining(5);
				lst.add(getTrainHero(5));
			} else if (times >= 7) {
				// training = pm.closeTraining(5);
				// lst.add(getTrainHero(5));
			}
		}
		lst.add(training);
		return lst;
	}

	public boolean isFull() {
		int times = getOfferSize();
		if (times >= affiliatedMap.size()) {
			return false;
		}
		return true;
	}

}
