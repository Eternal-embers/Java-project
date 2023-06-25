package javaCode;

public class visibilityModifier {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.printf("%12s%12s%12s%12s%12s\n","修饰符","同一类内访问","在同一包内访问","在不同包中的子类可访问","在不同包中可访问");
		System.out.printf("%15s%15d%15d%15d%15d\n","public",1,1,1,1);
		System.out.printf("%15s%15d%15d%15d%15d\n","protected",1,1,1,0);
		System.out.printf("%15s%15d%15d%15d%15d\n","default",1,1,0,0);
		System.out.printf("%15s%15d%15d%15d%15d\n","private",1,0,0,0);		
	}

}
