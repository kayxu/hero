package com.joymeng.core.spring.oxm;
public class ResourceUtils {
   /**
    * ��ȡ��ǰ�����ڵ�����·��	
    * @param cls   ��
    * @param name  �ļ���
    * @return
    */
   public static String getResourceFullPath(Class cls,String name){
	   String path= cls.getResource("").getPath();
	   path = path.substring(1);
	   StringBuffer fileName = new StringBuffer(path);
	   fileName.append(name);
	   return fileName.toString();
   }
 
}
