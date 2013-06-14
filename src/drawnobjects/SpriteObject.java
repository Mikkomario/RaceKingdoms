package drawnobjects;

import graphic.Sprite;

import java.awt.Point;

import processing.core.PApplet;
import racekingdoms.HelpMath;

/**
 * This class represents a creature or object in the game world that is drawn
 * to screen with a sprite (image)
 *
 * @author Gandalf.
 *         Created 26.11.2012.
 */
public class SpriteObject extends DrawnObject2D
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private Sprite sprite;
	
	private double imageSpeed, imageIndex;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * 
	 * Creates a new spriteobject with the given information. Animation and 
	 * visibility are set 
	 * on at default. Scaling and image angle won't be affected.
	 *
	 * @param x The new x-coordinate of the object (Game world Pxl)
	 * @param y The new y-coordinate of the object (Game world Pxl)
	 * @param sprite The Sprite with which the object will be drawn
	 */
	public SpriteObject(int x, int y, Sprite sprite)
	{
		super(x, y);
		
		// Initializes the attributes
		this.sprite = sprite;
		
		this.imageSpeed = 0.1;
		this.imageIndex = 0;
	}
	
	
	// IMPLEMENTED METHODS	-----------------------------------------------

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		// First handles the animation
		animate();
		
		// Then draws the sprite
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

	
	/**
	 * 
	 * This method checks whether a certain point would collide with the 
	 * object
	 *
	 * @param x The ingame x-coordinate of the point
	 * @param y The ingame y-coordinate of the point
	 * @return Is the point above the object's sprite
	 */
	public boolean pointCollides(int x, int y)
	{
		// Negates the transformation
		Point negatedPoint = negateTransformations2D(x, y);
		return HelpMath.pointIsInRange(negatedPoint, (int) getX(), 
				(int) getX() + getSprite().getWidth(), (int) getY(), 
				(int) getY() + getSprite().getHeight());
	}
	
	/**
	 * 
	 * Checks wheter this spriteobject collides with another spriteobject. 
	 * This method is quite heavy so it's not adviced to be used too often.
	 *
	 * @param s The spriteobject that might be colliding with this object
	 * @return Are the objects overlapping each other
	 */
	public boolean objectCollides(SpriteObject s)
	{
		// Negates the transformations for both objects
		Point negatedPosOther =
				negateTransformations2D((int) s.getX(), (int) s.getY());
		Point negatedPosThis =
				s.negateTransformations2D((int) getX(), (int) getY());
		
		int widthThis = getSprite().getWidth();
		int widthOther = s.getSprite().getWidth();
		int heightThis = getSprite().getHeight();
		int heightOther = s.getSprite().getHeight();
		
		if (negatedPosOther.x + widthOther < negatedPosThis.x)
			return false;
		else if (negatedPosOther.x > negatedPosThis.x + widthThis)
			return false;
		else if (negatedPosOther.y + heightOther < negatedPosThis.y)
			return false;
		else if (negatedPosOther.y > negatedPosThis.y + heightThis)
			return false;
		else
			return true;
	}
	
	// Handles the change of the image index
	private void animate()
	{
		this.imageIndex += getImageSpeed();
		this.imageIndex = this.imageIndex % getSprite().getImageNumber();
		
		if (this.imageIndex < 0)
			this.imageIndex += getSprite().getImageNumber();
	}
}