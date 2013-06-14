package tests;


import listeners.KeyListener;
import listeners.MouseListener;

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
	private KeyListener keyListener;
	private MouseListener mouselistener;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistener The keylistener that informs created listeners
	 * @param mouselistener The mouselistener that informs created listeners
	 * 
	 */
	public AbstractTest(ActorHandler actorhandler, DrawableHandler drawer, 
			KeyListener keylistener, MouseListener mouselistener)
	{
		// Initializes attributes
		this.actorhandler = actorhandler;
		this.drawer = drawer;
		this.keyListener = keylistener;
		this.mouselistener = mouselistener;
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
	 * @return The keylistener that informs the test's listeners
	 */
	protected KeyListener getKeyListener()
	{
		return this.keyListener;
	}
	
	/**
	 * @return The mouselistener that informs the test's listeners
	 */
	protected MouseListener getMouseListener()
	{
		return this.mouselistener;
	}
}
