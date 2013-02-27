package com.joymeng.game.cache.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

/**
 * 活跃玩家级别分布
 * @author admin
 *
 */
public class ActiveUserLevel {
	@Id
	String time;
	Map<Short,Integer> map=new HashMap<Short,Integer>();
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Map<Short, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Short, Integer> map) {
		this.map = map;
	}
}
