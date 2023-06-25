package javaCode;
public class RegularPolygon {
	private int n = 3;//边数
	private double size = 1;//边的长度
	private double x =0;//中心点横坐标
	private double y = 0;//中心点纵坐标
	public RegularPolygon() {
	}//构造方法
	public RegularPolygon(int n,double size) {
		this.n = n;
		this.size = size;
	}
	public RegularPolygon(int n,double size,double x,double y) {
		this.n = n;
		this.size = size;
		this.x = x;
		this.y = y;
	}
	public int getN() {
		return n;
	}
	public double getSize() {
		return size;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public void setN(int n) {
		this.n = n;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getPerimeter() {
		return n*size;
	}//获取周长
	public double getArea() {
		return (n*getPerimeter()*getPerimeter())/(4*Math.tan(Math.PI/n));
	}//获取面积
	public static void main(String[] args) {
		RegularPolygon polygon1 = new RegularPolygon();
		RegularPolygon polygon2 = new RegularPolygon(6,4);
		RegularPolygon polygon3 = new RegularPolygon(10,4,5.6,7.8);
		System.out.println("polygon1 perimeter:" + polygon1.getPerimeter() + " Area:" + polygon1.getArea());
		System.out.println("polygon2 perimeter:" + polygon2.getPerimeter() + " Area:" + polygon2.getArea());
		System.out.println("polygon3 perimeter:" + polygon3.getPerimeter() + " Area:" + polygon3.getArea());
	}
}
