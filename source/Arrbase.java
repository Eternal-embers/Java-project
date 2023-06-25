package javaCode;
public class Arrbase {
	public static void main(String[] args) {
		System.out.println("一旦数组被创建，它的大小就固定了"
				+ "可以用一个数组引用变量和下标来访问数组中的元素");
		System.out.print("声明数组的语法:elementType [] var;");
		System.out.println("或者elementType var[];");
		//创建数组
		double arr[] = new double[20];
		System.out.println("数值型基本数据类型的默认值为0，而char型的默认值为\u0000,boolean型的默认值为false");
		//获取数组长度
		System.out.println(arr.length);
		for(int i = 0;i < 20;i++)
			arr[i] = Math.random()*1000;
		for(double d: arr)//不使用下标变量就可以遍历整个数组
		{
			System.out.println(d);
		}
	}
}
