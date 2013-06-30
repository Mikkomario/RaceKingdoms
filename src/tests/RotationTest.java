package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;

/**
 * This class tests the rotateAroundPoint method in drawnobject
 *
 * @author Gandalf.
 *         Created 30.6.2013.
 */
public class RotationTest extends AbstractTest
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private RotationTestBox rotbox;
	private TestBox refbox;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a rotationtestbox and a testbox for reference
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public RotationTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);

		// Initializes attributes
		this.refbox = new TestBox(drawer, null);
		this.rotbox = new RotationTestBox(drawer, keylistenerhandler);
		
		// Sets the objects invisible
		this.refbox.setInvisible();
		this.rotbox.setInvisible();
		this.rotbox.inActivate();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void test()
	{
		// Activates the objects
		this.refbox.setVisible();
		this.rotbox.setVisible();
		this.rotbox.activate();
	}
	
}
