package com.joymeng.game.domain.hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.StringUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.building.PlayerBuilding;
import com.joymeng.game.domain.hero.data.HeroData;
import com.joymeng.game.domain.hero.data.HeroLevel;
import com.joymeng.game.domain.hero.data.HeroLevelUp;
import com.joymeng.game.domain.hero.data.HeroName;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.game.net.response.BuildingResp;

/**
 * 将领数据管理
 * 
 * @author admin
 * @date 2012-5-2 TODO
 */
public class HeroManager {
	static final Logger logger = LoggerFactory.getLogger(HeroManager.class);
	// 将领基础数据
	// key=gourpId value=HeroData
	private HashMap<Integer, HeroData> HeroDataMap = new HashMap<Integer, HeroData>();
	// 将领级别
	private HashMap<Integer, HeroLevel> HeroLevelMap = new HashMap<Integer, HeroLevel>();
	// 将领姓名
	private List<String>[] heroName = new ArrayList[3];// (0 男 1女 2名将)
	List<HeroName>[] name = new ArrayList[4];// 按照类别得到0-3姓名
	// 将领升级---每一级升级所需要的经验
	private HashMap<Integer, HeroLevelUp> heroLevelUpMap = new HashMap<Integer, HeroLevelUp>();
	// 将领升级---升到该级别所需要的所有的经验数值
	private HashMap<Integer,Integer> heroLevelUpData=new HashMap<Integer,Integer>();
	// 将领刷新
	private List<HeroRefresh> heroRefreshList = new ArrayList<HeroRefresh>();

	// 生成数据后的将领
	// key=groupId value=hero
	private List<Hero>[] heroList = new ArrayList[GameConst.HERO_TYPE];// (0-5)固化数据1-6
	private List<Hero>[] heroLevelList ;
	// key=id value=hero
	private HashMap<Integer, Hero> heroMap = new HashMap<Integer, Hero>();
	// private boolean isLock = false;
	private static HeroManager instance;

	public HeroManager() {

	}

	public static HeroManager getInstance() {
		if (instance == null) {
			instance = new HeroManager();
		}
		return instance;
	}

