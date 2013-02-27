package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.game.domain.role.UserAccount;


public class UserAccountMapper implements ParameterizedRowMapper<UserAccount> {

	@Override
	public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserAccount user = new UserAccount();
		user.setUserId(rs.getLong("userid"));
		user.setUserName(rs.getString("username"));
		user.setPassward(rs.getString("passward"));
		user.setCreateTime(rs.getTime("createtime"));
		return user;
	}

}
