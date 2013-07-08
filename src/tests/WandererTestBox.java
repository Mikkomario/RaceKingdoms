package tests;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DepthConstants;
import helpAndEnums.DoublePoint;
import helpAndEnums.Material;
import helpAndEnums.Movement;
import drawnobjects.AdvancedPhysicDrawnObject;

/**
 * Wanderertestboxes are advancedphysicobjects that are supposed to test the 
 * interactive collision method.
 *
 * @author Gandalf.
 *         Created 8.7.2013.
 */
public class WandererTestBox extends AdvancedPhysicDrawnObject
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private int maxx, maxy;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new wandererTestBox to a random location on the screen
	 * 
	 * @param screenwidth The width of the screen the box wanders on 
	 * @param screenheight The height of the screen the box wanders on  
	 * @param drawer The drawer that will draw the box
	 * @param collidablehandler The collidablehandler that will handle the 
	 * box's collision detection
	 * @param collisionhandler The collisionhandler that will inform the box 
	 * about collisions
	 * @param actorhandler The actorhandler that will move the box
	 */
	public WandererTestBox(int screenwidth, int screenheight, 
			DrawableHandler drawer, CollidableHandler collidablehandler, 
			CollisionHandler collisionhandler, ActorHandler actorhandler)
	{
		super(0, 0, DepthConstants.NORMAL, true, CollisionType.BOX, drawer, 
				collidablehandler, collisionhandler, actorhandler);

		// Initializes attributes
		this.maxx = screenwidth;
		this.maxy = screenheight;
		
		Random rand = new Random();
		setPosition((int) (rand.nextDouble() * this.maxx), 
				(int) (rand.nextDouble() * this.maxy));
		setMovement(Movement.createMovement(rand.nextDouble() * 360, 
				rand.nextDouble() * 5));
		
		setBoxCollisionPrecision(3, 2);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void onCollision(ArrayList<DoublePoint> colpoints,
			Collidable collided)
	{
		// Only bounces from interactive objects
		if (!(collided instanceof AdvancedPhysicDrawnObject))
			return;
		
		AdvancedPhysicDrawnObject d = (AdvancedPhysicDrawnObject) collided;
		
		// Bounces from the other object
		for (int i = 0; i < colpoints.size(); i++)
		{
			bounceInteractivelyFrom(d, colpoints.get(i), 0, 0);
		}
	}

	@Override
	public int getZHeight()
	{
		return (int) (getHeight() * getYscale());
	}

	@Override
	public int getDensity()
	{
		// The blocks are made of rubber :)
		return Material.RUBBER.getDensity();
	}

	@Override
	public int getWidth()
	{
		return 100;
	}

	@Override
	public int getHeight()
	{
		return 100;
	}

	@Override
	public int getOriginX()
	{
		return 50;
	}

	@Override
	public int getOriginY()
	{
		return 50;
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		// Draws the box and the collisionpoints
		applet.rect(0, 0, 100, 100);
		
		for (DoublePoint p: getCollisionPoints())
		{
			applet.rect((int) p.getX(), (int) p.getY(), 5, 5);
		}
	}

	@Override
	public void act()
	{
		super.act();
		// Checks the collisions with the walls so that the object always stays 
		// inside the screen
	}
	
	
	// OTHER METHODS	-------------------------------------------------
	
	private void checkWallCollision()
	{
		// TODO: Continue
		if (getX() < 0)
			getMovement().setHSpeed(Math.abs(getMovement().getHSpeed()));
		else if (getX() > this.maxx)
			getMovement().setHSpeed(Math.abs(getMovement().getHSpeed()));
	}
}
