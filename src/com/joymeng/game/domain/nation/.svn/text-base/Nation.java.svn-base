package com.joymeng.game.domain.nation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.response.AndroidMessageSender;
import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.base.net.response.RespModuleSet;
import com.joymeng.core.log.GameLog;
import com.joymeng.core.log.LogBuffer;
import com.joymeng.core.log.LogEvent;
import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.ProcotolType;
import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.cache.domain.PlayerCache;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.fight.FightEventManager;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;
import com.joymeng.game.domain.world.TipMessage;
import com.joymeng.game.domain.world.TipUtil;
import com.joymeng.game.domain.world.World;
import com.joymeng.services.core.buffer.JoyBuffer;

public class Nation extends ClientModuleBase {// 国家，州，市，县
	static boolean NATION_FIGHT = true;//手动开启县长争夺战
	static Logger logger = LoggerFactory.getLogger(Nation.class);
	private int id;
	private byte type;//0:国家 1： 州 2：市 3：县
	private String name;// 名字
	private int occupyUser;// 占领者id
	private int eventId;// 事件id 0:无事件 1:市长争夺战 2:州长争夺战 3:国王争夺战
	private String userInfo;// 占领者信息
	private String national;// 本县拥有用户id集合
	private int resourceId; // 资源点
	private int goldId;// 金矿点
	private int userNum;// 人数
	private int remark1;//占领者军功
	private int remark2;//州功勋
	private String remark3;//政绩加成
	private String remark4;// 加成比例

	private String occupyUsername;// 占领者名字，不对应数据库中字段
	private int heroId;// 英雄id
	private String soldierInfo;// 士兵信息
	private String heroInfo;// 将领信息
	
	/**
	 * 开启县长
	 */
	public static void startFight(){
		NATION_FIGHT = false;
	}
	
	/**
	 * 关闭县长
	 */
	public static void closeFight(){
		NATION_FIGHT = true;
	}
	
	

	public static boolean isFight() {
		return NATION_FIGHT;
	}

	public String getHeroInfo() {
		return heroInfo;
	}

	public void setHeroInfo(String heroInfo) {
		this.heroInfo = heroInfo;
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
	 * @return GET the userNum
	 */
	public int getUserNum() {
		return userNum;
	}

	/**
	 * @param SET
	 *            userNum the userNum to set
	 */
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	/**
	 * @return GET the resourceId
	 */
	public int getResourceId() {
		return resourceId;
	}

	/**
	 * @param SET
	 *            resourceId the resourceId to set
	 */
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return GET the goldId
	 */
	public int getGoldId() {
		return goldId;
	}

	/**
	 * @param SET
	 *            goldId the goldId to set
	 */
	public void setGoldId(int goldId) {
		this.goldId = goldId;
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
	 * @return GET the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param SET
	 *            name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return GET the occupyUser
	 */
	public int getOccupyUser() {
		return occupyUser;
	}

	/**
	 * @param SET
	 *            occupyUser the occupyUser to set
	 */
	public void setOccupyUser(int occupyUser) {
		this.occupyUser = occupyUser;
	}

	/**
	 * @return GET the eventId
	 */
	public int getEventId() {
		return eventId;
	}

	/**
	 * @param SET
	 *            eventId the eventId to set
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return GET the userInfo
	 */
	public String getUserInfo() {
		return userInfo;
	}

	/**
	 * @param SET
	 *            userInfo the userInfo to set
	 */
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return GET the national
	 */
	public String getNational() {
		return national;
	}

	/**
	 * @param SET
	 *            national the national to set
	 */
	public void setNational(String national) {
		this.national = national;
	}

	/**
	 * @return GET the remark1
	 */
	public int getRemark1() {
		return remark1;
	}

	/**
	 * @param SET
	 *            remark1 the remark1 to set
	 */
	public void setRemark1(int remark1) {
		this.remark1 = remark1;
	}

	/**
	 * @return GET the remark2
	 */
	public int getRemark2() {
		return remark2;
	}

	/**
	 * @param SET
	 *            remark2 the remark2 to set
	 */
	public void setRemark2(int remark2) {
		this.remark2 = remark2;
	}

	/**
	 * @return GET the remark3
	 */
	public String getRemark3() {
		return remark3;
	}

	/**
	 * @param SET
	 *            remark3 the remark3 to set
	 */
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	/**
	 * @return GET the remark4
	 */
	public String getRemark4() {
		return remark4;
	}

	/**
	 * @param SET
	 *            remark4 the remark4 to set
	 */
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}

	public String getOccupyUsername() {
		return occupyUsername;
	}

	public void setOccupyUsername(String occupyUsername) {
		this.occupyUsername = occupyUsername;
	}

	public int getHeroId() {
		return heroId;
	}

	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}

