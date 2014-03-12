package com.cs259;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import au.com.bytecode.opencsv.CSVReader;

/*
 * 
 * 1 inch = 2.54 cm
 * 1/3 " / sec
 * 0.846 cm/sec
 * 
 */

public class Cell {
   
	private int pos;
	//4379
	public Cell(int pos)
	{
//		int first = desc.charAt(0) -'A'+1;
//		
//		int second = desc.length() > 1?desc.charAt(1) -'A'+1:0;
//		int third =  desc.length() > 2?desc.charAt(2) -'A'+1:0;
//		
		this.pos = pos;
	}
    
	public static void processData(int x, int y, int pressure) throws IOException {
		
		System.out.println("Cell Number: (" + x + ","+ y +")");
		System.out.println("Pressure reading " + pressure);
		//Calculate pressure value
		double increaseByHowMuch = (0.2 * pressure);
		double ms = (increaseByHowMuch/0.3333)*1000;
		
		System.out.println("[CS259] Actuator displacement " + increaseByHowMuch);
		System.out.println("[CS259] Total time " + ms);
		
        String[] portNames = SerialPortList.getPortNames();
        System.out.println("[CS259] Available com ports");
        for(int i = 0; i < portNames.length; i++){
            System.out.println("[CS259] " + portNames[i]);
        }
        
        SerialPort serialPort = new SerialPort("COM13");
        try {
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);
            byte[] b = new byte[]{119};
	        serialPort.writeBytes(b);
            Thread.sleep(2000);
            b = new byte[]{118};
	        serialPort.writeBytes(b);
	        Thread.sleep((int)ms);
	        b = new byte[]{119};
	        serialPort.writeBytes(b);
            serialPort.closePort();
        }
        catch (SerialPortException | InterruptedException ex) {
            System.out.println(ex);
        }

    }
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		String input = "";
		
		while(true)
		{
		    System.out.println("Please enter call value: ");
		    input = scanner.nextLine();
		    
		    if(!(input == null || input.length() == 0) && input.equalsIgnoreCase("quit"))
		    	break;
		    
		    if(input == null || input.length() == 0)
			{
				System.err.println("Incorrect inputs");
			}
			else
			{
//				try {
//					new Cell(Integer.parseInt(input)).processData("C:\\Users\\manojrthakur\\Downloads\\pressure-reading.csv");
//				} catch (Exception e) {
//					System.out.println("Incorrect Input");
//				}
			}
		}
	}
}
