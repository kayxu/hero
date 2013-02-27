package com.joymeng.game.db;


import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.joymeng.core.utils.AotuName;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.db.dao.ArenaAwardDAO;
import com.joymeng.game.db.dao.ArenaDAO;
import com.joymeng.game.db.dao.BuildingDAO;
import com.joymeng.game.db.dao.FightEventDAO;
import com.joymeng.game.db.dao.NationDAO;
import com.joymeng.game.db.dao.PlayerCardsDAO;
import com.joymeng.game.db.dao.PlayerHeroDAO;
import com.joymeng.game.db.dao.PlayerRechargeDAO;
import com.joymeng.game.db.dao.PropsDAO;
import com.joymeng.game.db.dao.RelationDAO;
import com.joymeng.game.db.dao.UserQuestionDAO;
import com.joymeng.game.db.dao.UsernameStatusDAO;
import com.joymeng.game.db.row.AutoNameMapper;
import com.joymeng.game.db.row.FightEventRowMapper;
import com.joymeng.game.db.row.PlayerHeroRowMapper;
import com.joymeng.game.db.row.RoleDataRowMapper;
import com.joymeng.game.domain.hero.PlayerHero;
import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;


public class GameDAO extends SimpleJdbcDaoSupport {
	public static final int SELECT_PALYER_NUM = 20;//分页
	public static final int SELECT_ALLY_NUM = 20;//分页
	public static final byte LISTSTEP = 20;
	private static final Logger logger = LoggerFactory
			.getLogger(GameDAO.class);
	private static final AutoNameMapper namesMapper = new AutoNameMapper();
	private BuildingDAO buildDAO = new BuildingDAO(this);
	private NationDAO nationDAO = new NationDAO(this);
	private RelationDAO relationDAO = new RelationDAO(this);
	private ArenaDAO arenaDAO=new ArenaDAO(this);
	private UsernameStatusDAO usernameStatusDAO = new UsernameStatusDAO(this);
	private PlayerHeroDAO playerHeroDAO = new PlayerHeroDAO(this);
	private FightEventDAO fightEventDAO = new FightEventDAO(this);
	private PropsDAO propsDAO = new PropsDAO(this);
	private PlayerCardsDAO playerCardsDAO = new PlayerCardsDAO(this);
	private PlayerRechargeDAO playerRechargeDAO = new PlayerRechargeDAO(this);
	private ArenaAwardDAO arenaAwardDAO = new ArenaAwardDAO(this);
	private UserQuestionDAO userQuestionDAO = new UserQuestionDAO(this);
	private static final RoleDataRowMapper roleDataRowMapper = new RoleDataRowMapper();
	private static final PlayerHeroRowMapper playerHeroRowMapper=new PlayerHeroRowMapper();
	//插入角色
	public static final String SQL_ADDROLE="insert into roledata (name,userid,sex,faction,level,exp,gameMoney,joyMoney,packCapacity,storage,intradayOnlineTime,onlineTime,lastLoginTime,createTime,leaveTime,activeEndTime,vip,friends,enemys,warTimes,note,award,cityLevel,credit,attriCommander,attriPolitics,attriCharm,attriCapacity,playerCells,camp,campId,ladder,ladderId,nativeId,isChangeName,score,chip,ladderMax,arenaId,arenaKill,arenaTime,acceptedQuests,completedQuests,dailyQuests,fightInfo,boxNum,selectedAwardIdString,beAwardedIdString,newBag,lastPostQuestionTime,lastSignTime,canSpeak,canLogin,signNum,isSigned)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	// 查询角色
//	private static final String SQL_GET_ROLE_BY_ID = "select * from roledata where id=?";
//	private static final String SQL_GET_ALL_ROLES = "select * from roledata";
	private static final String SQL_GET_ROLE_BY_UID = "select * from roledata where userid=?";
	private static final String SQL_IS_NAME_EXIST = "select count(1) from roledata where name=?";
//	private static final String SQL_GET_RANDOM_PLAYER = "select * from roledata ORDER BY rand() limit 0,?";
//	public static final String SQL_GET_RANDOM_PLAYER1 = "SELECT * FROM roledata a WHERE a.userid IN (SELECT userID FROM playerbuilding WHERE buildingID =1000 AND occupyUserId = 0) AND a.level = ? AND a.level = ? AND a.userid != ? ORDER BY RAND() LIMIT 0,?";
//	public static final String SQL_GET_RANDOM_PLAYER2 = "SELECT * FROM roledata a WHERE a.userid IN (SELECT userID FROM playerbuilding WHERE buildingID =1000 AND occupyUserId = 0) AND a.level> ? AND a.level <= ? AND a.userid != 1 ORDER BY RAND() LIMIT 0,?";
//	public static final String SQL_GET_RANDOM_PLAYER3 = "SELECT * FROM roledata a WHERE a.userid IN (SELECT userID FROM playerbuilding WHERE buildingID =1000 AND occupyUserId = 0) AND a.level >= ? AND a.level < ? AND a.userid != 1 ORDER BY RAND() LIMIT 0,?";
	//按玩家金币降序选择前一定数量的roledata
	private static final String SQL_GET_RANK_PLAYER_BY_GAME_MONEY_DESC = "select * from roledata order by gameMoney desc limit ?";
	//按玩家钻石降序选择前一定数量的roledata
	private static final String SQL_GET_RANK_PLAYER_BY_JOY_MONEY_DESC = "select * from roledata order by joyMoney desc limit ?";
	//按玩家通天塔进度选择前一定数量的roledata
	private static final String SQL_GET_RANK_PLAYER_BY_LADDER_MAX_DESC = "select * from roledata order by ladderMax desc limit ?";
	private static final String SQL_GAMEMONEY="select sum(gameMoney) from roledata where userid in";
	private static final String SQL_JOYMONEY="select sum(joyMoney) from roledata where userid in";
	// 删除好友
//	private static final String SQL_DELETE_FRIEND = "update playercharacter set friends=? where id=?";
	// 取得没有用过的名字
	private static final String SQL_GET_ALL_NAMES = "select * from aotuName where status=0";
	// 设定名字已经用过
	private static final String SQL_UPDATE_NAME = "update aotuName set status=1 where userID=?";
	public static final String SQL_GET_PLAYER_CELLS = "select playerCells from roledata where userID=?";
	private static final String SQL_SAVE_ROLE = "update roledata set name=?, sex=?, faction=?, level=?, exp=?, gameMoney=?, joyMoney=?, packCapacity=?, storage=?, lastLoginTime=?, onlineTime=?, leaveTime=?, activeEndTime=?, intradayOnlineTime=?, vip=?, friends=?, enemys=?, note=?, award=?, cityLevel=?, credit=?, attriCommander=?, attriPolitics=?, attriCharm=?, attriCapacity=?, playerCells=? ,  warTimes = ? ,camp=? ,campId=?,ladder=?,ladderId=?,nativeId=? ,isChangeName=?,score=?,chip=?,archerEqu=?,infantryEqu=?,cavalryEqu=?,specialArms=?,ladderMax=?,arenaId=?,arenaKill=?,arenaTime =?,cityNum = ?,militaryMedals=? ,completedQuests=? ,acceptedQuests=? ,dailyQuests=?,achieve = ?,title=?,heroRefresh1=?,heroRefresh2=? ,fightInfo=?,boxNum=?,selectedAwardIdString=?,beAwardedIdString=?,newBag=?,lastPostQuestionTime=?,lastSignTime=?,canSpeak=?,canLogin=?,signNum=?,isSigned=? where userid=?";
//	private static final String SQL_SAVE_ROLE2 = "update roledata set  playerCells =? where userID=?";
	//删除playerBuilding 数据
//	public static final String SQL_DELETE_PLB = "delete from playerbuilding where 1 = 1 and userID  = ? ";
	//增加playerBuilding 数据
	public static final String SQL_INSERT_PLB = "insert into playerbuilding (buildLevel,buildingID,userID,isOccupy,category,x,price,honerPrice,updateTime,constructionTime,buildType,isUnique,canBeDestroyed,isLevelUp,inCometype,inComeCount,officerId,officerInfo,occupyUserId,propsId,operatcount,remark1,remark2,remark3,y,chargeOutTime,stealTime)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_UPDATE_PLB = "update playerbuilding set buildLevel=?, buildingID=?, userID=?, isOccupy=?, category=?, x=?,y=?, price=?, honerPrice=?, updateTime=?, constructionTime=?, buildType=?, isUnique=?, canBeDestroyed=?, isLevelUp=?, inCometype=?, inComeCount=?, officerId=?, officerInfo=?, occupyUserId=?, propsId=?, operatcount=?, remark1=?, remark2=?, remark3=?,additionCount=?,additionCountTime = ?,addTime = ?,stealCount=?,chargeOutTime=?,stealTime=? ,exp = ? where id=?";
	public static final String SQL_SELECT_PLB="select * from playerbuilding where userID = ?";
	public static final String SQL_SELECT_ALL_PLB = "select * from playerbuilding where userID = ?";
	//增加playerhero
	public static final String SQL_ADDHERO="insert into playerhero (id,userId,name,icon,level,exp,sex,attack,defence,hp,maxHp,memo,attackAdd,defenceAdd,hpAdd,color,soldierNum,weapon,armour,helmet,horse,skillNum,skill,buildId,status,trainEndTime,trainType,trainIndex,soldier,initHp,initAttack,initDefence,attackTotal,defenceTotal,hpTotal)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	//获得 playerhero通过id
	public static final String SQL_GETHERO="select * from playerhero where id = ?";
	//获得playerhero通过userid
	public static final String SQL_GETALLHERO="select * from playerhero where userId = ?";
	//保存playerhero
	public static final String SQL_SAVEHERO="update playerhero set userId=?, name=?, icon=?, level=?, exp=?, sex=?, attack=?, defence=?, hp=?, maxHp=?, memo=?, attackAdd=?, defenceAdd=?, hpAdd=?, color=?, soldierNum=?, weapon=?, armour=?, helmet=?, horse=?, skillNum=?, skill=?, buildId=?, status=?, trainEndTime=?, trainType=?, trainIndex=? ,soldier=? ,initHp=?,initAttack=?,initDefence=?,attackTotal = ?,defenceTotal=?,hpTotal=? where id=?";	
	public static final String SQL_DELHERO="delete from playerhero where 1 = 1 and id  = ? ";
	
	
	public static final String SQL_GET_ROLE_ALL = "select * from roledata where 1 = 1 ";
	public static final String SQL_ARENA="select * from roledata where arenaId!=0";
	
