package javaCode;
import java.util.Arrays;
public class Arr {
	public static void main(String[] args) {
		int length = 30;
		int arr[] = new int[length];
		for(int i = 0;i < length;i++)
			arr[i] = (int)(Math.random()*length);
		//Arrays.sort(arr);
		Arrays.sort(arr);
		//Arrays.binarySearch(arr,key);
		//Arrays.binarySearch(arr,fromindex,toindex,key);
		int key = Arrays.binarySearch(arr,15);
		if(key >= 0) System.out.println(arr[key]);
		else System.out.println("15 is not found in arr");
		for(int i = 0;i < length;i++)
			System.out.print(arr[i] + " ");
		System.out.println(" ");
		String str = (String)Arrays.toString(arr);
		System.out.println(str);
		//Arrays.toString(arr),返回String
		//Arrays.copyOf(arr,newlength)；返回一个从arr中复制newlength长度的数组
		//Arrays.copyRange(arr,from,to);返回arr中下标from到to组成的数组
		//Copyof会将原数组中的数据清空
		int tar[] = Arrays.copyOfRange(arr, 5, length);
		for(int i =0;i < length-1;i++)
			System.out.print(tar[i] + " ");
		System.out.print("");
		//下面的s无法打印，因为原arr中的数据已经由于copyOf()被转移了
		String s = (String)Arrays.toString(arr);
		System.out.println(s);
		
	}
}
