package com.joymeng.game.domain.building;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.fight.FightLog;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.Instances;
import com.joymeng.game.db.dao.BuildingDAO;
import com.joymeng.game.domain.fight.FightConst;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.fight.mod.Arena;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.item.ItemConst;
import com.joymeng.game.domain.item.PropsDelay;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.nation.GoldMine;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.Veins;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.PlayerLimitManager;
import com.joymeng.game.domain.role.PlayerNobility;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;

/**
 * 用户建筑类管理操作类
 * 
 * @author admin
 * @date 2012-4-24
 */
public class PlayerBuildingManager implements Instances {

	static EquipmentManager equipMgr = EquipmentManager.getInstance();
	static BuildingManager bldMgr = BuildingManager.getInstance();

	private PlayerMainCityManager pmc;
	private PlayerAffiliatedManager pma;
	private PlayerTavernManager playerTavern;
	private PlayerBarrackManager playerBarrack;
	private PlayerBlackSmithyManager playerBlackSmithy;
	private PlayerTrainingManager playerTraining;
	private static final Logger logger = LoggerFactory
			.getLogger(PlayerBuildingManager.class);
	private static NationManager naMgr = NationManager.getInstance();
	// 操作map类型
	public static final int TYPE_ADD = 1;
	public static final int TYPE_REMOVE = 2;
	public static final byte DEFALUT_BUILD = 0;// 建造中
	public static final byte COMPLETE_BUILD = 1;
	public static final byte LEVELUP_BUILD = 2;// 正在升级
	public static final byte COMPLETE_LEVELUP = 3;// 升级完成

	public static final int HORSE = 3;
	public static final int IRON = 2;
	public static final int WOOD = 1;
	public static final int GOLD = 0;
	public static final int BUILD_QUEUE_TYPE = 4;
	// 收取，偷盗类型
	public static final byte CHARGE_OUT = 1;
	public static final byte CHARGE_STEAL = 2;

	static Integer[] upgrade = new Integer[] { 1000, 1007, 1008, 1009, 1010,
			1011, 1012, 1014 };
	// 用户
	PlayerCharacter player;

	public boolean isBuilding;// 是否可以建造

	public boolean isDelay;// 是否使用延时道具

	public PlayerCharacter getPlayer() {
		return player;
	}

	public boolean isBuilding() {
		return isBuilding;
	}

	public boolean isDelay() {
		return isDelay;
	}

	public PlayerTavernManager getPlayerTavern() {
		return playerTavern;
	}

	public void setPlayerTavern(PlayerTavernManager playerTavern) {
		this.playerTavern = playerTavern;
	}

	public void setDelay(boolean isDelay) {
		this.isDelay = isDelay;
	}

	public void setBuilding(boolean isBuilding) {
		this.isBuilding = isBuilding;
	}

	public void setPlayer(PlayerCharacter player) {
		this.player = player;
	}

	/**
	 * @return the pmc
	 */
	public PlayerMainCityManager getPmc() {
		return pmc;
	}

	/**
	 * @param pmc
	 *            the pmc to set
	 */
	public void setPmc(PlayerMainCityManager pmc) {
		this.pmc = pmc;
	}

	/**
	 * @return the pma
	 */
	public PlayerAffiliatedManager getPma() {
		return pma;
	}

	/**
	 * @param pma
	 *            the pma to set
	 */
	public void setPma(PlayerAffiliatedManager pma) {
		this.pma = pma;
	}

	/**
	 * @return GET the playerBarrack
	 */
	public PlayerBarrackManager getPlayerBarrack() {
		return playerBarrack;
	}

	/**
	 * @param SET
	 *            playerBarrack the playerBarrack to set
	 */
	public void setPlayerBarrack(PlayerBarrackManager playerBarrack) {
		this.playerBarrack = playerBarrack;
	}

	/**
	 * @return GET the playerBlackSmithy
	 */
	public PlayerBlackSmithyManager getPlayerBlackSmithy() {
		return playerBlackSmithy;
	}

	/**
	 * @param SET
	 *            playerBlackSmithy the playerBlackSmithy to set
	 */
	public void setPlayerBlackSmithy(PlayerBlackSmithyManager playerBlackSmithy) {
		this.playerBlackSmithy = playerBlackSmithy;
	}

	/**
	 * @return GET the playerTraining
	 */
	public PlayerTrainingManager getPlayerTraining() {
		return playerTraining;
	}

	/**
	 * @param SET
	 *            playerTraining the playerTraining to set
	 */
	public void setPlayerTraining(PlayerTrainingManager playerTraining) {
		this.playerTraining = playerTraining;
	}

	// >>>>>>>>>>>>>>>>>>基本方法 basic<<<<<<<<<<<<<<<<<<<<<<<<<
	public void activation(PlayerBuilding p) {
		int key = p.getBuildingID();
		switch (key) {
		case GameConst.MAINCITY_ID:
			if (pmc == null) {
				pmc = new PlayerMainCityManager();
				pmc.activation(player, this);
			}

			break;
		case GameConst.BARRACK_ID:
			if (playerBarrack == null) {
				playerBarrack = new PlayerBarrackManager();
				playerBarrack.activation(player, this);
			}

			break;
		case GameConst.BLACKSMITHY_ID:
			if (playerBlackSmithy == null) {
				playerBlackSmithy = new PlayerBlackSmithyManager();
				playerBlackSmithy.activation(player, this);
			}

			break;
		case GameConst.FUSHU_CITY_ID:
			if (pma == null) {
				pma = new PlayerAffiliatedManager();
				pma.activation(player, this);
			} else {
				pma.getAffiliatedCity(p);
			}
			break;
		case GameConst.JIUGUAN_ID:
			if (playerTavern == null) {
				playerTavern = new PlayerTavernManager();
				playerTavern.activation(player, this);
			}
			break;
		case GameConst.TRAINING_ID:
			if (playerTraining == null) {
				playerTraining = new PlayerTrainingManager();
				playerTraining.activation(player, this);
			}
			break;
		}
	}

	/**
	 * key =buildingId_id
	 */
	public HashMap<Integer, PlayerBuilding> playerBuildDatas = new HashMap<Integer, PlayerBuilding>();
	public Map<Integer, PlayerSoldier> playerSolDatas = new HashMap<Integer, PlayerSoldier>();// 兵营士兵数据

