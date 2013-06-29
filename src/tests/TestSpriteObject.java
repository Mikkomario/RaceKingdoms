package tests;

import java.awt.Point;
import java.util.ArrayList;

import graphic.Sprite;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import drawnobjects.SpriteObject;

/**
 * This is a simple spriteobject used for testing
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class TestSpriteObject extends SpriteObject
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new spriteobject with the give sprite
	 *
	 * @param sprite The new sprite of the object
	 * @param drawer The drawer that will draw the object (optional)
	 * @param actorhandler The actorhandler that will call the object's act-event (optional)
	 */
	public TestSpriteObject(Sprite sprite, DrawableHandler drawer, ActorHandler actorhandler)
	{
		super(200, 200, sprite, drawer, actorhandler);

		this.setImageSpeed(0.1);
	}

	@Override
	public void onCollision(ArrayList<Point> collisionpoints, Collidable collided)
	{
		// Does nothing
	}

}
