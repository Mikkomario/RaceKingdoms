package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import handlers.MouseListenerHandler;

/**
 * Tests the graphical aspects of the program
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class GraphicTest extends AbstractTest
{
	// ATTRIBUTES	-------------------------------------------------------
	
	private TestBox box;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates the new test
	 *
	 * @param actorhandler
	 * @param drawer
	 * @param KeyListenerHandler
	 * @param MouseListenerHandler
	 */
	public GraphicTest(ActorHandler actorhandler, DrawableHandler drawer,
			KeyListenerHandler KeyListenerHandler, MouseListenerHandler MouseListenerHandler)
	{
		super(actorhandler, drawer, KeyListenerHandler, MouseListenerHandler);
		this.box = new TestBox();
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void test()
	{
		getDrawer().addDrawable(this.box);
	}

}
