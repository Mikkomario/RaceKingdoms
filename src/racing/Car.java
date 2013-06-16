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
	
	private double maxdrivespeed, accelration, turning, maxturning;
	private double turningfriction, turnrate;
	
	
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
		super(x, y, carspritebank.getSprite("test"), drawer, actorhandler);
		
		// Adds the car to the keyhandler (if possible)
		if (keyhandler != null)
			keyhandler.addKeyListener(this);
		
		// Initializes attributes
		this.maxdrivespeed = 10;
		this.turning = 0.01;
		this.accelration = 0.1;
		this.maxturning = 0.4;
		this.turningfriction = 0.015;
		this.turnrate = 0.9;
		
		// Initializes some stats
		setMaxRotation(20);
		setMaxSpeed(25);
		setFriction(0.1);
		setRotationFriction(0.7);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		if (coded)
		{
			// Turns with left / right arrowkey
			if (keyCode == PConstants.LEFT)
			{
				//System.out.println(calculateTurning());
				turn(calculateTurning());
			}
			else if (keyCode == PConstants.RIGHT)
				turn(-calculateTurning());
			
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
		// Adds turbo if C was pressed
		if (!coded)
		{
			if (key == 'c')
				addTurboBoost(10);
		}
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// Doesn't do anything (yet)
	}
	
	@Override
	public void act()
	{
		super.act();
		
		// Also implies the turning friction
		applyTurningFriction();
		// And the turnboost too
		addTurnBoost();
		//System.out.println(getDirection());
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	private void addNormalBoost()
	{
		// Remembers the last speed
		double lastspeed = getSpeed();
		// Adds the boost
		addMotion(getAngle(), getFriction() + this.accelration);
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
	
	private void addTurboBoost(double amount)
	{
		// Adds the boost
		addMotion(getAngle(), amount);
	}
	
	private void turn(double amount)
	{
		// TODO: Make turning less effective when the car moves slowly
		
		// Remembers the last rotation
		double lastrotation = getRotation();
		// Adds the turn
		addRotation(amount);
		// Checks if the car is rotating too fast and does the necessary repairs
		if (Math.abs(getRotation()) > Math.abs(this.maxturning * getSpeed()))
		{
			// If the car was already going too fast, doens't increase the turning
			if (Math.abs(lastrotation) > Math.abs(this.maxturning * getSpeed()))
				setRotation(lastrotation);
			// Otherwise, caps the speed to the max
			else if (getRotation() > 0)
				setRotation(this.maxturning * getSpeed());
			else
				setRotation(-this.maxturning * getSpeed());
		}
	}
	
	private void applyTurningFriction()
	{
		// TODO: Logaritminen rengaskitka!
		
		// Reduces the object's speed depending on how much the object is turning
		double angledifference = getAngleDifference();
		// If there's no difference or no speed, doesn't imply friction
		if (angledifference < 1 || getSpeed() == 0)
			return;
		
		//System.out.println(angledifference);
		
		diminishSpeed(this.turningfriction * angledifference);
	}
	
	private double getAngleDifference()
	{
		double angledifference = Math.abs(getAngle() - getDirection());
		
		if (angledifference > 180)
			angledifference = 360 - angledifference;
		
		return angledifference;
	}
	
	// Makes the car's direction change when the car is turned
	private void addTurnBoost()
	{
		// Calculates the turnboost (a certain amount out of turningfriction)
		double turnboost = getAngleDifference();
		// If there's no turning or no speed, doesn't do a thing
		if (turnboost < 1 || getSpeed() == 0)
			return;
		turnboost *= this.turningfriction;
		
		turnboost *= this.turnrate;
		
		addMotion(getAngle(), turnboost);
	}
	
	private double calculateTurning()
	{
		return getRotationFriction() + this.turning * getSpeed();
	}
}
