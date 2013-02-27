package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.game.domain.role.UsernameStatus;

public class UsernameStatusRowMapper implements ParameterizedRowMapper<UsernameStatus> {

	@Override
	public UsernameStatus mapRow(ResultSet rs, int arg1) throws SQLException {
		UsernameStatus u = new UsernameStatus();
		u.setId(rs.getInt("id"));
		u.setFullName(rs.getString("fullname"));
		u.setSex(rs.getInt("sex"));
		u.setStatus(rs.getInt("status"));
		return u;
	}

}
