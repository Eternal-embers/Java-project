package javaCode;

import java.util.Scanner;
public class ShowPrime {
	private int num;
	StackOfInteger stack;
	public ShowPrime() {
	}
	public ShowPrime(int num) {
		this.num = num;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void getPrime() {
		stack = new StackOfInteger();
		int i,j;
		for(i = 2;i<=num;i++) {
			for(j = 2;j<=Math.sqrt(i);j++)
				if(i%j==0) break;
			if(j>Math.sqrt(i)) stack.push(i);
		}
	}
	public void reverse() {
		stack.traversalStack(30);
	}//逆序打印素数
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		ShowPrime ob = new ShowPrime();
		System.out.print("请输入一个正整数:");
		ob.setNum(in.nextInt());
		System.out.println(ob.getNum());
		ob.getPrime();
		System.out.printf("%d以内的素数:",ob.getNum());
		ob.stack.traversalStack(30);
		System.out.printf("%d以内的素数:",ob.getNum());
		ob.reverse();
		in.close();
	}

}
