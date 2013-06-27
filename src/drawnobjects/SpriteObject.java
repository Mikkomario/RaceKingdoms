package drawnobjects;

import graphic.Sprite;
import graphic.SpriteBank;
import handlers.ActorHandler;
import handlers.DrawableHandler;

import processing.core.PApplet;

/**
 * This class represents a creature or object in the game world that is drawn
 * to screen with a sprite (image)
 *
 * @author Gandalf.
 *         Created 26.11.2012.
 */
public abstract class SpriteObject extends PhysicDrawnObject
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private Sprite sprite;
	private double imageSpeed, imageIndex;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * Creates a new spriteobject with the given information. Animation and 
	 * visibility are set 
	 * on at default. Scaling and image angle won't be affected.
	 *
	 * @param x The new x-coordinate of the object (Game world Pxl)
	 * @param y The new y-coordinate of the object (Game world Pxl)
	 * @param sprite The Sprite with which the object will be drawn
	 * @param drawer The drawablehandler that draws the object (optional)
	 * @param actorhandler The actorhandler that calls the object's act event (optional)
	 */
	public SpriteObject(int x, int y, Sprite sprite, DrawableHandler drawer, ActorHandler actorhandler)
	{
		super(x, y, drawer, actorhandler);
		
		// Initializes the attributes
		this.sprite = sprite;
		
		this.imageSpeed = 0.1;
		this.imageIndex = 0;
	}
	
	/**
	 * Creates a new spriteobject using the given spritebank and spritename
	 *
	 * @param x The ingame x-coordinate of the object's origin (pxl)
	 * @param y The ingame y-coordinate of the object's origin (pxl)
	 * @param drawer The drawablehandler that draws the object (optional)
	 * @param actorhandler The actorhandler that calls the object's act event (optional)
	 * @param bank The spritebank that holds the sprite of the object
	 * @param spritename The name of the object's sprite in the bank
	 */
	public SpriteObject(int x, int y, DrawableHandler drawer, 
			ActorHandler actorhandler, SpriteBank bank, String spritename)
	{
		super(x, y, drawer, actorhandler);
		
		// Initializes the attributes
		this.sprite = bank.getSprite(spritename);
		
		this.imageSpeed = 0.1;
		this.imageIndex = 0;
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		// Draws the sprite
		applet.image(getSprite().getSubImage(getImageIndex()), 0, 0);
	}
	
	@Override
	public double getOriginX()
	{
		return getSprite().getOriginX();
	}

	@Override
	public double getOriginY()
	{
		return getSprite().getOriginY();
	}
	
	@Override
	public void act()
	{
		super.act();
		
		// The spriteaóbject also handles the animation in the act-event
		animate();
	}
	
	
	// GETTERS & SETTERS	-----------------------------------------------
	
	/**
	 * @return The sprite as which the object is represented
	 */
	public Sprite getSprite()
	{
		return this.sprite;
	}
	
	/**
	 *This method changes the sprite with which the object is represented. The 
	 *image index will be set to 0 in the process.
	 * @param newSprite The new sprite
	 */
	public void setSprite(Sprite newSprite)
	{
		if (newSprite == null)
			return;
		
		this.sprite = newSprite;
		this.imageIndex = 0;
	}
	
	/**
	 * @return How fast the frames in the animation change (animframe / frame)
	 */
	public double getImageSpeed()
	{
		return this.imageSpeed;
	}
	
	/**
	 * Changes how fast the frames in the animation change
	 * 
	 * @param imageSpeed The new animation speed (animframes / frame)
	 */
	public void setImageSpeed(double imageSpeed)
	{
		this.imageSpeed = imageSpeed;
	}
	
	/**
	 * @return Which subimage from the animation is currently drawn [0, numberOfSubimages[
	 */
	public int getImageIndex()
	{
		return (int) this.imageIndex;
	}
	
	/**
	 * Changes which subimage from the animation is currently drawn
	 * 
	 * @param imageIndex The index of the subimage drawn [0, numberOfSubimages[
	 */
	public void setImageIndex(int imageIndex)
	{
		this.imageIndex = imageIndex;
	}
	
	
	// OTHER METHODS	---------------------------------------------------
	
	// Handles the change of the image index
	private void animate()
	{
		this.imageIndex += getImageSpeed();
		this.imageIndex = this.imageIndex % getSprite().getImageNumber();
		
		if (this.imageIndex < 0)
			this.imageIndex += getSprite().getImageNumber();
	}

	@Override
	public int getWidth()
	{
		return getSprite().getWidth();
	}

	@Override
	public int getHeight()
	{
		return getSprite().getHeight();
	}
}