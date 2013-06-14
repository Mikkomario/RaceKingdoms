package handlers;

import handleds.Actor;
import handleds.Handled;


/**
 * The object from this class will control multiple actors, calling their 
 * act-methods and removing them when necessary
 *
 * @author Gandalf.
 *         Created 27.11.2012.
 */
public class ActorHandler extends LogicalHandler implements Actor
{	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new actorhandler. Actors must be added manually later (as soon 
	 * as possible if the autodeath is on)
	 *
	 * @param autodeath Will the handler die if there are no living actors to be handled
	 */
	public ActorHandler(boolean autodeath)
	{
		super(autodeath);
	}
	
	
	// IMPLEMENTED METHODS	----------------------------------------------

	@Override
	public void act()
	{
		//System.out.println(this.actors.size());
		// This calls for all active actor's act method
		for (int i = 0; i < getHandledNumber(); i++)
		{
			//System.out.println("Tries to act");
			if (getActor(i).isActive())
			{
				//System.out.println("Acts");
				getActor(i).act();
			}
		}
	}
	
	@Override
	protected void addHandled(Handled h)
	{
		// Actorhandler only adds actors
		if (h instanceof Actor)
			super.addHandled(h);
	}
	
	// OTHER METHODS	---------------------------------------------------
	
	// Casts the handled to actor (or null)
	private Actor getActor(int index)
	{
		Handled maybeActor = getHandled(index);
		
		if (maybeActor instanceof Actor)
			return (Actor) maybeActor;
		else
			return null;
	}
	
	/**
	 * Adds a new actor to the handled actors
	 *
	 * @param a The actor to be added
	 */
	public void addActor(Actor a)
	{
		super.addHandled(a);
	}
}
