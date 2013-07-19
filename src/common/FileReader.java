package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import processing.core.PApplet;

/**
 * Filereader is an abstract class that allows the subclasses to read files 
 * and react to them somehow.
 *
 * @author Gandalf.
 *         Created 19.7.2013.
 */
public abstract class FileReader
{
	// ABSTRACT METHODS	--------------------------------------------------
	
	/**
	 * This method is called each time a line is read from a file
	 *
	 * @param line The line read from the file
	 */
	protected abstract void onLine(String line);
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Checks if a certain file exists
	 *
	 * @param filename the name of the file
	 * @return Does the file exist
	 */
	public static boolean checkFile(String filename)
	{
		File test = new File(filename);
		return test.isFile();
	}
	
	/**
	 * Reads a file and makes the object react to it somehow
	 *
	 * @param filename
	 * @param applet
	 */
	protected void readFile(String filename, PApplet applet)
	{
		// First checks if the file actually exists
		if (!checkFile(filename))
		{
			System.out.println("Did not find a file named " + filename);
			return;
		}
		
		BufferedReader reader = applet.createReader(filename);
		String line = "";
		
		try
		{
			// Loops until the file ends
			while (true)
			{	
				line = reader.readLine();
				if (line == null)
				{
					reader.close();
					return;
				}
				
				// Skips the empty lines
				if (line.length() == 0)
					continue;
				
				onLine(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			line = null;
		}
	}
}
