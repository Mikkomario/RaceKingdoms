package camera;

import processing.core.PApplet;
import drawnobjects.DrawnObject2D;
import handlers.DrawnObjectHandler;

/**
 * This class follows the camera and draws objects. It only draws objects that 
 * will be shown on screen
 *
 * @author Gandalf.
 *         Created 16.6.2013.
 */
public class CameraDrawer extends DrawnObjectHandler
{
	// ATTRIBUTES	----------------------------------------------------
	
	private BasicCamera camera;
	
	
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new cameradrawer. The drawer is not added to any handler 
	 * and must be drawn manually with the drawSelf() -method
	 *
	 * @param autodeath Will the drawer die when it doesn't have anything to 
	 * draw anymore
	 * @param camera The camera that draws the drawer
	 */
	public CameraDrawer(boolean autodeath, BasicCamera camera)
	{
		super(autodeath, null);
		
		// Initializes attributes
		this.camera = camera;
	}
	
	
	// IMPLEMENTED METHODS	--------------------------------------------
	
	@Override
	public void drawSelf(PApplet applet)
	{
		// Only draws objects that are within the camera's range
		for (int i = 0; i < getHandledNumber(); i++)
		{
			DrawnObject2D d = getDrawnObject(i);
			
			// Doesn't draw invisible objects
			if (!d.isVisible())
				continue;
			
			// Only draws object inside the camera's vision
			if (this.camera.objectCollides(d))
				d.drawSelf(applet);
		}
	}
}
