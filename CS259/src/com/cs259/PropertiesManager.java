package com.cs259;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
	
	public static final String collectionfrequency = "collectionfrequency";
	public static final String accumulationTime = "accumulationTime";
	public static final String pressurecalculator = "pressurecalculator";
	public static final String groupingfactor = "groupingfactor";
	public static final String destinationDir = "destinationDir";
	public static final String simulationFrequency = "simulationFrequency";
	public static final String xoffset = "xoffset";
	public static final String yoffset = "yoffset";
	
	private Properties properties = new Properties() ;  
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	private static PropertiesManager instance = null;
	
	public static PropertiesManager getInstance()
	{
		if(instance == null)
		{
			instance = new PropertiesManager();
		}
		return instance;
	}
	
	public int getCollectionfreq()
	{
		return Integer.parseInt(properties.getProperty(collectionfrequency));
	}
	
	public String getDestDir()
	{
		return (properties.getProperty(destinationDir));
	}
	
	public int getSimulationfrequency()
	{
		return Integer.parseInt(properties.getProperty(simulationFrequency));
	}
	
	public int getAccumulationFreq()
	{
		return Integer.parseInt(properties.getProperty(accumulationTime));
	}
	
	public String getPressureCalculator()
	{
		return (properties.getProperty(pressurecalculator));
	}
	
	public int getGroupingFactor()
	{
		return Integer.parseInt(properties.getProperty(groupingfactor));
	}
	
	public int getXOffset()
	{
		return Integer.parseInt(properties.getProperty(xoffset));
	}
	
	public int getYoffSet()
	{
		return Integer.parseInt(properties.getProperty(yoffset));
	}
	
	private PropertiesManager()
	{	
	    try {
			properties.load(this.getClass().getResourceAsStream("bedsore.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
