package tests;

import listeners.KeyListener;
import handlers.DrawableHandler;
import handlers.KeyListenerHandler;
import helpAndEnums.DoublePoint;

/**
 * This class works like a testbox except it can also be rotated around certain 
 * points
 *
 * @author Gandalf.
 *         Created 30.6.2013.
 */
public class RotationTestBox extends TestBox implements KeyListener
{
	// ATTRIBUTES	------------------------------------------------------
	
	private boolean active;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new rotation test box with the given information
	 * @param drawer The drawablehandler that draws the object (optional)
	 * @param keyhandler The keylistenerhandler that informs the object about 
	 * keys (optional)
	 */
	public RotationTestBox(DrawableHandler drawer, 
			KeyListenerHandler keyhandler)
	{
		super(drawer, null);
		
		// Initializes attributes
		this.active = true;
		
		// Adds the object to the handler (if possible)
		if (keyhandler != null)
			keyhandler.addKeyListener(this);
	}
	
	
	// IMPLEMENTEED METHODS	---------------------------------------------

	@Override
	public void onKeyDown(int key, int keyCode, boolean coded)
	{
		if (!coded)
		{
			// On I the object is rotated around a point
			if (key == 'i')
				rotateAroundPoint(1, new DoublePoint(0, 0));
		}
	}

	@Override
	public void onKeyPressed(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}

	@Override
	public void onKeyReleased(int key, int keyCode, boolean coded)
	{
		// Does nothing
	}

	@Override
	public boolean isActive()
	{
		return this.active;
	}

	@Override
	public boolean activate()
	{
		this.active = true;
		return true;
	}

	@Override
	public boolean inactivate()
	{
		this.active = false;
		return true;
	}
}
