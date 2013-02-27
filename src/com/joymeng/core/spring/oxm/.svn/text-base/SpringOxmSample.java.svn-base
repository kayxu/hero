package com.joymeng.core.spring.oxm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import com.joymeng.core.spring.oxm.castor.LoginLog;
import com.joymeng.core.spring.oxm.castor.User;


/**
 * spring oxm 示例，需要读取conf：/application-oxm.xml mapping.xml
 * @author admin
 *
 */
public class SpringOxmSample {
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    
    private User getUser(){
    	 LoginLog log1 = new LoginLog();
         log1.setIp("192.168.1.90");
         log1.setLoginDate(new Date());
         LoginLog log2 = new LoginLog();
         log2.setIp("192.168.1.92");
         log2.setLoginDate(new Date());  
         LoginLog log3 = new LoginLog();
         log3.setIp("192.168.1.93");
         log3.setLoginDate(new Date());
         User user = new User();
         user.setUserName("spring-oxm");
         user.addLoginLog(log1);
         user.addLoginLog(log2);
         user.addLoginLog(log3);
         return user;
    }
    /**
     * JAVA对象转化为XML
     */
    public  void objectToXml()throws Exception{
        User user = getUser();
        FileOutputStream os = null;
        try {
        	Resource resource =new FileSystemResource("conf/SpringOxmSample.xml");
        	System.out.println(resource.getFile().getAbsolutePath());
        	os = new FileOutputStream(resource.getFile());
            this.marshaller.marshal(user, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
    
    /**
     * XML转化为JAVA对象
     */
    public  void xmlToObject()throws Exception{
    	 FileInputStream is = null;
    	 User u = null;
         try {
        	 Resource resource = new FileSystemResource("conf/SpringOxmSample.xml");
             is = new FileInputStream(resource.getFile());
             u = (User) this.unmarshaller.unmarshal(new StreamSource(is));
         } finally {
             if (is != null) {
                 is.close();
             }
         }
        for(LoginLog log : u.getLogs()){
        	if(log!=null){
        		System.out.println("访问IP: " + log.getIp());
        		System.out.println("访问时间: " + log.getLoginDate());
            }
        }
       
    }

    public static void main(String[] args) throws Exception {
    	ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/application-oxm.xml");
        SpringOxmSample oxm = (SpringOxmSample) appContext.getBean("springOxm");
        oxm.objectToXml();
        oxm.xmlToObject();
        SpringOxmSample so=new SpringOxmSample();
//        so.readFile();
        so.testFileSystemResource();
//        so.testClassPath();
//        so.testUrlResource();
        System.exit(0);
    }
    
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
    /**
     * 遍历目录
     */
    private String location = "classpath:*.xml"; 
    
    public void readFile() throws IOException { 
        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver(); 
        Resource[] source = resourceLoader.getResources(location); 
        for (int i = 0; i < source.length; i++) { 
            Resource resource = source[i]; 
            System.out.println(resource.getFilename()+" "+resource.getURL().toString());
            resource.getInputStream();
        } 
    } 
	/**
	 * UrlResource 封装了java.net.URL，它能够被用来访问任何通过URL可以获得的对象，例如：文件、HTTP对象、FTP对象等。
	 * 所有的URL都有个标准的 String表示，这些标准前缀可以标识不同的URL类型，包括file:访问文件系统路径，
	 * http: 通过HTTP协议访问的资源，ftp: 通过FTP访问的资源等等。 
	 * UrlResource 对象可以在Java代码中显式地使用 UrlResource 构造函数来创建
	 *
	 */
	public void testUrlResource() throws Exception{
		Resource resource = new UrlResource("file:/E:/java/Server/bin/SpringOxmSample.xml");
		//Resource resource = new UrlResource("http://127.0.0.1:8080/index.html");
		//Resource resource = new UrlResource(new URI("http://127.0.0.1:8080/index.html"));
		//System.out.println(resource.getFile().getAbsolutePath());//URL时就不要拿他的路径了，这是拿不到的
		System.out.println(resource.exists());
		System.out.println(resource.isOpen());
		System.out.println(resource.getURL());
		System.out.println(resource.getURI());
		System.out.println(resource.isReadable());
		System.out.println(resource.getFilename());
		System.out.println(resource.getDescription());
		System.out.println(new Date(resource.lastModified()));
	}
	/**
	 * 这是为处理 java.io.File 而准备的Resource实现。它既可以作为File提供，也可以作为URL
	 * @throws Exception 
	 */
	public void testFileSystemResource() throws Exception{
		//file:/D:/workspace/MyEclipseSSH/Spring305/ 到本工程的目录中
		Resource resource = new FileSystemResource("conf/SpringOxmSample.xml");
		
		System.out.println(resource.exists());
		System.out.println(resource.isOpen());
		System.out.println(resource.getURL());
		System.out.println(resource.getURI());
		System.out.println(resource.isReadable());
		System.out.println(resource.getFilename());
		System.out.println(resource.getDescription());
		System.out.println(new Date(resource.lastModified()));
	}
	/**
	 * 这个类标识从classpath获得的资源。它会使用线程context的类加载器(class loader)、给定的类加载器或者用来载入资源的给定类。
	 * 如果类路径上的资源存在于文件系统里，这个 Resource 的实现会提供类似于java.io.File的功能。而如果资源是存在于还未解开
	 * (被servlet引擎或其它的环境解开)的jar包中，这些 Resource 实现会提供类似于java.net.URL 的功能。 
	 */
	public void testClassPath() throws IOException{
		
		//Resource resource = new ClassPathResource("/com/spring305/test/resource/ResourceBean.xml");
		Resource resource = new ClassPathResource("SpringOxmSample.xml");
		System.out.println(resource.getFile().getAbsolutePath());
		System.out.println(resource.exists());
		System.out.println(resource.isOpen());
		System.out.println(resource.getURL());
		System.out.println(resource.getURI());
		System.out.println(resource.isReadable());
		System.out.println(resource.getFilename());
		System.out.println(resource.getDescription());
		System.out.println(new Date(resource.lastModified()));
		File file = resource.getFile();
		if(file.isFile()){
			//以字节为单位读取文件的内容,常用于二进制文件,如声音,图象,影象等文件
			InputStream inputStream = new FileInputStream(file);
			int temp ;
			while ((temp = inputStream.read())!=-1) {
				System.out.write(temp);
				//System.out.println(temp);				
			}
			inputStream.close();
		}
		System.out.println("再读");
		if(file.isFile()){
			// 以字符为单位读取文件,常用与读文本,数字等类型的文件
			Reader reader = new InputStreamReader(new FileInputStream(file));
			int temp ;
			while ((temp = reader.read())!=-1) {
				System.out.write(temp);
				//System.out.println(temp);				
			}
			reader.close();
		}
		System.out.println(resource.isOpen());
		
		
	}
}

