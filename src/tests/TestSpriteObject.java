package tests;

import graphic.Sprite;
import drawnobjects.SpriteObject;

/**
 * This is a simple spriteobject used for testing
 *
 * @author Gandalf.
 *         Created 14.6.2013.
 */
public class TestSpriteObject extends SpriteObject
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new spriteobject with the give sprite
	 *
	 * @param sprite
	 */
	public TestSpriteObject(Sprite sprite)
	{
		super(200, 200, sprite);

		this.setImageSpeed(0.5);
	}

}
