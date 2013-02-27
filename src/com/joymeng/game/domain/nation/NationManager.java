package com.joymeng.game.domain.nation;

import hirondelle.date4j.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.utils.PushSign;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.Instances;
import com.joymeng.game.db.dao.NationDAO;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.Power;
import com.joymeng.game.domain.role.PowerCalculate;
import com.joymeng.game.domain.world.GameDataManager;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;

public class NationManager extends PushSign implements Instances {
	public static Logger logger = Logger.getLogger(NationManager.class
			.getName());

	private static NationManager instance;//

	public static NationManager getInstance() {
		if (instance == null)
			instance = new NationManager();
		return instance;
	}

	// 需要推送用户
	public Map<Integer, Map<Integer, Object>> resourcesPush = new HashMap<Integer, Map<Integer, Object>>();

	@Override
	public Map<Integer, Map<Integer, Object>> getPushTypeMap() {
		// logger.info("nationManager : 总数量：" + resourcesPush.size());
		return resourcesPush;
	}

	// 需要推送用户
	// 军功加成
	public Map<Integer, Double> armyAdd = new HashMap<Integer, Double>();
	public int stateCount = 0;
	public int cityCount = 0;
	public int districtCount = 0;
	// **州总功勋
	//public Map<Integer, Integer> miliMap = new HashMap<Integer, Integer>();
	// **州总功勋
	// 政绩map
	//public Map<Integer, Double> achievMap = new HashMap<Integer, Double>();
	public Map<Integer, Double> userAchievMap = new HashMap<Integer, Double>();

	// 标准政绩
	public HashMap<Integer, Achievements> achievementsMap = new HashMap<Integer, Achievements>();

	/**
	 * 
	 */
	public HashMap<Integer, Nation> allNationMap = new HashMap<Integer, Nation>();
	public HashMap<Integer, Nation> nationMap = new HashMap<Integer, Nation>();
	public HashMap<Integer, Nation> countryMap = new HashMap<Integer, Nation>();
	public HashMap<Integer, Nation> stateMap = new HashMap<Integer, Nation>();
	public HashMap<Integer, Nation> allCityMap = new HashMap<Integer, Nation>();
	/**
	 * 特定国家下的州们
	 */
	public HashMap<Integer, ArrayList<Nation>> stateInCertainCountryMap = new HashMap<Integer, ArrayList<Nation>>();

	public HashMap<Integer, ArrayList<Nation>> cityMap = new HashMap<Integer, ArrayList<Nation>>();

	/**
	 * 特定市下的县们
	 */
	public HashMap<Integer, ArrayList<Nation>> countyMap = new HashMap<Integer, ArrayList<Nation>>();

	public HashMap<Integer, List<GoldMine>> goldMap = new HashMap<Integer, List<GoldMine>>();// 资源点

	public HashMap<Integer, Veins> allVeinsMap = new HashMap<Integer, Veins>();// 资源点

	public HashMap<Integer, GoldMine> allGoldMap = new HashMap<Integer, GoldMine>();// 资源点

	public HashMap<Integer, GoldHero> allGoldHero = new HashMap<Integer, GoldHero>();// 资源点
	public HashMap<Integer, GoldCharge> allGoldCharge = new HashMap<Integer, GoldCharge>();// 资源点产出

	/**
	 * 自动撤防
	 */
	public void autoRemoval() {
		for (GoldMine gold : allGoldMap.values()) {
			if (gold.getUserId() != 0) {
				if (TimeUtils.nowLong()
						- gold.getIntervalTime() >= 6 * 60 * 60 * 1000) {
					// logger.info("州：" + gold.getStateId() + "| 资源点："
					// + gold.getId() + "| 超时，撤出用户：" + gold.getUserName()
					// + "|将领：" + gold.getHeroId() + "...start ");
					TipUtil tip = gold.retreat(gold.getSoMsg(), (byte) 0,
							(byte) 0);
					// logger.info("*********autoRemoval=" +
					// tip.getResultMsg());
				}
			}
		}
	}

	/**
	 * 获得头衔
	 * 
	 * @param type
	 * @return
	 */
	public static int getTitle(byte type) {
		switch (type) {
		case 0:
			return 4;
		case 1:
			return 3;
		case 2:
			return 2;
		case 3:
			return 1;
		default:
			return 0;
		}
	}

	/**
	 * 类型
	 */
	public void deduAch() {
		// 扣除每天政绩
		for (Nation nation : nationMap.values()) {
			if (nation.getOccupyUser() != 0) {
				PlayerCharacter pp = World.getInstance().getPlayer(
						nation.getOccupyUser());
				Achievements standard = achievementsMap.get(getTitle(nation
						.getType()));
				if (pp != null && standard != null) {
					if (pp.getResourcesData(GameConfig.ACHIVE) > 0) {
						if (pp.recordRoleData(GameConfig.ACHIVE,
								-1 * standard.getConsumption())) {
							// return true;
						} else {
							pp.recordRoleData(GameConfig.ACHIVE,
									-1 * pp.getResourcesData(GameConfig.ACHIVE));
							// return true;
						}
					}
				}
			}
		}
		// logger.info("扣除每天政绩");
	}
	
	public void loadAchiev(){
		boolean isFalg = false;
		for (Nation na : countryMap.values()) {
			if(null == na.getRemark3()){
				isFalg = true;
				break;
			}
		}
		
		if(isFalg){
			setAchiev();
		}
	}
	
	/**
	 * 得到某区域的政绩
	 * @param nativeid
	 * @return
	 */
	public double getAchievs(int nativeid){
		Nation n  = allNationMap.get(nativeid);
		if(n != null){
			if(null == n.getRemark3() || "".equals(n.getRemark3())){
				logger.info("Nation:"+n+"|achiev:"+0.0);
				return 0.0;
			}else{
				logger.info("Nation:"+n+"|achiev:"+n.getRemark3());
				return Double.parseDouble(n.getRemark3());
			}
		}else{
			logger.info("Nation:"+n+"|achiev:"+0.0);
			return 0.0;
		}
	}
	