	/**
	 * 判断playerBuildDatas是否为空
	 * 
	 * @return
	 */
	public boolean checkbuildDatas() {
		if (playerBuildDatas == null || playerBuildDatas.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据ID 获取PlayerBuilding
	 * 
	 * @param id
	 * @return
	 */
	public PlayerBuilding getPlayerBuild(int PlayerBuildingId) {
		if (checkbuildDatas())
			return null;
		//logger.info("sssssssssss:"+player.getId()+"|"+playerBuildDatas.size());
		return playerBuildDatas.get(PlayerBuildingId);
	}

	/**
	 * 更改 缓存
	 * 
	 * @param p
	 * @param type
	 * @return
	 */
	public PlayerBuilding modifyMyDatas(PlayerBuilding p, int type) {
		if (type == TYPE_ADD) {
			playerBuildDatas.put(p.getId(), p);
		} else if (type == TYPE_REMOVE) {
			// playerBuildDatas.remove(p);
		}
		return p;
	}

	// 是否主城
	public static boolean isMian(int pId) {
		return pId == GameConst.MAINCITY_ID;
	}

	/**
	 * 初始化士兵
	 * 
	 * @return
	 */
	public String getsoliderInit() {
		StringBuffer solider = new StringBuffer();
		for (int i = 1; i < 4; i++) {
			solider.append(player.getId()).append(",").append(i).append(",")
					.append(GameConst.INITIAL_SOLIDER).append(",0,0;");
		}
		return solider.toString();
	}

	/**
	 * 保存用户建筑信息
	 * 
	 * @param playerbuilding
	 * @return
	 */
	public boolean savePlayerBuilding(PlayerBuilding playerbuilding) {
		if (playerbuilding != null) {
			gameDao.getBuildDAO().savePlayerBuilding(playerbuilding);
			// logger.info(playerbuilding.toString());
		} else {
			// logger.info("savePlayerBuilding \n"+playerbuilding.toString());
		}

		// 存入放入map
		// playerBuildDatas.put(playerbuilding.getId(), playerbuilding);
		return true;
	}

	public void saveAllBuilding() {
		if (playerBuildDatas != null && playerBuildDatas.size() > 0) {
			for (PlayerBuilding pb : playerBuildDatas.values()) {
				synchronized (pb) {//同步
					savePlayerBuilding(pb);
				}
				
			}
		}
	}

	/**
	 * 等级是否开放
	 * 
	 * @param id
	 *            坐标ID
	 * @return
	 */
	public boolean isOpen(int bId) {
		// ->>>>>>>>>查询坐标点是否开放
		return bldMgr.isOpen(player.getData().getLevel(), bId);
	}

	/**
	 * 通过buildIngId获得建筑Build
	 * 
	 * @param bId
	 * @return
	 */
	public Building getBuilding(int bId) {
		return bldMgr.getBuilding(bId);
	}

	/**
	 * 获取某类建筑 数组
	 * 
	 * @param build
	 *            build对象
	 * @return
	 */
	public List<PlayerBuilding> getPlayersFromBuild(Building build) {
		if (checkbuildDatas())
			return null;
		List<PlayerBuilding> playerDatas = new ArrayList<PlayerBuilding>();
		for (PlayerBuilding pi : playerBuildDatas.values()) {
			if (build.getId() == pi.getBuildingID()) {
				playerDatas.add(getPlayerBuild(pi.getId()));
			}
		}
		return playerDatas;
	}

	/**
	 * 获取某类建筑 数组
	 * 
	 * @param build
	 *            build对象
	 * @return
	 */
	public PlayerBuilding getPlayersBuild(int buildingId, int userID) {
		for (PlayerBuilding pi : playerBuildDatas.values()) {
			if (buildingId == pi.getBuildingID() && pi.getUserID() == userID) {
				return pi;
			}
		}
		return null;
	}

	/**
	 * 获取PlayerBuilding
	 * 
	 * @param build
	 * @return
	 */
	public PlayerBuilding getPlayerFromBuild(Building build) {
		List<PlayerBuilding> pb = getPlayersFromBuild(build);
		if (pb == null || pb.size() == 0 || build.getIsUnique() == 0)
			return null;
		return pb.get(0);
	}

	/**
	 * 获得建筑等级 -- 用于建筑升级
	 * 
	 * @param id
	 *            建筑ID
	 * @return
	 */
	public short getBuildLevel(int bId) {
		PlayerBuilding pb = getPlayerFromBuild(getBuilding(bId));
		if (pb == null)
			return -1;
		return pb.getBuildLevel();
	}

	/**
	 * 获取酒馆更新时间
	 */
	public Timestamp getTavernTime() {
		if (playerTavern == null)
			return null;
		return playerTavern.getUpdateTime();
	}

	/**
	 * 设置酒馆刷新时间
	 */
	public void setTavernTime(long time) {
		if (playerTavern != null)
			playerTavern.setHeroTime();// 设置新时间
	}

	/**
	 * 获取经验列表
	 * 
	 * @param no
	 * @return
	 */
	public int[] getTrainExp(int no) {
		// logger.info("结束训练位为:" + no);
		if (playerTraining == null)
			return null;
		return playerTraining.getTrainExp(no);
	}

	/**
	 * 获取技能列表
	 * 
	 * @param no
	 * @return
	 */
	public int[] getTrainSkill(int no) {
		if (playerTraining == null)
			return null;
		return playerTraining.getTrainSkill(no);
	}

	// >>>>>>>>>>>>>>>>>>基本方法 basic <<<<<<<<<<<<<<<<<<<<<<<<<

	/**
	 * 加载用户数据
	 * 
	 * @param player_id
	 */
	public void load() {
		login();
		// 用户不存在
		if (checkbuildDatas()) {
			registered();
		}
	}

	/**
	 * 加载用户数据到缓存
	 * 
	 * @param player_id
	 *            用户ID
	 */
	public void login() {
		try {
			// logger.info("开始载入用户id:" + player.getId()
			// + "建筑数据<<<<<<<<<<<<<<<<<<<");
			// playerBuildDatas.clear();
			long time=TimeUtils.nowLong();
			List<PlayerBuilding> playerLst = gameDao.getBuildDAO()
					.getAllPlayerBuilding((int) player.getData().getUserid());
//			logger.info("init building 耗时="+(TimeUtils.nowLong()-time));
			if (playerLst != null && playerLst.size() > 0) {
				for (PlayerBuilding data : playerLst) {
					data = specal(data);
					modifyMyDatas(data, TYPE_ADD);
					// logger.info("用户id:"+player.getId()+"建筑id:"+data.getBuildingID());
					activation(data);
				}
			}
			System.out.println("***************" + playerBuildDatas.size());
			// logger.info("载入用户建筑数据成功<<<<<<<<<<<<<<<<<<<");
			// if(pma != null){
			// pma.getAffiliatedCity();
			// }
		} catch (Exception e) {
			logger.info("载入用户建筑数据失败<<<<<<<<<<<<<<<<<<<");
			GameLog.error("error playerBuilding login", e);
		}
	}

	public int specialConstruction() {
		switch (player.getNational()) {
		case 1000:
			return 1018;
		case 2000:
			return 1016;
		case 3000:
			return 1017;
		default:
			return 0;
		}
	}

	/**
	 * 切换特殊建筑
	 * 
	 * @param data
	 * @return
	 */
	public PlayerBuilding specal(PlayerBuilding data) {
		if ((data.getBuildingID() == 1016 || data.getBuildingID() == 1017 || data
				.getBuildingID() == 1018)
				&& data.getBuildingID() != specialConstruction()
				&& specialConstruction() != 0) {
			data.setBuildingID(specialConstruction());
			return data;
		}
		return data;
	}

	/**
	 * 修改特殊建筑
	 * 
	 * @return
	 */
	public PlayerBuilding getspecalData() {
		PlayerBuilding pb = null;
		for (PlayerBuilding data : playerBuildDatas.values()) {
			pb = specal(data);
		}

		// rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
		rms.addModule(pb);
		AndroidMessageSender.sendMessage(rms, player);
		// rms
		return null;

	}

	/**
	 * 注册加载默认建筑数据、并保存数据库
	 * 
	 * @param player_id
	 *            用户ID
	 */
	public void registered() {
		try {
			// logger.info("开始载入默认建筑固化数据<<<<<<<<<<<<<<<<<<<");
			HashMap<Integer, OriginalBuilding> data = bldMgr.buildMapDatas;
			for (OriginalBuilding b : data.values()) {
				System.out.println("....." + b.getBuildId());
				if (b.getRequireLevel() == 0
						&& (b.getRequireKingdom() == 0 || b.getRequireKingdom() == player
								.getData().getNativeId() / 1000)) {
					PlayerBuilding p = loadObject(b.getId(), b.getInitLevel());
					if (p != null) {
						// 放入数据库
						int bId = gameDao.getBuildDAO().addPlayerBuilding(p);
						// 放入缓存
						p.setId(bId);
						modifyMyDatas(p, TYPE_ADD);
						activation(p);
					}

				}
			}
			// 存入数据库
			// logger.info("载入默认建筑固化数据成功<<<<<<<<<<<<<<<<<<<");
			System.out.println(playerBuildDatas.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("载入默认建筑固化数据失败<<<<<<<<<<<<<<<<<<<");
			GameLog.error("error in load playerBuilding", e);
		}
	}

	public int getFucityNum() {
		if (pma == null) {
			return 0;
		} else {
			return pma.getOfferSize();
		}
	}

	/**
	 * 用户升级时，下发对应开放建筑
	 * 
	 * @return
	 */
	public List<PlayerBuilding> userLevelUp(short level1, short level2) {
		List<PlayerBuilding> lst = new ArrayList<PlayerBuilding>();
		try {
			// logger.info("开始载入 用户： " + player.getData().getLevel()
			// + "级建筑固化数据<<<<<<<<<<<<<<<<<<<");
			HashMap<Integer, OriginalBuilding> data = bldMgr.buildMapDatas;
			for (OriginalBuilding b : data.values()) {
				if (b.getRequireLevel() > level1
						&& b.getRequireLevel() <= level2
						&& (b.getRequireKingdom() == 0 || b.getRequireKingdom() == player
								.getData().getNativeId() / 1000)) {
					PlayerBuilding p = loadObject(b.getId(), (short) 1);
					if (p != null) {
						// 放入数据库
						// logger.info("添加建筑:" + p.getBuildingID() + "|用户等级:"
						// + player.getData().getLevel());
						int bId = gameDao.getBuildDAO().addPlayerBuilding(p);
						// 放入缓存
						p.setId(bId);
						modifyMyDatas(p, TYPE_ADD);
						activation(p);
						if (p.getBuildingID() == GameConst.FUSHU_CITY_ID) {
							pma.getAffiliatedCity(p);// 生成附属成。重新
							modifyMyDatas(p, TYPE_ADD);
						}
						if (p.getBuildingID() == GameConst.TRAINING_ID) {
							if (pma != null) {
								List<ClientModule> pmaLst = pma
										.getTraining((byte) 0);
								// rms
								RespModuleSet rms1 = new RespModuleSet(
										ProcotolType.BUILDING_RESP);
								rms1.addModules(pmaLst);
								AndroidMessageSender.sendMessage(rms1, player);
								// rms
								player.checkTraining();
							}
							modifyMyDatas(p, TYPE_ADD);
						}
						lst.add(p);
						// 消息
						if (p.getBuildingID() == 1019) {
							String msg = I18nGreeting.getInstance()
									.getMessage(
											"building19.message",
											new Object[] { player.getData()
													.getName() });
							NoticeManager.getInstance().sendSystemWorldMessage(
									msg);
						} else if (p.getBuildingID() == 1013) {
							String msg = I18nGreeting.getInstance()
									.getMessage(
											"building13.message",
											new Object[] { player.getData()
													.getName() });
							NoticeManager.getInstance().sendSystemWorldMessage(
									msg);
						}
					}

				}
			}
			// 存入数据库
			// logger.info("载入建筑  用户： " + player.getData().getLevel()
			// + "级固化数据成功<<<<<<<<<<<<<<<<<<<");
			// logger.info("*************** 新加建筑：" + lst.size());
			// rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
			for (PlayerBuilding pp : lst) {
				rms.addModule(pp);
			}
			AndroidMessageSender.sendMessage(rms, player);
			// rms
			// for(PlayerBuilding ppp : playerBuildDatas.values()){
			// logger.info("*******"+player.getId()+"********建筑：" +
			// ppp.getBuildingID());
			// }

		} catch (Exception e) {
			logger.info("载入建筑 用户： " + player.getData().getLevel()
					+ "级固化数据失败<<<<<<<<<<<<<<<<<<<");
			GameLog.error("error in load playerBuilding", e);
		}
		return lst;
	}

	/**
	 * 获得所有建筑
	 * 
	 * @return
	 */
	public List<PlayerBuilding> getPlayerAll() {
		// List<PlayerBuilding> lst = new ArrayList<PlayerBuilding>();
		// for(Integer i : playerBuildDatas.keySet()){
		// lst.add(playerBuildDatas.get(i));
		// }
		return new ArrayList<PlayerBuilding>(playerBuildDatas.values());
	}

	/**
	 * 获取固化数据
	 * 
	 * @return
	 */
	public List<Building> getAllBuilding() {
		return bldMgr.getBuildItem();
	}

	/**
	 * 更新时间
	 */
	public Timestamp updateDataTime(Timestamp d, int time) {
		return TimeUtils.addSecond(d.getTime(), time);
	}

	/**
	 * 设置用户建筑 收益/刷新时间
	 * 
	 * @param buildId
	 * @param d
	 * @param time
	 */
	public void updateOptTime(PlayerBuilding pb, Timestamp d, int time) {
		pb.setUpdateTime(updateDataTime(d, time));
	}

	/**
	 * 修改建造升级时间
	 * 
	 * @param pb
	 * @param d
	 * @param time
	 */
	public void updateConstructionTime(PlayerBuilding pb, Timestamp d, int time) {
		pb.setConstructionTime(updateDataTime(d, time));

	}

	/**
	 * 初始 player_building 对象
	 * 
	 * @param building
	 *            建筑
	 * @param id
	 *            坐标ID
	 * @param oId
	 *            playerBuinging 主键
	 * @param completeDate
	 *            建造完成时间
	 * @return
	 */
	public PlayerBuilding loadObject(int obId, short level) {
		// logger.info("-->>>>>>>>>>>>>>>>>level>>" + level
		// + "<<<<<<<<<<<<<<<<<<<<<<<<");
		// logger.info("-->>>>>>>>>>>>>>>>>buildId>>" + obId
		// + "<<<<<<<<<<<<<<<<<<<<<<<<");
		OriginalBuilding ob = bldMgr.getOriginalBuilding(obId);
		Building b = bldMgr.getBuilding(ob.getBuildId());
		if (b != null) {
			BuildingUpGrade bl = bldMgr.getBuindingLevel(b.getId(), level);

			PlayerBuilding playerbuilding = new PlayerBuilding();
			long now = TimeUtils.nowLong();
			// level 默认为一
			playerbuilding.setBuildLevel(level);
			playerbuilding.setRemark1("");
			playerbuilding.setRemark2("");
			playerbuilding.setRemark3("");
			playerbuilding.setBuildingID(b.getId());
			playerbuilding.setUserID((int) player.getData().getUserid());
			playerbuilding.setIsOccupy(1);
			playerbuilding.setCategory(b.getCategory());
			playerbuilding.setX(ob.getX());
			playerbuilding.setY(ob.getY());
			playerbuilding.setPrice(b.getPrice());
			playerbuilding.setHonerPrice(b.getHonerPrice());
			// 收益时间
			if (ob.getBuildId() == GameConst.JIUGUAN_ID) {
				playerbuilding.setUpdateTime(TimeUtils.addSecond(now,
						bl.getPeriod()));
			} else if (ob.getBuildId() == GameConst.BLACKSMITHY_ID) {
				playerbuilding.setUpdateTime(TimeUtils.addSecond(0, 0));
			} else if (ob.getBuildId() == GameConst.FUSHU_CITY_ID || bl == null) {
				playerbuilding.setUpdateTime(TimeUtils.addSecond(now, 0));
			} else {
				playerbuilding.setUpdateTime(TimeUtils.addSecond(now,
						bl.getPeriod()));
			}
			// 收益数量
			if (ob.getBuildId() == GameConst.JIUGUAN_ID
					|| ob.getBuildId() == GameConst.FUSHU_CITY_ID || bl == null) {
				playerbuilding.setInComeCount(0);
			} else {
				playerbuilding.setInComeCount(bl.getCount());
			}

			// 建造中
			playerbuilding.setBuildType(DEFALUT_BUILD);
			// 建造时间
			playerbuilding.setConstructionTime(TimeUtils.addSecond(now,
					-100 * 60 * 60));
			playerbuilding.setIsUnique(b.getIsUnique());
			playerbuilding.setCanBeDestroyed(b.getCanBeDestroyed());
			playerbuilding.setIsLevelUp(b.getIsLevelUp());
			if (ob.getBuildId() == GameConst.JIUGUAN_ID
					|| ob.getBuildId() == GameConst.FUSHU_CITY_ID || bl == null) {
				playerbuilding.setInCometype((byte) 0);
			} else {
				playerbuilding.setInCometype(bl.getType());
			}

			// 默认0
			playerbuilding.setOfficerId(0);
			playerbuilding.setOfficerInfo("");
			playerbuilding.setOccupyUserId(0);
			playerbuilding.setPropsId(0);
			playerbuilding.setOperatcount(0);
			if (b.getId() == GameConst.TRAINING_ID) {
				playerbuilding.setOperatcount(3584);// 默认开放前3位
			}
			// 默认建造完成时间
			playerbuilding.setChargeOutTime(TimeUtils.addSecond(now, 0));
			playerbuilding.setStealTime(TimeUtils.addSecond(now, 0));
			playerbuilding.setOfficerTime(TimeUtils.addSecond(now, 0));
			if (ob.getBuildId() == GameConst.BARRACK_ID) {
				playerbuilding.setSoldierMsg(getsoliderInit());
			}

			// // -- >>>>>>>>>>>转载对象
			return playerbuilding;
		}
		return null;
	}

	/**
	 * 是否有在升级的建筑
	 */
	public void isOnBuilding() {
		/** =========修改建筑序列 */
		int x = 0;
		for (PlayerBuilding pp : playerBuildDatas.values()) {
			if (pp.getBuildType() == PlayerBuildingManager.LEVELUP_BUILD) {
				x += 1;
			}
		}
		if (x > 0) {
			setBuilding(false);
		} else {
			setBuilding(true);
		}
		/** =========修改建筑序列 */
	}

	/**
	 * 更新状态建造 为已完成
	 * 
	 * @param pid
	 * @param type
	 * @return
	 */
	public TipUtil changeBuildingType(int pid, byte type) {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setFailTip("");
		PlayerBuilding p = getPlayerBuild(pid);
		// logger.info("~~~player:" + player.getData().getUserid()
		// + "~~~~mainCity:" + p);
		if (p == null) {
			return tip.setFailTip("playerBuilding is null");
		}
		int now = (int) ((TimeUtils.nowLong()) / 1000);
		int completeDate = (int) ((p.getConstructionTime().getTime()) / 1000);
		// logger.info("升级建筑>>>>>>>>>>>>现在时间：" + now + "|结束时间：" + completeDate);
		if (p.getBuildType() == LEVELUP_BUILD && now >= completeDate - 2) {
			p.setBuildType(COMPLETE_LEVELUP);
			// logger.info(p.getId() + " 建筑:" + p.getBuildingID() + "|等级:"
			// + p.getBuildLevel());
			p.setBuildLevel((short) (p.getBuildLevel() + 1));
			// logger.info(p.getId() + " 建筑:" + p.getBuildingID() + "|等级:"
			// + p.getBuildLevel());
			// setBuilding(true);
			isOnBuilding();
			// logger.info("修改用户建筑状态:" + player.getId());
			// logger.info("升级用户经验增加：" + p.getExp());
			FightLog.info("++++升级完成++++++++用户："+player.getId()+"|建筑："+p.getBuildingID()+"|等级升级:"+p.getBuildLevel()+"|经验："+p.getExp());
			player.saveExp(p.getExp());
//			player.saveData();// 保存用户数据
			p.setExp(0);// 经验制为 0
			// 修改收取时间
			BuildingUpGrade bu1 = bldMgr.getBuindingLevel(p.getBuildingID(),
					p.getBuildLevel());
			BuildingUpGrade bu = bldMgr.getBuindingLevel(p.getBuildingID(),
					(short) p.getOldLevel());
			if (bu1 != null && bu != null) {
				int updateT = bu1 == null ? 0 : bu1.getPeriod();
				int updateold = bu == null ? 0 : bu.getPeriod();
				p.setUpdateTime(TimeUtils.addSecond(p.getUpdateTime()
						.getTime(), updateT - updateold));
				p.setOldLevel(p.getBuildLevel());
			}
			// 修改收取时间
			// 跟新进数据库
//			savePlayerBuilding(p);
			QuestUtils.checkFinish(player, QuestUtils.TYPE3, true, p.getBuildingID(),
					p.getBuildLevel());
			// rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms.addModule(player.getData());

			rms.addModule(p);
			AndroidMessageSender.sendMessage(rms, player);
			// rms
			return tip.setSuccTip("");
		}
		return tip;
	}

	/**
	 * 建筑可以升级
	 * 
	 * @param buildingId
	 * @return
	 */
	public int[] levelUp(int pId) {
		StringBuffer sb = new StringBuffer();
		// 加载下延时建筑数据
		getdelayAdd((byte) BUILD_QUEUE_TYPE);
		if (isBuilding() || isDelay()) {
			// 建筑map为空
			if (checkbuildDatas() || pmc == null) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(new TipMessage("Upgrade failed",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							player.getUserInfo(), GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("获取主城失败",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							player.getUserInfo(), GameUtils.FLUTTER);
				}
				
				return new int[] { 0, 0, 0 };
			}
			PlayerBuilding playerBuilding = playerBuildDatas.get(pId);
			PlayerBuilding mainBuild = pmc.getMainCity();
			// 我的建筑中没有这个ID建筑
			if (playerBuilding == null || mainBuild == null
					|| playerBuilding.getBuildType() == LEVELUP_BUILD) {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					GameUtils.sendTip(new TipMessage("Upgrading.",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							player.getUserInfo(), GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("正在升级",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							player.getUserInfo(), GameUtils.FLUTTER);
				}
				
				return new int[] { 0, 0, 0 };
			}
			Building building = getBuilding(playerBuilding.getBuildingID());
			// 检验升级条件
			BuildingUpGrade bu = checkCondition(playerBuilding);
			if (bu == null) {
				return new int[] { 0, 0, 0 };
			}
			// 建筑是否可以升级
			if (building != null
					&& playerBuilding.getIsLevelUp() == 1
					&& player.getData().getLevel() > building
							.getLevelRequired()) {// 开启等级
				if (playerBuilding.getBuildingID() == GameConst.MAINCITY_ID) {
					return pmc.levelUp();// 调用主城管理类
				}
				// 需要升级建筑 等级
				Short buildLevel = getBuildLevel(building.getId());
				// 建筑等级必须小于主城等级
				if (buildLevel < mainBuild.getBuildLevel()) {
					if (buildLevel + 1 > mainBuild.getBuildLevel()
							&& TimeUtils.nowLong() <= mainBuild
									.getConstructionTime().getTime()) {
						return new int[] { 0, 0, 0 };
					}
					if (player.saveResources(GameConfig.GAME_MONEY,
							bu.getGold() * -1) == -1) {// 金币不足

						return new int[] { 0, 0, 0 };
					}
					// 更新数据库和缓存对应信息
					Long now = TimeUtils.nowLong();
					// 升级时间
					playerBuilding.setConstructionTime(TimeUtils.addSecond(
							now, bu.getTime()));
					playerBuilding.setBuildType(LEVELUP_BUILD);
					// 产出类型
					playerBuilding.setInCometype(bu.getType());
					playerBuilding.setInComeCount(bu.getCount());

					// logger.info("升级产生经验：" + bu.getExp());
					sb.append("用户：" + player.getId()).append(
							">>>升级产生经验：" + bu.getExp());
					playerBuilding.setExp(bu.getExp());// 设置经验
					// 进入数据库
//					savePlayerBuilding(playerBuilding);
					FightLog.info("升级：+++++++用户："+player.getId()+"|建筑："+playerBuilding.getBuildingID()+"|等级升级:"+playerBuilding.getBuildLevel()+"| 设置经验："+bu.getExp());
					sb.append(">>>建筑产生经验：" + playerBuilding.getExp());
					logger.info(sb.toString());
					// RoleData role = player.getData();
					// player.saveResources(GameConfig.GAME_MONEY, bu.getGold()
					// * -1);
					// player.setData(role);
//					player.saveData();
					// 设置 是否能升级
					setBuilding(false);
					/*
					 * GameUtils.sendTip(new TipMessage("开始升级",
					 * ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_SUCCESS),
					 * player.getUserInfo());
					 */
					return new int[] { 1, pId, bu.getGold() * -1 };
				}
				if(I18nGreeting.LANLANGUAGE_TIPS ==1){
					GameUtils.sendTip(new TipMessage("Upgrade failed.Upgrade your Capitol's level first.",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							player.getUserInfo(), GameUtils.FLUTTER);
				}else{
					GameUtils.sendTip(new TipMessage("超过主城等级,无法升级",
							ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
							player.getUserInfo(), GameUtils.FLUTTER);
				}
				
				return new int[] { 0, 0, 0 };
			}
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				GameUtils.sendTip(new TipMessage("It has reached the highest level.",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
						player.getUserInfo(), GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("超过开放等级,无法升级",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
						player.getUserInfo(), GameUtils.FLUTTER);
			}
			return new int[] { 0, 0, 0 };
		} else {
			String msg = I18nGreeting.getInstance().getMessage(
					"building.levelup", new Object[] {});
			GameUtils.sendTip(new TipMessage(msg, ProcotolType.BUILDING_RESP,
					GameConst.GAME_RESP_FAIL), player.getUserInfo(),
					GameUtils.FLUTTER);
			return new int[] { -1, 0, 0 };// 建筑队列
		}
	}

	/**
	 * 使用道具
	 */
	public TipUtil userluBan(int playerBuildingid, Props props) {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setFailTip("使用失败");
		// ****** rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
		PlayerBuilding playerBuilding = playerBuildDatas.get(playerBuildingid);
		if (playerBuilding != null) {
			byte type = props.getPrototype().getPropsType();
			switch (type) {
			case ItemConst.LUBAN_TYPE:// 建造时间
				if (playerBuilding.getConstructionTime().getTime() > TimeUtils.nowLong()
						&& playerBuilding.getBuildType() == LEVELUP_BUILD) {
					tip = player.getPlayerStorageAgent().getPis()
							.userProps(props.getId(), false);
					if (!tip.isResult()) {
						return tip;
					}
					playerBuilding.setConstructionTime(TimeUtils.addSecond(
							playerBuilding.getConstructionTime().getTime(),
							-1
									* Integer.parseInt(props.getPrototype()
											.getProperty3())));
//					savePlayerBuilding(playerBuilding);
					long now = TimeUtils.nowLong();
					long completeDate = playerBuilding.getConstructionTime()
							.getTime();

					if (playerBuilding.getBuildType() == LEVELUP_BUILD
							&& now >= completeDate) {
						changeBuildingType(playerBuildingid, COMPLETE_LEVELUP);
					} else {
						rms.addModule(playerBuildDatas.get(playerBuildingid));
					}

					tip.setSuccTip("");
					break;
				} else {
					return tip;
				}
			case ItemConst.ADD_HORSE_TYPE:// 马
				if (playerBuilding.getCategory() == 1
						&& !isMian(playerBuilding.getBuildingID())
						&& playerBuilding.getInCometype() == HORSE) {
					tip = player.getPlayerStorageAgent().getPis()
							.userProps(props.getId(), false);
					if (!tip.isResult()) {
						return tip;
					}
					PropsDelay delay = new PropsDelay();
					delay.setEndTime((int) (TimeUtils.nowLong() / 1000)
							+ Integer.parseInt(props.getPrototype()
									.getProperty3()));
					delay.setPropsId(props.getId());
					delay.setPropsType(props.getPrototype().getPropsType());
					delay.setAdditionCount(Integer.parseInt(props
							.getPrototype().getProperty2()));
					delay.setUserId((int) player.getData().getUserid());
					PropsDelay pd = player.getPlayerStorageAgent().getPis()
							.addDelay(delay);
					rms.addModule(pd);
					tip.setSuccTip("");

				} else {
					tip.setFailTip("建筑类型错误");
				}
				break;
			case ItemConst.ADD_IRON_TYPE:// 铁
				if (playerBuilding.getCategory() == 1
						&& !isMian(playerBuilding.getBuildingID())
						&& playerBuilding.getInCometype() == IRON) {
					tip = player.getPlayerStorageAgent().getPis()
							.userProps(props.getId(), false);
					if (!tip.isResult()) {
						return tip;
					}
					PropsDelay delay = new PropsDelay();
					delay.setEndTime((int) (TimeUtils.nowLong() / 1000)
							+ Integer.parseInt(props.getPrototype()
									.getProperty3()));
					delay.setPropsId(props.getId());
					delay.setPropsType(props.getPrototype().getPropsType());
					delay.setAdditionCount(Integer.parseInt(props
							.getPrototype().getProperty2()));
					delay.setUserId((int) player.getData().getUserid());
					PropsDelay pd = player.getPlayerStorageAgent().getPis()
							.addDelay(delay);

					tip.setSuccTip("");
					rms.addModule(pd);
				} else {
					tip.setFailTip("建筑类型错误");
				}
				break;
			case ItemConst.ADD_WOOD_TYPE:// 木材
				if (playerBuilding.getCategory() == 1
						&& !isMian(playerBuilding.getBuildingID())
						&& playerBuilding.getInCometype() == WOOD) {
					tip = player.getPlayerStorageAgent().getPis()
							.userProps(props.getId(), false);
					if (!tip.isResult()) {
						return tip;
					}
					PropsDelay delay = new PropsDelay();
					delay.setEndTime((int) (TimeUtils.nowLong() / 1000)
							+ Integer.parseInt(props.getPrototype()
									.getProperty3()));
					delay.setPropsId(props.getId());
					delay.setPropsType(props.getPrototype().getPropsType());
					delay.setAdditionCount(Integer.parseInt(props
							.getPrototype().getProperty2()));
					delay.setUserId((int) player.getData().getUserid());
					PropsDelay pd = player.getPlayerStorageAgent().getPis()
							.addDelay(delay);
					tip.setSuccTip("");
					rms.addModule(pd);
				} else {
					tip.setFailTip("建筑类型错误");
				}
				break;
			case ItemConst.ADD_GOLD_TYPE:// 金币增加
				if (isMian(playerBuilding.getBuildingID())) {
					tip = player.getPlayerStorageAgent().getPis()
							.userProps(props.getId(), false);
					if (!tip.isResult()) {
						return tip;
					}
					// playerBuilding.setAdditionCountTime(Integer.parseInt(props.getPrototype().getProperty2()));
					// Integer.parseInt(props.getPrototype().getProperty3())));
					PropsDelay delay = new PropsDelay();
					delay.setEndTime((int) (TimeUtils.nowLong() / 1000)
							+ Integer.parseInt(props.getPrototype()
									.getProperty3()));

					delay.setPropsId(props.getId());
					delay.setPropsType(props.getPrototype().getPropsType());
					delay.setAdditionCount(Integer.parseInt(props
							.getPrototype().getProperty2()));
					delay.setUserId((int) player.getData().getUserid());
					PropsDelay pd = player.getPlayerStorageAgent().getPis()
							.addDelay(delay);
					tip.setSuccTip("");
					rms.addModule(pd);
				} else {
					tip.setFailTip("建筑类型错误");
				}
				break;
			}
			// playerBuilding.setConstructionTime(CalendarUtil.addSecond(playerBuilding.getConstructionTime().getTime(),
			// -1* Integer.parseInt(props.getPrototype().getProperty2())));

			rms.addModuleBase(tip.getLst());
			AndroidMessageSender.sendMessage(rms, player);
			// ****** rms
			return tip;
		} else if (props.getPrototype().getPropsType() == ItemConst.BUILD_QUEUE_TYPE) {
			tip = player.getPlayerStorageAgent().getPis()
					.userProps(props.getId(), false);
			if (!tip.isResult()) {
				return tip;
			} else {
				PropsDelay delayQUEUE = player.getPlayerStorageAgent().getPis()
						.getDelay(ItemConst.BUILD_QUEUE_TYPE);
				long end = 0;
				if (delayQUEUE == null) {
					PropsDelay delay = new PropsDelay();
					delay.setEndTime((int) (TimeUtils.nowLong() / 1000)
					// + 60 * 60);
							+ Integer.parseInt(props.getPrototype()
									.getProperty3()));
					delay.setPropsId(props.getId());
					delay.setPropsType(props.getPrototype().getPropsType());
					delay.setUserId((int) player.getData().getUserid());
					PropsDelay pd = player.getPlayerStorageAgent().getPis()
							.addDelay(delay);
					setDelay(true);
					rms.addModule(pd);
					rms.addModuleBase(tip.getLst());
					end = (long) (delay.getEndTime()) * 1000;
				} else {
					delayQUEUE.setEndTime(delayQUEUE.getEndTime()
							+ Integer.parseInt(props.getPrototype()
									.getProperty3()));
					player.getPlayerStorageAgent().getPis()
							.saveDelay(delayQUEUE, player.getId());
					setDelay(true);
					rms.addModule(delayQUEUE);
					rms.addModuleBase(tip.getLst());
					end = (long) (delayQUEUE.getEndTime()) * 1000;
				}
				AndroidMessageSender.sendMessage(rms, player);
				String str = "使用成功，有效时间至" +TimeUtils.chDate(end);
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					str = "OK. Item effect is valid to" + TimeUtils.enDate(end);
				}
				tip.setSuccTip(str);
				return tip;
			}
		} else {
			return tip;
		}
	}

	/**
	 * 驻防/占领
	 */
	public TipUtil guard(boolean isSucc, int heroId, String soMsg,
			int beingAttackedId, String backMsg, FightEvent fightEvent1,
			FightEvent fightEvent2, byte wintype, int defHero) {
		StringBuffer sb = new StringBuffer("\n----------guard---------");
		sb.append("\n guard :" + isSucc + "|攻击用户：" + player.getId() + "|攻击将领:"
				+ heroId + "|攻击士兵:" + soMsg + "|属于玩家：" + beingAttackedId
				+ "|防御将领:" + defHero + "|防御士兵:" + backMsg + "\n");
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);//
		PlayerCharacter def = World.getInstance().getPlayer(beingAttackedId);
		if (pmc == null || pma == null) {
			tip.setFailTip("控制类对象为空");
			sb.append("控制类对象为空 \n");
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				GameUtils.sendTip(new TipMessage("Garrison failed.",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
						player.getUserInfo(), GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("驻防失败",
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
						player.getUserInfo(), GameUtils.FLUTTER);
			}
			
			return tip;
		} else {
			if (beingAttackedId == player.getData().getUserid()) {
				sb.append("======1=========\n");
				if (pmc.getMainCity() != null && pmc.getMainCity().getIsOccupy() == 1) {
					if (pmc.getMainCity().getOccupyUserId() == 0 ) {// 打怪物,无人驻防
						if(pmc.getMainCity().getOfficerId() == 0 ){//自己已占领
							if (heroId > 0) {
								tip = pmc.presence(heroId, soMsg, fightEvent1);// 驻防成功
								sb.append(tip.getStr() + "\n");
							}
						}else{
							if(I18nGreeting.LANLANGUAGE_TIPS ==1){
								return tip.setFailTip("You've garrisoned it.");
							}else{
								return tip.setFailTip("自己已驻防");
							}
							
						}
					} else {
						//收复
						tip = pmc.recover(isSucc, heroId, soMsg, backMsg,
								fightEvent1, fightEvent2);
						sb.append(tip.getStr() + "\n");
						GameUtils.sendTip(tip.getTip(), player.getUserInfo(),
								GameUtils.FLUTTER);
					}
				}

				// return tip;
			} else if (beingAttackedId != player.getData().getUserid()) {
				sb.append("======2=========\n");
				String m1 = "";
				String m2 ="";
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					m1 = "Garrsion in ";
					m2 ="'s Capitol";
				}else{
					m1 = "驻守";
					m2 ="主城 ";
				}
				tip = presenceFu(isSucc, heroId, soMsg, beingAttackedId, m1
						+ def.getData().getName() + m2, backMsg, fightEvent1,
						fightEvent2, defHero);
				sb.append(tip.getStr() + "\n");
				World.getInstance().savePlayer(player);
				GameUtils.sendTip(tip.getTip(), player.getUserInfo(),
						GameUtils.FLUTTER);
				// return tip;
			}
			QuestUtils.checkFinish(player, QuestUtils.TYPE4, true,
					FightConst.FIGHTBATTLE_GUARD, 0);
			QuestUtils.checkFinish(player, QuestUtils.TYPE5, true,
					FightConst.FIGHTBATTLE_GUARD, 0, wintype);
			QuestUtils.checkFinish(player, QuestUtils.TYPE30, true);
			logger.info(sb.toString());
			FightLog.info(sb.toString());
			return tip;
		}
	}

	// 获取主城
	public PlayerBuilding getMainCity() {
		if (pmc == null)
			return null;
		else {
			return pmc.getMainCity();
		}
	}

	/**
	 * 资源建筑
	 * 
	 * @return
	 */
	public List<PlayerBuilding> getAllResource() {
		List<PlayerBuilding> lst = new ArrayList<PlayerBuilding>();
		if (playerBuildDatas == null || playerBuildDatas.size() == 0) {
			return lst;
		} else {
			for (PlayerBuilding pp : playerBuildDatas.values()) {
				if (pp.getCategory() == 1) {
					lst.add(pp);
				}
			}
			return lst;
		}
	}

	/**
	 * 所有可升级建筑
	 * 
	 * @return
	 */
	public List<PlayerBuilding> getAllUpgrade() {

		List<PlayerBuilding> lst = new ArrayList<PlayerBuilding>();
		if (playerBuildDatas == null || playerBuildDatas.size() == 0) {
			return lst;
		} else {
			for (PlayerBuilding pp : playerBuildDatas.values()) {
				System.out.println(Arrays.asList(upgrade));
				if ((Arrays.asList(upgrade)).contains(pp.getBuildingID())) {
					lst.add(pp);
				}
			}
			return lst;
		}
	}

	/**
	 * 占领附属城
	 * 
	 * @param isSucc
	 * @param attHeroid
	 *            攻击英雄
	 * @param soMsg
	 *            攻击士兵
	 * @param having
	 *            拥有玩家
	 * @param defhero
	 *            防御将领
	 * @param ssMsg
	 *            防御士兵
	 * @param memo
	 *            将领说明
	 * @param havaUserId
	 *            拥有玩家
	 * @param fightEvent1
	 * @param fightEvent2
	 * @return
	 */
	public TipUtil presenceFu(boolean isSucc, int attHeroId, String soMsg,
			int having, String memo, String ssMsg, FightEvent fightEvent1,
			FightEvent fightEvent2, int defhero) {

		if (pma == null)
			return new TipUtil(ProcotolType.BUILDING_RESP).setFailTip("fail");
		else {
			return pma.presence(isSucc, attHeroId, soMsg, having, ssMsg, memo,
					fightEvent1, fightEvent2, defhero);
		}
	}

	// /**
	// * 主城被收复
	// */
	// public TipUtil recover(int heroId, String msg, String backMsg) {
	// if(pmc != null)
	// return pmc.recover(heroId,msg,backMsg);
	// return new TipUtil(ProcotolType.BUILDING_RESP).setFailTip("fail");
	// }
	/**
	 * 撤防/将领id
	 */
	public TipUtil disarm(PlayerHero ph, String soMsg) {
		int heroId=ph.getId();
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setFailTip("不能撤防");
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			tip.setFailTip("Withdraw failed.");
		}
		
		if (ph == null || ph.getStatus() == GameConst.HEROSTATUS_IDEL) {
			tip.setFailTip("将领状态不对,不能撤防");
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				tip.setFailTip("Withdraw failed.");
			}
			return tip;
		}
		if (pmc != null && pmc.getMainCity().getOfficerId() == heroId) {
			TipUtil myTip = pmc.withdrawal(soMsg, true);
			if (myTip != null) {
				tip.setSuccTip(myTip.getResultMsg());
			} else {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("Withdraw failed.");
				}else{
					tip.setFailTip("撤防失败");
				}
				
			}
			return tip;
		} else if (ph != null) {
			// 附属称
			if (pma != null && ph.getStatus() == GameConst.HEROSTATUS_ZHUFANG) {
				List<PlayerBuilding> afflst = pma.afflst2();
				boolean isAT = false;
				for (PlayerBuilding pa : afflst) {
					if (pa.getOfficerId() == heroId) {
						synchronized (pa) {
							tip = pma.withdrawal((byte) 0, heroId, soMsg);
							isAT = true;
						}
						
					}
				}
				if(!isAT){
					PlayerHero playerhero = player.getPlayerHeroManager().getHero(heroId);
					if(playerhero != null && playerhero.getStatus() == GameConst.HEROSTATUS_ZHUFANG){
						player.getPlayerHeroManager().motifyStatus(heroId,
								GameConst.HEROSTATUS_IDEL, "", "", 0);
						tip.setSuccTip("");
					}
				}
			}
			if (ph.getStatus() == GameConst.HEROSTATUS_ZHUFANG_VEINS) {
				// 科技撤防
				for (Veins v : naMgr.getAllVeins(player.getStateId())) {
					if (v.getHeroId() == heroId) {
						synchronized (v) {
							tip = v.disarm();
						}
						
					}
				}
			}

			if (ph.getStatus() == GameConst.HEROSTATUS_ZHUFANG_GOLDMINE) {
				// 金矿撤防
				for (GoldMine gold : naMgr.goldMap.get(player.getStateId())) {
					if (gold.getHeroId() == heroId) {
						synchronized (gold) {
							tip = gold.retreat(gold.getSoMsg(), (byte) 0, (byte) 0);
						}
					}
				}
			}

			if (ph.getStatus() == GameConst.HEROSTATUS_ZHUFANG_COUNTY) {
				// 县城撤防
				Nation nation = naMgr.nationMap.get(player.getData()
						.getNativeId());
				if (nation.getHeroId() == heroId) {
					synchronized (nation) {
						tip = nation.retreatNation(0, false);
					}
				}
			}
			if (ph.getStatus() == GameConst.HEROSTATUS_ARENA) {
				int rankId = player.getData().getArenaId();
				Arena arena = GameDataManager.arenaManager
						.getArena((short) rankId);
				if (arena == null||!arena.standUp()) {
					tip.setFailTip("竞技场错误，playerId=" + player.getId()
							+ " 当前排名=" + rankId);
					return tip;
				}
				if (arena.getUserId() == player.getData().getUserid()) {
					synchronized (arena) {
						arena.standUp();
					}
					// tip.setFailTip("将领撤出竞技场成功，heroId=" + arena.getHeroId());
					tip.setFailTip("将领撤出竞技场成功");
					if(I18nGreeting.LANLANGUAGE_TIPS ==1){
						tip.setFailTip("");
					}
					
				}else{
					logger.info("竞技场撤出错误，uid="+player.getId()+" heroId="+ph.getId()+" arenaId="+arena.getId());
					player.getPlayerHeroManager().motifyStatus(ph.getId(),GameConst.HEROSTATUS_IDEL,"","",0);
					player.getData().setArenaId(0);
				}

				arena.standUp();
				tip.setSuccTip("撤防成功");

			}

		}
		// ************************ rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
		rms.addModule(player.getData());
		AndroidMessageSender.sendMessage(rms, player);
		// ************************ rms
		return tip;
	}

	public void disarmMyAll(int stateId) {
		// 科技撤防
		logger.info("************:" + stateId);
		logger.info("=========" + naMgr.getAllVeins(stateId).toString());
		if (null != naMgr.getAllVeins(stateId)) {
			for (Veins v : naMgr.getAllVeins(stateId)) {
				if (v != null && v.getUserId() == player.getId()) {
					logger.info("我的科技：" + player.getId() + "|v id :="
							+ v.getId());
					v.disarm();
				}
			}
		}

		// 金矿撤防
		logger.info("=========" + naMgr.goldMap.get(stateId).toString());
		if (null != naMgr.goldMap.get(stateId)) {
			for (GoldMine gold : naMgr.goldMap.get(stateId)) {
				if (gold != null && gold.getUserId() == player.getId()) {
					logger.info("=====" + gold.toString());
					logger.info("我的金矿：" + player.getId() + "|gold id :="
							+ gold.getId());
					gold.retreat(gold.getSoMsg(), (byte) 0, (byte) 1);
				}
			}
		}
	}

	/**
	 * 主城被占领
	 */
	public TipUtil occupiedMainCity(boolean issucc, int userId, int heroId,
			String soMsg, String ssMsg, PlayerBuilding aff,
			FightEvent fightEvent1, FightEvent fightEvent2) {
		if (pmc != null) {
			return pmc.occupiedMainCity(issucc, userId, heroId, soMsg, ssMsg,
					aff, fightEvent1, fightEvent2);
		}
		return new TipUtil(ProcotolType.BUILDING_RESP).setFailTip("fail");
	}

	public int getdelayAdd(byte key) {
		switch (key) {
		case HORSE:
			PropsDelay delay = player.getPlayerStorageAgent().getPis()
					.getDelay(ItemConst.ADD_HORSE_TYPE);
			if (delay != null) {
				return delay.getAdditionCount();
			} else {
				return 0;
			}
		case IRON:
			PropsDelay delay1 = player.getPlayerStorageAgent().getPis()
					.getDelay(ItemConst.ADD_IRON_TYPE);
			if (delay1 != null) {
				return delay1.getAdditionCount();
			} else {
				return 0;
			}
		case WOOD:
			PropsDelay delay2 = player.getPlayerStorageAgent().getPis()
					.getDelay(ItemConst.ADD_WOOD_TYPE);
			if (delay2 != null) {
				return delay2.getAdditionCount();
			} else {
				return 0;
			}
		case GOLD:
			PropsDelay delay3 = player.getPlayerStorageAgent().getPis()
					.getDelay(ItemConst.ADD_GOLD_TYPE);
			if (delay3 != null) {
				return delay3.getAdditionCount();
			} else {
				return 0;
			}
		case BUILD_QUEUE_TYPE:// 是否存在建筑队列
			PropsDelay delay4 = player.getPlayerStorageAgent().getPis()
					.getDelay(ItemConst.BUILD_QUEUE_TYPE);
			if (delay4 != null) {
				return delay4.getPropsId();
			} else {
				return 0;
			}
		default:
			return 0;
		}
	}

	/**
	 * 建筑产出详细
	 * 
	 * @param pid
	 */
	public int[] getdetailCharge(int pid) {
		if (checkbuildDatas()) {
			// logger.info(">>>建筑map不存在");
			return new int[] { 0, 0, 0, 0, 0 };
		}
		PlayerBuilding p = playerBuildDatas.get(pid);
		if (p == null) {
			// logger.info(">>>建筑不存在");
			return new int[] { 0, 0, 0, 0, 0 };
		} else {
			BuildingUpGrade bu = bldMgr.getBuindingLevel(p.getBuildingID(),
					(short) p.getOldLevel());
			if (bu == null) {
				// logger.info(">>>建筑升级数据不存在");
				return new int[] { 0, 0, 0, 0, 0 };
			}
			int fu = 0;// 附属
			int occmain = 0;// 主城
			int delay = 0;// 道具
			int all = 0;// 全部
			if (p.getBuildingID() == GameConst.MAINCITY_ID) {
				if (p.getOccupyUserId() != 0) {
					occmain = (int) (-1 * bu.getCount() * 0.5);
					fu = getAddition();// 附属加成数量
				} else {
					fu = getAddition();// 附属加成数量
					if (p.getOfficerId() != 0) {
						occmain = (int) (bu.getCount() * 0.5);
					}
				}
				delay = (int) (bu.getCount() * getdelayAdd(p.getInCometype()) / 100);
				all = bu.getCount() + fu + occmain + delay;
				return new int[] { bu.getCount(), fu, delay, occmain, all };
				// return bu.getCount()+","+fu+","+delay+","+occmain+","+all;
			} else {
				delay = (int) (bu.getCount() * getdelayAdd(p.getInCometype()) / 100);
				all = bu.getCount() + delay;
				return new int[] { bu.getCount(), fu, delay, occmain, all };
			}
		}
	}

	/**
	 * 获取收益数量
	 * 
	 * @param pId
	 * @param type
	 * @return
	 */
	public int[] getCharge(int pId, byte type) {
		if (checkbuildDatas()) {
			return new int[] { -1, 0, -1 };
		}
		PlayerBuilding p = playerBuildDatas.get(pId);
		if (p == null)
			return new int[] { -1, 0, -1 };
		BuildingUpGrade bu = bldMgr.getBuindingLevel(p.getBuildingID(),
				(short) p.getBuildLevel());
		BuildingUpGrade bu1 = bldMgr.getBuindingLevel(p.getBuildingID(),
				p.getBuildLevel());
		int updateT = bu1 == null ? 0 : bu1.getPeriod();
		if (bu == null)
			return new int[] { -1, 0, p.getBuildingID() };
		int period = bu.getPeriod();// 间隔时间
		// int additionCount = 0;
		// if (p.getBuildingID() == GameConst.MAINCITY_ID) {
		// additionCount = getAddition();// 附属加成数量
		// logger.info("主城加成数量:" + additionCount);
		// p.setAdditionCount(additionCount);
		// // 驻防
		// if (p.getOccupyUserId() == 0 && p.getOfficerId() != 0) {//自己加成数量
		// int a = bu.getCount() / 2;
		// logger.info("驻防加成数量:" + a);
		// additionCount = additionCount + a;
		// p.setAdditionCount(additionCount + a);
		// }
		// }
		// // 其他加成数量
		// if (p.getAdditionCountTime() != 0) {
		// .getTime())
		// p.setAdditionCountTime(0);
		// }
		// int additionCountTime = (int) (bu.getCount()
		// * getdelayAdd(p.getInCometype()) / 100);
		int[] all = getdetailCharge(pId);
		// int oneCount = all[0]+all[1]+all[2]+all[3];
		int number = 0;
		if (period == 0) {
			return new int[] { -1, 0, p.getBuildingID() };
		}
		if (type == CHARGE_OUT) {// 收取
			// int del = 0;
			number = (int) Math
					.floor((TimeUtils.nowLong() - p
							.getChargeOutTime().getTime()) / (period * 1000));
			if (number > 10)
				number = 10;
			if (number == 0) {
				return new int[] { -1, 0, p.getBuildingID() };
			}
			// if (p.getBuildingID() == GameConst.MAINCITY_ID) {
			// if (p.getOccupyUserId() != 0) {
			// del = (int) (bu.getCount() * 0.5);
			// }
			// }
			// 设置收益时间
			Timestamp t = TimeUtils.addSecond(TimeUtils.nowLong(), 0);
			p.setChargeOutTime(t);
			p.setUpdateTime(TimeUtils.addSecond(t.getTime(), updateT));
			p.setOldLevel(p.getBuildLevel());
//			savePlayerBuilding(p);
			int income = (int) Math.floor((all[4]) * number);
			// - p.getStealCount();
			if (income < 0)
				return new int[] { -1, 0, p.getBuildingID() };
			return new int[] { bu.getType(), income, p.getBuildingID() };
		} else if (type == CHARGE_STEAL)// 偷盗
		{
			if (TimeUtils.nowLong()
					- p.getStealTime().getTime() >= 0) {
				number = (int) Math.floor((TimeUtils.nowLong() - p.getChargeOutTime().getTime())
						/ (period * 1000));
				if (number > 10)
					number = 10;
				int steal = (int) Math.floor((all[4]) * number
						* bu.getPlunderCount());// 偷盗数量
				steal = steal == 0 ? 1 : steal;//
				// 设置偷盗时间
				p.setStealTime(TimeUtils.addSecond(TimeUtils.nowLong(), 4 * 60 * 60));// 超过四小时才可以偷盗
				p.setStealCount(p.getStealCount() + steal);// 设置偷盗数量
//				savePlayerBuilding(p);
				return new int[] { bu.getType(), steal, p.getBuildingID() };
			} else {
				return new int[] { -1, 0, p.getBuildingID() };
			}
		}
		return new int[] { -1, 0, p.getBuildingID() };
	}

	/**
	 * 建筑物收取产出
	 * 
	 * @param pId
	 * @return
	 */
	public int[] chargeOut(int pId) {
		PlayerBuilding p = playerBuildDatas.get(pId);
		if (p == null) {
			return new int[] { -1, 0, 0 };
		}
		// 结束升级
		changeBuildingType(pId, COMPLETE_LEVELUP);
		int[] baseCount = getCharge(pId, CHARGE_OUT);
		// 用户根据类型加入对应数据
		if (baseCount[0] != -1) {
			// logger.info("保存用户资源信息：" + " 类型 =" + baseCount[0] + "| 数量="
			// + baseCount[1]);
			Props props = null;
			switch ((byte) baseCount[0]) {
			case 0:
				player.saveResources((byte) baseCount[0], baseCount[1]);
//				player.saveData();
				break;
			case 1:// 木材
				PropsPrototype pp0 = PropsManager.getInstance().getProps(
						GameConfig.GAME_TIMBER_ID);
				if (pp0 != null) {
					player.getPlayerStorageAgent().addProps(pp0.getId(),
							baseCount[1]);
				}
				break;
			case 2:// 铁矿
				PropsPrototype pp1 = PropsManager.getInstance().getProps(
						GameConfig.GAME_IRONORE_ID);
				if (pp1 != null) {
					player.getPlayerStorageAgent().addProps(pp1.getId(),
							baseCount[1]);
				}
				break;
			case 3:// 马匹
				PropsPrototype pp2 = PropsManager.getInstance().getProps(
						GameConfig.GAME_HORSES_ID);
				if (pp2 != null) {
					player.getPlayerStorageAgent().addProps(pp2.getId(),
							baseCount[1]);
				}
				break;
			}
			QuestUtils.checkFinish(player, QuestUtils.TYPE12, true, baseCount[0]);
			// logger.info("Props:" + props);
			// ************** rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms.addModule(p);
			AndroidMessageSender.sendMessage(rms, player);
			// ************** rms
		}
		return baseCount;
	}

	/**
	 * 将领士兵回营
	 * 
	 * @param hero
	 * @param msg
	 * @return
	 */
	public boolean backHeroAndSoldier(int hero, String msg) {
		if ("".equals(msg)) {
			boolean flag = player.getPlayerHeroManager().motifyStatus(hero,
					GameConst.HEROSTATUS_IDEL, "", "", 0);
			return flag;
		} else {
			if (hero == 0) {
				recoverSoldier(msg);
				return true;
			}
			boolean ff = recoverSoldier(msg);
			if (ff) {
				boolean flag = player.getPlayerHeroManager().motifyStatus(hero,
						GameConst.HEROSTATUS_IDEL, "", "", 0);
				return flag;
			}
			return false;
		}
	}

	/**
	 * 偷窃
	 * 
	 * @param pId
	 * @param userId
	 *            用户Id
	 * @return
	 */
	public int[] chargeOutByOccupy(int pId, int userId) {
		PlayerCharacter user = World.getInstance().getPlayer(userId);
		if (user == null) {
			return new int[] { -1, 0, 0 };
		}
		PlayerBuilding p = user.getPlayerBuilgingManager().playerBuildDatas
				.get(pId);

		if (p == null) {
			return new int[] { -1, 0, 0 };
		}
		if (user.getMainCity().getOccupyUserId() == player.getData()
				.getUserid() && p.getCategory() == 1) {// 他是我的附属成 且资源建筑为产出
			// 被偷盗数量
			int[] baseCount = user.getPlayerBuilgingManager().getCharge(pId,
					CHARGE_STEAL);
			// 用户根据类型加入对应数据
			if (baseCount[0] != -1) {
				// logger.info("save role" + user + " type =" + baseCount[0]
				// + "| count=" + baseCount[1]);
				Props props = null;
				// player.saveResources((byte) baseCount[0], baseCount[1]);
				switch ((byte) baseCount[0]) {
				case 0:
					player.saveResources((byte) baseCount[0], baseCount[1]);
//					user.saveData();
					break;
				case 1:// 木材
					PropsPrototype pp0 = PropsManager.getInstance().getProps(
							GameConfig.GAME_TIMBER_ID);
					if (pp0 != null) {
						player.getPlayerStorageAgent().addProps(pp0.getId(),
								baseCount[1]);
					}
					break;
				case 2:// 铁矿
					PropsPrototype pp1 = PropsManager.getInstance().getProps(
							GameConfig.GAME_IRONORE_ID);
					if (pp1 != null) {
						player.getPlayerStorageAgent().addProps(pp1.getId(),
								baseCount[1]);
					}
					break;
				case 3:// 马匹
					PropsPrototype pp2 = PropsManager.getInstance().getProps(
							GameConfig.GAME_HORSES_ID);
					if (pp2 != null) {
						player.getPlayerStorageAgent().addProps(pp2.getId(),
								baseCount[1]);
					}
					break;
				}
				// ************** rms
				RespModuleSet rms = new RespModuleSet(
						ProcotolType.BUILDING_RESP);
				rms.addModule(p);
				AndroidMessageSender.sendMessage(rms, player);
				// ************** rms
				// logger.info("Props:" + props);
			}
			// 用户偷盗数量用户添加数量
			return baseCount;
		}
		return new int[] { -1, 0, 0 };
	}

	// 训练位是否开放
	public boolean isOpenAff(int pId) {
		if (pma == null)
			return false;
		if (pma.isOpenAff(pId)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取附属城数量
	 * 
	 * @return
	 */
	public List<PlayerBuilding> getFuCity() {
		if (pma == null) {
			return null;
		} else {
			return new ArrayList<PlayerBuilding>(pma.affiliatedMap.values());
		}
	}

	/**
	 * 获取附属城加成收益
	 * 
	 * @return
	 */
	public int getAddition() {
		BuildingUpGrade bu = bldMgr.getBuindingLevel(getMainCity()
				.getBuildingID(), (short) getMainCity().getOldLevel());
		if (bu == null) {
			logger.info("用户：" + player.getId() + "用户数据不存在");
		}
		int addition = 0;
		List<PlayerBuilding> pb = getFuCity();
		if (pb != null && pb.size() != 0) {
			for (PlayerBuilding p : pb) {
				PlayerCharacter user = World.getInstance().getPlayer(
						(int) p.getOccupyUserId());
				if (user == null) {
					logger.info("用户：" + player.getId() + "|占领附属成为空");
					continue;
				}
				PlayerMainCityManager userPMC = user.getPlayerBuilgingManager().pmc;// 玩家主城
				if (userPMC != null) {
					addition += Math.ceil(userPMC.plunderGold(bu.getCount()));
					// logger.info("用户：" + player.getId() + "|占领附属成加成"
					// + Math.ceil(userPMC.plunderGold(bu.getCount())));
				}
			}
		}
		// logger.info("用户：" + player.getId() + "|占领附属成总加成" + addition);
		return addition;
	}

	/**
	 * 升级条件
	 * 
	 * @return
	 */
	public BuildingUpGrade checkCondition(PlayerBuilding playerBuilding) {
		// 建筑升级表
		BuildingUpGrade bu = bldMgr.getBuindingLevel(
				playerBuilding.getBuildingID(), playerBuilding.getBuildLevel());
		if (checkbuildDatas() || bu == null)
			return null;
		// 最大等级 小于等于现在等级
		if (getBuilding(playerBuilding.getBuildingID()).getMaxLevel() <= playerBuilding
				.getBuildLevel()) {
			return null;
		}
		// 主城判断爵位
		if (playerBuilding.getBuildingID() == GameConst.MAINCITY_ID) {
			// 用户爵位
			HashMap<Integer, PlayerNobility> map = PlayerLimitManager
					.getInstance().getPlayerNobilityMap();
			if (map == null
					|| map.size() == 0
					|| map.get(player.getPromotedMG()) == null
					|| getMainCity().getBuildLevel() >= map.get(
							player.getPromotedMG()).getTownMaxLevel()) {
				String msg = I18nGreeting.getInstance().getMessage(
						"bulidlevel.lack", null);
				GameUtils.sendTip(new TipMessage(msg,
						ProcotolType.BUILDING_RESP, GameConst.GAME_RESP_FAIL),
						player.getUserInfo(), GameUtils.FLUTTER);
				return null;
			}
		}
		// 判断金币
		if (player.getData().getGameMoney() < bu.getGold()) {
			// String full =
			// I18nGreeting.getInstance().getMessage("level.isfull",new Object[]
			// {});
			// GameUtils.sendTip(new TipMessage(full,
			// ProcotolType.BUILDING_RESP,
			// GameConst.GAME_RESP_FAIL), player.getUserInfo());
			return null;
		}
		return bu;
	}

	/**
	 * 训练士兵
	 * 
	 * @param type
	 *            1：金币 2：功勋
	 */
	public Map<Integer, PlayerSoldier> trainingSoldier(int soldierId,
			int count, byte type) {
		// 获取建造时间
		if (playerBarrack == null)
			return null;
		TrainingSoldier tso = playerBarrack.getSoldierTrain(soldierId);
		if (tso == null)
			return null;
		Map<Integer, PlayerSoldier> map = playerBarrack.trainingSoldier(tso,
				soldierId, count, type);
		if (map != null) {
			QuestUtils.checkFinish(player, QuestUtils.TYPE13, true, soldierId);
			return map;
		} else {
			return null;
		}

	}

	/**
	 * 训练士兵
	 * 
	 * @param type
	 *            1：金币 2：功勋
	 */
	public Map<Integer, PlayerSoldier> trainingOver() {
		// 获取建造时间
		// PlayerBarrack playerBarrack = (PlayerBarrack)player.getBarrack();//兵营
		if (playerBarrack == null)
			return null;
		return playerBarrack.trainingOver();
	}

	/**
	 * 修改训练时间
	 */
	public TipUtil changeTrainingTime(int soldierId, Props props) {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setFailTip("加速失败");
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			tip.setFailTip("fail");
		}
		
		if (playerBarrack == null) {
			tip.setFailTip("兵营不存在");
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				tip.setFailTip("fail");
			}
			
			return tip;
		} else {
			tip = player.getPlayerStorageAgent().getPis()
					.userProps(props.getId(), false);
			if (props.getPrototype().getPropsType() != ItemConst.TRAINING_SOLIDER_TYPE
					|| !tip.isResult()) {
				return tip;
			}
			PlayerSoldier playerSoldier = playerBarrack.changeTrainingTime(
					soldierId,
					Integer.parseInt(props.getPrototype().getProperty3()));
			// ******rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms.addModule(playerSoldier);
			rms.addModuleBase(tip.getLst());
			AndroidMessageSender.sendMessage(rms, player);
			// ********rms
			// tip.setSuccTip("加速成功");
			tip.setSuccTip("");
			return tip;
		}
	}

	/**
	 * 查看士兵
	 */
	public List<PlayerSoldier> viewSoldier(byte type) {// 是否特种兵 byte 1 = 特种兵
		if (playerBarrack == null)
			return null;
		return playerBarrack.viewSoldier(type);
	}

	// /**
	// * 更新进数据库
	// */
	// public synchronized boolean saveMapToString(Map<Integer, PlayerSoldier>
	// ps) {
	// return playerBarrack.saveMapToString(ps);
	// }
	/**
	 * 获取用户士兵
	 */
	public Map<Integer, PlayerSoldier> getSoldier(PlayerBarrackManager pp) {
		if (checkbuildDatas())
			return null;
		return pp.getSoldier();
	}

	/**
	 * 解析字符串
	 */
	public static HashMap<Integer, Integer> resolveSoMsg(String soMsg) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (soMsg != null && soMsg.length() > 0) {
			String[] so = soMsg.split(";");
			if (so != null && so.length > 0) {
				for (String s : so) {
					String[] scount = s.split(":");
					map.put(Integer.parseInt(scount[0]),
							Integer.parseInt(scount[1]));
				}
			}
		}
		return map;
	}

	/**
	 * 派兵
	 * 
	 * @param soMsg
	 *            兵种id:数量；
	 * @return
	 */
	public boolean dispatchSoldier(String soMsg) {
		if (playerBarrack == null)
			return false;
		if (FightUtil.getSoldierNum(soMsg) <= 0)
			return false;
		return playerBarrack.dispatchSoldier(soMsg);
	}

	/**
	 * 回收兵
	 */
	public boolean recoverSoldier(String soMsg) {
		if (playerBarrack == null)
			return false;
		return playerBarrack.recoverSoldier(soMsg);
	}

	/**
	 * solider
	 * 
	 * @param soMsg
	 * @return
	 */
	public String checkSolider(String soMsg) {
		if (playerBarrack == null)
			return "";
		return playerBarrack.checkSolider(soMsg);
	}

	/**
	 * 其他建筑驻扎士兵
	 * 
	 * @param playerBuildId
	 * @param soMsg
	 */
	public void presenceSoldier(int buildId, String soMsg) {
		PlayerBuilding p = getPlayerFromBuild(getBuilding(buildId));// 其他城池驻扎士兵
		p.setSoldierMsg(soMsg);
//		savePlayerBuilding(p);
	}

	/**
	 * 其他建筑 撤出士兵
	 * 
	 * @param playerBuildId
	 * @param soMsg
	 */
	public void pRecoverSoldier(int buildId, String soMsg) {
		PlayerBuilding p = getPlayerFromBuild(getBuilding(buildId));// 其他城池驻扎士兵
		p.setSoldierMsg("");
//		savePlayerBuilding(p);
	}

	/**
	 * 获取其他主城驻扎士兵信息
	 * 
	 * @param buildId
	 * @param soMsg
	 * @return map
	 */
	public HashMap<Integer, Integer> getPresenceSoldier(int buildId,
			String soMsg) {
		PlayerBuilding p = getPlayerFromBuild(getBuilding(buildId));// 其他城池驻扎士兵
		return resolveSoMsg(p.getSoldierMsg());
	}

	/**
	 * 获取其他主城驻扎士兵信息
	 * 
	 * @param buildId
	 * @param soMsg
	 * @return 字符
	 */
	public String getPSoldierString(int buildId, String soMsg) {
		PlayerBuilding p = getPlayerFromBuild(getBuilding(buildId));// 其他城池驻扎士兵
		return p.getSoldierMsg();
	}

	/**
	 * 得到其他用户建筑
	 * 
	 * @param pId
	 * @param userId
	 *            用户Id
	 * @return
	 */
	public List<PlayerBuilding> getOtherBuilding(int userId) {
		PlayerCharacter user = World.getInstance().getPlayer(userId);
		if (user == null) {
			return null;
		}
		return new ArrayList<PlayerBuilding>(
				user.getPlayerBuilgingManager().playerBuildDatas.values());
	}

	/**
	 * 判断是否开放
	 * 
	 * @param no
	 * @return
	 */
	public boolean isTrainingOpen(int no) {
		if (playerTraining == null)
			return false;
		return playerTraining.isTrainingOpen(no);
	}

	/**
	 * 得到训练台对象
	 * 
	 * @param no
	 *            训练位
	 * @return
	 */
	public TrainingBits getTrainingBits(int no) {
		if (isTrainingOpen(no)) {
			return bldMgr.getTrain(no);
		}
		return null;
	}

	/**
	 * 训练位开放
	 * 
	 * @param tb
	 * @return
	 */
	public PlayerBuilding openTraining(int no) {
		if (playerTraining == null)
			return null;
		return playerTraining.openTraining(no);
	}

	public TipUtil openVip(int no) {
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		tip.setFailTip("失败");
		PlayerBuilding pt = null;
		List<ClientModule> lst = new ArrayList<ClientModule>();
		if (playerTraining != null) {
			// pt = playerTraining.getTraining();
			tip.setFailTip("openTraining false");
			// pt.setTip(new TipMessage("openTraining false",
			// ProcotolType.BUILDING_RESP,GameConst.GAME_RESP_FAIL));
		} else {
			tip.setFailTip("训练场为空");
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				tip.setFailTip("openTraining false");
			}
			
			return tip;
		}
		if (no == 6) {// 20 60 100
			int cost = GameConfig.openVip1;
			// 判断vip对应 需要扣掉多少钻石
			if (player.getData().getJoyMoney() >= cost) {
				pt = openTraining(6);
				player.saveResources(GameConfig.JOY_MONEY, -1 * cost);

				logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1)
						+ " uid=" + player.getId() + " uname="
						+ player.getData().getName() + " diamondNum=" + cost
						+ " description=训练台开训练位");

				GameLog.logSystemEvent(LogEvent.USE_DIAMOND,
						String.valueOf(GameConst.DIAMOND_TRAIN), "",
						String.valueOf(cost), String.valueOf(player.getId()),
						"no=6");

				lst.add(pt);
				tip.setSuccTip("");
			} else {
				String msg = I18nGreeting.getInstance().getMessage(
						"dimond.not.enough", null);
				tip.setFailTip(msg);
			}
		} else if (no == 7) {
			int cost = GameConfig.openVip2;
			// 判断vip对应 需要扣掉多少钻石
			if (player.getData().getJoyMoney() >= cost) {
				pt = openTraining(7);
				player.saveResources(GameConfig.JOY_MONEY, -1 * cost);
				// logger.info("time=" +
				// TimeUtils.now().format(TimeUtils.FORMAT1)
				// + " uid=" + player.getId() + " uname="
				// + player.getData().getName() + " diamondNum=" + cost
				// + " description=训练台开训练位");
				GameLog.logSystemEvent(LogEvent.USE_DIAMOND,
						String.valueOf(GameConst.DIAMOND_TRAIN), "",
						String.valueOf(cost), String.valueOf(player.getId()),
						"no=7");
				lst.add(pt);
				tip.setSuccTip("");
			} else {
				String msg = I18nGreeting.getInstance().getMessage(
						"dimond.not.enough", null);
				tip.setFailTip(msg);
			}
			// return null;
		} else if (no == 8) {
			int cost = GameConfig.openVip3;
			// 判断vip对应 需要扣掉多少钻石
			if (player.getData().getJoyMoney() >= cost) {
				pt = openTraining(8);
				player.saveResources(GameConfig.JOY_MONEY, -1 * cost);
				// logger.info("time=" +
				// TimeUtils.now().format(TimeUtils.FORMAT1)
				// + " uid=" + player.getId() + " uname="
				// + player.getData().getName() + " diamondNum=" + cost
				// + " description=训练台开训练位");

				GameLog.logSystemEvent(LogEvent.USE_DIAMOND,
						String.valueOf(GameConst.DIAMOND_TRAIN), "",
						String.valueOf(cost), String.valueOf(player.getId()),
						"no=8");

				lst.add(pt);
				tip.setSuccTip("");
			} else {
				String msg = I18nGreeting.getInstance().getMessage(
						"dimond.not.enough", null);
				tip.setFailTip(msg);
			}
		}
		// rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.BUILDING_RESP);
		rms.addModules(lst);
		AndroidMessageSender.sendMessage(rms, player);
		// rms
		return tip;
	}

	/**
	 * 训练位关闭
	 * 
	 * @param tb
	 * @return
	 */
	public PlayerBuilding closeTraining(int no) {
		if (playerTraining == null)
			return null;
		else {
			// trainIndex
			if (player.getPlayerHeroManager().playerHeroMap != null
					&& player.getPlayerHeroManager().playerHeroMap.size() > 0) {
				for (PlayerHero hero : player.getPlayerHeroManager().playerHeroMap
						.values()) {
					if (hero.getTrainIndex() == no) {
						hero.stopTrain();
					}
				}
			}
		}
		return playerTraining.closeTraining(no);
	}

	/**
	 * 刷新铁匠铺
	 * 
	 * @param type
	 * @return
	 */
	public ArrayList<Equipment> refreshEqument(byte type) {// byte= 1
		if (playerBlackSmithy == null)
			return null;
		return playerBlackSmithy.refreshEqument(type);
	}

	// 刷将 时间更新
	public int setHeroTime() {
		if (playerTavern == null) {
			return 0;
		}
		playerTavern.setHeroTime();
		return (int) (playerTavern.getHeroTime() / 1000);
	}

	// 购买
	public Equipment buyEquipment(int eid) {
		if (playerBlackSmithy != null)
			return playerBlackSmithy.buyEquipment(eid);
		return null;
	}

	public TipUtil soliderEqu(int id, int num) {
		if (playerBarrack != null)
			return playerBarrack.soliderEqu(id, num);
		return new TipUtil(ProcotolType.BUILDING_RESP).setFailTip("兵营不存在");
	}

	/**
	 * 移除建筑
	 * 
	 * @param buildingId
	 *            建筑ID
	 * @param id
	 *            坐标ID
	 * @return
	 */
