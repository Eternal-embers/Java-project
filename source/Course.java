package javaCode;

import java.util.Scanner;

public class Course {
	private String cno;
	private String cname;
	private double credit;
	public Course() {
	}
	public Course(String cno,String cname,double credit) throws creditException {
		if(credit < 0.5 || credit > 6) throw new creditException("信用应该在0.5到6之间!");
		this.cno = cno;
		this.cname= cname;
		this.credit = credit;
		
	}
	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			String cno,cname;
			double credit;
			System.out.print("cno:");
			cno = in.next();
			System.out.print("cname:");
			cname = in.next();
			System.out.print("credit:");
			credit = in.nextDouble();
			Course ob = new Course(cno,cname,credit);
		}
		catch(creditException ex) {
			System.out.println(ex.message);
		}
	}

}

class creditException extends RuntimeException {
	String message;
	public creditException() {
	}
	public creditException(String message) {
		this.message = message;
	}
}