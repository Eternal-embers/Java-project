package javaCode;

import java.util.Arrays;

public class ArraysCopy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = new int[20];
		int[] arr_copyOf = new int[20];
		int[] arr_copyRange = new int[20];
		int[] arr_arraycopy = new int[20];
		int[] arr_clone = new int[20];
		for(int i = 0;i<20;i++)
			arr[i] = (int)(Math.random()*100+1);
		arr_copyOf = Arrays.copyOf(arr, arr.length);
		arr_copyRange = Arrays.copyOfRange(arr, 0, arr.length);
		System.arraycopy(arr, 0, arr_arraycopy, 0, arr.length);
		arr_clone = arr.clone();
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(arr_copyOf));
		System.out.println(Arrays.toString(arr_copyRange));
		System.out.println(Arrays.toString(arr_arraycopy));
		System.out.println(Arrays.toString(arr_clone));
	}

}
