package com.joymeng.core.event;

public class TestDemo {
	 DemoSource ds;
	 TestDemo(){
		 ds = new DemoSource();
		 Listener1 ls1=new Listener1();
		 Listener2 ls2=new Listener2();
		 ds.addDemoListener(ls2);
		 ds.addDemoListener(ls1);
		 ds.addDemoListener(new DemoListener(){

			@Override
			public void demoEvent(DemoEvent dm) {
				System.out.println("demo listener");
			}
			 
		 });
		 ds.notifyDemoEvent();
	 }
	 public static void main(String[] args){
		 new TestDemo();                       
	}
}
