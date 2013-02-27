package com.joymeng.game.test;

import com.joymeng.game.domain.role.PlayerCharacter;
import com.joymeng.game.domain.role.RoleData;

import groovy.lang.GroovyShell;

public class TestGroovy {
	public static void main(String[] args) {
		GroovyShell shell = new GroovyShell();
		String ruleStirng = "((parm1<456)||(time<(modifyTime-200))) && (money >10000)";
		try {
			Object result = null;
			shell.setProperty("money", 123);
			shell.setProperty("time", 456);
			shell.setProperty("modifyTime", 789);
			shell.setProperty("parm1", 123456);
			result = shell.evaluate(ruleStirng);
			System.out.println(result);
			PlayerCharacter pc=new PlayerCharacter();
			RoleData data=RoleData.create();
			pc.setData(data);
			shell.setProperty("data", data);
			ruleStirng="data.gameMoney<9999";
			result = shell.evaluate(ruleStirng);
			System.out.println(result);
		} catch (Exception cfe) {
			System.out.println("Syntax not correct" + cfe);
		}
	}

}
