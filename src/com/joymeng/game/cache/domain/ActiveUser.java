package com.joymeng.game.cache.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.game.cache.LogUserInterface;
import com.joymeng.web.entity.OnlineNum;

/**
 * 活跃用户
 * 
 * @author admin
 * 
 */
@Document
public class ActiveUser implements LogUserInterface{
	@Id
	String time;
	Set<Integer> set=new HashSet<Integer>();
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Set<Integer> getSet() {
		return set;
	}
	public void setSet(Set<Integer> set) {
		this.set = set;
	}
	@Override
	public int getTotal() {
		return set.size();
	}
	
	
}
