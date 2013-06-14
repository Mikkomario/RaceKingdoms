package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import listeners.KeyListener;
import listeners.MouseListener;

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
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistener The keylistener that informs created listeners
	 * @param mouselistener The mouselistener that informs created listeners
	 */
	public GraphicTest(ActorHandler actorhandler, DrawableHandler drawer,
			KeyListener keylistener, MouseListener mouselistener)
	{
		super(actorhandler, drawer, keylistener, mouselistener);
		
		this.box = new TestBox();
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void test()
	{
		getDrawer().addDrawable(this.box);
	}

}
