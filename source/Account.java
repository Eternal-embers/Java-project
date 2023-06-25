package javaCode;

import java.util.Scanner;

public class Account {
	private int id;//标识账号
	private double balance;//余额
	private double annualInterestRate;//年利率
	private String dateCreated;//开户日期
	public Account() {
	}
	public Account(double balance) {
		this.balance = balance;
	}
	public Account(int id,int balance) {
		this.id = id;
		this.balance = balance;
	}//构造方法
	public int getId() {
		return id;
	}//获取ID
	public double getBalance() {
		return balance;
	}//获取余额
	public double getAnnualInterestRate() {
		return annualInterestRate;
	}//获取年利率
	public double getMonthlyInterestRate() {
		return annualInterestRate/12;
	}//获取月利率
	public double getMonthlyInterest() {
		return balance*getMonthlyInterestRate();
	}//获取月利息
	public String getDateCreated() {
		return dateCreated;
	}//获取开户日期
	public void setId(int id) {
		this.id = id;
	}//修改id
	public void setBalance(double balance) {
		this.balance = balance;
	}//修改余额
	public void setannualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}//修改年利率
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}//修改创建日期
	public void withDraw(double take) {
		if(balance>=take) balance-=take;
		else System.out.println("余额不足");
	}//取款
	public void deposit(double save) {
		balance+=save;
	}//存款
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Account user1 = new Account(1152,20000);
		user1.annualInterestRate = 0.045;
		user1.dateCreated = "2023/2/25";
		user1.withDraw(2500);
		user1.deposit(3000);
		System.out.println("余额为：" + user1.getBalance());
		System.out.println("月利息为：" + user1.getMonthlyInterest());
		System.out.println("开户日期：" + user1.getDateCreated());
		in.close();
	}
}
