package com.joymeng.game.domain.role;


import java.util.ArrayList;
import java.util.List;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.core.utils.MathUtils;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameConfig;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.nation.NationManager;
import com.joymeng.game.domain.quest.PlayerQuestAgent;
import com.joymeng.services.core.buffer.JoyBuffer;

/**
 * 人物属性
 * @author admin
 *
 */
public class RoleData extends ClientModuleBase{
	
	// 常用的角色属性，能保留尽量保留
	// --------------------------------
//	private int id;// 角色ID，自增长
	private String name;// 角色名称
	private long userid;
	private byte sex = 0;// 角色性别（0:男 1:女）
	private byte faction = 1;// （图标）
	private short level = 1;// 等级(unsigned byte : 最大255)
	private int exp;// 经验
	private int gameMoney;// 游戏币
	private int joyMoney;// 乐币
	private short packCapacity;// 背包容量
	private String storage="";// 背包
	private byte identity = 0;//身份

	// 时间统计相关
	private int intradayOnlineTime;// 当日总在线时间
	private int onlineTime = 0;// 角色在线时间
	private long lastLoginTime = 0;// 角色上次登录时间
	private long createTime;// 创建时间
	private long leaveTime;// 上次退出时间
	private long activeEndTime;// 活跃时间
	

	// vip
	private String vip="";
	// 好友，仇敌相关
	private String friends="";
	private String enemys="";
	private int warTimes = 0;//战斗次数 
	private int achieve = 0;//政绩
	// --------------------------------
	// 策略类游戏的~
	// 签名
	private String note="";
	// 地理位置
	private int nativeId;//对应县城id
	// 功勋
	private int award;
	// 爵位0:平民/1:骑士/2:准男爵/3:男爵/4:子爵/5: 伯爵/6:侯爵/7:公爵
	private int cityLevel;
	// 荣誉
	private int credit;
	// 统帅
	private int attriCommander;
	// 内政
	private int attriPolitics;
	// 魅力
	private int attriCharm;
	// 潜力
	private int attriCapacity;
	
	private String playerCells;
	
	//战役记录     格式"id;0,1;time  /  id;0,1;time" 表示每个战役 (id;进入的次数,0免费1付费;更新的时间)
	private String camp;
	//战役id
	private int campId;
	//天梯记录   表示(状态;免费可刷新次数;付费可刷新次数;重置时间)
	private String ladder;
	//当前天梯进度id
	private byte ladderId;
	//通天塔最大进度
	private byte ladderMax;
	
	//已经转动次数
	private byte boxNum;
	//转动时间
	private long boxTime;
	//刷新出来的物品id
//	private String boxStr;
	//随机到的物品id，暂不保存到数据库中
//	private String temp_boxStr;
	//积分
	private int score = 0;
	//筹码
	private int chip = 0;
	//是否改过名字
	private byte isChangName=0;
	
	private int archerEqu = 0;//弓兵
	private int infantryEqu = 0;//步兵
	private int cavalryEqu= 0;//骑兵
	private int specialArms = 0;//特种兵
	private int arenaId;//排名
	private int arenaKill;//连胜次数
	private long arenaTime;//cd时间
	private int arenaLastId;//排名
	private long arenaLastTime;//暂时没用
	//已经完成的任务
	private String completedQuests;
	//接收的任务
	private String acceptedQuests;
	//日常任务 
	private String dailyQuests;
	//军功
	private int militaryMedals = 0;
	//
	private int heroRefresh1;//第一次自动刷新将领
	private String fightInfo;//统计各种战斗类型的参与次数
	
	private long lastPostQuestionTime;//上一次提交联系客服问题时间
	private long recordLastLoginTime;//临时变量，记录上次登录时间
	private long lastSignTime;//上次签到时间
	private byte canSpeak;//玩家是否可以发言
	private byte canLogin;//玩家是否可以登录
	private int signNum;//玩家签到次数
	private byte isSigned;//当天是否签过到
	public String getFightInfo() {
		return fightInfo;
	}

	public void setFightInfo(String fightInfo) {
		this.fightInfo = fightInfo;
	}

	public int getHeroRefresh1() {
		return heroRefresh1;
	}

	public void setHeroRefresh1(int heroRefresh1) {
		this.heroRefresh1 = heroRefresh1;
	}

