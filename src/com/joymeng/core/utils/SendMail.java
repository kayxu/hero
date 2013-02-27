package com.joymeng.core.utils;

import java.io.File;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.joymeng.core.scheduler.SchedulerServer;
import com.joymeng.game.domain.world.World;

public class SendMail {
	public ApplicationContext ctx = null;
	public JavaMailSender sender = null;

	public SendMail() {
		// 获取上下文
		ctx = new ClassPathXmlApplicationContext("application-mail.xml");
		// 获取JavaMailSender bean
		sender = (JavaMailSender) ctx.getBean("mailSender");
	}

	private static final SendMail instance = new SendMail();

	public static final SendMail getInstance() {
		return instance;
	}

	public static void main(String args[]) {
		SendMail sm = new SendMail();
		sm.sendText();
		sm.sendHTML();
		 File file=new File("E:/java/Server/log/db.log");
//		File file = new File("/log/db.log");
		sm.sendFile(file);
	}

	public void sendText() {

		SimpleMailMessage mail = new SimpleMailMessage(); // <span
															// style="color: #ff0000;">注意SimpleMailMessage只能用来发送text格式的邮件</span>

		try {
			mail.setTo("hero@joymeng.com");// 接受者
			mail.setFrom("hero@joymeng.com");// 发送者
			mail.setSubject("spring mail test!");// 主题
			mail.setText("springMail的简单发送测试");// 邮件内容
			sender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendHTML() {
		// 获取JavaMailSender bean
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mailMessage, true, "utf-8");
			messageHelper.setTo("hero@joymeng.com");// 接受者
			messageHelper.setFrom("hero@joymeng.com");// 发送者
			messageHelper.setSubject("hero服务器log日志");// 主题
			// 邮件内容，注意加参数true，表示启用html格式
			messageHelper
					.setText(
							"<html><head></head><body><h1>hello!!test</h1></body></html>",
							true);
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendFile(File file ) {
		// 获取JavaMailSender bean
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(
					mailMessage, true, "utf-8");
			messageHelper.setTo("hero@joymeng.com");// 接受者
			messageHelper.setFrom("hero@joymeng.com");// 发送者
			messageHelper.setSubject("hero服务器log日志");// 主题
			// 邮件内容，注意加参数true
			messageHelper
					.setText(
							"<html><head></head><body><h1>hello!test</h1></body></html>",
							true);
			// 附件内容
			// messageHelper.addInline("a", new File("E:/xiezi.jpg"));
			// messageHelper.addInline("b", new File("E:/logo.png"));
			// File file=new File("E:/java/Server/log/db.log");
//			File file = new File("/log/db.log");
			// 这里的方法调用和插入图片是不同的，使用MimeUtility.encodeWord()来解决附件名称的中文问题
			messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()),
					file);
			sender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
