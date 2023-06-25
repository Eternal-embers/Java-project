package javaCode;

public class BasicOperation {
	public BasicOperation() {
	}
	public int createPositiveNum() {
		return (int)(Math.random()*100+1);
	}//生成一个1~100的数`
	public int createNegativeNum() {
		return (int)(Math.random()*(-100)-1);
	}//生成一个-1~-100的数
	public int bigPositiveNum() {
		return (int)(Math.random()*1000+1);
	}
	public int smallPositiveNum() {
		return (int)(Math.random()*10+1);
	}
	public int bigNegativeNum() {
		int sign = (int)(Math.random()*10+1);
		if(sign<5) return (int)(Math.random()*(-1000)-1);
		return (int)(Math.random()*1000+1);
	}
	public int smallNegativeNum() {
		return (int)(Math.random()*(-10)-1);
	}
	public int add(int a,int b) {
		return a+b;
	}
	public int subtract(int a,int b) {
		return a-b;
	}
	public int muliply(int a,int b) {
		return a*b;
	}
	public int divide(int a,int b) {
		return a/b;
	}
}
