package javaCode;
import java.util.Scanner;
public class TernaryLinearEquation {
	private double a;
	private double b;
	private double c;
	private double d;
	private double e;
	private double f;
	private double g;
	private double h;
	private double i;
	private double j;
	private double k;
	private double l;
	TernaryLinearEquation(){
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
	public void setD(double d) {
		this.d = d;
	}
	public void setE(double e) {
		this.e = e;
	}
	public void setF(double f) {
		this.f = f;
	}
	public void setG(double g) {
		this.g = g;
	}
	public void setH(double h) {
		this.h = h;
	}
	public void setI(double i) {
		this.i = i;
	}
	public void setJ(double j) {
		this.j = j;
	}
	public void setK(double k) {
		this.k = k;
	}
	public void setL(double l) {
		this.l = l;
	}
	public double getX() {
		return ((f*j*d-b*j*h)*(b*j*g-b*f*k)-(f*j*c-b*j*g)*(b*j*h-b*f*l))
				/((f*j*a-b*j*e)*(b*j*g-b*f*k)-(f*j*c-b*j*g)*(b*j*e-b*f*i));
	}
	public double getY() {
		return ((e*i*d-a*i*h)*(a*i*g-a*e*k)-(e*i*c-a*i*g)*(a*i*h-a*e*l))
				/((e*i*b-a*i*f)*(a*i*g-a*e*k)-(e*i*c-a*i*g)*(a*i*f-a*e*j));
	}
	public double getZ() {
		return ((e*i*d-a*i*h)*(a*i*f-a*e*j)-(e*i*b-a*i*f)*(a*i*h-a*e*l))
				/((e*i*c-a*i*g)*(a*i*f-a*e*j)-(e*i*b-a*i*f)*(a*i*g-a*e*k));
	}
	public double check() {
		return ((g*k*d-c*k*h)*(c*k*f-c*g*j)-(g*k*b-c*k*f)*(c*k*h-c*g*l))
				/((g*k*a-c*k*e)*(c*k*f-c*g*j)-(g*k*b-c*k*f)*(c*k*e-c*g*i));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		TernaryLinearEquation equation = new TernaryLinearEquation();
		System.out.println("\tax + by + cz = d");
		System.out.println("\tex + fy + gz = h");
		System.out.println("\tix + jy + kz = l");
		System.out.println("");
		System.out.println("\tax + by + cz = d");;
		System.out.print("\ta = ");
		equation.setA(in.nextDouble());
		System.out.print("\tb = ");
		equation.setB(in.nextDouble());
		System.out.print("\tc = ");
		equation.setC(in.nextDouble());
		System.out.print("\td = ");
		equation.setD(in.nextDouble());
		System.out.println("\tex + fy + gz = h");
		System.out.print("\te = ");
		equation.setE(in.nextDouble());
		System.out.print("\tf = ");
		equation.setF(in.nextDouble());
		System.out.print("\tg = ");
		equation.setG(in.nextDouble());
		System.out.print("\th = ");
		equation.setH(in.nextDouble());
		System.out.println("\tix + jy + kz = l");
		System.out.print("\ti = ");
		equation.setI(in.nextDouble());
		System.out.print("\tj = ");
		equation.setJ(in.nextDouble());
		System.out.print("\tk = ");
		equation.setK(in.nextDouble());
		System.out.print("\tl = ");
		equation.setL(in.nextDouble());
		System.out.println("");
		System.out.println("\tx = " + equation.getX());
		System.out.println("\ty = " + equation.getY());
		System.out.println("\tz = " + equation.getZ());
		System.out.println("\tx0 = " + equation.check());
	}
}
