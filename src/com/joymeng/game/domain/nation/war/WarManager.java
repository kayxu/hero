package com.joymeng.game.domain.nation.war;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.common.Instances;
import com.joymeng.game.db.dao.NationDAO;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.PowerCalculate;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.World;

/**
 * 争夺战 管理类
 * 
 * @author Administrator
 * 
 */
public class WarManager extends PushSign implements Instances {
	public static int PER_SECOND = 1;// 占领每秒积分
	public static int PER_BARRACKS = 30;// 占领军营积分
	public static int CITY_MONSTER = 8;// 市长争夺战怪物
	public static int STATE_MONSTER = 16;// 州长争夺战怪物
	public static int COUNTY_MONSTER = 16;// 国王争夺战怪物

	public static int CD_TIME = 10;// CD 时间

	public static NationManager naMgr = NationManager.getInstance();

	public static Logger logger = Logger.getLogger(WarManager.class);
	public static WarManager instance;

	public static WarManager getInstance() {
		if (instance == null) {
			instance = new WarManager();
		}
		return instance;
	}

	public Map<Integer, Integer> top = new HashMap<Integer, Integer>();
	// 需要推送用户
	public Map<Integer, Map<Integer, Object>> warPush = new HashMap<Integer, Map<Integer, Object>>();

	public Map<Integer, Map<Integer, Object>> getPushTypeMap() {
		return warPush;
	}

	public HashMap<Integer, MilitaryCamp> allCamp = new HashMap<Integer, MilitaryCamp>();// 军营
	public HashMap<Integer, StrongHold> allHold = new HashMap<Integer, StrongHold>();// 据点
	public HashMap<Integer, UserWarData> warData = new HashMap<Integer, UserWarData>();// 用户战争数据
	public ArrayList<Nation> allNations = new ArrayList<Nation>();// 争夺的区域
	// 城市
	Map<Integer, List<Nation>> achieveCityMap = new TreeMap<Integer, List<Nation>>();
	// 州
	Map<Integer, List<Nation>> achieveStateMap = new TreeMap<Integer, List<Nation>>();
	// 国家
	Map<Integer, List<Nation>> achieveCountryMap = new TreeMap<Integer, List<Nation>>();

	public void warInit() {
		loadAchieve((byte) 0);
		loadAchieve((byte) 1);
		loadAchieve((byte) 2);
		initCamp();
		loadData();
		calcResults();
		stopWar(true);
		// startWar((byte)0, 4);
	}

	public static boolean IS_FIGHT = false;// 争夺战是否开启
	public static int FIGHT_START = 0;// 开始时间
	public static int FIGHT_END = 0;// 结束时间
	public static byte FIGHT_TYPE = -1;// 类型
	public static byte FIGHT_NUM = 0;// 开始区域数量
	public static boolean IS_REFRESH = false;// 是否刷新
	public static int CAMP_NUM = 0;// 军营数量

	public static Integer ROUND = 0;// 开启轮数

	/**
	 * 需要攻击多少次
	 * 
	 * @param type
	 * @return
	 */
	public static int getStrongNum(int nationId) {
		if (nationId % 1000 == 0) {
			return 8;
		} else if (nationId % 100 == 0) {
			return 8;
		} else if (nationId % 10 == 0) {
			return 4;
		}
		return 0;
	}

	/**
	 * 定时刷新据点信息
	 * 
	 * @param start
	 * @param end
	 */
	public void strongJob() {
		if (IS_FIGHT) {// 在时间段内
			int now = (int) (TimeUtils.nowLong() / 1000);
			logger.info("怪物总数量==============" + allHold.size());
			for (StrongHold hold : allHold.values()) {
				hold.refreshHold(now);
			}
		}
	}

	/**
	 * 刷新城主积分
	 */
	public void refultUserIntegral() {
		if (IS_FIGHT) {// 在时间段内
			for (MilitaryCamp camp : allCamp.values()) {
				if (camp.getNativeId() != 0 && camp.getOccCache() != null
						&& camp.isFalseCamp()) {
					camp.calculationWarIntegral(camp.getOccCache().getUserid(),
							false);
					camp.save();
				}
			}
		}
	}

	public void saveCamp(MilitaryCamp camp) {
		gameDao.getNationDAO().saveCamp(camp);
	}

	/**
	 * 保存全部
	 */
	public void saveAllCamp() {
		for (MilitaryCamp camp : allCamp.values()) {
			gameDao.getNationDAO().saveCamp(camp);
		}
	}

