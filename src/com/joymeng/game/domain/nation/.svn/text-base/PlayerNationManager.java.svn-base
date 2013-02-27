package com.joymeng.game.domain.nation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.Instances;
import com.joymeng.game.domain.building.PlayerBarrackManager;
import com.joymeng.game.domain.building.PlayerBuildingManager;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.nation.war.ComparatorWar;
import com.joymeng.game.domain.nation.war.MilitaryCamp;
import com.joymeng.game.domain.nation.war.StrongHold;
import com.joymeng.game.domain.nation.war.UserWarData;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.UsernameManager;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;

public class PlayerNationManager implements Instances {// 用户世界
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerBuildingManager.class);
	PlayerCharacter player;// 用户
	private static NationManager naMgr = NationManager.getInstance();
	private static WarManager waMgr = WarManager.getInstance();

	public PlayerCharacter getPlayer() {
		return player;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	// 获取州下所有市
	public List<ClientModuleBase> getAllCity(int stateId) {
//		logger.info("州id:" + stateId);
		System.out.println(naMgr.cityMap.get(stateId));
		List<ClientModuleBase> arr = new ArrayList<ClientModuleBase>();
		if(naMgr.cityMap.get(stateId) != null){
			for (Nation n : naMgr.cityMap.get(stateId)) {
				arr.add(n);
				if (!"".equals(n.getHeroInfo())) {
					SimplePlayerHero playerHero = new SimplePlayerHero(null,
							n.getHeroInfo());
					if (playerHero != null) {
						arr.add(playerHero);
					}
				}

				// if(n.getOccupyUser() != 0){
				// PlayerCharacter pp =
				// World.getInstance().getPlayer(n.getOccupyUser());
				// if(pp != null){
				// PlayerHero heros =
				// pp.getPlayerHeroManager().getHero(n.getHeroId());
				// if(heros != null){
				// SimplePlayerHero playerHero = new SimplePlayerHero(heros);
				// arr.add(playerHero);//添加英雄
				// }
				// }
				// }
			}
		}
		
//		logger.info("市级数据:" + arr.size());
		return arr;
	}

	// 获取所有州
	public HashMap<Integer, Nation> getAllState() {
		return naMgr.stateMap;
	}

	public HashMap<Integer, Nation> getAllCountry() {
		return naMgr.countryMap;
	}

	// 获取本周id
	public int getStateId() {
		return player.getData().getNativeId() / 100 * 100;
	}

	/**
	 * 获取所有科技
	 * 
	 * @param stateId
	 * @param type
	 * @return
	 */
	public List<Veins> getVeins(int stateId, byte type) {
		// List<Veins> veLst = new ArrayList<Veins>();
		List<Veins> allLst = naMgr.getMyVeins(stateId, type);
		// if(allLst == null){
		// return veLst;
		// }
		// for(Veins v : allLst){
		// if(v.getType() == type){
		// veLst.add(v);
		// }
		// }
		return allLst;
	}

	// List<GoldMine> gLst = getGold(stateId, type);
	/**
	 * 修改名字
	 * 
	 * @return
	 */
	public TipUtil setNationName(int id, String name) {
		TipUtil tip = new TipUtil(ProcotolType.NATION_RESP);
		tip.setFailTip("修改失败");

		StringBuffer sb = new StringBuffer();
		sb.append("|--------------------------------------------\n");
		sb.append("|修改名字=====城镇id:" + id + "|用户：" + player.getId() + "\n");

		Nation n = naMgr.nationMap.get(id);
		boolean flag = false;
		byte key = player.getData().getTitle();// 身份
		int bId = player.getData().getNativeId();
		byte deductionType = GameConfig.JOY_MONEY;// 消耗类型
		int deductionCount = 0;// 数量
		if (n == null) {
			tip.setFailTip("城镇id:" + id + "为空,无法修改");
			sb.append("|城镇id:" + id + "为空,无法修改" + "\n");
		} 
		else if(name.equals(n.getName())){
			tip.setFailTip("");
			sb.append("没有对名称作任何修改");
		}else {
			tip.setFailTip("级别不足无法修改");
			switch (key) {
			case GameConst.TITLE_MAYOR_TOWN:
				if (bId != id) {
					tip.setFailTip("非本县不能修改");
					sb.append("|非本县不能修改" + "\n");
					break;
				} else {
					flag = true;
					deductionType = GameConfig.GAME_MONEY;
					deductionCount = 100000;
					break;
				}
			case GameConst.TITLE_MAYOR_CITY:
				if (bId / 10 * 10 != id) {
					tip.setFailTip("非本市不能修改");
					sb.append("|非本市不能修改" + "\n");
					break;
				} else {
					flag = true;
					deductionCount = 20;
					break;
				}
			case GameConst.TITLE_GOVERNOR:
				if (bId / 100 * 100 != id) {
					tip.setFailTip("非本州不能修改");
					sb.append("|非本州不能修改" + "\n");
					break;
				} else {
					flag = true;
					deductionCount = 50;
					break;
				}
			case GameConst.TITLE_KING:
				if (bId / 1000 * 1000 != id) {
					tip.setFailTip("非本国不能修改");
					sb.append("|非本国不能修改" + "\n");
					break;
				} else {
					flag = true;
					deductionCount = 500;
					break;
				}
			}
		}
		if (flag) {
			sb.append("---------------条件符合可以修改 -------------------\n");
			TipUtil tipUtil = UsernameManager.getInstance().isNameLegal(name,2);
			
			if (name == null || "".equals(name)) {
				tip.setFailTip("请输入正确的名称");
				sb.append("|name 为空 无法修改 " + "\n");
			}
			else if(! tipUtil.isResult()){
				tip = tipUtil;
			}else if(!tipUtil.isResult()){
				tip.setFailTip(tipUtil.getTip().getMessage());
			}else if (name.length() > 12) {
				tip.setFailTip("请输入正确的名称");
				sb.append("|name 超出长度 无法修改 " + "\n");
			} else if (naMgr.isSameName(id, name)) {
				tip.setFailTip("该名称重复，请重新输入");
				sb.append("|name 有重名 无法修改 " + "\n");
			} else {
				if (player.saveResources(deductionType, -1 * deductionCount) < 0) {
					tip.setFailTip("钻石不足");
					sb.append("|消耗：" + deductionType + "|数量：" + deductionCount
							+ " 不足" + "\n");
				} else {
					n.setName(name);
					gameDao.getNationDAO().saveNation(n);// 保存
					// 扣钱 或砖石
					String msg = I18nGreeting.getInstance().getMessage(
							"nation.name.alter.success", null);
					tip.setSuccTip(msg);
					sb.append("|修改成功 " + "\n");
				}
			}
		}
		sb.append("|----------------------------------------" + "\n");
		logger.info(sb.toString());

		return tip;
	}


	/**
	 * 获取资源页面
	 * 
	 * @param stateId
	 *            州id
	 * @param type
	 *            资源建筑类型
	 * @return
	 */
	public ArrayList<ClientModule> getAllResource(byte p, byte type, int stateId) {
//		logger.info("级别： " + p + " 类型：" + type + " 州：" + stateId);
		ArrayList<ClientModule> rLst = new ArrayList<ClientModule>();
		GoldMine gold = naMgr.getMyOccupyGold(type, player);// 我占领的金矿
		int basic = 0;
		if (p == 0 && gold != null && stateId == getStateId()) {// 本州
			p = gold.getLevel();
		} else if (p == 0) {
			p = 1;
		}
		int id = stateId * 1000 + p * 100 + type * 10;
		if (p == 1) {
			List<Veins> vLst = getVeins(stateId, type);
			if (vLst != null && vLst.size() > 0 && naMgr.allGoldMap != null
					&& naMgr.allGoldMap.size() > 0) {
				rLst.addAll(vLst);
				for (Veins veins : vLst) {
					if (veins != null && veins.getUserId() != 0
							&& !"".equals(veins.getHeroInfo())) {
						SimplePlayerHero sp = new SimplePlayerHero(null,
								veins.getHeroInfo());
						rLst.add(sp);
					}
				}
				for (int i = 3; i < 8; i++) {
					int ids = id + i;
					GoldMine gg = naMgr.allGoldMap.get(ids);
					if (gg != null) {
						basic = gg.getChargeOut();
						if (gg.getUserId() != 0 && !"".equals(gg.getHeroInfo())) {
							SimplePlayerHero sp = new SimplePlayerHero(null,
									gg.getHeroInfo());
							rLst.add(sp);
						}
						rLst.add(gg);
					}
				}
			} else {
//				logger.info(" 州：" + stateId + "  资源类型：" + type + " 固化数据错误");
			}
		} else {
			for (int i = 0; i < 8; i++) {
				int ids = id + i;
				GoldMine gg = naMgr.allGoldMap.get(ids);
				if (gg != null) {
					basic = gg.getChargeOut();
					if (gg.getUserId() != 0 && !"".equals(gg.getHeroInfo())) {
						if (gg.getUserId() != 0 && !"".equals(gg.getHeroInfo())) {
							SimplePlayerHero sp = new SimplePlayerHero(null,
									gg.getHeroInfo());
							rLst.add(sp);
						}
					}
					rLst.add(gg);
				}
			}
		}
		SimpleResource sr = new SimpleResource(player, gold, naMgr.myVeins(
				stateId, (int) (player.getData().getUserid()), type),
				naMgr.basicAdd(stateId, type), basic, p,type);
		// 查询本周拥有
		// ************************
		RespModuleSet rms = new RespModuleSet(ProcotolType.NATION_RESP);
		rms.addModule(sr);
		rms.addModules(rLst);
		// rms.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms, player);
		// ************************
//		logger.info("页码" + p + " 州：" + stateId + "  资源类型：" + type + " 返回数量："
//				+ rLst.size());
		return rLst;
	}

	/**
	 * 剔除和特种兵
	 * 
	 * @param add
	 * @return
	 */
	public String takeOffSoliderEqu(String add) {
		HashMap<Integer, String> map = PlayerBarrackManager.resolveSoMsg(add);// 派兵
		HashMap<Integer, String> newMap = new HashMap<Integer, String>();
		for (Integer i : map.keySet()) {
			newMap.put(i, map.get(i).split(",")[0] + ",0");
		}
		return PlayerBarrackManager.generateSoMsg(newMap);
	}

	/**
	 * 添加士兵
	 * 
	 * @param veinsId
	 * @param playerId
	 * @param add
	 */
	public TipUtil addSoliderToVeins(int veinsId, int playerId, String add) {
		TipUtil tip = new TipUtil(ProcotolType.NATION_RESP);
		Veins veins = naMgr.allVeinsMap.get(veinsId);
		PlayerCharacter player = World.getInstance().getPlayer(playerId);
		if (veins == null || player == null) {
			tip.setFailTip("神祠或者用户不存在!");
			return tip;
		} else {
			if (veins.isAdd(player)) {
				if("".equals(add)){
					return tip.setFailTip("请选择增兵数目");
				}
				String sAdd = veins.takeOffSoliderEqu(add);
				if (!"".equals(sAdd)) {
					if (player.getPlayerBuilgingManager().dispatchSoldier(sAdd)) {
						tip = veins.addSoldier(player, add);
						if (tip.isResult()) {
							if (veins.getType() == 0) {
								QuestUtils.checkFinish(player, QuestUtils.TYPE37, true);
							} else if (veins.getType() == 1) {
								QuestUtils.checkFinish(player, QuestUtils.TYPE40, true);
							}
						}
						return tip;
					} else {
						return tip.setFailTip("派兵错误");
					}
				} else {
					return tip.setFailTip("已到上限，无法增兵");
				}
			} else {
				return tip.setFailTip("非本州科技无法增兵");
			}
		}
	}

	public List<UserWarData> nationFightUserWarData(int id) {
		List<UserWarData> arr = new ArrayList<UserWarData>();
		for (UserWarData data : WarManager.getInstance().warData.values()) {
			if (data != null && data.getNationId() == id) {
				arr.add(data);
				// PushSign.sendOne(data, new PlayerCharacter[]{player},
				// ProcotolType.NATION_RESP);
			}
		}
		return arr;
	}

	/**
	 * 下方争夺战数据
	 * 
	 * @param id
	 */
	public List<ClientModuleBase> nationFight(int id) {
		Nation nation = naMgr.getNation(id);
		List<ClientModuleBase> arr = new ArrayList<ClientModuleBase>();
		boolean flag = false;
		if (nation != null && WarManager.getInstance().IS_FIGHT) {
			if (id % 1000 == 0 && nation.getEventId() == 3) {
				flag = true;
			} else if (id % 100 == 0 && nation.getEventId() == 2) {
				flag = true;
			} else if (id % 10 == 0 && nation.getEventId() == 1) {
				flag = true;
			}
		}
		if (flag) {
			for (MilitaryCamp camp : waMgr.allCamp.values()) {
				if (camp.getNativeId() == id && !camp.isFalseCamp()) {
					arr.add(camp);// 添加军营
					StrongHold sh = waMgr.getMyStrong(camp.getId());
					arr.add(sh);// 添加据点
					if (camp.getOccCache() != null) {
						PlayerCharacter pp = World.getInstance().getPlayer(camp.getOccCache().getUserid());
						if (pp != null) {
							PlayerHero heros = pp.getPlayerHeroManager()
									.getHero(camp.getHeroId());
							if (heros != null) {
								SimplePlayerHero playerHero = new SimplePlayerHero(
										heros, "");
								arr.add(playerHero);// 添加英雄
							}
						}
					}
				}
			}
			for (MilitaryCamp camp : waMgr.allCamp.values()) {
				if (camp.getNativeId() == id && camp.isFalseCamp()) {
					arr.add(camp);// 添加军营
					if (camp.getOccCache() != null) {
						PlayerCharacter pp = World.getInstance().getPlayer(camp.getOccCache().getUserid());
						if (pp != null) {
							WarManager.getInstance().getMyWarData(pp.getId(),
									id);
							PlayerHero heros = pp.getPlayerHeroManager()
									.getHero(camp.getHeroId());
							if (heros != null) {
								SimplePlayerHero playerHero = new SimplePlayerHero(
										heros, "");
								arr.add(playerHero);// 添加英雄
							}
						}
					}
				}
			}
		}

		 // rms
//		 RespModuleSet rms = new RespModuleSet(ProcotolType.NATION_RESP);
//		 rms.addModuleBase(arr);
//		 AndroidMessageSender.sendMessage(rms, player);
		 // rms

		System.out.println("=============================size" + arr.size());
		return arr;
	}

	/**
	 * 增兵
	 * 
	 * @param campId
	 * @param addSolider
	 * @return
	 */
	public TipUtil moreTroops(int campId, String addSolider) {
		MilitaryCamp camp = WarManager.getInstance().getMyCamp(campId);
		TipUtil tip = new TipUtil(ProcotolType.NATION_RESP);
		tip.setFailTip("失败");
		if (camp != null) {
			logger.info("增兵军营id:" + camp.getId());
			tip = camp.moreTroops(player, addSolider);
		} else {
//			logger.info("军营不存在");
		}
		return tip;
	}

}
