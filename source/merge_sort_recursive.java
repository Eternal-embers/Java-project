package javaCode;

import java.util.Arrays;
import java.util.Scanner;

public class merge_sort_recursive {
	public static void merge_sort(int []arr,int[] result,int start,int end) {
		if(start >= end) return;
		int len = end - start,mid = (len >> 1) + start;
		int start1 = start,end1 = mid;
		int start2 = mid + 1,end2 = end;
		merge_sort(arr,result,start1,end1);
		merge_sort(arr,result,start2,end2);
		int k = start;
		while(start1 <= end1 && start2 <= end2)
			result[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
		while(start1 <= end1)
			result[k++] = arr[start1++];
		while(start2 <= end2)
			result[k++] = arr[start2++];
		for(k = start;k <= end;k++)
			arr[k] = result[k];
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
		merge_sort(arr,result,0,arr.length-1);
		System.out.println(Arrays.toString(arr));
		in.close();
	}

}
