/**
 * Copyright com.joymeng.game.domain.Equipment-EquipmentManager.java
 * @author xufangliang
 * @time 2012-5-3
 */
package com.joymeng.game.domain.item.equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.fight.FightLog;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.game.domain.world.GameDataManager;

/**
 * @author xufangliang
 * @time 2012-5-3
 * @fileName EquipmentManager.java 1.1
 */
public class EquipmentManager {

	static int STRENGTH_LEVELS = 0;// 强化等级 数
	static int QUALITY_LEVELS = 0;// 品质的级别

	public static final byte UPGRADE_QIANGHUA = 1; // 强化
	public static final byte UPGRADE_JINGLIANG = 2;// 精炼
	public static final byte UPGRADE_SHENGJIE = 3;// 升阶
	public static final byte UPGRADE_JIAZHI = 4;// 加制 特例

	// log日志,需要记录对应数据
	private Logger log = LoggerFactory.getLogger(EquipmentManager.class);

	private static EquipmentManager instance;

	public EquipmentManager() {
	}

	public static EquipmentManager getInstance() {
		if (instance == null) {
			instance = new EquipmentManager();
		}
		return instance;
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>装备固化数据<<<<<<<<<<<<<<<<<<<<<<
	public Map<Byte, List<EquipPrototype>> equipmentLevelDatas = new HashMap<Byte, List<EquipPrototype>>();
	public Map<Integer, EquipPrototype> equipmentDatas = new HashMap<Integer, EquipPrototype>();
	public Map<Integer, EquipmentStrength> strengthDatas = new HashMap<Integer, EquipmentStrength>();
	public Map<Integer, LuckyStone> luckyDatas = new HashMap<Integer, LuckyStone>();
	public Map<Integer, BasePrototype> baseDatas = new HashMap<Integer, BasePrototype>();
	public Map<Byte, Quality> qualityDatas = new HashMap<Byte, Quality>();
	public Map<Byte, Strength> streDatas = new HashMap<Byte, Strength>();
	public Map<Integer, EquipmentDismantling> basicDismantDats = new HashMap<Integer, EquipmentDismantling>();
	public Map<Byte, qualityStrength> qsDatas = new HashMap<Byte, qualityStrength>();
	public Map<Integer, EquipmentDismant> addDismantDatas = new HashMap<Integer, EquipmentDismant>();
	public Map<Integer, EquipmentData> dataDatas = new HashMap<Integer, EquipmentData>(); // 基本属性

	public Map<Integer, FirmEffect> firmDatas = new HashMap<Integer, FirmEffect>(); // 加制效果
	public Map<Integer, List<FirmEffect>> firmLstDatas = new HashMap<Integer, List<FirmEffect>>(); // 加制效果集合

	/**
	 * 缓存固化数据 到dataDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void load(String path) throws ClassNotFoundException {
		loadBase(path);
		loadQualityDatas(path);
		loadStrengthDatas(path);
		loadStrength(path);
		loadDatas(path);
		loadEquipment();
		loadLucky(path);
		loadBasicDismant(path);
		loadQSDatas(path);
		loadAddDismant(path);

		loadEqumentLevel();
		loadFirmDatas(path);// 加制数据表
		loadFirmLstDatas();// 加制数据lst
	}

	/**
	 * 缓存固化数据 到firmDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadFirmDatas(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path, FirmEffect.class);
		for (Object o : list) {
			FirmEffect e = (FirmEffect) o;
			// log.info(e.toString());
			firmDatas.put(e.getId(), e);
		}
	}

	/**
	 * 缓存固化数据 到firmLstDatas //需要loadFirmDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadFirmLstDatas() throws ClassNotFoundException {
		if (firmDatas != null && firmDatas.size() > 0) {
			for (FirmEffect fe : firmDatas.values()) {
				int id = fe.getEquipmentType() * 1000 + fe.getPropsId();
				List<FirmEffect> feLst = firmLstDatas.get(id);
				if (feLst == null)
					feLst = new ArrayList<FirmEffect>();
				feLst.add(fe);// 加入对应的lst
				firmLstDatas.put(id, feLst);
			}
		}
		// log.info(">>>>>>>>>>>>>>>>>>>firmLstDatas>>>"+firmLstDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 缓存固化数据 到dataDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadDatas(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path, EquipmentData.class);
		for (Object o : list) {
			EquipmentData e = (EquipmentData) o;
			dataDatas.put(e.getId(), e);
		}
		// log.info(">>>>>>>>>>>>>>>>>>>dataDatas>>>"+dataDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 根据装备等级加载数据
	 * 
	 * @throws ClassNotFoundException
	 */
	public void loadEqumentLevel() throws ClassNotFoundException {
		if (equipmentDatas == null || equipmentDatas.size() == 0) {
		} else {
			for (EquipPrototype eq : equipmentDatas.values()) {
				List<EquipPrototype> lst = equipmentLevelDatas.get(eq
						.getEquipmentLevel());
				if (eq.getQualityColor() == 1 && eq.getStrengthenLevel() == 0) {
					if (lst == null) {
						lst = new ArrayList<EquipPrototype>();
						lst.add(eq);
					} else {
						lst.add(eq);
					}
				}
				equipmentLevelDatas.put(eq.getEquipmentLevel(), lst);
			}
		}
		// log.info(">>>>>>>>>>>>>>>>>>>equipmentLevelDatas>>>"+equipmentLevelDatas.values()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 缓存固化数据 到equipmentMap
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadStrength(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path,
				EquipmentStrength.class);
		for (Object o : list) {
			EquipmentStrength e = (EquipmentStrength) o;
			strengthDatas.put(e.getId(), e);
		}
		// log.info(">>>>>>>>>>>>>>>>>>>strengthDatas>>>"+strengthDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 缓存固化数据 到equipmentMap
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadLucky(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path, LuckyStone.class);
		for (Object o : list) {
			LuckyStone e = (LuckyStone) o;
			// log.info(e.toString());
			luckyDatas.put(e.getId(), e);
		}
		// log.info(">>>>>>>>>>>>>>>>>>>luckyDatas>>>"+luckyDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 缓存固化数据 到loadQSDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadQSDatas(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path,
				qualityStrength.class);
		for (Object o : list) {
			qualityStrength e = (qualityStrength) o;
			qsDatas.put(e.getQuality(), e);
		}
		// log.info(">>>>>>>>>>>>>>>>>>>qsDatas>>>"+qsDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 缓存固化数据 到loadAddDismant
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadAddDismant(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path,
				EquipmentDismant.class);
		for (Object o : list) {
			EquipmentDismant e = (EquipmentDismant) o;
			e.setJzLv1StoneRate(e.getJzLv1StoneRate());
			e.setJzLv2StoneRate(e.getJzLv1StoneRate() + e.getJzLv2StoneRate());
			e.setJzLv3StoneRate(e.getJzLv2StoneRate() + e.getJzLv3StoneRate());
			e.setJzLv4StoneRate(e.getJzLv3StoneRate() + e.getJzLv4StoneRate());
			e.setJzLv5StoneRate(e.getJzLv4StoneRate() + e.getJzLv5StoneRate());
			e.setLuckyLv1StoneRate(e.getJzLv5StoneRate()
					+ e.getLuckyLv1StoneRate());
			e.setLuckyLv2StoneRate(e.getLuckyLv1StoneRate()
					+ e.getLuckyLv2StoneRate());
			e.setLuckyLv3StoneRate(e.getLuckyLv2StoneRate()
					+ e.getLuckyLv3StoneRate());
			e.setLuckyLv4StoneRate(e.getLuckyLv3StoneRate()
					+ e.getLuckyLv4StoneRate());
			e.setLuckyLv5StoneRate(e.getLuckyLv4StoneRate()
					+ e.getLuckyLv5StoneRate());
			addDismantDatas.put(e.getId(), e);
		}
		// log.info(">>>>>>>>>>>>>>>>>>>addDismantDatas>>>"+addDismantDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 缓存固化数据 到loadBasicDismant
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadBasicDismant(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path,
				EquipmentDismantling.class);
		for (Object o : list) {
			EquipmentDismantling e = (EquipmentDismantling) o;
			basicDismantDats.put(e.getId(), e);
		}
		// log.info(">>>>>>>>>>>>>>>>>>>basicDismantDats>>>"+basicDismantDats.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 品质系数固化数据 到dataDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadQualityDatas(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path, Quality.class);
		for (Object o : list) {
			Quality e = (Quality) o;
			qualityDatas.put(e.getId(), e);
		}
		// 设置 品质共几类
		QUALITY_LEVELS = qualityDatas.size();
		// log.info(">>>>>>>>>>>>>>>>>>>qualityDatas>>>"+qualityDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 缓存固化数据 到dataDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadStrengthDatas(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path, Strength.class);
		Map<Byte, Object> map = new HashMap<Byte, Object>();
		for (Object o : list) {
			Strength e = (Strength) o;
			streDatas.put(e.getId(), e);
			map.put(e.getsLevel(), e);
		}
		// 设置强化共有几种级别
		STRENGTH_LEVELS = map.size();
		// log.info(">>>>>>>>>>>>>>>>>>>streDatas>>>"+streDatas.size()+"<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 获取品质系数
	 * 
	 * @param quality
	 *            品质
	 * @return
	 */
	public byte getQuality(byte qualityId) {
		return qualityDatas.get(qualityId).getQualityValue();
	}

	public qualityStrength getQS(byte strengthLevel, byte quality) {
		if (qsDatas == null || qsDatas.size() == 0)
			return null;
		int id = strengthLevel * 10 + quality;
		log.info("拆解加成功率#### 强化等级 ： " + strengthLevel + " 品质：" + quality);
		return qsDatas.get((byte) id);
		// for(qualityStrength qs : qsDatas.values()){
		// if(qs.getQuality() == quality && qs.getStrengthLevel() ==
		// strengthLevel ){
		// return qs;
		// }
		// }
	}

	/**
	 * 获取基本数值
	 * 
	 * @param position
	 *            部位
	 * @param eLevel
	 *            等级
	 * @return
	 */
	public int getBasicData(byte position, byte eLevel) {
		int datas = 0;
		int id = position * 100 + eLevel;
		if (dataDatas != null && dataDatas.size() > 0) {
			EquipmentData data = dataDatas.get(id);
			if (data != null)
				datas = data.attackPiont + data.defensePiont + data.liftPiont
						+ data.soldierCount;
		}
		// for (EquipmentData data : dataDatas.values()) {
		// if (data.eLevel == eLevel && data.position == position) {
		// datas = data.attackPiont + data.defensePiont + data.liftPiont
		// + data.soldierCount;
		// }
		// }
		return datas;
	}

	/**
	 * 获取强化等级系数
	 * 
	 * @param position
	 *            部位
	 * @param sLevel
	 *            强化等级
	 * @return
	 */
	public float getSdatas(byte position, byte sLevel) {
		float datas = 0;
		if (sLevel == 0) {
			return datas;
		}
		for (Strength data : streDatas.values()) {
			if (data.getsLevel() == sLevel && data.getPosition() == position) {
				datas = data.getData();
			}
		}
		return datas;
	}

	// 装备属性 = 基本属性*（1+强化属性 +品质）
	public float getValue(byte position, byte sLevel, byte eLevel, byte quality) {
		return getBasicData(position, eLevel)
				* (getSdatas(position, sLevel) + getQuality(quality));
	}

	/**
	 * 得到装备唯一id
	 * 
	 * @param a
	 *            装备固化id
	 * @param b
	 *            装备品阶id
	 * @param c
	 *            装备强化id
	 * @return
	 */
	public static int getEquiId(int a, int b, int c) {
		return a * 10000 + b * 100 + c;
	}

	/**
	 * 获取装备 数值
	 * 
	 * @param eId
	 * @return
	 */
	public int getEquimentPoint(int eId) {
		if (equipmentDatas == null || equipmentDatas.size() == 0)
			return 0;
		EquipPrototype e = equipmentDatas.get(eId);
		if (e == null)
			return 0;
		return (int) (e.getAttackPiont() + e.getDefensePiont()
				+ e.getLiftPiont() + e.getSoldierCount());
	}

	/**
	 * 获取白装id
	 * 
	 * @param eId
	 * @return
	 */
	public int getEquimentWhiteId(int eId) {
		int id = eId / 10000;
		return id * 10000 + 100;
	}

	/**
	 * 缓存固化数据 到baseDatas
	 * 
	 * @param path
	 * @throws ClassNotFoundException
	 */
	public void loadBase(String path) throws ClassNotFoundException {
		List<Object> list = GameDataManager.loadData(path, BasePrototype.class);
		for (Object o : list) {
			BasePrototype e = (BasePrototype) o;
			baseDatas.put(e.getId(), e);
		}
	}

	public void loadEquipment() {
		log.info(">>>>>>>>>>>>>生成装备原型数据...开始<<<<<<<<<<<<<<<<<<<<");
		for (BasePrototype base : baseDatas.values()) {
			// 生成对应
			for (int i = 1; i <= QUALITY_LEVELS; i++) {
				for (int j = 0; j < STRENGTH_LEVELS; j++) {
					EquipPrototype e = new EquipPrototype();
					// 前三位 ID + 2位 品质 + 2位 强化等级
					e.setId(getEquiId(base.getId(), i, j));
					e.setEquipmentId(base.getId());
					e.setEquipmentIcon(base.getEquipmentIcon());
					e.setEquipmentLevel(base.getEquipmentLevel());
					e.setEquipmentName(base.getName());
					e.setExplain(base.getExplain());
					e.setEquipmentType(base.getEquipmentType());
					e.setBuyPrice(base.getBuyPrice() * i);// 装备购买价格=装备品质*基础购买价格（装备数据表中每件装备都有）
					e.setSell(base.isSell());
					e.setSellPrice(base.getSellPrice() * i);// 装备购买价格=装备品质*基础购买价格（装备数据表中每件装备都有）
					e.setQualityColor((byte) i);
					e.setStrengthenLevel((byte) j);
					e.setNextEquipmentId(base.getNextEquipmentId());
					e.setNeedLevel(base.getNeedLevel());

					float point = getValue(base.getEquipmentType(), (byte) j,
							base.getEquipmentLevel(), (byte) i);
					switch (base.getEquipmentType()) {
					case Equipment.SWORD:
						e.setAttackPiont(point);
						e.setDefensePiont(0);
						e.setLiftPiont(0);
						e.setSoldierCount(0);
						break;
					case Equipment.ARMOR:
						e.setDefensePiont(point);
						e.setAttackPiont(0);
						e.setLiftPiont(0);
						e.setSoldierCount(0);
						break;
					case Equipment.HELMET:
						e.setLiftPiont(point);
						e.setAttackPiont(0);
						e.setDefensePiont(0);
						e.setSoldierCount(0);
						break;
					case Equipment.MOUNTS:
						e.setSoldierCount(point);
						e.setAttackPiont(0);
						e.setDefensePiont(0);
						e.setLiftPiont(0);
						break;
					}
					// log.info("**********"+e.toString());
					equipmentDatas.put(e.getId(), e);
				}
			}
		}
		// log.info(">>>>>>>>>>>>>生成装备原型数据...成功<<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * 强化
	 * 
	 * @param e
	 * @return
	 */
	public EquiEffectResult strengthing(EquipPrototype e, String name) {
		EquiEffectResult result;

		if (e == null || e.getId() == 0) {
			result = EquiEffectResult.FAILED;
			result.setEquiOrResult("装备有误!");
			return result;
		}
		int id = e.getId();
		int ids = id / 10000;
		int qId = id % 10000 / 100;
		int sId = id % 100;
		int sLevel = sId;
		if (sLevel == STRENGTH_LEVELS - 1) {
			result = EquiEffectResult.FAILED;
			result.setEquiOrResult("已强化到最高级!");
			return result;
		} else {
			sLevel = sLevel + 1;
			result = EquiEffectResult.SUCCESSFULL;
			result.setEquiOrResult(String.valueOf(getEquiId(ids, qId, sLevel)));
			if (sLevel == 10) {// 发送全局消息
				String msg = I18nGreeting.getInstance().getMessage(
						"strengthen.10", new Object[] { name });
				if (!"".equals(msg)) {
					NoticeManager.getInstance().sendSystemWorldMessage(msg);
				}
			}
		}
		FightLog.info("strengthing===原id" + id + "|newId:"
				+ getEquiId(ids, qId, sLevel));
		return result;
	}

	/**
	 * 升阶
	 * 
	 * @param e
	 * @return
	 */
	public EquiEffectResult qualitying(EquipPrototype e) {
		EquiEffectResult result;

		if (e == null) {
			result = EquiEffectResult.FAILED;
			result.setEquiOrResult("装备有误!");
			return result;
		}

		int id = e.getId();
		int ids = id / 10000;
		int qId = id % 10000 / 100;
		int sId = id % 100;
		int qLevel = qId;
		if (qLevel == QUALITY_LEVELS) {
			result = EquiEffectResult.FAILED;
			result.setEquiOrResult("已升阶到最高级!");
			return result;
		} else {
			qLevel = qLevel + 1;
			result = EquiEffectResult.SUCCESSFULL;
			result.setEquiOrResult(String.valueOf(getEquiId(ids, qLevel, sId)));
		}
		FightLog.info("qualitying===原id" + id + "|newId:"
				+ String.valueOf(getEquiId(ids, qLevel, sId)));
		return result;
	}

	/**
	 * 升级
	 * 
	 * @param e
	 * @return
	 */
	public EquiEffectResult uping(EquipPrototype e) {
		EquiEffectResult result;

		if (e == null) {
			result = EquiEffectResult.FAILED;
			result.setEquiOrResult("装备有误!");
			return result;
		}
		int id = e.getId();
		int ids = e.getNextEquipmentId();
		int qId = id % 10000 / 100;
		if (ids == 0) {
			result = EquiEffectResult.FAILED;
			result.setEquiOrResult("已精炼到最高级!");
			return result;
		} else {
			result = EquiEffectResult.SUCCESSFULL;
			result.setEquiOrResult(String.valueOf(getEquiId(ids, qId, 0)));
		}
		FightLog.info("uping===原id" + id + "|nextid:" + ids + "|newId:"
				+ String.valueOf(getEquiId(ids, qId, 0)));
		return result;
	}

	/**
	 * 升级 ...
	 * 
	 * @param e
	 * @param type
	 *            类型
	 * @return
	 */
	public EquiEffectResult upgrade(Equipment e, byte type, String name) {
		if (type == EquipmentManager.UPGRADE_QIANGHUA) {
			return strengthing(e.getPrototype(), name);
		} else if (type == EquipmentManager.UPGRADE_JINGLIANG) {
			return uping(e.getPrototype());
		} else if (type == EquipmentManager.UPGRADE_SHENGJIE) {
			return qualitying(e.getPrototype());
		} else {
			EquiEffectResult er = EquiEffectResult.FAILED;
			er.setEquiOrResult("升级类型错误");
			return er;
		}
	}

	/**
	 * 通过id获取对象
	 * 
	 * @param id
	 * @return
	 */
	public EquipPrototype getEquipment(int id) {
		if (equipmentDatas == null || equipmentDatas.size() == 0)
			return null;
		return equipmentDatas.get(id);
	}

	/**
	 * 获取所有装备
	 * 
	 * @return
	 */
	public List<EquipPrototype> getAllEquipment() {
		return new ArrayList<EquipPrototype>(equipmentDatas.values());
	}

	/**
	 * 获取 升级信息
	 * 
	 * @param upgradeType
	 * @param upLevel
	 * @param equiLevle
	 * @return
	 */
	public EquipmentStrength getEquipmentStrength(byte upgradeType,
			byte equiLevle, byte upLevel) {
		// System.out.println("------------"+"^"+upgradeType+"^"+equiLevle+"^"+upLevel);
		if (null == strengthDatas)
			return null;
		if (upgradeType == UPGRADE_JIAZHI) {// 加制
			int ids = upgradeType * 100 + equiLevle;
			return strengthDatas.get(ids);
		}
		int id = upgradeType * 10000 + equiLevle * 100 + upLevel;
		return strengthDatas.get(id);
	}

	/**
	 * 获取几率
	 * 
	 * @param upgradeType
	 *            类型
	 * @param upLevel
	 *            目标等级
	 * @param equiLevle
	 *            装备等级
	 * @param PropsId
	 *            幸运石ID 不使用 id = 0
	 * @return
	 */
	public float fromluckyStone(byte upgradeType, byte upLevel, byte equiLevle,
			int PropsId) {
		if (null == luckyDatas || luckyDatas.size() == 0)
			return 0;
		int id = upgradeType * 100000 + equiLevle * 1000 + upLevel * 10
				+ PropsId;
		LuckyStone stone = luckyDatas.get(id);
		if (stone != null) {
			log.info("幸运石 id:" + PropsId + " 加成成功率：" + stone.getLuckeyRate());
			return stone.getBasicRate() + stone.getLuckeyRate();
		}

		// for(LuckyStone stone : luckyDatas.values()){
		// if(stone.getEquipLevel() == equiLevle && stone.getUpLevel() ==
		// upLevel && stone.getUserType() == upgradeType && stone.getLuckyId()
		// == PropsId){
		// return stone.getLuckeyRate()+stone.getBasicRate();
		// }else if(stone.getEquipLevel() == equiLevle && stone.getUpLevel() ==
		// upLevel && stone.getUserType() == upgradeType && PropsId == 0){
		// return stone.getBasicRate();
		// }
		// }
		return 0;
	}

	/**
	 * 通过装备和升级类型去的对应比例
	 * 
	 * @param e
	 * @param upgradeType
	 * @param PropsId
	 * @return
	 */
	public byte[] getEquip(Equipment e, byte upgradeType) {
		// 3 id 2,品质 3 强化等级
		int id = e.getId();
		if (id == 0) {
			return null;
		}
		// 目标等级
		int upLevel = 0;
		int ids = id / 10000;// id.substring(0, 3);
		// 装备等级
		byte nowEuuiLevle = e.getPrototype().getEquipmentLevel();
		// 品质等级
		int qId = id % 10000 / 100;
		// 强化等级
		int sId = id % 100;

		switch (upgradeType) {
		case UPGRADE_JINGLIANG:
			if (nowEuuiLevle == 1)
				upLevel = 5;
			else
				upLevel = nowEuuiLevle + 5;
			break;

		case UPGRADE_QIANGHUA:
			upLevel = sId + 1;
			break;
		case UPGRADE_SHENGJIE:
			upLevel = qId + 1;
			break;
		}

		byte[] b = { upgradeType, nowEuuiLevle, (byte) upLevel };

		return b;
	}

	/**
	 * 通过装备和升级类型去的对应比例
	 * 
	 * @param e
	 * @param upgradeType
	 * @param PropsId
	 * @return
	 */
	public float getProbability(Equipment e, byte upgradeType, int PropsId) {
		byte[] b = getEquip(e, upgradeType);
		if (b == null)
			return 0;
		return fromluckyStone(upgradeType, b[2], b[1], PropsId);
	}

	/**
	 * 获取拆解基本材料
	 * 
	 * @param sLevel
	 * @param eLevel
	 * @return
	 */
	public EquipmentDismantling getBasicDismant(byte sLevel, byte eLevel) {
		if (basicDismantDats == null || basicDismantDats.size() == 0) {
			return null;
		} else {
			List<EquipmentDismantling> list = new ArrayList<EquipmentDismantling>();
			for (EquipmentDismantling ed : basicDismantDats.values()) {
				if (sLevel == ed.getsLevel() && eLevel == ed.geteLevel()) {
					list.add(ed);
					// log.info(">>>成功率为"+ed.toString());
				}
			}
			// 根据几率随机出材料ID
			return getPropId(list);
		}
	}

	/**
	 * 获取基本拆解材料id
	 * 
	 * @param list
	 * @return
	 */
	public EquipmentDismantling getPropId(List<EquipmentDismantling> list) {
		float f = new Random().nextFloat();
		if (list == null || list.size() == 0)
			return null;
		if (list.size() == 1) {
			if (f > 0 && f <= list.get(0).basicRatio) {
				return list.get(0);
			}
		} else if (list.size() == 2) {
			if (f > 0 && f <= list.get(0).basicRatio)
				return list.get(0);
			else if (f > list.get(0).basicRatio
					&& f <= list.get(0).basicRatio + list.get(1).basicRatio)
				return list.get(1);
		} else if (list.size() == 3) {
			if (f > 0 && f <= list.get(0).basicRatio)
				return list.get(0);
			else if (f > list.get(0).basicRatio
					&& f <= list.get(0).basicRatio + list.get(1).basicRatio)
				return list.get(1);
			else if (f > list.get(0).basicRatio + list.get(1).basicRatio
					&& f < list.get(0).basicRatio + list.get(1).basicRatio
							+ list.get(2).basicRatio)
				return list.get(2);
		}
		return null;
	}

	/**
	 * 获取特殊拆解材料Id
	 * 
	 * @param sLevel
	 * @param qualityId
	 * @return 返回材料id 集合
	 */
	public List<Integer> getAddDismant(byte sLevel, byte eLevel, byte quality) {
		StringBuffer sb = new StringBuffer();
		sb.append("强化等级：" + sLevel + " 装备等级：" + eLevel + " 品质：" + quality);
		if (qsDatas == null || qsDatas.size() == 0 || addDismantDatas == null
				|| addDismantDatas.size() == 0) {
			return null;
		} else {
			List<Integer> pLst = new ArrayList<Integer>();
			int id = eLevel * 10 + sLevel;
			EquipmentDismant ed = addDismantDatas.get(id);
			if (ed == null)
				return pLst;
			else {
				sb.append("拆解加成基本数据 ： " + ed.toString());
				float addp = 0;
				qualityStrength qs = getQS(sLevel, quality);// 获取加成成功率
				// 获取特殊成功率
				if (qs != null) {
					sb.append("加成成功率：" + qs.toString());
					addp = qs.getAddRatio();
				}
				sb.append("加成次数：" + ed.getNum());
				for (int i = 0; i < ed.getNum(); i++) {
					int pid = getPropId(ed, addp);
					if (0 != pid) {
						sb.append("拆解加成材料id： " + pid);
						pLst.add(pid);
					}
				}
			}
			// for(EquipmentDismant ed:addDismantDatas.values()){
			// if(eLevel == ed.getEquiLevel() && eLevel ==
			// ed.getStrengthLevel()){
			//
			// }
			// }
			log.info(sb.toString());
			return pLst;
		}
	}

	public int getPropId(EquipmentDismant ed, float addRatio) {
		log.info("特殊材料成功率:" + ed.toString());
		float f = new Random().nextFloat();
		log.info("随机数字:" + f);
		if (f > 0 && f <= ed.getJzLv1StoneRate() + addRatio) {
			return 9;// 加制磨具1
		} else if (f > ed.getJzLv1StoneRate() + addRatio
				&& f <= ed.getJzLv2StoneRate() + addRatio) {
			return 10;// 加制磨具2
		} else if (f > ed.getJzLv2StoneRate() + addRatio
				&& f <= ed.getJzLv3StoneRate() + addRatio) {
			return 11;// 加制磨具3
		} else if (f > ed.getJzLv3StoneRate() + addRatio
				&& f <= ed.getJzLv4StoneRate() + addRatio) {
			return 12;// 加制磨具4
		} else if (f > ed.getJzLv4StoneRate() + addRatio
				&& f <= ed.getJzLv5StoneRate() + addRatio) {
			return 13;// 加制磨具5
		} else if (f > ed.getJzLv5StoneRate() + addRatio
				&& f <= ed.getLuckyLv1StoneRate() + addRatio) {
			return 4;// 幸运石1
		} else if (f > ed.getLuckyLv1StoneRate() + addRatio
				&& f <= ed.getLuckyLv2StoneRate() + addRatio) {
			return 5;// 幸运石2
		} else if (f > ed.getLuckyLv2StoneRate() + addRatio
				&& f <= ed.getLuckyLv3StoneRate() + addRatio) {
			return 6;// 幸运石3
		} else if (f > ed.getLuckyLv3StoneRate() + addRatio
				&& f <= ed.getLuckyLv4StoneRate() + addRatio) {
			return 7;// 幸运石4
		} else if (f > ed.getLuckyLv4StoneRate() + addRatio
				&& f <= ed.getLuckyLv5StoneRate() + addRatio) {
			return 8;// 幸运石5
		} else {
			return 0;
		}
	}

	public static void main(String[] args) {
		int id = 2530310;
		int ids = id / 10000;
		int qId = id % 10000 / 100;
		int sId = id % 100;
		// System.out.println(Math.pow(2, 2));
		System.out.println("ids:" + ids + "|qid:" + qId + "|sid:" + sId);
	}

	public Equipment getEqu(int id, int qId, int sId) {
		if (id == 0)
			return null;
		int eId = getEquiId(id, qId, sId);
		EquipPrototype ee = equipmentDatas.get(eId);
		if (ee != null) {
			return new Equipment(ee);
		}
		return null;

	}
}
