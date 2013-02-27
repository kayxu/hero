package com.joymeng.game.domain.flag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.award.ArenaAward;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.item.PlayerStorageAgent;
import com.joymeng.game.domain.item.props.Props;
import com.joymeng.game.domain.item.props.PropsManager;
import com.joymeng.game.domain.item.props.PropsPrototype;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 房间
 * 
 * @author Administrator
 * 
 */
public class Room extends ClientModuleBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Logger logger = Logger.getLogger(Room.class.getName());
	FlagManager fgr = FlagManager.getInstance();

	String id;
	GameHome homeA = null;// 51
	GameHome homeB = null;// 19
	int startTime;// 开始时间
	int refTime;// 刷新时间
	boolean isClose = false;// 是否关闭

	private Map<Integer, PlayerHero> heroMap = new HashMap<Integer, PlayerHero>();
	private Map<Byte, FlagLattice> flagLst = new HashMap<Byte, FlagLattice>();
	private Map<Byte, Road> roadLst = new HashMap<Byte, Road>();
	Byte[] homes = new Byte[] { 51, 19 };// 大本营id
	Byte[] capture = new Byte[] { 31, 39 };// 夺旗点id
	Byte[] lattices = new Byte[] { 31, 33, 13, 15, 17, 35, 37, 39, 53, 55, 57 };
	Byte[] roads = new Byte[] { 14, 16, 23, 27, 29, 32, 34, 36, 38, 41, 43, 47,
			54, 56 };

	// 通路1
	Byte[] road1 = new Byte[] { 41, 31, 32, 33, 34, 35, 36, 37, 38, 39, 29 };// 51-29
	Byte[] road2 = new Byte[] { 41, 31, 32, 33, 23, 13, 14, 15, 16, 17, 27, 37,
			38, 39, 29 };// 51-29
	Byte[] road3 = new Byte[] { 41, 31, 32, 33, 43, 53, 54, 55, 56, 57, 47, 37,
			38, 39, 29 };// 51-29

	PlayerCharacter[] allHomeArray = null;
	List<PlayerCharacter> allUser = new ArrayList<PlayerCharacter>();
	

	/**
	 * 生成room
	 */
	public Room() {
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * 得到我的数据
	 * 
	 * @param userid
	 * @return
	 */
	public GameHome[] getMyHome(int userid) {
		if (homeA.getPlayer().getId() == userid) {
			return new GameHome[] { homeA, homeB };
		} else if (homeB.getPlayer().getId() == userid) {
			return new GameHome[] { homeB, homeA };
		}
		return null;
	}

	/**
	 * 用户进驻大本营
	 * 
	 * @return
	 */
	public boolean initGameHome() {
		// 初始化大本营
		if (homeA != null && homeB != null) {
			homeA.setPoint(homes[0]);
			homeB.setPoint(homes[1]);
			return true;
		}
		return true;
	}

	/**
	 * 初始化格子
	 */
	public void initLattice() {
		// 格子
		for (int i = 0; i < lattices.length; i++) {
			FlagLattice f = new FlagLattice();
			f.setPoint(lattices[i]);
			if (lattices[i] % 5 == 0 || lattices[i] == 31 || lattices[i] == 39) {
				f.setSee(true);
			} else {
				f.setSee(false);
			}
			if (lattices[i] == (byte)15 || lattices[i] == (byte)55) {
				f.setBuff(100);
			}
			flagLst.put(f.getPoint(), f);
		}
		// 路
		for (int i = 0; i < roads.length; i++) {
			Road road = new Road();
			road.setPoint(roads[i]);
			roadLst.put(roads[i], road);
		}

	}

	/**
	 * 开始
	 * 
	 * @return
	 */
	public boolean startGame() {
		StringBuffer sb = new StringBuffer("|房间：" + getId() + "\n");
		if (initGameHome()) {
			initLattice();// 初始化格子数据
			// 发生开始消息
			// 下发对应数据
			List<ClientModuleBase> allLst = new ArrayList<ClientModuleBase>();
			// rms
			for (FlagLattice a : flagLst.values())
				allLst.add(a);
			sb.append("|格子数据：" + flagLst.size() + "\n");
			homeA.setReTime((int)(TimeUtils.nowLong()/1000));
			allLst.add(homeA);
			sb.append("|玩家A：" + homeA.getPlayer().getId() + "\n");
			sb.append("|玩家A携带将领：" + homeA.getHeroA() + "|" + homeA.getHeroB()
					+ "|" + homeA.getHeroC() + "\n");
			homeB.setReTime((int)(TimeUtils.nowLong()/1000));
			allLst.add(homeB);
			sb.append("|玩家B：" + homeB.getPlayer().getId() + "\n");
			sb.append("|玩家B携带将领：" + homeB.getHeroA() + "|" + homeB.getHeroB()
					+ "|" + homeB.getHeroC() + "\n");
			// 下发武将
			if (homeA.getHeroA() != 0) {
				PlayerHero pAA = homeA.getPlayer().getPlayerHeroManager()
						.getHero(homeA.getHeroA());
				SimplePlayerHero heroAA = new SimplePlayerHero(pAA, "");
				allLst.add(heroAA);
				heroMap.put(homeA.getHeroA(), pAA);
			}
			if (homeA.getHeroB() != 0) {
				PlayerHero pAB = homeA.getPlayer().getPlayerHeroManager()
						.getHero(homeA.getHeroB());
				SimplePlayerHero heroAB = new SimplePlayerHero(pAB, "");
				allLst.add(heroAB);
				heroMap.put(homeA.getHeroB(), pAB);
			}

			if (homeA.getHeroC() != 0) {
				PlayerHero pAC = homeA.getPlayer().getPlayerHeroManager()
						.getHero(homeA.getHeroC());
				SimplePlayerHero heroAC = new SimplePlayerHero(pAC, "");
				allLst.add(heroAC);
				heroMap.put(homeA.getHeroC(), pAC);
			}

			if (homeB.getHeroA() != 0) {
				PlayerHero pBA = homeB.getPlayer().getPlayerHeroManager()
						.getHero(homeB.getHeroA());
				SimplePlayerHero heroBA = new SimplePlayerHero(pBA, "");
				allLst.add(heroBA);
				heroMap.put(homeB.getHeroA(), pBA);
			}
			if (homeB.getHeroB() != 0) {
				PlayerHero pBB = homeB.getPlayer().getPlayerHeroManager()
						.getHero(homeB.getHeroB());
				SimplePlayerHero heroBB = new SimplePlayerHero(pBB, "");
				allLst.add(heroBB);
				heroMap.put(homeB.getHeroB(), pBB);
			}

			if (homeB.getHeroC() != 0) {
				PlayerHero pBC = homeB.getPlayer().getPlayerHeroManager()
						.getHero(homeB.getHeroC());
				SimplePlayerHero heroBC = new SimplePlayerHero(pBC, "");
				allLst.add(heroBC);
				heroMap.put(homeB.getHeroC(), pBC);
			}
			setRefTime((int) (TimeUtils.nowLong() / 1000) + 10);//
			setStartTime((int) (TimeUtils.nowLong() / 1000) + FlagManager.END_TIME);//设置结束时间
			System.out.println(getStartTime() - (int) (TimeUtils.nowLong() / 1000));
			allLst.add(this);// 房间下发
//			allHomeArray = new PlayerCharacter[] { homeA.getPlayer(),
//					homeB.getPlayer() };
			allUser.add(homeA.getPlayer());
			allUser.add(homeB.getPlayer());
			allHomeArray =  (PlayerCharacter[])allUser.toArray(new PlayerCharacter[allUser.size()]);
			// rms
			fgr.sendAll(allLst, allHomeArray);
			sb.append("|房间开启成功：" + getStartTime() + "\n");
			logger.info(sb.toString());
			return true;
		} else {
			sb.append("|房间开启失败：" + (int) (TimeUtils.nowLong() / 1000)
					+ "\n");
			logger.info(sb.toString());
			return false;
		}
	}

	/**
	 * 得到对应用户的 将领 point 第一个是 选择将领的坐标
	 * 
	 * @param heroA
	 * @param heroB
	 * @param heroC
	 * @param gamepoint
	 * @param latt
	 *            第一个点坐标
	 * @return
	 */
	public Byte[] myPoint(int heroA, int heroB, int heroC, byte gamepoint,
			byte latt) {
		byte a = gamepoint;
		byte b = gamepoint;
		byte c = gamepoint;
		Byte[] all = new Byte[3];
		for (FlagLattice flag : flagLst.values()) {
			if (flag.getHeroid() == heroA && heroA != 0) {
				a = flag.getPoint();
			}
			if (flag.getHeroid() == heroB && heroB != 0) {
				b = flag.getPoint();
			}
			if (flag.getHeroid() == heroC && heroC != 0) {
				c = flag.getPoint();
			}
		}
		all[0] = a;
		all[1] = b;
		all[2] = c;
		for (int i = 0; i < all.length; i++) {
			if (all[i] == -1) {
				all[i] = gamepoint;
			}
		}
		if (latt > 0) {
			if (a == latt) {
				all[0] = a;
				all[1] = b;
				all[2] = c;
			} else if (b == latt) {
				all[0] = b;
				all[1] = a;
				all[2] = c;
			} else if (c == latt) {
				all[0] = c;
				all[1] = a;
				all[2] = b;
			} else {
				all[0] = 0;
				all[1] = 0;
				all[2] = 0;
			}
		}
		logger.info("myPoint --->" + all[0] + "|" + all[1] + "|" + all[2]);
		return all;
	}

	/**
	 * 通过一点得到他所有相关点 先找到 对应路 ，在找对应的据点
	 * 
	 * @param point
	 * @return
	 */
	public Map<Byte, ClientModuleBase> oneToAll(Byte point) {
		Map<Byte, ClientModuleBase> allMyMap = new HashMap<Byte, ClientModuleBase>();
		Map<Byte, Road> roadMap = new HashMap<Byte, Road>();
		byte[] probably = new byte[] { (byte) (point - 1), (byte) (point + 1),
				(byte) (point - 10), (byte) (point + 10) };

		for (int i = 0; i < probably.length; i++) {
			if (probably[i] > 0) {
				if (roadLst.get((byte) probably[i]) != null) {
					roadMap.put(probably[i], roadLst.get((byte) probably[i]));
				}
			}
		}
		logger.info("坐标 ：" + point + "|周边  道路坐标" + roadMap.keySet());

		for (byte i : roadMap.keySet()) {
			int[] probablys = new int[] { i - 1, i + 1, i - 10, i + 10 };
			for (int j = 0; j < probablys.length; j++) {
				if (probablys[j] > 0) {
					if (flagLst.get((byte) probablys[j]) != null) {
						allMyMap.put((byte) probablys[j],
								flagLst.get((byte) probablys[j]));
					} else if (probablys[j] == 51) {
						allMyMap.put(homeA.getPoint(), homeA);
					} else if (probablys[j] == 19) {
						allMyMap.put(homeB.getPoint(), homeB);
					}
				}
			}
		}
		allMyMap.remove(point);// 删除我自己的坐标
		logger.info("坐标 ：" + point + "|周边   格子坐标" + allMyMap.keySet());
		return allMyMap;
	}

	/**
	 * 得到用户所有可移动点
	 * 
	 * @param all
	 */
	public Map<Byte, ClientModuleBase> getAllPoint(Byte[] all) {
		Map<Byte, ClientModuleBase> allMyMap = oneToAll(all[0]);// 选择将领的的坐标
		if (allMyMap.containsKey(all[1])) {
			// 包含将领1；添加将领1的相关坐标
			allMyMap.putAll(oneToAll(all[1]));
			if (allMyMap.containsKey(all[2])) {
				// 添加将领2的数据
				allMyMap.putAll(oneToAll(all[2]));
			}
		} else if (allMyMap.containsKey(all[2])) {
			// 添加将领2的数据
			allMyMap.putAll(oneToAll(all[2]));
			if (allMyMap.containsKey(all[1])) {
				// 添加将领1的数据
				allMyMap.putAll(oneToAll(all[1]));
			}
		}
		// 剔除自己本方将领数据
		for (int i = 0; i < all.length; i++) {
			if (all[i] != homes[0] && all[i] != homes[1]) {
				allMyMap.remove(all[i]);
			}
		}
		logger.info("将领可移动 --- 格子坐标集" + allMyMap.keySet());
		return allMyMap;
	}

	/**
	 * 得到我所有的点
	 * 
	 * @param mypoint
	 * @return
	 */
	public Map<Byte, ClientModuleBase> allMove(int homeid, byte point) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		tip.setFailTip("fail");
		if (this.outLine()) {// 没有掉线
			return null;
		}
		GameHome home = null;
		if (homeid == homeA.getPlayer().getId()) {
			home = homeA;
		} else {
			home = homeB;
		}
		// 查询 我的将领的全部坐标
		Byte[] myall = myPoint(home.getHeroA(), home.getHeroB(),
				home.getHeroC(), home.getPoint(), point);
		if (myall == null) {
			return null;
		}
		Map<Byte, ClientModuleBase> allMyMap = getAllPoint(myall);
		if (point == homes[0] || point == homes[1]) {
			allMyMap.remove(homes[0]);
			allMyMap.remove(homes[1]);
		} else {
			FlagLattice f = flagLst.get(myall[0]);
			if (f != null) {
				if (f.getUserid() == homeA.getPlayer().getId()) {
					if (f.getFlagId() != 0) {// 有旗
						allMyMap.remove(homes[1]);
					} else {
						allMyMap.remove(homes[0]);
					}
				} else {
					if (f.getFlagId() != 0) {// 有旗
						allMyMap.remove(homes[0]);
					} else {
						allMyMap.remove(homes[1]);
					}
				}
			} else {
				allMyMap.remove(homes[0]);
				allMyMap.remove(homes[1]);
			}
		}
		return allMyMap;
	}

	/**
	 * 是否可以移动
	 * 
	 * @param home
	 * @param heroid
	 * @param moveTo
	 * @return
	 */
	public TipUtil isMove(GameHome home, int heroid, byte moveTo,
			byte defPoint, int point) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		if (home.getMobility() < point) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setFailTip("No enough Mobility points!");
			}else{
				tip.setFailTip("行动力不足");
			}
			
			return tip;
		}
		Map<Byte, ClientModuleBase> allMyMap = allMove(home.getPlayer().getId(), defPoint);
		if (allMyMap != null) {
			logger.info("将领:" + heroid + "|可移动 --- 格子坐标集" + allMyMap.keySet()
					+ "|目标格子：" + moveTo);
			if (allMyMap.containsKey(moveTo)) {
				tip.setSuccTip("");
			} else {
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("Can't move to:"+ moveTo );
				}else{
					tip.setFailTip("不能移动到:" + moveTo + "区域");
				}
				
			}
		} else {
			logger.info("将领:" + heroid + "|可移动 --- 格子坐标集" + allMyMap + "|目标格子："
					+ moveTo);
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setFailTip("Can't move to:"+ moveTo );
			}else{
				tip.setFailTip("不能移动到:" + moveTo + "区域");
			}
		}
		return tip;
	}

	/**
	 * 将领移动到 格子moveTo
	 * 
	 * @param heroid
	 * @param moveTo
	 */

	public TipUtil moveFlagLattice(GameHome home, String soinfo,
			FlagLattice from, FlagLattice to, Map<Integer,Integer> dieAttAll,Map<Integer,Integer> dieDefAll) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		StringBuffer sb = new StringBuffer("用户：" + home.getPlayer().getId()
				+ "|将领：" + from.getHeroid() + "|移动到：" + to.getPoint() + "士兵："+soinfo+" \n");
		// 下发room
		fgr.sendOne(this, allHomeArray);
		inCamp(to, dieAttAll,dieDefAll);// 原将领回营
		// 先判断 用户在格子去，在判断 是否移动
		to.copy(from, soinfo, allHomeArray);
		home.motify(-1 * FlagManager.INIT_MOVE,allHomeArray);
		
		tip.setSuccTip("");
		sb.append("移动成功! \n");

		logger.info(sb.toString());
		return tip;
	}

	/**
	 * 将领出营 格子moveTo
	 * 
	 * @param heroid
	 * @param moveTo
	 */
	public TipUtil outCamp(GameHome home, int heroid, String soMsg,
			FlagLattice to, Map<Integer,Integer> dieAttAll,Map<Integer,Integer> dieDefAll) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		StringBuffer sb = new StringBuffer("用户：" + home.getPlayer().getId()
				+ "|将领：" + heroid + "|带兵" + soMsg + "|出营到格子：" + to.getPoint()
				+ " \n");
		fgr.sendOne(this, allHomeArray);
		inCamp(to, dieAttAll,dieDefAll);// 原将领回营
		// 先判断 用户在格子去，在判断 是否移动
		tip = to.creat(home.getPlayer().getId(), heroid, soMsg, allHomeArray);
		// if (tip.isResult()) {
		home.sendHero(heroid,allHomeArray);// 修改，下发
		home.motify(-1 * FlagManager.INIT_OUT,allHomeArray);
		//getMyRoad(home.getPlayer().getId());// 下发道路
		tip.setSuccTip("");
		sb.append("出营成功! \n");
		logger.info(sb.toString());
