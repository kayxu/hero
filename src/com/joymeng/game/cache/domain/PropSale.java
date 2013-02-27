package com.joymeng.game.cache.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joymeng.core.utils.TimeUtils;
import com.joymeng.web.entity.Sale;

/**
 * 道具销售
 * @author admin
 *
 */
public class PropSale {
	@Id
	String time;// 时间
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
	/**
	 * 如果id相同，则累积数量
	 * @param s
	 */
	public void add(Sale s){
		boolean b=false;
		for(Sale sale:list){
			if(sale.getId()==s.getId()){
				sale.setNum(sale.getNum()+s.getNum());
				b=true;
				break;
			}
		}
		if(!b){
			list.add(s);
		}
	}
}
