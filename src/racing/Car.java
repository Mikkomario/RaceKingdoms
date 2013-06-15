package racing;

import processing.core.PConstants;
import graphic.SpriteBank;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import drawnobjects.SpriteObject;

/**
 * Car is the playable car that races around the stages
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class Car extends SpriteObject implements listeners.KeyListener
{	
	// ATTRIBUTES	-----------------------------------------------------
	
	private double maxdrivespeed;
	private double turning;
	private double accelration;
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new car
	 *
	 * @param x The new x-coordinate of the car (ingame pxl)
	 * @param y The new y-coordinate of the car (ingame pxl)
	 * @param drawer The drawablehandler that draws the car (optional)
	 * @param actorhandler The actorhandler that moves the car (optional)
	 * @param keyhandler The keylistenerhandler that informs the car of the 
	 * keypresses (optional)
	 * @param carspritebank The Spritebank that holds the car's sprite
	 */
	public Car(int x, int y, DrawableHandler drawer,
			ActorHandler actorhandler, KeyListenerHandler keyhandler, 
			SpriteBank carspritebank)
	{
		super(x, y, carspritebank.getSprite("testcar"), drawer, actorhandler);
		
		// Adds the car to the keyhandler (if possible)
		if (keyhandler != null)
			keyhandler.addKeyListener(this);
		
		// Initializes attributes
		this.maxdrivespeed = 10;
		this.turning = 2;
		this.accelration = 3;
		
		// Initializes some stats
		setMaxRotation(20);
		setMaxSpeed(25);
		setFriction(0.5);
		setRotationFriction(0.2);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		if (coded)
		{
			// Turns with left / right arrowkey
			if (keyCode == PConstants.LEFT)
				addRotation(this.turning);
			else if (keyCode == PConstants.RIGHT)
				addRotation(-this.turning);
			
			// Goes forward with up arrowkey
			else if (keyCode == PConstants.UP)
			{
				addNormalBoost();
			}
		}
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// TODO Auto-generated method stub.
		
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	private void addNormalBoost()
	{
		// Remembers the last speed
		double lastspeed = getSpeed();
		// Adds the boost
		addMotion(getAngle(), this.accelration);
		// Checks if the car is going too fast and does the necessary repairs
		if (getSpeed() > this.maxdrivespeed)
		{
			// If the car was already going too fast, boost only affects direction
			if (lastspeed > this.maxdrivespeed)
				setSpeed(lastspeed);
			// Otherwise, caps the speed to the max
			else
				setSpeed(this.maxdrivespeed);
		}
	}

}
