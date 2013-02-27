package com.joymeng.game.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.UsernameStatusRowMapper;
import com.joymeng.game.domain.role.UsernameStatus;

public class UsernameStatusDAO {

	private static final Logger logger = LoggerFactory.getLogger(UsernameStatusDAO.class);
	GameDAO gameWorldDAO;
	public UsernameStatusDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	private static final UsernameStatusRowMapper usernameStatusRowMapper = new UsernameStatusRowMapper(); 
	public static final String SQL_ADD_USERNAME_STATUS="insert into usernamestatus (id,fullname,sex,status)values(?,?,?,?)";
	public static final String SQL_SELECT_AVALIABLE_MALE_USERNAME_STATUS = "select * from usernamestatus where sex=0 and status=1";
	public static final String SQL_SELECT_AVALIABLE_FEMALE_USERNAME_STATUS = "select * from usernamestatus where sex=1 and status=1";
	public static final String SQL_SELECT_ALL = "select * from usernamestatus";
	
	/**
	 * 1-可用,0-不可用
	 */
	public static final String SQL_SELECT_CERTAIN_NUM_MALE = "select * from usernamestatus where sex=1 and status=1 limit ?";
	public static final String SQL_SELECT_CERTAIN_NUM_FEMALE = "select * from usernamestatus where sex=0 and status=1 limit ?";
	public static final String SQL_SAVE_USERNAME_STATUS = "update usernamestatus set fullname=?, sex=?, status=? where id=?";
	
	//清空玩家随机名表
	public static final String SQL_CLEAR_USERNAME_STATUS = "truncate table usernamestatus";
	
	//从数据库中随机出LIMIT数量的连续的男玩家名称
	public static final String SQL_RANDOM_CERTAIN_NUM_MALE = "SELECT * FROM usernamestatus AS t1"
			+ " JOIN (SELECT ROUND(RAND() * (((SELECT MAX(id) FROM usernamestatus)-(SELECT MIN(id) FROM usernamestatus))/2)+(SELECT MIN(id) FROM usernamestatus)) AS id) AS t2"
			+ " WHERE t1.id >= t2.id AND t1.status=1 AND t1.sex=1"
			+ "ORDER BY t1.id LIMIT ?";
	
	public static final String SQL_RANDOM_CERTAIN_NUM_FEMALE = "SELECT * FROM usernamestatus AS t1"
			+ "JOIN (SELECT ROUND((SELECT MAX(id) FROM usernamestatus) - RAND() * (((SELECT MAX(id) FROM usernamestatus)-(SELECT MIN(id) FROM usernamestatus))/2)) AS id) AS t2"
			+ "WHERE t1.id >= t2.id AND t1.status=1 AND t1.sex=0 "
			+ "ORDER BY t1.id LIMIT ?";
	
	/**
	 * 增加UsernameStatus
	 * @param usernameStatus
	 * @return
	 */
	public void addUsernameStatus(final UsernameStatus usernameStatus){
		int i = 0;
		try {
//			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_USERNAME_STATUS,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, usernameStatus.getId());
					ps.setString(2,usernameStatus.getFullName());
					ps.setInt(3,usernameStatus.getSex());
					ps.setInt(4,usernameStatus.getStatus());
					return ps;
				}

			});

			//return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			i++;
			logger.info("until repeat：" + i);
			logger.info("" + usernameStatus.getId());
			logger.info(usernameStatus.getFullName());
			ex.printStackTrace();
		}
		//return 0;
		
	}
	
	/**
	 * 查询所有可用的男性玩家名称
	 * @return
	 */
	public List<UsernameStatus> getAllAvaliableMaleUsernameStatus(){
		try {
			List<UsernameStatus> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_AVALIABLE_MALE_USERNAME_STATUS, usernameStatusRowMapper);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 查询特定数量的可用男性玩家名称
	 * @param num
	 * @return
	 */
	public List<UsernameStatus> getCertainNumAvaliableMaleUsernameStatus(int num){
		try {
			List<UsernameStatus> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_CERTAIN_NUM_MALE, usernameStatusRowMapper,num);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	
	/**
	 * 查询特定数量的可用女性玩家名称
	 * @param num
	 * @return
	 */
	public List<UsernameStatus> getCertainNumAvaliableFemaleUsernameStatus(int num){
		try {
			List<UsernameStatus> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_CERTAIN_NUM_FEMALE, usernameStatusRowMapper,num);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	/**
	 * 查询所有可用的女性玩家名称
	 * @return
	 */
	public List<UsernameStatus> getAllAvaliableFemaleUsernameStatus(){
		try {
			List<UsernameStatus> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_AVALIABLE_FEMALE_USERNAME_STATUS, usernameStatusRowMapper);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 查询所有玩家名称信息
	 * @return
	 */
	public List<UsernameStatus> getAllUsernameStatus(){
		try {
			List<UsernameStatus> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_ALL, usernameStatusRowMapper);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public boolean saveUsernameStatus(UsernameStatus u){
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_SAVE_USERNAME_STATUS,
				u.getFullName(),
				u.getSex(),
				u.getStatus(),
				u.getId());
		return true;
	}
	
	public boolean clearUsernameStatus(){
		gameWorldDAO.getJdbcTemplate().update(SQL_CLEAR_USERNAME_STATUS);
		logger.info("***********正在清空玩家可用名表");
		return true;	
	}
	
	
}
