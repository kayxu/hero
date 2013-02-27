package com.joymeng.core.event;

import java.util.Enumeration;
import java.util.Vector;

public class DemoSource {
	private Vector repository = new Vector();
	DemoListener dl;

	public DemoSource() {
	}

	public void addDemoListener(DemoListener dl) {
		repository.addElement(dl);
	}

	public void notifyDemoEvent() {
		Enumeration<DemoListener> e = repository.elements();
		while(e.hasMoreElements()){
			dl = e.nextElement();
			dl.demoEvent(new DemoEvent(this));
		}
	}
}
