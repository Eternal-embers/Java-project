package javaCode;
import java.util.Scanner;

public class ATM {
	private Account[] customer;
	private int size;//记录当前使用的Account的下标
	private int maxSize;//记录当前已使用的最大的Account的下标
	static Scanner in = new Scanner(System.in);
	public  ATM() {
		customer = new Account[10];//默认开辟10个Acccount类的数组
	}//默认构造法
	public ATM(int num) {
		customer = new Account[num];//开辟指定的num个Account类的数组
	}//指定Account数组最大容量的构造法
	public void menu() {
		System.out.println("");
		System.out.println("________________");
		System.out.println("|              |");
		System.out.println("|  >>>Menu<<<  |");
		System.out.println("|______________|");
		System.out.println("1: check balance");
		System.out.println("2：withdraw");
		System.out.println("3：deposit");
		System.out.println("4：exit");
	}//菜单
	public void input() {
		while(true) {
			customer[maxSize] = new Account(0.001);
			System.out.print("id(输入小于0的id结束录入)：");
			customer[maxSize].setId(in.nextInt());
			if(customer[maxSize].getId()<0) return;
			System.out.print("balance：");
			customer[maxSize].deposit(in.nextDouble());
			System.out.print("dateCreated：");
			customer[maxSize].setDateCreated(in.next());
			System.out.println("- - - - - - - - - - - - - - -");
			maxSize++;
			if(maxSize>customer.length) {
				System.out.println("容量不足!");
				return;
			}
			/*
			 if(maxSize>customer.length) {
				Account[] t = new Account[customer.length*2];
			}//容量超出处理
			*/
		}
	}//录入账号信息
	public void signIn() {
		int id,i;
		System.out.print("请输入您的id：");
		id = in.nextInt();
		for(i = 0;i<maxSize;i++)
			if(id==customer[i].getId()) { 
				size = i;
				System.out.println("登录成功！");
				return;
			}
		if(i==maxSize) System.out.println("id输入错误!");
	}//登录账号
	public void operate(int n) {
		if(n==1) System.out.printf(customer[size].getBalance() + " $");
		else if(n==2) {
			System.out.print("请输入取款数：");
			customer[size].withDraw(in.nextDouble());
			System.out.printf("取款成功!");
		}
		else if(n==3) {
			System.out.println("请输入存款数：");
			customer[size].deposit(in.nextDouble());
			System.out.printf("存款成功!");
		}
		else if(n==4) return;
		else System.out.println("输入错误");
	}//运行
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int option = 0,on = 1;
		ATM ob = new ATM();
		ob.input();
		while(on>0) {
			System.out.println(">>>>登录<<<<");
			ob.signIn();
			ob.menu();
			System.out.print(">>");
			while(option!=4) {
				option = in.nextInt();
				ob.operate(option);
				ob.menu();
				System.out.print(">>");
			}
			System.out.println("感谢您的使用!");
			System.out.println("- - - - - - - - - - - - - - -");
			System.out.print("是否继续使用ATM系统?(输入负数结束，输入正数继续)：\n>>");
			on = in.nextInt();
		}
	}
}
