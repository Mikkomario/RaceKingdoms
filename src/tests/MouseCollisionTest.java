package tests;

import listeners.KeyListener;
import listeners.MouseListener;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import processing.core.PApplet;
import processing.core.PConstants;

/**Test to see if a mouse can collide with a box.
 * 
 * @author Unto	18.6.2013
 *
 */
public class MouseCollisionTest extends AbstractTest implements MouseListener, KeyListener{
	
	//ATTRIBUTES	------------------------------------------------------
	
	private TestBox testbox;
	private boolean active;
	private boolean alive;
	
	//CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The main applet
	 */
	public MouseCollisionTest(ActorHandler actorhandler,
			DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet) {
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler, applet);
		
		mouselistenerhandler.addMouseListener(this);
		keylistenerhandler.addKeyListener(this);
		
		this.active = false;
		this.alive = true;
		
		this.testbox = new TestBox(drawer);
		this.testbox.setInvisible();
		
	}

	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	public void test() {
		this.activate();
		this.testbox.setVisible();
		this.testbox.setPosition(400, 200);
		this.testbox.setAngle(45);
		this.testbox.setScale(2, 1);
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


	@Override
	public void onLeftDown(int mouseX, int mouseY) {
		// Does nothing
		
	}


	@Override
	public void onRightDown(int mouseX, int mouseY) {
		// Does nothing
		
	}


	@Override
	public void onLeftPressed(int mouseX, int mouseY) {
		// Does nothing
		
	}


	@Override
	public void onRightPressed(int mouseX, int mouseY) {
		// Does nothing
		
	}


	@Override
	public void onLeftReleased(int mouseX, int mouseY) {
		// Does nothing
		
	}


	@Override
	public void onRightReleased(int mouseX, int mouseY) {
		// Does nothing
		
	}


	@Override
	public boolean listensPosition(int x, int y) {
		//Let's see if our given point collides with the testbox
		return this.testbox.pointCollides(x, y);
	}


	@Override
	public boolean listensMouseEnterExit() {
		// We are interested in mouse's comings and goings
		return true;
	}


	@Override
	public void onMouseEnter(int mouseX, int mouseY) {
		// Does nothing
		System.out.println("Enter");
	}


	@Override
	public void onMouseOver(int mouseX, int mouseY) {
		System.out.println("You should be hanging over the box right now.");
		
	}


	@Override
	public void onMouseExit(int mouseX, int mouseY) {
		// Does nothing
		System.out.println("Exit");
	}


	@Override
	public void onMouseMove(int mouseX, int mouseY) {
		// Prints the coordinate data
		//this.testbox.testTransformation(mouseX, mouseY);
		//System.out.println(HelpMath.pointDirection((int) this.testbox.getX(), (int) this.testbox.getY(), mouseX, mouseY));
	}


	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		if (coded)
		{
			// Rotates with left & right
			if (keyCode == PConstants.LEFT)
				this.testbox.addAngle(1);
			else if (keyCode == PConstants.RIGHT)
				this.testbox.addAngle(-1);
			// Scales with up & down
			else if (keyCode == PConstants.UP)
				this.testbox.scale(1.05, 1.05);
			else if (keyCode == PConstants.DOWN)
				this.testbox.scale(0.95, 0.95);
		}
	}


	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}


	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}

}
