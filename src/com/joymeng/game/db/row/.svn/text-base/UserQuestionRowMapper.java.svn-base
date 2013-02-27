package com.joymeng.game.db.row;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.card.PlayerCards;
import com.joymeng.game.domain.question.UserQuestion;

public class UserQuestionRowMapper implements ParameterizedRowMapper<UserQuestion>{

	private static final Logger logger = LoggerFactory.getLogger(UserQuestionRowMapper.class);

	@Override
	public UserQuestion mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		UserQuestion userQuestion = new UserQuestion();
		userQuestion.setId(rs.getInt("id"));
		userQuestion.setUserid(rs.getLong("userid"));
		userQuestion.setContent(rs.getString("content"));
		userQuestion.setCreateTime(rs.getString("createTime"));
		return userQuestion;
	}
	
	
}
