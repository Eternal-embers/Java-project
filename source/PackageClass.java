package javaCode;

public class PackageClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("java.lang提供包装类，可以将基本数据类型作为对象处理");
		System.out.println("包装类和基础数据类型很相似，除了首字母为大写外");
		System.out.println("java.lang包中提供了Boolean,Character,Double,Float,"
				+ "Byte,Short,Integer,Long等包装类");
		System.out.println("每个类中都包含了doubleValue()、floatValue()、intValue()、longValue()等方法，"
				+ "这些方法可以将对象转换为基本类型值,这些方法返回包装对象对应的基本数据值");
		System.out.println("包装类无构造方法，所有的包装类的实例都是不可变的，"
				+ "这意味着一旦创建对象后，它们内部值就不可改变");
		System.out.println("数值包装类中包含了compareTo方法用于比较两个数值，"
				+ "compareTo(a,b),如果a大于或小于或等于b，其返回值分别是1，-1，0");
		System.out.println("数值包装类中有一个有用的静态方法valueOf(String s)，"
				+ "该方法创建一个对象并将它初始化为指定字符串表示的值");
		System.out.println("Integer类中的parseInt方法将一个数值字符串转换为一个int值，"
				+ "pareDouble方法将一个数值字符串转换为一个double值");
		int i;
		double d;
		System.out.println("Integer.parseInt(\"String s\",radix)");
		System.out.println(Integer.parseInt("11",2));//将二进制的数转换为十进制
		System.out.println(Integer.parseInt("12",8));//将八进制的数转换为十进制
		System.out.println(Integer.parseInt("1A",16));//将十六进制的数转换为十进制
		System.out.println(Integer.parseInt("22",3));//将三进制的数转换为十进制
		System.out.println(Integer.parseInt("33",6));//将六进制的数转换为十进制
		System.out.println(Integer.parseInt("11",16));//将十六进制的数转换为十进制
		i = Integer.parseInt("123456789");
		d = Double.parseDouble("3.1415926");
		System.out.println(i);
		System.out.println(d);
		System.out.println("String.format(String s,args)可以将args转换成其他进制的一个字符串");
		System.out.println(String.format("%x",26));//十六进制
		System.out.println(String.format("%o", 127));//八进制
		System.out.println(String.format("%b", 12));//boolean类型
	}

}
