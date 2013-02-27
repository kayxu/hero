package com.joymeng.core.event;

import java.util.EventObject;

public class DemoEvent {
	Object obj;
	public DemoEvent(Object source) {
		obj=source;
	}
	public void say(){
		System.out.println(" demo event ");
	}
	public Object getSource(){
		return obj;
	}
}
