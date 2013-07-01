package drawnobjects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DoublePoint;
import helpAndEnums.HelpMath;

/**
 * In addition to physicobject's functions. Advanced physicobject also handles 
 * moments and bouncing from other objects
 *
 * @author Gandalf.
 *         Created 1.7.2013.
 */
public abstract class AdvancedPhysicDrawnObject extends BasicPhysicDrawnObject
{
	// ATTRIBUTES	------------------------------------------------------
	
	// Contains each moment affecting the object
	private HashMap<Point, Double> moments;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new advancedphysicobject with the given information
	 *
	 * @param x The object's position's x-coordinate
	 * @param y The object's position's y-coordinate
	 * @param depth How 'deep' the object is drawn
	 * @param isSolid Can the object be collided with
	 * @param collisiontype What shape the object is collisionwise
	 * @param drawer The drawer that draws the object (optional)
	 * @param collidablehandler The collidablehandler that handles the collision 
	 * checking of the object (optional)
	 * @param collisionhandler The collisionhandler that informs the object 
	 * about collisions (optional)
	 * @param actorhandler The actorhandler that informs the object about steps (optional)
	 */
	public AdvancedPhysicDrawnObject(int x, int y, int depth, boolean isSolid,
			CollisionType collisiontype, DrawableHandler drawer,
			CollidableHandler collidablehandler,
			CollisionHandler collisionhandler, ActorHandler actorhandler)
	{
		super(x, y, depth, isSolid, collisiontype, drawer, collidablehandler,
				collisionhandler, actorhandler);
		
		// Initializes attributes
		this.moments = new HashMap<Point, Double>();
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public void act()
	{
		super.act();
		
		implyMoments();
		implyRotationFrictionToMoments();
		checkMaxRotationForMoments();
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
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
	protected void bounceFrom(DimensionalDrawnObject d, DoublePoint collisionpoint, 
			double bounciness, double lostenergymodifier)
	{
		// Some of the speed is lost during the collision
		diminishSpeed(getSpeed()*lostenergymodifier);
		
		DoublePoint pixelmovement = getPixelSpeed(negateTransformations(
				collisionpoint.getX(), collisionpoint.getY()));
		//System.out.println(pixelmovement);
		double pixelspeed = Math.abs(pixelmovement.getX()) + 
				Math.abs(pixelmovement.getY());
		double pixeldirection = HelpMath.getVectorDirection(pixelmovement.getX(), 
				pixelmovement.getY());
		
		// If there's no speed, doesn't do anything
		if (pixelspeed == 0)
			return;
		
		// Calculates the direction, towards which the force is applied
		double forcedir = d.getCollisionForceDirection(new 
				Point((int) collisionpoint.getX(), (int) collisionpoint.getY()));
		
		// Calculates the actual amount of force applied to the object
		double force = -HelpMath.getDirectionalForce(pixeldirection, pixelspeed, 
				forcedir) * (1 + bounciness);
		
		// Negative force is ignored
		if (force <= 0)
			return;
		
		// Applies the force to the object
		addMotion(forcedir, force);
		
		// TODO: Divide stuff in this method between multiple simpler methods
		// Adds a moment to the object
		double r = HelpMath.pointDistance(getX(), getY(), collisionpoint.getX(), 
				collisionpoint.getY());
		// TODO: Change to either +90 or -90 depending on whether the object 
		// is sliding or stuck on the collided object
		double tangle = HelpMath.checkDirection(HelpMath.pointDirection(getX(), 
				getY(), collisionpoint.getX(), collisionpoint.getY()) - 90);
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
	
	// Rotates the object according to the moments affecting the object
	private void implyMoments()
	{
		for (Point p: this.moments.keySet())
			rotateAroundRelativePoint(this.moments.get(p), p);
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
	
	private void checkMaxRotationForMoments()
	{
		// limits the moment(s) if needed
		for (Point momentorigin: this.moments.keySet())
		{
			double moment = this.moments.get(momentorigin);
			
			if (Math.abs(moment) > getMaxRotation())
			{
				if (moment < 0)
					moment = -getMaxRotation();
				else
					moment = getMaxRotation();
				
				this.moments.put(momentorigin, moment);
			}
		}
	}
}
