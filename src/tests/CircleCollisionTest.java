package tests;

import handlers.ActorHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import racing.Car;
import racing.CarSpriteBank;

/**
 * Circlecollisiontest tests collision between a car and a circular object
 *
 * @author Gandalf.
 *         Created 7.7.2013.
 */
public class CircleCollisionTest extends AbstractTest
{

	//ATTRIBUTES	------------------------------------------------------
	
	private Car testcar;
	private TestEllipse testcircle;
	private CollisionHandler colhandler;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	
	/**Constructs a testcar and testbox to see if they can collide.
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public CircleCollisionTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet) {
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);
		
		this.colhandler = new CollisionHandler(true, actorhandler);
		
		this.testcar = new CollisionTestCar(drawer, 
				this.colhandler.getCollidableHandler(), this.colhandler, actorhandler, 
				keylistenerhandler, new CarSpriteBank(applet));
		this.testcircle = new TestEllipse(drawer, this.colhandler.getCollidableHandler());
		
		this.testcar.inActivate();
		this.testcar.setInvisible();
		this.colhandler.inActivate();
		this.testcircle.setInvisible();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void test() {
		//Let's activate our participants and make them visible
		this.testcar.activate();
		this.testcar.setVisible();
		this.testcircle.setVisible();
		this.colhandler.activate();
	}
}
