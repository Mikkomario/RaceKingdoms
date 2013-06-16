package camera;

import handlers.DrawableHandler;
import listeners.CameraListener;

/**
 * This class follows the camera and draws objects. It only draws objects that 
 * will be shown on screen
 *
 * @author Gandalf.
 *         Created 16.6.2013.
 */
public class CameraDrawer extends DrawableHandler implements CameraListener
{
	// ATTRIBUTES	----------------------------------------------------
	
	private boolean active;
	private int camerax, cameray, cameraw, camerah;
	
	
	// CONSTRUCTOR	----------------------------------------------------
	
	/**
	 * Creates a new cameradrawer. The drawer is not added to any handler 
	 * and must be drawn manually with the drawSelf() -method
	 *
	 * @param autodeath Will the drawer die when it doesn't have anything to 
	 * draw anymore
	 */
	public CameraDrawer(boolean autodeath)
	{
		super(autodeath, null);
		
		// Initializes attributes
		this.active = true;
		this.camerah = 0;
		this.cameraw = 0;
		this.camerax = 0;
		this.cameray = 0;
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
	public void informCameraPosition(int posx, int posy, int w, int h)
	{
		this.camerax = posx;
		this.cameray = posy;
		this.cameraw = w;
		this.camerah = h;
	}
	
	
	// OTHER METHODS	---------------------------------------------------
	
	private void setOutsideInvisible()
	{
		// Goes through all the drawables and sets the ones that are outside 
		// invisible
		// TODO: Finish
	}
}
