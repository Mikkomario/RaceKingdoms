package backgrounds;

import graphic.Sprite;
import graphic.SpriteBank;
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
	// TODO: To be continued after some preparations are made
	
	// ATTRIBUTES	-------------------------------------------------------
	
	private Sprite texture;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new background with the given information
	 *
	 * @param x The backround's center's x-coordinate
	 * @param y The backgound's center's y-coordinate
	 * @param drawer The drawablehandler that draws the background
	 * @param bank The spritebank that holds the textrure sprite
	 * @param texturename The name of the texture in the bank
	 */
	public Background(int x, int y, DrawableHandler drawer, SpriteBank bank, 
			String texturename)
	{
		super(x, y, DepthConstants.BOTTOM, drawer);

		// Initializes attributes
		this.texture = bank.getSprite(texturename);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public int getOriginX()
	{
		// TODO Auto-generated method stub.
		return 0;
	}

	@Override
	public int getOriginY()
	{
		// TODO Auto-generated method stub.
		return 0;
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		// TODO Auto-generated method stub.
		
	}

}
