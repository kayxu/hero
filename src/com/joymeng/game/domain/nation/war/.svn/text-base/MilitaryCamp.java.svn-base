package com.joymeng.game.domain.nation.war;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.PushSign;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.fight.FightUtil;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 据点据点
 * @author Administrator
 *
 */
public class MilitaryCamp extends ClientModuleBase{
	static MongoServer mongs = MongoServer.getInstance();
	//MongoServer.getInstance().getPlayerCacheDAO().findPlayerCacheByUserid(getFriendId());
	static int REST_DURATION = 3 * 60;//占领保护时间
	
	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(MilitaryCamp.class);
	
	PlayerCache occCache;//用户cache
	/**
	 * id
	 */
	int id;
	/**
	 * 区域id
	 */
	int nativeId;

	/**
	 * 英雄
	 */
	int heroId;
	/**
	 * 士兵信息
	 */
	String soliderInfo;
	/**
	 * 占领时间
	 */
	int occTime;
	/**
	 * 是否本市/州/国
	 */
	boolean isThis;
	
	/**
	 * 伪据点,原来城池占领数据
	 */
	boolean isFalseCamp;

	int gameStartTime;//
	
	public int getNativeId() {
		return nativeId;
	}
	public void setNativeId(int nativeId) {
		this.nativeId = nativeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public String getSoliderInfo() {
		return soliderInfo;
	}
	public void setSoliderInfo(String soliderInfo) {
		this.soliderInfo = soliderInfo;
	}
	public boolean isThis() {
		return isThis;
	}
	public void setThis(boolean isThis) {
		this.isThis = isThis;
	}
	
	
	public PlayerCache getOccCache() {
		return occCache;
	}
	public void setOccCache(PlayerCache occCache) {
		this.occCache = occCache;
	}
	public int getOccTime() {
		return occTime;
	}
	public void setOccTime(int occTime) {
		this.occTime = occTime;
	}
	
	public boolean isFalseCamp() {
		return isFalseCamp;
	}
	public void setFalseCamp(boolean isFalseCamp) {
		this.isFalseCamp = isFalseCamp;
	}
	
	public void save(){
		WarManager.getInstance().saveCamp(this);
	}
	
	public int getGameStartTime() {
		return gameStartTime;
	}
	public void setGameStartTime(int gameStartTime) {
		this.gameStartTime = gameStartTime;
	}
	@Override
	public byte getModuleType() {
		return NTC_CAMP_BATTLE;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(getId());
		out.putInt(getNativeId());
		out.putInt(getHeroId());
		if(isFalseCamp){
			out.put((byte)1);
		}else{
			out.put((byte)0);
		}
		if(getOccCache() == null){
			out.putPrefixedString("",JoyBuffer.STRING_TYPE_SHORT);
			out.putInt(0);
		}else{
			out.putPrefixedString(getOccCache().getName(),JoyBuffer.STRING_TYPE_SHORT);
			out.putInt(getOccCache().getUserid());
		}
		if(isThis()){
			out.putInt(getOccTime() + 240 * 60 * 60);
		}else{
			out.putInt(getOccTime() + REST_DURATION);
		}
		out.putInt((int)(TimeUtils.nowLong()/1000) + 10);
		if(getNativeId()%1000 == 0){
			out.putInt(gameStartTime + 30 * 60);
		}else if(getNativeId()%100 == 0){
			out.putInt(gameStartTime + 30 * 60);
		}else if(getNativeId()%10 == 0){
			out.putInt(gameStartTime + 20 * 60);
		}
		if("".equals(getSoliderInfo())){
			out.putInt(0);
		}else{
			out.putInt(FightUtil.getSoldierNum(getSoliderInfo()));
		}
		out.putPrefixedString(getSoliderInfo(),JoyBuffer.STRING_TYPE_SHORT);
		logger.info(">>>MilitaryCamp 区域:"+getNativeId()+"|发送成功");
		
	}
	/**
	 * 是否是本区域
	 * @param my
	 * @return
	 */
	public boolean isArea(int my){
		if(nativeId%1000 == 0){
			if(nativeId == my/1000*1000){
				return true;
			}else{
				return false;
			}
		}else if (nativeId%100 == 0){
			if(nativeId == my/100*100){
				return true;
			}else{
				return false;
			}
		}else if (nativeId%10 == 0){
			if(nativeId == my/10*10){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	/**
	 * 据点是否可以占领
	 */
	public  TipUtil isOccupied(PlayerCharacter att){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("fail");
//		synchronized (lock) {
			Nation nation = NationManager.getInstance().getNation(getNativeId());
			if(nation == null){
//				logger.info("未开启争夺战");
				if(I18nGreeting.LANLANGUAGE_TIPS == 1) {
					tip.setFailTip("Battlefield locked!");
				}else{
					tip.setFailTip("未开启争夺战");
				}
				
				return tip;
			}
			if(!isFalseCamp){
				if(isThis()){
//					logger.info("本区域玩家占领,不能占领");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1) {
						tip.setFailTip("Occupy failed! Only the players in this area can occupy.");
					}else{
						tip.setFailTip("本区域玩家占领,不能占领");
					}
					
					return tip;
				}
				if(WarManager.getInstance().getMyCamp(att.getId(),getNativeId()) != null && !WarManager.getInstance().getMyCamp(att.getId(),getNativeId()).isFalseCamp ){
					if(I18nGreeting.LANLANGUAGE_TIPS == 1) {
						tip.setFailTip("Occupied Stronghold already.");
					}else{
						tip.setFailTip("已占领据点");
					}
//					logger.info(">>>不能发生战斗:"+"已占领据点");
					return tip;
				}else if(WarManager.getInstance().getMyCamp(att.getId(),getNativeId()) != null && WarManager.getInstance().getMyCamp(att.getId(),getNativeId()).isFalseCamp ){
					
					if(I18nGreeting.LANLANGUAGE_TIPS == 1) {
						tip.setFailTip("Occupied Capitol already.");
					}else{
						tip.setFailTip("已占领主城");
					}
//					logger.info(">>>不能发生战斗:"+"已占领主城");
					return tip;
				}
				if(att.getData().getTitle() != getUserTitle() && att.getId() != nation.getOccupyUser()){
					if(I18nGreeting.LANLANGUAGE_TIPS == 1) {
						tip.setFailTip("Official position is not enough or has been beyond.");
					}else{
						tip.setFailTip("官位不足或超出");
					}
					
//					logger.info(">>>不能发生战斗:"+"用户官位不足或超出");
					return tip;
				}
				if(TimeUtils.nowLong()/1000 - REST_DURATION  >= getOccTime()){
//					logger.info("");
					return tip.setSuccTip("");
				}else{
//					logger.info("保护时间内，无法攻击");
					if(I18nGreeting.LANLANGUAGE_TIPS == 1) {
						tip.setFailTip("Attack failed!It's in the period of protection.");
					}else{
						tip.setFailTip("保护时间内，无法攻击");
					}
					
					return tip;
				}
			}else {
				if(att.getData().getTitle() != getUserTitle() && att.getId() != nation.getOccupyUser()){
					if(I18nGreeting.LANLANGUAGE_TIPS == 1) {
						tip.setFailTip("Official position is not enough or has been beyond.");
					}else{
						tip.setFailTip("官位不足或超出");
					}
					logger.info(">>>不能发生战斗:"+"用户官位不足或超出");
					return tip;
				}
			}
			
			return tip.setSuccTip("");
//		}
	}

	/**
	 * 检验是否会战斗
	 */
	public  boolean checkFight(PlayerCharacter att,int playerHeroId,String attMsg,String defMsg,int attHeros){
//		synchronized (lock) {
			
			WarManager.IS_FIGHT = true;
			TipUtil tip = isOccupied(att);
			if(att.getData().getLevel() > GameConst.CAMP_END || att.getData().getLevel() < GameConst.CAMP_BEGIN){
//				logger.info("夺据点和市需要玩家等级10~30");
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setSuccTip("Occupying Strongholds and Cities need Lv.10-Lv.30.");
				}else{
					tip.setSuccTip("夺据点和市需要玩家等级10~30");
				}
				
				GameUtils.sendTip(tip.getTip(), att.getUserInfo(),GameUtils.FLUTTER);
				return false;
			}
			if(!WarManager.IS_FIGHT){
//				logger.info(">>>不能发生战斗:战斗为开放");
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("Can't battle: Locked.");
				}else{
					tip.setFailTip("不能发生战斗:战斗未开放");
				}
				
				//发送提示消息
				GameUtils.sendTip(tip.getTip(), att.getUserInfo(),GameUtils.FLUTTER);
				return false;
			}
			if(!tip.isResult()){
				tip.setFailTip(tip.getResultMsg());
//				logger.info(">>>不能发生战斗:"+tip.getResultMsg());
				GameUtils.sendTip(tip.getTip(), att.getUserInfo(),GameUtils.FLUTTER);
				return false;
			}
			if(getOccCache() == null){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("Can't battle.");
				}else{
					tip.setFailTip("无人占领据点");
				}
				
//				logger.info(">>>不能发生战斗:"+"无人占领据点");
				//占领
				tip = occupied(att, playerHeroId, attMsg, defMsg, attHeros, null, null);
				GameUtils.sendTip(tip.getTip(), att.getUserInfo(),GameUtils.FLUTTER);
				return false;
			}
			return true;
//		}
		
	}
	/**
	 * 直接占领
	 */
	public  void directOccupied(PlayerCharacter att,PlayerHero hero,String solider,boolean type){
//		synchronized (lock) {
			setOccCache(null);
			if(att != null){
				setOccCache(mongs.getLogServer().getPlayerCacheDAO().findPlayerCacheByUserid(att.getId()));
				setOccTime((int)(TimeUtils.nowLong()/1000));
			}
			if(isFalseCamp){
				setOccTime((int)(TimeUtils.nowLong()/1000));
			}
			if(hero != null){
				setHeroId(hero.getId());
				//logger.info("=======directOccupied");
				WarManager.getInstance().sendRmsOne(new SimplePlayerHero(hero, ""), getNativeId());
			}else{
				setHeroId(0);
			}
			
			if(att == null){
				setOccTime(0);
			}
			
			setSoliderInfo(solider);
			
			setThis(type);
			save();
			//logger.info("=======directOccupied");
			//全员推送消息
			WarManager.getInstance().sendRmsOne(this, getNativeId());
//		}
		
	}
	
