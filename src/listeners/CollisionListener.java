package listeners;

import java.awt.Point;
import java.util.HashMap;

import handleds.Collidable;
import handleds.LogicalHandled;

/**
 * Collisionlisteners are interested in collisions and react to them somehow. 
 * Collisionlisteners can be handled like any other logical handled
 *
 * @author Gandalf.
 *         Created 18.6.2013.
 */
public interface CollisionListener extends LogicalHandled
{
	/**
	 * @return The points which are used for collision tests. Larger tables 
	 * are a lot more precise but also much slower. The points should be 
	 * absolute in-game pixels
	 */
	public Point[] getCollisionPoints();
	
	/**
	 * This method is called each time the listening object collides with 
	 * an object
	 * @param collisionmap The map that holds each of the points where the object 
	 * collided and the corresponding collided object that was on the position
	 */
	public void onCollision(HashMap<Point, Collidable> collisionmap);
}
