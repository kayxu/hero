package com.joymeng.game.db.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.joymeng.game.cache.MongoServer;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.db.GameDAO;
import com.joymeng.game.domain.nation.GoldMine;
import com.joymeng.game.domain.nation.Nation;
import com.joymeng.game.domain.nation.Veins;
import com.joymeng.game.domain.nation.war.MilitaryCamp;
import com.joymeng.game.domain.nation.war.UserWarData;

public class NationDAO{
	GameDAO gameWorldDAO;
	private static final Logger logger = LoggerFactory
			.getLogger(NationDAO.class);
	//增加playerBuilding 数据
	//nation
	public static final String SQL_ADD_NATION = "insert into nation (id,type,name,occupyUser,eventId,userInfo,national,resourceId,goldId,remark1,remark2,remark3,remark4,userNum,heroId,soldierInfo)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_UPDATE_NATION="update nation set type=?, name=?,occupyUser=?, eventId=?, userInfo=?, national=?, resourceId=?, goldId=?, remark1=?, remark2=?, remark3=?, remark4=? ,userNum=?, heroId=?, soldierInfo=?,heroInfo=? where id=?";
	public static final String SQL_SELECT_NATION = "select * from nation";
	
	public static final String SQL_ADD_GOLDMINE = "insert into goldmine (id,stateId,userId,level,chargeOut,addition,intervalTime,heroId,soMsg,restTime,type,userName)values(?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_UPDATE_GOLDMINE = "update goldmine set stateId=?, userId=?, level=?, chargeOut=?, addition=?, intervalTime=?, heroId=?, soMsg=?, restTime=?,userName = ?,heroInfo=? where id=?";
	public static final String SQL_SELECT_GOLDMINE = "select * from goldmine order by id";
	
	public static final String SQL_ADD_VEINS="insert into veins (id,stateId,type,heroId,userSoMsg,baseSoMsg,restTime,addition,username,userStateId)values(?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_UPDATE_VEINS = "update veins set stateId=?, type=?, heroId=?, userSoMsg=?, baseSoMsg=?, restTime=?, addition=?,userId = ?,userStateId = ?,username=?,formerStateId=? ,heroInfo=? where id=?";
	public static final String SQL_SELECT_VEINS = "select * from veins";
	public static final String SQL_SUM_MILI_STATE= "SELECT SUM(militaryMedals) allCount FROM roledata WHERE FLOOR(nativeId/100)*100 = ? GROUP BY FLOOR(nativeId/100)";
	//camp
	public static final String SQL_ADD_CAMP = "insert into militarycamp (id,isFalseCamp)values(?,?)";
	public static final String SQL_UPDATE_CAMP="update militarycamp set  nativeId=?, userId=?, heroId=?, soliderInfo=?, occTime=?, isThis=? where id=?";
	public static final String SQL_SELECT_CAMP="select * from militarycamp";
	//userdata
	public static final String SQL_ADD_USERWAR = "insert into userwardata (id,userId,nationId,cdTime,warIntegral)values(?,?,?,?,?)";
	public static final String SQL_UPDATE_USERWAR="update userwardata set userId=?, nationId=?, cdTime=?, warIntegral=? where id=?";
	public static final String SQL_SELECT_USERWAR="select * from userwardata";
	public static final String SQL_DELETE_USERWAR="delete from userwardata";
	public NationDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
	/*****************DAO 操作*****************************/
	public int countMili(int state){//计算每州总军功
		List<Map<String,Object>> lst =  gameWorldDAO.getSimpleJdbcTemplate().queryForList(SQL_SUM_MILI_STATE, state);
		if(lst == null || lst.size() == 0){
			return 0;
		}else{
			Map<String,Object> map = lst.get(0);
			int num  = ((BigDecimal)(map.get("allCount"))).intValue();
			return num;
		}
		
	}
	/**
	 * 添加用户war数据
	 * @param militarycamp
	 */
	public int addUserWar(final UserWarData userwardata){
		if(userwardata.getWarCache() ==null ){
			return -1;
		}
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_USERWAR,1);
					ps.setInt(1,userwardata.getId());
					ps.setInt(2,userwardata.getWarCache().getUserid());
					ps.setInt(3,userwardata.getNationId());
					ps.setInt(4,userwardata.getCdTime());
					ps.setInt(5,userwardata.getWarIntegral());
					System.out.println("UserWarData 1");
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	/**
	 * 保存用户war数据
	 * @param militarycamp
	 */
	public void saveUserWar(UserWarData userwardata){
		if(userwardata.getWarCache() == null){
			return;
		}
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_USERWAR,
				userwardata.getWarCache().getUserid(),
				userwardata.getNationId(),
				userwardata.getCdTime(),
				userwardata.getWarIntegral(),
				userwardata.getId());
	}
	/**
	 * 获取用户数据
	 * @param militarycamp
	 */
	public UserWarData loadUserWar(Map<String, Object> map){
		UserWarData userwardata = new UserWarData();
		userwardata.setId((Integer) map.get("id"));
		int id = (Integer) map.get("userId");
		userwardata.setWarCache(GameUtils.getFromCache(id));
		userwardata.setNationId((Integer) map.get("nationId"));
		userwardata.setCdTime((Integer) map.get("cdTime"));
		userwardata.setWarIntegral((Integer) map.get("warIntegral"));
		return userwardata;
	}
	/**
	 * 删除全部用户数据
	 */
	public void deletaUserWarAll(){
		gameWorldDAO.getJdbcTemplate().update(SQL_DELETE_USERWAR);
	}
	/**
	 * 添加军营
	 * @param militarycamp
	 */
	public void addCamp(final MilitaryCamp militarycamp){
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_CAMP,1);
					ps.setInt(1,militarycamp.getId());
					ps.setBoolean(2, militarycamp.isFalseCamp());
