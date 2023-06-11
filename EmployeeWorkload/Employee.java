package EmployeeWorkload;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Employee {
	private String jobNum;//员工工号
	private String name;//员工姓名
	private int workload[];//工作量
	private double salary;//工作量对应的薪水
	
	//完整构造方法
	public Employee(Product product,String jobNum,String name,int workload[]) throws FileNotFoundException {
		//获取产品列表
		this.jobNum = jobNum;
		this.name = name;
		int length = product.getLength();
		this.workload = new int[length];
		for(int i = 0;i < length;i++) {
			this.workload[i] = workload[i];
		}
		//计算薪水
		for(int i = 0;i < length;i++) {
			this.salary += product.getPrice(i) * workload[i];
		}
	}
	//读取构造方法
	public Employee(Product product,String jobNum,String name,int workload[],double salary) throws FileNotFoundException {
		//获取产品列表
		this.jobNum = jobNum;
		this.name = name;
		int length = product.getLength();
		this.workload = new int[length];
		for(int i = 0;i < length;i++) {
			this.workload[i] = workload[i];
		}
		this.salary = salary;
	}
	//基本构造方法
	public Employee(Product product,String jobNum,String name) throws FileNotFoundException {
		workload = new int[product.getLength()];
		this.jobNum = jobNum;
		this.name = name;
	}
	
	//访问器
	public String getJobNum() {
		return jobNum;
	}
	public String getName() {
		return name;
	}
	public int getWorkload(int index) {
		return workload[index];
	}
	public Double getSalary() {
		return salary;
	}
	
	//修改器
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setWorkload(int index,int workload) {
		this.workload[index] = workload;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public void caculateSalary(Product product) {
		int length = product.getLength();
		//计算薪水
		double salary = 0;
		for(int i = 0;i < length;i++) {
			salary += product.getPrice(i) * workload[i];
		}
		this.salary = salary;
	}
	
	public void input(Scanner userInput,Product product) {
		product.showProduct();
		while(true) {
			int index,num;
			System.out.print("\t请输入产品序号(输入-1结束输入):");
			index = userInput.nextInt();
			if(index == -1) break;
			System.out.printf("\t请输入工作量:");
			num = userInput.nextInt();
			workload[index] += num;
		}
		//更新薪水
		caculateSalary(product);
	}
	
	//显示所有员工信息
	public void show(Product product) {
		int length = product.getLength();
		System.out.println("\t工号：" + jobNum);
		System.out.printf("\t>>> %s <<<\n",name);
		for(int i = 0;i < length;i++) {
			if(workload[i] > 0) System.out.printf("\t%s：%d\n",product.getName(i),workload[i]);
		}
		System.out.printf("\t工薪：%.3f\n",salary);
	}
	
	public static void main(String []args) throws FileNotFoundException {
		Product product = new Product();
		product.read();
		Employee employee = new Employee(product,"100001","赖清文");
		employee.show(product);
	}
}
