package com.joymeng.game.domain.role;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.RPGCreature;
import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.ChatChannel;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.event.GameEvent;
import com.joymeng.core.event.GameEventDispatcher;
import com.joymeng.core.event.GameEventListener;
import com.joymeng.core.fight.FightLog;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.award.DiamondAwardManager;
import com.joymeng.game.domain.building.PlayerBarrackManager;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.building.PlayerBuildingManager;
import com.joymeng.game.domain.building.PlayerMainCityManager;
import com.joymeng.game.domain.building.PlayerTavernManager;
import com.joymeng.game.domain.building.TrainingBits;
import com.joymeng.game.domain.card.CardManager;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightEventManager;
import com.joymeng.game.domain.fight.mod.Arena;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.friend.RelationManager;
import com.joymeng.game.domain.hero.PlayerHeroManager;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.PlayerStorageAgent;
import com.joymeng.game.domain.item.equipment.Equipment;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.nation.Achievements;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.PlayerNationManager;
import com.joymeng.game.domain.nation.battle.RegionBattleManager;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.quest.PlayerQuestAgent;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.recharge.RechargeManager;
import com.joymeng.game.domain.shop.PlayerShopManager;
import com.joymeng.game.domain.sign.SignManager;
import com.joymeng.game.domain.sound.SoundVoice;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipModule;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;

