package tests;

import camera.BasicCamera;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import racing.Car;
import racing.CarSpriteBank;

/**
 * Tests the car and the camera simultaneously
 *
 * @author Gandalf.
 *         Created 18.6.2013.
 */
public class CameraCarTest extends AbstractTest
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private Car testcar;
	private BasicCamera testcamera;
	
	
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
	public CameraCarTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);
		
		// Initializes attributes
		this.testcamera = new BasicCamera(-500, -225, drawer, actorhandler, 1000, 550);
		this.testcar = new Car(500, 225, null, actorhandler, 
				keylistenerhandler, new CarSpriteBank(applet));
		
		this.testcamera.inActivate();
		this.testcamera.setInvisible();
	}

	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	public void test()
	{
		this.testcamera.activate();
		this.testcamera.setVisible();
		this.testcamera.addDrawable(this.testcar);
		this.testcamera.scale(0.5, 0.5);
	}
}