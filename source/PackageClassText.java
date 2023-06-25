package javaCode;

public class PackageClassText {
	public static void main(String[] args) {
		Integer x = Integer.valueOf(10);
		System.out.println(x.intValue());
		System.out.println(x.compareTo(4));
		Integer [] arr = {1,2,3};
		System.out.println(arr[0] + arr[1] + arr[2]);//对象自动拆箱再相加
		Double x1 = 3.9;
		System.out.println(x1.intValue());//向下取整
		System.out.println(x1.compareTo(4.5));
		
	}
}
