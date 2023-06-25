package javaCode;

import java.util.Scanner;

public class Method{
    public static int getSum(int a,int b){
        return a+b;
    }
    
    static double getAvg(double a,double b,double c){
        return (a+b+c)/3;
    }
    
    public static void printRect(int a,int b){
        for(int i = 0;i<a;i++){
            for(int j = 0;j<b;j++)
                System.out.print("*");
        System.out.println("");
        }
    }

    public static void sortArr(int[]arr){
        int i,j,k;
        for(i = 0;i<arr.length-1;i++){
        k = i;
            for(j = i;j<arr.length;j++)
                if(arr[k]>arr[j]) k = j;
        if(k!=i){
        arr[k]^=arr[i];
        arr[i]^=arr[k];
        arr[k]^=arr[i];
        }
        }
        for(i = 0;i<arr.length;i++)
            System.out.println(arr[i]);
    }

    public static void Print99(){
        for (int i = 1;i<10;i++) {
            for(int j = 1;j<=i;j++) {
                System.out.print(j + " * " + i + " = " + i * j + "\t");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println(getSum(in.nextInt(),in.nextInt()));
        System.out.println(getAvg(in.nextDouble(),in.nextDouble(),in.nextDouble()));
        printRect(in.nextInt(),in.nextInt());
        int[] arr = new int[5];
        for(int i = 0;i<arr.length;i++) 
            arr[i] = in.nextInt();
        sortArr(arr);
        Print99();
        in.close();
    }
}	
