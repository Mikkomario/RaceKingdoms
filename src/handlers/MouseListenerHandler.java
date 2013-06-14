package handlers;

import listeners.MouseListener;

/**
 * Informs multiple objects about mouse's movements and clicks
 *
 * @author Gandalf.
 *         Created 28.12.2012.
 */
public class MouseListenerHandler extends AbstractMouseListenerHandler 
	implements MouseListener
{
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new empty mouselistenerhandler
	 *
	 * @param autodeath Will the handler die when it runs out of living listeners
	 */
	public MouseListenerHandler(boolean autodeath)
	{
		super(autodeath);
	}
	
	
	// IMPLEMENTED METHODS	-------------------------------------------------

	@Override
	public void onLeftDown(int mouseX, int mouseY)
	{
		// Does nothing
	}

	@Override
	public void onRightDown(int mouseX, int mouseY)
	{
		// Does nothing
	}

	@Override
	public void onLeftPressed(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setLeftMouseDown(true);
	}

	@Override
	public void onRightPressed(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setRightMouseDown(true);
	}

	@Override
	public boolean listensPosition(int x, int y)
	{
		// Handlers listen all positions
		return true;
	}

	@Override
	public boolean listensMouseEnterExit()
	{
		// Does not have a special area of interrest
		return false;
	}

	@Override
	public void onMouseEnter(int mouseX, int mouseY)
	{
		// Does nothing
	}

	@Override
	public void onMouseExit(int mouseX, int mouseY)
	{
		// Nothing
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
	}


	@Override
	public void onLeftReleased(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setLeftMouseDown(false);
	}


	@Override
	public void onRightReleased(int mouseX, int mouseY)
	{
		setMousePosition(mouseX, mouseY);
		setRightMouseDown(false);
	}


	@Override
	public void onMouseOver(int mouseX, int mouseY)
	{
		// Nothing
	}
}
