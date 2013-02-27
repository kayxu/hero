package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.game.domain.rank.RankHero;

public class RankHeroRowMapper implements ParameterizedRowMapper<RankHero> {

	@Override
	public RankHero mapRow(ResultSet rs, int arg1) throws SQLException {
		RankHero rh = new RankHero();
		rh.setHeroName(rs.getString("heroName"));
		rh.setLevel(rs.getInt("level"));
		rh.setValue(rs.getInt("value"));
		rh.setPlayerName(rs.getString("playerName"));
		rh.setPlayerId(rs.getLong("playerId"));
		rh.setColor(rs.getByte("color"));
		return rh;
	}

}
