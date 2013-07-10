package tests;

import backgrounds.Background;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;

/**
 * Tests drawing of the background
 *
 * @author Gandalf.
 *         Created 10.7.2013.
 */
public class BackgroundTest extends AbstractTest
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private Background testbackground;
	
	
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
	public BackgroundTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);

		// Initializes attributes
		TestSpriteBank testbank = new TestSpriteBank(applet);
		this.testbackground = new Background(500, 300, drawer, actorhandler, 
				testbank, "background");
		this.testbackground.setInvisible();
		//actorhandler.addActor(this.testbackground.getSpriteDrawer());
		this.testbackground.getSpriteDrawer().inActivate();
		this.testbackground.setDimensions(300, 300);
	}

	
	// IMPLEMENTED METHOD	---------------------------------------------
	
	@Override
	public void test()
	{
		this.testbackground.setVisible();
		this.testbackground.getSpriteDrawer().activate();
	}
}
