package javaCode;

import java.util.Scanner;

public class Employees {
	private int eno;
	private String ename;
	private int eage;
	public Employees() {
	}
	public Employees(int eno,String ename,int eage) throws EnoException{
		if(eno < 20 || eno > 60) throw new EnoException("Eno应该在20到60之间!");
		this.eno = eno;
		this.ename = ename;
		this.eage = eage;
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int eno,eage;
		String ename;
		System.out.print("eno:");
		eno = in.nextInt();
		System.out.print("ename:");
		ename = in.next();
		System.out.print("eage:");
		eage = in.nextInt();
		try{
		Employees em = new Employees(eno,ename,eage);
		}
		catch(EnoException ex) {
			System.out.println(ex.message);
		}
	}

}

class EnoException extends RuntimeException{
	String message;
	public EnoException() {
	}
	public EnoException(String message) {
		this.message = message;
	}
}
