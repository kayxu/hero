package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.game.domain.building.PlayerBuilding;

public class PlayerBuildingRowMapper  implements ParameterizedRowMapper<PlayerBuilding>{

	@Override
	public PlayerBuilding mapRow(ResultSet rs, int arg1) throws SQLException {
		// -->>>>>>>装载对象
		PlayerBuilding playerbuilding = new PlayerBuilding();
		// level 默认为一
		playerbuilding.setId((Integer) rs.getInt("id"));
		playerbuilding.setBuildLevel(((Integer) rs.getInt("buildLevel"))
				.shortValue());
		playerbuilding.setBuildingID(rs.getInt("buildingID"));
		playerbuilding.setUserID(rs.getInt("userID"));
		playerbuilding.setIsOccupy(rs.getInt("isOccupy"));
		playerbuilding.setCategory(rs.getInt("category"));
		playerbuilding.setX(((Integer) rs.getInt("x")).shortValue());
		playerbuilding.setY(((Integer) rs.getInt("y")).shortValue());
		playerbuilding.setPrice(rs.getInt("price"));
		playerbuilding.setHonerPrice(rs.getInt("honerPrice"));
		playerbuilding.setUpdateTime(rs.getTimestamp("updateTime"));
		playerbuilding.setInComeCount(rs.getInt("inComeCount"));
		playerbuilding.setBuildType(((Integer) rs.getInt("buildType"))
				.byteValue());
		playerbuilding.setConstructionTime(rs
				.getTimestamp("constructionTime"));
		playerbuilding.setIsUnique(rs.getInt("isUnique"));
		playerbuilding.setCanBeDestroyed(rs.getInt("canBeDestroyed"));
		playerbuilding.setIsLevelUp(rs.getInt("isLevelUp"));
		playerbuilding.setInCometype(((Integer) rs.getInt("inCometype"))
				.byteValue());
		playerbuilding.setOfficerId(rs.getInt("officerId"));
		playerbuilding.setOfficerInfo(rs.getString("officerInfo"));
		playerbuilding.setOccupyUserId(rs.getLong("occupyUserId"));
		playerbuilding.setPropsId( rs.getInt("propsId"));
		playerbuilding.setOperatcount(rs.getInt("operatcount"));
		playerbuilding.setAdditionCount(rs.getInt("additionCount"));
		playerbuilding.setAdditionCountTime( rs
				.getInt("additionCountTime"));
		playerbuilding.setChargeOutTime(rs.getTimestamp("chargeOutTime"));
		playerbuilding.setStealTime(rs.getTimestamp("stealTime"));
		if (playerbuilding.getAdditionCountTime() != 0) {
			playerbuilding.setAddTime(rs.getTimestamp("addTime"));
		}
		playerbuilding.setSoldierMsg(rs.getString("soldierMsg"));
		playerbuilding.setRemark1(rs.getString("remark1"));
		playerbuilding.setRemark2(rs.getString("remark2"));
		playerbuilding.setRemark3(rs.getString("remark3"));
		playerbuilding.setOfficerTime(rs.getTimestamp("officerTime"));
		playerbuilding.setOldLevel( rs.getInt("oldLevel"));
		playerbuilding.setInitialTimes( rs.getInt("initialTimes"));
		playerbuilding.setRefreshTimes( rs.getInt("refreshTimes"));
		playerbuilding.setExp( rs.getInt("exp"));
		return playerbuilding;
	}

}
