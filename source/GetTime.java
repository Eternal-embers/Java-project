package javaCode;

public class GetTime {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("nanoTime()方法在java.lang包中可用");
		System.out.println("nanoTime()方法用于返回当前系统时间的值(以纳秒为单位)");
		System.out.println("nanoTime()方法是静态的，可以使用类名进行访问");
		System.out.println("nanoTime()方法不会引起任何异常");
		System.out.println("nanoTime只能用于计算时间差,不能用于计算距离现在的时间，因为数量级太小");
		long millSeconds = System.nanoTime();
		System.out.println(millSeconds);
	}

}
