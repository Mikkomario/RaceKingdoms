package worlds;

import graphic.SpriteBank;
import handleds.Collidable;
import handlers.ActorHandler;
import handlers.CollidableHandler;
import handlers.DrawableHandler;

import java.util.ArrayList;

import backgrounds.Background;
import backgrounds.TileMap;

/**
 * Dimensional room is a room that has a position and size
 *
 * @author Gandalf.
 *         Created 12.7.2013.
 */
public class DimensionalRoom extends Room implements Collidable
{
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new dimensionalroom with the given size to the given position. 
	 * A new tilemap is also created into the room using the given information. 
	 * The room has the given background(s).
	 * 
	 * @param x The room's x-coordinate (in pixels)
	 * @param y The room's y-coordinate (in pixels)
	 * @param drawer The drawableHandler that will draw the contents of the room
	 * @param animator The actorhandler that will animate the background(s) and tiles 
	 * in the room (optional)
	 * @param collidablehandler The collidableHandler that will handle the room's 
	 * collision checking (optional)
	 * @param width The width of the room (in pixels)
	 * @param height The height of the room (in pixels)
	 * @param xtiles How many tiles the room has horizontally (>= 0)
	 * @param ytiles How many tiles the room has vertically (>= 0)
	 * @param bankindexes A table telling which index for a spritebank is 
	 * used in which tile
	 * @param rotations A table telling how much each tile is rotated
	 * @param xscales A table telling how the tiles are flipped around the x-axis
	 * @param yscales A table telling how the tiles are flipped around the y-axis
	 * @param nameindexes A table telling which index is used for each tile to 
	 * find their spritename in a spritebank
	 * @param backgrounds The background(s) used in the room
	 * @param tiletexturebanks A list of spritebanks containing the textures used in tiles
	 * @param tiletexturenames
	 */
	public DimensionalRoom(int x, int y, DrawableHandler drawer, 
			ActorHandler animator, CollidableHandler collidablehandler, 
			int width, int height, int xtiles, int ytiles, 
			short[] bankindexes, short[] rotations, short[] xscales, 
			short[] yscales, short[] nameindexes, 
			ArrayList<Background> backgrounds, 
			ArrayList<SpriteBank> tiletexturebanks,
			ArrayList<String> tiletexturenames)
	{
		super(backgrounds, new TileMap(x, y, drawer, animator, collidablehandler, 
				xtiles, ytiles, width / xtiles, height / ytiles, bankindexes, 
				rotations, xscales, yscales, nameindexes), tiletexturebanks, 
				tiletexturenames);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public Collidable pointCollides(int x, int y)
	{
		// Room uses tilemap for collision checking
		return getTiles().pointCollides(x, y);
	}

	@Override
	public boolean isSolid()
	{
		// Uses tilemap for collision checking
		return getTiles().isSolid();
	}

	@Override
	public boolean makeSolid()
	{
		// Uses tilemap for collision checking
		return getTiles().makeSolid();
	}

	@Override
	public boolean makeUnsolid()
	{
		// Uses tilemap for collision checking
		return getTiles().makeUnsolid();
	}
}
