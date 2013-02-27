package com.joymeng.game.domain.world;

import com.joymeng.core.utils.MessageQueue;

public class MessageManager {
	private MessageQueue messageQueue=new MessageQueue(); 
	private static MessageManager instance;
	public static MessageManager getInstance() {
		if (instance == null) {
			instance = new MessageManager();
		}
		return instance;
	}
	public MessageQueue getMessageQueue() {
		return messageQueue;
	}
	public void setMessageQueue(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	} 
	
}