	public int getHeroRefresh2() {
		return heroRefresh2;
	}

	public void setHeroRefresh2(int heroRefresh2) {
		this.heroRefresh2 = heroRefresh2;
	}

	private int heroRefresh2;//使用刷将符的刷新次数
	//用户称谓
	private byte title;//0--平民，1--县长，2--市长，3--州长，4--国家领导 5--县级官员,6--市级官员,7--省级官员,8--国级官员
	/**
	 * 被选择到左边栏中显示的奖品id，以,间隔各个id
	 */
	private String selectedAwardIdString = "";
	
	public int getMilitaryMedals() {
		return militaryMedals;
	}

	public void setMilitaryMedals(int militaryMedals) {
		this.militaryMedals = militaryMedals;
	}

	/**
	 * 被抽中的奖品id，以,间隔各个id
	 */
	private String beAwardedIdString = "";
	
	private byte newBag;//玩家是否已使用新手大礼包，1-已使用，0-未使用
	
	/**
	 * 玩家抽奖总机会
	 */
	private int totalChance;

	/**
	 * 玩家剩余抽奖机会
	 */
	private int leftChance;
	
	/**
	 * 已领取奖品
	 */
	private String recivedAwardIdString = "";
	
	/**
	 * 玩家再随机产生名称时记录当前生成名称
	 * 以便玩家对生成名称不满意时需要更换时，释放掉此名称
	 * 使此名称变的可用
	 */
	private String lastTimeTempUseFullName = "";
	
	private int tempRefreshCostChip;
	public int getAchieve() {
		return achieve;
	}

	public void setAchieve(int achieve) {
		this.achieve = achieve;
	}
	private int tempTurnCostChip;
	/**
	 * 附属城数量
	 */
	private byte cityNum;
	/**
	 * 玩家当前在排行榜中的页数
	 */
	private int tempCurrentPage;
	
	/**
	 * 道具宝箱中列表中装备id
	 */
	private String tempEquipIds = "";
	
	/**
	 * 道具宝箱中列表中物品id
	 */
	private String tempItemIds = "";
	
	/**
	 * 打开包裹时总的装备id
	 */
	private String tempTotalEquipIds = "";
	
	/**
	 * 打开包裹时总的物品id
	 */
	private String tempTotalItemIds = "";
	
	/**
	 * 物品或装备id或金币，功勋数量
	 */
	private int tempGoodOrEquipIdOrNeedValue;
	
	/**
	 * 道具宝箱中玩家获得的是什么道具
	 */
	private int tempWhatForPropsBox;
	
	/**
	 * 物品或装备的数量
	 */
	private int tempGoodsOrEquipNumForPropsBox;
	
	/**
	 * 记录玩家第几次打开包裹
	 */
	private int tempCountOpenPackageTimes;
	
	/**
	 * 玩家根据武将卡获取到的武将id
	 */
	private int tempGetPlayerHeroId;
	
	/**
	 * 玩家获取的hero临时存储
	 */
	private PlayerHero tempPlayerHero;
	
	private int tempHeroId;
	
	/**
	 * 玩家请求的武将卡id
	 */
	private int tempHeroCardId;
	
	/**
	 * 当前页面显示的所属城市的id，供玩家翻页时使用
	 */
	private int tempCityId;

	private long heroTime;
	
	/**
	 * 已生成的玩家角色们
	 */
	private List<String> generateNames = new ArrayList<String>();
	
	
	PlayerCharacter pc;
	
	public String getSelectedAwardIdString() {
		return selectedAwardIdString;
	}

	public void setSelectedAwardIdString(String selectedAwardIdString) {
		this.selectedAwardIdString = selectedAwardIdString;
	}

	public String getBeAwardedIdString() {
		return beAwardedIdString;
	}

	public void setBeAwardedIdString(String beAwardedIdString) {
		this.beAwardedIdString = beAwardedIdString;
	}
	
	
	
	
	
//	public String getBoxStr() {
//		return boxStr;
//	}
//
//	public void setBoxStr(String boxStr) {
//		this.boxStr = boxStr;
//	}

//	public String getTemp_boxStr() {
//		return temp_boxStr;
//	}
//
//	public void setTemp_boxStr(String temp_boxStr) {
//		this.temp_boxStr = temp_boxStr;
//	}

