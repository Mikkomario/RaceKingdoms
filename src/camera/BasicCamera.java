package camera;

import handleds.Drawable;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
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
	// TODO: Create a new class for the followerhandler
	
	
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
	 */
	public BasicCamera(int x, int y, DrawableHandler drawer,
			ActorHandler actorhandler)
	{
		super(x, y, drawer, actorhandler);
		
		// Initializes attributes
		this.followerhandler =  new DrawableHandler(false, null);
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
		// TODO Draw all the objects that the camera should show
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
}
