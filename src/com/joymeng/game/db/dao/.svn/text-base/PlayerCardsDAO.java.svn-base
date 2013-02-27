package com.joymeng.game.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.PlayerCardsRowMapper;
import com.joymeng.game.domain.card.PlayerCards;
public class PlayerCardsDAO {

	private static final Logger logger = LoggerFactory.getLogger(PlayerCardsDAO.class);
	
	public static final PlayerCardsRowMapper playerCardsRowMapper = new PlayerCardsRowMapper();
	
	GameDAO gameWorldDAO;
	public PlayerCardsDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
	/**
	 * 增加玩家牌新的牌局
	 */
	public static final String SQL_ADD_PLAYER_CARDS = "insert into playercards (userid,faces,indexes,what,val,flipChance,rotateChance,turnsForChance)values(?,?,?,?,?,?,?,?)";
	
	/**
	 * 更新玩家牌局
	 */
	public static final String SQL_UPDATE_PLAYER_CARDS = "update playercards set faces=?, indexes=?, what=?, val=?, flipChance=?, rotateChance=?, turnsForChance=? where userid=?";
	
	/**
	 * 查询特定玩家牌局
	 */
	public static final String SQL_FIND_CERTAIN_PLAYER_CARDS = "select * from playercards where userid=?";
	
	/**
	 * 删除特定玩家牌局
	 */
	public static final String SQL_DELET_PLAYER_CARDS = "delete from playercards where userid=?";
	
	/**
	 * 增加玩家新的牌局
	 * @param usernameStatus
	 * @return
	 */
	public boolean addPlayerCards(final PlayerCards playerCards){
		try {
			//KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_PLAYER_CARDS,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1, playerCards.getUserid());
					ps.setString(2,playerCards.getFaces());
					ps.setString(3,playerCards.getIndexes());
					ps.setString(4, playerCards.getWhat());
					ps.setString(5, playerCards.getValues());
					ps.setInt(6, playerCards.getFlipChance());
					ps.setInt(7, playerCards.getRotateChance());
					ps.setInt(8, playerCards.getTurnsForChance());
					return ps;
				}


			});

			return true;
			//return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
		
	}
	
	
	/**
	 * 更新玩家牌局
	 * @param playerCards PlayerCards
	 * @return
	 */
	public boolean updatePlayerCards(final PlayerCards playerCards){
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_PLAYER_CARDS,
				playerCards.getFaces(),
				playerCards.getIndexes(),
				playerCards.getWhat(),
				playerCards.getValues(),
				playerCards.getFlipChance(),
				playerCards.getRotateChance(),
				playerCards.getTurnsForChance(),
				playerCards.getUserid());
		return true;
		
	}
	
	
	/**
	 * 找到特定玩家的牌局
	 * @return PlayerCards
	 */
	public PlayerCards findCertainPlayerCards(int userid){
		try {
			PlayerCards playerCards = gameWorldDAO.getSimpleJdbcTemplate().queryForObject(
					SQL_FIND_CERTAIN_PLAYER_CARDS, playerCardsRowMapper, userid);
			return playerCards;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	/**
	 * 删除玩家牌局信息
	 * @param userid
	 * @return
	 */
	public boolean deletePlayerCards(int userid){
		gameWorldDAO.getJdbcTemplate().update(SQL_DELET_PLAYER_CARDS,userid);
		logger.info("***********删除玩家牌局信息:"+userid);
		return true;	
	}
	
	
}
