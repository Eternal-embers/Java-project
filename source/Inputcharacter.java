package javaCode;

import java.util.Scanner;

public class Inputcharacter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		char ch1,ch2;
		System.out.println("next()不会读取字符前后的空格/Tab键，未开始前的空格/Tab/换行都不读取，\n"
				+ "只读取字符，当开始读取字符时，遇到空格/Tab键/回车键截至读取");
		System.out.println("nextline()读取空格/Tab键，有回车键时截取读取");
		System.out.println("如果要在输入数字后再输入字符串，要选择next(),nextLine()直接遇到换行会报错");
		ch1 = in.nextLine().charAt(0);
		ch2 = in.next().toCharArray()[0];
		System.out.println(ch1 + " " + ch2);
		in.close();
	}
}
