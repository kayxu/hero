package com.joymeng.game.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.joymeng.core.base.domain.BaseUser;
import com.joymeng.game.db.DBManager;
import com.joymeng.game.domain.friend.Enemy;
import com.joymeng.game.domain.hero.HeroOptType;
import com.joymeng.game.domain.item.PropsDelay;

public class Test2 {
	static Logger logger = LoggerFactory.getLogger(Test.class);

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException {
		// TODO Auto-generated method stub
//		 createModelClient(PropsDelay.class);
//		addSql(PropsDelay.class);
		saveSql(PropsDelay.class);
//		delSql(PropsDelay.class);
//		selectSql(Friend.class);
	}

	/**
	 * 生成client模块的序列化方法
	 */
	public static void createModelClient(Class<?> T) {
		System.out.println("---------serialize-----------");
		try {
			Object data = T.newInstance();// 创建对象
			Field[] fs = T.getDeclaredFields();// 获得属性
			String str = "out.put";
			System.out.println("out.put(getModuleType());");// 第一行
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];

				f.setAccessible(true); // 设置些属性是可以访问的
				if (f.getType() == int.class) {
					System.out.println(str + "Int(" + f.getName() + ");");
				} else if (f.getType() == long.class) {
					System.out.println(str + "Long(" + f.getName() + ");");
				} else if (f.getType() == boolean.class) {
					System.out.println("error");
				} else if (f.getType() == byte.class) {
					System.out.println(str + "(" + f.getName() + ");");
				} else if (f.getType() == String.class) {
					System.out.println(str + "PrefixedString(" + f.getName()
							+ ");");
				}else if(f.getType() == Timestamp.class){
					System.out.println(str + "Timestamp("+f.getName()+");");
				} else if (f.getType() == Short.class || f.getType() == short.class ) {
					System.out.println(str + "Short(" + f.getName() + ");");
				} else {
					if (f.getType().toString().equals("class [I")) {// int[]
						System.out.println("JoyBufferPlus.putIntArray(out, "
								+ f.getName() + ");");
					} else if (f.getType().toString().equals("class [S")) {// short[]
						System.out.println("JoyBufferPlus.putShortArray(out, "
								+ f.getName() + ");");

					} else if (f.getType().toString().equals("class [B")) {// byte[]
						System.out.println("JoyBufferPlus.putByteArray(out, "
								+ f.getName() + ");");

					} else if (f.getType().toString()
							.equals("class [Ljava.lang.String;")) {// string[]
						System.out.println("JoyBufferPlus.putStringArray(out, "
								+ f.getName() + ");");

					} else {
						System.out.println("error type2=="
								+ f.getType().toString());
					}

				}
			}
			System.out.println("---------deserialize-----------");
//			sss=in.get();
			System.out.println("byte modelType=in.get();");
			 
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				
				f.setAccessible(true); // 设置些属性是可以访问的
				Method[] methodlist = T.getDeclaredMethods();
				for (Method method : methodlist) {
					String mname = method.getName();
					if (mname.toLowerCase().equals(
							"get" + f.getName().toLowerCase())) {
						str="this."+f.getName()+"=in.get";
						if (f.getType() == int.class) {
							System.out.println(str + "Int();");
						} else if (f.getType() == long.class) {
							System.out.println(str + "Long();");
						} else if (f.getType() == boolean.class) {
							System.out.println("error");
						} else if (f.getType() == byte.class) {
							System.out.println(str + "();");
						} else if (f.getType() == String.class) {
							System.out.println(str + "PrefixedString();");
						} else if (f.getType() == Short.class || f.getType() == short.class) {
							System.out.println(str + "Short();");
						} else if (f.getType() == Timestamp.class){
							System.out.println(str + "Timestamp();");
						}else {
							System.out.println("error");
						}
					}
				}
			}
			System.out.println("---------export-----------");
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];

				f.setAccessible(true); // 设置些属性是可以访问的
				Method[] methodlist = T.getDeclaredMethods();
				for (Method method : methodlist) {
					String mname = method.getName();
					if (mname.toLowerCase().equals(
							"get" + f.getName().toLowerCase())) {
						System.out.println("System.out.println(\""+f.getName()+"==\"+"+mname+"());");
					}
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 测试enum
	 */
	public static void testEnum() {
		HeroOptType opt = HeroOptType.HERO_ADDSKILL;
		// System.out.println(HeroOptType.HERO_ADDSKILL.values());
		System.out.println(opt.ordinal());
		HeroOptType[] values = HeroOptType.values();
		for (HeroOptType op : values) {
			System.out.println("op=" + op);
		}
		System.out.println(HeroOptType.HERO_ADDSKILL.getValue());
		System.out.println(HeroOptType.HERO_ADDSKILL.name());
		System.out.println(HeroOptType.values()[3]);
	}

	// 生成插入sql语句
	public static void addSql(Class<?> T) throws InstantiationException,
			IllegalAccessException {
		System.out.println("---------insert-----------");
		String name = T.getSimpleName().toLowerCase();
		String str = "insert into " + name + " (";
		Object data = T.newInstance();// 创建对象
		Method[] methodlist = T.getDeclaredMethods();//获得方法
		Field[] fs = T.getDeclaredFields();// 获得属性
		List<String > list=new ArrayList<String>();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];

			f.setAccessible(true); // 设置些属性是可以访问的
			str += f.getName();
			if (i != fs.length - 1) {
				str += ",";
			}
			String sss="ps.";
			for (Method method : methodlist) {
				String mname = method.getName();
				if (mname.toLowerCase().equals(
						"get" + f.getName().toLowerCase())) {
					if (f.getType() == int.class) {
						sss += "setInt("+(i+1)+","+T.getSimpleName().toLowerCase()+"."+mname+"());";
					} else if (f.getType() == long.class) {
						sss += "setLong("+(i+1)+","+T.getSimpleName().toLowerCase()+"."+mname+"());";
					} else if (f.getType() == boolean.class) {
						sss += "setBoolean("+(i+1)+","+T.getSimpleName().toLowerCase()+"."+mname+"());";
					} else if (f.getType() == byte.class) {
						sss += "setByte("+(i+1)+","+T.getSimpleName().toLowerCase()+"."+mname+"());";
					} else if (f.getType() == String.class) {
						sss += "setString("+(i+1)+","+T.getSimpleName().toLowerCase()+"."+mname+"());";
					} else if (f.getType() == Short.class) {
						sss += "setShort("+(i+1)+","+T.getSimpleName().toLowerCase()+"."+mname+"());";
					} else {
						System.out.println("error");
					}
//					setString(1, user.getName());
				}
			}
			
			
			list.add(sss);	
		}
		str += ")values(";
		for (int i = 0; i < fs.length; i++) {
			str += "?";
			if (i != fs.length - 1) {
				str += ",";
			}
		}
		str += ")";
		System.out.println(str);
		// insert into user(username,passward)values(?,?)
		//	ps.setString(1, user.getName());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

	}

	// 生成删除sql语句
	public static void delSql(Class<?> T) throws InstantiationException,
			IllegalAccessException {
		// delete from playerhero where 1 = 1 and id = ?
	}

	public static void selectSql(Class<?> T) throws InstantiationException,
			IllegalAccessException {
		System.out.println("-----------select---------");
		Method[] methodlist = T.getDeclaredMethods();
		Field[] fs = T.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];

			f.setAccessible(true); // 设置些属性是可以访问的
			// System.out.println("name:"+f.getName()+"\t value = "+val);
			for (Method method : methodlist) {
				String mname = method.getName();
				String str = T.getSimpleName().toLowerCase() + "." + mname
						+ "(rs.";
				if (mname.toLowerCase().equals(
						"set" + f.getName().toLowerCase())) {
					if (f.getType() == int.class) {
						str += "getInt";
					} else if (f.getType() == long.class) {
						str += "getLong";
					} else if (f.getType() == boolean.class) {
						str += "getBoolean";
					} else if (f.getType() == byte.class) {
						str += "getByte";
					} else if (f.getType() == String.class) {
						str += "getString";
					} else if (f.getType() == Short.class) {
						str += "getShort";
					} else {
						System.out.println("error");
					}
					System.out.println(str + "(\"" + f.getName() + "\"));");
				}
			}
		}
		// playerHero.setName(rs.getString("name"));
	}

	/*
	 * 生成保存sql语句
	 */
	public static void saveSql(Class<?> T) throws InstantiationException,
			IllegalAccessException {
		System.out.println("-----------save---------");
		Object data = T.newInstance();

		String str = "update " + T.getSimpleName().toLowerCase() + " set ";
		Field[] fs = T.getDeclaredFields();
		Method[] methodlist = T.getDeclaredMethods();
		List<String> list = new ArrayList();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];

			f.setAccessible(true); // 设置些属性是可以访问的
			// String type = f.getType().toString();//得到此属性的类型

			// Object val = f.get(cla);//得到此属性的值
			if (f.getName().equals("id")) {
				continue;
			}
			// System.out.println("~~~~name="+f.getName());
			// System.out.println("value="+f.get(data));
			// System.out.println("type="+f.getType());
			// if(i%5==0){
			// str+="\n";
			// }
			str += f.getName() + "=?, ";
			// System.out.println("name:"+f.getName()+"\t value = "+val);
			for (Method method : methodlist) {
				String mname = method.getName();
				// System.out.println("方法＝"+method.getName());
				// if(mname.startsWith("set")||mname.startsWith("getId")){
				// continue;
				// }
				// System.out.println("====="+mname.toLowerCase());
				if (mname.toLowerCase().equals(
						"get" + f.getName().toLowerCase())) {
					list.add(T.getSimpleName().toLowerCase() + "." + mname
							+ "(),");
					break;
				}
				// System.out.println("player."+mname+"(),");
			}

		}
		// 找到最后一个“，”去除

		str += "where id=?";
		int index = str.lastIndexOf(',');
		String newStr = str.substring(0, index)
				+ str.substring(index + 1, str.length());

		// System.out.println("len="+str.length()+" index="+index);
		System.out.println(newStr);
		//
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

	}

	/**
	 * 增加一个玩家
	 */
	public static void testAddUser() {
		try {
			BaseUser user = new BaseUser();
			user.setName("aaaaaaaaa");
			user.setPassword("bbb");
			System.out.println(add(user));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static final String ADD_USER = "insert into user(username,passward)values(?,?)";

	public static int add(final BaseUser user) {
		try {
			DBManager db = DBManager.getInstance();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			db.getWorldDAO().getJdbcTemplate()
					.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(
								Connection conn) throws SQLException {
							PreparedStatement ps = conn.prepareStatement(
									ADD_USER, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, user.getName());
							ps.setString(2, user.getPassword());

							return ps;
						}

					}, keyHolder);

			return keyHolder.getKey().intValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
}