	/**
	 * 分页查询玩家数据
	 */
	public static final String SQL_QUERY_ROLE_BY_PAGE = "select * from roledata limit ?,?";
//	public static final String SQL_ADDEVENT="insert into fightevent (id,userId,heroId,memo,data)values(?,?,?,?,?)";
//	public static final String SQL_GETEVENT="select * from fightevent where id = ?";
	//nation
	
//	public static final String SQL_ADD_MAIL = "insert into mail (id,messageType,messString,sendUserId,receiveUserId,sendTime,remark1,remark2)values(?,?,?,?,?,?,?,?)";
//	public static final String SQL_DELETE_MAIL = "delete from mail where messageType = ?";
//	public static final String SQL_SELECT_MAIL = "select * from mail";
	//统计相关的查询
	public static final String SQL_COUNT_ROLES = "select count(*) from roledata";
	public static final String SQL_LEVEL="select count(*) from roledata where level=? AND createTime >= ? AND createTime <= ? ";
	public static final String SQL_QUERY_REG="SELECT count(*) FROM roledata WHERE createTime >= ? AND createTime <= ? ";
	public static final String SQL_NAME = "select * from roledata where userid=?";
	
	/**
	 * @return GET the relationDAO
	 */
	public RelationDAO getRelationDAO() {
		return relationDAO;
	}

