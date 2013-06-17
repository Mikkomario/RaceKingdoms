package handlers;

import drawnobjects.DrawnObject2D;
import handleds.Handled;

/**
 * Drawnobjecthandler is a special drawablehandler that handles only drawn 
 * objects
 *
 * @author Gandalf.
 *         Created 17.6.2013.
 */
public class DrawnObjectHandler extends DrawableHandler
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new empty drawnobjecthandler with the given information
	 *
	 * @param autodeath Will the handler automatically die when it runs out of handleds
	 * @param superhandler Which drawablehandler will draw the handler and its content
	 */
	public DrawnObjectHandler(boolean autodeath, DrawableHandler superhandler)
	{
		super(autodeath, superhandler);
	}
	
	
	// IMPLEMENTED METHODS	---------------------------------------------
	
	@Override
	protected void addHandled(Handled h)
	{
		// Can only add drawnobjects
		if (h instanceof DrawnObject2D)
			super.addHandled(h);
	}
	
	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 *Adds the given drawnobject to the drawn objects
	 *
	 * @param d The object to be added
	 */
	public void addDrawnObject(DrawnObject2D d)
	{
		super.addHandled(d);
	}
	
	/**
	 * Gets a handled from the list of handleds casted as a drawnobject
	 *
	 * @param index The index of the drawnobject
	 * @return The drawnobject from the index
	 */
	protected DrawnObject2D getDrawnObject(int index)
	{
		Handled maybeDrawnObject = getHandled(index);
		if (maybeDrawnObject instanceof DrawnObject2D)
			return (DrawnObject2D) maybeDrawnObject;
		else
			return null;
	}
}
