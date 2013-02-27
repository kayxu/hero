package com.joymeng.game.db.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.RankHeroRowMapper;
import com.joymeng.game.domain.rank.RankHero;

public class PlayerHeroDAO {

	private static final Logger logger = LoggerFactory.getLogger(PlayerHeroDAO.class);
	GameDAO gameWorldDAO;
	public PlayerHeroDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
	public static final RankHeroRowMapper rankHeroRowMapper = new RankHeroRowMapper();
	/**
	 * 查询攻击力排行榜
	 */
	public static final String SQL_SELECT_RANK_HERO_ATTACK = "select playerhero.name as heroName,playerhero.level as level,playerhero.attackTotal as value,roledata.name as playerName,roledata.userid as playerId,playerhero.color as color from playerhero,roledata where roledata.userid=playerhero.userid order by playerhero.attackTotal desc limit ?";
	
	/**
	 * 查询防御力排行榜
	 */
	public static final String SQL_SELECT_RANK_HERO_DEFENCE = "select playerhero.name as heroName,playerhero.level as level,playerhero.defenceTotal as value,roledata.name as playerName,roledata.userid as playerId,playerhero.color as color from playerhero,roledata where roledata.userid=playerhero.userid order by playerhero.defenceTotal desc limit ?";
	
	/**
	 * 查询生命值排行榜
	 */
	public static final String SQL_SELECT_RANK_HERO_HP = "select playerhero.name as heroName,playerhero.level as level,playerhero.hpTotal as value,roledata.name as playerName,roledata.userid as playerId,playerhero.color as color from playerhero,roledata where roledata.userid=playerhero.userid order by playerhero.hpTotal desc limit ?";
	
	/**
	 * 按武将攻击力排行
	 * @param num int 前num个
	 * @return
	 * @throws DataAccessException
	 */
	public List<RankHero> getRankByHeroAttack(int num)throws DataAccessException{
		try {
			return gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_RANK_HERO_ATTACK,rankHeroRowMapper,num);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 按武将防御力排行
	 * @param num int 前num个
	 * @return
	 * @throws DataAccessException
	 */
	public List<RankHero> getRankByHeroDefence(int num) throws DataAccessException{
		try {
			return gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_RANK_HERO_DEFENCE,rankHeroRowMapper,num);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 按武将生命值排行
	 * @param num int 前num个
	 * @return
	 * @throws DataAccessException
	 */
	public List<RankHero> getRankByHeroHP(int num) throws DataAccessException{
		try {
			return gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_RANK_HERO_HP,rankHeroRowMapper,num);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
