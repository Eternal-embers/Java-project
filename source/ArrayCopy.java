package javaCode;
import java.lang.System;
public class ArrayCopy {
	public static void printArray(int[] array) {
		for(int i = 0;i<array.length;i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("");
	}
	public static void main(String[] args) {
		//数组复制
		int sourceArr[] = new int[10];
		int targetArr[] = new int[sourceArr.length];
		int target[] = new int[sourceArr.length];
		for(int i = 0;i<10;i++)
			sourceArr[i] = (int)(Math.random()*100);
		//循环复制
		for(int i = 0;i<sourceArr.length;i++)
			targetArr[i] = sourceArr[i];
		//用System.arraycopy()方法复制
		System.arraycopy(sourceArr,0,target,0,sourceArr.length);
		for(int i = 0;i<10;i++) {
			System.out.print(targetArr[i] + " ");
		}
		System.out.println("");
		//打印数组
		printArray(new int[]{3,5,7,11,13});
		printArray(targetArr);
		printArray(target);
		//在java中，数组是对象，JVM将对象存储在一个称作堆的内存区域，堆用于动态内存分配
	}
}