//	public boolean removeBuilding(int id) {
//		try {
//			gameDao.getSimpleJdbcTemplate().update(BuildingDAO.SQL_DELETE_PLB,
//					id);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//	}

	// // 建筑是否可以移除
	// PlayerBuilding pb = getPlayerBuild(id);
	// // 是否能够拆除
	// if (pb == null || pb.getCanBeDestroyed() == 0) {
	// return 0;
	// }
	// // 缓存移除
	// modifyMyDatas(pb,TYPE_REMOVE);
	// // DB移除
	// gameDao.getSimpleJdbcTemplate().update(GameWorldDAO.SQL_DELETE_PLB, id);
	// // 获取建造价格
	// int price = pb.getPrice();
	// // TODO 用户加上金币
	//
	// return 1;
	// }

	// /**
	// * 获取所有资源建筑
	// *
	// * @return
	// */
	// public List<PlayerBuilding> getChargebuild() {
	// List<PlayerBuilding> lst = new ArrayList<PlayerBuilding>();
	// for (PlayerBuilding p : playerBuildDatas.values()) {
	// if (p.getBuildType() == GameConst.RESOURCES_TYPE) {
	// lst.add(p);
	// }
	// }
	// return lst;
	// }
	// TODO 测试
	public static void main(String[] args) {
		// int a = 1;
		// System.out.println(a == 0 ? 0 : 1);
		System.out.println(TimeUtils.nowLong());
		System.out.println(TimeUtils.nowLong() / 1000);
		int time = (int) (TimeUtils.nowLong() / 1000);
		System.out.println(time);
		System.out.println(time * 1000);
		new ArrayList<Integer>(1);
		// System.out.println(CalendarUtil.format3(no));
	}

	/**
	 * 士兵总数
	 * 
	 * @return
	 */
	public int allSoliderCount() {
		if (playerBarrack == null || playerBarrack.getBarrack() == null) {
			return 0;
		} else {
			return playerBarrack.allSoliderCount();
		}
	}

	/**
	 * 获取所有兵装
	 * 
	 * @return
	 */
	public int allSoliderEqu() {
		return player.getData().getArcherEqu()
				+ player.getData().getCavalryEqu()
				+ player.getData().getInfantryEqu()
				+ player.getData().getSpecialArms();
	}

	public void checkTraing() {
		if (getPma() != null) {
			getPma().getTraining((byte) 0);
		}
	}

	public boolean isFuCityFull() {
		if (pma != null && pma.getFreeCity() != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * 进机场是否存在
	 */
	public void addBuild(int obid,int level){
		boolean isadd = false;
		OriginalBuilding ob = bldMgr.getOriginalBuilding(obid);
		if(ob== null){
			return;
		}
		if(getPlayer().getData().getLevel()>level){
			for(PlayerBuilding p :playerBuildDatas.values()){
				if(p.getBuildingID() == ob.getBuildId()){
					isadd = true;
				}
			}
			if(!isadd){
				PlayerBuilding jinji = loadObject(obid, (short) 1);//1013 -->23
				if(jinji != null){
					int bId = gameDao.getBuildDAO().addPlayerBuilding(jinji);
					// 放入缓存
					jinji.setId(bId);
					modifyMyDatas(jinji, TYPE_ADD);
				}
			}
		}
	}
	
	/**
	 * 返回用户兵种等级
	 * @param type
	 * @return
	 */
	public int getSoliderLeve(byte type){
		if(null==playerBarrack){
			return 0;
		}else{
			return playerBarrack.soliderLevel.get(type);
		}
	}

}
