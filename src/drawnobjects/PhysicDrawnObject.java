package drawnobjects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import handleds.Actor;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import helpAndEnums.DoublePoint;
import helpAndEnums.HelpMath;

/**
 * In addition to spriteobjects drawing capabilities many basic physics can be 
 * applied to the physicobject
 *
 * @author Gandalf.
 *         Created 28.11.2012.
 */
public abstract class PhysicDrawnObject extends DrawnObject implements Actor
{	
	// ATTRIBUTES	------------------------------------------------------
	
	private double hspeed, vspeed, rotation, friction, rotFriction, maxspeed, 
			maxrotation;
	// Contains each moment affecting the object
	private HashMap<Point, Double> moments;
	
	
	// CONSTRUCTOR	------------------------------------------------------

	/**
	 * Creates a new physicobject with the given information. The object will
	 * be static until motion is applied. There's no friction or rotation friction 
	 * either until those are added. The object is active at default.
	 *
	 * @param x The ingame x-coordinate of the new object
	 * @param y The ingame y-coordinate of the new object
	 * @param drawer The drawablehandler that draws the object (optional)
	 * @param actorhandler The actorhandler that calls the object's act event (optional)
	 */
	public PhysicDrawnObject(int x, int y, DrawableHandler drawer, ActorHandler actorhandler)
	{
		super(x, y, drawer);
		
		// Initializes attributes
		this.hspeed = 0;
		this.vspeed = 0;
		this.rotation = 0;
		this.friction = 0;
		this.rotFriction = 0;
		this.maxspeed = -1;
		this.maxrotation = -1;
		this.moments = new HashMap<Point, Double>();
		
		// Adds the object to the actorhandler if possible
		if (actorhandler != null)
			actorhandler.addActor(this);
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	public void act()
	{
		// Handles the movement of the object
		move();
		rotate();
		implyMoments();
		//System.out.println(getPixelSpeed(new Point(0, 0)));
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
	public void addMotion(double direction, double force)
	{
		//double haccelration = Math.cos(Math.toRadians(direction))*force;
		//double haccelration = HelpMath.lendirX(force, direction);
		//double vaccelration = Math.sin(Math.toRadians(direction))*force;
		//double vaccelration = HelpMath.lendirY(force, direction);
		
		addVelocity(dirHSpeed(direction, force), dirVSpeed(direction, force));
	}
	
	/**
	 * 
	 * Makes the object move towards given direction with given speed
	 *
	 * @param direction Towards what direction will the object move (degrees)
	 * @param speed How fast the objec will be moving (pxl / step)
	 */
	public void setMotion(double direction, double speed)
	{	
		setVelocity(dirHSpeed(direction, speed), dirVSpeed(direction, speed));
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
	public double getDirection()
	{
		return HelpMath.getVectorDirection(getHspeed(), getVspeed());
	}
	
	/**
	 * Changes the objects moving speed, keeping the same direction
	 *
	 * @param speed The object's new speed (pxl / step)
	 */
	public void setSpeed(double speed)
	{
		//setMotion(getDirection(), speed);
		double lastSpeed = getSpeed();
		
		// Changes the velocity
		this.hspeed *= speed / lastSpeed;
		this.vspeed *= speed / lastSpeed;
	}
	
	/**
	 * Changes the object's maximum speed
	 *
	 * @param maxspeed The new maximum speed of the object (negative if you 
	 * don't want to limit the speed (default))
	 */
	public void setMaxSpeed(double maxspeed)
	{
		this.maxspeed = maxspeed;
	}
	
	/**
	 * @return The maximum speed of the object (not used if negative)
	 */
	public double getMaxSpeed()
	{
		return this.maxspeed;
	}
	
	/**
	 * Changes how fast the object can rotate
	 *
	 * @param maxrotation How fast the object can rotate (negative if no limit)
	 */
	public void setMaxRotation(double maxrotation)
	{
		this.maxrotation = maxrotation;
	}
	
	
	// OTHER METHODS	----------------------------------------------------
	
	/**
	 * Reduces the speed of the object (positive or negative) by the given amount
	 *
	 * @param force
	 */
	public void diminishSpeed(double force)
	{
		// Calculates the old speed
		double lastSpeed = getSpeed();
		double newSpeed = lastSpeed;
		
		// Calculates the new speed
		if (lastSpeed <= force)
		{
			// Changes the velocity
			this.hspeed = 0;
			this.vspeed = 0;
		}
		else
		{
			newSpeed -= force;
			// Changes the velocity
			setSpeed(newSpeed);
		}
	}
	
	/**
	 * Adds a new moment to the object.
	 *
	 * @param p The point around which the object will rotate (relative)
	 * @param force How much the object rotates around the point (degrees / step)
	 */
	public void addMoment(Point p, double force)
	{
		if (p == null)
			return;
		
		// If there is no moment affecting the given point, it is added as a 
		// new moment
		if (!this.moments.containsKey(p))
			this.moments.put(p, getRotationFriction() + force);
		// Otherwise the old and the new moment are added together
		else
			this.moments.put(p, this.moments.get(p) + getRotationFriction() + force);
	}
	
	/**
	 * Stops each of the moments affecting the object
	 */
	public void stopMoments()
	{
		this.moments.clear();
	}
	
	/**
	 * The object bounces with a certain object it collides with.
	 *
	 * @param d The object collided with
	 * @param collisionpoint The point in which the collision happens (absolute)
	 * @param bounciness How much the object bounces away from the given object (1+)
	 * @param lostenergymodifier How much energy is lost during the collision (0-1)
	 */
	protected void bounceFrom(DrawnObject d, DoublePoint collisionpoint, 
			double bounciness, double lostenergymodifier)
	{
		// Some of the speed is lost during the collision
		diminishSpeed(getSpeed()*lostenergymodifier);
		
		/*
		Point pixelmovement = getPixelSpeed(collisionpoint);
		//System.out.println(pixelmovement);
		double pixelspeed = Math.abs(pixelmovement.x) + Math.abs(pixelmovement.y);
		double pixeldirection = HelpMath.getVectorDirection(pixelmovement.x, 
				pixelmovement.y);
		
		// If there's no speed, doesn't do anything
		if (pixelspeed == 0)
			return;
		*/
		
		// Calculates the direction towards which the force is applied
		double forcedir = d.getCollisionForceDirection(new 
				Point((int) collisionpoint.getX(), (int) collisionpoint.getY()));
		
		// Calculates the actual amount of force applied to the object
		double force = -HelpMath.getDirectionalForce(getDirection(), getSpeed(), 
				forcedir) * (1 + bounciness);
		
		// Negative force is ignored
		if (force <= 0)
			return;
		
		// Applies the force to the object
		addMotion(forcedir, force);
		
		// TODO: Divide stuff in this method between multiple simpler methods
		// Adds a moment to the object
		// TODO: Should r be relative to scale or width of the object?
		double r = HelpMath.pointDistance(getX(), getY(), collisionpoint.getX(), 
				collisionpoint.getY());
		double tangle = HelpMath.checkDirection(HelpMath.pointDirection(getX(), 
				getY(), collisionpoint.getX(), collisionpoint.getY()) + 90);
		// Calculates the moment
		// The moment also depends of the largest possible range of the object
		// TODO: Add a nice variable here
		double moment = HelpMath.getDirectionalForce(forcedir, force, tangle) 
				* r / getMaxRangeFromOrigin() * 5;
		
		addMoment(negateTransformations(collisionpoint.getX(), 
				collisionpoint.getY()), moment);
		// TODO: Also add same effect to the other object (a new method?)
		// TODO: Add mass and density and height
	}
	
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
		
		// Also checks the maximum speed and rotation
		checkMaxSpeed();
		checkMaxRotation();
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
	
	// Rotates the object according to the moments affecting the object
	private void implyMoments()
	{
		for (Point p: this.moments.keySet())
			rotateAroundRelativePoint(this.moments.get(p), p);
	}
	
	// Slows the speed the amount of given friction
	private void implyFriction()
	{
		diminishSpeed(getFriction());
	}
	
	// Slows the rotation speed the amoutn of given friction
	private void implyRotationFriction()
	{	
		// Slows down the object's rotation
		if (Math.abs(getRotation()) <= getRotationFriction())
			this.rotation = 0;
		else if (getRotation() > 0)
			this.rotation -= getRotationFriction();
		else
			this.rotation += getRotationFriction();
		// Also slows down each of the object's moments
		implyRotationFrictionToMoments();
	}
	
	private void implyRotationFrictionToMoments()
	{
		// If there are no moments, doesn't do anything
		if (this.moments.isEmpty())
			return;

		ArrayList<Point> momentstobeended = new ArrayList<Point>();
		
		// Goes through all the moments
		for (Point p: this.moments.keySet())
		{
			double f = this.moments.get(p);
			
			// If the moment has run out it is no longer recognised
			if (Math.abs(f) < getRotationFriction())
			{
				momentstobeended.add(p);
				continue;
			}
			else if (f > 0)
				f -= getRotationFriction();
			else
				f += getRotationFriction();
			
			// Changes the moment
			this.moments.put(p, f);
		}
		
		// Removes the unnecessary moments
		for (int i = 0; i < momentstobeended.size(); i++)
			this.moments.remove(momentstobeended.get(i));
	}
	
	private void checkMaxSpeed()
	{
		if (this.maxspeed >= 0 && getSpeed() > this.maxspeed)
		{
			//System.out.println(getSpeed());
			setSpeed(this.maxspeed);
		}
	}
	
	private void checkMaxRotation()
	{
		// If maxrotation is negative, skips the whole thing
		if (this.maxrotation < 0)
			return;
		
		// Limits the rotation speed (if needed)
		if (Math.abs(getRotation()) > this.maxrotation)
		{
			if (getRotation() < 0)
				setRotation(-this.maxrotation);
			else
				setRotation(this.maxrotation);
		}
		// Also limits the moment(s) if needed
		for (Point momentorigin: this.moments.keySet())
		{
			double moment = this.moments.get(momentorigin);
			
			if (Math.abs(moment) > this.maxrotation)
			{
				if (moment < 0)
					moment = -this.maxrotation;
				else
					moment = this.maxrotation;
				
				this.moments.put(momentorigin, moment);
			}
		}
	}
	
	private double dirHSpeed(double direction, double speed)
	{
		double checkdir = HelpMath.checkDirection(direction);
		double alpha = checkdir % 90;
		//System.out.println(alpha);
		double firstspeed = alpha / 90 * speed;
		double secondspeed = speed - firstspeed;
		
		if (checkdir >= 270)
			return firstspeed;
		else if (checkdir >= 180)
			return -secondspeed;
		else if (checkdir >= 90)
			return -firstspeed;
		else
			return secondspeed;
	}
	
	private double dirVSpeed(double direction, double speed)
	{
		double checkdir = HelpMath.checkDirection(direction);
		double alpha = checkdir % 90;
		//System.out.println(alpha);
		double firstspeed = alpha / 90 * speed;
		double secondspeed = speed - firstspeed;
		
		if (checkdir >= 270)
			return secondspeed;
		else if (checkdir >= 180)
			return firstspeed;
		else if (checkdir >= 90)
			return -secondspeed;
		else
			return -firstspeed;
	}
	
	// Calculates the speed of the relative pixel
	/**
	 * Calculates the speed of a single pixel in the object
	 *
	 * @param pixel The pixel's relative coordinates
	 * @return The pixel's x- and y-movment (absolute)
	 */
	protected DoublePoint getPixelSpeed(Point pixel)
	{
		DoublePoint absolutestart = transform(pixel.x, pixel.y);
		DoublePoint relativeend = new DoublePoint(pixel.getX(), pixel.getY());
		// Moves the pixel according to rotations / moments
		// Basic rotation
		relativeend = HelpMath.getRotatedPosition(getOriginX(), getOriginY(), 
				relativeend, getRotation());
		// All moments
		for (Point momentorigin: this.moments.keySet())
		{
			relativeend = HelpMath.getRotatedPosition(momentorigin.x, 
					momentorigin.y, relativeend, this.moments.get(momentorigin));
		}
		// Transforms the point into an absolute value
		DoublePoint absoluteend = transform(relativeend.getX(), relativeend.getY());
		// Adds the object's speed
		absoluteend = new DoublePoint(absoluteend.getX() + getHspeed(), 
				absoluteend.getY() + getVspeed());
		
		return new DoublePoint(absoluteend.getX() - absolutestart.getX(), 
				absoluteend.getY() - absolutestart.getY());
	}
}
