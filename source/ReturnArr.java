package javaCode;

public class ReturnArr {
	//当一个方法返回一个数组时，数组的引用被返回
	public static int[] sum(int[] list) {
		int []sum = new int[list.length];
		sum[0] = list[0];
		for(int i = 1;i<list.length;i++) {
			sum[i]+=sum[i-1]+list[i];
		}
		return sum;
	}
	public static void main(String[] args) {
		int list[] = new int[10];
		for(int i = 0;i < 10;i++)
			list[i] = i;
		int[] arr = sum(list);
		for(int i = 0;i < 10;i++)
			System.out.print(arr[i] + " ");
	}
}
