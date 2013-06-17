package camera;

import processing.core.PApplet;
import drawnobjects.DrawnObject2D;
import racekingdoms.HelpMath;
import handlers.CameraListenerHandler;
import handlers.DrawnObjectHandler;
import listeners.CameraListener;

/**
 * This class follows the camera and draws objects. It only draws objects that 
 * will be shown on screen
 *
 * @author Gandalf.
 *         Created 16.6.2013.
 */
public class CameraDrawer extends DrawnObjectHandler implements CameraListener
{
	// ATTRIBUTES	----------------------------------------------------
	
	private boolean active;
	private int camerax1, cameray1, camerax2, cameray2;
	
	// TODO: Finish and check if the whole drawer is even needed!
	
	
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new cameradrawer. The drawer is not added to any handler 
	 * and must be drawn manually with the drawSelf() -method
	 *
	 * @param autodeath Will the drawer die when it doesn't have anything to 
	 * draw anymore
	 * @param informer The listenerhandler that will inform the drawer about 
	 * the camera's position (Optional)
	 */
	public CameraDrawer(boolean autodeath, CameraListenerHandler informer)
	{
		super(autodeath, null);
		
		// Initializes attributes
		this.active = true;
		this.camerax1 = 0;
		this.camerax2 = 0;
		this.cameray1 = 0;
		this.cameray2 = 0;
		
		// Adds the object to the informer
		if (informer != null)
			informer.addListener(this);
	}
	
	
	// IMPLEMENTED METHODS	--------------------------------------------

	@Override
	public boolean isActive()
	{
		return this.active;
	}

	@Override
	public boolean activate()
	{
		this.active = true;
		return true;
	}

	@Override
	public boolean inActivate()
	{
		this.active = false;
		return true;
	}

	@Override
	public void informCameraPosition(int posx, int posy, int w, int h, int angle)
	{
		this.camerax1 = posx - w / 2;
		this.camerax2 = posx + w / 2;
		this.cameray1 = posy - h / 2;
		this.cameray2 = posy + h / 2;
	}
	
	@Override
	public void drawSelf(PApplet applet)
	{
		// Only draws objects that are within the camera's range
		// TODO: Add more precise checking and test if this is even needed
		// TODO: Doesn't understand rotation
		// TODO: Probably should be done with objectCollides -method (which is heavy...)
		for (int i = 0; i < getHandledNumber(); i++)
		{
			DrawnObject2D d = getDrawnObject(i);
			
			if (!d.isVisible() || 
					HelpMath.pointIsInRange(d.getPosition(), this.camerax1, 
					this.camerax2, this.cameray1, this.cameray2))
				continue;
			
			d.drawSelf(applet);
		}
	}
	
	
	// OTHER METHODS	---------------------------------------------------
	
	/*
	private void setOutsideInvisible()
	{
		// Goes through all the drawables and sets the ones that are outside 
		// invisible
		// TODO: Finish and / or check if its even needed
		
		// Currently only checks if the origin of the objects is outside the drawn area
		for (int i = 0; i < getHandledNumber(); i++)
		{
			DrawnObject2D d = getDrawnObject(i);
			boolean dincamera = HelpMath.pointIsInRange(d.getPosition(), 
					this.camerax1, this.camerax2, this.cameray1, this.cameray2);
			
			if (!d.isVisible() && dincamera)
				d.setVisible();
			else if (d.isVisible() && !dincamera)
				d.setInvisible();
		}
	}
	*/
}
