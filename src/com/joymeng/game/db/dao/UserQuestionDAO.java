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

import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.UserQuestionRowMapper;
import com.joymeng.game.domain.question.UserQuestion;

public class UserQuestionDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserQuestionDAO.class);
	GameDAO gameWorldDAO;
	public UserQuestionDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
	private static final UserQuestionRowMapper userQuestionRowMapper = new UserQuestionRowMapper();
	
	/**
	 * 增加玩家联系客服问题
	 */
	public static final String SQL_ADD_USER_QUESTION = "insert into userquestion(userid,content) values(?,?) ";
	
	/**
	 * 删除特定id的联系客服问题
	 */
	public static final String SQL_REMOVE_USER_QUESTION = "delete from userquestion where id=?";
	
	/**
	 * 查询联系客服问题总数
	 */
	public static final String SQL_COUNT_USER_QUESTION = "select count(*) from userquestion";
	
	/**
	 * 分页查询联系客服问题
	 */
	public static final String SQL_QUERY_USER_QUESTION_BY_PAGE = "select * from userquestion limit ?,?";
	public static final String SQL_QUERY_USER_QUESTION_BY_TIME="SELECT * FROM userquestion WHERE createTime >= ? AND createTime <= ? ORDER BY createTime DESC LIMIT 0, 1000";
	
	/**
	 * 增加玩家联系客服问题
	 * @param usernameStatus
	 * @return
	 */
	public boolean addUserQuestion(final UserQuestion userQuestion){
		try {
			//KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_USER_QUESTION,
							Statement.RETURN_GENERATED_KEYS);
					ps.setLong(1, userQuestion.getUserid());
					ps.setString(2,userQuestion.getContent());
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
	 * 删除玩家联系客服信息
	 * @param userid
	 * @return
	 */
	public boolean removeUserQuestion(int id){
		gameWorldDAO.getJdbcTemplate().update(SQL_REMOVE_USER_QUESTION,id);
		logger.info("***********删除玩家联系客服问题信息:"+id);
		return true;	
	}
	
	/**
	 * 计算联系客服问题记录总条数
	 * @return int 记录总条数
	 */
	public int countUserQuestion(){
		int num = gameWorldDAO.getSimpleJdbcTemplate().queryForInt(SQL_COUNT_USER_QUESTION);
		return num;	
		
	}
	
	
	/**
	 * 分页查询玩家联系客服问题
	 * @param fromIndex 起始下标
	 * @param pageSize 每个页面记录条数
	 * @return
	 */
	public List<UserQuestion> queryUserQuestionByPage(int fromIndex,int pageSize){
		try {
			List<UserQuestion> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_QUERY_USER_QUESTION_BY_PAGE, userQuestionRowMapper, fromIndex,pageSize);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public List<UserQuestion> queryUserQuestionByTime(String start,String end){
		try {
			List<UserQuestion> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_QUERY_USER_QUESTION_BY_TIME, userQuestionRowMapper, start,end);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	
	
	
	
	
	
}
