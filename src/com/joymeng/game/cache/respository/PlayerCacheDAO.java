package com.joymeng.game.cache.respository;

import java.util.List;

import com.joymeng.game.cache.domain.PlayerCache;

public interface PlayerCacheDAO {

	public void insert(PlayerCache playerCache);
	
	public List<PlayerCache> findAll();
	
	public void dropCollection();
	
	public void deletePlayerCache(int userid);
	
	public List<PlayerCache> findSameLevel(short level,int userid);
	
	public List<PlayerCache> findSameLevelAndNotOccupied(short level,int userid);
	
	public List<PlayerCache> findRangeLevelAndNotOccupied(short level,int userid);
	
	public PlayerCache findPlayerCacheByUserid(int userid);
	
	public PlayerCache randomAPlayerCache();
	
	 /**
	  * 随机一定数量的玩家信息
	  * @param num 随机出的玩家数量
	  * @param level 玩家自己当前等级
	  * @param userid 玩家自己id
	  * @return
	  */
	public List<PlayerCache> randomCertainNumPlayerCache(int num,short level,long userid);
	 public List<PlayerCache> findPlayers(int ids[]);
}
