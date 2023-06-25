package javaCode;
import java.util.Scanner;
public class Reverse {
			public static void reverse(int number) {
				String str = Integer.toString(number);
				int len = str.length();
				for(int i = 0;i < len;i++)
					System.out.print(str.charAt(len-1-i));
					//注意此处len-1-i，因为str的下标为从0到len-i；
			}
			public static void main(String[] args) {
				Scanner input = new Scanner(System.in);
				int number = input.nextInt();
				reverse(number);
				input.close();
			}
}