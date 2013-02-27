package com.joymeng.game.test;
import java.lang.reflect.Method;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
public class test3 {

	public static void main(String[] args) throws Exception{
	  ClassPool pool = ClassPool.getDefault();
	  CtClass pt = pool.makeClass("asdf",pool.get("TestBean"));
	  CtField fld = new CtField(pool.get("java.lang.String"),
	          "_version", pt);
	  fld.setModifiers(Modifier.PUBLIC );
	  pt.addField(fld);
	  CtMethod method1 = new CtMethod(pool.get("java.lang.String"), "getM",null, pt);
	  method1.setBody("{return \"你好\";}");
	  pt.addMethod(method1);
	  CtMethod method3 = new CtMethod(pool.get("java.lang.String"), "getVersion",null, pt);
	  method3.setBody("{return _version;}");
	  pt.addMethod(method3);
	  CtConstructor cc=new CtConstructor(null,pt);
	  cc.setBody("this.field=\"why?\";");
	  pt.addConstructor(cc);
	  CtMethod method2 = new CtMethod(CtClass.voidType, "setF",new CtClass[]{pool.get("java.lang.String")}, pt);
	  method2.setBody("{this.field=$1;}");
	  pt.addMethod(method2);
	  CtMethod method4 = new CtMethod(CtClass.voidType, "setVersion",new CtClass[]{pool.get("java.lang.String")}, pt);
	  method4.setBody("{this._version=$1;}");
	  pt.addMethod(method4);
	  Class c=pt.toClass();
	  TestBean bean=(TestBean) c.newInstance();
	  System.out.println(bean.getM());
	  System.out.println(bean.getF());
	  bean.setF("setf");
	  System.out.println(bean.getF());
	  System.out.println(bean.getF());
	  Method setMethod = bean.getClass().getMethod(
	            "setVersion",
	            new Class[] { "".getClass() });
	     setMethod.invoke(bean,
	                new Object[] { "123.321" });
	     Method getMethod = bean.getClass().getMethod(
	                "getVersion",
	                new Class[] {  });
	    Object    returnObject = getMethod.invoke(bean,
	                    new Object[] {  });
	    System.out.println(returnObject);
	}
	public abstract class TestBean {
	public String field;
	public abstract String getM();
	public abstract void setF(String f);
	public String getF(){
	  return this.field;
	}
	}
}