	public void savewarData(UserWarData warDa) {
		gameDao.getNationDAO().saveUserWar(warDa);
	}

	/**
	 * 保存全部
	 */
	public void saveAllWarData() {
		for (UserWarData warDa : warData.values()) {
			gameDao.getNationDAO().saveUserWar(warDa);
		}
	}

	public void sendWorld(byte type, Nation nation, String name) {
		String msg = "";
		int x = 1;
		switch (FIGHT_TYPE) {
		case 0:
			if (type == 0) {// 开始
				if (ROUND == 16)
					msg = I18nGreeting.getInstance().getMessage(
							"cityWarStart16", new Object[] {});
				else {
					msg = I18nGreeting.getInstance().getMessage("cityWarStart",
							new Object[] { ROUND });
				}
			} else if (type == 1) {// 结束
				if (ROUND == 16)
					msg = I18nGreeting.getInstance().getMessage("cityWarEnd16",
							new Object[] {});
				else if (ROUND == 15) {
					msg = I18nGreeting.getInstance().getMessage("cityWarEnd15",
							new Object[] {});
				} else {
					msg = I18nGreeting.getInstance().getMessage("cityWarEnd",
							new Object[] { ROUND, ROUND + 1 });
				}
			} else {// 成为长官
				if (nation != null) {
					Nation state = NationManager.getInstance().getNation(
							nation.getId() / 100 * 100);
					Nation coun = NationManager.getInstance().getNation(
							nation.getId() / 1000 * 1000);
					if (state != null && coun != null) {
						msg = I18nGreeting.getInstance().getMessage(
								"beCityMayor" + x,
								new Object[] { ROUND, name, coun.getName(),
										state.getName(), nation.getName() });
						x++;
					}
				}
			}

			break;
		case 1:
			if (type == 0) {
				msg = I18nGreeting.getInstance().getMessage("stateStart",
						new Object[] {});
			} else if (type == 1) {
				msg = I18nGreeting.getInstance().getMessage("stateEnd",
						new Object[] {});
			} else {// 成为长官
				if (nation != null) {
					Nation country = NationManager.getInstance().getNation(
							nation.getId() / 1000 * 1000);
					if (country != null) {
						msg = I18nGreeting.getInstance().getMessage(
								"bestateMayor",
								new Object[] { name, country.getName(),
										nation.getName() });
					}
				}
			}

			break;
		case 2:
			if (type == 0) {
				msg = I18nGreeting.getInstance().getMessage("countryStart",
						new Object[] {});
			} else if (type == 1) {
				msg = I18nGreeting.getInstance().getMessage("countryEnd",
						new Object[] {});
			} else {
				if (nation != null) {
					msg = I18nGreeting.getInstance().getMessage("beKing",
							new Object[] { nation.getName(), name });
				}
			}

			break;
		}
		if (!"".equals(msg)) {
			if (type == 1) {// 关闭
				GameUtils.sendWolrdMessage(new TipMessage(msg,
						ProcotolType.CHAT_RESP, GameConst.GAME_RESP_SUCCESS,
						type, FIGHT_TYPE), (byte) 1);
			} else {// 开始
				GameUtils.sendWolrdMessage(new TipMessage(msg,
						ProcotolType.CHAT_RESP, GameConst.GAME_RESP_SUCCESS,
						ProcotolType.NATION_RESP, FIGHT_TYPE), (byte) 1);
			}

		}
		// logger.info("世界消息>>>" + msg);
	}

	/**
	 * 战役开始//市,州,国家--0,1,2
	 */
	public byte startWar(boolean isAuto, int[] nationMap) {
		calcResults();// 计算
		stopWar(false);// 先清空数据
		if (IS_FIGHT) {
			return (byte) -1;
		}
		top.clear();
		// 获取随机区域 ，完全自动
		allNations = randomNation(FIGHT_TYPE, FIGHT_NUM, isAuto, nationMap);
		// System.out.println(allNations);
		if (allNations != null && allNations.size() > 0) {
			for (Nation na : allNations) {
				calcNationCamp(na, CAMP_NUM);
			}
			IS_FIGHT = true;
			++ROUND;
			sendWorld((byte) 0, null, "");
			logger.info("类型：" + FIGHT_TYPE + "|开始时间："
					+ TimeUtils.chDate(TimeUtils.nowLong()) + "|城市："
					+ allNations.toString());

			GameLog.logSystemEvent(LogEvent.WARSTART,
					"开始时间：" + TimeUtils.chDate(TimeUtils.nowLong()),
					"城市：" + allNations.toString(), "类型：" + FIGHT_TYPE, "数量"
							+ FIGHT_NUM, "自动开启：" + isAuto, "军营数量：" + CAMP_NUM,
					"轮次：" + ROUND);
			// WarThread th = new WarThread();
			// th.run();// 开启线程
			// if(IS_FIGHT && th.DOWNTIME == 1){//重新开启
			// }
			// startWarNoThread();
			startWar();
			// startWarNoThread();
			// logger.info("类型：" + FIGHT_TYPE + "|结束时间："
			// + CalendarUtil.format(TimeUtils.nowLong()));
			return (byte) 0;
		} else {
			logger.info("类型：" + FIGHT_TYPE + "|开始时间："
					+ TimeUtils.chDate(TimeUtils.nowLong()) + "|城市："
					+ allNations);
			return (byte) 1;
		}
	}

