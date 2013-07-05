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
import helpAndEnums.Movement;

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
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * Returns a momnet affecting the object from the given point
	 *
	 * @param origin The origin of the point returned
	 * @return The amount of rotation around the point
	 */
	protected double getMoment(Point origin)
	{
		if (this.moments.containsKey(origin))
			return this.moments.get(origin);
		else
			return 0;
	}
	
	/**
	 * Sets a moment to a new value
	 *
	 * @param origin The origin of the moment
	 * @param moment How much rotation the moment has
	 */
	protected void setMoment(Point origin, double moment)
	{
		this.moments.put(origin, moment);
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
			this.moments.put(p, force);
		// Otherwise the old and the new moment are added together
		else
			this.moments.put(p, this.moments.get(p) + force);
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
		getMovement().diminishSpeed(getMovement().getSpeed()*lostenergymodifier);
		
		// TODO: There must be something wrong with the pixelspeed or something 
		// since the object bounces even when bounciness = 0
		Movement pixelmovement = getPixelRotationMovement(collisionpoint);
		double pixelspeed = pixelmovement.getSpeed();
		double pixeldirection = pixelmovement.getDirection();
		
		// If there's no speed, doesn't do anything
		if (getMovement().getSpeed() == 0)
			return;
		
		// Calculates the direction, towards which the force is applied
		double forcedir = d.getCollisionForceDirection(collisionpoint.getAsPoint());
		
		// Calculates the actual amount of force applied to the object
		Movement oppmovement = 
				getMovement().getOpposingMovement().getDirectionalMovement(forcedir);
		double opprotationforce = -HelpMath.getDirectionalForce(pixeldirection, 
				pixelspeed, forcedir);
		double force = bounciness * (oppmovement.getSpeed() + opprotationforce);
		
		// Adds the opposing force and the force (if they are not negative)
		if (Math.abs(oppmovement.getDirection() - forcedir) < 45)
		{
			addOpposingForce(oppmovement.getSpeed(), opprotationforce, 
					forcedir, collisionpoint);
			if (force > 0)
				addForce(force, forcedir, collisionpoint);
		}
		
		/*
		pixelmovement = getPixelSpeed(negateTransformations(
				collisionpoint.getX(), collisionpoint.getY()));
		pixelspeed = Math.abs(pixelmovement.getX()) + 
				Math.abs(pixelmovement.getY());
		pixeldirection = HelpMath.getVectorDirection(pixelmovement.getX(), 
				pixelmovement.getY());
		System.out.println(-HelpMath.getDirectionalForce(pixeldirection, pixelspeed, 
				forcedir));
		*/
		
		// TODO: Divide stuff in this method between multiple simpler methods
		// TODO: Also add same effect to the other object (a new method?)
		// TODO: Add mass and density and height
		// TODO: Add methods: addForce and addOpposingForce (which add the 
		// moment in a different manner)
	}
	
	// Calculates the speed of the relative pixel
	/**
	 * Calculates the speed of a single pixel in the object
	 *
	 * @param pixel The pixel's relative coordinates
	 * @return The pixel's x- and y-movment (absolute)
	 */
	protected Movement getPixelMovement(DoublePoint pixel)
	{
		// TODO: Check whether the tangent should be +90 or -90. I'm too 
		// confused right now
		return Movement.movementSum(getMovement(), getPixelRotationMovement(pixel));
		
		/* Old version, use if the new one doesn't work
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
		absoluteend = new DoublePoint(absoluteend.getX() + getMovement().getHSpeed(), 
				absoluteend.getY() + getMovement().getVSpeed());
		
		return new DoublePoint(absoluteend.getX() - absolutestart.getX(), 
				absoluteend.getY() - absolutestart.getY());
		*/
	}
	
	private Movement getPixelRotationMovement(DoublePoint pixel)
	{
		// Adds the basic rotation
		Movement pixelmovement = Movement.createMovement(
						HelpMath.pointDirection(getX(), getY(), pixel.getX(), 
								pixel.getY()) + 90, getRotation());
		// Adds movement caused by the moments
		for (Point relmomentorigin: this.moments.keySet())
		{
			// Calculates the momentorigin's absolute position
			DoublePoint absmomentorigin = transform(relmomentorigin.x, 
					relmomentorigin.y);
			// Adds the movement
			pixelmovement = Movement.movementSum(pixelmovement, 
					Movement.createMovement(
					HelpMath.pointDirection(absmomentorigin.getX(), 
					absmomentorigin.getY(), pixel.getX(), pixel.getY()) + 90, 
					this.moments.get(relmomentorigin)));
		}
		
		return pixelmovement;
	}
	
	// Rotates the object according to the moments affecting the object
	private void implyMoments()
	{
		// TODO: Take rotationfriction into account somewhere?
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
	
	private void addForce(double force, double forcedir, DoublePoint forcepixel)
	{
		// Applies the force to the object
		addMotion(forcedir, force);
		// TODO: Get a nice number here too :)
		// TODO: Weird that it works better when there's a negative sign...?
		addRotation(-calculateMoment(forcedir, force, forcepixel, 
				new DoublePoint(getX(), getY())));
	}
	
	private void addOpposingForce(double movementforce, double rotationforce, 
			double forcedir, DoublePoint colpixel)
	{
		//System.out.println(movementforce);
		// Applies the force to the object (only movementforce counts)
		addMotion(forcedir, movementforce);
		
		// Applies moment to all (the other) points in the object
		DoublePoint[] colpoints = getCollisionPoints();
		
		// TODO: Doesn't work right when the speed is too high!
		for (int i = 0; i < colpoints.length; i++)
		{
			DoublePoint colpoint = colpoints[i];
			// TODO: Try to come up with a way to always get nice numbers here
			double moment = calculateMoment(forcedir, 
					0.3*movementforce + 0.3*rotationforce, colpoint, colpixel);
			// TODO: Kinda works but the whole moment system is a bit too aggressive
			addMoment(negateTransformations(colpoint.getX(), colpoint.getY()), 
					moment);
		}
		/*
		addMoment(negateTransformations(pixel.getX(), 
				pixel.getY()), modifier * calculateMoment(forcedir, force, pixel));
		*/
	}
	
	private double calculateMoment(double forcedir, double force, 
			DoublePoint forcepixel, DoublePoint rotationpixel)
	{
		// Calculates the range
		double r = HelpMath.pointDistance(rotationpixel.getX(), rotationpixel.getY(), 
				forcepixel.getX(), forcepixel.getY());
		// Calculates the right direction for the force
		double tangle = HelpMath.checkDirection(HelpMath.pointDirection(
				rotationpixel.getX(), rotationpixel.getY(), forcepixel.getX(), 
				forcepixel.getY()) - 90);
		//System.out.println(HelpMath.getDirectionalForce(forcedir, force, tangle));
		// Calculates the moment
		// The moment also depends of the largest possible range of the object
		// TODO: Add a nice variable here
		return HelpMath.getDirectionalForce(forcedir, force, tangle) 
				* r / getMaxRangeFromOrigin();
	}
}
