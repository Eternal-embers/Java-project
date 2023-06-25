package javaCode;

public class ChainedExceptionDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			method1();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Catch a exception from method1");
		}
		finally {
			System.out.println("Exception类继承自java.lang.Throwable");
			System.out.println("在任何情况下，finally块中的代码都会被执行");
			System.out.println("finally字句中的代码经常用于关闭文件及清理资源");
		}
	}
	public static void method1() throws Exception{
		try {
			method2();
		}
		catch(Exception ex) {
			System.out.println("catch a exception from method2");
			throw new Exception("New info from method1",ex);//重新抛出异常
		}
	}
	public static void method2() throws Exception{
		throw new Exception("New info from method2");
	}

}
