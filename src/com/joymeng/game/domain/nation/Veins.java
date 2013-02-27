package com.joymeng.game.domain.nation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.chat.NoticeManager;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.building.PlayerBarrackManager;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Veins extends ClientModuleBase {// 矿脉点
	private static NationManager naMgr = NationManager.getInstance();
	static Logger logger = LoggerFactory.getLogger(Veins.class);
	int id; // 规则 州id *100 + id
	int stateId;// 所属州id
	byte isMain;// 主资源
	byte type;// 类型 0 矿脉 1 资源点
	int userId;// 占领id
	String username = "";// 用户名
	int userStateId;// 占领者所属洲
	int formerStateId;// 原属州
	int heroId;// 武将id
	String userSoMsg;// 用户带兵
	String baseSoMsg = "";// 原始驻兵
	long restTime;// 休整时间
	int addition;// 加成比率
	String heroInfo;
	public String getUsername() {
		return username;
	}


	public String getHeroInfo() {
		return heroInfo;
	}


	public void setHeroInfo(String heroInfo) {
		this.heroInfo = heroInfo;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return GET the isMain
	 */
	public byte getIsMain() {
		return isMain;
	}

	/**
	 * @return GET the formerStateId
	 */
	public int getFormerStateId() {
		return formerStateId;
	}

	/**
	 * @param SET
	 *            formerStateId the formerStateId to set
	 */
	public void setFormerStateId(int formerStateId) {
		this.formerStateId = formerStateId;
	}

	/**
	 * @param SET
	 *            isMain the isMain to set
	 */
	public void setIsMain(byte isMain) {
		this.isMain = isMain;
	}

	/**
	 * @return GET the userStateId
	 */
	public int getUserStateId() {
		return userStateId;
	}

	/**
	 * @param SET
	 *            userStateId the userStateId to set
	 */
	public void setUserStateId(int userStateId) {
		this.userStateId = userStateId;
	}

	/**
	 * @return GET the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param SET
	 *            userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return GET the type
	 */
	public byte getType() {
		return type;
	}

	/**
	 * @param SET
	 *            type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * @return GET the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param SET
	 *            id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return GET the stateId
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * @param SET
	 *            stateId the stateId to set
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return GET the heroId
	 */
	public int getHeroId() {
		return heroId;
	}

	/**
	 * @param SET
	 *            heroId the heroId to set
	 */
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	/**
	 * @return GET the userSoMsg
	 */
	public String getUserSoMsg() {
		return userSoMsg;
	}

	/**
	 * @param SET
	 *            userSoMsg the userSoMsg to set
	 */
	public void setUserSoMsg(String userSoMsg) {
		this.userSoMsg = userSoMsg;
	}

	/**
	 * @return GET the baseSoMsg
	 */
	public String getBaseSoMsg() {
		return baseSoMsg;
	}

	/**
	 * @param SET
	 *            baseSoMsg the baseSoMsg to set
	 */
	public void setBaseSoMsg(String baseSoMsg) {
		this.baseSoMsg = baseSoMsg;
	}

	/**
	 * @return GET the restTime
	 */
	public long getRestTime() {
		return restTime;
	}

	/**
	 * @param SET
	 *            restTime the restTime to set
	 */
	public void setRestTime(long restTime) {
		this.restTime = restTime;
	}

	/**
	 * @return GET the addition
	 */
	public int getAddition() {
		return addition;
	}

	/**
	 * @param SET
	 *            addition the addition to set
	 */
	public void setAddition(int addition) {
		this.addition = addition;
	}

	@Override
	public byte getModuleType() {
		return NTC_NATION_VEINS;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putInt(stateId);
		out.put(isMain);
		out.put(type);
		out.putInt(userId);
		out.putInt(userStateId);
		out.putInt(formerStateId);
		out.putInt(heroId);
		out.putPrefixedString(baseSoMsg, (byte) 2);
		out.putInt((int) (restTime / 1000));
		out.putInt(addition);
		if(userId == 0){
			out.putPrefixedString("",(byte)2);
		}else{
			PlayerCache ss  = GameUtils.getFromCache(userId);
			if(ss != null){
				out.putPrefixedString(ss.getName(),(byte)2);
			}else{
				out.putPrefixedString("",(byte)2);
			}
		}
//		out.putPrefixedString(username, (byte) 2);
		out.putInt(FightUtil.getSoldierNum(getBaseSoMsg()));
		Nation my = NationManager.getInstance().getNation(stateId);
		if(my != null){
			out.putPrefixedString(my.getName(),(byte)2);
		}else{
			out.putPrefixedString("",(byte)2);
		}
		Nation other = NationManager.getInstance().getNation(userStateId);
		if(other != null){
			out.putPrefixedString(other.getName(),(byte)2);
		}else{
			out.putPrefixedString("",(byte)2);
		}
	}

	@Override
	public void deserialize(JoyBuffer in) {
		this.id = in.getInt();
		this.stateId = in.getInt();
		this.isMain = in.get();
		this.type = in.get();
		this.userId = in.getInt();
		this.userStateId = in.getInt();
		this.formerStateId = in.getInt();
		this.heroId = in.getInt();
		this.userSoMsg = in.getPrefixedString();
		this.baseSoMsg = in.getPrefixedString();
		this.restTime = in.getLong();
		this.addition = in.getInt();
	}

	/**
	 *  保存数据
	 * @param sMsg
	 * @return
	 */
	public boolean saveSoldierMsg(HashMap<Integer, String> sMsg) {
		String s = PlayerBarrackManager.generateSoMsg(sMsg);
		if (s != null && !"".equals(s)) {
//			setUserSoMsg(getBaseSoMsg());
			setBaseSoMsg(s);
			naMgr.saveVeins(this);
			NationManager.getInstance().sendRmsOne(this, getUserStateId());
			NationManager.getInstance().sendRmsOne(this, getStateId());
			sendRefresh();
		}
//		World.getInstance().getPlayer(uid);
//		logger.info("XXXXXXXXX保存基本数据："+s);
		return true;
		
	}
	
	
	public String dispatchV(String msg,int sonum) {
		setUserSoMsg(getBaseSoMsg());
		HashMap<Integer, String> maps = PlayerBarrackManager.resolveSoMsg(msg);
		int id = 0;
		int num = 0;
		for (Integer i : maps.keySet()) {
			if (Integer.parseInt(maps.get(i).split(",")[0]) > num) {
				id = i;
				num = Integer.parseInt(maps.get(i).split(",")[0]);
			}
		}
		id = getRestraint(id);
		if(id == 0){
//			logger.info("获取克制兵种失败!");
			return "";
		}else{
			if(getHeroId() == 0){
				return dispatchVeins(id, sonum, "");
			}else{
				PlayerCharacter pp = World.getInstance().getPlayer(getUserId());
				PlayerHero hero = pp.getPlayerHeroManager().getHero(getHeroId());
				return dispatchVeins(id, hero.getSoldierNum(), "");
			}
		}
	}
	
	/**
	 * 获取克制兵种
	 * @param id
	 * @return
	 */
	public int getRestraint(int id) {
		switch (id) {
			case 1:
				return 3;
			case 2:
				return 1;
			case 3:
				return 2;
			case 4:
				return getSpecialSolder();
			case 5:
				return getSpecialSolder();
			case 6:
				return getSpecialSolder();
			default:
				return 0;
		}
	}

	/**
	 * 派兵应战
	 * 
	 * @param type
	 *            派兵类型
	 * @param soMsg
	 *            自己选择派兵
	 * @return
	 */
	public String dispatchVeins(int type, int num, String soMsg) {
//		logger.info("类型 ： " + type + "  派出士兵：" + soMsg);
		HashMap<Integer, String> map = PlayerBarrackManager.resolveSoMsg(soMsg);// 派兵数据
		HashMap<Integer, String> maps = PlayerBarrackManager
				.resolveSoMsg(getBaseSoMsg());
		HashMap<Integer, String> newMap = new HashMap<Integer, String>();
		if (maps != null) {
			if (map == null || map.size() == 0) {// 自动派兵
				if (num >= FightUtil.getSoldierNum(getBaseSoMsg())) {//派兵数
					setUserSoMsg(getBaseSoMsg());
					String msg = getBaseSoMsg();
					setBaseSoMsg("");
					naMgr.saveVeins(this);
					return msg;
				}
				if (maps.get(type) != null
						&& 0 != Integer.parseInt(maps.get(type).split(",")[0])) {
					if (Integer.parseInt(maps.get(type).split(",")[0]) > num) {
						newMap.put(type, num + ",0");
						maps.put(type,
								Integer.parseInt(maps.get(type).split(",")[0])-num
										+ ",0");
						num = 0;
					} else {
						num = num
								- Integer
										.parseInt(maps.get(type).split(",")[0]);
						newMap.put(type,
								Integer.parseInt(maps.get(type).split(",")[0])
										+ ",0");
						maps.put(type,"0,0");

					}
				}
				if (num > 0) {
					for (Integer i : maps.keySet()) {
						if (num <= 0) {
							break;
						} else if (Integer.parseInt(maps.get(i).split(",")[0]) > num) {
							newMap.put(i, num + ",0");
							maps.put(i,
									Integer.parseInt(maps.get(i).split(",")[0])
											- num + ",0");
							num = 0;
						} else if(Integer.parseInt(maps.get(i).split(",")[0]) > 0 && Integer.parseInt(maps.get(i).split(",")[0]) <=num){
							num = num
									- Integer
											.parseInt(maps.get(i).split(",")[0]);
							newMap.put(i,
									Integer.parseInt(maps.get(i).split(",")[0])
											+ ",0");
							maps.put(i,"0,0");

						}
					}
				}
				saveSoldierMsg(maps);
				return PlayerBarrackManager.generateSoMsg(newMap);
			} else {// 选择派兵
				if (map != null && map.size() > 0) {
					for (Integer i : map.keySet()) {
						if (maps.get(i) == null
								|| Integer.parseInt(maps.get(i).split(",")[0]) < Integer
										.parseInt(map.get(i).split(",")[0]))
							return "";
						else {
							maps.put(
									i,
									Integer.parseInt(maps.get(i).split(",")[0])
											- Integer.parseInt(map.get(i)
													.split(",")[0]) + ",0");
						}
					}
					saveSoldierMsg(maps);
					return soMsg;
				}
			}
		}
		return "";
	}
	/**
	 * 最大驻兵数
	 * @return
	 */
	public int maxBase() {
		int num = FightUtil.getSoldierNum(getBaseSoMsg());
		int poor = GameConst.MAX_VEINS_COUNT - num;
		return poor;
	}

	/**
	 * 回收或者添加士兵
	 * 
	 * @param soMsg
	 * @return
	 */
	public String recoverSoldier(String soMsg) {
//		logger.info("类型 ： " + type + "  回收士兵：" + soMsg);
		HashMap<Integer, String> map = PlayerBarrackManager.resolveSoMsg(soMsg);// 派兵
//		HashMap<Integer, String> newMap = new HashMap<Integer, String>();																		// 数据
		HashMap<Integer, String> maps = PlayerBarrackManager
				.resolveSoMsg(getBaseSoMsg());// 科技驻守士兵数据
		if (map == null || map.size() == 0)
			return "";
		int t = maxBase();
		if (t == 0) {
//			logger.info("类型 ： " + type + "|返回用户士兵：" + soMsg);
			return soMsg;// 返回士兵未驻防士兵
		} else if (t >= FightUtil.getSoldierNum(soMsg)) {// 差距兵数 > 派兵数
			for (Integer i : map.keySet()) {
				if(i > 3){
					int tt = getSpecialSolder();
					if(tt == 0){
						break;
					}
					String[] so = map.get(i).split(",");
					if (maps.get(tt) == null) {
//						newMap.put(i, so[0]+",0");
						maps.put(tt, so[0]+",0");
						map.put(i, "0,"+so[1]);
						
					} else {
						String[] ms = maps.get(tt).split(",");
//						newMap.put(i, so[0]+",0");
						maps.put(tt, Integer.parseInt(ms[0]) +Integer.parseInt(so[0])+",0");
						map.put(i, "0,"+so[1]);
					}
				}
				if (i < 4) {
					String[] so = map.get(i).split(",");
					if (maps.get(i) == null) {
//						newMap.put(i, so[0]+",0");
						maps.put(i, so[0]+",0");
						map.put(i, "0,"+so[1]);
						
					} else {
						String[] ms = maps.get(i).split(",");
//						newMap.put(i, so[0]+",0");
						maps.put(i, Integer.parseInt(ms[0]) +Integer.parseInt(so[0])+",0");
						map.put(i, "0,"+so[1]);
					}
				}
			}
//			String back = PlayerBarrackManager.generateSoMsg(newMap);
			saveSoldierMsg(maps);// 保存士兵信息
			return soMsg;
		} else {
			// int t = maxBase();
			// 弓兵 ：1 步兵 ：3 骑兵：2
			for (int x = 0; x < 3; x++) {
				int i = 0;
				if (x == 0) {
					i = 1;
				} else if (x == 1) {
					i = 3;
				} else if (x == 2) {
					i = 2;
				}
				// 补兵规则 弓 -> 步-> 骑
				if (map.get(i) != null
						&& Integer.parseInt(map.get(i).split(",")[0]) > 0) {
					if (maps.get(i) == null) {
						if (t == 0) {
							break;
						} else if (Integer.parseInt(map.get(i).split(",")[0]) > t) {
							maps.put(i, t + ",0");
							map.put(i,
									Integer.parseInt(map.get(i).split(",")[0])
											- t + ",0");
							t = 0;
						} else {
							t = t - Integer.parseInt(map.get(i).split(",")[0]);
							maps.put(i,
									Integer.parseInt(map.get(i).split(",")[0])
											+ ",0");
							map.put(i,"0,"+map.get(i).split(",")[1]);
						}
					} else {
						if (t == 0) {
							break;
						} else if (Integer.parseInt(map.get(i).split(",")[0]) > t) {
							maps.put(i,
									Integer.parseInt(maps.get(i).split(",")[0])
											+ t + ",0");
							map.put(i,
									Integer.parseInt(map.get(i).split(",")[0])
											- t + ",0");
							t = 0;
						} else {
							t = t - Integer.parseInt(map.get(i).split(",")[0]);
							maps.put(
									i,
									Integer.parseInt(maps.get(i).split(",")[0])
											+ Integer.parseInt(map.get(i)
													.split(",")[0]) + ",0");
							map.put(i,"0,"+map.get(i).split(",")[1]);
						}
					}
				}
			}
		}
		saveSoldierMsg(maps);// 保存士兵信息
		return PlayerBarrackManager.generateSoMsg(map);
	}
	
	/**
	 * 科技 撤退 清空用户和将领信息
	 * @return
	 */
	public boolean deleteVeins(boolean issend){
		int otherstateid = getUserStateId();
		this.setUserId(0);
		this.setHeroId(0);
		this.setHeroInfo("");
		this.setUserSoMsg("");
//		this.setUserStateId(getStateId());
//		this.setFormerStateId(getStateId());
		this.setUsername("");
		if(otherstateid != getStateId()){
			if(issend){
				this.setHeroId(-1);//设置负数，需要客户端接收方清空     资源台
			}
			NationManager.getInstance().sendRmsOne(this, otherstateid);
			if(issend){
				NationManager.getInstance().sendRmsOne(new RefreshResources(), otherstateid);
			}
		}
		this.setHeroId(0);
		NationManager.getInstance().sendRmsOne(this, getStateId());
		return true;
	}
	/**
	 * 发送消息
	 * @param lst
	 */
	public void sendRMS(List<ClientModule> lst,PlayerCharacter player){
		RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);//模块消息
		rms.addModules(lst);
		AndroidMessageSender.sendMessage(rms, player);
	}
	/**
	 * 驻扎 本州科技 (本州科技不 设置驻守士兵)
	 */
	public void stateAffVeins(PlayerCharacter p,PlayerHero ph,String soMsg){
		deleteVeins(true);
		this.setUserId((int)(p.getData().getUserid()));// 占领id
		this.setUsername(p.getData().getName());//占领者name
		this.setHeroId(ph.getId());// 武将id
		this.setHeroInfo(GameUtils.heroInfo(ph));// 武将id
		this.setBaseSoMsg(soMsg);// 原始驻兵
		this.setFormerStateId(getStateId());
		this.setUserStateId(p.getStateId());//本州id
		this.setRestTime(TimeUtils.nowLong());// 休整时间
		List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
		//先发将领再发神祠数据
		lst.add(new SimplePlayerHero(ph, ""));
		lst.add(this);
		NationManager.getInstance().sendRmsList(lst, p.getStateId());
		NationManager.getInstance().sendRmsList(lst, getStateId());
		sendRefresh();
	}
	
	/**
	 * 刷新页面请求
	 */
	public void sendRefresh(){
		NationManager.getInstance().sendRmsOne(new RefreshResources(), getUserStateId());
		if(getUserStateId() !=getStateId()){
			NationManager.getInstance().sendRmsOne(new RefreshResources(), getStateId());
		}
	}
	
	/**
	 * 收复 >>> 空闲附属科技
	 * @param type
	 * @param formerStateId
	 * @param heroId
	 * @param somsg
	 */
	public TipUtil recoverVeins(PlayerCharacter player){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		String str = "";
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			str = "Withdraw failed.";
		}else{
			str ="撤防失败!";
		}
		tip.setFailTip(str);
		int hero = this.getHeroId();
		if(player.getPlayerBuilgingManager().backHeroAndSoldier(hero, ""))
		{	
			this.deleteVeins(false);
			//************************
			RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);
			rms.addModule(this);
			rms.addModule(player.getPlayerHeroManager().getHero(hero));
			rms.addModule(naMgr.getsimple(player, getType(), player.getStateId()));
			AndroidMessageSender.sendMessage(rms, player);
			//************************
			logger.info("[recoverVeins]撤防附属神祠成功");
			sendRefresh();
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				str = "Withdraw other's Shrine successfully!";
			}else{
				str ="撤防附属神祠成功";
			}
			tip.setSuccTip(str);
		}else{
			logger.info("[recoverVeins]撤防附属神祠失败");
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				str = "Withdraw other's Shrine failed.";
			}else{
				str ="撤防失败!";
			}
			tip.setFailTip(str);
		}
		return tip;
	}
	
	/**
	 * 撤退
	 * @param this
	 * @return
	 */
	public TipUtil disarm(){
		TipUtil tip = new TipUtil(ProcotolType.BUILDING_RESP);
		synchronized (this) {
			logger.info("[disarm]>>>科技:"+getId());
			tip.setFailTip("撤防 失败!");
			PlayerCharacter player = World.getInstance().getPlayer(this.getUserId());
			if(player ==null ){
				return tip.setFailTip("用户没有对应的神祠");
			}
			if(this.recoverVeins(player).isResult()){
				tip.setSuccTip("");
				naMgr.saveVeins(this);
			}
			return tip;
		}
		
	}
	/**
	 * 战斗结束  -- 特种兵变成本国特种兵驻防
	 * @param soMsg
	 * @return
	 */
	public String stateSpecialSoldiers(int contryId,String soMsg){
		HashMap<Integer, String> sMsg = PlayerBarrackManager.resolveSoMsg(soMsg);
//		HashMap<Integer, String> state =new HashMap<Integer, String>();
		if(sMsg == null || sMsg.size() == 0){
			return "";
		}else{
			int ids = getSpecialSolder();
			if(ids == 0){
				for(int i = 4;i<7;i++){
					if(sMsg.get(i) == null){
						sMsg.put(i, "0,0");
					}
				}
			}else{
				for(int i = 4;i<7;i++){
					if(i != ids && ids != 0){
						if(sMsg.get(i) != null){
							int no = Integer.parseInt(sMsg.get(i).split(",")[0]);
							if(sMsg.get(ids) == null){
								sMsg.put(ids, no+",0");
								sMsg.put(i, "0,0");
							}else{
								sMsg.put(ids, Integer.parseInt(sMsg.get(ids).split(",")[0]) +no+",0");
								sMsg.put(i, "0,0");
							}
						}
					}
				}
			}
			return PlayerBarrackManager.generateSoMsg(sMsg);
		}
	}
	/**
	 * 派兵
	 * @param player
	 * @param heroId
	 * @param soMsg
	 * @return
	 */
	public boolean dispatch(PlayerCharacter player,int heroId, String soMsg){
//		logger.info("派兵状态 ---驻防将领id：" + heroId + " 派兵 --- 驻防士兵：" + soMsg);
		String memeo = "金矿神祠";
		String m1 = "驻守";
		String state ="本州";
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			memeo = "Gold Mine Shrine";
			m1 = "Garrsion in ";
			state = "OwnState";
		}
		
		if(getType() == 1){
			memeo = "资源神祠";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				memeo = "Res Mine Shrine";
				m1 = "Garrsion in ";
				state = "OwnState";
			}
		}
		
		
		if(getStateId() != player.getData().getNativeId()/100*100){
			Nation statess = NationManager.getInstance().getNation(getStateId());
			if(statess != null){
				state = statess.getName();
			}
		}
		boolean flag = player.getPlayerHeroManager().motifyStatus(heroId,
				GameConst.HEROSTATUS_ZHUFANG_VEINS,m1+state+memeo,"",TimeUtils.nowLong());// 驻防
		boolean flag1 = false;
		if (!"".equals(soMsg)){
			flag1 = player.getPlayerBuilgingManager().dispatchSoldier(soMsg);// 派兵
		}else{
			logger.info("派兵为空！");
			flag1 =  true;
		}
		if (flag && flag1) {
			return true;
		}else{
			logger.info("将领驻防或者派兵失败！");
			return false;
		}
	}
	
	/**
	 * 占领   本周科技
	 * @return
	 */
	public TipUtil occupyStateVeins(PlayerCharacter player,int playerHeroId,byte type,String tuisoMsg,FightEvent fightEvent1, FightEvent fightEvent2){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
//		logger.info("[occupyStateVeins]>>>科技:"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId());
		tip.setFailTip("占领本州科技:"+this.getId()+" 失败!");
		String name = "";
		if(this.getType() == 0){
			name = "金矿神祠";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				name = "Gold Mine Shrine";
			}
		}else{
			name = "资源神祠";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				name = "Res Mine Shrine";
			}
		}
