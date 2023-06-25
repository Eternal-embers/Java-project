package javaCode;

import java.math.BigInteger;
import java.util.Scanner;

public class LargeFactorial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an integer:");
		int n = in.nextInt();
		System.out.println(n + "! is " + factorial(n));
	}
	public static BigInteger factorial(long n) {
		BigInteger result = BigInteger.ONE;
		for(int i = 1;i <= n;i++)
			result = result.multiply(new BigInteger(i + ""));
		return result;
	}
}
