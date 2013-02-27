package com.joymeng.game.cache.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.web.entity.OnlineTime;

/**
 * 在线时间统计
 * 
 * @author admin
 * 
 */
@Document
public class GameOnlineTime {
	@Id
	String time;// 只用于显示
	Date date;
	long max;// 最大在线时间
	long total;// 总在线时间
	long average;//平均在线时间
	List<OnlineTime> list =new ArrayList<OnlineTime>();
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getMax() {
		return max;
	}

	public void setMax(long max) {
		this.max = max;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<OnlineTime> getList() {
		return list;
	}
	
	public long getAverage() {
		return average;
	}

	public void setAverage(long average) {
		this.average = average;
	}

	public void setList(List<OnlineTime> list) {
		this.list = list;
	}
	public boolean add2List(OnlineTime ot){
		if(TimeUtils.isSameDay(date.getTime(),ot.getTimestamp().getTime())){//如果有当天数据
			list.add(ot);
			return true;
		}else{
			return false;
		}
	}
}
