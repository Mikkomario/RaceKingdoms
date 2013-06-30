package drawnobjects;

import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DoublePoint;

import java.awt.Point;

import listeners.CollisionListener;

/**
 * Collidingdrawnobject is a subclass of the drawnobject that can collide with 
 * other objects and may react to collisions
 *
 * @author Gandalf.
 *         Created 30.6.2013.
 */
public abstract class CollidingDrawnObject extends DimensionalDrawnObject 
		implements CollisionListener
{
	// ATTRIBUTES	------------------------------------------------------
	
	private Point[] relativecollisionpoints;
	private boolean active;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new collidingdrawnobject with the given information
	 *
	 * @param x The object's position's x-coordinate
	 * @param y The object's position's y-coordinate
	 * @param isSolid Can the object be collided with
	 * @param collisiontype What shape the object is collisionwise
	 * @param drawer Which drawablehandler will draw the object (optional)
	 * @param collidablehandler The collidablehandler that will handle the object's 
	 * collision checking (optional)
	 * @param collisionhandler The collisionhandler that will handle the object's 
	 * collision informing (optional)
	 */
	public CollidingDrawnObject(int x, int y, boolean isSolid,
			CollisionType collisiontype, DrawableHandler drawer, 
			CollidableHandler collidablehandler, CollisionHandler collisionhandler)
	{
		super(x, y, isSolid, collisiontype, drawer, collidablehandler);
		
		// Initializes attributes
		this.active = true;
		this.relativecollisionpoints = null;

		// Adds the object to the handler
		if (collisionhandler != null)
			collisionhandler.addCollisionListener(this);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	public boolean isActive()
	{
		return this.active;
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
	
	@Override
	public DoublePoint[] getCollisionPoints()
	{
		Point[] relativepoints = getRelativeCollisionPoints();
		
		// if relativepoints don't exist, returns an empty table
		if (relativepoints == null)
			return new DoublePoint[0];
		
		DoublePoint[] newpoints = new DoublePoint[relativepoints.length];
		
		// Transforms each of the points and adds them to the new table
		for (int i = 0; i < relativepoints.length; i++)
		{
			newpoints[i] = transform(relativepoints[i].x, relativepoints[i].y);
		}
		
		return newpoints;
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return The relative collision coordinates from which the collisions 
	 * are checked
	 */
	protected Point[] getRelativeCollisionPoints()
	{
		// If the collisionpoints have not yet been initialized, initializes them
		if (this.relativecollisionpoints == null)
			initializeCollisionPoints(1, 1);
		
		return this.relativecollisionpoints;
	}
	
	/**
	 * Changes the object's list of collisionpoints
	 *
	 * @param collisionpoints The new set of relative collisionpoints. Use 
	 * null if you wan't no collision points.
	 */
	protected void setRelativeCollisionPoints(Point[] collisionpoints)
	{
		if (collisionpoints != null)
			this.relativecollisionpoints = collisionpoints;
		else
			this.relativecollisionpoints = new Point[0];
	}
	
	/**
	 * Changes how precisely the object checks collisions. More precision means 
	 * slower checking and more precise results. Large and scaled objects should 
	 * have higher precisions than small objects.
	 *
	 * @param edgeprecision How precise is the collision checking on the edges 
	 * of the object? 0 means no collision checking on edges, 1 means only corners 
	 * and 2+ adds more (4*edgeprecision) collisionpoints to the edges.
	 * @param insideprecision How precise is the collision checking inside the 
	 * object? 0 means no collision checking inside the object, 1 means only 
	 * the center of the object is checked and 2+ means alot more 
	 * (insideprecision^2) collisionpoints inside the object.
	 */
	protected void setCollisionPrecision(int edgeprecision, int insideprecision)
	{
		// Doesn't work with negative values
		if (edgeprecision < 0 || insideprecision < 0)
			return;
		
		initializeCollisionPoints(edgeprecision, insideprecision);
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	private void initializeCollisionPoints(int edgeprecision, int insideprecision)
	{
		// edgeprecision 0 -> no sides or corners
		// insideprecision 0 -> no inside points
		
		// Calculates the number of collisionpoints
		int size = edgeprecision*4 + (int) Math.pow(insideprecision, 2);
		this.relativecollisionpoints = new Point[size];
		
		int index = 0;
		
		if (edgeprecision > 0)
		{
			// Goes through the edgepoints and adds them to the table
			for (int ex = 0; ex < edgeprecision + 1; ex++)
			{
				for (int ey = 0; ey < edgeprecision + 1; ey++)
				{
					// Only adds edges
					if (ex != 0 && ex != edgeprecision && ey != 0 && ey != edgeprecision)
						continue;
					
					// Adds a point to the table
					this.relativecollisionpoints[index] = new Point(
							(int) (ex / (double) edgeprecision *getWidth()), 
							(int) (ey / (double) edgeprecision *getHeight()));
					
					index++;
				}
			}
		}
		if (insideprecision > 0)
		{
			// Goes through the insidepoints and adds them to the table
			for (int ix = 1; ix < insideprecision + 1; ix++)
			{
				for (int iy = 1; iy < insideprecision + 1; iy++)
				{	
					// Adds a point to the table
					this.relativecollisionpoints[index] = new Point(
							(int) (ix / (double) (insideprecision + 1) *getWidth()), 
							(int) (iy / (double) (insideprecision + 1) *getHeight()));
					
					index++;
				}
			}
		}
	}
}