//		logger.info("**************回复原士兵"+getUserSoMsg());
		//占领本周科技玩家
		PlayerCharacter p = World.getInstance().getPlayer(this.getUserId());
		if(playerHeroId == 0){//占领失败
//			setUserSoMsg(getBaseSoMsg());
			setBaseSoMsg(getUserSoMsg());
			//战报
			if(fightEvent1 != null && fightEvent2 !=null){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					fightEvent1.setMemo("You dispatched hero to garrison "+name+" but failed.");
					fightEvent2.setMemo("Your hero garrisoning "+name+" defended against "+player.getData().getName()+"'s attack.");
				}else{
					fightEvent1.setMemo("你派遣武将驻防本州"+name+"失败了");
					fightEvent2.setMemo("你驻防本州"+name+"的武将抵御了"+player.getData().getName()+"的进攻");
				}
				
			}
			NationManager.getInstance().sendRmsOne(this, getUserStateId());
			NationManager.getInstance().sendRmsOne(this, getStateId());
			sendRefresh();
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setFailTip("garrisoning failed."); 
			}else{
				tip.setFailTip("占领"+name+" 失败!");
			}
//			tip.setSuccTip("succ");
			return tip;
		}else{
			if(p==null){//无用户占领
				PlayerHero ph = player.getPlayerHeroManager().getHero(playerHeroId);
				if(ph != null && dispatch(player,playerHeroId, "")){
					stateAffVeins(player, ph,this.getBaseSoMsg());//本周科技占领
//					logger.info("[occupyStateVeins]>>>"+name+":"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId()+">>>占领科技成功");
					naMgr.saveVeins(this);
					sendRefresh();
					tip.setSuccTip("占领"+name+"成功");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setFailTip("garrisoning successfully."); 
					}
					return tip;
				}else{
					tip.setFailTip("将领状态错误!");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setFailTip("Hero status data error."); 
					}
					return tip;
				}
			}else{
				recoverSoldier(tuisoMsg);//防守方收兵回科技
				//用户将领回去
				int phero = getHeroId();
				if(p.getPlayerBuilgingManager().backHeroAndSoldier(getHeroId(), "")){
//					logger.info("[occupyStateVeins]>>>"+name+":原占领用户撤退");
					PlayerHero ph = player.getPlayerHeroManager().getHero(playerHeroId);
					if(ph != null && dispatch(player,playerHeroId, "")){
						stateAffVeins(player, ph,this.getUserSoMsg());//本周科技占领
//						logger.info("[occupyStateVeins]>>>"+name+":"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId()+">>>占领科技成功");
						//战报
						if(fightEvent1 != null && fightEvent2 !=null){
							if(I18nGreeting.LANLANGUAGE_TIPS == 1){
								fightEvent1.setMemo("You dispatched "+player.getPlayerHeroManager().getHero(playerHeroId).getName()+" and defeated "+p.getData().getName()+", successfully garrisoned "+name);
								fightEvent2.setMemo("Your hero"+name+" garrisoning "+p.getPlayerHeroManager().getHero(phero).getName()+" was defeated by "+player.getData().getName()+"");
							}else{
								fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(playerHeroId).getName()+"击败"+p.getData().getName()+"，成功驻防了本州"+name);
								fightEvent2.setMemo("你驻防本州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"被"+player.getData().getName()+"击败了，失去了驻防权");
							}
							
						}
						PushSign.sendOne(this, new PlayerCharacter[]{p}, ProcotolType.USER_INFO_RESP);
						tip.setSuccTip("占领"+name+"成功");
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setFailTip("garrisoning successfully."); 
						}
						return tip;
					}else{
//						logger.info("[occupyStateVeins]>>>用户|将领:"+playerHeroId +" 驻扎失败");
						tip.setFailTip("用户|将领:"+playerHeroId +" 驻扎失败");
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setFailTip("garrisoning failed."); 
						}
						return tip;
					}
				}else{
//					logger.info("[occupyStateVeins]>>>"+name+":"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId()+">>>回营失败");
					tip.setFailTip("用户:"+p.getData().getUserid()+"|将领:"+this.getHeroId() +" 回营失败");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setFailTip("garrisoning failed."); 
					}
					return tip;
				}
			}
		}
	}
	/**
	 * 占领  非本周科技
	 * @return
	 */
	public TipUtil occupyOtherStateVeins(PlayerCharacter player,int playerHeroId,String soMsg,String tuiSomSg,int thero,FightEvent fightEvent1, FightEvent fightEvent2,int deathNum){
		String name = "";
		String type= "";
		int oldid = getUserStateId();
		if(this.getType() == 0){
			name = "金矿神祠";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				name = "Gold Mine Shrine"; 
			}
		}else{
			name = "资源神祠";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				name = "Res Mine Shrine"; 
			}
		}
