package drawnobjects;

import java.awt.Point;
import java.util.ArrayList;

import graphic.Sprite;
import graphic.SpriteBank;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.DrawableHandler;

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
	 * @param drawer The drawer that draws the object
	 * @param actorhandler The actorhandler that makes the object react to steps
	 * @param bank The spritebank that holds the sprites used in the object
	 * @param spritename The name of the sprite in the bank
	 * @param maskname 
	 */
	public MaskedSpriteObject(int x, int y, DrawableHandler drawer,
			ActorHandler actorhandler, SpriteBank bank, String spritename, 
			String maskname)
	{
		super(x, y, drawer, actorhandler, bank, spritename);

		// Initializes attributes
		this.mask = bank.getSprite(maskname);
	}

	
	// IMPLEMENTED METHODS	-----------------------------------------------
	
	@Override
	protected void setRelativeCollisionPoints(Point[] collisionpoints)
	{
		super.setRelativeCollisionPoints(collisionpoints);
		
		if (collisionpoints == null)
			super.setRelativeCollisionPoints(collisionpoints);
		else
			super.setRelativeCollisionPoints(
					getRefinedCollisionPoints(collisionpoints));
	}
	
	@Override
	protected void setCollisionPrecision(int edgeprecision, int insideprecision)
	{
		super.setCollisionPrecision(edgeprecision, insideprecision);
		
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
		int c = this.mask.getSubImage(getImageIndex()).get(p.x, p.y);
		return c == maskcolor;
	}
}