	/**
	 * 修改士兵
	 */
	public void motifyCampSolider(String solider){
//		setUserId(att.getId());
//		setHeroId(hero);
		setSoliderInfo(solider);
		save();
		logger.info("=======motifyCampSolider");
		//全员推送消息
		WarManager.getInstance().sendRmsOne(this, getNativeId());
	}
	/**
	 * 重置
	 * @param defMsg
	 */
	public void resetHero(String defMsg){
		int id = 0;
		if(getOccCache() != null){
			id = getOccCache().getUserid();
		}
		PlayerCharacter def = World.getInstance().getPlayer(id);
		if(def != null){
			//rms
			PlayerHero defHero = def.getPlayerHeroManager().getHero(getHeroId());
			if(defHero != null){
				defHero.setSoldier(defMsg);
				//全员推送消息
				logger.info("=======resetHero");
				WarManager.getInstance().sendRmsOne(new SimplePlayerHero(defHero,""), getNativeId());
			}
			setSoliderInfo(defMsg);
			save();
			//全员推送消息
			logger.info("=======resetHero");
			WarManager.getInstance().sendRmsOne(this, getNativeId());
			//rms
		}
	}
	/**
	 * 得到占领时间积分
	 * @param userId
	 * boolean issend 是否成功
	 */
	public void calculationWarIntegral(int userId,boolean issend){
		if(this.isFalseCamp){
			if(userId != 0){
				UserWarData data = WarManager.getInstance().getMyWarData(userId, getNativeId());
				int times = 0;
				if(getOccTime() == 0){
					times = (int)(TimeUtils.nowLong()/1000 - getGameStartTime()) > 20 *60 ? 20*60 : (int)(TimeUtils.nowLong()/1000 - getGameStartTime());
				}else{
					times = (int)(TimeUtils.nowLong()/1000 - getOccTime()) > 20 *60 ? 20*60 : (int)(TimeUtils.nowLong()/1000 - getOccTime());
				}
				//int times = (int)(TimeUtils.nowLong()/1000 - getOccTime()) > 20 *60 ? 20*60 : (int)(TimeUtils.nowLong()/1000 - getOccTime());
				data.saveWarData(UserWarData.WAR_INTEGRAL,times* WarManager.PER_SECOND,issend);
				setOccTime((int)(TimeUtils.nowLong()/1000));
			}
		}
	}
	/**
	 * 撤退
	 * @param backMsg
	 * @return
	 */
	public TipUtil retreat(String backMsg){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("fail");
		if(getOccCache() == null){
			tip.setSuccTip("Nationid"+getNativeId()+"无人驻防");
			return tip;
		}
		PlayerCharacter def = World.getInstance().getPlayer(getOccCache().getUserid());
		if(def != null){
			if("".equals(backMsg)){
				backMsg = getSoliderInfo();
			}
			if(def.backHeroAndSoldier(getHeroId(), backMsg)){
				//计算玩家积分
				calculationWarIntegral(getOccCache().getUserid(),true);
				//制空据点
				directOccupied(null, null, "",false);
				save();
				tip.setSuccTip("");
			}else{
				tip.setFailTip("回收士兵fail");
			}
		}
		return tip;
	}
	
