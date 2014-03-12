package com.cs259;

public class BasicPressureCalculator implements IPressureCalculator {

	@Override
	public double calculatePressure(int[][] arr) {
		
		int base = 0;
		double sum = 0.0d;
		for(int[] arr1: arr)
		{
			for(int i : arr1)
			{
				sum+= i;
				base++;
			}
		}
		return sum/base;
	}

}
