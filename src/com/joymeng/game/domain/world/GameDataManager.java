package com.joymeng.game.domain.world;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.joymeng.core.log.GameLog;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.XmlUtils;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.domain.box.ExtremeBoxManager;
import com.joymeng.game.domain.box.PropsBoxManager;
import com.joymeng.game.domain.building.BuildingManager;
import com.joymeng.game.domain.building.RefreshsBuildManager;
import com.joymeng.game.domain.card.CardManager;
import com.joymeng.game.domain.fight.FightEventManager;
import com.joymeng.game.domain.fight.FightManager;
import com.joymeng.game.domain.fight.mod.ArenaManager;
import com.joymeng.game.domain.fight.mod.CampaignManager;
import com.joymeng.game.domain.fight.mod.LadderManager;
import com.joymeng.game.domain.fight.result.ArenaRewardManager;
import com.joymeng.game.domain.fight.result.FightRewardManager;
import com.joymeng.game.domain.force.MobManager;
import com.joymeng.game.domain.hero.HeroManager;
import com.joymeng.game.domain.hero.data.HeroBarManager;
import com.joymeng.game.domain.item.equipment.EquipmentManager;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.monster.MonsterManager;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.nation.war.WarManager;
import com.joymeng.game.domain.quest.QuestManager;
import com.joymeng.game.domain.recharge.RechargeManager;
import com.joymeng.game.domain.role.PlayerExpManager;
import com.joymeng.game.domain.role.PlayerLimitManager;
import com.joymeng.game.domain.role.PowerCalculate;
import com.joymeng.game.domain.role.UsernameManager;
import com.joymeng.game.domain.shop.GoodsManager;
import com.joymeng.game.domain.sign.SignManager;
import com.joymeng.game.domain.skill.SkillManager;
import com.joymeng.game.domain.soldier.SoldierManager;
import com.joymeng.game.domain.soldier.SoldierNumManager;
import com.joymeng.game.domain.train.TrainExpManager;
import com.joymeng.game.domain.train.TrainSkillManager;
import com.sun.imageio.plugins.common.I18N;
import com.sun.jmx.snmp.Timestamp;

