package com.cs259;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import au.com.bytecode.opencsv.CSVReader;

/*
 * 
 * 
(28,18)		(345,18)
(28,652)	(345,652)


width = 297 - 64
height = 594 - 128

Factor: 4.95

100,297
20, 60
 * 
 */
public class PressureImageapplet extends Applet implements java.awt.event.MouseListener   {
	Image img;
	MediaTracker tr;
	double[][] matrix = new double[128][];
	boolean init = true;
	String fileName = "E:\\UCLA\\java-heat-map.jpg";
	Button b = new Button("Refresh");
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		
		CSVReader csvReader = null;
		int timestamp = (int)System.currentTimeMillis()/(PropertiesManager.getInstance().getSimulationfrequency()*1000);
		
		//String fileName = PropertiesManager.getInstance().getDestDir()+"\\pressure_supine-"+timestamp+".csv";
		String fileName = PropertiesManager.getInstance().getDestDir()+"\\pressure_supine.csv";
		try {
			csvReader = new CSVReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String[]> content = null;
		try {
			content = csvReader.readAll();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] arr = content.get(0);
		
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
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		tr = new MediaTracker(this);
		
		BufferedImage img = null;
		try {
			    img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
				e.printStackTrace();
		}
		tr.addImage(img, 0);
		
		g.drawImage(img, 0, 0,64*5,128*5, this);
		add(b);
	      
	}
	
	
//	@Override
//	public boolean action(Event evt, Object what) {
//		if(evt.target == b)
//		{
//			refreshImage();
//		}
//		return true;
//	}
	
	private void refreshImage()
	{
		Graphics g  = getGraphics();
		this.fileName = "E:\\UCLA\\java-heat-map-1.jpg";
		paint(g);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent ev) {
			
		int x ,y ; 
	    x = ev.getX() ;     
	    y = ev.getY() ;     
	   
	    int newx = (x+PropertiesManager.getInstance().getXOffset())/5;
	    int newy = (y+PropertiesManager.getInstance().getYoffSet())/5;
	    System.out.println("Pressure position at (" + newx + "," + newy+") " + matrix[newy][newx] );
	    	
	    try {
			ReadingsConsumer.processData(newx, newy, matrix[newy][newx], init);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(init)
		    init = false;
	    ev.consume();
	    //repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		
	}
	
}