//		getMyRoad(home.getPlayer().getId());// 下发道路
		return tip;
	}

	/**
	 * 夺旗
	 * 
	 * @param flagLatticeId
	 *            //夺旗点
	 * @param gamePoint
	 *            //大本营id
	 */
	public TipUtil captureFlag(Byte flagLatticeId, Byte gamePoint) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		FlagLattice flag = flagLst.get(flagLatticeId);
		if (flag != null && flag.getUserid() != 0) {
			GameHome targetHome = null;
			GameHome myHome = getMyHome(flag.getUserid())[0];
			if (gamePoint == homeA.getPoint()) {
				targetHome = homeA;
				myHome= homeB;
			} else if (gamePoint == homeB.getPoint()) {
				targetHome = homeB;
				myHome= homeA;
			}
			Map<Byte, ClientModuleBase> allMoves = allMove(flag.getUserid(),
					flagLatticeId);// 我可以走的数据点
			if (flag != null && targetHome != null) {
				if (flag.getFlagId() != 0) {// 交旗
					if (allMoves.containsKey(gamePoint)) {
						if (flag.getUserid() == targetHome.getPlayer().getId()
								&& flag.getFlagId() != targetHome.getPoint()) {
							// 交旗成功
							tip.setSuccTip("");
							// game over ... 战斗结束
							// 计算用户积分 下发结束消息
							quitRoom(targetHome.getPlayer().getId(), 0,false);
						} else {
							// 交旗失败
							if(I18nGreeting.LANLANGUAGE_TIPS == 1){
								tip.setFailTip("Can't submit the flag.");
							}else{
								tip.setFailTip("不能交自己的旗子");
							}
						}
					} else {
						// 交旗失败
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setFailTip("Can't submit the flag.");
						}else{
							tip.setFailTip("不能提交旗子");
						}
					}
				} else {// 夺旗
					if (targetHome.isFlag()) {
						if (flag.getUserid() == targetHome.getPlayer().getId()) {
							if(I18nGreeting.LANLANGUAGE_TIPS == 1){
								tip.setFailTip("Can't submit the flag.");
							}else{
								tip.setFailTip("不能提交旗子");
							}
						} else {
							if (allMoves.containsKey(gamePoint)) {
								// 夺旗成功
								flag.captureFlag(allHomeArray, targetHome.getPoint());
								targetHome.changeFlag(allHomeArray, false);
								//发送消息对方用户
								String msg = I18nGreeting.getInstance().getMessage("captureFlag.success", new Object[]{});
								GameUtils.sendTip(new TipMessage(msg, ProcotolType.CAPTURE_FLAG, GameConst.GAME_RESP_SUCCESS), targetHome.getPlayer().getUserInfo(),GameUtils.FLUTTER);
								
								String msg1 = I18nGreeting.getInstance().getMessage("captureFlag.prompt", new Object[]{});
								GameUtils.sendTip(new TipMessage(msg1, ProcotolType.CAPTURE_FLAG, GameConst.GAME_RESP_SUCCESS), myHome.getPlayer().getUserInfo(),GameUtils.FLUTTER);
								//发送消息对方用户
								tip.setSuccTip("");
							} else {
								
								if(I18nGreeting.LANLANGUAGE_TIPS == 1){
									tip.setFailTip("You can't capture the flag now.");
								}else{
									tip.setFailTip("不能夺旗");
								}
							}
						}
					} else {
						
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setFailTip("The flag has been taken away.");
						}else{
							tip.setFailTip("旗子已被夺走");
						}
					}
				}
			} else {
				
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("Stronghold doesn't exist.");
				}else{
					tip.setFailTip("大本营或格子不存在");
				}
			}
		}
		logger.info(tip.getResultMsg());
		return tip;
	}

	/**
	 * 回大本营
	 * 
	 * @param to
	 */
	public void inCamp(FlagLattice to, Map<Integer,Integer> dieAll1,Map<Integer,Integer> dieAll2) {
		StringBuffer sb = new StringBuffer();
		sb.append("|玩家：" + to.getUserid() + "|将领：" + to.getHeroid() + "回营\n");
		//getMyRoad(to.getUserid());// 下发道路
		if (to.getUserid() == homeA.getPlayer().getId()) {
			if (to.getFlagId() != 0) {// 旗子会对方大本营
				homeB.changeFlag(allHomeArray, true);
			}
			homeA.recHero(to.getHeroid(), dieAll2,allHomeArray);//守城将领 奖撤退减兵
			to.clean(allHomeArray);
			sb.append("玩家id："+homeA.getPlayer().getId()+"|死亡人数："+dieAll2);
			sb.append("玩家id："+homeB.getPlayer().getId()+"|死亡人数："+dieAll1);
		} else if (to.getUserid() == homeB.getPlayer().getId()) {
			if (to.getFlagId() != 0) {// 旗子会对方大本营
				homeA.changeFlag(allHomeArray, true);
			}
			homeB.recHero(to.getHeroid(), dieAll2,allHomeArray);//守城将领 撤退减兵
			to.clean(allHomeArray);
			sb.append("玩家id："+homeA.getPlayer().getId()+"|死亡人数："+dieAll1);
			sb.append("玩家id："+homeB.getPlayer().getId()+"|死亡人数："+dieAll2);
		}
		sb.append("|成功\n");
		//getMyRoad(home.getPlayer().getId());// 下发道路
		logger.info(sb.toString());
	}
	
	/**
	 * 士兵死亡
	 * @param myid
	 * @param dieAll1
	 * @param dieAll2
	 */
	public void setDealNum(int myid, Map<Integer,Integer> dieAll1,Map<Integer,Integer> dieAll2){
		GameHome[] my = getMyHome(myid);
		if(my != null){
			my[0].setAllDeath(dieAll1,allHomeArray);
			my[1].setAllDeath(dieAll2,allHomeArray);
		}
	}

	/**
	 * 移动通用方法
	 * 
	 * @param home
	 * @param heroid
	 * @param soMsg
	 * @param from
	 * @param to
	 * @return
	 */
	public TipUtil move(boolean win, int userid, int heroid,
			String attSurplusSolider, FlagLattice from, FlagLattice to,
			String defSurplusSolider,Map<Integer,Integer> dieAttNum,Map<Integer,Integer> dieDefNum) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		StringBuffer sb = new StringBuffer("|战斗结果：" + win + "|用户：" + userid
				+ "|将领：" + heroid + "|移动到格子：" + to.getPoint() + "|是否大本营："
				+ from + " \n");
		GameHome home = null;
		GameHome[] allHome = getMyHome(userid);
		if (allHome != null) {
			home = allHome[0];
			sb.append("|攻击方："+userid +"|战果："+win+"\n");
			if (win) {// 攻击方获胜
				if (from == null) {// 出营
					sb.append("|攻击方胜利，剩余士兵为："+attSurplusSolider);
					this.outCamp(home, heroid, attSurplusSolider, to, dieAttNum,dieDefNum);
					
				} else {// 移格子
					this.moveFlagLattice(home, attSurplusSolider, from, to,
							dieAttNum,dieDefNum);
					sb.append("|攻击方胜利，剩余士兵为："+attSurplusSolider);
				}
				tip.setSuccTip("");
			} else {
				// 防守方胜利
				if (from == null) {// 出营 失败
					// 修改驻防兵力
					to.motifySolider(defSurplusSolider,allHomeArray);
					home.motify(-1 * FlagManager.INIT_OUT,allHomeArray);
					sb.append("防御方胜利：士兵为："+defSurplusSolider);
				} else {// 移格子
						// 修改驻防兵力
					home.motify(-1 * FlagManager.INIT_MOVE,allHomeArray);
					to.motifySolider(defSurplusSolider,allHomeArray);
					inCamp(from, dieAttNum,dieDefNum);// 原将领回营
					sb.append("防御方胜利：士兵为："+defSurplusSolider);
				}
				tip.setSuccTip("");
			}
		} else {
			
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setFailTip("Player camp data error!");
			}else{
				tip.setFailTip("玩家大本营错误");
			}
			sb.append("玩家大本营错误");
		}
		getMyRoad(home.getPlayer().getId());// 下发道路
		buffOnHero(homeA.getPlayer().getId());//下发buff
		buffOnHero(homeB.getPlayer().getId());//下发buff
		logger.info(sb.toString());
		return tip;
	}

	/**
	 * 道路结束点
	 * 
	 * @param pn
	 * @param my 需要到的点
	 * @param road 道路点集合
	 * @param enemy 敌人占领点集合
	 * @return
	 */
	public int roadEnd(boolean pn, byte my, Byte[] road, List<Byte> enemy) {
		if (pn) {
			int end = 0;
			for (int i = 0; i < road.length; i++) {
				if (road[i] == my) {
					end = i;
					break;
				} else {
					if (enemy.contains(road[i])) {// 是否有敌人
						break;
					}
				}
			}
			return end;
		} else {
			int end = road.length;
			for (int i = road.length - 1; i >= 0; i--) {
				if (road[i] == my) {
					end = i;
					break;
				} else {
					if (enemy.contains(road[i])) {// 是否有敌人
						break;
					}
				}
			}
			return end;
		}
	}

	/**
	 * 计算莫条路 是否连接
	 * 
	 * @param pn
	 *            //正反序
	 * @param my 我的home点集合
	 * @param road 路点集合
	 * @param enemy 敌人占领点击和
	 * @return
	 */
	public Map<Byte, Road> getRoad(boolean pn, byte my, Byte[] road,
			List<Byte> enemy) {
		Map<Byte, Road> roadMap = new HashMap<Byte, Road>();
		if (pn) {
			int end = roadEnd(pn, my, road, enemy);
			if (end > 0) {
				for (int t = 0; t < end; t++) {
					if (roadLst.containsKey(road[t])) {
						roadMap.put(road[t], roadLst.get(road[t]));
					}
				}
			}
		} else {
			int end = roadEnd(pn, my, road, enemy);
			if (end < road.length) {
				for (int t = road.length - 1; t >= end; t--) {
					if (roadLst.containsKey(road[t])) {
						roadMap.put(road[t], roadLst.get(road[t]));
					}
				}
			}
		}
		logger.info("计算莫条路 是否连接:"+roadMap.keySet());
		return roadMap;
	}

	/**
	 * 得到 对应道路
	 * 
	 * @param game
	 * @param my
	 * @param enemy
	 *            //敌人的坐标
	 * @return
	 */
	public Map<Byte, Road> allRoad(int game, byte my, List<Byte> enemy) {
		Map<Byte, Road> roadMap = new HashMap<Byte, Road>();
		if (my == game) {
			return roadMap;
		}
		if (game == homes[0] && my != homes[0]) {// 道路顺序
			// a路...
			roadMap.putAll(getRoad(true, my, road1, enemy));
			// b路 ...
			roadMap.putAll(getRoad(true, my, road2, enemy));
			// c路...
			roadMap.putAll(getRoad(true, my, road3, enemy));
		} else if (game == homes[1] && my != homes[1]) {// 逆序
			// a路...
			roadMap.putAll(getRoad(false, my, road1, enemy));
			// b路 ...
			roadMap.putAll(getRoad(false, my, road2, enemy));
			// c路...
			roadMap.putAll(getRoad(false, my, road3, enemy));
		}
		return roadMap;
	}

	/**
	 * 显示用户道路通路
	 * 
	 * @param home
	 * @param heroid
	 * @param moveTo
	 */
	public void getMyRoad(int userId) {
		
		GameHome my = null;
		GameHome enemy = null;
		if (homeA.getPlayer().getId() == userId) {
			enemy = homeB;
			my = homeA;

		} else {
			enemy = homeA;
			my = homeB;
		}
		fgr.sendOne(new Road((byte)0), new PlayerCharacter[] { my.getPlayer() });
		fgr.sendOne(new Road((byte)0), new PlayerCharacter[] { enemy.getPlayer() });
		if (homeA != null && homeB != null) {
			// 道路坐标
			Map<Byte, Road> myroadMap = new HashMap<Byte, Road>();
			Map<Byte, Road> otherroadMap = new HashMap<Byte, Road>();
			// 得到a 的通路
			Byte[] all = myPoint(my.getHeroA(), my.getHeroB(), my.getHeroC(),
					my.getPoint(), (byte) 0);
			Byte[] enemys = myPoint(enemy.getHeroA(), enemy.getHeroB(),
					enemy.getHeroC(), enemy.getPoint(), (byte) 0);
			// 我的
			for (int i = 0; i < all.length; i++) {
				myroadMap.putAll(allRoad(my.getPoint(), all[i],
						new ArrayList<Byte>(Arrays.asList(enemys))));
			}
			// 对方的
			for (int i = 0; i < enemys.length; i++) {
				otherroadMap.putAll(allRoad(enemy.getPoint(), enemys[i],
						new ArrayList<Byte>(Arrays.asList(all))));
			}
			// 下发
			List<ClientModuleBase> mylst = new ArrayList<ClientModuleBase>(
					myroadMap.values());
			List<ClientModuleBase> otherlst = new ArrayList<ClientModuleBase>(
					otherroadMap.values());
			// 发送双方的数据
			fgr.sendAll(mylst, new PlayerCharacter[] { my.getPlayer() });
			fgr.sendAll(otherlst, new PlayerCharacter[] { enemy.getPlayer() });
			System.out.println("ROAD用户："+my.getPlayer().getId()+"|"+myroadMap.keySet().toString());
			System.out.println("ROAD用户："+enemy.getPlayer().getId()+"|"+otherroadMap.keySet().toString());
		}
	}

	/**
	 * 请求战斗 请求
	 * 
	 * @param userid
	 * @param heroid
	 * @param moveFrom
	 * @param moveTo
	 * @return
	 */
	public TipUtil isFight(int userid, int heroid, String soinfo,
			byte moveFrom, byte moveTo) {
		TipUtil tip = new TipUtil(ProcotolType.FLAG_RESP);
		StringBuffer sb = new StringBuffer("用户：" + userid + "|将领：" + heroid
				+ " 从:" + moveFrom + " 移到:" + moveTo + " \n");
		FlagLattice to = flagLst.get(moveTo);
		FlagLattice from = flagLst.get(moveFrom);
		GameHome home = null;
		byte my = 0;// 我的坐标
		int[] buffs = new int[] { 0, 0 };// 默认buff
		boolean isToNext = false;// 是否能够继续

		if (to == null) {
			sb.append("移动目标不存在！\n");
			tip.setFailTip("移动目标不存在");
		} else {
			buffs[1] = to.getBuff(); // 防御默认加成
			if (homeA.getPlayer().getId() == userid) {
				home = homeA;
			}
			if (homeB.getPlayer().getId() == userid) {
				home = homeB;
			}
			if (home == null) {
				sb.append("用户大本营不存在！\n");
				tip.setFailTip("用户大本营不存在");
			} else {
				// 用户大本营
				if (from == null) {
					sb.append("将领：" + heroid + "不在格子区！\n");
					boolean isatt = home.isAtHome(heroid);
					if (!isatt) {
						sb.append("将领：" + heroid + "不再大本营，不能出城！\n");
						tip.setFailTip("将领：" + heroid + "不再大本营！");
					} else {
						tip = isMove(home, heroid, moveTo, home.getPoint(),
								FlagManager.INIT_OUT);// 是否可以出营到对应的点
						if (tip.isResult()) {
							my = home.getPoint();
							isToNext = true;
						} else {
							sb.append(tip.getResultMsg() + "\n");
						}
					}
				} else {
					sb.append("将领：" + heroid + "在格子区！\n");
					tip = isMove(home, heroid, moveTo, from.getPoint(),
							FlagManager.INIT_MOVE);// 是否可以移动到对应的点
					if (tip.isResult()) {
						my = from.getPoint();// 攻击默认加成
						buffs[0] = from.getBuff();
						isToNext = true;
					} else {
						sb.append(tip.getResultMsg() + "\n");
					}
				}
			}
			if (isToNext) {//
				if (to.getUserid() != userid && to.getHeroid() != 0) {
					int[] heroBuffs = fightBuffAllRoad(userid, my,
							to.getPoint());
					sb.append("|武将加成："+heroBuffs[0]+":"+heroBuffs[1]+"\n");
					buffs[0] = buffs[0] + heroBuffs[0];
					buffs[1] = buffs[1] + heroBuffs[1];
					// 获取 攻击防守的 buff
					tip.setBufint(buffs);
					// 需要战斗
					tip.setSuccTip("");
					sb.append("将领：" + heroid + "发生战斗！|攻击方加成：" + buffs[0]
							+ "|防御方加成" + buffs[1] + "\n");
				} else {
					// 不需要战斗 直接进行下一步 移动
					sb.append("士兵数：" +soinfo+ "\n");
					this.move(true, userid, heroid, soinfo, from, to, "", null,null);
					sb.append("将领：" + heroid + "不发生战斗---直接移动！\n");
					tip.setFailTip("");
				}
			}
		}
		logger.info(sb.toString());
		return tip;
	}

	/**
	 * 取得每个热门的道路
	 * 
	 * @param userid
	 * @param target
	 * @return
	 */
	public List<ClientModuleBase> buffOnHero(int userid) {
		GameHome[] allHome = getMyHome(userid);
		//HashMap<HeroPoint, Integer> map = new HashMap<HeroPoint, Integer>();
		List<ClientModuleBase> hpList = new ArrayList<ClientModuleBase>();
		if (allHome != null) {
			if (allHome[0].getHeroA() != 0) {
				FlagLattice fa = getMyLatt(userid, allHome[0].getHeroA());
				if (heroMap.get(allHome[0].getHeroA()) != null) {
					if (fa != null) {
						HeroPoint hp = new HeroPoint(heroMap.get(allHome[0].getHeroA()),fa,fightBuffAllRoad(userid, fa.getPoint(),
								fa.getPoint())[0]
								+ fa.getBuff());
						//map.put(hp,0);
						hpList.add(hp);
					} else {
						//map.put(null, 0);
					}
				}
			}
			if (allHome[0].getHeroB() != 0) {
				FlagLattice fa = getMyLatt(userid, allHome[0].getHeroB());
				if (heroMap.get(allHome[0].getHeroB()) != null) {
					if (fa != null) {
						HeroPoint hp = new HeroPoint(heroMap.get(allHome[0].getHeroB()),fa,fightBuffAllRoad(userid, fa.getPoint(),
								fa.getPoint())[0]
								+ fa.getBuff());
						//map.put(hp,0);
						hpList.add(hp);
						logger.info("FlagLattice id:"+fa.getPoint()+"|buff:"+fa.getBuff());
					} else {
						//map.put(null, 0);
					}
				}
			}
			if (allHome[0].getHeroC() != 0) {
				FlagLattice fa = getMyLatt(userid, allHome[0].getHeroC());
				if (heroMap.get(allHome[0].getHeroC()) != null) {
					if (fa != null) {
						HeroPoint hp = new HeroPoint(heroMap.get(allHome[0].getHeroC()),fa,fightBuffAllRoad(userid, fa.getPoint(),
								fa.getPoint())[0]
								+ fa.getBuff());
						//map.put(hp,0);
						hpList.add(hp);
						logger.info("FlagLattice id:"+fa.getPoint()+"|buff:"+fa.getBuff());
					} else {
						//map.put(null, 0);
					}
				}
			}
		}
		
		fgr.sendAll(hpList, allHomeArray);
		logger.info("用户："+userid + "|buff"+hpList.toString());
		
		return hpList;
	}

	/**
	 * 获取所有buff
	 * 
	 * @param userid
	 * @param my
	 * @param target
	 * @return
	 */
	public int[] fightBuffAllRoad(int userid, byte my, byte target) {
		GameHome[] allHome = getMyHome(userid);
		int[] buffs = new int[] { 0, 0 };
		StringBuffer sb = new StringBuffer();
		sb.append("用户："+userid+"|我的点："+my+"|进攻点："+target+"\n");
		if (allHome != null) {
			int att = 0;
			// 攻击方
			int att1 = fightBuffOneRoad(allHome[0], allHome[1], my, target,
					road1);
			att = att1;
			sb.append("攻击：路1："+att1+"\n");
			int att2 = fightBuffOneRoad(allHome[0], allHome[1], my, target, road2);
			if(att < att2)
				att = att2;
			sb.append("攻击：路2："+att2+"\n");
			int att3 = fightBuffOneRoad(allHome[0], allHome[1], my, target, road3);
			if(att < att3)
				att = att3;
			sb.append("攻击：路3："+att3+"\n");
			sb.append("攻击：路/加成"+att+"\n");
			buffs[0] = att;
			// 防御方
			int def  = 0;
			int def1 = fightBuffOneRoad(allHome[1], allHome[0], target, target,
					road1);
			def = def1;
			sb.append("防御：路1："+def1+"\n");
			int def2= fightBuffOneRoad(allHome[1], allHome[0], target, target,
					road2);
			if(def < def2)
				def = def2;
			sb.append("防御：路2："+def2+"\n");
			int def3 = fightBuffOneRoad(allHome[1], allHome[0], target, target,
					road3);
			if(def < def3)
				def = def3;
			sb.append("防御：路3："+def3+"\n");
			sb.append("防御：路/加成"+def+"\n");
			buffs[1] = def;
		}
		logger.info(sb.toString());
		return buffs;
	}

	/**
	 * 获取加成
	 * 
	 * @param mys
	 * @param enenys
	 * @param my
	 * @param target 
	 * @param road
	 * @return
	 */
	public int fightBuffOneRoad(GameHome mys, GameHome enenys, byte my,
			byte target, Byte[] road) {
		StringBuffer sb = new StringBuffer();
		sb.append("用户："+mys.getPoint() + " |敌人："+enenys.getPoint()+"|我的点："+my+"|进攻点："+target + "\n");
		int buff = 0;
		Byte[] myall = myPoint(mys.getHeroA(), mys.getHeroB(), mys.getHeroC(),
				mys.getPoint(), my);
		Byte[] enemys = myPoint(enenys.getHeroA(), enenys.getHeroB(),
				enenys.getHeroC(), enenys.getPoint(), (byte)0);
		if(myall != null && enemys != null){
			if (mys.getPoint() == homes[0]) {// 正序
				buff = fightBuffOnRoad(true, my, target, road, new ArrayList<Byte>(
						Arrays.asList(myall)),
						new ArrayList<Byte>(Arrays.asList(enemys)));
				sb.append("正序： buff="+buff);
			} else {// 反序
				buff = fightBuffOnRoad(false, my, target, road,
						new ArrayList<Byte>(Arrays.asList(myall)),
						new ArrayList<Byte>(Arrays.asList(enemys)));
				sb.append("反序： buff="+buff);
			}
		}
		logger.info(sb.toString());
		return buff;
	}

	/**
	 * 获取 攻击防守的 回路将领加成buff
	 * 
	 * @param pn
	 * @param my
	 *            我的点
	 * @param target
	 *            目标点
	 * @param road
	 * @param mys
	 * @param enemys
	 * @return
	 */
	public int fightBuffOnRoad(boolean pn, byte my, byte target, Byte[] road,
			List<Byte> mys, List<Byte> enemys) {
		// 查看自己能拿几条路回去
		// 查看回去的路是否有敌人
		// 没有敌人看有几个我方将领
		StringBuffer sb = new StringBuffer();
		sb.append("正反序："+pn+"|我点"+my+"|进攻点："+target+"\n");
		sb.append("敌人所在点："+enemys.toString());
		int buff = 0;
		if (pn) {
			int end = roadEnd(pn, target, road, enemys);
			sb.append("正序：节点"+end+"\n");
			if (end > 0) {
				for (int t = 0; t < end; t++) {
					if (mys.contains(road[t]) && road[t] != my) {
						buff += FlagManager.INIT_BUFF;
					}
				}
			}
		} else {
			int end = roadEnd(pn, target, road, enemys);
			sb.append("反序：节点"+end+"\n");
			if (end < road.length) {
				for (int t = road.length - 1; t >= end; t--) {
					if (mys.contains(road[t]) && road[t] != my) {
						buff += FlagManager.INIT_BUFF;
					}
				}
			}
		}
		sb.append("buff="+buff+"\n");
		logger.info(sb.toString());
		return buff;
	}

	/**
	 * TODO 退出
	 * 
	 * @param winid
	 *            胜利者id
	 * @param playerId
	 *            退出者id windid , playerid =0 时 ，房间到时自动关闭
	 */
	public Room quitRoom(int winid, int playerId,boolean isEnter) {
		GameHome win = null;
		GameHome other = null;
		if (winid != 0) {// 胜利者确定
			if (winid == homeA.getPlayer().getId()){
				win = homeA;
				other = homeB;
			}
			else{
				win = homeB;
				other = homeA;
			}
				
		} else if (playerId != 0 && winid == 0) {// 用户自动退出
			if (playerId == homeA.getPlayer().getId()){
				win = homeB;
				other = homeA;
			}else{
				win = homeA;
				other = homeB;
			}
				
		}// 时间到自动计算胜利者
		if(isEnter){
			allUser.remove(other.getPlayer());
			allHomeArray =  (PlayerCharacter[])allUser.toArray(new PlayerCharacter[allUser.size()]);
		}
		// 计算结果
		calcuWin(win);
		// 下发客户端显示,生成奖励
		setClose(true);
		fgr.sendOne(this, allHomeArray);
		// fgr.roomMap.remove(getId());// 关闭这个房间
		return this;
	}

	/**
	 * 掉线
	 * 
	 * @return
	 */
	public boolean outLine() {
		// 判断是否掉线
		// 计算结果
		// 下方奖励
		if (World.getInstance().getOnlineRole(homeA.getPlayer().getId()) == null) {
			// A掉线
			quitRoom(0, homeA.getPlayer().getId(),false);
			return true;
		} else if (World.getInstance().getOnlineRole(homeB.getPlayer().getId()) == null) {
			// B掉线
			quitRoom(0, homeB.getPlayer().getId(),false);
			return true;
		} else {
			// 查看是否到时间结束
			if ( (getStartTime() -10) < (int) (TimeUtils.nowLong()/1000)) {// 自动关闭
				quitRoom(0, 0,false);
				return true;
			}
		}
		return false;
	}

	/**
	 * 结果 返回积分
	 * 
	 * @param win
	 * @return
	 * 武将卡碎片id 598
	 */
	public void calcuWin(GameHome win) {
		StringBuffer sb = new StringBuffer("|计算房间结果，房间id：" + getId() + "\n");
		GameHome fail = null;
		if (win != null) {// win胜利者
			if (win == homeA)
				fail = homeB;
			else
				fail = homeA;
		} else {
			Map<GameHome, Integer> result = new HashMap<GameHome, Integer>();
			for (FlagLattice a : flagLst.values()) {
				if (a.getUserid() != 0 && a.getHeroid() != 0) {
					GameHome home = getMyHome(a.getUserid())[0];
					int t = result.get(home) == null ? 0 : result.get(home);
					// 计算积分
					result.put(home,t+ FightUtil.getSoldierNum(a.getSoinfo()));
				}
			}
			sb.append("|用户：" + homeA.getPlayer().getId() + "|战场剩余士兵："
					+ result.get(homeA));
			sb.append("|用户：" + homeB.getPlayer().getId() + "|战场剩余士兵："
					+ result.get(homeB));
			if(result.get(homeA) != null && result.get(homeB) != null){
				if (result.get(homeA) >= result.get(homeB)) {
					fail = homeB;
					win = homeA;
				} else {
					fail = homeA;
					win = homeB;
				}
			}else{
				if(result.get(homeA) != null && result.get(homeB) == null){
					win = homeA;
					fail = homeB;
				}else if(result.get(homeA) == null && result.get(homeB) != null){
					fail = homeA;
					win = homeB;
				}else{
					win = homeA;
					fail = homeB;
				}
			}
		}
		for (FlagLattice a : flagLst.values()) {
			if (a.getUserid() != 0 && a.getHeroid() != 0) {
				// 回收士兵
				if (homeA.getPlayer().getId() == a.getUserid()) {
					homeA.getPlayer().getPlayerBuilgingManager()
							.recoverSoldier(a.getSoinfo());
				} else if (homeB.getPlayer().getId() == a.getUserid()) {
					homeB.getPlayer().getPlayerBuilgingManager()
							.recoverSoldier(a.getSoinfo());
				}
				sb.append("|回收用户：" + a.getUserid() + "|士兵：" + a.getSoinfo());
			}
		}
		
		int winSco = win.getAllDeah()*win.getPlayer().getData().getLevel() * FlagManager.WIN_MULTIPLE;
		sb.append("|胜利者id:" + win.getPlayer().getId() +"|死亡总兵数："+win.getAllDeah());
		int failSco = fail.getAllDeah() * fail.getPlayer().getData().getLevel()
				* FlagManager.FIL_MULTIPLE;
		sb.append("|失败者id:" + fail.getPlayer().getId() +"|死亡总兵数："+fail.getAllDeah());
		
		sb.append("|胜利者id:" + win.getPlayer().getId() + "|最后积分：" + winSco
				+ "\n");
		sb.append("|失败者id:" + fail.getPlayer().getId() + "|最后积分：" + failSco
				+ "\n");
		
		String title = I18nGreeting.getInstance().getMessage("flag.fight.end.title",new Object[] {});
		String flagend = I18nGreeting.getInstance().getMessage("flag.fight.end.Content",new Object[] {});
		// 计算对应的奖励
		ArenaAward winAren = arenaAward(win.getPlayer(), winSco,598,awardNum(win.getPlayer().getData().getLevel()),title,flagend);
		ArenaAward failAren = arenaAward(fail.getPlayer(), failSco,0,0,title,flagend);
		// 生成大礼包
		sb.append("胜利者 大礼包id:" );
		sb.append(winAren == null ? 0 : winAren.getId() + "\n");
		// 生成大礼包
		sb.append("失败者 大礼包id:");
		sb.append(failAren == null ? 0 : failAren.getId() + "\n");
		logger.info(sb.toString());
		
		List<ClientModuleBase> clientLst = new ArrayList<ClientModuleBase>();
		win.setWin(true);
		clientLst.add(win);
		fail.setWin(false);
		clientLst.add(fail);
		fgr.sendAll(clientLst, allHomeArray);
		//发送奖励
 		GameStart winstart = new GameStart((byte) 1,"",(byte) 0,(byte) 0,0);
 		GameStart failstart = new GameStart((byte) 1,"",(byte) 0,(byte) 0,0);
//		GameStart winstart = new GameStart((byte) 1,
//				"1 VS 1 争夺战结束," + win.getPlayer().getData().getName()
//						+ " 获得了胜利！" + winAren == null ? "注意查收礼包！" : "",(byte) 0,(byte) 0);
//		GameStart failstart = new GameStart((byte) 1,
//				"1 VS 1 争夺战结束," + win.getPlayer().getData().getName()
//						+ " 获得了胜利！" + failAren == null ? "注意查收礼包！" : "",(byte) 0,(byte) 0);
 		for(int i = 0 ; i < allHomeArray.length;i++){
 			if(allHomeArray[i].getId() == win.getPlayer().getId()){
 				fgr.sendOne(winstart, new PlayerCharacter[] { win.getPlayer() });
 			}else if(allHomeArray[i].getId() == fail.getPlayer().getId()){
 				fgr.sendOne(failstart, new PlayerCharacter[] { fail.getPlayer() });
 			}
 		}
 		
 		//发送消息
 		if(win != null && fail != null){
 			String msg = I18nGreeting.getInstance().getMessage("PVP.message",
 					new Object[]{win.getPlayer().getData().getName(),fail.getPlayer().getData().getName()});
 			NoticeManager.getInstance().sendSystemWorldMessage(msg);
 		}
 		
 		
// 		if(!isEnter){
// 			
// 		}
		
		
		
	}
	/**
	 * 经验卡碎片
	 * @param level
	 * @return
	 */
	public int awardNum(short level ){
		if(level <= 5){
			return 1;
		}else if(level <= 10 && level >= 6){
			return 2;
		}else if(level <= 15 && level >= 11){
			return 3;
		}else if(level <= 20 && level >= 16){
			return 4;
		}else if(level <= 30 && level >= 21){
			return 6;
		}else{
			return 0;
		}
	}

	/**
	 * 生成奖励
	 * 
	 * @param pc
	 * @param arr
	 * 武将卡碎片id 598
	 */
	public static ArenaAward arenaAward(PlayerCharacter pc, int cal,int propsid,int num,String title,String msg) {
		ArrayList<Cell> arr = getReward(cal);
		if(propsid > 0){
			PropsPrototype pro = PropsManager.getInstance().getProps(propsid);
			if (pro != null) {
				Props pp3 = new Props(pro);
				Cell cell = new Cell(pp3, num);
				arr.add(cell);
			}
		}
		if (arr != null && arr.size() > 0) {
			StringBuffer sb = new StringBuffer();
			String areStr = PlayerStorageAgent.createFromList(arr);
			sb.append("用户id:" + pc.getId() + "大礼包内容:" + areStr + "\n");
			ArenaAward aren = new ArenaAward();
			aren.setUserId(pc.getId());
			aren.setTitle(title);
			aren.setGoodsOrEqu(areStr);
			aren.setTipsForAward(msg);
			logger.info(sb.toString());
			/*int id = DBManager.getInstance().getWorldDAO().getArenaAwardDAO()
					.addArenaAward(aren);*/
			ArenaAward a = pc.getFightEventManager().addPersonalSystemAward(aren);
			int id = a.getId();
			GameUtils.sendEventOpenFlag((byte)3,pc.getUserInfo());//系统奖励提示 标志
			aren.setId(id);
			return aren;
		}
		return null;
	}

	/**
	 * 下发奖励
	 * 
	 * @param cal
	 *            分数
	 * @return
	 */
	public static ArrayList<Cell> getReward(int cal) {
		logger.info("经验：" + cal);
		ArrayList<Cell> cellSt = new ArrayList<Cell>();
		// 积分换算成对应的道具 1000，10000，100000，1000000
		PropsPrototype p1 = PropsManager.getInstance().getProps(40);
		int millions = cal / Integer.parseInt(p1.getProperty2());// id 40
		if (millions > 0) {
			if (p1 != null) {
				Props pp1 = new Props(p1);
				Cell c1 = new Cell(pp1, millions);
				cellSt.add(c1);
			}
		}

		PropsPrototype p2 = PropsManager.getInstance().getProps(39);
		int tenW = cal % Integer.parseInt(p1.getProperty2())
				/ Integer.parseInt(p2.getProperty2());// id 39
		if (tenW > 0) {
			if (p2 != null) {
				Props pp2 = new Props(p2);
				Cell c2 = new Cell(pp2, tenW);
				cellSt.add(c2);
			}
		}
		PropsPrototype p3 = PropsManager.getInstance().getProps(38);
		int million = cal % Integer.parseInt(p2.getProperty2())
				/ Integer.parseInt(p3.getProperty2());// id 38
		if (million > 0) {
			if (p3 != null) {
				Props pp3 = new Props(p3);
				Cell c3 = new Cell(pp3, million);
				cellSt.add(c3);
			}
		}

		PropsPrototype p4 = PropsManager.getInstance().getProps(37);
		int thousands = cal % Integer.parseInt(p3.getProperty2())
				/ Integer.parseInt(p4.getProperty2());// id 37
		if (thousands > 0) {
			if (p4 != null) {
				Props pp4 = new Props(p4);
				Cell c4 = new Cell(pp4, thousands);
				cellSt.add(c4);
			}
		}
		return cellSt;
	}

	/**
	 * 找到我的武将 格子位置
	 * 
	 * @param userid
	 * @param heroid
	 * @return
	 */
	public FlagLattice getMyLatt(int userid, int heroid) {
		for (FlagLattice f : flagLst.values()) {
			if (f.getUserid() == userid && heroid != 0
					&& f.getHeroid() == heroid) {
				return f;
			}
		}
		return null;
	}

	@Override
	public byte getModuleType() {
		return NTC_ROOM;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putPrefixedString(id, (byte) 2);
		out.putInt(refTime+30);
		if (isClose) {
			out.put((byte) 1);
		} else {
			out.put((byte) 0);
		}
		out.putInt(startTime);
	}
	
	/**
	 * 刷新 
	 * @param i
	 */
	public void refreshMobility(int i){
		if(homeA != null && homeB != null){
			if(getRefTime() + 20 <= (int)(TimeUtils.nowLong()/1000))
			{	
				setRefTime((int)(TimeUtils.nowLong()/1000));
				homeA.motify(i, allHomeArray);
				homeB.motify(i, allHomeArray);
				fgr.sendOne(this, allHomeArray);
			}else{
			}	
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getRefTime() {
		return refTime;
	}

	public void setRefTime(int refTime) {
		this.refTime = refTime;
	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	public Map<Integer, PlayerHero> getHeroMap() {
		return heroMap;
	}

	public void setHeroMap(Map<Integer, PlayerHero> heroMap) {
		this.heroMap = heroMap;
	}

	public Map<Byte, FlagLattice> getFlagLst() {
		return flagLst;
	}

	public void setFlagLst(Map<Byte, FlagLattice> flagLst) {
		this.flagLst = flagLst;
	}

	public Map<Byte, Road> getRoadLst() {
		return roadLst;
	}

	public void setRoadLst(Map<Byte, Road> roadLst) {
		this.roadLst = roadLst;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a =0;
		System.out.println(a == 0 ? "bb" :"aa");
	}
	
}