	/**
	 * 载入将领相关数据
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void load(String path) throws Exception {
		loadHeroData(path);
		loadHeroLevel(path);
		loadHeroName(path);
		loadHeroLevelUp(path);
		loadHeroRefresh(path);
		create();
	}

	public void loadHeroRefresh(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, HeroRefresh.class);
		for (Object obj : list) {
			HeroRefresh data = (HeroRefresh) obj;
			heroRefreshList.add(data);
			// System.out.println("(" + data.getLevel() + "," + data.getExp()
			// + ")");
		}
	}

	/**
	 * 将领经验表
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void loadHeroLevelUp(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, HeroLevelUp.class);
		// System.out.println("list.size="+list.size());
		int total=0;
		for (Object obj : list) {
			HeroLevelUp data = (HeroLevelUp) obj;
			heroLevelUpMap.put(data.getLevel(), data);
			heroLevelUpData.put(data.getLevel(), total);
			total+=data.getExp();
			// System.out.println("(" + data.getLevel() + "," + data.getExp()
			// + ")");
		}
		// System.out.println("heroLevelUpMap.size="+heroLevelUpMap.size());
	}
	/**
	 * 获得升级到该级别所需要的总的经验数值
	 * @param level
	 * @return
	 */
	public int getTotalExp(int level){
		return heroLevelUpData.get(level);
	}
	/**
	 * 载入英雄数据
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void loadHeroData(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, HeroData.class);
		for (Object obj : list) {
			HeroData data = (HeroData) obj;
			HeroDataMap.put(data.getId(), data);
		}
	}

	/**
	 * 载入级别数据
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void loadHeroLevel(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, HeroLevel.class);
		for (Object obj : list) {
			HeroLevel data = (HeroLevel) obj;
			HeroLevelMap.put(data.getId(), data);
		}
	}

	/**
	 * 载入英雄名字
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void loadHeroName(String path) throws Exception {
		List<Object> list = GameDataManager.loadData(path, HeroName.class);

		for (int i = 0; i < name.length; i++) {
			name[i] = new ArrayList<HeroName>();
		}
		for (Object obj : list) {
			HeroName data = (HeroName) obj;
			// System.out.println("data.getType()=="+data.getType()+" data.getName()=="+data.getName());
			name[data.getType()].add(data);
		}
		// 将领姓名规则:(普通将领 姓名(type==0,1).称号(type==2 称号))||(名将（type==3）姓名)

		for (int i = 0; i < heroName.length; i++) {
			heroName[i] = new ArrayList<String>();
		}
		List<HeroName> nameTitle = name[2];// 称号
		for (int i = 0; i < name.length; i++) {
			if (i == 2) {// 跳过称号
				continue;
			}
			List<HeroName> t = name[i];
			if (i == 3) {// 名将
				for (HeroName n : t) {
					heroName[2].add(n.getName());
				}
			} else {
				// 遍历 称号
				for (HeroName hn : nameTitle) {
					for (HeroName n : t) {
						heroName[i].add((n.getName() + "." + hn.getName()));
					}
				}

			}
		}
	}

	/**
	 * 生成规则 1 读取武将表HeroData，根据武将品级id（levelId）去武将品级表中查找相应的数据 设置武将 "性别" 头像 说明 招募价格
	 * 天生技能 酒馆等级下限 酒馆等级上限
	 * 
	 * 2根据groupId，随机一个姓名 3读取武将品级表 HeroLevel 4赋值其他属性
	 */
	public void create() {
		// 初始化数组
		for (int i = 0; i < heroList.length; i++) {
			heroList[i] = new ArrayList<Hero>();
		}
		heroLevelList=new ArrayList[HeroLevelMap.size()];
		for (int i = 0; i < heroLevelList.length; i++) {
			heroLevelList[i] = new ArrayList<Hero>();
		}
		int num = 20;// 每一个将领生成次数
		// isLock = true;
		int id = 0;
		try {
			for (int i = 0; i < num; i++) {
				// 步骤1
				Iterator<Integer> it = HeroDataMap.keySet().iterator();
				while (it.hasNext()) {
					int key = it.next();
					Hero hero = new Hero();
					id++; // id数值是唯一的
					HeroData heroData = HeroDataMap.get(key);
					hero.setBaseId(heroData.getId());
					int groupId = heroData.getGroupId();// 获得武将组（1-6）
					int levelId = heroData.getLevel();// 获得品级
					// 步骤2
					int nameType = heroData.getNameType();// 获得姓名类型（1男，2女
															// 其他的是名将）
					String name = randomName(nameType);// 获得姓名
					// 步骤3
					HeroLevel heroLevel = HeroLevelMap.get(levelId);
					// 步骤4
					hero.setLevelId(heroLevel.getLevel());
					hero.setId(id);
					hero.setName(name);
					// 根据规则生成变化的数值 (攻击，生命，技能格）
					hero.setAttack(MathUtils.random(heroLevel.getMinAttack(),
							heroLevel.getMaxAttack()));
					hero.setMaxHp(MathUtils.random(heroLevel.getMinHp(),
							heroLevel.getMaxHp()));
					hero.setDefence(heroLevel.getDefence());
					// 随机获得技能格子
					int snum[] = new int[] { 4, 5, 6 };
					int srate[] = new int[] { heroLevel.getSkillRate1(),
							heroLevel.getSkillRate2(),
							heroLevel.getSkillRate3() };
					int skillNum = MathUtils.getRandomId2(snum, srate, 100);
					hero.setSkillNum((byte) skillNum);
					// 直接赋值的属性
					hero.setAttackAdd(heroLevel.getAttackAdd());
					hero.setDefenceAdd(heroLevel.getDefenceAdd());
					hero.setHpAdd(heroLevel.getHpAdd());
					hero.setSoldierNum(GameDataManager.soldierNumManager
							.getSoldierNumMap().get(1).getNum());// 1级带兵
					hero.setColor(heroLevel.getColor());
					// 赋值玩家属性
					hero.setIcon(heroData.getIcon());
					hero.setSex(heroData.getSex());
					hero.setMaxLevel(heroData.getMaxLevel());
					hero.setMinLevel(heroData.getMinLevel());
					hero.setBornSkill(heroData.getBornSkill());
					hero.setMemo(heroData.getMemo());
					hero.setMoney(heroData.getMoney());
					// 加入到map，list中,groupId=0-5
					if(groupId==6){
						hero.setSpecial(true);
					}
					heroList[groupId - 1].add(hero);
					heroLevelList[heroLevel.getId() - 1].add(hero);
					heroMap.put(hero.getId(), hero);
					// logger.info("-----------------name=" +
					// hero.getName()+"groupId="+groupId);
					// hero.print();
					// logger.info("name=" + hero.getName());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// export();
		// isLock = false;

	}

	/**
	 * 获得某一类型的随机姓名
	 * 
	 * @param nameType
	 * @return
	 */
	public String randomName(int nameType) {
		if (nameType == 1 || nameType == 2) {// 男//女
			int random = MathUtils.random(heroName[nameType - 1].size());
			// System.out.println("random ="+random);
			return heroName[nameType - 1].get(random);
		} else {// 名将姓名对应相应的id
			for (HeroName hn : name[3]) {
				if (nameType == hn.getId()) {
					return hn.getName();
				}
			}
			return "error";

		}
		// System.out.println("random name="+heroNameArray[nameType].size()+" nameType="+nameType);

	}

	/**
	 * 根据 herodata id范围随机一个将领
	 * 
	 * @param id1
	 * @param id2
	 * @return
	 */
	public Hero randomPropHero(int id1, int id2) {
		List<Hero> list = new ArrayList<Hero>();
		Iterator<Integer> it = heroMap.keySet().iterator();
		while (it.hasNext()) {
			Hero hero = heroMap.get(it.next());
			if (hero.getBaseId() >= id1 && hero.getBaseId() <= id2) {
				list.add(hero);
			}
		}
		// 混乱数组
		Collections.shuffle(list);
		for (Hero hero : list) {
			return hero;
		}
		return null;
	}

	/***
	 * 根据品级1-31获得将领
	 * 
	 * @param levelId
	 * @return
	 */
	public Hero randomHero(int levelId) {
		List<Hero> list = heroLevelList[levelId - 1];
		// 混乱数组
		Collections.shuffle(list);
		for (Hero hero : list) {
			return hero;
		}
		return null;
	}

	/**
	 * 获得一组武将
	 * 
	 * @param groupId
	 * @param level
	 *            根据酒馆刷新规则，获得相应的武将组（0-5）
	 * @return
	 */
	public Hero randomHero(int groupId, int level) {
		// logger.info("随机将领 groupId="+groupId);
		List<Hero> list = heroList[groupId];
		// 混乱数组
		Collections.shuffle(list);
		// 找到一个符合条件的英雄
		for (Hero hero : list) {
			// if (hero.getMinLevel() <= level && hero.getMaxLevel() >= level) {
			return hero;
			// }
		}
		logger.info("random hero error ,groupId=" + groupId);
		for (Hero hero : list) {
			logger.info("minlevel=" + hero.getMinLevel() + " maxLevel="
					+ hero.getMaxLevel());
		}
		return null;
	}

	public Hero getById(int id) {
		return heroMap.get(id);
	}

	// /**
	// * 导出将领表到xml文件，并且打印出来 方便进行检测
	// */
	// public void export() {
	// Iterator it = heroMap.keySet().iterator();
	// while (it.hasNext()) {
	// Hero hero = heroMap.get(it.next());
	// System.out.println("-----------------------");
	// hero.print();
	// }
	// }

	/**
	 * 获得指定级别的经验数值
	 * 
	 * @param level
	 */
	public int getExp(int level) {
		HeroLevelUp exp = heroLevelUpMap.get(level);// 获得当前级别上限
		return exp.getExp();
	}

	/**
	 * 当前最大级别
	 * 
	 * @return
	 */
	public int maxLevel() {
		return heroLevelUpMap.size();
	}

	/**
	 * 刷新將領
	 * 
	 * @param type
	 * @param player
	 */
	public boolean refresh(int type, PlayerCharacter player) {
		logger.info("hero refresh");
		RespModuleSet rms = new RespModuleSet(ProcotolType.HERO_RESP);// 模块消息
		PlayerBuilding pbuild = player.getTavern();
		if (pbuild == null) {
			logger.info("刷新将领 ，酒馆不存在，uid=" + player.getId());
			return false;
		}
//		Hero[] array = null;
		int level = pbuild.getBuildLevel();// 酒馆级别
		String heroStr = player.getTavernHero();
		long time = pbuild.getUpdateTime().getTime();// 结束时间
		logger.info("刷新将领 ,type =" + type + "酒馆级别=" + level + " 当前将领="
				+ heroStr);
		// 根据级别获得酒馆的相关数据
		int groupRate[] = GameDataManager.heroBarManager.getHeroBarMap().get(
				level);
//		logger.info(GameUtils.getIntArrayLog(groupRate, "groupRate"));
		if(time<0){
			logger.info("ERROR,hero manager time="+time);
		}
		int oldId[] = StringUtils.changeToInt(heroStr, ";");

		if (type == 0) {// 自动刷新判断时间
			// 未到时间，不能刷新
			if (oldId != null && TimeUtils.nowLong() < time) {
				// 发送未召唤的将领
				if (type == 0) {// 3个
//					array = new Hero[oldId.length];
					for (int i = 1; i < oldId.length; i++) {
						Hero hero = GameDataManager.heroManager
								.getById(oldId[i]);
						if (hero == null) {
							continue;
						}
//						array[i] = hero;
						rms.addModule(hero);
					}
				}
				logger.info("未到刷新时间，不能刷新，idlist=" + player.getTavernHero()
						+ " type=" + type);
				AndroidMessageSender.sendMessage(rms, player);
				return false;
			}
		}
		int num = 0;// 刷新数量
		if(oldId==null){//第一次登录
			oldId=new int[4];
		}
		if (type == 0 || type == 1) {//普通将领：0自动刷新，1手动刷新
			num = 3;// 刷新数量
			int freshNum = 0;//任务的刷新次数
//			array = new Hero[num];
			boolean b=false;//是否是第一次刷新
			if (type==0) {
				 freshNum = player.getData().getHeroRefresh1();
				 if(freshNum==0){//第一次刷新
					 for (HeroRefresh hr : heroRefreshList) {
							if (hr.getType() == 0) {
								if (freshNum == hr.getRefreshTimes()) {
									int heroId[]={hr.getNum1(),hr.getNum2(),hr.getNum3()};
//									logger.info("HeroRefresh =="+hr.getNum1()+" "+hr.getNum2()+" "+hr.getNum3());
//									array[0] = this.randomHero(hr.getNum1());
//									array[1] = this.randomHero(hr.getNum2());
//									array[2] = this.randomHero(hr.getNum3());
									for(int i=0;i<heroId.length;i++){
										Hero hero=this.randomHero(heroId[i]);
										rms.addModule(hero);
										oldId[i+1]=hero.getId();
									}
									b=true;
									break;
								}
							}
						}
						player.getData().setHeroRefresh1(1);
				 }
			} else {
				if(type==1){//第一次手动刷新
					 freshNum = player.getData().getHeroRefresh2();
					for (HeroRefresh hr : heroRefreshList) {
						if (hr.getType() == 1) {
							if (freshNum == hr.getRefreshTimes()-1) {
								int heroId[]={hr.getNum1(),hr.getNum2(),hr.getNum3()};
								for(int i=0;i<heroId.length;i++){
									Hero hero=this.randomHero(heroId[i]);
									rms.addModule(hero);
									oldId[i+1]=hero.getId();
								}
//								logger.info("HeroRefresh =="+hr.getNum1()+" "+hr.getNum2()+" "+hr.getNum3());
//								array[0] = this.randomHero(hr.getNum1());
//								array[1] = this.randomHero(hr.getNum2());
//								array[2] = this.randomHero(hr.getNum3());
								b=true;
//								rms.addModule(array[0]);
//								rms.addModule(array[1]);
//								rms.addModule(array[2]);
								break;
							}
						}
					}
					player.getData().setHeroRefresh2(freshNum + 1);
				}
			}
			if(!b){//不是第一次刷新
				for (int i = 0; i < num; i++) {
					int groupId = MathUtils.getRandomId2(new int[] { 0, 1, 2, 3, 4,
							5 }, groupRate, 100);// 根据酒馆规则获得相应的组别
					Hero hero = GameDataManager.heroManager.randomHero(groupId,
							player.getData().getLevel());
					if (hero == null) {
						logger.info("刷新将领 hero is null  groupId=" + groupId);
						break;
					}
					rms.addModule(hero);
					oldId[i+1]=hero.getId();
//					array[i] = hero;
					logger.info("color=" + hero.getColor() + " groupId==" + groupId
							+ " heroId=" + hero.getId());
				}
			}
		
//			String newStr = GameUtils.changeIds(array, (byte) 0, heroStr);
			String newStr=StringUtils.recoverNewStr(StringUtils.changeToString(oldId), ";");
			logger.info("新的将领1=" + newStr);
			// 保存id
			player.setTavernHero(newStr);
			// 设置刷新后的时间
			player.setHeroTime();
		} else {
			num = 1;// 刷新数量,刷新名将
//			array = new Hero[num];
			Hero hero = null;
			if (type == 2) {
				hero = GameDataManager.heroManager.getById(oldId[0]);
			} else {
				hero = GameDataManager.heroManager.randomHero(5, player
						.getData().getLevel());
			}
			if (hero == null) {
				logger.info("刷新将领 为空，groupId=" + 5);
				GameUtils
						.sendTip(new TipMessage("",
								ProcotolType.HERO_RESP,
								GameConst.GAME_RESP_FAIL,
								(byte) ProcotolType.HERO_REFRESH), player
								.getUserInfo(),GameUtils.FLUTTER);
				return false;
			}
//			array[0] = hero;
			rms.addModule(hero);
			oldId[0]=hero.getId();
			// logger.info("hero money="+hero.getMoney());
			// hero.print();
			// heroTime = player.heroTime();
			// 保存id
//			String newStr = GameUtils.changeIds(array, (byte) 0, heroStr);
			String newStr=StringUtils.recoverNewStr(StringUtils.changeToString(oldId), ";");
			logger.info("新的将领2=" + newStr);
			player.setTavernHero(newStr);
		}
		List<ClientModule> list = rms.getModuleList();
		for (ClientModule module : list) {
			if (module.getModuleType() == ClientModule.NTC_DTCD_HERO) {
				((Hero) module).getId();
//				logger.info("将领刷新 check，send to client hero id="
//						+ ((Hero) module).getId());
			}
		}
		logger.info("可以刷新,id=" + type + "，idlist=" + player.getTavernHero());
		//写入日志  玩家 在日期xx刷新将领type,将领id列表
		GameLog.logPlayerEvent(player, LogEvent.HERO_FRESH, new LogBuffer().add(type).add(player.getTavernHero()));

		rms.addModule(player.getData());
		rms.addModule(pbuild);
		logger.info(pbuild.print());
		logger.info("time="+pbuild.getUpdateTime());
		AndroidMessageSender.sendMessage(rms, player);
		// World.getInstance().savePlayer(player);
		GameUtils
		.sendTip(new TipMessage("",
				ProcotolType.HERO_RESP,
				GameConst.GAME_RESP_SUCCESS,
				(byte) ProcotolType.HERO_REFRESH), player
				.getUserInfo(),GameUtils.FLUTTER);
		BuildingResp resp = new BuildingResp();
		resp.setType((byte)-1);
		resp.setUserInfo(player.getUserInfo());
		 
		return true;
	}
}
