package com.joymeng.game;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.test.jetty.JettyFactory;


public class JettyServerApp {
	static final Logger logger = LoggerFactory.getLogger(JettyServerApp.class);
	public static Server server;
	public static void start() {
		//设定Spring的profile
		System.setProperty("spring.profiles.active", "development");

		//启动Jetty
		server = JettyFactory.createServerInSource(PORT, CONTEXT);
        WebAppContext webContext = new WebAppContext("WebContent", CONTEXT);
       // webContext.setDefaultsDescriptor("jetty/webdefault-windows.xml");
        webContext.setDescriptor("WebContent/WEB-INF/web.xml");
        //  //修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
        server.setHandler(webContext);
		JettyFactory.setTldJarNames(server, TLD_JAR_NAMES);

		try {
			server.start();
//
			System.out.println("Server running at " + BASE_URL);
			System.out.println("Hit Enter to reload the application");
//
//			//等待用户输入回车重载应用.
//			while (true) {
//				char c = (char) System.in.read();
//				if (c == '\n') {
//					JettyFactory.reloadContext(server);
//				}
//			}
		} catch (Exception e) {
			logger.info("服务器启动失败:"+e.getMessage());
			System.exit(-1);
		}
	}
	
	/**
	 * jetty 服务器关闭
	 */
	public static void close(){
		if(server != null){
			try {
				server.stop();
				logger.info("jetty 服务关闭成功");
			} catch (Exception e) {
				logger.info("jetty 服务关闭失败|"+e.toString());
				e.printStackTrace();
			}
		}
	}
	public static final int PORT = 8088;
	public static final String CONTEXT = "/quickstart";
	public static final String BASE_URL = "http://127.0.0.1:8088/quickstart";
	public static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web",
			"joda-time-jsptags", "springside-core" };
	public static void main(String[] args) {
		JettyServerApp.start();
	}
}
