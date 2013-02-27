package com.joymeng.game.net.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.net.request.ActiveRequest;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

/**
 * 活动
 * 
 * @author admin
 * @date 2012-4-23 
 */
@JoyMessageService
public class ActiveService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(ActiveService.class);

	private ActiveService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleActive(ActiveRequest request, ServicesContext context) {
		UUID uuid  =  UUID.randomUUID(); 
		logger.info("echo from start" + request.getJoyID()+" service"+this.getClass().getName()+"uuid="+uuid);
		logger.info("echo from end " + request.getJoyID()+" service"+this.getClass().getName()+"uuid="+uuid);

		return null;
	}
}
