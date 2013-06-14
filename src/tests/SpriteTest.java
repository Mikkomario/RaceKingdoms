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
		this.spriteobj = new TestSpriteObject(this.bank.getSprite("mushroom"));
	}

	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public void test()
	{
		getDrawer().addDrawable(this.spriteobj);
		getActorHandler().addActor(this.spriteobj);
		
		/*
		this.spriteobj.setImageIndex(1);
		this.spriteobj.setImageSpeed(0);
		*/
	}
}
