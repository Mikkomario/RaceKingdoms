package camera;

import drawnobjects.PhysicDrawnObject;
import handlers.ActorHandler;
import handlers.DrawableHandler;

/**A camera, which follows the given object around.
 * 
 * @author Unto 18.6.2013
 *
 */
public class FollowerCamera extends BasicCamera{
	
	//ATTRIBUTES	----------------------------------------------------
	
	private PhysicDrawnObject followed;

	
	//CONSTRUCTOR	---------------------------------------------------
	
	/**
	 * 
	 * @param drawerdrawer The drawablehandler that will draw the camera and objects 
	 * it showsactorhandler The actorhandler that informs the camera about 
	 * the act-event
	 * @param screenWidth The width of the screen
	 * @param screenHeight The height of the screen
	 * @param followed	The followed PhysicDrawnObject e.g. the Car
	 */
	public FollowerCamera(DrawableHandler drawer,
			ActorHandler actorhandler, int screenWidth, int screenHeight, 
			PhysicDrawnObject followed) {
		
		super((int)(-followed.getX()), (int)(-followed.getY()), drawer, 
				actorhandler, screenWidth, screenHeight);

		this.followed = followed;
	}
	
	//IMPLEMENTED METHODS	--------------------------------------------
	
	@Override
	public void act(){
		super.act();
		setPosition(-followed.getX(), -followed.getY());
	}

}
