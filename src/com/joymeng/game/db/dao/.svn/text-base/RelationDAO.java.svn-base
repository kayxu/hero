package com.joymeng.game.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.joymeng.game.db.DBManager;
import com.joymeng.game.db.GameDAO;
import com.joymeng.game.domain.friend.Enemy;
import com.joymeng.game.domain.friend.Friend;

public class RelationDAO {
	private static final Logger logger = LoggerFactory
			.getLogger(RelationDAO.class);
	public static final String SQL_SELECT_FRIEND ="select * from friend where myId = ?";//好友
	public static final String SQL_SELECT_PLAN_FRIEND ="select * from friend where myId = ? and type = 0";//准好友
	public static final String SQL_DELETE_FRIEND ="delete from friend where  myId = ? and friendId = ?";//准好友
	public static final String SQL_ADD_FRIEND = "insert into friend (id,myId,friendId,type,addTime)values(?,?,?,?,?)";
	public static final String SQL_UPDATE_FRIEND = "update friend set  type=?, addTime=? where myId=? and friendId=?,";
	public static final String SQL_SELECT_ENEMY="select * from enemy where myId = ? order by attackTime desc";
	public static final String SQL_ADD_ENEMY="insert into enemy (id,myId,enemyId,attackTime,reason)values(?,?,?,?,?)";
	GameDAO gameWorldDAO;
	public RelationDAO(GameDAO dao){
		gameWorldDAO = dao;
	}
	
	//public static final String SQL_UPDATE_ENEMY = "update enemy set myId=?, enemyId=?, attackTime=?, reason=? where ";
	/*****************DAO 操作*****************************/
	public void addFriend(final Friend friend){
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_FRIEND,1);
					ps.setInt(1,friend.getId());
					ps.setInt(2,friend.getMyId());
					ps.setInt(3,friend.getFriendId());
					ps.setByte(4,friend.getType());
					ps.setLong(5,friend.getAddTime());
					return ps;
				}
			}, keyHolder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void addEnemy(final Enemy enemy){
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_ENEMY,1);
					ps.setInt(1,enemy.getId());
					ps.setInt(2,enemy.getMyId());
					ps.setInt(3,enemy.getEnemyId());
					ps.setLong(4,enemy.getAttackTime());
					ps.setString(5,enemy.getReason());
					return ps;
				}
			}, keyHolder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public Friend loadFriend(Map<String, Object> map){
		Friend friend = new Friend();
		friend.setId((Integer) map.get("id"));
		friend.setMyId((Integer) map.get("myId"));
		friend.setFriendId((Integer) map.get("friendId"));
		friend.setType(((Integer)map.get("type")).byteValue());
		friend.setAddTime((Long) map.get("addTime"));
		return friend;
	}
	
	public Enemy loadEnemy(Map<String, Object> map){
		Enemy enemy = new Enemy();
		enemy.setId((Integer)map.get("id"));
		enemy.setMyId((Integer)map.get("myId"));
		enemy.setEnemyId((Integer)map.get("enemyId"));
		enemy.setAttackTime((Long)map.get("attackTime"));
		enemy.setReason((String)map.get("reason"));
		return enemy;
	}
//	//保存数据
//	public boolean saveFriend(Friend friend){
//		gameWorldDAO.getJdbcTemplate().update(SQL_UPDATE_FRIEND,
//				friend.getType(),
//				friend.getAddTime(),
//				friend.getMyId(),
//				friend.getFriendId());
//		return true;
//	}
	//保存数据
	public boolean delFriend(Friend friend){
		friend.toString();
		gameWorldDAO.getJdbcTemplate().update(SQL_DELETE_FRIEND,
				friend.getMyId(),
				friend.getFriendId());
		return true;
	}
	
}
