package com.cs259;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class TestBasic {
	
	public static void main(String[] args) {
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
		            
		            byte[] b = new byte[]{118};
			        serialPort.writeBytes(b);
			        Thread.sleep(2000);
			        
			        b = new byte[]{117};
			        serialPort.writeBytes(b);
			        Thread.sleep(2000);
			        			     
			        b = new byte[]{118};
			        serialPort.writeBytes(b);
			        Thread.sleep(2000);
			        
			        serialPort.writeBytes(b);
		            serialPort.closePort();
		            
		        }
		        catch (SerialPortException | InterruptedException ex) {
		            System.out.println(ex);
		        }

	}
	
}