//		logger.info("[occupyOtherStateVeins]>>>"+name+":"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId()+"|将领:"+playerHeroId);
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("占领本州附属"+name+":"+this.getId()+" 失败!");
		String stateName = naMgr.getStateName(getStateId());//州名
		PlayerCharacter p = World.getInstance().getPlayer(this.getUserId());
		calculateMili(player,"", deathNum);//计算军功
		//用户将领回去
		int phero = getHeroId();
		if((naMgr.getVeinsAffiliated(player.getStateId(), getType()) >=2 ) && getStateId() != player.getStateId()){//不是收复自己矿脉
			if(playerHeroId == 0 && phero > 0){//战斗失败
				recoverSoldier(tuiSomSg);//防守方收兵回科技
				if(getStateId() == player.getStateId()){
					//战报
					if(fightEvent1 != null && fightEvent2 !=null){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched hero "+player.getPlayerHeroManager().getHero(thero).getName()+" to recapture "+name+" but failed.");
							fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" occupying "+stateName+"'s "+name+" defended against "+player.getData().getName()+"'s attack.");
						}else{
							fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(thero).getName()+"收复本州"+name+"时失败了");
							fightEvent2.setMemo("你占领"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"抵御了"+player.getData().getName()+"的进攻");
						}
						
					}
				}else{
					//战报
					if(fightEvent1 != null && fightEvent2 !=null){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched hero "+player.getPlayerHeroManager().getHero(thero).getName()+" to occupy "+stateName+"'s"+name+" but failed.");
							fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" garrisoning "+stateName+"'s "+name+" defended "+player.getData().getName()+"'s attack.");
						}else{
							fightEvent1.setMemo("你派遣武将"+player.getPlayerHeroManager().getHero(thero).getName()+"占领"+stateName+"州"+name+"时失败了");
							fightEvent2.setMemo("你驻防"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"抵御了"+player.getData().getName()+"的进攻");
						}
						
					}
				}
			}else if(phero > 0){
					//战报
					if(fightEvent1 != null && fightEvent2 !=null){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched"+ player.getPlayerHeroManager().getHero(playerHeroId).getName()+ "and defeated"+ stateName + "successfully");
							fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" occupying "+stateName+"'s "+name+" was defeated by "+player.getData().getName()+".");
						}else{
							fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(playerHeroId).getName()+"攻打"+stateName+"的"+name+"胜利");
							fightEvent2.setMemo("你占领"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"被"+player.getData().getName()+"击败了");
						}
						
					}
			}
			if(naMgr.soldiersVeins(this) <= 0){
				if(p != null){
					//士兵为 0 武将回去
					if(p.getPlayerBuilgingManager().backHeroAndSoldier(getHeroId(), "")){
						deleteVeins(true);//清空数据
						NationManager.getInstance().sendRmsOne(this, getUserStateId());
						NationManager.getInstance().sendRmsOne(this, getStateId());
						sendRefresh();
					}
				}
			}
			tip.setFailTip("附属"+name+"位置已满，占领失败");
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				
				tip.setFailTip("The number of Shrines that you can occupy has been full.");
			}
			return tip;
		}else{
			if(playerHeroId == 0){//战斗失败
//				player.getPlayerBuilgingManager().dispatchSoldier(soMsg);
//				this.dispatchV(soMsg, FightUtil.getSoldierNum(soMsg));
//					logger.info("防守方收兵回"+name+":"+tuiSomSg);
					recoverSoldier(tuiSomSg);//防守方收兵回科技
				if(getStateId() == player.getStateId()){
					//战报
					if(fightEvent1 != null && fightEvent2 !=null){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched hero "+player.getPlayerHeroManager().getHero(thero).getName()+" to recapture "+name+" but failed.");
							fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" occupying "+stateName+"'s "+name+" defended against "+player.getData().getName()+"'s attack.");
						}else{
							fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(thero).getName()+"收复本州"+name+"时失败了");
							fightEvent2.setMemo("你占领"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"抵御了"+player.getData().getName()+"的进攻");
						}
						
					}
				}else{
					//战报
					if(fightEvent1 != null && fightEvent2 !=null){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched hero "+player.getPlayerHeroManager().getHero(thero).getName()+" to occupy "+stateName+"'s "+name+" but failed.");
							fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" garrisoning "+stateName+"'s "+name+" defended "+player.getData().getName()+"'s attack.");
						}else{
							fightEvent1.setMemo("你派遣武将"+player.getPlayerHeroManager().getHero(thero).getName()+"占领"+stateName+"州"+name+"时失败了");
							fightEvent2.setMemo("你驻防"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"抵御了"+player.getData().getName()+"的进攻");
						}
						
					}
				}
