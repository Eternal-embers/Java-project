package javaCode;
import java.util.Scanner;
public class QuadraticEquation {
	private double a;
	private double b;
	private double c;
	public QuadraticEquation() {
	}
	public double getA() {
		return a;
	}
	public double getB() {
		return b;
	}
	public double getC() {
		return c;
	}
	public void setA(double a) {
		this.a = a;
	}
	public void setB(double b) {
		this.b = b;
	}
	public void setC(double c) {
		this.c = c;
	}
	public double getDelta() {
		return b*b-4*a*c;
	}
	public double getRoot1() {
		return (-b+Math.sqrt(getDelta()))/2*a;
	}
	public double getRoot2() {
		return (-b-Math.sqrt(getDelta())/2*a);
	}
	public String getImaginaryRoot1() {
		return -b/2*a + "+" + Math.sqrt((4*a*c - b*b)/a)/2 + "* i";
	}
	public String getImaginaryRoot2() {
		return -b/2*a + "-" + Math.sqrt((4*a*c - b*b)/a)/2 + "* i";
	}
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		QuadraticEquation equation1 = new QuadraticEquation();
		System.out.println("a*x*x+b*x+c = 0");
		System.out.print("a = ");
		equation1.setA(in.nextDouble());
		System.out.print("b = ");
		equation1.setB(in.nextDouble());
		System.out.print("c = ");
		equation1.setC(in.nextDouble());
		if(equation1.getDelta()>0) {
			System.out.println("Root1 = " + equation1.getRoot1());
			System.out.println("Root2 = " + equation1.getRoot2());
		}
		else if(equation1.getDelta()==0) System.out.println("Root1 = Root2 = " + equation1.getRoot1());
		else {
			System.out.println("Delta = " + equation1.getDelta() + " 方程无实数根");
			System.out.println("ImaginaryRoot1 = " + equation1.getImaginaryRoot1());
			System.out.println("ImaginaryRoot2 = " + equation1.getImaginaryRoot2());
		}
	}
}
