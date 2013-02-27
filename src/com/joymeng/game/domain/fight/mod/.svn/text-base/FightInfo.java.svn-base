package com.joymeng.game.domain.fight.mod;

import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.utils.StringUtils;
import com.joymeng.game.domain.fight.FightConst;

public class FightInfo {
	private static final Logger logger = LoggerFactory
			.getLogger(FightInfo.class);
	public HashMap<Byte,Integer> map = new HashMap<Byte,Integer>();
	public static final byte TOTAL=FightConst.FIGHTBATTLE_INFO;//总的统计次数
	public FightInfo(String str){
		if(str==null||str.equals("")){
			return;
		}
		String[] info=str.split(";");
		for(int i=0;i<info.length;i++){
			String[] ss=info[i].split(":");
			byte fightType=Byte.parseByte(ss[0]);//战斗类型
			int fightNum=Integer.parseInt(ss[1]);//战斗次数
//			logger.info("fightType:"+fightType+" fightNum:"+fightNum);
			map.put(fightType, fightNum);
		}
	}
	public String toStr(){
		Iterator<Byte> it=map.keySet().iterator();
		String str[]=new String[map.size()];
		int i=0;
		while(it.hasNext()){
			byte type=it.next();
			int num=map.get(type);
			StringBuilder sb=new StringBuilder();
			sb.append(type).append(":").append(num);
//			logger.info("fight info "+sb.toString());
			str[i]=sb.toString();
			i++;
		}
		return StringUtils.recoverNewStr(str, ";");
	}
	/**
	 * 增加一次战斗统计
	 * @param type
	 */
	public void addFight(byte type){
		if(map.containsKey(type)){
			int num=map.get(type);
			map.put(type, num+1);
		}else{
			map.put(type, 1);
		}
		if(map.containsKey(TOTAL)){
			int num=map.get(TOTAL);
			map.put(TOTAL, num+1);
		}else{
			map.put(TOTAL, 1);
		}
	
	}
	/**
	 * 获得某一个类型的战斗次数
	 * @param type
	 * @return
	 */
	public int getFightNum(byte type){
		if(map.containsKey(type)){
			return map.get(type);
		}else{
			return 0;
		}
	}
	/**
	 * 清除某种战斗类型的统计
	 * @param type
	 */
	public void clearFightNum(byte type){
		if(map.containsKey(type)){
			map.put(type, 0);
		}
	}
	public HashMap<Byte, Integer> getMap() {
		return map;
	}
	public void setMap(HashMap<Byte, Integer> map) {
		this.map = map;
	}
	
}
