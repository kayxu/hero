package com.joymeng.game.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.joymeng.game.db.DBManager;
import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.PlayerBuildingRowMapper;
import com.joymeng.game.domain.building.PlayerBuilding;

public class BuildingDAO{
	GameDAO gameWorldDAO;
	private static final Logger logger = LoggerFactory
			.getLogger(BuildingDAO.class);
	private static final PlayerBuildingRowMapper playerBuildingRowMapper=new PlayerBuildingRowMapper();
	//增加playerBuilding 数据
	public static final String SQL_INSERT_PLB = "insert into playerbuilding (buildLevel,buildingID,userID,isOccupy,category,x,price,honerPrice,updateTime,constructionTime,buildType,isUnique,canBeDestroyed,isLevelUp,inCometype,inComeCount,officerId,officerInfo,occupyUserId,propsId,operatcount,remark1,remark2,remark3,y,chargeOutTime,stealTime,soldierMsg)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String SQL_UPDATE_PLB = "update playerbuilding set buildLevel=?, buildingID=?, userID=?, isOccupy=?, category=?, x=?,y=?, price=?, honerPrice=?, updateTime=?, constructionTime=?, buildType=?, isUnique=?, canBeDestroyed=?, isLevelUp=?, inCometype=?, inComeCount=?, officerId=?, officerInfo=?, occupyUserId=?, propsId=?, operatcount=?, remark1=?, remark2=?, remark3=?,additionCount=?,additionCountTime = ?,addTime = ?,stealCount=?,chargeOutTime=?,stealTime=? ,exp = ? ,officerTime = ?,soldierMsg = ?,oldLevel=?,InitialTimes=?,refreshTimes=? where id=?";
	public static final String SQL_SELECT_PLB="select * from playerbuilding where userID = ?";
	public static final String SQL_SELECT_ALL_PLB = "select * from playerbuilding where userID = ?";
	public static final String UPDATE_FIELD = "update playerbuilding set ? = ? where userID = ?";
//	public static final String SQL_DELETE_PLB = "delete from playerbuilding where id= ?";
	public BuildingDAO (GameDAO dao){
		gameWorldDAO = dao;
	}
	
	public boolean setField(String filed , Object o,int userId){
		gameWorldDAO.getJdbcTemplate().update(UPDATE_FIELD,filed,o,userId);
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
	public int addPlayerBuilding(final PlayerBuilding playerbuilding) {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(
							SQL_INSERT_PLB, 1);
					ps.setShort(1, playerbuilding.getBuildLevel());
					ps.setInt(2, playerbuilding.getBuildingID());
					ps.setInt(3, playerbuilding.getUserID());
					ps.setInt(4, playerbuilding.getIsOccupy());
					ps.setInt(5, playerbuilding.getCategory());
					ps.setInt(6, playerbuilding.getX());
					ps.setInt(7, playerbuilding.getPrice());
					ps.setInt(8, playerbuilding.getHonerPrice());
					ps.setTimestamp(9, playerbuilding.getUpdateTime());
					ps.setTimestamp(10, playerbuilding.getConstructionTime());
					ps.setByte(11, playerbuilding.getBuildType());
					ps.setInt(12, playerbuilding.getIsUnique());
					ps.setInt(13, playerbuilding.getCanBeDestroyed());
					ps.setInt(14, playerbuilding.getIsLevelUp());
					ps.setByte(15, playerbuilding.getInCometype());
					ps.setInt(16, playerbuilding.getInComeCount());
					ps.setInt(17, playerbuilding.getOfficerId());
					ps.setString(18, playerbuilding.getOfficerInfo());
					ps.setLong(19, playerbuilding.getOccupyUserId());
					ps.setInt(20, playerbuilding.getPropsId());
					ps.setInt(21, playerbuilding.getOperatcount());
					ps.setString(22, playerbuilding.getRemark1());
					ps.setString(23, playerbuilding.getRemark2());
					ps.setString(24, playerbuilding.getRemark3());
					ps.setInt(25, playerbuilding.getY());
					ps.setTimestamp(26, playerbuilding.getChargeOutTime());
					ps.setTimestamp(27, playerbuilding.getStealTime());
					ps.setString(28, playerbuilding.getSoldierMsg());
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
	 * 保存用户建筑信息
	 * 
	 * @param playerbuilding
	 * @return
	 */
	public boolean savePlayerBuilding(PlayerBuilding playerbuilding) {
		try {
			gameWorldDAO.getJdbcTemplate().update(SQL_UPDATE_PLB,
					playerbuilding.getBuildLevel(), playerbuilding.getBuildingID(),
					playerbuilding.getUserID(), playerbuilding.getIsOccupy(),
					playerbuilding.getCategory(), playerbuilding.getX(),
					playerbuilding.getY(), playerbuilding.getPrice(),
					playerbuilding.getHonerPrice(), playerbuilding.getUpdateTime(),
					playerbuilding.getConstructionTime(),
					playerbuilding.getBuildType(), playerbuilding.getIsUnique(),
					playerbuilding.getCanBeDestroyed(),
					playerbuilding.getIsLevelUp(), playerbuilding.getInCometype(),
					playerbuilding.getInComeCount(), playerbuilding.getOfficerId(),
					playerbuilding.getOfficerInfo(),
					playerbuilding.getOccupyUserId(), playerbuilding.getPropsId(),
					playerbuilding.getOperatcount(), playerbuilding.getRemark1(),
					playerbuilding.getRemark2(), playerbuilding.getRemark3(),
					playerbuilding.getAdditionCount(),
					playerbuilding.getAdditionCountTime(),
					playerbuilding.getAddTime(), playerbuilding.getStealCount(),
					playerbuilding.getChargeOutTime(),
					playerbuilding.getStealTime(), playerbuilding.getExp(),
					playerbuilding.getOfficerTime(),playerbuilding.getSoldierMsg(),
					playerbuilding.getOldLevel(),
					playerbuilding.getInitialTimes(),
					playerbuilding.getRefreshTimes(),
					playerbuilding.getId());
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	public List<PlayerBuilding> getAllPlayerBuilding(int userId){
		try{
			List<PlayerBuilding> list = DBManager.getInstance().getWorldDAO().getJdbcTemplate().query(SQL_SELECT_ALL_PLB, playerBuildingRowMapper, userId);
		return list;
		} catch (EmptyResultDataAccessException e) {
			logger.error("no role exist with id : " + userId);
			return null;
		}
	}
}
