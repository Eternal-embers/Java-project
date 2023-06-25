package javaCode;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionMethod {
	public void testArithmeticException() throws ArithmeticException{
		System.out.println(1/0);
	}
	public void  testNullPointerException() throws NullPointerException{
		int [] arr = null;
		System.out.println(arr[0]);
	}
	public void testIndexOutOfBoundsException() throws IndexOutOfBoundsException{
		int arr[] = {1,2,3,4,5};
		System.out.println(arr[5]);
	}
	public void testIllegalArgumentException(int a,String str) throws IllegalArgumentException{
		System.out.println(a + str);
	}
	public void test() 
	throws ArithmeticException,NullPointerException,IndexOutOfBoundsException,IllegalArgumentException
	{
		testArithmeticException();
		testNullPointerException();
		testIndexOutOfBoundsException();
		testIllegalArgumentException(2,"hello");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExceptionMethod ob = new ExceptionMethod();
		try {
			ob.testArithmeticException();
		}
		catch(ArithmeticException ex){
			System.out.println(ex.getClass());//获取抛出异常的类
			System.out.println(ex.getCause());//获取异常的原因
			System.out.println(ex.getLocalizedMessage());//获取小范围的消息
			System.out.println(ex.getMessage());//获取异常的消息
			System.out.println(ex.toString());
			System.out.println(Arrays.toString(ex.getStackTrace()));//获取属于此可抛出对象的堆栈跟踪
			System.out.println(Arrays.toString(ex.getSuppressed()));//获取被抑制的异常
		}
		System.out.println("");
		try {
			ob.testNullPointerException();
		}
		catch(NullPointerException ex) {
			System.out.println(ex.getClass());
			System.out.println(ex.getCause());
			System.out.println(ex.getLocalizedMessage());
			System.out.println(ex.getMessage());
			System.out.println(ex.toString());
			System.out.println(Arrays.toString(ex.getStackTrace()));
			System.out.println(Arrays.toString(ex.getSuppressed()));
		}
		System.out.println("");
		try {
		ob.testIndexOutOfBoundsException();
		}
		catch(IndexOutOfBoundsException ex) {
			System.out.println(ex.toString());
		}
		System.out.println("");
		try {
		ob.testIllegalArgumentException(new Scanner(System.in).nextInt(),"hello");
		}
		catch(IllegalArgumentException | InputMismatchException ex ) {
			System.out.println(ex.getClass());
			System.out.println(ex.toString());
		}
	}

}
