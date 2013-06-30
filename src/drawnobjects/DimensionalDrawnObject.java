package drawnobjects;

import java.awt.Point;

import handleds.Collidable;
import handlers.CollidableHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.HelpMath;

/**
 * This is a subclass of the drawnobject that can be used in collisionchecking 
 * and in situations that require the object to have dimensions
 *
 * @author Gandalf.
 *         Created 30.6.2013.
 */
public abstract class DimensionalDrawnObject extends DrawnObject implements Collidable
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private boolean solid;
	private CollisionType collisiontype;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new dimensionalobject with the given information
	 *
	 * @param x The x-coordinate of the object's position
	 * @param y The y-coordinate of the object's position
	 * @param isSolid Is the object solid. In other words, can the object be collided with
	 * @param collisiontype What kind of shape the object is collisionwise
	 * @param drawer The drawablehandler that draws the object (optional)
	 * @param collidablehandler The collidablehandler that will handle the object's 
	 * collision checking (optional)
	 */
	public DimensionalDrawnObject(int x, int y, boolean isSolid, 
			CollisionType collisiontype, DrawableHandler drawer, 
			CollidableHandler collidablehandler)
	{
		super(x, y, drawer);
		
		// Initializes attributes
		this.solid = isSolid;
		this.collisiontype = collisiontype;
		
		// Adds the object to the handler, if possible
		if (collidablehandler != null)
			collidablehandler.addCollidable(this);
	}
	
	
	// ABSTRACT METHODS	--------------------------------------------------
	
	/**
	 * @return The width of the object
	 */
	public abstract int getWidth();
	
	/**
	 * @return The height of the object
	 */
	public abstract int getHeight();
	
	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public boolean isSolid()
	{
		return this.solid;
	}
	
	@Override
	public boolean makeSolid()
	{
		this.solid = true;
		return true;
	}
		
	@Override
	public boolean makeUnsolid()
	{
		this.solid = false;
		return true;
	}
	
	@Override
	public Collidable pointCollides(int x, int y)
	{
		// Negates the transformation
		Point negatedPoint = negateTransformations(x, y);
		
		// Returns the object if it collides with the point
		if (HelpMath.pointIsInRange(negatedPoint, 0, 
				getWidth(), 0, getHeight()))
			return this;
		else
			return null;
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return The object's collisiontype
	 */
	protected CollisionType getCollisionType()
	{
		return this.collisiontype;
	}
	
	/**
	 * Changes the object's collision type
	 *
	 * @param newtype The object's new collision type
	 */
	protected void setCollisionType(CollisionType newtype)
	{
		this.collisiontype = newtype;
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Calculates the direction towards which the force caused by the collision 
	 * applies.<p>
	 * 
	 * Boxes push the point away from the nearest side.<br>
	 * Circles push the point away from the origin of the object.<br>
	 * Walls always push the point to their right side
	 * 
	 * @param collisionpoint The point at which the collision happens
	 * @return To which direction the force should apply
	 */
	public double getCollisionForceDirection(Point collisionpoint)
	{
		// Circles simply push the object away
		if (this.collisiontype == CollisionType.CIRCLE)
			return HelpMath.pointDirection(getX(), getY(), 
					collisionpoint.x, collisionpoint.y);
		// Walls simply push the object to the right (relative)
		else if (this.collisiontype == CollisionType.WALL)
			return getAngle();
		// Boxes are the most complicated
		else if (this.collisiontype == CollisionType.BOX)
		{
			//System.out.println("Calculatingbox");
			// Calculates the side which the object touches
			Point relativepoint = negateTransformations(collisionpoint.x, 
					collisionpoint.y);
			double relxdiffer = -0.5 + relativepoint.x / (double) getWidth();
			double relydiffer = -0.5 + relativepoint.y / (double) getHeight();
			//System.out.println(relxdiffer + " / " + relydiffer);
			// Returns drection of one of the sides of the object
			if (Math.abs(relxdiffer) >= Math.abs(relydiffer))
			{
				if (relxdiffer >= 0)
					return getAngle();
				else
					return HelpMath.checkDirection(getAngle() + 180);
			}
			else
			{
				if (relydiffer >= 0)
					return HelpMath.checkDirection(getAngle() + 270);
				else
					return HelpMath.checkDirection(getAngle() + 90);
			}
		}
		
		// In case one of these types wasn't the case, returns 0
		return 0;
	}
	
	/**
	 * @return the longest possible radius of the object (from origin to a corner)
	 */
	public double getMaxRangeFromOrigin()
	{
		// First checks which sides are larger
		double maxXDist = Math.max(getOriginX(), getWidth() - getOriginX());
		double maxYDist = Math.max(getOriginY(), getHeight() - getOriginY());
		
		// Scales the values according to the object's scaling
		maxXDist *= getXscale();
		maxYDist *= getYscale();
		
		// Calculates the length from origin to the corner of those sides
		return HelpMath.pointDistance(0, 0, maxXDist, maxYDist);
	}
}
