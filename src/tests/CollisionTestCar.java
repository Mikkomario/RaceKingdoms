package tests;

import java.awt.Point;
import java.util.HashMap;

import processing.core.PApplet;
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
		setSprite(getMask());
	}
	
	
	// IMPLEMENTED METHODS
	
	@Override
	public void onCollision(HashMap<Point, Collidable> collisionmap)
	{
		System.out.println("Collides!");
	}
	
	@Override
	public void drawSelf(PApplet applet)
	{
		// Also draws the collisionpoints
		super.drawSelf(applet);
		
		//applet.pushMatrix();
		applet.fill(255, 0, 0);
		//applet.scale(3);
		
		//Point tran = transform(0, 0);
		//applet.rect(tran.x, tran.y, 3, 3);
		//System.out.println(tran);
		
		//System.out.println("*******");
		
		for (Point p: getCollisionPoints())
		{
			applet.rect(p.x, p.y, 5, 5);
			//System.out.println(p);
		}
		
		/*
		System.out.println("***********************");
		for (Point p: getRelativeCollisionPoints())
		{
			System.out.println(p);
		}
		*/
		
		//applet.popMatrix();
		applet.fill(0);
	}
	
	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		// Scales the object with W and S
		super.onKeyDown(key, keyCode, coded);
		
		if (!coded)
		{
			if (key == 'w')
				scale(1.1, 1.1);
			else if (key == 's')
				scale(0.9, 0.9);
		}
	}
}
