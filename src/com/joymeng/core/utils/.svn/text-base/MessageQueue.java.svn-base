package com.joymeng.core.utils;

import java.util.PriorityQueue;

import com.joymeng.game.domain.world.TipMessage;
/**
 * 优先级消息队列,默认级别0最高
 * @author admin
 *
 */
public class MessageQueue extends PriorityQueue<TipMessage>{
	
	public TipMessage getMessage(){
		return (TipMessage)this.poll();
	}
	public boolean add(TipMessage message){
		return this.offer(message);
	}
	public boolean add(TipMessage message,int priority){
		message.setPriotity(priority);
		return this.offer(message);
	}
	
}
