package com.joymeng.game.domain.world;

import java.util.LinkedList;
import java.util.List;

import com.joymeng.core.utils.TimeUtils;

/**
 * 游戏主循环消耗监视
 * 
 * @author ShaoLong Wang
 */
public class WorldMonitor {

	static long MAIN_LOOP_TPS = 5; // 主循环每秒间隔
	static long MAIN_LOOP_INTERVAL = 1000 / MAIN_LOOP_TPS; // 每次主循环的最大固定消耗时间
	public static long PRINT_WORLD_MONITOR_TICK_COUNT = 10000;
	static final int MAX_MONITOR_NUM = 50;
	static List<WorldMonitor> monitors = new LinkedList<WorldMonitor>();

	static WorldMonitor peakMonitor;
	static long avarTimeElapse;

	long startTime;// 起始时间

	long timeElapse;// 每次循环的实际消耗时间

	long sceneTimeElapse;// 场景逻辑的消耗时间

	long casinoTimeElapse;// 赌场逻辑的消耗时间

	long fightTimeElapse;// 战场逻辑的消耗时间

	long saveTimeElapse;// 保存逻辑的消耗时间

	long obactivityElapse;// 运营活动时间

	long activityNpcElapse;// 动态NPCtick

	long runbusinessTimeElapse;// 赌场逻辑的消耗时间

	long familyTimeElapse;// 家族逻辑的消耗时间

	long activityTimeElapse;// 场景逻辑的消耗时间

	long teamCopyTimeElapse;// 团队副本逻辑的消耗时间

	/**
	 * 开始计时监视
	 */
	public void start() {
		startTime = TimeUtils.nowLong();
	}

	/**
	 * 停止计时监视
	 */
	public void stop() {
		timeElapse = TimeUtils.nowLong() - startTime;
		// addMonitor(this);
	}

	/**
	 * 得到计时剩余
	 * 
	 * @return
	 */
	public long getTimeRemain() {
		long remain = MAIN_LOOP_INTERVAL - timeElapse;
		return remain < 0 ? 0 : remain;
	}

	/**
	 * 添加监视
	 * 
	 * @param monitor
	 */
	public static void addMonitor(WorldMonitor monitor) {
		if (monitors.size() > MAX_MONITOR_NUM) {
			monitors.remove(0);
		}

		monitors.add(monitor);

		boolean isPeak = false;
		if (peakMonitor == null) {
			peakMonitor = monitor;
			isPeak = true;
		} else {
			if (monitor.timeElapse > peakMonitor.timeElapse) {

				peakMonitor = monitor;
				isPeak = true;

			}
		}

		if (isPeak) {
			System.out.println("peak:" + WorldMonitor.peakMonitor);
		}

	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append("*******************************************");

		sb.append("\n timeElapse:");
		sb.append(timeElapse);

		sb.append("\n	| sceneTimeElapse:");
		sb.append(sceneTimeElapse);

		sb.append("\n	| casinoTimeElapse:");
		sb.append(casinoTimeElapse);

		sb.append("\n	| fightTimeElapse:");
		sb.append(fightTimeElapse);

		sb.append("\n	| saveTimeElapse:");
		sb.append(saveTimeElapse);

		sb.append("\n	| obactivityElapse:");
		sb.append(obactivityElapse);

		sb.append("\n	| activityNpcElapse:");
		sb.append(activityNpcElapse);

		sb.append("\n*******************************************");

		return sb.toString();
	}

}
