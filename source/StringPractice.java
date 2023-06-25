package javaCode;

public class StringPractice {
	public static void main(String[] args) {
		String s1 = "Hello,";
		String s2 = "world!";
		String s = s1 + s2;
		System.out.println(s);
		System.out.println("1" + 1);//输出一个字符串
		System.out.println('1' + 1);//输出一个整数
		System.out.println("1" + 1 + 1);//输出一个字符串
		System.out.println("1" + (1+1));//输出一个字符串
		System.out.println('1' + (1+1));//输出一个整数
		System.out.println("Welcome" + 1 + 1);
		System.out.println(1 + "Welcomd" + ('\u0001'+1));
	}
}
