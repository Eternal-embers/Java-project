package JavaInput;
import java.math.BigInteger;
import java.util.Scanner;

public class JavaInput {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner input = new Scanner(System.in);
		byte a = input.nextByte();
		short b = input.nextShort();
		int c = input.nextInt();
		long d = input.nextLong();
		BigInteger e = input.nextBigInteger();
		float f = input.nextFloat();
		double g = input.nextDouble();
		String h = input.next();
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println(g);
		System.out.println(h);
		input.close();
	}

}
