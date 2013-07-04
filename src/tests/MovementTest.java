package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;

/**
 * This class tests the baic movement methods in physicobjects
 *
 * @author Gandalf.
 *         Created 4.7.2013.
 */
public class MovementTest extends AbstractTest
{
	// ATTRIBUTES	------------------------------------------------------
	
	private MovementTestBox box;
	
	
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
	public MovementTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);
		
		// Initializes attributes
		this.box = new MovementTestBox(drawer, actorhandler, keylistenerhandler);
		
		// Inactivates the box for now
		this.box.inActivate();
		this.box.setInvisible();
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public void test()
	{
		// Activates the box
		this.box.activate();
		this.box.setVisible();
	}

}
