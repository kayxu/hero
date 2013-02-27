package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.core.base.domain.Server;

public class ServerRowMapper implements ParameterizedRowMapper<Server> {

	@Override
	public Server mapRow(ResultSet rs, int rowNum) throws SQLException {
		Server server = new Server();
		server.setId(rs.getByte("id"));
		server.setInstanceId(rs.getInt("instanceId"));
		server.setName(rs.getString("name"));
		server.setType(rs.getByte("type"));
		server.setState(rs.getByte("state"));
		return server;
	}

}