	public int getTempHeroId() {
		return tempHeroId;
	}

	public void setTempHeroId(int tempHeroId) {
		this.tempHeroId = tempHeroId;
	}

	public byte getIsChangName() {
		return isChangName;
	}
	
	public byte getCityNum() {
		return cityNum;
	}

	public void setCityNum(byte cityNum) {
		this.cityNum = cityNum;
	}

	public void setIsChangName(byte isChangName) {
		this.isChangName = isChangName;
	}

	/**
	 * 获取 playerCells
	 * @return the playerCells
	 */
	public String getPlayerCells() {
		return playerCells;
	}

	/**
	 * 设置 playerCells
	 * @param playerCells the playerCells to set
	 */
	public void setPlayerCells(String playerCells) {
		this.playerCells = playerCells;
	}
	

	public int getArcherEqu() {
		return archerEqu;
	}

	public void setArcherEqu(int archerEqu) {
		this.archerEqu = archerEqu;
	}

	public int getInfantryEqu() {
		return infantryEqu;
	}

	public void setInfantryEqu(int infantryEqu) {
		this.infantryEqu = infantryEqu;
	}

	public int getCavalryEqu() {
		return cavalryEqu;
	}

	public void setCavalryEqu(int cavalryEqu) {
		this.cavalryEqu = cavalryEqu;
	}

	public int getSpecialArms() {
		return specialArms;
	}

	public void setSpecialArms(int specialArms) {
		this.specialArms = specialArms;
	}

	// 建筑面积
	// 特种兵建筑
	// 行政建筑
	// 占领自己主城的玩家
	// 自己占领的玩家主城，1~10个
//	public int getId() {
//		return id;
//	}
	
	/**
	 * @return GET the nativeId
	 */
	public int getNativeId() {
		return nativeId;
	}

	/**
	 * @param SET nativeId the nativeId to set
	 */
	public void setNativeId(int nativeId) {
		this.nativeId = nativeId;
	}

//	public void setId(int id) {
//		this.id = id;
//	}
	
	
	/**
	 * @return GET the warTimes
	 */
	public int getWarTimes() {
		return warTimes;
	}