/**
 * 相当于玩家管理类 主要功能包括事件的处理
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class PlayerCharacter extends RPGCreature implements ChatChannel,
		TipModule {

	static final Logger logger = LoggerFactory.getLogger(PlayerCharacter.class);
	// PlayerQuestAgent questAgent = new PlayerQuestAgent(this);
	GameEventDispatcher eventDispatcher = new GameEventDispatcher();
	private byte state = GameConst.STATE_OFFLINE; // 玩家在线状态(online, offline)
	private TipMessage tip;
	private long shoutTime;
	private long saveTime;// 上一次保存的时间
	private byte halt;// 是否挂起
	//收发消息的统计
	private AtomicLong send=new AtomicLong();
	private AtomicLong receive=new AtomicLong();
	public long getShoutTime() {
		return shoutTime;
	}

	public void setShoutTime(long shoutTime) {
		this.shoutTime = shoutTime;
	}


	public AtomicLong getSend() {
		return send;
	}

	public void setSend(AtomicLong send) {
		this.send = send;
	}

	public AtomicLong getReceive() {
		return receive;
	}

	public void setReceive(AtomicLong receive) {
		this.receive = receive;
	}

	/**
	 * 军功加成
	 * 
	 * @return
	 */
	public double goldMili() {
		if (NationManager.getInstance().getSatateMilitary(getStateId()) == 0) {
			return 0.01;
		}
		int count = NationManager.getInstance().getSatateMilitary(getStateId()) < 2000 ? 2000
				: NationManager.getInstance().getSatateMilitary(getStateId());
		if (count != 0) {
			double b = getResourcesData(GameConfig.MEDALS) * 10.0 / (count);
			long l1 = Math.round(b * 100); // 四舍五入
			double ret = l1 / 100.0;
			if (b > 0.01) {
				logger.info("用户:" + getId() + "  功勋加成:" + ret);
				return ret;
				// setGoldAdd(b);
			}
		}
		logger.info("用户:" + getId() + "  功勋加成:" + 0.01);
		return 0.01;
	}
	
	/**
	 * 得到我的城市
	 * @param key
	 * @return
	 */
	public int getIdentity(byte key) {
		switch (key) {
		case GameConst.TITLE_KING:// 国王
			return getData().getNativeId() / 1000 * 1000;
		case GameConst.TITLE_GOVERNOR:// 州长
			return getData().getNativeId() / 100 * 100;
		case GameConst.TITLE_MAYOR_CITY:// 市长
			return getData().getNativeId() / 10 * 10;
		case GameConst.TITLE_MAYOR_TOWN:// 县长
			return getData().getNativeId();
		default:
			return 0;
		}
	}

	/**
	 * 政绩加成 平民加成=0.7*〔1+0.2*（玩家政绩-平民标准政绩）/平民标准政绩〕*县长加成
	 * 
	 * @return
	 */
	public int goldAche() {
		// NationManager.getInstance().setAchiev();//计算政绩加成
		if (getData().getTitle() != 0 && getData().getTitle() % 2 == 0) {

			double bb = NationManager.getInstance().getAchievs(
					getIdentity(getData().getTitle()));
			logger.info("用户:" + getId() + "  政绩加成:" + bb);
			return (int) (bb * 100);

		} else {
			if (NationManager.getInstance().userAchievMap.get(getId()) != null) {
				double tt = NationManager.getInstance().userAchievMap
						.get(getId());
				logger.info("用户:" + getId() + "  政绩加成:" + tt);
				return (int) (tt * 100);
			} else {

				double bb = NationManager.getInstance().getInstance()
						.getAchievs(getIdentity(GameConst.TITLE_MAYOR_TOWN));
				Achievements userAch = NationManager.getInstance().achievementsMap
						.get((int) getData().getTitle());
				if (userAch != null) {
					double aa = 0.7
							* (1 + 0.2
									* (getResourcesData(GameConfig.ACHIVE) - userAch
											.getStandard())
									/ userAch.getStandard()) * bb;
					logger.info("用户:" + getId() + "  政绩加成:" + aa);
					NationManager.getInstance().userAchievMap.put(getId(), aa);
					return (int) (aa * 100);
				} else {
					logger.info("用户:" + getId() + "  政绩加成:" + 0);
					return 0;
				}

			}
		}
	}

	/**
	 * 注册事件监听
	 * 
	 * @param listener
	 * @param event
	 */
	public final void registEventListener(GameEventListener listener,
			short event) {
		eventDispatcher.addListener(listener, event);
	}

	/**
	 * 通知事件发生
	 * 
	 * @param evt
	 */
	public final void notifyEvent(GameEvent evt) {
		eventDispatcher.raise(evt);
	}

	// 玩家基本属性
	private int id;
	private UserInfo UserInfo;// 玩家终端在服务端的连接对象，包含了玩家的连接信息，返回给终端的消息中应设置此对象

	public byte getHalt() {
		return halt;
	}

	public void setHalt(byte halt) {
		this.halt = halt;
	}

	public int getId() {
		return (int) getData().getUserid();
	}

	public UserInfo getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		UserInfo = userInfo;
	}

	/**
	 * 得到数据库实体类
	 */
	public RoleData saveData() {
		gameDao.saveRole(this.getData());
		return data;
	}

	/**
	 * 是否有足够的数据
	 * 
	 * @param type
	 * @param count
	 * @return
	 */
	public boolean isFull(byte type, int count) {
		if (count < 0) {
			if (getResourcesData(type) >= Math.abs(count)) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * 消耗数据
	 * 
	 * @param type
	 * @param count
	 */
	public boolean recordRoleData(byte type, int count) {
		if (count > 0) {
			saveResources(type, count);
			return true;
		} else {
			if (getResourcesData(type) >= Math.abs(count)) {
				saveResources(type, count);
				return true;
			} else {
				return false;
			}

		}
	}

	/**
	 * 得到某类数据
	 * 
	 * @param key
	 * @return
	 */
	public int getResourcesData(byte key) {
		switch (key) {
		case GameConfig.GAME_MONEY:
			return getData().getGameMoney();
		case GameConfig.GAME_WARS:
			return getData().getWarTimes();
		case GameConfig.JOY_MONEY:
			return getData().getJoyMoney();
		case GameConfig.AWARD:
			return getData().getAward();
		case GameConfig.GAME_EXP:
			return getData().getExp();
		case GameConfig.MEDALS:
			return getData().getMilitaryMedals();// 军功
		case GameConfig.ARCHEREQU:
			return getData().getArcherEqu();
		case GameConfig.INFANTRYEQU:
			return getData().getInfantryEqu();
		case GameConfig.CAVALRYEQU:
			return getData().getCavalryEqu();
		case GameConfig.SPECIALARMS:
			return getData().getSpecialArms();
		case GameConfig.ACHIVE:
			return getData().getAchieve();
		case GameConfig.CHIP:
			return getData().getChip();
		default:
			return 0;
		}
	}

	/**
	 * 保存用户基本数据 可添加
	 * 
	 * @param addCount
	 * @return
	 */
	public int saveResources(byte type, int addCount) {
		int allCount = 0;
		switch (type) {
		case GameConfig.GAME_MONEY:
			allCount = getData().getGameMoney() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setGameMoney(allCount);
			break;
		case GameConfig.GAME_WARS:
			allCount = getData().getWarTimes() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setWarTimes(allCount);
			break;
		case GameConfig.JOY_MONEY:
			allCount = getData().getJoyMoney() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setJoyMoney(allCount);
			break;
		case GameConfig.AWARD:
			allCount = getData().getAward() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setAward(allCount);
			break;
		case GameConfig.MEDALS:
			allCount = getData().getMilitaryMedals() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setMilitaryMedals(allCount);
			break;
		case GameConfig.ARCHEREQU:
			allCount = getData().getArcherEqu() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setArcherEqu(allCount);
			break;
		case GameConfig.INFANTRYEQU:
			allCount = getData().getInfantryEqu() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setInfantryEqu(allCount);
			break;
		case GameConfig.CAVALRYEQU:
			allCount = getData().getCavalryEqu() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setCavalryEqu(allCount);
			break;
		case GameConfig.SPECIALARMS:
			allCount = getData().getSpecialArms() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setSpecialArms(allCount);
			break;
		case GameConfig.ACHIVE:
			allCount = getData().getAchieve() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setAchieve(allCount);
			break;
		case GameConfig.CHIP:
			allCount = getData().getChip() + addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setChip(allCount);
			break;
		case GameConfig.SCORE:
			allCount = getData().getScore()+addCount;
			if (allCount < 0) {
				return -1;
			}
			getData().setScore(allCount);
			break;
		}
		// rms
		// ************************* rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.USER_INFO_RESP);// 模块消息
		rms.addModule(getData());
		AndroidMessageSender.sendMessage(rms, this);
		// ************************* rms
		// rms
		return allCount;
	}

	public List<PlayerBuilding> saveExp(int addCount) {
		short level = getData().getLevel();
		if (level < GameConfig.MAX_LEVEL) {
			getData().setExp(getData().getExp() + addCount);
			levelUp();// 升级
		}
		FightLog.info("保存经验---玩家：" + getId() + "|经验：" + addCount);

		// ************************* rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.USER_INFO_RESP);// 模块消息
		rms.addModule(getData());
		AndroidMessageSender.sendMessage(rms, this);
		// ************************* rms

		return getPlayerBuilgingManager().userLevelUp(level,
				getData().getLevel());
	}

	/**
	 * 得到用户的爵位
	 * 
	 * @return
	 */
	public int getPromotedMG() {
		return getData().getCityLevel();
	}

	public void setPromotedMG() {
		getData().setCityLevel(getData().getCityLevel() + 1);
		QuestUtils.checkFinish(this, QuestUtils.TYPE28, true);
	}

	/**
	 * 用户升级
	 */
	public void levelUp() {
		logger.info("用户当前等级:" + getData().getLevel());
		int level = getData().getLevel();
		PlayerLimit pl = PlayerLimitManager.getInstance().getPlayerLimit(level);
		if (pl == null) {
		} else if (level < GameConfig.MAX_LEVEL) {
			logger.info("用户经验：" + getData().getExp() + "  升级需要经验："
					+ pl.getLevelExp());
			if (getData().getExp() >= pl.getLevelExp()) {
				int ll = getData().getLevel();
				logger.info("用户原来等级:" + getData().getLevel());
				getData().setLevel((short) (getData().getLevel() + 1));// 等级加一
				logger.info("用户升级后等级：" + getData().getLevel());
				// 根据升级等级发内测奖品
				DiamondAwardManager.getInstance().levelUpAward(this);
				// 缓存数据
				GameUtils.putToCache(this);
				// 扣除升级经验
				getData().setExp(getData().getExp() - pl.getLevelExp());
				logger.info("用户扣除经验后:" + getData().getExp());
//				saveData();
				QuestUtils.checkFinish(this, QuestUtils.TYPE27, true);
				this.playerQuestAgent.addDailyQuestOnLevelup();
				sendSound((byte) 1, getData().getLevel());
				levelUp();// 递归升级
			}
		}
	}

	PlayerQuestAgent playerQuestAgent;
	PlayerBuildingManager playerBuilgingManager;
	PlayerHeroManager playerHeroManager;
	PlayerStorageAgent playerStorageAgent;
	PlayerNationManager playerNationManager;
	PlayerShopManager playerShopManager;
	PlayerUser playerUser;
	RoleData data;
	RelationManager relationManager;
	FightEventManager fightEventManager;
	RegionBattleManager regionBattleManager;
	CardManager cardManager;
	RechargeManager rechargeManager;
	SignManager signManager;
	long heartTime;// 心跳时间

	/**
	 * 载入数据
	 */
	public void loadData(RoleData data) {
		this.id = (int) data.getUserid();
		this.data = data;
		data.setPc(this);
	}

	/**
	 * 初始化玩家相关数据
	 */
	public void init() {
		long time=TimeUtils.nowLong();
		playerHeroManager = new PlayerHeroManager();
		playerHeroManager.setPlayer(this);
		playerHeroManager.init();
		long time1=TimeUtils.nowLong();
//		logger.info("player init 1耗时="+(time1-time));
		playerBuilgingManager = new PlayerBuildingManager();
		playerBuilgingManager.setPlayer(this);
		playerBuilgingManager.load();// 加载个人用户数据
		long time2=TimeUtils.nowLong();
//		logger.info("player init 2耗时="+(time2-time1));
		playerStorageAgent = new PlayerStorageAgent();
		playerStorageAgent.setOwner(this);
		playerStorageAgent.loadPlayerCells(getData().getPlayerCells());// 加载用户背包数据
		long time3=TimeUtils.nowLong();
//		logger.info("player init 3耗时="+(time3-time2));
		playerNationManager = new PlayerNationManager();
		playerNationManager.setPlayer(this);

		regionBattleManager = new RegionBattleManager();
		regionBattleManager.setPlayer(this);

		relationManager = new RelationManager();
		relationManager.setPlayer(this);
		relationManager.init();
		long time4=TimeUtils.nowLong();
//		logger.info("player init 4耗时="+(time4-time3));
		// 加载战报
		setFightEventManager(new FightEventManager(this));

		// 加载CardManager
		setCardManager(new CardManager(this));

		// 加载RechargeManager
		setRechargeManager(new RechargeManager(this));
		
		//加载SignManager
		setSignManager(new SignManager(this));

		playerUser = new PlayerUser();
		playerUser.setPlayer(this);

		// goldMili();

		playerHeroManager.updateHero();
		long time5=TimeUtils.nowLong();
//		logger.info("player init 5耗时="+(time5-time4));
	}

	/**
	 * 保存玩家数据
	 */
	public void saveSelf() {
//		logger.info("=================player save start,id=" + this.getId());
		long time=TimeUtils.nowLong();
		// 保存将领
		playerHeroManager.saveAll();
		// 保存建筑数据
		playerBuilgingManager.saveAllBuilding();
		// 背包数据
		playerStorageAgent.saveCells();
		// 资源点保存
		// NationManager.getInstance().saveAll();
		// 保存玩家数据
		if (playerQuestAgent != null) {
			playerQuestAgent.save();
		}

		saveData();
		logger.info("保存玩家耗时="+(TimeUtils.nowLong()-time)+" uid="+this.getId());
//		logger.info("=================player save end,id=" + this.getId());

		// 玩家离线清除保存在mongoDB中的数据
		// MongoServer MongoServer = new MongoServer();
		// MongoServer.getPlayerCacheDAO().deletePlayerCache((int)this.getData().getUserid());
	}

	public void logout() {

	}

	// 设置县id
	public void setNativeId(int contry) {
		getData().setNativeId(contry);
	}

	public int getNational() {
		return getData().getNativeId() / 1000 * 1000;
	}

	/**
	 * 缓存起来，根据规则，需要设定过期时间
	 */
	public void cached() {

	}

	/**
	 * @return GET the playerUser
	 */
	public PlayerUser getPlayerUser() {
		return playerUser;
	}

	/**
	 * @param SET
	 *            playerUser the playerUser to set
	 */
	public void setPlayerUser(PlayerUser playerUser) {
		this.playerUser = playerUser;
	}

	/**
	 * @return GET the playerNationManager
	 */
	public PlayerNationManager getPlayerNationManager() {
		return playerNationManager;
	}

	/**
	 * @return GET the relationManager
	 */
	public RelationManager getRelationManager() {
		return relationManager;
	}

	/**
	 * @param SET
	 *            relationManager the relationManager to set
	 */
	public void setRelationManager(RelationManager relationManager) {
		this.relationManager = relationManager;
	}

	/**
	 * @param SET
	 *            playerNationManager the playerNationManager to set
	 */
	public void setPlayerNationManager(PlayerNationManager playerNationManager) {
		this.playerNationManager = playerNationManager;
	}

	public PlayerBuildingManager getPlayerBuilgingManager() {
		return playerBuilgingManager;
	}

	/**
	 * 获取 playerStorageAgent
	 * 
	 * @return the playerStorageAgent
	 */
	public PlayerStorageAgent getPlayerStorageAgent() {
		return playerStorageAgent;
	}

	/**
	 * 设置 playerStorageAgent
	 * 
	 * @param playerStorageAgent
	 *            the playerStorageAgent to set
	 */
	public void setPlayerStorageAgent(PlayerStorageAgent playerStorageAgent) {
		this.playerStorageAgent = playerStorageAgent;
	}

	/**
	 * @return GET the playerShopManager
	 */
	public PlayerShopManager getPlayerShopManager() {
		return playerShopManager;
	}

	/**
	 * @param SET
	 *            playerShopManager the playerShopManager to set
	 */
	public void setPlayerShopManager(PlayerShopManager playerShopManager) {
		this.playerShopManager = playerShopManager;
	}

	public void setPlayerBuilgingManager(
			PlayerBuildingManager playerBuilgingManager) {
		this.playerBuilgingManager = playerBuilgingManager;
	}

	public FightEventManager getFightEventManager() {
		return fightEventManager;
	}

	public void setFightEventManager(FightEventManager fightEventManager) {
		this.fightEventManager = fightEventManager;
	}

	public PlayerHeroManager getPlayerHeroManager() {
		return playerHeroManager;
	}

	public void setPlayerHeroManager(PlayerHeroManager playerHeroManager) {
		this.playerHeroManager = playerHeroManager;
	}

	public RegionBattleManager getRegionBattleManager() {
		return regionBattleManager;
	}

	public void setRegionBattleManager(RegionBattleManager regionBattleManager) {
		this.regionBattleManager = regionBattleManager;
	}

	public CardManager getCardManager() {
		return cardManager;
	}

	public void setCardManager(CardManager cardManager) {
		this.cardManager = cardManager;
	}

	public RechargeManager getRechargeManager() {
		return rechargeManager;
	}

	public void setRechargeManager(RechargeManager rechargeManager) {
		this.rechargeManager = rechargeManager;
	}

	public RoleData getData() {
		return data;
	}

	public void setData(RoleData data) {
		this.data = data;
		this.id = (int) data.getUserid();
	}

	public long getHeartTime() {
		return heartTime;
	}

	public void setHeartTime(long heartTime) {
		this.heartTime = heartTime;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public long getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(long saveTime) {
		this.saveTime = saveTime;
	}

	public SignManager getSignManager() {
		return signManager;
	}

	public void setSignManager(SignManager signManager) {
		this.signManager = signManager;
	}

	/**
	 * 获取主城对象
	 * 
	 * @return
	 */
	public PlayerBuilding getMainCity() {
		// return playerBuilgingManager.getPlayerFromBuild(playerBuilgingManager
		// .getBuilding(GameConst.MAINCITY_ID));
		PlayerMainCityManager maincity = playerBuilgingManager.getPmc();
		if (maincity != null) {
			return maincity.getMainCity();
		}
		return null;
	}

	/**
	 * 获取酒馆对象
	 * 
	 * @return
	 */
	public PlayerBuilding getTavern() {
		PlayerTavernManager playerTavern = playerBuilgingManager
				.getPlayerTavern();
		if (playerTavern != null) {
			return playerTavern.getTavern();
		}
		return null;
	}

	// 设置时间
	public void setHeroTime() {
		int time = playerBuilgingManager.setHeroTime();
		// 保存到玩家身上，主要是为了传输给客户端
		// this.getData().setHeroTime(time);
	}

	/**
	 * 得到酒馆英雄
	 * 
	 * @return
	 */
	public String getTavernHero() {
		PlayerBuilding p = getTavern();
		if (p == null)
			return "";
		logger.info(">>>得到酒馆" + p.getRemark1());
		return p.getRemark1() == null ? "" : p.getRemark1();
	}

	/**
	 * 设置酒馆英雄
	 * 
	 * @param s
	 * @return
	 */
	public boolean setTavernHero(String s) {
		PlayerBuilding p = getTavern();
		if (p == null)
			return false;
		logger.info(">>>保存酒馆" + s);
		p.setRemark1(s);
//		playerBuilgingManager.savePlayerBuilding(p);// 保存如数据库
		logger.info(">>>酒馆数据:" + p.getRemark1());
		return true;
	}

	/**
	 * 获取兵营对象
	 * 
	 * @return
	 */
	public PlayerBuilding getBarrack() {
		PlayerBarrackManager barrack = playerBuilgingManager.getPlayerBarrack();
		if (barrack != null) {
			return barrack.getBarrack();
		}
		return null;
		// return playerBuilgingManager.getPlayerFromBuild(playerBuilgingManager
		// .getBuilding(GameConst.BARRACK_ID));
	}

	/**
	 * 获得背包某个装备
	 * 
	 * @param id
	 * @return
	 */
	public Equipment getEquipment(int id) {
		if (id == 0)
			return null;
		Cell cell = playerStorageAgent.getCell(id);
		if (cell == null)
			return null;
		return (Equipment) cell.getItem();
	}

	/**
	 * 获得装备 加制 的技能效果id
	 * 
	 * @param id
	 * @return
	 */
	public int getFirmId(int id, int heroid) {
		return playerStorageAgent.getFirmId(id, heroid);
	}

	/**
	 * 加制 装备数值
	 * 
	 * @param eId
	 * @return
	 */
	public int getFirmPoint(int eId, int heroid) {
		return playerStorageAgent.getFirmPoint(eId, heroid);
	}

	/**
	 * 加值 技能 id
	 * 
	 * @param id
	 *            装备id
	 * @return
	 */
	public int getEffcetId(int id) {
		if (id == 0)
			return 0;
		Equipment e = getEquipment(id);
		if (e == null)
			return 0;
		return e.getEffectId();
	}

	/**
	 * 加值结束时间
	 * 
	 * @param id
	 *            装备id
	 * @return
	 */
	public long getEffcetTime(int id) {
		Equipment e = getEquipment(id);
		if (e == null)
			return 0;
		return e.getEffectTime();
	}

	/**
	 * 获得道具
	 * 
	 * @param id
	 * @return
	 */
	public Props getProps(int id) {
		if (id == 0)
			return null;
		Cell cell = playerStorageAgent.getCell(id);
		if (cell == null)
			return null;
		return (Props) cell.getItem();
	}

	/**
	 * 训练台是否开放
	 * 
	 * @param no
	 * @return
	 */
	public boolean isTrainingOpen(int no) {
		return playerBuilgingManager.isTrainingOpen(no);
	}

	/**
	 * 获得训练台
	 * 
	 * @param no
	 * @return
	 */
	public TrainingBits getTrainingBits(int no) {
		try {
			logger.info("训练位id:" + no);
			TrainingBits tb = playerBuilgingManager.getTrainingBits(no);
			System.out.println("获得训练位：" + tb.toString());
			return playerBuilgingManager.getTrainingBits(no);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 是否有 num 个数据
	 * 
	 * @param itemId
	 * @param num
	 * @param type
	 * @return
	 */
	public boolean isEnough(int itemId, int num, byte type) {
		return playerStorageAgent.isDelete(itemId, num, type).getStatus();
	}

	/**
	 * 获取用户所属县城
	 * 
	 * @param nationId
	 * @return
	 */
	public Nation getUserNation(int nationId) {
		if (nationId == 0)
			return null;
		return NationManager.getInstance().getNation(nationId);
	}

	/**
	 * 根据道具id获得技能书
	 * 
	 * @param itemId
	 * @return
	 */
	public int getSkillIdFromBook(int itemId) {
		if (!getPlayerStorageAgent().isBook(itemId)) {
			logger.info("item id=" + itemId + " is not a skill book");
			return 0;
		}
		int skillId = getPlayerStorageAgent().getBookSkill(itemId);
		return skillId;
	}

	// 修改角色姓名
	public RoleData motifyName(String name) {
		logger.info("[motifyName]");

		int joy = GameConfig.motifyName;
		if (name == null || name.length() > 18) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new TipMessage("Does not conform to the format!",
						ProcotolType.USER_INFO_REQ, GameConst.GAME_RESP_FAIL), this
						.getUserInfo(), GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("不符合格式",
						ProcotolType.USER_INFO_REQ, GameConst.GAME_RESP_FAIL), this
						.getUserInfo(), GameUtils.FLUTTER);
			}
			
			return getData();
		}

		TipUtil tip = UsernameManager.getInstance().isNameLegal(name, 1);
		if (!tip.isResult()) {
			GameUtils.sendTip(new TipMessage(tip.getTip().getMessage(),
					ProcotolType.USER_INFO_REQ, GameConst.GAME_RESP_FAIL), this
					.getUserInfo(), GameUtils.FLUTTER);
			return getData();
		}

		if (gameDao.isNameExist(name)) {
			String msg = I18nGreeting.getInstance().getMessage(
					"name.alter.fail", null);
			GameUtils.sendTip(new TipMessage(msg, ProcotolType.USER_INFO_REQ,
					GameConst.GAME_RESP_FAIL), this.getUserInfo(),
					GameUtils.FLUTTER);
			return getData();
		} else {
			if (getData().getJoyMoney() >= joy) {
				saveResources(GameConfig.JOY_MONEY, -1 * joy);

//				logger.info("time=" + TimeUtils.now().format(TimeUtils.FORMAT1)
//						+ " uid=" + getId() + " uname=" + getData().getName()
//						+ " diamondNum=" + joy + " description=君主改名");
				GameLog.logSystemEvent(LogEvent.USE_DIAMOND,
						String.valueOf(GameConst.DIAMOND_CHANGENAME), "",
						String.valueOf(joy), String.valueOf(this.getId()));

				getData().setName(name);
//				saveData();
				/*
				 * GameUtils.sendTip( new TipMessage("ok",
				 * ProcotolType.USER_INFO_REQ, GameConst.GAME_RESP_SUCCESS),
				 * this .getUserInfo());
				 */
				String msg = I18nGreeting.getInstance().getMessage(
						"name.alter.success", null);
				GameUtils.sendTip(
						new TipMessage(msg, ProcotolType.USER_INFO_REQ,
								GameConst.GAME_RESP_SUCCESS), this
								.getUserInfo(), GameUtils.FLUTTER);
				// 缓存
				GameUtils.putToCache(this);
				// 如果有竞技场排名，更新
//				if (this.getData().getArenaId() != 0) {
//					Arena a = ArenaManager.getInstance().getArena(
//							(short) this.getData().getArenaId());
//					synchronized (a) {
//						a.setUserName(this.getData().getName());
//					}
//				}
				return getData();
			} else {
				String msg = I18nGreeting.getInstance().getMessage(
						"dimond.not.enough", null);
				GameUtils.sendTip(new TipMessage(msg,
						ProcotolType.USER_INFO_REQ, GameConst.GAME_RESP_FAIL),
						this.getUserInfo(), GameUtils.FLUTTER);
				return getData();
			}

		}
	}

	@Override
	public TipMessage getTip() {
		return tip;
	}

	@Override
	public void setTip(TipMessage tip) {
		this.tip = tip;
	}

	public int getStateId() {
		return this.getData().getNativeId() / 100 * 100;
	}

	public PlayerQuestAgent getPlayerQuestAgent() {
		return playerQuestAgent;
	}

	public void setPlayerQuestAgent(PlayerQuestAgent playerQuestAgent) {
		this.playerQuestAgent = playerQuestAgent;
	}

	/**
	 * 获取爵位限制数据
	 * 
	 * @return
	 */
	public PlayerNobility getPlayerNobility() {
		return PlayerLimitManager.getInstance().getPlayerNobilityMap()
				.get(getPromotedMG());
	}

	/**
	 * 升级爵位
	 */
	public TipUtil promotedMG() {
		logger.info("开始升级");
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("fail");
		int index = getPromotedMG();
		HashMap<Integer, PlayerNobility> map = PlayerLimitManager.getInstance()
				.getPlayerNobilityMap();
		if (index == map.size() - 1) {
			logger.info("以达到最高级,无法升级");
			String msg = I18nGreeting.getInstance().getMessage("title.max",
					null);
			return tip.setFailTip(msg);
		} else {
			int target = index + 1;
			PlayerNobility pn = map.get(target);
			if (pn != null) {
				int lordLevel = pn.getLordLevel();
				if (getData().getLevel() >= lordLevel) {
					if (getData().getAward() < pn.getCost()) {
						String msg = I18nGreeting.getInstance().getMessage(
								"feat.lack", null);
						return tip.setSuccTip(msg);
					}
					this.saveResources(GameConfig.AWARD, -1 * pn.getCost());
					this.setPromotedMG();// 升级
					logger.info("用户:" + getData().getUserid() + "|爵位:" + index
							+ "|升级:" + target + "|消耗:" + pn.getCost());
//					saveData();
					// ************************
					RespModuleSet rms = new RespModuleSet(
							ProcotolType.USER_INFO_RESP);
					rms.addModule(getData());
					AndroidMessageSender.sendMessage(rms, this);
					// ************************
					// 缓存
					GameUtils.putToCache(this);
					String msg = I18nGreeting.getInstance().getMessage("Knighthood.message."+getPromotedMG(),
					new Object[]{getData().getName()});
					NoticeManager.getInstance().sendSystemWorldMessage(msg);
					return tip.setSuccTip("");
				} else {
					logger.info("需要等级" + lordLevel);
					if(I18nGreeting.LANLANGUAGE_TIPS ==1){
						return tip.setFailTip("Need lord's Lv" + lordLevel);
					}else{
						return tip.setFailTip("需要等级" + lordLevel);
					}
					
				}
			} else {
				logger.info("固化数据无法错误");
				if(I18nGreeting.LANLANGUAGE_TIPS ==1){
					return tip.setFailTip("failed");
				}else{
					return tip.setFailTip("固化数据无法错误");
				}
				
			}
		}
	}

	

	/**
	 * 派兵 /派将领
	 * 
	 * @param player
	 * @param heroId
	 * @param soMsg
	 * @return
	 */
	public boolean dispatch(int heroId, String memo, String soMsg, byte type,
			long time) {
		logger.info("派兵状态 ---驻防将领id：" + heroId + " 派兵 --- 驻防士兵：" + soMsg);

		boolean flag1 = false;
		if (!"".equals(soMsg)) {
			flag1 = getPlayerBuilgingManager().dispatchSoldier(soMsg);// 派兵
		} else {
			logger.info("派兵为空！");
			flag1 = true;
		}
		if (flag1) {
			boolean flag = getPlayerHeroManager().motifyStatus(heroId, type,
					memo, soMsg, time);// 驻防
			if (flag) {
				return true;
			} else {
				logger.info("将领驻防或者");
				return false;
			}
		} else {
			logger.info("派兵失败！");
			return false;
		}
	}

	/**
	 * 回收士兵/将领
	 * 
	 * @param hero
	 * @param msg
	 * @return
	 */
	public boolean backHeroAndSoldier(int hero, String msg) {
		if ("".equals(msg)) {
			boolean flag = getPlayerHeroManager().motifyStatus(hero,
					GameConst.HEROSTATUS_IDEL, "", "", 0);
			return flag;
		} else {
			if (hero == 0) {
				getPlayerBuilgingManager().recoverSoldier(msg);
				return true;
			}
			boolean ff = getPlayerBuilgingManager().recoverSoldier(msg);
			if (ff) {
				boolean flag = getPlayerHeroManager().motifyStatus(hero,
						GameConst.HEROSTATUS_IDEL, "", "", 0);
				return flag;
			}
			return false;
		}
	}

	@Override
	public void tick() {
		if ((this.getState()==GameConst.STATE_ONLINE)&&(TimeUtils.nowLong() - this.saveTime > 7200 * 1000)) {// 半小时保存一次
			World.getInstance().savePlayer(this);
			this.saveTime = TimeUtils.nowLong();
			logger.info("定时保存玩家数据，id=" + this.getId());
		}
		// x秒未接受到心跳，则离线
		boolean isOffline = false;
		if (this.getHalt() == 0) {// 挂起状态
			if (TimeUtils.nowLong() - this.getHeartTime() > GameConfig.haltTime * 1000) {
				logger.info(GameConfig.haltTime+"秒未接受到心跳，则离线");
				isOffline = true;
			}
		} else {
			if (TimeUtils.nowLong() - this.getHeartTime() > GameConfig.heartTime * 1000) {
				logger.info(GameConfig.heartTime+"秒未接受到心跳，则离线");
				isOffline = true;
			}
		}
		if (isOffline) {
			if (this.getState() == GameConst.STATE_ONLINE) {
				this.getData().setLeaveTime(TimeUtils.nowLong());
				this.getCardManager().saveCards();// 保存牌局数据
				this.getRechargeManager().saveRechargeInfo();//保存玩家冲值信息
				UsernameManager.getInstance().releaseName(this);// 玩家在选择一个随机名后突然退出游戏
				GameUtils.sendTip(new TipMessage("", ProcotolType.SYSTEM_RESP,
						GameConst.GAME_RESP_FAIL), this.getUserInfo(), 4);
			}
			if(	World.getInstance().kickRole(this)){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					this.setTip(new TipMessage("Timeout …… Game saved.", -1, (byte) 0));
				}else{
					this.setTip(new TipMessage("超时...保存", -1, (byte) 0));
				}
				
				this.setHeartTime(TimeUtils.nowLong());
			}
		} else {
			// logger.info("check player tick="+this.id);
		}
	}

	/**
	 * 发送声音
	 * 
	 * @param sound
	 */
	public void sendSound(byte type, int level) {
		SoundVoice sound = SoundVoice.getInstance();
		if (sound != null) {
			sound.setType(type);
			sound.setLevel(level);
		}
		// ************************* rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.USER_INFO_RESP);// 模块消息
		rms.addModule(sound);
		AndroidMessageSender.sendMessage(rms, this);
		// ************************* rms
	}

	/**
	 * 转换成字符串保存 name : 等级 : 头像 : 爵位
	 */
	public String toCache() {
		PlayerBuilding main = getPlayerBuilgingManager().getMainCity();
		StringBuffer sb = new StringBuffer();
		sb.append(getData().getName()).append(";").append(getData().getLevel())
				.append(";").append(getData().getFaction()).append(";")
				.append(getData().getCityLevel());
		sb.append(";").append(getId()).append(";")
				.append(main.getOccupyUserId()).append(";")
				.append("hero-" + main.getOfficerId()).append(";")
				.append(main.getOfficerInfo());
		return sb.toString();
	}

	/**
	 * 开启关闭训练位
	 */
	public void checkTraining() {
		PlayerBuilding training = null;
		System.out.println(getId() + ":" + getData().getTitle());
		switch (getData().getTitle()) {
		case 2:// 开启训练位号 9
			training = getPlayerBuilgingManager().openTraining(9);
			training = getPlayerBuilgingManager().closeTraining(11);
			training = getPlayerBuilgingManager().closeTraining(10);
			// rms
			RespModuleSet rms1 = new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms1.addModule(training);
			AndroidMessageSender.sendMessage(rms1, this);
			// rms
			break;
		case 4:// 开启训练位号 10
			training = getPlayerBuilgingManager().openTraining(9);
			training = getPlayerBuilgingManager().openTraining(10);
			training = getPlayerBuilgingManager().closeTraining(11);
			// rms
			RespModuleSet rms2 = new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms2.addModule(training);
			AndroidMessageSender.sendMessage(rms2, this);
			// rms
			break;
		case 6:// 开启训练位号 11
			training = getPlayerBuilgingManager().openTraining(9);
			training = getPlayerBuilgingManager().openTraining(10);
			training = getPlayerBuilgingManager().openTraining(11);
			// rms
			RespModuleSet rms3 = new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms3.addModule(training);
			AndroidMessageSender.sendMessage(rms3, this);
			// rms
			break;
		case 8:// 开启训练位号 11
			training = getPlayerBuilgingManager().openTraining(9);
			training = getPlayerBuilgingManager().openTraining(10);
			training = getPlayerBuilgingManager().openTraining(11);
			// rms
			RespModuleSet rms4 = new RespModuleSet(ProcotolType.BUILDING_RESP);
			rms4.addModule(training);
			AndroidMessageSender.sendMessage(rms4, this);
			// rms
			break;
		}
	}

	/**
	 * 检测将领上限
	 * 
	 * @return
	 */
	public boolean checkHeroNumlimit() {
		// 判断是否已经达到当前上限
		PlayerLimit limit = GameDataManager.playerLimitManager.get(this
				.getData().getLevel());
		// 酒馆招将次数
		int num = this.getPlayerBuilgingManager().getPlayerTavern()
				.getItemTime();
		logger.info("当前酒馆可以多招将领=" + num);
		int maxNum = limit.getHeroNum() + num;
		if (this.getPlayerHeroManager().getPlayerHeroMap().size() >= maxNum) {
			return false;
		}
		return true;

	}
	
	/**
	 * 用户对象转换成cache对象
	 */
	public PlayerCache playerToCache(){
		PlayerCache playerCache = new PlayerCache();
		playerCache.setUserid((int) getData().getUserid());
		playerCache.setName(getData().getName());
		playerCache.setLevel(getData().getLevel());
		playerCache.setFaction(getData().getFaction());
		playerCache.setCityLevel(getData().getCityLevel());

		PlayerBuilding main = getPlayerBuilgingManager()
				.getMainCity();
		playerCache.setOccupyUserId(main.getOccupyUserId());
		playerCache.setOfficerId(main.getOfficerId());
		playerCache.setOfficerInfo(main.getOfficerInfo());
		
		//保存主线任务进度
		//playerCache.setTaskId(this.getPlayerQuestAgent().getCompleteMaxId((byte)1));
		return playerCache;
	}
	
	/**
	 * 是否可以换将
	 * @return
	 */
	public boolean isKeepUnder(){
		switch (getData().getTitle()) {
		case GameConst.TITLE_KING:// 国王
			if(NationManager.getInstance().viewWar() == 4 || (WarManager.getInstance().IS_FIGHT && WarManager.getInstance().FIGHT_TYPE == 2)){
				return false;
			}else{
				return true;
			}
		case GameConst.TITLE_GOVERNOR:// 州长
			if(NationManager.getInstance().viewWar() == 3 || (WarManager.getInstance().IS_FIGHT && WarManager.getInstance().FIGHT_TYPE == 1)){
				return false;
			}else{
				return true;
			}
		case GameConst.TITLE_MAYOR_CITY:// 市长
			if(NationManager.getInstance().viewWar() == 2 || (WarManager.getInstance().IS_FIGHT && WarManager.getInstance().FIGHT_TYPE == 0)){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * nation换防
	 * @param heroId
	 * @param soMsg
	 * @return
	 */
	public TipUtil keepUnder(int heroId,String soMsg){
		TipUtil tip = new TipUtil(ProcotolType.HERO_RESP);
		tip.setFailTip("");
		String str ="换防失败";
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			str = "Change hero failed";
		}
		StringBuffer sb = new StringBuffer("-----------------------------------------\n");
		sb.append("用户："+getId()+"|换防将领："+heroId+"|原来带兵："+soMsg+"\n");
		String newStr = getPlayerBuilgingManager().checkSolider(soMsg);
		if(!isKeepUnder()){
			sb.append("争夺战开启期间，不能换将\n");
			str = "争夺战开启期间，不能换将";
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				str = "Battle is in heat. Can't change heroes.";
			}
			sb.append("-----------------------------------------\n");
		}else{
			if ("".equals(newStr)) {
				sb.append("[checkSolider]士兵解析错误\n");
				sb.append("-----------------------------------------\n");
			}else{
				Nation n = NationManager.getInstance().getNation(getIdentity(getData().getTitle()));
				if(n != null){
					sb.append("nation:"+n.getId()+"|占领者是："+n.getOccupyUser()+"\n");
					//1.原驻防将领撤退，2.新驻防将领进驻
					if(n.getOccupyUser() == getId()){//是我占领的
						tip = n.keepUnder(this, heroId, newStr);
						sb.append(tip.getStr()+"\n");
						sb.append("-----------------------------------------\n");
						if(tip.isResult()){
							tip.setSuccTip("");
						}
					}else{
						sb.append("-----------------------------------------\n");
					}
				}else{
					sb.append("nation:"+n+"\n");
					sb.append("-----------------------------------------\n");
				}
			}
		}
		
		if(tip.isResult()){
			str = "换防成功";
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				str = "Change hero Successfully.";
			}
		}
		GameUtils.sendTip(new TipMessage(str, ProcotolType.ITEMS_REQ, GameConst.GAME_RESP_SUCCESS), getUserInfo(),GameUtils.FLUTTER);
		logger.info(sb.toString());
		return tip;
	}
	
	/**
	 * 获取某类士兵的等级
	 * @param type
	 * @return
	 */
	public int getSoliderLevel(byte type){
		return getPlayerBuilgingManager().getSoliderLeve(type);
	}

}