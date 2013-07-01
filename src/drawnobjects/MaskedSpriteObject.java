package drawnobjects;

import java.awt.Point;
import java.util.ArrayList;

import graphic.Sprite;
import graphic.SpriteBank;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.CollisionHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;

/**
 * Masked spriteobject checks collisions according to the mask, only collision
 * points within the mask are taken into notice
 *
 * @author Gandalf.
 *         Created 27.6.2013.
 */
public abstract class MaskedSpriteObject extends SpriteObject
{
	// ATTRIBUTES	------------------------------------------------------
	
	private Sprite mask;
	private static int maskcolor = -65536;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new masked spriteobject with the given position and sprites
	 * The object is added to the given handlers (optional).
	 *
	 * @param x The object's new x-coordinate
	 * @param y The object's new y-coordinate
	 * @param depth How 'deep' the object is drawn
	 * @param isSolid Can the object be collided with
	 * @param collisiontype What shape the object is collisionwise
	 * @param drawer The drawer that draws the object
	 * @param collidablehandler The collidablehandler that handles the object's 
	 * collision checking
	 * @param collisionhandler The collisionhandler that informs the object 
	 * about collisions 
	 * @param actorhandler The actorhandler that makes the object react to steps
	 * @param bank The spritebank that holds the sprites used in the object
	 * @param spritename The name of the sprite in the bank
	 * @param maskname The name of the collisionmask in the bank
	 */
	public MaskedSpriteObject(int x, int y, int depth, boolean isSolid, 
			CollisionType collisiontype, DrawableHandler drawer, 
			CollidableHandler collidablehandler, CollisionHandler collisionhandler, 
			ActorHandler actorhandler, SpriteBank bank, String spritename, 
			String maskname)
	{
		super(x, y, depth, isSolid, collisiontype, drawer, collidablehandler, 
				collisionhandler, actorhandler, bank, spritename);
		//super(x, y, drawer, actorhandler, bank, spritename);

		// Initializes attributes
		this.mask = bank.getSprite(maskname);
		
		setBoxCollisionPrecision(1, 2);
	}

	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	protected void setRelativeCollisionPoints(Point[] collisionpoints)
	{
		if (collisionpoints == null)
			super.setRelativeCollisionPoints(collisionpoints);
		else
			super.setRelativeCollisionPoints(
					getRefinedCollisionPoints(collisionpoints));
	}
	
	@Override
	protected void setBoxCollisionPrecision(int edgeprecision, int insideprecision)
	{
		super.setBoxCollisionPrecision(edgeprecision, insideprecision);
		
		// Refines the collisionpoints
		setRelativeCollisionPoints(getRelativeCollisionPoints());
	}
	
	@Override
	public Collidable pointCollides(int x, int y)
	{
		Collidable c = super.pointCollides(x, y);
		
		if (c == null)
			return null;
		// Point collides only if it's also in the mask
		else if (!maskContainsPoint(negateTransformations(x, y)))
			return null;
		else
			return c;
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return The mask used in the spriteobject
	 */
	public Sprite getMask()
	{
		return this.mask;
	}
	
	/**
	 * @param newmask Changes the object's mask to a new one
	 */
	public void setMask(Sprite newmask)
	{
		this.mask = newmask;
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	private Point[] getRefinedCollisionPoints(Point[] collisionpoints)
	{
		// Removes the collisionpoints that aren't in the mask
		ArrayList<Point> templist = new ArrayList<Point>();
		// Adds all the relevant points to the list
		for (int i = 0; i < collisionpoints.length; i++)
		{
			if (maskContainsPoint(collisionpoints[i]))
				templist.add(collisionpoints[i]);
		}
		// Adds all points from the list to the table
		Point[] newpoints = new Point[templist.size()];
		for (int i = 0; i < templist.size(); i++)
		{
			newpoints[i] = templist.get(i);
		}
		return newpoints;
	}
	
	private boolean maskContainsPoint(Point p)
	{	
		int maskindex = 0;
		// If the mask is animated as well, uses the animated mask
		if (getMask().getImageNumber() == getSprite().getImageNumber())
			maskindex = getImageIndex();
		
		int c = this.mask.getSubImage(maskindex).get(p.x, p.y);
		//System.out.println(c == maskcolor);
		return c == maskcolor;
	}
}
