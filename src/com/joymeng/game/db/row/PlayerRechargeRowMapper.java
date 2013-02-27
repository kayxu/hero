package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.game.domain.recharge.PlayerRecharge;

public class PlayerRechargeRowMapper implements ParameterizedRowMapper<PlayerRecharge> {
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerRechargeRowMapper.class);

	@Override
	public PlayerRecharge mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		PlayerRecharge playerRecharge = new PlayerRecharge();
		playerRecharge.setUserid(rs.getInt("userid"));
		playerRecharge.setAlreadyRechargeVal(rs.getInt("alreadyRechargeVal"));
		playerRecharge.setStageAndPackageIds(rs.getString("stageAndPackageIds"));
		return playerRecharge;
	}

}
