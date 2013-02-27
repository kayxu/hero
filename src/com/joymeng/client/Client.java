package com.joymeng.client;


import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joymeng.client.net.LoginRequest;
import com.joymeng.services.core.message.JoyNormalMessage.UserInfo;

public class Client {
	public static final int INST_LOGIN_SVR = 0xFFFE;//登录服务器的实例号
	public static final int INST_GAME_SVR=0xFFAA;//游戏服务器实例号
	public static final int LOGIN_REQ = 0x0;//登录 指令
	public static final int LOGIN_RESP = 0x1;
	public static final int ECHO_REQ = 0xA;//echo 指令
	public static final int ECHO_RESP = 0xB;
//	public static final int LOGIN_UID=230;
    public static Logger logger = LoggerFactory.getLogger(Client.class);

    public static final long UID = 0xFFFFFFFF;
    public static final long UID1 = 0xFFFFFFFE;

    public static final byte CLIENT_HEART_REQ = 0x0A;
    public static final byte CLIENT_HEART_RESP = 0x0B;

    public static void main(String[] args) throws Exception {
		// 经过网管，重定位到某台连接服务器，登录成功，再发送 echo请求
		// 登录协议request和response可以添加属性，但协议头不能变
		// 也可以将登录服务器和ECHO服务器分别赋予一个实例号
		// 这样登录服务器独立于游戏服务器
    	connect("10.80.1.254", 60000, false);
    }


    /**
     * 用户登录
     */
    public static void login(IoSession session) {
		// 用户登录验证
		LoginRequest req = new LoginRequest();
		req.setDestInstanceID(INST_LOGIN_SVR);
		req.setName("fff");
		req.setPwd("fff");
		req.setType((byte)1);//1正常，0试玩
		req.setUserInfo( new UserInfo());
    	//用户注册验证
//    	RegRequest req=new RegRequest();
//    	req.setDestInstanceID(INST_LOGIN_SVR);
//    	req.setName("mytest2");
//    	req.setPwd("mytest2");
//    	req.setUserInfo(new UserInfo());
		
		session.write(req);
		logger.info("begin to login");
    }

  


    /**
     * 连接服务器
     * 
     * @param host
     * @param port
     * @throws Exception
     */
    public static void connect(String host, int port, boolean isLogin)
	    throws Exception {

		ClientHandler clientListener = new ClientHandler();
		NioSocketConnector connector = new NioSocketConnector();
	
		// 添加多线程接受消息
		connector.setHandler(clientListener);
		// 添加编码解码过滤器
		connector.getFilterChain().addLast("byte",
			new ProtocolCodecFilter(new ClientCodecFactory()));
	
		ConnectFuture future = connector.connect(new InetSocketAddress(host,
			port));

		future.awaitUninterruptibly();
	
		if (future.isConnected()) {
		    logger.info ("---connected(" + host + ":" + port + ") successful...");
		    if (isLogin) {
		    	login(future.getSession());
		    }
		} else {
		    logger.info("connected fail...");
		}
    }
}
