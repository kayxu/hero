package com.joymeng.core;

import java.util.ArrayList;
import java.util.List;

import com.joymeng.game.common.Instances;

/**
 * 职责： 管理RPGObject 类型 管理tick任务链表 管理事件处理，
 * 
 * @author Shaolong Wang
 */
public abstract class RPGCreature implements Tickable, Instances {

	List<Tickable> tasks;

	/**
	 * 增加一个tick任务
	 * 
	 * @param task
	 */
	public void addTask(Tickable task) {
		if (tasks == null) {
			tasks = new ArrayList<Tickable>();
		}

		tasks.add(task);
	}

	/**
	 * 删除一个tick任务
	 * 
	 * @param task
	 */
	public void removeTask(Tickable task) {
		if (tasks == null) {
			return;
		}

		tasks.remove(task);
	}

	/**
	 * 返回所有任务列表
	 * 
	 * @return
	 */
	public List<Tickable> getTask() {

		return tasks;
	}

	public void tick() {
		if (tasks == null) {
			return;
		}
		for (int i = tasks.size() - 1; i >= 0; i--) {
			tasks.get(i).tick();
		}
	}
}
