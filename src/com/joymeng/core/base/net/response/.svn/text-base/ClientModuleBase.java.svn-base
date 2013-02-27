package com.joymeng.core.base.net.response;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.joymeng.game.net.client.ClientModule;
import com.joymeng.services.core.buffer.JoyBuffer;
import com.joymeng.services.core.message.JoyProtocol;

public class ClientModuleBase implements ClientModule, java.io.Serializable {

	@Override
	public byte getModuleType() {
		return 0;
	}

	@Override
	public final void serialize(JoyBuffer out) {
		int index = out.position();
		out.skip(JoyProtocol.BYTE_LENGTH); // 4个字节长度
		out.put(getModuleType());
		_serialize(out);
		out.putInt(index, out.position() - index - JoyProtocol.BYTE_LENGTH);

	}

	@Override
	public void deserialize(JoyBuffer in) {

	}

	public void _serialize(JoyBuffer out) {

	}
	/**
	 * 输出对象的属性数值
	 * @return
	 */
	public String print() {
		String s = "";
		try {
//			s = getPropertyString(this);
			return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	public String getPropertyString(Object entityName) throws Exception {
		Class c = entityName.getClass();
		Field field[] = c.getDeclaredFields();
		StringBuilder sb = new StringBuilder();

//		sb.append("------ " + " begin ------\n");
		sb.append("------ ").append(c.getName()).append("\n");
		for (Field f : field) {
			sb.append(f.getName());
			sb.append(" : ");
			sb.append(invokeMethod(entityName, f.getName(), null));
			sb.append(" | ");
		}
//		com.joymeng.core.utils.StringUtil.append(this.sb, strings);
//		sb.append("------ " + " end ------\n");
		return sb.toString();
	}


	public Object invokeMethod(Object owner, String methodName, Object[] args)
			throws Exception {
		Class ownerClass = owner.getClass();

		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			return " can't find 'get" + methodName + "' method";
		}
		return method.invoke(owner);
	}
}
