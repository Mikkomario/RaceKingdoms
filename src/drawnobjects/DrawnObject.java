package drawnobjects;

import handleds.Drawable;
import handlers.DrawableHandler;
import helpAndEnums.DoublePoint;
import helpAndEnums.HelpMath;
import helpAndEnums.Movement;

import java.awt.Point;

import common.GameObject;

import processing.core.PApplet;

/**
 * An object from this class is can be drawed on screen as an two dimensional object
 *
 * @author Gandalf.
 *         Created 26.11.2012.
 */
public abstract class DrawnObject extends GameObject implements Drawable
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private double xscale, yscale, x, y, angle;
	private boolean visible;
	private int depth;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new drawnobject with the given position. The object is 
	 * solid and active upon creation
	 *
	 * @param x The new x-coordinate of the object (Game world Pxl)
	 * @param y The new y-coordinate of the object (Game world Pxl)
	 * @param depth How 'deep' the object is drawn
	 * @param drawer The handler that draws the object (optional)
	 */
	public DrawnObject(int x, int y, int depth, DrawableHandler drawer)
	{
		// Initializes the attributes
		this.x = x;
		this.y = y;
		this.xscale = 1;
		this.yscale = 1;
		this.visible = true;
		this.angle = 0;
		this.depth = depth;
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
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public boolean isVisible()
	{
		return this.visible;
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
	public int getDepth()
	{
		return this.depth;
	}
	
	@Override
	public boolean setDepth(int depth)
	{
		this.depth = depth;
		return true;
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
	public DoublePoint getPosition()
	{
		return new DoublePoint(this.x, this.y);
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
	 * Changes the object's position according to the given movement
	 *
	 * @param movement The movement the object 'makes'
	 */
	public void addPosition(Movement movement)
	{
		addPosition(movement.getHSpeed(), movement.getVSpeed());
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
		double tempx = px;
		double tempy = py;
		
		// Position Translate (test this)
		tempx -= x;
		tempy -= y;
		
		// Rotation
		if (angle > 0)
		{
			double prevDir = HelpMath.pointDirection(0, 0, tempx, tempy);
			double newDir = HelpMath.checkDirection(prevDir - angle);
			double dist = HelpMath.pointDistance(0, 0, tempx, tempy);
			
			tempx = HelpMath.lendirX(dist, newDir);
			tempy = HelpMath.lendirY(dist, newDir);
		}
		
		// Scaling
		if (xscale != 1 || yscale != 1)
		{
			double xdist = tempx;
			double ydist = tempy;
			double newxdist = xdist*(1/xscale);
			double newydist = ydist*(1/yscale);
			
			tempx -= xdist - newxdist;
			tempy -= ydist - newydist;
		}
		
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
	protected DoublePoint transform(double x, double y)
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
	 * @param angle The angle transformation (0-360)
	 * @param originx The x-coordinate of the origin transformation
	 * @param originy The y-coordinate of the origin transformation
	 * @return Absolute position with transformations added
	 */
	protected DoublePoint transform(double px, double py, double x, double y, 
			double xscale, double yscale, double angle, int originx, int originy)
	{	
		double tempx = px;
		double tempy = py;
		
		// Origin translate
		tempx -= originx;
		tempy -= originy;
		
		// Scaling
		if (xscale != 1 || yscale != 1)
		{
			double xdist = tempx;
			double ydist = tempy;
			double newxdist = xdist*xscale;
			double newydist = ydist*yscale;
			tempx -= xdist - newxdist;
			tempy -= ydist - newydist;
		}
		
		// Rotation
		if (angle > 0)
		{
			double prevDir = HelpMath.pointDirection(0, 0, tempx, tempy);
			double newDir = HelpMath.checkDirection(prevDir + angle);
			double dist = HelpMath.pointDistance(0, 0, tempx, tempy);
			tempx = HelpMath.lendirX(dist, newDir);
			tempy = HelpMath.lendirY(dist, newDir);
		}
		
		// Position Translate
		tempx += x;
		tempy += y;
		
		return new DoublePoint(tempx, tempy);
	}
	
	/**
	 * Rotates the object around a certain (absolute) position
	 *
	 * @param angle The amount of degrees the object rotates
	 * @param p The point around which the object rotates
	 */
	public void rotateAroundPoint(double angle, DoublePoint p)
	{
		// Moves the object around the point
		DoublePoint newposition = 
				HelpMath.getRotatedPosition(p.getX(), p.getY(), getPosition(), angle);
		setPosition(newposition.getX(), newposition.getY());
		// Also rotates the object
		addAngle(angle);
	}
	
	/**
	 * Rotates the object around a relative point. 
	 * A bit heavier than the rotatearoundpoint method
	 *
	 * @param angle The amount of degrees the object is rotated
	 * @param p The relative point around which the object is rotated
	 */
	public void rotateAroundRelativePoint(double angle, Point p)
	{
		DoublePoint abspoint = transform(p.x, p.y);
		rotateAroundPoint(angle, abspoint);
	}
}