//				if(p != null){
//					PushSign.sendOne(this, new PlayerCharacter[]{p}, ProcotolType.NATION_RESP);
//				}
				tip.setFailTip("占领失败");
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("garrisoning failed.");
				}
				return tip;
			}
			if(p == null){//防御方不存在
				if(naMgr.soldiersVeins(this) > 0){
					tip.setFailTip("士兵未消耗完,不能占领");
					//战报
					if(fightEvent1 != null){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched"+ player.getPlayerHeroManager().getHero(playerHeroId).getName()+ "and defeated"+ stateName + "successfully");
						}else{
							fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(playerHeroId).getName()+"攻打"+stateName+"的"+name+"胜利");
						}
						
					}
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setFailTip("You haven't killed all the troops!");
					}
					
					return tip;
				}
				PlayerHero ph = player.getPlayerHeroManager().getHero(playerHeroId);
				if(ph != null && dispatch(player,playerHeroId, soMsg)){
					stateAffVeins(player, ph,stateSpecialSoldiers(player.getStateId()/1000*1000, soMsg));//
//					logger.info("[occupyStateVeins]>>>"+name+":"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId()+">>>占领科技成功");
					tip.setSuccTip("");
					sendRefresh();
					//全局消息
					Nation my = NationManager.getInstance().getNation(player.getData().getNativeId()/100*100);
					Nation otherstate =  NationManager.getInstance().getNation(this.getStateId());
					if(my != null && otherstate!=null){
						String msg = "";
						if(this.getType() == 0){
							if(getStateId() == player.getStateId()){//收复
								type = "veinsGold.my";
							}else{
								type = "veinsGold.other";
							}
							//msg = I18nGreeting.getInstance().getMessage("veinsGold", new Object[]{my.getName(),player.getData().getName(),otherstate.getName()});
						}else{
							if(getStateId() == player.getStateId()){//收复
								type = "veinsRes.my";
							}else{
								type = "veinsRes.other";
							}
							//msg = I18nGreeting.getInstance().getMessage("veinsRes", new Object[]{my.getName(),player.getData().getName(),otherstate.getName()});
						}
						msg = I18nGreeting.getInstance().getMessage(type, new Object[]{my.getName(),player.getData().getName(),otherstate.getName()});
						if(!"".equals(msg)){
							NoticeManager .getInstance().sendSystemWorldMessage(msg);
							//GameUtils.sendWolrdMessage(new TipMessage(msg, ProcotolType.USER_INFO_RESP, GameConst.GAME_RESP_SUCCESS), (byte)1);
						}
						//发送全部goldmine数据
						NationManager.getInstance().getAllStateResource(getType(), player.getStateId(),oldid,getStateId());
					}
					return tip;
				}else{
					tip.setFailTip("将领状态错误!");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setFailTip("Hero status data error."); 
					}
					return tip;
				}
			}else{
				if(naMgr.soldiersVeins(this) > 0){
					tip.setFailTip(name+"士兵未消耗完,不能占领");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setFailTip("You haven't killed all the troops!"); 
					}
					//战报
					if(fightEvent1 != null && fightEvent2 !=null){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							fightEvent1.setMemo("You dispatched"+ player.getPlayerHeroManager().getHero(playerHeroId).getName()+ "and defeated"+ stateName + "successfully");
							fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" occupying "+stateName+"'s "+name+" was defeated by "+player.getData().getName()+".");
						}else{
							fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(playerHeroId).getName()+"攻打"+stateName+"的"+name+"胜利");
							fightEvent2.setMemo("你占领"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"被"+player.getData().getName()+"击败了");
						}
						
					}
					return tip;
				}
				if(p.getPlayerBuilgingManager().backHeroAndSoldier(getHeroId(), "")){
//					logger.info("[occupyStateVeins]>>>"+name+":原占领用户撤退");
					PlayerHero ph = player.getPlayerHeroManager().getHero(playerHeroId);
					if(ph != null && dispatch(player,playerHeroId, soMsg)){
						stateAffVeins(player, ph,stateSpecialSoldiers(player.getStateId()/1000*1000, soMsg));//本周科技占领
						logger.info("[occupyStateVeins]>>>"+name+":"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId()+">>>占领科技成功");
						//战报
						if(getStateId() == player.getStateId()){
							if(fightEvent1 != null && fightEvent2 !=null){
								if(I18nGreeting.LANLANGUAGE_TIPS == 1){
									fightEvent1.setMemo("You dispatched hero "+player.getPlayerHeroManager().getHero(playerHeroId).getName()+" and recaptured "+name);
									fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" occupying "+stateName+"'s "+name+" was defeated by "+player.getData().getName()+".");
								}else{
									fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(playerHeroId).getName()+"收复了本州"+name);
									fightEvent2.setMemo("你占领"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"被"+player.getData().getName()+"击败了，失去了驻防权");
								}
								
							}
						}else{
							if(fightEvent1 != null && fightEvent2 !=null){
								if(I18nGreeting.LANLANGUAGE_TIPS == 1){
									fightEvent1.setMemo("You dispatched hero "+player.getPlayerHeroManager().getHero(playerHeroId).getName()+" and occupied "+stateName+"'s "+name);
									fightEvent2.setMemo("Your hero "+p.getPlayerHeroManager().getHero(phero).getName()+" garrisoning "+stateName+"'s "+name+" was defeated by "+player.getData().getName()+".");
								}else{
									fightEvent1.setMemo("你派遣"+player.getPlayerHeroManager().getHero(playerHeroId).getName()+"占领了"+stateName+"州"+name);
									fightEvent2.setMemo("你驻防"+stateName+"州"+name+"的武将"+p.getPlayerHeroManager().getHero(phero).getName()+"被"+player.getData().getName()+"击败了，失去了驻防权");
								}
								
							}
						}
						//全局消息
						Nation my = NationManager.getInstance().getNation(player.getData().getNativeId()/100*100);
						Nation otherstate =  NationManager.getInstance().getNation(this.getStateId());
						if(my != null && otherstate!=null){
							String msg = "";
							if(this.getType() == 0){
								if(getStateId() == player.getStateId()){//收复
									type = "veinsGold.my";
								}else{
									type = "veinsGold.other";
								}
								//msg = I18nGreeting.getInstance().getMessage("veinsGold", new Object[]{my.getName(),player.getData().getName(),otherstate.getName()});
							}else{
								if(getStateId() == player.getStateId()){//收复
									type = "veinsRes.my";
								}else{
									type = "veinsRes.other";
								}
								//msg = I18nGreeting.getInstance().getMessage("veinsRes", new Object[]{my.getName(),player.getData().getName(),otherstate.getName()});
							}
							msg = I18nGreeting.getInstance().getMessage(type, new Object[]{my.getName(),player.getData().getName(),otherstate.getName()});
							if(!"".equals(msg)){
								NoticeManager.getInstance().sendSystemWorldMessage(msg);
								//GameUtils.sendWolrdMessage(new TipMessage(msg, ProcotolType.USER_INFO_RESP, GameConst.GAME_RESP_SUCCESS), (byte)1);
							}
							//发送全部gouldmine数据
							NationManager.getInstance().getAllStateResource(getType(), getStateId(),oldid,getStateId());
						}
						
						//PushSign.sendOne(this, new PlayerCharacter[]{p}, ProcotolType.NATION_RESP);
						tip.setSuccTip("");
						sendRefresh();
						return tip;
					}else{
//						logger.info("[occupyStateVeins]>>>用户|将领:"+playerHeroId +" 驻扎失败");
						tip.setFailTip("用户|将领:"+playerHeroId +" 驻扎失败");
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setFailTip("garrisoning failed."); 
						}
						return tip;
					}
				}else{
//					logger.info("[occupyStateVeins]>>>"+name+":"+this.getId()+"|所属州:"+this.getStateId()+"|占领者所属州:"+this.getUserStateId()+">>>回营失败");
					tip.setFailTip("用户:"+p.getData().getUserid()+"|将领:"+this.getHeroId() +" 回营失败");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						tip.setFailTip("garrisoning failed."); 
					}
					return tip;
				}
			}
		}
	}
	
	/**
 	 * 占领科技
	 * @return
	 */
	public TipUtil occupyVeins(PlayerCharacter player,int playerHeroId,String msg,String tuismg,int theros,FightEvent fightEvent1, FightEvent fightEvent2,int deathNum){
		String name = "";
		if(this.getType() == 0){
			name = "金矿神祠";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				name = "Gold Mine Shrine";
			}
		}else{
			name = "资源神祠";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				name = "Res Mine Shrine";
			}
		}
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
//		logger.info("[occupyVeins]"+name+":"+id+"|hero:"+playerHeroId+"|士兵:"+msg);
		tip.setFailTip("占领本州附属"+name+":"+id+" 失败!");
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			tip.setFailTip("Occupy other 's "+name+":"+id+" failed.");
		}
