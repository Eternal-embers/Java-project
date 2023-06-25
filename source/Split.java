package javaCode;

public class Split {
	public static void main(String[] args) {
		String s1 = "hello world sort search";
		String split1 = " ";
		String s2 = "quick-artistic-efficient-friendly";
		String split2 = "-";
		String []group1 = s1.split(" ");
		String []group2 = s2.split("-");
		System.out.println(group1.length);
		System.out.println(group2.length);
		for(String s:group1) {
			System.out.print(s + " ");
		}
		System.out.println("");
		for(String s:group2) {
			System.out.print(s + " ");
		}
	}
}
