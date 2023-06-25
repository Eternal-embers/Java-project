package javaCode;

public class SupperRectangle extends GeometricObject{
	private double width;
	private double height;
	public SupperRectangle() {
	}
	public SupperRectangle(double width,double height) {
		this.width = width;
		this.height = height;
	}
	public SupperRectangle(double width,double height,String color,boolean filled) {
		this.width = width;
		this.height = height;
		setColor(color);
		setFilled(filled);
	}
	public double getWidth() {
		return width;
	}
	public double getheight() {
		return height;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getArea() {
		return width*height;
	}
	public double getPerimeter() {
		return 2*(width+height);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
