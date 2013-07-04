package tests;

import java.util.ArrayList;

import listeners.KeyListener;

import processing.core.PApplet;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DepthConstants;
import helpAndEnums.DoublePoint;
import drawnobjects.BasicPhysicDrawnObject;

/**
 * Tests the basic movement functions of the proram
 *
 * @author Gandalf.
 *         Created 4.7.2013.
 */
public class MovementTestBox extends BasicPhysicDrawnObject implements KeyListener
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new testbox with the given information
	 *
	 * @param drawer The drawer that will draw the box (optional)
	 * @param actorhandler The actorhandler that will inform the box of steps (optional)
	 * @param keylistenerhandler The keylistenerhandler that will inform the box 
	 * about keypresses (optional)
	 */
	public MovementTestBox(DrawableHandler drawer, ActorHandler actorhandler, 
			KeyListenerHandler keylistenerhandler)
	{
		super(500, 300, DepthConstants.NORMAL, false, CollisionType.BOX, drawer, 
				null, null, actorhandler);
		setBoxCollisionPrecision(1, 1);
		
		// Adds the object to the handler
		if (keylistenerhandler != null)
			keylistenerhandler.addKeyListener(this);
	}
	
	
	// IMPLEMENTED METHODS

	@Override
	public void onCollision(ArrayList<DoublePoint> colpoints,
			Collidable collided)
	{
		// Does nothing
	}

	@Override
	public int getWidth()
	{
		return 100;
	}

	@Override
	public int getHeight()
	{
		return 100;
	}

	@Override
	public int getOriginX()
	{
		return 50;
	}

	@Override
	public int getOriginY()
	{
		return 50;
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		applet.rect(0, 0, 100, 100);
	}

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		if (!coded)
		{
			if (key == 'd')
				addMotion(0, 0.1);
		}
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}
	
}