	public PropsDAO getPropsDAO() {
		return propsDAO;
	}

	public void setPropsDAO(PropsDAO propsDAO) {
		this.propsDAO = propsDAO;
	}

	/**
	 * @return GET the buildDAO
	 */
	public BuildingDAO getBuildDAO() {
		return buildDAO;
	}
	
	public NationDAO getNationDAO() {
		return nationDAO;
	}
	public ArenaDAO getArenaDAO(){
		return arenaDAO;
	}
	
	public UsernameStatusDAO getUsernameStatusDAO(){
		return usernameStatusDAO;
	}
	
	public PlayerHeroDAO getPlayerHeroDAO(){
		return playerHeroDAO;
	}
	
	public FightEventDAO getFightEventDAO(){
		return fightEventDAO;
	}
	
	public PlayerCardsDAO getPlayerCardsDAO(){
		return playerCardsDAO;
	}
	
	public PlayerRechargeDAO getPlayerRechargeDAO(){
		return playerRechargeDAO;
	}
	
	public ArenaAwardDAO getArenaAwardDAO(){
		return arenaAwardDAO;
	}
	
	public UserQuestionDAO getUserQuestionDAO(){
		return userQuestionDAO;
	}
	
	/**
	 * 增加一个角色
	 * @param rd
	 * @return
	 */
	public boolean  addRoleData(final RoleData roledata){
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADDROLE,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1,roledata.getName());
					ps.setLong(2,roledata.getUserid());
					ps.setByte(3,roledata.getSex());
					ps.setByte(4,roledata.getFaction());
					ps.setShort(5,roledata.getLevel());
					ps.setInt(6,roledata.getExp());
					ps.setInt(7,roledata.getGameMoney());
					ps.setInt(8,roledata.getJoyMoney());
					ps.setShort(9,roledata.getPackCapacity());
					ps.setString(10,roledata.getStorage());
					ps.setInt(11,roledata.getIntradayOnlineTime());
					ps.setInt(12,roledata.getOnlineTime());
//					ps.setLong(13,roledata.getLastLoginTime());
					ps.setTimestamp(13, new Timestamp(roledata.getLastLoginTime()));
					ps.setTimestamp(14, new Timestamp(roledata.getCreateTime()));
//					ps.setLong(14,roledata.getCreateTime());
//					ps.setLong(15,roledata.getLeaveTime());
					ps.setTimestamp(15, new Timestamp(TimeUtils.nowLong()));//alter by madi, alter leaveTime
//					ps.setLong(16,roledata.getActiveEndTime());
					ps.setTimestamp(16, new Timestamp(roledata.getActiveEndTime()));
					ps.setString(17,roledata.getVip());
					ps.setString(18,roledata.getFriends());
					ps.setString(19,roledata.getEnemys());
					ps.setInt(20,roledata.getWarTimes());
					ps.setString(21,roledata.getNote());
					ps.setInt(22,roledata.getAward());
					ps.setInt(23,roledata.getCityLevel());
					ps.setInt(24,roledata.getCredit());
					ps.setInt(25,roledata.getAttriCommander());
					ps.setInt(26,roledata.getAttriPolitics());
					ps.setInt(27,roledata.getAttriCharm());
					ps.setInt(28,roledata.getAttriCapacity());
					ps.setString(29,roledata.getPlayerCells());
					ps.setString(30, roledata.getCamp());
					ps.setInt(31, roledata.getCampId());
					ps.setString(32, roledata.getLadder());
					ps.setInt(33, roledata.getLadderId());
					ps.setInt(34, roledata.getNativeId());
					ps.setInt(35, roledata.getIsChangName());
					ps.setInt(36, roledata.getScore());
					ps.setInt(37, roledata.getChip());
					ps.setInt(38, roledata.getLadderMax());
					ps.setInt(39, roledata.getArenaId());
					ps.setInt(40, roledata.getArenaKill());
					ps.setTimestamp(41, new Timestamp(roledata.getArenaTime()));
					ps.setString(42, roledata.getAcceptedQuests());
					ps.setString(43,roledata.getCompletedQuests());
					ps.setString(44, roledata.getDailyQuests());
					ps.setString(45, roledata.getFightInfo());
					ps.setInt(46, roledata.getBoxNum());
					ps.setString(47, roledata.getSelectedAwardIdString());
					ps.setString(48, roledata.getBeAwardedIdString());
					ps.setByte(49, roledata.getNewBag());
					ps.setTimestamp(50, new Timestamp(roledata.getLastPostQuestionTime()));
					ps.setTimestamp(51, new Timestamp(roledata.getLastSignTime()));
					ps.setByte(52,roledata.getCanSpeak());
					ps.setByte(53, roledata.getCanLogin());
					ps.setInt(54, roledata.getSignNum());
					ps.setByte(55, roledata.getIsSigned());
					return ps;
				}
			});

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
//	/**
//	 * 得到角色
//	 * 
//	 * @param roleId
//	 * @return
//	 * @throws DataAccessException
//	 */
//	public RoleData getRole(int roleId) throws DataAccessException {
//		try {
//			
//			RoleData roleData = getSimpleJdbcTemplate().queryForObject(
//					SQL_GET_ROLE_BY_ID, roleDataRowMapper, roleId);
//			return roleData;
//		} catch (EmptyResultDataAccessException e) {
//			logger.error("no role exist with id : " + roleId);
//			return null;
//		}
//	}
	
