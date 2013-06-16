package tests;

import processing.core.PApplet;
import handleds.Actor;
import handleds.Drawable;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class FpsApsTest extends AbstractTest implements Actor, Drawable
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private boolean active;
	private boolean visible;
	private boolean alive;
	private long lastmillis;
	private int fps, fpsalt;
	private int aps;
	private int frames;
	private int actions;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public FpsApsTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);

		// Initializes the attributes
		this.active = false;
		this.visible = false;
		this.alive = true;
		this.lastmillis = System.currentTimeMillis();
		this.fps = 0;
		this.aps = 0;
		this.frames = 0;
		this.actions = 0;
		this.fpsalt = 0;
		
		// Adds the object to the handlers
		actorhandler.addActor(this);
		drawer.addDrawable(this);
		
	
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void test()
	{
		setVisible();
		activate();
	}

	@Override
	public boolean isActive()
	{
		return this.active;
	}

	@Override
	public boolean activate()
	{
		this.active = true;
		return true;
	}

	@Override
	public boolean inActivate()
	{
		this.active = false;
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
		this.alive = false;
		return true;
	}

	@Override
	public void drawSelf(PApplet applet)
	{
		// Calculates the fps
		this.frames ++;
		
		// Draws the current fps and aps
		applet.fill(0);
		applet.text("FPS: " + this.fps + " (" + this.fpsalt + ")", 100, 100);
		applet.text("APS: " + this.aps, 100, 130);
	}

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
	public void act()
	{
		// Calculates the aps and fps
		this.actions ++;
		
		if (System.currentTimeMillis() - this.lastmillis > 1000)
		{
			this.aps = this.actions;
			this.fps = this.frames;
			this.fpsalt = (int) getApplet().frameRate;
			this.actions = 0;
			this.frames = 0;
			this.lastmillis = System.currentTimeMillis();
		}
	}

}
