package handlers;

import drawnobjects.DrawnObject2D;
import handleds.Collidable;
import handleds.Handled;

/**
 * This class handles multiple collidables
 *
 * @author Gandalf.
 *         Created 18.6.2013.
 */
public class CollidableHandler extends Handler implements Collidable
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new empty collidablehandler
	 *
	 * @param autodeath Will the handler die if it runs out of handleds
	 * @param superhandler The collidablehandler that holds the handler
	 */
	public CollidableHandler(boolean autodeath, CollidableHandler superhandler)
	{
		super(autodeath, superhandler);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public boolean objectCollides(Collidable object)
	{
		// Returns true if the 
		return false;
	}

	@Override
	public boolean isSolid()
	{
		// TODO Auto-generated method stub.
		return false;
	}

	@Override
	public boolean makeSolid()
	{
		// TODO Auto-generated method stub.
		return false;
	}

	@Override
	public boolean makeUnsolid()
	{
		// TODO Auto-generated method stub.
		return false;
	}
	
	@Override
	protected void addHandled(Handled h)
	{
		// Only adds Collidables
		if (h instanceof Collidable)
			super.addHandled(h);
	}
	
	
	// OTHER METHOD	-----------------------------------------------------
	
	private Collidable getCollidable(int index)
	{
		Handled maybecollidable = getHandled(index);
		// Casts the handled to collidable
		if (maybecollidable instanceof Collidable)
			return (Collidable) maybecollidable;
		else
			return null;
	}
	
	/**
	 * Adds a new collidable to the list of collidables
	 *
	 * @param c The collidable to be added
	 */
	public void addCollidable(Collidable c)
	{
		addHandled(c);
	}
}