//		GoldMine gold = naMgr.getMyOccupyGold(this.getType(),player);
//		if(gold == null){
//			return tip.setFailTip("没有对应资源点,不能占领科技");
//		}
		SimpleResource simpleResource = naMgr.getsimple(player,getType(),getStateId());
		if(playerHeroId == 0){
//			player.getPlayerBuilgingManager().dispatchSoldier(msg);
//			this.dispatchV(msg, FightUtil.getSoldierNum(msg));
		}
		byte type = this.getType();
		if(this.getUserStateId() == player.getStateId())
		{
			//科技属于本州
			TipUtil myTip = occupyStateVeins(player, playerHeroId, type,tuismg,fightEvent1,fightEvent2);
			if(myTip.isResult()){
//				logger.info("[occupyVeins]"+name+":"+id+"|类型:"+type+"|hero:"+playerHeroId+"|士兵:"+msg+">>>占领成功");
				//************************
				RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);
				//rms.addModule(this);
				rms.addModule(simpleResource);
				rms.addModule(player.getPlayerHeroManager().getHero(playerHeroId));
				AndroidMessageSender.sendMessage(rms, player);
				//************************
				if(type == 0){
					String message = I18nGreeting.getInstance().getMessage("veins.occupy.success", new Object[]{"矿神"});
					tip.setSuccTip(message);
				}
				else{
					String message = I18nGreeting.getInstance().getMessage("veins.occupy.success", new Object[]{"女神"});
					tip.setSuccTip(message);
				}
				//tip.setSuccTip("占领成功");
			}else{
//				logger.info("[occupyVeins]"+name+":"+id+"|类型:"+type+"|hero:"+playerHeroId+"|士兵:"+msg+">>>占领失败"+"["+myTip.getResultMsg()+"]");
				tip = myTip;
				//************************
				RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);
				AndroidMessageSender.sendMessage(rms, player);
				//************************
				return tip;
			}
		}else if(this.getUserStateId() != player.getStateId()){
			TipUtil myTip = occupyOtherStateVeins(player, playerHeroId, msg,tuismg,theros,fightEvent1,fightEvent2,deathNum);
			if(myTip.isResult()){
//				logger.info("[occupyVeins]"+name+":"+id+"|类型:"+type+"|hero:"+playerHeroId+"|士兵:"+msg+">>>占领成功");
				//************************
				RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);
				rms.addModule(this);
				rms.addModule(simpleResource);
				rms.addModule(player.getPlayerHeroManager().getHero(playerHeroId));
				AndroidMessageSender.sendMessage(rms, player);
				//************************
				naMgr.saveVeins(this);
				String message = I18nGreeting.getInstance().getMessage("veins.occupy.success", new Object[]{name});
				tip.setSuccTip(message);
				GameUtils.sendTip(tip.getTip(),player.getUserInfo(),GameUtils.FLUTTER);
			}else{
//				logger.info("[occupyVeins]"+name+":"+id+"|类型:"+type+"|hero:"+playerHeroId+"|士兵:"+msg+">>>占领失败"+"["+myTip.getResultMsg()+"]");
				tip = myTip;
				GameUtils.sendTip(tip.getTip(),player.getUserInfo(),GameUtils.FLUTTER);
				//************************
				RespModuleSet rms=new RespModuleSet(ProcotolType.NATION_RESP);
				AndroidMessageSender.sendMessage(rms, player);
				//************************
				return tip;
			}
		}
		return tip;
	}
	
	public boolean isAdd(PlayerCharacter player){
		if(this.getUserStateId() == player.getStateId()){
			return true;
		}else{
			return false;
		}
	}
	
	public int calculateMili(PlayerCharacter player,String add,int num){
//		logger.info("军功:"+num);
		if(num == 0)//增兵
		{
			HashMap<Integer, String> maps = PlayerBarrackManager
					.resolveSoMsg(add);// 科技驻守士兵数据
			int pnum = 0;
			int tnum = 0;
			for(Integer key : maps.keySet()){
				if(key < 4){
					int a = Integer.parseInt(maps.get(key).split(",")[0]);
					pnum += a;
				}else{
					int a = Integer.parseInt(maps.get(key).split(",")[0]);
					tnum += a;
				}
			}
//			logger.info("普通兵:"+pnum + "|特种兵:"+tnum);
//			logger.info("增兵>>>>>>>>>>军功数量:"+pnum*0.1+tnum*0.4);
			player.recordRoleData(GameConfig.MEDALS, (int)(pnum*0.1+tnum*0.4));
			return (int)(pnum*0.1+tnum*0.4);
			//return (int)(pnum*0.1+tnum*0.4);
		}else{
			int m = (int)(num * 0.1);
			if(m < 1){
				m = 1;
			}
//			logger.info("攻击矿脉>>>>>>军功数量:"+m);
			player.recordRoleData(GameConfig.MEDALS, m);
			//return m;
			return m;
		}
	}
	/**
	 * 增兵 >>> 科技派兵 
	 * @param v
	 * @param add
	 * @return
	 */
	public TipUtil addSoldier(PlayerCharacter player,String add){
		TipUtil tip = new TipUtil(ProcotolType.NATION_RESP);
		tip.setFailTip("增兵失败");
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			tip.setFailTip("Add troops failed.");
		}
		if(this.getUserStateId() == player.getStateId()){
			String reco = this.recoverSoldier(add);//添加
			int num = calculateMili(player,reco, 0);//计算军功
			String msg = I18nGreeting.getInstance().getMessage("soldier.add.success", new Object[]{num});
			tip.setSuccTip(msg);
			SimpleResource si = naMgr.getsimple(player,getType(),getUserStateId());
			RespModuleSet rms=new RespModuleSet(ProcotolType.NATION_RESP);
			rms.addModule(si);
			//rms.addModule(this);
			AndroidMessageSender.sendMessage(rms, player);
			return tip;
		}else{
			//************************
			RespModuleSet rms=new RespModuleSet(ProcotolType.NATION_RESP);
			AndroidMessageSender.sendMessage(rms, player);
			//************************
			tip.setFailTip("非本州科技无法增兵");
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setFailTip("You can't add troops in this state.");
			}
			
			return tip;
		}
	}
	
	/**
	 * 剔除和特种兵
	 * @param add
	 * @return
	 */
	public String takeOffSoliderEqu(String add){
		int need = maxBase();
		if(need <= 0) {
			return "";
		}else if(need > 0 && need >= FightUtil.getSoldierNum(add)){
			HashMap<Integer, String> map = PlayerBarrackManager.resolveSoMsg(add);// 派兵
			HashMap<Integer, String> newMap = new HashMap<Integer, String>();
			for(Integer i : map.keySet()){
				newMap.put(i, map.get(i).split(",")[0]+",0");
			}
			return PlayerBarrackManager.generateSoMsg(newMap);
		}else{
			HashMap<Integer, String> map = PlayerBarrackManager.resolveSoMsg(add);// 派兵
			HashMap<Integer, String> newMap = new HashMap<Integer, String>();
			for(Integer i : map.keySet()){
				if(need > 0){
					int num = Integer.parseInt(map.get(i).split(",")[0]);
					if(num >= need){
						newMap.put(i, need+",0");
						need = 0;
					}else{
						newMap.put(i, num+",0");
						need -= num ;
					}
				}
			}
			return PlayerBarrackManager.generateSoMsg(newMap);
		}
	}
	
	/**
	 * 是否能够占领
	 * @return
	 */
	public boolean isOcc(){
		if(FightUtil.getSoldierNum(getBaseSoMsg()) > 0)
			return false;
		return true;
	}
	/**
	 * 校验占领
	 * @param player
	 * @return
	 */
	public TipUtil checks(PlayerCharacter player){
		TipUtil tip = new TipUtil(ProcotolType.NATION_RESP);
		if(naMgr.getMyOccupyGold(this.getType(),player) != null){
			return tip.setSuccTip("");
		}
		tip.setFailTip("没有金矿不能占领!");
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			tip.setFailTip("Occupy failed!");
		}
		return tip;
	}
	/**
	 * 获取本州特种兵类型
	 * @return
	 */
	public int getSpecialSolder(){
		int country = getUserStateId()/1000*1000;
		switch (country) {
		case 1000:
			return 6;
		case 2000:
			return 5;
		case 3000:
			return 4;
		default:
			return 0;
		}
	}
	/**
	 * 检查战斗
	 * @param id
	 * @param hero
	 * @param msg
	 * @return
	 */
	public boolean checkFight(PlayerCharacter player,PlayerHero hero,String msg,String tuimsg){
		TipUtil tip = new TipUtil(ProcotolType.NATION_RESP);
		if(player.getData().getUserid() == getUserId()){
			tip.setFailTip("自己无法攻打自己的神祠");
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				tip.setFailTip("You can't attack your own Shrine!");
			}
			GameUtils.sendTip(tip.getTip(), player.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
//		if(this.getUserStateId() != player.getStateId() && !naMgr.getFreeVeins(player.getStateId(), type)){
//			GameUtils.sendTip(tip.setFailTip("没有空闲科技").getTip(),player.getUserInfo());
//			return false;
//		}
//		if(this.getHeroId() == 0 && this.getStateId() != player.getStateId()){
//			this.dispatchV(msg, FightUtil.getSoldierNum(msg));
////			player.getPlayerBuilgingManager().recoverSoldier(msg);
//			tip = occupyVeins(player, hero.getId(), msg,tuimsg,hero.getId(),null,null,0);
//			GameUtils.sendTip(tip.getTip(),player.getUserInfo());
//			return false;
//		}
		if(this.getHeroId() == 0 && this.getUserStateId() == player.getStateId()){
//			this.dispatchV(msg, FightUtil.getSoldierNum(msg));
//			player.getPlayerBuilgingManager().recoverSoldier(msg);
			tip = occupyVeins(player, hero.getId(), msg,tuimsg,hero.getId(),null,null,0);
			//GameUtils.sendTip(tip.getTip(),player.getUserInfo());
			return false;
		}else if(this.getHeroId() == 0 && this.getUserStateId() != player.getStateId()){
			int dea = FightUtil.getSoldierNum(getBaseSoMsg());//矿脉兵数
			this.dispatchV(msg, FightUtil.getSoldierNum(msg));
			if(dea >= FightUtil.getSoldierNum(msg)){
				dea =  FightUtil.getSoldierNum(msg);//死亡士兵
			}
			tip = occupyVeins(player, hero.getId(), msg,tuimsg,hero.getId(),null,null,dea);
			//GameUtils.sendTip(tip.getTip(),player.getUserInfo());
			return false;
		}else if(this.getHeroId() != 0 && FightUtil.getSoldierNum(getBaseSoMsg())==0){
			tip = occupyVeins(player, hero.getId(), msg,tuimsg,hero.getId(),null,null,0);
			//GameUtils.sendTip(tip.getTip(),player.getUserInfo());
			return false;
		}
		return true;
	}
}
