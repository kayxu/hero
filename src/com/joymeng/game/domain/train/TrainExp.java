package com.joymeng.game.domain.train;

import java.lang.reflect.Method;
import java.util.HashMap;

public class TrainExp {
	private int level;
	private int build1;
	private int build2;
	private int build3;
	private int build4;
	private int build5;
	private int build6;
	private int build7;
	private int build8;
	private int build9;
	private int build10;
	private int build11;
	private int build12;
	private int build13;
	private int build14;
	private int build15;
	private int build16;
	private int build17;
	private int build18;
	private int build19;
	private int build20;
	private int build21;
	private int build22;
	private int build23;
	private int build24;
	private int build25;
	private int build26;
	private int build27;
	private int build28;
	private int build29;
	private int build30;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBuild1() {
		return build1;
	}

	public void setBuild1(int build1) {
		this.build1 = build1;
	}

	public int getBuild2() {
		return build2;
	}

	public void setBuild2(int build2) {
		this.build2 = build2;
	}

	public int getBuild3() {
		return build3;
	}

	public void setBuild3(int build3) {
		this.build3 = build3;
	}

	public int getBuild4() {
		return build4;
	}

	public void setBuild4(int build4) {
		this.build4 = build4;
	}

	public int getBuild5() {
		return build5;
	}

	public void setBuild5(int build5) {
		this.build5 = build5;
	}

	public int getBuild6() {
		return build6;
	}

	public void setBuild6(int build6) {
		this.build6 = build6;
	}

	public int getBuild7() {
		return build7;
	}

	public void setBuild7(int build7) {
		this.build7 = build7;
	}

	public int getBuild8() {
		return build8;
	}

	public void setBuild8(int build8) {
		this.build8 = build8;
	}

	public int getBuild9() {
		return build9;
	}

	public void setBuild9(int build9) {
		this.build9 = build9;
	}

	public int getBuild10() {
		return build10;
	}

	public void setBuild10(int build10) {
		this.build10 = build10;
	}

	public int getBuild11() {
		return build11;
	}

	public void setBuild11(int build11) {
		this.build11 = build11;
	}

	public int getBuild12() {
		return build12;
	}

	public void setBuild12(int build12) {
		this.build12 = build12;
	}

	public int getBuild13() {
		return build13;
	}

	public void setBuild13(int build13) {
		this.build13 = build13;
	}

	public int getBuild14() {
		return build14;
	}

	public void setBuild14(int build14) {
		this.build14 = build14;
	}

	public int getBuild15() {
		return build15;
	}

	public void setBuild15(int build15) {
		this.build15 = build15;
	}

	public int getBuild16() {
		return build16;
	}

	public void setBuild16(int build16) {
		this.build16 = build16;
	}

	public int getBuild17() {
		return build17;
	}

	public void setBuild17(int build17) {
		this.build17 = build17;
	}

	public int getBuild18() {
		return build18;
	}

	public void setBuild18(int build18) {
		this.build18 = build18;
	}

	public int getBuild19() {
		return build19;
	}

	public void setBuild19(int build19) {
		this.build19 = build19;
	}

	public int getBuild20() {
		return build20;
	}

	public void setBuild20(int build20) {
		this.build20 = build20;
	}

	public int getBuild21() {
		return build21;
	}

	public void setBuild21(int build21) {
		this.build21 = build21;
	}

	public int getBuild22() {
		return build22;
	}

	public void setBuild22(int build22) {
		this.build22 = build22;
	}

	public int getBuild23() {
		return build23;
	}

	public void setBuild23(int build23) {
		this.build23 = build23;
	}

	public int getBuild24() {
		return build24;
	}

	public void setBuild24(int build24) {
		this.build24 = build24;
	}

	public int getBuild25() {
		return build25;
	}

	public void setBuild25(int build25) {
		this.build25 = build25;
	}

	public int getBuild26() {
		return build26;
	}

	public void setBuild26(int build26) {
		this.build26 = build26;
	}

	public int getBuild27() {
		return build27;
	}

	public void setBuild27(int build27) {
		this.build27 = build27;
	}

	public int getBuild28() {
		return build28;
	}

	public void setBuild28(int build28) {
		this.build28 = build28;
	}

	public int getBuild29() {
		return build29;
	}

	public void setBuild29(int build29) {
		this.build29 = build29;
	}

	public int getBuild30() {
		return build30;
	}

	public void setBuild30(int build30) {
		this.build30 = build30;
	}
	/**
	 * 通过方法名获得对应返回值
	 * @param methodName
	 * @return
	 */
	public int fromMethodName(TrainExp t,String methodName){
		try {
			Class c = Class.forName(t.getClass().getName());
			Method m1 = c.getMethod(methodName); 
			Object returnS = m1.invoke(t, new Object[]{});  
			return (Integer)returnS;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	public static void main(String[] args) {
		int[] a = new int[2];
		a[0] = 1 ;
		a[1] = 2;
		HashMap<Integer,int[]> trainExpMap  = new HashMap<Integer, int[]>();
		trainExpMap.put(1, a);
		int[] tt = trainExpMap.get(1);
		System.out.println(tt[0]+""+tt[1]);
		for(int[] s : trainExpMap.values()){
			for(int i = 0 ; i < s.length ; i++){
				s[i] = s[i]*2;
			}
		}
		int[] ttt = trainExpMap.get(1);
		System.out.println(ttt[0]+""+ttt[1]);
	}
}
