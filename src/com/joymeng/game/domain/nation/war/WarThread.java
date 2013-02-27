package com.joymeng.game.domain.nation.war;

import hirondelle.date4j.DateTime;

import com.joymeng.core.utils.TimeUtils;

public class WarThread extends Thread {
	static int DOWNTIME = 1;//宕机
	// 战役管理类
	public WarManager warMgr = WarManager.getInstance();
	public WarThread(String name) {
		setName(name);
	}
	public void run() {
		try {
			System.out.println("争夺战 :" + warMgr.FIGHT_TYPE + ">>> 线程开始....");
			while (true) {
				DateTime now = TimeUtils.now();
				this.sleep(5*1000);
				//System.out.println(this.getName()+"|分："+cal.get(cal.MINUTE)+"|秒："+cal.get(cal.SECOND));
				if (!warMgr.IS_FIGHT) {// 开启失败...退出
					System.out.println("争夺战 :" + warMgr.FIGHT_TYPE + "开启失败...退出....");
					DOWNTIME = 0;
					break;
				}
				warMgr.refultUserIntegral();
				// 刷新据点
				if (now.getMinute() % 2 == 0 && now.getSecond() >= 0 && now.getSecond() <= 5) {// 在时间段内
					if (warMgr.IS_FIGHT) {// 可以战斗情况下.刷新据点
						warMgr.strongJob();
					}
				}

				if (warMgr.IS_REFRESH) {
					// 结束战斗,整理数据
					if ((now.getMinute() == 20 || now.getMinute() == 50)
							&& now.getSecond() >= 0 && now.getSecond() <= 5) {//时间段内
						warMgr.calcResults();
						warMgr.stopWar(true);
						System.out.println("争夺战 到时间 : :" + warMgr.FIGHT_TYPE + "线程结束....");
						DOWNTIME = 0;
						break;
					}
				}else{
					if(warMgr.IS_FIGHT && (TimeUtils.nowLong()/1000 > warMgr.FIGHT_END
							|| TimeUtils.nowLong()/1000 < warMgr.FIGHT_START)) {//开始战争且时间到了
						warMgr.calcResults();
						warMgr.stopWar(true);
						System.out.println("争夺战 到时间 : :" + warMgr.FIGHT_TYPE + "线程结束....");
						DOWNTIME = 0;
						break;
					}
				}
			}
			//Thread.sleep(100);
			System.out.println("争夺战 :" + warMgr.FIGHT_TYPE + "线程结束....");
		} catch (Exception e) {
			System.out.println("争夺战 :" + warMgr.FIGHT_TYPE + "线程关闭错误....");
			e.printStackTrace();
		}

	}
}
