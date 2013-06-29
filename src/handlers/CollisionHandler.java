package handlers;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import listeners.CollisionListener;
import handleds.Actor;
import handleds.Collidable;
import handleds.Handled;

/**
 * A handler that checks collisions between multiple collisionlisteners and 
 * Collidables
 *
 * @author Gandalf.
 *         Created 18.6.2013.
 */
public class CollisionHandler extends LogicalHandler implements Actor
{
	// ATTRIBUTES	-----------------------------------------------------
	
	private CollidableHandler collidablehandler;
	
	
	// CONSTRUCTOR	-----------------------------------------------------
	
	/**
	 * Creates a new empty collisionhandler
	 *
	 * @param autodeath Will the handler die when it runs out of listeners
	 * @param superhandler Which actorhandler will inform the handler about 
	 * the act-event (Optional)
	 */
	public CollisionHandler(boolean autodeath, ActorHandler superhandler)
	{
		super(autodeath, superhandler);
		
		// Initializes attributes
		this.collidablehandler = new CollidableHandler(false, null);
	}
	
	
	// IMPLEMENTED METHODS	--------------------------------------------

	@Override
	public void act()
	{
		// Checks collisions between all the listeners and collidables
		for (int listenerind = 0; listenerind < getHandledNumber(); listenerind++)
		{
			// Remembers the important data about the listener
			CollisionListener listener = getCollisionListener(listenerind);
			
			// Inactive listeners are not counted
			if (!listener.isActive())
				continue;
			
			Point[] colpoints = listener.getCollisionPoints();
			HashMap<Collidable, ArrayList<Point>> collidedpoints = 
					new HashMap<Collidable, ArrayList<Point>>();
			
			for (int colind = 0; 
					colind < this.collidablehandler.getHandledNumber(); colind++)
			{
				// Remembers the collidable
				Collidable c = this.collidablehandler.getCollidable(colind);
				
				// Non-solid collidables cannot collide
				if (!c.isSolid())
					continue;
				
				// Listener cannot collide with itself
				if (listener.equals(c))
					continue;
				
				// Checks all points if they would collide
				for (int pointi = 0; pointi < colpoints.length; pointi++)
				{
					Collidable collider = c.pointCollides(colpoints[pointi].x, 
							colpoints[pointi].y);
					
					if (collider == null)
						continue;
						//System.out.println("Foundcollision");

					// The arraylist may need to be initialized
					if (!collidedpoints.containsKey(collider))
						collidedpoints.put(collider, new ArrayList<Point>());
					// Remembers the point and the collided object
					collidedpoints.get(collider).add(colpoints[pointi]);
				}
			}
			
			// Informs the listener about each object it collided with
			for (Collidable c: collidedpoints.keySet())
				listener.onCollision(collidedpoints.get(c), c);
		}
	}
	
	@Override
	public void addHandled(Handled h)
	{
		// Only adds collisionlisteners and collidables
		if (h instanceof CollisionListener)
			super.addHandled(h);
		else if (h instanceof Collidable)
			this.collidablehandler.addCollidable((Collidable) h);
	}
	
	
	// OTHER METHODS	--------------------------------------------------
	
	/**
	 * Adds a new collisionlistener to the checked listeners
	 *
	 * @param c The new collisionlistener
	 */
	public void addCollisionListener(CollisionListener c)
	{
		super.addHandled(c);
	}
	
	/**
	 * Adds a new collidable to the list of checkked collidables
	 *
	 * @param c The collidable to be added
	 */
	public void addCollidable(Collidable c)
	{
		this.collidablehandler.addCollidable(c);
	}
	
	private CollisionListener getCollisionListener(int index)
	{
		Handled maybeListener = getHandled(index);
		
		if (maybeListener instanceof CollisionListener)
			return (CollisionListener) maybeListener;
		else
			return null;
	}
}
