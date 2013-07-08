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
	
	
	// ABSTRACT METHODS --------------------------------------------------
	
	/**
	 * @return How high the object is in the third dimension (including scaling). 
	 * Used for calculating the object's mass. Use a negative value for circular 
	 * objects that are shaped like a 3-dimensional ball.
	 */
	public abstract int getZHeight();
	
	/**
	 * @return The density of the object. Including how well the object fits 
	 * into its "box" or "circle" depending on its collision type. So the density 
	 * of a wooden half of a ball would be woods density * 0.5.
	 */
	public abstract int getDensity();
	
	
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
	 * @param frictionmodifier How much energy is lost during the collision (0-1)
	 */
	public void bounceFrom(DimensionalDrawnObject d, DoublePoint collisionpoint, 
			double bounciness, double frictionmodifier)
	{
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
		
		bounce(collisionpoint, bounciness, frictionmodifier, oppmovement, 
				opprotationforce, forcedir);
	}
	
	private void bounce(DoublePoint collisionpoint, double bounciness, 
			double frictionmodifier, Movement oppmovement, 
			double opprotationspeed, double forcedir)
	{
		double force = bounciness * (oppmovement.getSpeed() + 
				opprotationspeed);
		
		// Adds the opposing force and the force (if they are not negative)
		if (HelpMath.getAngleDifference180(oppmovement.getDirection(), forcedir) < 90)
		{
			addOpposingForce(oppmovement.getSpeed(), opprotationspeed, 
					forcedir, collisionpoint);
			if (force > 0)
				addForce(force, forcedir, collisionpoint);
			
			// Also adds friction if needed
			if (frictionmodifier > 0)
				addWallFriction(oppmovement, frictionmodifier);
		}
		
		// TODO: Also add same effect to the other object (a new method?)
		// TODO: Add mass and density and height
	}
	
	/**
	 * Bounces from an object pushing it away at the same time
	 *
	 * @param d The object collided with
	 * @param collisionpoint The point where the collision happens
	 * @param bounciness How much additional speed is gained (0+)
	 * @param frictionmodifier What is the friction modifier between the two 
	 * objects
	 */
	protected void bounceInteractivelyFrom(AdvancedPhysicDrawnObject d, 
			DoublePoint collisionpoint, double bounciness, 
			double frictionmodifier)
	{
		// Calculates the direction of the collision
		double oppdir = d.getCollisionForceDirection(collisionpoint.getAsPoint());
		double forcedir = HelpMath.checkDirection(oppdir + 180);
		
		// Calculates the directional momentums of the objects
		Movement dirmovementthis = getMovement().getDirectionalMovement(forcedir);
		Movement dirmovementother = d.getMovement().getDirectionalMovement(oppdir);
		double speedthis = dirmovementthis.getSpeed();
		double speedother = dirmovementother.getSpeed();
		
		Movement rotationmovementthis = 
				getPixelRotationMovement(collisionpoint).getDirectionalMovement(forcedir);
		Movement rotationmovementother = 
				d.getPixelRotationMovement(collisionpoint).getDirectionalMovement(oppdir);
		double rotationspeedthis = rotationmovementthis.getSpeed();
		double rotationspeedother = rotationmovementother.getSpeed();
		// TODO: One might want to add a modifier to the rotationspeed
		
		// if the colliding object doesn't have any speed. Does nothing 
		// (the bounce should be done independently in the other object)
		if (speedthis == 0)
			return;
		
		// Checks that the objects are actually colliding to each other and 
		// not going to opposite directions
		// TODO: Use dirmovement here
		Movement pixmovementthis = getPixelMovement(collisionpoint).
				getDirectionalMovement(forcedir);
		Movement pixmovementother = d.getPixelMovement(collisionpoint).
				getDirectionalMovement(oppdir);
		if (HelpMath.getAngleDifference180(pixmovementthis.getDirection(), 
				pixmovementother.getDirection()) >= 90)
			return;
		
		// Changes the signs of the momentums and speeds if needed
		if (HelpMath.getAngleDifference180(dirmovementthis.getDirection(), forcedir) > 90)
			speedthis *= -1;
		if (HelpMath.getAngleDifference180(dirmovementother.getDirection(), oppdir) > 90)
			speedother *= -1;
		if (HelpMath.getAngleDifference180(rotationmovementthis.getDirection(), forcedir) > 90)
			rotationspeedthis *= -1;
		if (HelpMath.getAngleDifference180(rotationmovementother.getDirection(), oppdir) > 90)
			rotationspeedother *= -1;
		
		double massthis = getMass();
		double massother = d.getMass();
		double momentumthis = (speedthis + rotationspeedthis)* massthis;
		double momentumother = (speedother + rotationspeedother) * massother;
		
		// Also, if the objects are moving to the same direction with the same 
		// speed, doesn't do anything
		/*
		else if (pixmovementthis.getSpeed() == pixmovementother.getSpeed())
			return;
		*/
		// Calculates the maximum momentum the other may apply to the collided 
		// object
		// That is the momentum it would need to gain to start moving with the 
		// colliding object's speed
		double maxMomentumApplied = momentumother + speedthis * d.getMass();
		
		// If the object doesn't have that much momentum, it uses all its momentum 
		// to push the object
		if (momentumthis < maxMomentumApplied)
		{
			bounce(collisionpoint, bounciness, frictionmodifier, 
					dirmovementthis, rotationspeedthis, oppdir);
			
			double force = momentumthis / massother;
			
			d.addForce(force * (1 + bounciness), forcedir, collisionpoint);
		}
		// Otherwise, pushes the other object with the maximum force and leaves 
		// some of the momentum to the pushing object
		else
		{
			double momentumtransferred = maxMomentumApplied - momentumother;
			double pushforce = momentumtransferred / massother;
			d.addForce(pushforce, forcedir, collisionpoint);
			double speedloss = momentumtransferred / massthis;
			double rotationspeedloss = rotationspeedthis / speedloss;
			speedloss = speedthis / speedloss;
			
			bounce(collisionpoint, bounciness, frictionmodifier, 
					Movement.createMovement(oppdir, speedloss), 
					rotationspeedloss, oppdir);
		}
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
		addRotation(calculateMoment(forcedir, force, forcepixel, 
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
		
		for (int i = 0; i < colpoints.length; i++)
		{
			DoublePoint colpoint = colpoints[i];
			// TODO: Try to come up with a way to always get nice numbers here
			double moment = calculateMoment(forcedir, 
					0.3*movementforce + 0.3*rotationforce, colpoint, colpixel);
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
		// Calculates the moment
		// The moment also depends of the largest possible range of the object
		return HelpMath.getDirectionalForce(forcedir, force, tangle) 
				* r / getMaxRangeFromOrigin();
	}
	
	private void addWallFriction(Movement oppmovement, double frictionmodifier)
	{
		double friction = oppmovement.getSpeed() * frictionmodifier;
		// Diminishes the speed that was not affected by the oppposing force
		setMovement(getMovement().getDirectionalllyDiminishedMovement(
				oppmovement.getDirection() + 90, friction));
	}
	
	private int getVolume()
	{
		// For ball-like objects, uses the ball's method for calculating volyme
		if (getCollisionType() == CollisionType.CIRCLE && getHeight() < 0)
		{
			return (int) ((4.0 / 3.0) * Math.PI * Math.pow(getRadius(), 3)); 
		}
		
		// For others, calculates the area of the object first
		int area = 0;
		
		if (getCollisionType() == CollisionType.CIRCLE)
		{
			// PI * r^2 for a circle
			int radius = (int) (getRadius() * (getXscale() + getYscale()) / 2);
			area = (int) (2 * Math.PI * Math.pow(radius, 2));
		}
		else
			area = (int) (getXscale() * getWidth() * getYscale() * getHeight()); 
		// And returns that multiplied with the height
		return area * getZHeight();
	}
	
	private int getMass()
	{
		return getVolume() * getDensity();
	}
}
