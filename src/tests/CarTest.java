package tests;

import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import racing.Car;
import racing.CarSpriteBank;

/**
 * CarTest simply tests the basic movement of the car
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class CarTest extends AbstractTest
{
	// ATTRIBUTES	------------------------------------------------------
	
	private Car car;
	
	
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
	public CarTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);

		// Initializes attributes
		this.car = new Car(300, 300, drawer, null, null, actorhandler, 
				keylistenerhandler, new CarSpriteBank(applet));
		this.car.inActivate();
		this.car.setInvisible();
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void test()
	{
		this.car.activate();
		this.car.setVisible();
	}
}
