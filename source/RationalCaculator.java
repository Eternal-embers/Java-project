package javaCode;

public class RationalCaculator {

	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("No arguments");
		}
		String s1 = new String(args[0]);
		String[] s2 = s1.split("+");
		System.out.println(s2.toString());
	}
}
