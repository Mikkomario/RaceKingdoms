package backgrounds;

import graphic.SpriteBank;
import graphic.SpriteDrawer;
import handlers.ActorHandler;
import handlers.DrawableHandler;
import helpAndEnums.DepthConstants;
import processing.core.PApplet;
import drawnobjects.DrawnObject;

/**
 * Background is a simple surface that can be drawn under other objects
 *
 * @author Gandalf.
 *         Created 1.7.2013.
 */
public class Background extends DrawnObject
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private SpriteDrawer texturedrawer;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new background with the given information
	 *
	 * @param x The backround's center's x-coordinate
	 * @param y The backgound's center's y-coordinate
	 * @param drawer The drawablehandler that draws the background
	 * @param actorhandler The actorhandler that animates the background 
	 * (optional, for animated backgrounds)
	 * @param bank The spritebank that holds the textrure sprite
	 * @param texturename The name of the texture in the bank
	 */
	public Background(int x, int y, DrawableHandler drawer, 
			ActorHandler actorhandler, SpriteBank bank, String texturename)
	{
		super(x, y, DepthConstants.BOTTOM, drawer);

		// Initializes attributes
		this.texturedrawer = 
				new SpriteDrawer(bank.getSprite(texturename), actorhandler);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public int getOriginX()
	{
		return this.texturedrawer.getSprite().getOriginX();
	}

	@Override
	public int getOriginY()
	{
		return this.texturedrawer.getSprite().getOriginY();
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		// Draws the sprite
		this.texturedrawer.drawSprite(applet);
	}
	
	
	// GETTERS & SETTERS	----------------------------------------------
	
	/**
	 * @return The spritedrawer used to drawing the texture of the background
	 */
	public SpriteDrawer getSpriteDrawer()
	{
		return this.texturedrawer;
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Changes the background's width and height
	 *
	 * @param width The new width of the background
	 * @param height The new height of the background
	 */
	public void setDimensions(int width, int height)
	{
		// Calculates the scaling
		double xscale = width / (double) this.texturedrawer.getSprite().getWidth();
		double yscale = height / (double) this.texturedrawer.getSprite().getHeight();
		
		// Scales the object
		scale(xscale, yscale);
	}
}
