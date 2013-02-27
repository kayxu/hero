package com.joymeng.game.cache.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.web.entity.OnlineNum;
import com.joymeng.web.entity.OnlineTime;

/**
 * 在线数量统计
 * 
 * @author admin
 * 
 */
@Document
public class GameOnlineNum {
	@Id
	String time;// 只用于显示
	List<OnlineNum> list=new ArrayList<OnlineNum>();
	int max;
	int average;
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<OnlineNum> getList() {
		return list;
	}

	public void setList(List<OnlineNum> list) {
		this.list = list;
	}
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getAverage() {
		return average;
	}

	public void setAverage(int average) {
		this.average = average;
	}

	public boolean add2List(OnlineNum on){
		if(TimeUtils.isSameDay(TimeUtils.getTimes(time),on.getTimestamp().getTime())){//如果有当天数据
			list.add(on);
			return true;
		}else{
			return false;
		}
	}
}
