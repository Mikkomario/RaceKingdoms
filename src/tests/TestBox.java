package tests;

import processing.core.PApplet;
import drawnobjects.DrawnObject2D;

/**
 * This class is a simple box that can be drawn
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class TestBox extends DrawnObject2D
{
	/**
	 * Creates a new testbox that does nothing but can be drawn
	 */
	public TestBox()
	{
		super(200, 200);
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public double getOriginX()
	{
		return 50;
	}

	@Override
	public double getOriginY()
	{
		return 50;
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		applet.rect(0, 0, 100, 100);
	}
}
