package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.domain.hero.PlayerHero;

public class PlayerHeroRowMapper  implements ParameterizedRowMapper<PlayerHero> {

	@Override
	public PlayerHero mapRow(ResultSet rs, int arg1) throws SQLException {
		PlayerHero playerhero = new PlayerHero();
		playerhero.setId(rs.getInt("id"));
		playerhero.setUserId(rs.getInt("userId"));
		playerhero.setName(rs.getString("name"));
		playerhero.setIcon(rs.getString("icon"));
		playerhero.setLevel(rs.getInt("level"));
		playerhero.setExp(rs.getInt("exp"));
		playerhero.setSex(rs.getByte("sex"));
		playerhero.setAttack(rs.getInt("attack"));
		playerhero.setDefence(rs.getInt("defence"));
		playerhero.setHp(rs.getInt("hp"));
		playerhero.setMaxHp(rs.getInt("maxHp"));
//		playerhero.setMemo(rs.getString("memo"));
		playerhero.setAttackAdd(rs.getInt("attackAdd"));
		playerhero.setDefenceAdd(rs.getInt("defenceAdd"));
		playerhero.setHpAdd(rs.getInt("hpAdd"));
		playerhero.setColor(rs.getByte("color"));
		playerhero.setSoldierNum(rs.getInt("soldierNum"));
		playerhero.setWeapon(rs.getInt("weapon"));
		playerhero.setArmour(rs.getInt("armour"));
		playerhero.setHelmet(rs.getInt("helmet"));
		playerhero.setHorse(rs.getInt("horse"));
		playerhero.setSkillNum(rs.getByte("skillNum"));
		playerhero.setSkill(rs.getString("skill"));
		playerhero.setBuildId(rs.getInt("buildId"));
//		playerhero.setStatus(rs.getByte("status"));
		playerhero.setInfo(rs.getByte("status"), rs.getString("memo"), rs.getString("soldier"));
		playerhero.setInitAttack(rs.getInt("initAttack"));
		playerhero.setInitDefence(rs.getInt("initDefence"));
		playerhero.setInitHp(rs.getInt("initHp"));
//		playerhero.setTrainEndTime(rs.getLong("trainEndTime"));
		try {
			if (rs.getTimestamp("trainEndTime") == null) {
				playerhero.setTrainEndTime(TimeUtils.nowLong());
			} else {
				playerhero.setTrainEndTime(rs.getTimestamp("trainEndTime").getTime());
			}
		} catch (Exception e) {
			playerhero.setTrainEndTime(TimeUtils.nowLong());
		}
		playerhero.setTrainType(rs.getByte("trainType"));
		playerhero.setTrainIndex(rs.getByte("trainIndex"));
//		playerhero.setSoldier(rs.getString("soldier"));
		return playerhero;
	}

}
