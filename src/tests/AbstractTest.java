package tests;


import processing.core.PApplet;
import handlers.KeyListenerHandler;
import handlers.MouseListenerHandler;

import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * This class provides necessary tools for testing aspects of the game
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public abstract class AbstractTest
{
	// ATTRIBUTES	------------------------------------------------------
	
	private ActorHandler actorhandler;
	private DrawableHandler drawer;
	private KeyListenerHandler KeyListenerHandler;
	private MouseListenerHandler MouseListenerHandler;
	private PApplet applet;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public AbstractTest(ActorHandler actorhandler, DrawableHandler drawer, 
			KeyListenerHandler keylistenerhandler, 
			MouseListenerHandler mouselistenerhandler, 
			PApplet applet)
	{
		// Initializes attributes
		this.actorhandler = actorhandler;
		this.drawer = drawer;
		this.KeyListenerHandler = keylistenerhandler;
		this.MouseListenerHandler = mouselistenerhandler;
		this.applet = applet;
	}
	
	
	// ABSTRACT METHODS	---------------------------------------------------
	
	/**
	 * Initializes (and runs) the test
	 */
	public abstract void test();
	
	
	// OTHER METHODS	---------------------------------------------------
	
	/**
	 * @return The handler that handles the test's actors
	 */
	protected ActorHandler getActorHandler()
	{
		return this.actorhandler;
	}
	
	/**
	 * @return The drawer that draws the test's drawables
	 */
	protected DrawableHandler getDrawer()
	{
		return this.drawer;
	}
	
	/**
	 * @return The KeyListenerHandler that informs the test's listeners
	 */
	protected KeyListenerHandler getKeyListenerHandler()
	{
		return this.KeyListenerHandler;
	}
	
	/**
	 * @return The MouseListenerHandler that informs the test's listeners
	 */
	protected MouseListenerHandler getMouseListenerHandler()
	{
		return this.MouseListenerHandler;
	}
	
	/**
	 * @return The applet running the tests
	 */
	protected PApplet getApplet()
	{
		return this.applet;
	}
}
