package listeners;

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
	 * This method is called each time the listening object collides with 
	 * an object
	 *
	 * @param collided
	 */
	public void onCollision(Collidable collided);
}
