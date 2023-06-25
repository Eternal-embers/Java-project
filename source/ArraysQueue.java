package javaCode;

import java.util.Arrays;

public class ArraysQueue {
	private int[] queue;//数组表示队列
	private int size;//保存队列中元素个数
	private int capacity;//队列大小
	public ArraysQueue() {
		queue = new int[16];
	}//默认构造大小为16的构造方法
	
	public ArraysQueue(int capacity) {
		queue = new int[capacity];
	}//指定队列大小的构造方法
	
	public ArraysQueue(int arr[]) {
		queue = new int[arr.length*2];
		capacity = arr.length*2;
		System.arraycopy(arr,0,queue,0,arr.length);
		size = arr.length;
	}//指定数组为队列
	
	public int getCapacity() {
		return capacity;
	}//获取队列容量
	
	public int getSize() {
		return size;
	}//获取队列中元素个数1
	
	public void enQueue(int element) {
		if(size+1>capacity) {
			int[] queue = new int[capacity*2];//新建两倍大小的队列
			//将原先队列中的元素复制到新的队列
			System.arraycopy(this.queue, 0, queue, 0,size);
			queue[size++] = element;//增加新的队列元素
			this.queue = queue;//将新的队列的引用赋给该对象的队列
			capacity*=2;//容量扩大两倍
		}
		else queue[size++] = element;
	}//入队
	
	public int deQueue() {
		int[] queue = new int[this.capacity];//创建新的队列
		int element = this.queue[0];//记录出队元素
		//将出队后剩下的队列元素复制到queue中
		System.arraycopy(this.queue, 1, queue, 0, capacity-1);
		size--;
		this.queue = queue;
		return element;
	}//出队
	
	public void showQueue() {
		for(int i = 0;i<size;i++)
			System.out.print(queue[i] + " ");
		System.out.println("");
	}//打印队列元素
	
	public void clearQueue() {
		int[] queue = new int[capacity];
		this.queue = queue;
		size = 0;
	}//清空队列
	
	public boolean isEmpty() {
		if(size==0) return true;
		return false;
	}//判断队列是否为空
	
	public void queueSort() {
		int[] arr = new int[size];
		arr = Arrays.copyOf(queue, size);
		Arrays.sort(arr);
		System.arraycopy(arr,0, queue, 0, size);
	}
	
	public static void main(String[] args) {
		int[] arr = new int[20];
		for(int i = 0;i<20;i++) 
			arr[i] = (int)(Math.random()*100+1);
		ArraysQueue q = new ArraysQueue(arr);
		q.showQueue();
		System.out.println("入队：66");
		q.enQueue(66);
		System.out.println("出队：" + q.deQueue());
		q.showQueue();
		q.queueSort();
		q.showQueue();
		System.out.println(q.getCapacity());
		System.out.println(q.getSize());
		q.clearQueue();
		System.out.println("队列是否为空？" + q.isEmpty());
	}
}
