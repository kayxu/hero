package com.joymeng.core.event;

public class Listener1 implements DemoListener{

	@Override
	public void demoEvent(DemoEvent dm) {
		System.out.println("...listen 1");
	}

}
