package com.joymeng.game.domain.nation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.building.PlayerBarrackManager;
import com.joymeng.game.domain.fight.FightEvent;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.hero.SimplePlayerHero;
import com.joymeng.game.domain.item.Cell;
import com.joymeng.game.domain.quest.QuestUtils;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

public class GoldMine extends ClientModuleBase{//金矿
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static NationManager naMgr = NationManager.getInstance();
	static Logger logger = LoggerFactory.getLogger(GoldMine.class);
	int id;
	int stateId;//州id
	int userId;//占领者
	String userName = "";//占领者名字
	byte type;// 0 金矿 1 ：资源点
	byte level;//金矿级别
	int chargeOut;//产出
	int addition;//加成
	long intervalTime;//占领时间
	int heroId;//将领id
	String soMsg;//士兵驻扎信息
	long restTime ;//休整时间
	int charge;//收益
	String heroInfo;
	/**
	 * @return GET the id
	 */
	public int getId() {
		return id;
	}
	
	

	public String getHeroInfo() {
		return heroInfo;
	}



	public void setHeroInfo(String heroInfo) {
		this.heroInfo = heroInfo;
	}



	/**
	 * @param SET id the id to set
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
	 * @return GET the type
	 */
	public byte getType() {
		return type;
	}
	/**
	 * @param SET type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}
	/**
	 * @param SET stateId the stateId to set
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return GET the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param SET userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return GET the level
	 */
	public byte getLevel() {
		return level;
	}
	/**
	 * @param SET level the level to set
	 */
	public void setLevel(byte level) {
		this.level = level;
	}
	/**
	 * @return GET the chargeOut
	 */
	public int getChargeOut() {
		return chargeOut;
	}
	/**
	 * @param SET chargeOut the chargeOut to set
	 */
	public void setChargeOut(int chargeOut) {
		this.chargeOut = chargeOut;
	}
	/**
	 * @return GET the addition
	 */
	public int getAddition() {
		return addition;
	}
	/**
	 * @param SET addition the addition to set
	 */
	public void setAddition(int addition) {
		this.addition = addition;
	}
	/**
	 * @return GET the intervalTime
	 */
	public long getIntervalTime() {
		return intervalTime;
	}
	/**
	 * @param SET intervalTime the intervalTime to set
	 */
	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}
	/**
	 * @return GET the heroId
	 */
	public int getHeroId() {
		return heroId;
	}
	/**
	 * @param SET heroId the heroId to set
	 */
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	/**
	 * @return GET the soMsg
	 */
	public String getSoMsg() {
		return soMsg;
	}
	/**
	 * @param SET soMsg the soMsg to set
	 */
	public void setSoMsg(String soMsg) {
		this.soMsg = soMsg;
	}
	/**
	 * @return GET the restTime
	 */
	public long getRestTime() {
		return restTime;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getCharge() {
		return charge;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	/**
	 * @param SET restTime the restTime to set
	 */
	public void setRestTime(long restTime) {
		this.restTime = restTime;
	}
	@Override
	public byte getModuleType() {
		// TODO Auto-generated method stub
		return NTC_NATION_GOLD;
	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.putInt(stateId);
		out.putInt(userId);
		if(userId == 0){
			out.putPrefixedString("",(byte)2);
		}else{
			PlayerCharacter pc = World.getInstance().getPlayer(userId);
			if(pc != null){
				out.putPrefixedString(pc.getData().getName(),(byte)2);
			}else{
				out.putPrefixedString("",(byte)2);
			}
		}
		out.put(type);
		out.put(level);
		out.putInt(chargeOut);
		if(getUserId() == 0){
			out.putInt(naMgr.basicAdd(getStateId(),getType()) +1);
		}else{
			out.putInt(naMgr.myadd(getStateId(),getUserId(), getType()));
		}
		
		out.putInt((int)(intervalTime/1000));
		out.putInt(heroId);
		out.putPrefixedString(soMsg,(byte)2);
		out.putInt((int)(restTime/1000));
		//out.putLong(restTime);
//		out.putInt(charge);
	}
	@Override
	public void deserialize(JoyBuffer in) {
		byte modelType=in.get();
		this.id=in.getInt();
		this.stateId=in.getInt();
		this.userId=in.getInt();
		this.type=in.get();
		this.level=in.get();
		this.chargeOut=in.getInt();
		this.addition=in.getInt();
		this.intervalTime=in.getLong();
		this.heroId=in.getInt();
		this.soMsg=in.getPrefixedString();
		this.restTime=in.getLong();
		
	}
	
	/**
	 * 保存数据
	 * @param sMsg
	 */
	public void saveSoldierMsg(HashMap<Integer, String> sMsg){
		String s = PlayerBarrackManager.generateSoMsg(sMsg);
		if(s != null && !"".equals(s)){
			setSoMsg(s);
		}
	}
	
	public boolean check(PlayerCharacter pp,int playerHeroId,String msg,int heros){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		String memo ="金矿";
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			memo = "Goldmine";
		}
		if(getType() == 1){
			memo ="资源点";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				memo = "Resmine";
			}
		}
		if(naMgr.getMyOccupyGold(this.getType(),pp) != null){
			tip.setFailTip("已有"+memo+"不能占领");
			GameUtils.sendTip(tip.getTip(), pp.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
		tip = checks();
		if(getStateId() != pp.getStateId()){
			tip.setFailTip(I18nGreeting.getInstance().getMessage(
					"goldMine.no", null));
			GameUtils.sendTip(tip.getTip(), pp.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
		if(!tip.isResult()){
			GameUtils.sendTip(tip.getTip(), pp.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
		if(pp.getData().getUserid() == getUserId()){
			tip.setFailTip("自己攻打自己的"+memo);
			GameUtils.sendTip(tip.getTip(), pp.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
		
		
		int num = GameConfig.goldMine;
		if(pp.getResourcesData(GameConfig.MEDALS) < num)
		{
			tip.setFailTip("军功不足");
			GameUtils.sendTip(tip.getTip(), pp.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
//		if(this.getUserId() != 0){
//			tip = checks();
//			if(!tip.isResult()){
//				GameUtils.sendTip(tip.getTip(), pp.getUserInfo());
//				return false;
//			}
//		}
		if(pp.getData().getNativeId()/100*100 == getStateId() && getUserId() != 0 ){
			return true;
		}else{
			tip = occupyGold(pp,playerHeroId,msg,null,heros);
			GameUtils.sendTip(tip.getTip(), pp.getUserInfo(),GameUtils.FLUTTER);
			return false;
		}
	}
	
	/**
	 * 占领    GoldMine
	 * @return
	 */
	public boolean presenceGoldMine(PlayerCharacter player,PlayerHero ph,String msg){
		this.setUserId((int)player.getData().getUserid());
		this.setHeroId(ph.getId());
		this.setHeroInfo(GameUtils.heroInfo(ph));
		this.setSoMsg(msg);
		this.setUserName(player.getData().getName());
		this.setRestTime(TimeUtils.nowLong() + 2 * 60 *60 *1000);
		this.setIntervalTime(TimeUtils.nowLong());
		naMgr.saveGoldMine(this);//保存
		List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
		//下发顺序下发将领，再发金矿数据
		lst.add(new SimplePlayerHero(ph, ""));
		//lst.add(player.getData());
		lst.add(this);
		lst.add(new RefreshResources());
		NationManager.getInstance().sendRmsList(lst, getStateId());
		return true;
	}
	
	/**
	 * 清空    GoldMine
	 * @return
	 */
	public boolean emptyGoldMine(){
		this.setUserId(0);
		this.setHeroId(0);
		this.setHeroInfo("");
		this.setSoMsg("");
		this.setUserName("");
		this.setRestTime(0);
		this.setIntervalTime(0);
		naMgr.saveGoldMine(this);//保存
		NationManager.getInstance().sendRmsOne(this, getStateId());
		NationManager.getInstance().sendRmsOne(new RefreshResources(), getStateId());
		return true;
	}
	
	/**
	 *  占领失败 >>> 士兵回金矿
	 */
	public boolean retreatToGold(PlayerCharacter player,String soMsg){
		logger.info("占领失败--->占领："+this.getId()+ "|  士兵："+soMsg);
		if(this != null  && this.getUserId()  == player.getData().getUserid()){
//			boolean flag = false;
//			if(!"".equals(soMsg)){
//				flag =  this.recoverSoldier(soMsg);//士兵回金矿
//			}
//			if(flag){
			this.setSoMsg(soMsg);
//			this.setAddition(addition);
			naMgr.saveGoldMine(this);
			logger.info("占领失败--->占领："+this.getId()+ "| 士兵："+soMsg +" >>> 成功");
			NationManager.getInstance().sendRmsOne(this, getStateId());//发送
			return true;
//			}
//			logger.info("占领失败--->占领："+this.getId()+ "| 士兵："+soMsg +" >>> 失败");
//			return false;
		}
		logger.info("占领失败--->占领："+this.getId()+ "| 士兵："+soMsg +" >>> 失败    资源点不存在");
		return false;
	}
	
	/**
	 * 占领影响资源 类型
	 * @param type 矿类型
	 * @param id 矿id
	 * @param playerHeroId 占领将领id
	 * @param msg 占领士兵信息
	 * @param tuiMsg 战败士兵
	 * @return
	 */
	public TipUtil occupyGold(PlayerCharacter player,int playerHeroId,String msg,String tuiMsg,int heros){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("用户占领，失败");
//		if(this != null){
			if(player.getData().getNativeId()/100*100 != this.getStateId()){
				logger.info("占领州："+player.getData().getNativeId()/100*100+ "| 金矿州id:"+this.getStateId());
				tip.setFailTip("用户不能占领其他州资源");
				return tip;
			}
			logger.info("占领："+id+ "| 类型："+this.getType() + "| 英雄id:"+playerHeroId+"| 士兵："+msg);
			if(naMgr.getMyOccupyGold(this.getType(),player) != null){
				tip.setFailTip("用户已有"+this.getType()+"类型资源");
				logger.info("用户已有"+this.getType()+"类型资源");
				return tip;
			}
			if(TimeUtils.nowLong() < this.getIntervalTime() - 10*1000){//2个小时不能占领
				logger.info("占领："+id+ "| 类型："+this.getType() + "| 英雄id:"+playerHeroId+"| 士兵："+msg +" >>> 时间未到不能占领");
				tip.setFailTip(" 时间未到不能占领!");
				return tip;
			}
			//扣除军功
			player.recordRoleData(GameConfig.MEDALS, -1*GameConfig.goldMine);
			logger.info("扣除军功:"+GameConfig.goldMine);
			/*** 1: **************  占领失败流程 ***************/
			//占领失败
			if(playerHeroId == 0){
				logger.info("*****************  占领失败流程 ***************");
				PlayerCharacter p = World.getInstance().getPlayer(this.getUserId());
				if( p != null){
					//守护用户士兵返回
					if(retreatToGold(p, tuiMsg)){
						List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
						lst.add(new SimpleResource(player, this, naMgr.myVeins(getStateId(), this.getUserId(), this.getType()), naMgr.basicAdd(getStateId(),this.getType()),this.getChargeOut(),this.getLevel(),this.getType()));
						sendRMS(lst,player);
						tip.setFailTip("占领失败");
					}else{
						logger.info("金矿对象为空，失败");
						tip.setFailTip("金矿对象为空，失败");
					}
				}
				tip.setFightresult(true);
				tip.setFailTip("占领失败");
				return tip;
			}
			/*** 1: **************  占领失败流程 ***************/
			
			/*** 2: **************  占领成功流程流程  资源 没人占领  ***************/
			if(this.getUserId() == 0){//没人占领
				logger.info("*****************占领成功流程流程  资源 没人占领***************");
				//士兵派兵
				PlayerHero ph = player.getPlayerHeroManager().getHero(playerHeroId);
				if(ph == null || !dispatch(player,playerHeroId, msg)){
					tip.setFailTip("用户派兵，失败");
					return tip;
				}
				
				if(presenceGoldMine(player, ph, msg)){
					List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
					lst.add(player.getPlayerHeroManager().getHero(playerHeroId));
					lst.add(new SimplePlayerHero(player.getPlayerHeroManager().getHero(playerHeroId),""));
					SimpleResource sr = new SimpleResource(player, this, naMgr.myVeins(getStateId(), this.getUserId(), this.getType()), naMgr.basicAdd(getStateId(),this.getType()),this.getChargeOut(),this.getLevel(),this.getType());
					lst.add(sr);
					sendRMS(lst,player);//发送rms消息
					//消耗50军功，占领成功
					String message = I18nGreeting.getInstance().getMessage("gold.occupy.success", new Object[]{50});
					tip.setSuccTip(message);
					//tip.setSuccTip("占领成功");
				}else{
					logger.info("占领："+id+ "| 类型："+this.getType() + "| 英雄id:"+playerHeroId+"| 士兵："+msg +" >>> 失败");
					tip.setFailTip("用户占领，失败");
					return tip;
				}
				/*** 2: **************  占领成功流程流程   资源 没人占领  ***************/
			}else{//有人占领
				/*** 3: **************  占领成功流程流程  资源 被人占领  ***************/
				PlayerCharacter pppppp = World.getInstance().getPlayer(this.getUserId());
				if(pppppp != null ){
					logger.info("*****************占领成功流程流程  资源 被人占领***************");
					//********* 用户  p  操作
					PlayerHero otherHero = pppppp.getPlayerHeroManager().getHero(this.getHeroId());
					int in = (int)(naMgr.income(pppppp,getStateId(),otherHero.getLevel(), this));
					if(!retreat(tuiMsg,(byte)1,(byte)1).isResult()){
						return tip;
					}
					System.out.println("***********产出数量"+in);
					//********* 用户  p  操作
					PlayerHero ph = player.getPlayerHeroManager().getHero(playerHeroId);
					if(ph == null || !dispatch(player,playerHeroId, msg))
					{
						logger.info("用户："+player.getData().getName()+"派兵，失败");
						tip.setFailTip("用户占领，失败");
						return tip;
					}
					if(presenceGoldMine(player, ph, msg)){
						logger.info("用户"+pppppp.getData().getUserid()+"|原金币:"+pppppp.getData().getGameMoney()+"|增加金币:"+in/2);
						saveResources(pppppp,this.getType(), in/2);
						logger.info("用户"+pppppp.getData().getUserid()+"|增加金币:"+in/2 +"|现在金币:"+pppppp.getData().getGameMoney());
						
						logger.info("用户"+player.getData().getUserid()+"|原金币:"+player.getData().getGameMoney()+"|增加金币:"+in/2);
						saveResources(player,this.getType(), in/2);
						logger.info("用户"+player.getData().getUserid()+"|增加金币:"+in/2 +"|现在金币:"+player.getData().getGameMoney());
//						player.saveData();
//						pppppp.saveData();
						naMgr.saveGoldMine(this); 
						//*************************  rms 用户  p  操作
						RespModuleSet rms1=new RespModuleSet(ProcotolType.USER_INFO_RESP);//模块消息
						rms1.addModule(pppppp.getData());
						AndroidMessageSender.sendMessage(rms1, pppppp);
						//*************************  rms 用户  p  操作
						//*************************  rms
						RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);//模块消息
						rms.addModule(player.getData());
						rms.addModule(new SimpleResource(player, this, naMgr.myVeins(getStateId(), this.getUserId(), this.getType()), naMgr.basicAdd(getStateId(),this.getType()),this.getChargeOut(),this.getLevel(),this.getType()));
						AndroidMessageSender.sendMessage(rms, player);
						//*************************  rms
						//tip.setSuccTip("占领成功");
						String message = I18nGreeting.getInstance().getMessage("gold.occupy.success", new Object[]{50});
						tip.setSuccTip(message);
					}else{
						logger.info("占领："+id+ "| 类型："+this.getType() + "| 英雄id:"+playerHeroId+"| 士兵："+msg +" >>> 失败");
						tip.setFailTip("用户占领，失败");
						return tip;
					}
					
				}
				/*** 3: **************  占领成功流程流程  资源 被人占领  ***************/
			}
			//处理任务
			if(this.type==0){
				QuestUtils.checkFinish(player, QuestUtils.TYPE36, true);
			}else{
				QuestUtils.checkFinish(player, QuestUtils.TYPE39, true);
			}
		return tip;
	}
	/**
	 * 战斗拥有战报
	 * @param player 用户
	 * @param playerHeroId 英雄id
	 * @param msg
	 * @param tuiMsg
	 * @param heros
	 * @param fightEvent1
	 * @param fightEvent2
	 */
	public void occBattle(PlayerCharacter player,int playerHeroId,String msg,String tuiMsg,int heros,FightEvent fightEvent1,FightEvent fightEvent2){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		PlayerCharacter occ = World.getInstance().getPlayer(this.getUserId());
		PlayerHero myHero = player.getPlayerHeroManager().getHero(heros);
//		PlayerHero occHero = occ.getPlayerHeroManager().getHero(this.getHeroId());
		tip = occupyGold(player, playerHeroId, msg, tuiMsg, heros);
		if(fightEvent1 != null && fightEvent2 != null ){
			String name = "";
			if(this.getType() == 0){
				name = "金矿";
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					name = "Goldmine";
				}
			}else{
				name = "资源点";
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					name = "Resmine";
				}
			}
			if(tip.isResult() && !tip.isFightresult() && occ !=null && myHero != null){
				
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					fightEvent1.setMemo("You dispatched hero "+myHero.getName()+" and occupied "+occ.getData().getName()+"'s "+name);
					fightEvent2.setMemo("Your "+this.getLevel()+" in "+name+" area was occupied by "+player.getData().getName());
				}else{
					fightEvent1.setMemo("你派遣"+myHero.getName()+"占领了"+occ.getData().getName()+"的"+name);
					fightEvent2.setMemo("你在第"+this.getLevel()+"区的"+name+"被+"+player.getData().getName()+"占领了");
				}
				tip.setSuccTip("");
			}else if(!tip.isResult() && tip.isFightresult()){
				
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					fightEvent1.setMemo("You dispatched hero to garrison "+name+" but failed.");
					fightEvent2.setMemo("Your hero garrisoning "+name+" defended against "+player.getData().getName()+"'s attack.");
				}else{
					fightEvent1.setMemo("你派遣武将驻防本州"+name+"失败了");
					fightEvent2.setMemo("你驻防本州"+name+"的武将抵御了"+player.getData().getName()+"的进攻");
				}
				
				tip.setFailTip("fail");
			}
		}
	}
	
	public TipUtil checks(){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		if(TimeUtils.nowLong()  < this.getRestTime() - 10*1000){
			logger.info("retreat："+this+ "| 类型："+type + "| 士兵："+soMsg +" >>> 时间未到不能撤");
			String msg = I18nGreeting.getInstance().getMessage("goldMine.protection",
					null);
			tip.setFailTip(msg);
			return tip;
		}
		return tip.setSuccTip("");
	}
	/**
	 * 撤退 , 士兵回兵营---撤退      0 : 金矿   1 : 资源点  
	 * @param soMsg 士兵数据
	 * @param occ 0 :主动撤防  1:被其他玩家占领
	 * @param type 类型
	 * @return
	 */
	public TipUtil retreat(String soMsg,byte occ,byte type){
		TipUtil tip = new TipUtil(ProcotolType.USER_INFO_RESP);
		tip.setFailTip("用户撤退，失败");
		try {
			PlayerCharacter player = World.getInstance().getPlayer(getUserId());
			if(player == null){
				logger.info("【player】为空");
				tip.setFailTip("用户撤退，失败");
				return tip;
			}
			if(type == 0){
				if(TimeUtils.nowLong()- this.getIntervalTime() < 2*60*60*1000){
					logger.info("retreat："+this+ "| 类型："+type + "| 士兵："+soMsg +" >>> 时间未到不能撤");
					occ = 1;//两小时撤退没有收益
					tip.setFailTip("时间内没有收益!");
//				return tip;
				}
			}
			synchronized (this) {
				PlayerHero hero = player.getPlayerHeroManager().getHero(getHeroId());
				if(hero ==null || !player.getPlayerBuilgingManager().backHeroAndSoldier(getHeroId(), soMsg)){
					logger.info("retreat："+getId()+ "| 类型："+type + "| 士兵："+soMsg +" 【backHeroAndSoldier】回收士兵错误");
					tip.setFailTip("用户撤退，失败");
					return tip;
				}
				//收益
				if(occ == 0){
					int mu = (int)(naMgr.income(player,getStateId(),hero.getLevel(), this));
					logger.info("*******收益数量"+mu);
					saveResources(player,getType(), mu);
					logger.info("retreat："+getId()+ "| 类型："+type + "| 士兵："+soMsg +" 主动撤防,收获资源:"+mu);
				}
				if(emptyGoldMine()){//清空金矿数据
					List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
					lst.add(hero);
					lst.add(this);
					lst.add(new SimpleResource(player, null, naMgr.myVeins(getStateId(), this.getUserId(), this.getType()), naMgr.basicAdd(getStateId(),this.getType()),this.getChargeOut(),this.getLevel(),this.getType()));
					lst.add(new RefreshResources());
					sendRMS(lst,player);//发送rms消息
					String msg = I18nGreeting.getInstance().getMessage("quit.success", null);
					tip.setSuccTip(msg);
					naMgr.saveGoldMine(this);
					return tip;
				}else{
					logger.info("retreat："+getId()+ "| 类型："+type + "| 士兵："+soMsg +" >>> 士兵");
					return tip;
				}
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			return tip;
		}
	}
	/**
	 * 发送rms消息
	 * @param lst
	 */
	public void sendRMS(List<ClientModuleBase> lst,PlayerCharacter user){
		//*************************  rms
		RespModuleSet rms=new RespModuleSet(ProcotolType.USER_INFO_RESP);//模块消息
		rms.addModuleBase(lst);
		AndroidMessageSender.sendMessage(rms, user);
		//*************************  rms
	}
	/**
	 * 回收或者添加士兵
	 * @param soMsg 士兵数据
	 * @return
	 */
	public boolean recoverSoldier(String soMsg) {
		logger.info("类型 ： "+type + "  回收士兵："+soMsg);
		String ss = PlayerBarrackManager.addSomsg(getSoMsg(), soMsg);
		setSoMsg(ss);
		return true;
	}
	
	/**
	 * 产出
	 * @param player 用户
	 * @param type 类型
	 * @param count 数量
	 */
	public void saveResources(PlayerCharacter player,byte type,int count){
		if(count > 0){
			if(type == 0){
				logger.info("收益前数量>>>:"+player.getData().getGameMoney());
				player.saveResources(GameConfig.GAME_MONEY, count);
				logger.info("收益后数量>>>:"+player.getData().getGameMoney());
				List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
				lst.add(player.getData());
				logger.info("收益数量:"+count);
				//NationManager.getInstance().sendRmsList(lst, getStateId());
				sendRMS(lst,player);//发送rms消息
			}else if(type == 1){
				Cell cell = player.getPlayerStorageAgent().addPropsCell(GameConfig.GAME_TIMBER_ID, count);
				Cell cell1 = player.getPlayerStorageAgent().addPropsCell(GameConfig.GAME_IRONORE_ID, count);
				Cell cell2 = player.getPlayerStorageAgent().addPropsCell(GameConfig.GAME_HORSES_ID, count);
				logger.info("收益数量:"+count);
				List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
				lst.add(cell);
				lst.add(cell1);
				lst.add(cell2);
				sendRMS(lst,player);//发送rms消息
				//NationManager.getInstance().sendRmsList(lst, getStateId());
			}
		}
	}
	/**
	 * 兵营  >>> 派兵
	 * @param player 用户
	 * @param heroId 英雄id
	 * @param soMsg 派兵数据
	 * @return
	 */
	public boolean dispatch(PlayerCharacter player,int heroId, String soMsg){
		logger.info("派兵状态 ---驻防将领id：" + heroId + " 派兵 --- 驻防士兵：" + soMsg);
		
		boolean flag1 = false;
		if (!"".equals(soMsg)){
			flag1 = player.getPlayerBuilgingManager().dispatchSoldier(soMsg);// 派兵
		}else{
			logger.info("派兵为空！");
			flag1 =  true;
		}
		if (flag1) {
			String memo ="金矿";
			String m1 = "驻守";
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				memo = "Goldmine";
				m1 = "Garrsion in ";
			}
			if(getType() == 1){
				memo ="资源点";
				if(I18nGreeting.LANLANGUAGE_TIPS == 1){
					memo = "Resmine";
					m1 = "Garrsion in ";
				}
			}
			boolean flag = player.getPlayerHeroManager().motifyStatus(heroId,
					GameConst.HEROSTATUS_ZHUFANG_GOLDMINE,m1+memo,soMsg,TimeUtils.nowLong()+2*60*60*1000);// 驻防
			if(flag)
				return true;
			else{
				logger.info("将领驻防或者");
				return false;
			}
		}else{
			logger.info("派兵失败！");
			return false;
		}
		
	}
}
