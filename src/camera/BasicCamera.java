package camera;

import listeners.CameraListener;
import handleds.Drawable;
import handlers.ActorHandler;
import handlers.CameraListenerHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import racekingdoms.HelpMath;
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
	
	private DrawableHandler followerhandler;
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
		this.followerhandler =  new DrawableHandler(false, null);
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		
		// Informs the listeners about the camera's position
		informStatus();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public double getOriginX()
	{
		return 0;
	}

	@Override
	public double getOriginY()
	{
		return 0;
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		this.followerhandler.drawSelf(applet);
	}
	
	@Override
	public boolean kill()
	{
		// Kills the handler as well
		return (this.followerhandler.kill() && super.kill());
	}
	
	@Override
	public boolean setVisible()
	{
		// Visibility also affects the handler
		return (this.followerhandler.setVisible() && super.setVisible());
	}

	@Override
	public boolean setInvisible()
	{
		// Visibility also affects the handler
		return (this.followerhandler.setInvisible() && super.setInvisible());
	}
	
	/*
	@Override
	public void setPosition(double x, double y)
	{
		// Actually sets the position to the very opposite
		// (X increases -> X of each of the drawn objects decreases!)
		super.setPosition(-x, -y);
		
		// Also informs the listeners about the change
		informStatus();
	}
	
	@Override
	public void setAngle(double angle)
	{
		// The objects are, once again, rotated into opposite direction as 
		// the camera
		super.setAngle(-angle);
		
		informStatus();
	}
	
	@Override
	public void setScale(double xscale, double yscale)
	{
		// Scaling is done in reverse as well (when camera becomes smaller, 
		// objects become larger)
		super.setScale(1 / xscale, 1 / yscale);
		
		// And informs the listeners
		informStatus();
	}
	
	@Override
	public void addPosition(double hspeed, double vspeed)
	{
		// Speed is considered the opposite
		super.addPosition(-hspeed, -vspeed);
	}
	
	@Override
	public void addAngle(double rotation)
	{
		// Rotation is added as negated
		super.addAngle(-rotation);
	}
	
	*/
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
				(int) -getX(), (int) -getY(), 
				(int) Math.abs(this.screenWidth * getXscale()), 
				(int) Math.abs(this.screenHeight * getYscale()), 
				(int) HelpMath.checkDirection(-getAngle()));
	}
}
