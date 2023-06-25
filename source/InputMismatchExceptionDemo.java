package javaCode;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputMismatchExceptionDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		boolean continueInput = true;
		do {
			try {
			System.out.println("Enter an integer:");
			int number = in.nextInt();//如果输入错误则执行catch块中的代码
			System.out.println("The number entered is " + number);
			continueInput = false;//如果输入正确则结束输入
			}
			catch (InputMismatchException ex) {
				System.out.println("Try again.(" + 
				"Incorrect input:an integer is required)");
				in.nextLine();//Discard input;
			}
		}while(continueInput);//当接收到的是一个有效值时，结束输入
		in.close();
	}

}
