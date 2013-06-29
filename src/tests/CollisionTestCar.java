package tests;

import java.awt.Point;
import java.util.ArrayList;

import drawnobjects.DrawnObject2D;

import processing.core.PApplet;
import graphic.SpriteBank;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import helpAndEnums.HelpMath;
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
		
		// Just tests the miscalculations during transform and negatetransform
		Point p = new Point(0, 0);
		System.out.println("Start: " + p);
		for (int i = 0; i < 10; i++)
		{
			p = negateTransformations(p.x, p.y);
			System.out.println("Relative" + i + ": X " + p.x + " Y " + p.y);
			p = transform(p.x, p.y);
			System.out.println("Absolute" + i + ": X " + p.x + " Y " + p.y);
		}
	}
	
	
	// IMPLEMENTED METHODS
	
	@Override
	public void onCollision(ArrayList<Point> collisionpoints, Collidable collided)
	{
		//System.out.println("Collides!");

		// Bounces away from drawnobjects
		if (collided instanceof DrawnObject2D)
		{
			DrawnObject2D d = (DrawnObject2D) collided;
			
			bounceFrom(d, collisionpoints.get(0), 3, 0.1);
		}
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
			// With d and a the car is rotated around the origin of the screen
			else if (key == 'd')
				rotateAroundPoint(-2, new Point(500, 300));
			else if (key == 'a')
				rotateAroundPoint(2, new Point(500, 300));
			else if (key == 'e')
				addMotion(HelpMath.checkDirection(getAngle() - 90), 0.5);
			else if (key == 'q')
				addMotion(HelpMath.checkDirection(getAngle() + 90), 0.5);
		}
	}
}
