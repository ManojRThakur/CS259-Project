package com.cs259;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.tc33.jheatchart.HeatChart;

import au.com.bytecode.opencsv.CSVReader;

public class ImagePrintGen {

	public static void main(String[] args) throws IOException {
		
		generate("E:\\UCLA\\Winter2014\\CS259\\Readings\\pressure_supine.csv", 123);
	}
	
	public static void generate(String file, long timestamp) throws IOException {
		CSVReader csvReader = new CSVReader(new FileReader(file));
		List<String[]> content = csvReader.readAll();
		String[] arr = content.get(0);
		System.out.println(arr.length);
		double[][] matrix = new double[128][];
		for(int i = 0 ; i < matrix.length;i++)
			matrix[i] = new double[64];
		
		int row = -1;
		for (int i = arr.length-1; i >= 0 && row < 128; i--) {
			if (i % 64 == 0) {
				row++;
				if (row < 128)
					matrix[row] = new double[64];
				else
					break;
			}
			if (arr[i] != null)
				matrix[row][i % 64] = Integer.parseInt(arr[i].isEmpty()?"0":arr[i]);
			else
				System.out.println(i);
		}
		
		// Create our heat map chart using our data.
		HeatChart map = new HeatChart(matrix);

		// Customise the chart.
		map.setCellSize(new Dimension(10, 10));
		map.setTitle("Pressure Values");
		map.setXAxisLabel("X Axis");
		map.setYAxisLabel("Y Axis");
		map.setCellSize(new Dimension(10, 10));
		// Output the chart to a file.
		map.saveToFile(new File(PropertiesManager.getInstance().getDestDir()+"\\pressure-"+timestamp+".jpg"));
	}
}