//	public List<RoleData> getRoles(int num) throws DataAccessException {
//		try {
//			
//			return getSimpleJdbcTemplate().query(SQL_GET_RANDOM_PLAYER,roleDataRowMapper,num);
//		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	/**
	 * 玩家金币前一定数量排行榜
	 * @param num
	 * @return
	 * @throws DataAccessException
	 */
	public List<RoleData> getRankRolesByGameMoney(int num) throws DataAccessException{
		try {
			return getSimpleJdbcTemplate().query(SQL_GET_RANK_PLAYER_BY_GAME_MONEY_DESC,roleDataRowMapper,num);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 玩家钻石前一定数量的排行榜
	 * @param num
	 * @return
	 * @throws DataAccessException
	 */
	public List<RoleData> getRankRolesByJoyMoney(int num) throws DataAccessException{
		try {
			return getSimpleJdbcTemplate().query(SQL_GET_RANK_PLAYER_BY_JOY_MONEY_DESC,roleDataRowMapper,num);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 玩家通天塔进度一定数量的排行榜
	 * @param num
	 * @return
	 * @throws DataAccessException
	 */
	public List<RoleData> getRankRolesByLadderMax(int num) throws DataAccessException{
		try {
			return getSimpleJdbcTemplate().query(SQL_GET_RANK_PLAYER_BY_LADDER_MAX_DESC,roleDataRowMapper,num);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<RoleData> getRoles1(String sql,short level1,short level2,int userid,int num) throws DataAccessException {
		try {
			return getSimpleJdbcTemplate().query(sql,roleDataRowMapper,level1,level2,userid,num);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public RoleData getRoleByUid(int uid) throws DataAccessException {
		try {
			
			RoleData roleData = getSimpleJdbcTemplate().queryForObject(
					SQL_GET_ROLE_BY_UID, roleDataRowMapper, uid);
			return roleData;
		} catch (EmptyResultDataAccessException e) {
//			logger.error("no role exist with uid : " + uid);
			return null;
		}
	}
	
//	public List<RoleData> getAllRoles()throws DataAccessException{
//		try {
//			return getSimpleJdbcTemplate().query(SQL_GET_ALL_ROLES,roleDataRowMapper);
//		} catch (EmptyResultDataAccessException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//	}
	
	public List<AotuName> getAllNames() {
		return getSimpleJdbcTemplate().query(SQL_GET_ALL_NAMES, namesMapper);
	}

	public void deleteName(int id) {
		getSimpleJdbcTemplate().update(SQL_UPDATE_NAME, id);
		logger.info("delete name userid=" + id);
	}
	/**
	 * 保存角色信息
	 * @param player
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public boolean saveRole(RoleData roledata) {
		//注意，生成后的语句要去掉 getCreateTime ，timestamp的类型不保存
		getSimpleJdbcTemplate().update(SQL_SAVE_ROLE,
				roledata.getName(),
				roledata.getSex(),
				roledata.getFaction(),
				roledata.getLevel(),
				roledata.getExp(),
				roledata.getGameMoney(),
				roledata.getJoyMoney(),
				roledata.getPackCapacity(),
				roledata.getStorage(),
				new Timestamp(roledata.getLastLoginTime()),
				roledata.getOnlineTime(),
//				new Timestamp(roledata.getCreateTime()),
				new Timestamp(roledata.getLeaveTime()),
				//new Timestamp(TimeUtils.nowLong()),//leaveTime
				new Timestamp(roledata.getActiveEndTime()),
				roledata.getIntradayOnlineTime(),
				roledata.getVip(),
				roledata.getFriends(),
				roledata.getEnemys(),
				roledata.getNote(),
				roledata.getAward(),
				roledata.getCityLevel(),
				roledata.getCredit(),
				roledata.getAttriCommander(),
				roledata.getAttriPolitics(),
				roledata.getAttriCharm(),
				roledata.getAttriCapacity(),
				roledata.getPlayerCells(),
				roledata.getWarTimes(),
				roledata.getCamp(),
				roledata.getCampId(),
				roledata.getLadder(),
				roledata.getLadderId(),
				roledata.getNativeId(),
				roledata.getIsChangName(),
				roledata.getScore(),
				roledata.getChip(),
				roledata.getArcherEqu(),
				roledata.getInfantryEqu(),
				roledata.getCavalryEqu(),
				roledata.getSpecialArms(),
				roledata.getLadderMax(),
				roledata.getArenaId(),
				roledata.getArenaKill(),
				new Timestamp(roledata.getArenaTime()),
				roledata.getCityNum(),
				roledata.getMilitaryMedals(),
				roledata.getCompletedQuests(),
				roledata.getAcceptedQuests(),
				roledata.getDailyQuests(),
				roledata.getAchieve(),
				roledata.getTitle(),
				roledata.getHeroRefresh1(),
				roledata.getHeroRefresh2(),
				roledata.getFightInfo(),
				roledata.getBoxNum(),
				roledata.getSelectedAwardIdString(),
				roledata.getBeAwardedIdString(),
				roledata.getNewBag(),
				new Timestamp(roledata.getLastPostQuestionTime()),
				new Timestamp(roledata.getLastSignTime()),
				roledata.getCanSpeak(),
				roledata.getCanLogin(),
				roledata.getSignNum(),
				roledata.getIsSigned(),
				roledata.getUserid()
				);

		return true;
	}
	
//	/**
//	 * 保存角色信息
//	 * @param player
//	 * @return
//	 */
//	public boolean saveRole2(RoleData player) {
//		getSimpleJdbcTemplate().update(SQL_SAVE_ROLE2,
//				player.getPlayerCells(),
//				player.getId());
//
//		return true;
//	}
	/**
	 * 获得全部英雄
	 * @param userId
	 * @return
	 */
	public List<PlayerHero> getAllPlayerHero(int userId){
	try {
			
			List<PlayerHero> list = getSimpleJdbcTemplate().query(SQL_GETALLHERO, playerHeroRowMapper, userId);
			return list;
		} catch (EmptyResultDataAccessException e) {
			logger.error("no role exist with id : " + userId);
			return null;
		}
	}
	public PlayerHero getPlayerHero(int heroId){
		List<PlayerHero> list = getSimpleJdbcTemplate().query(SQL_GETHERO, playerHeroRowMapper, heroId);
		return list.get(0);
	}
	/**
	 * 删除一个英雄
	 * @param playerhero
	 * @return
	 */
	public boolean delPlayerHero(PlayerHero playerhero){
		try{
			getSimpleJdbcTemplate().update(SQL_DELHERO,playerhero.getId() );
		}catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 保存玩家英雄
	 * @param playerhero
	 * @return
	 */
	public boolean savePlayerHero(PlayerHero playerhero){
		try{
			getSimpleJdbcTemplate().update(SQL_SAVEHERO,
					playerhero.getUserId(),
					playerhero.getName(),
					playerhero.getIcon(),
					playerhero.getLevel(),
					playerhero.getExp(),
					playerhero.getSex(),
					playerhero.attack,
					playerhero.defence,
					playerhero.hp,
					playerhero.maxHp,
					playerhero.getMemo(),
					playerhero.getAttackAdd(),
					playerhero.getDefenceAdd(),
					playerhero.getHpAdd(),
					playerhero.getColor(),
					playerhero.soldierNum,
					playerhero.getWeapon(),
					playerhero.getArmour(),
					playerhero.getHelmet(),
					playerhero.getHorse(),
					playerhero.getSkillNum(),
					playerhero.getSkill(),
					playerhero.getBuildId(),
					playerhero.getStatus(),
//					playerhero.getTrainEndTime(),
					new Timestamp(playerhero.getTrainEndTime()),
					playerhero.getTrainType(),
					playerhero.getTrainIndex(),
					playerhero.getSoldier(),
					playerhero.getInitHp(),
					playerhero.getInitAttack(),
					playerhero.getInitDefence(),
					playerhero.getAttack(),
					playerhero.getDefence(),
					playerhero.getHp(),
					playerhero.getId()//这一行替换的时候不需要删除
					);
		}catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
//	/**
//	 * 批量保存将领数据
//	 * @param list
//	 */
//	public void savePlayerHero(final List list){
//	        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter(){  
//	            public void setValues(PreparedStatement ps,int i) throws SQLException{  
//	            	PlayerHero playerhero=(PlayerHero)list.get(i);
//	            	ps.setInt(1,playerhero.getId());
//					ps.setInt(2,playerhero.getUserId());
//					ps.setString(3,playerhero.getName());
//					ps.setString(4,playerhero.getIcon());
//					ps.setInt(5,playerhero.getLevel());
//					ps.setInt(6,playerhero.getExp());
//					ps.setByte(7,playerhero.getSex());
//					ps.setInt(8,playerhero.getAttack());
//					ps.setInt(9,playerhero.getDefence());
//					ps.setInt(10,playerhero.getHp());
//					ps.setInt(11,playerhero.getMaxHp());
//					ps.setString(12,playerhero.getMemo());
//					ps.setInt(13,playerhero.getAttackAdd());
//					ps.setInt(14,playerhero.getDefenceAdd());
//					ps.setInt(15,playerhero.getHpAdd());
//					ps.setByte(16,playerhero.getColor());
//					ps.setInt(17,playerhero.getSoldierNum());
//					ps.setInt(18,playerhero.getWeapon());
//					ps.setInt(19,playerhero.getArmour());
//					ps.setInt(20,playerhero.getHelmet());
//					ps.setInt(21,playerhero.getHorse());
//					ps.setByte(22,playerhero.getSkillNum());
//					ps.setString(23,playerhero.getSkill());
//					ps.setInt(24,playerhero.getBuildId());
//					ps.setByte(25,playerhero.getStatus());
////					ps.setLong(26,playerhero.getTrainEndTime());
//					ps.setTimestamp(26, new Timestamp(playerhero.getTrainEndTime()) );
//					ps.setByte(27,playerhero.getTrainType());
//					ps.setByte(28,playerhero.getTrainIndex());
//					ps.setString(29,playerhero.getSoldier());
//	              }  
//	              public int getBatchSize(){  
//	                 return list.size();  
//	              }  
//	        };  
//	        try{
//	            getJdbcTemplate().batchUpdate(SQL_SAVEHERO, setter);
//	        }catch(Exception ex){
//	        	ex.printStackTrace();
//	        }
//	    
//	}
	public long sumGameMoney(Set<Integer> set){
		String sql=SQL_GAMEMONEY+"(";
		int index=0;
		for(Integer i:set){
			sql+=String.valueOf(i);
			index++;
			if(index<set.size()){
				sql+=",";
			}
		}
		sql+=")";
		return this.getJdbcTemplate().queryForLong(sql);
	}
	public int sunJoyMoney(Set<Integer> set){
		String sql=SQL_JOYMONEY+"(";
		int index=0;
		for(Integer i:set){
			sql+=String.valueOf(i);
			index++;
			if(index<set.size()){
				sql+=",";
			}
		}
		sql+=")";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	/**
	 * 增加一个玩家英雄
	 * @param hero
	 * @return
	 */
	public int addPlayerHero(final PlayerHero playerhero){
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADDHERO,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1,playerhero.getId());
					ps.setInt(2,playerhero.getUserId());
					ps.setString(3,playerhero.getName());
					ps.setString(4,playerhero.getIcon());
					ps.setInt(5,playerhero.getLevel());
					ps.setInt(6,playerhero.getExp());
					ps.setByte(7,playerhero.getSex());
					ps.setInt(8,playerhero.getAttack());
					ps.setInt(9,playerhero.getDefence());
					ps.setInt(10,playerhero.getHp());
					ps.setInt(11,playerhero.getMaxHp());
					ps.setString(12,playerhero.getMemo());
					ps.setInt(13,playerhero.getAttackAdd());
					ps.setInt(14,playerhero.getDefenceAdd());
					ps.setInt(15,playerhero.getHpAdd());
					ps.setByte(16,playerhero.getColor());
					ps.setInt(17,playerhero.getSoldierNum());
					ps.setInt(18,playerhero.getWeapon());
					ps.setInt(19,playerhero.getArmour());
					ps.setInt(20,playerhero.getHelmet());
					ps.setInt(21,playerhero.getHorse());
					ps.setByte(22,playerhero.getSkillNum());
					ps.setString(23,playerhero.getSkill());
					ps.setInt(24,playerhero.getBuildId());
					ps.setByte(25,playerhero.getStatus());
//					ps.setLong(26,playerhero.getTrainEndTime());
					ps.setTimestamp(26, new Timestamp(playerhero.getTrainEndTime()) );
					ps.setByte(27,playerhero.getTrainType());
					ps.setByte(28,playerhero.getTrainIndex());
					ps.setString(29, playerhero.getSoldier());
					ps.setInt(30,playerhero.getInitHp());
					ps.setInt(31,playerhero.getInitAttack());
					ps.setInt(32,playerhero.getInitDefence());
					
					ps.setInt(33,playerhero.getAttackTotal());
					ps.setInt(34,playerhero.getDefenceTotal());
					ps.setInt(35,playerhero.getHpTotal());
					return ps;
				}

			}, keyHolder);

			return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
//	/**
//	 * 获得一个战斗事件
//	 * @param eventId
//	 * @return
//	 * @throws DataAccessException
//	 */
//	public FightEvent getEvent(int eventId) throws DataAccessException {
//		try {
//			
//			FightEvent fightEvent = getSimpleJdbcTemplate().queryForObject(
//					SQL_GETEVENT, fightEventRowMapper, eventId);
//			return fightEvent;
//		} catch (EmptyResultDataAccessException e) {
//			logger.error("no event exist with id : " + eventId);
//			return null;
//		}
//	}
//	/**
//	 * 增加战斗记录
//	 * @param fightevent
//	 * @return
//	 */
//	public int addFightEvent(final FightEvent fightevent){
//		try {
//			DBManager db=DBManager.getInstance();
//			KeyHolder keyHolder = new GeneratedKeyHolder();
//			db.getWorldDAO().getJdbcTemplate().update(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn)
//						throws SQLException {
//					PreparedStatement ps = conn.prepareStatement(SQL_ADDEVENT,
//							Statement.RETURN_GENERATED_KEYS);
//					ps.setInt(1,fightevent.getId());
//					ps.setInt(2,fightevent.getUserId());
//					ps.setInt(3,fightevent.getHeroId());
//					ps.setString(4,fightevent.getMemo());
//					ps.setString(5,fightevent.getData());
//					return ps;
//				}
//
//			}, keyHolder);
//
//			return keyHolder.getKey().intValue();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return 0;
//	}
//	public static void main(String[] args){
//		DBManager.getInstance().init();
//		GameWorldDAO d = new GameWorldDAO();
//		d.getSimpleJdbcTemplate().queryForInt("select id from playercharacter where id = 1");
//		System.out.println(d.getSimpleJdbcTemplate());
//	}
	
	public int add(final HashMap<Integer,Object> map,final String sql) {
		try{
			KeyHolder keyHolder=new GeneratedKeyHolder();
			getJdbcTemplate().update(new PreparedStatementCreator(){
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps=conn.prepareStatement(sql,1);
					for(int i = 1 ; i <= map.size();i++){
						Object o = map.get(i);
						if(o instanceof Byte){
							ps.setByte(i, (Byte)o);
						}else if(o instanceof Timestamp){
							ps.setTimestamp(i,(Timestamp)o);
						}else if(o instanceof Time){
							ps.setTime(i,(Time)o);
						}else if(o instanceof Blob){
							ps.setBlob(i, (Blob)o);
						}else if(o instanceof String){
							ps.setString(i, (String)o);
						}else if(o instanceof Long){
							ps.setLong(i, (Long)o);
						}else if(o instanceof Double){
							ps.setDouble(i, (Double)o);
						}else if(o instanceof Short){
							ps.setShort(i, (Short)o);
						}
					}
					
					return ps;
				}
			},keyHolder);
		return keyHolder.getKey().intValue();
		}catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}
	
//	/**
//	 * 删除好友
//	 * @param roleId
//	 * @param deleteId
//	 */
//	public void deleteFriend(int roleId,int deleteId){
//		RoleData role = getSimpleJdbcTemplate().queryForObject(SQL_GET_ROLE_BY_ID, roleDataRowMapper, roleId);
//		String friendsStr = role.getFriends();
//		List<NeighborRole> friends = new ArrayList<NeighborRole>();
//		if(friendsStr != null && friendsStr.equals("") == false)
//		{
//			NeighborRole friend = null;
//			String[] friendStrs = friendsStr.split(";");
//			for(String friendStr:friendStrs)
//			{
//				String[] params = friendStr.split(",");
//				friend = NeighborRole.createFriend(roleId, Integer.parseInt(params[0]),params[2],Byte.parseByte(params[3]));
//				friend.setFavor(Byte.parseByte(params[1]));
//				friends.add(friend);
//			}
//		}
//		for(NeighborRole friend:friends){
//			if(friend.getNeighborRoleId() == deleteId){
//				friends.remove(friend);
//				break;
//			}
//		}
//		StringBuffer sb = new StringBuffer();
//		boolean isFirst = true;
//		for(NeighborRole friend:friends)
//		{
//			if(isFirst)
//			{
//				isFirst = false;
//			}
//			else
//			{
//				sb.append(";");
//			}
//			
//			sb.append(friend.getNeighborRoleId());
//			sb.append(",");
//			sb.append(friend.getFavor());
//			sb.append(",");
//			sb.append(friend.getName());
//			sb.append(",");
//			sb.append(friend.getFaction());
//		}
//		getSimpleJdbcTemplate().update(SQL_DELETE_FRIEND, sb.toString(), roleId);
//	}
	/**
	 * 是否已有同名角色
	 * @param name
	 * @return
	 * @throws DataAccessException
	 */
	public boolean isNameExist(String name) throws DataAccessException
	{
		return getSimpleJdbcTemplate().queryForInt(SQL_IS_NAME_EXIST, name) > 0;
	}
	
//	/**
//     * 创建角色
//     * @param skyId
//     * @param spouseName
//     * @param sex
//     * @return int roleId
//     */
//	public int addRole(RoleData role) throws DataAccessException
//	{
//		if(this.simpleJdbcInsert_role == null)
//		{
//			simpleJdbcInsert_role = new SimpleJdbcInsert(getDataSource()).withTableName("playercharacter").usingColumns("skyId","userid","name","sex","faction","weapon").usingGeneratedKeyColumns("id");
//		}
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("skyId", role.getSkyId());
//        map.put("userid", role.getUserId());
//        map.put("name", role.getName());
//        map.put("sex", role.getSex());
//        map.put("faction", role.getFaction());
//        map.put("weapon", role.getWeapon());
//        Number id = simpleJdbcInsert_role.executeAndReturnKey(map);
//        
//        // insert的daily_activity表数据
//        addDailyActivity(id.intValue());
//        logger.debug("current insert roleId ====== " + id.intValue());
//        return id.intValue();
//	}
	
	public List<RoleData> getAllRole(String condition,int page){
		if (page <= 0) {
			page = 1;
		}
		int from = (page - 1) * SELECT_PALYER_NUM;
		int to = GameDAO.SELECT_PALYER_NUM;
		condition += " limit "+from+","+to;
		logger.debug("condition:"+condition);
		return getSimpleJdbcTemplate().query(SQL_GET_ROLE_ALL + condition,roleDataRowMapper);
	}
//	
	public int getPlayersCountByGMCondition(String condition) {
		String sql = "select count(*) from roledata where 1=1  " +condition ;
		logger.debug(sql);
		return getSimpleJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * 分页查询玩家数据
	 * @param fromIndex 查询记录的起始记录索引
	 * @param pageSize 每次查询的记录条数
	 * @return
	 */
	public List<RoleData> queryRoleByPage(int fromIndex,int pageSize){
		try {
			List<RoleData> list = getSimpleJdbcTemplate().query(SQL_QUERY_ROLE_BY_PAGE, roleDataRowMapper, fromIndex,pageSize);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 计算数据库中玩家数据总记录条数
	 * @return
	 */
	public int countRoles(){
		int num = getSimpleJdbcTemplate().queryForInt(SQL_COUNT_ROLES);
		return num;	
		
	}
	public int countRoles(int level,String start,String end){
		int num = getSimpleJdbcTemplate().queryForInt(SQL_LEVEL, level,start,end);
		return num;	 
	}
	
	public int countReg(String start,String end){
			int num =getSimpleJdbcTemplate().queryForInt(SQL_QUERY_REG, start,end);
			return num;
	}
	public RoleData getName(long id){
		List<RoleData> list = getSimpleJdbcTemplate().query(SQL_NAME, roleDataRowMapper,(int)id);
		if(list.size()==0){
			logger.info("get name is null ,id="+id);
			return null;
		}
		return list.get(0);	
	}
	/**
	 * 获得全部玩家
	 * @param userId
	 * @return
	 */
	public List<RoleData> getPlayerArena(){
	try {
			
			List<RoleData> list = getSimpleJdbcTemplate().query(SQL_ARENA, roleDataRowMapper);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	//**************************删除用户所有相关数据 sql *******************************************//
	private static String DEL_ROLEDATA = "DELETE FROM roledata WHERE userId = ?";   //用户信息
	private static String DEL_FRIEND = "DELETE FROM friend WHERE myId = ? OR friendId = ?";//用户好友信息
	private static String DEL_BUILDING = "DELETE FROM playerbuilding WHERE userID = ?";//用户建筑信息
	private static String DEL_CARDS = "DELETE FROM playercards WHERE userid = ?";//用户card信息
	private static String DEL_HERO = "DELETE FROM playerhero WHERE userId = ?";//用户将领信息
	private static String DEL_CHARGE = "DELETE FROM playerrecharge WHERE userId = ?";//用户charge信息
	private static String DEL_DELAY = "DELETE FROM propsdelay WHERE userId = ?";//用户延时道具信息
	private static String DEL_QUESTION = "DELETE FROM userquestion WHERE userId = ?";//用户问题信息
	private static String DEL_USERWARDATA = "DELETE FROM userwardata WHERE userId = ?";//用户积分信息
	
	//用户占领建筑信息
	private static String UPT_BUILDING = "UPDATE  playerbuilding SET occupyUserId = 0,officerId=0,officerInfo='',soldierMsg='' WHERE occupyUserId = ?";//用户占领建筑信息
	//用户竞技场信息
	private static String UPT_ARENA = "UPDATE arena SET userID = 0,heroId =0,heroInfo ='',userName='',soldier='' WHERE userId = ?";//用户占领建筑信息
	//用户金矿信息
	private static String UPT_GOLDMINE = "UPDATE goldmine SET userId = 0,heroId =0,soMsg ='',heroInfo='',userName='' WHERE userId = ?";//用户占领建筑信息
	//用户区域信息
	private static String UPT_NATION = "UPDATE nation SET occupyUser = 0,heroId =0,soldierInfo ='',heroInfo='' WHERE occupyUser = ?";//用户占领建筑信息
	//用户矿脉信息
	private static String UPT_VEINS = "UPDATE veins SET userId = 0,heroId =0,heroInfo='',userName='' WHERE userId = ?";//用户占领建筑信息
	
	
	public void removeRole(int userid){
		try{
			getSimpleJdbcTemplate().update(DEL_ROLEDATA,userid);
			getSimpleJdbcTemplate().update(DEL_FRIEND,userid,userid);
			getSimpleJdbcTemplate().update(DEL_BUILDING,userid);
			getSimpleJdbcTemplate().update(DEL_CARDS,userid);
			getSimpleJdbcTemplate().update(DEL_HERO,userid);
			getSimpleJdbcTemplate().update(DEL_CHARGE,userid);
			getSimpleJdbcTemplate().update(DEL_DELAY,userid);
			getSimpleJdbcTemplate().update(DEL_QUESTION,userid);
			getSimpleJdbcTemplate().update(DEL_USERWARDATA,userid);
			getSimpleJdbcTemplate().update(UPT_BUILDING,userid);
			getSimpleJdbcTemplate().update(UPT_ARENA,userid);
			getSimpleJdbcTemplate().update(UPT_GOLDMINE,userid);
			getSimpleJdbcTemplate().update(UPT_NATION,userid);
			getSimpleJdbcTemplate().update(UPT_VEINS,userid);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//**************************删除用户所有相关数据 sql *******************************************//
}