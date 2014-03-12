package com.cs259;

import javax.swing.JFrame;

public class PressureSoreApp {

	
	//Usage <simulate readings>
	public static void main(String[] args) throws InterruptedException {
		
		boolean simulate = false;
			
		if(args.length > 0)
			try{
				simulate = Boolean.parseBoolean(args[0]);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
		if(simulate)
		{
			new ReadingGenerator(PropertiesManager.getInstance().getSimulationfrequency(),PropertiesManager.getInstance().getDestDir()).start();
			Thread.sleep(1000);
		}
			
		JFrame frame = new JFrame();
		PressureImageapplet p = new PressureImageapplet();
		try
		{	
			p.init();
			p.start();
			frame.getContentPane().add(p);
			frame.resize(350, 700);
			frame.setVisible(true);
		}
		finally
		{
			p.stop();
		}
	}
}