package handleds;

import drawnobjects.DrawnObject2D;

/**
 * Collidable objects can collide with each other
 *
 * @author Gandalf.
 *         Created 18.6.2013.
 */
public interface Collidable extends Handled
{
	/**
	 * Checks whether an object is colliding with the listener
	 *
	 * @param object The object that might collide with the listener
	 * @return Do the two objects collide
	 */
	public boolean objectCollides(Collidable object);
	
	/**
	 * @return Can the object be collided with at this time
	 */
	public boolean isSolid();
	
	/**
	 * Tries to make the object solid so that the objects will collide with it
	 * @return Was the object made solid
	 */
	public boolean makeSolid();
	
	/**
	 * Tries to make the object unsolid so that no object can collide with it
	 *
	 * @return Was the object successfully made solid
	 */
	public boolean makeUnsolid();
}
