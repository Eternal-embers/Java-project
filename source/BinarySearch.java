package javaCode;

public class BinarySearch {
	public static int binarySearch(int[] list,int key){
		int low = 0;
		int high = list.length - 1;
		while(low<=high){
			int mid = (low + high)/2;
			if(key < list[mid]){
				high = mid-1;//在左边
			}
			else if(key==list[mid]){
				return mid;
			}
			else low = mid + 1;//在右边
		}
		return  -low-1;//如果未找到，返回-low-1；
	}
	
	public static void main(String[] args){
		int length = 30,i,j;
		int list[] = new int[length];
		for(i = 0;i<length;i++)
			list[i] = (int)(Math.random()*length);
		//插入排序
		for(i = 2;i<length;i++){
			int temp = list[i];
			for(j = i-1;j>=0;j--)
				if(temp < list[j]) list[j+1] = list[j];//挪位
				else break;
			list[j+1] = temp;
		}
		for(i = 0;i< length;i++)
			System.out.print(list[i] + " ");
		System.out.println("");
		int key = binarySearch(list,15);
		if(key > 0) System.out.println(list[key]);
		else System.out.println("15 is not found in list");
	}
}
