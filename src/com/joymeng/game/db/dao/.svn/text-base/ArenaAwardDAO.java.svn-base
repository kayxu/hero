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
import com.joymeng.game.db.row.ArenaAwardRowMapper;
import com.joymeng.game.domain.award.ArenaAward;

public class ArenaAwardDAO {

	private static final Logger logger = LoggerFactory.getLogger(ArenaAwardDAO.class);
	GameDAO gameWorldDAO;
	public ArenaAwardDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
	public static final ArenaAwardRowMapper arenaAwardRowMapper = new ArenaAwardRowMapper();
	
	/**
	 * 增加玩家竞技场奖励信息
	 */
	public static final String SQL_ADD_ARENA_AWARD = "insert into arenaaward (userId,rankId,gold,medal,goodsOrEqu,title,tipsForAward) values(?,?,?,?,?,?,?)";
	
	/**
	 * 更新玩家竞技场奖励信息
	 */
	public static final String SQL_UPDATE_ARENA_AWARD = "";
	
	/**
	 * 删除玩家竞技场奖励信息
	 */
	public static final String SQL_DELETE_ARENA_AWARD = "delete from arenaaward where id=?";
	
	/**
	 * 查询特定玩家的竞技场奖励信息
	 */
	public static final String SQL_FIND_PLAYE_ARENA_AWARDS = "select * from arenaaward where userId=? order by id desc";
	
	/**
	 * 计算特定玩家系统奖励信息数量
	 */
	public static final String SQL_COUNT_PLAYER_ARENA_AWARDS = "select count(*) from arenaaward where userId=?";
	
	/**
	 * 计算玩家的竞技场奖励数目（注意   仅为竞技场奖励数目  以title为区别）
	 */
	public static final String SQL_COUNT_PLAYER_ONLY_ARENA_AWARDS = "select count(*) from arenaaward where userId=? and title='竞技场奖励'";
	
	/**
	 *  查询玩家所有竞技场奖励（注意   仅为竞技场奖励数目    以titile为区别）
	 */
	public static final String SQL_QUERY_PLAYER_ARENA_AWARDS = "select * from arenaaward where userId=? and title='竞技场奖励' order by id desc";
	
	/**
	 * 删除玩家的所有竞技场奖励（注意   仅为竞技场奖励）
	 */
	public static final String SQL_DELETE_PLAYER_ALL_ARENA_AWARD = "delete from arenaaward where userId=? and title='竞技场奖励'";
	
	/**
	 * 增加竞技场奖励信息
	 * @param arrenaAward
	 * @return
	 */
	public int addArenaAward(final ArenaAward arenaAward){
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_ARENA_AWARD,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1,arenaAward.getUserId());
					ps.setInt(2,arenaAward.getRankId());
					ps.setInt(3, arenaAward.getGold());
					ps.setInt(4,arenaAward.getMedal());
					ps.setString(5, arenaAward.getGoodsOrEqu());
					ps.setString(6, arenaAward.getTitle());
					ps.setString(7,arenaAward.getTipsForAward());
					return ps;
				}

			}, keyHolder);

			return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 删除玩家竞技场奖品信息
	 * @param id
	 * @return
	 */
	public boolean deleteArenaAward(int id){
		gameWorldDAO.getJdbcTemplate().update(SQL_DELETE_ARENA_AWARD,id);
		return true;
	}
	
	/**
	 * 查找特定玩家的所有竞技场奖励信息
	 * @param userId
	 * @return
	 */
	public List<ArenaAward> findOfCertainPlayer(int userId){
		try {
			List<ArenaAward> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_FIND_PLAYE_ARENA_AWARDS, arenaAwardRowMapper,userId);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/**
	 * 判断玩家是否有竞技场奖励信息
	 * @param userId
	 * @return
	 */
	public boolean isPlayerHasArenaAward(int userId){
		int num = gameWorldDAO.getSimpleJdbcTemplate().queryForInt(SQL_COUNT_PLAYER_ARENA_AWARDS, userId);
		if(num > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 计算玩家的竞技场奖励数目（注意   仅为竞技场奖励数目   以titile为区别）
	 * @param userId
	 * @return
	 */
	public int countArenaAward(int userId){
		int num = gameWorldDAO.getSimpleJdbcTemplate().queryForInt(SQL_COUNT_PLAYER_ONLY_ARENA_AWARDS, userId);
		return num;	
	}
	
	
	/**
	 * 查询玩家所有竞技场奖励（注意   仅为竞技场奖励数目    以titile为区别）
	 * @param userId
	 * @return
	 */
	public List<ArenaAward> queryPlayerArenaAward(int userId){
		try {
			List<ArenaAward> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_QUERY_PLAYER_ARENA_AWARDS, arenaAwardRowMapper,userId);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}	
	}
	
	/**
	 * 删除玩家的所有竞技场奖励（注意   仅为竞技场奖励）
	 * @param userId
	 * @return
	 */
	public boolean delAllPlayerArenaAward(int userId){
		gameWorldDAO.getJdbcTemplate().update(SQL_DELETE_PLAYER_ALL_ARENA_AWARD,userId);
		return true;	
	}
	
	/**
	 * 删除数据库中玩家竞技场奖励只留最近2个奖励
	 * @param userId
	 * @return
	 */
	public boolean delPlayerArenaAwardRemainTwo(int userId){
		int awardNum = countArenaAward(userId);
		if(awardNum <= 2){
			return true;
		}
		else{
			List<ArenaAward> list = queryPlayerArenaAward(userId);
			delAllPlayerArenaAward(userId);
			for(int i=1;i>=0;i--){
				addArenaAward(list.get(i));
			}
			return true;
		}	
	}
	
	
	
	
	
}
