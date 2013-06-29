package listeners;

import java.awt.Point;
import java.util.ArrayList;

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
	 * @param colpoints The points in which the collision(s) happened
	 * @param collided The object with which the collision(s) happened
	 */
	public void onCollision(ArrayList<Point> colpoints, Collidable collided);
}
