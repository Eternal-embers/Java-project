package javaCode;

import java.util.Scanner;

//判断回文串时忽略既非字母又非数字的字符
public class PalindromeIgnoreNonAlphanumeric {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a String:");
		String s = in.nextLine();
		System.out.println("Ignoring nonalphanumeric characters,\nis \"" + s + "\" a palindromes?\n" + isPalindrome(s));
		in.close();
	}
	public static boolean isPalindrome(String s) {
		String s1 = filter(s);//获取只有字母和数字的字符串
		String s2 = reverse(s1);//获取倒置的字符串
		return s1.equals(s2);//如果倒置的字符串s2和s1相同，则为回文串
	}
	public static String filter(String s) {
		StringBuilder str = new StringBuilder();
		for(int i = 0;i<s.length();i++)
			//如果是字母或数字则将其添加到str字符串中
			if(Character.isLetterOrDigit(s.charAt(i))) {
				str.append(s.charAt(i));
			}
		return str.toString();//返回一个只有字母和数字的字符串
	}
	public static String reverse(String s) {
		StringBuilder str = new StringBuilder(s);
		str.reverse();//将字符串倒置
		return str.toString();//返回倒置的字符串
	}
}
