package com.joymeng.core.spring.local;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.joymeng.core.utils.PropertyManager;
import com.joymeng.core.utils.TimeUtils;
import com.joymeng.game.common.GameConst;
/**
 * spring本地化字符串处理
 * @author admin
 *
 */
public class I18nGreeting {
	static Logger logger = LoggerFactory.getLogger(I18nGreeting.class);
	public static final String BASEFILENAME = "conf/application.properties";
	static Locale LANLANGUAGE = null;//语言类型
	public static int LANLANGUAGE_TIPS = 0 ; //中文
	static ApplicationContext ctx;
	public static I18nGreeting instance;
	public static I18nGreeting getInstance(){
		if(instance == null)
			instance = new I18nGreeting();
		return instance;
	}
	
	/**
	 * 启动加载语言类型
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void load() throws FileNotFoundException, IOException{
		PropertyManager pm = new PropertyManager(BASEFILENAME);
		String str = pm.getString("LanguageTips");
		if("US".equals(str)){
			LANLANGUAGE = GameConst.US;
			LANLANGUAGE_TIPS = 1;//英文
		}else{
			LANLANGUAGE = GameConst.CHINA;
			LANLANGUAGE_TIPS = 0;//中文
		}
		logger.info("语言类型："+str);
	}
	
	/**
	 * 获得对应数据
	 * @param key
	 * @param params
	 * @return
	 */
	public String getMessage(String key,Object[] params){
		try {
			if(ctx == null){
				ctx = new FileSystemXmlApplicationContext("/conf/fmt_resource.xml"); 
			}
	        String msg = ctx.getMessage(key, params, LANLANGUAGE); //语言
	        logger.info("返回语言："+msg);
	        return msg;
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
			return "";
		}
		
	}
	
	public static void main(String[] args) throws Exception{
//		rsrBdlMessageResource();
//		rrsrBdlMessageResource();
		I18nGreeting ig=new I18nGreeting();
		ig.ctxMessageResource(Locale.CHINA);
		HashMap<String, String> map=ig.getStringMap();
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			String value=map.get(key);
			System.out.println(key + value);
		}
	}
	private HashMap<String, String> stringMap = new HashMap<String, String>();
	private  void ctxMessageResource(Locale locale) throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("fmt_resource.xml");
		Object[] params = {"John", TimeUtils.nowLong()};
		//读取任意一个国际化配置文件，
		PropertyManager pm=new PropertyManager("conf/fmt_resource_en_US.properties");
		Enumeration<?> en=pm.getProperties().propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String value= ctx.getMessage(key,params,locale);
			stringMap.put(key, value);
//			String Property = pm.getString(key);
//			System.out.println(key + Property);
//			String str1 = ctx.getMessage(key,params,Locale.US);
//			String str2 = ctx.getMessage(key,params,Locale.CHINA);	
//			System.out.println(str1);
//			System.out.println(str2+"\n");	
		}
	}
	public HashMap<String, String> getStringMap() {
		return stringMap;
	}
	public void setStringMap(HashMap<String, String> stringMap) {
		this.stringMap = stringMap;
	}	
	
}
