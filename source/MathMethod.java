package javaCode;

import java.util.Scanner;

public class MathMethod {
	public static void main(String[] args) {
		//Math.random() -double
		double randnum = Math.random();//Math.random()为静态方法，返回一个大于等于0，小于1的double类型的随机数
		System.out.println(randnum);
		int a,b;
		Scanner input = new Scanner(System.in);
		System.out.print("a = ");
		a = input.nextInt();
		System.out.print("b = ");
		b = input.nextInt();
		int rand = (int)(Math.random()*a+b);//获取一个大于等于b，小于a+b的随机数
		System.out.println(rand);
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		//Math.PI/Math.E
		System.out.println("PI = " +Math.PI);
		System.out.println("e = " + Math.E);
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		//Math类的三角函数方法
		System.out.println(Math.sin(Math.PI));//Maht.sin(Radiant),参数单位弧度，返回double值
		System.out.println(Math.cos(Math.PI/2));//Math.cos(Radiant),参数单位弧度，返回double值
		System.out.println("tan(PI/4) = " + Math.tan(Math.PI/4));//Math.tan(Radiant),参数单位弧度，返回double值
		System.out.println("asin(1) = " + Math.asin(1));//Math.asin(Radiant),参数单位弧度，返回double值
		System.out.println("acos(0) = " + Math.acos(0));//Math.acos(Radiant),参数单位弧度，返回double值
		System.out.println("Math.atan(2) = " + Math.atan(1) + "\nPI/4 =" + Math.PI/4);//Math.atan(Radiant),参数单位弧度，返回double值
		System.out.println(Math.toRadians(360));//Math.toRadians(double) -double
		System.out.println(Math.toDegrees(Math.PI));//Math.toDegree(double) -double
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		//舍入方法
		System.out.println("ceil(double x) -double,向上舍入它最接近的整数");
		System.out.println("floor(double x) -double,向下舍入它最接近的整数");
		System.out.println("rint(double x) -double,舍入它最接近的整数，如果与两个整数的距离相等，偶数的那个返回");
		System.out.println("round(double x) -double,如果x是单精度，返回(int)Math.floor(x+0.5),如果x为双精度,返回(long)Math.floor(x+0.5)");
		System.out.println("ceil(2.1) = " + Math.ceil(2.1));
		System.out.println("ceil(2.6) = " + Math.ceil(2.6));
		System.out.println("ceil(-2.1) = " + Math.ceil(-2.1));
		System.out.println("ceil(-2.6) = " + Math.ceil(-2.6));
		System.out.println("floor(1.1) = " + Math.floor(1.1));
		System.out.println("floor(1.6) = " + Math.floor(1.6));
		System.out.println("floor(-1.1) = " + Math.floor(-1.1));
		System.out.println("floor(-1.6) = " + Math.floor(-1.6));
		System.out.println("rint(3.5) = " + Math.rint(3.5));
		System.out.println("rint(3.4) = " + Math.rint(3.4));
		System.out.println("rint(-2.6) = " + Math.rint(-2.6));
		System.out.println("rint(-2.5) = " + Math.rint(-2.5));
		System.out.println("round((float)1234567890000.4) = " + Math.round((float)1234567890000.4));
		System.out.println("round((double)1234567890000.4 = )" + Math.round(1234567890000.4));
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		//MIN,miX,abS
		System.out.println("min(1.1,1.3) = " + Math.min(1.1,1.3));
		System.out.println("mix(0.8,1.0) = " + Math.max(0.8,1.0));
		System.out.println("abs(-1.5) = " + Math.abs(1.5));
		System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -\n");
		//Math常用方法
		System.out.println(Math.exp(1));
		System.out.println(Math.log(Math.E));
		System.out.println(Math.pow(2, 10));
		System.out.println(Math.sqrt(3));
		System.out.println(Math.log10(10));
		input.close();//关闭Scanner类的input对象
	}
}
