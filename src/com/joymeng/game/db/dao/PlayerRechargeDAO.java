package com.joymeng.game.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.PlayerRechargeRowMapper;
import com.joymeng.game.domain.recharge.PlayerRecharge;

public class PlayerRechargeDAO {

	private static final Logger logger = LoggerFactory.getLogger(PlayerRechargeDAO.class);
	
	public static final PlayerRechargeRowMapper playerRechargeRowMapper = new PlayerRechargeRowMapper();
	
	GameDAO gameWorldDAO;
	public PlayerRechargeDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
	/**
	 * 增加玩家充值信息
	 */
	public static final String SQL_ADD_PLAYER_RECHAGE = "insert into playerrecharge(userid,alreadyRechargeVal,stageAndPackageIds)values(?,?,?)";
	
	/**
	 * 更新玩家充值信息
	 */
	public static final String SQL_UPDATE_PLAYER_RECHAGE = "update playerrecharge set alreadyRechargeVal=?, stageAndPackageIds=? where userid=?";
	
	/**
	 * 查找特定玩家充值信息
	 */
	public static final String SQL_FIND_CERTAIN_PLAYER_RECHAGE = "select * from playerrecharge where userid=?";
	
	/**
	 * 增加玩家充值信息
	 * @param playerRecharge
	 * @return
	 */
	public boolean addPlayerRecharge(final PlayerRecharge playerRecharge){
		try {
			//KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_PLAYER_RECHAGE,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, playerRecharge.getUserid());
					ps.setInt(2,playerRecharge.getAlreadyRechargeVal());
					ps.setString(3, playerRecharge.getStageAndPackageIds());
					return ps;
				}
			});

			return true;
			//return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * 更新玩家充值信息
	 * @param playerRecharge
	 * @return
	 */
	public boolean updatePlayerRecharge(final PlayerRecharge playerRecharge){
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_PLAYER_RECHAGE,
				playerRecharge.getAlreadyRechargeVal(),
				playerRecharge.getStageAndPackageIds(),
				playerRecharge.getUserid());
		return true;
	}
	
	/**
	 * 查询特定玩家的充值信息
	 * @param userid
	 * @return
	 */
	public PlayerRecharge findCertainPlayerRecharge(int userid){
		try {
			PlayerRecharge playerRecharge = gameWorldDAO.getSimpleJdbcTemplate().queryForObject(
					SQL_FIND_CERTAIN_PLAYER_RECHAGE, playerRechargeRowMapper, userid);
			return playerRecharge;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
}