	/**
	 * 开启
	 */
	public void startWar() {
		WarThread th = new WarThread("test");
		th.run();// 开启线程
		if (IS_FIGHT && FIGHT_TYPE >= 0 && th.DOWNTIME == 1) {// 重新开启
			startWar();
		}
	}

	/**
	 * 计算 活动结果
	 */
	public void calcResults() {
		for (MilitaryCamp camp : allCamp.values()) {
			if (camp.isFalseCamp && camp.getOccCache() != null) {
				// 计算最后占领的玩家积分
				camp.calculationWarIntegral(camp.getOccCache().getUserid(),
						false);
			}
		}
		if (allNations != null && allNations.size() > 0) {
			for (Nation na : allNations) {
				PlayerCharacter pp = beExecutiveChief(na);
				// logger.info("区域:" + na.getId());
				if (pp != null) {
					// logger.info("区域:" + na.getId() + "|玩家:" + pp.getId()
					// + " 成为区域长官");
					// 发送与去玩家消息
					// sendWorld((byte)2,na, pp.getData().getName());
					sendSysMsg(na, pp.getData().getName());
				} else {
					StopWar stop = new StopWar();
					switch (FIGHT_TYPE) {
					case 0:
						stop.setEventId(1);
						break;
					case 1:
						stop.setEventId(2);
						break;
					case 2:
						stop.setEventId(3);
						break;
					default:
						stop.setEventId(2);
						break;
					}
					String str = "争夺战结束,无人胜利";
					if(I18nGreeting.LANLANGUAGE_TIPS ==1){
						str = "Battle ends. No one wins.";
					}
					stop.setPusgMsg(str);
					logger.info("===========calcResults");
					sendRmsOne(stop, na.getId());
				}

			}
		}
	}

	/**
	 * 战役结束 isAuto
	 */
	public void stopWar(boolean isAuto) {
		StringBuffer sb = new StringBuffer();
		// 清空状态
		if (allNations != null && allNations.size() > 0) {
			for (Nation na : allNations) {
				sb.append(na.getOccupyUser()+"|");
				eventSet(na, 0);
			}
		}
		for (MilitaryCamp camp : allCamp.values()) {
			camp.resetAllThis();// 清空数据
		}
		// 清空userWarData
		clearWarData();
		if (IS_FIGHT) {
			sendWorld((byte) 1, null, "");
		}
		if (IS_FIGHT && FIGHT_TYPE > 0 && isAuto) {
			// 州长国王检测
			PowerCalculate.getInstance().calaNationState();
			logger.info(PowerCalculate.getInstance().getPowerData());
		}
		if (IS_FIGHT) {
			GameLog.logSystemEvent(LogEvent.WARSTART, "结束时间："
					+ TimeUtils.chDate(TimeUtils.nowLong()), "类型："
							+ FIGHT_TYPE, "数量" + FIGHT_NUM, "军营数量："
							+ CAMP_NUM, "轮次：" + ROUND+"|城市："
									+ allNations+"|胜者："+sb.toString());
		}
		//结束
		IS_FIGHT = false;
		
		clearAll();

	}

	/**
	 * 清空全部userwardata数据
	 */
	public void clearWarData() {
		warData.clear();
		gameDao.getNationDAO().deletaUserWarAll();
	}

