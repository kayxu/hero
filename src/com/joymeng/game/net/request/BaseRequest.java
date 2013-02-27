package com.joymeng.game.net.request;

import com.joymeng.game.GameServerApp;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyRequest;

public class BaseRequest extends JoyRequest {
	
	public void init(){
		GameServerApp.receive.incrementAndGet();
	}
	public BaseRequest(int i) {
		super(i);
	}
	@Override
	protected void _deserialize(JoyBuffer joybuffer) {
		
	}

	@Override
	protected void _serialize(JoyBuffer joybuffer) {
		
	}
}
