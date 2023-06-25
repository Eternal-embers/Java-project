package javaCode;

public class GeometricObject {
	private String color = "white";
	private boolean filled;
	private java.util.Date dateCreated;
	public GeometricObject() {
	}
	public GeometricObject(String color,boolean filled) {
		dateCreated = new java.util.Date();
		this.color = color;
		this.filled = filled;
	}//构造方法
	public String getColor() {
		return color;
	}//获取颜色
	public void setColor(String color) {
		this.color = color;
	}//修改颜色
	public boolean isFilled() {
		return filled;
	}//是否填充
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	public java.util.Date getDateCreated(){
		return dateCreated;
	}
	public String toString() {
		return "created on " + dateCreated + "\ncolor: " + color 
				+ " and filled：" + filled;
	}
	public static void main(String[] args) {
	}
}
