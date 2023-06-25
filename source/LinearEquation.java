package javaCode;

import java.util.Scanner;

public class LinearEquation {
	//数据域
	private double a;
	private double b;
	private double c;
	private double d;
	private double e;
	private double f;
	//构造方法
	LinearEquation(){
	}
	
	//访问器
	public double getA() {
		return a;
	}
	public double getB() {
		return b;
	}
	public double getC() {
		return c;
	}
	public double getD() {
		return d;
	}
	public double getE() {
		return e;
	}
	public double getF() {
		return f;
	}
	//修改器
	public  void setA(double a) {
		this.a = a;
	}
	public  void setB(double b) {
		this.b = b;
	}
	public  void setC(double c) {
		this.c = c;
	}
	public  void setD(double d) {
		this.d = d;
	}
	public  void setE(double e) {
		this.e = e;
	}
	public  void setF(double f) {
		this.f = f;
	}
	//方法
	public double getX() {
		return (c*e-b*f)/(a*e-b*d);
	}
	public double getY() {
		return (c*d-a*f)/(b*d-a*e);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		LinearEquation equation = new LinearEquation(); 
		System.out.println("ax + by = c");
		System.out.println("dx + ey = f");
		System.out.print("a = ");
		equation.setA(in.nextDouble());
		System.out.print("b = ");
		equation.setB(in.nextDouble());
		System.out.print("c = ");
		equation.setC(in.nextDouble());
		System.out.print("d = ");
		equation.setD(in.nextDouble());
		System.out.print("e = ");
		equation.setE(in.nextDouble());
		System.out.print("f = ");
		equation.setF(in.nextDouble());
		System.out.println("x = " + equation.getX());
		System.out.println("y = " + equation.getY());
		in.close();
	}
}
