package tests;

import java.util.ArrayList;

import handlers.ActorHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;

/**
 * Interactive collision test tests the bounceInteractivelyFrom -method in 
 * the advancedPhysicDrawnObject class
 *
 * @author Gandalf.
 *         Created 8.7.2013.
 */
public class InteractiveCollisionTest extends AbstractTest
{
	// ATTRIBUTES	------------------------------------------------------
	
	private ArrayList<WandererTestBox> boxes;
	private CollisionHandler colhandler;
	
	
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
	public InteractiveCollisionTest(ActorHandler actorhandler,
			DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);
		
		// Initializes attributes
		this.colhandler = new CollisionHandler(true, actorhandler);
		this.colhandler.inActivate();
		this.boxes = new ArrayList<WandererTestBox>();
		
		// Creates a few boxes
		for (int i = 0; i < 2; i++)
		{
			this.boxes.add(new WandererTestBox(applet.width, applet.height, 
					drawer, this.colhandler.getCollidableHandler(), 
					this.colhandler, actorhandler));
		}
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void test()
	{
		// Activates the collisionhandler
		this.colhandler.activate();
	}
}
