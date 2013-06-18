package camera;

import java.awt.Point;

import listeners.CameraListener;
import handleds.Drawable;
import handlers.ActorHandler;
import handlers.CameraListenerHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import racekingdoms.HelpMath;
import drawnobjects.DrawnObject2D;
import drawnobjects.PhysicDrawnObject;

/**
 * This object acts as the camera of the game, drawing multiple elements from the 
 * world into a smaller screen
 *
 * @author Gandalf.
 *         Created 16.6.2013.
 */
public class BasicCamera extends PhysicDrawnObject
{
	// ATTRIBUTES	------------------------------------------------------
	
	private CameraDrawer followerhandler;
	private CameraListenerHandler listenerhandler;
	private int screenWidth, screenHeight;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new basic camera added to the given handlers and starting 
	 * from the given position
	 *
	 * @param x The camera's new x-coordinate
	 * @param y The camera's new y-coordinate
	 * @param drawer The drawablehandler that will draw the camera and objects 
	 * it shows
	 * @param actorhandler The actorhandler that informs the camera about 
	 * the act-event
	 * @param screenWidth The width of the screen
	 * @param screenHeight The height of the screen
	 */
	public BasicCamera(int x, int y, DrawableHandler drawer,
			ActorHandler actorhandler, int screenWidth, int screenHeight)
	{
		super(x, y, drawer, actorhandler);
		
		// Initializes attributes
		this.listenerhandler = new CameraListenerHandler(true, null);
		this.followerhandler =  new CameraDrawer(false, this);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		// Informs the listeners about the camera's position
		informStatus();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public double getOriginX()
	{
		return -this.screenWidth / 2;
	}

	@Override
	public double getOriginY()
	{
		return -this.screenHeight / 2;
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		this.followerhandler.drawSelf(applet);
	}
	
	@Override
	public void drawSelf(PApplet applet)
	{
		applet.pushMatrix();
		
		// and translates the origin to the right position
		applet.translate((float) -getOriginX(), (float) -getOriginY());
		// scales it depending on it's xscale and yscale
		applet.scale((float) (getXscale()), (float) (getYscale()));
		// rotates it depending on its angle
		applet.rotate((float) Math.toRadians((360 - getAngle())));
		// Translates the sprite to the object's position
		applet.translate((float) getX(), (float) getY());
		
		// Finally draws the object
		drawSelfBasic(applet);
		
		// Loads the previous transformation
		applet.popMatrix();
	}
	
	@Override
	public int getWidth()
	{
		return this.screenWidth;
	}

	@Override
	public int getHeight()
	{
		return this.screenHeight;
	}
	
	@Override
	public boolean objectCollides(DrawnObject2D d)
	{
		// Negates the transformations for both objects
		Point negatedPosOther =
				negateTransformations((int) d.getX(), (int) d.getY(), (int) -getX(), 
						(int) -getY(), 1 / getXscale(), 1 / getYscale(), (int) -getAngle(), 
						(int) -getOriginX(), (int) -getOriginY());
		Point negatedPosThis =
				d.negateTransformations((int) -getX(), (int) -getY());
		
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
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Adds an drawable to the drawables the camera draws
	 *
	 * @param drawable The object the camera will draw
	 */
	public void addDrawable(Drawable drawable)
	{
		this.followerhandler.addDrawable(drawable);
	}
	
	/**
	 * Adds a new cameralistener to the camera
	 *
	 * @param listener The new cameralistener
	 */
	public void addCameraListener(CameraListener listener)
	{
		this.listenerhandler.addListener(listener);
	}
	
	private void informStatus()
	{
		// Doesn't inform the "real" values but the more easily understandable 
		// ones
		this.listenerhandler.informCameraPosition(
				(int) getX(), (int) getY(), 
				(int) Math.abs(this.screenWidth * getXscale()), 
				(int) Math.abs(this.screenHeight * getYscale()), 
				(int) HelpMath.checkDirection(getAngle()));
	}
}
