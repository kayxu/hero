package com.joymeng.core.base.net.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.core.base.net.request.EchoRequest;
import com.joymeng.core.base.net.response.EchoResp;
import com.joymeng.services.core.annotation.JoyMessageHandler;
import com.joymeng.services.core.annotation.JoyMessageService;
import com.joymeng.services.core.context.ServicesContext;
import com.joymeng.services.core.message.JoyProtocol;
import com.joymeng.services.core.service.AbstractJoyService;

@JoyMessageService
public class EchoService extends AbstractJoyService {
	static Logger logger = LoggerFactory.getLogger(EchoService.class);

	private EchoService() {

	}

	@JoyMessageHandler
	public JoyProtocol handleEcho(EchoRequest request, ServicesContext context) {
		logger.info("echo from " + request.getJoyID());

		EchoResp resp = new EchoResp();
		resp.setUserInfo(request.getUserInfo());

		return resp;
	}

}
