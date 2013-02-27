package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.game.domain.fight.mod.Arena;

public class ArenaRowMapper implements ParameterizedRowMapper<Arena> {

	@Override
	public Arena mapRow(ResultSet rs, int arg1) throws SQLException {
		Arena arena=new Arena();
		arena.setId(rs.getShort("id"));
		arena.setUserId(rs.getInt("userId"));
		arena.setHeroId(rs.getInt("heroId"));
		arena.setUserName(rs.getString("userName"));
		arena.setHeroInfo(rs.getString("heroInfo"));
		arena.setSoldier(rs.getString("soldier"));
		return arena;
	}

}
