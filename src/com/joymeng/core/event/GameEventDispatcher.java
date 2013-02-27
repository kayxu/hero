package com.joymeng.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joymeng.core.Tickable;
import com.joymeng.core.log.GameLog;

/**
 * 游戏事件对象 添加同步事件和异步事件的概念 2011/01/29
 * 
 * @author Shaolong Wang
 */
public class GameEventDispatcher implements Tickable {
	/**
	 * 同步事件MAP 按type-handler 关系对应事件处理模块:自身事件
	 */
	Map<Short, List<GameEventListener>> listenerMap = new HashMap<Short, List<GameEventListener>>();

	List<GameEvent> asyEvents = null;

	private final void _raise(GameEvent event) {
		// 处理
		List<GameEventListener> listeners = listenerMap.get(event.getCode());
		if (listeners != null) {
			for (GameEventListener listener : listeners) {
				try {
					listener.handleEvent(event);
				} catch (Exception e) {
					GameLog.error("raise error ", e);
				}
			}
		}
	}

	private final void _preserve(GameEvent event) {
		if (asyEvents == null) {
			asyEvents = new ArrayList<GameEvent>();
		}

		if (asyEvents.contains(asyEvents) == false) {
			asyEvents.add(event);
		}
		return;
	}

	/**
	 * 提交事件
	 * 
	 * @param event
	 */
	public final void raise(GameEvent event) {
		try {
			// 查看是否延迟结束
			if (event.getDelayTick() > 0) {
				_preserve(event);
			} else {
				_raise(event);
			}
		} catch (Exception e) {
			GameLog.error("error in handle event ", e);
		}

	}

	/**
	 * 添加同步监听者
	 * 
	 * @param listener
	 * @param event
	 */
	public final void addListener(GameEventListener listener, short event) {
		List<GameEventListener> hlist = listenerMap.get(event);
		if (hlist != null) {
			if (!hlist.contains(listener))// 不重复添加
				hlist.add(listener);
		} else {
			hlist = new ArrayList<GameEventListener>();
			hlist.add(listener);
			listenerMap.put(event, hlist);
		}
	}

	/**
	 * 移除事件监听
	 * 
	 * @param listener
	 * @param event
	 */
	public final void removeEventListener(GameEventListener listener,
			short event) {
		List<GameEventListener> hlist = listenerMap.get(event);
		if (hlist != null) {
			hlist.remove(listener);
		}
	}

	/**
	 * 移除所有事件监听
	 */
	public final void removeAllListeners() {
		listenerMap.clear();
	}

	/**
	 * 每个tick处理异步事件
	 */
	@Override
	public void tick() {
		for (GameEvent event : asyEvents) {
			event.tick();
			raise(event);// 提交
		}
	}
}
