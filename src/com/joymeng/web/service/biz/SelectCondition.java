package com.joymeng.web.service.biz;

public class SelectCondition {

	public static String getSelectValue(String key){
		if(key == null){
			return " 1 ";
		}
		switch (key) {
		case "0":
			return " level ";
		case "1":
			return " gameMoney ";
		case "2":
			return " joyMoney ";
		case "3":
			return " name ";
		case "4":
			return " userid ";
		case "5":
			return " award ";
		case "6":
			return " chip ";
		case "7":
			return " achieve ";
		case "8":
			return " militaryMedals ";
		case "9":
			return " cityLevel ";
		case "10":
			return " title ";
		case "11":
			return " createTime ";
		case "12":
			return " lastLoginTime ";
		default:
			return " 1 ";
		}
	}
	
	/**
	 * 获得 符号
	 * @param key
	 * @return
	 */
	public static String getSelectSymbol(String key){
		if(key == null){
			return " != ";
		}
		switch (key) {
		case "0":
			return " > ";
		case "1":
			return " = ";
		case "2":
			return " < ";
		default:
			return " != ";
		}
	}
}