	/**
	 * Achiev 设置政绩加成 国家加成：每个国家的加成比例，初始50%，国战可以增加这个值
	 * 国王加成=0.7*〔1+0.2*（玩家政绩-国王标准政绩）/国王标准政绩〕*国家加成
	 * 州长加成=0.7*〔1+0.2*（玩家政绩-州长标准政绩）/州长标准政绩〕*国王加成
	 * 市长加成=0.7*〔1+0.2*（玩家政绩-市长标准政绩）/市长标准政绩〕*州长加成
	 * 县长加成=0.7*〔1+0.2*（玩家政绩-县长标准政绩）/县长标准政绩〕*市长加成
	 * 平民加成=0.7*〔1+0.2*（玩家政绩-平民标准政绩）/平民标准政绩〕*县长加成
	 * 
	 * @return
	 */
	public int setAchiev() {
		userAchievMap.clear();
		// 国家政绩 国王8
		for (Nation na : countryMap.values()) {
			Achievements standard = achievementsMap.get(4);
			if (na.getOccupyUser() == 0 && standard != null
					&& standard.getStandard() != 0) {
				double aa = 0.7
						* (1 + 0.2 * (0 - standard.getStandard())
								/ standard.getStandard())
						* Double.parseDouble(na.getRemark4());
				na.setRemark3(String.valueOf(aa));
				na.setRemark1(0);
				na.save();
				//achievMap.put(na.getId(), aa);
			} else if (standard != null && standard.getStandard() != 0) {
				PlayerCharacter pp = World.getInstance().getPlayer(
						na.getOccupyUser());
				if (pp != null) {
					double tt = ((pp.getData().getAchieve() - standard
							.getStandard()) / standard.getStandard()) > 1 ? 1
							: ((pp.getData().getAchieve() - standard
									.getStandard()) / standard.getStandard());
					double aa = 0.7 * (1 + 0.2 * tt)
							* Double.parseDouble(na.getRemark4());
					na.setRemark3(String.valueOf(aa));
					na.setRemark1(pp.getData().getAchieve());
					na.save();
					//achievMap.put(na.getId(), aa);
				}
			}
		}
		// 州政绩 州长 6
		for (Nation na : stateMap.values()) {
			Achievements standard = achievementsMap.get(3);
			double stateAchie = -1;
			if ( na.getOccupyUser() == 0 && standard != null
					&& standard.getStandard() != 0) {
				double aa = 0.7
						* (1 + 0.2 * (0 - standard.getStandard())
								/ standard.getStandard())
						* getAchievs(na.getId() / 1000 * 1000);
				stateAchie = aa;
				na.setRemark3(String.valueOf(aa));
				na.setRemark1(0);
				na.save();
				//achievMap.put(na.getId(), aa);
			} else if (standard != null && standard.getStandard() != 0) {
				PlayerCharacter pp = World.getInstance().getPlayer(
						na.getOccupyUser());
				if (pp != null) {
					double tt = ((pp.getData().getAchieve() - standard
							.getStandard()) / standard.getStandard()) > 1 ? 1
							: ((pp.getData().getAchieve() - standard
									.getStandard()) / standard.getStandard());
					double aa = 0.7 * (1 + 0.2 * tt)
							* getAchievs(na.getId() / 1000 * 1000);
					stateAchie = aa;
					na.setRemark3(String.valueOf(aa));
					na.setRemark1(pp.getData().getAchieve());
					na.save();
					//achievMap.put(na.getId(), aa);
				}
			}
			// 市政绩 市长 4
			for (Nation city : cityMap.get(na.getId())) {
				Achievements citys = achievementsMap.get(2);
				double cityAchie = -1;
				if (stateAchie != -1 && city.getOccupyUser() == 0
						&& citys != null && citys.getStandard() != 0) {
					double aacity = 0.7
							* (1 + 0.2 * (0 - citys.getStandard())
									/ citys.getStandard()) * stateAchie;
					//achievMap.put(city.getId(), aacity);
					cityAchie = aacity;
					city.setRemark3(String.valueOf(aacity));
					city.setRemark1(0);
					city.save();
				} else if (stateAchie != -1 && citys != null
						&& citys.getStandard() != 0) {
					PlayerCharacter pp = World.getInstance().getPlayer(
							city.getOccupyUser());
					if (pp != null) {
						double tt = ((pp.getData().getAchieve() - citys
								.getStandard()) / citys.getStandard()) > 1 ? 1
								: ((pp.getData().getAchieve() - citys
										.getStandard()) / citys.getStandard());
						double aacity = 0.7 * (1 + 0.2 * tt) * stateAchie;
						//achievMap.put(city.getId(), aacity);
						cityAchie = aacity;
						city.setRemark3(String.valueOf(aacity));
						city.setRemark1(pp.getData().getAchieve());
						city.save();
					}
				}
				// 县政绩 县长2
				for (Nation town : countyMap.get(city.getId())) {
					Achievements towns = achievementsMap.get(1);
					if (cityAchie != -1 && town.getOccupyUser() == 0
							&& towns != null && towns.getStandard() != 0) {
						double aacity = 0.7
								* (1 + 0.2 * (0 - towns.getStandard())
										/ towns.getStandard()) * cityAchie;
						//achievMap.put(town.getId(), aacity);
						town.setRemark3(String.valueOf(aacity));
						town.setRemark1(0);
						town.save();
					} else if (cityAchie != -1 && towns != null
							&& towns.getStandard() != 0) {
						PlayerCharacter pp = World.getInstance().getPlayer(
								town.getOccupyUser());
						if (pp != null) {
							double tt = ((pp.getData().getAchieve() - towns
									.getStandard()) / towns.getStandard()) > 1 ? 1
									: ((pp.getData().getAchieve() - towns
											.getStandard()) / towns
											.getStandard());
							double aacity = 0.7 * (1 + 0.2 * tt) * cityAchie;
							//achievMap.put(town.getId(), aacity);
							town.setRemark3(String.valueOf(aacity));
							town.setRemark1(pp.getData().getAchieve());
							town.save();
						}
					}
				}
			}
		}

		// logger.info(">>>政绩数量" + achievMap.size());
		return 0;
	}
	
	
	/**
	 * 得到州军工
	 * @param id
	 * @return
	 */
	public int getSatateMilitary(int id){
		Nation n = stateMap.get(id);
		if(n == null || "".equals(n.getRemark2())){
			 logger.info("州"+n+"|总军功:" + 0);
			return 0;
		}else{
			 logger.info("州"+n+"|总军功:"+n.getRemark2());
			return n.getRemark2();
		}
	}
	/**
	 * 军功
	 */
	public void satateMilitary() {
		for (Integer i : stateMap.keySet()) {
			int countMili = gameDao.getNationDAO().countMili(i);
			stateMap.get(i).setRemark2(countMili);
			//miliMap.put(i, countMili);
			stateMap.get(i).save();
			logger.info("州总军功:" + countMili);
		}
	}

