package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.fight.mod.Ladder;
import com.joymeng.game.domain.fight.mod.LadderInfo;
import com.joymeng.game.domain.role.RoleData;

public class RoleDataRowMapper implements ParameterizedRowMapper<RoleData> {
	private static final Logger logger = LoggerFactory
			.getLogger(RoleDataRowMapper.class);

	@Override
	public RoleData mapRow(ResultSet rs, int rowNum) throws SQLException {
		RoleData roledata = new RoleData();
//		roledata.setId(rs.getInt("id"));
		roledata.setName(rs.getString("name"));
		roledata.setUserid(rs.getLong("userid"));
		roledata.setSex(rs.getByte("sex"));
		roledata.setFaction(rs.getByte("faction"));
		roledata.setLevel(rs.getShort("level"));
		roledata.setExp(rs.getInt("exp"));
		roledata.setGameMoney(rs.getInt("gameMoney"));
		roledata.setJoyMoney(rs.getInt("joyMoney"));
		roledata.setPackCapacity(rs.getShort("packCapacity"));
		roledata.setStorage(rs.getString("storage"));
		roledata.setLastLoginTime(rs.getLong("lastLoginTime"));
		roledata.setOnlineTime(rs.getInt("onlineTime"));
//		roledata.setCreateTime(rs.getLong("createTime"));
		roledata.setLeaveTime(rs.getLong("leaveTime"));
//		roledata.setActiveEndTime(rs.getLong("activeEndTime"));
		roledata.setIntradayOnlineTime(rs.getInt("intradayOnlineTime"));
		roledata.setVip(rs.getString("vip"));
		roledata.setFriends(rs.getString("friends"));
		roledata.setEnemys(rs.getString("enemys"));
//		if(rs.getString("note")==null){
//			roledata.setNote("");
//		}else{
			roledata.setNote(rs.getString("note"));
//		}
		roledata.setCamp(rs.getString("camp"));
		roledata.setCampId(rs.getInt("campId"));
		roledata.setAward(rs.getInt("award"));
		roledata.setCityLevel(rs.getInt("cityLevel"));
		roledata.setCredit(rs.getInt("credit"));
		roledata.setAttriCommander(rs.getInt("attriCommander"));
		roledata.setAttriPolitics(rs.getInt("attriPolitics"));
		roledata.setAttriCharm(rs.getInt("attriCharm"));
		roledata.setAttriCapacity(rs.getInt("attriCapacity"));
		roledata.setNativeId(rs.getInt("nativeId"));
		roledata.setIsChangName(rs.getByte("isChangeName"));
		roledata.setScore(rs.getInt("score"));
		roledata.setChip(rs.getInt("chip"));
		roledata.setLadderMax(rs.getByte("ladderMax"));
		roledata.setArenaId(rs.getInt("arenaId"));
		roledata.setArenaKill(rs.getInt("arenaKill"));
		roledata.setHeroRefresh1(rs.getInt("heroRefresh1"));
		roledata.setHeroRefresh2(rs.getInt("heroRefresh2"));
		if(rs.getString("playerCells")==null){
			roledata.setPlayerCells("");
		}else{
			roledata.setPlayerCells(rs.getString("playerCells"));
		}
		try {
			if (rs.getTimestamp("arenaTime") == null) {
				roledata.setArenaTime(TimeUtils.nowLong());
			} else {
				roledata.setArenaTime(rs.getTimestamp("arenaTime").getTime());
			}
		} catch (Exception e) {
			logger.error(
					"arenaTime is error,can not be [0000-00-00 00:00:00] of format.",
					e);
			roledata.setArenaTime(TimeUtils.nowLong());
		}
		try {
			if (rs.getTimestamp("createTime") == null) {
				roledata.setCreateTime(TimeUtils.nowLong());
			} else {
				roledata.setCreateTime(rs.getTimestamp("createTime").getTime());
			}
		} catch (Exception e) {
			logger.error(
					"createTime is error,can not be [0000-00-00 00:00:00] of format.",
					e);
			roledata.setCreateTime(TimeUtils.nowLong());
		}
		try {
			if (rs.getTimestamp("leaveTime") == null) {
				roledata.setLeaveTime(TimeUtils.nowLong());
			} else {
				roledata.setLeaveTime(rs.getTimestamp("leaveTime").getTime());
			}
			
		} catch (Exception e) {
			logger.error(
					"leaveTime is error,can not be [0000-00-00 00:00:00] of format.",
					e);
			roledata.setLeaveTime(TimeUtils.nowLong());
		}
		try {
			if (rs.getTimestamp("lastLoginTime") == null) {
				roledata.setLastLoginTime(TimeUtils.nowLong());
			} else {
				roledata.setLastLoginTime(rs.getTimestamp("lastLoginTime").getTime());
			}
		} catch (Exception e) {
			logger.error(
					"leaveTime is error,can not be [0000-00-00 00:00:00] of format.",
					e);
			roledata.setCreateTime(TimeUtils.nowLong());
		}
		try {
			if (rs.getTimestamp("activeEndTime") == null) {
				roledata.setCreateTime(TimeUtils.nowLong());
			} else {
				roledata.setCreateTime(rs.getTimestamp("activeEndTime").getTime());
			}
		} catch (Exception e) {
			logger.error(
					"activeEndTime is error,can not be [0000-00-00 00:00:00] of format.",
					e);
			roledata.setCreateTime(TimeUtils.nowLong());
		}
		roledata.setNativeId(rs.getInt("nativeId"));//县城
		if(rs.getString("ladder")==null||rs.getString("ladder").equals("")){
			roledata.setLadder(	new LadderInfo("").toStr());
		}else{
			roledata.setLadder(rs.getString("ladder"));
		}
		roledata.setLadderId(rs.getByte("ladderId"));
		roledata.setArcherEqu(rs.getInt("archerEqu"));
		roledata.setInfantryEqu(rs.getInt("infantryEqu"));
		roledata.setCavalryEqu(rs.getInt("cavalryEqu"));
		roledata.setSpecialArms(rs.getInt("specialArms"));
		roledata.setCityNum(rs.getByte("cityNum"));
		roledata.setMilitaryMedals(rs.getInt("militaryMedals"));
		roledata.setAcceptedQuests(rs.getString("acceptedQuests"));
		roledata.setCompletedQuests(rs.getString("completedQuests"));
		roledata.setDailyQuests(rs.getString("dailyQuests"));
		roledata.setAchieve(rs.getInt("achieve"));
		roledata.setTitle(rs.getByte("title"));
		roledata.setFightInfo(rs.getString("fightInfo"));
		roledata.setBoxNum(rs.getByte("boxNum"));
		roledata.setSelectedAwardIdString(rs.getString("selectedAwardIdString"));
		roledata.setBeAwardedIdString(rs.getString("beAwardedIdString"));
		roledata.setNewBag(rs.getByte("newBag"));
		roledata.setLastPostQuestionTime(rs.getLong("lastPostQuestionTime"));
		try {
			if (rs.getTimestamp("lastSignTime") == null) {
				roledata.setLastSignTime(0);
			} else {
				roledata.setLastSignTime(rs.getTimestamp("lastSignTime").getTime());
			}
		} catch (Exception e) {
			logger.error(
					"lastSignTime is error,can not be [0000-00-00 00:00:00] of format.",
					e);
		}
//		logger.info("======" + roledata.getLastSignTime());
		roledata.setCanSpeak(rs.getByte("canSpeak"));
		roledata.setCanLogin(rs.getByte("canLogin"));
		roledata.setSignNum(rs.getInt("signNum"));
		roledata.setIsSigned(rs.getByte("isSigned"));
		
		return roledata;
	}

}
