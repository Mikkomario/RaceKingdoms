package handlers;

import processing.core.PApplet;
import handleds.Drawable;
import handleds.Handled;

/**
 * The object from this class will draw multiple drawables, calling their 
 * drawSelf-methods and removing them when necessary
 *
 * @author Gandalf.
 *         Created 27.11.2012.
 */
public class DrawableHandler extends Handler implements Drawable
{	
	// ATTRIBUTES	------------------------------------------------------
	
	private int depth;
	private boolean usesDepth;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new drawablehandler. Drawables must be added later manually.
	 * If autodeath is on and no drawables are added, the handler will die.
	 *
	 * @param autodeath Will the handler die if it has no living drawables to handle
	 * @param usesDepth Will the handler draw the objects in a depth-specific order
	 * @param depth How 'deep' the objects in this handler are drawn
	 * @param superhandler The drawablehandler that will draw this handler (optional)
	 */
	public DrawableHandler(boolean autodeath, boolean usesDepth, int depth, 
			DrawableHandler superhandler)
	{
		super(autodeath, superhandler);
		
		// Initializes attributes
		this.depth = depth;
		this.usesDepth = usesDepth;
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------
	
	@Override
	public void drawSelf(PApplet applet)
	{
		// This calls for all active actor's act method
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (getDrawable(i).isVisible())
				getDrawable(i).drawSelf(applet);
		}
	}

	@Override
	public boolean isVisible()
	{
		// Returns false only if all the actors are inactive
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (getDrawable(i).isVisible())
				return true;
		}
		
		return false;
	}

	@Override
	public boolean setVisible()
	{
		// tries to set all the drawables visible, returns false if all the drawables
		// couldn't be made visible
		boolean returnValue = true;
		
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (!getDrawable(i).setVisible())
				returnValue = false;
		}
		
		return returnValue;
	}

	@Override
	public boolean setInvisible()
	{
		// tries to set all the drawables invisible, returns false if all the drawables
		// couldn't be made invisible
		boolean returnValue = true;
		
		for (int i = 0; i < getHandledNumber(); i++)
		{
			if (!getDrawable(i).setInvisible())
				returnValue = false;
		}
		
		return returnValue;
	}
	
	@Override
	public int getDepth()
	{
		return this.depth;
	}
	
	@Override
	public boolean setDepth(int depth)
	{
		this.depth = depth;
		return true;
	}
	
	@Override
	protected void addHandled(Handled h)
	{
		// Can only add drawables
		if (!(h instanceof Drawable))
			return;
		
		Drawable d = (Drawable) h;
		
		// If the depth sorting is on, finds the spot for the object
		if (this.usesDepth)
		{
			int index = 0;
			
			while (index < getHandledNumber() - 1)
			{
				// Checks if there's an object with a higher depth
				if (((Drawable) getHandled(index)).getDepth() < d.getDepth())
				{
					super.insertHandled(d, index);
					return;
				}
				else
					index ++;
			}
			// If no object with a higher depth was found, simply adds the 
			// drawable to the end of the list as usual
		}
		
		super.addHandled(d);
	}
	
	@Override
	protected Class<?> getSupportedClass()
	{
		return Drawable.class;
	}
	
	
	// OTHER METHODS	---------------------------------------------------
	
	/**
	 *Adds the given actor to the handled actors
	 *
	 * @param d The actor to be added
	 */
	public void addDrawable(Drawable d)
	{
		super.addHandled(d);
	}
	
	/**
	 * Gets a handled from the list of handleds casted as a drawable
	 *
	 * @param index The index of the drawable
	 * @return The drawable from the index
	 */
	private Drawable getDrawable(int index)
	{
		Handled maybeDrawable = getHandled(index);
		if (maybeDrawable instanceof Drawable)
			return (Drawable) maybeDrawable;
		else
			return null;
	}
}