	/**
	 * 初始设置区域camp
	 */
	public void calcNationCamp(Nation na, int campNum) {
		int i = 1;
		for (MilitaryCamp camp : allCamp.values()) {
			if (campNum > 0) {
				if (camp.getNativeId() == 0) {
					camp.setNativeId(na.getId());
					camp.setOccTime(0);
					camp.setGameStartTime((int) (TimeUtils.nowLong() / 1000));
					initStrong(camp);
					camp.save();
					--campNum;
				}
			}
			if (i > 0) {
				if (camp.getNativeId() == 0 && camp.isFalseCamp) {
					camp.setOccTime(0);
					camp.setNativeId(na.getId());
					camp.setOccCache(MongoServer.getInstance()
							.getLogServer().getPlayerCacheDAO()
							.findPlayerCacheByUserid(na.getOccupyUser()));
					camp.setHeroId(na.getHeroId());
					camp.setGameStartTime((int) (TimeUtils.nowLong() / 1000));
					camp.setSoliderInfo(na.getSoldierInfo() == null ? "" : na
							.getSoldierInfo());
					camp.save();
					top.put(na.getId(), na.getOccupyUser());
					i--;

				}
			}
		}
	}

	/**
	 * 加载类型map 市,州,国家
	 * 
	 * @param type
	 */
	public void loadAchieve(byte type) {
		if (type == 0) {// 城市
			for (Nation n : naMgr.allCityMap.values()) {// 所有地区
				List<Nation> lst = achieveCityMap.get(n.getRemark1());
				if (lst != null) {
					lst.add(n);
				} else {
					lst = new ArrayList<Nation>();
					lst.add(n);
					achieveCityMap.put(n.getRemark1(), lst);
				}

			}
		}
		if (type == 1) {// 州
			for (Nation n : naMgr.stateMap.values()) {// 所有地区
				List<Nation> lst = achieveStateMap.get(n.getRemark1());
				if (lst != null) {
					lst.add(n);
				} else {
					lst = new ArrayList<Nation>();
					lst.add(n);
					achieveStateMap.put(n.getRemark1(), lst);
				}
			}
		}
		if (type == 2) {// 国家
			for (Nation n : naMgr.countryMap.values()) {// 所有地区
				List<Nation> lst = achieveCountryMap.get(n.getRemark1());
				if (lst != null) {
					lst.add(n);
				} else {
					lst = new ArrayList<Nation>();
					lst.add(n);
					achieveCountryMap.put(n.getRemark1(), lst);
				}
			}
		}
		StringBuffer sb = new StringBuffer("\n");
		sb.append("Type:" + type + "\n");
		sb.append("城市数据加载：" + achieveCityMap.size() + "|"
				+ achieveCityMap.toString() + "\n");
		sb.append("州数据加载：" + achieveStateMap.size() + "|"
				+ achieveStateMap.toString() + "\n");
		sb.append("国家数据加载：" + achieveCountryMap.size() + "|"
				+ achieveCountryMap.toString() + "\n");
		// System.out.println("sss");
		logger.info(sb.toString());
	}

