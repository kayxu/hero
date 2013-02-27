package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.joymeng.game.domain.award.ArenaAward;

public class ArenaAwardRowMapper implements ParameterizedRowMapper<ArenaAward> {

	@Override
	public ArenaAward mapRow(ResultSet rs, int rowNum) throws SQLException {
		ArenaAward arenaAward = new ArenaAward();
		arenaAward.setId(rs.getInt("id"));
		arenaAward.setUserId(rs.getInt("userId"));
		arenaAward.setRankId(rs.getInt("rankId"));
		arenaAward.setGold(rs.getInt("gold"));
		arenaAward.setMedal(rs.getInt("medal"));
		arenaAward.setGoodsOrEqu(rs.getString("goodsOrEqu"));
		arenaAward.setTitle(rs.getString("title"));
		arenaAward.setTipsForAward(rs.getString("tipsForAward"));
		return arenaAward;
	}

}
