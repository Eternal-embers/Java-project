package javaCode;

import java.util.Scanner;
public class decimalToHex {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("please input a decimal:");
		int decimal = input.nextInt();
		String hex = "";
		while(decimal!=0) {
		int hexValue = decimal % 16;
		char hexDigit = (0<=hexValue && hexValue<=9) ? (char)(hexValue + '0'):(char)(hexValue - 10 + 'A');
		hex = hexDigit + hex;//先求出的为低位，所以放在后面，尾插
		decimal /= 16;
		}
		System.out.println("The hex number is " + hex);
		input.close();
	}
}
