package tests;

import processing.core.PApplet;
import handlers.CollidableHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;

/**
 * This class works like a testbox except it is an ellipse. It is used for 
 * testing collisions with circular objects
 *
 * @author Gandalf.
 *         Created 7.7.2013.
 */
public class TestEllipse extends TestBox
{
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new test ellipse with the given information
	 *
	 * @param drawer The drawer that will draw the object (optional)
	 * @param collidablehandler The handler that will handle the object's 
	 * collision detection (optional)
	 */
	public TestEllipse(DrawableHandler drawer,
			CollidableHandler collidablehandler)
	{
		super(drawer, collidablehandler);
		
		// Sets the collision system
		setCollisionType(CollisionType.CIRCLE);
		setRadius(50);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public void drawSelfBasic(PApplet applet)
	{
		applet.ellipse(0, 0, 100, 100);
	}
}
