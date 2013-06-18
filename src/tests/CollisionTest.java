package tests;

import handleds.Actor;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import racing.Car;
import racing.CarSpriteBank;

/**Test to see if the car can collide with a box.
 * 
 * @author Unto	18.6.2013
 *
 */
public class CollisionTest extends AbstractTest implements Actor{
	
	//ATTRIBUTES	------------------------------------------------------
	
	private Car testcar;
	private TestBox testbox;
	private boolean active;
	private boolean alive;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	
	/**Constructs a testcar and testbox to see if they can collide.
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public CollisionTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet) {
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);
		
		actorhandler.addActor(this);
		this.active = false;
		this.alive = true;
		
		this.testcar = new Car(500, 225, drawer, actorhandler, 
				keylistenerhandler, new CarSpriteBank(applet));
		this.testbox = new TestBox(drawer);
		
		this.testcar.inActivate();
		this.testcar.setInvisible();
		
		this.testbox.setInvisible();
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void test() {
		//Let's activate our participants and make them visible
		this.activate();
		this.testcar.activate();
		this.testcar.setVisible();
		this.testbox.setVisible();
		
		
	}
	
	@Override
	public void act(){
		if(this.testcar.objectCollides(this.testbox))
			System.out.println("Collision detected!");
	}


	@Override
	public boolean isActive() {
		return this.active;
	}


	@Override
	public boolean activate() {
		this.active = true;
		return true;
	}


	@Override
	public boolean inActivate() {
		this.active = false;
		return true;
	}


	@Override
	public boolean isDead() {
		return !this.alive;
	}


	@Override
	public boolean kill() {
		this.alive = false;
		return true;
	}

}
