package javaCode;

public class StringClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("charAt(index)返回字符串中下标为index的字符");
		System.out.println("length()方法返回字符串的大小");
		System.out.println("substring方法返回一个字符串的子串");
		System.out.println("indexOf和lastIndexOf方法返回第一个或者最后一个匹配的字符或者字符串");
		System.out.println("equals和compareTo方法比较两个字符串");
		System.out.println("trim方法将字符串两端的空白字符裁剪掉");
		System.out.println("toLowerCase和toUpperCase分别返回字符串的小写和大写形式");
		//替换和拆分字符串
		System.out.println("replace、replaceFirst、replaceAll会返回一个源自原始字符串的新串(并未改变原字符串)");
		String s1 = "this is a master string";
		String s2 = s1.replace('a','A');
		String s3 = s1.replace("master","Match");
		String s4 = s1.replace('a', 'A');
		String s5 = s1.replaceAll("is","IS");
		System.out.println(s2);
		System.out.println(s3);
		System.out.println(s4);
		System.out.println(s5);
		//使用模式匹配、替换和拆分
		String s6 = "In Java,regulat expression is used to match string with special way";
		System.out.println("正则表达式用来描述一个字符串集的模式");
		System.out.println(s6.matches("[abc]"));
		System.out.println(s6.equals(s6));
		System.out.println("equals和matches方法都可以匹配字符串,但matches方法的功能更强大，"
				+ "它不仅可以匹配固定的字符串，还可以匹配一组遵从某种模式的字符串");
		//字符串与数组之间的转换
		char [] chars = s6.toCharArray();
		System.out.println(chars[chars.length-1]);
		char [] dst = new char[100];
		s6.getChars(7, chars.length, dst, 0);
		for(int i = 0;dst[i]!='\0';i++)
			System.out.print(dst[i]);
		System.out.println("");
		String s7 = new String(dst);
		
		System.out.println(s7);
		//将字符和数值转换为字符串
		System.out.println(String.valueOf(5.4423));
		//format可以格式化字符串
		System.out.println("String.format可以将多个变量格式化成一个字符串");
		String s8 = String.format("%3s%7.2f%6s%6s","男", 45.556,14,"赖清文");
		System.out.println(s8);
		//String和StringBuilder和StringBuffer的对比
		System.out.println("从可变性上讲，String是不可变的，StringBuilder，StringBuffer的长度是可变的");
		System.out.println("从运行速度上讲，StringBuilder > StringBuffer > String");
		System.out.println("从线程安全上讲，StringBuilder是不安全的，而StringBuffer是线程安全的");
		System.out.println("所以String适合少量的字符串操作的情况");
		System.out.println("StringBuilder适合单线程下字符缓冲区进行大量操作的情况");
		System.out.println("StringBuffer适合多线程下字符缓冲区进行大量操作的情况");
		System.out.println("String是不可变的，但是程序中String的字符串却可以改变，原因是生成了新的字符串而不是改变了原先字符串中的内容");
		System.out.println("相当与创建了新的串并让原先的字符串变量指向新的字符串");
	}
}
