package javaCode;

public class CatchExceptinoInMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0;i<2;i++) {
			System.out.print(i + " ");
			try {
				System.out.println(1/0);
			}
			catch(Exception ex) {
				System.out.println("catch exception");
			}//由于捕获异常在for循环内，所以捕获异常后还可以继续执行for循环
		}
		try {
			for(int i = 0;i<2;i++) {
				System.out.print(i + " ");
				System.out.println(1/0);
			}
		}//如果出现异常直接执行catch块
		catch(Exception ex) {
			System.out.println("catch Exceptino");
		}
	}

}