	/**
	 * 随机是查询
	 * 
	 * @param type
	 *            类型,市,州,国家
	 * @param num
	 *            随机数量
	 * @param isAuto
	 *            是否自己设定
	 * @param nationMap
	 *            nationid map
	 * 
	 */
	public ArrayList<Nation> randomNation(byte type, int num, boolean isAuto,
			int[] nationMap) {
		if (!isAuto) {
			if (nationMap != null && nationMap.length > 0) {
				ArrayList<Nation> getLst = new ArrayList<Nation>();
				for (Integer i : nationMap) {
					if (type == 0) {
						Nation city = NationManager.getInstance().allCityMap
								.get(i);
						if (city != null) {
							getLst.add(city);
						}
					} else if (type == 1) {
						Nation state = NationManager.getInstance().stateMap
								.get(i);
						if (state != null) {
							getLst.add(state);
						}
					} else if (type == 2) {
						Nation country = NationManager.getInstance().countryMap
								.get(i);
						if (country != null) {
							getLst.add(country);
						}
					}
				}

				if (type == 0) {
					for (Nation n : getLst) {
						eventSet(n, 1);
					}
				} else if (type == 1) {
					for (Nation n : getLst) {
						eventSet(n, 2);
					}
				} else if (type == 2) {
					for (Nation n : getLst) {
						eventSet(n, 3);
					}
				}

				return getLst;
			}
		}
		if (type == 0) {// 市
			ArrayList<Nation> getLst = new ArrayList<Nation>();
			loop: for (Integer i : achieveCityMap.keySet()) {
				List<Nation> lst = achieveCityMap.get(i);
				List<Nation> copy = new ArrayList<Nation>(lst);
				if (num > 0) {
					if (lst.size() <= num) {
						getLst.addAll(copy);
						copy.removeAll(lst);
						lst = copy;
						num = num - copy.size();
						break;
					} else {
						Collections.shuffle(lst);
						List<Nation> st = lst.subList(0, num);
						getLst.addAll(st);
						copy.removeAll(st);
						lst = copy;
						num = 0;
						break loop;
					}
				}
			}
			// getLst.add(NationManager.getInstance().getNation(1210));
			for (Nation n : getLst) {
				eventSet(n, 1);
			}
			return getLst;
		} else if (type == 1) {// 州
			ArrayList<Nation> getLst = new ArrayList<Nation>();
			loop: for (Integer i : achieveStateMap.keySet()) {
				List<Nation> lst = achieveStateMap.get(i);
				List<Nation> copy = new ArrayList<Nation>(lst);
				if (num > 0) {
					if (lst.size() <= num) {
						getLst.addAll(copy);
						copy.removeAll(lst);
						lst = copy;
						num = num - copy.size();
						break;
					} else {
						Collections.shuffle(lst);
						List<Nation> st = lst.subList(0, num);
						getLst.addAll(st);
						copy.removeAll(st);
						lst = copy;
						num = 0;
						break loop;
					}
				}
			}
			for (Nation n : getLst) {
				eventSet(n, 2);
			}
			return getLst;
		} else if (type == 2) {// 国家
			ArrayList<Nation> getLst = new ArrayList<Nation>();
			loop: for (Integer i : achieveCountryMap.keySet()) {
				List<Nation> lst = achieveCountryMap.get(i);
				List<Nation> copy = new ArrayList<Nation>(lst);
				if (num > 0) {
					if (lst.size() <= num) {
						getLst.addAll(copy);
						copy.removeAll(lst);
						lst = copy;
						num = num - copy.size();
						break;
					} else {
						Collections.shuffle(lst);
						List<Nation> st = lst.subList(0, num);
						getLst.addAll(st);
						copy.removeAll(st);
						lst = copy;
						num = 0;
						break loop;
					}
				}
			}
			for (Nation n : getLst) {
				eventSet(n, 3);
			}
			return getLst;
		}
		return null;
	}

	/**
	 * 得到我的战斗数据/没有新增
	 */
	public UserWarData getMyWarData(int userId, int nationId) {
		UserWarData uwd = null;
		loop: for (UserWarData data : warData.values()) {
			if (data.getWarCache() != null
					&& data.getWarCache().getUserid() == userId
					&& data.getNationId() == nationId) {
				uwd = data;
				break loop;
			}
		}
		if (uwd == null) {// 新增用户战争数据
			UserWarData myData = new UserWarData(MongoServer.getInstance()
					.getLogServer().getPlayerCacheDAO().findPlayerCacheByUserid(userId),
					nationId, 0, 0);
			int id = gameDao.getNationDAO().addUserWar(myData);
			if (id == -1) {
				logger.info("新增用户战争数据:错误");
			} else {
				myData.setId(id);
				warData.put(id, myData);
				uwd = myData;
			}

		}
		return uwd;
	}

	public void loadData() {
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate()
				.queryForList(NationDAO.SQL_SELECT_USERWAR);
		for (Map<String, Object> map : lst) {
			UserWarData userdata = gameDao.getNationDAO().loadUserWar(map);
			warData.put(userdata.getId(), userdata);
		}
	}

	/**
	 * 初始化军营
	 */
	public void initCamp() {
		for (Nation n : naMgr.allNationMap.values()) {
			if (n.getEventId() > 0) {
				allNations.add(n);
				switch (n.getEventId()) {
				case 1:
					FIGHT_TYPE = 0;
					break;
				case 2:
					FIGHT_TYPE = 1;
					break;
				case 3:
					FIGHT_TYPE = 2;
					break;
				}
			}
		}
		List<Map<String, Object>> lst = gameDao.getSimpleJdbcTemplate()
				.queryForList(NationDAO.SQL_SELECT_CAMP);
		for (Map<String, Object> map : lst) {
			MilitaryCamp militarycamp = gameDao.getNationDAO().loadCamp(map);
			allCamp.put(militarycamp.getId(), militarycamp);
			// 初始化
			initStrong(militarycamp);
		}
		// logger.info(">>>军营数量:" + allCamp.size());
		// startWarByRestart();
	}

