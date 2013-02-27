package com.joymeng.game.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.SMTPAppender;

public class TestLog4jSendMail {
	static Logger logger = Logger.getLogger(TestLog4jSendMail.class);
	SMTPAppender appender = new SMTPAppender();
	public TestLog4jSendMail() {
		try {
			appender.setSMTPUsername("hero");
			appender.setSMTPPassword("abc,123");
			appender.setTo("hero@joymeng.com");
			appender.setFrom("hero@joymeng.com");
			// SMTP服务器 smtp.163.com
			appender.setSMTPHost("10.80.1.23");
			appender.setLocationInfo(true);
			appender.setSubject("Test Mail From Log4J");
			appender.setLayout(new PatternLayout());
			appender.activateOptions();
			logger.addAppender(appender);
			logger.error("Hello World");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Printing ERROR Statements", e);
		}
	}
	public static void main(String args[]) {
		new TestLog4jSendMail();
	}
}
