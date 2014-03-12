package com.cs259;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class ReadingGenerator extends Thread{
	
	private int generationFreq = 10;
	private String destDir = "";
	
	public ReadingGenerator(int generationFreq, String destDir) {
		super();
		this.generationFreq = generationFreq;
		this.destDir = destDir;
	}

	@Override
	public void run() {
		
			int timestamp = (int)System.currentTimeMillis()/(generationFreq*1000);
			
		    InputStream fis = this.getClass().getResourceAsStream("pressure_supine.csv");  
		    InputStreamReader isr = new InputStreamReader(fis);
			CSVReader csvReader = new CSVReader(isr);
			List<String[]> content = null;
			try {
				content = csvReader.readAll();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			int val = 12;
			Random r = new Random();
			
			while(true)
			{
				timestamp = (int)System.currentTimeMillis()/(generationFreq*1000);
				CSVWriter cwr = null;
				List<String[]> contentTemp = new ArrayList<>();
				contentTemp.add(new String[content.get(0).length]);
				try
				{
					cwr = new CSVWriter(new FileWriter(destDir+"\\pressure_supine-"+timestamp+".csv"));
					//cwr = new CSVWriter(new FileWriter(destDir+"\\pressure_supine.csv"));
					String valueStr = "";
					for(int i = 0 ; i < content.get(0).length;i++)
					{
						valueStr = content.get(0)[i].isEmpty()?"1":content.get(0)[i];
						val = r.nextInt(Integer.parseInt(valueStr)>0?Integer.parseInt(valueStr):1);
						contentTemp.get(0)[i] = valueStr;				
					}
					System.out.println(contentTemp.get(0).length);
					cwr.writeAll(contentTemp);
					cwr.flush();
					Thread.sleep(10);
					ImagePrintGen.generate(destDir+"\\pressure_supine-"+timestamp+".csv", timestamp);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try {
						cwr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(30*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
}
