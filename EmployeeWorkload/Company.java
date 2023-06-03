package EmployeeWorkload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Company {
	public Product product;//产品
	public ArrayList<Employee> employee = new ArrayList<Employee>();//员工
	int size = 0;//员工数目
	public File file = new File("D:\\programming\\eclipse\\EmployeeWorkload\\data\\employee.txt");//保存员工信息的文件对象
	
	//构造方法
	public Company() throws FileNotFoundException {
		product = new Product();
		product.read();
		read();
	}
	
	//覆盖原先的员工信息
	public void save() throws IOException {
		PrintWriter writer = new PrintWriter(file);
		int length = employee.size();
		for(int i = 0;i < length;i++) {
			Employee tempEmp = employee.get(i);
			writer.write(tempEmp.getJobNum() + "\n");
			writer.write(tempEmp.getName() + "\n");
			for(int j = 0;j < product.getLength();j++) {
				writer.write(tempEmp.getWorkload(j) + "  ");
			}
			writer.write(tempEmp.getSalary().toString() + "\n");
		}
		writer.close();
	}
	
	//读取员工列表
	public void read() throws FileNotFoundException {
		Scanner fileInput = new Scanner(file);
		String jobNum;
		String name;
		int workload[] = new int[product.getLength()];
		double salary;
		while(fileInput.hasNextLine()) {
			jobNum = fileInput.nextLine();
			name = fileInput.nextLine();
			for(int i = 0;i < product.getLength();i++) {
				workload[i] = fileInput.nextInt();
			}
			salary = fileInput.nextDouble();
			fileInput.nextLine();
			employee.add(new Employee(product,jobNum,name,workload,salary));
			size++;
		}
		fileInput.close();
	}
	
	//登记员工信息
	public void record(Scanner userInput) throws FileNotFoundException {
		String jobNum;
		String name;
		System.out.println("\t>>> 工作量登记 <<<");
		System.out.print("\t请输入工号(输入#返回)：");
		jobNum = userInput.nextLine();
		if(jobNum.charAt(0) == '#') return;
		System.out.print("\t请输入姓名:");
		name = userInput.nextLine();
		int index = ifExist(name);
		if(index != -1) {
			employee.get(index).input(userInput,product);
		}//如果已经有该员工的信息，直接登记工作量
		else {
			employee.add(new Employee(product,jobNum,name));
			size++;
			employee.get(size - 1).input(userInput,product);
		}//如果没有该员工的信息，添加该员工的信息，再登记工作量
		System.out.println("\t登记成功!");
	}
	
	//按姓名判断员工是否存在
	public int ifExist(String name) {
		for(int i = 0;i < size;i++) {
			if(employee.get(i).getName().compareTo(name) == 0)return i;
		}
		return -1;
	}
	
	//将员工按工薪排序并输出
	public void sort() {
		ArrayList<Employee> emp = new ArrayList<Employee>(employee);//将employee复制一份用于排序
		//选择排序
		int i,j,k;
		for(i = 0;i < size - 1;i++) {
			k = i;
			for(j = i + 1;j < size;j++) {
				if(emp.get(k).getSalary() < emp.get(j).getSalary()) k = j;
			}
			if(k != i) {
				Employee temp = emp.get(i);
				emp.set(i, emp.get(k));
				emp.set(k,temp);
			}
		}
		//输出排行榜
		System.out.println("\t>>> 工作量排行榜 <<<");
		System.out.printf("\t%4s\t%s\t\t%s\n","排名","姓名","工新");
		for(i = 0;i < emp.size();i++) {
			System.out.printf("\t%4d\t%s\t\t%.3f\n",i + 1,emp.get(i).getName(),emp.get(i).getSalary());
		}
	}
	
	//删除员工信息，支持按工号删除
	public void delete(Scanner userInput) {
		int option;
		System.out.print("\t请选择删除模式(按工号删除(1)/按姓名删除(0):");
		option = userInput.nextInt();
		userInput.nextLine();
		if(option == 1) {
			System.out.print("\t请输入员工工号:");
			String jobNum = userInput.nextLine();
			for(int i = 0;i < size;i++) {
				if(employee.get(i).getJobNum().compareTo(jobNum) == 0) {
					employee.remove(i);
					size--;
				}
			}
		}
		else {
			String name = null;
			System.out.print("\t请输入员工姓名:");
			name = userInput.nextLine();
			int index = ifExist(name);
			if(index != -1) {
				employee.remove(index);
				size--;
			}
			else System.out.println("\t员工不存在!");
		}
	}
	
	public void search(Scanner userInput) {
		int option;
		System.out.print("\t请选择查询模式(按工号查询(1)/按姓名查询(0):");
		option = userInput.nextInt();
		userInput.nextLine();
		if(option == 1) {
			System.out.print("\t请输入员工工号(输入#返回):");
			String jobNum = userInput.nextLine();
			if(jobNum.charAt(0) == '#') return;
			for(int i = 0;i < size;i++) {
				if(employee.get(i).getJobNum().compareTo(jobNum) == 0) {
					employee.get(i).show(product);
				}
			}
		}
		else {
			String name = null;
			System.out.print("\t请输入员工姓名(输入#返回):");
			name = userInput.nextLine();
			if(name.charAt(0) == '#') return;
			int index = ifExist(name);
			if(index != -1) {
				employee.get(index).show(product);
			}
			else System.out.println("\t员工不存在!");
		}
	}
	
	//用户菜单
	public void menu() {
		System.out.println("");
		System.out.println("\t        >>>>> 用户模式 <<<<<");
		System.out.println("\t——————————————————————————————————————");
		System.out.println("\t菜单：");
		System.out.println("\t* 退出 - - - - - - - - - - - - - 0");
		System.out.println("\t* 产品列表 - - - - - - - - - -  - 1");
		System.out.println("\t* 登记工作量 - - - - - - - - -  - 2");
		System.out.println("\t* 排行榜- - - - - - - - - - -  - 3");
		System.out.println("\t* 注册 - - - - - - - - - - - - - 4");
		System.out.println("\t——————————————————————————————————————");
		System.out.print("\t>>");
	}
	
	//管理员菜单
	public void adminstration() {
		System.out.println("");
		System.out.println("\t       >>>>> 管理员模式 <<<<<");
		System.out.println("\t——————————————————————————————————————");
		System.out.println("\t菜单：");
		System.out.println("\t* 返回用户模式 - - - - - - - - - - - -0");
		System.out.println("\t* 添加产品 - - - - - - - - - - - - - 1");
		System.out.println("\t* 查看全体员工信息 - - - - - - - - - -2");
		System.out.println("\t* 查询员工工作数据 - - - - - - - - -  3");
		System.out.println("\t* 更新员工数据 - - - - - - - - - - - 4");
		System.out.println("\t* 删除员工数据 - - - - - - - - - - - 5");
		System.out.print("\t>>");
	}
	
	public static void main(String[] args) throws InputMismatchException, IOException{
		Company company = new Company();//创建company对象自动读取产品列表和员工信息
		System.out.println("\t—————————————————————————————————————");
		System.out.println("\t*  程序已自动从文件中读取产品列表!      *");
		System.out.println("\t*  程序已自动从文件中获取所有员工信息！  *");
		System.out.println("\t*  程序已完成初始化!                  *");
		System.out.println("\t*     >>>>>  XX有限公司 <<<<<<       *");
		System.out.println("\t—————————————————————————————————————");
		Scanner userInput = new Scanner(System.in);
		int option;
		while(true) {
			//选项输入
			company.menu();
			try {
				option = userInput.nextInt();
				userInput.nextLine();
			}
			catch(InputMismatchException ex) {
				System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
				System.out.print("\t>>");
				userInput.nextLine();//清空缓冲区
				option = userInput.nextInt();
			}
			System.out.println("");
			
			//管理员模式，输入1024进入管理员模式
			if(option == 1024) {
				while(option != 0) {
				company.adminstration();
				try {
					option = userInput.nextInt();
					userInput.nextLine();
				}
				catch(InputMismatchException ex) {
					System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<");
					System.out.print("\t>>");
					userInput.nextLine();//清空缓冲区
					option = userInput.nextInt();
				}
				System.out.println("");
					switch(option) {
					case 0: System.out.println("\t已返回用户模式！"); break;
					case 1: {
							company.product.productInput(company.product); 
							System.out.println("\t添加成功!");
							break;
					}
					case 2:{
						int length = company.employee.size();
						for(int i = 0;i < length;i++) {
						System.out.println("\t————————————————————"
								+ "————————————————————");
							company.employee.get(i).show(company.product);
						}
						System.out.println("\t————————————————————"
							+ "————————————————————");
						userInput.nextLine();
						break;
					}
					case 3: {
						company.search(userInput);
						break;
					}
					case 4: {
						company.save(); 
						System.out.println("\t更新成功!");
						break;
					}
					case 5: {
						company.delete(userInput); 
						System.out.println("\t删除成功!");
						break;
					}
					}
				}
			}
			
			//用户模式
			else {
				switch(option) {
				case 0: {
					company.save();//保存数据
					System.out.println("\t程序已自动将所有员工数据更新至文件中!");
					userInput.close();
					System.out.println("\t感谢您的使用!"); return;
				}
				case 1: {
					int flag = 0;
					System.out.printf("\t是否按工价排序的产品列表(1/0):");
					flag = userInput.nextInt();
					if(flag == 1) {
						company.product.sort();
					}
					else {
						company.product.showProduct();
					}
					userInput.nextLine();
					userInput.nextLine();
					break;
				}
				case 2: {
					company.record(userInput); break;
				}
				case 3: company.sort(); break;
				case 4: {
					String jobNum;
					String name;
					System.out.print("\t请输入工号:");
					jobNum = userInput.nextLine();
					System.out.print("\t请输入姓名:");
					name = userInput.nextLine();
					company.employee.add(new Employee(company.product,jobNum,name));
					System.out.println("\t注册成功!");
					break;
				}
				default: System.out.println("\t>>>>>>> 输入错误，请重新输入！ <<<<<<<"); break;
				}
			}
		}
	}
}
