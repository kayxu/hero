package com.joymeng.game.test;
import junit.framework.Assert;

import org.junit.Test;
import org.apache.commons.math3.random.RandomDataGenerator;

public class RandomTest {
	
	@Test 
	public void testReseed(){//测试不使用reseed方法
		RandomDataGenerator generator = new RandomDataGenerator();
		int num1 = generator.nextInt(0, 100);
		int num2 = generator.nextInt(0, 100);
		Assert.assertTrue(num1 - num2 != 0);//不是每次都成功
	}
	
	@Test 
	public void testReseed1(){//测试reseed方法
		RandomDataGenerator generator = new RandomDataGenerator();
		generator.reSeed(1000);
		int num1 = generator.nextInt(0, 100);
		generator.reSeed(1000);
		int num2 = generator.nextInt(0, 100);
		Assert.assertEquals(num1 - num2, 0);
	}
	
	@Test
	public void testNextInt(){//确认nextInt()是否包含lower边界
		RandomDataGenerator generator = new RandomDataGenerator();
		int lower = 0;
		int upper = 2;
		int num = -1;
		for(int i = 0;i < 100; i++){
			num = generator.nextInt(lower, upper);
			if(num == lower){
				break;
			}
		}
		Assert.assertEquals(lower,num);//不是每次都成功,一次成功就满足
	}
	
	@Test
	public void testNextInt1(){//确认nextInt()是否包含upper边界
		RandomDataGenerator generator = new RandomDataGenerator();
		int lower = 0;
		int upper = 2;
		int num = -1;
		for(int i = 0;i < 100; i++){
			num = generator.nextInt(lower, upper);
			if(num == upper){
				break;
			}
		}
		Assert.assertEquals(upper,num);//不是每次都成功,一次成功就满足
	}
	
}
