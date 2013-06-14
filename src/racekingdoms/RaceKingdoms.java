package racekingdoms;


import handlers.DrawableHandler;
import handlers.MainKeyListenerHandler;
import handlers.MainMouseListenerHandler;
import handlers.StepHandler;
import processing.core.PApplet;

/**
 * This class starts the program and creates the necessary elements of the game. 
 * This class also informs listeners about keystrokes and the mouse
 *
 * @author Gandalf.
 *         Created 13.6.2013.
 */
public class RaceKingdoms extends PApplet
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private static final long serialVersionUID = 1L;
	private MainKeyListenerHandler keyhandler;
	private MainMouseListenerHandler mousehandler;
	private StepHandler stephandler;
	private DrawableHandler drawer;
	private boolean needsUpdating; 
	
	
	// IMPLEMENTED METHODS	---------------------------------------------

	@Override
	public void setup()
	{
		// Sets the screen
		size(1000, 550);
		noFill();
		
		// Initializes the attributes
		this.keyhandler = new MainKeyListenerHandler();
		this.mousehandler = new MainMouseListenerHandler();
		this.stephandler = new StepHandler(16, this);
		this.drawer = new DrawableHandler(false);
		this.needsUpdating = true;
		
		// Starts the game
		new Thread(this.stephandler).start();
	}

	@Override
	public void draw()
	{
		if (this.needsUpdating)
		{
			this.drawer.drawSelf(this);
			this.needsUpdating = false;
		}
	}
	
	@Override
	public void keyPressed()
	{
		this.keyhandler.onKeyPressed(this.key, this.keyCode, this.key == CODED);
	}
	
	@Override
	public void keyReleased()
	{
		this.keyhandler.onKeyReleased(this.key, this.keyCode, this.key == CODED);
	}
	
	@Override
	public void mousePressed()
	{
		this.mousehandler.setMouseStatus(this.mouseX, 
				this.mouseY, true, this.mouseButton);
	}
	
	@Override
	public void mouseReleased()
	{
		this.mousehandler.setMouseStatus(this.mouseX, 
				this.mouseY, false, this.mouseButton);
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * This method should be called when the screen needs redrawing
	 */
	public void callScreenUpdate()
	{
		this.needsUpdating = true;
	}
	
	/**
	 * This method updates the mouse's position in the game
	 */
	public void callMousePositionUpdate()
	{
		this.mousehandler.setMousePosition(this.mouseX, this.mouseY);
	}
}
