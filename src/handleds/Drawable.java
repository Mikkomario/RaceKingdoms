package handleds;

import processing.core.PApplet;

/**
 * All objects which implement this class are drawn at some point
 *
 * @author Gandalf.
 *         Created 26.11.2012.
 */
public interface Drawable extends Handled
{
	/**
	 *	Draws the object to the applet
	 *
	 *@param applet The applet to which the object is drawn
	 */
	public void drawSelf(PApplet applet);
	
	/**
	 * @return Should the object be drawn at this time
	 */
	public boolean isVisible();
	
	/**
	 * Tries to set the object visible
	 *
	 * @return Was the object made visible
	 */
	public boolean setVisible();
	
	/**
	 * Tries to momentarily make the object invisible
	 *
	 * @return Was the object made invisible
	 */
	public boolean setInvisible();
	
	/**
	 * @return How deep should the object be drawn (object with positive depth 
	 * are drawn to the bottom, objects with negative depth are drawn to the top)
	 */
	public int getDepth();
	
	/**
	 * Tries to change the objects depth
	 * 
	 * @param depth The object's new depth (negative = top, positive = bottom)
	 * @return Was the object's depth successfully changed
	 */
	public boolean setDepth(int depth);
}
