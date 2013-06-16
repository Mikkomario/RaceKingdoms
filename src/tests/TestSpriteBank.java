package tests;

import java.io.FileNotFoundException;

import processing.core.PApplet;

import graphic.SpriteBank;

/**
 * This class holds all the sprites used in the tests
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class TestSpriteBank extends SpriteBank
{
	// IMPLEMENTED METHODS	------------------------------------------------

	/**
	 * This creates and initializes the new spritebank
	 *
	 * @param applet The applet creating the spritebank
	 */
	public TestSpriteBank(PApplet applet)
	{
		super(applet);
	}

	@Override
	public void createSprites() throws FileNotFoundException
	{
		createSprite("images/crystal_mushroom_death_strip_4.png", 4, 50, 50, "mushroom");
	}

}
