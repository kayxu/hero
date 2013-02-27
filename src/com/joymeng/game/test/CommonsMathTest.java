package com.joymeng.game.test;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.random.RandomDataGenerator;

public class CommonsMathTest {

	private static RandomDataGenerator generator = new RandomDataGenerator();
	
	/**
	 * 在某个区间随机一个不是某一个数的随机数
	 * @param lower 区间最小数 包含lower
	 * @param upper 区间最大数 包含upper
	 * @param num 区间中不可选的数
	 * @return 随机 int 数
	 */
	public static int randomIntNotInclude(int lower,int upper,int num){
		
		if(lower == upper && upper == num){
			throw new IllegalArgumentException("three parameters should not equal");
		}
		int n = generator.nextInt(lower, upper);
		while(n == num){
			n = generator.nextInt(lower, upper);
		}
		return n;	
	}

	
	
	public static void main(String[] args){
		Complex lhs = new Complex(1.0, 3.0);
		Complex rhs = new Complex(2.0, 5.0);

		Complex answer = lhs.add(rhs);       // add two complex numbers
		        answer = lhs.subtract(rhs);  // subtract two complex numbers
		     double   d = lhs.abs();          // absolute value
		        answer = lhs.conjugate(); // complex conjugate
		/*test();
		System.out.println("_____________________");
		test();*/
		
	/*	for(int i = 0;i < 100;i++){
			System.out.println(randomIntNotInclude(1,2,2));
		}*/
		
		//从List中随机一定数量数据
	/*	List<Integer> intList = new ArrayList<Integer>();
		for(int i = 0;i< 100;i++){
			intList.add(i);
		}

		Object[] rdArray = generator.nextSample(intList, 8);
		for(Object o : rdArray){
			System.out.println((int) o);
		}*/
		
		//生成一个长度为k，数据0到n-1的打乱的int型数组
		int[] a = generator.nextPermutation(20, 20);
		for(int i : a){
			System.out.print( i + "  ");
		}
		
		//System.out.println(generator.nextHexString(20));
		
		
	}
	
	
	
	public static void test(){
		generator.reSeed(1000);
		for(int i=0;i<10;i++){
			System.out.println(generator.nextInt(0, 100));
		}
	}
}
