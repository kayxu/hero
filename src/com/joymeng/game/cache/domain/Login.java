package com.joymeng.game.cache.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.game.cache.LogUserInterface;

/**
 * 登录记录
 * @author admin
 * 
 */
@Document
public class Login extends ClientModuleBase implements LogUserInterface{
	@Id
	String time;// 登录时间
	int num;// 登录次数
	Set<Integer> set = new HashSet<Integer>();// 有效登录次数
	int lost;// 流失数量

	public Login(String time) {
		this.time = time;
		this.num = 1;
	}

	public void add(int uid) {
		num++;
		set.add(uid);
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

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

	public int getLost() {
		return lost;
	}

	public void setLost(int lost) {
		this.lost = lost;
	}

	@Override
	public int getTotal() {
		return set.size();
	}

}