	/**
	 * @return GET the nationMap
	 */
	public HashMap<Integer, Nation> getNationMap() {
		return nationMap;
	}

	/**
	 * @param SET
	 *            nationMap the nationMap to set
	 */
	public void setNationMap(HashMap<Integer, Nation> nationMap) {
		this.nationMap = nationMap;
	}

	public boolean checkMap() {
		if (nationMap == null || nationMap.size() == 0)
			return false;
		return true;
	}

	/**
	 * 保存全部资源 数据
	 */
	public void saveAll() {
		for (Veins v : allVeinsMap.values()) {
			saveVeins(v);
		}
		for (GoldMine g : allGoldMap.values()) {
			saveGoldMine(g);
		}
	}

	public void inits() {
		for (Nation n : nationMap.values()) {
			allNationMap.put(n.getId(), n);
			if (n.getType() == 0) {
				countryMap.put(n.getId(), n);// 所有国家
			}
			if (n.getType() == 1) {
				stateMap.put(n.getId(), n);// 所有州
			}
			if (n.getType() == 1) {// 如果是州
				int id = (n.getId() / 1000) * 1000;
				ArrayList<Nation> nationLst = stateInCertainCountryMap.get(id);
				if (null == nationLst) {
					nationLst = new ArrayList<Nation>();
					nationLst.add(n);
				} else {
					nationLst.add(n);
				}
				stateInCertainCountryMap.put(id, nationLst);
			}

			if (n.getType() == 2) {
				int id = (n.getId() / 100) * 100;
				ArrayList<Nation> nationLst = cityMap.get(id);
				if (nationLst == null) {
					nationLst = new ArrayList<Nation>();
					nationLst.add(n);
				} else {
					nationLst.add(n);
				}
				allCityMap.put(n.getId(), n);// 所有城市
				cityMap.put(id, nationLst);
			}
			// System.out.println("");

			// 如果是县
			if (n.getType() == 3) {
				int id = (n.getId() / 10) * 10;
				ArrayList<Nation> nationLst = countyMap.get(id);
				if (null == nationLst) {
					nationLst = new ArrayList<Nation>();
					nationLst.add(n);
				} else {
					nationLst.add(n);
				}
				countyMap.put(id, nationLst);

			}
		}
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * @throws ClassNotFoundException
	 */
	public void init(String path) throws ClassNotFoundException {
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate()
				.queryForList(NationDAO.SQL_SELECT_NATION);
		for (Map<String, Object> map : lst) {
			Nation n = gameDao.getNationDAO().loadObject(map);
			nationMap.put(n.getId(), n);

		}
		init2();
		init3();
		init4(path);
		init5(path);
		inits();
		//satateMilitary();
		loadAchievements(path);
		// initCamp();
		// setAchiev();
		// makeGold();
	}

	/**
	 * 加载基本政绩数据
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadAchievements(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path, Achievements.class);
		for (Object obj : list) {
			Achievements data = (Achievements) obj;
			achievementsMap.put(data.getId(), data);
		}
	}

	/**
	 * 查询
	 * 
	 * @param id
	 */
	public void init2() {
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate()
				.queryForList(NationDAO.SQL_SELECT_GOLDMINE);
		for (Map<String, Object> map : lst) {
			if (map != null) {
				GoldMine gold = gameDao.getNationDAO().loadGold(map);
				allGoldMap.put(gold.getId(), gold);
				List<GoldMine> goldLst = goldMap.get(gold.getStateId());
				if (goldLst == null) {
					goldLst = new ArrayList<GoldMine>();
					goldLst.add(gold);
				} else {
					goldLst.add(gold);
				}
				goldMap.put(gold.getStateId(), goldLst);
			}
		}
		// System.out.println(">>>>>>>>>>>>>>" + goldMap.size());
	}

	/**
	 * 查询
	 * 
	 * @param id
	 */
	public void init3() {
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate()
				.queryForList(NationDAO.SQL_SELECT_VEINS);
		// System.out.println("***************" + lst.size());
		for (Map<String, Object> map : lst) {
			if (map != null) {
				Veins veins = gameDao.getNationDAO().loadVeins(map);
				allVeinsMap.put(veins.getId(), veins);
			}
		}
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * @throws ClassNotFoundException
	 */
	public void init4(String path) throws ClassNotFoundException {
		// logger.info(">>>>>>>>>>>>>GoldHero...开始加载>>>>>>>>>>>>>");
		List<Object> list = GameDataManager.loadData(path, GoldHero.class);
		for (Object o : list) {
			GoldHero p = (GoldHero) o;
			allGoldHero.put(p.getId(), p);
		}
		// logger.info(">>>>>>>>>>>>>GoldHero...加载成功>>>>>>>>>>>>>");
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * @throws ClassNotFoundException
	 */
	public void init5(String path) throws ClassNotFoundException {
		// logger.info(">>>>>>>>>>>>>GoldHero...开始加载>>>>>>>>>>>>>");
		List<Object> list = GameDataManager.loadData(path, GoldCharge.class);
		for (Object o : list) {
			GoldCharge p = (GoldCharge) o;
			allGoldCharge.put(p.getId(), p);
		}
		// logger.info(">>>>>>>>>>>>>GoldCharge...加载成功>>>>>>>>>>>>>");
	}

	/**
	 * 随机区域
	 */
	public int myNation(int country, int state, int city) {
		if (state == 0) {
			state = randomSanto(100);
		}
		if (city == 0) {
			city = randomSanto(10);
		}
		int nation = randomSanto(1);

		int myNation = country * 1000 + state + city + nation;
		modifyNation(myNation, 1);
		// logger.info("随机区域:" + myNation);
		return myNation;
	}

	public void makeGold() {
		// for(int i =0 ;i < 4;i++){
		// MilitaryCamp camp = new MilitaryCamp();
		// camp.setFalseCamp(true);
		// gameDao.getNationDAO().addCamp(camp);
		// }
		// HashMap<Integer, Nation> map = stateMap;
		// System.out.println(map.size());
		// for (Nation nation : map.values()) {
		// for (int i = 0; i < 1; i++) {// 3个资源点
		// Veins veins = new Veins();
		// veins.setId(nation.getId() * 100 + 10 + i);
		// veins.setAddition(0);
		// if (i == 0)
		// veins.setIsMain((byte) 1);
		// veins.setRestTime(TimeUtils.nowLong());
		// veins.setStateId(nation.getId());
		// veins.setUserStateId(nation.getId());
		// veins.setType((byte) 1);
		// gameDao.getNationDAO().addVeins(veins);
		// }
		// for (int i = 0; i < 1; i++) {// 3个资源点
		// Veins veins = new Veins();
		// veins.setId(nation.getId() * 100 + 0 + i);
		// if (i == 0)
		// veins.setIsMain((byte) 1);
		// veins.setAddition(0);
		// veins.setRestTime(TimeUtils.nowLong());
		// veins.setStateId(nation.getId());
		// veins.setUserStateId(nation.getId());
		// veins.setType((byte) 0);
		// gameDao.getNationDAO().addVeins(veins);
		// }
		// for(int i = 3 ;i < 64; i ++ ){//61个金矿
		// GoldMine goldMine = new GoldMine();
		// int id = (8-i/8) *10 + 1;
		// goldMine.setId(nation.getId() * 1000 + (byte)(i/8+1)*100 + 1*10 +
		// i%8);
		// goldMine.setAddition(0);
		// goldMine.setChargeOut(allGoldCharge.get(id).getChageOut());
		// goldMine.setType((byte)1);
		// goldMine.setIntervalTime(TimeUtils.nowLong());
		// goldMine.setLevel((byte)(i/8+1));
		// goldMine.setRestTime(TimeUtils.nowLong());
		// goldMine.setStateId(nation.getId());
		// gameDao.getNationDAO().addGoldMine(goldMine);
		// }
		// for(int i = 3 ;i < 64; i ++ ){//61个金矿
		// GoldMine goldMine = new GoldMine();
		// int id = (byte)(8-i/8) *10 + 0;
		// goldMine.setId(nation.getId() * 1000 + (byte)(i/8+1)*100 + 0*10 +
		// i%8);
		// goldMine.setAddition(0);
		// goldMine.setChargeOut(allGoldCharge.get(id).getChageOut());
		// goldMine.setType((byte)0);
		// goldMine.setIntervalTime(TimeUtils.nowLong());
		// goldMine.setLevel((byte)(i/8+1));
		// goldMine.setRestTime(TimeUtils.nowLong());
		// goldMine.setStateId(nation.getId());
		// gameDao.getNationDAO().addGoldMine(goldMine);
		// }
		// }
	}

	// 国家id
	public void loadNation() {
		for (int c = 1; c < 4; c++) {
			// 3个国家
			Nation nation = getNations(c);
			gameDao.getNationDAO().addNation(nation);// 加入map
			for (int i = 1; i < 9; i++) {// 八州
				Nation nation1 = new Nation();
				stateCount++;
				nation1.setId(nation.getId() + i * 100);
				nation1.setName("州" + stateCount);
				nation1.setType((byte) 1);
				gameDao.getNationDAO().addNation(nation1);// 加入map
				for (int j = 1; j < 9; j++) {// 八市
					Nation nation2 = new Nation();
					cityCount++;
					nation2.setId(nation.getId() + i * 100 + j * 10);
					nation2.setName("市" + cityCount);
					nation2.setType((byte) 2);
					gameDao.getNationDAO().addNation(nation2);// 加入map
					for (int k = 1; k < 9; k++) {// 八县
						Nation nation3 = new Nation();
						districtCount++;
						nation3.setId(nation.getId() + i * 100 + j * 10 + k);
						nation3.setName("县" + districtCount);
						nation3.setType((byte) 3);
						gameDao.getNationDAO().addNation(nation3);// 加入数据库
					}
				}
			}
		}
	}
	String[] stateName = new String[]{"月夜","喜马拉雅","野火","祭都","望绿","撒哈拉","火岩","雄关"};
			

	
	public void updateNation(){
		for(Nation n: allNationMap.values()){
			if(n.getId() == 1000){
				updateNationName(n, "亚瑟之国");
			}
			if(n.getId() == 2000){
				updateNationName(n, "狮鹫之国");
			}
			if(n.getId() == 3000){
				updateNationName(n, "天使之国");
			}
		}
	}
	
	public void updateNationName(Nation n ,String name){
		if(n != null){
			n.retreatNation(0, true);
			n.setName(name);
			n.save();
		}
	}

	/**
	 * 转移地理位置 countyid 迁入县
	 */
	public boolean MigrationCounty(PlayerCharacter player, int countyin) {
		if (countyin == 0) {
			return true;
		}
		int countyout = player.getData().getNativeId();
		logger.info("*************用户："+player.getId()+"|迁入："+countyin+"|迁出："+countyout);
		if (modifyNation(countyout, -1)) {
			modifyNation(countyin, 1);
			player.setNativeId(countyin);
			if (countyin / 100 != countyout / 100) {// 撤出对应的金矿,矿脉
				player.getPlayerBuilgingManager().disarmMyAll(
						countyout / 100 * 100);
				 logger.info("*************撤出金矿矿脉");
			}
			if (countyin / 1000 != countyout / 1000) {// 特殊建筑切换
				player.getPlayerBuilgingManager().getspecalData();
				 logger.info("************特殊建筑切换");
			}
//			player.saveData();
			// rms
			RespModuleSet rms = new RespModuleSet(ProcotolType.USER_INFO_RESP);
			rms.addModule(player.getData());
			AndroidMessageSender.sendMessage(rms, player);
			// rms
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到国家
	 * 
	 * @param type
	 * @return
	 */
	public Nation getNations(int type) {
		// 3个国家
		Nation nation = new Nation();// 国家

		switch (type) {
		case 1:
			nation.setId(GameConst.COUNTRY_ID1 * 1000);
			nation.setName(GameConst.COUNTRY_NAME1);
			nation.setType((byte) 0);
			break;
		case 2:
			nation.setId(GameConst.COUNTRY_ID2 * 1000);
			nation.setName(GameConst.COUNTRY_NAME2);
			nation.setType((byte) 0);
			break;
		case 3:
			nation.setId(GameConst.COUNTRY_ID3 * 1000);
			nation.setName(GameConst.COUNTRY_NAME3);
			nation.setType((byte) 0);
			break;
		}
		return nation;
	}

	/**
	 * 是否有重名的城
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public boolean isSameName(int id, String name) {
		if (nationMap == null || nationMap.size() == 0)
			return true;
		for (Nation ee : nationMap.values()) {
			if (ee.getId() != id && name.equals(ee.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到对象
	 * 
	 * @param id
	 * @return
	 */
	public Nation getNation(int id) {
		Nation n = nationMap.get(id);
		if (n == null)
			return null;
		return n;
	}

	/**
	 * 返回对应数组
	 * 
	 * @param id
	 * @return
	 */
	public Nation[] getDetailed(int id) {
		Nation n = getNation(id);
		if (n == null)
			return null;
		Nation[] na = new Nation[4];
		int contyrId = id / 1000 * 1000;// 1100
		int stateId = (id / 100) * 100;// 1100
		int cityId = id / 10 * 10;// 1110
		Nation contry = getNation(contyrId);
		Nation state = getNation(stateId);
		Nation city = getNation(cityId);
		if (contry == null || state == null || city == null)
			return null;
		na[0] = contry;
		na[1] = state;
		na[2] = city;
		na[3] = n;
		return na;
	}

	/**
	 * 获得本市在线列表
	 * 
	 * @param nationId
	 * @return
	 */
	public ArrayList<PlayerCharacter> getCityOnline(int nationId) {
		// 获得在线列表
		ConcurrentHashMap<Integer, PlayerCharacter> playerList = gameWorld
				.getWorldRoleMap();
		Nation[] nation = getDetailed(nationId);
		if (nation != null) {
			int cityId = nation[2].getId();
			return GameUtils.getList((byte) 3, cityId, playerList);
		}
		return null;
	}

	/**
	 * 返回对应数组
	 * 
	 * @param id
	 * @return
	 */
	public String getName(int id) {
		Nation[] na = getDetailed(id);
		if (na == null || na.length == 0)
			return "";
		else {
			return na[0].getName() + "-" + na[1].getName() + "-"
					+ na[2].getName() + "-" + na[3].getName();
		}
	}

	/**
	 * 人数最少城池id //根据国家获取人数最少的县城
	 * 
	 * @param contryId
	 */
	public int minSanto(int contryId) {
		if (!checkMap()) {
			return 0;
		}
		Nation nation = getNation(contryId);
		int nationId = 0;
		int min = nation.getUserNum();
		if (min == 0) {
			nationId = contryId + randomSanto(100) + randomSanto(10)
					+ randomSanto(1);
			return nationId;// 随机县城
		}
		for (Nation na : nationMap.values()) {// 查询州
			if (na.getId() / 1000 * 1000 == contryId && na.getId() % 100 == 0
					&& na.getUserNum() <= min) {
				min = na.getUserNum();
				nationId = na.getId();
			}
		}
		for (Nation na : nationMap.values()) {// 查询市
			if (na.getId() / 100 * 100 == nationId && na.getUserNum() <= min
					&& na.getId() % 10 == 0) {
				min = na.getUserNum();
				nationId = na.getId();
			}
		}
		for (Nation na : nationMap.values()) {// 查询县
			if (na.getId() / 10 * 10 == nationId && na.getUserNum() <= min
					&& na.getId() % 10 != 0) {// 本国人数最少的县
				min = na.getUserNum();
				nationId = na.getId();
			}
		}
		return nationId;
	}

	/**
	 * 用户随机县城
	 * 
	 * @param contryId
	 * @return
	 */
	public TipUtil getRandomNation(PlayerCharacter pp, int contryId) {
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		int nationId = minSanto(contryId);
		if (nationId == 0) {
			// logger.info("[getRandomNation]随机县城出错");
			tip.setFailTip("[getRandomNation]随机县城出错");
			return tip;
		} else {
			// 成功
			if (modifyNation(nationId, 1)) {
				pp.getData().setNativeId(nationId);
//				pp.saveData();
				// rms
				RespModuleSet rms = new RespModuleSet(
						ProcotolType.USER_INFO_RESP);
				rms.addModule(pp.getData());
				AndroidMessageSender.sendMessage(rms, pp);
				// rms
				tip.setSuccTip("");
				return tip;
				// return nationId;
			}
			tip.setFailTip("修改人数出错");
			return tip;
		}
	}

	/**
	 * 修改人数
	 * 
	 * @param id
	 * @return
	 */
	public boolean modifyNation(int id, int num) {
		Nation[] na = getDetailed(id);
		if (na == null)
			return false;
		na[0].setUserNum(na[0].getUserNum() + num);
		na[1].setUserNum(na[1].getUserNum() + num);
		na[2].setUserNum(na[2].getUserNum() + num);
		na[3].setUserNum(na[3].getUserNum() + num);
		gameDao.getNationDAO().saveNation(na[0]);
		gameDao.getNationDAO().saveNation(na[1]);
		gameDao.getNationDAO().saveNation(na[2]);
		gameDao.getNationDAO().saveNation(na[3]);
		return true;
	}

	/**
	 * 随机
	 * 
	 * @param num
	 *            范围
	 * @return
	 */
	public static int randomSanto(int num) {
		return (int) (Math.random() * 8 + 1) * num;
	}

	public GoldMine saveGoldMine(GoldMine goldmine) {
		// logger.info("保存金矿数据");
		gameDao.getNationDAO().saveGoldMine(goldmine);
		return goldmine;
	}

	public Veins saveVeins(Veins veins) {
		// logger.info("保存资源点数据");
		gameDao.getNationDAO().saveVeins(veins);
		return veins;
	}

	/**
	 * 获取本州所有科技 数量
	 * 
	 * @param stateId
	 * @param type
	 * @return
	 */
	public List<Veins> getAllVeins(int stateId) {
		List<Veins> veLst = new ArrayList<Veins>();
		// List<Veins> allLst = allVeinsMap.get(stateId);
		for (Veins v : allVeinsMap.values()) {
			if (v.getUserStateId() == stateId) {
				veLst.add(v);
			}
		}
		return veLst;
	}

	/**
	 * 获取本州附属神赐
	 * 
	 * @param stateId
	 * @param type
	 * @return
	 */
	public int getVeinsAffiliated(int stateId, byte type) {
		int i = 0;
		for (Veins v : allVeinsMap.values()) {
			if (v.getUserStateId() == stateId && v.getType() == type
					&& v.getStateId() != stateId) {// 占领的科技 不算本州科技
				i++;
			}
		}
		return i;
	}

	/**
	 * 获取本州所有科技 数量
	 * 
	 * @param stateId
	 * @param type
	 * @return
	 */
	public List<Veins> getVeins(int stateId, byte type) {
		List<Veins> veLst = new ArrayList<Veins>();
		for (Veins v : allVeinsMap.values()) {
			if (v.getUserStateId() == stateId && v.getType() == type
					&& v.getStateId() != stateId) {// 占领的科技 不算本州科技
				veLst.add(v);
			}
			if ((v.getUserStateId() == stateId || v.getUserStateId() == 0)
					&& v.getStateId() == stateId && v.getType() == type) {// 自己家的科技
				veLst.add(v);
			}
		}
		return veLst;
	}

	/**
	 * 获取本州所有科技 数量
	 * 
	 * @param stateId
	 * @param type
	 * @return
	 */
	public List<Veins> getMyVeins(int stateId, byte type) {
		List<Veins> veLst = new ArrayList<Veins>();
		// List<Veins> allLst = allVeinsMap.get(stateId);
		veLst.add(getMainVeins(stateId, type));
		for (Veins v : allVeinsMap.values()) {
			if (v.getStateId() != stateId && v.getUserStateId() == stateId
					&& v.getType() == type) {
				veLst.add(v);
			}
		}
		if (veLst.size() > 3) {
			return veLst.subList(0, 3);
		}

		return veLst;
	}

	/**
	 * 获取所有金矿
	 * 
	 * @param stateId
	 * @param type
	 * @return
	 */
	public List<GoldMine> getGold(int stateId, byte type) {
		List<GoldMine> veLst = new ArrayList<GoldMine>();
		List<GoldMine> allLst = goldMap.get(stateId);
		for (GoldMine v : allLst) {
			if (v.getType() == type) {
				veLst.add(v);
			}
		}
		return veLst;
	}

	/**
	 * 判断本州所有 科技
	 * 
	 * @return
	 */
	public int getOccupyVeins(int stateId, byte type) {
		List<Veins> lst = getVeins(stateId, type);
		return lst.size();
	}

	// /**
	// * 获取 所有占领科技
	// * @return
	// */
	// public List<Veins> getOccMyVeinsLst(int stateId,byte type){
	// List<Veins> lst = getVeins(stateId, type);
	// List<Veins> vLst = new ArrayList<Veins>();
	// for(Veins v :lst){
	// if(v.getUserId() ){
	// vLst.add(v);
	// }
	// }
	// return vLst;
	// }

	/**
	 * 判断本州主矿归属 州
	 * 
	 * @return
	 */
	public Veins getMainVeins(int state, byte type) {
		// List<Veins> lst = getVeins(state, type);
		for (Veins v : allVeinsMap.values()) {
			if (v.getStateId() == state && v.getType() == type) {
				return v;
			}
		}
		return null;
	}

	/**
	 * 判断本州主矿归属 州
	 * 
	 * @return
	 */
	public boolean isMainIn(int stateid, byte type) {
		Veins v = getMainVeins(stateid, type);
		if (v != null && v.getUserStateId() == stateid)
			return true;
		return false;
	}

	/**
	 * 获取州名
	 * 
	 * @param id
	 * @return
	 */
	public String getStateName(int id) {
		Nation nation = getNation(id / 100 * 100);
		if (nation != null) {
			return nation.getName();
		}
		return "";
	}

	// /**
	// * 根据州id查找 附属id
	// * @param stateId
	// * @param type
	// * @return
	// */
	// public Veins getVeinsFrom(int stateId,byte type,int gurdStateId){
	// List<Veins> lst = getVeins(stateId, type);//获取本州所有科技
	// for(Veins veins: lst){
	// if(veins.getIsMain() == 0 && veins.getFormerStateId() == gurdStateId){
	// return veins;
	// }
	// }
	// return null;
	// }

	/**
	 * 根据州id查找 附属id
	 * 
	 * @param stateId
	 * @param type
	 * @return
	 */
	// public Veins getVeinsMain(int stateId,byte type,int occStateId){
	// List<Veins> lst = getVeins(stateId, type);//获取本州所有科技
	// for(Veins veins: lst){
	// if(veins.getIsMain() == 1 && veins.getUserStateId() == occStateId){
	// return veins;
	// }
	// }
	// return null;
	// }
	/**
	 * 获取基本加成
	 * 
	 * @param type
	 * @return
	 */
	public int basicAdd(int stateid, byte type) {
		return getOccupyVeins(stateid, type);
		// if(isMainIn(stateid,type)){
		// return getOccupyVeins(stateid, type);
		// }else{
		// return getOccupyVeins(stateid, type);
		// }
	}

	/**
	 * 查看科技剩余兵量
	 * 
	 * @param id
	 * @param type
	 * @return
	 */
	public int soldiersVeins(Veins veins) {
		if (veins == null) {
			return 1;
		} else {
			return FightUtil.getSoldierNum(veins.getBaseSoMsg());
		}
	}

	/**
	 * 查看我 的 占领数量
	 * 
	 * @param uid
	 * @param type
	 * @return
	 */
	public int myVeins(int stateId, int uid, byte type) {
		int multiple = 0;
		List<Veins> vLst = getVeins(stateId, type);
		for (Veins v : vLst) {
			if (v.getUserId() == uid) {
				multiple += 1;
			}
		}
		// logger.info("用户：" + uid + "|占领科技数量:" + multiple);
		return multiple;
	}

	/**
	 * 用户查看加成倍数
	 */
	public int veinsMultiple(int stateId, int uid, byte type) {
		int multiple = 1;
		// if(!isMainIn(stateId,type)){
		// return multiple;// 加成倍数
		// }
		multiple += getOccupyVeins(stateId, type) + myVeins(stateId, uid, type);// 默认加成倍数
		// logger.info("用户：" + uid + " 州：" + stateId + "  加成倍数：" + multiple);
		return multiple;
	}

	public int myadd(int stateId, int uid, byte type) {
		double multiple = veinsMultiple(stateId, uid, type);
		multiple += (NationManager.getInstance().armyAdd.get(uid) == null ? 0
				: NationManager.getInstance().armyAdd.get(uid));// World.getInstance().getPlayer(uid);
		 logger.info("用户：" + uid + " 州：" + stateId + "  加成倍数：" + multiple);
		return (int) (multiple * 1000);
	}

	/**
	 * 产出>>> 数量
	 * 
	 * @param uid
	 * @param type
	 * @param level
	 * @param g
	 * @return
	 */
	public int income(PlayerCharacter player, int stateId, int level,
			GoldMine gold) {
		StringBuffer sb = new StringBuffer();
		double tt = (myadd(stateId, player.getId(), gold.getType())) / 1000;
		GoldHero gh = allGoldHero.get((int) (level * 10 + gold.getType()));
		if (gh == null || gh.getInterval() == 0) {
			sb.append("武将等级 :" + level + " 类型：" + gold.getType() + " 无对应数据 \n");
			return 0;
		} else {
			int sc = gh.getInterval();
			int number = (int) ((TimeUtils.nowLong() - gold
					.getIntervalTime()) / 1000);
			if (number > 6 * 60 * 60) {
				number = 6 * 60 * 60;
			}
			// 收益
			int income = (int) (number * gold.getChargeOut() * tt / (sc));
			sb.append("收益时间差:" + number + "\n");
			sb.append("收益加成:" + tt + "\n");
			sb.append("基本数量:" + gold.getChargeOut() + "\n");
			sb.append("时间间隔:" + sc + "\n");
			sb.append("收益钱数" + income);
			logger.info(sb.toString());
			return income;
		}
	}

	/**
	 * 是否占领了金矿/0:金矿 1 资源建筑
	 */
	public GoldMine getMyOccupyGold(byte type, PlayerCharacter player) {
		List<GoldMine> goldLst = getGold(
				player.getData().getNativeId() / 100 * 100, type);
		for (GoldMine good : goldLst) {
			if (good.getUserId() == player.getData().getUserid()
					&& good.getType() == type)
				return good;
		}
		return null;
	}

	public static void main(String[] args) {
		Map<Integer, Double> map = new HashMap<Integer, Double>();
		map.put(1, 1.0);
		map.put(4, 4.0);
		map.put(2, 2.0);
		for (Integer i : map.keySet()) {
			map.remove(i);
		}
		// System.out.println(map.values());
		// SimpleDateFormat dateFormat = new SimpleDateFormat("EE");
		// SimpleDateFormat dateFormat1 = new SimpleDateFormat(
		// "yyyy-MM-dd hh:mm:ss");
		// Date date;
		// try {
		// date = dateFormat1.parse("2012-08-12 13:14:15");
		// // System.out.println(dateFormat.format(date));
		// Calendar caleder = Calendar.getInstance();
		// System.out.println(caleder.get(caleder.MINUTE));
		// } catch (java.text.ParseException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * 某国 州长战斗力列表
	 * 
	 * @param countyId
	 * @return
	 */
	public ArrayList<Power> getPower(int countyId) {
		ArrayList<Power> powerLst = new ArrayList<Power>();
		if (countyId % 1000 == 0) {
			Map<Integer, Power> powerData = PowerCalculate.getInstance()
					.getPowerData();
			if (powerData != null && powerData.size() > 0) {
				for (Integer i : powerData.keySet()) {
					if (i % 1000 != 0 && i / 1000 == countyId / 1000) {
						powerLst.add(powerData.get(i));
					}
				}
				Collections.sort(powerLst);
				Power county = powerData.get(countyId);// 国家
				if (county != null)
					powerLst.add(county);
			}
		}
		return powerLst;
	}

	/**
	 * 查看争夺战类型
	 */
	public byte viewWar() {
		DateTime now = TimeUtils.now();
		// 县长：每天 晚上10 - 早上11
		if (!Nation.isFight() || (now.getHour() >= 22 || now.getHour() < 11)) {
			return (byte) 1;
		} else if (now.getHour() >= 11
				&& now.getHour() < 19) {
			// 市长：早上11 - 晚上 7
			return (byte) 2;
		} else if (now.getHour() >= 20
				&& now.getHour() < 21
				&& now.getWeekDay() <= 7
				&& now.getWeekDay() > 1 && now.getMinute() < 31) {
			// 州长 ：晚上8 - 8：30 周一到周六
			return (byte) 3;
		} else if (now.getHour() >= 20
				&& now.getHour() < 21
				&&  now.getWeekDay() == 1 && now.getMinute() < 31) {
			// 国王 ：晚上8 - 8：30周日
			return (byte) 4;
		} else {
			return (byte) 0;
		}

	}

	/**
	 * 推荐国家
	 * 
	 * @return
	 */
	public int recommendCounty() {
		StringBuffer sb = new StringBuffer();

		int size = 1000000000;
		int country = 1000;
		for (Nation n : countryMap.values()) {
			sb.append("国家id:" + n.getId() + "|用户人数：" + n.getUserNum() + "\n");
			if (size >= n.getUserNum()) {
				country = n.getId();
				size = n.getUserNum();
			}
		}
		sb.append("推荐国家id:" + country + "\n");
		logger.info(sb.toString());
		return country;
	}

	/**
	 * 获取某州资源的第一页数据
	 * 
	 * @param type
	 * @param stateId
	 * @return
	 */
	public void getAllStateResource(byte type, int statenew, int oldstateid,
			int base) {
		ArrayList<ClientModuleBase> newLst = new ArrayList<ClientModuleBase>();
		ArrayList<ClientModuleBase> oldLst = new ArrayList<ClientModuleBase>();
		for (int p = 1; p < 8; p++) {
			int id = statenew * 1000 + p * 100 + type * 10;
			int id1 = oldstateid * 1000 + p * 100 + type * 10;
			if (allGoldMap != null && allGoldMap.size() > 0) {
				if (p == 1) {
					for (int i = 3; i < 8; i++) {
						int ids = id + i;
						GoldMine gg = allGoldMap.get(ids);
						if (gg != null) {
							newLst.add(gg);
							if (gg.getUserId() != 0
									&& !"".equals(gg.getHeroInfo())) {
								SimplePlayerHero sp = new SimplePlayerHero(
										null, gg.getHeroInfo());
								newLst.add(sp);
							}
						}
					}
				} else {
					for (int i = 0; i < 8; i++) {
						int ids = id + i;
						GoldMine gg = allGoldMap.get(ids);
						if (gg != null) {
							newLst.add(gg);
							if (gg.getUserId() != 0
									&& !"".equals(gg.getHeroInfo())) {
								SimplePlayerHero sp = new SimplePlayerHero(
										null, gg.getHeroInfo());
								newLst.add(sp);
							}
						}
					}
				}
				newLst.add(new RefreshResources());
				if (p == 1) {
					for (int i = 3; i < 8; i++) {
						int ids = id1 + i;
						GoldMine gg = allGoldMap.get(ids);
						if (gg != null) {
							oldLst.add(gg);
							if (gg.getUserId() != 0
									&& !"".equals(gg.getHeroInfo())) {
								SimplePlayerHero sp = new SimplePlayerHero(
										null, gg.getHeroInfo());
								oldLst.add(sp);

							}
						}
					}
				} else {
					for (int i = 0; i < 8; i++) {
						int ids = id1 + i;
						GoldMine gg = allGoldMap.get(ids);
						if (gg != null) {
							oldLst.add(gg);
							if (gg.getUserId() != 0
									&& !"".equals(gg.getHeroInfo())) {
								SimplePlayerHero sp = new SimplePlayerHero(
										null, gg.getHeroInfo());
								oldLst.add(sp);
							}
						}
					}
				}
				oldLst.add(new RefreshResources());
			} else {
				logger.info(" 资源类型：" + type + " 固化数据错误");
			}
			Map<Integer, Object> pushMap1 = getPushPlayerMap(statenew);
			if (pushMap1 != null) {
				for (Integer ids : pushMap1.keySet()) {
					PlayerCharacter playerOnline = World.getInstance()
							.getOnlineRole(ids);
					if (playerOnline != null) {
						sendOne(getsimple(playerOnline, type, statenew),
								new PlayerCharacter[] { playerOnline },
								ProcotolType.USER_INFO_RESP);
					}
				}
			}
			Map<Integer, Object> pushMap2 = getPushPlayerMap(oldstateid);
			if (pushMap2 != null) {
				for (Integer ids : pushMap2.keySet()) {
					PlayerCharacter playerOnline = World.getInstance()
							.getOnlineRole(ids);
					if (playerOnline != null) {
						sendOne(getsimple(playerOnline, type, oldstateid),
								new PlayerCharacter[] { playerOnline },
								ProcotolType.USER_INFO_RESP);
					}
				}
			}
			sendRmsList(newLst, statenew);
			sendRmsList(oldLst, oldstateid);
		}

		return;
	}

	public void calculationMilitary() {
		List<Integer> delKey = new ArrayList<Integer>();
		for (Integer id : armyAdd.keySet()) {
			PlayerCharacter pc = World.getInstance().getOnlineRole(id);
			if (pc != null) {
				armyAdd.put(id, pc.goldMili());
			} else {
				delKey.add(id);
			}
		}
		for (Integer id : delKey) {
			armyAdd.remove(id);
		}
	}

	/**
	 * 得到物品的SimpleResource
	 * 
	 * @param player
	 * @param type
	 * @param stateId
	 * @return
	 */
	public SimpleResource getsimple(PlayerCharacter player, byte type,
			int instate) {

		// byte p = 1;
		// int charge = 0;
		List<GoldMine> golds = goldMap.get(instate);
		if (golds != null) {
			GoldMine my = null;
			GoldMine one = null;
			for (GoldMine good : golds) {
				if (good.getLevel() == 1 && good.getType() == type) {
					one = good;
				}
				if (good.getUserId() == player.getId()
						&& good.getType() == type) {
					my = good;
				}
			}
			// if(my == null){
			// p = -1;
			// }else{
			// p = my.getLevel();
			// //charge = my.getChargeOut();
			// }
			return new SimpleResource(player, my, myVeins(player.getStateId(),
					(int) (player.getData().getUserid()), type), basicAdd(
					player.getStateId(), type), 0, (byte) -1, type);
		}
		// }
		return null;
	}

}
