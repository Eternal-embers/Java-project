package javaCode;

import java.math.BigDecimal;

public class BigDecimalText {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigDecimal num1 = new BigDecimal("0.33333333333333333333333333");
		BigDecimal num2 = new BigDecimal("0.44444444444444444444444444");
		BigDecimal add = num1.add(num2);//大数加法
		BigDecimal negate = num1.negate();//返回大数负数
		BigDecimal mult = num1.multiply(num2);//大数乘法
		BigDecimal div = num1.divide(num2);//大数除法
		//BigDecimal sqrt = num1.sqrt(null);
		BigDecimal pow = num1.pow(3);//大数的指数
		String s = num1.toString();//大数进制转换
		System.out.println(add);
		System.out.println(negate);
		System.out.println(mult);
		System.out.println(div);
		//System.out.println(sqrt);
		System.out.println(pow);
		System.out.println(s);
	}
}
