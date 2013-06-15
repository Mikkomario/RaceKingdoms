package racing;

import java.io.FileNotFoundException;

import processing.core.PApplet;

import graphic.SpriteBank;

/**
 * Holds all the sprites for the game's cars
 *
 * @author Gandalf.
 *         Created 15.6.2013.
 */
public class CarSpriteBank extends SpriteBank
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates the spritebank and loads the sprites
	 *
	 * @param applet The applet that can load sprites
	 */
	public CarSpriteBank(PApplet applet)
	{
		super(applet);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void createSprites() throws FileNotFoundException
	{
		createSprite("images/dragon_head.png", 1, 91, 52, "test");
	}

}
