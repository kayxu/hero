package com.joymeng.core.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 详情见 http://jsczxy2.iteye.com/blog/1423778
 * @author XUYI
 * Spring Security 3.1 PasswordEncoder
 */
public class EncryptUtil {
    //从配置文件中获得
    private static final String SITE_WIDE_SECRET = "user_center_pa%@!ss";
    private static final PasswordEncoder encoder = new StandardPasswordEncoder(
       SITE_WIDE_SECRET);
 
    public static String encrypt(String rawPassword) {
         return encoder.encode(rawPassword);
    }
 
    public static boolean match(String rawPassword, String password) {
         return encoder.matches(rawPassword, password);
    }
    
    public static void main(String[] args) {
    	String pwd="每次结果都不一样伐?";
//    	for(int i=0;i<10;i++){
//    		String encode=EncryptUtil.encrypt("123");
//    		System.out.println(encode);
    		System.out.println(EncryptUtil.match("123", "17047d1f5902b7f93df1ef3c18c88a423a400b3274ba66d0fe98a52fd7924e9c7e9dffd0ec554a73"));
//    	}
    	
        //但是把每次结果拿出来进行match，你会发现可以得到true。
	}
 }
