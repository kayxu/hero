package com.joymeng.game.domain.nation.war;

import java.util.Comparator;


public class ComparatorWar implements Comparator {
//	public static ComparatorWar instance;
//	public static ComparatorWar getInstance(){
//		if(instance == null){
//			instance = new ComparatorWar();
//		}
//		return instance;
//	}
	@Override
	public int compare(Object o1, Object o2) {
		UserWarData data1 = (UserWarData)o1;
		UserWarData data2 = (UserWarData)o2;
		 int flag = data2.getWarIntegral() - data1.getWarIntegral();
		  if(flag==0){
		   return data1.getId() - data2.getId();
		  }else{
		   return flag;
		  }  
	}

}
