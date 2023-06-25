package javaCode;

import java.util.Scanner;

public class Palindrome {
	public static void main(String[] args) {
		int i;
		Scanner input = new Scanner(System.in);
		System.out.print("num1 = ");
		int num1 = input.nextInt();
		System.out.print("num2 = ");
		int num2 = input.nextInt();
		String s1 = String.valueOf(num1);//第一种方法将整数转化为字符串
		String s2 = Integer.toString(num2);//第二种方法将整数转化为字符串
		System.out.println(s1);
		System.out.println(s2);
		int len1 = s1.length();
		int len2 = s2.length();
		System.out.println(len1 + "," + len2);
		char ch1,ch2;
		//判断s1
		for(i = 0;i<len1-i;i++) {
			ch1 = s1.charAt(i);
			ch2 = s1.charAt(len1-1-i);//最后一个下标为len2-i
			System.out.println(ch1+ "-" + ch2);
			if(ch1!=ch2) {
				System.out.printf(s1 + "不是回文数");
				break;
			}
		input.close();
		}
		System.out.println(i);
		System.out.println(len1-i);
		if(i>=len1-i) System.out.println(s1 + "是回文数");
		//判断s2
		for(i = 0;i<len2-i;i++) {
			ch1 = s2.charAt(i);
			ch2 = s2.charAt(len2-1-i);
			System.out.println(ch1+ "-" + ch2);
			if(ch1!=ch2) {
				System.out.printf(s2 + "不是回文数");
				break;
			}
		}
		System.out.println(i);
		System.out.println(len2-i);
		if(i>=len2-i) System.out.println(s2 + "是回文数");
	}
}