	/**
	 * @param SET warTimes the warTimes to set
	 */
	public void setWarTimes(int warTimes) {
		this.warTimes = warTimes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public byte getFaction() {
		return faction;
	}

	public void setFaction(byte faction) {
		this.faction = faction;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public short getPackCapacity() {
		return packCapacity;
	}

	public void setPackCapacity(short packCapacity) {
		this.packCapacity = packCapacity;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getOnlineTime() {
		return onlineTime;
	}
	
	public byte getIdentity() {
		return identity;
	}

	public void setIdentity(byte identity) {
		this.identity = identity;
	}

	public void setOnlineTime(int onlineTime) {
		this.onlineTime = onlineTime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(long leaveTime) {
		this.leaveTime = leaveTime;
	}

	public long getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(long activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public int getIntradayOnlineTime() {
		return intradayOnlineTime;
	}

	public void setIntradayOnlineTime(int intradayOnlineTime) {
		this.intradayOnlineTime = intradayOnlineTime;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getFriends() {
		return friends;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	public String getEnemys() {
		return enemys;
	}

	public void setEnemys(String enemys) {
		this.enemys = enemys;
	}

	public int getGameMoney() {
		return gameMoney;
	}

	public void setGameMoney(int gameMoney) {
		this.gameMoney = gameMoney;
	}

	public int getJoyMoney() {
		return joyMoney;
	}

	public void setJoyMoney(int joyMoney) {
		this.joyMoney = joyMoney;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public int getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(int cityLevel) {
		this.cityLevel = cityLevel;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getAttriCommander() {
		return attriCommander;
	}

	public void setAttriCommander(int attriCommander) {
		this.attriCommander = attriCommander;
	}

	public int getAttriPolitics() {
		return attriPolitics;
	}

	public void setAttriPolitics(int attriPolitics) {
		this.attriPolitics = attriPolitics;
	}

	public int getAttriCharm() {
		return attriCharm;
	}

	public void setAttriCharm(int attriCharm) {
		this.attriCharm = attriCharm;
	}

	public int getAttriCapacity() {
		return attriCapacity;
	}

	public void setAttriCapacity(int attriCapacity) {
		this.attriCapacity = attriCapacity;
	}
	
	

	public byte getLadderMax() {
		return ladderMax;
	}

	public void setLadderMax(byte ladderMax) {
		this.ladderMax = ladderMax;
	}

	public int getTempCityId() {
		return tempCityId;
	}

	public void setTempCityId(int tempCityId) {
		this.tempCityId = tempCityId;
	}

	@Override
	public byte getModuleType() {
		return NTC_ROLEDATA;
	}
	
//	public void simple(JoyBuffer out) {
//		out.putInt((int) userid);
//		out.putShort(level);
//		out.putPrefixedString(name,(byte)2);
//		out.putPrefixedString("aff_city_1.png",(byte)2);
//	}
	@Override
	public void _serialize(JoyBuffer out) {
		out.putInt((int) userid);
		out.putPrefixedString(name,(byte)2);
//		out.putLong(id);
		out.put(sex);
		out.put(faction);
		out.putShort(level);
		out.putInt(exp);
		out.putInt(gameMoney);
		out.putInt(joyMoney);
		out.putShort(packCapacity);
		out.putPrefixedString(storage,(byte)2);
		out.putLong(lastLoginTime);
		out.putInt(onlineTime);
		out.putLong(createTime);
		out.putLong(leaveTime);
		out.putLong(activeEndTime);
		out.putInt(intradayOnlineTime);
		out.putPrefixedString(vip,(byte)2);
		out.putPrefixedString(friends,(byte)2);
		out.putPrefixedString(enemys,(byte)2);
		out.putPrefixedString(note,(byte)2);
		out.putInt(award);
		out.put((byte)cityLevel);
		out.putInt(credit);
		out.putInt(attriCommander);
		out.putInt(attriPolitics);
		out.putInt(attriCharm);
		out.putInt(attriCapacity);
		//酒馆刷新时间heroTime
		out.putLong(heroTime);
		out.putInt(nativeId);
		out.put(title);
		if(NationManager.getInstance().getDetailed(nativeId) == null){
			out.putPrefixedString("",JoyBuffer.STRING_TYPE_SHORT);
			out.putPrefixedString("",JoyBuffer.STRING_TYPE_SHORT);
			out.putPrefixedString("",JoyBuffer.STRING_TYPE_SHORT);
			out.putPrefixedString("",JoyBuffer.STRING_TYPE_SHORT);
		}else{
			out.putPrefixedString(NationManager.getInstance().getDetailed(nativeId)[0].getName(),JoyBuffer.STRING_TYPE_SHORT);
			out.putPrefixedString(NationManager.getInstance().getDetailed(nativeId)[1].getName(),JoyBuffer.STRING_TYPE_SHORT);
			out.putPrefixedString(NationManager.getInstance().getDetailed(nativeId)[2].getName(),JoyBuffer.STRING_TYPE_SHORT);
			out.putPrefixedString(NationManager.getInstance().getDetailed(nativeId)[3].getName(),JoyBuffer.STRING_TYPE_SHORT);
		}
		out.putPrefixedString(camp,JoyBuffer.STRING_TYPE_SHORT);
		out.putInt(campId);
		out.putPrefixedString(this.ladder,JoyBuffer.STRING_TYPE_SHORT);
		out.put(this.ladderId);
		out.put(this.isChangName);
		out.putInt(this.score);
		out.putInt(this.chip);
		//out.put(this.boxNum);
		//out.putPrefixedString(this.selectedAwardIdString,JoyBuffer.STRING_TYPE_SHORT);
		//out.putPrefixedString(this.beAwardedIdString,JoyBuffer.STRING_TYPE_SHORT);
		out.putInt(this.archerEqu);//弓兵
		out.putInt(this.infantryEqu);//步兵
		out.putInt(this.cavalryEqu);//骑兵
		out.putInt(this.specialArms);//特种兵
		out.put(cityNum);
		out.putInt(arenaId);
		out.putInt(arenaKill);
		out.putLong(arenaTime/1000);
		String[] str=this.getCompletedQuests().split(";");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<str.length;i++){
			sb.append("<").append(str[i]).append(">");
		}
		out.putPrefixedString(sb.toString(),JoyBuffer.STRING_TYPE_SHORT);
	}

	@Override
	public void deserialize(JoyBuffer in) {
//		this.id=in.getInt();
		this.name=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.userid=in.getLong();
		this.sex=in.get();
		this.faction=in.get();
		this.level=in.getShort();
		this.exp=in.getInt();
		this.gameMoney=in.getInt();
		this.joyMoney=in.getInt();
		this.packCapacity=in.getShort();
		this.storage=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.lastLoginTime=in.getLong();
		this.onlineTime=in.getInt();
		this.createTime=in.getLong();
		this.leaveTime=in.getLong();
		this.activeEndTime=in.getLong();
		this.intradayOnlineTime=in.getInt();
		this.vip=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.friends=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.enemys=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.note=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.award=in.getInt();
		this.cityLevel=in.getInt();
		this.credit=in.getInt();
		this.attriCommander=in.getInt();
		this.attriPolitics=in.getInt();
		this.attriCharm=in.getInt();
		this.attriCapacity=in.getInt();
		this.heroTime=in.getLong();
//		this.playerCells=in.getPrefixedString();
		String str1=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		String str2=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		String str3=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		String str4=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.camp=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.campId=in.getInt();
		this.ladder=in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		this.ladderId=in.get();
		this.isChangName=in.get();
		this.score=in.getInt();
		this.chip=in.getInt();
		//this.boxNum = in.get();
		//this.selectedAwardIdString = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
		//this.beAwardedIdString = in.getPrefixedString(JoyBuffer.STRING_TYPE_SHORT);
	}
//	public void print(){
////		System.out.println("id=="+getId());
//		System.out.println("name=="+getName());
//		System.out.println("userid=="+getUserid());
//		System.out.println("sex=="+getSex());
//		System.out.println("faction=="+getFaction());
//		System.out.println("level=="+getLevel());
//		System.out.println("exp=="+getExp());
//		System.out.println("gameMoney=="+getGameMoney());
//		System.out.println("joyMoney=="+getJoyMoney());
//		System.out.println("packCapacity=="+getPackCapacity());
//		System.out.println("storage=="+getStorage());
//		System.out.println("lastLoginTime=="+getLastLoginTime());
//		System.out.println("onlineTime=="+getOnlineTime());
//		System.out.println("createTime=="+getCreateTime());
//		System.out.println("leaveTime=="+getLeaveTime());
//		System.out.println("activeEndTime=="+getActiveEndTime());
//		System.out.println("intradayOnlineTime=="+getIntradayOnlineTime());
//		System.out.println("vip=="+getVip());
//		System.out.println("friends=="+getFriends());
//		System.out.println("enemys=="+getEnemys());
//		System.out.println("note=="+getNote());
//		System.out.println("award=="+getAward());
//		System.out.println("cityLevel=="+getCityLevel());
//		System.out.println("credit=="+getCredit());
//		System.out.println("attriCommander=="+getAttriCommander());
//		System.out.println("attriPolitics=="+getAttriPolitics());
//		System.out.println("attriCharm=="+getAttriCharm());
//		System.out.println("attriCapacity=="+getAttriCapacity());
//		System.out.println("playerCells=="+getPlayerCells());
//		System.out.println("heroTime=="+this.getHeroTime());
//	}
	/**
	 * 创建角色
	 * @return
	 */
	public static RoleData create(){
		RoleData rd=new RoleData();
		rd.setCamp("");
		rd.setCampId(0);
		rd.setLadder("");
		rd.setLadderId((byte)0);
		rd.setLevel((short)1);
		//new add by madi
		rd.setScore(0);
		rd.setChip(0);
		rd.setName("测试"+MathUtils.random(999));
		rd.setPlayerCells("");
		rd.setCityLevel(0);
		rd.setJoyMoney(GameConfig.bornJoyMoney);
		rd.setGameMoney(GameConfig.bornGameMoney);
		rd.setAward(GameConfig.bornAward);
		rd.setNativeId(1111);
//		rd.setLadder(Ladder.getInitStr());
		rd.setCreateTime(TimeUtils.nowLong());
		rd.setActiveEndTime(TimeUtils.nowLong());
		rd.setLastLoginTime(TimeUtils.nowLong());// add by madi
		rd.setLeaveTime(TimeUtils.nowLong());
		rd.setMilitaryMedals(200);
		rd.setAcceptedQuests("1,"+PlayerQuestAgent.ACCEPTED);
		rd.setCompletedQuests("");
		rd.setDailyQuests("");
		rd.setFightInfo("");
		//初始化的数据
		return rd;
	}
	
	public PlayerCharacter getPc() {
		return pc;
	}

	public void setPc(PlayerCharacter pc) {
		this.pc = pc;
	}

	public String getCamp() {
		return camp;
	}

	public void setCamp(String camp) {
		this.camp = camp;
	}

	public int getCampId() {
		return campId;
	}

	public void setCampId(int campId) {
		this.campId = campId;
	}

	public String getLadder() {
		return ladder;
	}

	public void setLadder(String ladder) {
//		System.out.println("ladder="+ladder);
		this.ladder = ladder;
	}

	public byte getLadderId() {
		return ladderId;
	}

	public void setLadderId(byte ladderId) {
		this.ladderId = ladderId;
	}

//	public long getHeroTime() {
//		return heroTime;
//	}
//
//	public void setHeroTime(long heroTime) {
//		this.heroTime = heroTime;
//	}

	public byte getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(byte boxNum) {
		this.boxNum = boxNum;
	}

	public long getBoxTime() {
		return boxTime;
	}

	public void setBoxTime(long boxTime) {
		this.boxTime = boxTime;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getChip() {
		return chip;
	}

	public void setChip(int chip) {
		this.chip = chip;
	}
	
	public int getTotalChance() {
		return totalChance;
	}

	public void setTotalChance(int totalChance) {
		this.totalChance = totalChance;
	}

	public int getLeftChance() {
		return leftChance;
	}

	public void setLeftChance(int leftChance) {
		this.leftChance = leftChance;
	}

	public String getRecivedAwardIdString() {
		return recivedAwardIdString;
	}

	public void setRecivedAwardIdString(String recivedAwardIdString) {
		this.recivedAwardIdString = recivedAwardIdString;
	}

	public String getLastTimeTempUseFullName() {
		return lastTimeTempUseFullName;
	}

	public void setLastTimeTempUseFullName(String lastTimeTempUseFullName) {
		this.lastTimeTempUseFullName = lastTimeTempUseFullName;
	}

	public int getTempRefreshCostChip() {
		return tempRefreshCostChip;
	}

	public void setTempRefreshCostChip(int tempRefreshCostChip) {
		this.tempRefreshCostChip = tempRefreshCostChip;
	}

	public int getTempTurnCostChip() {
		return tempTurnCostChip;
	}

	public void setTempTurnCostChip(int tempTurnCostChip) {
		this.tempTurnCostChip = tempTurnCostChip;
	}

	public int getArenaId() {
		return arenaId;
	}

	public void setArenaId(int arenaId) {
		this.arenaId = arenaId;
	}

	public int getArenaKill() {
		return arenaKill;
	}

	public void setArenaKill(int arenaKill) {
		this.arenaKill = arenaKill;
	}

	public long getArenaTime() {
		return arenaTime;
	}

	public void setArenaTime(long arenaTime) {
		this.arenaTime = arenaTime;
	}

	public int getTempCurrentPage() {
		return tempCurrentPage;
	}

	public void setTempCurrentPage(int tempCurrentPage) {
		this.tempCurrentPage = tempCurrentPage;
	}

	public String getTempEquipIds() {
		return tempEquipIds;
	}

	public void setTempEquipIds(String tempEquipIds) {
		this.tempEquipIds = tempEquipIds;
	}

	public String getTempItemIds() {
		return tempItemIds;
	}

	public void setTempItemIds(String tempItemIds) {
		this.tempItemIds = tempItemIds;
	}

	public int getTempGoodOrEquipIdOrNeedValue() {
		return tempGoodOrEquipIdOrNeedValue;
	}

	public void setTempGoodOrEquipIdOrNeedValue(int tempGoodOrEquipIdOrNeedValue) {
		this.tempGoodOrEquipIdOrNeedValue = tempGoodOrEquipIdOrNeedValue;
	}

	public int getTempWhatForPropsBox() {
		return tempWhatForPropsBox;
	}

	public void setTempWhatForPropsBox(int tempWhatForPropsBox) {
		this.tempWhatForPropsBox = tempWhatForPropsBox;
	}

	public int getTempGoodsOrEquipNumForPropsBox() {
		return tempGoodsOrEquipNumForPropsBox;
	}

	public void setTempGoodsOrEquipNumForPropsBox(int tempGoodsOrEquipNumForPropsBox) {
		this.tempGoodsOrEquipNumForPropsBox = tempGoodsOrEquipNumForPropsBox;
	}

	public int getArenaLastId() {
		return arenaLastId;
	}

	public void setArenaLastId(int arenaLastId) {
		this.arenaLastId = arenaLastId;
	}

	public long getArenaLastTime() {
		return arenaLastTime;
	}

	public void setArenaLastTime(long arenaLastTime) {
		this.arenaLastTime = arenaLastTime;
	}

	public int getTempCountOpenPackageTimes() {
		return tempCountOpenPackageTimes;
	}

	public void setTempCountOpenPackageTimes(int tempCountOpenPackageTimes) {
		this.tempCountOpenPackageTimes = tempCountOpenPackageTimes;
	}

	public String getTempTotalEquipIds() {
		return tempTotalEquipIds;
	}

	public void setTempTotalEquipIds(String tempTotalEquipIds) {
		this.tempTotalEquipIds = tempTotalEquipIds;
	}

	public String getTempTotalItemIds() {
		return tempTotalItemIds;
	}

	public void setTempTotalItemIds(String tempTotalItemIds) {
		this.tempTotalItemIds = tempTotalItemIds;
	}

	public String getCompletedQuests() {
		return completedQuests;
	}

	public void setCompletedQuests(String completedQuests) {
		this.completedQuests = completedQuests;
	}

	public String getAcceptedQuests() {
		return acceptedQuests;
	}

	public void setAcceptedQuests(String acceptedQuests) {
		this.acceptedQuests = acceptedQuests;
	}

	public int getTempGetPlayerHeroId() {
		return tempGetPlayerHeroId;
	}

	public void setTempGetPlayerHeroId(int tempGetPlayerHeroId) {
		this.tempGetPlayerHeroId = tempGetPlayerHeroId;
	}

	public PlayerHero getTempPlayerHero() {
		return tempPlayerHero;
	}

	public void setTempPlayerHero(PlayerHero tempPlayerHero) {
		this.tempPlayerHero = tempPlayerHero;
	}

	public int getTempHeroCardId() {
		return tempHeroCardId;
	}

	public void setTempHeroCardId(int tempHeroCardId) {
		this.tempHeroCardId = tempHeroCardId;
	}

	public String getDailyQuests() {
		return dailyQuests;
	}

	public void setDailyQuests(String dailyQuests) {
		this.dailyQuests = dailyQuests;
	}

	public byte getTitle() {
		return title;
	}

	public void setTitle(byte title) {
		this.title = title;
	}

	public List<String> getGenerateNames() {
		return generateNames;
	}

	public void setGenerateNames(List<String> generateNames) {
		this.generateNames = generateNames;
	}

	public byte getNewBag() {
		return newBag;
	}

	public void setNewBag(byte newBag) {
		this.newBag = newBag;
	}

	public long getLastPostQuestionTime() {
		return lastPostQuestionTime;
	}

	public void setLastPostQuestionTime(long lastPostQuestionTime) {
		this.lastPostQuestionTime = lastPostQuestionTime;
	}

	public byte getCanSpeak() {
		return canSpeak;
	}

	public void setCanSpeak(byte canSpeak) {
		this.canSpeak = canSpeak;
	}

	public byte getCanLogin() {
		return canLogin;
	}

	public void setCanLogin(byte canLogin) {
		this.canLogin = canLogin;
	}

	public long getRecordLastLoginTime() {
		return recordLastLoginTime;
	}

	public void setRecordLastLoginTime(long recordLastLoginTime) {
		this.recordLastLoginTime = recordLastLoginTime;
	}

	public int getSignNum() {
		return signNum;
	}

	public void setSignNum(int signNum) {
		this.signNum = signNum;
	}

	public byte getIsSigned() {
		return isSigned;
	}

	public void setIsSigned(byte isSigned) {
		this.isSigned = isSigned;
	}

	public long getLastSignTime() {
		return lastSignTime;
	}

	public void setLastSignTime(long lastSignTime) {
		this.lastSignTime = lastSignTime;
	}
	
	

}
