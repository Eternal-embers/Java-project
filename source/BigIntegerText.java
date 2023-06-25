package javaCode;

import java.math.BigInteger;

public class BigIntegerText {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BigInteger num1 = new BigInteger("92333344749947297497342232424290000");
		BigInteger num2 = new BigInteger("432408408083024801384080000");
		BigInteger add = num1.add(num2);//大数加法
		BigInteger negate = num1.negate();//返回大数负数
		BigInteger mult = num1.multiply(num2);//大数乘法
		BigInteger div = num1.divide(num2);//大数除法
		BigInteger mod = num2.mod(num1);//大数取余
		BigInteger gcd = num1.gcd(num2);//大数求最大公倍数
		BigInteger sqrt = num1.sqrt();
		BigInteger pow = num1.pow(3);//大数的指数
		String s = num1.toString(16);//大数进制转换
		System.out.println(add);
		System.out.println(negate);
		System.out.println(mult);
		System.out.println(div);
		System.out.println(mod);
		System.out.println(gcd);
		System.out.println(sqrt);
		System.out.println(pow);
		System.out.println(s);
	}
}
