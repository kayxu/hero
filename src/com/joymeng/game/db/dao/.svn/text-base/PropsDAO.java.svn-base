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

import com.joymeng.game.db.GameDAO;
import com.joymeng.game.domain.item.PropsDelay;

public class PropsDAO {
	GameDAO gameWorldDAO;
	private static final Logger logger = LoggerFactory
			.getLogger(PropsDAO.class);
	public PropsDAO(GameDAO dao){
		gameWorldDAO=dao;
	}
	//查询
	public static final String SQL_SELECT_DELAY="SELECT * FROM propsdelay WHERE userID = ?";
	//增加
	public static final String SQL_ADD_DELAY="insert into propsdelay (id,propsId,propsType,endTime,userId,additionCount)values(?,?,?,?,?,?)";
	//删除
	public static final String DELETE_DELAY="DELETE FROM propsdelay WHERE id =?";
	//更新
	public static final String SQL_UPDATE_DELAY= "update propsdelay set propsId=?, propsType=?, endTime=?, userId=?,additionCount=? where id=?";
	
	/*****************DAO 操作*****************************/
	public int addPropsDelay(final PropsDelay propsDelay){
		try {
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			gameWorldDAO.getJdbcTemplate().update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					PreparedStatement ps = conn.prepareStatement(SQL_ADD_DELAY,1);
					ps.setInt(1,propsDelay.getId());
					ps.setInt(2,propsDelay.getPropsId());
					ps.setByte(3,propsDelay.getPropsType());
					ps.setInt(4,propsDelay.getEndTime());
					ps.setInt(5,propsDelay.getUserId());
					ps.setInt(6,propsDelay.getAdditionCount());
					System.out.println("propsDelay 1");
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public PropsDelay addDelay(PropsDelay propsDelay){
		int id = addPropsDelay(propsDelay);
		propsDelay.setId(id);
		return propsDelay;
	}
			
	/**
	 * 更新
	 * @param nation
	 */
	public void savePropsDelay(PropsDelay propsdelay,int id){
		gameWorldDAO.getJdbcTemplate().update(SQL_UPDATE_DELAY,propsdelay.getPropsId(),
				propsdelay.getPropsType(),
				propsdelay.getEndTime(),
				propsdelay.getUserId(),
				propsdelay.getAdditionCount(),
				id
		);	
	}
	
	public PropsDelay loadObject(Map<String, Object> map){
		PropsDelay propsDelay = new PropsDelay();
		propsDelay.setId( (Integer) map.get("id"));
		propsDelay.setPropsId( (Integer) map.get("propsId"));
		propsDelay.setPropsType( ((Integer) map.get("propsType")).byteValue());
		propsDelay.setEndTime( (Integer) map.get("endTime"));
		propsDelay.setUserId( (Integer) map.get("userId"));
		propsDelay.setAdditionCount((Integer) map.get("additionCount"));
		return propsDelay;
	}
	
	public boolean deletePropsDelay(int id){
		gameWorldDAO.getJdbcTemplate().update(DELETE_DELAY,id);
		logger.info("***********删除delay数据:"+id);
		return true;
	}
	
	/*****************DAO 操作*****************************/
}
