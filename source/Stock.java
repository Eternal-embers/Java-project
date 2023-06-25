package javaCode;
import java.util.Scanner;
public class Stock {
	private String symbol;
	private String name;
	double previousClosingPrice;
	double currentPrice;
	public Stock(){
		symbol = "ORCL";
		name = "Oracle Corporation";
	}
	public double getPreviousClosingPrice() {
		return previousClosingPrice;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public double getChangePercent() {
		return (currentPrice - previousClosingPrice)/previousClosingPrice;
	}
	public void showproperty() {
		System.out.println(symbol + " " + name);
		System.out.println("previousClosingPrice:" + previousClosingPrice);
		System.out.println("currentPrice:" + currentPrice);
		System.out.println("changePercent:" + getChangePercent());
	}
	public void setStock(double prePrice,double curPrice) {
		previousClosingPrice = prePrice;
		currentPrice = curPrice;
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("创建Stock对象");
		Stock stock1 = new Stock();
		stock1.setStock(in.nextDouble(), in.nextDouble());
		stock1.showproperty();
		in.close();
	}

}
