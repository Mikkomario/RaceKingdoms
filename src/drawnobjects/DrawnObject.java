package drawnobjects;

import handleds.Collidable;
import handleds.Drawable;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.HelpMath;

import java.awt.Point;

import listeners.CollisionListener;

import processing.core.PApplet;

/**
 * An object from this class is can be drawed on screen as an two dimensional image
 *
 * @author Gandalf.
 *         Created 26.11.2012.
 */
public abstract class DrawnObject implements Drawable, Collidable, CollisionListener
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private double xscale, yscale, x, y, angle;
	private boolean visible, alive, solid;
	private Point[] relativecollisionpoints;
	private boolean active;
	private CollisionType collisiontype;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new drawnobject with the given position. The object is 
	 * solid and active upon creation
	 *
	 * @param x The new x-coordinate of the object (Game world Pxl)
	 * @param y The new y-coordinate of the object (Game world Pxl)
	 * @param drawer The handler that draws the object (optional)
	 */
	public DrawnObject(int x, int y, DrawableHandler drawer)
	{
		// Initializes the attributes
		this.collisiontype = CollisionType.BOX;
		this.x = x;
		this.y = y;
		this.xscale = 1;
		this.yscale = 1;
		this.visible = true;
		this.alive = true;
		this.angle = 0;
		this.solid = true;
		this.active = true;
		this.relativecollisionpoints = null;
		//initializeCollisionPoints(1, 1);
		
		// Adds the object to the drawer (if possible)
		if (drawer != null)
			drawer.addDrawable(this);
	}
	
	
	// ABSTRACT METHODS	---------------------------------------------------
	
	/**
	 * @return The Object's origin's x-translation from the left
	 */
	public abstract int getOriginX();
	
	/**
	 * @return The Object's origin's y-translation from the top
	 */
	public abstract int getOriginY();
	
	/**
	 * In this method, the object should draw itself as without any concerns 
	 * about the position, transformation or origin position.
	 *
	 * @param applet the applet with which the object is drawn
	 */
	public abstract void drawSelfBasic(PApplet applet);
	
	/**
	 * @return The width of the object
	 */
	public abstract int getWidth();
	
	/**
	 * @return The height of the object
	 */
	public abstract int getHeight();
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

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
	public boolean isDead()
	{
		return !this.alive;
	}

	@Override
	public boolean kill()
	{
		// Ends the drawing and also kills the object
		this.alive = false;
		return true;
	}

	@Override
	public boolean setVisible()
	{
		this.visible = true;
		return true;
	}

	@Override
	public boolean setInvisible()
	{
		this.visible = false;
		return true;
	}
	
	@Override
	public void drawSelf(PApplet applet)
	{
		applet.pushMatrix();
		
		// Translates the sprite to the object's position
		applet.translate((float) getX(), (float) getY());
		// rotates it depending on its angle
		applet.rotate((float) Math.toRadians((360 - getAngle())));
		// scales it depending on it's xscale and yscale
		applet.scale((float) getXscale(), (float) getYscale());
		// and translates the origin to the right position
		applet.translate(-getOriginX(), -getOriginY());
		
		// Finally draws the object
		drawSelfBasic(applet);
		
		// Loads the previous transformation
		applet.popMatrix();
	}
	
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
	
	@Override
	public Point[] getCollisionPoints()
	{
		Point[] relativepoints = getRelativeCollisionPoints();
		
		// if relativepoints don't exist, returns an empty table
		if (relativepoints == null)
			return new Point[0];
		
		Point[] newpoints = new Point[relativepoints.length];
		
		// Transforms each of the points and adds them to the new table
		for (int i = 0; i < relativepoints.length; i++)
		{
			newpoints[i] = transform(relativepoints[i].x, relativepoints[i].y);
		}
		
		return newpoints;
	}
	
	
	// GETTERS & SETTERS	-----------------------------------------------
	
	/**
	 * @return The object's rotation around the z-axis in degrees [0, 360[
	 */
	public double getAngle()
	{
		return HelpMath.checkDirection(this.angle);
	}
	
	/**
	 * 
	 * Changes how much the object is rotated before drawing
	 *
	 * @param angle The angle of the drawn sprite in degrees around the z-axis [0, 360[
	 */
	public void setAngle(double angle)
	{
		this.angle = angle;
		checkAngle();
	}
	
	/**
	 * 
	 * Increases the object's angle by the given amount
	 *
	 * @param rotation How much the angle around the z-axis is increased (degrees)
	 */
	public void addAngle(double rotation)
	{
		setAngle(getAngle() + rotation);
	}
	
	/**
	 * @return How much the sprite is scaled horizontally (from the original 
	 * angle) (default at 1)
	 */
	public double getXscale()
	{
		return this.xscale;
	}
	
	/**
	 * @return How much the sprite is scaled vertically (from the original 
	 * angle) (default at 1)
	 */
	public double getYscale()
	{
		return this.yscale;
	}
	
	/**
	 * Changes how much the sprite is scaled horizontally and vertically
	 * (from the original angle)
	 *
	 * @param xscale The new horizontal scale of the sprite (default at 1)
	 * @param yscale The new vertical scale of the sprite (default at 1)
	 */
	public void setScale(double xscale, double yscale)
	{
		this.xscale = xscale;
		this.yscale = yscale;
	}
	
	/**
	 * @return X-coordinate of the objects position in the game world (pxl)
	 */
	public double getX()
	{
		return this.x;
	}
	
	/**
	 * @return Y-coordinate of the objects position in the game world (pxl)
	 */
	public double getY()
	{
		return this.y;
	}
	
	/**
	 * @return The position of the object in a point format
	 */
	public Point getPosition()
	{
		return new Point((int) this.x, (int) this.y);
	}
	
	/**
	 * 
	 * Changes the object's position in the game world
	 *
	 * @param x The new position's x-coordinate (pxl)
	 * @param y The new position's y-coordinate (pxl)
	 */
	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Changes the object's position by the given amount
	 *
	 * @param hspeed How much the object is moved horizontally
	 * @param vspeed How much the object is move vertically
	 */
	public void addPosition(double hspeed, double vspeed)
	{
		setPosition(getX() + hspeed, getY() + vspeed);
	}
	
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
	
	
	// OTHER METHODS	---------------------------------------------------
	
	// Restores the angle to between 0 and 360
	private void checkAngle()
	{
		this.angle = HelpMath.checkDirection(this.angle);
	}
	
	/**
	 * Scales the object with the given factors. The scaling stacks with previous 
	 * scaling and is not necessarily dependent on the original size of the object
	 *
	 * @param xscale How much the object is scaled horizontally
	 * @param yscale How much the object is scaled vertically
	 */
	public void scale(double xscale, double yscale)
	{
		setScale(getXscale() * xscale, getYscale() * yscale);
	}
	
	/*
	public boolean objectCollides(Collidable c)
	{
		DrawnObject2D d = null;
		
		// Only works with drawnobjects currently
		if (c instanceof DrawnObject2D)
			d = (DrawnObject2D) c;
		else
			return false;
		
		// Negates the transformations for both objects
		Point negatedPosOther =
				negateTransformations((int) d.getX(), (int) d.getY());
		Point negatedPosThis =
				d.negateTransformations((int) getX(), (int) getY());
		
		int widthThis = getWidth();
		int widthOther = d.getWidth();
		int heightThis = getHeight();
		int heightOther = d.getHeight();
		
		//System.out.println(negatedPosThis + "; " + negatedPosOther);
		
		if (negatedPosOther.x + widthOther < negatedPosThis.x)
			return false;
		if (negatedPosOther.x > negatedPosThis.x + widthThis)
			return false;
		if (negatedPosOther.y + heightOther < negatedPosThis.y)
			return false;
		if (negatedPosOther.y > negatedPosThis.y + heightThis)
			return false;
		
		return true;
	}
	*/
	
	/**
	 * Transforms the point so that the collision can be checked without
	// transformations.
	 *
	 * @param x The x-coordinate of the point to be negated
	 * @param y The y-coordinate of the point to be negated
	 * @return The point where all of the object's transformations are negated
	 */
	public Point negateTransformations(double x, double y)
	{
		return negateTransformations(x, y, getX(), getY(), getXscale(), 
				getYscale(), getAngle(), getOriginX(), getOriginY());
	}
	
	/**
	 * Transforms the point so that the collision can be checked without
	// transformations. Uses specific transformations.
	 * @param x The x-coordinate in the transformed position
	 * @param y The y-coordinate in the transformed position
	 * @param px The x-coordinate of the point to be negated
	 * @param py The y-coordinate of the point to be negated
	 * @param xscale The x-scale in the transformation
	 * @param yscale The y-scale in the transformation
	 * @param angle The angle in the transformation (0-359)
	 * @param originx The x-coordinate of the transformatio's origin
	 * @param originy The y-coordinate of the transformatio's origin
	 * @return The point where all of the object's transformations are negated
	 */
	protected static Point negateTransformations(double px, double py, double x, 
			double y, double xscale, double yscale, double angle, int originx, 
			int originy)
	{
		// TODO: Skip some of the phases if there's nothing to transform
		double tempx = px;
		double tempy = py;
		
		// Position Translate (test this)
		tempx -= x;
		tempy -= y;
		
		// Rotation
		double prevDir = HelpMath.pointDirection(0, 0, tempx, tempy);
		//System.out.println(prevDir);
		double newDir = HelpMath.checkDirection(prevDir - angle);
		//System.out.println(newDir);
		double dist = HelpMath.pointDistance(0, 0, tempx, tempy);
		//System.out.println(dist);
		tempx = HelpMath.lendirX(dist, newDir);
		tempy = HelpMath.lendirY(dist, newDir);
		//System.out.println(tempx);
		//System.out.println(tempy);
		
		// Scaling
		double xdist = tempx;
		double ydist = tempy;
		double newxdist = xdist*(1/xscale);
		double newydist = ydist*(1/yscale);
		tempx -= xdist - newxdist;
		tempy -= ydist - newydist;
		
		// Origin translate
		tempx += originx;
		tempy += originy;
		
		return new Point((int) tempx, (int) tempy);
	}
	
	/**
	 * Transforms the position depending on the object's current transformation
	 *
	 * @param x Position's x-coordinate relative to the object's origin
	 * @param y Position's y-coordinate relative to the object's origin
	 * @return Absolute position with transformations added
	 */
	protected Point transform(double x, double y)
	{	
		return transform(x, y, getX(), getY(), getXscale(), getYscale(), 
				getAngle(), getOriginX(), getOriginY());
	}
	
	/**
	 * Transforms the position depending on the object's current transformation
	 *
	 * @param px Position's x-coordinate relative to the object's origin
	 * @param py Position's y-coordinate relative to the object's origin
	 * @param x The x-coordinate of the position transformation
	 * @param y The y-coordinate of the position transformation
	 * @param xscale The xscale transformation
	 * @param yscale The yscale transformation
	 * @param angle The angle transformation
	 * @param originx The x-coordinate of the origin transformation
	 * @param originy The y-coordinate of the origin transformation
	 * @return Absolute position with transformations added
	 */
	protected Point transform(double px, double py, double x, double y, 
			double xscale, double yscale, double angle, int originx, int originy)
	{	
		double tempx = px;
		double tempy = py;
		
		// Origin translate
		tempx -= originx;
		tempy -= originy;
		
		// Scaling
		double xdist = tempx;
		double ydist = tempy;
		double newxdist = xdist*xscale;
		double newydist = ydist*yscale;
		tempx -= xdist - newxdist;
		tempy -= ydist - newydist;
		
		// Rotation
		double prevDir = HelpMath.pointDirection(0, 0, tempx, tempy);
		//System.out.println(prevDir);
		double newDir = HelpMath.checkDirection(prevDir + angle);
		//System.out.println(newDir);
		double dist = HelpMath.pointDistance(0, 0, tempx, tempy);
		tempx = HelpMath.lendirX(dist, newDir);
		tempy = HelpMath.lendirY(dist, newDir);
		
		// Position Translate
		tempx += x;
		tempy += y;
		
		return new Point((int) tempx, (int) tempy);
	}
	
	/**
	 * Rotates the object around a certain (absolute) position
	 *
	 * @param angle The amount of degrees the object rotates
	 * @param p The point around which the object rotates
	 */
	public void rotateAroundPoint(double angle, Point p)
	{
		/*
		 * This was the first version of the method but it had small problems 
		 * keeping the distance the same
		//double prevdist = HelpMath.pointDistance(getX(), getY(), p.x, p.y);
		// Calculates the starting value
		Point relativebefore = negateTransformations(p.x, p.y);
		// Rotates the car
		addAngle(angle);
		// Calculates the diference between the start and the end
		Point absoluteafter = transform(relativebefore.x, relativebefore.y);
		// Moves the car back into the right position
		addPosition(p.x - absoluteafter.x, p.y - absoluteafter.y);
		//double newdist = HelpMath.pointDistance(getX(), getY(), absoluteafter.x, absoluteafter.y);
		//System.out.println(prevdist - newdist);
		//Point relativeafter = negateTransformations(p.x, p.y);
		//System.out.println(relativebefore + " -> " + relativeafter);
		 * 
		 */
		
		// Calculates the old and the new directions (from the point to the object)
		double prevdir = HelpMath.pointDirection(p.x, p.y, getX(), getY());
		double newdir = HelpMath.checkDirection(prevdir + angle);
		// Also calculates the distance between the object and the point 
		// (which stays the same during the process)
		double dist = HelpMath.pointDistance(p.x, p.y, getX(), getY());
		// Moves the object around the point to the new position
		setPosition(p.x + HelpMath.lendirX(dist, newdir), p.y + 
				HelpMath.lendirY(dist, newdir));
		// Also rotates the object
		addAngle(angle);
	}
	
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