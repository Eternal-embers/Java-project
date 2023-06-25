package javaCode;

public class MyInteger {
	private int value = 0;
	public MyInteger() {
	}
	public MyInteger(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}//获取MyInteger对象的值
	public void setValue(int value) {
		this.value = value;
	}//
	public  boolean isEven(int integer) {
		if(integer%2==0) return false;
		return true;
	}//判断Integer对象是否为奇数
	public  boolean isOdd(int integer) {
		if(integer%2==0) return true;
		return false;
	}//判断Integer对象是否为偶数
	public boolean isPrime(int integer) {
		int i,t = integer;
		for( i = 2;i <= Math.sqrt(t);i++) {
			if(t%i==0) break;
		}
		if(i > Math.sqrt(t)) return true;
		return false;
	}//判断Integer对象是否为素数
	public static boolean isEven(MyInteger obj) {
		if(obj.value%2==0) return false;
		return true;
	}//判断MyInteger.value是否为奇数
	public static boolean isOdd(MyInteger obj) {
		if(obj.value%2==0) return true;
		return false;
	}//判断MyInteger.value是否为偶数
	public static boolean isPrime(MyInteger obj) {
		int i,t = obj.value;
		for( i = 2;i <= Math.sqrt(t);i++) {
			if(t%i==0) break;
		}
		if(i > Math.sqrt(t)) return true;
		return false;
	}//判断MyInteger.value是否为素数
	public boolean equals(int value) {
		if(this.value==value) return true;
		return false;
	}//判断所指定的值是否与对象的值相等
	public  boolean equals(MyInteger obj) { 
		if(obj.value==this.value) return true;
		return false;
	}//判断指定的对象的值是否与该对象的值相等
	public static int ParseInt(char[] s) {
		String str = new String(s);//将字符数组转换为字符串
		return Integer.valueOf(str);
	}//将字符数组转换为int值
	public static int ParseInt(String s) {
		return Integer.valueOf(s);
	}//将字符串转换为int值
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyInteger integer = new MyInteger();
		MyInteger i = new MyInteger(30);
		char [] chArr = {'1','3','1','4','5','2','0'};
 		integer.setValue(100);
		System.out.println(integer.getValue());
		System.out.println("23 is Even? " + integer.isEven(23));
		System.out.println("20 is Odd? " + integer.isOdd(20));
		System.out.println("17 is prime? " + integer.isPrime(17));
		System.out.println("object integer is Even? " + isEven(integer));
		System.out.println("object integer is Odd? " + isOdd(integer));
		System.out.println("object interger is prime? " + isPrime(integer));
		System.out.println("100 equals to integer? " + integer.equals(100));
		System.out.println("100 equals to object integer? " + integer.equals(i));
		System.out.println(ParseInt(chArr));
		System.out.println(String.valueOf(chArr));//调用String类的valueof方法
		System.out.println(Integer.parseInt("520"));
	}
}
