package com.joymeng.game.domain.nation;

import com.joymeng.core.base.net.response.ClientModuleBase;
import com.joymeng.services.core.buffer.JoyBuffer;

public class RefreshResources extends ClientModuleBase {

	public byte getModuleType() {
		return NTC_REFRESH_RESOURCES;
	}

	@Override
	public void _serialize(JoyBuffer out) {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
