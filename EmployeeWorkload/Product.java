package EmployeeWorkload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Product {
	//ArrayList必须在此处实例化，否则在构造方法中的name和price为空值
	private int length;//产品种类的数目
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<Double> price = new ArrayList<Double>();
	public File file = new File("D:\\programming\\eclipse\\EmployeeWorkload\\data\\product.txt");//默认读取和保存路径
	
	public void product(String[] name,double price[]) {
		if(name.length == price.length) {
			this.length = name.length;
			for(int i = 0;i < this.length;i++) {
				this.name.add(name[i]);
				this.price.add(price[i]);
			}
		}
		else {
			System.out.println("无法用长度不同的产品名称数组和产品价格数组构造对象！");
			System.out.println("请确保每一件产品都有对应的价格");
		}
	}
	//访问器
	public ArrayList<String> getName(){
		return name;
	}//获取产品名称的ArrayList对象
	public ArrayList<Double> getPrice(){
		return price;
	}//获取产品价格的ArrayList对象
	public String getName(int index) {
		return name.get(index);
	}//获取某产品名称
	public double getPrice(int index) {
		return price.get(index);
	}
	public int getLength() {
		return length;
	}//获取产品数量
	
	//修改器
	public void setName(String name,int index){
		this.name.set(index, name);
	}//index从0开始
	public void setPrice(double price,int index) {
		this.price.set(index, price);
	}//index从0开始
	
	//展示所有产品数据
	public void showProduct() {
		System.out.println("\t——————————————————————————"
	    		+ "———————————————————————————");
		System.out.printf("\t\t序号\t%-20s%s\n",">>>产品<<<",">>>价格<<<");
		for(int i = 0;i < name.size();i++) {
			System.out.printf("\t\t<%d>\t%-20s%s\n",i,name.get(i),price.get(i));
		}
		System.out.println("\t——————————————————————————"
	    		+ "———————————————————————————\n");
	}
	
	//添加产品
	public void add(String name,double price) {
		this.name.add(name);
		this.price.add(price);
		this.length++;
	}
	
	//排序,插入排序
	@SuppressWarnings("unchecked")
	public void sort() {
		//此处name和price覆盖了数据域中的name和price
		ArrayList<String> name = (ArrayList<String>) this.name.clone();
		ArrayList<Double> price = (ArrayList<Double>) this.price.clone();
		int length = name.size();
		int i,j;
		for(i = 0;i < length;i++) {
			String tempName = name.get(i);
			double tempPrice = price.get(i);
			for(j = i - 1;j >= 0;j--) {
				if(tempPrice > price.get(j) ) {
					name.set(j + 1, name.get(j));
					price.set(j + 1, price.get(j));
				}
				else break;
			}
			name.set(j + 1,tempName);
			price.set(j + 1, tempPrice);
		}
		System.out.println("\t——————————————————————————"
	    		+ "———————————————————————————");
		System.out.printf("\t\t序号\t%-20s%s",">>>产品<<<",">>>价格<<<\n");
		for(i = 0;i < length;i++) {
			System.out.printf("\t\t<%d>\t%-20s%s\n",i,name.get(i),price.get(i));
		}
		System.out.println("\t——————————————————————————"
	    		+ "———————————————————————————\n");
	}
	
	//输入产品数据
	public void productInput(Product product) {
		Scanner userInput = new Scanner(System.in);
		System.out.println("\t请输入产品名称和价格(输入#结束输入)");
		System.out.printf("\t  %-20s%s","产品","价格\n");
		while(true){
			System.out.print("\t>>");
			String name = userInput.next();
			if(name.charAt(0) == '#') break;
			double price = userInput.nextDouble();
			product.add(name,price);
		}
		userInput.close();
	}
	
	//保存
	@SuppressWarnings("resource")
	public void save(File file) throws IOException {
		FileWriter writer = new FileWriter(file,true);//追加数据2
		int length = name.size();
		if(length != price.size()) {
			System.out.println("产品数量和价格数量不相等，保存失败!");
			return;
		}
		for(int i = 0;i < length;i++) {
			writer.write(name.get(i) +  "\n");
			writer.write(price.get(i).toString() + "\n");
			writer.flush();
		}
		writer.close();
	}
	
	public void read() throws FileNotFoundException {
		try {
			Scanner readProduct = new Scanner(this.file);
			while(readProduct.hasNextLine()) {
				this.add(readProduct.nextLine(),Double.valueOf(readProduct.nextLine()));
			}
			readProduct.close();
		}
		catch(FileNotFoundException ex) {
			System.out.println("文件未找到或不存在，读取失败!");
			return;
		}
		
	}
	
	
	public static void main(String []args) throws IOException {
		//测试
		Product product = new Product();
		product.productInput(product);
		product.showProduct();
		product.sort();
		product.save(product.file); 
		product.read();
		product.showProduct();
		product.sort();
	}
	
}
