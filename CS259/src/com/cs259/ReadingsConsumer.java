package com.cs259;

import java.io.IOException;
import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

/*
 * 
 * 1 inch = 2.54 cm
 * 1/3 " / sec
 * 0.846 cm/sec
 * 
 */

public class ReadingsConsumer {

	private static ArrayList<Double> readings = new ArrayList<Double>();
	
	
	public static void processData(int x, int y, double pressure, boolean init) throws IOException {

		if(init)
		{
			System.out.println("[CS259] Initializing Actuator");
			//initialize();
			System.out.println("[CS259] Done initializing");
		}
		int timestamp = (int) System.currentTimeMillis() / (60 * 1000);
		
			timestamp = ((int) System.currentTimeMillis() / (30 * 1000)) - 1;
			System.out.println(timestamp);
			System.out.println("Cell Number: (" + x + "," + y + ")");
			System.out.println("[CS359] Pressure reading " + pressure);
			byte movement = 117;
			if (!readings.isEmpty() && readings.get(readings.size() - 1) < pressure) {
				movement = 118;
			}
			readings.add(pressure);
			//if(movement == 118)
			pressure = Math.abs(pressure - readings.get(readings.size()-2));
			System.out.println("[CS359] Movement  =  " + ((movement == 118)?"backwards" : "forward"));
			// Calculate pressure value
			double increaseByHowMuch = (0.2 * pressure);
			double ms = (increaseByHowMuch / 0.3333) * 1000;

			System.out.println("[CS259] Actuator displacement "+ increaseByHowMuch);
			System.out.println("[CS259] Total time " + ms);

			String[] portNames = SerialPortList.getPortNames();
			System.out.println("[CS259] Available com ports");
			for (int i = 0; i < portNames.length; i++) {
				System.out.println("[CS259] " + portNames[i]);
			}

			SerialPort serialPort = new SerialPort("COM13");
			try {
				serialPort.openPort();
				serialPort.setParams(SerialPort.BAUDRATE_9600,
						SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
				byte[] b = new byte[] { 119 };
				serialPort.writeBytes(b);
				Thread.sleep(2000);
				b = new byte[] { movement };
				serialPort.writeBytes(b);
				Thread.sleep((int) ms);
				b = new byte[] { 119 };
				serialPort.writeBytes(b);
				serialPort.closePort();
			} catch (SerialPortException | InterruptedException ex) {
				System.out.println(ex);
			}
			

	}
	
	private static void initialize()
	{
		SerialPort serialPort = new SerialPort("COM13");
		try {
			serialPort.openPort();
			serialPort.setParams(SerialPort.BAUDRATE_9600,
					SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			byte[] b = new byte[] { 119 };
			serialPort.writeBytes(b);
			Thread.sleep(2000);
			b = new byte[] { 117 };
			serialPort.writeBytes(b);
			Thread.sleep(20000);
			b = new byte[] { 119 };
			serialPort.writeBytes(b);
			serialPort.closePort();
		} catch (SerialPortException | InterruptedException ex) {
			System.out.println(ex);
		}
		
	}
	
	public static void main(String[] args) {

//		Scanner scanner = new Scanner(new InputStreamReader(System.in));
//		String input = "";
//
//		System.out.println("Please enter call value: ");
//		input = scanner.nextLine();
//
//		// if(!(input == null || input.length() == 0) &&
//		// input.equalsIgnoreCase("quit"))
//
//		if (input == null || input.length() == 0) {
//			System.err.println("Incorrect inputs");
//		} else {
//			try {
//				new ReadingsConsumer(Integer.parseInt(input)).processData();
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("Incorrect Input");
//			}
//		}
	}
}
