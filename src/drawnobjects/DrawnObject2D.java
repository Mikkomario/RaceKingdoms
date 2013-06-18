package drawnobjects;

import handleds.Collidable;
import handleds.Drawable;
import handlers.DrawableHandler;

import java.awt.Point;

import processing.core.PApplet;
import racekingdoms.HelpMath;

/**
 * An object from this class is can be drawed on screen as an two dimensional image
 *
 * @author Gandalf.
 *         Created 26.11.2012.
 */
public abstract class DrawnObject2D implements Drawable, Collidable
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private double xscale, yscale, x, y, angle;
	private boolean visible, alive, solid;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * 
	 * Creates a new drawnobject with the given position
	 *
	 * @param x The new x-coordinate of the object (Game world Pxl)
	 * @param y The new y-coordinate of the object (Game world Pxl)
	 * @param drawer The handler that draws the object (optional)
	 */
	public DrawnObject2D(int x, int y, DrawableHandler drawer)
	{
		// Initializes the attributes
		this.x = x;
		this.y = y;
		this.xscale = 1;
		this.yscale = 1;
		this.visible = true;
		this.alive = true;
		this.angle = 0;
		this.solid = true;
		
		// Adds the object to the drawer (if possible)
		if (drawer != null)
			drawer.addDrawable(this);
	}
	
	
	// ABSTRACT METHODS	---------------------------------------------------
	
	/**
	 * @return The Object's origin's x-translation from the left
	 */
	public abstract double getOriginX();
	
	/**
	 * @return The Object's origin's y-translation from the top
	 */
	public abstract double getOriginY();
	
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
		applet.translate((float) -getOriginX(), (float) -getOriginY());
		
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
	
	/**
	 * Checks whether the object collides with the given point
	 *
	 * @param x The x-coordinate of the point (absolute)
	 * @param y The y-coordinate of the point (absolute)
	 * @return Does the point collide with the object
	 */
	public boolean pointCollides(int x, int y)
	{
		// Negates the transformation
		Point negatedPoint = negateTransformations(x, y);
		
		return HelpMath.pointIsInRange(negatedPoint, 0, 
				getWidth(), 0, getHeight());
	}
	
	/**
	 * Checks whether two objects collide
	 *
	 * @param d The object to be collided with
	 * @return Do the objects collide
	 */
	@Override
	public boolean objectCollides(DrawnObject2D d)
	{
		// Negates the transformations for both objects
		Point negatedPosOther =
				negateTransformations((int) d.getX(), (int) d.getY());
		Point negatedPosThis =
				d.negateTransformations((int) getX(), (int) getY());
		
		int widthThis = getWidth();
		int widthOther = d.getWidth();
		int heightThis = getHeight();
		int heightOther = d.getHeight();
		
		if (negatedPosOther.x + widthOther < negatedPosThis.x)
			return false;
		else if (negatedPosOther.x > negatedPosThis.x + widthThis)
			return false;
		else if (negatedPosOther.y + heightOther < negatedPosThis.y)
			return false;
		else if (negatedPosOther.y > negatedPosThis.y + heightThis)
			return false;
		else
			return true;
	}
	
	/**
	 * Transforms the point so that the collision can be checked without
	// transformations.
	 *
	 * @param x The x-coordinate of the point to be negated
	 * @param y The y-coordinate of the point to be negated
	 * @return The point where all of the object's transformations are negated
	 */
	public Point negateTransformations(int x, int y)
	{
		/*
		double tempx = x;
		double tempy = y;
		
		// Rotation
		int prevDir = HelpMath.pointDirection((int) getX(), (int) getY(), x, y);
		int newDir = prevDir - (int) getAngle();
		int dist = HelpMath.pointDistance((int) getX(), (int) getY(), x, y);
		tempx = getX() + HelpMath.lendirX(dist, newDir);
		tempy = getY() + HelpMath.lendirY(dist, newDir);
		// Scaling
		double xdist = tempx - getX();
		double ydist = tempy - getY();
		double newxdist = xdist*(1/getXscale());
		double newydist = ydist*(1/getYscale());
		tempx -= xdist - newxdist;
		tempy -= ydist - newydist;
		// Origin translate
		tempx += getOriginX();
		tempy += getOriginY();
		
		return new Point((int) tempx, (int) tempy);
		*/
		return negateTransformations(x, y, (int) getX(), (int) getY(), 
				getXscale(), getYscale(), (int) getAngle(), (int) getOriginX(), 
				(int) getOriginY());
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
	protected static Point negateTransformations(int px, int py, int x, int y, 
			double xscale, double yscale, int angle, int originx, int originy)
	{
		double tempx = px;
		double tempy = py;
		
		// Rotation
		int prevDir = HelpMath.pointDirection(x, y, px, py);
		int newDir = prevDir - angle;
		int dist = HelpMath.pointDistance(x, y, px, py);
		tempx = x + HelpMath.lendirX(dist, newDir);
		tempy = y + HelpMath.lendirY(dist, newDir);
		// Scaling
		double xdist = tempx - x;
		double ydist = tempy - y;
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
	protected Point transform(int x, int y)
	{	
		/*
		double tempx = x;
		double tempy = y;
		
		// Rotates and translates position
		int prevDir = HelpMath.pointDirection(0, 0, x, y);
		int newDir = prevDir + (int) getAngle();
		int dist = x + y;
		tempx = getX() + HelpMath.lendirX(dist, newDir);
		tempy = getY() + HelpMath.lendirY(dist, newDir);
		
		// Scales
		double xdist = tempx - getX();
		double ydist = tempy - getY();
		double newxdist = xdist*getXscale();
		double newydist = ydist*getYscale();
		tempx -= xdist - newxdist;
		tempy -= ydist - newydist;
		
		// Origin translate
		tempx -= getOriginX();
		tempy -= getOriginY();
		
		return new Point((int) tempx, (int) tempy);
		*/
		return transform(x, y, (int) getX(), (int) getY(), getXscale(), 
				getYscale(), (int) getAngle(), (int) getOriginX(), 
				(int) getOriginY());
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
	protected static Point transform(int px, int py, int x, int y, double xscale, 
			double yscale, int angle, int originx, int originy)
	{	
		double tempx = px;
		double tempy = py;
		
		// Rotates and translates position
		int prevDir = HelpMath.pointDirection(0, 0, px, py);
		int newDir = prevDir + angle;
		int dist = px + py;
		tempx = x + HelpMath.lendirX(dist, newDir);
		tempy = y + HelpMath.lendirY(dist, newDir);
		
		// Scales
		double xdist = tempx - x;
		double ydist = tempy - y;
		double newxdist = xdist*xscale;
		double newydist = ydist*yscale;
		tempx -= xdist - newxdist;
		tempy -= ydist - newydist;
		
		// Origin translate
		tempx -= originx;
		tempy -= originy;
		
		return new Point((int) tempx, (int) tempy);
	}
}