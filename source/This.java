package javaCode;

public class This {
	private int p;
	public This() {
		System.out.println("This's no-arg constructor invoked");
	}
	public This(int p) {
		this.p = p;
		System.out.println(this.p);
	}
	public void setThis(int p) {
		this.p = p;
		System.out.println(this.p);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		This t = new This();
		System.out.println(t.p);
		t.setThis(10);
	}

}
