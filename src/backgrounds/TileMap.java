package backgrounds;

import java.util.ArrayList;

import processing.core.PApplet;
import graphic.SpriteBank;
import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.DrawableHandler;
import helpAndEnums.CollisionType;
import helpAndEnums.DepthConstants;
import drawnobjects.DimensionalDrawnObject;

/**
 * Tilemaps hold a certain number of tiles. Tilemaps can be created using tables 
 * telling which values to use
 *
 * @author Gandalf.
 *         Created 9.7.2013.
 */
public class TileMap extends DimensionalDrawnObject
{
	// ATTRIBUTES	------------------------------------------------------
	
	private int width, height, tilewidth, tileheight;
	private short[] bankindexes, rotations, xscales, yscales, nameindexes;
	private boolean initialized;
	private DrawableHandler tiledrawer, parentdrawer;
	private ActorHandler tileanimator;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new uninitialized tilemap, giving it the information it 
	 * needs to initialize itself. 
	 *
	 * @param x The tilemap's top-left x-coordinate
	 * @param y The tilemap's top-left y-coordinate
	 * @param drawer The drawablehandler that will draw the tiles in the map
	 * @param animator The actorhandler that will animate the tiles (optional)
	 * @param collidablehandler The collidableHandler that will handle the map's 
	 * collision checking (optional)
	 * @param width How many tiles the map holds horzontally
	 * @param height How many tiles the map holds vertically 
	 * @param tilewidth How wide the tiles are (in pixels)
	 * @param tileheight How high the tiles are (in pixels)
	 * @param bankindexes A table telling which index for a spritebank is 
	 * used in which tile
	 * @param rotations A table telling how much each tile is rotated
	 * @param xscales A table telling how the tiles are flipped around the x-axis
	 * @param yscales A table telling how the tiles are flipped around the y-axis
	 * @param nameindexes A table telling which index is used for each tile to 
	 * find their spritename in a spritebank
	 */
	public TileMap(int x, int y, DrawableHandler drawer, ActorHandler animator, 
			CollidableHandler collidablehandler, 
			int width, int height, int tilewidth, int tileheight, 
			short[] bankindexes, short[] rotations, short[] xscales, 
			short[] yscales, short[] nameindexes)
	{
		super(x, y, DepthConstants.BOTTOM - 5, true, CollisionType.BOX, drawer, 
				collidablehandler);
		
		// Initializes attributes
		this.initialized = false;
		this.width = width;
		this.height = height;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
		this.bankindexes = bankindexes;
		this.rotations = rotations;
		this.xscales = xscales;
		this.yscales = yscales;
		this.nameindexes = nameindexes;
		
		this.parentdrawer = drawer;
		this.tiledrawer = null;
		// Only uses animation if actorhandler was specified
		if (animator != null)
			this.tileanimator = new ActorHandler(false, animator);
		else
			this.tileanimator = null;
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public int getOriginX()
	{
		return getWidth() / 2;
	}

	@Override
	public int getOriginY()
	{
		return getHeight() / 2;
	}

	@Override
	public void drawSelfBasic(PApplet applet)
	{
		// Is never used
	}
	
	@Override
	public void drawSelf(PApplet applet)
	{	
		// Draws the tiles if they are initialized
		if (this.initialized)
			this.tiledrawer.drawSelf(applet);	
		// Doesn't draw the map itself so no drawSelfBasic is called
	}
	
	@Override
	public boolean kill()
	{
		// Also kills all the tiles and clears the data
		clear();
		this.bankindexes = null;
		this.rotations = null;
		this.xscales = null;
		this.yscales = null;
		this.nameindexes = null;
		
		if (this.tileanimator != null)
			this.tileanimator.kill();
		
		return super.kill();
	}
	
	@Override
	public boolean isVisible()
	{
		// Only initialized tilemaps are visible
		return super.isVisible() && this.initialized;
	}
	
	@Override
	public int getWidth()
	{
		return this.width * this.tilewidth;
	}

	@Override
	public int getHeight()
	{
		return this.height * this.tileheight;
	}
	
	
	// OTHER METHODS	-------------------------------------------------
	
	/**
	 * Clears the map of tiles, freeing the used memory. 
	 * The tiles can be recreated with the initialize method.
	 */
	public void clear()
	{
		// Kills all the tiles and sets the map into uninitialized state
		this.initialized = false;
		if (this.tiledrawer != null)
		{
			this.tiledrawer.kill();
			this.tiledrawer = null;
		}
	}
	
	/**
	 * Initializes the map so that it can be drawn. The map may be cleared 
	 * after it's no longer needed.
	 *
	 * @param banks A list containing the spritebanks used in the map
	 * @param texturenames A list containing the spritenames of the textures 
	 * in the spritebanks
	 */
	public void initialize(ArrayList<SpriteBank> banks, 
			ArrayList<String> texturenames)
	{
		// If the map was already initialized, does nothing
		if (this.initialized)
			return;
		
		// Initializes the tiledrawer
		this.tiledrawer = new DrawableHandler(false, false, getDepth(), 
				this.parentdrawer);
		
		// Creates all the tiles
		for (int i = 0; i < this.width * this.height; i++)
		{
			// If the bankindex or nameindex is negative, that means that the 
			// tile should be ignored
			if (this.bankindexes[i] < 0 || this.nameindexes[i] < 0)
				continue;
			
			int x = (int) getX() - getOriginX() + (i % this.height) * this.tilewidth;
			int y = (int) getY() - getOriginY() + (i / this.width) * this.tileheight;
			Tile newtile = new Tile(x, y, this.tiledrawer, this.tileanimator, 
					banks.get(this.bankindexes[i]), 
					texturenames.get(this.nameindexes[i]), 
					this.tilewidth, this.tileheight);
			// Rotates and scales the tile
			newtile.setAngle(this.rotations[i]);
			newtile.scale(this.xscales[i], this.yscales[i]);
			// Also changes the object's position so that the starting position 
			// becomes the left corner
			newtile.addPosition(-newtile.getOriginX(), -newtile.getOriginY());
		}
	}
}
