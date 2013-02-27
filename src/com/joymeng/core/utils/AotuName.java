package com.joymeng.core.utils;

import com.joymeng.game.common.Instances;

public class AotuName implements Instances {
	private String name;
	private byte type;
	private int id;
	// private static Logger logger = LoggerFactory.getLogger(AotuName.class);
	static final String RES = "AotuName.xml";

	// public static void loadName(String path)
	// {
	// try
	// {
	// List<String> xingList = new ArrayList<String>();
	// List<String> maleList = new ArrayList<String>();
	// List<String> manList = new ArrayList<String>();
	// File file = new File(path + RES);
	// Document d;
	// d = XmlUtils.load(file);
	// Element[] elements1 = XmlUtils.getChildrenByName(d.getDocumentElement(),
	// "FristName");
	// Element[] elements2 = XmlUtils.getChildrenByName(d.getDocumentElement(),
	// "MaleName");
	// Element[] elements3 = XmlUtils.getChildrenByName(d.getDocumentElement(),
	// "ManName");
	//
	// for (Element e : elements1)
	// {
	// String xing = XmlUtils.getAttribute(e, "value");
	// xingList.add(xing);
	// }
	// for (Element e : elements2)
	// {
	// String male = XmlUtils.getAttribute(e, "value");
	// maleList.add(male);
	// }
	// for (Element e : elements3)
	// {
	// String man = XmlUtils.getAttribute(e, "value");
	// manList.add(man);
	// }
	// if(xingList.size() > 0){
	// for(String sx:xingList){
	// for(String male:maleList){
	// AotuName aotu = new AotuName();
	// aotu.setType((byte) 0);
	// aotu.setName(sx+male);
	// gameDao.loadName(aotu);
	// logger.info("load name=" + aotu.getName() + "type=" + aotu.getType());
	// }
	// for(String man:manList){
	// AotuName aotu = new AotuName();
	// aotu.setType((byte) 1);
	// aotu.setName(sx+man);
	// gameDao.loadName(aotu);
	// logger.info("load name=" + aotu.getName() + "type=" + aotu.getType());
	// }
	// }
	// }
	// }
	// catch(Exception e)
	// {
	// logger.error("AllyShop load error ! ", e);
	// }
	// }

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

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
