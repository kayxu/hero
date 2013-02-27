package com.joymeng.game.db.row;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.joymeng.game.domain.card.PlayerCards;

public class PlayerCardsRowMapper implements ParameterizedRowMapper<PlayerCards>{
	
	private static final Logger logger = LoggerFactory.getLogger(PlayerCardsRowMapper.class);

	@Override
	public PlayerCards mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		PlayerCards playerCards = new PlayerCards();
		playerCards.setUserid(rs.getInt("userid"));
		playerCards.setFaces(rs.getString("faces"));
		playerCards.setIndexes(rs.getString("indexes"));
		playerCards.setWhat(rs.getString("what"));
		playerCards.setValues(rs.getString("val"));
		playerCards.setFlipChance(rs.getInt("flipChance"));
		playerCards.setRotateChance(rs.getInt("rotateChance"));
		playerCards.setTurnsForChance(rs.getInt("turnsForChance"));
		
		return playerCards;
	}

}