	/** 
	 * 据点争夺战
	 * @param player 用户
	 * @param playerHeroId 将领id
	 * @param msg 
	 * @param tuiMsg
	 * @param heros
	 * @param fightEvent1
	 * @param fightEvent2
	 */
	public  TipUtil occupied(PlayerCharacter att,int playerHeroId,String attMsg,String defMsg,int attHeros,FightEvent fightEvent1,FightEvent fightEvent2){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("fail");
//		synchronized (lock) {
			if(!occNationCamp(att)){
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					tip.setFailTip("Attack failed!You haven't killed all the monsters!");
				}else{
					tip.setFailTip("未消灭完怪物,无法攻打");
				}
				
				return tip;
			}
			if(!isFalseCamp){
				MilitaryCamp ccc = WarManager.getInstance().getMyCamp(att.getId(),getNativeId());
				if(ccc != null){
					if(ccc.isFalseCamp){
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setFailTip("Occupied Capitol already.");
						}else{
							tip.setFailTip("已占领主城");
						}
						
					}else{
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							tip.setFailTip("Occupied Stronghold already.");
						}else{
							tip.setFailTip("已占领据点");
						}
						
					}
					return tip;
				}
			}
			int id = 0;
			if(getOccCache() != null){
				id = getOccCache().getUserid();
			}
			//防守方
			PlayerCharacter def = World.getInstance().getPlayer(id);
			if(playerHeroId == 0){
				/**1: ********** 占领失败 */
				resetHero(defMsg);//重置驻防士兵
				save();
				if(fightEvent1 != null && fightEvent2 != null && def != null){
					String name = "据点";
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						name= "Stronghold";
					}
					if(this.isFalseCamp){
						name = getNationName();
					}
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						fightEvent1.setMemo("You attacked "+def.getData().getName()+"'s "+name+"but failed.");
						fightEvent2.setMemo("You defended the attack from "+att.getData().getName()+" to "+name+".");
					}else{
						fightEvent1.setMemo("你进攻"+def.getData().getName()+"的"+name+"失败了");
						fightEvent2.setMemo("你抵御了"+att.getData().getName()+"对"+name+"的进攻");
					}
					
				}
				return tip.setSuccTip("");
				/**1: ********** 占领失败 */
			}else{
				//攻击用户的data
				UserWarData myData = WarManager.getInstance().getMyWarData(att.getId(), getNativeId());
				
				if(def == null){
					/**2:**********占领空据点*/
					PlayerHero atth = att.getPlayerHeroManager().getHero(playerHeroId);
					
					byte zhufang = GameConst.HEROSTATUS_ZHUFANG_CAMPE;
//					if(this.isFalseCamp){
//						zhufang = getHeroStatus();
//					}
					if(FightUtil.getSoldierNum(attMsg) > atth.getSoldierNum()){
						logger.info("超出将领带兵上限");
						if(I18nGreeting.LANLANGUAGE_TIPS == 1){
							return tip.setFailTip("Occupy failed!");
						}else{
							return tip.setFailTip("占领失败!");
						}
					}
					String m1 = "";
					String m2 = "";
					if(I18nGreeting.LANLANGUAGE_TIPS == 1){
						m1= "Garrsion in ";
						m2 = " Stronghold";
					}else{
						m1= "驻防";
						m2 = "据点";
					}
					if(att.dispatch(playerHeroId,m1+ getNationName(getNativeId())+m2,attMsg,zhufang,TimeUtils.nowLong())){
						if(this.isFalseCamp){
							//我的据点为空
							MilitaryCamp campp = WarManager.getInstance().getMyCamp(att.getId(), getNativeId());
							if(!campp.isFalseCamp){
								campp.resetThis();//清空数据
							}
						}
						directOccupied(att, atth, attMsg,isArea(att.getData().getNativeId()));
						myData.saveWarData(UserWarData.WAR_INTEGRAL, WarManager.PER_BARRACKS,true);
						save();
						
						//rms
						List<ClientModuleBase> push = new ArrayList<ClientModuleBase>();
						push.add(this);
						//push.add(new SimplePlayerHero(atth, ""));
						push.add(myData);
						WarManager.getInstance().sendRmsList(push, getNativeId());
						PushSign.sendAll(push, new PlayerCharacter[]{att}, ProcotolType.USER_INFO_RESP);
						//rms
						return tip.setSuccTip("");
					}else{
						return tip.setFailTip("占领失败,[dispatch]派兵错误");
					}
					/**2:**********占领空据点*/
				}else{
					/**3:**********战斗占领据点*/
					if(def.getId() == att.getId()){
						return tip.setFailTip("我已占领,不能战斗");
					}
					int defHero = getHeroId();
					byte zhufang = GameConst.HEROSTATUS_ZHUFANG_CAMPE;
//					if(this.isFalseCamp){
//						zhufang = getHeroStatus();
//					}
					//UserWarData defData = WarManager.getInstance().getMyWarData(def.getId(), getNativeId());
					calculationWarIntegral(def.getId(), false);//计算防御者的积分
					if(retreat(defMsg).isResult() && att.dispatch(playerHeroId,getNationName(getNativeId()), attMsg,zhufang,TimeUtils.nowLong())){
						PlayerHero atth = att.getPlayerHeroManager().getHero(playerHeroId);
						if(this.isFalseCamp){
							//我的交换据点
							MilitaryCamp campp = WarManager.getInstance().getMyCamp(att.getId(), getNativeId());
							if(!campp.isFalseCamp){
								if(campp.retreat("").isResult())//我的据点撤退.将领撤退
									def.dispatch(defHero,getNationName(getNativeId()), defMsg, GameConst.HEROSTATUS_ZHUFANG_CAMPE,TimeUtils.nowLong());//驻防
									PlayerHero defHH = def.getPlayerHeroManager().getHero(defHero);
									campp.directOccupied(def, defHH, defMsg,isArea(def.getData().getNativeId()));
									campp.save();
									UserWarData defData = WarManager.getInstance().getMyWarData(def.getId(), getNativeId());
									List<ClientModuleBase> push = new ArrayList<ClientModuleBase>();
									push.add(campp);
									push.add(defData);
									//push.add(defHH);
									WarManager.getInstance().sendRmsList(push, getNativeId());//发送防御方
							}
						}
						//占领据点
						directOccupied(att, atth, attMsg,isArea(att.getData().getNativeId()));
						myData.saveWarData(UserWarData.WAR_INTEGRAL, WarManager.PER_BARRACKS,true);
						save();
						if(fightEvent1 != null && fightEvent2 != null){
							String name = "据点";
							if(I18nGreeting.LANLANGUAGE_TIPS == 1){
								name= "Stronghold";
							}
							if(this.isFalseCamp){
								name = getNationName();
								TipUtil tippp = new TipUtil(ProcotolType.USER_INFO_RESP);
								String msg = I18nGreeting.getInstance().getMessage("war.occ", new Object[]{att.getData().getName()});
								tippp.setSuccTip(msg);
								GameUtils.sendTip(tippp.getTip(), def.getUserInfo(),GameUtils.FLUTTER);
							}
							if(I18nGreeting.LANLANGUAGE_TIPS == 1){
								fightEvent1.setMemo("You successfully defeated "+def.getData().getName()+" and occupied " + name);
								fightEvent2.setMemo("You're defeated by "+att.getData().getName()+" and lose "+name);
							}else{
								fightEvent1.setMemo("你击败"+def.getData().getName()+"占领了"+name);
								fightEvent2.setMemo("你被"+att.getData().getName()+"击败，失去了"+name);
							}
							
						}
						//rms
						List<ClientModuleBase> push = new ArrayList<ClientModuleBase>();
						push.add(this);
						push.add(myData);
						WarManager.getInstance().sendRmsList(push, getNativeId());
						PushSign.sendAll(push, new PlayerCharacter[]{att,def}, ProcotolType.USER_INFO_RESP);
						//rms
						return tip.setSuccTip("");
					}else{
						return tip.setFailTip("占领失败");
					}
					/**3:**********战斗占领空据点*/
				}
			}
