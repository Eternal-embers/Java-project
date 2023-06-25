package javaCode;

public class House implements java.lang.Cloneable,Comparable<House>{
	private int id;
	private double area;
	private java.util.Date whenBuilt;
	public House(int id,double area) {
		this.id = id;
		this.area = area;
		whenBuilt = new java.util.Date();
	}
	public int getId() {
		return id;
	}
	public double getArea() {
		return area;
	}
	public java.util.Date getWhenBuilt(){
		return whenBuilt;
	}
	@Override
	public Object clone() {
		try {
			return (Object) super.clone();
		}
		catch(CloneNotSupportedException ex) {
			return null;
		}
	}
	@Override
	public int compareTo(House o) {
		if(area > o.area)
			return 1;
		else if(area < o.area)
			return -1;
		else return 0;
	}
	public static void main(String[] args) {
		House house1 = new House(10001,3200);
		House house2 = new House(10002,4000);
		System.out.println(house1.getWhenBuilt());
		System.out.println(house2.getWhenBuilt());
		System.out.println("house1 up to house2? " + house1.compareTo(house2));
		
	}

}
