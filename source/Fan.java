package javaCode;

public class Fan {
	public final int SLOW = 1;
	public final int MEDIUM = 2;
	public final int FAST = 3;
	int speed;//速度
	private boolean on = false;//开关状态
	private double radius;//半径
	private String color;//颜色
	public Fan(){
	}//构造方法
	public int getSpeed(){
		return speed;
	}
	public boolean getOn() {
		return on;
	}
	public double getRidius() {
		return radius;
	}
	public String getColor() {
		return color;
	}
	public void setSpeed(int speed) {
		if(speed >= SLOW && speed <= FAST) this.speed = speed;
		else System.out.println("设置速度错误！");
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String toString() {
		if(on) return "speed:" + speed + " radius:" + radius + " color:" + color;
		return "fan is off";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fan fan1 = new Fan();
		fan1.setSpeed(fan1.FAST);
		fan1.setRadius(10);
		fan1.setColor("yellow");
		fan1.setOn(true);
		Fan fan2 = new Fan();
		fan2.setSpeed(fan2.MEDIUM);
		fan2.setRadius(5);
		fan2.setColor("blue");
		fan2.setOn(false);
		System.out.println("fan1\n" + fan1.toString());
		System.out.println("fan2\n" + fan2.toString());
	}
}
