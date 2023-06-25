package javaCode;
import java.util.Scanner;
public class QuotienWithException {
	public static int quotient(int number1,int number2) {
		if(number2==0) 
			throw new ArithmeticException("Divisor cannot be zero");//抛出一个异常
		return number1/number2;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("异常类为java.lang.ArithemeticException");
		System.out.println("构造方法ArithemeticException(str)被调用以创建一个异常对象，str是描述异常的消息");
		System.out.println("try{");
		System.out.println("Code to run;");
		System.out.println("A statement or a method that may throw an exception;");
		System.out.println("More Code to run;}");
		System.out.println("catch(type ex){");
		System.out.println("Code to process the exception;}");
		Scanner in = new Scanner(System.in);
		System.out.print("Enter two integers：");
		int number1 = in.nextInt();
		int number2 = in.nextInt();
		try {
			int result = quotient(number1,number2);
			System.out.println(number1 + "/" + number2 + " is " + result);
		}//调用方法的语句包含在try块中，try包含了正常情况下执行的代码
		catch (ArithmeticException ex) {
			System.out.println("Exception：an integer " 
			+ "cannot be divisied by zero ");
		}//catch语句捕获异常，执行catch块中的代码以处理异常
		System.out.println("Execution continues...");
		in.close();
	}
}