	public String getSoldierInfo() {
		return soldierInfo;
	}

	public void setSoldierInfo(String soldierInfo) {
		this.soldierInfo = soldierInfo;
	}

	@Override
	public byte getModuleType() {
		return NTC_NATION;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt(id);
		out.put(type);
		out.putPrefixedString(name, (byte) 2);
		out.putInt(eventId);
		if (getOccupyUser() == 0) {
			out.putPrefixedString("", (byte) 2);
		} else {
			PlayerCache ss = MongoServer.getInstance().getLogServer().getPlayerCacheDAO()
					.findPlayerCacheByUserid(getOccupyUser());
			if (ss != null) {
				out.putPrefixedString(ss.getName(), (byte) 2);
			} else {
				out.putPrefixedString("", (byte) 2);
			}
		}
		out.putInt(heroId);
		out.putInt(occupyUser);
	}

	@Override
	public void deserialize(JoyBuffer in) {
		byte modelType = in.get();
		this.id = in.getInt();
		this.type = in.get();
		this.name = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.occupyUser = in.getInt();
		this.eventId = in.getInt();
		this.userInfo = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.national = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.resourceId = in.getInt();
		this.goldId = in.getInt();
		this.remark1 = in.getInt();
		this.remark2 = in.getInt();
		this.remark3 = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.remark4 = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.heroId = in.getInt();
		this.soldierInfo = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);

	}


	public boolean checkEnterBattle(PlayerCharacter player) {
		// 规定时间段内可战
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String nowTime = sdf.format(nowDate);
	
		/* if(!((nowTime.compareTo("22:00:00") > 0 &&
			nowTime.compareTo("24:00:00") < 0) || nowTime.compareTo("00:00:00") >
		 	0 && nowTime.compareTo("11:00:00") < 0)){ GameUtils.sendTip(new
		 			TipMessage("县长争夺战现在处于关闭状态" , ProcotolType.REGION_BATTLE_RESP,
		 			GameConst.GAME_RESP_FAIL), player.getUserInfo());
		 	logger.info("县长争夺战现在处于关闭状态"); 
		 	return false; 
		 }*/
		
		if(!FightEventManager.isCountyWarGoing() && NATION_FIGHT){//如果县长争夺战处于关闭状态
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new
			 			TipMessage("Townleader Battle is locked now!" , ProcotolType.REGION_BATTLE_RESP,
			 			GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new
			 			TipMessage("县长争夺战现在处于关闭状态" , ProcotolType.REGION_BATTLE_RESP,
			 			GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}
			
//			logger.info("县长争夺战现在处于关闭状态"); 
		 	return false; 
		}

		// 如果不是县长和平民不可发动争夺站
		if (player.getData().getTitle() > GameConst.TITLE_MAYOR_TOWN) {
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new
			 			TipMessage("You are not allowed to attend Throne now!" , ProcotolType.REGION_BATTLE_RESP,
			 			GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("当前身份不允许进入争夺战",
						ProcotolType.REGION_BATTLE_RESP, GameConst.GAME_RESP_FAIL),
						player.getUserInfo(),GameUtils.FLUTTER);
			}
			
//			logger.info("当前身份不允许进入争夺战");
			return false;
		}
		
		//如果当前玩家等级不是5-30级，则不可发动县长争夺战
		if(player.getData().getLevel() < GameConst.BEGIN_FIGHT || player.getData().getLevel() > 30){
			if(I18nGreeting.LANLANGUAGE_TIPS == 1){
				GameUtils.sendTip(new
			 			TipMessage("Failde!Your Lord should be at least Lv.5." , ProcotolType.REGION_BATTLE_RESP,
			 			GameConst.GAME_RESP_FAIL), player.getUserInfo(),GameUtils.FLUTTER);
			}else{
				GameUtils.sendTip(new TipMessage("当前等级不能参与县长争夺战，最低需要LV5",
						ProcotolType.REGION_BATTLE_RESP, GameConst.GAME_RESP_FAIL),
						player.getUserInfo(),GameUtils.FLUTTER);
			}
			
			return false;
		}
		// 如果当前玩家是本县县长则不可发动争夺战
		/*
		 * if(player.getData().getNativeId() == id &&
		 * player.getData().getTitle() == 1){ return false; }
		 */
		return true;
	}

	/**
	 * 占领某个区域
	 * 
	 * @param player
	 *            玩家
	 * @param heroId
	 *            int 英雄id
	 * @param soldierInfo
	 *            String 士兵信息
	 */
	public void occupyNation(PlayerCharacter player, int hId, String sInfo) {
		this.setHeroId(hId);
		PlayerHero ph = player.getPlayerHeroManager().getHero(hId);
		if (ph != null) {
			this.setHeroInfo(GameUtils.heroInfo(ph));
		}
		this.setSoldierInfo(sInfo);
		this.setOccupyUser((int) player.getData().getUserid());
		this.setOccupyUsername(player.getData().getName());
		this.setRemark1(player.getData().getAchieve());
//		logger.info(player.getData().getName() + "占领了：" + this.getId() + "/"
//				+ this.getName());
		DBManager.getInstance().getWorldDAO().getNationDAO().saveNation(this);

		// 保存用户称谓为县长
		RoleData roleData = player.getData();
		roleData.setTitle(GameConst.TITLE_MAYOR_TOWN);
		logger.info("NATION occupyNation:"+roleData.getUserid()+"|变成："+roleData.getTitle());
		player.checkTraining();
		// roleData.setNativeId(this.getId());
//		DBManager.getInstance().getWorldDAO().saveRole(roleData);
		save();
	}

	/**
	 * 玩家丢弃已经占领过的区域
	 * 
	 * @param player
	 *            玩家
	 */
	public void discardNation(PlayerCharacter player) {
		// 回收已占领县城的士兵
		player.getPlayerBuilgingManager().recoverSoldier(this.getSoldierInfo());

		// 改变以前县城的将领状态为空闲
		// 如果将领已被撤防
		if (heroId != 0) {
			// player.getPlayerHeroManager().getHero(heroId).setStatus((byte)0);
			player.getPlayerHeroManager().motifyStatus(heroId,
					GameConst.HEROSTATUS_IDEL, "", "", 0);
		}

		this.setHeroId(0);
		this.setSoldierInfo(null);
		this.setOccupyUser(0);
		this.setOccupyUsername("");
		
		DBManager.getInstance().getWorldDAO().getNationDAO().saveNation(this);

		// 丢弃县长称谓
		// RoleData roleData = player.getData();
		// roleData.setTitle((byte)0);
		// DBManager.getInstance().getWorldDAO().saveRole(roleData);
	}

	/**
	 * 换防 p1换防p2，p2回城
	 * p1 撤退/或者迁移到 p2城
	 * p2撤退
	 * @param old 原玩家
	 * @param newPlayer 新玩家
	 * @return
	 * p1 -> p2/ p2->0
	 */
	public TipUtil takeover(PlayerCharacter old, PlayerCharacter newPlayer) {
		StringBuffer sb= new StringBuffer();
		sb.append("takeover 换防 \n");
		TipUtil tip = new TipUtil(ProcotolType.HERO_RESP);
		tip.setFailTip("fail");
		if (old != null && newPlayer != null) {
			Nation oldNation = null;//p1
			Nation newNation = null;//p2
			if(this.getOccupyUser() == old.getId()){
				oldNation = this;
			}
			switch (newPlayer.getData().getTitle()) {
			case 2:
				newNation = NationManager.getInstance().getNation(newPlayer.getData().getNativeId());
				break;
			case 4:
				newNation = NationManager.getInstance().getNation(newPlayer.getData().getNativeId()/10*10);
				break;
			case 6:
				newNation = NationManager.getInstance().getNation(newPlayer.getData().getNativeId()/100*100);
				break;
			case 8:
				newNation = NationManager.getInstance().getNation(newPlayer.getData().getNativeId()/1000*1000);
				break;
			}
			if(newNation != null && !(newNation.getOccupyUser() == newPlayer.getId())){
				newNation = null;
			}
			if(oldNation != null && newNation == null){
				//p1撤退回去并清空
				logger.info("======新区域为空");
				oldNation.retreatNation(newPlayer.getData().getNativeId(), true);
			}else if(newNation != null && oldNation != null){
				int p1hero = oldNation.getHeroId();
				String p1Somsg = oldNation.getSoldierInfo();
				newNation.retreatNation(newPlayer.getData().getNativeId(), true);// p2撤退
				//p1 数据保存到p2地址
				logger.info("=========原区域："+newNation.getId()+"|用户："+old.getId()+"|将领："+p1hero+"|士兵："+p1Somsg);
				newNation.occNations(old, p1hero, p1Somsg, newPlayer.getData().getNativeId());
				tip.setSuccTip("");
			}else{
				tip.setFailTip("玩家城池数据错误");
			}
		}else{
			sb.append("Nation id:"+getId()+"玩家不存在 ,换防 \n");
			tip.setFailTip("玩家不存在");
		}
		logger.info(sb.toString());
		return tip;
	}

	/**
	 * 本区域玩家撤退 
	 * @param newNationId  新县城.没有填0
	 * @param isClean 清除玩家职位 isClean =true
	 * @return
	 */
	public TipUtil retreatNation(int newNationId, boolean isClean) {
		TipUtil tip = new TipUtil(ProcotolType.HERO_RESP);
		StringBuffer sb= new StringBuffer();
		tip.setFailTip("fail");
		sb.append("retreatNation方式撤退 \n");
		if (getOccupyUser() == 0) {
			sb.append("Nation id:"+getId()+"|没有玩家占领 \n" );
			tip.setFailTip("撤退玩家id为0");
		} else {
			PlayerCharacter occ = World.getInstance()
					.getPlayer(getOccupyUser());//占领玩家
			if (occ != null) {
				PlayerHero hero = occ.getPlayerHeroManager().getHero(
						getHeroId());//占领英雄
				if (hero == null) {
					// 原用户迁移到新地方/不迁移nationid == 0
					NationManager.getInstance().MigrationCounty(occ, newNationId);
					// 清空nation
					clean(occ,isClean);// 清空nation数据， 清空职位
					sb.append("Nation id:"+getId()+"|将领不存在，清空职位 \n" );
					tip.setFailTip("将领不存在");
				} else {
					// 撤退
					if (occ.backHeroAndSoldier(hero.getId(), getSoldierInfo())) {//士兵，将领撤退
						// 原用户迁移到新地方/不迁移nationid == 0
						NationManager.getInstance().MigrationCounty(occ,newNationId);
						// 清空nation
						clean(occ,isClean);// 清空nation数据， 清空职位
//						occ.saveData();
						// rms
						RespModuleSet rms = new RespModuleSet(
								ProcotolType.HERO_RESP);
						rms.addModule(hero);
						rms.addModule(occ.getData());
						AndroidMessageSender.sendMessage(rms, occ);
						// rms
						save();
						tip.setSuccTip("");
					} else {
						sb.append("Nation id:"+getId()+"|玩家撤退失败 \n" );
						tip.setFailTip("玩家撤退失败");
					}
				}

			} else {
				sb.append("Nation id:"+getId()+"|撤退玩家不存在  \n" );
				tip.setFailTip("撤退玩家不存在");
			}
		}
		logger.info(sb.toString());
		return tip;
	}


	/**
	 * 撤出我占领的区域
	 * @param townId  新的县id
	 */
	public void disarmMyNation(int townId,int playerId) {
		// 撤防撤防
		for (Nation nation : NationManager.getInstance().nationMap.values()) {
			if (nation.getOccupyUser() == playerId) {
				//去除用户所占领的区域
				nation.retreatNation(townId, true);
			}
		}
	}
	/**
	 * 战争设置
	 * 
	 * @param playerId 用户
	 * @param heroId 英雄
	 * @param soMsg 士兵
	 * @param townId 新的县id
	 */
	public void occNations(PlayerCharacter occ, int heroId, String soMsg,
			int townId) {
		// rms
		RespModuleSet rms = new RespModuleSet(ProcotolType.HERO_RESP);
			PlayerHero ph = occ.getPlayerHeroManager().getHero(heroId);
			if (ph != null) {
				disarmMyNation(townId,occ.getId());//清除其他驻防的数据
				occ.getPlayerHeroManager().changeStatu(heroId, getHeroStatus(),getNationName(getId()));// 改变驻防状态
				this.setHeroId(heroId);
				this.setSoldierInfo(soMsg);
				this.setOccupyUser((int) occ.getData().getUserid());
				this.setRemark1(occ.getData().getAchieve());
				this.setHeroInfo(GameUtils.heroInfo(ph));
				rms.addModule(ph);
				occ.setNativeId(townId);
				// 设置长官
				occ.getData().setTitle(getTitle());
				//写入日志  玩家 喊话
				GameLog.logPlayerEvent(occ, LogEvent.TITLE, new LogBuffer().add(this.id).add(heroId).add(soMsg));
				occ.checkTraining();// 是否开启训练台
				rms.addModule(occ.getData());
				this.save();
//				occ.saveData();
				logger.info("NATION occNations:"+occ.getId()+"|变成："+occ.getData().getTitle());
				AndroidMessageSender.sendMessage(rms, occ);
			}else{
				logger.info("nation id："+this.getId()+"|用户："+occ.getId()+"|将领："+heroId +" is null");
			}
			save();
//		}
	}
	
	/**
	 * 换防
	 * @param heroId
	 * @param soMsg
	 */
	public TipUtil keepUnder(PlayerCharacter att,int heroId,String soMsg){
		TipUtil tip = new TipUtil(ProcotolType.HERO_RESP);
		tip.setFailTip("");
		StringBuffer sb= new StringBuffer("---------nation keepUnder---------");
		PlayerHero ph = att.getPlayerHeroManager().getHero(heroId);
		String memo = "";
		if(I18nGreeting.LANLANGUAGE_TIPS ==1){
			memo = "Garrsion in ";
		}else{
			memo ="驻守";
		}
		if(ph!= null && ph.getStatus() == GameConst.HEROSTATUS_IDEL){//将领状态可以换防
			if(att.dispatch(heroId, memo+getNationName(getId()), soMsg, getHeroStatus(), TimeUtils.nowLong())){
				if (att.backHeroAndSoldier(getHeroId(), getSoldierInfo())) {//士兵，将领撤退
					List<ClientModuleBase> lst = new ArrayList<ClientModuleBase>();
					setHeroId(heroId);
					setSoldierInfo(soMsg);
					setHeroInfo(GameUtils.heroInfo(ph));
					this.save();
					lst.add(this);
					tip.setLst(lst);
					sb.append("********************换将成功\n");
					tip.setSuccTip("");
				}else{
					sb.append("将领："+getHeroId()+"|士兵："+getSoldierInfo()+"|返回失败\n");
					sb.append("换将失败返回："+att.backHeroAndSoldier(heroId, soMsg)+"\n");
					//失败派出的将返回
					att.backHeroAndSoldier(heroId, soMsg);
				}
			}else{
				sb.append("将领："+heroId+"|士兵："+soMsg+"|派遣失败\n");
			}
		}else{
			sb.append("将领："+heroId+"|不能驻防\n");
		}
		sb.append("---------end---------");
		tip.setStr(sb.toString());
		return tip;
	}
	/**
	 * 得到职位
	 * 
	 * @return
	 */
	public byte getTitle() {
		int ids = getId();
		if (ids % 1000 == 0) {
			return GameConst.TITLE_KING;
		} else if (ids % 100 == 0) {
			return GameConst.TITLE_GOVERNOR;
		} else if (ids % 10 == 0) {
			return GameConst.TITLE_MAYOR_CITY;
		} else if (ids % 10 != 0) {
			return GameConst.TITLE_MAYOR_TOWN;
		} else {
			return GameConst.TITLE_CIVILIAN;//市民
		}
	}

	/**
	 * 得到职位
	 * 
	 * @return
	 */
	public static String getNationName(int ids) {
		if(I18nGreeting.LANLANGUAGE_TIPS == 1){
			if (ids % 1000 == 0) {
				return "country";
			} else if (ids % 100 == 0) {
				return "state";
			} else if (ids % 10 == 0) {
				return "city";
			} else if (ids % 10 != 0) {
				return "town";
			} else {
				return "the common people";
			}
		}else{
			if (ids % 1000 == 0) {
				return "国";
			} else if (ids % 100 == 0) {
				return "州";
			} else if (ids % 10 == 0) {
				return "市";
			} else if (ids % 10 != 0) {
				return "县";
			} else {
				return "平民";
			}
		}
		
	}
	
	/**
	 * 撤退
	 * @param town
	 */
	public boolean retreatAndClear(int playerId){
		if(this.getOccupyUser() == playerId){
			PlayerCharacter def = World.getInstance().getPlayer(this.getOccupyUser());
			if(def != null){
				if(def.backHeroAndSoldier(this.getHeroId(), this.getSoldierInfo())){
					//clean();
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 清空
	 * 
	 * @param occ
	 *            用户
	 * @param isClean
	 *            是否清除职位
	 */
	public void clean(PlayerCharacter occ, boolean isClean) {
		if (occ != null && this.getOccupyUser() == occ.getId()) {
			this.setHeroId(0);
			this.setSoldierInfo("");
			this.setHeroInfo("");
			if (isClean) {
				this.setRemark1(0);
				this.setOccupyUser(0);
				// 设置平民
				occ.getData().setTitle(GameConst.TITLE_CIVILIAN);
				logger.info("NATION clean:"+occ.getId()+"|变成："+GameConst.TITLE_CIVILIAN);
			}
			occ.checkTraining();// 是否开启训练台
			this.save();
//			occ.saveData();
			logger.info("用户：" + occ.getId() + "|清空数据");
		}
	}

	/**
	 * 得到将领状态
	 * 
	 * @return
	 */
	public byte getHeroStatus() {
		if (getId() % 1000 == 0) {
			return GameConst.HEROSTATUS_ZHUFANG_NATION_COUNTRY;
		} else if (getId() % 100 == 0) {
			return GameConst.HEROSTATUS_ZHUFANG_NATION_STATE;
		} else if (getId() % 10 == 0) {
			return GameConst.HEROSTATUS_ZHUFANG_NATION_CITY;
		}else if (getId() % 10 != 0) {
			return GameConst.HEROSTATUS_ZHUFANG_COUNTY;
		}
		return GameConst.HEROSTATUS_ZHUFANG_CAMPE;
	}

	public void save() {
		DBManager.getInstance().getWorldDAO().getNationDAO().saveNation(this);
	}

}
