package com.joymeng.game.domain.hero.data;

import org.w3c.dom.Element;
/**
 * 将领姓名表
 * @author admin
 * @date 2012-6-1
 * TODO
 */
public class HeroName {
	private int id;
	private String name;
	private byte type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * Element转化成实例对象
	 * 
	 * @param element
	 * @throws Exception
	 */
	public void load(Element element) throws Exception {

	}
}
