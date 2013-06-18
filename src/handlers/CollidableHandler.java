package handlers;

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
		// Returns true if any of the collidables collides with the object
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (getCollidable(i).objectCollides(object))
				return true;
		}
		
		return false;
	}

	@Override
	public boolean isSolid()
	{
		// Handler is solid if any of the objects are solid
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (getCollidable(i).isSolid())
				return true;
		}
		
		return false;
	}

	@Override
	public boolean makeSolid()
	{
		boolean returnvalue = true;
		// Tries to make all of the collidables solid
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (!getCollidable(i).makeSolid())
				returnvalue = false;
		}
		// Returns whether all of the objects were made solid
		return returnvalue;
	}

	@Override
	public boolean makeUnsolid()
	{
		boolean returnvalue = true;
		// Tries to make all of the collidables solid
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (!getCollidable(i).makeUnsolid())
				returnvalue = false;
		}
		// Returns whether all of the objects were made solid
		return returnvalue;
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
