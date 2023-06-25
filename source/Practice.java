package javaCode;

import java.util.Scanner;

public class Practice {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Scanner in = new Scanner(System.in);
		int a[] = new int[26];
		int max = 0;
		String s = in.next();
		for(int i = 0;i < s.length();i++)
			a[s.charAt(i) - 'a']++;
		for(int i = 0;i < 26;i++)
			if(a[max] < a[i]) max = i;
		System.out.println((char)('a' + max));
		System.out.println(a[max]);
	}

}
