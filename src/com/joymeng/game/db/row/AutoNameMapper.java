package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.core.utils.AotuName;

public class AutoNameMapper implements ParameterizedRowMapper<AotuName> {

	@Override
	public AotuName mapRow(ResultSet rs, int rowNum) throws SQLException {
		AotuName autoName = new AotuName();
		autoName.setName(rs.getString("name"));
		autoName.setType(rs.getByte("type"));
		autoName.setId(rs.getInt("id"));
		return autoName;
	}

}
