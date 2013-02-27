package com.joymeng.game.cache.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.web.entity.Sale;

/**
 * 道具使用
 * @author admin
 *
 */
@Document
public class PropUse {
	@Id
	String time;// 登录时间
	List<Sale> list=new ArrayList<Sale>();
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<Sale> getList() {
		return list;
	}
	public void setList(List<Sale> list) {
		this.list = list;
	}
	
}
