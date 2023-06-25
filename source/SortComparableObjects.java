package javaCode;

import java.math.BigInteger;

public class SortComparableObjects {
	
	public static void main(String[] args) {
		String[] cities = {"Savannah","Boston","Atlanta","Tampa"};
		java.util.Arrays.sort(cities);
		for(String city: cities)
			System.out.println(city + "");
		System.out.println();
		BigInteger[] hugeNumbers = {
				new BigInteger("23048023840238048023984"),
				new BigInteger("248038053405304084305830"),
				new BigInteger("483058405380480548058304")
				};
		java.util.Arrays.sort(hugeNumbers);
		for(BigInteger number: hugeNumbers)
			System.out.println(number + " ");
	}

}