//					ps.setInt(2,militarycamp.getNativeId());
//					ps.setInt(3,militarycamp.getUserId());
//					ps.setInt(4,militarycamp.getHeroId());
//					ps.setString(5,militarycamp.getSoliderInfo());
//					ps.setInt(6,militarycamp.getOccTime());
//					ps.setBoolean(7, militarycamp.isThis());
					System.out.println("native 1");
					return ps;
				}
			}, keyHolder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 保存军营
	 * @param militarycamp
	 */
	public void saveCamp(MilitaryCamp militarycamp){
		int userid = 0;
		if(militarycamp.getOccCache() != null){
			userid = militarycamp.getOccCache().getUserid();
		}
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_CAMP,militarycamp.getNativeId(),
				userid,
				militarycamp.getHeroId(),
				militarycamp.getSoliderInfo(),
				militarycamp.getOccTime(),
				militarycamp.isThis(),
				militarycamp.getId());
	}
	/**
	 * 获取军营
	 * @param militarycamp
	 */
	public MilitaryCamp loadCamp(Map<String, Object> map){
		MilitaryCamp militarycamp = new MilitaryCamp();
		militarycamp.setId((Integer) map.get("id"));
		militarycamp.setNativeId((Integer) map.get("nativeId"));
		int userid = (Integer) map.get("userId");
		militarycamp.setOccCache(GameUtils.getFromCache(userid));
		militarycamp.setHeroId((Integer) map.get("heroId"));
		militarycamp.setSoliderInfo((String)map.get("soliderInfo"));
		militarycamp.setOccTime((Integer) map.get("occTime"));
		militarycamp.setThis((Boolean) map.get("isThis"));
		militarycamp.setFalseCamp((Boolean) map.get("isFalseCamp"));
		return militarycamp;
	}
	public void addNation(final Nation nation){
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_NATION,1);
					ps.setInt(1,nation.getId());
					ps.setByte(2,nation.getType());
					ps.setString(3,nation.getName());
					ps.setInt(4,nation.getOccupyUser());
					ps.setInt(5,nation.getEventId());
					ps.setString(6,nation.getUserInfo());
					ps.setString(7,nation.getNational());
					ps.setInt(8,nation.getResourceId());
					ps.setInt(9,nation.getGoldId());
					ps.setInt(10,nation.getRemark1());
					ps.setInt(11,nation.getRemark2());
					ps.setString(12,nation.getRemark3());
					ps.setString(13,nation.getRemark4());
					ps.setInt(14, nation.getUserNum());
					ps.setInt(15, nation.getHeroId());
					ps.setString(16, nation.getSoldierInfo());
					System.out.println("native 1");
					return ps;
				}
			}, keyHolder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 更新
	 * @param nation
	 */
	public void saveNation(Nation nation){
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_NATION,
			nation.getType(),
			nation.getName(),
			nation.getOccupyUser(),
			nation.getEventId(),
			nation.getUserInfo(),
			nation.getNational(),
			nation.getResourceId(),
			nation.getGoldId(),
			nation.getRemark1(),
			nation.getRemark2(),
			nation.getRemark3(),
			nation.getRemark4(),
			nation.getUserNum(),
			nation.getHeroId(),
			nation.getSoldierInfo(),
			nation.getHeroInfo(),
			nation.getId()
		);	
	}

	
	public Nation loadObject(Map<String, Object> map){
		Nation nation = new Nation();
		nation.setId( (Integer) map.get("id"));
		nation.setType(((Integer)map.get("type")).byteValue());
		nation.setName((String)map.get("name"));
		nation.setOccupyUser((Integer)map.get("occupyUser"));
		nation.setEventId((Integer)map.get("eventId"));
		nation.setUserInfo((String)map.get("userInfo"));
		nation.setNational((String)map.get("national"));
		nation.setResourceId((Integer)map.get("resourceId"));
		nation.setGoldId((Integer)map.get("goldId"));
		nation.setRemark1((Integer)map.get("remark1"));
		nation.setRemark2((Integer)map.get("remark2"));
		nation.setRemark3((String)map.get("remark3"));
		nation.setRemark4((String)map.get("remark4"));
		nation.setUserNum((Integer)map.get("userNum"));
		nation.setHeroId((Integer)map.get("heroId"));
		nation.setSoldierInfo((String)map.get("soldierInfo"));
		nation.setHeroInfo((String)map.get("heroInfo"));
		return nation;
	}
	
	public GoldMine loadGold(Map<String, Object> map){
		GoldMine goldmine = new GoldMine();
		goldmine.setId((Integer) map.get("id"));
		goldmine.setStateId((Integer) map.get("stateId"));
		goldmine.setUserId((Integer) map.get("userId"));
		goldmine.setLevel( ((Integer)map.get("level")).byteValue());
		goldmine.setChargeOut((Integer) map.get("chargeOut"));
		goldmine.setAddition((Integer) map.get("addition"));
		goldmine.setIntervalTime((Long) map.get("intervalTime"));
		goldmine.setHeroId((Integer) map.get("heroId"));
		goldmine.setSoMsg((String) map.get("soMsg"));
		goldmine.setUserName((String) map.get("userName"));
		goldmine.setRestTime((Long) map.get("restTime"));
		goldmine.setType(((Integer)map.get("type")).byteValue());
		goldmine.setHeroInfo((String)map.get("heroInfo"));
		return goldmine;
	}
	
	public Veins loadVeins(Map<String, Object> map){
		Veins veins = new Veins();
		veins.setId((Integer) map.get("id"));
		veins.setStateId((Integer) map.get("stateId"));
		veins.setType(((Integer) map.get("type")).byteValue());
		veins.setHeroId((Integer) map.get("heroId"));
		veins.setUserStateId((Integer) map.get("userStateId"));
		veins.setUserId((Integer) map.get("userId"));
		veins.setUserSoMsg((String) map.get("userSoMsg"));
		veins.setBaseSoMsg((String) map.get("baseSoMsg"));
		veins.setRestTime((Long) map.get("restTime"));
		veins.setAddition((Integer) map.get("addition"));
		veins.setUsername((String) map.get("username"));
		veins.setFormerStateId((Integer) map.get("formerStateId"));
		veins.setIsMain(((Integer) map.get("isMain")).byteValue());
		veins.setHeroInfo((String)map.get("heroInfo"));
		return veins;
	}
	/**
	 * 添加属性 返回ID
	 * 
	 * @param building
	 * @param id
	 * @param completeDate
	 * @return insert into playerbuilding
	 *         (id,buildLevel,buildingID,userID,isOccupy
	 *         ,isInitial,category,coordinateID
	 *         ,levelRequired,price,honerPrice,updateTime
	 *         ,buildType,isUnique,canBeDestroyed
	 *         ,isLevelUp,inCometype,officerId,
	 *         officerInfo,occupyUserId,propsId,operatcount
	 *         ,remark1,remark2,remark3
	 *         )values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 */
	public void addGoldMine(final GoldMine goldmine) {
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_GOLDMINE,1);
					ps.setInt(1,goldmine.getId());
					ps.setInt(2,goldmine.getStateId());
					ps.setInt(3,goldmine.getUserId());
					ps.setByte(4,goldmine.getLevel());
					ps.setInt(5,goldmine.getChargeOut());
					ps.setInt(6,goldmine.getAddition());
					ps.setLong(7,goldmine.getIntervalTime());
					ps.setInt(8,goldmine.getHeroId());
					ps.setString(9,goldmine.getSoMsg());
					ps.setLong(10,goldmine.getRestTime());
					ps.setInt(11,goldmine.getType());
					ps.setString(12, "");
					System.out.println("native 1");
					return ps;
				}
			}, keyHolder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean saveGoldMine(GoldMine goldmine){
//		update goldmine set stateId=?, userId=?, level=?, chargeOut=?, addition=?, intervalTime=?, heroId=?, soMsg=?, restTime=? where id=?
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_GOLDMINE,
			goldmine.getStateId(),
			goldmine.getUserId(),
			goldmine.getLevel(),
			goldmine.getChargeOut(),
			goldmine.getAddition(),
			goldmine.getIntervalTime(),
			goldmine.getHeroId(),
			goldmine.getSoMsg(),
			goldmine.getRestTime(),
			goldmine.getUserName(),
			goldmine.getHeroInfo(),
			goldmine.getId()
		);	
		return true;
	}
	/**
	 * 添加属性 返回ID
	 * 
	 * @param building
	 * @param id
	 * @param completeDate
	 * @return insert into playerbuilding
	 *         (id,buildLevel,buildingID,userID,isOccupy
	 *         ,isInitial,category,coordinateID
	 *         ,levelRequired,price,honerPrice,updateTime
	 *         ,buildType,isUnique,canBeDestroyed
	 *         ,isLevelUp,inCometype,officerId,
	 *         officerInfo,occupyUserId,propsId,operatcount
	 *         ,remark1,remark2,remark3
	 *         )values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	 */
	public void addVeins(final Veins veins) {
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_VEINS,1);
					ps.setInt(1,veins.getId());
					ps.setInt(2,veins.getStateId());
					ps.setByte(3,veins.getType());
					ps.setInt(4,veins.getHeroId());
					ps.setString(5,veins.getUserSoMsg());
					ps.setString(6,veins.getBaseSoMsg());
					ps.setLong(7,veins.getRestTime());
					ps.setInt(8,veins.getAddition());
					ps.setString(9, "");
					ps.setInt(10, veins.getUserStateId());
					System.out.println("native 1");
					return ps;
				}
			}, keyHolder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean saveVeins(Veins veins){
//		update veins set stateId=?, type=?, heroId=?, userSoMsg=?, baseSoMsg=?, restTime=?, addition=? where id=?
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_VEINS,
				veins.getStateId(),
				veins.getType(),
				veins.getHeroId(),
				veins.getUserSoMsg(),
				veins.getBaseSoMsg(),
				veins.getRestTime(),
				veins.getAddition(),
				veins.getUserId(),
				veins.getUserStateId(),
				veins.getUsername(),
				veins.getFormerStateId(),
				veins.getHeroInfo(),
				veins.getId()
		);	
		return true;
	}
	/*****************DAO 操作*****************************/
	

}