	/**
	 * 得到我的军营
	 * 
	 * @param my
	 * @param nationId
	 * @return
	 */
	public MilitaryCamp getMyCamp(int my, int nationId) {
		for (MilitaryCamp camp : allCamp.values()) {
			if (camp.getOccCache() != null
					&& camp.getOccCache().getUserid() == my
					&& camp.getNativeId() == nationId) {
				return camp;
			}
		}
		return null;
	}

	/**
	 * 得到我的军营
	 * 
	 * @param my
	 * @param nationId
	 * @return
	 */
	public MilitaryCamp getMyCamp(int id) {
		return allCamp.get(id);
	}

	/**
	 * 初始化据点
	 */
	public void initStrong(MilitaryCamp militarycamp) {
		StrongHold stronghold = new StrongHold();
		stronghold.setCampId(militarycamp.getId());
		stronghold.setNationId(militarycamp.getNativeId());
		stronghold.setRefreshTime((int) (TimeUtils.nowLong() / 1000));
		stronghold.setFightingProcess((byte) 0);
		// 设置怪物部队
		allHold.put(stronghold.getCampId(), stronghold);
	}

	/**
	 * 得到我的据点
	 * 
	 * @param campId
	 */
	public StrongHold getMyStrong(int campId) {
		for (StrongHold stronghold : allHold.values()) {
			if (stronghold.getCampId() == campId) {
				return stronghold;
			}
		}
		return null;
	}

	/**
	 * 是否可以攻击主城
	 * 
	 * @param player
	 * @param nationId
	 */
	public boolean isAttNation(PlayerCharacter att, int nationId) {
		MilitaryCamp camp = getMyCamp(att.getId(), nationId);
		if (camp != null) {
			if (nationId % 1000 == 0) {
				if (att.getData().getNativeId() / 1000 * 1000 == nationId) {
					return true;
				}
			} else if (nationId % 100 == 0) {
				if (att.getData().getNativeId() / 100 * 100 == nationId) {
					return true;
				}
			} else if (nationId % 10 == 0) {
				if (att.getData().getNativeId() / 10 * 10 == nationId) {
					return true;
				}
			}

			StrongHold strong = getMyStrong(camp.getId());
			if (strong != null) {
				return strong.isThrough(getStrongNum(nationId)).isResult();
			}
		}
		return false;
	}

	/**
	 * 随机生成长官
	 * 
	 * @param nation
	 */
	public UserWarData getExecutive(Nation nation) {
		// 根据地区生成
		Map<Integer, UserWarData> datalst = new HashMap<Integer, UserWarData>();
		List<UserWarData> warLst = new ArrayList<UserWarData>();
		// 取出本城战役积分最高者
		for (UserWarData userdata : warData.values()) {
			if (userdata.getNationId() == nation.getId()
					&& userdata.getWarCache() != null) {
				datalst.put(userdata.getWarCache().getUserid(), userdata);
			}
		}
		for (MilitaryCamp mili : allCamp.values()) {
			if (mili.getOccCache() != null
					&& mili.getNativeId() == nation.getId()) {
				if (datalst.get(mili.getOccCache().getUserid()) != null) {
					warLst.add(datalst.get(mili.getOccCache().getUserid()));
				}
			}
		}
		UserWarData execu = minWarIntegral(warLst);
		// 取消对应区域战斗显示
		eventSet(nation, 0);
		// 取消对应区域战斗显示
		if (execu == null) {
			return null;
		} else {
			List<Integer> clearLst = new ArrayList<Integer>();
			// 从缓存中移除
			for (UserWarData userdata : warData.values()) {
				if (userdata.getWarCache() != null
						&& execu.getWarCache() != null
						&& execu.getWarCache().getUserid() == userdata
								.getWarCache().getUserid()) {
					clearLst.add(userdata.getId());
				}
			}
			for (Integer igz : clearLst) {
				warData.remove(igz);
			}
			return execu;
		}
	}

