package javaCode;

import java.util.Arrays;
import java.util.Scanner;

public class merge_sort {
	public static void mergeSort(int []arr) {
		int len = arr.length;
		int[] result = new int[len];
		int block,start;
		for(block = 1;block < len*2;block *= 2) {
			for(start = 0;start < len;start += 2*block) {
				int low = start;
				int mid = (start + block) < len ? (start + block) : len;
				int high = (start + 2*block) < len ? (start + 2*block) : len;
				//两个块的起始下标及结束下标
				int start1 = low,end1 = mid;
				int start2 = mid,end2 = high;
				while(start1 < end1 && start2 < end2) {
					result[low++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
				}
				while(start1 < end1) {
					result[low++] = arr[start1++];
				}
				while(start2 < end2) {
					result[low++] = arr[start2++];
				}
			}
			int[] temp = arr;
			arr = result;
			result = temp;
		}
		result = arr;
	}
	public static void fillArray(int[] arr) {
		for(int i = 0;i<arr.length;i++)
			arr[i] = (int)(Math.random()*100+1);
	}
	public static void main(String[] args) {
		System.out.print("n = ");
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] arr = new int[n];
		int[] result = new int[n];
		fillArray(arr);
		System.out.println(Arrays.toString(arr));
		mergeSort(arr);
		System.out.println(Arrays.toString(arr));
		in.close();
	}

}
