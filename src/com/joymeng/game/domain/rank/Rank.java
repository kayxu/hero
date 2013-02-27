package com.joymeng.game.domain.rank;

import java.util.ArrayList;

import com.joymeng.core.utils.TimeUtils;

public class Rank {
	byte type;//类型
	long time;//更新时间
	String name;//排行榜名称
	ArrayList<RankUnit> list=new ArrayList<RankUnit>();//排行榜
	int  minPoint;//最低分，低于该分就不用进入排行榜了
	/**
	 * 重新排序
	 */
	public void reRank(){
		
		this.time=TimeUtils.nowLong();
		for(RankUnit ru:list){
			ru.getPoint();
		}
	}
	public boolean addToRank(RankUnit ru){
		if(ru.getPoint()<this.minPoint){
			return false;
		}
		return true;
	}
}