	/**
	 * 成为长官
	 * 
	 * @param nation
	 * @return
	 */
	public PlayerCharacter beExecutiveChief(Nation nation) {
		UserWarData userData = getExecutive(nation);
		if (userData != null && userData.getWarCache() != null) {
			MilitaryCamp wincamp = getMyCamp(
					userData.getWarCache().getUserid(), nation.getId());
			if (wincamp != null && wincamp.getOccCache() != null) {
				int winUser = wincamp.getOccCache().getUserid();// 胜利者id
				int originalOccupiers = nation.getOccupyUser();// 原占领着
				if (originalOccupiers != 0 && winUser != 0) {
					if (winUser == originalOccupiers) {// 胜利者是原占领者
						PlayerCharacter defPlayer = World.getInstance()
								.getPlayer(nation.getOccupyUser());
						int defNavte = defPlayer.getData().getNativeId();// 防守的区域id

						// 占领成为 区域官员
						nation.occNations(defPlayer, wincamp.getHeroId(),
								wincamp.getSoliderInfo(), defNavte);// 派将领驻防对应的区域
						wincamp.directOccupied(null, null, "", false);// 清空用户占领camp
						logger.info("区域长官未变化,原用户存在===用户:" + defPlayer.getId()
								+ "|成为:" + nation.getId() + " 的首长");
						return defPlayer;
					} else {// 胜利者不是原占领者
						PlayerCharacter winPlayer = World.getInstance()
								.getPlayer(winUser);
						PlayerCharacter defPlayer = World.getInstance()
								.getPlayer(nation.getOccupyUser());
						MilitaryCamp defCamp = getMyCamp(defPlayer.getId(),
								nation.getId());
						int defNavte = defPlayer.getData().getNativeId();// 防守的区域id
						int defHero = nation.getHeroId();// 防守的将领
						// 交换换防区域撤退
						nation.takeover(defPlayer, winPlayer);
						// 占领成为 区域官员
						nation.occNations(winPlayer, wincamp.getHeroId(),
								wincamp.getSoliderInfo(), defNavte);
						wincamp.directOccupied(null, null, "", false);// 清空胜利者camp
						if (defCamp.getHeroId() == defHero) {// 占领的用户和占领州玩家是同一个将领，用户清空
							defCamp.directOccupied(null, null, "", false);
						}
						logger.info("区域长官发生变化,原用户存在===用户:" + winPlayer.getId()
								+ "|成为:" + nation.getId() + " 的首长");
						return winPlayer;
					}
				} else if (originalOccupiers == 0 && winUser != 0) {// 胜利者存在，原占领用户不存在
					PlayerCharacter winPlayer = World.getInstance().getPlayer(
							winUser);
					int my = NationManager.getInstance().myNation(
							nation.getId() / 1000,
							nation.getId() % 1000 / 100 * 100,
							nation.getId() % 100 / 10 * 10);
					nation.occNations(winPlayer, wincamp.getHeroId(),
							wincamp.getSoliderInfo(), my);
					wincamp.directOccupied(null, null, "", false);// 清空胜利者camp
					logger.info("区域长官发生变化,原用户不存在===用户:" + winPlayer.getId()
							+ "|成为:" + nation.getId() + " 的首长");
					return winPlayer;
				}
			}
		}
		return null;
	}

	/**
	 * 得到伪camp/区域伪数据
	 * 
	 * @param nationid
	 */
	public MilitaryCamp getNationCamp(int nationid) {
		for (MilitaryCamp camp : allCamp.values()) {
			if (camp.getNativeId() == nationid && camp.isFalseCamp) {
				return camp;
			}
		}
		return null;
	}

	/**
	 * 最小积分用户
	 */
	public UserWarData minWarIntegral(List<UserWarData> lst) {
		int min = 0;
		UserWarData data = null;
		for (UserWarData datas : lst) {
			if (datas.getWarIntegral() > min) {
				min = datas.getWarIntegral();
				data = datas;
			}
		}
		return data;
	}

