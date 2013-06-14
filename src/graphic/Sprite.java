package graphic;

import java.io.FileNotFoundException;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This object represents a drawn image that can be animated. Sprites are
 * meant to be used in multiple objects and those objects should handle the
 * animation (this class merely loads and provides all the neccessary images)
 *
 * @author Gandalf.
 *         Created 27.11.2012.
 */
public class Sprite
{	
	// ATTRIBUTES	-------------------------------------------------------
	
	private PImage strip;
	private PImage[] images;
	
	private int origX, origY;
	private String spritename;
	
	
	// CONSTRUCTOR	-------------------------------------------------------
	
	/**
	 * This method creates a new sprite based on the information provided by 
	 * the caller. The images are loaded from a strip that contains one or more 
	 * images.
	 *
	 * @param filename The location of the loaded image (data/...)
	 * @param numberOfImages How many separate images does the strip contain?
	 * @param originX the x-coordinate of the sprite's origin (Pxl)
	 * @param originY the y-coordinate of the sprite's origin (Pxl)
	 * @param spritename The name of the sprite. This is used in finding the sprite later
	 * @param applet The application with which the images are loaded
	 * @throws FileNotFoundException If an image can't be loaded with the given 
	 * filename, the constructor will throw this exception. It is advised to 
	 * not use the sprite since the 
	 * object can't function properly if this happens
	 */
	public Sprite(String filename, int numberOfImages, int originX, int originY,
			String spritename, PApplet applet) throws FileNotFoundException
	{
		// Checks the variables
		if (filename == null || numberOfImages < 0 || applet == null)
			throw new IllegalArgumentException();
		
		// Initializes attributes
		this.origX = originX;
		this.origY = originY;
		this.spritename = spritename;
		
		// Loads the image
		this.strip = applet.loadImage(filename);
		if (this.strip == null)
		{
			System.err.println("Could not load the image: " + filename);
			throw new FileNotFoundException();
		}
		
		// Creates the subimages
		this.images = new PImage[numberOfImages];
		
		for (int i = 0; i < numberOfImages; i++)
		{
			// Calculates the needed variables
			int sx, sw;
			sw = this.strip.width / numberOfImages;
			sx = i*sw;
			
			// TODO: Test this at some point!
			this.images[i] = this.strip.get(sx, 0, sw, this.strip.height);
		}
	}
	
	
	// GETTERS & SETTERS	------------------------------------------------
	
	/**
	 * @return returns how many subimages exist within this sprite
	 */
	public int getImageNumber()
	{
		return this.images.length;
	}
	
	/**
	 * @return The x-coordinate of the origin of the sprite (Pxl)
	 */
	public int getOriginX()
	{
		return this.origX;
	}
	
	/**
	 * @return The y-coordinate of the origin of the sprite (Pxl)
	 */
	public int getOriginY()
	{
		return this.origY;
	}
	
	/**
	 * @return How wide a single subimage is (pxl)
	 */
	public int getWidth()
	{
		return getSubImage(0).width;
	}
	
	/**
	 * @return How tall a single subimage is (pxl)
	 */
	public int getHeight()
	{
		return this.strip.height;
	}
	
	/**
	 * @return The unique index / name of the sprite
	 */
	public String getName()
	{
		return this.spritename;
	}
	
	
	// METHODS	------------------------------------------------------------
	
	/**
	 * This method returns a single subimage from the sprite.
	 *
	 * @param imageIndex The index of the image to be drawn [0, numberOfImages]
	 * @return The subimage from the given index
	 */
	public PImage getSubImage(int imageIndex)
	{
		// Checks the given index and adjusts it if needed
		if (imageIndex < 0 || imageIndex >= this.images.length)
			imageIndex = Math.abs(imageIndex % this.images.length);
		
		return this.images[imageIndex];
	}
}
