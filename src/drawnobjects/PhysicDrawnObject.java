package drawnobjects;

import racekingdoms.HelpMath;
import handleds.Actor;

/**
 * In addition to spriteobjects drawing capabilities many basic physics can be 
 * applied to the physicobject
 *
 * @author Gandalf.
 *         Created 28.11.2012.
 */
public abstract class PhysicDrawnObject extends DrawnObject2D implements Actor
{
	
	// TODO: Test this class
	
	// ATTRIBUTES	------------------------------------------------------
	
	private double hspeed, vspeed, rotation, friction, rotFriction;
	private boolean active;
	
	
	// CONSTRUCTOR	------------------------------------------------------

	/**
	 * Creates a new physicobject with the given information. The object will
	 * be static until motion is applied. There's no friction or rotation friction 
	 * either until those are added. The object is active at default.
	 *
	 * @param x The ingame x-coordinate of the new object
	 * @param y The ingame y-coordinate of the new object
	 */
	public PhysicDrawnObject(int x, int y)
	{
		super(x, y);
		
		this.hspeed = 0;
		this.vspeed = 0;
		this.rotation = 0;
		this.friction = 0;
		this.rotFriction = 0;
		this.active = true;
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	public boolean isActive()
	{
		return this.active;
	}
	
	@Override
	public void act()
	{
		// Handles the movement of the object
		move();
		rotate();
	}
	
	@Override
	public boolean inActivate()
	{
		this.active = false;
		return true;
	}
	
	@Override
	public boolean activate()
	{
		this.active = true;
		return true;
	}
	
	
	// GETTERS & SETTERS	-----------------------------------------------
	
	/**
	 * @return The speed with which the object is moving horizontally (pxl / step)
	 */
	public double getHspeed()
	{
		return this.hspeed;
	}
	
	/**
	 * @return The speed with which the object is moving vertically (pxl / step)
	 */
	public double getVspeed()
	{
		return this.vspeed;
	}
	
	/**
	 * @return The speed with which the object is moving (pxl / step)
	 */
	public double getSpeed()
	{
		return Math.abs(this.hspeed) + Math.abs(this.vspeed);
	}
	
	/**
	 * Changes the object's movement speed
	 *
	 * @param hspeed The new horizontal speed (pxl / step)
	 * @param vspeed The new vertical speed (pxl / step)
	 */
	public void setVelocity(double hspeed, double vspeed)
	{
		this.hspeed = hspeed;
		this.vspeed = vspeed;
	}
	
	/**
	 * 
	 * Adds some horizontal and vertical motion to the object. The movement stacks 
	 * with the previous movement speed.
	 *
	 * @param haccelration How much speed is increased horizontally (pxl / step)
	 * @param vacceltarion How much speed is increased vertically (pxl / step)
	 */
	public void addVelocity(double haccelration, double vacceltarion)
	{
		this.hspeed += haccelration;
		this.vspeed += vacceltarion;
	}
	
	/**
	 * @return How much the object is rotated at each step (degrees / step)
	 */
	public double getRotation()
	{
		return this.rotation;
	}
	
	/**
	 * Changes how fast the object rotates around its origin
	 *
	 * @param rotation The speed with which the object rotates (degrees / step)
	 */
	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}
	
	/**
	 * @return How much the object's speed is reduced at each step (pxl / step)
	 */
	public double getFriction()
	{
		return this.friction;
	}
	
	/**
	 * Changes how much the object's speed is reduced at each step
	 *
	 * @param friction the new friction of the object (pxl / step)
	 */
	public void setFriction(double friction)
	{
		this.friction = friction;
	}
	
	/**
	 * @return How much the rotation of the sprite is reduced at each step
	 */
	public double getRotationFriction()
	{
		return this.rotFriction;
	}
	
	/**
	 * Changes how much the rotation of the sprite is reduced at each step
	 * 
	 * @param rotationFriction How much the rotation is reduced at each step (degrees / step)
	 */
	public void setRotationFriction(double rotationFriction)
	{
		this.rotFriction = rotationFriction;
	}
	
	/**
	 *Changes how much the object rotates at each step. The rotation accelration 
	 *stacks with the previous rotation speed.
	 *
	 * @param raccelration How much faster will the object be rotated (degrees / step)
	 */
	public void addRotation(double raccelration)
	{
		this.rotation += raccelration;
	}
	
	/**
	 * Adds the objects movement towards the given direction
	 *
	 * @param direction Direction towards wich the force is applied (degrees)
	 * @param force The amount of force applied to the object (pxl / step)
	 */
	public void addMotion(int direction, double force)
	{
		//double haccelration = Math.cos(Math.toRadians(direction))*force;
		double haccelration = HelpMath.lendirX(force, direction);
		//double vaccelration = Math.sin(Math.toRadians(direction))*force;
		double vaccelration = HelpMath.lendirY(force, direction);
		
		addVelocity(haccelration, vaccelration);
	}
	
	/**
	 * 
	 * Makes the object move towards given direction with given speed
	 *
	 * @param direction Towards what direction will the object move (degrees)
	 * @param speed How fast the objec will be moving (pxl / step)
	 */
	public void setMotion(int direction, double speed)
	{
		double newhspeed = HelpMath.lendirX(speed, direction);
		double newvspeed = HelpMath.lendirY(speed, direction);
		
		setVelocity(newhspeed, newvspeed);
	}
	
	/**
	 * 
	 * Changes the object's movement direction
	 *
	 * @param direction The object's new direction (degrees)
	 */
	public void setDirection(int direction)
	{
		setMotion(direction, getSpeed());
	}
	
	/**
	 * @return The direction towards which the object is currently moving
	 */
	public int getDirection()
	{
		return (int) (Math.toDegrees(Math.atan2(getVspeed(), getHspeed())));
	}
	
	/**
	 * Changes the objects moving speed, keeping the same direction
	 *
	 * @param speed The object's new speed (pxl / step)
	 */
	public void setSpeed(double speed)
	{
		setMotion(getDirection(), speed);
	}
	
	
	// OTHER METHODS	----------------------------------------------------
	
	// Moves the object and handles the friction
	private void move()
	{
		//this.x += getHspeed();
		//this.y += getVspeed();
		addPosition(getHspeed(), getVspeed());
		
		// Checks the friction
		if (getFriction() == 0)
			return;
		
		implyFriction();
	}
	
	// Rotates teh object and handles the rotation friction
	private void rotate()
	{
		//this.angle += getRotation();
		addAngle(getRotation());
		
		if (getRotationFriction() == 0)
			return;
		
		implyRotationFriction();
	}
	
	// Slows the speed the amount of given friction
	private void implyFriction()
	{
		// Calculates the old speed
		double lastSpeed = getSpeed();
		double newSpeed = lastSpeed;
		
		// Calculates the new speed
		if (lastSpeed <= getFriction())
		{
			// Changes the velocity
			this.hspeed = 0;
			this.vspeed = 0;
		}
		else
		{
			newSpeed -= getFriction();
			// Changes the velocity
			this.hspeed *= newSpeed / lastSpeed;
			this.vspeed *= newSpeed / lastSpeed;
		}
	}
	
	// Slows the rotation speed the amoutn of given friction
	private void implyRotationFriction()
	{	
		if (Math.abs(getRotation()) <= getRotationFriction())
			this.rotation = 0;
		else if (getRotation() > 0)
			this.rotation -= getRotationFriction();
		else
			this.rotation += getRotationFriction();
	}
}