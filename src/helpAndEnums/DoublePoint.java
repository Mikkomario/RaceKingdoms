package helpAndEnums;

/**
 * DoublePoint is a point that holds the data in a double format. Doublepoint 
 * should be used in situations that require very good accuracy
 *
 * @author Gandalf.
 *         Created 30.6.2013.
 */
public class DoublePoint
{
	// ATTRIBUTES	------------------------------------------------------
	
	private double x;
	private double y;
	
	
	// CONSTRUCTOR	------------------------------------------------------
	
	/**
	 * Creates a new point wiht the given coordinates
	 *
	 * @param x The x-coordinate of the point
	 * @param y The y-coordinate of the point
	 */
	public DoublePoint(double x, double y)
	{
		// Initializes attributes
		this.x = x;
		this.y = y;
	}
	
	
	// Getters & Setters
	
	/**
	 * @return The x-coordinate of the point
	 */
	public double getX()
	{
		return this.x;
	}
	
	/**
	 * @return The y-coordinate of the point
	 */
	public double getY()
	{
		return this.y;
	}
	
	/**
	 * Changes the point's x-coordinate 
	 *
	 * @param x The point's new x-coordinate
	 */
	public void setX(double x)
	{
		this.x = x;
	}
	
	/**
	 * Changes the point's y-coordinate
	 *
	 * @param y The point's new y-coordinate
	 */
	public void setY(double y)
	{
		this.y = y;
	}
}
