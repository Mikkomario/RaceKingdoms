package tests;

import processing.core.PApplet;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class SpriteTest extends AbstractTest
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private TestSpriteBank bank;
	private TestSpriteObject spriteobj;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new test with the required information
	 * 
	 * @param actorhandler The handler that handles created actors
	 * @param drawer The drawer that draws created drawables
	 * @param keylistenerhandler The KeyListenerHandler that informs created listeners
	 * @param mouselistenerhandler The MouseListenerHandler that informs created listeners
	 * @param applet The applet that loads sprites
	 * 
	 */
	public SpriteTest(ActorHandler actorhandler, DrawableHandler drawer,
			handlers.KeyListenerHandler keylistenerhandler,
			handlers.MouseListenerHandler mouselistenerhandler, PApplet applet)
	{
		super(actorhandler, drawer, keylistenerhandler, mouselistenerhandler);
		
		this.bank = new TestSpriteBank(applet);
		this.spriteobj = new TestSpriteObject(this.bank.getSprite("mushroom"), 
				drawer, actorhandler);
		
		this.spriteobj.inActivate();
		this.spriteobj.setInvisible();
	}

	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public void test()
	{
		this.spriteobj.activate();
		this.spriteobj.setVisible();
		/*
		this.spriteobj.setImageIndex(1);
		this.spriteobj.setImageSpeed(0);
		*/
		this.spriteobj.setMaxSpeed(13);
		this.spriteobj.setMotion(75, 10);
		//this.spriteobj.addMotion(90, 5);
		this.spriteobj.setFriction(0.1);
		this.spriteobj.setRotation(20);
		this.spriteobj.setRotationFriction(0.2);
		System.out.println(this.spriteobj.getSpeed());
	}
}