/**
 * 游戏数据管理,主要是载入固化数据
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
public class GameDataManager {
	private static Logger logger = Logger.getLogger(GameDataManager.class
			.getName());

	// static final String RES = "/gameData.xml";
	// static final String RES_REP_SHOP = "heroRepShop.xml";

	// static List<String> tips = new ArrayList<String>();

	public static final HeroManager heroManager = HeroManager.getInstance();
	public static final SoldierManager soldierManager = SoldierManager
			.getInstance();
	public static final MonsterManager monsterManager = MonsterManager
			.getInstance();
	public static final CampaignManager campaignManager = CampaignManager
			.getInstance();
	public static final SkillManager skillManager = SkillManager.getInstance();
	public static final BuildingManager buildingManager = BuildingManager
			.getInstance();
	public static final EquipmentManager equipmentManager = EquipmentManager
			.getInstance();
	public static final PropsManager propsManager = PropsManager.getInstance();
	public static final TrainExpManager trainExpManager = TrainExpManager
			.getInstance();
	public static final TrainSkillManager trainSkillManager = TrainSkillManager
			.getInstance();
	public static final SoldierNumManager soldierNumManager = SoldierNumManager
			.getInstance();
	public static final HeroBarManager heroBarManager = HeroBarManager
			.getInstance();
	public static final PlayerExpManager playerExpManager = PlayerExpManager
			.getInstance();
	public static final FightRewardManager fightRewardManager = FightRewardManager
			.getInstance();
	public static final PlayerLimitManager playerLimitManager = PlayerLimitManager
			.getInstance();
	public static final NationManager nationManager = NationManager
			.getInstance();
	public static final GoodsManager goodsManager = GoodsManager.getInstance();
	public static final LadderManager ladderManager = LadderManager
			.getInstance();
	public static final MobManager forceManager = MobManager.getInstance();
	public static final ExtremeBoxManager eBoxManager = ExtremeBoxManager
			.getInstance();
	public static final UsernameManager usernameManager = UsernameManager
			.getInstance();
	public static final ArenaRewardManager arenaRewardManager = ArenaRewardManager
			.getInstance();
	public static final ArenaManager arenaManager = ArenaManager.getInstance();
	public static final QuestManager questManager = QuestManager.getInstance();
	public static final PropsBoxManager propsBoxManager = PropsBoxManager
			.getInstance();

	public static void load(String path) {

		try {
			trainExpManager.load(path);
			soldierNumManager.load(path);// 要放在将领数据前
			heroManager.load(path);
			trainSkillManager.load(path);
			soldierManager.load(path);
			skillManager.load(path);
			buildingManager.load(path);
			equipmentManager.load(path);
			propsManager.loadProps(path);

			heroBarManager.load(path);
			playerExpManager.load(path);
			playerLimitManager.load(path);
			monsterManager.load(path);
			campaignManager.load(path);
			fightRewardManager.load(path);
			goodsManager.load(path);
			ladderManager.load(path);
			forceManager.load(path);
			eBoxManager.loadAwardDatas(path);
			eBoxManager.loadAdditionalDatas(path);
			usernameManager.load(path);
			arenaRewardManager.load(path);
			propsBoxManager.loadPropsBoxes(path);
			arenaManager.init();
			nationManager.init(path);
			questManager.load(path);
			CardManager.loadCardAwardRates(path);
			RechargeManager.loadRechargeAwardRule(path);
			FightEventManager.loadStageFightEvent(path);
			SignManager.loadData(path);
			// loadTips();
			
			RefreshsBuildManager.getInstance().load(path);
			
			PowerCalculate.getInstance().calaNationState();
			PowerCalculate.getInstance().getPowerData();
			logger.info("开始加载政绩相关");
//			NationManager.getInstance().satateMilitary();//州总功勋
//			NationManager.getInstance().deduAch();//扣除每天政绩
//			NationManager.getInstance().setAchiev();//计算政绩加成
			NationManager.getInstance().loadAchiev();
			WarManager.getInstance().warInit();
			
//			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
			GameLog.error("game data load error!", e);
			System.exit(0);
		}
	}

	public static void loadTips() throws Exception {
		logger.info("开始载入小提示<<<<<<<<<<<<<<<");
	}

	/**
	 * 载入固化数据
	 * 
	 * @param path
	 * @param T
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Object> loadData(String path, Class<?> T)
			throws ClassNotFoundException {
		String fileName = T.getSimpleName();
		logger.info("loadData form " + fileName + "<<<<<<<<<<<<<<<<<<<<<<<<<<");
		// System.out.println("fileName=" + fileName);
		File file = new File(path + "/" + fileName + ".xml");
		Document d;
		List<Object> list = new ArrayList();
		try {
			d = XmlUtils.load(file);
			Element[] elements = XmlUtils.getChildrenByName(
					d.getDocumentElement(), fileName);
			for (Element element : elements) {
				Object data = T.newInstance();// 创建对象
				Field[] fs = T.getDeclaredFields();
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					f.setAccessible(true); // 设置些属性是可以访问的
					String str = XmlUtils.getAttribute(element, f.getName());
					// System.out.println("str="+str+"name="+ f.getName());
					try {
						if (f.getType() == int.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Integer.parseInt(str));
							}

						} else if (f.getType() == long.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Long.parseLong(str));
							}

						} else if (f.getType() == boolean.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Boolean.parseBoolean(str));
							}

						} else if (f.getType() == byte.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Byte.parseByte(str));
							}
						} else if (f.getType() == String.class) {
							f.set(data, str);
						} else if (f.getType() == Short.class
								|| f.getType() == short.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Short.parseShort(str));
							}

						} else if (f.getType() == float.class
								|| f.getType() == Float.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
								f.set(data, Float.parseFloat(str));
							}

						} else if (f.getType() == Timestamp.class) {
							if (str != null && !"".equalsIgnoreCase(str)) {
							}
						} else {
							// System.out.println("error type=" +
							// f.getType()+" name="+f.getName());
						}
					} catch (Exception ex) {
						logger.info("load data error,str=" + f.getName()
								+ " fileName=" + fileName);
						ex.printStackTrace();
					}

				}
				list.add(data);
			}
			// 打印属性
			for (Object obj : list) {
				Field[] fs = T.getDeclaredFields();
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					f.setAccessible(true); // 设置些属性是可以访问的
					Object val = f.get(obj);// 得到此属性的值
					// System.out.println("name:" + f.getName() + "\t value = "
					// + val);
				}
			}

		} catch (Exception e) {

			GameLog.error("error in load " + fileName, e);
			e.printStackTrace();
			System.exit(0);
		}
		return list;
	}

	// public static void main(String[] args) {
	// // ExcelUtils.fileLst.clear();
	// // ExcelUtils.recursion(Constants.RES_PATH,".xml");
	// // System.out.println(ExcelUtils.fileLst.size());
	// try {
	// File file = new File(GameConst.RES_PATH + "Building.xml");
	// Document d = XmlUtils.load(file);
	//
	// Class<?> c = Class
	// .forName("com.joymeng.game.domain.building.BuildingManager");
	//
	// // Method methlist[] = c.getDeclaredMethods();
	// Method getInstance = c.getMethod("getInstance", new Class[] {});
	// Method load = c.getMethod("load", new Class[] { Element.class });
	// Object obj = getInstance.invoke(null, null);
	// System.out.println(obj);
	// load.invoke(obj, new Element[] { d.getDocumentElement() });
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// // GameDataManager.load(Constants.RES_PATH+"Building.xml");
	//
	// }
}
