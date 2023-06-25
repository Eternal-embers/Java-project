package javaCode;

import java.util.Scanner;

public class StackOfInteger {
	private int[] elements;//数组表示栈
	private int size;//栈顶下标
	public static final int DEFAULT_CAPACITY = 16;//默认容量
	//构造方法
	public StackOfInteger() {
		elements = new  int[DEFAULT_CAPACITY];
	}
	public StackOfInteger(int capacity) {
		elements = new int[capacity];
	}
	//方法
	public void push(int value) {
		//如果数据的数量大于栈的容量
		if(size >= elements.length) { 
			int[] temp = new int[elements.length*2];//java中如果栈空间不足，一般直接将栈的容量扩大两倍
			System.arraycopy(elements, 0, temp, 0, elements.length);
			elements = temp;//将扩容的栈的引用值赋给elements；
		}
		elements[size++] = value;//新的值放入栈顶，栈顶下标加一
	}//压栈
	public int pop() {
		if(empty()) return -1;//如果栈为空返回-1
		return elements[size--];//栈顶元素出栈，栈顶下标减一
	}//出栈
	public int peek() {
		return elements[size-1];//返回栈顶的元素
	}//获取栈顶元素
	public boolean empty() {
		return size==0;//如果栈顶下标为0则为空，否则栈不为空
	}//判断栈是否为空
	public void traversalStack() {
		int count = 0;
		for(int i = size-1;i >= 0;i--) {
			System.out.print(elements[i] + " ");
			count++;
			if(count%20==0 && i!=0)	System.out.print("\n  >>> ");
		}
		System.out.println("");
	}//遍历栈
	public void traversalStack(int n) {
		int count = 0;
		for(int i = size-1;i >= 0;i--) {
			System.out.print(elements[i] + " ");
			count++;
			if(count%n==0 && i!=0)	System.out.print("\n  >>> ");
		}
		System.out.println("");
	}//可以选择每行打印元素个数，遍历栈
	public int getSize() {
		return size;
	}//获取栈顶下标
	public void stackSort() {
		int temp,i,j;
		for(i = 1;i < size;i++) {
			temp = elements[i];
			for(j = i-1;j >= 0;j--)
				if(temp > elements[j]) elements[j+1] = elements[j];
				else break;
			elements[j+1] = temp;
		}
	}//对栈内元素排序
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StackOfintegers stack = new StackOfintegers(10);//初始化一个栈容量为
		Scanner in = new Scanner(System.in);
		int data,option = 0,n;
		System.out.print("是否选择自动压栈？(Y[1]/N[0]):");
		option = in.nextInt();
		if(option==1) {
			System.out.print("请输入压栈次数:");
			n = in.nextInt();
			for(int i = 0;i<n;i++) {
				data = (int)(Math.random()*100+1);
				stack.push(data);
			}
			System.out.println("压栈结束!");
		}
		else {
			while(option!=-1) {
				data = (int)(Math.random()*100+1);
				System.out.println("data = " + data);
				System.out.printf("是否将%d压栈?( 是[1]/结束[-1]/不压栈[!1&&!-1] ):\n>>",data);
				option = in.nextInt();
				if(option==1) {
					stack.push(data);
					System.out.printf("%d压栈成功\n",data);
				}
				System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
			}
		}
		System.out.print("排序前：");
		stack.traversalStack();
		stack.stackSort();
		System.out.print("排序后：");
		stack.traversalStack();
		in.close();
	}
}
