package com.joymeng.game.net.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.game.net.request.ServerInnerRequest;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;


@JoyMessageService
public class ServerInnerService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(ServerInnerService.class);

	private ServerInnerService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleServerInner(ServerInnerRequest resp, ServicesContext context) {
		logger.info("handleServerInner-------------------");
		int id=resp.getSrcInstanceID();
//		ServerInnerResp resp = new ServerInnerResp("hello test");
//		//设置具体要通讯的服务器的instanceId
//		resp.setDestInstanceID(0x0600);
//		return resp;
		return null;
	}

}