	/**
	 * 设置状态
	 * 
	 * @param na
	 * @param type
	 */
	public void eventSet(Nation na, int type) {
		// List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
		na.setEventId(type);
		// lst.add(na);
		if (na.getId() % 100 != 0
				&& naMgr.stateMap.get(na.getId() / 100 * 100) != null) {
			naMgr.stateMap.get(na.getId() / 100 * 100).setEventId(type);
			naMgr.stateMap.get(na.getId() / 100 * 100).save();
			// lst.add(naMgr.stateMap.get(na.getId() / 100 * 100));
		}
		// PushSign.sendAll(lst, new PlayerCharacter[]{},
		// ProcotolType.USER_INFO_RESP);
		na.save();
	}

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		int nation = 3250;
		System.out.println(nation / 1000 + " " + nation % 1000 / 100 * 100
				+ " " + nation % 100 / 10 * 10);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		System.out.println(map.get(0) == null ? 0 : 1);

	}

	/**
	 * 发送全局消息
	 * 
	 * @param nation
	 * @param name
	 */
	public void sendSysMsg(Nation nation, String name) {
		String message = "";
		int x = 1;
		switch (FIGHT_TYPE) {
		case 0:
			if (nation != null) {

				Nation state = NationManager.getInstance().getNation(
						nation.getId() / 100 * 100);
				Nation coun = NationManager.getInstance().getNation(
						nation.getId() / 1000 * 1000);
				if (state != null && coun != null) {
					// if (ROUND == 1) {
					message = I18nGreeting.getInstance().getMessage(
							"beCityMayor" + x,
							new Object[] { ROUND, name, coun.getName(),
									state.getName(), nation.getName() });
					x++;
					// } else if (ROUND == 16) {
					// message = I18nGreeting.getInstance().getMessage(
					// "beCityMayor16",
					// new Object[] { name, ROUND, coun.getName(),
					// state.getName(), nation.getName() });
					// } else {
					// message = I18nGreeting.getInstance().getMessage(
					// "beCityMayor",
					// new Object[] { name, ROUND, coun.getName(),
					// state.getName(), nation.getName() });
					// }
				}
				StopWar stop = new StopWar();
				stop.setEventId(1);
				stop.setPusgMsg(I18nGreeting.getInstance().getMessage(
						"war.stop.tip",
						new Object[] { getExecutive(), name, nation.getName(),
								getExecutive() }));
				logger.info("===========sendSysMsg");
				sendRmsOne(stop, nation.getId());
			}

			break;
		case 1:
			if (nation != null) {
				Nation country = NationManager.getInstance().getNation(
						nation.getId() / 1000 * 1000);
				if (country != null) {
					message = I18nGreeting.getInstance().getMessage(
							"bestateMayor",
							new Object[] { name, country.getName(),
									nation.getName() });
				}
				StopWar stop = new StopWar();
				stop.setEventId(2);
				stop.setPusgMsg(I18nGreeting.getInstance().getMessage(
						"war.stop.tip",
						new Object[] { getExecutive(), name, nation.getName(),
								getExecutive() }));
				sendRmsOne(stop, nation.getId());
			}

			break;
		case 2:
			if (nation != null) {
				message = I18nGreeting.getInstance().getMessage("beKing",
						new Object[] { nation.getName(), name });
				StopWar stop = new StopWar();
				stop.setEventId(3);
				stop.setPusgMsg(I18nGreeting.getInstance().getMessage(
						"war.stop.tip",
						new Object[] { getExecutive(), name, nation.getName(),
								getExecutive() }));
				sendRmsOne(stop, nation.getId());
			}

			break;
		}
		if (!"".equals(message)) {
			NoticeManager.getInstance().sendSystemWorldMessage(message);// 发送系统消息
		}
	}

	/**
	 * 积分排行榜
	 * 
	 * @param nationId
	 * @param userId
	 */
	public ArrayList<UserWarData> topScore(int nationId, PlayerCharacter player) {
		ArrayList<UserWarData> useData = new ArrayList<UserWarData>();
		ComparatorWar comparators = new ComparatorWar();
		UserWarData data = null;
		if (player != null) {
			data = WarManager.getInstance().getMyWarData(player.getId(),
					nationId);
		}
		for (MilitaryCamp camp : WarManager.getInstance().allCamp.values()) {
			if (camp.getNativeId() == nationId && camp.getOccCache() != null
					&& !camp.isFalseCamp()) {
				UserWarData datas = WarManager.getInstance().getMyWarData(
						camp.getOccCache().getUserid(), nationId);
				useData.add(datas);
			}
			if (camp.getNativeId() == nationId && camp.getOccCache() != null
					&& camp.isFalseCamp()) {
				camp.calculationWarIntegral(camp.getOccCache().getUserid(),
						false);
				camp.save();
				UserWarData datas = WarManager.getInstance().getMyWarData(
						camp.getOccCache().getUserid(), nationId);
				useData.add(datas);
			}
		}
		Collections.sort(useData, comparators);
		if (data != null) {
			useData.add(data);
		}
		return useData;
	}

	/**
	 * 得到战争类型
	 * 
	 * @return
	 */
	public String getExecutive() {
		String name = "";
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			switch (FIGHT_TYPE) {
			case 0:
				name = "Mayor";
				break;
			case 1:
				name = "Governor";
				break;
			case 2:
				name = "King";
				break;
			}
		}else{
			switch (FIGHT_TYPE) {
			case 0:
				name = "市长";
				break;
			case 1:
				name = "州长";
				break;
			case 2:
				name = "国王";
				break;
			}
		}
		
		return name;
	}
}
