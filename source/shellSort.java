package javaCode;

public class shellSort {
public static void fillArray(int array[]) {
	for(int i = 0;i<array.length;i++) 
		array[i] = (int)(Math.random()*100+1);
}
public static void showArray(int array[]) {
	for(int i = 0;i<array.length;i++) 
		System.out.printf(" %3d ",array[i]);
	System.out.println("");
}
	public static void main(String[] args) {
		int j;
		int[] array = new int[20];
		fillArray(array);
		showArray(array);
		for(int gap = array.length/2;gap>0;gap/=2) {
			for(int i = gap;i<array.length;i++) {
				int temp = array[i];
				for(j = i;j>=gap && temp<array[j-gap];j-=gap) 
					array[j] = array[j-gap];
				array[j] = temp;
			}
		}
		showArray(array);
	}
}
