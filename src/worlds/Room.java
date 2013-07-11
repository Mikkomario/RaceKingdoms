package worlds;

import java.util.ArrayList;

import common.GameObject;

import backgrounds.Background;
import backgrounds.TileMap;

/**
 * Room represents a single restricted area in a game. A room contains a 
 * background and a tilemap as well as a group of objects. A room can start 
 * and end and it will inform objects about such events.
 *
 *
 * @author Gandalf.
 *         Created 11.7.2013.
 */
public class Room
{
	// TODO: Add roomlistener interface and make this class inform and handle them
	// in fact, make roomlistenerhandler class as well
	
	// ATTRIBUTES	-----------------------------------------------------
	
	private ArrayList<Background> backgrounds;
	private TileMap tilemap;
	private ArrayList<GameObject> objects;
	private int width, height;
	private boolean active;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new room, filled with backgrounds, tiles and objects. 
	 * The room will remain inactive until started.
	 *
	 * @param width The width of the room (in pixels)
	 * @param height The height of the room (in pixels)
	 * @param backgrounds The background(s) used in the room. Use empty list or 
	 * null if no backgrounds will be used. The backgrounds should cover the room's 
	 * area that is not covered by tiles.
	 * @param tilemap The tilemap used in the room (null if no tiles are used)
	 * @param objects The objects that will be placed inside the room
	 */
	public Room(int width, int height, ArrayList<Background> backgrounds, 
			TileMap tilemap, ArrayList<GameObject> objects)
	{
		// Initializes attributes
		this.width = width;
		this.height = height;
		this.tilemap = tilemap;
		this.objects = objects;
		this.backgrounds = backgrounds;
		this.active = false;
	}
	
	
	// GETTERS & SETTERS	---------------------------------------------
	
	/**
	 * Changes the size of the room
	 *
	 * @param width The room's new width (in pixels)
	 * @param height The room's new height (in pixels)
	 */
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	/**
	 * @return The room's width (in pixels)
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * @return The room's height (in pixels)
	 */
	public int getHeight()
	{
		return this.height;
	}
	
	/**
	 * Changes the backgrounds shown in the room
	 *
	 * @param backgrounds The new background(s) shown in the room (null if 
	 * no background is used in the room)
	 */
	public void setBackgrounds(ArrayList<Background> backgrounds)
	{
		this.backgrounds = backgrounds;
	}
	
	/**
	 * @return The current background(s) shown in the room
	 */
	public ArrayList<Background> getBackgrounds()
	{
		return this.backgrounds;
	}
	
	/**
	 * @return The tilemap used in the room
	 */
	public TileMap getTiles()
	{
		return this.tilemap;
	}
	
	/**
	 * Changes the room's tilemap
	 *
	 * @param tiles The new tilemap used in the room (null if no tiles are used)
	 */
	public void setTiles(TileMap tiles)
	{
		this.tilemap = tiles;
	}
	
	/**
	 * @return Is the room currently in use.
	 */
	public boolean isActive()
	{
		return this.active;
	}
	
	// TODO: Continue
}
