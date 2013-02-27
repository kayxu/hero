package com.joymeng.game.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.log.GameLog;
import com.joymeng.game.cache.domain.Log;

public class MongoLogManager {
	static Logger logger = LoggerFactory.getLogger(MongoLogManager.class);
	private static MongoLogManager instance;

	public static MongoLogManager getInstance() {
		if (null == instance) {
			instance = new MongoLogManager();
		}
		return instance;
	}
	
	public void clear(){
		
	}
	public void addLog(Log log,byte type,int id){
		String message="";
		String[] str=null;
		switch(type){
		case 0:
			 message=log.getMessage();
			str=message.split("\\|");
			int uid=Integer.parseInt(str[0]);
			if(uid==id){
				logger.info("log="+log.toString());
//				MongoServer.getInstance().removeData("hero",log.getId());
			}
			break;
		case 1:
			 message=log.getMessage();
			 str=message.split("\\|");
//			 竞技场id=850
			 int arenaId=Integer.parseInt(str[2].substring(6));
			 System.out.println(log.toString());
			 if(arenaId==id){
					logger.info("log="+log.toString());
//					MongoServer.getInstance().removeData("hero",log.getId());
				}
			break;
		}
	}
}
