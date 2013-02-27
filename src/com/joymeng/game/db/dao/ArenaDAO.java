package com.joymeng.game.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.GameServerApp;
import com.joymeng.game.db.GameDAO;
import com.joymeng.game.db.row.ArenaRowMapper;
import com.joymeng.game.domain.fight.mod.Arena;

/**
 * 竞技场
 * @author admin
 * @date 2012-6-27
 * TODO
 */
public class ArenaDAO{
	private static final Logger logger = LoggerFactory
			.getLogger(ArenaDAO.class);
	GameDAO gameWorldDAO;
	public ArenaDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
//	update arena set userId=0,heroId=0,userName="",heroInfo="",soldier=""
	private static final ArenaRowMapper arenaRowMapper=new ArenaRowMapper();
	//arena
		public static final String SQL_ADD_ARENA="insert into arena (userId,heroId,userName,heroInfo,soldier,id,serverId,time)values(?,?,?,?,?,?,?,?)";
		public static final String SQL_UPDATE_ARENA="update arena set userId=?, heroId=?, userName=?, heroInfo=?,soldier=?,time=?,serverId=? where id=?";
		public static final String SQL_SELECT_ARENA="select * from arena ";
	/*****************DAO 操作*****************************/
	public void add(final Arena arena){
		try {
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_ARENA,
							Statement.RETURN_GENERATED_KEYS);
					ps.setInt(1,arena.getUserId());
					ps.setInt(2,arena.getHeroId());
					ps.setString(3,arena.getUserName());
					ps.setString(4,arena.getHeroInfo());
					ps.setString(5, arena.getSoldier());
					ps.setShort(6,arena.getId());
					ps.setInt(7, GameServerApp.instanceID);
					ps.setTimestamp(8, new Timestamp(TimeUtils.nowLong()));
					return ps;
				}

			});

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 更新
	 * @param nation
	 */
	public void saveArena(Arena arena){
		gameWorldDAO.getSimpleJdbcTemplate().update(SQL_UPDATE_ARENA,
				arena.getUserId(),
				arena.getHeroId(),
				arena.getUserName(),
				arena.getHeroInfo(),
				arena.getSoldier(),
				new Timestamp(TimeUtils.nowLong()),
				GameServerApp.instanceID,
				arena.getId()
		);	
	}
	/**
	 * 获得全部英雄
	 * @param userId
	 * @return
	 */
	public List<Arena> getAll(){
	try {
			
			List<Arena> list = gameWorldDAO.getSimpleJdbcTemplate().query(SQL_SELECT_ARENA, arenaRowMapper);
			return list;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	

}
