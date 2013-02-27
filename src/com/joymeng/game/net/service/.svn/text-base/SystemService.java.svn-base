package com.joymeng.game.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.service.EchoService;
import com.joymeng.game.net.request.SystemRequest;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 系统
 * 
 * @author admin
 * @date 2012-4-23 TODO
 */
@JoyMessageService
public class SystemService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(EchoService.class);

	private SystemService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleSystem(SystemRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID()+" service"+this.getClass().getName());

		return null;
	}
}
