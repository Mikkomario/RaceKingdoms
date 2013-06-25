package tests;

import graphic.SpriteBank;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import racing.Car;

/**
 * This car tests the collision detection
 *
 * @author Gandalf.
 *         Created 25.6.2013.
 */
public class CollisionTestCar extends Car
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new car with the given information. The car is automatically 
	 * added to the necessary handlers
	 *
	 * @param drawer The drawer that will draw the car
	 * @param actorhandler The actorhandler that will call the car's act event
	 * @param keyhandler The keyhandler that informs the car about keypresses
	 * @param carspritebank The spritebank holding "testcar" sprite
	 * @param collisionhandler The collisionhandler that will inform the car 
	 * about collisions
	 */
	public CollisionTestCar(DrawableHandler drawer,
			ActorHandler actorhandler, KeyListenerHandler keyhandler,
			SpriteBank carspritebank, CollisionHandler collisionhandler)
	{
		super(500, 300, drawer, actorhandler, keyhandler, carspritebank);
		
		// Adds the car to the handler
		collisionhandler.addCollisionListener(this);
	}
	
	
	// IMPLEMENTED METHODS
	
	@Override
	public void onCollision(Collidable collided)
	{
		System.out.println("Collides!");
	}
}
