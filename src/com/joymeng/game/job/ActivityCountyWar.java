package com.joymeng.game.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.spring.local.I18nGreeting;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameConst;
import com.joymeng.game.common.GameUtils;
import com.joymeng.game.domain.fight.FightEventManager;
import com.joymeng.game.domain.world.TipMessage;

public class ActivityCountyWar implements Job{
	private static Logger log = LoggerFactory.getLogger(ActivityCountyWar.class);
	
	private static boolean showEnd = false;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(isWarGoing()){
			String str = "县长争夺战开启了,特殊兵种,功勋,宝物,快踏上王权之路第一步!";
			if(I18nGreeting.LANLANGUAGE_TIPS ==1){
				str = "Townleader Battle is in the heat.Fight for Special troops, Honors and Treasures!!";
			}
			GameUtils.sendWolrdMessage(new TipMessage(str,0,GameConst.GAME_RESP_SUCCESS), (byte)1);
			log.info("县长争夺战开始  " + TimeUtils.now());
			GameUtils.sendWolrdMessage(null,(byte)2);//活动开启标志
		}
		else{
			if(!showEnd){
				String str = "今日县长争夺战已经结束，敬请期待明日开启！";
				if(I18nGreeting.LANLANGUAGE_TIPS ==1){
					str = "Today's Townleader Battle is over.Please stay tuned for tomorrow's battle!";
				}
				showEnd = true;
				GameUtils.sendWolrdMessage(new TipMessage(str,0,GameConst.GAME_RESP_SUCCESS), (byte)1);
				log.info("县长争夺战结束  " + TimeUtils.now());
			}
		
		}
		
	}
	
	/**
	 * 判断战争是否继续
	 * @return
	 */
	private boolean isWarGoing(){
		//Date nowDate = new Date();
		//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		//String nowTime = sdf.format(nowDate);
		/*if(!((nowTime.compareTo("22:00:00") > 0 && nowTime.compareTo("24:00:00") < 0)
			|| nowTime.compareTo("00:00:00") > 0 && nowTime.compareTo("11:00:00") < 0)){	
			log.info("县长争夺战现在处于关闭状态");
			return false;
		}*/
		/*if(!(nowTime.compareTo("05:00:00") > 0 && nowTime.compareTo("23:59:00") < 0)){
			log.info("县长争夺战现在处于关闭状态");
			return false;
		}*/
		if(!FightEventManager.isCountyWarGoing()){
			log.info("县长争夺战现在处于关闭状态");
			return false;
		}
		log.info("县长争夺战处于开启状态");
		showEnd = false;
		return true;
	}
	
}
