package com.joymeng.game.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.FightEventRowMapper;
import com.joymeng.game.domain.fight.FightEvent;

public class FightEventDAO {

//	private static final Logger logger = LoggerFactory.getLogger(FightEventDAO.class);
//	
	GameDAO gameWorldDAO;
	public FightEventDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
//	public static final FightEventRowMapper fightEventRowMapper = new FightEventRowMapper();
//	
//	public static final String SQL_ADD_FIGHT_EVENT = "insert into fightevent(userId,heroId,type,result,memo,data,time,isAttack)values(?,?,?,?,?,?,?,?)";
//	
//	/**
//	 * 查询某一个玩家的站报消息前25条记录
//	 */
//	public static final String SQL_SELECT_CURRENT_PLAYER_FIGHT_EVENT = "select * from fightevent where userid=? order by time desc limit 25";
//	
//	/**
//	 * 查询某一个玩家的某一类型的战报消息
//	 */
//	public static final String SQL_SELECT_CERTAIN_FIGHT_EVENT = "select * from fightevent where userid=? and type=? order by time desc";
//	
//	/**
//	 * 删除特定的战报消息
//	 */
//	public static final String SQL_DELETE_FIGHT_EVENT = "delete from fightevent where id=?";
//	
//	
//	/**
//	 * 清除一定天数未登录的玩家战报消息
//	 */
//	public static final String SQL_DELETE_SOME_DAYS_NOT_LOGIN_FIGHT_EVENT = "delete from fightevent where userId in (select userid from roledata  where to_days(now()) - to_days(lastLoginTime) > ?)";
//	
//	/**
//	 * 按战报id查询战斗记录
//	 */
//	public static final String SQL_SELECT_FIGHT_EVENT_BY_ID = "select * from fightevent where id=?";
//	
//	/**
//	 * 查询阶段战报
//	 */
//	public static final String SQL_SELECT_STAGE_FIGHT_EVENT = "select * from fightevent_quest";
//	
//	/**
//	 * 保存玩家战报消息
//	 * @param fightEvent
//	 */
//	public int saveFightEvent(final FightEvent fightEvent){
//		
//		try {
//			KeyHolder keyHolder = new GeneratedKeyHolder();
//			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn)
//						throws SQLException {
//					PreparedStatement ps = conn.prepareStatement(SQL_ADD_FIGHT_EVENT,
//							Statement.RETURN_GENERATED_KEYS);
//					ps.setInt(1, fightEvent.getUserId());
//					ps.setInt(2, fightEvent.getHeroId());
//					ps.setByte(3, fightEvent.getType());
//					ps.setByte(4, fightEvent.getResult());
//					ps.setString(5, fightEvent.getMemo());
//					ps.setString(6, fightEvent.getData());
//					ps.setTimestamp(7, new Timestamp(fightEvent.getFightTime()));
//					ps.setByte(8, fightEvent.getIsAttack());
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
//	
//	/**
//	 * 删除战报消息
//	 * @param id 战报id
//	 * @return
//	 */
//	public boolean removeFightEvent(int id){
//		gameWorldDAO.getJdbcTemplate().update(SQL_DELETE_FIGHT_EVENT,id);
//		logger.info("***********删除战报消息id:"+id);
//		return true;	
//	}
//	
//	/**
//	 * 清除一定天数未登录的玩家战报消息
//	 * @param intervalDays 间隔天数
//	 * @return
//	 */
//	public boolean clearSomeDaysNotLoginFightEvent(int intervalDays){
//		int rowsEffected = gameWorldDAO.getJdbcTemplate().update(SQL_DELETE_SOME_DAYS_NOT_LOGIN_FIGHT_EVENT,intervalDays);
//		logger.info("***********清除" + intervalDays + "没有登录的玩家");
//		logger.info("删除记录条数 :" + rowsEffected);
//		return true;
//	}
//	
//	/**
//	 * 按玩家userid查询玩家战报消息
//	 */
//	public List<FightEvent> getCertainFightEventByUserId(int userid){
//		try {
//			List<FightEvent> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_CURRENT_PLAYER_FIGHT_EVENT, fightEventRowMapper,userid);
//			return list;
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//		
//	}
//	
//	/**
//	 * 按玩家userid和玩家战报类型查询战报消息
//	 * @param userid int
//	 * @param type int
//	 * @return
//	 */
//	public List<FightEvent> getCertainFightEventByUserIdAndType(int userid,int type){
//		try {
//			List<FightEvent> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_CERTAIN_FIGHT_EVENT, fightEventRowMapper,userid);
//			return list;
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}
//	
//	/**
//	 * 按战报id获取玩家战斗事件，本查询方法供观战操作调用
//	 */
//	public FightEvent getFightEventById(int id){
//		try {
//			FightEvent fightEvent = gameWorldDAO.getSimpleJdbcTemplate().queryForObject(SQL_SELECT_FIGHT_EVENT_BY_ID, fightEventRowMapper,id);
//			return fightEvent;
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}
//	
//	/**
//	 * 获取阶段战报
//	 * @return
//	 */
//	public FightEvent getStageFightEvent(){
//		try {
//			List<FightEvent> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_STAGE_FIGHT_EVENT, fightEventRowMapper);
//			return list.get(0);
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//	}
	
}
