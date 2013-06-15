package racing;

import processing.core.PConstants;
import graphic.SpriteBank;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import drawnobjects.SpriteObject;

/**
 * Car is the playable car that races around the stages
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class Car extends SpriteObject implements listeners.KeyListener
{	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new car
	 *
	 * @param x The new x-coordinate of the car (ingame pxl)
	 * @param y The new y-coordinate of the car (ingame pxl)
	 * @param drawer The drawablehandler that draws the car (optional)
	 * @param actorhandler The actorhandler that moves the car (optional)
	 * @param keyhandler The keylistenerhandler that informs the car of the 
	 * keypresses (optional)
	 * @param carspritebank The Spritebank that holds the car's sprite
	 */
	public Car(int x, int y, DrawableHandler drawer,
			ActorHandler actorhandler, KeyListenerHandler keyhandler, 
			SpriteBank carspritebank)
	{
		super(x, y, carspritebank.getSprite("testcar"), drawer, actorhandler);
		
		// Adds the car to the keyhandler (if possible)
		if (keyhandler != null)
			keyhandler.addKeyListener(this);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		// Turns with left / right arrowkey
		if (coded)
		{
			if (keyCode == PConstants.LEFT)
				addRotation(2);
			else if (keyCode == PConstants.RIGHT)
				addRotation(-2);
		}
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// TODO Auto-generated method stub.
		
	}

}