//		}
		
	}
	/**
	 * 得到国家名字
	 * @return
	 */
	public String getNationName(){
		if (I18nGreeting.LANLANGUAGE_TIPS ==1 ){
			if(getNativeId()%1000 == 0){
				return "Country";
			}else if(getNativeId()%100 == 0){
				return "State";
			}else if(getNativeId()%10 == 0){
				return "City";
			}
			return "Stronghold";
		}else{
			if(getNativeId()%1000 == 0){
				return "国家";
			}else if(getNativeId()%100 == 0){
				return "州府";
			}else if(getNativeId()%10 == 0){
				return "市府";
			}
			return "据点";
		}
		
	}
	/**
	 * 伪城市时是否可以攻打
	 * @param att
	 * @return
	 */
	public boolean occNationCamp(PlayerCharacter att){
		if(this.isFalseCamp){
			if(WarManager.getInstance().isAttNation(att, getNativeId())){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	/**
	 * 重置数据
	 * @param nations
	 */
	public void resetThis(){
		if(getOccCache() != null){
			retreat("").isResult();
		}
		directOccupied(null, null, "",false);
	}
	
	/**
	 * 重置数据
	 * @param nations
	 */
	public void resetAllThis(){
		if(getOccCache() != null){
			retreat("").isResult();
		}
		setNativeId(0);
		directOccupied(null, null, "",false);
	}
	
	/**
	 * 增兵
	 */
	public TipUtil moreTroops(PlayerCharacter att,String addSolider){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("失败");
		if(getOccCache() != null && att.getId() == getOccCache().getUserid()){
			PlayerHero myHero = att.getPlayerHeroManager().getHero(getHeroId());
			if(myHero != null){
				if(FightUtil.getSoldierNum(addSolider) <=0){
					tip.setFailTip("增兵数量不能为0");
				}else if(FightUtil.getSoldierNum(addSolider) > myHero.getSoldierNum()){
					tip.setFailTip("增兵超过带兵上限");
				}else{
					String newStr = att.getPlayerBuilgingManager().checkSolider(addSolider);
					if ("".equals(newStr)) {
						logger.info("[checkSolider]士兵解析错误\n");
						return tip.setFailTip("失败");
					}
					if(att.backHeroAndSoldier(0, getSoliderInfo())){
						if(att.getPlayerBuilgingManager().dispatchSoldier(newStr)){
							setSoliderInfo(newStr);
							myHero.setSoldier(newStr);
							save();
							//rms
							List<ClientModuleBase> push = new ArrayList<ClientModuleBase>();
							push.add(this);
							push.add(new SimplePlayerHero(myHero, ""));
							WarManager.getInstance().sendRmsList(push, getNativeId());
							tip.setSuccTip("");
						}else{
							tip.setFailTip("派士兵错误");
						}
					}else{
						tip.setFailTip("回收士兵错误");
					}
				}
			}else{
				tip.setFailTip("将领数据错误");
			}
		}else{
			tip.setFailTip("未占领不能增兵");
		}
		return tip;
	}
	
	/**
	 * 取得参加人员的级别限制
	 * @return
	 */
	public int getUserTitle(){
		if(getNativeId()%1000==0){
			return GameConst.TITLE_GOVERNOR;//GOVERNOR
		}else if(getNativeId()%100==0){
			return GameConst.TITLE_MAYOR_CITY;//MAYOR_CITY
		}else if(getNativeId()%10==0){
			return GameConst.TITLE_MAYOR_TOWN;//CIVILIAN
		}
		return GameConst.TITLE_CIVILIAN;
	}
	
	/**
	 * 得到对应区域名字
	 * @param nativeId
	 * @return
	 */
	public static String getNationName(int nativeId){
		Nation country = NationManager.getInstance().getNation(nativeId/1000*1000);
		Nation state = NationManager.getInstance().getNation(nativeId/100*100);
		Nation city = NationManager.getInstance().getNation(nativeId/10*10);
		if(nativeId%1000 == 0){
			if(country != null){
				return country.getName();
			}
		}else if(nativeId%100 == 0){
			if(country != null && state != null){
				return country.getName()+"-"+state.getName();
			}
		}else if(nativeId%10 == 0){
			if(country != null && state != null && city != null ){
				return country.getName()+"-"+state.getName()+"-"+city.getName();
			}
		}else if(nativeId%10 == 0){
			if(country != null && state != null && city != null ){
				return country.getName()+"-"+state.getName()+"-"+city.getName();
			}
		}
		return "";
	}
}
