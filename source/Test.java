package javaCode;

public class Test {
	private static int i = 0;
	private static int j = 0;
	public static void main(String[] args) {
		int i = 2;//局部变量i导致实例变量i被隐藏
		int k = 3;
		{
			int j = 3;//作用域仅限于此括号块内
			System.out.println(i + " " + j);//局部变量和实例变量同名会隐藏实例变量
		}
		k = i + j;//此处的i为局部变量，此处的j为实例变量
		System.out.println("k is " + k);//
		System.out.println("j is " + j);//输出实例变量
	}
}
